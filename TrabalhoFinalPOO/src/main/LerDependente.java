package main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

import calcularsalario.DependenteException;
import entity.Dependente;
import entity.Funcionario;
import entity.Parentesco;
import persistence.DependenteDao;

public class LerDependente {

    public static void processar(
    		
    		
            String[] dados,
            DependenteDao depDao,
            Funcionario funcionarioAtual,
            Set<String> cpfsDependentes,
            List<String[]> registrosRecusados) throws DependenteException {
    	
    	//seta o tipo de data a receber
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String nomeDep = dados[0];
        String cpfDep = dados[1];
        LocalDate dataNascDep = LocalDate.parse(dados[2], formatter);

        Parentesco parentesco;
        try {
        	//pega a string do parentesco e transforma em Parentesco PARENTESCO
            parentesco = Parentesco.valueOf(dados[3].toUpperCase());
        } catch (Exception e) {
        	//se não der joga no recusados
            registrosRecusados.add(new String[]{cpfDep, nomeDep});
            return;
        }
        	//se a lista de dependentes já tiver o cpf dependente atual...
        if (cpfsDependentes.contains(cpfDep)) {
        	//joga ele no recusados
            registrosRecusados.add(new String[]{cpfDep, nomeDep});
            return;
        }

        Dependente dependente = new Dependente(nomeDep, cpfDep, dataNascDep, parentesco);
        try {
        	//insere no banco
            depDao.inserir(dependente, funcionarioAtual.getId());
            //associa o dep atual ao funcionario atual
            funcionarioAtual.adicionarDependente(dependente);
            //atualiza a lista de cpfs pra evitar futura duplicidade
            cpfsDependentes.add(cpfDep);
            //imprime que foi
            System.out.println(nomeDep + " adicionado com sucesso.");
        } catch (Exception e) {
        	//caso de errado, vai pro recusados.csv
            registrosRecusados.add(new String[]{cpfDep, nomeDep});
            System.out.println("Problemas ao gravar dependente: " + nomeDep);
        }
    }
}
