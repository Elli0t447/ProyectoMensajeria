package aplicacion;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginUsuario 
{
	// Nombre usuario logueado
	private static String usuario;
	// Contraseña usuario logueado
	private static String contraseña;
	// ID usuario logueado
	private static int id_usuario;
	
	private static Connection cn;
	
	public static String getNombreUsuarioConectado() { return usuario; }
	public static String getContraseñaUsrConectado() { return contraseña; }
	public static int getIdUsuarioConectado() { return id_usuario; }
	
	public LoginUsuario(String user, String pass)
	{
		usuario = user;
		contraseña = encriptarContra(pass);
		cn = Conexion.Conectar();
	}
	
	// Devuelve datos de un usuario por su nombre
	private ResultSet usuarioRegistrado()
	{
	    ResultSet rs = null;

	    try 
	    {
	        PreparedStatement pst = cn.prepareStatement("SELECT * FROM usuario WHERE nombre = ?");
	        pst.setString(1, usuario);
	        rs = pst.executeQuery();
	        
	    } 
	    catch (SQLException ex) 
	    {
	        System.out.println("Error SQL (usuarioRegistrado)");
	    }
	    
		return rs;
	}
	
	// Comprueba si existe el usuario introducido en la base de datos
	public boolean comprobarExistenciaUser()
	{
		try 
		{
			ResultSet rs = usuarioRegistrado();
			
			while (rs.next())
			{
				String user = rs.getString("nombre");
				String pass = rs.getString("contra");				
				id_usuario = rs.getInt("id_usuario");
				
				if (user.equals(usuario) && pass.equals(contraseña))
				{
					System.out.println("Usuario " + usuario + " existe en la base de datos!");
					return true;
				}
				else
				{
					System.out.println("Usuario o contraseña incorrecta!");
				}
			}	
		} 
		catch (SQLException e) 
		{
			System.out.println("Error de SQL (comprobarExistenciaUser)");
		}
		return false;
		
	}
	
	// Devolver nombre usuario por id usuario
	public static String nombreUserPorId(int id_u)
	{
	    ResultSet rs = null;
	    String nombreResult = null;
	    try 
	    {
	        PreparedStatement pst = cn.prepareStatement("SELECT * FROM usuario WHERE id_usuario = ?");
	        pst.setInt(1, id_u);
	        rs = pst.executeQuery();
	        
	        while (rs.next())
	        {
	        	 nombreResult = rs.getString("nombre");        	
	        }
	        
	    } 
	    catch (SQLException ex) 
	    {
	        System.out.println("Error SQL (nombreUserPorId)");
	    }
	    
		return nombreResult;
	}

	// Devolver id usuario por nombre usuario
	public static int idUserPorNombre(String nom_u)
	{
	    ResultSet rs = null;
	    int nombreResult = 0;
	    try 
	    {
	        PreparedStatement pst = cn.prepareStatement("SELECT * FROM usuario WHERE nombre = ?");
	        pst.setString(1, nom_u);
	        rs = pst.executeQuery();
	        
	        while (rs.next())
	        {
	        	 nombreResult = rs.getInt("id_usuario");        	
	        }
	        
	    } 
	    catch (SQLException ex) 
	    {
	        System.out.println("Error SQL (idUserPorNombre)");
	    }
	    
		return nombreResult;
	}
		
	// Inserta en usuario los datos de los textfield de LoginUI
	public void nuevaCuenta()
	{
		try
		{			
			PreparedStatement pst = cn.prepareStatement("INSERT INTO usuario (id_usuario, nombre, contra) VALUES (DEFAULT,?,?)");
			pst.setString(1, usuario);
			pst.setString(2, contraseña);
            pst.executeUpdate();
		}
		catch (SQLException e)
		{
			System.out.println("ErrorSQL (nuevaCuenta)");
		}
	}
	
	// Devuelve todos los nombres de usuario registrados en la base de datos
	public static ResultSet allNomUsuarios()
	{
		ResultSet rs = null;

	    try 
	    {
	        PreparedStatement pst = cn.prepareStatement("SELECT nombre FROM usuario");
	        rs = pst.executeQuery();
	        
	    } 
	    catch (SQLException ex) 
	    {
	        System.out.println("Error SQL (allNomUsuarios)");
	    }
	    
		return rs;
	}
	
	// Se le pasa un id_usuario y un id_chat y devuelve true o false en función de si administra ese chat o no
	public static boolean isAdmin(int id_usuario, int id_chat)
	{	
		ResultSet rs;
		boolean admin = false;	
		
		try
		{	
			if (cn != null)
			{
				PreparedStatement pst = cn.prepareStatement("SELECT * FROM participa WHERE id_usuario = ? AND id_chat = ?");
				pst.setInt(1, id_usuario);
				pst.setInt(2, id_chat);
                rs = pst.executeQuery();
                
                while (rs.next())
                {
                	admin = rs.getBoolean("administra");
                }
                
                if (admin)
                {
                	return true;
                }
                else
                {
                	return false;
                }
			}
			else
			{
				System.out.println("Conexión nula.");
			}
		}
		catch (SQLException e)
		{
			System.out.println("Error SQL (isAdmin)");
		}
		
		return false;
	}
	
	// Devuelve la contraseña encriptada con MD5
	public static String encriptarContra(String contra)
	{
		String encriptada = null;	
		MessageDigest m;
		try 
		{
			m = MessageDigest.getInstance("MD5");  
	        m.update(contra.getBytes());  
	          
	        
	        byte[] bytes = m.digest();  
	           
	        StringBuilder str = new StringBuilder();  
	       
	        for(int i = 0; i < bytes.length; i++)  
	        {  
	            str.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));  
	        }  
	                    
	        encriptada = str.toString();  
		} 
		catch (NoSuchAlgorithmException e) 
		{
			System.out.println("Error encriptación MD5");
		}  
		
		return encriptada;
	}
}
