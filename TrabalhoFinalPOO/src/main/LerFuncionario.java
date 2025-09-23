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
    	
    	//seta o tipo de data a receber
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String nome = dados[0];
        String cpf = dados[1];
        LocalDate dataNasc = LocalDate.parse(dados[2], formatter);
        double salarioBruto = Double.parseDouble(dados[3]);
        
        
    	//se a lista de funcionarios já tiver o cpf dependente atual...
        if (cpfsFuncionarios.contains(cpf)) {
        	//recusa e joga no recusados.csv
            registrosRecusados.add(new String[]{cpf, nome});
        }

        Funcionario funcionario = new Funcionario(nome, cpf, dataNasc, salarioBruto);
        try {
        	//insere no banco
            funcDao.inserir(funcionario);
            // Adiciona a lista de funcionários inseridos
            funcionariosInseridos.add(funcionario);
            //incrementa a lista pra evitar outro cpf igual
            cpfsFuncionarios.add(cpf);
            //imprime
            System.out.println(nome + " adicionado com sucesso.");
        } catch (Exception e) {
        	//caso não, recusa e joga no recusados.csv
            registrosRecusados.add(new String[]{cpf, nome});
            System.out.println("Problemas ao gravar funcionário: " + nome);
        }
        //gera folha pagamentos
        try {
            folhaDao.inserir(new FolhaPagamento(funcionario, LocalDate.now()));
        } catch (Exception e) {
            System.out.println("Erro ao gerar folha de pagamento para: " + funcionario.getNome());
        }

        return funcionario;
    }
}