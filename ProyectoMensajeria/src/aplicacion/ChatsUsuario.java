package aplicacion;

import java.awt.Color;
import java.sql.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
		
		try 
		{
			while (rsConver.next())
			{
				countChats++;
			}
			
			while (rsGrupo.next())
			{
				countChats++;
			}
			
			for (int i = 0; i < countChats; i++)
			{
				JPanel panelChat = new JPanel();
				panelChat.setBounds(10, 0, 0, 0);
				panelChat.setBackground(new Color(255, 245, 245));
				panelChat.setBorder(new EmptyBorder(1, 1, 1, 1));
				panelChat.setLayout(null);
				padre.add(panelChat);
				
				
				JLabel nombreChat = new JLabel("Chat");
				panelChat.add(nombreChat);
				
				JLabel descripcionChat = new JLabel("Descripcion");
				panelChat.add(descripcionChat);
				
				padre.revalidate();
			}
			
			if (countChats > 0)
			{
				return true;
			}
		} 
		catch (SQLException e) 
		{
			System.out.println("Error SQL");
		}
		
		return false;
	}
	
	private ResultSet conversUser()
	{
		Connection cn = login.getConexion();
		String usuario = login.getUsuario();
		String consultaSQL = "SELECT usuario.id_usuario, nombre, conversacion.id_chat FROM usuario INNER JOIN conversacion ON usuario.id_usuario = conversacion.id_usu1 OR usuario.id_usuario = conversacion.id_usu2 WHERE nombre = ?";
		
		ResultSet rs = null;
		
		try 
	    {
	        PreparedStatement pst = cn.prepareStatement(consultaSQL);
	        pst.setString(1, usuario);
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
		String usuario = login.getUsuario();
		String consultaSQL = "SELECT usuario.id_usuario, nombre, participa.id_chat FROM usuario INNER JOIN participa USING (id_usuario) WHERE nombre = ?";
		
		ResultSet rs = null;
		
		try 
	    {
	        PreparedStatement pst = cn.prepareStatement(consultaSQL);
	        pst.setString(1, usuario);
	        rs = pst.executeQuery();
	        
	    } 
	    catch (SQLException ex) 
	    {
	        System.out.println("Error al seleccionar datos");
	    }
	
		return rs;		
	}
}
