package dialogos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.*;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

import aplicacion.AmigosUsuario;
import aplicacion.Conexion;
import aplicacion.LoginUsuario;

public class CrearGrupo 
{
	private static Connection cn;
	private ArrayList<Integer> participantes = new ArrayList<>();
	
	public CrearGrupo()
	{
		cn = Conexion.Conectar();
	}
	
	public void rellenarAmigos(JPanel padre)
	{	
		try 
		{
			AmigosUsuario amigos = new AmigosUsuario();		
			ResultSet rs = amigos.amigosUser(LoginUsuario.getIdUsuario());
			
			int positionUI = 10;
			int increment = 38;
			
			while (rs.next())
			{
				int id_usu1 = rs.getInt("id_usu1");
				int id_usu2 = rs.getInt("id_usu2");
				
				int usuarioFinal;
		
				if (id_usu1 != LoginUsuario.getIdUsuario())
				{
					usuarioFinal = id_usu1;				
				}
				else
				{
					usuarioFinal = id_usu2;
				}
				
				String nom = LoginUsuario.nombreUserPorId(usuarioFinal);
				
				JPanel panelAmigo = new JPanel();
				panelAmigo.setLayout(null);
				panelAmigo.setBackground(new Color(255, 250, 250));
				panelAmigo.setBounds(10, positionUI, 213, 25);
				padre.add(panelAmigo);
				
				positionUI += increment;
				
				JLabel nomAmigo = new JLabel(nom);
				nomAmigo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
				nomAmigo.setBounds(8, 6, 122, 13);
				panelAmigo.add(nomAmigo);
				
				panelAmigo.addMouseListener(new MouseAdapter()
				{
					
					@Override
					public void mouseClicked(MouseEvent e)
					{							
						
						if (panelAmigo.getBackground().equals(new Color(255, 250, 250)))
						{
							panelAmigo.setBackground(new Color(65, 105, 205));
							nomAmigo.setForeground(Color.WHITE);
							participantes.add(usuarioFinal);
						}
						else
						{
							panelAmigo.setBackground(new Color(255, 250, 250));
							nomAmigo.setForeground(Color.BLACK);
							for (int i = 0; i < participantes.size(); i++)
							{
								if (participantes.get(i) == usuarioFinal)
								{
									participantes.remove(i);
								}
							}
						}
					}
				});
				
			}
			
			padre.setPreferredSize(new Dimension(200, positionUI));
			padre.repaint();
			padre.revalidate();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			System.out.println("Error de SQL (rellenarAmigos)");
		}
	}
	
	public void crearGrupo(String nom, String descripcion)
	{
		try
		{			
			if (cn != null)
			{
				PreparedStatement pst = cn.prepareStatement("BEGIN; INSERT INTO chat VALUES (DEFAULT); INSERT INTO grupo SELECT currval ('chat_id_chat_seq'), ?, ?, now(); INSERT INTO participa (id_chat, id_usuario, administra) SELECT currval ('chat_id_chat_seq'), ?, true; END;");
                pst.setString(1, nom);
                pst.setString(2, descripcion);
                pst.setInt(3, LoginUsuario.getIdUsuario());
				pst.executeUpdate();
				
				for (int i = 0; i < participantes.size(); i++)
				{
					addParticipante(ultimoChat(), participantes.get(i), false);
				}
			}
			else
			{
				System.out.println("Conexión nula.");
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("ErrorSQL (crearGrupo)");
		}
	}
	
	public void addParticipante(int id_chat, int usuario, boolean admin)
	{		
		try
		{			
			if (cn != null)
			{
				PreparedStatement pst = cn.prepareStatement("BEGIN; INSERT INTO participa (id_chat, id_usuario, administra) VALUES (?, ?, ?); END;");
                pst.setInt(1, id_chat);
                pst.setInt(2, usuario);
                pst.setBoolean(3, admin);
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
			System.out.println("ErrorSQL (addParticipante)");
		}		
	}
	
	private int ultimoChat()
	{
		try
		{			
			if (cn != null)
			{
				ResultSet rs;
				int chat = 0;
				
				PreparedStatement pst = cn.prepareStatement("SELECT last_value FROM chat_id_chat_seq");
				rs = pst.executeQuery();
				
				while (rs.next())
				{
					chat = rs.getInt("last_value");
				}
				
				return chat;
			}
			else
			{
				System.out.println("Conexión nula.");
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("ErrorSQL (ultimoChat)");
		}
		
		return 0;	
	}
}
