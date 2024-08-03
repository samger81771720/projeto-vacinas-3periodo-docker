
package controller;

import java.util.List;

import exception.ControleVacinasException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.dto.VacinaDTO;
import model.entity.Endereco;
import model.entity.Pessoa;
import model.entity.Vacina;
import model.seletor.PessoaSeletor;
import model.seletor.VacinaSeletor;
import model.service.PessoaService;

@Path("/pessoa")
public class PessoaController {
	
	PessoaService pessoaService = new PessoaService();
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Pessoa logarController (Pessoa pessoa)throws ControleVacinasException {
		return pessoaService.efetuarLogin(pessoa);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Pessoa salvar(Pessoa novaPessoa) throws ControleVacinasException{
		return pessoaService.salvar(novaPessoa);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public boolean alterar(Pessoa pessoaAtualizada)throws ControleVacinasException {
		return pessoaService.alterar(pessoaAtualizada);
	}
	
	@GET
	@Path("/{id}")
	public Pessoa consultarPorId(@PathParam("id")int id){
		return pessoaService.consultarPorId(id);
	}
	
	@GET
	@Path("/consultarTodasPessoas")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Pessoa> consultarTodos(){
		 return pessoaService.consultarTodos();
	}
	
	@POST
	@Path("/filtroConsultarPessoas")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Pessoa> consultarPessoasComFiltro(PessoaSeletor seletor) throws ControleVacinasException{
		return pessoaService.consultarPessoasComFiltro(seletor);
	}
	
	@DELETE
	@Path("/{id}")
	public boolean excluir(@PathParam("id")int id) {
		return pessoaService.excluir(id);
	}
	
}
