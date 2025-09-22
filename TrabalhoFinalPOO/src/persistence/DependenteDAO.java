package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import calcularsalario.DependenteException;
import conexao.ConnectionFactory;
import entity.Dependente;
import entity.Funcionario;
import entity.Parentesco;

public class DependenteDAO {
	private Connection connection;

	public DependenteDAO() {
		connection = new ConnectionFactory().getConnection();
	}

	public void inserir(Dependente dependente, int id_funcionario) {
		String sql = "insert into dependentes(nome,cpf,data_nascimento,parentesco,id_funcionario) values (?,?,?,?,?)";
		try {
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