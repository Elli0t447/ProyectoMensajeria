package aplicacion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion 
{
	// Devuelve la conexion a la base de datos
	public static Connection Conectar()
	{
		Connection cn = null;
		
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
			System.out.println("Error de conexión.");
		}
		
		return cn;
	}
}
