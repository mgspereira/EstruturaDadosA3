public class Client {
    private String nome;
    private int age;

    // Validicacao:
    //    Cliente precisa ter nome com pelo menos 5 caracteres, não brancos (“ “). Além disso, a
    //    idade mínima deve ser 16 anos.
    // TODO: Como implementar essa validacao

    //construtor
    public Client(String nome, int age) {
        this.nome = nome;
        this.age = age;
    }

    public String getName( ){
        return this.nome;
    } // retorna o nome ( name ) do cliente

    public int getAge( ){
        return this.age;
    } // retorna a idade ( age ) do cliente

    public boolean isElderly(){
        return (this.age >= 60);
    } // retorna true se a pessoa for idosa ( >= 60 anos ).


}
