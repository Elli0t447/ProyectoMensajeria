package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion 
{
	// La conexión a la base de datos a la que se referencia
	public static Connection cn;
	
	// Conectar a la base de datos
	public static void ConectarBD()
	{	
		String ruta = "jdbc:postgresql://localhost:5432/mensajeria";
		String user = "mensajeriaUser";
		String password = "msj";
		
		try 
		{
			Class.forName("org.postgresql.Driver");
			cn = DriverManager.getConnection(ruta,user,password);
		}
		catch (SQLException | ClassNotFoundException e) 
		{
			System.out.println("Error de conexión a la base de datos.");
		}
	}
	
	// Cierra la conexión a la base de datos
	public static void CerrarConexionBD()
	{
		try 
		{
			cn.close();
			cn = null;
		} 
		catch (SQLException e) 
		{
			System.out.println("Error al cerrar conexión de la base de datos");
		}
	}
}
