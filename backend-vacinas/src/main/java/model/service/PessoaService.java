package model.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import exception.ControleVacinasException;
import model.entity.Pessoa;
import model.repository.PessoaRepository;
import model.seletor.PessoaSeletor;

public class PessoaService {
	
	PessoaRepository pessoaRepository = new PessoaRepository();
	
	// Funcionalidade terá outras implementações futuramente
	public Pessoa efetuarLogin(Pessoa pessoa) throws ControleVacinasException {
		validarCamposPreenchidosParalogin(pessoa);
	   Pessoa pessoaLogada = pessoaRepository.efetuarLogin(pessoa);
	    if (pessoaLogada != null) {
	        switch (pessoaLogada.getTipo()) {
	            case Pessoa.ADMINISTRADOR:
	                return pessoaLogada;
	            case Pessoa.USUARIO:
	                return pessoaLogada;
	            default:
	                throw new ControleVacinasException("Essa pessoa não se encontra cadastrada no sistema.");
	        }
	    } else {
	        throw new ControleVacinasException("Verifique os dados de acesso e tente novamente.");
	    }
	}
	
	public Pessoa consultarPorId(int id) {
		return pessoaRepository.consultarPorId(id);
	}
	
	public Pessoa salvar(Pessoa novaPessoa) throws ControleVacinasException{
	 	validarCamposPreenchidosDePessoa(novaPessoa);
		verificarCpfParaCadastrar(novaPessoa);
		verificarDisponibilidadeLogin(novaPessoa);
		verificarDisponibilidadeSenha(novaPessoa);
		return pessoaRepository.salvar(novaPessoa);
	}
	
	public boolean alterar(Pessoa pessoaAtualizada) throws ControleVacinasException{
		
		Pessoa pessoaConsultada = pessoaRepository.consultarPorId(pessoaAtualizada.getId()); 
		
		if(!pessoaConsultada.getCpf().equals(pessoaAtualizada.getCpf())) {
			throw new ControleVacinasException("Não é possível atualizar o número do seu CPF.");
		}
		 validarCamposPreenchidosDePessoa(pessoaAtualizada);
		
		if(!pessoaRepository.consultarPorId(pessoaAtualizada.getId()).getLogin().equals(pessoaAtualizada.getLogin()))  {
			verificarDisponibilidadeLogin(pessoaAtualizada);
		}
		
		if(!pessoaRepository.consultarPorId(pessoaAtualizada.getId()).getSenha().equals(pessoaAtualizada.getSenha()))  {
			verificarDisponibilidadeSenha(pessoaAtualizada);
		}
		
		return pessoaRepository.alterar(pessoaAtualizada);
	}
	
	public ArrayList<Pessoa> consultarTodos() {
		return pessoaRepository.consultarTodos();
	}
	
	public boolean excluir(int id) {
		return pessoaRepository.excluir(id);
	}
	
	private void validarCamposPreenchidosDePessoa(Pessoa novaPessoa) throws ControleVacinasException {
		   
		String mensagemValidacao = "";
		   
	    if (novaPessoa.getEnderecoDaPessoa().getCep() == null ||
	    	 novaPessoa.getEnderecoDaPessoa().getCep().replace(" ", "").length() == 0) {
	         mensagemValidacao += " - O campo CEP, referente ao endereço da pessoa, precisa ser preenchido. ";
	    }
	    if (novaPessoa.getContatoDaPessoa().getTelefone() == null ||
	    	 novaPessoa.getContatoDaPessoa().getTelefone().replace(" ", "").length() == 0) {
	         mensagemValidacao += " - O campo telefone referente ao contato da pessoa precisa ser preenchido. ";
	    }
		if (novaPessoa.getNome() == null || novaPessoa.getNome().replace(" ", "").length() == 0) {
		    mensagemValidacao += " - O campo nome precisa ser preenchido. ";
		}
		if(novaPessoa.getNome().replace(" ", "").length() < 8) {
		    mensagemValidacao += " - O campo nome precisa ter ao menos oito letras e os espaços em branco não fazem parte da contagem.";
		}
		if (!novaPessoa.getNome().matches("^[\\p{L} ]+$")) {
		    mensagemValidacao += "O campo nome precisa ser preenchido apenas com letras. ";
		}
		LocalDate dataNascimento = novaPessoa.getDataNascimento();
		if (dataNascimento != null) {
		    LocalDate dataLimite = LocalDate.now().minusYears(120);
		    if (dataNascimento.isBefore(dataLimite)) {
		        mensagemValidacao += " - A pessoa não pode ter mais de 120 anos de idade. ";
		    }
		} else {
			mensagemValidacao += " - O campo data de nascimento precisa ser preenchido. ";
		}
		if (
				novaPessoa.getSexo().toUpperCase() == null ||
			    novaPessoa.getSexo().toUpperCase().replace(" ", "").length() == 0
			) {
		    mensagemValidacao += " - O campo sexo precisa ser informado.";
		}
	   if(
		   novaPessoa.getCpf() == null ||
		   novaPessoa.getCpf().replace(" ", "").length() == 0
		   ) {
			mensagemValidacao += " - O campo cpf precisa ser preenchido. ";
		}
	   if(novaPessoa.getCpf().length() != 11) {
			mensagemValidacao += " - O campo cpf precisa ter 11 números. ";
		}
		
	   if (!novaPessoa.getCpf().isEmpty() && !novaPessoa.getCpf().matches("[0-9]+")) {
		    mensagemValidacao += " - O campo CPF precisa ser preenchido apenas com números. ";
		}
		if(
			novaPessoa.getLogin() == null ||
			novaPessoa.getLogin().replace(" ", "").length() == 0
			) {
			mensagemValidacao += " - O campo login precisa ser preenchido. ";
		}
		if (novaPessoa.getLogin().contains(" ") || novaPessoa.getLogin().length() < 8 || novaPessoa.getLogin().length() > 20) {
		    mensagemValidacao += " - O campo login precisa ser preenchido com no mínimo oito e no máximo vinte caracteres, sem espaços.";
		}
		if ( novaPessoa.getSenha().contains(" ") ||  novaPessoa.getSenha().length() < 8 ||  novaPessoa.getSenha().length() > 20) {
		    mensagemValidacao += " - O campo senha precisa ser preenchido com no mínimo oito e no máximo vinte caracteres, sem espaços.";
		}
		if(novaPessoa.getTipo() != Pessoa.ADMINISTRADOR  && novaPessoa.getTipo() != Pessoa.USUARIO) {
			mensagemValidacao += " - Os únicos tipos permitidos para preencher esse campo são: USUÁRIO ou ADMINISTRADOR.";
		}
		if(!mensagemValidacao.isEmpty()) {
			throw new ControleVacinasException("As observaçõe(s) a seguir precisa(m) ser atendida(s): "+mensagemValidacao);
		}
	
	}
	
	private void verificarCpfParaCadastrar(Pessoa novaPessoa) throws ControleVacinasException{
	       if(pessoaRepository.verificarCpfParaCadastrar(novaPessoa)) {
	        	throw new ControleVacinasException("O cpf informado já se encontra cadastrado no sistema.");
	        } 
	}
	
	private void validarCamposPreenchidosParalogin(Pessoa novaPessoa) throws ControleVacinasException {
		   
		String mensagemValidacao = "";
			
	    if (
	    		novaPessoa.getLogin() == null 
	    		|| novaPessoa.getSenha() == null
	    		|| novaPessoa.getLogin().replace(" ", "").length() == 0 
	    		|| novaPessoa.getSenha().replace(" ", "").length() == 0
	    	) {
	        mensagemValidacao += " - O campo login e o campo senha precisam ser preenchidos e não podem haver espaços em branco no preenchimento dos campos. ";
	    }
	    if(novaPessoa.getLogin().contains(" ") || novaPessoa.getLogin().length()<8 || novaPessoa.getLogin().length() > 20) {
	    	mensagemValidacao += " - O campo login precisa ter no mínimo 8 e no máximo 12 caracteres e sem espaços. ";
	    }
	    if(novaPessoa.getSenha().contains(" ") || novaPessoa.getSenha().length()<8 || novaPessoa.getSenha().length() > 20) {
	    	mensagemValidacao += " - O campo senha precisa ter no mínimo 8 e no máximo 12 caracteres e sem espaços. ";
	    }
	    if(!mensagemValidacao.isEmpty()) {
			throw new ControleVacinasException("As observações a seguir precisam ser atendidas para efetuar o login com sucesso: " + mensagemValidacao);
		}
	    
	}
	
	private void verificarDisponibilidadeLogin(Pessoa pessoaLoginDesejado) throws ControleVacinasException{
		if(pessoaRepository.verificarDisponibilidadeLogin(pessoaLoginDesejado)) {
			throw new ControleVacinasException("Login já existente no sistema. Tente outro.");
		}
	}
	
	private void verificarDisponibilidadeSenha(Pessoa pessoaSenhaDesejada) throws ControleVacinasException{
		if(pessoaRepository.verificarDisponibilidadeSenha(pessoaSenhaDesejada)) {
			throw new ControleVacinasException("Senha já existente no sistema. Tente outra.");
		}
	}
	
	public List<Pessoa> consultarPessoasComFiltro(PessoaSeletor seletor) throws ControleVacinasException{
		if(seletor.getNomePessoa() != null && seletor.getNomePessoa().replace(" ", "").length() > 0) {
			return pessoaRepository.consultarPessoasComFiltro(seletor);
		} else {
			throw new ControleVacinasException("Preencha o campo nome para fazer a busca.");
		}
	}
		
}
