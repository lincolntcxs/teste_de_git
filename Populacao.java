import java.util.ArrayList;

public class Populacao {
    private ArrayList<Pessoa> popula; 
    
    Populacao() {
        this.popula = new ArrayList<>();
        this.carrega_dados_bd(); // ✅ Agora carrega do MySQL!
    }

    public ArrayList<Pessoa> get_pessoas(){
        return this.popula;
    }

    public void add_pessoa(Pessoa pessoa){
        popula.add(pessoa);
    }

    public int get_populacao(){
        return popula.size();
    }

    public Eleitor buscar_eleitor(String buscado) {
        for(Pessoa pessoa: popula){
            if(pessoa.get_nome().equalsIgnoreCase(buscado) && pessoa instanceof Eleitor){
                return (Eleitor) pessoa;
            }
        } return null;
    }

    public boolean existe_candidato(String buscado) {
        for(Pessoa pessoa: popula){
            if(pessoa.get_nome().equalsIgnoreCase(buscado) && pessoa instanceof Candidato){
                return true;
            }
        } return false;
    }

    public void listar_eleitores(){
        for(Pessoa pessoa : popula){
            if(pessoa instanceof Eleitor){
                System.out.println(pessoa + "\n");
            }
        }
    }

        public void listar_candidatos(){
        for(Pessoa pessoa : popula){
            if(pessoa instanceof Candidato){
                System.out.println(pessoa + "\n");
            }
        }
    }

    public void listar(){
        for (int i = 0; i < get_populacao(); i++) {
            System.out.println(popula.get(i).toString() + "\n");
        }
    }

    private void carrega_dados_bd() {
        // Carrega candidatos do MySQL
        ArrayList<Candidato> candidatos = Gerenciador_db.carregar_candidatos();
        this.popula.addAll(candidatos);
        
        // Carrega eleitores do MySQL
        ArrayList<Eleitor> eleitores = Gerenciador_db.carregar_eleitores();
        this.popula.addAll(eleitores);
        
        System.out.println("🎯 População carregada: " + this.popula.size() + " pessoas");
    }
}