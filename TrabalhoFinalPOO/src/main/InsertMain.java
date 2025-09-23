package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import calcularsalario.DependenteException;
import entity.Funcionario;
import persistence.DependenteDao;
import persistence.FolhaPagamentoDao;
import persistence.FuncionarioDao;

public class InsertMain {

    public static void ArquivoEntrada(File arquivoEntrada, File arquivoSaida, File arquivoRecusados) throws DependenteException {
    	//confirma a existencia do csv informado
        if (!arquivoEntrada.exists()) {
            System.out.println("Arquivo não encontrado: " + arquivoEntrada.getAbsolutePath());
            return;
        }
        //guarda funcionarios inseridos com sucesso
        Set<Funcionario> funcionariosInseridos = new HashSet<>();
        //guarda os cpfs pra comparar
        Set<String> cpfsFuncionarios = new HashSet<>();
        //guarda os cpfs pra comparar
        Set<String> cpfsDependentes = new HashSet<>();
        //lista dos cpfs recusados
        List<String[]> registrosRecusados = new ArrayList<>();
        
        //cria os daos
        FuncionarioDao funcDao = new FuncionarioDao();
        DependenteDao depDao = new DependenteDao();
        FolhaPagamentoDao folhaDao = new FolhaPagamentoDao();
        
        
        //le o arquivo entrada.csv
        try (Scanner leitor = new Scanner(arquivoEntrada)) {
        	//setar a forma de entrada da data de nascimento
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            Funcionario funcionarioAtual = null;
            
            
            while (leitor.hasNextLine()) {
            	//pula pro proximo quando a linha é vazia
                String linha = leitor.nextLine();
                if (linha.isEmpty()) continue;
                //garante que tá lendo uma entrada certa
                String[] dados = linha.split(";");
                if (dados.length < 4) continue;

                // Verifica se é funcionário ou dependente
                boolean eFuncionario = true;
                try {
                	//confere se o terceiro indice é double
                    Double.parseDouble(dados[3]); 
                } catch (NumberFormatException e) {
                	//se não, não é funcionario
                    eFuncionario = false;
                }
                //se for funcionario, chama o LerFuncionario
                if (eFuncionario) {
                    funcionarioAtual = LerFuncionario.processar(dados, funcDao, folhaDao, 
                        cpfsFuncionarios, registrosRecusados, funcionariosInseridos);
                }
                //se for dependente, chama o LerDependente
                else if (funcionarioAtual != null) {
                    LerDependente.processar(dados, depDao, funcionarioAtual, cpfsDependentes, registrosRecusados);
                }
            }
            // gera saída e recusados na classe gerador de arquivos
            GeradorArquivo.gerarSaida(arquivoSaida, funcionariosInseridos);
            GeradorArquivo.gerarRecusados(arquivoRecusados, registrosRecusados);

        } catch (FileNotFoundException e) {
            System.out.println("Erro ao abrir o arquivo: " + e.getMessage());
        }
    }
}