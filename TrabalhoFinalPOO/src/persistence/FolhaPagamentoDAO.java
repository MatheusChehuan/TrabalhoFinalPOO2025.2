package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import calcularsalario.FolhaPagamento;
import conexao.ConnectionFactory;
import entity.Funcionario;


public class FolhaPagamentoDAO {
	private Connection connection;
	
	public FolhaPagamentoDAO(){
		connection = new ConnectionFactory().getConnection();
	}
	public void inserir(FolhaPagamento fp) {
		String sql = "insert into folha_pagamento(id_funcionario,data_pagamento,desconto_inss,desconto_ir,salario_liquido) values (?,?,?,?,?)";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, fp.getFuncionario().getId());
			stmt.setDate(2, java.sql.Date.valueOf(fp.getDataPagamento()));
			stmt.setDouble(3, fp.getDescontoINSS());
			stmt.setDouble(4, fp.getDescontoIR());
			stmt.setDouble(5, fp.getSalarioLiquido());
			
			stmt.execute();
			stmt.close();//talvez esteja no lugar errado
			connection.close();
			
		} catch (SQLException e) {
			System.out.println("Problemas ao gravar registro");
		}
	}
}