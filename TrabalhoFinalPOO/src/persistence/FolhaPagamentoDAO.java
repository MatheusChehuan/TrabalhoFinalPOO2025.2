package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import calcularsalario.FolhaPagamento;
import conexao.ConnectionFactory;


public class FolhaPagamentoDao {
	private Connection connection;
	
	public FolhaPagamentoDao(){
		connection = new ConnectionFactory().getConnection();
	}
	public void inserir(FolhaPagamento fp) {
		String sql1 = "truncate table funcionarios restart identity cascade;";
		String sql = "insert into folha_pagamento(id_funcionario,data_pagamento,desconto_inss,desconto_ir,salario_liquido) values (?,?,?,?,?)";
		try {
			PreparedStatement stmt1 = connection.prepareStatement(sql1);
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, fp.getFuncionario().getId());
			stmt.setDate(2, java.sql.Date.valueOf(fp.getDataPagamento()));
			stmt.setDouble(3, fp.getDescontoINSS());
			stmt.setDouble(4, fp.getDescontoIR());
			stmt.setDouble(5, fp.getSalarioLiquido());
			
			stmt.execute();
			//stmt.close();//talvez esteja no lugar errado
			
		} catch (SQLException e) {
			System.out.println("Problemas ao gravar registro");
		}
	}
}