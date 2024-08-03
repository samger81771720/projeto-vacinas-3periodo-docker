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
import model.entity.Endereco;
import model.entity.Estoque;
import model.entity.Unidade;
import model.entity.Vacina;
import model.seletor.VacinaSeletor;

public class EstoqueRepository implements BaseRepository<Estoque>{
	
	@Override
	public Estoque salvar(Estoque novoEstoque) {
	    String query = "insert into VACINAS.ESTOQUE (idUnidade, idVacina, quantidade, dataLote, validade) values (?, ?, ?, ?, ?)";
	    Connection conn = Banco.getConnection();
	    PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
	    try {
	        this.preencherParametrosParaInsertOuUpdate(pstmt, novoEstoque, false);
	        int linhasAfetadas = pstmt.executeUpdate();
	        if (linhasAfetadas != 1) {
	            throw new SQLException("A tentativa de inserção de um registro no estoque da unidade não foi bem sucedida.");
	        }
	    } catch (SQLException erro) {
	        System.out.println("Erro na tentativa de salvar um novo estoque da unidade " + novoEstoque.getUnidade().getNome() + " no banco de dados.");
	        System.out.println("Erro: " + erro.getMessage());
	        return null;
	    } finally {
	        Banco.closeStatement(pstmt);
	        Banco.closeConnection(conn);
	    }
	    return novoEstoque; 
	}

	public boolean excluirEstoqueDaUnidade(int idUnidade, int idVacina) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean excluiu = false;
		String query = "delete from VACINAS.ESTOQUE where idUnidade =  " 
									  + idUnidade +  " and idVacina = " + idVacina;
		try {
			if(stmt.executeUpdate(query) == 1) {
				excluiu = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao tentar excluir o estoque com os id´s informados.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return excluiu;
	}

	@Override
	public boolean alterar(Estoque estoque) {
		boolean alterou = false;
	    String query = "update VACINAS.ESTOQUE set idUnidade = ?, idVacina = ?, quantidade = ?, "
	    						  + "dataLote = ?, validade = ? where idUnidade = ? and idVacina = ? ";
	    Connection conn = Banco.getConnection();
	    PreparedStatement pstmt = Banco.getPreparedStatement(conn, query);
	    try {
	    	this.preencherParametrosParaInsertOuUpdate(pstmt, estoque, true);
	        alterou = pstmt.executeUpdate() > 0;
	    } catch (SQLException erro) {
	        System.out.println("Erro ao tentar atualizar o estoque da unidade " + estoque.getUnidade().getNome());
	        System.out.println("Erro: " + erro.getMessage());
	    } finally {
	        Banco.closeStatement(pstmt);
	        Banco.closeConnection(conn);
	    }
	    return alterou;
	}

	@Override
	public ArrayList<Estoque> consultarTodos() {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ArrayList<Estoque> listaDeTodosEstoques = new ArrayList<>();
		ResultSet resultado = null;
		String query = "select U.id as idUnidade, E.idVacina, "
							      + "E.quantidade, E.dataLote, E.validade from VACINAS.ESTOQUE E "
							      + "inner join VACINAS.UNIDADE U on E.idUnidade = U.id order by U.nome asc";
		try{
			resultado = stmt.executeQuery(query);
			while(resultado.next()){
				Estoque estoque = this.converterParaObjeto(resultado);
				listaDeTodosEstoques.add(estoque);
			}
		} catch (SQLException erro){
			System.out.println("Erro ao consultar a lista com os estoques de todas as unidades.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return listaDeTodosEstoques;
	}
	
	public ArrayList<Estoque> consultarEstoquesDaUnidadePorId(Estoque estoqueDaUnidade) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ArrayList<Estoque> listaDeTodosEstoques = new ArrayList<>();
		ResultSet resultado = null;
		String query = " select "
											+ " idUnidade, "
											+ " idVacina, "
											+ " quantidade, "
											+ " dataLote, "
											+ " validade "
											+ " from "
											+ " VACINAS.ESTOQUE "
								   + " where "
											+ " idUnidade = " + estoqueDaUnidade.getUnidade().getId();
		try{
			resultado = stmt.executeQuery(query);
			while(resultado.next()){
				Estoque estoque = this.converterParaObjeto(resultado);
				listaDeTodosEstoques.add(estoque);
			}
		} catch (SQLException erro){
			System.out.println("Erro ao consultar a lista com os estoques da unidade pelo id da unidade.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return listaDeTodosEstoques;
	}

	public Estoque consultarPorIds(int idUnidade, int idVacina) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		Estoque estoque = null;
		String query = " select "
											+ " idUnidade, "
											+ " idVacina, "
											+ " quantidade, "
											+ " dataLote, "
											+ " validade "
											+ " from "
											+ " VACINAS.ESTOQUE "
											+ " where "
											+ " idUnidade = " + idUnidade 
											+ " and "
											+ " idVacina  = " + idVacina;
		try {
			resultado = stmt.executeQuery(query);
			if(resultado.next()) {
				estoque = this.converterParaObjeto(resultado);
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao tentar consultar o estoque da unidade de id " + idUnidade);
			System.out.println("Erro: "+erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return estoque;		
	}
	
	
	@Override
	public boolean excluir(int id) {
		return false;
	}
	
	public List<VacinaDTO> consultarComFiltros(VacinaSeletor seletor){
		
		ArrayList<VacinaDTO> listagemComFiltrosSelecionados = new ArrayList<>();
		
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		
		String sql = "select "
				+ "	VACINA.id as idVacina, "
				+ "	VACINA.nome as nomeVacina, "
				+ "	VACINA.categoria as categoriaVacina, "
				+ "	VACINA.idadeMinima as idadeMinimaVacina, "
				+ "	VACINA.idadeMaxima as idadeMaximaVacina, "
				+ "	VACINA.contraIndicacao as contraIndicacaoVacina, "
				+ "	FABRICANTE.nome as nomeFabricante, "
				+ "	FABRICANTE.id as idFabricante, "
				+ "	UNIDADE.id as idUnidade, "
				+ "	UNIDADE.nome as nomeUnidade, "
				+ "	ENDERECO.bairro as bairroUnidade, "
				+ "	ENDERECO.cep as cepUnidade "
				+ " FROM "
				+ "    VACINAS.VACINA "
				+ " JOIN "
				+ "    VACINAS.FABRICANTE ON VACINAS.VACINA.idFabricante = VACINAS.FABRICANTE.id "
				+ " JOIN "
				+ "    VACINAS.ESTOQUE ON VACINAS.VACINA.id = VACINAS.ESTOQUE.idVacina "
				+ " JOIN "
				+ "    VACINAS.UNIDADE ON VACINAS.ESTOQUE.idUnidade = VACINAS.UNIDADE.id "
				+ " JOIN "
				+ "    VACINAS.ENDERECO ON VACINAS.UNIDADE.idEndereco = VACINAS.ENDERECO.id "
				+ " JOIN "
				+ "    VACINAS.CONTATO on VACINAS.UNIDADE.idContato = VACINAS.CONTATO.id where VACINAS.ESTOQUE.quantidade > 0 ";
		
		if(seletor.temFiltro()) {
			 sql = preencherFiltros(seletor,sql);
		}
		
		if(seletor.temPaginacao()) {
			sql += " LIMIT " + seletor.getLimite(); 
			sql += " OFFSET " + seletor.getOffSet();
		}
		
		try {
			resultado = stmt.executeQuery(sql);
			while(resultado.next()) {
				VacinaDTO vacinaDTO = construirDoResultSet(resultado);
				listagemComFiltrosSelecionados.add(vacinaDTO);
			}
		} catch(SQLException erro){
			System.out.println(
					"Erro durante a execução do método \"consultarComFiltros\" ao consultar "
				 + "as unidades com seus respectivos estoques de vacinas de acordo com o  filtro selecionado."
					);
			System.out.println("Erro: "+erro.getMessage());
		} finally{
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return listagemComFiltrosSelecionados;
	}
	
	private Estoque converterParaObjeto(ResultSet resultado) throws SQLException{
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
	
	private void preencherParametrosParaInsertOuUpdate	(PreparedStatement pstmt, Estoque novoEstoque,  boolean isUpdate) 
			throws SQLException  {
	    pstmt.setInt(1, novoEstoque.getUnidade().getId());
		pstmt.setInt(2, novoEstoque.getVacina().getId());
		pstmt.setInt(3, novoEstoque.getQuantidade());
		if(novoEstoque.getDataLote() != null) {
			pstmt.setDate(4, Date.valueOf(novoEstoque.getDataLote()));
		}
		if(novoEstoque.getValidade() != null) {
			pstmt.setDate(5, Date.valueOf(novoEstoque.getValidade()));
		}
		 if (isUpdate) {
	         pstmt.setInt(6, novoEstoque.getUnidade().getId());
	         pstmt.setInt(7, novoEstoque.getVacina().getId());
	      }
	}
	
	private String preencherFiltros(VacinaSeletor seletor, String sql) {

		final String AND = " and ";
		
		if (
	    		seletor.getNomeVacina() != null && seletor.getNomeVacina().trim().length() > 0
	    	) {
	        sql += AND + " UPPER(VACINA.nome) LIKE UPPER ('%" + seletor.getNomeVacina() + "%')";
	    }
	    
		if (
	    		seletor.getCategoria() != null && seletor.getCategoria().trim().length() > 0
	    	) {
	        sql += AND + " UPPER(VACINA.categoria) LIKE UPPER ('%" + seletor.getCategoria() + "%')";
	    }
		
		if (
	    		seletor.getNomeFabricante() != null && seletor.getNomeFabricante().trim().length() > 0
	    	) {
	        sql += AND + " UPPER(FABRICANTE.nome) LIKE UPPER ('%" + seletor.getNomeFabricante() + "%')";
	    }
		
		if (
	    		seletor.getNomeUnidade() != null && seletor.getNomeUnidade().trim().length() > 0
	    	) {
	        sql += AND + " UPPER(UNIDADE.nome) LIKE UPPER ('%" + seletor.getNomeUnidade() + "%')";
	    }
		
		if (
	    		seletor.getNomeBairro() != null && seletor.getNomeBairro().trim().length() > 0
	    	) {
	        sql += AND + " UPPER(ENDERECO.bairro) LIKE UPPER ('%" + seletor.getNomeBairro() + "%')";
	    }
		
		if (
	    		seletor.getNumeroCep() != null && seletor.getNumeroCep().trim().length() > 0
	    	) {
	        sql += AND + " UPPER(ENDERECO.cep) LIKE UPPER ('%" + seletor.getNumeroCep() + "%')";
	    }
		
		if (seletor.getIdadeMinima() != 0 && seletor.getIdadeMaxima() != 0) {
	        sql += AND + "((VACINA.idadeMinima BETWEEN " 
	        		+ seletor.getIdadeMinima()	+ AND + seletor.getIdadeMaxima() 
	        		+ ") " + AND + " (VACINA.idadeMaxima BETWEEN " + seletor.getIdadeMinima() 
	        		+ AND + seletor.getIdadeMaxima() + "))";
	    }
		
		if (seletor.getIdadeMinima() != 0) {
	        sql += AND + "(VACINA.idadeMinima >= " + seletor.getIdadeMinima() 
	        	   +    AND + " VACINA.idadeMaxima >= " + seletor.getIdadeMinima()  + ")";
	    }
	    
		if (seletor.getIdadeMaxima() != 0) {
	        sql += AND + "(VACINA.idadeMinima <= " + seletor.getIdadeMaxima() 
	        	   +    AND + " VACINA.idadeMaxima <= " + seletor.getIdadeMaxima()  + ")";
	    }
		
		  if (seletor.isContraIndicacao() != null) {
		        if (!seletor.isContraIndicacao()) {
		            sql += AND + " VACINA.contraIndicacao = false";
		        } else {
		            sql += AND + " VACINA.contraIndicacao = true";
		        }
		    }
		
		return sql;
	}
	
	private VacinaDTO construirDoResultSet(ResultSet resultado) throws SQLException{
		
		VacinaDTO vacinaDTO = new VacinaDTO();
		
		VacinaRepository vacinaRepository = new VacinaRepository();
		vacinaDTO.setVacina(vacinaRepository.consultarPorId(resultado.getInt("idVacina")));
		
		FabricanteRepository fabricanteRepository = new FabricanteRepository();
		vacinaDTO.setFabricante(fabricanteRepository.consultarPorId(resultado.getInt("idFabricante")));
		
		UnidadeRepository unidadeRepository = new UnidadeRepository();
		vacinaDTO.setUnidade(unidadeRepository.consultarPorId(resultado.getInt("idUnidade")));
		
		return vacinaDTO;
	}

	@Override
	public Estoque consultarPorId(int id) {
		return null;
	}

}
