package model.entity;

public class Vacina {
	
	private int id;
	private String nome;
	private Fabricante fabricanteDaVacina;
	private String categoria;
	private int idadeMinima;
	private int idadeMaxima;
	private boolean contraIndicacao;
	
	public Vacina() {
		super();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Fabricante getFabricanteDaVacina() {
		return fabricanteDaVacina;
	}
	public void setFabricanteDaVacina(Fabricante fabricanteDaVacina) {
		this.fabricanteDaVacina = fabricanteDaVacina;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public int getIdadeMinima() {
		return idadeMinima;
	}
	public void setIdadeMinima(int idadeMinima) {
		this.idadeMinima = idadeMinima;
	}
	public int getIdadeMaxima() {
		return idadeMaxima;
	}
	public void setIdadeMaxima(int idadeMaxima) {
		this.idadeMaxima = idadeMaxima;
	}
	public boolean isContraIndicacao() {
		return contraIndicacao;
	}
	public void setContraIndicacao(boolean contraIndicacao) {
		this.contraIndicacao = contraIndicacao;
	}
	
}


