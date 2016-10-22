/*
 * #%L
 * PanDrugsDB Backend
 * %%
 * Copyright (C) 2015 - 2016 Fátima Al-Shahrour, Elena Piñeiro, Daniel Glez-Peña and Miguel Reboiro-Jato
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
package es.uvigo.ei.sing.pandrugsdb.service;

import com.qmino.miredot.annotations.ReturnType;
import es.uvigo.ei.sing.pandrugsdb.controller.VariantsAnalysisController;
import es.uvigo.ei.sing.pandrugsdb.service.entity.ComputationStatusMetadata;
import es.uvigo.ei.sing.pandrugsdb.service.entity.UserLogin;
import es.uvigo.ei.sing.pandrugsdb.service.security.SecurityContextUserAccessChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.InputStream;

/**
 * Service to submit VCF Analysis computations, as well as to follow their status
 *
 * @autor Daniel Glez-Peña
 */
@Path("variantsanalysis")
@Service
@RolesAllowed({ "ADMIN", "USER" })
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class DefaultVariantsAnalysisService implements VariantsAnalysisService {
	private final static Logger LOG = LoggerFactory.getLogger(DefaultVariantsAnalysisService.class);

	@Inject
	private VariantsAnalysisController controller;


	@POST
	@Override
	@Path("/{login}")
	@Consumes(MediaType.WILDCARD)
	@ReturnType("java.lang.Integer")
	public Response startVariantsScoreUserComputation(
			@PathParam("login") UserLogin login,
			InputStream vcfFile,
			@Context SecurityContext security) throws ForbiddenException, NotAuthorizedException {

		final String userLogin = login.getLogin();
		final SecurityContextUserAccessChecker checker =
				new SecurityContextUserAccessChecker(security);
		System.out.println("INSIDE");
			return
				checker.doIfPrivileged(
					userLogin,
					() ->
						Response.ok(
							controller.startVariantsScopeUserComputation(login, vcfFile)
						).build()
					,
					() -> {
						LOG.error(String.format(
								"Illegal access to create a computation as user %s on behalf of user %s",
								checker.getUserName(), userLogin
						));

						throw new ForbiddenException("User "+userLogin+" is not you");
					}
				);

	}

	@Override
	@Path("{login}/{computationId}")
	@ReturnType("es.uvigo.ei.sing.pandrugsdb.service.entity.ComputationStatusMetadata")
	@GET
	public Response getComputationStatus(
			@PathParam("login") UserLogin login,
			@PathParam("computationId") Integer computationId,
			@Context SecurityContext security) throws ForbiddenException, NotAuthorizedException {

		final String userLogin = login.getLogin();
		final SecurityContextUserAccessChecker checker =
				new SecurityContextUserAccessChecker(security);


		return
			checker.doIfPrivileged(
					userLogin,
					() -> {
						if (!controller.getUserOfComputation(computationId).getLogin().equals(userLogin)) {
							throw new ForbiddenException("You have not a computation with id = " +computationId);
						}
						return Response.ok(
								controller.getComputationsStatus(computationId)
						).build();
					}
					,
					() -> {
						LOG.error(String.format(
								"Illegal access to get gene ranking for a computation as user %s on behalf of user %s",
								checker.getUserName(), userLogin
						));

						throw new ForbiddenException("User "+userLogin+" is not you");
					}
			);
	}
}