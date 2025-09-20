package calcularsalario;

import java.time.LocalDate;

import entity.Dependente;
import entity.Funcionario;
import entity.Parentesco;
import persistence.DependenteDAO;
import persistence.FolhaPagamentoDAO;
import persistence.FuncionarioDAO;

public class main {
	//criei só pra testar
	public static void main(String[] args) {
		
		FuncionarioDAO funcdao = new FuncionarioDAO();
		DependenteDAO depdao = new DependenteDAO();
		FolhaPagamentoDAO folhadao = new FolhaPagamentoDAO();

		
		try {
			Funcionario funcionario1 = new Funcionario("beltrano", "0100015", LocalDate.of(1999, 6, 29), 3000.);
			funcdao.inserir(funcionario1);
			Dependente dependente1 = new Dependente("Bolhinha","00010116",LocalDate.of(2015, 7, 11),Parentesco.FILHO,funcionario1.getId());
			Dependente dependente2 = new Dependente("Fulaninho","00010127", LocalDate.of(2017, 3, 9),Parentesco.FILHO,funcionario1.getId());
			
			funcionario1.adicionarDependente(dependente1);
			funcionario1.adicionarDependente(dependente2);
			
			
			depdao.inserir(funcionario1,dependente1);
			depdao.inserir(funcionario1,dependente2);
			FolhaPagamento folhaPagamento1 = new FolhaPagamento(funcionario1,LocalDate.of(2025, 10, 5));

			folhadao.inserir(folhaPagamento1);
			
		} catch (DependenteException e) {
			e.printStackTrace();
		}
		
	}

}
