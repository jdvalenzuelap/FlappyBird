
//Juan Diego Valenzuela A00825349
//Proyecto Final POO FlapyBird

import javax.swing.JFrame;

public class MyVentana extends JFrame {
		PanelJuego panel;
		public MyVentana() {
			super("Flappy Bird!");
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
			
			this.panel = new PanelJuego(10); //Metemos el parametro de la velocidad en 10
			
			
			this.addKeyListener(this.panel);
			
			
			this.setResizable(false);
			this.add(panel);
			this.pack();
			this.setVisible(true);
		}
		
		public static void main(String[] args) {
			MyVentana ventana = new MyVentana();
			
		}
}
