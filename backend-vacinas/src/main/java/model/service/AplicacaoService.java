package model.service;

import java.time.LocalDate;
import java.util.List;

import exception.ControleVacinasException;
import model.dto.AplicacaoDTO;
import model.entity.Aplicacao;
import model.repository.AplicacaoRepository;
import model.repository.UnidadeRepository;
import model.seletor.AplicacaoSeletor;

public class AplicacaoService {
	
	AplicacaoRepository aplicacaoRepository = new AplicacaoRepository();
	UnidadeRepository unidadeRepository = new UnidadeRepository();
	
	public Aplicacao salvar(Aplicacao aplicacao)throws ControleVacinasException{
		validarPreenchimentoCampos(aplicacao);
		consultarEstoqueUnidade(aplicacao);
		return aplicacaoRepository.salvar(aplicacao);
		}
	
	public Aplicacao consultarPorId(int id) {
		return aplicacaoRepository.consultarPorId(id);
	}
	
	public List<AplicacaoDTO> consultarComFiltros(AplicacaoSeletor seletor){
		return aplicacaoRepository.consultarComFiltros(seletor);
	}
	
	private void validarPreenchimentoCampos(Aplicacao aplicacao) throws ControleVacinasException{
		String mensagem = "";
		if(aplicacao.getPessoaQueRecebeu() == null || aplicacao.getPessoaQueRecebeu().getId() == 0) {
			mensagem += "Para cadastrar a aplica��o � preciso inserir os dados da pessoa a ser vacinada.";
		}
		if(aplicacao.getVacinaAplicada() == null || aplicacao.getVacinaAplicada().getId() == 0) {
			mensagem += "Para cadastrar a aplica��o � preciso inserir os dados da vacina a ser aplicada na pessoa.";
		}
		if(aplicacao.getUnidadeOndeAplicou() == null || aplicacao.getUnidadeOndeAplicou().getId() == 0) {
			mensagem += "Para cadastrar a aplica��o � preciso inserir os dados da unidade onde a vacina ser� aplicada.";
		}
		if(aplicacao.getDataAplicacao() == null) {
			mensagem += "O preenchimento do campo data � obrigat�rio e a data deve ser a data atual.";
		}
		if(!aplicacao.getDataAplicacao().equals(LocalDate.now())) {
			mensagem += "A data da aplica��o da vacina deve ser a data atual.";
		}
		if(!mensagem.isEmpty()) {
			throw new ControleVacinasException("A(s) observa��e(s) listada(s) precisa(m) ser atendida)s) "
					+ "para que o cadastro do registro da aplica��o da vacina seja efetuado com sucesso. Seguem as observa��es: "+mensagem);
		}
	}
	
	private void consultarEstoqueUnidade(Aplicacao aplicacao) throws ControleVacinasException{
		boolean temEstoque = unidadeRepository.consultarEstoqueUnidade(aplicacao); 
		if(!temEstoque){
			throw new ControleVacinasException("N�o h� nenhuma dose dispon�vel "
																						    + "da vacina " + aplicacao.getVacinaAplicada().getNome() 
																							+" na unidade " + aplicacao.getUnidadeOndeAplicou().getNome());
		}
	}
		
}	
	

