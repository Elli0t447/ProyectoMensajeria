package interfaz;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;
import javax.swing.border.LineBorder;

public class PrincipalUI extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textoChat;
	private JLabel descripcionTest;

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
		     ImageIcon programIcon = new ImageIcon(PrincipalUI.class.getResource("/img/logo.png"));
		     setIconImage(programIcon.getImage());
		 }
		 catch (Exception whoJackedMyIcon) 
		 {
		    System.out.println("Error icono no encontrado");
		 }
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 684, 460);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel toolbar = new JPanel();
		toolbar.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(227, 227, 227)));
		toolbar.setBackground(new Color(255, 250, 250));
		toolbar.setBounds(0, 0, 242, 438);
		contentPane.add(toolbar);
		toolbar.setLayout(null);
		
		JPanel azul = new JPanel();
		azul.setBackground(new Color(65, 105, 225));
		azul.setBounds(0, 0, 65, 438);
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
		salir.setBounds(13, 379, 45, 43);
		azul.add(salir);
		
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
		
		JButton ajustes = new JButton("");
		ajustes.setIcon(new ImageIcon(PrincipalUI.class.getResource("/img/ajustes.png")));
		ajustes.setContentAreaFilled(false);
		ajustes.setBorderPainted(false);
		ajustes.setBounds(14, 324, 33, 43);
		azul.add(ajustes);
		
		JLabel chatTest = new JLabel("chat ejemplo");
		chatTest.setFont(new Font("Segoe UI", Font.PLAIN, 17));
		chatTest.setBounds(75, 39, 106, 18);
		toolbar.add(chatTest);
		
		JLabel chatTest_1 = new JLabel("chat ejemplo");
		chatTest_1.setFont(new Font("Segoe UI", Font.PLAIN, 17));
		chatTest_1.setBounds(75, 108, 106, 18);
		toolbar.add(chatTest_1);
		
		JLabel chatTest_2 = new JLabel("chat ejemplo");
		chatTest_2.setFont(new Font("Segoe UI", Font.PLAIN, 17));
		chatTest_2.setBounds(75, 186, 106, 18);
		toolbar.add(chatTest_2);
		
		descripcionTest = new JLabel("Descripcion");
		descripcionTest.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		descripcionTest.setForeground(new Color(128, 128, 128));
		descripcionTest.setBounds(95, 67, 74, 18);
		toolbar.add(descripcionTest);
		
		JLabel descripcionTest_1 = new JLabel("Descripcion");
		descripcionTest_1.setForeground(Color.GRAY);
		descripcionTest_1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		descripcionTest_1.setBounds(95, 143, 74, 18);
		toolbar.add(descripcionTest_1);
		
		JLabel descripcionTest_2 = new JLabel("Descripcion");
		descripcionTest_2.setForeground(Color.GRAY);
		descripcionTest_2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		descripcionTest_2.setBounds(95, 215, 74, 18);
		toolbar.add(descripcionTest_2);
		
		JPanel tab_chat = new JPanel();
		tab_chat.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(227, 227, 227)));
		tab_chat.setBackground(new Color(65, 105, 225));
		tab_chat.setBounds(242, 0, 438, 48);
		contentPane.add(tab_chat);
		tab_chat.setLayout(null);
		
		JLabel nombreChat = new JLabel("id_chat / nombrechat");
		nombreChat.setForeground(new Color(255, 255, 255));
		nombreChat.setFont(new Font("Segoe UI", Font.BOLD, 20));
		nombreChat.setBounds(10, 5, 239, 33);
		tab_chat.add(nombreChat);
		
		textoChat = new JTextField();
		textoChat.setBounds(263, 377, 391, 39);
		contentPane.add(textoChat);
		textoChat.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(65, 105, 225)));
		textoChat.setForeground(new Color(128, 128, 128));
		textoChat.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		textoChat.setColumns(10);
		
	}
}
