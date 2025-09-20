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

	public void inserir(Funcionario funcionario, Dependente dependente) {
		String sql = "insert into dependentes(nome,cpf,data_nascimento,parentesco,id_funcionario) values (?,?,?,?,?)";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, dependente.getNome());
			stmt.setString(2, dependente.getCpf());
			stmt.setDate(3, java.sql.Date.valueOf(dependente.getDataNascimento()));
			stmt.setString(4, dependente.getParentesco().name());
			stmt.setInt(5, funcionario.getId());
			
			stmt.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Problemas ao gravar registro");
		}
	}
	/*
	 * public void atualizar(Dependente dependente) { String sql =
	 * "update dependentes set nome=?, cpf=?, data_nascimento=?, parentesco=? where id =?"
	 * ; try { PreparedStatement stmt = connection.prepareStatement(sql);
	 * stmt.setString(1, dependente.getNome()); stmt.setString(2,
	 * dependente.getCpf()); stmt.setDate(3,
	 * java.sql.Date.valueOf(dependente.getDataNascimento())); stmt.setString(4,
	 * dependente.getParentesco().name());
	 * 
	 * stmt.execute(); stmt.close();//talvez esteja errado connection.close();
	 * 
	 * } catch (SQLException e) {
	 * System.out.println("Problemas ao gravar registro"); } } public void
	 * apagar(int id) { String sql = "delete from dependente where id =?"; try {
	 * PreparedStatement stmt = connection.prepareStatement(sql); stmt.setInt(1,
	 * id); stmt.execute();
	 * 
	 * stmt.close(); connection.close();
	 * 
	 * } catch (SQLException e) {
	 * System.out.println("Problemas ao gravar registro"); } } public
	 * List<Dependente> listar(){ String sql = "select * from dependente";
	 * List<Dependente> dependentes = new ArrayList<>(); try { PreparedStatement
	 * stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery();
	 * 
	 * while(rs.next()) { try { dependentes.add(new Dependente
	 * (rs.getString("nome"), rs.getString("cpf"),
	 * rs.getDate("dataNascimento").toLocalDate(),
	 * Parentesco.valueOf(rs.getString("Parentesco")) )); } catch
	 * (DependenteException e) { System.err.println("Dependente inválido!");
	 * e.printStackTrace(); } } stmt.close(); //talvez esteja errado
	 * connection.close(); //talvez esteja no lugar errado
	 * 
	 * } catch (SQLException e) {
	 * System.out.println("Problemas ao listar registros!"); } return dependentes; }
	 */

}