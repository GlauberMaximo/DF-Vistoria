package Vistoria;

import com.formdev.flatlaf.FlatIntelliJLaf;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import Vistoria.View.Login;

public class Main {

	public static void main(String[] args) {
		try {

			UIManager.setLookAndFeel(new FlatIntelliJLaf());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		java.awt.EventQueue.invokeLater(() -> {
			new Login().setVisible(true);
		});
	}
}
