public class Candidato extends Pessoa{
    private String partido;
    private String numero_para_voto;
    private int votos;

    Candidato(String nome, int idade, String sexo, String partido, String numero_para_voto){
        super(nome, idade, sexo);
        this.partido = partido;
        this.numero_para_voto = numero_para_voto;
        this.votos = 0;
    }
    
    public String get_partido(){
        return partido;
    }

    public String get_numero_para_voto(){
        return numero_para_voto;
    }

    public int get_qtdvotos(){
        return votos;
    }

    @Override
    public String toString(){
        return super.toString() + "\nPartido: " + get_partido() + "\nNumero para voto: " + get_numero_para_voto(); 
    }
}