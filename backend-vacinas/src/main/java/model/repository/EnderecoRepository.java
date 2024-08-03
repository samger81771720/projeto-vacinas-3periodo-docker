package model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.entity.Endereco;

public class EnderecoRepository implements BaseRepository<Endereco>{

	@Override
	public Endereco salvar(Endereco endereco) {
	   String query = "insert into VACINAS.ENDERECO (logradouro, numero, complemento, bairro, localidade, estado, cep, pais) "
	   		+ "values (?,?,?,?,?,?,?,?)";
	    Connection conn = Banco.getConnection();
	    PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
	    try {
	    	    this.preencherParametrosParaInsertOuUpdate(pstmt, endereco, false);
	            pstmt.execute();
	            ResultSet resultado = pstmt.getGeneratedKeys();
	            if(resultado.next()) {
	            	endereco.setId(resultado.getInt(1));
	        }
	    } catch(SQLException erro){
	        System.out.println("Erro na tentativa de salvar um novo endereço no banco de dados.");
	        System.out.println("Erro: " + erro.getMessage());
	    } finally {
	        Banco.closeStatement(pstmt);
	        Banco.closeConnection(conn);
	    }
	    return endereco; 
	}
	
	
	
	@Override
	public boolean excluir(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean excluiu = false;
		String query = "delete from VACINAS.ENDERECO where id = " + id;
		try {
			if(stmt.executeUpdate(query) == 1) {
				excluiu = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao tentar excluir o endereço do cadastro.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return excluiu;
	}

	@Override
	public boolean alterar(Endereco endereco) {
		boolean alterou = false;
	    String query = "update VACINAS.ENDERECO set "
	    		+ "	logradouro = ?, "
	    		+ "	numero = ?, "
	    		+ "	complemento = ?, "
	    		+ "	bairro = ?, "
	    		+ "	localidade = ?, "
	    		+ "	estado = ?, "
	    		+ "	cep = ?, "
	    		+ "	pais = ? "
	    		+ "  where id = ?";
	    Connection conn = Banco.getConnection();
	    PreparedStatement pstmt = Banco.getPreparedStatement(conn, query);
	    try {
	    	this.preencherParametrosParaInsertOuUpdate(pstmt, endereco, true);
	        alterou = pstmt.executeUpdate() > 0;
	    } catch (SQLException erro) {
	        System.out.println("Erro ao atualizar o endereço informado.");
	        System.out.println("Erro: " + erro.getMessage());
	    } finally {
	        Banco.closeStatement(pstmt);
	        Banco.closeConnection(conn);
	    }
	    return alterou;
	}

	@Override
	public Endereco consultarPorId(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		Endereco endereco = null;
		String query = "select id, logradouro, numero, complemento, bairro, localidade, estado, cep, pais "
								+ "from VACINAS.ENDERECO where	id = " + id;
		try {
			resultado = stmt.executeQuery(query);
			if(resultado.next()) {
				endereco = this.converterParaObjeto(resultado);
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao tentar consultar o endereço de id "+id);
			System.out.println("Erro: "+erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return endereco;
	}

	@Override
	public ArrayList<Endereco> consultarTodos() {
			Connection conn = Banco.getConnection();
			Statement stmt = Banco.getStatement(conn);
			ArrayList<Endereco> enderecos = new ArrayList<>();
			ResultSet resultado = null;
			String query = "select id, logradouro, numero, complemento, bairro, localidade, estado, cep, pais "
									  + "from VACINAS.ENDERECO ";
			try{
				resultado = stmt.executeQuery(query);
				while(resultado.next()){
					Endereco endereco = this.converterParaObjeto(resultado);
					enderecos.add(endereco);
				}
			} catch (SQLException erro){
				System.out.println("Erro ao consultar a lista de endereços.");
				System.out.println("Erro: " + erro.getMessage());
			} finally {
				Banco.closeResultSet(resultado);
				Banco.closeStatement(stmt);
				Banco.closeConnection(conn);
			}
			return enderecos;
	}
	
	private Endereco converterParaObjeto(ResultSet resultado) throws SQLException {
		Endereco endereco = new Endereco();
		endereco.setId(resultado.getInt("id"));
		endereco.setLogradouro(resultado.getString("logradouro"));
		endereco.setNumero(resultado.getString("numero"));
		endereco.setComplemento(resultado.getString("complemento"));
		endereco.setBairro(resultado.getString("bairro"));
		endereco.setLocalidade(resultado.getString("localidade"));
		endereco.setEstado(resultado.getString("estado"));
		endereco.setCep(resultado.getString("cep"));
		endereco.setPais(resultado.getString("pais"));
		return endereco;
	}
		
	private void preencherParametrosParaInsertOuUpdate(PreparedStatement pstmt, Endereco endereco,  boolean isUpdate) throws SQLException  {
	    pstmt.setString(1, endereco.getLogradouro());
		pstmt.setString(2, endereco.getNumero());
		pstmt.setString(3, endereco.getComplemento());
		pstmt.setString(4, endereco.getBairro());
		pstmt.setString(5, endereco.getLocalidade());
		pstmt.setString(6, endereco.getEstado());
		pstmt.setString(7, endereco.getCep());
		pstmt.setString(8, endereco.getPais());
		 if (isUpdate) {
	            pstmt.setInt(9, endereco.getId());
	            }
	}
	
}
