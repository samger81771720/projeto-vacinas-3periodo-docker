package model.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.entity.Fabricante;


public class FabricanteRepository implements BaseRepository<Fabricante>{

	@Override
	public Fabricante salvar(Fabricante novaEntidade) {
		return null;
	}

	@Override
	public boolean excluir(int id) {
		return false;
	}

	@Override
	public boolean alterar(Fabricante entidade) {
		return false;
	}

	@Override
	public Fabricante consultarPorId(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		Fabricante fabricante = null;
		String query = "select id, idEndereco, idContato, nome from VACINAS.FABRICANTE where id = " + id;
		try {
			resultado = stmt.executeQuery(query);
			if(resultado.next()) {
				fabricante = this.converterParaObjeto(resultado);
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao tentar consultar o fabricante de id "+id);
			System.out.println("Erro: "+erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return fabricante;
	}

	@Override
	public ArrayList<Fabricante> consultarTodos() {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ArrayList<Fabricante> listaDeFabricantes = new ArrayList<>();
		ResultSet resultado = null;
		String query = " select "
										+ " id, "
										+ " idEndereco, "
										+ " idContato, "
										+ " nome "
										+ " from "
										+ " VACINAS.FABRICANTE ";
		try{
			resultado = stmt.executeQuery(query);
			while(resultado.next()){
				Fabricante fabricante = this.converterParaObjeto(resultado);
				listaDeFabricantes.add(fabricante);
			}
		} catch (SQLException erro){
			System.out.println("Erro ao consultar a lista de fabricantes.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return listaDeFabricantes;
	}
	
	private Fabricante converterParaObjeto(ResultSet resultado) throws SQLException {
		Fabricante fabricante = new Fabricante();
		fabricante.setId(resultado.getInt("id"));
		fabricante.setNome(resultado.getString("nome"));
		EnderecoRepository enderecoRepository = new EnderecoRepository();
		fabricante.setEnderecoDoFabricante(enderecoRepository.consultarPorId(resultado.getInt("idEndereco")));
		ContatoRepository contatoRepository = new ContatoRepository();
		fabricante.setContatoDoFabricante(contatoRepository.consultarPorId(resultado.getInt("idContato")));
		return fabricante;
	}

}
