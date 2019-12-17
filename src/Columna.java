
//Juan Diego Valenzuela A00825349
//Proyecto Final POO FlapyBird

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Columna extends Rectangle {
	private Image imagen,
				  imagen2;
	
	public Columna(int x, int y, int width, int height) {
		super(x,y,width,height);
		this.imagen = new ImageIcon("columna.png").getImage();
		this.imagen2 = new ImageIcon("columna2.png").getImage();
	}
	
	public Image getImagen() { //Columna de arriba
		return this.imagen;
	}
	
	public Image getImagen2() { //Columna de abajo
		return this.imagen2;
	}
}
