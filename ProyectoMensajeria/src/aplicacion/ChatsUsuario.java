package aplicacion;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import interfaz.InfoGrupoUI;
import interfaz.PrincipalUI;

public class ChatsUsuario 
{
	private int idChat;
	private String nomChat;	
	private boolean administrador;
	private static int positionUI;
	
	private MensajesChat mC;
	private Connection cn;
	
	public ChatsUsuario(MensajesChat mensajes)
	{
		mC = mensajes;
		cn = Conexion.Conectar();
	}
	
	public boolean mostrarListaChats(JPanel padre)
	{
		ResultSet rsConver = conversUser();
		ResultSet rsGrupo = gruposUser();
		
		int countChats = 0;
		positionUI = 20;
		int incremento = 60;
		
		int usuarioContrario = 0;
		
		try 
		{
			while (rsConver.next())
			{
				idChat = rsConver.getInt("id_chat");
				int idUser1 = rsConver.getInt("id_usu1");
				int idUser2 = rsConver.getInt("id_usu2");
				
				// L�gica para determinar el usuario que tiene que mostrar en la conversacion 
				if (idUser1 != LoginUsuario.getIdUsuario())
				{
					usuarioContrario = idUser1;				
				}
				else
				{
					usuarioContrario = idUser2;
				}
				
				nomChat =  "Chat con " + LoginUsuario.nombreUserPorId(usuarioContrario);
				
				// Creacion del elemento de la interfaz que contiene la conversacion
				JPanel panelChat = new JPanel();				
				panelChat.setBackground(new Color(255, 250, 250));
				panelChat.setBounds(10, positionUI, 146, 52);
				panelChat.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(227, 227, 227)));
				panelChat.setLayout(null);
				padre.add(panelChat);
				
				// Incremento en el panel para que no se acumulen y vayan posicionandose 
				positionUI += incremento;
				
				String nomIndividual = nomChat;
				int idIndividual = idChat;
				int usuarioFinal = usuarioContrario;
				
				JButton nombreChat = new JButton(nomChat);
				nombreChat.setBounds(-5,0,151,35);
				nombreChat.setBackground(new Color(255, 250, 250));
				nombreChat.setBorderPainted(false);
				nombreChat.setFocusPainted(false);
				nombreChat.setHorizontalAlignment(SwingConstants.LEFT);
				nombreChat.setContentAreaFilled(true);
				nombreChat.setFont(new Font("Segoe UI", Font.BOLD, 13));		
				
				nombreChat.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) 
					{		
						MensajesChat mC = new MensajesChat();
						PrincipalUI.nombreChat.setText(nomIndividual);
						PrincipalUI.setCurrentChatTitulo(nomIndividual);
						PrincipalUI.setCurrentChatDesc("");
						PrincipalUI.nombreChat.setBounds(10, 12, (int)PrincipalUI.nombreChat.getPreferredSize().getWidth() + 10, 33);
						PrincipalUI.descripcionChat.setText("");
						PrincipalUI.containerMsj.removeAll();
						PrincipalUI.setChatEnvio(idIndividual);
						mC.cargarChat(idIndividual, PrincipalUI.containerMsj);	
						mostrarOpcionChat(usuarioFinal);
					}
				});
				panelChat.add(nombreChat);
				countChats++;
				
					
			}
			
			while (rsGrupo.next())
			{
				nomChat = rsGrupo.getString("nombre");
				administrador = rsGrupo.getBoolean("administra");
				String descripcion = rsGrupo.getString("descripcion");
				idChat = rsGrupo.getInt("id_chat");
				
				// Creacion del elemento de la interfaz que contiene el grupo
				JPanel panelChat = new JPanel();				
				panelChat.setBackground(new Color(255, 250, 250));
				panelChat.setBounds(10, positionUI, 146, 52);
				panelChat.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(227, 227, 227)));
				panelChat.setLayout(null);
				padre.add(panelChat);
				
				positionUI += incremento;
				
				String nomIndividual = nomChat;
				int idIndividual = idChat;
				boolean admin = administrador;
				int usuarioFinal = usuarioContrario;
				
				JLabel descripcionChatIndividual = new JLabel(descripcion);
				descripcionChatIndividual.setBounds(10,25,146,20);
				descripcionChatIndividual.setForeground(Color.GRAY);
				descripcionChatIndividual.setFont(new Font("Segoe UI", Font.PLAIN, 9));
				panelChat.add(descripcionChatIndividual);
				
				JButton nombreChat = new JButton(nomChat);
				nombreChat.setBounds(-5,0,151,35);
				nombreChat.setBackground(new Color(255, 250, 250));
				nombreChat.setBorderPainted(false);
				nombreChat.setFocusPainted(false);
				nombreChat.setHorizontalAlignment(SwingConstants.LEFT);
				nombreChat.setContentAreaFilled(true);
				nombreChat.setFont(new Font("Segoe UI", Font.BOLD, 13));
				nombreChat.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) 
					{											
						mC = new MensajesChat();	
						PrincipalUI.nombreChat.setText(nomIndividual);
						PrincipalUI.setCurrentChatTitulo(nomIndividual);
						PrincipalUI.setCurrentChatDesc(descripcion);
						PrincipalUI.nombreChat.setBounds(10, 4, (int)PrincipalUI.nombreChat.getPreferredSize().getWidth() + 10, 33);					
						PrincipalUI.descripcionChat.setText(descripcion);
						PrincipalUI.descripcionChat.setBounds(10, 30, (int)PrincipalUI.descripcionChat.getPreferredSize().getWidth() + 10, 21);
						PrincipalUI.containerMsj.removeAll();
						PrincipalUI.setChatEnvio(idIndividual);
						PrincipalUI.setCurrentAdministra(admin);
						mC.cargarChat(idIndividual, PrincipalUI.containerMsj);	
						mostrarOpcionChat(usuarioFinal);
					}
				});
				panelChat.add(nombreChat);	
				panelChat.revalidate();
				panelChat.repaint();
					
				countChats++;
			}
			
			padre.setPreferredSize(new Dimension(150, positionUI - 8));
			padre.revalidate();
			padre.repaint();
			
			if (countChats > 0)
			{
				return true;
			}
		} 
		catch (SQLException e) 
		{
			System.out.println("Error SQL");
			e.printStackTrace();
		}
		
		return false;
	}
	
	private static ResultSet conversUser()
	{
		Connection cn = LoginUsuario.getConexion();
		int id_usuario = LoginUsuario.getIdUsuario();
		String consultaSQL = "SELECT usuario.id_usuario, nombre, conversacion.id_chat, id_usu1, id_usu2 FROM usuario INNER JOIN conversacion ON usuario.id_usuario = conversacion.id_usu1 OR usuario.id_usuario = conversacion.id_usu2 WHERE usuario.id_usuario = ?";
		
		ResultSet rs = null;
		
		try 
	    {
	        PreparedStatement pst = cn.prepareStatement(consultaSQL);
	        pst.setInt(1, id_usuario);
	        rs = pst.executeQuery();
	        
	    } 
	    catch (SQLException ex) 
	    {
	        System.out.println("Error al seleccionar datos");
	    }
		
		return rs;
	}
	
	private static ResultSet gruposUser()
	{
		Connection cn = LoginUsuario.getConexion();
		int id_usuario = LoginUsuario.getIdUsuario();
		String consultaSQL = "SELECT usuario.id_usuario, participa.id_chat, administra, grupo.nombre, grupo.descripcion FROM usuario INNER JOIN participa USING (id_usuario) INNER JOIN grupo USING (id_chat) WHERE usuario.id_usuario = ?";
		
		ResultSet rs = null;
		
		try 
	    {
	        PreparedStatement pst = cn.prepareStatement(consultaSQL);
	        pst.setInt(1, id_usuario);
	        rs = pst.executeQuery();
	        
	    } 
	    catch (SQLException ex) 
	    {
	        System.out.println("Error al seleccionar datos");
	    }
	
		return rs;
	}
	
	private void mostrarOpcionChat(int id_usu)
	{
		// Si es un grupo	
		if (!PrincipalUI.chatsU.isConver(PrincipalUI.getCurrentChat()))
		{
			PrincipalUI.chatOption.removeAll();
			
			JLabel infoChat = new JLabel();
			infoChat.setIcon(new ImageIcon(MensajesChat.class.getResource("/img/info.png")));
			infoChat.setBounds(167, 8, 39, 37);
			
			infoChat.addMouseListener(new MouseAdapter()
			{
				@Override
				public void mouseClicked(MouseEvent e)
				{
					InfoGrupoUI info = new InfoGrupoUI();
					info.setVisible(true);
				}
			});
			PrincipalUI.chatOption.add(infoChat);
		}
		// Si es una conversacion
		else if (PrincipalUI.chatsU.isConver(PrincipalUI.getCurrentChat()))
		{
			ResultSet rsAmigos = fechaAmistad(LoginUsuario.getIdUsuario(), id_usu);
			Date amigosFecha = null;
			try 
			{
				while (rsAmigos.next())
				{
					amigosFecha = rsAmigos.getDate("amigodesde");
				}
				
				PrincipalUI.chatOption.removeAll();
				JLabel amigosDesde = new JLabel("Amigos desde: " + amigosFecha);
				amigosDesde.setForeground(Color.WHITE);
				amigosDesde.setFont(new Font("Segoe UI", Font.PLAIN, 16));
				amigosDesde.setBounds(20, 12, 240, 27);
				PrincipalUI.chatOption.add(amigosDesde);
			} 
			catch (SQLException e) 
			{
				
				e.printStackTrace();
			}	
		}
		
		PrincipalUI.chatOption.revalidate();
		PrincipalUI.chatOption.repaint();
	}
	
	private ResultSet fechaAmistad(int id_u1, int id_u2)
	{
		ResultSet rs = null;
		
		try 
	    {
	        PreparedStatement pst = cn.prepareStatement("SELECT amigodesde FROM amistad WHERE (id_usu1 = ? AND id_usu2 = ?) OR (id_usu1 = ? AND id_usu2 = ?)");
	        pst.setInt(1, id_u1);
	        pst.setInt(2, id_u2);
	        pst.setInt(3, id_u2);
	        pst.setInt(4, id_u1);
	        rs = pst.executeQuery();	        
	    } 
	    catch (SQLException ex) 
	    {
	        System.out.println("Error al seleccionar datos");
	    }
		
		return rs;	
	}

	public boolean isConver(int id_c)
	{
		ResultSet conversaciones = conversUser();
		boolean found = false;
		
		try 
		{
			while (conversaciones.next())
			{
				int idEncontrada = conversaciones.getInt("id_chat");
				if (idEncontrada == id_c)
				{
					found = true;
				}
			}
			
			if (found)
			{
				return true;
			}
			else
			{
				return false;
			}
		} 
		catch (SQLException e) 
		{
			System.out.println("Error de SQL");
			e.printStackTrace();
		}
		
		return false;
	}

}
