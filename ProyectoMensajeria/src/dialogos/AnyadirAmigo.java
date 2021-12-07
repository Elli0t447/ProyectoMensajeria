package dialogos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JComboBox;

import aplicacion.Conexion;
import aplicacion.LoginUsuario;

public class AnyadirAmigo 
{
	private static Connection cn;

	public AnyadirAmigo()
	{
		cn = Conexion.Conectar();
	}
		
	public void enviarSolicitud(int id_u, int id_u2)
	{	
		try
		{			
			if (cn != null)
			{
				PreparedStatement pst = cn.prepareStatement("INSERT INTO amistad (id_usu1, id_usu2, aceptada) VALUES (?,?,false)");
				pst.setInt(1, id_u);
				pst.setInt(2, id_u2);
                pst.executeUpdate();
			}
			else
			{
				System.out.println("Conexión nula.");
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("Error SQL (enviarSolicitud)");
		}
	}
	
	public void insertarChat(int id_u, int id_u2)
	{
		try
		{			
			if (cn != null)
			{
				PreparedStatement pst = cn.prepareStatement("BEGIN;INSERT INTO chat VALUES (DEFAULT); INSERT INTO conversacion (id_chat, id_usu1, id_usu2) SELECT currval('chat_id_chat_seq'), ?,?; END;");
				pst.setInt(1, id_u);
				pst.setInt(2, id_u2);
                pst.executeUpdate();
			}
			else
			{
				System.out.println("Conexión nula.");
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("Error SQL (insertarChat)");
		}
	}
	
	public void rellenarBox(JComboBox<String> box)
	{
		ResultSet rs = noAmigosUser(LoginUsuario.getIdUsuario());
		
		try 
		{
			while (rs.next())
			{
				String nomUsu = rs.getString("nombre");
				box.addItem(nomUsu);
			}
		} 
		catch (SQLException e) 
		{
			System.out.println("Error de SQL (rellenarBox)");
			e.printStackTrace();
		}
	}
	
	private ResultSet noAmigosUser(int id_u)
	{
		ResultSet rs = null;
		
		try 
	    {
	        PreparedStatement pst = cn.prepareStatement("SELECT id_usuario, nombre FROM usuario WHERE id_usuario NOT IN (SELECT id_usu2 FROM amistad WHERE id_usu1 = ?) AND id_usuario NOT IN (SELECT id_usu1 FROM amistad WHERE id_usu2 = ?) AND id_usuario != ?");
	        pst.setInt(1, id_u);
	        pst.setInt(2, id_u);
	        pst.setInt(3, id_u);
	        rs = pst.executeQuery();
	        
	    } 
	    catch (SQLException ex) 
	    {
	        System.out.println("Error al seleccionar datos");
	    }
		
		return rs;
	}
}
