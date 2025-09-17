package Vistoria.DAO;

import Vistoria.Model.Vistoria;
import Vistoria.Model.ResultadoVistoria;
import Vistoria.Conexao.ConexaoSQL;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VistoriaDAO {

    // CREATE - inserir nova vistoria
    public void inserir(Vistoria vistoria) {
        String sql = "INSERT INTO vistoria (data_vistoria, resultado, observacoes, idAgendamento, idFuncionario) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setDate(1, Date.valueOf(vistoria.getDataVistoria()));
            stmt.setString(2, mapResultadoToDB(vistoria.getResultado()));
            stmt.setString(3, vistoria.getObservacoes());
            stmt.setInt(4, vistoria.getIdAgendamento());
            stmt.setInt(5, vistoria.getIdFuncionario());

            stmt.executeUpdate();

            // Recupera o ID gerado
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    vistoria.setIdVistoria(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao inserir vistoria: " + e.getMessage());
        }
    }

    // READ - buscar por ID
    public Vistoria buscarPorId(int id) {
        String sql = "SELECT * FROM vistoria WHERE idVistoria = ?";
        Vistoria vistoria = null;

        try (Connection conn = ConexaoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    vistoria = mapResultSetToVistoria(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar vistoria: " + e.getMessage());
        }
        return vistoria;
    }

    // READ - listar todas
    public List<Vistoria> listarTodas() {
        String sql = "SELECT * FROM vistoria";
        List<Vistoria> vistorias = new ArrayList<>();

        try (Connection conn = ConexaoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                vistorias.add(mapResultSetToVistoria(rs));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar vistorias: " + e.getMessage());
        }
        return vistorias;
    }

    // UPDATE - atualizar
    public void atualizar(Vistoria vistoria) {
        String sql = "UPDATE vistoria SET data_vistoria = ?, resultado = ?, observacoes = ?, "
                   + "idAgendamento = ?, idFuncionario = ? WHERE idVistoria = ?";

        try (Connection conn = ConexaoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(vistoria.getDataVistoria()));
            stmt.setString(2, mapResultadoToDB(vistoria.getResultado()));
            stmt.setString(3, vistoria.getObservacoes());
            stmt.setInt(4, vistoria.getIdAgendamento());
            stmt.setInt(5, vistoria.getIdFuncionario());
            stmt.setInt(6, vistoria.getIdVistoria());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar vistoria: " + e.getMessage());
        }
    }

    // DELETE - remover
    public void deletar(int id) {
        String sql = "DELETE FROM vistoria WHERE idVistoria = ?";

        try (Connection conn = ConexaoSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao deletar vistoria: " + e.getMessage());
        }
    }

    // === MÉTODOS AUXILIARES ===

    // Converte ResultSet -> Objeto Vistoria
    private Vistoria mapResultSetToVistoria(ResultSet rs) throws SQLException {
        int idVistoria = rs.getInt("idVistoria");
        LocalDate dataVistoria = rs.getDate("data_vistoria").toLocalDate();
        ResultadoVistoria resultado = mapResultadoFromDB(rs.getString("resultado"));
        String observacoes = rs.getString("observacoes");
        int idAgendamento = rs.getInt("idAgendamento");
        int idFuncionario = rs.getInt("idFuncionario");

        return new Vistoria(idVistoria, dataVistoria, resultado, observacoes, idAgendamento, idFuncionario);
    }

    // Mapeia enum Java -> String do banco
    private String mapResultadoToDB(ResultadoVistoria resultado) {
        switch (resultado) {
            case APROVADO: return "Aprovado";
            case REPROVADO: return "Reprovado";
            case APROVADO_COM_RESSALVAS: return "Aprovado com ressalvas";
            default: throw new IllegalArgumentException("Resultado inválido: " + resultado);
        }
    }

    // Mapeia String do banco -> enum Java
    private ResultadoVistoria mapResultadoFromDB(String valor) {
        switch (valor) {
            case "Aprovado": return ResultadoVistoria.APROVADO;
            case "Reprovado": return ResultadoVistoria.REPROVADO;
            case "Aprovado com ressalvas": return ResultadoVistoria.APROVADO_COM_RESSALVAS;
            default: throw new IllegalArgumentException("Valor inválido no banco: " + valor);
        }
    }
}
