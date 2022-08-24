/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asmtomachcode;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Ventana extends JFrame{
    //todos estos son los componentes para activar las funciones correspondientes
	private JButton btnOk, btnLmp; //atratan el clic de la accion a realizar
	private JPanel pnl, pnl2;
	public static JTextArea areaTranslate;
	private JTextField txtBus;
	private JLabel lb1, lb2,lb3,lb4;
        
        private JScrollPane jscroll=new JScrollPane();
        
        
	private Ensamblador ensambla;
        

	public Ventana() { // se invoca a la clase padre Ensamblador con super y asi mismo se inicializan los
		//componentes visibles 
		super("Ensamblador"); 
		setSize(600,400);
		setLayout(new FlowLayout() ); //ordena los componentes en un flujo direccional
		//this.getContentPane().setBackground(Color);
                initComponents();
                try {
                    FondoSwing fondo = new FondoSwing(ImageIO.read(new File("C:\\Users\\fampa\\OneDrive\\Documentos\\NetBeansProjects\\Traductor\\src\\asmtomachcode\\interprete.jpg")));
                    JPanel panel = (JPanel) this.getContentPane();
                    panel.setBorder(fondo);
                }catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                setLocationRelativeTo(null);
		setVisible(true);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //para cerrar el programa con x
                
	}

	public void initComponents() {

		pnl = new JPanel(); //creando el contenedor
		pnl.setPreferredSize(new Dimension(500,200) ); //tamano
                pnl.setBackground(Color.blue);
		pnl2 = new JPanel();
                pnl2.setPreferredSize(new Dimension(500,120) );
                pnl2.setBackground(Color.ORANGE);
		btnOk = new JButton("OK");
		btnOk.setPreferredSize(new Dimension(100,40));
		btnLmp = new JButton("Limpiar");
		btnLmp.setPreferredSize(new Dimension(100,40));
		btnLmp.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent ev){
				areaTranslate.setText("");
				txtBus.setText("");
		}}); //accion de limpiar al dar clic en el boton limpiar

		EventoBuscar evB = new EventoBuscar(); //accion del boton 
		btnOk.addActionListener( evB );
		//cuerpo 
		areaTranslate = new JTextArea(20,50);
                areaTranslate.setBackground(Color.BLACK);
                areaTranslate.setForeground(Color.white);
                areaTranslate.setFont(new Font("Consolas",Font.BOLD,22));
                areaTranslate.setEditable(false);
		txtBus = new JTextField(45);
                txtBus.setFont(new Font("Consolas",Font.BOLD,18));
		lb2 = new JLabel("Lenguaje Ensamblador:");
                lb2.setFont(new Font("Arial Italic",Font.BOLD,18));
                lb2.setForeground(Color.white);
                lb1 = new JLabel("Código Máquina:");
                lb1.setFont(new Font("Arial Italic",Font.BOLD,18));
                lb3=new JLabel("TRADUCTOR DE LENGUAJE");
                lb3.setFont(new Font("Arial Italic",Font.BOLD,18));
                lb3.setForeground(Color.white);
                lb3.setBackground(Color.red);
                lb3.setOpaque(true);
                lb4=new JLabel("ENSAMBLADOR A CÓDIGO MÁQUINA");
                lb4.setFont(new Font("Arial Italic",Font.BOLD,18));
                lb4.setForeground(Color.white);
                lb4.setBackground(Color.red);
                lb4.setOpaque(true);
                
		//agregando a ls componentes pra que sean vistos cuando en el constructor se de la orden con setvisible
		pnl.add(lb3);
                pnl.add(lb4);
                pnl.add(lb2);
		pnl.add(txtBus);//codigo
		pnl.add(btnOk);
		pnl.add(btnLmp);
                pnl2.add(lb1);
		pnl2.add(areaTranslate);
		add(pnl);
		add(pnl2);

	}

	class EventoBuscar implements ActionListener {
		public void actionPerformed ( ActionEvent ev ) {
			if( txtBus.getText().isEmpty() ) {
				JOptionPane.showMessageDialog(null,"No se ha ingresado"+
							" cadena de codigo","Codigo: ERROR",0);
				return;
			}
                        //String instruccion=txtBus.getText();
			ensambla = new Ensamblador();//crear objeto de instruccion en lenguaje ensamblador
                        ensambla.setInstruccion(txtBus.getText());//asignar instruccion al objeto
                        areaTranslate.setText("");//limpia area de texto
                        try{
                            ensambla.machineCode(ensambla.getInstruccion());//llamar a metodo de conversion de ASM a codigo maquina pasando como parametro la instruccion obtenida del txtField
                            areaTranslate.append("\n\t\t"+ensambla.getCodigo());//mostrar codigo maquina en textArea
                        }catch(Exception e){
                            JOptionPane.showMessageDialog(null,"ERROR DE SINTAXIS","Codigo: ERROR",0);
                        }
		}
	}

}
