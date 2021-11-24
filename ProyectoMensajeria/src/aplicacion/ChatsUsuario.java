package aplicacion;

import java.awt.Color;
import java.awt.Font;
import java.sql.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet.FontAttribute;

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
				
				int pos = 20;
				int incremento = 20;
				
				for (int j = 0; j < countChats; j++ )
				{			
					JPanel panelChat = new JPanel();				
					panelChat.setBackground(new Color(255, 255, 255));
					panelChat.setBounds(10, incremento, 146, 52);
					panelChat.setBorder(new EmptyBorder(10, 10, 10, 10));
					panelChat.setLayout(null);
					padre.add(panelChat);
					
					pos = incremento;
					incremento = pos + 60;	
					
					JButton nombreChat = new JButton("Nombre chat");
					nombreChat.setBounds(-5,0,151,35);
					nombreChat.setBackground(new Color(255, 255, 255));
					nombreChat.setBorderPainted(false);
					nombreChat.setHorizontalAlignment(SwingConstants.LEFT);
					nombreChat.setContentAreaFilled(true);
					nombreChat.setFont(new Font("Segoe UI", Font.BOLD, 13));
					panelChat.add(nombreChat);
					
					
					JLabel descripcionChat = new JLabel("Descripcion");
					descripcionChat.setBounds(10,30,146,20);
					descripcionChat.setForeground(Color.GRAY);
					descripcionChat.setFont(new Font("Segoe UI", Font.PLAIN, 9));
					panelChat.add(descripcionChat);
				}
		
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
