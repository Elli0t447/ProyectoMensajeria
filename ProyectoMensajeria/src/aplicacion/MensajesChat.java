package aplicacion;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JPanel;

import interfaz.PrincipalUI;

public class MensajesChat 
{
	private static int positionUI;
	
	public static int getPositionUI() { return positionUI; }
	
	public void cargarChat(int id_c, JPanel parent)
	{
		PrincipalUI.tab_noChat.setVisible(false);
		PrincipalUI.tab_chat.setVisible(true);	
		
		ResultSet rs = mensajesUserEnChat(id_c);
		
		try 
		{
			positionUI = 10;
			int incremento = 54;
			
			while (rs.next())
			{
				String mensaje = rs.getString("texto");
				int usuario = rs.getInt("id_usuario");
				
				String nomUsuario = LoginUsuario.nombreUserPorId(usuario);
				
				JPanel msjPanel = new JPanel();
				msjPanel.setLayout(null);
				msjPanel.setBorder(null);
				msjPanel.setBackground(Color.WHITE);
				msjPanel.setBounds(12, positionUI, 590, 47);
				parent.add(msjPanel);
				
				positionUI += incremento;
				
				JLabel usuarioMensaje = new JLabel(nomUsuario);
				usuarioMensaje.setForeground(new Color(65, 105, 225));
				usuarioMensaje.setFont(new Font("Tahoma", Font.BOLD, 14));
				usuarioMensaje.setBounds(10, 10, 87, 13);
				msjPanel.add(usuarioMensaje);
				
				JLabel textoMensaje = new JLabel(mensaje);
				textoMensaje.setForeground(Color.DARK_GRAY);
				textoMensaje.setFont(new Font("Segoe UI", Font.PLAIN, 12));
				textoMensaje.setBounds(12, 26, 570, 13);
				msjPanel.add(textoMensaje);
			}
			
			PrincipalUI.scrollMensaje.getViewport().setViewPosition(new Point(1, MensajesChat.getPositionUI()));
			parent.setPreferredSize(new Dimension(560, positionUI));
			parent.revalidate();
			parent.repaint();
			
		}
		catch (SQLException e) 
		{
			System.out.println("Error de SQL");
			e.printStackTrace();
		}
	}
	
	public void enviarMensaje(int id_c, int id_u, String mensaje)
	{
		Connection cn = LoginUsuario.getConexion();
		
		try
		{			
			if (cn != null)
			{
				PreparedStatement pst = cn.prepareStatement("INSERT INTO mensaje (id_mensaje, id_chat, texto, fecha, id_usuario) VALUES (DEFAULT,?,?,now(),?)");
				pst.setInt(1, id_c);
				pst.setString(2, mensaje);
				pst.setInt(3, id_u);
                pst.executeUpdate();
			}
			else
			{
				System.out.println("Conexión nula.");
			}
		}
		catch (SQLException e)
		{
			System.out.println("Usuario con el mismo nombre ya existe");
		}
	}
	
	private ResultSet mensajesUserEnChat(int id_c)
	{
		Connection cn = LoginUsuario.getConexion();
		String consultaSQL = "SELECT texto, id_usuario, id_chat FROM mensaje WHERE id_chat = ? ORDER BY fecha ASC";
		
		ResultSet rs = null;
		
		try 
	    {
	        PreparedStatement pst = cn.prepareStatement(consultaSQL);
	        pst.setInt(1, id_c);
	        rs = pst.executeQuery();	        
	    } 
	    catch (SQLException ex) 
	    {
	        System.out.println("Error al seleccionar datos");
	    }
	
		return rs;
	}
}
