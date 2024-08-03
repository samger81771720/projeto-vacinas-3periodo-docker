package model.repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.entity.Contato;

public class ContatoRepository  implements BaseRepository<Contato> {

	@Override
	public Contato salvar(Contato contato) {
		String query = "insert into VACINAS.CONTATO (telefone, email) "
		   		+ "values (?,?)";
		    Connection conn = Banco.getConnection();
		    PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
		    try {
		    	    this.preencherParametrosParaInsertOuUpdate(pstmt, contato, false);
		            pstmt.execute();
		            ResultSet resultado = pstmt.getGeneratedKeys();
		            if(resultado.next()) {
		            	contato.setId(resultado.getInt(1));
		        }
		    } catch(SQLException erro){
		        System.out.println("Erro na tentativa de salvar um novo contato no banco de dados.");
		        System.out.println("Erro: " + erro.getMessage());
		    } finally {
		        Banco.closeStatement(pstmt);
		        Banco.closeConnection(conn);
		    }
		    return contato;
	}

	@Override
	public boolean excluir(int id) {
		// TODO Stub de método gerado automaticamente
		return false;
	}

	@Override
	public boolean alterar(Contato contato) {
		boolean alterou = false;
	    String query = "update VACINAS.CONTATO set telefone = ?,	email = ? where id = ?";
	    Connection conn = Banco.getConnection();
	    PreparedStatement pstmt = Banco.getPreparedStatement(conn, query);
	    try {
	    	this.preencherParametrosParaInsertOuUpdate(pstmt, contato, true);
	        alterou = pstmt.executeUpdate() > 0;
	    } catch (SQLException erro) {
	        System.out.println("Erro ao atualizar o contato informado.");
	        System.out.println("Erro: " + erro.getMessage());
	    } finally {
	        Banco.closeStatement(pstmt);
	        Banco.closeConnection(conn);
	    }
	    return alterou;
	}

	
	
	@Override
	public Contato consultarPorId(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		Contato contato = null;
		String query=" select id, telefone, email from VACINAS.CONTATO where id = "+id;
		try {
			resultado = stmt.executeQuery(query);
		if(resultado.next()) {
			contato = new Contato();
			contato.setId(resultado.getInt("id"));
			contato.setTelefone(resultado.getString("telefone"));
			contato.setEmail(resultado.getString("email"));
		}
		} catch (SQLException erro) {
			System.out.println("Erro ao tentar consultar o contato de id "+id);
			System.out.println("Erro: "+erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return contato;
	}

	@Override
	public ArrayList<Contato> consultarTodos() {
		// TODO Stub de método gerado automaticamente
		return null;
	}

	private void preencherParametrosParaInsertOuUpdate(PreparedStatement pstmt, Contato contato,  boolean isUpdate) throws SQLException  {
	    pstmt.setString(1, contato.getTelefone());
		pstmt.setString(2, contato.getEmail());
		 if (isUpdate) {
	            pstmt.setInt(3, contato.getId());
	            }
	}
	
}
