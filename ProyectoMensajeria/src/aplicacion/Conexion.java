package aplicacion;

import java.sql.*;

public class Conexion 
{
	public static Connection Conectar()
	{
		Connection cn = null;
		
		String ruta = "jdbc:postgresql://localhost:5432/mensajeria";
		String user = "postgres";
		String password = "";
		
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
