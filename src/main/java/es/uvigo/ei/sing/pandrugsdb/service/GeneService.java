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

import es.uvigo.ei.sing.pandrugsdb.service.entity.GeneInteraction;

public interface GeneService {
	/**
	 * Returns a list of genes that interact with a provided gene with a
	 * certain degree of distance.
	 * 
	 * @param geneSymbol the gene symbol of the reference gene.
	 * @param degree the maximum degree of distance.
	 * @return a list of gene interactions including the provided gene.
	 */
	public abstract GeneInteraction[] interactions(String geneSymbol, int degree);
}
