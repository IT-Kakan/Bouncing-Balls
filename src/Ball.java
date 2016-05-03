import java.awt.geom.Ellipse2D;

public class Ball {
	
	/* Gravitational constant */
	private static final double g = 9.82;
	
	private double x, y, vx, vy, r, m;
	private final double ax, ay;
	
	public Ball(double x, double y, double vx, double vy, double r, double m) {
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
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
		
		x += vx * deltaT;
		y += vy * deltaT;
		
		vy += ay * deltaT;
		
		//vx += ax * deltaT;
		//vy += ay * deltaT;
	}
	
	public Ellipse2D getBall() {
		return new Ellipse2D.Double(x - r, y - r, 2 * r, 2 * r);
	}
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public double getXVelocity() {
		return vx;
	}
	public double getYVelocity() {
		return vy;
	}
	public double getR() {
		return r;
	}
	public double getM() {
		return m;
	}
	public void reverseXVelocity() {
		vx *= -1;
	}
	public void reverseYVelocity() {
		vy *= -1;
	}
	
	
	
}
