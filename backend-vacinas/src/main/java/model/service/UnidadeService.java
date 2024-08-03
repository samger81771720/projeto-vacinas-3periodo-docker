package model.service;

import java.util.ArrayList;
import java.util.List;

import model.entity.Estoque;
import model.entity.Unidade;
import model.repository.EstoqueRepository;
import model.repository.UnidadeRepository;

public class UnidadeService {
	
	UnidadeRepository unidadeRepository = new UnidadeRepository();
	
	public Unidade consultarPorId(int id) {
		return unidadeRepository.consultarPorId(id);
	}
	
	public List<Estoque> consultarEstoquesDaUnidade(int idUnidade) {
		return unidadeRepository.consultarEstoquesDaUnidade(idUnidade);
	}
	
	public ArrayList<Unidade> consultarTodos() {
		return unidadeRepository.consultarTodos();
	}
	
}
