package aplicacion;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import interfaz.PrincipalUI;

public class ChatsUsuario 
{
	private int idChat;
	private String nomChat;
	private static int positionUI;
	
	public boolean mostrarListaChats(JPanel padre)
	{
		ResultSet rsConver = conversUser();
		ResultSet rsGrupo = gruposUser();
		
		int countChats = 0;
		positionUI = 20;
		int incremento = 60;
		
		try 
		{
			while (rsConver.next())
			{
				idChat = rsConver.getInt("id_chat");
				int idUser1 = rsConver.getInt("id_usu1");
				int idUser2 = rsConver.getInt("id_usu2");
				
				int usuarioFinal;
				
				if (idUser1 != LoginUsuario.getIdUsuario())
				{
					usuarioFinal = idUser1;				
				}
				else
				{
					usuarioFinal = idUser2;
				}
				
				nomChat =  "Chat con " + LoginUsuario.nombreUserPorId(usuarioFinal);
				
				// Creacion del elemento de la interfaz que contiene la conversacion
				JPanel panelChat = new JPanel();				
				panelChat.setBackground(new Color(255, 250, 250));
				panelChat.setBounds(10, positionUI, 146, 52);
				panelChat.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(227, 227, 227)));
				panelChat.setLayout(null);
				padre.add(panelChat);
				
				positionUI += incremento;
				
				String nomIndividual = nomChat;
				int idIndividual = idChat;
				JButton nombreChat = new JButton(nomChat);
				nombreChat.setBounds(-5,0,151,35);
				nombreChat.setBackground(new Color(255, 250, 250));
				nombreChat.setBorderPainted(false);
				nombreChat.setFocusPainted(false);
				nombreChat.setHorizontalAlignment(SwingConstants.LEFT);
				nombreChat.setContentAreaFilled(true);
				nombreChat.setFont(new Font("Segoe UI", Font.BOLD, 13));			
				nombreChat.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) 
					{		
						MensajesChat mC = new MensajesChat();
						PrincipalUI.nombreChat.setText(nomIndividual);
						PrincipalUI.nombreChat.setBounds(10, 12, 190, 33);
						PrincipalUI.descripcionChat.setText("");
						PrincipalUI.containerMsj.removeAll();
						PrincipalUI.setChatEnvio(idIndividual);
						
						mC.cargarChat(idIndividual, PrincipalUI.containerMsj);	
						
					}
				});
				panelChat.add(nombreChat);
				countChats++;
			}
			
			while (rsGrupo.next())
			{
				nomChat = rsGrupo.getString("nombre");
				String descripcion = rsGrupo.getString("descripcion");
				idChat = rsGrupo.getInt("id_chat");
				
				// Creacion del elemento de la interfaz que contiene el grupo
				JPanel panelChat = new JPanel();				
				panelChat.setBackground(new Color(255, 250, 250));
				panelChat.setBounds(10, positionUI, 146, 52);
				panelChat.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(227, 227, 227)));
				panelChat.setLayout(null);
				padre.add(panelChat);
				
				positionUI += incremento;
				
				String nomIndividual = nomChat;
				int idIndividual = idChat;
				
				JLabel descripcionChat = new JLabel(descripcion);
				descripcionChat.setBounds(10,25,146,20);
				descripcionChat.setForeground(Color.GRAY);
				descripcionChat.setFont(new Font("Segoe UI", Font.PLAIN, 9));
				panelChat.add(descripcionChat);
				
				JButton nombreChat = new JButton(nomChat);
				nombreChat.setBounds(-5,0,151,35);
				nombreChat.setBackground(new Color(255, 250, 250));
				nombreChat.setBorderPainted(false);
				nombreChat.setFocusPainted(false);
				nombreChat.setHorizontalAlignment(SwingConstants.LEFT);
				nombreChat.setContentAreaFilled(true);
				nombreChat.setFont(new Font("Segoe UI", Font.BOLD, 13));
				nombreChat.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) 
					{											
						MensajesChat mC = new MensajesChat();	
						PrincipalUI.nombreChat.setText(nomIndividual);
						PrincipalUI.nombreChat.setBounds(10, 4, 190, 33);
						PrincipalUI.descripcionChat.setText(descripcion);
						PrincipalUI.containerMsj.removeAll();
						PrincipalUI.setChatEnvio(idIndividual);
						mC.cargarChat(idIndividual, PrincipalUI.containerMsj);	
					}
				});
				panelChat.add(nombreChat);	
				
							
				countChats++;
			}
			
			padre.setPreferredSize(new Dimension(150, positionUI - 8));
			padre.revalidate();
			padre.repaint();
			
			if (countChats > 0)
			{
				return true;
			}
		} 
		catch (SQLException e) 
		{
			System.out.println("Error SQL");
			e.printStackTrace();
		}
		
		return false;
	}
	
	private ResultSet conversUser()
	{
		Connection cn = LoginUsuario.getConexion();
		int id_usuario = LoginUsuario.getIdUsuario();
		String consultaSQL = "SELECT usuario.id_usuario, nombre, conversacion.id_chat, id_usu1, id_usu2 FROM usuario INNER JOIN conversacion ON usuario.id_usuario = conversacion.id_usu1 OR usuario.id_usuario = conversacion.id_usu2 WHERE usuario.id_usuario = ?";
		
		ResultSet rs = null;
		
		try 
	    {
	        PreparedStatement pst = cn.prepareStatement(consultaSQL);
	        pst.setInt(1, id_usuario);
	        rs = pst.executeQuery();
	        
	    } 
	    catch (SQLException ex) 
	    {
	        System.out.println("Error al seleccionar datos");
	    }
		
		return rs;
	}
	
	private ResultSet gruposUser()
	{
		Connection cn = LoginUsuario.getConexion();
		int id_usuario = LoginUsuario.getIdUsuario();
		String consultaSQL = "SELECT usuario.id_usuario, participa.id_chat, grupo.nombre, grupo.descripcion FROM usuario INNER JOIN participa USING (id_usuario) INNER JOIN grupo USING (id_chat) WHERE usuario.id_usuario = ?";
		
		ResultSet rs = null;
		
		try 
	    {
	        PreparedStatement pst = cn.prepareStatement(consultaSQL);
	        pst.setInt(1, id_usuario);
	        rs = pst.executeQuery();
	        
	    } 
	    catch (SQLException ex) 
	    {
	        System.out.println("Error al seleccionar datos");
	    }
	
		return rs;
	}
}
