import classes.Conta;

import java.util.Scanner;
import java.util.Random;

public class GerenciarContas {
    public static void main(String[] args){
        // Variáveis e instância de input
        String nome;
        double inicial;
        int limite;
        Scanner entrada = new Scanner(System.in);

        // Criando número da conta
        Random numero = new Random();
        int conta = 1 + numero.nextInt(9999);
    
        // Inputs do cliente
        System.out.println("Abrindo uma nova conta");
        System.out.print("Nome do cliente: ");
        nome = entrada.nextLine();
        System.out.print("Limite de saques diários: ");
        limite = entrada.nextInt();
        
        System.out.print("Valor inicial a ser depositado na conta: ");
        inicial = entrada.nextDouble();
        
        // Criando conta do cliente
        Conta minhaConta = new Conta(nome, conta, inicial, limite);
        minhaConta.iniciar();

        // Encerrando instância do input
        entrada.close();
    }   
}