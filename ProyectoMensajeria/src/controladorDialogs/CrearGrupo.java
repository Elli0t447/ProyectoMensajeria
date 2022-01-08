package controladorDialogs;

import static modelo.Conexion.cn;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.*;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

import controladorMain.AmigosUsuario;
import controladorMain.LoginUsuario;


public class CrearGrupo 
{	
	// Lista con los participantes seleccionados
	private ArrayList<Integer> participantes = new ArrayList<>();
	
	// Llena el contenedor de participantes a añadir, con tus amigos
	public void rellenarAmigos(JPanel padre)
	{	
		try
		{
			AmigosUsuario amigos = new AmigosUsuario();		
			ResultSet rsAmigosUserConectado = amigos.amigosUser(LoginUsuario.getIdUsuarioConectado());
			
			int positionUI = 10;
			int increment = 38;
			
			while (rsAmigosUserConectado.next())
			{
				int id_usu1 = rsAmigosUserConectado.getInt("id_usu1");
				int id_usu2 = rsAmigosUserConectado.getInt("id_usu2");
				
				// Lógica para determinar cual usuario mostrar
				int usuarioFinal;
		
				if (id_usu1 != LoginUsuario.getIdUsuarioConectado())
				{
					usuarioFinal = id_usu1;				
				}
				else
				{
					usuarioFinal = id_usu2;
				}
				
				String nomAmigo = LoginUsuario.nombreUserPorId(usuarioFinal);
				
				JPanel panelAmigo = new JPanel();
				panelAmigo.setLayout(null);
				panelAmigo.setBackground(new Color(255, 250, 250));
				panelAmigo.setBounds(10, positionUI, 213, 25);
				padre.add(panelAmigo);
				
				positionUI += increment;
				
				JLabel nomAmigoLabel = new JLabel(nomAmigo);
				nomAmigoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
				nomAmigoLabel.setBounds(8, 6, 122, 13);
				panelAmigo.add(nomAmigoLabel);
				
				panelAmigo.addMouseListener(new MouseAdapter()
				{				
					@Override
					public void mouseClicked(MouseEvent e)
					{												
						// Si no esta en la lista de seleccionados lo añade y lo muestra en la interfaz como seleccionado
						if (!participantes.contains(usuarioFinal))
						{
							participantes.add(usuarioFinal);
							
							panelAmigo.setBackground(new Color(65, 105, 205));
							nomAmigoLabel.setForeground(Color.WHITE);				
						}
						// Si ya esta en la lista significa que ya esta seleccionado asi que lo deselecciona y elimina de la lista
						else
						{
							for (int i = 0; i < participantes.size(); i++)
							{
								if (participantes.get(i) == usuarioFinal)
								{
									participantes.remove(i);
								}
							}
							
							panelAmigo.setBackground(new Color(255, 250, 250));
							nomAmigoLabel.setForeground(Color.BLACK);
						}
					}
				});
				
			}
			
			// Determinar el tamaño del scroll
			padre.setPreferredSize(new Dimension(0, positionUI));
			
			padre.repaint();
			padre.revalidate();
		} 
		catch (SQLException e) 
		{
			
			System.out.println("Error de SQL (rellenarAmigos)");
		}
	}
	
	// Inserta en chat, en grupo, y en participa para crear un grupo con tu usuario logueado como usuario administrador
	public void crearGrupo(String nom, String descripcion)
	{
		try
		{			
			cn.setAutoCommit(false);
			
			PreparedStatement pst = cn.prepareStatement("BEGIN; INSERT INTO chat VALUES (DEFAULT); INSERT INTO grupo SELECT currval ('chat_id_chat_seq'), ?, ?, now(); INSERT INTO participa (id_chat, id_usuario, administra) SELECT currval ('chat_id_chat_seq'), ?, true; END;");
            pst.setString(1, nom);
            pst.setString(2, descripcion);
            pst.setInt(3, LoginUsuario.getIdUsuarioConectado());
			pst.executeUpdate();
					
			
			// Además añade a los usuarios iniciales que hayas seleccionado
			for (int i = 0; i < participantes.size(); i++)
			{
				addParticipante(ultimoChat(), participantes.get(i), false);
			}
			
			cn.commit();
			cn.setAutoCommit(true);
		}
		catch (SQLException e)
		{
			
			System.out.println("ErrorSQL (crearGrupo)");
		}
	}
	
	// Añade un participante al grupo
	public void addParticipante(int id_chat, int usuario, boolean admin)
	{		
		try
		{			
			PreparedStatement pst = cn.prepareStatement("INSERT INTO participa (id_chat, id_usuario, administra) VALUES (?, ?, ?);");
            pst.setInt(1, id_chat);
            pst.setInt(2, usuario);
            pst.setBoolean(3, admin);
			pst.executeUpdate();
		}
		catch (SQLException e)
		{
			
			System.out.println("ErrorSQL (addParticipante)");
		}		
	}
	
	// Devuelve el id del ultimo chat insertado
	private int ultimoChat()
	{
		try
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
		catch (SQLException e)
		{
			
			System.out.println("ErrorSQL (ultimoChat)");
		}
		
		return 0;	
	}
}
