package calcularsalario;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import entity.Dependente;
import entity.Funcionario;
import entity.Parentesco;
import persistence.DependenteDAO;
import persistence.FolhaPagamentoDAO;
import persistence.FuncionarioDAO;

public class Mainteste {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // endereço do arquivo de entrada
        System.out.println("Digite o caminho completo do arquivo CSV de entrada:");
        String caminhoArquivo = sc.nextLine();
        File file = new File(caminhoArquivo);

        if (!file.exists()) {
            System.out.println("Arquivo não encontrado: " + caminhoArquivo);
            return;
        }

        Set<Funcionario> funcionariosInseridos = new HashSet<>();
        Set<String> cpfsFuncionarios = new HashSet<>();
        Set<String> cpfsDependentes = new HashSet<>();
        List<String[]> registrosRecusados = new ArrayList<>();

        FuncionarioDAO funcDao = new FuncionarioDAO();
        DependenteDAO depDao = new DependenteDAO();
        FolhaPagamentoDAO folhaDao = new FolhaPagamentoDAO();

        try (Scanner leitor = new Scanner(file)) {
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
                    Double.parseDouble(dados[3]); // se não der, é dependente
                } catch (NumberFormatException e) {
                    isFuncionario = false;
                }

                if (isFuncionario) {
                    String nome = dados[0];
                    String cpf = dados[1];
                    LocalDate dataNasc = LocalDate.parse(dados[2], formatter);
                    double salarioBruto = Double.parseDouble(dados[3]);

                    if (cpfsFuncionarios.contains(cpf)) {
                        registrosRecusados.add(new String[]{cpf, nome});
                        funcionarioAtual = null;
                        continue;
                    }

                    Funcionario funcionario = new Funcionario(nome, cpf, dataNasc, salarioBruto);
                    try {
                        funcDao.inserir(funcionario);
                        funcionariosInseridos.add(funcionario);
                        cpfsFuncionarios.add(cpf);
                        funcionarioAtual = funcionario;
                        System.out.println(nome + " adicionado com sucesso.");
                    } catch (Exception e) {
                        registrosRecusados.add(new String[]{cpf, nome});
                        System.out.println("Problemas ao gravar registro de funcionário: " + nome);
                        funcionarioAtual = null;
                        continue;
                    }

                    // Gerar folha de pagamento
                    try {
                        folhaDao.inserir(new FolhaPagamento(funcionario, LocalDate.now()));
                    } catch (Exception e) {
                        System.out.println("Erro ao gerar folha de pagamento para: " + funcionario.getNome());
                    }

                } else if (funcionarioAtual != null) {
                    // É dependente
                    String nomeDep = dados[0];
                    String cpfDep = dados[1];
                    LocalDate dataNascDep = LocalDate.parse(dados[2], formatter);
                    Parentesco parentesco = null;
                    try {
                        parentesco = Parentesco.valueOf(dados[3].toUpperCase());
                    } catch (Exception e) {
                        registrosRecusados.add(new String[]{cpfDep, nomeDep});
                        continue;
                    }

                    if (cpfsDependentes.contains(cpfDep)) {
                        registrosRecusados.add(new String[]{cpfDep, nomeDep});
                        continue;
                    }

                    Dependente dependente = new Dependente(nomeDep, cpfDep, dataNascDep, parentesco);
                    try {
                        depDao.inserir(dependente, funcionarioAtual.getId());
                        funcionarioAtual.adicionarDependente(dependente);
                        cpfsDependentes.add(cpfDep);
                        System.out.println(nomeDep + " adicionado com sucesso.");
                    } catch (Exception e) {
                        registrosRecusados.add(new String[]{cpfDep, nomeDep});
                        System.out.println("Problemas ao gravar registro de dependente: " + nomeDep);
                    }
                }
            }

            // CSV de saída completo
            System.out.println("\nDigite o caminho completo para salvar o arquivo de saída com o nome:");
            String caminhoSaida = sc.nextLine();
            File arquivoSaida = new File(caminhoSaida);

            try (PrintWriter saida = new PrintWriter(new FileWriter(arquivoSaida))) {
                for (Funcionario f : funcionariosInseridos) {
                    saida.println(f.getNome() + ";" + f.getCpf() + ";" + f.getDataNascimento() + ";"
                            + String.format("%.2f", f.calcularINSS()) + ";" 
                            + String.format("%.2f", f.calcularIR()) + ";" 
                            + String.format("%.2f", f.calcularSalarioLiquido()));
                }
                System.out.println("Arquivo de saída gerado com sucesso em: " + arquivoSaida.getAbsolutePath());
            } catch (Exception e) {
                System.out.println("Erro ao gerar arquivo de saída: " + e.getMessage());
            }

            // CSV de recusados
            System.out.println("\nDigite o caminho completo para salvar o arquivo de recusados com nome:");
            String caminhoRecusados = sc.nextLine();
            File arquivoRecusados = new File(caminhoRecusados);

            try (PrintWriter recusados = new PrintWriter(new FileWriter(arquivoRecusados))) {
                for (String[] r : registrosRecusados) {
                    recusados.println(r[0] + ";" + r[1]);
                }
                System.out.println("Arquivo de recusados gerado com sucesso em: " + arquivoRecusados.getAbsolutePath());
            } catch (Exception e) {
                System.out.println("Erro ao gerar arquivo de recusados: " + e.getMessage());
            }

        } catch (FileNotFoundException e) {
            System.out.println("Erro ao abrir o arquivo: " + e.getMessage());
        } catch (DependenteException e1) {
			// TODO Auto-generated catch block
		}
    }
}