package controladorDialogs;

import static modelo.Conexion.cn;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import controladorMain.LoginUsuario;
import vista.PrincipalUI;

public class AjustesUsuario 
{
	// Rellenar los datos de la pantalla de ajustes
	public void datosAjustes()
	{
		String nomUsu = LoginUsuario.nombreUserPorId(LoginUsuario.getIdUsuarioConectado());
		
		PrincipalUI.usuarioLabelAjustes.setText(nomUsu);
		PrincipalUI.idlabel.setText("(ID " + LoginUsuario.getIdUsuarioConectado() + ")");
		PrincipalUI.nombreField.setText(nomUsu);
	}
	
	// Actualiza los datos de un usuario por su id
	public void actualizarUsuario(String nom_usu, String pass, int id_usu)
	{
		try
		{			
			pass = LoginUsuario.encriptarContra(pass);
			
			PreparedStatement pst = cn.prepareStatement("UPDATE usuario SET nombre = ?, contra = ? WHERE id_usuario = ?");
			pst.setString(1, nom_usu);
			pst.setString(2, pass);
			pst.setInt(3, id_usu);
            pst.executeUpdate();
		}
		catch (SQLException e)
		{
			System.out.println("Error SQL (actualizarUsuario)");
		}
	}
	
	// Borrar un usuario por su id
	public void borrarUsuario(int id_usu)
	{	
		try
		{			
			PreparedStatement pst = cn.prepareStatement("DELETE FROM usuario WHERE id_usuario = ?");
			pst.setInt(1, id_usu);
            pst.executeUpdate();
		}
		catch (SQLException e)
		{
			System.out.println("Error SQL (borrarUsuario)");
		}
	}
}
