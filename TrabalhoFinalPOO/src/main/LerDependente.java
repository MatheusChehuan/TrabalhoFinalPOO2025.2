package main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

import calcularsalario.DependenteException;
import calcularsalario.Parentesco;
import entity.Dependente;
import entity.Funcionario;
import persistence.DependenteDao;

public class LerDependente {

    public static void processar(
            String[] dados,
            DependenteDao depDao,
            Funcionario funcionarioAtual,
            Set<String> cpfsDependentes,
            List<String[]> registrosRecusados) throws DependenteException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String nomeDep = dados[0];
        String cpfDep = dados[1];
        LocalDate dataNascDep = LocalDate.parse(dados[2], formatter);

        Parentesco parentesco;
        try {
            parentesco = Parentesco.valueOf(dados[3].toUpperCase());
        } catch (Exception e) {
            registrosRecusados.add(new String[]{cpfDep, nomeDep});
            return;
        }

        if (cpfsDependentes.contains(cpfDep)) {
            registrosRecusados.add(new String[]{cpfDep, nomeDep});
            return;
        }

        Dependente dependente = new Dependente(nomeDep, cpfDep, dataNascDep, parentesco);
        try {
            depDao.inserir(dependente, funcionarioAtual.getId());
            funcionarioAtual.adicionarDependente(dependente);
            cpfsDependentes.add(cpfDep);
            System.out.println(nomeDep + " adicionado com sucesso.");
        } catch (Exception e) {
            registrosRecusados.add(new String[]{cpfDep, nomeDep});
            System.out.println("Problemas ao gravar dependente: " + nomeDep);
        }
    }
}
