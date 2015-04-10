/*
 * #%L
 * PanDrugsDB Backend
 * %%
 * Copyright (C) 2015 Fátima Al-Shahrour, Elena Piñeiro, Daniel Glez-Peña and Miguel Reboiro-Jato
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

import static es.uvigo.ei.sing.pandrugsdb.service.ServiceUtils.createBadRequestException;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Service;

import es.uvigo.ei.sing.pandrugsdb.controller.GeneDrugController;
import es.uvigo.ei.sing.pandrugsdb.persistence.entity.GeneDrug;
import es.uvigo.ei.sing.pandrugsdb.service.entity.GeneDrugBasicInfos;

/**
 * Service to query the gene drugs lists.
 * 
 * @author Miguel Reboiro-Jato
 */
@Path("genedrug")
@Service
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class DefaultGeneDrugService implements GeneDrugService {
	@Inject
	private GeneDrugController controller;
	
	@Override
	@GET
	@Consumes(MediaType.WILDCARD)
	@Transactional
	public GeneDrugBasicInfos list(
		@QueryParam("gene") List<String> genes,
		@QueryParam("startPosition") Integer startPosition,
		@QueryParam("maxResults") Integer maxResults
	) throws BadRequestException, InternalServerErrorException {
		try {
			if (genes == null || genes.isEmpty()) 
				throw new IllegalArgumentException("At least one gene should be provided");
			
			final List<GeneDrug> geneDrugs = controller.searchForGeneDrugs(
				genes.toArray(new String[genes.size()]),
				startPosition, maxResults
			);
			
			return new GeneDrugBasicInfos(geneDrugs);
		} catch (IllegalArgumentException iae) {
			throw createBadRequestException(iae.getMessage());
		}
	}
}
