package vista;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

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
import javax.swing.border.MatteBorder;

import controladorDialogs.InfoGrupo;
import controladorMain.ChatsUsuario;
import controladorMain.LoginUsuario;

public class InfoGrupoUI extends JDialog 
{
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	
	// Objeto de la clase del funcionamiento
	private InfoGrupo infog;
	
	// Labels de la informacion del grupo
	private JLabel fechaCreacion;
	private JLabel descripcionGroup;
	private JLabel nombreGrupo;
	
	// Scroll de la lista de participantes	
	private JScrollPane scroll;
	
	// Boton para cambiar de admins
	private static JButton adminButton;
	
	// Contenedor de participantes del grupo
	private static JPanel participantes;
	
	public static JButton getAdminButton() { return adminButton; }
	public static JPanel getParticipantesContainer() { return participantes; }
 
	/**
	 * Create the dialog.
	 */
	public InfoGrupoUI() 
	{
		setModalityType(ModalityType.APPLICATION_MODAL);
		setIconImage(Toolkit.getDefaultToolkit().getImage(InfoGrupoUI.class.getResource("/img/chat.png")));
		setTitle("Informaci\u00F3n de " + PrincipalUI.currentChatTitulo);
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
				ChatsUsuario chatu = new ChatsUsuario();
				int opcion = JOptionPane.showConfirmDialog(null, "¿Quieres salir del grupo?", "Salir", JOptionPane.YES_NO_OPTION);
				
				ResultSet rsParticipantes = InfoGrupo.participantesGrupo(ChatsUsuario.getCurrentChat());
				int count_participants = 0;
				
				ResultSet rsAdmins = InfoGrupo.adminsGrupo(ChatsUsuario.getCurrentChat());
				int count_admins = 0;
				
				// El usuario que sera nuevo administrador si sale el admin y no hay otro
				int id_usuNewAdmin = 0;
				
				try 
				{
					while (rsParticipantes.next())
					{
						count_participants++;
						id_usuNewAdmin = rsParticipantes.getInt("id_usuario");
					}
					
					while (rsAdmins.next())
					{
						count_admins++;
					}
					
					
					
					// Si hay mas de un participante, solo sale del grupo
					if (opcion == 0 && count_participants > 1)
					{
						
						
						if (count_admins > 1)
						{
							infog.removeParticipante(ChatsUsuario.getCurrentChat(), LoginUsuario.getIdUsuarioConectado());
							dispose();
							
							// Mostrar de nuevo los chats del usuario
							PrincipalUI.container.removeAll();
							chatu.mostrarListaChats(PrincipalUI.container);
							PrincipalUI.tab_chat.setVisible(false);
							PrincipalUI.tab_noChat.setVisible(true);
						}
						else
						{
							// Salir del grupo
							infog.removeParticipante(ChatsUsuario.getCurrentChat(), LoginUsuario.getIdUsuarioConectado());
							dispose();
							
							// El ultimo usuario del grupo pasará a ser administrador
							infog.updateAdmin(id_usuNewAdmin, ChatsUsuario.getCurrentChat(), true);				
							
							// Mostrar de nuevo los chats del usuario
							PrincipalUI.container.removeAll();
							chatu.mostrarListaChats(PrincipalUI.container);
							PrincipalUI.tab_chat.setVisible(false);
							PrincipalUI.tab_noChat.setVisible(true);
						}					
					}
					// Si hay solo 1 participante (el usuario logueado), sale y borra del grupo
					else if (opcion == 0 && count_participants <= 1)
					{
						infog.salirUltimoEnGrupo(ChatsUsuario.getCurrentChat());
						dispose();
						JOptionPane.showMessageDialog(null, "Se ha borrado el grupo porque eras el último participante", "Salir", JOptionPane.INFORMATION_MESSAGE);
						
						// Mostrar de nuevo los chats del user
						PrincipalUI.container.removeAll();
						chatu.mostrarListaChats(PrincipalUI.container);
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
				// Abre el dialogo para invitar usuarios
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
		
		JLabel labelParticip = new JLabel("Participantes");
		labelParticip.setBorder(new MatteBorder(0, 0, 4, 0, (Color) new Color(65, 105, 225)));
		labelParticip.setFont(new Font("Segoe UI", Font.PLAIN, 17));
		labelParticip.setBounds(16, 65, 105, 36);
		contentPanel.add(labelParticip);
		
		scroll = new JScrollPane();
		scroll.setSize(230, 290);
		scroll.setLocation(16, 110);
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
		descripcionGroup.setBounds(271, 111, 150, 102);
		contentPanel.add(descripcionGroup);
		
		JLabel labelDesc = new JLabel("Descripci\u00F3n");
		labelDesc.setBorder(new MatteBorder(0, 0, 4, 0, (Color) new Color(65, 105, 225)));
		labelDesc.setFont(new Font("Segoe UI", Font.PLAIN, 17));
		labelDesc.setBounds(271, 73, 96, 28);
		contentPanel.add(labelDesc);
			
		if (LoginUsuario.isAdmin(LoginUsuario.getIdUsuarioConectado(), ChatsUsuario.getCurrentChat()))
		{
			adminButton = new JButton("Cambiar admins");
			adminButton.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent e) 
				{
					// Actualiza los admins
					infog.actualizarAdmins();
					
					// Recarga los participantes del grupo y hace invisible el boton 
					InfoGrupoUI.getParticipantesContainer().removeAll();
					InfoGrupo info = new InfoGrupo(nombreGrupo, fechaCreacion, descripcionGroup);
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
	
	// Inicializa las variables de la clase y el contenido de la interfaz
	private void init()
	{
		infog = new InfoGrupo(nombreGrupo, fechaCreacion, descripcionGroup);
		infog.mostrarInfoGrupo();
		infog.mostrarParticipantes(participantes);
	}
}
