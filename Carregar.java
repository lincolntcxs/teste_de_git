import java.nio.file.Files;
import java.nio.file.Paths;

public class Carregar{
    public static void carregar_dados_padroes(Populacao populacao) {
        try {
            // Tenta ler do arquivo JSON
            String content = new String(Files.readAllBytes(Paths.get("dados_padroes.json")));
            System.out.println("✅ Dados carregados do JSON!");
        } catch (Exception e) {
            // Fallback para dados manuais
            System.out.println("ℹ️  Usando dados default manuais...");
            carregarDadosManuais(populacao);
        }
    }
    
    private static void carregarDadosManuais(Populacao populacao) {
        // Mesmos dados do JSON, mas em código
        populacao.add_pessoa(new Candidato("Carlos Silva", 45, "M", "PT", "13"));
        populacao.add_pessoa(new Candidato("Maria Santos", 52, "F", "PSDB", "45"));
        populacao.add_pessoa(new Eleitor("Ana Costa", 25, "F", "123456789"));
        populacao.add_pessoa(new Eleitor("Pedro Alves", 30, "M", "987654321"));
    }
}