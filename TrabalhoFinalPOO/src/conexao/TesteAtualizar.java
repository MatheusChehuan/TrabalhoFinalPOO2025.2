package conexao;

import java.time.LocalDate;

import entity.Funcionario;
import persistence.FuncionarioDAO;

public class TesteAtualizar {

	public static void main(String[] args) {
		Funcionario funcionario = new Funcionario("José","156",LocalDate.of(1999, 6, 29),5000.);
		FuncionarioDAO dao = new FuncionarioDAO();
		dao.atualizar(funcionario);
		System.out.println("Funcionario alterado com sucesso!");

	}

}
