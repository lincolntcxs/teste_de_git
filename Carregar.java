public class Carregar{ 
    public static void carregar_dados_manuais(Populacao populacao) {
        // Mesmos dados do JSON, mas em código
        populacao.add_pessoa(new Candidato("Carlos Silva", 45, "M", "PT", "13"));
        populacao.add_pessoa(new Candidato("Maria Santos", 52, "F", "PSDB", "45"));
        populacao.add_pessoa(new Eleitor("Ana Costa", 25, "F", "123456789"));
        populacao.add_pessoa(new Eleitor("Pedro Alves", 30, "M", "987654321"));
    }
}