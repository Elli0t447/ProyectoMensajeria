package controladorDialogs;

import static modelo.Conexion.cn;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JComboBox;

import controladorMain.LoginUsuario;


public class AnyadirAmigo 
{		
	// Enviar una solicitud a un usuario (id_1 sera quien la manda id_2 quien la recibe)
	public void enviarSolicitud(int id_u, int id_u2)
	{	
		try
		{	
			PreparedStatement pst = cn.prepareStatement("INSERT INTO amistad (id_usu1, id_usu2, aceptada) VALUES (?,?,false)");
			pst.setInt(1, id_u);
			pst.setInt(2, id_u2);
            pst.executeUpdate();
		}
		catch (SQLException e)
		{
			
			System.out.println("Error SQL (enviarSolicitud)");
		}
	}
	
	// Crea una nueva conversacion entre dos usuarios
	public void insertarChat(int id_u, int id_u2)
	{
		try
		{			
			cn.setAutoCommit(false);
			
			PreparedStatement pst = cn.prepareStatement("BEGIN;INSERT INTO chat VALUES (DEFAULT); INSERT INTO conversacion (id_chat, id_usu1, id_usu2) SELECT currval('chat_id_chat_seq'), ?,?; END;");	
			pst.setInt(1, id_u);
			pst.setInt(2, id_u2);
            pst.executeUpdate();
            
            cn.commit();
            cn.setAutoCommit(true);
		}
		catch (SQLException e)
		{
			System.out.println("Error SQL (insertarChat)");
		}
	}
	
	// Rellena la ComboBox con todos los usuarios de la base de datos de los que no eres amigo
	public void rellenarBox(JComboBox<String> box)
	{	
		ResultSet rsNoAmigos = noAmigosUser(LoginUsuario.getIdUsuarioConectado());
		
		try
		{
			while (rsNoAmigos.next())
			{
				String nomUsu = rsNoAmigos.getString("nombre");
				box.addItem(nomUsu);
			}
		} 
		catch (SQLException e) 
		{
			System.out.println("Error SQL (rellenarBox)");
		}
	}
	
	// Devuelve todos los usuarios de la base de datos que no son amigos del usuario
	private ResultSet noAmigosUser(int id_u)
	{
		ResultSet rsNoAmigos = null;
		
		try
	    {
			PreparedStatement pst = cn.prepareStatement("SELECT id_usuario, nombre FROM usuario WHERE id_usuario NOT IN (SELECT id_usu2 FROM amistad WHERE id_usu1 = ?) AND id_usuario NOT IN (SELECT id_usu1 FROM amistad WHERE id_usu2 = ?) AND id_usuario != ?");
	        pst.setInt(1, id_u);
	        pst.setInt(2, id_u);
	        pst.setInt(3, id_u);
	        rsNoAmigos = pst.executeQuery();
	        
	    } 
	    catch (SQLException ex) 
	    {
	        System.out.println("Error SQL (noAmigosUser)");
	    }
		
		return rsNoAmigos;
	}
}
