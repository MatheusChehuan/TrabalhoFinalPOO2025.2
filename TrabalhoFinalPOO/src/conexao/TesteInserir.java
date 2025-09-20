package conexao;

import java.time.LocalDate;
import java.util.Scanner;
import persistence.FuncionarioDAO;
import entity.Funcionario;

public class TesteInserir {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Digite o nome:");
		String nome = sc.nextLine();
		
		System.out.println("Digite o cpf:");
		String cpf = sc.nextLine();
		
		System.out.println("Digite a data de nascimento:");
		LocalDate datanascimento = LocalDate.parse(sc.nextLine());
		
		System.out.println("Digite o salario bruto:");
		double salariobruto = sc.nextInt();
		
		Funcionario funcionario = new Funcionario(nome, cpf, datanascimento, salariobruto);
		
		FuncionarioDAO dao = new FuncionarioDAO();
		
		dao.inserir(funcionario);
	}

}