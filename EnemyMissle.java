import java.util.*;
import java.awt.Rectangle;

public class EnemyMissle extends Projectile {
	
	int xloc, yloc;
	public int width=9, height=9;
	
	public EnemyMissle (int x, int y) {
		super(x, y);
		xloc = x;
		yloc = y;
	}
	
	public int getX () {
		return xloc;
	}
	
	public int getY () {
		return yloc;
	}
	
	public void act () {
		move();
	}
	
	public void move () {
		yloc += 8;
	}
	
	public boolean isOnScreen(){
		if(yloc>0 && yloc<=SpaceInvaders.HEIGHT){
			return true;
		}else{
			return false;
		}
	}
	
	public Rectangle getBounds(){
		return new Rectangle(xloc, yloc, width, height);
	}
	
	public boolean checkCollisions(Player ship, EnemyMissle missle){
		boolean collide=false;
		if(ship.getBounds().intersects((double)missle.getX(), (double)missle.getY(), (double)width, (double)height)){
			collide=true;
		}
		return collide;
	}		
}