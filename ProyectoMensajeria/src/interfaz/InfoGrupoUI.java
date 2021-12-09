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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;


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
	private static JButton adminButton;
	
	public static JButton getAdminButton() { return adminButton; }
	
	public static JPanel getParticipantesContainer() { return participantes; }
 
	/**
	 * Create the dialog.
	 */
	public InfoGrupoUI() 
	{
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
		salirButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				int opcion = JOptionPane.showConfirmDialog(null, "¿Quieres salir del grupo?", "Salir", JOptionPane.YES_NO_OPTION);
				
				ResultSet participantes = InfoGrupo.participantesGrupo(PrincipalUI.getCurrentChat());
				int count_participants = 0;
				
				ResultSet admins = InfoGrupo.adminsGrupo(PrincipalUI.getCurrentChat());
				int count_admins = 0;
				
				int id_usuNewAdmin = 0;
				
				try 
				{
					while (participantes.next())
					{
						count_participants++;
						id_usuNewAdmin = participantes.getInt("id_usuario");
					}
					
					while (admins.next())
					{
						count_admins++;
						System.out.println("Admin");
					}
					
					// Si hay mas de un participante, solo sale del grupo
					if (opcion == 0 && count_participants > 1)
					{
						if (count_admins > 1)
						{
							infog.salirIndividualGrupo(PrincipalUI.getCurrentChat(), LoginUsuario.getIdUsuario());
							dispose();
							
							// Mostrar de nuevo los chats del usuario
							PrincipalUI.container.removeAll();
							PrincipalUI.chatsU.mostrarListaChats(PrincipalUI.container);
							PrincipalUI.tab_chat.setVisible(false);
							PrincipalUI.tab_noChat.setVisible(true);
						}
						else
						{
							System.out.println("ye");
							// Salir del grupo
							infog.salirIndividualGrupo(PrincipalUI.getCurrentChat(), LoginUsuario.getIdUsuario());
							dispose();
							
							// Un usuario del grupo pasará a ser administrador
							infog.updateAdmin(id_usuNewAdmin, PrincipalUI.getCurrentChat(), true);				
							
							// Mostrar de nuevo los chats del usuario
							PrincipalUI.container.removeAll();
							PrincipalUI.chatsU.mostrarListaChats(PrincipalUI.container);
							PrincipalUI.tab_chat.setVisible(false);
							PrincipalUI.tab_noChat.setVisible(true);
						}					
					}
					// Si hay solo 1 participante (el usuario logueado), sale y borra del grupo
					else if (opcion == 0 && count_participants <= 1)
					{
						infog.salirFinalGrupo(PrincipalUI.getCurrentChat());
						dispose();
						JOptionPane.showMessageDialog(null, "Se ha borrado el grupo porque eras el último participante", "Salir", JOptionPane.INFORMATION_MESSAGE);
						
						// Mostrar de nuevo los chats del user
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
				catch (SQLException e1) 
				{
					System.out.println("Error SQL (salirButton - InfoGrupoUI)");
					e1.printStackTrace();
				}		
			}
		});
		
		JButton invitarButton = new JButton("Invitar usuarios");
		invitarButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				InvitarUsuarioUI invi = new InvitarUsuarioUI();
				invi.setVisible(true);
			}
		});
		invitarButton.setForeground(Color.WHITE);
		invitarButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
		invitarButton.setFocusPainted(false);
		invitarButton.setBorderPainted(false);
		invitarButton.setBackground(new Color(65, 105, 225));
		invitarButton.setBounds(271, 280, 160, 53);
		contentPanel.add(invitarButton);
		salirButton.setFocusPainted(false);
		salirButton.setBorderPainted(false);
		salirButton.setBackground(new Color(255, 0, 0));
		salirButton.setForeground(Color.WHITE);
		salirButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
		salirButton.setBounds(297, 357, 113, 30);
		contentPanel.add(salirButton);
		
		JPanel panelBG = new JPanel();
		panelBG.setBackground(new Color(65, 105, 225));
		panelBG.setBounds(0, 0, 469, 53);
		contentPanel.add(panelBG);
		panelBG.setLayout(null);
		
		nombreGrupo = new JLabel("Nombre grupo");
		nombreGrupo.setBounds(9, 13, 213, 25);
		panelBG.add(nombreGrupo);
		nombreGrupo.setForeground(new Color(255, 255, 255));
		nombreGrupo.setFont(new Font("Segoe UI", Font.BOLD, 18));
		
		fechaCreacion = new JLabel("Fecha creaci\u00F3n: 20/09/2021");
		fechaCreacion.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		fechaCreacion.setForeground(new Color(255, 255, 255));
		fechaCreacion.setBounds(250, 15, 236, 25);
		panelBG.add(fechaCreacion);
		
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
		contentPanel.add(scroll);
		
		descripcionGroup = new JLabel("<html>Descripcion larga del grupo lorem ipsum cositas ipsum lorem</html>");
		descripcionGroup.setVerticalAlignment(SwingConstants.TOP);
		descripcionGroup.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		descripcionGroup.setBounds(271, 103, 150, 102);
		contentPanel.add(descripcionGroup);
		
		JLabel labelDesc = new JLabel("Descripci\u00F3n:");
		labelDesc.setFont(new Font("Segoe UI", Font.PLAIN, 17));
		labelDesc.setBounds(271, 65, 96, 36);
		contentPanel.add(labelDesc);
		
		adminButton = new JButton("Cambiar admins");
		if (LoginUsuario.isAdmin(LoginUsuario.getIdUsuario(), PrincipalUI.getCurrentChat()))
		{
			adminButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) 
				{
					infog.actualizarAdmins();
					
					InfoGrupoUI.getParticipantesContainer().removeAll();
					InfoGrupo info = new InfoGrupo();
					info.mostrarParticipantes(InfoGrupoUI.getParticipantesContainer());
					adminButton.setVisible(false);
				}
			});
			adminButton.setForeground(Color.WHITE);
			adminButton.setBackground(new Color(0, 128, 0));
			adminButton.setBorderPainted(false);
			adminButton.setVisible(false);
			adminButton.setFocusPainted(false);
			adminButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
			adminButton.setBounds(271, 226, 160, 36);
			contentPanel.add(adminButton);
		}
		
		init();
	}
	
	private void init()
	{
		infog = new InfoGrupo();
		infog.mostrarInfoGrupo(nombreGrupo, fechaCreacion, descripcionGroup);
		infog.mostrarParticipantes(participantes);
	}
}
