package model.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.dto.VacinaDTO;
import model.entity.Contato;
import model.entity.Endereco;
import model.entity.Pessoa;
import model.seletor.PessoaSeletor;
import model.seletor.VacinaSeletor;

public class PessoaRepository implements BaseRepository<Pessoa>{

	@Override
	public Pessoa salvar(Pessoa novaPessoa) {
		String query = "insert into VACINAS.PESSOA (idEndereco, idContato, nome, dataNascimento, "
					              + "sexo, cpf, login, senha, tipo, doencaPreexistente) values (?,?,?,?,?,?,?,?,?,?)";
		    Connection conn = Banco.getConnection();
		    PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
		    try {
		    		this.preencherParametrosParaInsertOuUpdate(pstmt, novaPessoa, false);
		            pstmt.execute();
		            ResultSet resultado = pstmt.getGeneratedKeys();
		            if(resultado.next()) {
		            	novaPessoa.setId(resultado.getInt(1));
		        }
		    } catch(SQLException erro){
		        System.out.println("Erro na tentativa de salvar uma nova pessoa no banco de dados.");
		        System.out.println("Erro: " + erro.getMessage());
		    } finally {
		    	Banco.closeStatement(pstmt);
		        Banco.closeConnection(conn);
		    }
		    return novaPessoa; 
	}

	@Override
	public boolean excluir(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean excluiu = false;
		String query = "delete from VACINAS.PESSOA where id = " + id;
		try {
			if(stmt.executeUpdate(query) == 1) {
				excluiu = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao tentar excluir a pessoa do cadastro.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return excluiu;
	}

	@Override
	public boolean alterar(Pessoa pessoaAtualizada) {
		boolean alterou = false;
	    String query = "update VACINAS.PESSOA set idEndereco = ?, idContato = ?, nome = ?, dataNascimento = ?, "
	    					      + "sexo = ?, cpf = ?, login = ?, senha = ?, tipo = ?, doencaPreexistente = ? where id = ?";
	    Connection conn = Banco.getConnection();
	    PreparedStatement pstmt = Banco.getPreparedStatement(conn, query);
	    try {
	    	this.preencherParametrosParaInsertOuUpdate(pstmt, pessoaAtualizada, true);
	        alterou = pstmt.executeUpdate() > 0;
	    } catch (SQLException erro) {
	        System.out.println("Erro ao atualizar a pessoa informada.");
	        System.out.println("Erro: " + erro.getMessage());
	    } finally {
	        Banco.closeStatement(pstmt);
	        Banco.closeConnection(conn);
	    }
	    return alterou;
	}

	@Override
	public Pessoa consultarPorId(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		Pessoa pessoa = null;
		String query = "select "
											+ "	id, "
											+ "	idEndereco, "
											+ "	idContato, "
											+ "	nome, "
											+ "	dataNascimento, "
											+ "	sexo, "
											+ "	cpf, "
											+ "	login, "
											+ "	senha, "
											+ "	tipo, "
											+ " doencaPreexistente "
											+ " from VACINAS.PESSOA "
											+ "where	id = " + id;
		try {
			resultado = stmt.executeQuery(query);
			if(resultado.next()) {
				pessoa = this.converterParaObjeto(resultado);
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao tentar consultar a pessoa de id " + id);
			System.out.println("Erro: "+erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return pessoa;
	}
	
	public Pessoa efetuarLogin(Pessoa pessoaLogin) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		Pessoa pessoa = null;
		String query="select id, idEndereco, idContato, nome,	dataNascimento,	sexo, cpf, login, senha, tipo, doencaPreexistente "
				+ "from VACINAS.PESSOA p where p.login = '" + pessoaLogin.getLogin() + "' and p.senha = '" + pessoaLogin.getSenha() + "'";
		try {
			resultado = stmt.executeQuery(query);
		if(resultado.next()) {
			pessoa = this.converterParaObjeto(resultado);
		}
		} catch (SQLException erro) {
			System.out.println("Erro ao tentar confirmar o login e senha do usuário de nome  "+pessoaLogin.getNome());
			System.out.println("Erro: "+erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return pessoa;
	}
	
	public boolean verificarDisponibilidadeLogin(Pessoa pessoaLoginDesejado) {
		 Connection conn = Banco.getConnection();
		 Statement stmt = Banco.getStatement(conn);
		 ResultSet resultado = null;
		 boolean loginExiste = false;
		 String query = "select count(id) as contagem from VACINAS.PESSOA where	login = '" + pessoaLoginDesejado.getLogin() + "'";
		 try {
			 resultado = stmt.executeQuery(query);
			 if(resultado.next()){
				 int contagemDoNumeroDeRegistros = resultado.getInt("contagem");
				 loginExiste = contagemDoNumeroDeRegistros  > 0;
			 }
		 } catch(SQLException erro){
			 System.out.println("Erro na tentativa de verificar se o login existe para autorizar o cadasto com o mesmo.");
			 System.out.println("Erro: " + erro.getMessage());
		 } finally {
			 Banco.closeResultSet(resultado);
			 Banco.closeStatement(stmt);
			 Banco.closeConnection(conn);
		 }
		return loginExiste;
	}
	
	public boolean verificarDisponibilidadeSenha(Pessoa pessoaSenhaDesejada) {
		 Connection conn = Banco.getConnection();
		 Statement stmt = Banco.getStatement(conn);
		 ResultSet resultado = null;
		 boolean senhaExiste = false;
		 String query = "select count(id) as contagem from VACINAS.PESSOA where	senha = '" + pessoaSenhaDesejada.getSenha() + "'";
		 try {
			 resultado = stmt.executeQuery(query);
			 if(resultado.next()){
				 int contagemDoNumeroDeRegistros = resultado.getInt("contagem");
				 senhaExiste = contagemDoNumeroDeRegistros  > 0;
			 }
		 } catch(SQLException erro){
			 System.out.println("Erro na tentativa de verificar se a senha existe para autorizar o cadasto com a mesma.");
			 System.out.println("Erro: " + erro.getMessage());
		 } finally {
			 Banco.closeResultSet(resultado);
			 Banco.closeStatement(stmt);
			 Banco.closeConnection(conn);
		 }
		return senhaExiste;
	}
	
	@Override
	public ArrayList<Pessoa> consultarTodos() {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ArrayList<Pessoa> listaDePessoas = new ArrayList<>();
		ResultSet resultado = null;
		String query = "select"
												+ "	id, "
												+ "	idEndereco, "
												+ "	idContato, "
												+ "	nome, "
												+ "	dataNascimento, "
												+ "	sexo, "
												+ "	cpf, "
												+ "	login, "
												+ "	senha, "
												+ "	tipo, "
												+ "	doencaPreexistente "
												+ "  from "
												+ "	VACINAS.PESSOA";
		try{
			resultado = stmt.executeQuery(query);
			while(resultado.next()){
				Pessoa pessoa = this.converterParaObjeto(resultado);
				listaDePessoas.add(pessoa);
			}
		} catch (SQLException erro){
			System.out.println("Erro ao consultar a lista de pessoas.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return listaDePessoas;
	}
	
	public List<Pessoa> consultarPessoasComFiltro(PessoaSeletor seletor){
			
			ArrayList<Pessoa> listagemComPessoas = new ArrayList<>();
			
			Connection conn = Banco.getConnection();
			Statement stmt = Banco.getStatement(conn);
			ResultSet resultado = null;
			String sql = "select "
											+ " id, "
											+ " idEndereco, "
											+ " idContato, "
											+ " nome, "
											+ " dataNascimento, "
											+ " sexo, "
											+ " cpf, "
											+ " login, "
											+ " senha, "
											+ " tipo, "
											+ " doencaPreexistente "
											+ " from "
											+ " VACINAS.PESSOA "
											+ " where "
											+ " UPPER(VACINAS.PESSOA.nome) LIKE UPPER('%" + seletor.getNomePessoa() + "%') "
											+ " order by "
											+ " nome asc ";
			/*if(seletor.temPaginacao()) {
				sql += " LIMIT " + seletor.getLimite(); 
				sql += " OFFSET " + seletor.getOffSet();
			}*/
			try {
				resultado = stmt.executeQuery(sql);
				while(resultado.next()) {
					Pessoa pessoa = converterParaObjeto(resultado);
					listagemComPessoas.add(pessoa);
				}
			} catch(SQLException erro){
				System.out.println(
						"Erro durante a execução do método \"consultarComFiltros\" ao consultar "
					 + "a pessoa selecionada com filtros."
						);
				System.out.println("Erro: "+erro.getMessage());
			} finally{
				Banco.closeResultSet(resultado);
				Banco.closeStatement(stmt);
				Banco.closeConnection(conn);
			}
			return listagemComPessoas;
		}
	
	private Pessoa converterParaObjeto(ResultSet resultado) throws SQLException {
		Pessoa pessoa = new Pessoa();
		pessoa.setId(resultado.getInt("id"));
		EnderecoRepository enderecoRepository = new EnderecoRepository();
		Endereco enderecoDaPessoa = enderecoRepository.consultarPorId(resultado.getInt("idEndereco"));
		pessoa.setEnderecoDaPessoa(enderecoDaPessoa);
		ContatoRepository contatoRepository = new ContatoRepository();
		Contato contatoDaPessoa = contatoRepository.consultarPorId(resultado.getInt("idContato"));
		pessoa.setContatoDaPessoa(contatoDaPessoa);
		pessoa.setNome(resultado.getString("nome"));
		if(resultado.getDate("dataNascimento")!=null) {
			pessoa.setDataNascimento(resultado.getDate("dataNascimento").toLocalDate());
		}
		pessoa.setSexo(resultado.getString("sexo"));
		pessoa.setCpf(resultado.getString("cpf"));
		pessoa.setLogin(resultado.getString("login"));
		pessoa.setSenha(resultado.getString("senha"));
		pessoa.setTipo(resultado.getInt("tipo"));
		pessoa.setDoencaPreexistente(resultado.getBoolean("doencaPreexistente"));
		return pessoa;
	}
	
	private void preencherParametrosParaInsertOuUpdate(PreparedStatement pstmt, Pessoa novaPessoa,  boolean isUpdate) throws SQLException  {
		
		EnderecoRepository  enderecoRepository = new EnderecoRepository();
		Endereco enderecoDaPessoa = enderecoRepository.salvar(novaPessoa.getEnderecoDaPessoa());
		novaPessoa.setEnderecoDaPessoa(enderecoDaPessoa);  
		
		ContatoRepository contatoRepository = new ContatoRepository();
		Contato contatoDaPessoa = contatoRepository.salvar(novaPessoa.getContatoDaPessoa());
		novaPessoa.setContatoDaPessoa(contatoDaPessoa); 
		
		pstmt.setInt(1, novaPessoa.getEnderecoDaPessoa().getId());
		pstmt.setInt(2, novaPessoa.getContatoDaPessoa().getId());		
		pstmt.setString(3, novaPessoa.getNome());
		if(novaPessoa.getDataNascimento() != null) {
			pstmt.setDate(4, Date.valueOf(novaPessoa.getDataNascimento()));
		}
		pstmt.setString(5, novaPessoa.getSexo());
		pstmt.setString(6, novaPessoa.getCpf());
		pstmt.setString(7,novaPessoa.getLogin());
		pstmt.setString(8,novaPessoa.getSenha());
		pstmt.setInt(9, novaPessoa.getTipo());
		pstmt.setBoolean(10, novaPessoa.isDoencaPreexistente());
			 if (isUpdate) {
	            pstmt.setInt(11, novaPessoa.getId());
	            }
	
	}

	public boolean verificarCpfParaCadastrar(Pessoa novaPessoa) {
		 Connection conn = Banco.getConnection();
		 Statement stmt = Banco.getStatement(conn);
		 ResultSet resultado = null;
		 boolean cpfExiste = false;
		 String query = "select count(id) as contagem from VACINAS.PESSOA where	cpf ="+novaPessoa.getCpf();
		 try {
			 resultado = stmt.executeQuery(query);
			 if(resultado.next()){
				 int contagemDoNumeroDeRegistros = resultado.getInt("contagem");
				 cpfExiste = contagemDoNumeroDeRegistros  > 0;
			 }
		 } catch(SQLException erro){
			 System.out.println("Erro na tentativa de verificar se o cpf existe.");
			 System.out.println("Erro: " + erro.getMessage());
		 } finally {
			 Banco.closeResultSet(resultado);
			 Banco.closeStatement(stmt);
			 Banco.closeConnection(conn);
		 }
		return cpfExiste;
	}

}

