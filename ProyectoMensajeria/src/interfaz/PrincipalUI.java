package interfaz;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.border.MatteBorder;

import aplicacion.ChatsUsuario;
import aplicacion.LoginUsuario;
import aplicacion.MensajesChat;

import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
	private JPanel container;
	
	private JLabel nochatLabel;
	private JLabel noChatDesc;
	
	public static JLabel nombreChat;
	public static JPanel containerMsj;
	
	private static JScrollPane scrollChat;
	public static JScrollPane scrollMensaje;
	
	private static int chatEnvio;
	private static boolean currentAdministra;
	private static String currentChatTitulo;
	private static String currentChatDesc;
	
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
		
		setResizable(false);
		setTitle("Conversaciones @" + LoginUI.login.getUsuario());
		try 
		{
			 // Definir icono del ejecutable
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
				
		JPanel toolbar = new JPanel();
		toolbar.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(227, 227, 227)));
		toolbar.setBackground(new Color(255, 250, 250));
		toolbar.setBounds(0, 0, 250, 489);
		contentPane.add(toolbar);
		toolbar.setLayout(null);
		
		tab_chat = new JPanel();
		tab_chat.setVisible(false);
		tab_chat.setBorder(null);
		tab_chat.setBackground(Color.WHITE);
		tab_chat.setBounds(248, 0, 608, 489);
		contentPane.add(tab_chat);
		tab_chat.setLayout(null);
		
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
		barChat.setBounds(66, 0, 186, 489);
		toolbar.add(barChat);
		barChat.setLayout(null);
		
		container = new JPanel();
		container.setBorder(null);
		container.setVisible(false);
		container.setBackground(new Color(255, 250, 250));
		container.setBounds(0, 0, 166, 489);	
		container.setLayout(null);
		
		scrollChat = new JScrollPane();
		scrollChat.setBounds(0, 0, 184, 489);
		scrollChat.setBorder(new MatteBorder(0, 0, 0, 1, (Color) new Color(227, 227, 227)));
		scrollChat.getVerticalScrollBar().setUnitIncrement(7);
		scrollChat.setViewportBorder(null);
		scrollChat.setViewportView(container);	
		barChat.add(scrollChat);
		
		nochatLabel = new JLabel("No tienes ningun chat :(");
		nochatLabel.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(65, 105, 225)));
		nochatLabel.setBounds(10, 10, 149, 34);
		barChat.add(nochatLabel);
		nochatLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
		
		noChatDesc = new JLabel("<html>Añade a un usuario como amigo o crea un grupo para empezar a conversar </html>");
		noChatDesc.setVerticalAlignment(SwingConstants.TOP);
		noChatDesc.setBounds(10, 53, 124, 58);
		barChat.add(noChatDesc);
		noChatDesc.setForeground(Color.GRAY);
		noChatDesc.setHorizontalAlignment(SwingConstants.CENTER);
		noChatDesc.setFont(new Font("Segoe UI", Font.BOLD, 9));	
		
		JButton amigos = new JButton("");
		amigos.setFocusPainted(false);
		amigos.setBounds(10, 68, 45, 52);
		bg_toolbar.add(amigos);
		amigos.setIcon(new ImageIcon(PrincipalUI.class.getResource("/img/amigos.png")));
		amigos.setContentAreaFilled(false);
		amigos.setBorderPainted(false);	
		
		JButton chats = new JButton("");
		chats.setFocusPainted(false);
		chats.setIcon(new ImageIcon(PrincipalUI.class.getResource("/img/chat.png")));
		chats.setContentAreaFilled(false);
		chats.setBorderPainted(false);
		chats.setBounds(10, 10, 45, 52);
		bg_toolbar.add(chats);
		
		JPanel bg_chat = new JPanel();
		bg_chat.setBackground(new Color(65, 105, 225));
		bg_chat.setBounds(0, 0, 652, 60);
		tab_chat.add(bg_chat);
		bg_chat.setLayout(null);
		
		descripcionChat = new JLabel("descripcionChat");
		descripcionChat.setBounds(11, 30, 119, 21);
		bg_chat.add(descripcionChat);
		descripcionChat.setForeground(new Color(211, 211, 211));
		descripcionChat.setFont(new Font("Segoe UI", Font.BOLD, 15));
		
		nombreChat = new JLabel("id_chat / nombrechat");
		nombreChat.setBounds(10, 4, 190, 33);
		bg_chat.add(nombreChat);
		nombreChat.setForeground(new Color(255, 255, 255));
		nombreChat.setFont(new Font("Segoe UI", Font.BOLD, 18));
		
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
		
		JButton ajustes = new JButton("");
		ajustes.setFocusPainted(false);
		ajustes.setIcon(new ImageIcon(PrincipalUI.class.getResource("/img/ajustes.png")));
		ajustes.setContentAreaFilled(false);
		ajustes.setBorderPainted(false);
		ajustes.setBounds(15, 373, 33, 43);
		bg_toolbar.add(ajustes);
		
		init();
		
	}
	
	private void init()
	{
		
		chatsU = new ChatsUsuario(mC);
		if (chatsU.mostrarListaChats(container))
		{
			container.setVisible(true);
			scrollChat.setVisible(true);
		}	
		
		scrollChat.revalidate();
		scrollChat.repaint();
	}
}
