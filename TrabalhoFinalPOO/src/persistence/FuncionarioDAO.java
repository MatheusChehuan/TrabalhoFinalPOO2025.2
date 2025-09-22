package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexao.ConnectionFactory;
import entity.Funcionario;

public class FuncionarioDAO {

	private Connection connection;

	public FuncionarioDAO() {
		connection = new ConnectionFactory().getConnection();
	}

	public void inserir(Funcionario funcionario) {
		String sql = "insert into funcionarios(nome,cpf,data_nascimento,salario_bruto) values (?,?,?,?) returning id_funcionario";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, funcionario.getNome());
			stmt.setString(2, funcionario.getCpf());
			stmt.setDate(3, java.sql.Date.valueOf(funcionario.getDataNascimento())); // chatgpt que me deu a dica
			stmt.setDouble(4, funcionario.getSalarioBruto());
	       
			try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next())
                    funcionario.setId(rs.getInt(1));
            }

		} catch (SQLException e) {
			System.out.println("Problemas ao gravar registro");
		}
		try {
			PreparedStatement stmt = connection.prepareStatement("select * from funcionarios where cpf=?");
			stmt.setString(1, funcionario.getCpf());
			ResultSet result = stmt.executeQuery();
		
		} catch (SQLException e) {
			System.out.println("Problemas ao gravar registro");
		}
	}
}
