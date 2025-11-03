import java.sql.*;

public class Gerenciador_db {
    static {
        try {
            // ✅ REGISTRA O DRIVER MANUALMENTE
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Driver MySQL não encontrado!");
            e.printStackTrace();
        }
    }

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
            stmt.setString(3, cand.get_sexo());
            stmt.setString(4, cand.get_partido());
            stmt.setString(5, cand.get_numero_para_voto());

            stmt.executeUpdate();
            System.out.println("✅ Candidato salvo no MySQL: " + cand.get_nome());
        }catch (SQLException e) {
            System.out.println("❌ Erro ao salvar candidato: " + e.getMessage());
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
}
