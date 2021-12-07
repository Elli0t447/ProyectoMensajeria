package interfaz;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

import javax.swing.border.MatteBorder;

import aplicacion.AmigosUsuario;
import aplicacion.ChatsUsuario;
import aplicacion.LoginUsuario;
import aplicacion.MensajesChat;
import dialogos.AjustesUsuario;

import javax.swing.SwingConstants;

public class PrincipalUI extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public static ChatsUsuario chatsU;
	public static MensajesChat mC;
	
	public static JPanel tab_chat;
	public static JPanel tab_noChat;
	public static JLabel descripcionChat;
	private JPanel barChat;
	public static JPanel container;
	
	private JLabel nochatLabel;
	private JLabel noChatDesc;
	
	public static JLabel nombreChat;
	public static JPanel containerMsj;
	
	private static JScrollPane scrollChat;
	public static JScrollPane scrollMensaje;
	private static JScrollPane scrollPeticion;
	private static JScrollPane scrollAmigos;
	
	private static int chatEnvio;
	private static boolean currentAdministra;
	private static String currentChatTitulo;
	private static String currentChatDesc;
	public static JPanel tab_amigos;
	private JLabel lblAmigos;
	private JLabel lblAjustes_1;
	public static JLabel usuarioLabelAjustes;
	private JLabel lblNombre;
	private JPanel panelito;
	private JLabel descrip;
	private JLabel lblContrasea;
	public static JTextField nombreField;
	public static JTextField passwordField;
	public static JLabel iDlabel;
	private JButton aplicarCambios;
	public static JLabel descAmigo;
	public static JLabel descPeti;
	public static JPanel amigoContainer;
	public static JPanel peticionesContainer;
	
	public static AmigosUsuario amics;
	private static AjustesUsuario ajustesUsu;
	public static JPanel bg_chat;
	public static JPanel chatOption;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	
	public static void setCurrentChatTitulo(String c) { currentChatTitulo = c;}
	public static void setCurrentChatDesc(String d) { currentChatDesc = d;}
	
	public static String getCurrentChatTitulo() { return currentChatTitulo; }
	public static String getCurrentChatDesc() { return currentChatDesc; }
	
	public static void setCurrentAdministra(boolean a) { currentAdministra = a;}
	public static boolean getCurrentAdministra() { return currentAdministra; }
	
	public static void setChatEnvio(int c) { chatEnvio = c;}
	public static int getCurrentChat() { return chatEnvio; }

	/**
	 * Create the frame.
	 */
	public PrincipalUI() 
	{
		mC = new MensajesChat();
		ajustesUsu = new AjustesUsuario();
		
		setResizable(false);
		setTitle("Mensajer�a @" + LoginUI.login.getUsuario());
		try 
		{
			 // Definir icono de la aplicaci�n
		     ImageIcon programIcon = new ImageIcon(PrincipalUI.class.getResource("/img/chat.png"));
		     setIconImage(programIcon.getImage());
		 }
		 catch (Exception whoJackedMyIcon) 
		 {
		    System.out.println("Error icono no encontrado");
		 }
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 860, 517);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tab_amigos = new JPanel();
		tab_amigos.setVisible(false);
		
		JPanel tab_ajustes = new JPanel();
		tab_ajustes.setBackground(Color.WHITE);
		tab_ajustes.setVisible(false);
		
		tab_chat = new JPanel();
		tab_chat.setVisible(false);
		tab_chat.setBorder(null);
		tab_chat.setBackground(Color.WHITE);
		tab_chat.setBounds(251, 0, 608, 489);
		contentPane.add(tab_chat);
		tab_chat.setLayout(null);
		
		bg_chat = new JPanel();
		bg_chat.setBackground(new Color(65, 105, 225));
		bg_chat.setBounds(0, 0, 652, 60);
		tab_chat.add(bg_chat);
		bg_chat.setLayout(null);
		
		descripcionChat = new JLabel("descripcionChat");
		descripcionChat.setBounds(10, 30, (int)descripcionChat.getPreferredSize().getWidth() + 10, 21);
		bg_chat.add(descripcionChat);
		descripcionChat.setForeground(new Color(211, 211, 211));
		descripcionChat.setFont(new Font("Segoe UI", Font.BOLD, 15));
		
		nombreChat = new JLabel("id_chat / nombrechat");
		nombreChat.setBounds(10, 4, (int)nombreChat.getPreferredSize().getWidth() + 10, 33);
		bg_chat.add(nombreChat);
		nombreChat.setForeground(new Color(255, 255, 255));
		nombreChat.setFont(new Font("Segoe UI", Font.BOLD, 18));
		
		chatOption = new JPanel();
		chatOption.setBackground(new Color(65, 105, 225));
		chatOption.setBounds(380, 4, 215, 47);
		bg_chat.add(chatOption);
		chatOption.setLayout(null);
		
		lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(PrincipalUI.class.getResource("/img/info.png")));
		lblNewLabel_1.setBounds(157, 8, 39, 37);
		chatOption.add(lblNewLabel_1);
		
		lblNewLabel = new JLabel("Amigos desde: 2021/06/05");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblNewLabel.setBounds(10, 10, 201, 27);
		chatOption.add(lblNewLabel);
		
		containerMsj = new JPanel();
		containerMsj.setBorder(null);
		containerMsj.setBackground(Color.WHITE);
		containerMsj.setBounds(12, 64, 586, 356);
		containerMsj.setLayout(null);
		
		scrollMensaje = new JScrollPane();
		scrollMensaje.setBounds(10, 60, 588, 368);
		scrollMensaje.getVerticalScrollBar().setUnitIncrement(7);
		scrollMensaje.setBorder(null);
		scrollMensaje.setViewportBorder(null);
		scrollMensaje.setViewportView(containerMsj);
		tab_chat.add(scrollMensaje);
		tab_ajustes.setBounds(65, 0, 791, 489);
		contentPane.add(tab_ajustes);
		tab_ajustes.setLayout(null);
		
		usuarioLabelAjustes = new JLabel("USUARIO");
		usuarioLabelAjustes.setForeground(new Color(65, 105, 225));
		usuarioLabelAjustes.setFont(new Font("Segoe UI", Font.BOLD, 35));
		usuarioLabelAjustes.setBounds(191, 10, 221, 47);
		tab_ajustes.add(usuarioLabelAjustes);
		
		lblNombre = new JLabel("Nombre:");
		lblNombre.setForeground(new Color(0, 0, 0));
		lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblNombre.setBounds(230, 88, 66, 32);
		tab_ajustes.add(lblNombre);
		
		panelito = new JPanel();
		panelito.setBackground(new Color(255, 250, 250));
		panelito.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(227, 227, 227)));
		panelito.setBounds(0, 0, 181, 489);
		tab_ajustes.add(panelito);
		panelito.setLayout(null);
		
		lblAjustes_1 = new JLabel("Ajustes");
		lblAjustes_1.setBounds(16, 10, 80, 34);
		panelito.add(lblAjustes_1);
		lblAjustes_1.setFont(new Font("Segoe UI", Font.BOLD, 19));
		lblAjustes_1.setBorder(new MatteBorder(0, 0, 4, 0, (Color) new Color(65, 105, 225)));
		
		descrip = new JLabel("<html>Cambia los ajustes de tu usuario</html>");
		descrip.setForeground(Color.DARK_GRAY);
		descrip.setBounds(16, 52, 103, 45);
		panelito.add(descrip);
		descrip.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		
		lblContrasea = new JLabel("Contrase\u00F1a:");
		lblContrasea.setForeground(Color.BLACK);
		lblContrasea.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblContrasea.setBounds(215, 134, 94, 47);
		tab_ajustes.add(lblContrasea);
		
		nombreField = new JTextField();
		nombreField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		nombreField.setBounds(319, 96, 420, 24);
		tab_ajustes.add(nombreField);
		nombreField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		passwordField.setColumns(10);
		passwordField.setBounds(319, 149, 420, 24);
		tab_ajustes.add(passwordField);
		
		aplicarCambios = new JButton("Aplicar cambios");
		aplicarCambios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				
				/*
				 * Busca y introduce todos los usuarios en la variable "rs" para comprobar si el cambio de nombre
				 * no cumple la restricci�n de integridad 'UNICO NOMBRE'.
				 * 
				 * No dejara cambiar el nombre al usuario si ya existe uno as�.
				 * */
				
				ResultSet rs = LoginUsuario.allNomUsuarios();

				try 
				{
					while(rs.next())
					{
						if (!nombreField.getText().equals(rs.getString("nombre")) || nombreField.getText().equals(LoginUsuario.nombreUserPorId(LoginUsuario.getIdUsuario())))
						{
							ajustesUsu.actualizarUsuario(nombreField.getText(), passwordField.getText(), LoginUsuario.getIdUsuario());
							usuarioLabelAjustes.setText(nombreField.getText());
						}
						else
						{
							System.out.println("Error nombre de usuario ya existente!!");
						}
					}
					
					System.out.println("Usuario actualizado!");
				} 
				catch (SQLException e1) 
				{
					System.out.println("Error de SQL");
					e1.printStackTrace();
				}
			}
		});
		aplicarCambios.setBorderPainted(false);
		aplicarCambios.setBackground(new Color(65, 105, 225));
		aplicarCambios.setForeground(Color.WHITE);
		aplicarCambios.setFont(new Font("Segoe UI", Font.BOLD, 16));
		aplicarCambios.setBounds(211, 202, 153, 47);
		tab_ajustes.add(aplicarCambios);
		
		iDlabel = new JLabel("(ID 1)");
		iDlabel.setForeground(Color.DARK_GRAY);
		iDlabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		iDlabel.setBounds(201, 53, 47, 24);
		tab_ajustes.add(iDlabel);
		
		JButton borrarUsuario = new JButton("BORRAR USUARIO");
		borrarUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				
				int opcion = JOptionPane.showConfirmDialog(null, "�Est�s seguro de querer borrar tu usuario? (esta acci�n no se puede deshacer)", "ATENCI�N", JOptionPane.YES_NO_OPTION);
				
				if (opcion == 0)
				{
					ajustesUsu.borrarUsuario(LoginUsuario.getIdUsuario());
					dispose();
					new LoginUI().setVisible(true);
				}
				else
				{
					System.out.println("Usuario no borrado");
				}
				
			}
		});
		borrarUsuario.setBorderPainted(false);
		borrarUsuario.setBackground(Color.RED);
		borrarUsuario.setForeground(Color.WHITE);
		borrarUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		borrarUsuario.setBounds(633, 443, 143, 32);
		tab_ajustes.add(borrarUsuario);
		tab_amigos.setLayout(null);
		tab_amigos.setBorder(null);
		tab_amigos.setBackground(new Color(245, 245, 245));
		tab_amigos.setBounds(65, 0, 791, 489);
		contentPane.add(tab_amigos);
		
		descAmigo = new JLabel("<html>No tienes amigos, a�ade a algunos para conversar</html>");
		descAmigo.setForeground(Color.GRAY);
		descAmigo.setVisible(true);
		descAmigo.setFont(new Font("Segoe UI", Font.BOLD, 16));
		descAmigo.setBounds(10, 54, 179, 63);
		tab_amigos.add(descAmigo);
		
		lblAmigos = new JLabel("Amigos");
		lblAmigos.setFont(new Font("Segoe UI", Font.BOLD, 19));
		lblAmigos.setBorder(new MatteBorder(0, 0, 4, 0, (Color) new Color(65, 105, 225)));
		lblAmigos.setBounds(10, 10, 84, 34);
		tab_amigos.add(lblAmigos);
		
		JPanel peticiones = new JPanel();
		peticiones.setBorder(new MatteBorder(0, 1, 0, 0, (Color) Color.LIGHT_GRAY));
		peticiones.setBackground(Color.WHITE);
		peticiones.setBounds(355, 0, 436, 489);
		tab_amigos.add(peticiones);
		peticiones.setLayout(null);
		
		JLabel lblPeticiones = new JLabel("Peticiones de amistad");
		lblPeticiones.setBounds(104, 15, 210, 30);
		lblPeticiones.setFont(new Font("Segoe UI", Font.BOLD, 19));
		lblPeticiones.setBorder(new MatteBorder(0, 0, 4, 0, (Color) new Color(65, 105, 225)));
		peticiones.add(lblPeticiones);
		
		descPeti = new JLabel("<html>Ahora mismo no tienes peticiones de amistad, espera a recibir una</html>");
		descPeti.setForeground(Color.GRAY);
		descPeti.setVisible(true);
		descPeti.setFont(new Font("Segoe UI", Font.BOLD, 16));
		descPeti.setBounds(85, 102, 273, 50);
		peticiones.add(descPeti);
		
		scrollPeticion = new JScrollPane();
		scrollPeticion.setSize(418, 409);
		scrollPeticion.setLocation(10, 70);
		scrollPeticion.setViewportBorder(null);
		scrollPeticion.setBorder(null);
		scrollPeticion.getVerticalScrollBar().setUnitIncrement(7);
		
		peticionesContainer = new JPanel();
		peticionesContainer.setBackground(Color.WHITE);
		peticionesContainer.setBounds(10, 55, 416, 424);
		scrollPeticion.setViewportView(peticionesContainer);
		peticionesContainer.setLayout(null);
		peticiones.add(scrollPeticion);
		
		scrollAmigos = new JScrollPane();
		scrollAmigos.setSize(335, 430);
		scrollAmigos.setLocation(10, 54);
		scrollAmigos.setViewportBorder(null);
		scrollAmigos.setBorder(null);
		scrollAmigos.getVerticalScrollBar().setUnitIncrement(7);
		
		amigoContainer = new JPanel();
		amigoContainer.setBackground(new Color(245, 245, 245));
		amigoContainer.setBounds(10, 58, 335, 421);
		scrollAmigos.setViewportView(amigoContainer);
		amigoContainer.setLayout(null);
		tab_amigos.add(scrollAmigos);
		
		JButton newAmigo = new JButton("");
		newAmigo.setFocusPainted(false);
		newAmigo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				AnyadirAmigoUI addAmigo = new AnyadirAmigoUI();
				addAmigo.setVisible(true);
			}
		});
		newAmigo.setBorderPainted(false);
		newAmigo.setContentAreaFilled(false);
		newAmigo.setIcon(new ImageIcon(PrincipalUI.class.getResource("/img/anyadir_blue.png")));
		newAmigo.setBounds(97, 19, 43, 21);
		tab_amigos.add(newAmigo);
		
		JTextField escribirTexto = new JTextField();
		escribirTexto.setBounds(10, 438, 588, 36);
		PrincipalUI.tab_chat.add(escribirTexto);
		escribirTexto.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(65, 105, 225)));
		escribirTexto.setForeground(new Color(128, 128, 128));
		escribirTexto.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		escribirTexto.setColumns(10);
					
		escribirTexto.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{	
				if (!escribirTexto.getText().equals(""))
				{	
					mC.enviarMensaje(chatEnvio, LoginUsuario.getIdUsuario(), escribirTexto.getText());
					mC.cargarChat(chatEnvio, PrincipalUI.containerMsj);	
					escribirTexto.setText("");
				}
				else
				{
					System.out.println("Escribe algo aunque sea mamoooon");
				}
			}
		});
		
		JPanel toolbar = new JPanel();
		toolbar.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(227, 227, 227)));
		toolbar.setBackground(new Color(255, 250, 250));
		toolbar.setBounds(0, 0, 250, 489);
		contentPane.add(toolbar);
		toolbar.setLayout(null);
		
		JPanel bg_toolbar = new JPanel();
		bg_toolbar.setBackground(new Color(65, 105, 225));
		bg_toolbar.setBounds(0, 0, 65, 489);
		toolbar.add(bg_toolbar);
		bg_toolbar.setLayout(null);
		
		JButton salir = new JButton("");
		salir.setFocusPainted(false);
		salir.setIcon(new ImageIcon(PrincipalUI.class.getResource("/img/salir.png")));
		salir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				dispose();
				new LoginUI().setVisible(true);
			}
		});
		salir.setBorderPainted(false);
		salir.setContentAreaFilled(false);
		salir.setBounds(13, 425, 45, 43);
		bg_toolbar.add(salir);
		
		barChat = new JPanel();
		barChat.setBackground(new Color(255, 250, 250));
		barChat.setBounds(66, 47, 186, 442);
		toolbar.add(barChat);
		barChat.setLayout(null);
		
		container = new JPanel();
		container.setBorder(null);
		container.setVisible(false);
		container.setBackground(new Color(255, 250, 250));
		container.setBounds(0, 0, 166, 489);	
		container.setLayout(null);
		
		scrollChat = new JScrollPane();
		scrollChat.setBounds(0, 2, 184, 430);
		scrollChat.setBorder(new MatteBorder(0, 0, 0, 1, (Color) new Color(227, 227, 227)));
		scrollChat.getVerticalScrollBar().setUnitIncrement(7);
		
		nochatLabel = new JLabel("No tienes ning�n chat :(");
		nochatLabel.setBorder(null);
		nochatLabel.setBounds(10, 10, 149, 34);
		barChat.add(nochatLabel);
		nochatLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
		scrollChat.setViewportBorder(null);
		scrollChat.setViewportView(container);	
		
		noChatDesc = new JLabel("<html>A�ade a un usuario como amigo o crea un grupo para empezar a conversar </html>");
		noChatDesc.setBounds(10, 40, 124, 58);
		barChat.add(noChatDesc);
		noChatDesc.setVerticalAlignment(SwingConstants.TOP);
		noChatDesc.setForeground(Color.GRAY);
		noChatDesc.setHorizontalAlignment(SwingConstants.CENTER);
		noChatDesc.setFont(new Font("Segoe UI", Font.BOLD, 9));	
		barChat.add(scrollChat);
		
		JButton amigos = new JButton("");
		amigos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				amics = new AmigosUsuario();
				
				if (amics.mostrarListaAmigos(amigoContainer))
				{
					descAmigo.setVisible(false);
				}
				if (amics.mostrarListaPeticiones(peticionesContainer))
				{
					descPeti.setVisible(false);					
				}
			
				tab_amigos.setVisible(true);
				tab_chat.setVisible(false);
				tab_ajustes.setVisible(false);
				barChat.setVisible(false);
			}
		});
		amigos.setFocusPainted(false);
		amigos.setBounds(10, 68, 45, 52);
		bg_toolbar.add(amigos);
		amigos.setIcon(new ImageIcon(PrincipalUI.class.getResource("/img/amigos.png")));
		amigos.setContentAreaFilled(false);
		amigos.setBorderPainted(false);	
		
		JButton chats = new JButton("");
		chats.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				container.removeAll();
				
				chatsU = new ChatsUsuario(mC);
				if (chatsU.mostrarListaChats(container))
				{
					container.setVisible(true);
					nochatLabel.setVisible(false);
					noChatDesc.setVisible(false);
				}	
				else
				{
					container.setVisible(true);
					nochatLabel.setVisible(true);
					noChatDesc.setVisible(true);
				}
				tab_amigos.setVisible(false);
				tab_noChat.setVisible(true);
				tab_ajustes.setVisible(false);
				barChat.setVisible(true);
				
				toolbar.repaint();
				toolbar.revalidate();
			}
		});
		chats.setFocusPainted(false);
		chats.setIcon(new ImageIcon(PrincipalUI.class.getResource("/img/chat.png")));
		chats.setContentAreaFilled(false);
		chats.setBorderPainted(false);
		chats.setBounds(10, 10, 45, 52);
		bg_toolbar.add(chats);
		
		JButton ajustes = new JButton("");
		ajustes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				tab_ajustes.setVisible(true);
				tab_noChat.setVisible(false);
				tab_chat.setVisible(false);
				barChat.setVisible(false);		
				tab_amigos.setVisible(false);
				
				contentPane.revalidate();
				contentPane.repaint();
			}
		});
		ajustes.setFocusPainted(false);
		ajustes.setIcon(new ImageIcon(PrincipalUI.class.getResource("/img/ajustes.png")));
		ajustes.setContentAreaFilled(false);
		ajustes.setBorderPainted(false);
		ajustes.setBounds(15, 373, 33, 43);
		bg_toolbar.add(ajustes);
		
		JButton anyadir = new JButton("");
		anyadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				CrearGrupoUI crearGrp = new CrearGrupoUI();
				crearGrp.setVisible(true);
			}
		});
		anyadir.setIcon(new ImageIcon(PrincipalUI.class.getResource("/img/anyadir.png")));
		anyadir.setFocusPainted(false);
		anyadir.setContentAreaFilled(false);
		anyadir.setBorderPainted(false);
		anyadir.setBounds(10, 125, 42, 43);
		bg_toolbar.add(anyadir);
		
		JLabel tituloChats = new JLabel("Chats");
		tituloChats.setFont(new Font("Segoe UI", Font.BOLD, 21));
		tituloChats.setBorder(new MatteBorder(0, 0, 4, 0, (Color) new Color(65, 105, 225)));
		tituloChats.setBounds(76, 8, 72, 34);
		toolbar.add(tituloChats);
		
		tab_noChat = new JPanel();
		tab_noChat.setBorder(null);
		
		tab_noChat.setBackground(Color.WHITE);
		tab_noChat.setBounds(248, 0, 608, 489);
		contentPane.add(tab_noChat);
		tab_noChat.setLayout(null);
		
		JLabel mensajeriaTitle = new JLabel("App Mensajer\u00EDa");
		mensajeriaTitle.setBorder(new MatteBorder(0, 0, 4, 0, (Color) new Color(65, 105, 225)));
		mensajeriaTitle.setFont(new Font("Segoe UI", Font.BOLD, 25));
		mensajeriaTitle.setBounds(217, 247, 192, 66);
		tab_noChat.add(mensajeriaTitle);
		
		JLabel mensajeriaSubtitle = new JLabel("Chatea con tus amigos");
		mensajeriaSubtitle.setHorizontalAlignment(SwingConstants.CENTER);
		mensajeriaSubtitle.setForeground(Color.GRAY);
		mensajeriaSubtitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
		mensajeriaSubtitle.setBounds(223, 323, 182, 46);
		tab_noChat.add(mensajeriaSubtitle);
		
		JLabel icon = new JLabel("");
		icon.setIcon(new ImageIcon(PrincipalUI.class.getResource("/img/app.png")));
		icon.setBounds(224, 130, 128, 128);
		tab_noChat.add(icon);
		
		init();
		ajustesUsu.datosAjustes();
		
	}
	
	private void init()
	{		
		chatsU = new ChatsUsuario(mC);
		if (chatsU.mostrarListaChats(container))
		{
			container.setVisible(true);
			nochatLabel.setVisible(false);
			noChatDesc.setVisible(false);
		}	
		else
		{		
			container.setVisible(true);
			nochatLabel.setVisible(true);
			noChatDesc.setVisible(true);
			
		}
				
		scrollChat.revalidate();
		scrollChat.repaint();

	}
}
