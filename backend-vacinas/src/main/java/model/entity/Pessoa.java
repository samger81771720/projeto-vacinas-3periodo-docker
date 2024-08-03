package model.entity;

import java.time.LocalDate;

public class Pessoa {
	
	public static final int USUARIO = 1;
	public static final int ADMINISTRADOR = 2;
	public static final String MASCULINO = "M";
	public static final String FEMININO = "F";
	public static final String OUTRO = "O";
	
	private int id;
	private Endereco enderecoDaPessoa;
	private Contato contatoDaPessoa;
	private String nome;
	private LocalDate dataNascimento;
	private String sexo;
	private String cpf;
	private String login;
	private String senha;
	private int tipo;
	private boolean doencaPreexistente;
	
	public Pessoa() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Endereco getEnderecoDaPessoa() {
		return enderecoDaPessoa;
	}
	public void setEnderecoDaPessoa(Endereco enderecoDaPessoa) {
		this.enderecoDaPessoa = enderecoDaPessoa;
	}
	public Contato getContatoDaPessoa() {
		return contatoDaPessoa;
	}
	public void setContatoDaPessoa(Contato contatoDaPessoa) {
		this.contatoDaPessoa = contatoDaPessoa;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public boolean isDoencaPreexistente() {
		return doencaPreexistente;
	}
	public void setDoencaPreexistente(boolean doencaPreexistente) {
		this.doencaPreexistente = doencaPreexistente;
	}
	
	
	
}
