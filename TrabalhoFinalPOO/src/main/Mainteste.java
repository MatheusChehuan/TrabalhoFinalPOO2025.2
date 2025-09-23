package main;

import java.io.File;
import java.util.Scanner;

import calcularsalario.DependenteException;

public class Mainteste {

    public static void main(String[] args) throws DependenteException {
        Scanner sc = new Scanner(System.in);

        System.out.println("Digite o caminho completo do arquivo CSV de entrada:");
        File entrada = new File(sc.nextLine());

        System.out.println("Digite o caminho completo para salvar o arquivo de saída:");
        File saida = new File(sc.nextLine());

        System.out.println("Digite o caminho completo para salvar o arquivo de recusados:");
        File recusados = new File(sc.nextLine());

        InsertMain.ArquivoEntrada(entrada, saida, recusados);
    }
}
