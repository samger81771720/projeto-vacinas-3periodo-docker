package model.service;

import java.util.ArrayList;

import model.entity.Fabricante;
import model.repository.FabricanteRepository;

public class FabricanteService {
	
	FabricanteRepository fabricanteRepository = new FabricanteRepository();
	
	public Fabricante consultarPorId(int id) {
		return fabricanteRepository.consultarPorId(id);
	}
	
	public ArrayList<Fabricante> consultarTodos() {
		return fabricanteRepository.consultarTodos();
	}
	
}
