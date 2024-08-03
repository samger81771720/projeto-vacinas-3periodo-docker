package controller;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.entity.Contato;
import model.service.ContatoService;

@Path("/contato")
public class ContatoController {
	
	ContatoService contatoService = new ContatoService();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Contato salvar(Contato contato) {
		return contatoService.salvar(contato);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public boolean alterar(Contato contato) {
		return contatoService.alterar(contato);
	}
	
	@GET
	@Path("/{id}")
	public Contato consultarPorId(@PathParam("id")int id){
		return contatoService.consultarPorId(id);
	}
	
	
	
}
