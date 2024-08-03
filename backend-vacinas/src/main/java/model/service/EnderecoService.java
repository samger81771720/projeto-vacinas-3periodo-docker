package model.service;

import java.util.ArrayList;

import model.entity.Endereco;
import model.repository.EnderecoRepository;

public class EnderecoService {
	
	EnderecoRepository enderecoRepository = new EnderecoRepository();
	
	public Endereco salvar(Endereco endereco) {
		return enderecoRepository.salvar(endereco);
	}
	
	public boolean alterar(Endereco endereco) {
		return enderecoRepository.alterar(endereco);
	}
	
	public boolean excluir(int id) {
		return enderecoRepository.excluir(id);
	}
	
	public Endereco consultarPorId(int id) {
		return enderecoRepository.consultarPorId(id);
	}
	
	public ArrayList<Endereco> consultarTodos() {
		return enderecoRepository.consultarTodos();
	}

}
