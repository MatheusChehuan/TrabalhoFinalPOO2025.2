package arquivos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import calcularsalario.DependenteException;
import calcularsalario.Parentesco;
import entity.Dependente;
import entity.Funcionario;

	public class arquivoteste {

		public static void main(String[] args) {

			try {
				File file = new File("C:\\Users\\mathe\\OneDrive\\Documents\\teste\\entrada.csv");
				Scanner sc = new Scanner(file);

				Set<Funcionario> funcionarios = new HashSet<>();
				Set<Dependente> dependentes = new HashSet<>();
				
				
				while (sc.hasNextLine()) {
				    String linha = sc.nextLine();
				    //adiciona funcionarios
				    if (!linha.isEmpty()) {


				    String[] dados = linha.split(";");
				    String nome = dados[0];
				    String cpf = dados[1];
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
				    LocalDate dataNasc = LocalDate.parse(dados[2], formatter);
				    double salarioBruto = Double.parseDouble(dados[3]);

				    Funcionario funcionario = new Funcionario(nome, cpf, dataNasc, salarioBruto);
				    funcionarios.add(funcionario);

				    //adiciona dependentes
				    while (sc.hasNextLine()) {
				        String linhadep = sc.nextLine();
				        
				        if (linhadep.isEmpty()) {
				            break;
				        }

				        String[] dadosdep = linhadep.split(";");
				        
				        
				        //a ver isso aqui
				        if (dadosdep.length == 4) {
				            linha = linhadep; 
				            break;
				        }
				        String nomedep = dadosdep[0];
				        String cpfdep = dadosdep[1];
				        LocalDate dataNascDep = LocalDate.parse(dadosdep[2], formatter);
				        Parentesco parentesco = Parentesco.valueOf(dadosdep[3].toUpperCase());

				        Dependente dependente = new Dependente(nomedep, cpfdep, dataNascDep, parentesco);
				        dependentes.add(dependente);
				        funcionario.adicionarDependente(dependente);
				    }
				  }
				}
				sc.close();

				System.out.println("=====Leitura de arquivo=====\n");
				for (Funcionario e : funcionarios) {
					System.out.println(e);
				}
				for (Dependente d : dependentes) {
					System.out.println(d);
				}

				System.out.println("\n====Gravação de arquivo====");
				FileWriter caminho = new FileWriter("C:\\Users\\mathe\\OneDrive\\Documents\\teste\\teste.csv");
				PrintWriter gravar = new PrintWriter(caminho);

				for (Funcionario e : funcionarios) {
					String linhaArquivo = e.getNome() + ";" + e.getCpf() + ";" + e.getDataNascimento() + ";"
							+ String.format("%.2f",e.calcularINSS()) + ";" + String.format("%.2f",e.calcularIR()) + ";" + String.format("%.2f",e.calcularSalarioLiquido()) + "\n";
					gravar.printf(linhaArquivo);
				}
			
				gravar.close();
				System.out.println("\nGravação de arquivo feita com sucesso!");
	
			} catch (FileNotFoundException e) {
				System.out.println("Arquivo não encontrado");
			}
			catch (IOException e1) {
				System.out.println("Arquivo de saída com problema");

			} catch (DependenteException e1) {
				System.out.println("Erro no dependente");
			}
		}
}