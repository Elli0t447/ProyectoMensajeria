package interfaz;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Dialog.ModalityType;

public class InfoGruposUI extends JDialog 
{
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			InfoGruposUI dialog = new InfoGruposUI();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public InfoGruposUI() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setIconImage(Toolkit.getDefaultToolkit().getImage(InfoGruposUI.class.getResource("/img/chat.png")));
		setTitle("Informaci\u00F3n de " + PrincipalUI.getCurrentChatTitulo());
		setBounds(100, 100, 476, 445);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(65, 105, 225));
		panel.setBounds(0, 0, 469, 53);
		contentPanel.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nombre grupo");
		lblNewLabel.setBounds(10, 10, 130, 25);
		panel.add(lblNewLabel);
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
		
		JLabel lblNewLabel_3 = new JLabel("Fecha creaci\u00F3n: 20/09/2021");
		lblNewLabel_3.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblNewLabel_3.setForeground(new Color(255, 255, 255));
		lblNewLabel_3.setBounds(259, 21, 200, 13);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_1 = new JLabel("Participantes:");
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.PLAIN, 17));
		lblNewLabel_1.setBounds(16, 65, 105, 36);
		contentPanel.add(lblNewLabel_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(15, 103, 224, 297);
		contentPanel.add(panel_1);
		
		JLabel lblNewLabel_2 = new JLabel("<html>Descripcion larga del grupo lorem ipsum cositas sis snnono asdjajsaj</html>");
		lblNewLabel_2.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblNewLabel_2.setBounds(271, 103, 150, 293);
		contentPanel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_1_1 = new JLabel("Descripci\u00F3n:");
		lblNewLabel_1_1.setFont(new Font("Segoe UI", Font.PLAIN, 17));
		lblNewLabel_1_1.setBounds(271, 65, 96, 36);
		contentPanel.add(lblNewLabel_1_1);
	}
}
