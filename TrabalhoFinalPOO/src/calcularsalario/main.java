package calcularsalario;

import java.time.LocalDate;

import entity.Dependente;
import entity.Funcionario;
import persistence.DependenteDAO;
import persistence.FolhaPagamentoDAO;
import persistence.FuncionarioDAO;

public class main {
	//criei só pra testar
	public static void main(String[] args) {
		
		FuncionarioDAO funcdao = new FuncionarioDAO();
		DependenteDAO depdao = new DependenteDAO();
		FolhaPagamentoDAO folhadao = new FolhaPagamentoDAO();

		Funcionario funcionario1 = new Funcionario("Matheus", "156.262.177-70", LocalDate.of(1999, 6, 29), 3000.);
		Dependente dependente1 = null;
		Dependente dependente2 = null;
		
		try {
			dependente1 = new Dependente("Bolhinha","00100",LocalDate.of(2015, 7, 11),Parentesco.FILHO);
			dependente2 = new Dependente("Fulaninho","99191", LocalDate.of(2017, 3, 9),Parentesco.FILHO);
			
			funcionario1.adicionarDependente(dependente1);
			funcionario1.adicionarDependente(dependente2);
			
			FolhaPagamento folhaPagamento1 = new FolhaPagamento(funcionario1,LocalDate.of(2025, 10, 5));
			
			funcdao.inserir(funcionario1);
			depdao.inserir(funcionario1,dependente1);
			depdao.inserir(funcionario1,dependente2);
			folhadao.inserir(folhaPagamento1);
			
		} catch (DependenteException e) {
			e.printStackTrace();
		}
		
		System.out.println(funcionario1.toString()+"\n");
		System.out.println(dependente1.getNome() + "\n" + dependente2.getNome());
	}

}
