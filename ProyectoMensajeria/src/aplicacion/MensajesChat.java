package aplicacion;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.Principal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import interfaz.InfoGrupoUI;
import interfaz.PrincipalUI;

public class MensajesChat
{
	private static int positionUI;
	private static int msj;
	
	private Connection cn;
	
	private MensajesChat mcc;
	private AmigosUsuario amigos;
	
	public MensajesChat()
	{
		cn = Conexion.Conectar();
		amigos = new AmigosUsuario();
	}
	
	public void cargarChat(int id_c, JPanel parent)
	{
		PrincipalUI.tab_noChat.setVisible(false);
		PrincipalUI.tab_chat.setVisible(true);	
		
		ResultSet rs = selectMensajesUserEnChat(id_c);
		
		try 
		{
			positionUI = 10;
			int incremento = 54;
			
			while (rs.next())
			{
				String mensaje = rs.getString("texto");
				int usuario = rs.getInt("id_usuario");
				msj = rs.getInt("id_mensaje");
				
				// Fecha actual
				Date hoy = Date.valueOf(LocalDate.now());
				
				// Fecha de envio del mensaje
				Date fecha = rs.getDate("fecha");
				Timestamp fechaHora = rs.getTimestamp("fecha");
								
				// Hora de envio del mensaje
				String[] aux = fechaHora.toString().split("\\.");			
				String hora[] = aux[0].split("\s");	
				
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
				usuarioMensaje.setBounds(10, 10, (int)usuarioMensaje.getPreferredSize().getWidth() + 16, 13);
				msjPanel.add(usuarioMensaje);
				
				JLabel textoMensaje = new JLabel(mensaje);
				textoMensaje.setForeground(Color.DARK_GRAY);
				textoMensaje.setFont(new Font("Segoe UI", Font.PLAIN, 12));
				textoMensaje.setBounds(12, 26, 570, 13);
				msjPanel.add(textoMensaje);	
				
				JLabel fechaMensaje = new JLabel();
				fechaMensaje.setForeground(Color.GRAY);
				fechaMensaje.setFont(new Font("Segoe UI", Font.BOLD, 9));
				fechaMensaje.setBounds((int)usuarioMensaje.getPreferredSize().getWidth() + 23, 10, 570, 13);
				msjPanel.add(fechaMensaje);	
				
				if (fecha.before(hoy))
				{
					fechaMensaje.setText(fecha.toString());
				}
				else
				{
					fechaMensaje.setText("hoy a las " + hora[1]);
				}
				
				int usuFinal = usuario;
				
				msjPanel.addMouseListener(new MouseAdapter() 
				{
					@Override
					public void mouseEntered(MouseEvent e) 
					{
						msjPanel.setBackground(new Color(245, 245, 245));
						
						if (PrincipalUI.chatsU.isConver(PrincipalUI.getCurrentChat()) == false && PrincipalUI.getCurrentAdministra() == true)
						{
							
							if (LoginUsuario.getIdUsuario() == usuFinal)
							{	
								miniMenu.setVisible(true);
								iconEditar.setVisible(true);							
							}
							else
							{
								miniMenu.setVisible(true);
								miniMenu.setBounds(525, -2, 30, 25);
								iconBorrar.setBounds(5,-3,30,30);
								iconEditar.setVisible(false);
							}
						}
						else if (PrincipalUI.chatsU.isConver(PrincipalUI.getCurrentChat()) == false && PrincipalUI.getCurrentAdministra() == false)
						{
							if (LoginUsuario.getIdUsuario() == usuFinal)
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
							if (LoginUsuario.getIdUsuario() == usuFinal)
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
							if (LoginUsuario.getIdUsuario() == usuFinal)
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
							if (LoginUsuario.getIdUsuario() == usuFinal)
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
							if (LoginUsuario.getIdUsuario() == usuFinal)
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
						String opcion = (String) JOptionPane.showInputDialog(null, "Editar mensaje: ", "Editar", JOptionPane.QUESTION_MESSAGE, null, null, mensaje);
						
						if (opcion == null)
						{
							System.out.println("na no editao");
						}
						else
						{
							editarMensaje(opcion, currentMensaje);
							
							mcc = PrincipalUI.mC;
							PrincipalUI.setCurrentChatDesc(PrincipalUI.getCurrentChatDesc());
							PrincipalUI.setCurrentChatTitulo(PrincipalUI.getCurrentChatTitulo());
							PrincipalUI.nombreChat.setText(PrincipalUI.getCurrentChatTitulo());
							
							PrincipalUI.descripcionChat.setText(PrincipalUI.getCurrentChatDesc());
							PrincipalUI.containerMsj.removeAll();
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
												
						// Es admin de un grupo
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
						// No es admin de un grupo
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
						// Si es una conversación
						else if (PrincipalUI.chatsU.isConver(PrincipalUI.getCurrentChat()) == true && PrincipalUI.getCurrentAdministra() == false)
						{
							if (LoginUsuario.getIdUsuario() == usuario)
							{
								miniMenu.setVisible(true);
							}
							else
							{
								miniMenu.setVisible(false);
								iconEditar.setVisible(false);
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
							if (PrincipalUI.chatsU.isConver(PrincipalUI.getCurrentChat()) == true)
							{
								PrincipalUI.nombreChat.setBounds(10, 12, 361, 33);
							}
							PrincipalUI.descripcionChat.setText(PrincipalUI.getCurrentChatDesc());
							PrincipalUI.containerMsj.removeAll();
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
			
			PrincipalUI.scrollMensaje.getViewport().setViewPosition(new Point(1, positionUI));
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
		cn = LoginUsuario.getConexion();
		
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
			System.out.println("Usuario con el mismo nombre ya existe (enviarMensaje)");
		}
	}
	
	private ResultSet selectMensajesUserEnChat(int id_c)
	{
		cn = LoginUsuario.getConexion();
		String consultaSQL = "SELECT * FROM mensaje WHERE id_chat = ? ORDER BY fecha ASC";
		
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

	private void editarMensaje(String txt, int id_m)
	{
		cn = LoginUsuario.getConexion();
		
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
			System.out.println("Usuario con el mismo nombre ya existe (cambiarMensaje)");
		}
	}
	
	public void borrarMensaje(int id_m)
	{
		cn = LoginUsuario.getConexion();
		
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
			System.out.println("Usuario con el mismo nombre ya existe (borrarMensaje)");
		}
	}

}
