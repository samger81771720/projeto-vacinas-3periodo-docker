package model.service;
import model.entity.Contato;
import model.repository.ContatoRepository;

public class ContatoService {
	
	ContatoRepository contatoRepository = new ContatoRepository(); 
	
	public Contato consultarPorId(int id) {
		return contatoRepository.consultarPorId(id);
	}

	public Contato salvar(Contato contato) {
		return contatoRepository.salvar(contato);
	}
	
	public boolean alterar(Contato contato) {
		return contatoRepository.alterar(contato);
	}
}
