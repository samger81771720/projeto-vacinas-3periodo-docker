package model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.entity.Aplicacao;
import model.entity.Contato;
import model.entity.Endereco;
import model.entity.Estoque;
import model.entity.Unidade;

public class UnidadeRepository implements BaseRepository<Unidade>{

	@Override
	public Unidade salvar(Unidade novaEntidade) {
		// TODO Stub de método gerado automaticamente
		return null;
	}

	@Override
	public boolean excluir(int id) {
		// TODO Stub de método gerado automaticamente
		return false;
	}

	@Override
	public boolean alterar(Unidade entidade) {
		// TODO Stub de método gerado automaticamente
		return false;
	}

	@Override
	public Unidade consultarPorId(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		Unidade unidade = null;
		String query = "select id, idEndereco, idContato, nome from VACINAS.UNIDADE where id = " + id;
		try {
			resultado = stmt.executeQuery(query);
			if(resultado.next()) {
				unidade = this.converterParaObjetoUnidade(resultado);
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao tentar consultar a unidade de id "+id);
			System.out.println("Erro: "+erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return unidade;
	}
	
	public List<Estoque> consultarEstoquesDaUnidade(int idUnidade) {
		ArrayList<Estoque> estoqueDaUnidade = new ArrayList<>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		String query = "select idUnidade, idVacina, quantidade, dataLote, "
								  + "validade from VACINAS.ESTOQUE where idUnidade = " 
								  + idUnidade;
		try{
			resultado = stmt.executeQuery(query);
			while(resultado.next()){
				estoqueDaUnidade.add(this.converterParaObjetoEstoque(resultado));
			}
		} catch (SQLException erro){
			System.out.println("Erro ao executar o estoque da unidade de id " + idUnidade);
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return estoqueDaUnidade;
	}

	@Override
	public ArrayList<Unidade> consultarTodos() {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ArrayList<Unidade> unidades = new ArrayList<>();
		ResultSet resultado = null;
		String query = " select "
												+ " id, "
												+ " idEndereco, "
												+ " idContato, "
												+ " nome "
												+ " from "
												+ " VACINAS.UNIDADE "
												+ " order by nome ";
		try{
			resultado = stmt.executeQuery(query);
			while(resultado.next()){
				Unidade unidade = this.converterParaObjetoUnidade(resultado);
				unidades.add(unidade);
			}
		} catch (SQLException erro){
			System.out.println("Erro ao consultar a lista de unidades.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return unidades;
	}
	
	public boolean consultarEstoqueUnidade(Aplicacao aplicacao) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		boolean temEstoque = false;
		String query = "select quantidade from VACINAS.ESTOQUE where "
								  + " idVacina = " + aplicacao.getVacinaAplicada().getId()  + " and "
								  + " idUnidade = " + aplicacao.getUnidadeOndeAplicou().getId();
		try {
			resultado = stmt.executeQuery(query);
			   if (resultado.next()) { 
		            if(resultado.getInt("quantidade") > 0) {
		                temEstoque = true;
		            }
		        }
		} catch (SQLException erro) {
			System.out.println("Erro ao tentar verificar se há estoque da vacina " 
												   + aplicacao.getVacinaAplicada().getNome() 
											       + " na unidade " + aplicacao.getUnidadeOndeAplicou().getNome() 
											       +" para realizar o registro de aplicação na no usuário " 
											       + aplicacao.getPessoaQueRecebeu().getNome());
			System.out.println("Erro: "+erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return temEstoque;
	}
	
	public boolean darBaixaEstoqueUnidade(Aplicacao aplicacao) {
	    boolean deuBaixa = false;
	    String query = "update VACINAS.ESTOQUE set quantidade = (quantidade - 1) "
	    						  + "where idVacina = ? and idUnidade = ?";
	    Connection conn = Banco.getConnection();
	    PreparedStatement pstmt = Banco.getPreparedStatement(conn, query);
	    try {
	        pstmt.setInt(1, aplicacao.getVacinaAplicada().getId());
	        pstmt.setInt(2, aplicacao.getUnidadeOndeAplicou().getId());
	        int linhasAfetadas = pstmt.executeUpdate();
	        if (linhasAfetadas == 1) {
	            deuBaixa = true;
	        }
	    } catch (Exception erro) {
	        System.out.println("Erro na tentativa de dar baixa no estoque da vacina " 
	                          + aplicacao.getVacinaAplicada().getNome() + " da unidade " 
	                          + aplicacao.getUnidadeOndeAplicou().getNome() 
	                          + ". Verifique a qtde da vacina no estoque e efetue o registro de baixa novamente." ) ;
	        System.out.println("Erro: O estoque dessa vacina estava zerado " + erro);
	    } finally {
	        Banco.closePreparedStatement(pstmt);
	        Banco.closeConnection(conn);
	    }
	    return deuBaixa;
	}

	private Unidade converterParaObjetoUnidade(ResultSet resultado) throws SQLException {
		Unidade unidade = new Unidade();
		unidade.setId(resultado.getInt("id"));
		unidade.setNome(resultado.getString("nome"));
		EnderecoRepository enderecoRepository = new EnderecoRepository();
		Endereco enderecoDaUnidade = enderecoRepository.consultarPorId(resultado.getInt("idEndereco"));
		unidade.setEnderecoDaUnidade(enderecoDaUnidade);
		ContatoRepository contatoRepository = new ContatoRepository();
		Contato contatoDaUnidade = contatoRepository.consultarPorId(resultado.getInt("idContato"));
		unidade.setContatoDaUnidade(contatoDaUnidade);
		return unidade;
	}
	
	private Estoque converterParaObjetoEstoque(ResultSet resultado) throws SQLException{
		Estoque estoque = new Estoque();
		UnidadeRepository unidadeRepository = new UnidadeRepository();
		estoque.setUnidade(unidadeRepository.consultarPorId(resultado.getInt("idUnidade")));
		VacinaRepository vacinaRepositor = new VacinaRepository();
		estoque.setVacina(vacinaRepositor.consultarPorId(resultado.getInt("idVacina")));
		estoque.setQuantidade(resultado.getInt("quantidade"));
		if(resultado.getDate("dataLote") != null) {
			estoque.setDataLote(resultado.getDate("dataLote").toLocalDate());;
		}
		if(resultado.getDate("validade") != null) {
			estoque.setValidade(resultado.getDate("validade").toLocalDate());
		}
		return estoque;
	}
	
}
