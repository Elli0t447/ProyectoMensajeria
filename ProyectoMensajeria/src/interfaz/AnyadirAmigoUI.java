package interfaz;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import aplicacion.AnyadirAmigo;
import aplicacion.LoginUsuario;

import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.awt.Dialog.ModalityType;

public class AnyadirAmigoUI extends JDialog 
{
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JComboBox noAmigosBox;
	
	private AnyadirAmigo add;
	
	public JComboBox getNoAmigosBox() { return noAmigosBox; }

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AnyadirAmigoUI dialog = new AnyadirAmigoUI();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AnyadirAmigoUI() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("A\u00F1adir amigo");
		setIconImage(Toolkit.getDefaultToolkit().getImage(AnyadirAmigoUI.class.getResource("/img/anyadir.png")));
		setBounds(100, 100, 371, 257);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JPanel panel = new JPanel();
			panel.setBackground(new Color(65, 105, 225));
			panel.setBounds(0, 0, 365, 49);
			contentPanel.add(panel);
			panel.setLayout(null);
			
			JLabel lblNewLabel_1 = new JLabel("A\u00F1adir usuario");
			lblNewLabel_1.setForeground(new Color(255, 255, 255));
			lblNewLabel_1.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblNewLabel_1.setBounds(129, 7, 125, 34);
			panel.add(lblNewLabel_1);
		}
		
		noAmigosBox = new JComboBox();
		noAmigosBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		noAmigosBox.setRequestFocusEnabled(false);
		noAmigosBox.setBounds(128, 91, 152, 21);
		contentPanel.add(noAmigosBox);
		
		JLabel lblNewLabel = new JLabel("Usuario:");
		lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblNewLabel.setBounds(47, 92, 58, 13);
		contentPanel.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Enviar solicitud");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				add.enviarSolicitud(LoginUsuario.getIdUsuario(), LoginUsuario.idUserPorNombre((String) noAmigosBox.getSelectedItem()));
				dispose();
				JOptionPane.showMessageDialog(null, "Solicitud enviada!", "Solicitud", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBorderPainted(false);
		btnNewButton.setBackground(new Color(65, 105, 225));
		btnNewButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
		btnNewButton.setBounds(106, 162, 143, 38);
		contentPanel.add(btnNewButton);
		
		init();
	}
	
	private void init()
	{
		add = new AnyadirAmigo();
		add.rellenarBox(noAmigosBox);
	}
}
