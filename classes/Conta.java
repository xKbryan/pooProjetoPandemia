package classes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Conta {
    private String nome;
    private int conta, saques, limiteSaque;
    private double saldo;
    private File arquivo;
    Scanner entrada = new Scanner(System.in);

    public Conta(String nome, int conta, double saldo_inicial, int limite) {
        arquivo = new File("Log.txt");
        this.nome = nome;
        this.conta = conta;
        this.limiteSaque = limite;
        saldo = saldo_inicial;
        saques = 0;
        
        try {
            this.log("Conta aberta\nLimite de saques diário: " + limiteSaque + "\nSaldo inicial: " + saldo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void extrato() {
        System.out.println("\tEXTRATO");
        System.out.println("Nome: " + this.nome);
        System.out.println("Número da conta: " + this.conta);
        System.out.printf("Saldo atual: %.2f\n", this.saldo);
        System.out.println("Saques realizados hoje: " + this.saques + "\n");

        try {
            this.log("Extrato emitido");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void log(String operacao) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	    Date date = new Date();
        String agora = dateFormat.format(date);
        
        FileWriter fw = new FileWriter(arquivo, true);

		fw.write("--------------------------------------\n");
		fw.write(String.valueOf(agora) + " - ");
		fw.write(String.valueOf(this.nome + ": " + this.conta + "\n"));
		fw.write(String.valueOf(operacao) + "\n");
		fw.write(String.valueOf("Saldo: " + this.saldo + "\n"));
		fw.write(String.valueOf("Saques realizados: " + this.saques + "\n"));
		fw.write("--------------------------------------\n\n");
		
		fw.close();
    }
    
    public void sacar(double valor){
        String res = "";

        if (valor <= 0) {
            System.out.println("Valor não aceito. Faça um saque com valor positivo\n");

            res = "Tentativa de saque falho\nValor da tentatida de saque: " + valor;
        } else if (saldo >= valor){
            saldo -= valor;
            saques++;
            System.out.println("Sacado: " + valor);
            System.out.println("Novo saldo: " + saldo + "\n");

            res = "Saque feito\nValor do saque: " + valor;
        } else {
            System.out.println("Saldo insuficiente. Faça um depósito\n");

            res = "Tentativa de saque não suficiente\nValor da tentatida de saque: " + valor;
        }
        
        try {
            this.log(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void depositar(double valor) {
        String res = "";
        if(valor > 0) {
            saldo += valor;
            System.out.println("Depositado: " + valor);
            System.out.println("Novo saldo: " + saldo + "\n");

            res = "Deposito feito \nValor do deposito: " + valor;
        } else {
            System.out.println("Valor do deposito não pode ser negativo\n");

            res = "Tentativa de deposito mal sucedida \nValor da tentativa de deposito: " + valor;
        }

        try {
            this.log(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void iniciar(){
        int opcao;

        do {
            exibeMenu();
            opcao = entrada.nextInt();
            escolheOpcao(opcao);
        } while(opcao != 4);
    }
    
    public void exibeMenu(){
        System.out.println("\t Escolha a opção desejada");
        System.out.println("1 - Consultar Extrato");
        System.out.println("2 - Sacar");
        System.out.println("3 - Depositar");
        System.out.println("4 - Sair\n");
        System.out.print("Opção: ");
        
    }
    
    public void escolheOpcao(int opcao){
        double valor;
        
        switch( opcao ){
            case 1:    
                    extrato();
                    break;
            case 2: 
                    if(saques < limiteSaque){
                        System.out.print("Quanto deseja sacar: ");
                        valor = entrada.nextDouble();
                        sacar(valor);
                    } else{
                        System.out.println("Limite de saques diários atingidos.\n");

                        try {
                            this.log("Saque não efetuado.\nLimite de saques diários atingido: " + limiteSaque);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                    
            case 3:
                    System.out.print("Quanto deseja depositar: ");
                    valor = entrada.nextDouble();
                    depositar(valor);
                    break;
                    
            case 4: 
                    System.out.println("Sistema encerrado.");
                    break;
                    
            default:
                    System.out.println("Opção inválida");
        }
    }
}