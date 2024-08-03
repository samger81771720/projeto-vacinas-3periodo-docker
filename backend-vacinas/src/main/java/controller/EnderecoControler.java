package controller;

import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.entity.Endereco;
import model.service.EnderecoService;

@Path("/endereco")
public class EnderecoControler {

	EnderecoService enderecoService = new EnderecoService();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Endereco salvar(Endereco endereco) {
		return enderecoService.salvar(endereco);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public boolean alterar(Endereco endereco) {
		return enderecoService.alterar(endereco);
	}
	
	@DELETE
	@Path("/{id}")
	public boolean excluir(@PathParam("id")int id) {
		return enderecoService.excluir(id);
	}
	
	@GET
	@Path("/{id}")
	public Endereco consultarPorId(@PathParam("id")int id){
		return enderecoService.consultarPorId(id);
	}
	
	@GET
	@Path("/consultarTodosEnderecos")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Endereco> consultarTodos(){
		 return enderecoService.consultarTodos();
	}
	
}


