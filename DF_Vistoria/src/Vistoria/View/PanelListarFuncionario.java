package Vistoria.View;

import Vistoria.Controller.FuncionarioController;
import Vistoria.Model.Funcionario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class PanelListarFuncionario extends JPanel {

    private static final long serialVersionUID = 1L;

    private JTable table;
    private DefaultTableModel tableModel;
    private FuncionarioController funcionarioController;

    public PanelListarFuncionario() {
        this.funcionarioController = new FuncionarioController();

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Lista de Funcionários", JLabel.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        add(titulo, BorderLayout.NORTH);

        // Modelo da tabela
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Matrícula", "Email", "Cargo"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // impede edição direta
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Botões
        JPanel panelButtons = new JPanel();
        panelButtons.setBackground(Color.WHITE);

        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnAlterar = new JButton("Alterar");
        JButton btnExcluir = new JButton("Excluir");

        btnAtualizar.setBackground(new Color(70, 130, 180));
        btnAtualizar.setForeground(Color.WHITE);

        btnAlterar.setBackground(new Color(255, 165, 0));
        btnAlterar.setForeground(Color.WHITE);

        btnExcluir.setBackground(new Color(220, 20, 60));
        btnExcluir.setForeground(Color.WHITE);

        panelButtons.add(btnAtualizar);
        panelButtons.add(btnAlterar);
        panelButtons.add(btnExcluir);
        add(panelButtons, BorderLayout.SOUTH);

        // Eventos
        btnAtualizar.addActionListener(e -> carregarFuncionarios());
        btnAlterar.addActionListener(this::alterarFuncionario);
        btnExcluir.addActionListener(this::excluirFuncionario);

        // Carrega ao abrir
        carregarFuncionarios();
    }

    private void carregarFuncionarios() {
        tableModel.setRowCount(0); // limpa tabela
        List<Funcionario> funcionarios = funcionarioController.listarTodos();

        for (Funcionario f : funcionarios) {
            tableModel.addRow(new Object[]{
                    f.getIdFuncionario(),
                    f.getNome(),
                    f.getMatricula(),
                    f.getEmail(),
                    f.getCargo()
            });
        }
    }

    private void alterarFuncionario(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um funcionário para alterar.");
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        Funcionario funcionario = funcionarioController.buscarPorId(id);

        if (funcionario == null) return;

        JTextField txtNome = new JTextField(funcionario.getNome());
        JTextField txtEmail = new JTextField(funcionario.getEmail());
        JPasswordField txtSenha = new JPasswordField(funcionario.getSenha());

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.add(new JLabel("Nome:"));
        panel.add(txtNome);
        panel.add(new JLabel("Email:"));
        panel.add(txtEmail);
        panel.add(new JLabel("Senha:"));
        panel.add(txtSenha);

        int result = JOptionPane.showConfirmDialog(this, panel, "Alterar Funcionário",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            funcionario.setNome(txtNome.getText().trim());
            funcionario.setEmail(txtEmail.getText().trim());
            funcionario.setSenha(new String(txtSenha.getPassword()).trim());

            funcionarioController.atualizarFuncionario(funcionario);
            JOptionPane.showMessageDialog(this, "Funcionário atualizado com sucesso!");
            carregarFuncionarios();
        }
    }

    private void excluirFuncionario(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um funcionário para excluir.");
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String nome = (String) tableModel.getValueAt(selectedRow, 1);

        String motivo = JOptionPane.showInputDialog(this,
                "Informe o motivo do desligamento de " + nome + ":\n(Ex: Justa Causa, Pedido de Demissão, etc.)");

        if (motivo == null || motivo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "É necessário informar um motivo para o desligamento.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Deseja realmente desligar " + nome + "?\nMotivo: " + motivo,
                "Confirmação", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            // Registrar desligamento
            Vistoria.Model.Desligamento desligamento = new Vistoria.Model.Desligamento();
            desligamento.setIdFuncionario(id);
            desligamento.setNomeFuncionario(nome);
            desligamento.setMotivo(motivo);

            new Vistoria.DAO.DesligamentoDAO().registrar(desligamento);

            // Excluir funcionário
            funcionarioController.deletarFuncionario(id);
            JOptionPane.showMessageDialog(this, "Funcionário desligado com sucesso!\nMotivo: " + motivo);
            carregarFuncionarios();
        }
    
    }
}
