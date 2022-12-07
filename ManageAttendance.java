public class ManageAttendance {
    private final int MAXSIZE;
    private final Client[] vetor;
    private int posUltimo = -1;
    private int count_attend_old=0;

    public ManageAttendance(int size) {
        this.MAXSIZE = size;
        this.vetor = new Client[size];
    }  // inicia nova instância com o tamanho máximo das filas

    public boolean isEmpty() {
        return (this.posUltimo == -1);
    } // retorna true quando não há clientes para atendimento

    public int numClients( ){
        return this.posUltimo + 1;
    } // retorna o número de clientes aguardando atendimento

    public int numElderlyClients( ){
        int qtd_elderly = 0;
        for (int i = 0; i <= this.posUltimo; i++){
            if(vetor[i].isElderly() == true){
                qtd_elderly += 1;
            }
        }
        return qtd_elderly;
    } // retorna o número de clientes idosos aguardando atendimento

    public void addClientes(Client valor){
        boolean cliente_inserido = false;
        int last_oldman = 0;

        if(isEmpty()){
            cliente_inserido=insereInicio(valor);
        }else {
            if (valor.isElderly() == true) {
                if(numClients() == 1 && vetor[0].isElderly() == false){
                    cliente_inserido=insereInicio(valor);
                } else if (numClients() == 2 && vetor[1].isElderly() == false) {
                    cliente_inserido=insert(valor, 1);
                } else if (numClients() == 3 && vetor[2].isElderly() == false && vetor[1].isElderly() == true) {
                    cliente_inserido=insert(valor, 2);
                } else if (numClients() == 3 && vetor[2].isElderly() == false && vetor[1].isElderly() == false) {
                    cliente_inserido=insereInicio(valor);
                } else if (numClients() > 3) {
                    for(int i = this.posUltimo; i >= 0; i--){
                        if(vetor[i].isElderly()){
                            last_oldman = i;
                            break;
                        }
                    }
                    if(last_oldman == 0){
                        cliente_inserido=insert(valor, 2); // pq ja atendeu 1 idoso
                    } else if (last_oldman == numClients() && vetor[numClients()-1].isElderly()==false){
                        cliente_inserido=insereFinal(valor);
                    } else if (vetor[last_oldman-1].isElderly()==false) {
                        cliente_inserido=insert(valor, last_oldman+1);
                    } else if (vetor[last_oldman-1].isElderly()==true) {
                        if(last_oldman+2 > numClients()){
                            cliente_inserido=insereFinal(valor);
                        }else{
                            cliente_inserido=insert(valor, last_oldman+2);
                        }
                    } else {
                        if(vetor[0].isElderly() && count_attend_old == 2){
                            cliente_inserido=insert(valor, 2);
                        }else{
                            cliente_inserido=insereFinal(valor);
//                            primeiro
                        } // primeiro for velho insere no seguindo se nao tiver outros velhor a frete

                    }
                } else{
                    cliente_inserido=insereFinal(valor);
                }
            } else { // normal
                cliente_inserido=insereFinal(valor);
            }
        }
        if(cliente_inserido){
            System.out.println("> Inserido: " + valor.getName());
        }else{
            System.out.println("> novo cliente não foi inserido!");
        }

    }

    public Client showNext( ){
        return vetor[0];
    } // retorna o próximo cliente a ser atendido, mas NÃO remove o cliente // TODO: teste

    public Client getNext( ){
        Client next = showNext();
        if (!isEmpty()){
            for (int i = 0; i < numClients(); i++) {
                try {
                    vetor[i] = vetor[i+1];
                }
                catch(Exception e) {
                    continue;
                }
            }
            this.posUltimo--;
        }
        if(next.isElderly()){
            count_attend_old++;
        }
        return next;
    } // retorna o próximo cliente para ser atendido e remove da fila

    public void showQueues() {
        String str_olds = "";
        String str_norm = "";
        for (int i = 0; i <= this.posUltimo; i++) {
            if(vetor[i].isElderly()){
                str_olds += "-" + vetor[i].getName() + ":" + vetor[i].getAge();
            }else{
                str_norm += "-" + vetor[i].getName() + ":" + vetor[i].getAge();
            }
        }
        if (str_olds.isBlank()){
            System.out.print("idoso:vazia");
        }else{
            System.out.print("idoso:"+str_olds.substring(1));
        }
        if(str_norm.isBlank()){
            System.out.print("; " + "normal:vazia\n");
        }else{
            System.out.print("; " + "normal:" + str_norm.substring(1) + "\n");
        }
    }

    // Métodos Auxiliares
    public boolean isFull() {
        if (this.posUltimo == (MAXSIZE - 1)) {return true;}
        else {return false;}
    }

    //insere inicio
    private boolean insereInicio(Client valor) {
        if (isEmpty()) {
            this.vetor[0] = valor;
            this.posUltimo = 0;
            return true;
        }
        else if (isFull()) {
            return false;
        }
        else {
            for (int i = posUltimo; i >= 0; i--) {
                vetor[i+1] = vetor[i];
            }
            vetor[0] = valor;
            this.posUltimo++;
            return true;
        }
    }

    //insere meio
    private boolean insereMeio(Client valor, int pos) {
        if ((pos < 0) || (pos > this.posUltimo)) {
            return false;
        }
        else if (isFull()) {
            return false;
        }
        else{
            for (int i = this.posUltimo; i >= pos; i--) {
                vetor[i+1] = vetor[i];
            }
            vetor[pos] = valor;
            this.posUltimo++;
            return true;
        }
    }

    //insere final
    private boolean insereFinal(Client valor) {
        if (isEmpty()) {
            vetor[0] = valor;
            this.posUltimo++;
            return true;
        }
        else if (isFull()) {
            return false;
        }
        else {
            vetor[posUltimo+1] = valor;
            this.posUltimo++;
            return true;
        }
    }

    //insert
    public boolean insert(Client valor, int pos) {
        if (pos == 0) {return insereInicio(valor);}
        else if (pos == (posUltimo+1)) {return insereFinal(valor);}
        else {return insereMeio(valor, pos);}
    }

    public void printFila(){
        int max_value_name = 7;
        for (int i = 0; i <= this.posUltimo; i++){
            if(vetor[i].getName().length() > max_value_name){
                max_value_name = vetor[i].getName().length();
            }
        }

        //posicao / nome / idade
        String string_line = "-";
        String finalResult = string_line.repeat(7+max_value_name+6+8+5+3);

        System.out.println("+" + finalResult + "+"); // inicio

        System.out.printf("| %7s " + "| %"+max_value_name+"s " + "| %"+6+"s " + "| %"+5+"s |", "posicao", "nome", "idade", "idoso");
        System.out.println();
        System.out.printf("| %7s " + "+ %"+max_value_name+"s " + "+ %"+6+"s " + "+ %"+5+"s |",
                string_line.repeat(7), string_line.repeat(max_value_name), string_line.repeat(6), string_line.repeat(5));
        System.out.println(); // sep

        for (int i = 0; i <= this.posUltimo; i++){
            System.out.format("| %7s " + "| %"+max_value_name+"s " + "| %6s " + "| %5s |",
                    (i+1), vetor[i].getName(), vetor[i].getAge(), vetor[i].isElderly());
            System.out.println(); // value
        }
        System.out.println("+" + finalResult + "+"); // final
        //TODO: exibir quantidade total
    }

}
