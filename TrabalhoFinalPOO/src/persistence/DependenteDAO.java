package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import conexao.ConnectionFactory;
import entity.Dependente;

public class DependenteDao {
	private Connection connection;

	public DependenteDao() {
		connection = new ConnectionFactory().getConnection();
	}

	public void inserir(Dependente dependente, int id_funcionario) {
		String sql1 = "truncate table dependentes restart identity cascade;";
		String sql = "insert into dependentes(nome,cpf,data_nascimento,parentesco,id_funcionario) values (?,?,?,?,?)";
		try {
			PreparedStatement stmt1 = connection.prepareStatement(sql1);
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, dependente.getNome());
			stmt.setString(2, dependente.getCpf());
			stmt.setDate(3, java.sql.Date.valueOf(dependente.getDataNascimento()));
			stmt.setString(4, dependente.getParentesco().name());
			stmt.setInt(5, id_funcionario);
			
			stmt.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Problemas ao gravar registro");
		}
	}
}