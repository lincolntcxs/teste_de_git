import java.util.ArrayList;

public class Urna { 
    private ArrayList<Voto> votos;

    Urna(){
        this.votos = new ArrayList<>();
    }

    public void add_voto(Voto voto){
        votos.add(voto);
    }
    
    public void get_votos(){
        for(int i = 0; i < votos.size(); i++){
            System.out.print(votos.get(i).get_id() + " ");
        }
    }

    public String get_qtdvotos(){
        return "Quantidade de votos: " + votos.size();
    }

    public void listar(){
        for(int i = 0; i < votos.size(); i++){
            System.out.println(votos.get(i).toString() + "\n");
        }
    }
}