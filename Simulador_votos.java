import java.util.Random;

public class Simulador_votos {
    
    public static void simularVotacao(Populacao sta, Urna eleicao_2025) {
        System.out.println("🎲 Iniciando simulação de votação...");
        
        Random random = new Random();
        String[] candidatos = {
            "Dra. Marina EcoVerde", "Prof. Arthur Conhecimento", 
            "Tec. Lucas Inovações", "Sra. Beatriz Comunidade",
            "Dr. Carlos Saúde", "Eng. Sofia Infraestrutura",
            "Sr. Roberto Trabalho", "Dra. Ana Cultura"
        };
        
        // Simula votos para cada eleitor
        for (Pessoa pessoa : sta.get_pessoas()) {
            if (pessoa instanceof Eleitor) {
                Eleitor eleitor = (Eleitor) pessoa;
                
                if (!eleitor.get_votou()) {
                    // Gera voto aleatório
                    String reitor = candidatos[random.nextInt(candidatos.length)];
                    String vice = candidatos[random.nextInt(candidatos.length)];
                    
                    // Garante que reitor e vice sejam diferentes
                    while (vice.equals(reitor)) {
                        vice = candidatos[random.nextInt(candidatos.length)];
                    }
                    
                    // Cria e registra o voto
                    Voto voto = new Voto(eleicao_2025.get_prox_id());
                    voto.set_reitor(reitor);
                    voto.set_vice(vice);
                    
                    eleicao_2025.add_voto(voto);
                    eleitor.votar();
                    
                    // Salva no banco de dados
                    Gerenciador_db.votar_no_db(eleitor.get_nome());
                    Gerenciador_db.salvar_voto(voto);
                    
                    System.out.println("✅ " + eleitor.get_nome() + " votou em: " + 
                                     reitor + " / " + vice);
                }
            }
        }
        System.out.println("🎯 Simulação concluída! " + eleicao_2025.get_qtdvotos() + " votos registrados.");
    }
}