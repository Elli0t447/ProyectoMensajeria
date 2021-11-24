package aplicacion;

import java.awt.Color;
import java.awt.Font;
import java.sql.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class ChatsUsuario 
{
	private LoginUsuario login;
	
	public ChatsUsuario(LoginUsuario l)
	{
		login = l;
	}
	
	public LoginUsuario getLogin() { return login; }
	
	public boolean mostrarChats(JPanel padre)
	{
		ResultSet rsConver = conversUser();
		ResultSet rsGrupo = gruposUser();
		
		int countChats = 0;
		int positionUI = 20;
		int incremento = 60;
		
		try 
		{
			while (rsConver.next())
			{
				int idUser1 = rsConver.getInt("id_usu1");
				int idUser2 = rsConver.getInt("id_usu2");
				
				int usuarioFinal;
				
				if (idUser1 != login.getIdUsuario())
				{
					usuarioFinal = idUser1;
					
				}
				else
				{
					usuarioFinal = idUser2;
				}
				
				String nombreUsuario = login.nombreUserPorId(usuarioFinal);
				
				// Creacion del elemento de la interfaz que contiene la conversacion
				JPanel panelChat = new JPanel();				
				panelChat.setBackground(new Color(255, 255, 255));
				panelChat.setBounds(10, positionUI, 146, 52);
				panelChat.setBorder(new EmptyBorder(10, 10, 10, 10));
				panelChat.setLayout(null);
				padre.add(panelChat);
				
				positionUI += incremento;
				
				JButton nombreChat = new JButton("Chat con " + nombreUsuario);
				nombreChat.setBounds(-5,0,151,35);
				nombreChat.setBackground(new Color(255, 255, 255));
				nombreChat.setBorderPainted(false);
				nombreChat.setHorizontalAlignment(SwingConstants.LEFT);
				nombreChat.setContentAreaFilled(true);
				nombreChat.setFont(new Font("Segoe UI", Font.BOLD, 13));
				panelChat.add(nombreChat);
							
				countChats++;
			}
			
			while (rsGrupo.next())
			{
				String nomGrupo = rsGrupo.getString("nombre");
				String descripcion = rsGrupo.getString("descripcion");
				
				// Creacion del elemento de la interfaz que contiene el grupo
				JPanel panelChat = new JPanel();				
				panelChat.setBackground(new Color(255, 255, 255));
				panelChat.setBounds(10, positionUI, 146, 52);
				panelChat.setBorder(new EmptyBorder(10, 10, 10, 10));
				panelChat.setLayout(null);
				padre.add(panelChat);
				
				positionUI += incremento;
				
				JButton nombreChat = new JButton(nomGrupo);
				nombreChat.setBounds(-5,0,151,35);
				nombreChat.setBackground(new Color(255, 255, 255));
				nombreChat.setBorderPainted(false);
				nombreChat.setHorizontalAlignment(SwingConstants.LEFT);
				nombreChat.setContentAreaFilled(true);
				nombreChat.setFont(new Font("Segoe UI", Font.BOLD, 13));
				panelChat.add(nombreChat);
				
				
				JLabel descripcionChat = new JLabel(descripcion);
				descripcionChat.setBounds(10,30,146,20);
				descripcionChat.setForeground(Color.GRAY);
				descripcionChat.setFont(new Font("Segoe UI", Font.PLAIN, 9));
				panelChat.add(descripcionChat);
				
				countChats++;
			}
			
			padre.revalidate();
			
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
	
	private ResultSet conversUser()
	{
		Connection cn = login.getConexion();
		int id_usuario = login.getIdUsuario();
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
	
	private ResultSet gruposUser()
	{
		Connection cn = login.getConexion();
		int id_usuario = login.getIdUsuario();
		String consultaSQL = "SELECT usuario.id_usuario, participa.id_chat, grupo.nombre, grupo.descripcion FROM usuario INNER JOIN participa USING (id_usuario) INNER JOIN grupo USING (id_chat) WHERE usuario.id_usuario = ?";
		
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
}
