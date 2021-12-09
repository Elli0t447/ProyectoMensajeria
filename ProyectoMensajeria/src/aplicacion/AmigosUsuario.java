package aplicacion;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import dialogos.AnyadirAmigo;
import interfaz.PrincipalUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AmigosUsuario 
{
	private static Connection cn;
	private static AmigosUsuario au;
	
	public AmigosUsuario()
	{
		cn = Conexion.Conectar();
	}
	
	public boolean mostrarListaAmigos(JPanel padre)
	{				
		try 
		{
			ResultSet rsA = amigosUser(LoginUsuario.getIdUsuario());
			
			int positionUI = 10;
			int incremento = 44;
			int countAmigos = 0;
			
			while (rsA.next())
			{
				int id_usu1 = rsA.getInt("id_usu1");
				int id_usu2 = rsA.getInt("id_usu2");
				
				int usuarioFinal;
		
				if (id_usu1 != LoginUsuario.getIdUsuario())
				{
					usuarioFinal = id_usu1;				
				}
				else
				{
					usuarioFinal = id_usu2;
				}
				
				JPanel panelAmigo = new JPanel();
				panelAmigo.setBackground(new Color(250, 250, 250));
				panelAmigo.setBounds(10, positionUI, 316, 34);
				panelAmigo.setLayout(null);
				padre.add(panelAmigo);
				
				positionUI += incremento;
				
				JLabel nombreAmigo = new JLabel(LoginUsuario.nombreUserPorId(usuarioFinal));
				nombreAmigo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
				nombreAmigo.setBounds(10, 10, 251, 13);
				panelAmigo.add(nombreAmigo);
				
				JButton borrarAmigo = new JButton();
				borrarAmigo.setBorderPainted(false);
				borrarAmigo.setContentAreaFilled(false);
				borrarAmigo.setIcon(new ImageIcon(AmigosUsuario.class.getResource("/img/borrar.png")));
				borrarAmigo.setBounds(271, 6, 35, 21);
				borrarAmigo.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) 
					{		
						int opcion = JOptionPane.showConfirmDialog(null, "Quieres borrar a " + LoginUsuario.nombreUserPorId(usuarioFinal) + " como amigo?", "Aviso", JOptionPane.YES_NO_OPTION);
						
						if (opcion == 0)
						{
							au = PrincipalUI.amics;
							borrarAmigo(LoginUsuario.getIdUsuario(), usuarioFinal);
							
							PrincipalUI.amigoContainer.removeAll();
			                if(au.mostrarListaAmigos(PrincipalUI.amigoContainer) == false)
			                {
			                	PrincipalUI.descAmigo.setVisible(true);
			                }
			                
			                PrincipalUI.amigoContainer.revalidate();
							PrincipalUI.amigoContainer.repaint();
						}
						else
						{
							System.out.println("Amigo no borrado");
						}
						
					}
				});
				panelAmigo.add(borrarAmigo);
						
				padre.setPreferredSize(new Dimension(250, positionUI));
				padre.revalidate();
				padre.repaint();
				countAmigos++;
			}
			if (countAmigos > 0)
			{			
				return true;
			}
		} 
		catch (SQLException e) 
		{
			System.out.println("Error de SQL");
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean mostrarListaPeticiones(JPanel padre)
	{
		ResultSet rsA = peticionesUser(LoginUsuario.getIdUsuario());
		
		int countPeticiones = 0;
		try 
		{
			int positionUI = 10;
			int incremento = 44;
			
			while (rsA.next())
			{
				int id_usu1 = rsA.getInt("id_usu1");
				int id_usu2 = rsA.getInt("id_usu2");
				
				int usuarioFinal;
		
				if (id_usu1 != LoginUsuario.getIdUsuario())
				{
					usuarioFinal = id_usu1;				
				}
				else
				{
					usuarioFinal = id_usu2;
				}
				
				JPanel panelPeticion = new JPanel();
				panelPeticion.setBackground(new Color(250, 250, 250));
				panelPeticion.setBounds(10, positionUI, 500, 34);
				panelPeticion.setLayout(null);
				padre.add(panelPeticion);
				
				positionUI += incremento;
				JLabel nombreAmigoPeticion = new JLabel(LoginUsuario.nombreUserPorId(usuarioFinal));
				nombreAmigoPeticion.setFont(new Font("Segoe UI", Font.PLAIN, 12));
				nombreAmigoPeticion.setBounds(10, 10, 251, 13);
				panelPeticion.add(nombreAmigoPeticion);
				
				JButton denegarPeticion = new JButton();
				denegarPeticion.setBorderPainted(false);
				denegarPeticion.setContentAreaFilled(false);
				denegarPeticion.setIcon(new ImageIcon(AmigosUsuario.class.getResource("/img/cancel.png")));
				denegarPeticion.setBounds(351, 6, 35, 21);
				denegarPeticion.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) 
					{		
						au = PrincipalUI.amics;
						borrarAmigo(LoginUsuario.getIdUsuario(), usuarioFinal);
						
						PrincipalUI.peticionesContainer.removeAll();
						if(au.mostrarListaPeticiones(PrincipalUI.peticionesContainer) == false)
						{
							PrincipalUI.descPeti.setVisible(true);
						}
						
						PrincipalUI.peticionesContainer.revalidate();
						PrincipalUI.peticionesContainer.repaint();
					}
				});
				panelPeticion.add(denegarPeticion);
				
				JButton aceptarPeticion = new JButton();
				aceptarPeticion.setBorderPainted(false);
				aceptarPeticion.setContentAreaFilled(false);
				aceptarPeticion.setIcon(new ImageIcon(AmigosUsuario.class.getResource("/img/check.png")));
				aceptarPeticion.setBounds(320, 7, 35, 21);
				aceptarPeticion.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) 
					{		
						AnyadirAmigo add = new AnyadirAmigo();
						
						au = PrincipalUI.amics;
						aceptarAmistad(LoginUsuario.getIdUsuario(), usuarioFinal);
						PrincipalUI.chatsU.mostrarListaChats(PrincipalUI.container);
						add.insertarChat(LoginUsuario.getIdUsuario(), usuarioFinal);
						
						PrincipalUI.peticionesContainer.removeAll();
						PrincipalUI.amigoContainer.removeAll();
		                if(au.mostrarListaPeticiones(PrincipalUI.peticionesContainer) == false)
		                {
		                	PrincipalUI.descPeti.setVisible(true);
		                }
		                if(au.mostrarListaAmigos(PrincipalUI.amigoContainer) == false)
		                {
		                	PrincipalUI.descAmigo.setVisible(true);
		                }
										
						PrincipalUI.peticionesContainer.revalidate();
						PrincipalUI.peticionesContainer.repaint();
					}
				});
				panelPeticion.add(aceptarPeticion);
						
				padre.setPreferredSize(new Dimension(padre.getWidth(), positionUI));
				padre.revalidate();
				padre.repaint();
				countPeticiones++;
			}
			if (countPeticiones > 0)
			{		
				return true;
			}
		} 
		catch (SQLException e) 
		{
			System.out.println("Error de SQL");
			e.printStackTrace();
		}
		return false;
	}
	
	public ResultSet amigosUser(int id_u)
	{
		ResultSet rs = null;
		
		try 
	    {
	        PreparedStatement pst = cn.prepareStatement("SELECT * FROM amistad WHERE (id_usu1 = ? OR id_usu2 = ?) AND aceptada = true");
	        pst.setInt(1, id_u);
	        pst.setInt(2, id_u);
	        rs = pst.executeQuery();
	        
	    } 
	    catch (SQLException ex) 
	    {
	        System.out.println("Error al seleccionar datos");
	    }
		
		return rs;
	}
	
	
	private ResultSet peticionesUser(int id_u)
	{
		ResultSet rs = null;
		
		try 
	    {
	        PreparedStatement pst = cn.prepareStatement("SELECT * FROM amistad WHERE id_usu2 = ? AND aceptada = false");
	        pst.setInt(1, id_u);
	        rs = pst.executeQuery();
	        
	    } 
	    catch (SQLException ex) 
	    {
	        System.out.println("Error al seleccionar datos");
	    }
		
		return rs;
	}

	private void aceptarAmistad(int id_u1, int id_u2)
	{
		try
		{			
			if (cn != null)
			{
				PreparedStatement pst = cn.prepareStatement("UPDATE amistad SET aceptada = 'true', amigodesde = now() WHERE (id_usu1 = ? AND id_usu2 = ?) OR (id_usu1 = ? AND id_usu2 = ?)");
				pst.setInt(1, id_u1);
				pst.setInt(2, id_u2);
				pst.setInt(3, id_u2);
				pst.setInt(4, id_u1);
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
			System.out.println("Error de SQL (aceptarAmistad)");
		}
	}
	
	private void borrarAmigo(int id_u1, int id_u2)
	{
		try
		{			
			if (cn != null)
			{
				ResultSet rs = null;
				int id_chat = 0;
				
				PreparedStatement deleteAmistad = cn.prepareStatement("DELETE FROM amistad WHERE (id_usu1 = ? AND id_usu2 = ?) OR (id_usu1 = ? AND id_usu2 = ?)");
				deleteAmistad.setInt(1, id_u1);
				deleteAmistad.setInt(2, id_u2);
				deleteAmistad.setInt(3, id_u2);
				deleteAmistad.setInt(4, id_u1);
                deleteAmistad.executeUpdate();    
				
				PreparedStatement pst = cn.prepareStatement("SELECT * FROM conversacion WHERE (id_usu1 = ? AND id_usu2 = ?) OR (id_usu1 = ? AND id_usu2 = ?)");
				pst.setInt(1, id_u1);
				pst.setInt(2, id_u2);
				pst.setInt(3, id_u2);
				pst.setInt(4, id_u1);
                rs = pst.executeQuery();         
                
                while (rs.next())
                {
                	id_chat = rs.getInt("id_chat");            	          	
                }
                
                PreparedStatement borrar = cn.prepareStatement("DELETE FROM chat WHERE id_chat = ?");
            	borrar.setInt(1, id_chat);
            	borrar.executeUpdate();
			}
			else
			{
				System.out.println("Conexión nula.");
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("Error de SQL (borrarAmigo)");
		}
	}
}
