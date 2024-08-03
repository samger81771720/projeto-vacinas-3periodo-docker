package model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class Banco {
	
// @Resource(name="jdbc/sistema")
//	private static DataSource ds;
	
	private static final String NAME_DATASOURCE = "SenacDS";
	
	/*Context initContext = new InitialContext();: Cria um objeto InitialContext, 
	 * que é usado para realizar operações de pesquisa no ambiente de contexto.

Context envContext = (Context) initContext.lookup("java:/comp/env");: 
Obtém o contexto de ambiente (environment context) do contexto inicial. 
Este contexto é geralmente usado para procurar recursos configurados no ambiente.

DataSource ds = (DataSource) envContext.lookup(NAME_DATASOURCE);: 
Realiza uma pesquisa no contexto de ambiente para encontrar o recurso de
 origem de dados (DataSource) com o nome especificado na constante 
 NAME_DATASOURCE. O objeto DataSource representa uma fonte de 
 conexão com um banco de dados.

Connection conn = ds.getConnection();: Obtém uma conexão do DataSource 
encontrado na etapa anterior. Isso é feito chamando o método getConnection()
 no objeto DataSource. Este método retorna uma conexão com o banco de dados.

return conn;: Retorna a conexão obtida. Se tudo ocorrer sem erros até este ponto, 
uma conexão bem-sucedida foi estabelecida e é retornada pelo método.*/
	
	public static Connection getConnection(){
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) envContext.lookup(NAME_DATASOURCE);
			Connection conn = ds.getConnection();
			return conn; 
		} catch (Exception e) {
			System.out.println("Erro ao obter a Connection.");
			System.out.println("Erro: " + e.getMessage());
			return null;
		}
	}
	
	public static void closeConnection(Connection conn){
		try {
			if(conn != null){
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("Problema no fechamento da conexÃ£o.");
			System.out.println("Erro: " + e.getMessage());
		}	
	}
	
	public static Statement getStatement(Connection conn){
		try {
			Statement stmt = conn.createStatement();
			return stmt;
		} catch (SQLException e) {
			System.out.println("Erro ao obter o Statement.");
			System.out.println("Erro: " + e.getMessage());
			return null;
		}
	}
		
	public static void closeStatement(Statement stmt){
		try {
			if(stmt != null){
				stmt.close();
			}
		} catch (SQLException e) {
			System.out.println("Problema no fechamento do Statement.");
			System.out.println("Erro: " + e.getMessage());
		}	
	}
	
	public static PreparedStatement getPreparedStatement(Connection conn, String sql){
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			return stmt;
		} catch (Exception e) {
			System.out.println("Erro ao obter o PreparedStatement.");
			System.out.println("Erro: " + e.getMessage());
			return null;
		}
	}
	
	public static PreparedStatement getPreparedStatementWithPk(Connection conn, String sql){
		try {
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			return stmt;
		} catch (Exception e) {
			System.out.println("Erro ao obter o PreparedStatement.");
			System.out.println("Erro: " + e.getMessage());
			return null;
		}
	}

	public static void closePreparedStatement(Statement stmt){
		try {
			if(stmt != null){
				stmt.close();
			}
		} catch (SQLException e) {
			System.out.println("Problema no fechamento do PreparedStatement.");
			System.out.println("Erro: " + e.getMessage());
		}	
	}
	
	public static void closeResultSet(ResultSet result){
		try {
			if(result != null){
				result.close();
			}
		} catch (SQLException e) {
			System.out.println("Problema no fechamento do ResultSet");
			System.out.println("Erro: " + e.getMessage());
		}
	}
}
