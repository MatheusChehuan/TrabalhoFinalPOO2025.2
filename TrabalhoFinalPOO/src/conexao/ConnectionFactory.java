package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	private String url = "jdbc:postgresql://localhost:5432/postgres";
	private String usuario ="postgres";
	private String senha = "bolhao";
	private Connection connection;
	
	
	public Connection getConnection() {
		System.out.println("Conectando ao banco.......");
		try {
			connection = DriverManager.getConnection(url, usuario, senha);
			if (connection != null) {
				System.out.println("Conectado com sucesso!");
				return connection;
			}
		} catch (SQLException e) {
			System.out.println("Não foi possivel se conectar ao banco.");
		}
		return connection;
	}

}
