package model.repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.entity.Vacina;

public class VacinaRepository implements BaseRepository<Vacina>{

	@Override
	public Vacina salvar(Vacina novaVacina) {
		 String query = "insert into VACINAS.VACINA "
		 						   + "(idFabricante, nome, categoria, idadeMinima, idadeMaxima, contraIndicacao) "
		 						   + "values (?, ? , ? , ?, ?, ?)";
			    Connection conn = Banco.getConnection();
			    PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
			    try {
			    	    preencherParametrosParaInsertOuUpdate(pstmt, novaVacina, false);
			            pstmt.execute();
			            ResultSet resultado = pstmt.getGeneratedKeys();
			            if(resultado.next()) {
			            	novaVacina.setId(resultado.getInt(1));
			        }
			    } catch(SQLException erro){
			        System.out.println("Erro na tentativa de salvar uma nova vacina no banco de dados.");
			        System.out.println("Erro: " + erro.getMessage());
			    } finally {
			        Banco.closeStatement(pstmt);
			        Banco.closeConnection(conn);
			    }
		return novaVacina; 
	}

	@Override
	public boolean excluir(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean excluiu = false;
		String query = "delete from VACINAS.VACINA where id = " + id;
		try {
			if(stmt.executeUpdate(query) == 1) {
				excluiu = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao tentar excluir a vacina do cadastro.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return excluiu;
	}

	@Override
	public boolean alterar(Vacina vacinaAlterada) {
		boolean alterou = false;
	    String query = " update "
	    						  + " VACINAS.VACINA set "
	    						  + " idFabricante = ?, "
	    						  + " nome = ?, "
	    						  + " categoria = ?, "
	    						  + " idadeMinima = ?, "
	    						  + " idadeMaxima = ?, "
	    						  + " contraIndicacao = ? "
	    						  + " where id = ? ";
	    Connection conn = Banco.getConnection();
	    PreparedStatement pstmt = Banco.getPreparedStatement(conn, query);
	    try {
	    	preencherParametrosParaInsertOuUpdate(pstmt, vacinaAlterada, true);
	        alterou = pstmt.executeUpdate() > 0;
	    } catch (SQLException erro) {
	        System.out.println("Erro ao atualizar Vacina");
	        System.out.println("Erro: " + erro.getMessage());
	    } finally {
	        Banco.closeStatement(pstmt);
	        Banco.closeConnection(conn);
	    }
	    return alterou;
	}
	
	/*
	 O ResultSet é uma tabela de resultados da consulta SQL.
	Após executeQuery, o ResultSet contém as linhas e colunas retornadas pela consulta.
	Você usa next() para iterar sobre as linhas do ResultSet.
	Você pode acessar os dados da linha atual do ResultSet usando métodos como getInt, getString, etc.
	A função converterParaObjeto mapeia esses valores para um objeto da sua classe de domínio.
	*/
	@Override
	public Vacina consultarPorId(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		Vacina vacina = null;
		String query = "select id, idFabricante, nome, categoria, idadeMinima, idadeMaxima,	"
				                  + "contraIndicacao from VACINAS.VACINA where id = " + id;
		try {
			resultado = stmt.executeQuery(query);
			if(resultado.next()) {
				vacina = this.converterParaObjeto(resultado);
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao tentar consultar a vacina de id "+id);
			System.out.println("Erro: "+erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return vacina;
	}

	@Override
	public ArrayList<Vacina> consultarTodos() {
			ArrayList<Vacina> vacinas = new ArrayList<>();
			Connection conn = Banco.getConnection();
			Statement stmt = Banco.getStatement(conn);
			ResultSet resultado = null;
			String query = " select id, idFabricante, nome, categoria, idadeMinima, "
								      + " idadeMaxima, contraIndicacao from VACINAS.VACINA "
								      + " order by nome ";
			try{
				resultado = stmt.executeQuery(query);
				while(resultado.next()){
					Vacina vacina = new Vacina();
					vacina.setId(resultado.getInt("id"));
					vacina.setNome(resultado.getString("nome"));
					FabricanteRepository fabricanteRepository = new FabricanteRepository();
					vacina.setFabricanteDaVacina(fabricanteRepository.consultarPorId(resultado.getInt("idFabricante")));
					vacina.setCategoria(resultado.getString("categoria"));
					vacina.setIdadeMinima(resultado.getInt("idadeMinima"));
					vacina.setIdadeMaxima(resultado.getInt("idadeMaxima"));
					vacina.setContraIndicacao(resultado.getBoolean("contraIndicacao"));
					vacinas.add(vacina);
				}
			} catch (SQLException erro){
				System.out.println("Erro ao executar consultar todas as vacinas.");
				System.out.println("Erro: " + erro.getMessage());
			} finally {
				Banco.closeResultSet(resultado);
				Banco.closeStatement(stmt);
				Banco.closeConnection(conn);
			}
			return vacinas;
	}
	
	public List<String> consultarTodasCategorias() {
		ArrayList<String> listaDeCategorias = new ArrayList<>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		String query = " select distinct CATEGORIA from VACINAS.VACINA ";
		 //SELECT DISTINCT: Seleciona apenas valores únicos da coluna especificada.
		try{
			resultado = stmt.executeQuery(query);
			while(resultado.next()){
				String categoria = "";
				categoria = resultado.getString("categoria");
				listaDeCategorias.add(categoria);
			}
		} catch (SQLException erro){
			System.out.println("Erro ao executar consultar todas as categorias.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return listaDeCategorias;
	}
	
	private Vacina converterParaObjeto(ResultSet resultado) throws SQLException{
		Vacina vacina = new Vacina();
		vacina.setId(resultado.getInt("id"));
		vacina.setNome(resultado.getString("nome"));
		FabricanteRepository fabricanteRepository = new FabricanteRepository();
		vacina.setFabricanteDaVacina(fabricanteRepository.consultarPorId(resultado.getInt("id")));
		vacina.setCategoria(resultado.getString("categoria"));
		vacina.setIdadeMinima(resultado.getInt("idadeMinima"));
		vacina.setIdadeMaxima(resultado.getInt("idadeMaxima"));
		vacina.setContraIndicacao(resultado.getBoolean("contraIndicacao"));
		return vacina;
	}
	
	private void preencherParametrosParaInsertOuUpdate(PreparedStatement pstmt, Vacina vacina,  boolean isUpdate) throws SQLException  {
	    pstmt.setInt(1, vacina.getFabricanteDaVacina().getId());
		pstmt.setString(2, vacina.getNome());
		pstmt.setString(3, vacina.getCategoria());
		pstmt.setInt(4, vacina.getIdadeMinima());
		pstmt.setInt(5, vacina.getIdadeMaxima());
		pstmt.setBoolean(6, vacina.isContraIndicacao());
		 if (isUpdate) {
	            pstmt.setInt(7, vacina.getId());
	            }
	}

}
