package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
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

        if (!arquivoEntrada.exists()) {
            System.out.println("Arquivo não encontrado: " + arquivoEntrada.getAbsolutePath());
            return;
        }

        Set<Funcionario> funcionariosInseridos = new HashSet<>();
        Set<String> cpfsFuncionarios = new HashSet<>();
        Set<String> cpfsDependentes = new HashSet<>();
        List<String[]> registrosRecusados = new ArrayList<>();

        FuncionarioDao funcDao = new FuncionarioDao();
        DependenteDao depDao = new DependenteDao();
        FolhaPagamentoDao folhaDao = new FolhaPagamentoDao();

        try (Scanner leitor = new Scanner(arquivoEntrada)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            Funcionario funcionarioAtual = null;

            while (leitor.hasNextLine()) {
                String linha = leitor.nextLine();
                if (linha.isEmpty()) continue;

                String[] dados = linha.split(";");
                if (dados.length < 4) continue;

                // Verifica se é funcionário ou dependente
                boolean isFuncionario = true;
                try {
                    Double.parseDouble(dados[3]); 
                } catch (NumberFormatException e) {
                    isFuncionario = false;
                }

                if (isFuncionario) {
                    funcionarioAtual = LerFuncionario.processar(dados, funcDao, folhaDao, 
                        cpfsFuncionarios, registrosRecusados, funcionariosInseridos);
                } else if (funcionarioAtual != null) {
                    LerDependente.processar(dados, depDao, funcionarioAtual, cpfsDependentes, registrosRecusados);
                }
            }

            // gera saída e recusados
            gerarSaida(arquivoSaida, funcionariosInseridos);
            gerarRecusados(arquivoRecusados, registrosRecusados);

        } catch (FileNotFoundException e) {
            System.out.println("Erro ao abrir o arquivo: " + e.getMessage());
        }
    }

    private static void gerarSaida(File arquivoSaida, Set<Funcionario> funcionariosInseridos) {
        try (PrintWriter saida = new PrintWriter(new FileWriter(arquivoSaida))) {
            for (Funcionario f : funcionariosInseridos) {
                saida.println(f.getNome() + ";" + f.getCpf() + ";" + f.getDataNascimento() + ";"
                        + String.format("%.2f", f.calcularINSS()) + ";" 
                        + String.format("%.2f", f.calcularIR()) + ";" 
                        + String.format("%.2f", f.calcularSalarioLiquido()));
            }
            System.out.println("Arquivo de saída gerado em: " + arquivoSaida.getAbsolutePath());
        } catch (Exception e) {
            System.out.println("Erro ao gerar arquivo de saída: " + e.getMessage());
        }
    }

    private static void gerarRecusados(File arquivoRecusados, List<String[]> registrosRecusados) {
        try (PrintWriter recusados = new PrintWriter(new FileWriter(arquivoRecusados))) {
            for (String[] r : registrosRecusados) {
                recusados.println(r[0] + ";" + r[1]);
            }
            System.out.println("Arquivo de recusados gerado em: " + arquivoRecusados.getAbsolutePath());
        } catch (Exception e) {
            System.out.println("Erro ao gerar arquivo de recusados: " + e.getMessage());
        }
    }
}