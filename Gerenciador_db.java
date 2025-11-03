import java.sql.*;
import java.util.ArrayList;

import com.mysql.cj.jdbc.ClientPreparedStatement;

public class Gerenciador_db {
    /* 
     static {
        try {
            // ✅ REGISTRA O DRIVER MANUALMENTE
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Driver MySQL não encontrado!");
            e.printStackTrace();
        }  
    } */

    private static final String URL = "jdbc:mysql://localhost:3306/eleicao";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void salvar_candidato(Candidato cand){
        try {
            // ✅ REGISTRA O DRIVER MANUALMENTE
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Driver MySQL não encontrado!");
            e.printStackTrace();
        }
        
        String sql = "INSERT INTO candidato (nome, idade, partido, numero_para_votar, sexo) VALUES (?, ?, ?, ?, ?)";

        try(Connection conn = getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, cand.get_nome());
            stmt.setInt(2, cand.get_idade());
            stmt.setString(3, cand.get_partido());
            stmt.setString(4, cand.get_numero_para_voto());
            stmt.setString(5, cand.get_sexo());

            stmt.executeUpdate();
            System.out.println("✅ Candidato salvo no MySQL: " + cand.get_nome());
        }catch (SQLException e) {
            System.out.println("❌ Erro ao salvar candidato: " + e.getMessage());
        }
    }

    public static void votar_no_db(String nome_cand){
        String sql = "UPDATE eleitor SET votou = true WHERE nome = ?";
        
        try (Connection conn = getConnection();
        
        PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, nome_cand);
        
        int linhasAfetadas = stmt.executeUpdate();
        
        if (linhasAfetadas > 0) {
            System.out.println("✅ Candidato '" + nome_cand + "' marcado como votou no MySQL");
        } else {
            System.out.println("⚠️  Candidato '" + nome_cand + "' não encontrado");
        }
        
    } catch (SQLException e) {
        System.out.println("❌ Erro ao marcar candidato como votou: " + e.getMessage());
        }

    }

    public static void salvar_voto(Voto voto){
        String sql = "INSERT INTO voto (reitor, vice) VALUES (?, ?)";
        try(Connection conn = getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, voto.get_reitor());
            stmt.setString(2, voto.get_vice());

            stmt.executeUpdate();
            System.out.println("✅ Voto salvo no MySQL:  + voto.get_id()");
        }catch (SQLException e){
            System.out.println("❌ Erro ao salvar voto: " + e.getMessage());
        }
    }

    public static void salvar_eleitor(Eleitor elei){
        String sql = "INSERT INTO eleitor (nome, idade, sexo, titulo) VALUES(?, ?, ?, ?)";

        try(Connection conn = getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, elei.get_nome());
            stmt.setInt(2, elei.get_idade());
            stmt.setString(3, elei.get_sexo());
            stmt.setString(4, elei.get_titulo());

            stmt.executeUpdate();
            System.out.println("✅ Eleitor salvo no MySQL: " + elei.get_nome());
        }catch (SQLException e) {
            System.out.println("❌ Erro ao salvar eleitor: " + e.getMessage());
        }
    }

    public static ArrayList<Candidato> carregar_candidatos(){
        ArrayList<Candidato> candidatos = new ArrayList<>();
        String sql = "SELECT * FROM candidato";
        
        try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
        
        while (rs.next()) {
            Candidato cand = new Candidato(
                rs.getString("nome"),
                rs.getInt("idade"),
                rs.getString("sexo"),
                rs.getString("partido"),
                rs.getString("numero_para_votar")
            );
            candidatos.add(cand);
        }
        System.out.println("✅ " + candidatos.size() + " candidatos carregados do MySQL");
        
           } catch (SQLException e) {
            System.out.println("❌ Erro ao buscar candidatos: " + e.getMessage());
        }
        return candidatos;
    }

    public static ArrayList<Voto> carregar_urna(){
        ArrayList<Voto> urna = new ArrayList<>();
        String sql = "SELECT * FROM voto";

        try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()){

            while(rs.next()){
                Voto vot = new Voto(
                    rs.getInt("id"),
                    rs.getString("reitor"),
                    rs.getString("vice")
                );
                urna.add(vot);
            }
            System.out.println("✅ " + urna.size() + " votos carregados do MySQL");
        }
        catch (SQLException e) {
            System.out.println("❌ Erro ao buscar votos: " + e.getMessage());
        }
        return urna;
    }

    public static ArrayList<Eleitor> carregar_eleitores(){
        ArrayList<Eleitor> eleitores = new ArrayList<>();
        String sql = "SELECT * FROM eleitor";
        
        try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
        
        while (rs.next()) {
            Eleitor elei = new Eleitor(
                rs.getString("nome"),
                rs.getInt("idade"),
                rs.getString("sexo"),
                rs.getString("titulo")
            );

            if (rs.getBoolean("votou")) {
                elei.votar();
            }

            eleitores.add(elei);
        }
        System.out.println("✅ " + eleitores.size() + " eleitores carregados do MySQL");
        
           } catch (SQLException e) {
            System.out.println("❌ Erro ao buscar eleitores: " + e.getMessage());
        }
        return eleitores;
    }
}
