package model.entity;

public class Unidade {
	
	private int id;
	private String nome;
	private Endereco enderecoDaUnidade;
	private Contato contatoDaUnidade;
	
	public Unidade() {
		super();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Endereco getEnderecoDaUnidade() {
		return enderecoDaUnidade;
	}

	public void setEnderecoDaUnidade(Endereco enderecoDaUnidade) {
		this.enderecoDaUnidade = enderecoDaUnidade;
	}

	public Contato getContatoDaUnidade() {
		return contatoDaUnidade;
	}

	public void setContatoDaUnidade(Contato contatoDaUnidade) {
		this.contatoDaUnidade = contatoDaUnidade;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
