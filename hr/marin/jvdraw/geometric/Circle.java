package hr.marin.jvdraw.geometric;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * <p>
 * An implementation of {@link GeometricalObject} that represents a circle
 * shape.<br>
 * A circle consists of a center point, a radius, a outline color and,
 * optionally, a fill color.
 * </p>
 * <p>
 * If the user provides the fill color in the constructor, the circle is
 * regarded as filled. Otherwise, it is regarded as not filled.
 * </p>
 * 
 * @author Marin
 *
 */
public class Circle extends GeometricalObject {
	/**
	 * The center point of the circle
	 */
	private Point center;
	/**
	 * The radius of the circle
	 */
	private int radius;

	/**
	 * Creates a new circle with the given center point, radius and outline and
	 * fill colors. The fill color can be null (the circle will not be filled).
	 * 
	 * @param center
	 *            The center point of the circle
	 * @param radius
	 *            The radius of the circle
	 * @param outline
	 *            The outline color that this circle will be drawn in
	 * @param fill
	 *            The fill color that this circle will be drawn in. If null, the
	 *            circle is regarded as not filled.
	 * @throws IllegalArgumentException If the given radius is negative
	 */
	public Circle(Point center, int radius, Color outline, Color fill) {
		super(outline, fill);
		if(radius < 0) {
			throw new IllegalArgumentException("Radius must be nonnegative.");
		}
		this.radius = radius;
		this.center = center;
	}

	@Override
	public Rectangle getBoundingRect() {
		return new Rectangle(center.x - radius, center.y - radius, 2*radius, 2*radius);
	}

	/**
	 * Method checks whether this circle is filled or empty. If a fill color was
	 * provided through the constructor of this object, the circle will be
	 * filled. If null was provided instead of a color, the circle will be
	 * empty.
	 * 
	 * @return True if the circle is filled, false otherwise.
	 */
	public boolean isFilled() {
		return (getBackgroundColor() != null);
	}

	/**
	 * Gets the center point of the circle.
	 * @return The center point of the circle.
	 */
	public Point getCenter() {
		return center;
	}

	/**
	 * Sets the center point of the circle.
	 * @param center The new center point of the circle
	 */
	public void setCenter(Point center) {
		this.center = center;
		fireListeners();
	}
	
	/**
	 * Gets the radius of the circle.
	 * @return The radius of the circle.
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * Sets the radius of the circle.
	 * @param radius The new radius of the circle
	 * @throws IllegalArgumentException If the given radius is negative
	 */
	public void setRadius(int radius) {
		if(radius < 0) {
			throw new IllegalArgumentException("Radius must be nonnegative.");
		}
		this.radius = radius;
		fireListeners();
	}
	
	@Override
	public void paint(Graphics2D g) {
		Graphics2D temp = (Graphics2D) g.create();
		if(isFilled()) {
			temp.setColor(getBackgroundColor());
			temp.fillOval(center.x - radius, center.y - radius, 2*radius, 2*radius);
		}

		temp.setColor(getForegroundColor());
		temp.drawOval(center.x - radius, center.y - radius, 2*radius, 2*radius);
	
		temp.dispose();
	}
	
	@Override
	public String toString() {
		return "Circle";
	}
}
