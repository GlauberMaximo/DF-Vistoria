package Vistoria;

import com.formdev.flatlaf.FlatIntelliJLaf;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import Vistoria.View.Login;

public class Main {

	public static void main(String[] args) {
		try {
			// Define o tema FlatLaf (pode trocar por FlatDarkLaf, FlatIntelliJLaf,
			// FlatDarculaLaf)
			UIManager.setLookAndFeel(new FlatIntelliJLaf());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		// Inicia a aplicação com a tela de login
		java.awt.EventQueue.invokeLater(() -> {
			new Login().setVisible(true);
		});
	}
}
