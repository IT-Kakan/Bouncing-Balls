import java.awt.geom.Ellipse2D;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class DummyModel implements IBouncingBallsModel {

	private final double areaWidth;
	private final double areaHeight;

	//private double x, y, vx, vy, r;
	private List<Ball> balls;

	public DummyModel(double width, double height) {
		this.areaWidth = width;
		this.areaHeight = height;
		
		balls = new LinkedList<>();
		
		double x = 2;
		double y = 2;
		double vx = 2.3;
		double vy = 1;
		double r = 1;
		double m = 1;
	
		addBall(x, y, 0, 0, r, m); //Test gravity
		addBall(x, y+3, 0, 0, r, m); //Test collision
		
//		addBall(x, y, vx, 0, r);
//		addBall(x, y, vx, 0, r);
		//addBall(x+2, y+2, vx-1, vy+1, r+0.2);
	}

	@Override
	public void tick(double deltaT) {	
		for (Ball b : balls) {
			if (ballHitsWall(b)) {
				b.reverseXVelocity();
			}
			if (ballHitsFloorOrCeiling(b)) {
				b.reverseYVelocity();
			}
			
			for (Ball b2 : balls) {
				if (b != b2 && ballHitsBall(b, b2)) {
					//System.exit(0);
					//collideBalls(b, b2);
					
					//Testa lodrät kollision
					
					b.reverseYVelocity();
					b2.reverseYVelocity();		
				}
			}
			b.tick(deltaT);
		}
		
		/* A collision between the balls can be calculated
		 * in the following way. As with collision with a wall
		 * the velocity tangential to the surface of the ball
		 * is not affected.
		 * 
		 * The velocity along the line between the centre of
		 * the ball is changed under conservation of the total
		 * kinetic energy mv^2/2 and momentum mv of the two
		 * balls.
		 * 
		 * Begin by trying with the simple case of two equal balls.
		 * (Note that to do this calculation you must make a change
		 * of coordinates. An easy way to do this is to implement
		 * the subroutines rectToPolar and polarToRect and debug
		 * these before you use them!)
		 */
	}

	public void addBall(double x, double y, double vx, double vy, double r, double m) {
		balls.add(new Ball(x, y, vx, vy, r, m));
	}
	
	/*
	 * Determines whether or not the ball will hit a wall this tick. (needs to be more exact?)
	 */
	public boolean ballHitsWall(Ball ball) {
		double ballRadius = ball.getR();
		double xPos = ball.getX();
		return xPos < ballRadius || xPos > areaWidth - ballRadius;
	}
	
	/*
	 * Determines whether or not the ball will hit the floor or the ceiling this tick. (needs to be more exact?)
	 */
	public boolean ballHitsFloorOrCeiling(Ball ball) {
		double ballRadius = ball.getR();
		double yPos = ball.getY();
		return yPos < ballRadius || yPos > areaHeight - ballRadius;
		
	}
	
	/*
	 * Calculates whether or not the two balls have hit each other.
	 */
	public boolean ballHitsBall(Ball first, Ball second) {
		double xDiff = Math.abs(first.getX() - second.getX());
		double yDiff = Math.abs(first.getY() - second.getY());
		double distanceBetweenBalls = Math.sqrt(xDiff * xDiff + yDiff * yDiff); //Pythagorean theorem
		return distanceBetweenBalls <= first.getR() + second.getR();
	}
	
	public void collideBalls(Ball first, Ball second) {
		double xDiff = first.getX() - second.getX();
		double yDiff = first.getY() - second.getY();
		double angleMagnitude = Math.sqrt(xDiff * xDiff + yDiff * yDiff); //Pythagorean theorem
		double angle = Math.atan2(yDiff, xDiff);
		//TODO: finish
		//TODO: make vectors and use polar coordinates
		
		
	}
	
	public PolarVector rectToPolar(RectVector c) {
		double theta = Math.atan2(c.getY(), c.getX());
		double r = c.getX() / Math.cos(theta);
		
		return new PolarVector(r, theta);
	}
	
	public RectVector polarToRect(PolarVector c) {
		double r = c.getR();
		double theta = c.getTheta();
		
		double x = r * Math.cos(theta);
		double y = r * Math.sin(theta);
		
		return new RectVector(x, y);
	}
	
	@Override
	public List<Ellipse2D> getBalls() {
		List<Ellipse2D> myBalls = new LinkedList<Ellipse2D>();
		
		for (Ball b : balls) {
			myBalls.add(b.getBall());
		}
		
		return myBalls;
	}
}
