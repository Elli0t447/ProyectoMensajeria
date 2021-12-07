package interfaz;

import java.awt.BorderLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import dialogos.CrearGrupo;


public class CrearGrupoUI extends JDialog 
{
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	
	private CrearGrupo newGrp;
	private JPanel participantes;
	private JScrollPane scroll;


	/**
	 * Create the dialog.
	 */
	public CrearGrupoUI() 
	{
		setModalityType(ModalityType.APPLICATION_MODAL);
		newGrp = new CrearGrupo();
		
		setTitle("Nuevo chat");
		setBounds(100, 100, 466, 485);
		setIconImage(Toolkit.getDefaultToolkit().getImage(CrearGrupoUI.class.getResource("/img/chat.png")));
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JPanel BG = new JPanel();
			BG.setBackground(new Color(65, 105, 225));
			BG.setBounds(0, 0, 545, 52);
			contentPanel.add(BG);
			BG.setLayout(null);
			
			JLabel title = new JLabel("Crear nuevo grupo");
			title.setForeground(Color.WHITE);
			title.setFont(new Font("Segoe UI", Font.BOLD, 20));
			title.setBounds(143, 10, 183, 35);
			BG.add(title);
		}
		
		JLabel labelNombre = new JLabel("Nombre:");
		labelNombre.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		labelNombre.setBounds(51, 74, 63, 25);
		contentPanel.add(labelNombre);
		
		JLabel labelDescripcion = new JLabel("Descripci\u00F3n:");
		labelDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		labelDescripcion.setBounds(37, 119, 77, 25);
		contentPanel.add(labelDescripcion);
		
		JLabel labelParticipantes = new JLabel("A\u00F1adir participantes:");
		labelParticipantes.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		labelParticipantes.setBounds(20, 200, 146, 25);
		contentPanel.add(labelParticipantes);
		
		JTextField nombreGrupo = new JTextField();
		nombreGrupo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		nombreGrupo.setBounds(124, 78, 298, 22);
		contentPanel.add(nombreGrupo);
		nombreGrupo.setColumns(10);
		
		JTextArea descripcionGrupo = new JTextArea();
		descripcionGrupo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		descripcionGrupo.setBorder(new LineBorder(Color.LIGHT_GRAY));
		descripcionGrupo.setBounds(124, 121, 298, 69);
		descripcionGrupo.setLineWrap(true);
		contentPanel.add(descripcionGrupo);
	
		scroll = new JScrollPane();
		scroll.setSize(230, 200);
		scroll.setLocation(20, 230);
		scroll.setViewportBorder(null);
		
		scroll.setBorder(null);
		scroll.getVerticalScrollBar().setUnitIncrement(5);
		
		participantes = new JPanel();
		participantes.setBounds(37, 235, 233, 202);
		scroll.setViewportView(participantes);
		participantes.setLayout(null);
		contentPanel.add(scroll);
		
		JButton crearGrupoBtn = new JButton("CREAR GRUPO");
		crearGrupoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				newGrp.crearGrupo(nombreGrupo.getText(), descripcionGrupo.getText());
				dispose();
				
				// Mostrar de nuevo los chats del usuario
				PrincipalUI.container.removeAll();
				PrincipalUI.chatsU.mostrarListaChats(PrincipalUI.container);
			}
		});
		crearGrupoBtn.setBorderPainted(false);
		crearGrupoBtn.setBackground(new Color(65, 105, 225));
		crearGrupoBtn.setForeground(new Color(255, 255, 255));
		crearGrupoBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
		crearGrupoBtn.setBounds(292, 353, 146, 62);
		contentPanel.add(crearGrupoBtn);
		
		init();
	}
	
	private void init()
	{
		newGrp.rellenarAmigos(participantes);
	}
}
