import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.applet.Applet;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.io.File;
import javax.imageio.ImageIO;
import java.net.URL;
import java.applet.AudioClip;
import java.util.ArrayList;
import java.util.Random;


public class SpaceInvaders extends Canvas implements KeyListener{
	public static final int WIDTH=400;
	public static final int HEIGHT=500;
	private static AudioClip level1Loop;
	private BufferedImage bg;
	private BufferedImage ship, shipLeft, shipDown, shipUp, shipRight;
	private BufferedImage enemy1img, enemy1explodeimg, eMissleimg, shieldimg, titleimg, gameoverimg;
	private int bgCount, score;
	private BufferStrategy bStrategy;
	private boolean god = false;
	private Player player1;
	private ArrayList Missles;
	private ArrayList Enemy1s;
	private ArrayList enemyMissles;
	private BufferedImage missle;
	private BufferedImage buffer;
	private long endTime, startTime;
	private Random gen;
	private AudioClip missleFire, boom;
	public boolean start = false, gameover = false, startplay = false;
	
	public SpaceInvaders(){
		JFrame SpaceInvaders = new JFrame("SpaceInvaders");
		
		JPanel panel = (JPanel) SpaceInvaders.getContentPane();
		setBounds(0, 0, WIDTH, HEIGHT);
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		panel.setLayout(null);
		panel.add(this);
		bgCount = 0;
		player1 = new Player();
		Missles = new ArrayList();
		Enemy1s = new ArrayList();
		enemyMissles = new ArrayList();
		gen = new Random();
		score = 0;
		
		SpaceInvaders.setBounds(0, 0, WIDTH, HEIGHT);
		SpaceInvaders.setVisible(true);
		SpaceInvaders.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		SpaceInvaders.setResizable(false);
		createBufferStrategy(2);
		bStrategy = getBufferStrategy();
		requestFocus();
		addKeyListener(this);
		try{
			ship = ImageIO.read(new File("ship.gif"));
		}catch(Exception e){
			System.err.print(e);
		}
		try{
			shipLeft = ImageIO.read(new File("shipLeft.gif"));
		}catch(Exception e){
			System.err.print(e);
		}
		try{
			shipRight = ImageIO.read(new File("shipRight.gif"));
		}catch(Exception e){
			System.err.print(e);
		}
		try{
			shipDown = ImageIO.read(new File("shipDown.gif"));
		}catch(Exception e){
			System.err.print(e);
		}
		try{
			shipUp = ImageIO.read(new File("shipUp.gif"));
		}catch(Exception e){
			System.err.print(e);
		}
		try{
			bg = ImageIO.read(new File("bg.gif"));
		}catch(Exception e){
			System.err.print(e);
		}
		try{
			enemy1img = ImageIO.read(new File("enemy1.gif"));
		}catch(Exception e){
			System.err.print(e);
		}
		try{
			missle = ImageIO.read(new File("missle.gif"));
		}catch(Exception e){
			System.err.print(e);
		}
		try{
			enemy1explodeimg = ImageIO.read(new File("explode.gif"));
		}catch(Exception e){
			System.err.print(e);
		}
		try{
			eMissleimg = ImageIO.read(new File("enemymissle.gif"));
		}catch(Exception e){
			System.err.print(e);
		}
		try{
			shieldimg = ImageIO.read(new File("shield.gif"));
		}catch(Exception e){
			System.err.print(e);
		}
		try{
			titleimg = ImageIO.read(new File("title.gif"));
		}catch(Exception e){
			System.err.print(e);
		}
		try{
			gameoverimg = ImageIO.read(new File("gameover.gif"));
		}catch(Exception e){
			System.err.print(e);
		}
		try{
			level1Loop = Applet.newAudioClip(new URL("file:game.wav"));
		}catch(Exception e){
			System.err.print(e);
		}
		try{
			missleFire = Applet.newAudioClip(new URL("file:missleFire.wav"));
		}catch(Exception e){
			System.err.print(e);
		}
		try{
			boom = Applet.newAudioClip(new URL("file:explode.wav"));
		}catch(Exception e){
			System.err.print(e);
		}
	}
	
	public void paintWorld(){
		Graphics2D g = (Graphics2D)bStrategy.getDrawGraphics();
		bgCount++;
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		if (!start) {
			g.drawImage(titleimg, 0, 0, this);
			g.setColor(Color.white);
			g.drawString("PRESS 'S' TO START", (WIDTH/2)-50, (HEIGHT/2)-50);
		}
		else {
			if (startplay) {
				level1Loop.loop();
				startplay = false;
			}
			if (gameover) {
				g.fillRect(0, 0, WIDTH, HEIGHT);
				g.drawImage(gameoverimg, 100, 200, this);
				g.setColor(Color.white);
				g.drawString("score: "+(score*10), (WIDTH/2)-25, (HEIGHT/2)+100);
			}
			else {
				g.setPaint(new TexturePaint(bg, new Rectangle(0, bgCount, 300, 500)));
				g.fillRect(0, 0, WIDTH, HEIGHT);
				
				paintEnemy1(g);
				paintShip(g);
				paintMissles(g);
				paintenemyMissles(g);
				paintFPS(g);
			}
		}
		bStrategy.show();
	}
	
	public void paintEnemy1(Graphics2D g){
		if(Enemy1s.size() < 10 && gen.nextInt(20) == 5){
			Enemy1s.add(new Enemy1(gen.nextInt(WIDTH - 200) + 100, -50, (gen.nextInt(5) + 1)));
		}
		for(int i=0; i<Enemy1s.size(); i++){
			g.drawImage(enemy1img, ((Enemy1)Enemy1s.get(i)).getX(), ((Enemy1)Enemy1s.get(i)).getY(), this);
			((Enemy1)Enemy1s.get(i)).act();
			if(gen.nextInt(10) == 5)
				((Enemy1)Enemy1s.get(i)).shoot();
			if(!((Enemy1)Enemy1s.get(i)).isOnScreen())
				Enemy1s.remove(i);
		}
	}
		
	public void paintShip(Graphics2D g){
		
		if(player1.up || player1.down || player1.left || player1.right){
			player1.posX+=player1.vertX;
			player1.posY+=player1.vertY;
		}else{
			player1.vertX=0;
			player1.vertY=0;
		}
		if(player1.posX > WIDTH-60)
			player1.posX = WIDTH-60;
		if(player1.posY > HEIGHT-100)
			player1.posY = HEIGHT-100;
		if(player1.posY <= 0)
			player1.posY = 0;
		if(player1.posX <= -6)
			player1.posX = -6;
		if(player1.up)
			g.drawImage(shipUp, player1.posX, player1.posY, this);
		else if(player1.down)
			g.drawImage(shipDown, player1.posX, player1.posY, this);
		else if(player1.right)
			g.drawImage(shipRight, player1.posX, player1.posY, this);
		else if(player1.left)
			g.drawImage(shipLeft, player1.posX, player1.posY, this);
		else
			g.drawImage(ship, player1.posX, player1.posY, this);
	}
	
	public void paintMissles(Graphics2D g){
		
		if(player1.fire){
			//if(Missles.size() < 30)
				Missles.add(new Missle(player1.posX+30, player1.posY));
				playSound(missleFire);
				player1.fire=false;
		}
		if(Missles.size()>0){
			for(int i=0; i<Missles.size(); i++){
				((Missle)Missles.get(i)).act();
				//System.out.println(Missles.size());
				//System.out.println(((Missle)Missles.get(i)).getY());
			
				g.drawImage(missle, ((Missle)Missles.get(i)).getX(), ((Missle)Missles.get(i)).getY(), this);
				if(!((Missle)Missles.get(i)).isOnScreen()){
					//System.out.println("inside if");
					Missles.remove(i);
				}
				else if(((Missle)Missles.get(i)).checkCollisions(Enemy1s, (Missle)Missles.get(i))){
					//System.out.println("inside if");
					playSound(boom);
					g.drawImage(enemy1explodeimg, ((Missle)Missles.get(i)).getX() - 44, ((Missle)Missles.get(i)).getY() - 45, this);
					Missles.remove(i);
					score++;
				}
				if(Missles.size()==0){
					break;
				}
				/*if(((Missle)Missles.get(i)).checkCollisions(Enemy1s, (Missle)Missles.get(i))){
					Missles.remove(i);
				}*/
			}
		}
	}
	
	public void paintenemyMissles(Graphics2D g){
		
		for (int i = 0; i < Enemy1s.size(); i++) {
			if (((Enemy1)Enemy1s.get(i)).shoot()) {
				enemyMissles.add(new EnemyMissle(((Enemy1)Enemy1s.get(i)).getX()+15, ((Enemy1)Enemy1s.get(i)).getY()+28));
			}
		}
		
		if(enemyMissles.size()>0){
			for(int i=0; i<enemyMissles.size(); i++){
				((EnemyMissle)enemyMissles.get(i)).act();
				
				g.drawImage(eMissleimg, ((EnemyMissle)enemyMissles.get(i)).getX(), ((EnemyMissle)enemyMissles.get(i)).getY(), this);
				
				if(!((EnemyMissle)enemyMissles.get(i)).isOnScreen()){
					enemyMissles.remove(i);
				}
				else if(((EnemyMissle)enemyMissles.get(i)).checkCollisions(player1, (EnemyMissle)enemyMissles.get(i))){
					playSound(boom);
					g.drawImage(shieldimg, player1.posX - 10, player1.posY - 10, this);
					enemyMissles.remove(i);
					if (!god) {
						player1.shield--;
					}
					if (player1.shield <= 0) {
						gameover = true;
					}
				}
				if(enemyMissles.size()==0){
					break;
				}
			}
		}
		
	}
	
	public void paintFPS(Graphics2D g){
		g.setColor(Color.white);		
		g.drawString("FPS: "+String.valueOf(1000/(endTime + 1)), WIDTH-300, HEIGHT-50);
		g.drawString("SHIELD: "+player1.shield, WIDTH-200, HEIGHT-50);
		g.drawString("SCORE: "+(score*10), WIDTH-90, HEIGHT-50);
		if (god) {
			g.drawString("CHEATS ENABLED", 20, 50);
		}
	}
	
	public void playSound(final AudioClip name){
		new Thread(
			new Runnable(){
				public void run(){
					name.play();
				}
			}
		).start();
	}
		
	public void game(){
		endTime = 1000;
				
		while(isVisible()){
			startTime = System.currentTimeMillis();
			paintWorld();
			endTime = System.currentTimeMillis() - startTime;
			try{
				Thread.sleep(10);
			}catch(Exception e){
				System.err.print(e);
			}
		}
	}
	
	public void keyPressed(KeyEvent e){
		switch(e.getKeyCode()){
			case KeyEvent.VK_S: start = true; startplay = true; break;
			case KeyEvent.VK_G: god = true; break;
			case KeyEvent.VK_N: god = false; break;
		}

		player1.keyPressed(e);
	}
	
	public void keyReleased(KeyEvent e){
		player1.keyReleased(e);
	}
	
	public void keyTyped(KeyEvent e){
	}
	
	public static void main(String[] args){
		SpaceInvaders yes = new SpaceInvaders();
		yes.game();
	}
}