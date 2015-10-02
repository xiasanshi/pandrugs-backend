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
package es.uvigo.ei.sing.pandrugsdb.persistence.entity;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.UniqueConstraint;

@Entity(name = "gene")
public class Gene implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "symbol")
	private String symbol;
	
	@Column(name = "approved_symbol")
	private String approvedSymbol;
	
	@Column(name = "previous_symbols")
	private String previousSymbols;
	@Column(name = "entrez_gene", nullable = true)
	private String entrezGene;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "gene_pathway",
		uniqueConstraints = @UniqueConstraint(columnNames = {"entrez_gene", "pathway_id"}),
		joinColumns = @JoinColumn(name = "entrez_gene", referencedColumnName = "entrez_gene", nullable = true),
		inverseJoinColumns = @JoinColumn(name = "pathway_id", referencedColumnName = "id")
	)
	private List<Pathway> pathways;
	
	Gene() {}
	
	public String getSymbol() {
		return symbol;
	}
	
	public String getApprovedSymbol() {
		return approvedSymbol;
	}
	
	public String getPreviousSymbols() {
		return previousSymbols;
	}
	
	public String getEntrezGene() {
		return entrezGene;
	}
	
	public List<Pathway> getPathways() {
		return Collections.unmodifiableList(this.pathways);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((approvedSymbol == null) ? 0 : approvedSymbol.hashCode());
		result = prime * result + ((entrezGene == null) ? 0 : entrezGene.hashCode());
		result = prime * result + ((previousSymbols == null) ? 0 : previousSymbols.hashCode());
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Gene other = (Gene) obj;
		if (approvedSymbol == null) {
			if (other.approvedSymbol != null)
				return false;
		} else if (!approvedSymbol.equals(other.approvedSymbol))
			return false;
		if (entrezGene == null) {
			if (other.entrezGene != null)
				return false;
		} else if (!entrezGene.equals(other.entrezGene))
			return false;
		if (previousSymbols == null) {
			if (other.previousSymbols != null)
				return false;
		} else if (!previousSymbols.equals(other.previousSymbols))
			return false;
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equals(other.symbol))
			return false;
		return true;
	}
}
