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
package es.uvigo.ei.sing.pandrugsdb.service.genescore;

import static java.util.stream.Collectors.toMap;

import java.util.Map;
import java.util.function.Function;

import es.uvigo.ei.sing.pandrugsdb.persistence.entity.GeneDrug;

public class StaticGeneScoreCalculator implements GeneScoreCalculator {
	private final Map<String, Double> geneScore;
	
	public StaticGeneScoreCalculator(Map<String, Double> geneScore) {
		this.geneScore = geneScore;
	}

	@Override
	public double directGScore(GeneDrug geneDrug) {
		return this.geneScore.getOrDefault(geneDrug.getGeneSymbol(), 0d);
	}

	@Override
	public Map<String, Double> indirectGScores(GeneDrug geneDrug) {
		return geneDrug.getIndirectGeneSymbols().stream()
			.collect(toMap(
				Function.identity(),
				gene -> geneScore.getOrDefault(gene, 0d)
			));
	}
	
	@Override
	public double indirectGScore(GeneDrug geneDrug, String indirectGene) {
		return geneScore.getOrDefault(indirectGene, 0d);
	}
}