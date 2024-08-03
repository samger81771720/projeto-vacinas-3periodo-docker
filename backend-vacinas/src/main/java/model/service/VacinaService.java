package model.service;

import java.util.ArrayList;
import java.util.List;

import model.entity.Vacina;
import model.repository.VacinaRepository;

public class VacinaService {
	
	VacinaRepository vacinaRepository = new VacinaRepository();
	
	public Vacina consultarPorId(int id) {
		return vacinaRepository.consultarPorId(id);
	}
	
	public ArrayList<Vacina> consultarTodos() {
		return vacinaRepository.consultarTodos();
	}
	
	public boolean excluir(int id) {
		return vacinaRepository.excluir(id);
	}
	
	public Vacina salvar(Vacina novaVacina) {
		return vacinaRepository.salvar(novaVacina);
	}
	
	public boolean alterar(Vacina vacinaAlterada) {
		return vacinaRepository.alterar(vacinaAlterada);
	}
	
	public List<String> consultarTodasCategorias() {
		return vacinaRepository.consultarTodasCategorias();
	}

}
