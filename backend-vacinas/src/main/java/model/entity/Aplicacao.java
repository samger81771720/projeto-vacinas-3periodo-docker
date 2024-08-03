package model.entity;

import java.time.LocalDate;

public class Aplicacao {
	
	private int id;
	private Pessoa pessoaQueRecebeu;
	private Vacina vacinaAplicada;
	private Unidade unidadeOndeAplicou;
	private LocalDate dataAplicacao;
	
	public Aplicacao() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Pessoa getPessoaQueRecebeu() {
		return pessoaQueRecebeu;
	}
	public void setPessoaQueRecebeu(Pessoa pessoaQueRecebeu) {
		this.pessoaQueRecebeu = pessoaQueRecebeu;
	}
	public Vacina getVacinaAplicada() {
		return vacinaAplicada;
	}
	public void setVacinaAplicada(Vacina vacinaAplicada) {
		this.vacinaAplicada = vacinaAplicada;
	}
	public Unidade getUnidadeOndeAplicou() {
		return unidadeOndeAplicou;
	}
	public void setUnidadeOndeAplicou(Unidade unidadeOndeAplicou) {
		this.unidadeOndeAplicou = unidadeOndeAplicou;
	}
	public LocalDate getDataAplicacao() {
		return dataAplicacao;
	}
	public void setDataAplicacao(LocalDate dataAplicacao) {
		this.dataAplicacao = dataAplicacao;
	}
	
}

