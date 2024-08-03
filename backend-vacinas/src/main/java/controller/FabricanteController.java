package controller;

import java.util.List;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.entity.Endereco;
import model.entity.Fabricante;
import model.service.FabricanteService;

@Path("/fabricante")
public class FabricanteController {
	
	FabricanteService fabricanteService = new FabricanteService();
	
	@GET
	@Path("/{id}")
	public Fabricante consultarPorId(@PathParam("id")int id) {
		return fabricanteService.consultarPorId(id);
	}
	
	@GET
	@Path("/consultarTodos")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Fabricante> consultarTodos(){
		 return fabricanteService.consultarTodos();
	}

}
