package interfaz;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import aplicacion.LoginUsuario;
import dialogos.InfoGrupo;

import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.MatteBorder;
import javax.swing.ImageIcon;


public class InfoGrupoUI extends JDialog 
{
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JLabel fechaCreacion;
	private JLabel descripcionGroup;
	private JLabel nombreGrupo;
	private InfoGrupo infog;
	private static JPanel participantes;
	private JScrollPane scroll;
	
	public static JPanel getParticipantesContainer() { return participantes; }
 
	/**
	 * Create the dialog.
	 */
	public InfoGrupoUI() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setIconImage(Toolkit.getDefaultToolkit().getImage(InfoGrupoUI.class.getResource("/img/chat.png")));
		setTitle("Informaci\u00F3n de " + PrincipalUI.getCurrentChatTitulo());
		setBounds(100, 100, 476, 445);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JButton salirButton = new JButton("Salir del grupo");
		salirButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				int opcion = JOptionPane.showConfirmDialog(null, "¿Quieres salir del grupo?", "Salir", JOptionPane.YES_NO_OPTION);
				
				if (opcion == 0)
				{
					infog.salirGrupo(PrincipalUI.getCurrentChat(), LoginUsuario.getIdUsuario());
					dispose();
					
					// Mostrar de nuevo los chats del usuario
					PrincipalUI.container.removeAll();
					PrincipalUI.chatsU.mostrarListaChats(PrincipalUI.container);
					PrincipalUI.tab_chat.setVisible(false);
					PrincipalUI.tab_noChat.setVisible(true);
				}
				else
				{
					System.out.println("No se ha salido del grupo");
				}
			}
		});
		
		JButton salirButton_1 = new JButton("Invitar usuarios");
		salirButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				InvitarUsuarioUI invi = new InvitarUsuarioUI();
				invi.setVisible(true);
			}
		});
		salirButton_1.setForeground(Color.WHITE);
		salirButton_1.setFont(new Font("Segoe UI", Font.BOLD, 16));
		salirButton_1.setFocusPainted(false);
		salirButton_1.setBorderPainted(false);
		salirButton_1.setBackground(new Color(65, 105, 225));
		salirButton_1.setBounds(271, 280, 160, 53);
		contentPanel.add(salirButton_1);
		salirButton.setFocusPainted(false);
		salirButton.setBorderPainted(false);
		salirButton.setBackground(new Color(255, 0, 0));
		salirButton.setForeground(Color.WHITE);
		salirButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
		salirButton.setBounds(297, 357, 113, 30);
		contentPanel.add(salirButton);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(65, 105, 225));
		panel.setBounds(0, 0, 469, 53);
		contentPanel.add(panel);
		panel.setLayout(null);
		
		nombreGrupo = new JLabel("Nombre grupo");
		nombreGrupo.setBounds(10, 10, 213, 25);
		panel.add(nombreGrupo);
		nombreGrupo.setForeground(new Color(255, 255, 255));
		nombreGrupo.setFont(new Font("Segoe UI", Font.BOLD, 18));
		
		fechaCreacion = new JLabel("Fecha creaci\u00F3n: 20/09/2021");
		fechaCreacion.setFont(new Font("Segoe UI", Font.ITALIC, 16));
		fechaCreacion.setForeground(new Color(255, 255, 255));
		fechaCreacion.setBounds(233, 11, 236, 25);
		panel.add(fechaCreacion);
		
		JLabel labelParticip = new JLabel("Participantes:");
		labelParticip.setFont(new Font("Segoe UI", Font.PLAIN, 17));
		labelParticip.setBounds(16, 65, 105, 36);
		contentPanel.add(labelParticip);
		
		scroll = new JScrollPane();
		scroll.setSize(230, 300);
		scroll.setLocation(16, 100);
		scroll.setViewportBorder(null);
		
		scroll.setBorder(null);
		scroll.getVerticalScrollBar().setUnitIncrement(5);
		
		participantes = new JPanel();
		participantes.setBounds(15, 103, 224, 297);
		scroll.setViewportView(participantes);
		participantes.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setVisible(false);
		panel_1.setBackground(new Color(255, 250, 250));
		panel_1.setBounds(10, 10, 210, 34);
		participantes.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_4_1 = new JLabel("");
		lblNewLabel_4_1.setIcon(new ImageIcon(InfoGrupoUI.class.getResource("/img/borrar.png")));
		lblNewLabel_4_1.setBounds(186, 5, 18, 24);
		panel_1.add(lblNewLabel_4_1);
		
		JLabel lblNewLabel = new JLabel("elliot");
		lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 10, 35, 13);
		panel_1.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Admin");
		lblNewLabel_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 128, 0)));
		lblNewLabel_1.setForeground(new Color(0, 128, 0));
		lblNewLabel_1.setBounds(148, 11, 35, 13);
		panel_1.add(lblNewLabel_1);
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setVisible(false);
		panel_1_1.setBackground(new Color(255, 250, 250));
		panel_1_1.setBounds(10, 54, 210, 34);
		participantes.add(panel_1_1);
		panel_1_1.setLayout(null);
		
		JLabel lblLuis = new JLabel("luis");
		lblLuis.setBounds(10, 10, 29, 13);
		lblLuis.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		panel_1_1.add(lblLuis);
		
		JLabel lblNewLabel_1_1 = new JLabel("Admin");
		lblNewLabel_1_1.setForeground(new Color(0, 128, 0));
		lblNewLabel_1_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 128, 0)));
		lblNewLabel_1_1.setBounds(148, 11, 35, 13);
		panel_1_1.add(lblNewLabel_1_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setVisible(false);
		panel_2.setBackground(Color.WHITE);
		panel_2.setBounds(10, 98, 210, 34);
		participantes.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("maricarmenee");
		lblNewLabel_3.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblNewLabel_3.setBounds(10, 10, 96, 13);
		panel_2.add(lblNewLabel_3);
		contentPanel.add(scroll);
		
		descripcionGroup = new JLabel("<html>Descripcion larga del grupo lorem ipsum cositas ipsum lorem</html>");
		descripcionGroup.setVerticalAlignment(SwingConstants.TOP);
		descripcionGroup.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		descripcionGroup.setBounds(271, 103, 150, 293);
		contentPanel.add(descripcionGroup);
		
		JLabel labelDesc = new JLabel("Descripci\u00F3n:");
		labelDesc.setFont(new Font("Segoe UI", Font.PLAIN, 17));
		labelDesc.setBounds(271, 65, 96, 36);
		contentPanel.add(labelDesc);
		
		init();
	}
	
	private void init()
	{
		infog = new InfoGrupo();
		infog.mostrarInfoGrupo(nombreGrupo, fechaCreacion, descripcionGroup);
		infog.mostrarParticipantes(participantes);
	}
}
