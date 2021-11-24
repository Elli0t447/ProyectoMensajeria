package interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.border.MatteBorder;

import aplicacion.LoginUsuario;

import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;
import javax.swing.SwingConstants;

public class LoginUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField usuarioLogin;
	private JPasswordField passwordLogin;
	
	public static LoginUsuario login;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		try 
		{
			//Definir el LookAndFeel del programa
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());	
		} 		
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (InstantiationException e) 
		{
			e.printStackTrace();
		} 
		catch (IllegalAccessException e) 
		{
			e.printStackTrace();
		} 
		catch (UnsupportedLookAndFeelException e) 
		{	
			e.printStackTrace();
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginUI frame = new LoginUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginUI() 
	{	
		 setResizable(false);
		 try 
		 {
			 // Definir icono del ejecutable
		     ImageIcon programIcon = new ImageIcon(LoginUI.class.getResource("/img/chat.png"));
		     setIconImage(programIcon.getImage());
		  }
		  catch (Exception whoJackedMyIcon) 
		  {
		     System.out.println("Error icono no encontrado");
		  }
		
		setTitle("Mensajer\u00EDa");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 387, 509);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(55, 76, 269, 337);
		contentPane.add(panel);
		panel.setBorder(new MatteBorder(1, 1, 1, 2, (Color) UIManager.getColor("Button.light")));
		panel.setLayout(null);
		
		JLabel userLabel = new JLabel("Usuario");
		userLabel.setBounds(24, 24, 66, 21);
		panel.add(userLabel);
		userLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
		
		usuarioLogin = new JTextField();
		usuarioLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				loginAction();
			}
		});
		usuarioLogin.setForeground(new Color(128, 128, 128));
		usuarioLogin.setBorder(new MatteBorder(0, 0, 3, 0, (Color) new Color(65, 105, 225)));
		usuarioLogin.setToolTipText("Introduce tu usuario");
		usuarioLogin.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		usuarioLogin.setBounds(24, 51, 214, 30);
		panel.add(usuarioLogin);
		usuarioLogin.setColumns(10);
		
		JLabel passLabel = new JLabel("Contrase\u00F1a");
		passLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
		passLabel.setBounds(24, 126, 109, 13);
		panel.add(passLabel);
		
		passwordLogin = new JPasswordField();
		passwordLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				loginAction();
			}
		});
		passwordLogin.setForeground(new Color(128, 128, 128));
		passwordLogin.setBorder(new MatteBorder(0, 0, 3, 0, (Color) new Color(65, 105, 225)));
		passwordLogin.setToolTipText("Introduce tu contrase\u00F1a");
		passwordLogin.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		passwordLogin.setBounds(24, 149, 214, 30);
		panel.add(passwordLogin);
		
		
		
		JButton btnNewButton = new JButton("ENTRAR");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				 loginAction();
			}
		});
		btnNewButton.setBorderPainted(false);
		btnNewButton.setBackground(new Color(65, 105, 225));
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
		btnNewButton.setBounds(68, 210, 132, 47);
		panel.add(btnNewButton);
		
		
		
		JButton btnNewButton_1 = new JButton("CREAR CUENTA");
		btnNewButton_1.setForeground(new Color(105, 105, 105));
		btnNewButton_1.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton_1.setAlignmentY(Component.TOP_ALIGNMENT);
		btnNewButton_1.setContentAreaFilled(false);
		btnNewButton_1.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(169, 169, 169)));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{				
				String passwordFormated = new String(passwordLogin.getPassword());
				login = new LoginUsuario(usuarioLogin.getText(), passwordFormated);			
				login.nuevaCuenta();
				
				JOptionPane.showMessageDialog(null, "Nuevo usuario creado!", "Nuevo", JOptionPane.INFORMATION_MESSAGE);
				usuarioLogin.setText("");
				passwordLogin.setText("");
			}
		});
		btnNewButton_1.setBackground(new Color(255, 255, 255));
		btnNewButton_1.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 12));
		btnNewButton_1.setBounds(91, 291, 91, 21);
		panel.add(btnNewButton_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(65, 105, 225));
		panel_1.setBounds(0, 0, 384, 157);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel imagen = new JLabel("");
		imagen.setBounds(232, 10, 32, 39);
		panel_1.add(imagen);
		imagen.setIcon(new ImageIcon(LoginUI.class.getResource("/img/chat.png")));
		
		JLabel lblNewLabel = new JLabel("Mensajeria");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblNewLabel.setBounds(135, 38, 101, 19);
		panel_1.add(lblNewLabel);
		
		
	}
	
	private void loginAction()
	{
		String passwordFormated = new String(passwordLogin.getPassword());
	     login = new LoginUsuario(usuarioLogin.getText(), passwordFormated);
	     if (login.comprobarUser())
	     {
	    	 dispose();
	    	 new PrincipalUI().setVisible(true);
			 System.out.println("Log in correcto!");
	     }
	     else
	     {
	    	 JOptionPane.showMessageDialog(null, "Usuario o contraseņa incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
	     }
	}
}
