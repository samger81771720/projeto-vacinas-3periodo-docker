package model.entity;

public class Fabricante {
	
	private int id;
	private String nome;
	private Endereco enderecoDoFabricante;
	private Contato contatoDoFabricante;
	
	public Fabricante() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Endereco getEnderecoDoFabricante() {
		return enderecoDoFabricante;
	}

	public void setEnderecoDoFabricante(Endereco enderecoDoFabricante) {
		this.enderecoDoFabricante = enderecoDoFabricante;
	}

	public Contato getContatoDoFabricante() {
		return contatoDoFabricante;
	}

	public void setContatoDoFabricante(Contato contatoDoFabricante) {
		this.contatoDoFabricante = contatoDoFabricante;
	}
	
}


