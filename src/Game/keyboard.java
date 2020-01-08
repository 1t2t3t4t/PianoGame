package Game;

import java.awt.EventQueue;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class keyboard extends JLayeredPane{
	
	JPanel shape1 = new JPanel();
	JPanel shape2 = new JPanel();
	JPanel shape3 = new JPanel();
	JPanel shape4 = new JPanel();
	JPanel shape5 = new JPanel();
	JPanel btnC = new JPanel();
	JPanel btnD = new JPanel();
	JPanel btnE = new JPanel();
	JPanel btnF = new JPanel();
	JPanel btnG = new JPanel();
	JPanel btnA = new JPanel();
	JPanel btnB = new JPanel();
	JPanel btnC1 = new JPanel();
	private Synthesizer synt;
	

	private JFrame frame;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					keyboard window = new keyboard();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public keyboard() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setLayout(null);
		
		frame = new JFrame();
		frame.getContentPane().setForeground(Color.BLACK);
		frame.getContentPane().add(this, BorderLayout.WEST);
		frame.setBounds(0, 0, 800, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(this);
		frame.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				String str = e.getKeyChar()+"";
				System.out.println(str);
				switch(str){
				case "s" :
					synt.getChannels()[0].noteOff(60);
					break;
				case "e" :
					synt.getChannels()[0].noteOff(61);
					break;
				case "d" :
					synt.getChannels()[0].noteOff(62);
					break;
				case "r" :
					synt.getChannels()[0].noteOff(63);
					break;
				case "f" :
					synt.getChannels()[0].noteOff(64);
					break;
				case "g" :
					synt.getChannels()[0].noteOff(65);
					break;
				case "y" :
					synt.getChannels()[0].noteOff(66);
					break;
				case "h" :
					synt.getChannels()[0].noteOff(67);
					break;	
				case "u" :
					synt.getChannels()[0].noteOff(68);
					break;
				case "j" :
					synt.getChannels()[0].noteOff(69);
					break;
				case "i" :
					synt.getChannels()[0].noteOff(70);
					break;	
				case "k" :
					synt.getChannels()[0].noteOff(71);
					break;
				case "l" :
					synt.getChannels()[0].noteOff(72);
					break;	
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				String str = e.getKeyChar()+"";
				switch(str){
				case "s" :
					synt.getChannels()[0].noteOn(60, 100);
					break;
				case "e" :
					synt.getChannels()[0].noteOn(61, 100);
					break;
				case "d" :
					synt.getChannels()[0].noteOn(62, 100);
					break;
				case "r" :
					synt.getChannels()[0].noteOn(63, 100);
					break;
				case "f" :
					synt.getChannels()[0].noteOn(64, 100);
					break;
				case "g" :
					synt.getChannels()[0].noteOn(65, 100);
					break;
				case "y" :
					synt.getChannels()[0].noteOn(66, 100);
					break;
				case "h" :
					synt.getChannels()[0].noteOn(67, 100);
					break;	
				case "u" :
					synt.getChannels()[0].noteOn(68, 100);
					break;
				case "j" :
					synt.getChannels()[0].noteOn(69, 100);
					break;
				case "i" :
					synt.getChannels()[0].noteOn(70, 100);
					break;	
				case "k" :
					synt.getChannels()[0].noteOn(71, 100);
					break;
				case "l" :
					synt.getChannels()[0].noteOn(72, 100);
					break;	
				}
			}
		});
		try {
			synt = MidiSystem.getSynthesizer();
			synt.open();
		} catch (MidiUnavailableException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		shape1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) { 
				shape1.setBackground(Color.red); 
				keyPressed("e");
			}
			@Override
			public void mouseReleased(MouseEvent e) { 
				shape1.setBackground(Color.black);
				keyReleased("e");
				}
		});
		shape1.setBackground(Color.black);
		shape1.setBounds(145, 0, 30, 130);
		this.add(shape1, new Integer(1), 0);
		
		

		shape2.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) { 
				shape2.setBackground(Color.red); 
				keyPressed("r");
				}
			@Override
			public void mouseReleased(MouseEvent e) { 
				shape2.setBackground(Color.black);
				keyReleased("r");
				}
		});
		shape2.setBackground(Color.black);
		shape2.setBounds(207, 0, 30, 130);
		this.add(shape2, new Integer(1), 0);
		
		

		shape3.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) { 
				shape3.setBackground(Color.red); 
				keyPressed("y");
			}
			@Override
			public void mouseReleased(MouseEvent e) { 
				shape3.setBackground(Color.black);
				keyReleased("y");
				}
		});
		shape3.setBackground(Color.black);
		shape3.setBounds(330, 0, 30, 130);
		this.add(shape3, new Integer(1), 0);
		
		

		shape4.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) { 
				shape4.setBackground(Color.red); 
				keyPressed("u");
			}
			@Override
			public void mouseReleased(MouseEvent e) { 
				shape4.setBackground(Color.black);
				keyReleased("u");
				}
		});
		shape4.setBackground(Color.black);
		shape4.setBounds(3100, 0, 30, 130);
		this.add(shape4, new Integer(1), 0);
		
		

		shape5.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) { 
				shape5.setBackground(Color.red); 
				keyPressed("i");
			}
			@Override
			public void mouseReleased(MouseEvent e) { 
				shape5.setBackground(Color.black);
				keyReleased("i");
				}
		});
		shape5.setBackground(Color.black);
		shape5.setBounds(452, 0, 30, 130);
		this.add(shape5, new Integer(1), 0);
		
		

		btnC.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				btnC.setBackground(Color.red); 
				keyPressed("s");
				}
			@Override
			public void mouseReleased(MouseEvent e) {
				btnC.setBackground(Color.white);
				keyReleased("s");
				}
		});
		btnC.setBackground(Color.WHITE);
		btnC.setBounds(100, 0, 60, 240);
		this.add(btnC, new Integer(0),0);
		
		
		

		btnD.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) { 
				btnD.setBackground(Color.red); 
				keyPressed("d");
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				btnD.setBackground(Color.white);
				keyReleased("d");
				}
		});
		
		btnD.setBackground(Color.WHITE);
		btnD.setBounds(161, 0, 60, 240);
		this.add(btnD, new Integer(0),0);
		
		
		

		btnE.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				btnE.setBackground(Color.red); 
				keyPressed("f");
			}
			@Override
			public void mouseReleased(MouseEvent e) { 
				btnE.setBackground(Color.white);
				keyReleased("f");
				}
		});
		
		btnE.setBackground(Color.WHITE);
		btnE.setBounds(222, 0, 60, 240);
		this.add(btnE, new Integer(0),0);
		
		
		

		btnF.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) { 
				btnF.setBackground(Color.red); 
				keyPressed("g");
			}
			@Override
			public void mouseReleased(MouseEvent e) { 
				btnF.setBackground(Color.WHITE);
				keyReleased("g");
				}
		});
		btnF.setBackground(Color.WHITE);
		btnF.setBounds(283, 0, 60, 240);
		this.add(btnF, new Integer(0),0);
		
		
		

		btnG.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) { 
				btnG.setBackground(Color.red); 
				keyPressed("h");
			}
			@Override
			public void mouseReleased(MouseEvent e) { 
				btnG.setBackground(Color.white);
				keyReleased("h");
				}
		});
		btnG.setBackground(Color.WHITE);
		btnG.setBounds(344, 0, 60, 240);
		this.add(btnG, new Integer(0),0);
		
		
		

		btnA.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) { 
				btnA.setBackground(Color.red); 
				keyPressed("j");
			}
			@Override
			public void mouseReleased(MouseEvent e) { 
				btnA.setBackground(Color.white);
				keyReleased("j");
				}
		});
		btnA.setBackground(Color.WHITE);
		btnA.setBounds(405, 0, 60, 240);
		this.add(btnA, new Integer(0),0);
		
		
		
		

		btnB.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) { 
				btnB.setBackground(Color.red); 
				keyPressed("k");
			}
			@Override
			public void mouseReleased(MouseEvent e) { 
				btnB.setBackground(Color.white);
				keyReleased("k");
				}
		});
		btnB.setBackground(Color.WHITE);
		btnB.setBounds(466, 0, 60, 240);
		this.add(btnB, new Integer(0),0);
		
		
		

		btnC1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) { 
				btnC1.setBackground(Color.red); 
				keyPressed("l");
			}
			@Override
			public void mouseReleased(MouseEvent e) { 
				btnC1.setBackground(Color.white);
				keyReleased("l");
				}
		});
		btnC1.setBackground(Color.WHITE);
		btnC1.setBounds(527, 0, 60, 240);
		this.add(btnC1, new Integer(0),0);
		
		frame.setVisible(true);
		
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		
		g.drawRect(99, 0, 61, 241);
		g.drawRect(160, 0, 61, 241);
		g.drawRect(221, 0, 61, 241);
		g.drawRect(282, 0, 61, 241);
		g.drawRect(343, 0, 61, 241);
		g.drawRect(404, 0, 61, 241);
		g.drawRect(465, 0, 61, 241);
		g.drawRect(526, 0, 61, 241);
		
		
		
	}
	
	public void keyPressed(String str){
			switch(str){
			case "s" :
				synt.getChannels()[0].noteOn(60, 100);
				break;
			case "e" :
				synt.getChannels()[0].noteOn(61, 100);
				break;
			case "d" :
				synt.getChannels()[0].noteOn(62, 100);
				break;
			case "r" :
				synt.getChannels()[0].noteOn(63, 100);
				break;
			case "f" :
				synt.getChannels()[0].noteOn(64, 100);
				break;
			case "g" :
				synt.getChannels()[0].noteOn(65, 100);
				break;
			case "y" :
				synt.getChannels()[0].noteOn(66, 100);
				break;
			case "h" :
				synt.getChannels()[0].noteOn(67, 100);
				break;	
			case "u" :
				synt.getChannels()[0].noteOn(68, 100);
				break;
			case "j" :
				synt.getChannels()[0].noteOn(69, 100);
				break;
			case "i" :
				synt.getChannels()[0].noteOn(70, 100);
				break;	
			case "k" :
				synt.getChannels()[0].noteOn(71, 100);
				break;
			case "l" :
				synt.getChannels()[0].noteOn(72, 100);
				break;	
			}
	}
	
	
	public void keyReleased(String str){
			switch(str){
			case "s" :
				synt.getChannels()[0].noteOff(60);
				break;
			case "e" :
				synt.getChannels()[0].noteOff(61);
				break;
			case "d" :
				synt.getChannels()[0].noteOff(62);
				break;
			case "r" :
				synt.getChannels()[0].noteOff(63);
				break;
			case "f" :
				synt.getChannels()[0].noteOff(64);
				break;
			case "g" :
				synt.getChannels()[0].noteOff(65);
				break;
			case "y" :
				synt.getChannels()[0].noteOff(66);
				break;
			case "h" :
				synt.getChannels()[0].noteOff(67);
				break;	
			case "u" :
				synt.getChannels()[0].noteOff(68);
				break;
			case "j" :
				synt.getChannels()[0].noteOff(69);
				break;
			case "i" :
				synt.getChannels()[0].noteOff(70);
				break;	
			case "k" :
				synt.getChannels()[0].noteOff(71);
				break;
			case "l" :
				synt.getChannels()[0].noteOff(72);
				break;	
			}
	}
}
