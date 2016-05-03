import java.awt.geom.Ellipse2D;

public class Ball {
	
	/* Gravitational constant */
	private static double g = 9.82;
	
	private double x, y, vx, vy, ax, ay, r, m;
	
	public Ball(double x, double y, double vx, double vy, double r) {
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		this.r = r;
		m = 1;
		
		this.ax = 0;
		this.ay = 0;//-1 * g;
	}
	
	public void tick(double deltaT) {
		//y' = h(y,t)
		//y(t+deltaT) = y(t) + y'(t) * deltaT
		
		//F = m y''
		
		x += vx * deltaT;
		y += vy * deltaT;
		
		vx += ax * deltaT;
		vy += ay * deltaT;
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
	public void reverseXVelocity() {
		vx *= -1;
	}
	public void reverseYVelocity() {
		vy *= -1;
	}
	
	
	
}
