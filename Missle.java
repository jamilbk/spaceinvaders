import java.util.*;
import java.awt.Rectangle;

public class Missle extends Projectile {
	
	int xloc, yloc;
	int width=3, height=16;
	
	public Missle (int x, int y) {
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
		yloc -= 10;
	}
	
	public boolean isOnScreen(){
		if(yloc>0 && yloc<=SpaceInvaders.HEIGHT){
			return true;
		}else{
			return false;
		}
	}
	
	public Rectangle getBounds(){
		return new Rectangle(xloc, yloc, 3, 16);
	}
	
	public boolean checkCollisions(ArrayList enemy, Missle missle){
		boolean collide=false;;
		if(enemy.size()>0){
			for(int i=0; i<enemy.size(); i++){
				if(((Enemy1)enemy.get(i)).getBounds().intersects((double)missle.getX(), (double)missle.getY(), (double)width, (double)height)){
					enemy.remove(i);
					collide=true;
					break;
				}
			}
		}
		return collide;
	}		
}