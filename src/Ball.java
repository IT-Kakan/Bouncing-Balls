import java.awt.geom.Ellipse2D;

public class Ball {
	
	/* Gravitational constant */
	private static final double g = 9.82;
	
	private double r, m;
	private RectVector coordinates, velocity;
	private final double ax, ay;
	
	public Ball(double x, double y, double vx, double vy, double r, double m) {
		coordinates = new RectVector(x, y);
		velocity = new RectVector(vx, vy);
		//this.x = x;
		//this.y = y;
		//this.vx = vx;
		//this.vy = vy;
		this.r = r;
		this.m = m;
		
		this.ax = 0; //No acceleration along the x axis, only gravity
		this.ay = -g; /* This is constant and never changes. The acceleration is never
		changed as there is never any new energy added */
	}
	
	public void tick(double deltaT) {
		//y' = h(y,t)
		//y(t+deltaT) = y(t) + y'(t) * deltaT
		
		//F = m y''
		
		coordinates.setX(getX() + getXVelocity() * deltaT);
		coordinates.setY(getY() + getYVelocity() * deltaT);
		//x += vx * deltaT;
		//y += vy * deltaT;
		
		//velocity.setY(getYVelocity() + ay * deltaT);
		
		//vx += ax * deltaT;
		//vy += ay * deltaT;
	}
	
	public Ellipse2D getBall() {
		return new Ellipse2D.Double(getX() - r, getY() - r, 2 * r, 2 * r);
	}
	
	public double getX() {
		return coordinates.getX();
	}
	public double getY() {
		return coordinates.getY();
	}
	public RectVector getCoordinates() {
		return coordinates;
	}
	public RectVector getVelocity() {
		return velocity;
	}
	public double getXVelocity() {
		return velocity.getX();
	}
	public double getYVelocity() {
		return velocity.getY();
	}
	public double getR() {
		return r;
	}
	public double getM() {
		return m;
	}
	public void reverseXVelocity() {
		velocity.setX(velocity.getX() * -1);
	}
	public void reverseYVelocity() {
		velocity.setY(velocity.getY() * -1);
	}
	public void setCoordinates(RectVector coords) {
		coordinates = coords;
	}
	
	
	
}
