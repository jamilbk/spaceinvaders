import java.util.*;


public class Projectile {
	
	int xloc, yloc;
	
	public Projectile (int x, int y) {
		xloc = x;
		yloc = y;
	}
	
	public int getX () {
		return xloc;
	}
	
	public int getY () {
		return yloc;
	}
	
	public void move () {
		yloc -= 5;
	}	
	
	public boolean isOnScreen(){
		if(yloc>0 && yloc<=SpaceInvaders.HEIGHT){
			return true;
		}else{
			return false;
		}
	}
}