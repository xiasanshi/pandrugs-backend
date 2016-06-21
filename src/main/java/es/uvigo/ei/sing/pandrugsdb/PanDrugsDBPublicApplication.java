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
package es.uvigo.ei.sing.pandrugsdb;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.glassfish.jersey.server.validation.ValidationFeature;

import es.uvigo.ei.sing.pandrugsdb.service.DefaultCancerService;
import es.uvigo.ei.sing.pandrugsdb.service.DefaultGeneDrugService;
import es.uvigo.ei.sing.pandrugsdb.service.DefaultGeneService;
import es.uvigo.ei.sing.pandrugsdb.service.DefaultRegistrationService;
import es.uvigo.ei.sing.pandrugsdb.service.DefaultSessionService;
import es.uvigo.ei.sing.pandrugsdb.service.mime.GeneRankingMessageBodyReader;

@ApplicationPath("public")
public class PanDrugsDBPublicApplication extends ResourceConfig {
	public PanDrugsDBPublicApplication() {
		super(
			DefaultRegistrationService.class,
			DefaultGeneDrugService.class,
			DefaultSessionService.class,
			DefaultCancerService.class,
			DefaultGeneService.class
		);
		
		register(RequestContextFilter.class);
		register(JacksonFeature.class);
		register(ValidationFeature.class);
		register(UnexpectedExceptionMapper.class);
		register(GeneRankingMessageBodyReader.class);
	}
}
