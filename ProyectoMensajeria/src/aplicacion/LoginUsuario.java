package aplicacion;

import java.sql.*;

public class LoginUsuario 
{
	private String usuario, contrase�a;
	private Connection cn;
	
	public String getUsuario() { return usuario; }
	
	public LoginUsuario(String user, String pass)
	{
		usuario = user;
		contrase�a = pass;
		cn = Conexion.Conectar();
	}
	
	public boolean comprobarUser()
	{
		try 
		{
			ResultSet rs = usuarioRegistrado();
			
			while (rs.next())
			{
				String user = rs.getString("nombre");
				String pass = rs.getString("contra");
				
				if (user.equals(usuario) && pass.equals(contrase�a))
				{
					System.out.println("Usuario existe en la base de datos");
					return true;
				}
				else
				{
					System.out.println("Usuario o contrase�a incorrecta!");
				}
			}	
		} 
		catch (SQLException e) 
		{
			System.out.println("Error de SQL");
		}
		return false;
		
	}
	
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
	        System.out.println("Error al seleccionar datos");
	    }
	    
		return rs;
	}
	
	
	public void nuevaCuenta()
	{
		try
		{			
			if (cn != null)
			{
				PreparedStatement pst = cn.prepareStatement("INSERT INTO usuario (id_usuario, nombre, contra) VALUES (DEFAULT,?,?)");
				pst.setString(1, usuario);
				pst.setString(2, contrase�a);
                pst.executeUpdate();
			}
			else
			{
				System.out.println("Conexi�n nula.");
			}
		}
		catch (SQLException e)
		{
			System.out.println("Usuario con el mismo nombre ya existe");
		}
	}
}
