package Vistoria.DAO;

import Vistoria.Model.Laudo;
import Vistoria.Model.Vistoria;
import Vistoria.Conexao.ConexaoSQL;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LaudoDAO {

    public void criarLaudo(Laudo laudo) {
        String sql = "INSERT INTO laudo (caminho_arquivo, data_geracao, idVistoria) VALUES (?, ?, ?)";

        try (Connection conn = ConexaoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, laudo.getCaminhoArquivo());
            stmt.setTimestamp(2, Timestamp.valueOf(laudo.getDataGeracao()));
            stmt.setInt(3, laudo.getIdVistoria());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        laudo.setIdLaudo(rs.getInt(1));
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao criar laudo: " + e.getMessage());
        }
    }
    
    public Laudo buscarLaudoPorIdVistoria(int idVistoria) {
        String sql = "SELECT * FROM laudo WHERE idVistoria = ?";
        Laudo laudo = null;

        try (Connection conn = ConexaoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idVistoria);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    laudo = new Laudo();
                    laudo.setIdLaudo(rs.getInt("idLaudo"));
                    laudo.setCaminhoArquivo(rs.getString("caminho_arquivo"));
                    laudo.setDataGeracao(rs.getTimestamp("data_geracao").toLocalDateTime());
                    laudo.setIdVistoria(rs.getInt("idVistoria"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar laudo: " + e.getMessage());
        }
        return laudo;
    }
    
    public List<Laudo> buscarLaudosPorIdCliente(int idCliente) {
        String sql = "SELECT l.* FROM laudo l " +
                     "JOIN vistoria v ON l.idVistoria = v.idVistoria " +
                     "JOIN agendamento a ON v.idAgendamento = a.idAgendamento " +
                     "WHERE a.idCliente = ?";
        
        List<Laudo> laudos = new ArrayList<>();

        try (Connection conn = ConexaoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Laudo laudo = new Laudo();
                    laudo.setIdLaudo(rs.getInt("idLaudo"));
                    laudo.setCaminhoArquivo(rs.getString("caminho_arquivo"));
                    laudo.setDataGeracao(rs.getTimestamp("data_geracao").toLocalDateTime());
                    laudo.setIdVistoria(rs.getInt("idVistoria"));
                    laudos.add(laudo);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar laudos do cliente: " + e.getMessage());
        }
        return laudos;
    }
}