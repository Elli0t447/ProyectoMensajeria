package dialogos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import aplicacion.Conexion;
import aplicacion.LoginUsuario;
import interfaz.PrincipalUI;

public class AjustesUsuario 
{
	private Connection cn;
	
	public AjustesUsuario()
	{
		cn = Conexion.Conectar();
	}
	
	public void datosAjustes()
	{
		String nomUsu = LoginUsuario.nombreUserPorId(LoginUsuario.getIdUsuario());
		
		PrincipalUI.usuarioLabelAjustes.setText(nomUsu);
		PrincipalUI.iDlabel.setText("(ID " + LoginUsuario.getIdUsuario() + ")");
		PrincipalUI.nombreField.setText(nomUsu);
		PrincipalUI.passwordField.setText(LoginUsuario.getContraseña());
	}
	
	public void actualizarUsuario(String nom_usu, String pass, int id_usu)
	{
		try
		{			
			if (cn != null)
			{
				PreparedStatement pst = cn.prepareStatement("UPDATE usuario SET nombre = ?, contra = ? WHERE id_usuario = ?");
				pst.setString(1, nom_usu);
				pst.setString(2, pass);
				pst.setInt(3, id_usu);
                pst.executeUpdate();
			}
			else
			{
				System.out.println("Conexión nula.");
			}
		}
		catch (SQLException e)
		{
			System.out.println("Error SQL (actualizarUsuario)");
		}
	}
	
	public void borrarUsuario(int id_usu)
	{	
		try
		{			
			if (cn != null)
			{
				PreparedStatement pst = cn.prepareStatement("DELETE FROM usuario WHERE id_usuario = ?");
				pst.setInt(1, id_usu);
	            pst.executeUpdate();
			}
			else
			{
				System.out.println("Conexión nula.");
			}
		}
		catch (SQLException e)
		{
			System.out.println("Error SQL (borrarUsuario)");
		}
	}
}
