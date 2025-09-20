package conexao;

import persistence.FuncionarioDAO;

public class TesteApagar {

	public static void main(String[] args) {
		FuncionarioDAO dao = new FuncionarioDAO();
		dao.apagar(1);
		System.out.println("Apagado com sucesso!");
	}

}
