package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "jdbc:mysql://localhost:3306/locadora_db?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String SENHA = "34595583a";

    /**
     * Retorna uma conexão com o banco de dados MySQL.
     * 
     * @return Connection ativa com o banco.
     * @throws SQLException se ocorrer um erro na conexão.
     */
    public static Connection getConexao() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Força carregamento do driver (opcional em JDBC moderno)
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC não encontrado.");
            throw new SQLException("Erro ao carregar o driver JDBC.", e);
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            throw e;
        }
    }
}
