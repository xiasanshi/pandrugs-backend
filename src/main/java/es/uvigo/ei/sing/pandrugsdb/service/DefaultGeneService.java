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

import static es.uvigo.ei.sing.pandrugsdb.util.Checks.requireNonEmpty;
import static es.uvigo.ei.sing.pandrugsdb.util.Checks.requirePositive;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Service;

import es.uvigo.ei.sing.pandrugsdb.controller.GeneInformationController;
import es.uvigo.ei.sing.pandrugsdb.service.entity.GeneInteraction;

@Path("gene")
@Service
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class DefaultGeneService implements GeneService {
	@Inject
	private GeneInformationController controller;
	
	@GET
	@Path("/{geneSymbol}/interactions")
	@Override
	public GeneInteraction[] interactions(
		@PathParam("geneSymbol") String geneSymbol,
		@QueryParam("degree") @DefaultValue("1") int degree
	) {
		requireNonEmpty(geneSymbol);
		requirePositive(degree);
		
		return controller.interactions(geneSymbol, degree).stream()
			.sorted((gi1, gi2) -> gi1.getGeneSymbol().compareTo(gi2.getGeneSymbol()))
			.map(GeneInteraction::new)
		.toArray(GeneInteraction[]::new);
	}
}
