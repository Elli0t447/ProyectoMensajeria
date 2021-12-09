package interfaz;

import javax.swing.JDialog;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import dialogos.InfoGrupo;
import dialogos.InvitarUsuario;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

public class InvitarUsuarioUI extends JDialog 
{

	private static final long serialVersionUID = 1L;
	
	private JScrollPane scroll;
	private JPanel amigoContainer;
	
	private InvitarUsuario invitar;
	
	/**
	 * Create the dialog.
	 */
	public InvitarUsuarioUI() 
	{
		invitar = new InvitarUsuario();
		
		getContentPane().setBackground(Color.WHITE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(InvitarUsuarioUI.class.getResource("/img/anyadir.png")));
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Invitar");
		setBackground(Color.WHITE);
		setBounds(100, 100, 450, 413);
		getContentPane().setLayout(null);
		
		JPanel BG = new JPanel();
		BG.setBackground(new Color(65, 105, 225));
		BG.setBounds(0, 0, 444, 52);
		getContentPane().add(BG);
		BG.setLayout(null);
		
		JLabel labelTitulo = new JLabel("Invitar usuarios al grupo");
		labelTitulo.setForeground(Color.WHITE);
		labelTitulo.setFont(new Font("Segoe UI", Font.BOLD, 17));
		labelTitulo.setBounds(122, 10, 201, 32);
		BG.add(labelTitulo);
		
		JLabel labelAmigos = new JLabel("Mis amigos:");
		labelAmigos.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		labelAmigos.setBounds(20, 68, 80, 31);
		getContentPane().add(labelAmigos);
		
		scroll = new JScrollPane();
		scroll.setSize(270, 170);
		scroll.setLocation(20, 100);
		scroll.setViewportBorder(null);
		
		scroll.setBorder(null);
		scroll.getVerticalScrollBar().setUnitIncrement(5);
		
		amigoContainer = new JPanel();
		amigoContainer.setBackground(new Color(240, 240, 240));
		amigoContainer.setBounds(20, 109, 292, 150);
		scroll.setViewportView(amigoContainer);
		amigoContainer.setLayout(null);
		getContentPane().add(scroll);
		
		JButton agregarButton = new JButton("Agregar seleccionados");
		agregarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{			
				invitar.agregarParticipantes();
				dispose();
				
				// Recargar interfaz
				InfoGrupoUI.getParticipantesContainer().removeAll();
				InfoGrupo info = new InfoGrupo();
				info.mostrarParticipantes(InfoGrupoUI.getParticipantesContainer());
				
			}
		});
		agregarButton.setFocusPainted(false);
		agregarButton.setBackground(new Color(65, 105, 225));
		agregarButton.setBorderPainted(false);
		agregarButton.setForeground(Color.WHITE);
		agregarButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
		agregarButton.setBounds(100, 304, 254, 52);
		getContentPane().add(agregarButton);
		
		JLabel lblNewLabel = new JLabel("<html>A\u00F1ade a algunos de tus amigos al grupo</html>");
		lblNewLabel.setForeground(Color.DARK_GRAY);
		lblNewLabel.setBorder(new MatteBorder(0, 0, 3, 0, (Color) new Color(65, 105, 225)));
		lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setBounds(309, 103, 118, 44);
		getContentPane().add(lblNewLabel);
		
		init();
	}
	
	private void init()
	{
		invitar.cargarMisAmigos(amigoContainer);
	}
}
