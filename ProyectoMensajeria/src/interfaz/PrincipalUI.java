package interfaz;


import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Color;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextField;
import javax.swing.border.MatteBorder;

import aplicacion.ChatsUsuario;

import javax.swing.SwingConstants;

public class PrincipalUI extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField escribirTexto;
	private ChatsUsuario chats;
	
	public static JPanel tab_chat;
	public static JPanel tab_noChat;
	public static JLabel descripcionChat;
	private JPanel chatContainer;
	private JPanel container;
	
	private JLabel nochatLabel;
	private JLabel noChatDesc;
	
	public static JLabel nombreChat;
	public static JPanel containerMsj;

	/**
	 * Create the frame.
	 */
	public PrincipalUI() 
	{
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
		toolbar.setBounds(0, 0, 236, 489);
		contentPane.add(toolbar);
		toolbar.setLayout(null);
		
		tab_chat = new JPanel();
		tab_chat.setVisible(false);
		tab_chat.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(227, 227, 227)));
		tab_chat.setBackground(Color.WHITE);
		tab_chat.setBounds(233, 0, 623, 489);
		contentPane.add(tab_chat);
		tab_chat.setLayout(null);
		
		escribirTexto = new JTextField();
		escribirTexto.setBounds(12, 430, 602, 34);
		tab_chat.add(escribirTexto);
		escribirTexto.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(65, 105, 225)));
		escribirTexto.setForeground(new Color(128, 128, 128));
		escribirTexto.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		escribirTexto.setColumns(10);
		
		JPanel azul = new JPanel();
		azul.setBackground(new Color(65, 105, 225));
		azul.setBounds(0, 0, 65, 489);
		toolbar.add(azul);
		azul.setLayout(null);
		
		JButton salir = new JButton("");
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
		azul.add(salir);
		
		chatContainer = new JPanel();
		chatContainer.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(227, 227, 227)));
		chatContainer.setBackground(new Color(255, 250, 250));
		chatContainer.setBounds(66, 0, 169, 489);
		toolbar.add(chatContainer);
		chatContainer.setLayout(null);
		
		container = new JPanel();
		container.setVisible(false);
		container.setBackground(new Color(255, 250, 250));
		container.setBounds(0, 0, 166, 489);
		chatContainer.add(container);
		container.setLayout(null);
		
		nochatLabel = new JLabel("No tienes ningun chat :(");
		nochatLabel.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(65, 105, 225)));
		nochatLabel.setBounds(10, 10, 149, 34);
		chatContainer.add(nochatLabel);
		nochatLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
		
		noChatDesc = new JLabel("<html>Añade a un usuario como amigo o crea un grupo para empezar a conversar </html>");
		noChatDesc.setVerticalAlignment(SwingConstants.TOP);
		noChatDesc.setBounds(10, 53, 124, 58);
		chatContainer.add(noChatDesc);
		noChatDesc.setForeground(Color.GRAY);
		noChatDesc.setHorizontalAlignment(SwingConstants.CENTER);
		noChatDesc.setFont(new Font("Segoe UI", Font.BOLD, 9));	
		
		JButton amigos = new JButton("");
		amigos.setBounds(10, 68, 45, 52);
		azul.add(amigos);
		amigos.setIcon(new ImageIcon(PrincipalUI.class.getResource("/img/amigos.png")));
		amigos.setContentAreaFilled(false);
		amigos.setBorderPainted(false);	
		
		JButton chats = new JButton("");
		chats.setIcon(new ImageIcon(PrincipalUI.class.getResource("/img/chat.png")));
		chats.setContentAreaFilled(false);
		chats.setBorderPainted(false);
		chats.setBounds(10, 10, 45, 52);
		azul.add(chats);
		
		JPanel blueBar = new JPanel();
		blueBar.setBackground(new Color(65, 105, 225));
		blueBar.setBounds(0, 0, 652, 60);
		tab_chat.add(blueBar);
		blueBar.setLayout(null);
		
		descripcionChat = new JLabel("descripcionChat");
		descripcionChat.setBounds(11, 30, 119, 21);
		blueBar.add(descripcionChat);
		descripcionChat.setForeground(new Color(211, 211, 211));
		descripcionChat.setFont(new Font("Segoe UI", Font.BOLD, 15));
		
		nombreChat = new JLabel("id_chat / nombrechat");
		nombreChat.setBounds(10, 4, 190, 33);
		blueBar.add(nombreChat);
		nombreChat.setForeground(new Color(255, 255, 255));
		nombreChat.setFont(new Font("Segoe UI", Font.BOLD, 18));
		
		containerMsj = new JPanel();
		containerMsj.setBorder(null);
		containerMsj.setBackground(Color.WHITE);
		containerMsj.setBounds(12, 64, 601, 356);
		tab_chat.add(containerMsj);
		containerMsj.setLayout(null);
		
		tab_noChat = new JPanel();
		
		tab_noChat.setBackground(Color.WHITE);
		tab_noChat.setBounds(233, 0, 623, 489);
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
		
		JButton ajustes = new JButton("");
		ajustes.setIcon(new ImageIcon(PrincipalUI.class.getResource("/img/ajustes.png")));
		ajustes.setContentAreaFilled(false);
		ajustes.setBorderPainted(false);
		ajustes.setBounds(15, 373, 33, 43);
		azul.add(ajustes);
		
		init();
		
	}
	
	private void init()
	{
		chats = new ChatsUsuario(LoginUI.login);
		if (chats.mostrarListaChats(container))
		{
			container.setVisible(true);
		}	
	}
}
