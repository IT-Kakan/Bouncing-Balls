import java.awt.geom.Ellipse2D;
import java.util.LinkedList;
import java.util.List;

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
		
		addBall(x, y, vx, 0, r);
		addBall(x, y, vx, 0, r);
		//addBall(x+2, y+2, vx-1, vy+1, r+0.2);
	}

	@Override
	public void tick(double deltaT) {		
		for (Ball b : balls) {			
			double x = b.getX();
			double y = b.getY();
			double r = b.getR();
			
			if (x < r || x > areaWidth - r) {
				b.reverseXVelocity();
			}
			if (y < r || y > areaHeight - r) {
				b.reverseYVelocity();
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

	public void addBall(double x, double y, double vx, double vy, double r) {
		balls.add(new Ball(x, y, vx, vy, r));
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
