package interfaz;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;


public class InfoGruposUI extends JDialog 
{
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

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
		
		JLabel nombreGrupo = new JLabel("Nombre grupo");
		nombreGrupo.setBounds(10, 10, 130, 25);
		panel.add(nombreGrupo);
		nombreGrupo.setForeground(new Color(255, 255, 255));
		nombreGrupo.setFont(new Font("Segoe UI", Font.BOLD, 18));
		
		JLabel fechaCreacion = new JLabel("Fecha creaci\u00F3n: 20/09/2021");
		fechaCreacion.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		fechaCreacion.setForeground(new Color(255, 255, 255));
		fechaCreacion.setBounds(259, 21, 200, 13);
		panel.add(fechaCreacion);
		
		JLabel labelParticip = new JLabel("Participantes:");
		labelParticip.setFont(new Font("Segoe UI", Font.PLAIN, 17));
		labelParticip.setBounds(16, 65, 105, 36);
		contentPanel.add(labelParticip);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(15, 103, 224, 297);
		contentPanel.add(panel_1);
		
		JLabel descripcionGroup = new JLabel("<html>Descripcion larga del grupo lorem ipsum cositas ipsum lorem</html>");
		descripcionGroup.setVerticalAlignment(SwingConstants.TOP);
		descripcionGroup.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		descripcionGroup.setBounds(271, 103, 150, 293);
		contentPanel.add(descripcionGroup);
		
		JLabel labelDesc = new JLabel("Descripci\u00F3n:");
		labelDesc.setFont(new Font("Segoe UI", Font.PLAIN, 17));
		labelDesc.setBounds(271, 65, 96, 36);
		contentPanel.add(labelDesc);
	}
}
