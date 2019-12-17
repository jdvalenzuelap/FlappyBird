
//Juan Diego Valenzuela A00825349
//Proyecto Final POO FlapyBird

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Bird extends Rectangle {
	private Image imagen;
	
	public Bird(int x, int y, int width, int height) {
		super(x,y,width,height);
		this.imagen = new ImageIcon("bird.png").getImage();
	}
	
	public Image getImagen() {
		return this.imagen;
	}
}
