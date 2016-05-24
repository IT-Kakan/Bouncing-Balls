import java.awt.geom.Ellipse2D;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class DummyModel implements IBouncingBallsModel {

	private final double areaWidth;
	private final double areaHeight;

	private List<Ball> balls;

	public DummyModel(double width, double height) {
		this.areaWidth = width;
		this.areaHeight = height;
		
		balls = new LinkedList<>();
		
		double x = 2;
		double y = 2;
		double vx = 2.3;
		double vy = 1;
		double r = 0.5;
		double m = 1;
	
		addBall(x, y, 0.5, 0, r, m);
		addBall(x, y+3, 0, 0, r+0.5, m*2);
		//addBall(x+1, y, 0, 0.2, r+0.2, m+0.5);
	}

	@Override
	public void tick(double deltaT) {
		
		for (int i = 0; i < balls.size(); i++) {
			Ball b = balls.get(i);
			for (int j = 0; j < balls.size(); j++) {
				Ball b2 = balls.get(j);
				if (b != b2) {
					collision(b, b2);
				}
			}
			
			if (ballHitsWall(b)) {
				b.reverseXVelocity();
			}
			if (ballHitsFloorOrCeiling(b)) {
				b.reverseYVelocity();
			}
			
			b.tick(deltaT);
		}
	}

	public void addBall(double x, double y, double vx, double vy, double r, double m) {
		balls.add(new Ball(x, y, vx, vy, r, m));
	}
	
	/*
	 * Determines whether or not the ball will hit a wall this tick.
	 */
	public boolean ballHitsWall(Ball ball) {
		double ballRadius = ball.getR();
		double xPos = ball.getX();
		
		double collisionLeft = 0 + ballRadius;
		double collisionRight = areaWidth - ballRadius;
		
		if ((ball.getXVelocity() <= 0) && (xPos <= collisionLeft)) {
			return true;
		} else if ((ball.getXVelocity() >= 0) && (xPos >= collisionRight)) {
			return true;
		} else {
			return false;
		}
	}
	
	/*
	 * Determines whether or not the ball will hit the floor or the ceiling this tick.
	 */
	public boolean ballHitsFloorOrCeiling(Ball ball) {
		double ballRadius = ball.getR();
		double yPos = ball.getY();
		
		double collisionHigh = areaHeight - ballRadius;
		double collisionLow = 0 + ballRadius;
		
		if ((ball.getYVelocity() >= 0) && (yPos >= collisionHigh)) {
			return true;
		} else if ((ball.getYVelocity() <= 0) && (yPos <= collisionLow)) {
			return true;
		} else {
			return false;
		}
	}
	
	public PolarVector rectToPolar(RectVector c) {
		double r = Math.sqrt(c.getX()*c.getX() + c.getY()*c.getY());
		
		double theta = Math.atan2(c.getY(), c.getX());
		if (c.getX() < 0) {
			theta += Math.PI;
		}
		
		return new PolarVector(r, theta);
	}
	
	public RectVector polarToRect(PolarVector c) {
		double r = c.getR();
		double theta = c.getTheta();
		
		double x = r * Math.cos(theta);
		double y = r * Math.sin(theta);
		
		return new RectVector(x, y);
	}

	public void rotate(RectVector r, double rotateAngle) {
		PolarVector p = rectToPolar(r);
		p.setTheta(p.getTheta() + rotateAngle);
		
		RectVector newR = polarToRect(p);
		r.setX(newR.getX());
		r.setY(newR.getY());
	}

	public void	collision(Ball b1, Ball b2) {
		double deltaX = b1.getX() - b2.getX();
		double deltaY = b1.getY() - b2.getY();

		double collisionDistance = b1.getR() + b2.getR(); 
		
		if (deltaX*deltaX + deltaY*deltaY < collisionDistance*collisionDistance) {
			double rotateAngle = Math.atan2(deltaY, deltaX);

			rotate(b1.getVelocity(), -rotateAngle);
			rotate(b2.getVelocity(), -rotateAngle);
			
			double I = b1.getM() * b1.getXVelocity() + b2.getM() * b2.getXVelocity();
			double R = -(b2.getXVelocity() - b1.getXVelocity());
			
			b1.getVelocity().setX((I-R*b2.getM()) / (b1.getM() + b2.getM()));
			b2.getVelocity().setX(R + b1.getXVelocity());
			
			rotate(b1.getVelocity(), rotateAngle);
			rotate(b2.getVelocity(), rotateAngle);
		}
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
