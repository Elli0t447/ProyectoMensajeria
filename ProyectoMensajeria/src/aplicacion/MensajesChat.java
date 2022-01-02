package aplicacion;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

import interfaz.PrincipalUI;

public class MensajesChat
{	
	private Connection cn;
	
	// Instancia de la interfaz principal
	private MensajesChat mcc;
	
	public MensajesChat()
	{
		cn = Conexion.Conectar();
	}
	
	// Muestra los mensajes del chat por id_chat
	public void cargarChat(int id_c, JPanel parent)
	{
		PrincipalUI.tab_noChat.setVisible(false);
		PrincipalUI.tab_chat.setVisible(true);	
		
		ResultSet rsMensajesEnChat = selectMensajesEnChat(id_c);
		
		try 
		{
			int positionUI = 10;
			int incremento = 54;
			
			while (rsMensajesEnChat.next())
			{
				String mensaje = rsMensajesEnChat.getString("texto");
				int usuario = rsMensajesEnChat.getInt("id_usuario");
				int id_msj = rsMensajesEnChat.getInt("id_mensaje");
				
				// Fecha actual
				Date hoy = Date.valueOf(LocalDate.now());
				
				// Fecha de envio del mensaje
				Date fecha = rsMensajesEnChat.getDate("fecha");
				Timestamp fechaHora = rsMensajesEnChat.getTimestamp("fecha");
								
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
				
				// Cambia la posicion del panel siguiente
				positionUI += incremento;
				
				int currentMensaje = id_msj;
				
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
				
				// Determinar el texto de la fecha del mensaje
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
						
						if (PrincipalUI.chatsU.isConver(ChatsUsuario.getCurrentChat(), LoginUsuario.getIdUsuarioConectado()) == false && PrincipalUI.getCurrentAdministra() == true)
						{
							
							if (LoginUsuario.getIdUsuarioConectado() == usuFinal)
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
						else if (PrincipalUI.chatsU.isConver(ChatsUsuario.getCurrentChat(), LoginUsuario.getIdUsuarioConectado()) == false && PrincipalUI.getCurrentAdministra() == false)
						{
							if (LoginUsuario.getIdUsuarioConectado() == usuFinal)
							{
								miniMenu.setVisible(true);
							}
							else
							{
								miniMenu.setVisible(false);
							}
						}
						else if (PrincipalUI.chatsU.isConver(ChatsUsuario.getCurrentChat(), LoginUsuario.getIdUsuarioConectado()) == true)
						{
							if (LoginUsuario.getIdUsuarioConectado() == usuFinal)
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
												
						if (PrincipalUI.chatsU.isConver(ChatsUsuario.getCurrentChat(), LoginUsuario.getIdUsuarioConectado()) == false && PrincipalUI.getCurrentAdministra() == true)
						{
							miniMenu.setVisible(true);
							if (LoginUsuario.getIdUsuarioConectado() == usuFinal)
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
						else if (PrincipalUI.chatsU.isConver(ChatsUsuario.getCurrentChat(), LoginUsuario.getIdUsuarioConectado()) == false && PrincipalUI.getCurrentAdministra() == false)
						{
							if (LoginUsuario.getIdUsuarioConectado() == usuFinal)
							{
								miniMenu.setVisible(true);
							}
							else
							{
								miniMenu.setVisible(false);
							}
						}
						else if (PrincipalUI.chatsU.isConver(ChatsUsuario.getCurrentChat(), LoginUsuario.getIdUsuarioConectado()) == true)
						{
							if (LoginUsuario.getIdUsuarioConectado() == usuFinal)
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
							System.out.println("Mensaje no editado");
						}
						else
						{
							mcc = PrincipalUI.mensajesC;
							
							// Editar mensaje
							editarMensaje(opcion, currentMensaje);
							
							
							
							// Recargar la interfaz
							PrincipalUI.containerMsj.removeAll();
							PrincipalUI.setCurrentAdministra(PrincipalUI.getCurrentAdministra());
							mcc.cargarChat(ChatsUsuario.getCurrentChat(), PrincipalUI.containerMsj);
							
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
						if (PrincipalUI.chatsU.isConver(ChatsUsuario.getCurrentChat(), LoginUsuario.getIdUsuarioConectado()) == false && PrincipalUI.getCurrentAdministra() == true)
						{
							miniMenu.setVisible(true);
							if (LoginUsuario.getIdUsuarioConectado() == usuario)
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
						else if (PrincipalUI.chatsU.isConver(ChatsUsuario.getCurrentChat(), LoginUsuario.getIdUsuarioConectado()) == false && PrincipalUI.getCurrentAdministra() == false)
						{
							if (LoginUsuario.getIdUsuarioConectado() == usuario)
							{
								miniMenu.setVisible(true);
							}
							else
							{
								miniMenu.setVisible(false);
							}
						}
						// Si es una conversación
						else if (PrincipalUI.chatsU.isConver(ChatsUsuario.getCurrentChat(), LoginUsuario.getIdUsuarioConectado()) == true && PrincipalUI.getCurrentAdministra() == false)
						{
							if (LoginUsuario.getIdUsuarioConectado() == usuario)
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

							mcc = PrincipalUI.mensajesC;
							PrincipalUI.nombreChat.setText(PrincipalUI.currentChatTitulo);
							if (PrincipalUI.chatsU.isConver(ChatsUsuario.getCurrentChat(), LoginUsuario.getIdUsuarioConectado()) == true)
							{
								PrincipalUI.nombreChat.setBounds(10, 12, 361, 33);
							}
							PrincipalUI.descripcionChat.setText(PrincipalUI.currentChatDesc);
							PrincipalUI.containerMsj.removeAll();
							PrincipalUI.setCurrentAdministra(PrincipalUI.getCurrentAdministra());
							mcc.cargarChat(ChatsUsuario.getCurrentChat(), PrincipalUI.containerMsj);
							
							parent.revalidate();
							parent.repaint();
						}
						else
						{
							System.out.println("Mensaje no borrado");
						}			
					}
					
				});
			}
			
			// Baja el scroll abajo del todo para mostrar el ultimo mensaje
			PrincipalUI.scrollMensaje.getViewport().setViewPosition(new Point(1, positionUI));
			
			// Determina el tamaño del scroll
			parent.setPreferredSize(new Dimension(560, positionUI));
			
			parent.revalidate();
			parent.repaint();
	
		}
		catch (SQLException e) 
		{
			System.out.println("Error de SQL (cargarChat)");
		}
	}
	
	// Insertar un mensaje por chat, usuario y texto del mensaje
	public void enviarMensaje(int id_c, int id_u, String mensaje)
	{	
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
			System.out.println("ErrorSQL (enviarMensaje)");
		}
	}
	
	// Devuelve todos los mensajes de un chat
	private ResultSet selectMensajesEnChat(int id_c)
	{
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
	        System.out.println("Error SQL (selectMensajesUserEnChat)");
	    }
	
		return rs;
	}

	// Edita el texto de un mensaje
	private void editarMensaje(String txt, int id_m)
	{
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
			System.out.println("ErrorSQL (cambiarMensaje)");
		}
	}
	
	// Borra el mensaje de un usuario en un chat
	public void borrarMensaje(int id_m)
	{
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
			System.out.println("ErrorSQL (borrarMensaje)");
		}
	}
}
