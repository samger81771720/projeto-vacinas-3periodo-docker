package model.dto;

import model.entity.Aplicacao;

public class AplicacaoDTO {
	
	private Aplicacao aplicacao;
	private String fabricanteDaVacinaAplicada;
	
	public Aplicacao getAplicacao() {
		return aplicacao;
	}

	public void setAplicacao(Aplicacao aplicacao) {
		this.aplicacao = aplicacao;
	}
	
	public String getFabricanteDaVacinaAplicada() {
		return fabricanteDaVacinaAplicada;
	}

	public void setFabricanteDaVacinaAplicada(String fabricanteDaVacinaAplicada) {
		this.fabricanteDaVacinaAplicada = fabricanteDaVacinaAplicada;
	}

}
