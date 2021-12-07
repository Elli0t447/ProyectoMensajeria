package dialogos;

import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import aplicacion.Conexion;
import aplicacion.LoginUsuario;
import interfaz.InfoGrupoUI;
import interfaz.PrincipalUI;

public class InfoGrupo 
{
	private Connection cn;
	
	public InfoGrupo()
	{
		cn = Conexion.Conectar();
	}
	
	public void mostrarInfoGrupo(JLabel tituloLabel, JLabel fechaLabel, JLabel descripLabel)
	{
		ResultSet rs = grupoPorId(PrincipalUI.getCurrentChat());
		
		try 
		{
			while (rs.next())
			{
				String nom = rs.getString("nombre");
				String desc = rs.getString("descripcion");
				String fecha = rs.getString("fecha_creacion");
				
				tituloLabel.setText(nom);
				descripLabel.setText(desc);
				fechaLabel.setText("Fecha de creación: " + fecha);
			}	
		} 
		catch (SQLException e) 
		{
			System.out.println("Error SQL (mostrarInfoGrupo)");
			e.printStackTrace();
		}
	}
	
	public void mostrarParticipantes(JPanel padre)
	{
		ResultSet rs = participantesGrupo(PrincipalUI.getCurrentChat());
		
		try 
		{
			int positionUI = 10;
			int increment = 44;
			
			while (rs.next())
			{
				int id_usu = rs.getInt("id_usuario");
				String nomUsu = LoginUsuario.nombreUserPorId(id_usu);
				
				boolean administra = rs.getBoolean("administra");
				
				JPanel panelUsu = new JPanel();
				panelUsu.setBounds(10, positionUI, 210, 34);
				panelUsu.setLayout(null);
				panelUsu.setBackground(new Color(255, 250, 250));
				padre.add(panelUsu);
				
				positionUI += increment;
				
				JLabel nombreParticipante = new JLabel(nomUsu);
				nombreParticipante.setFont(new Font("Segoe UI", Font.PLAIN, 14));
				nombreParticipante.setBounds(10, 10, 133, 13);
				panelUsu.add(nombreParticipante);
						
				JLabel adminIcon = new JLabel("Admin");
				
				if (administra)
				{				
					adminIcon.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 128, 0)));
					adminIcon.setForeground(new Color(0, 128, 0));
					adminIcon.setBounds(148, 11, 35, 13);
					panelUsu.add(adminIcon);
				}
				
				
			   if (LoginUsuario.isAdmin(LoginUsuario.getIdUsuario(), PrincipalUI.getCurrentChat()) == true)
			   {
					if (id_usu == LoginUsuario.getIdUsuario())
					{
						adminIcon.setBounds(168, 11, 35, 13);
					}
					else
					{
						JLabel iconBorrar = new JLabel("");
						iconBorrar.setIcon(new ImageIcon(InfoGrupoUI.class.getResource("/img/borrar.png")));
						iconBorrar.setBounds(186, 5, 18, 24);
						iconBorrar.addMouseListener(new MouseAdapter() 
						{
							@Override
							public void mouseClicked(MouseEvent e)
							{
								int opcion = JOptionPane.showConfirmDialog(null, "¿Quieres eliminar a " + LoginUsuario.nombreUserPorId(id_usu) + " del grupo?", "Eliminar", JOptionPane.YES_NO_OPTION);

								if (opcion == 0)
								{	
									removeParticipante(PrincipalUI.getCurrentChat(), id_usu);
									
									// Recargar los participantes del grupo al borrar un usuario
									InfoGrupoUI.getParticipantesContainer().removeAll();
									mostrarParticipantes(InfoGrupoUI.getParticipantesContainer());
									
									InfoGrupoUI.getParticipantesContainer().revalidate();
									InfoGrupoUI.getParticipantesContainer().repaint();
									
								}
							}
						});
					
						panelUsu.add(iconBorrar);
					}
			   }
			   else
			   {
				   adminIcon.setBounds(168, 11, 35, 13);
			   }
			   
			}	
			
			padre.revalidate();
			padre.repaint();
		} 
		catch (SQLException e) 
		{
			System.out.println("Error SQL (mostrarParticipantes)");
			e.printStackTrace();
		}
	}
	
	private ResultSet participantesGrupo(int id_chat)
	{
		ResultSet rs = null;
		
		try
		{
			PreparedStatement pst = cn.prepareStatement("SELECT * FROM participa WHERE id_chat = ?");
			pst.setInt(1, id_chat);
	        rs = pst.executeQuery();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println("Error SQL (participantesGrupo)");
		}
		
		return rs;
	}
	
	private ResultSet grupoPorId(int id_chat)
	{
		ResultSet rs = null;
		
		try
		{
			PreparedStatement pst = cn.prepareStatement("SELECT * FROM grupo WHERE id_chat = ?");
			pst.setInt(1, id_chat);
	        rs = pst.executeQuery();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println("Error SQL (grupoPorId)");
		}
		
		return rs;
	}
	
	public void removeParticipante(int id_chat, int id_usu)
	{
		try
		{			
			if (cn != null)
			{
				PreparedStatement pst = cn.prepareStatement("DELETE FROM participa WHERE id_chat = ? AND id_usuario = ?");
                pst.setInt(1, id_chat);
                pst.setInt(2, id_usu);
				pst.executeUpdate();
			}
			else
			{
				System.out.println("Conexión nula. remove");
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("ErrorSQL (removeParticipante)");
		}		
	}
	
	public void salirGrupo(int id_chat, int id_usuario)
	{
		try
		{			
			if (cn != null)
			{
				PreparedStatement pst = cn.prepareStatement("DELETE FROM participa WHERE id_chat = ? AND id_usuario = ?");
				pst.setInt(1, id_chat);
				pst.setInt(2, id_usuario);
	            pst.executeUpdate();
			}
			else
			{
				System.out.println("Conexión nula.");
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("Error SQL (salirGrupo)");
		}
	}
}
