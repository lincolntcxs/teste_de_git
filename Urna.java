import java.util.*;

public class Urna { 
    private ArrayList<Voto> votos;

    Urna(){
        this.votos = new ArrayList<>();
        this.carregar_urna_db();
    }

    public void add_voto(Voto voto){
        votos.add(voto);
    }
    
    public ArrayList<Voto> votos(){
        return this.votos;
    } 

    public void get_votos(){
        for(int i = 0; i < votos.size(); i++){
            System.out.print(votos.get(i).get_id() + " ");
        }
    }

    public int get_prox_id() {
        return this.votos.size() + 1;
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

     public void mostrar_resultado_eleicao() {
        if (votos.isEmpty()) {
            System.out.println("❌ Nenhum voto registrado ainda!");
            return;
        }
        
        // HashMap para contar votos de REITOR
        Map<String, Integer> contagem_reitor = new HashMap<>();
        Map<String, Integer> contagem_vice = new HashMap<>();
        
        // Conta os votos
        for (Voto voto : votos) {
            // Conta voto para Reitor
            String reitor = voto.get_reitor();
            contagem_reitor.put(reitor, contagem_reitor.getOrDefault(reitor, 0) + 1);
            
            // Conta voto para Vice
            String vice = voto.get_vice();
            contagem_vice.put(vice, contagem_vice.getOrDefault(vice, 0) + 1);
        }
        
        // Encontra os mais votados
        String reitor_mais_votado = encontrarMaisVotado(contagem_reitor);
        String vice_mais_votado = encontrarMaisVotado(contagem_vice);
        
        // Exibe resultados
        System.out.println("\n🎊 RESULTADO DA ELEIÇÃO 🎊");
        System.out.println("===============================");
        
        System.out.println("\n🏆 REITOR MAIS VOTADO:");
        System.out.println("👑 " + reitor_mais_votado + " - " + contagem_reitor.get(reitor_mais_votado) + " votos");
        
        System.out.println("\n🎯 VICE-REITOR MAIS VOTADO:");
        System.out.println("⭐ " + vice_mais_votado + " - " + contagem_vice.get(vice_mais_votado) + " votos");
        
        System.out.println("\n📊 DETALHAMENTO COMPLETO:");
        System.out.println("\nCARGOS PARA REITOR:");
        exibir_ranking(contagem_reitor);
        
        System.out.println("\nCARGOS PARA VICE-REITOR:");
        exibir_ranking(contagem_vice);
    }
    
    private String encontrarMaisVotado(Map<String, Integer> contagem) {
        String maisVotado = null;
        int maxVotos = 0;
        
        for (Map.Entry<String, Integer> entry : contagem.entrySet()) {
            if (entry.getValue() > maxVotos) {
                maxVotos = entry.getValue();
                maisVotado = entry.getKey();
            }
        }
        return maisVotado;
    }
    
    private void exibir_ranking(Map<String, Integer> contagem) {
        // Converte para lista para ordenar
        List<Map.Entry<String, Integer>> lista = new ArrayList<>(contagem.entrySet());
        
        // Ordena por votos (decrescente)
        lista.sort((a, b) -> b.getValue().compareTo(a.getValue()));
        
        // Exibe ranking
        int posicao = 1;
        for (Map.Entry<String, Integer> entry : lista) {
            System.out.println(posicao + "º - " + entry.getKey() + ": " + entry.getValue() + " votos");
            posicao++;
        }
    }
}