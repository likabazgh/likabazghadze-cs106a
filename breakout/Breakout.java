
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;

import acm.graphics.GLabel;
import acm.graphics.GLine;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.ConsoleProgram;
import acm.program.GraphicsProgram;
import acm.util.MediaTools;
import acm.util.RandomGenerator;

public class Breakout extends GraphicsProgram {
	
	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

/** Separation between bricks */
	private static final int BRICK_SEP = 4;

/** Width of a brick */
	private static final int BRICK_WIDTH=
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

/** Number of turns */
	AudioClip BounceClip=MediaTools.loadAudioClip("bounce.au");
	
	private GRect paddle;
	private GOval ball;

	double vx;
    double vy;
	
	RandomGenerator rgen= RandomGenerator.getInstance();
	
	int totalBricks = NBRICKS_PER_ROW*NBRICK_ROWS;
	
	int life=3;
	
	public void run() {
		while(true){
		setBackground(Color.lightGray);
		addMouseListeners();
		drawBricks();
		drawPaddle();
		drawBall();
		ballMovement();
		waitForClick(); //if we want this game to continue after every "death" run method must be in while circle and after ending each of them we should refill lives.
		removeAll();
		life=3;
		}
	}
	//first we have to draw the bricks and make them colored with given colors for each column.
	private void drawBricks(){
		for(int i=1; i<=NBRICKS_PER_ROW; i++){
			for (int j=1; j<=NBRICK_ROWS; j++){
				int x=(WIDTH-NBRICKS_PER_ROW*BRICK_WIDTH-(NBRICKS_PER_ROW-1)*BRICK_SEP)/2+(i-1)*(BRICK_WIDTH+BRICK_SEP);
				int y=BRICK_Y_OFFSET+(j-1)*(BRICK_HEIGHT+BRICK_SEP);
				
		        GRect rect= new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);
				add(rect);
				
				if( j==1 || j==2){
					rect.setFilled(true);
					rect.setColor(Color.RED);
				}
				
				if( j==3 || j==4){
					rect.setFilled(true);
					rect.setColor(Color.ORANGE);
				}
				
				if( j==5 || j==6){
					rect.setFilled(true);
					rect.setColor(Color.YELLOW);
				}
				
				if( j==7 || j==8){
					rect.setFilled(true);
					rect.setColor(Color.GREEN);
				}
				
				if( j==9 || j==10){
					rect.setFilled(true);
					rect.setColor(Color.CYAN);
				}
			}
		}
	}
	// this method draws paddle on its coordinates
	private void drawPaddle(){
		paddle=new GRect((WIDTH-PADDLE_WIDTH)/2, HEIGHT-PADDLE_Y_OFFSET-PADDLE_HEIGHT, PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		paddle.setColor(Color.DARK_GRAY);
		add(paddle);
	}
	//this draws the ball in the centre of canvas
	private void drawBall(){
	    ball=new GOval(WIDTH/2-BALL_RADIUS, HEIGHT/2-BALL_RADIUS, 2*BALL_RADIUS, 2*BALL_RADIUS); 
		ball.setFilled(true);
		ball.setColor(Color.darkGray);
		add(ball);
	}
	// mouseMoved method for paddle to move across the canvas
	public void mouseMoved(MouseEvent e){
		double x= e.getX()-PADDLE_WIDTH;
		double y= HEIGHT-PADDLE_HEIGHT-PADDLE_Y_OFFSET;
	
	
	if(x>0){
		paddle.setLocation(x, y);
	}
}
	//in this method we have ball's velocities across X and Y
	private void ballMovement(){
		waitForClick();
		
		vx=rgen.nextDouble(1.0, 3.0);
		if(rgen.nextBoolean(0.5)) vx=-vx;
		vy=3.0;
		
		while(true){
			ball.move(vx, vy);
			pause(10);
			
			if(ballCrushesTopHorisotalLine()){ // when ball touches canvas's ceiling Y coordinate changes
				vy=-vy;
			}
			
			if(ballCrushesVerticalLine()){ //when ball touches the wall X coordinate changes
				vx=-vx;
			}
			
			GObject collider= getCollidingObject();

			//if ball crushes the paddle it should change Y directions without removing paddle
			if(collider==paddle){
				vy=-vy;
			}

			//but it should remove "collider" when it touches bricks and then change direction
			if(collider!=null&&collider!=paddle){
				remove(collider);
				vy=-vy;
				totalBricks--;
			}

			//after all the bricks are removed,game is over and the player wins
			if(noBricksPresent()){
				removeAll();
				GLabel label1=new GLabel("YOU WIN");
				add(label1, getWidth()/2-label1.getWidth()/2, getHeight()/2-label1.getHeight()/2);
			}
			

			//if the ball gets under the bricks it means it will touch the floor and you will have -1 life. game continues unless number of lives are more then 0.
			if(ball.getY()>=getHeight()-2*BALL_RADIUS){
				remove(ball);
				life--;
				if(life>0){
				GLabel label2=new GLabel("YOU ARE LOOSER. you have "+ life +" tries left");
				add(label2, getWidth()/2-label2.getWidth()/2, getHeight()/2-label2.getHeight()/2);
				waitForClick();
				remove(label2);
				drawBall();
				ballMovement();
				}
			}
			//when life reaches 0 you loose the game.
			if(life==0){
				removeAll();
				BounceClip.play();
				GLabel label3=new GLabel("END GAME");
				add(label3, getWidth()/2-label3.getWidth()/2, getHeight()/2-label3.getHeight()/2);
				break;
			}
			
			//for paddle to not stick the ball,we should change ball's location when this situation occures
			if(ballIsInPaddle1()){
				ball.setLocation(ball.getX(), HEIGHT-PADDLE_Y_OFFSET-PADDLE_HEIGHT-2*BALL_RADIUS);
			}
			if(ballIsInPaddle2()){
				ball.setLocation(ball.getX(), HEIGHT-PADDLE_Y_OFFSET);
			}
		}
	}
	private boolean ballCrushesTopHorisotalLine(){
		return ball.getY()<0;
	}
	private boolean ballCrushesVerticalLine(){
		return ball.getX() >= getWidth()-2*BALL_RADIUS || ball.getX()<0;
	}
	private boolean noBricksPresent(){
		return totalBricks==0;
	}
	private boolean ballIsInPaddle1(){
		return ball.getX()>=paddle.getX() &&  ball.getX()<=paddle.getX()+PADDLE_WIDTH && ball.getY()>=HEIGHT-PADDLE_Y_OFFSET-PADDLE_HEIGHT-2*BALL_RADIUS && ball.getY()<=HEIGHT-PADDLE_Y_OFFSET-PADDLE_HEIGHT/2;
	}
	private boolean ballIsInPaddle2(){
		return ball.getX()>=paddle.getX() &&  ball.getX()<=paddle.getX()+PADDLE_WIDTH && ball.getY()>=HEIGHT-PADDLE_Y_OFFSET-PADDLE_HEIGHT/2 && ball.getY()<=HEIGHT-PADDLE_Y_OFFSET;
	}
	
	//method for inventing colliding objects and then colliders for ball to know which objects it should remove remove
	private GObject getCollidingObject(){
		GObject collider1= getElementAt(ball.getX(), ball.getY());
		GObject collider2= getElementAt(ball.getX()+2*BALL_RADIUS, ball.getY());
		GObject collider3= getElementAt(ball.getX(), ball.getY()+2*BALL_RADIUS);
		GObject collider4= getElementAt(ball.getX()+2*BALL_RADIUS, ball.getY()+2*BALL_RADIUS);
		GObject collider5= getElementAt(ball.getX()+BALL_RADIUS,ball.getY());
		GObject collider6= getElementAt(ball.getX(), ball.getY()+BALL_RADIUS);
		GObject collider7= getElementAt(ball.getX()+2*BALL_RADIUS, ball.getY()+BALL_RADIUS);

		
		if(collider1 != null){
			return collider1;
		}
		if(collider2 != null){
			return collider2;
		}
		if(collider3 != null){
			return collider3;
		}
		if(collider4 != null){
			return collider4;
		}
		if(collider5 != null && collider5 != ball){
			return collider5;
		}
		if(collider6 != null && collider6 != ball){
			return collider6;
		}
		if(collider7 != null && collider7 !=ball){
			return collider7;
		}
		else{
			return null;
		}
		
}
}