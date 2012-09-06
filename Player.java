import java.awt.event.KeyEvent;
import java.awt.Rectangle;

public class Player{
	
	public int PLAYER_SPEED=8;
	public boolean up, down, left, right, fire;
	public int vertX=0, vertY=0, shield = 100;
	int width=60, height=70;
	public int posX=SpaceInvaders.WIDTH/2, posY=SpaceInvaders.HEIGHT-100;
	
	
	public Player(){
	}
	
	public void keyPressed(KeyEvent e){
		switch(e.getKeyCode()){
			case KeyEvent.VK_UP: up = true; break;
			case KeyEvent.VK_DOWN: down = true; break;
			case KeyEvent.VK_LEFT: left = true; break;
			case KeyEvent.VK_RIGHT: right = true; break;
			case KeyEvent.VK_SPACE: fire = true; break;
		}
		updateSpeed();
	}
	
	public void keyReleased(KeyEvent e){
		switch(e.getKeyCode()){
			case KeyEvent.VK_UP: up = false; break;
			case KeyEvent.VK_DOWN: down = false; break;
			case KeyEvent.VK_RIGHT: right = false; break;
			case KeyEvent.VK_LEFT: left = false; break;
			//case KeyEvent.VK_SPACE: fire = false; break;
		}
		updateSpeed();
	}
	
	public void updateSpeed(){
		if(up){
			vertY=-PLAYER_SPEED;
		}
		if(down){
			vertY=PLAYER_SPEED;
		}
		if(right){
			vertX=PLAYER_SPEED;
		}
		if(left){
			vertX=-PLAYER_SPEED;
		}
	}
	
	public Rectangle getBounds(){
		return new Rectangle(posX, posY, width, height);
	}
}