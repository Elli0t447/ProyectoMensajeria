package vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.MatteBorder;

import controladorMain.LoginUsuario;

public class LoginUI extends JFrame 
{
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField usuarioLogin;
	private JPasswordField passwordLogin;
	
	private static LoginUsuario login;
	
	public static void main(String[] args) 
	{
		try 
		{
			//Definir el LookAndFeel del programa
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());	
		} 		
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) 
		{
			System.out.println("Error al definir el LookAndFeel");
			
		}
		
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					LoginUI frame = new LoginUI();
					frame.setVisible(true);
				} 
				catch (Exception e) 
				{
					
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
		  catch (Exception e) 
		  {
			 
		     System.out.println("Error icono no encontrado");
		  }
		
		setTitle("Mensajer\u00EDa - inicio");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 387, 509);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel form = new JPanel();
		
		form.setBounds(55, 76, 269, 337);
		form.setBackground(new Color(255, 255, 255));
		contentPane.add(form);
		form.setBorder(new MatteBorder(1, 1, 1, 2, (Color) UIManager.getColor("Button.light")));
		form.setLayout(null);
		
		JLabel userLabel = new JLabel("Usuario");
		userLabel.setBounds(24, 24, 66, 21);
		form.add(userLabel);
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
		form.add(usuarioLogin);
		usuarioLogin.setColumns(10);
		
		JLabel passLabel = new JLabel("Contrase\u00F1a");
		passLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
		passLabel.setBounds(24, 126, 109, 13);
		form.add(passLabel);
		
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
		form.add(passwordLogin);
		
		
		
		JButton botonLogin = new JButton("ENTRAR");
		botonLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				 loginAction();
			}
		});
		botonLogin.setBorderPainted(false);
		botonLogin.setBackground(new Color(65, 105, 225));
		botonLogin.setForeground(new Color(255, 255, 255));
		botonLogin.setFont(new Font("Segoe UI", Font.BOLD, 20));
		botonLogin.setBounds(68, 210, 132, 47);
		form.add(botonLogin);	
		
		JButton botonNuevaCuenta = new JButton("CREAR CUENTA");
		botonNuevaCuenta.setForeground(new Color(105, 105, 105));
		botonNuevaCuenta.setHorizontalAlignment(SwingConstants.LEFT);
		botonNuevaCuenta.setAlignmentY(Component.TOP_ALIGNMENT);
		botonNuevaCuenta.setContentAreaFilled(false);
		botonNuevaCuenta.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(169, 169, 169)));
		botonNuevaCuenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{				
				try
				{
					String passwordFormated = new String(passwordLogin.getPassword());
					login = new LoginUsuario(usuarioLogin.getText(), passwordFormated);	
					
					int opcion = JOptionPane.showConfirmDialog(null, "¿Quieres crear una cuenta con estos datos?", " Crear", JOptionPane.YES_NO_OPTION);
					int countUsus = 0;
						
					// Si el usuario existe no deja crear la cuenta nueva
					if (opcion == 0)
					{
						ResultSet rsNomUsus = LoginUsuario.allNomUsuarios();
						while (rsNomUsus.next())
						{
							if (usuarioLogin.getText().equals(rsNomUsus.getString("nombre")))
							{
								countUsus++;
							}
						}
						
						if (countUsus <= 0)
						{								
							login.nuevaCuenta();
							
							JOptionPane.showMessageDialog(null, "Nuevo usuario creado!", "Nuevo", JOptionPane.INFORMATION_MESSAGE);
							usuarioLogin.setText("");
							passwordLogin.setText("");
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Usuario ya existente!", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}		
					else
					{
						System.out.println("No se ha creado nueva cuenta");
					}
				}
				catch (SQLException ex)
				{
					ex.printStackTrace();
					System.out.println("ErrorSQL (botonNuevaCuenta)");
				}
			}
		});
		botonNuevaCuenta.setBackground(new Color(255, 255, 255));
		botonNuevaCuenta.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 12));
		botonNuevaCuenta.setBounds(91, 291, 91, 21);
		form.add(botonNuevaCuenta);
		
		JPanel bg = new JPanel();
		bg.setBounds(0, 0, 383, 157);
		bg.setBorder(null);
		bg.setBackground(new Color(65, 105, 225));
		contentPane.add(bg);
		bg.setLayout(null);
		
		JLabel imagen = new JLabel("");
		imagen.setBounds(232, 10, 32, 39);
		bg.add(imagen);
		imagen.setIcon(new ImageIcon(LoginUI.class.getResource("/img/chat.png")));
		
		JLabel iconoText = new JLabel("Mensajeria");
		iconoText.setBounds(135, 38, 101, 19);
		iconoText.setForeground(Color.WHITE);
		iconoText.setFont(new Font("Segoe UI", Font.BOLD, 18));
		bg.add(iconoText);		
	}
	
	// Entrar a la aplicacion con un usuario determinado
	private void loginAction()
	{
		 String passwordFormated = new String(passwordLogin.getPassword());
		 
	     login = new LoginUsuario(usuarioLogin.getText(), passwordFormated);
	     if (login.comprobarExistenciaUser())
	     {
	    	 dispose();
	    	 new PrincipalUI().setVisible(true);
			 System.out.println("Log-in correcto!");
	     }
	     else
	     {
	    	 JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
	     }
	}
}
