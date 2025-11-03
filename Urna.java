import java.util.ArrayList;

public class Urna { 
    private ArrayList<Voto> votos;

    Urna(){
        this.votos = new ArrayList<>();
        this.carregar_urna_db();
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

    private void carregar_urna_db(){
        ArrayList<Voto> vot= Gerenciador_db.carregar_urna();
        this.votos.addAll(vot);
        System.out.println("🎯 Urna carregada com: " + this.votos.size() + " votos");
    }

    public void listar(){
        for(int i = 0; i < votos.size(); i++){
            System.out.println(votos.get(i).toString() + "\n");
        }
    }
}