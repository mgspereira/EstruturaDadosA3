// Dinheiro na Mao, tem apenas um caixa para atendimento aos clientes

// Cada cliente que chega para ser atendido pode ser idoso (idade igual ou superior a 60 anos) ou não.  ----- ??

// Objetivo:
// um sistema que irá coordenar o atendimento dos clientes do banco.

//o programa deverá alternar a chamada de atendimento entre os idosos e os não-idosos, priorizando os idosos.
// Ao chegar um idoso, o atendimento deste idoso deve ser priorizado.

//O programa deverá alternar a
//        chamada de atendimento entre os idosos e os não-idosos, priorizando os idosos. Ao chegar um
//        idoso, o atendimento deste idoso deve ser priorizado. A cada dois atendimentos de idosos, um não
//        idoso poderá ser atendido. No momento que um idoso deveria ser chamado, caso não tenha idoso,
//        um não-idoso deverá ser chamado, e vice-versa.

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // entrada de dados
        Scanner in = new Scanner(System.in,"ISO-8859-1");
        int escolha;
        ManageAttendance system = new ManageAttendance(5);

        do {
            do { // O programa ira encerrar com a opção 5 do menu
                System.out.print("___________________________________________________________________________________________________\n" +
                        "1 - Chegada do cliente na agencia\n" +
                        "2 - Verificar quem eh o próximo a ser atendido\n" +
                        "3 - Atender um cliente (ao atender o cliente, o nome e a idade dele deve ser apresentada)\n" +
                        "4 - Exibir as filas (idoso e nao idoso)\n" +
                        "5 - Finalizar o programa (que so podera ser finalizado caso nao tenha mais clientes aguardando)\n" +
                        "___________________________________________________________________________________________________\n" +
                        "Digite sua escolha: ");

                try{
                    escolha = Integer.parseInt(in.nextLine());
                }catch (Exception e){
                    escolha=-1;
                }

                // Valida a escolha de opcao
                if(escolha < 0 || escolha >= 5){
                    if(escolha==5){
                        if(system.isEmpty()){
                            System.out.println("Finalzando o programa..");
                            System.exit(0);
                        }else{
                            System.out.println("> Ha clientes para atender! Nao pode sair.");
                        }

                    }else {
                        System.out.println("Opcao invalida!");
                    }
                }

            }while(escolha < 0 || escolha > 5);

            switch (escolha){
                case 1:
                    System.out.print("-> Nome: ");
                    String nome = in.nextLine();
                    if(nome.length() > 5){
                        System.out.print("-> Idade: ");
                        int idade = Integer.parseInt(in.nextLine());
                        if(idade > 16){
                            system.addClientes(new Client(nome, idade)); // Adiciona
                        }else{
                            System.out.println("novo cliente nao foi inserido. Menor de idade");
                        }
                    }else{
                        System.out.println("novo cliente nao foi inserido. Nome invalido!");
                    }
                    break;
                case 2:
                    if (system.isEmpty()){
                        System.out.println("Fila vazia!");
                    }else{
                        Client next_client = system.showNext();
                        System.out.println("Proximo da fila >> Nome: " + next_client.getName() + " Idade: " + next_client.getAge());
                    }
                    break;
                case 3:
                    if (system.isEmpty()){
                        System.out.println("Fila vazia!");
                    }else{
                        Client client_att = system.getNext();
                        System.out.println("Atendimento >> Nome: " + client_att.getName() + " Idade: " + client_att.getAge());
                    }
                    break;
                case 4:
                    system.showQueues();
                    break;
                case 0:
                    system.printFila();
                    break;
            }

            // Continuacao do programa
            System.out.println("\n");
        }while(true);


    }
}
