package aplicacion;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import interfaz.PrincipalUI;

public class MensajesChat
{
	private static int positionUI;
	private static int msj;
	
	private MensajesChat mcc;
	
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
				msj = rs.getInt("id_mensaje");
				
				String nomUsuario = LoginUsuario.nombreUserPorId(usuario);
	
				JPanel msjPanel = new JPanel();
				msjPanel.setLayout(null);
				msjPanel.setBorder(null);
				msjPanel.setBackground(Color.WHITE);
				msjPanel.setBounds(12, positionUI, 590, 47);
				parent.add(msjPanel);
				
				positionUI += incremento;
				
				int currentMensaje = msj;
				
				JPanel miniMenu = new JPanel();
				miniMenu.setLayout(null);
				miniMenu.setBorder(null);
				miniMenu.setVisible(false);
				miniMenu.setBackground(new Color(235,235,235));
				miniMenu.setBounds(494, -2, 60, 25);
				msjPanel.add(miniMenu);
				
				JLabel iconEditar = new JLabel();
				iconEditar.setBounds(10,-3,30,30);
				iconEditar.setIcon(new ImageIcon(MensajesChat.class.getResource("/img/editar.png")));
				miniMenu.add(iconEditar);
				
				JLabel iconBorrar = new JLabel();
				iconBorrar.setBounds(32,-3,30,30);
				iconBorrar.setIcon(new ImageIcon(MensajesChat.class.getResource("/img/borrar.png")));
				miniMenu.add(iconBorrar);
				
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
				
				msjPanel.addMouseListener(new MouseAdapter() 
				{
					@Override
					public void mouseEntered(MouseEvent e) 
					{
						msjPanel.setBackground(new Color(245, 245, 245));
												
						if (PrincipalUI.chatsU.isConver(PrincipalUI.getCurrentChat()) == false && PrincipalUI.getCurrentAdministra() == true)
						{
							miniMenu.setVisible(true);
							if (LoginUsuario.getIdUsuario() == usuario)
							{
								iconEditar.setVisible(true);							
							}
							else
							{
								miniMenu.setBounds(525, -2, 30, 25);
								iconBorrar.setBounds(5,-3,30,30);
								iconEditar.setVisible(false);
							}
						}
						else if (PrincipalUI.chatsU.isConver(PrincipalUI.getCurrentChat()) == false && PrincipalUI.getCurrentAdministra() == false)
						{
							if (LoginUsuario.getIdUsuario() == usuario)
							{
								miniMenu.setVisible(true);
							}
							else
							{
								miniMenu.setVisible(false);
							}
						}
						else if (PrincipalUI.chatsU.isConver(PrincipalUI.getCurrentChat()) == true)
						{
							if (LoginUsuario.getIdUsuario() == usuario)
							{
								miniMenu.setVisible(true);
							}
							else
							{
								miniMenu.setVisible(false);
							}
						}
					}
					
					@Override
					public void mouseExited(MouseEvent e) 
					{
						msjPanel.setBackground(Color.WHITE);
						miniMenu.setVisible(false);
					}
				});
				
				iconEditar.addMouseListener(new MouseAdapter() 
				{
					@Override
					public void mouseEntered(MouseEvent e) 
					{
						msjPanel.setBackground(new Color(245, 245, 245));
												
						if (PrincipalUI.chatsU.isConver(PrincipalUI.getCurrentChat()) == false && PrincipalUI.getCurrentAdministra() == true)
						{
							miniMenu.setVisible(true);
							if (LoginUsuario.getIdUsuario() == usuario)
							{
								iconEditar.setVisible(true);							
							}
							else
							{
								miniMenu.setBounds(525, -2, 30, 25);
								iconBorrar.setBounds(5,-3,30,30);
								iconEditar.setVisible(false);
							}
						}
						else if (PrincipalUI.chatsU.isConver(PrincipalUI.getCurrentChat()) == false && PrincipalUI.getCurrentAdministra() == false)
						{
							if (LoginUsuario.getIdUsuario() == usuario)
							{
								miniMenu.setVisible(true);
							}
							else
							{
								miniMenu.setVisible(false);
							}
						}
						else if (PrincipalUI.chatsU.isConver(PrincipalUI.getCurrentChat()) == true)
						{
							if (LoginUsuario.getIdUsuario() == usuario)
							{
								miniMenu.setVisible(true);
							}
							else
							{
								miniMenu.setVisible(false);
							}
						}
					}
					
					@Override
					public void mouseExited(MouseEvent e) 
					{
						msjPanel.setBackground(Color.WHITE);
						miniMenu.setVisible(false);
					}
					
					@Override
					public void mouseClicked(MouseEvent e) 
					{
						String opcion = JOptionPane.showInputDialog(null, "Editar mensaje: ", "Editar", JOptionPane.INFORMATION_MESSAGE);					
						
						if (opcion.equals(""))
						{
							System.out.println("na no editao");
						}
						else
						{
							cambiarMensaje(opcion, currentMensaje);
							
							mcc = PrincipalUI.mC;
							PrincipalUI.setCurrentChatDesc(PrincipalUI.getCurrentChatDesc());
							PrincipalUI.setCurrentChatTitulo(PrincipalUI.getCurrentChatTitulo());
							PrincipalUI.nombreChat.setText(PrincipalUI.getCurrentChatTitulo());
							PrincipalUI.nombreChat.setBounds(10, 4, 190, 33);
							PrincipalUI.descripcionChat.setText(PrincipalUI.getCurrentChatDesc());
							PrincipalUI.containerMsj.removeAll();
							PrincipalUI.setChatEnvio(PrincipalUI.getCurrentChat());
							PrincipalUI.setCurrentAdministra(PrincipalUI.getCurrentAdministra());
							mcc.cargarChat(PrincipalUI.getCurrentChat(), PrincipalUI.containerMsj);
							parent.revalidate();
							parent.repaint();
						}
					}
					
				});
				
				iconBorrar.addMouseListener(new MouseAdapter() 
				{
					@Override
					public void mouseEntered(MouseEvent e) 
					{
						msjPanel.setBackground(new Color(245, 245, 245));
												
						if (PrincipalUI.chatsU.isConver(PrincipalUI.getCurrentChat()) == false && PrincipalUI.getCurrentAdministra() == true)
						{
							miniMenu.setVisible(true);
							if (LoginUsuario.getIdUsuario() == usuario)
							{
								iconEditar.setVisible(true);							
							}
							else
							{
								miniMenu.setBounds(525, -2, 30, 25);
								iconBorrar.setBounds(5,-3,30,30);
								iconEditar.setVisible(false);
							}
						}
						else if (PrincipalUI.chatsU.isConver(PrincipalUI.getCurrentChat()) == false && PrincipalUI.getCurrentAdministra() == false)
						{
							if (LoginUsuario.getIdUsuario() == usuario)
							{
								miniMenu.setVisible(true);
							}
							else
							{
								miniMenu.setVisible(false);
							}
						}
						else if (PrincipalUI.chatsU.isConver(PrincipalUI.getCurrentChat()) == true)
						{
							if (LoginUsuario.getIdUsuario() == usuario)
							{
								miniMenu.setVisible(true);
							}
							else
							{
								miniMenu.setVisible(false);
							}
						}
					}
					
					@Override
					public void mouseExited(MouseEvent e) 
					{
						msjPanel.setBackground(Color.WHITE);
						miniMenu.setVisible(false);
					}
					
					@Override
					public void mouseClicked(MouseEvent e) 
					{
						int opcion = JOptionPane.showConfirmDialog(null, "Quieres borrar el mensaje?", "Aviso", JOptionPane.YES_NO_OPTION);
						
						if (opcion == 0)
						{
							borrarMensaje(currentMensaje);

							mcc = PrincipalUI.mC;
							PrincipalUI.nombreChat.setText(PrincipalUI.getCurrentChatTitulo());
							PrincipalUI.nombreChat.setBounds(10, 4, 190, 33);
							PrincipalUI.descripcionChat.setText(PrincipalUI.getCurrentChatDesc());
							PrincipalUI.containerMsj.removeAll();
							PrincipalUI.setChatEnvio(PrincipalUI.getCurrentChat());
							PrincipalUI.setCurrentAdministra(PrincipalUI.getCurrentAdministra());
							mcc.cargarChat(PrincipalUI.getCurrentChat(), PrincipalUI.containerMsj);
							
							parent.revalidate();
							parent.repaint();
						}
						else
						{
							System.out.println("pos na no se borra bro");
						}
						
						
					}
					
				});
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
		String consultaSQL = "SELECT id_mensaje, texto, id_usuario, id_chat FROM mensaje WHERE id_chat = ? ORDER BY fecha ASC";
		
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

	private void cambiarMensaje(String txt, int id_m)
	{
		Connection cn = LoginUsuario.getConexion();
		
		try
		{			
			if (cn != null)
			{
				PreparedStatement pst = cn.prepareStatement("UPDATE mensaje SET texto = ? WHERE id_mensaje = ?");
				pst.setString(1, txt);
				pst.setInt(2, id_m);
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
	
	public void borrarMensaje(int id_m)
	{
		Connection cn = LoginUsuario.getConexion();
		
		try
		{			
			if (cn != null)
			{
				PreparedStatement pst = cn.prepareStatement("DELETE FROM mensaje WHERE id_mensaje = ?");
				pst.setInt(1, id_m);
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
}
