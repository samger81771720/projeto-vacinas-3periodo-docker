package model.seletor;

public class VacinaSeletor extends BaseSeletor{
	
	private String nomeVacina; 
	private String categoria; 
	private int idadeMinima;
	private int idadeMaxima;
	private Boolean contraIndicacao; 
	private String nomeFabricante; 
	private String nomeUnidade; 
	private String nomeBairro;
	private String numeroCep;
	
	public VacinaSeletor() {
		super();
	}
	
	public String getNomeVacina() {
		return nomeVacina;
	}
	public void setNomeVacina(String nomeVacina) {
		this.nomeVacina = nomeVacina;
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
	public Boolean isContraIndicacao() {
		return contraIndicacao;
	}
	public void setContraIndicacao(Boolean contraIndicacao) {
		this.contraIndicacao = contraIndicacao;
	}
	public String getNomeFabricante() {
		return nomeFabricante;
	}
	public void setNomeFabricante(String nomeFabricante) {
		this.nomeFabricante = nomeFabricante;
	}
	public String getNomeUnidade() {
		return nomeUnidade;
	}
	public void setNomeUnidade(String nomeUnidade) {
		this.nomeUnidade = nomeUnidade;
	}
	public String getNomeBairro() {
		return nomeBairro;
	}
	public void setNomeBairro(String nomeBairro) {
		this.nomeBairro = nomeBairro;
	}
	public String getNumeroCep() {
		return numeroCep;
	}
	public void setNumeroCep(String numeroCep) {
		this.numeroCep = numeroCep;
	}
	
	public boolean temFiltro() {
		return 
						   (this.nomeVacina != null && this.nomeVacina.trim().length() > 0)
						|| (this.categoria != null && this.categoria.trim().length() > 0)
						|| (this.idadeMinima != 0)
						|| (this.idadeMaxima != 0)
						|| (this.isContraIndicacao() != null)
						|| (this.nomeFabricante != null && this.nomeFabricante.trim().length() > 0)
						|| (this.nomeUnidade != null && this.nomeUnidade.trim().length() > 0)
						|| (this.nomeBairro != null && this.nomeBairro.trim().length() > 0)
						|| (this.numeroCep != null && this.numeroCep.trim().length() > 0);
	}
	
}
