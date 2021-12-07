package dialogos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import aplicacion.Conexion;
import aplicacion.LoginUsuario;
import interfaz.PrincipalUI;

public class InvitarUsuario 
{
	private Connection cn;
	
	public InvitarUsuario()
	{
		cn = Conexion.Conectar();
	}
	
	public static ArrayList<Integer> amigosList = new ArrayList<>();
	
	public void cargarMisAmigos(JPanel padre)
	{
		try 
		{
			ResultSet rs = amigosNoEnElGrupo(LoginUsuario.getIdUsuario(), PrincipalUI.getCurrentChat());
			
			int positionUI = 10;
			int incremento = 44;
			
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
				
				JPanel panelAmigo = new JPanel();
				panelAmigo.setBackground(new Color(255, 250, 250));
				panelAmigo.setBounds(10, positionUI, 235, 34);
				panelAmigo.setLayout(null);
				padre.add(panelAmigo);
				
				positionUI += incremento;
				
				JLabel nombreAmigo = new JLabel(LoginUsuario.nombreUserPorId(usuarioFinal));
				nombreAmigo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
				nombreAmigo.setBounds(10, 10, 252, 13);
				panelAmigo.add(nombreAmigo);		
				
				panelAmigo.addMouseListener(new MouseAdapter()
				{
					
					@Override
					public void mouseClicked(MouseEvent e)
					{							
						
						if (panelAmigo.getBackground().equals(new Color(255, 250, 250)))
						{
							panelAmigo.setBackground(new Color(65, 105, 205));
							nombreAmigo.setForeground(Color.WHITE);
							amigosList.add(usuarioFinal);
						}
						else
						{
							panelAmigo.setBackground(new Color(255, 250, 250));
							nombreAmigo.setForeground(Color.BLACK);
							for (int i = 0; i < amigosList.size(); i++)
							{
								if (amigosList.get(i) == usuarioFinal)
								{
									amigosList.remove(i);
								}
							}
						}
					}
				});
						
				padre.setPreferredSize(new Dimension(250, positionUI));
				padre.revalidate();
				padre.repaint();
			}
	
		} 
		catch (SQLException e) 
		{
			System.out.println("Error de SQL");
			e.printStackTrace();
		}
	}
	
	public void agregarParticipantes()
	{
		CrearGrupo c = new CrearGrupo();
		
		for (int i: amigosList)
		{
			c.addParticipante(PrincipalUI.getCurrentChat(), i, false);
		}
	}
	
	public ResultSet amigosNoEnElGrupo(int id_u, int id_chat)
	{
		ResultSet rs = null;
		
		try 
	    {
	        PreparedStatement pst = cn.prepareStatement("SELECT * FROM amistad WHERE ((id_usu1 = ? OR id_usu2 = ?) AND aceptada = true) AND (id_usu1 NOT IN (SELECT id_usuario FROM participa WHERE id_chat = ?) OR id_usu2 NOT IN (SELECT id_usuario FROM participa WHERE id_chat = ?))");
	        pst.setInt(1, id_u);
	        pst.setInt(2, id_u);
	        pst.setInt(3, id_chat);
	        pst.setInt(4, id_chat);
	        rs = pst.executeQuery();
	        
	    } 
	    catch (SQLException ex) 
	    {
	        System.out.println("Error SQL (amigosNoEnElGrupo)");
	    }
		
		return rs;
	}
}
