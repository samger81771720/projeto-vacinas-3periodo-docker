package model.entity;

import java.time.LocalDate;

public class Estoque {
	
	private Unidade unidade;
	private Vacina vacina;
	private int quantidade;
	private LocalDate dataLote;
	private LocalDate validade;
	
	public Estoque() {
		super();
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public Vacina getVacina() {
		return vacina;
	}

	public void setVacina(Vacina vacina) {
		this.vacina = vacina;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public LocalDate getDataLote() {
		return dataLote;
	}

	public void setDataLote(LocalDate dataLote) {
		this.dataLote = dataLote;
	}

	public LocalDate getValidade() {
		return validade;
	}

	public void setValidade(LocalDate validade) {
		this.validade = validade;
	}
	
}
