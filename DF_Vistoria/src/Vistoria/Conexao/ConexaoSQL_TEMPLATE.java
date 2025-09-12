package Vistoria.Conexao;

//O arquivo original com suas credenciais deve ser ignorado pelo Git.
//Renomeie este arquivo para ConexaoSQL.java e preencha com suas credenciais.

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoSQL_TEMPLATE {

	// Substitua 'SUA_SENHA' e 'SEU_USUARIO' pelas suas credenciais
	private static final String URL = "jdbc:mysql://localhost:3306/sistema_vistoria_df";
	private static final String USER = "SEU_USUARIO";
	private static final String PASSWORD = "SUA_SENHA";

	public static Connection getConnection() {
		try {
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			return conn;
		} catch (SQLException e) {
			System.out.println("Erro ao conectar: " + e.getMessage());
			return null;
		}
	}

	// Método de teste da conexão
	public static void main(String[] args) {
		Connection conn = getConnection();
		if (conn != null) {
			System.out.println("Conexão realizada com sucesso!");
			try {
				conn.close();
				System.out.println("Conexão encerrada.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}