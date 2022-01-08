package controladorDialogs;

import static modelo.Conexion.cn;

import java.awt.Color;
import java.awt.Font;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import controladorMain.ChatsUsuario;
import controladorMain.LoginUsuario;
import vista.InfoGrupoUI;

public class InfoGrupo 
{
	// Lista de usuarios seleccionados en la lista de la interfaz
	private static ArrayList<Integer> cambiarAdmins = new ArrayList<>();
	
	// Elementos de la interfaz de InfoGrupoUI pasados por el constructor
	private static JLabel tituloLabel;
	private static JLabel fechaLabel;
	private static JLabel descripLabel;
	
	public static JLabel getTituloLabel() { return tituloLabel; }
	public static JLabel getFechaLabel() { return fechaLabel; }
	public static JLabel getDescripLabel() { return descripLabel; }
	
	public InfoGrupo(JLabel titulo, JLabel fecha, JLabel desc)
	{
		tituloLabel = titulo;
		fechaLabel = fecha;
		descripLabel = desc;
	}
	
	// Muestra la información del grupo en la interfaz
	public void mostrarInfoGrupo()
	{		
		try
		{
			ResultSet rsInfoGrupo = infoGrupoPorIdChat(ChatsUsuario.getCurrentChat());
			
			while (rsInfoGrupo.next())
			{
				String nom = rsInfoGrupo.getString("nombre");
				String desc = rsInfoGrupo.getString("descripcion");
				String fecha = rsInfoGrupo.getString("fecha_creacion");
				
				tituloLabel.setText(nom);
				descripLabel.setText(desc);
				fechaLabel.setText("Fecha de creación: " + fecha);
			}	
		} 
		catch (SQLException e) 
		{
			System.out.println("Error SQL (mostrarInfoGrupo)");
			
		}
	}
	
	// Muestra la información de los participantes en una lista en la interfaz
	public void mostrarParticipantes(JPanel padre)
	{
		try
		{
			ResultSet rsParticipantes = participantesGrupo(ChatsUsuario.getCurrentChat());
			
			int positionUI = 10;
			int increment = 44;
			
			while (rsParticipantes.next())
			{
				int id_usu = rsParticipantes.getInt("id_usuario");
				String nomUsu = LoginUsuario.nombreUserPorId(id_usu);
				
				boolean administra = rsParticipantes.getBoolean("administra");
				
				JPanel panelUsu = new JPanel();
				panelUsu.setBounds(10, positionUI, 210, 34);
				panelUsu.setLayout(null);
				panelUsu.setBackground(new Color(255, 250, 250));
				
				padre.add(panelUsu);
				
				// Incrementa la posicion Y del container para que no se sobrepongan los paneles
				positionUI += increment;
				
				JLabel nombreParticipante = new JLabel(nomUsu);
				nombreParticipante.setFont(new Font("Segoe UI", Font.PLAIN, 14));
				nombreParticipante.setBounds(10, 10, 133, 13);
				panelUsu.add(nombreParticipante);
						
				JLabel adminIcon = new JLabel("Admin");
				
				// Añadir un icono de admin si administran en el grupo
				if (administra)
				{				
					adminIcon.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 128, 0)));
					adminIcon.setForeground(new Color(0, 128, 0));
					adminIcon.setBounds(148, 11, 35, 13);
					panelUsu.add(adminIcon);
				}
				
				// Variable auxiliar individual
				int usuarioFinal = id_usu;
				
				if (LoginUsuario.isAdmin(LoginUsuario.getIdUsuarioConectado(), ChatsUsuario.getCurrentChat()) == true)
				{			  
					// Añade la funcionalidad de seleccionar y agregar admins a los usuarios
					panelUsu.addMouseListener(new MouseAdapter()
				    {					
						@Override
						public void mouseClicked(MouseEvent e)
						{							
							// seleccionar
							if (!cambiarAdmins.contains(usuarioFinal) && !nombreParticipante.getText().equals(LoginUsuario.nombreUserPorId(LoginUsuario.getIdUsuarioConectado())))
							{
								cambiarAdmins.add(usuarioFinal);
								
								panelUsu.setBackground(new Color(65, 105, 205));
								nombreParticipante.setForeground(Color.WHITE);					
							}
							else //deseleccionar
							{	
								for (int i = 0; i < cambiarAdmins.size(); i++)
								{
									if (cambiarAdmins.get(i) == usuarioFinal)
									{
										cambiarAdmins.remove(i);
									}
								}
								
								panelUsu.setBackground(new Color(255, 250, 250));
								nombreParticipante.setForeground(Color.BLACK);					
							}
							
							// Muestra el boton de cambiar admin si hay mas de 1 seleccionado
							if (cambiarAdmins.size() > 0)
							{
								InfoGrupoUI.getAdminButton().setVisible(true);
							}
							else
							{
								InfoGrupoUI.getAdminButton().setVisible(false);
							}
						}
					});
				   
				    // Si el usuario en la lista es tu usuario conectado, no añade el boton de borrarlo del grupo
					if (id_usu == LoginUsuario.getIdUsuarioConectado())
					{
						adminIcon.setBounds(168, 11, 35, 13);
					}
					// Si el usuario en la lista no es tu usuario, añade un boton para poder eliminarlo del grupo
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
								
								// Borrar a un usuario del grupo
								int opcion = JOptionPane.showConfirmDialog(null, "¿Quieres eliminar a " + LoginUsuario.nombreUserPorId(id_usu) + " del grupo?", "Eliminar", JOptionPane.YES_NO_OPTION);

								if (opcion == 0)
								{	
									removeParticipante(ChatsUsuario.getCurrentChat(), id_usu);
									
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
			   // Reposiciona el icono de admin
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
			
		}
	}
	
	public static ResultSet participantesGrupo(int id_chat)
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
			System.out.println("Error SQL (participantesGrupo)");
		}
		
		return rs;
	}
	
	public static ResultSet adminsGrupo(int id_chat)
	{
		ResultSet rs = null;
		
		try
		{	
			PreparedStatement pst = cn.prepareStatement("SELECT * FROM participa WHERE id_chat = ? AND administra = true");
			pst.setInt(1, id_chat);
	        rs = pst.executeQuery();
		}
		catch(SQLException e)
		{		
			System.out.println("Error SQL (adminsGrupo)");
		}
		
		return rs;
	}

	// Saca toda la informacion de un grupo por id_chat
	private ResultSet infoGrupoPorIdChat(int id_chat)
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
			System.out.println("Error SQL (infoGrupoPorIdChat)");
		}
		
		return rs;
	}
	
	// Borra a un usuario de un grupo
	public void removeParticipante(int id_chat, int id_usu)
	{
		try 
		{			
			PreparedStatement pst = cn.prepareStatement("DELETE FROM participa WHERE id_chat = ? AND id_usuario = ?");
			pst.setInt(1, id_chat);
            pst.setInt(2, id_usu);
			pst.executeUpdate();
		}
		catch (SQLException e)
		{			
			System.out.println("ErrorSQL (removeParticipante)");
		}		
	}
	
	// Borra un chat por completo por id_chat
	public void salirUltimoEnGrupo(int id_chat)
	{
		try 
		{		
			PreparedStatement pst = cn.prepareStatement("DELETE FROM chat WHERE id_chat = ?");
			pst.setInt(1, id_chat);
            pst.executeUpdate();
		}
		catch (SQLException e)
		{		
			System.out.println("Error SQL (salirUltimoEnGrupo)");
		}
	}
	
	// Convierte a un usuario en un chat concreto en administrador
	public void updateAdmin(int id_usuario, int id_chat, boolean admin)
	{
		try
		{			
			PreparedStatement pst = cn.prepareStatement("UPDATE participa SET administra = ? WHERE id_chat = ? AND id_usuario = ?");
			pst.setBoolean(1, admin);
			pst.setInt(2, id_chat);
			pst.setInt(3, id_usuario);
            pst.executeUpdate();
		}
		catch (SQLException e)
		{			
			System.out.println("Error SQL (updateAdmin)");
		}
	}
	
	/* Bucle para actualizar a todos los usuarios seleccionados (en la ArrayList cambiarAdmins)
	  Si ya son admins y estan seleccionados, los vuelven a false en vez de volver a cambiarlos a true
	  Además despues de haberlos actualizado limpia la lista de seleccionados*/ 
	public void actualizarAdmins()
	{
		for (int i: cambiarAdmins)
		{
			if (LoginUsuario.isAdmin(i, ChatsUsuario.getCurrentChat()))
			{
				updateAdmin(i, ChatsUsuario.getCurrentChat(), false);
			}
			else if (!LoginUsuario.isAdmin(i, ChatsUsuario.getCurrentChat()))
			
			{
				updateAdmin(i, ChatsUsuario.getCurrentChat(), true);
			}
		}
		
		 cambiarAdmins.clear();
	}
}
