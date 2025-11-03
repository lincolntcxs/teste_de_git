import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;

public class WebAppUltraSimple {
    
    private static Populacao sta = new Populacao();
    private static Urna eleicao = new Urna();
    private static int id = 1;
    
    public static void main(String[] args) throws IOException {
        // ✅ CARREGA DADOS DO BANCO ANTES DE INICIAR!
        carregarDadosDoBanco();
        
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("🚀 Servidor web iniciado: http://localhost:8080");
        
        while (true) {
            Socket clientSocket = serverSocket.accept();
            new Thread(new ClientHandler(clientSocket)).start();
        }
    }
    
    // 🆕 MÉTODO PARA CARREGAR DADOS DO BANCO
    private static void carregarDadosDoBanco() {
        System.out.println("📂 Carregando dados do banco...");
        
        try {
            // 1. Carrega candidatos do BD
            carregarCandidatos();
            
            // 2. Carrega eleitores do BD  
            carregarEleitores();
            
            // 3. Carrega votos do BD
            carregarVotos();
            
            System.out.println("✅ Dados carregados: " + sta.get_pessoas().size() + " pessoas");
            
        } catch (Exception e) {
            System.out.println("❌ Erro ao carregar dados: " + e.getMessage());
            // Carrega dados manuais se o BD falhar
            carregarDadosManuais();
        }
    }
    
    private static void carregarCandidatos() throws SQLException {
        String sql = "SELECT nome, idade, sexo, partido, numero FROM candidato";
        
        Connection conn = Gerenciador_db.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        
        int count = 0;
        while (rs.next()) {
            Candidato cand = new Candidato(
                rs.getString("nome"),
                rs.getInt("idade"),
                rs.getString("sexo"), 
                rs.getString("partido"),
                rs.getString("numero")
            );
            sta.add_pessoa(cand);
            count++;
        }
        System.out.println("✅ " + count + " candidatos carregados");
        
        rs.close();
        stmt.close();
        conn.close();
    }
    
    private static void carregarEleitores() throws SQLException {
        String sql = "SELECT nome, idade, sexo, titulo, votou FROM eleitor";
        
        Connection conn = Gerenciador_db.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        
        int count = 0;
        while (rs.next()) {
            Eleitor eleitor = new Eleitor(
                rs.getString("nome"),
                rs.getInt("idade"),
                rs.getString("sexo"),
                rs.getString("titulo")
            );
            
            // Marca se já votou
            if (rs.getBoolean("votou")) {
                eleitor.votar();
            }
            
            sta.add_pessoa(eleitor);
            count++;
        }
        System.out.println("✅ " + count + " eleitores carregados");
        
        rs.close();
        stmt.close();
        conn.close();
    }
    
    private static void carregarVotos() throws SQLException {
        String sql = "SELECT reitor, vice FROM votos";
        
        Connection conn = Gerenciador_db.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        
        int count = 0;
        while (rs.next()) {
            Voto voto = new Voto(id++);
            voto.set_reitor(rs.getString("reitor"));
            voto.set_vice(rs.getString("vice"));
            eleicao.add_voto(voto);
            count++;
        }
        System.out.println("✅ " + count + " votos carregados");
        
        rs.close();
        stmt.close();
        conn.close();
    }
    
    // 🆕 MÉTODO DE FALLBACK - DADOS MANUAIS
    private static void carregarDadosManuais() {
        System.out.println("📝 Carregando dados manuais de fallback...");
        
        // Candidatos
        sta.add_pessoa(new Candidato("Dra. Marina EcoVerde", 45, "F", "PS", "44"));
        sta.add_pessoa(new Candidato("Prof. Arthur Conhecimento", 52, "M", "PDE", "25"));
        sta.add_pessoa(new Candidato("Tec. Lucas Inovações", 38, "M", "PTEC", "13"));
        
        // Eleitores
        sta.add_pessoa(new Eleitor("Ana Programadora", 25, "F", "123456789"));
        sta.add_pessoa(new Eleitor("Bruno Desenvolvedor", 30, "M", "987654321"));
        sta.add_pessoa(new Eleitor("Carla Analista", 28, "F", "456123789"));
        
        System.out.println("✅ Dados manuais carregados: " + sta.get_pessoas().size() + " pessoas");
    }
    
    static class ClientHandler implements Runnable {
        private Socket clientSocket;
        
        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }
        
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                
                String requestLine = in.readLine();
                if (requestLine != null) {
                    String[] requestParts = requestLine.split(" ");
                    String method = requestParts[0];
                    String path = requestParts[1];
                    
                    System.out.println("📨 Recebido: " + method + " " + path);
                    
                    // Roteamento simples
                    if (path.equals("/")) {
                        sendHtmlResponse(out, getHomePage());
                    } else if (path.equals("/candidatos")) {
                        sendJsonResponse(out, getCandidatosJson());
                    } else if (path.equals("/eleitores")) {
                        sendJsonResponse(out, getEleitoresJson());
                    } else if (path.equals("/votar")) {
                        if (method.equals("POST")) {
                            // Processa voto
                            StringBuilder body = new StringBuilder();
                            String line;
                            while ((line = in.readLine()) != null && !line.isEmpty()) {
                                body.append(line);
                            }
                            String response = processarVoto(body.toString());
                            sendPlainResponse(out, response);
                        } else {
                            sendHtmlResponse(out, getVotarPage());
                        }
                    } else if (path.equals("/resultado")) {
                        sendHtmlResponse(out, getResultadoPage());
                    } else {
                        sendPlainResponse(out, "404 - Página não encontrada");
                    }
                }
                
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        private void sendHtmlResponse(PrintWriter out, String content) {
            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: text/html; charset=UTF-8");
            out.println();
            out.println(content);
        }
        
        private void sendJsonResponse(PrintWriter out, String content) {
            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: application/json; charset=UTF-8");
            out.println();
            out.println(content);
        }
        
        private void sendPlainResponse(PrintWriter out, String content) {
            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: text/plain; charset=UTF-8");
            out.println();
            out.println(content);
        }
        
        private String getHomePage() {
            return "<html><body><h1>🗳️ Sistema Eleição UFABC</h1>" +
                   "<p>Sistema funcionando! 🎉</p>" +
                   "<p><a href='/candidatos'>Candidatos</a> | " +
                   "<a href='/eleitores'>Eleitores</a> | " +
                   "<a href='/votar'>Votar</a> | " +
                   "<a href='/resultado'>Resultado</a></p>" +
                   "</body></html>";
        }
        
        private String getCandidatosJson() {
            List<Map<String, String>> candidatos = new ArrayList<>();
            for (Pessoa p : sta.get_pessoas()) {
                if (p instanceof Candidato) {
                    Candidato cand = (Candidato) p;
                    Map<String, String> info = new HashMap<>();
                    info.put("nome", cand.get_nome());
                    info.put("partido", cand.get_partido());
                    info.put("numero", cand.get_numero_para_voto());
                    candidatos.add(info);
                }
            }
            return candidatos.toString();
        }
        
        private String getEleitoresJson() {
            List<Map<String, String>> eleitores = new ArrayList<>();
            for (Pessoa p : sta.get_pessoas()) {
                if (p instanceof Eleitor) {
                    Eleitor eleitor = (Eleitor) p;
                    Map<String, String> info = new HashMap<>();
                    info.put("nome", eleitor.get_nome());
                    info.put("titulo", eleitor.get_titulo());
                    info.put("votou", String.valueOf(eleitor.get_votou()));
                    eleitores.add(info);
                }
            }
            return eleitores.toString();
        }
        
        private String getVotarPage() {
            return "<html><body><h1>✅ Votar</h1>" +
                   "<form method='POST'>" +
                   "<input type='text' name='eleitorNome' placeholder='Nome do Eleitor' required><br>" +
                   "<input type='text' name='reitor' placeholder='Candidato a Reitor' required><br>" +
                   "<input type='text' name='vice' placeholder='Candidato a Vice' required><br>" +
                   "<button type='submit'>Votar</button>" +
                   "</form>" +
                   "<p><a href='/'>← Voltar</a></p>" +
                   "</body></html>";
        }
        
        private String processarVoto(String body) {
            try {
                // Parsing melhorado do formulário
                Map<String, String> params = new HashMap<>();
                String[] pairs = body.split("&");
                for (String pair : pairs) {
                    String[] keyValue = pair.split("=");
                    if (keyValue.length == 2) {
                        params.put(keyValue[0], URLDecoder.decode(keyValue[1], "UTF-8"));
                    }
                }
                
                String eleitorNome = params.get("eleitorNome");
                String reitor = params.get("reitor");
                String vice = params.get("vice");
                
                if (eleitorNome == null || reitor == null || vice == null) {
                    return "❌ Dados incompletos!";
                }
                
                Eleitor aux = sta.buscar_eleitor(eleitorNome);
                if (aux != null && !aux.get_votou()) {
                    Voto voto = new Voto(id++);
                    voto.set_reitor(reitor);
                    voto.set_vice(vice);
                    eleicao.add_voto(voto);
                    aux.votar();
                    
                    // Salva no BD se possível
                    try {
                        Gerenciador_db.salvar_voto(voto);
                        Gerenciador_db.votar_no_db(eleitorNome);
                    } catch (Exception e) {
                        System.out.println("⚠️  Não salvou no BD: " + e.getMessage());
                    }
                    
                    return "✅ Voto registrado para: " + eleitorNome + "\nReitor: " + reitor + "\nVice: " + vice;
                }
                return "❌ Eleitor não encontrado ou já votou!";
            } catch (Exception e) {
                return "❌ Erro: " + e.getMessage();
            }
        }
        
        private String getResultadoPage() {
            Map<String, Integer> votosReitor = new HashMap<>();
            Map<String, Integer> votosVice = new HashMap<>();
            
            // Supondo que eleicao.getVotos() retorne a lista de votos
            // Se não tiver esse método, você precisa criar
            for (Voto voto : eleicao.votos()) {
                votosReitor.put(voto.get_reitor(), 
                    votosReitor.getOrDefault(voto.get_reitor(), 0) + 1);
                votosVice.put(voto.get_vice(), 
                    votosVice.getOrDefault(voto.get_vice(), 0) + 1);
            }
            
            return "<html><body><h1>📊 Resultado da Eleição</h1>" +
                   "<h2>Votos para Reitor:</h2><pre>" + votosReitor.toString() + "</pre>" +
                   "<h2>Votos para Vice:</h2><pre>" + votosVice.toString() + "</pre>" +
                   "<p><a href='/'>← Voltar</a></p>" +
                   "</body></html>";
        }
    }
}