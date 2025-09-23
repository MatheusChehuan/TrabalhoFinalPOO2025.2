package main;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

import entity.Funcionario;

public interface GeradorArquivo {
	//saída
    public static void gerarSaida(File arquivoSaida, Set<Funcionario> funcionariosInseridos) {
        try (PrintWriter saida = new PrintWriter(new FileWriter(arquivoSaida))) {
            for (Funcionario f : funcionariosInseridos) {
                saida.println(f.getNome() + ";" + f.getCpf() + ";" + f.getDataNascimento() + ";"+ String.format("%.2f", f.calcularINSS()) + ";" + String.format("%.2f", f.calcularIR()) + ";" + String.format("%.2f", f.calcularSalarioLiquido()));
            }
            System.out.println("Arquivo de saída gerado em: " + arquivoSaida.getAbsolutePath());
        } catch (Exception e) {
            System.out.println("Erro ao gerar arquivo de saída: " + e.getMessage());
        }
    }
    //recusados
    public static void gerarRecusados(File arquivoRecusados, List<String[]> registrosRecusados) {
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
