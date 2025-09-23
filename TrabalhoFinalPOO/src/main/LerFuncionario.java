package main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

import calcularsalario.FolhaPagamento;
import entity.Funcionario;
import persistence.FolhaPagamentoDao;
import persistence.FuncionarioDao;

public class LerFuncionario {

    public static Funcionario processar(
            String[] dados,
            FuncionarioDao funcDao,
            FolhaPagamentoDao folhaDao,
            Set<String> cpfsFuncionarios,
            List<String[]> registrosRecusados,
            Set<Funcionario> funcionariosInseridos) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String nome = dados[0];
        String cpf = dados[1];
        LocalDate dataNasc = LocalDate.parse(dados[2], formatter);
        double salarioBruto = Double.parseDouble(dados[3]);

        if (cpfsFuncionarios.contains(cpf)) {
            registrosRecusados.add(new String[]{cpf, nome});
        }

        Funcionario funcionario = new Funcionario(nome, cpf, dataNasc, salarioBruto);
        try {
            funcDao.inserir(funcionario);
            funcionariosInseridos.add(funcionario);
            cpfsFuncionarios.add(cpf);
            System.out.println(nome + " adicionado com sucesso.");
        } catch (Exception e) {
            registrosRecusados.add(new String[]{cpf, nome});
            System.out.println("Problemas ao gravar funcionário: " + nome);
        }

        try {
            folhaDao.inserir(new FolhaPagamento(funcionario, LocalDate.now()));
        } catch (Exception e) {
            System.out.println("Erro ao gerar folha de pagamento para: " + funcionario.getNome());
        }

        return funcionario;
    }
}
