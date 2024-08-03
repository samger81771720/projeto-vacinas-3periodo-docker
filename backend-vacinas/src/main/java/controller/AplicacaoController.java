package controller;

import java.util.List;

import exception.ControleVacinasException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.dto.AplicacaoDTO;
import model.entity.Aplicacao;
import model.entity.Endereco;
import model.seletor.AplicacaoSeletor;
import model.seletor.VacinaSeletor;
import model.service.AplicacaoService;

@Path("/aplicacao")
public class AplicacaoController {
	
	AplicacaoService aplicacaoService = new AplicacaoService();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Aplicacao salvar(Aplicacao aplicacao) throws ControleVacinasException{
		return aplicacaoService.salvar(aplicacao);
	}
	
	@GET
	@Path("/{id}")
	public Aplicacao consultarPorId(@PathParam("id")int id){
		return aplicacaoService.consultarPorId(id);
	}
	
	@POST
	@Path("/filtroAplicacoesPessoa")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<AplicacaoDTO> consultarComFiltros(AplicacaoSeletor seletor){
		return aplicacaoService.consultarComFiltros(seletor);
	}
	
}
