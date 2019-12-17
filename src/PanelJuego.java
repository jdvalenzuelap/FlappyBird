
//Juan Diego Valenzuela A00825349
//Proyecto Final POO FlapyBird


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;


import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
//Crear una clse tubo que tenga como atributo una imagen y un rectangulo
public class PanelJuego extends JPanel implements ActionListener, MouseListener, KeyListener{
	
	private static final int WIDTH = 1200, //Medidas del panel
							 HEIGHT = 800;
	
	private Bird bird; //Rectangulo del pajaro que juega
	
	private ArrayList<Columna> columnas; //Para no tener que definir la longitud del arreglo usamos un arrayList, no sabemos que tanto dura el jugador
	
	private Random rand; //Para generar una altura de la columna aleatoria
	
	private int birdDown,//Variable para reducir altura del pajaro
				doble, 
				velocidad, //Velocidad del juego
				contador,//Para agregar las primeras cuatro columnas
				contador2, //Para el error al volver querer a jugar
				contador3, //Para aumentar velocidad
				puntaje;
	
	private boolean empieza, //Booleanos del juego
					pierde;
	
	private Image grass, 
				  nube;
	
	public PanelJuego(int velocidad){
		super();
		
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));

		Timer timer = new Timer(20, this); //Lo usamos para que genere un action performed cada cierto tiempo
		timer.start();
		this.rand = new Random();
		
		this.bird = new Bird(WIDTH / 2 - 10, HEIGHT / 2 - 10, 40, 40);
		this.columnas = new ArrayList<Columna>();
		this.birdDown = 0;
		this.doble = 0;
		this.velocidad = velocidad;
		this.contador = 0;
		this.pierde = false;
		this.puntaje = 0;
		this.grass = new ImageIcon("grass.png").getImage();
		this.nube = new ImageIcon("nube.png").getImage();
		this.contador2 = 0;
		
		this.addMouseListener(this);
		
		//Agregamos las primeras cuatro columnas
		this.agregarColumna();
		this.agregarColumna();
		this.agregarColumna();
		this.agregarColumna();
		
		
	}
	
	public void agregarColumna() {
		int separacion = 300;
		int ancho = 100;
		int alto = rand.nextInt(300) + 30;
		
		
		if (contador < 4) { //Agrega las primeras cuatro columnas
			this.columnas.add(new Columna(WIDTH + ancho + this.columnas.size() * separacion, HEIGHT - alto - 100, ancho, alto));
			this.columnas.add(new Columna(WIDTH + ancho + (this.columnas.size() - 1) * separacion, 0, ancho, HEIGHT - alto - separacion));
			contador ++;
		}else { //Agrega todas las demas columnas
			this.columnas.add(new Columna(this.columnas.get(this.columnas.size()-1).x + separacion*2, HEIGHT - alto - 100, ancho, alto));
			this.columnas.add(new Columna(this.columnas.get(this.columnas.size()-1).x, 0, ancho, HEIGHT - alto - separacion));
		}
		
		
	}
	
	private void pintaFondo(Graphics g) {
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, WIDTH, HEIGHT);
	}
	
	private void pintaBird(Graphics g) {
		//g.setColor(Color.RED);
		//g.fillRect((int)bird.getX(), (int)bird.getY(), (int)bird.getWidth(), (int)bird.getHeight());
		g.drawImage(this.bird.getImagen(), this.bird.x, this.bird.y, this.bird.width+20, this.bird.height+20, this);
	}
	
	private void pintaPiso(Graphics g) {
		g.setColor(Color.ORANGE);
		g.fillRect(0, HEIGHT-100, WIDTH, 120);
		g.drawImage(this.grass, 0, HEIGHT-140, WIDTH/4, 40, this);
		g.drawImage(this.grass, WIDTH/4, HEIGHT-140, WIDTH/4, 40, this);
		g.drawImage(this.grass, WIDTH/2, HEIGHT-140, WIDTH/4, 40, this);
		g.drawImage(this.grass, WIDTH - WIDTH/4 , HEIGHT-140, WIDTH/4, 40, this);
		//g.setColor(new Color(0,210,0));
		//g.fillRect(0, HEIGHT-120, WIDTH, 20);
	}
	
	private void pintaColumna(Graphics g, Columna columna) {
		//g.setColor(new Color(0, 150, 0));
		if(columna.y == 0) { //Columna de arriba
			g.drawImage(columna.getImagen2(),columna.x,columna.y,columna.width+15,columna.height+15,this);
		}else { //Columna de abajo
			g.drawImage(columna.getImagen(),columna.x,columna.y,columna.width+15,columna.height+15,this);
		}
		
		//g.fillRect(columna.x, columna.y, columna.width, columna.height);
		
	}
	
	private void pintaNube(Graphics g) {
		g.drawImage(this.nube, 170, 210, 200, 100, this);
		g.drawImage(this.nube, 670, 110, 200, 100, this);
	}
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//Fondo
		this.pintaFondo(g);
		//Nube
		this.pintaNube(g);
		//Pajaro
		this.pintaBird(g);
		//Columnas
		for(int i=0; i < this.columnas.size(); i++) {
			this.pintaColumna(g, this.columnas.get(i));
		}
		//Piso
		this.pintaPiso(g);
		//Texto de perdio
		if(this.pierde) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", 1, 100));
			g.drawString("GAME OVER", 275, HEIGHT/2);
			g.setFont(new Font("Arial", 1, 40));
			g.drawString("Click para volver a jugar!", 350, HEIGHT/2 + 50);
		}
		//Puntaje
		g.setFont(new Font("Arial",1,100));
		g.setColor(Color.WHITE);
		g.drawString(String.valueOf(this.puntaje), WIDTH/2 - 25, 100);
		
		
		
	}
	
	public void brinca() { //Hacemos brincar el pajaro
		
		this.birdDown -= this.birdDown + 13;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		if(this.contador3 == 200) {
			this.velocidad++;
			this.contador3 = 0;
		}else {
			this.contador3++;
		}
		if(this.empieza && !this.pierde) {
			//Movimiento de las columnas y eliminar las que salieron del panel
			for(int i=0; i < this.columnas.size(); i++) {
				Columna actual = this.columnas.get(i);
				actual.x -= this.velocidad;
			}
			
			for(int i=0; i < this.columnas.size(); i++) {
				Columna actual = this.columnas.get(i);
				//Eliminamos columnas fuera del panel
				if(actual.x + actual.width < 0) {
					
					this.columnas.remove(actual);
					if(actual.y == 0) { //Solo agregamos nuevas columnas si la ultima que eliminamos es una columna superior
						this.agregarColumna();
					}
				}	
			}
			
			
			
			//Caida del pajaro
			this.doble++;
			if(this.doble % 2 == 0 && this.birdDown < 16) {
				this.birdDown += 2; //Hacemos la caida exponencial con cada dos actionPerformed y tambien para poder hacer el brinco
			}
			this.bird.y += birdDown;
			
			for(int i=0; i < this.columnas.size(); i++) {
				
				Columna actual = this.columnas.get(i);
				
				//Definimos el espacio en el que se le agrega un punto al pajaro
				if(actual.y == 0 && this.bird.x + bird.width /2 > actual.x + actual.width / 2 - 7 && this.bird.x + bird.width /2 < actual.x + actual.width / 2 + 7) {
					this.puntaje++;
					System.out.println(this.puntaje);
					
				}
				
				//Cuando choca el pajaro con columna
				if(actual.intersects(this.bird)) {
					this.pierde = true;
				}
				
			}
			
			//Cuando choca el pajaro con el techo o el piso
			if(this.bird.y > HEIGHT - 120 || this.bird.y < 0 ) {
				this.bird.y = HEIGHT - 140;
				this.pierde = true;
			}
			
			this.repaint();
		}
		
		
		
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		
	}

	@Override
	public void mousePressed(MouseEvent evt) {
		if(!this.pierde) {
			empieza = true;
			this.brinca();
		}else {
			if(this.contador2 == 1) {
				this.columnas.clear();
				this.bird = new Bird(WIDTH / 2 - 10, HEIGHT / 2 - 10, 40, 40);
				this.birdDown = 0;
				this.doble = 0;
				this.contador = 0;
				this.pierde = false;
				this.puntaje = 0;
				this.contador2 = 0;
				this.velocidad = 10;
				this.agregarColumna();
				this.agregarColumna();
				this.agregarColumna();
				this.agregarColumna();
			}else {
				this.contador2++;
			}
			
		}

		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent evt) {
		// TODO Auto-generated method stub
		if(evt.getKeyCode() == KeyEvent.VK_SPACE) {
			if(!this.pierde) {
				empieza = true;
				this.brinca();
			}else {
				if(this.contador2 == 1) {
					this.columnas.clear();
					this.bird = new Bird(WIDTH / 2 - 10, HEIGHT / 2 - 10, 40, 40);
					this.birdDown = 0;
					this.doble = 0;
					this.contador = 0;
					this.pierde = false;
					this.puntaje = 0;
					this.contador2 = 0;
					this.velocidad = 10;
					this.agregarColumna();
					this.agregarColumna();
					this.agregarColumna();
					this.agregarColumna();
				}else {
					this.contador2++;
				}
				
			}
			
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
