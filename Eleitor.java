public class Eleitor extends Pessoa{
    private String titulo;
    private boolean votou; 

    Eleitor(String nome, int idade, String sexo, String titulo){
        super(nome, idade, sexo);
        this.titulo = titulo;
        this.votou = false;
    }

    public String get_titulo(){
        return titulo;
    }

    public boolean get_votou(){
        return votou;
    }

    public void votar(){
        votou = true;
    }

    public String toString(){
        return super.toString() + "\nTitulo: " +get_titulo(); 
    }
}