public class Pessoa {
    private String nome;
    private int idade;
    private String sexo;

    Pessoa(String nome, int idade, String sexo){
        this.nome = nome;
        this.idade = idade;
        this.sexo = sexo;
    }

    public String get_nome(){
        return nome;
    }
    
    public int get_idade(){
        return idade;
    }

    public String get_sexo(){
        return sexo;
    }

    public String toString(){
        return "Nome: " + get_nome() + "\nIdade: " + get_idade() + "\nSexo: " + get_sexo();
    }

    public static void main(String[] args){
        System.out.println("AQUI");
        Pessoa carlos = new Pessoa("Carlos", 34, "Masculino");
        System.out.println(carlos.toString());
    }
}