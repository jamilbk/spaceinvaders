import java.util.*;
import java.awt.Rectangle;

public class Enemy1 {
	
	public int xloc, yloc, xhome;
	public int xx = 0;
	int speed = 0;
	int width=30, height=29;
	Random rand;
	
	public Enemy1(int x, int y, int spd) {
		xloc = x;
		yloc = y;
		xhome = x;
		speed = spd;
		rand = new Random();
	}
	
	public int getX () {
		return xloc;
	}
	
	public int getY () {
		return yloc;
	}
	
	public void act () {
		move();
		shoot();
	}
	
	public void move () {
	//	yloc += speed;
	//	xloc = (int)(100*Math.sin((double)yloc/50))+xhome;
		
		yloc += ((double)(3 * speed) / 4) + (5*Math.cos((double)xx/15));
		xloc = ((int)(75*Math.sin((double)yloc/50))+xhome);
		xx++;
		
		if (yloc >= 650) {
			yloc += (speed * speed)/2;
		}
	}
	
	public boolean shoot () {
		int x = rand.nextInt(50);
		if (x == 0) {
			return true;
		}
		else return false;
		
	}
	
	
	public boolean isOnScreen(){
		if(yloc<=SpaceInvaders.HEIGHT){
			return true;
		}else{
			return false;
		}
	}
	
	public Rectangle getBounds(){
		return new Rectangle(xloc, yloc, width, height);
	}
}