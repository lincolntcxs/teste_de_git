import java.util.ArrayList;

public class Populacao {
    private ArrayList<Pessoa> popula; 
    
    Populacao() {
        this.popula = new ArrayList<>();
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
                System.out.println(pessoa);
            }
        }
    }

        public void listar_candidatos(){
        for(Pessoa pessoa : popula){
            if(pessoa instanceof Candidato){
                System.out.println(pessoa);
            }
        }
    }

    public void listar(){
        for (int i = 0; i < get_populacao(); i++) {
            System.out.println(popula.get(i).toString() + "\n");
        }
    }

    private void carregarDadosDefault() {
        // Candidatos
        this.add_pessoa(new Candidato("Carlos Silva", 45, "M", "PT", "13"));
        this.add_pessoa(new Candidato("Maria Santos", 52, "F", "PSDB", "45"));
        this.add_pessoa(new Candidato("Fernanda Lima", 41, "F", "NOVO", "30"));
        
        // Eleitores
        this.add_pessoa(new Eleitor("Ana Costa", 25, "F", "123456789"));
        this.add_pessoa(new Eleitor("Pedro Alves", 30, "M", "987654321"));
        this.add_pessoa(new Eleitor("Roberto Santos", 35, "M", "456123780"));
    }
}