package hr.marin.jvdraw.geometric;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * <p>
 * An implementation of {@link GeometricalObject} that represents a line shape.<br>
 * A line consists of a starting and an ending point and a foreground color.
 * </p>
 * 
 * @author Marin
 *
 */
public class Line extends GeometricalObject {
	/**
	 * The starting point of the line
	 */
	private Point start;
	/**
	 * The ending point of the line
	 */
	private Point end;

	/**
	 * Creates a new {@link Line} object with the given start and end points and
	 * the given color.
	 * 
	 * @param start
	 *            The starting point of the line
	 * @param end
	 *            The ending point of the line
	 * @param color
	 *            The color that this line will be drawn in.
	 */
	public Line(Point start, Point end, Color color) {
		super(color, null);
		this.start = start;
		this.end = end;
	}

	@Override
	public Rectangle getBoundingRect() {
		int x = Math.min(start.x, end.x);
		int y = Math.min(start.y, end.y);
		int w = Math.max(start.x, end.x) - x;
		int h = Math.max(start.y, end.y) - y;

		return new Rectangle(x, y, w, h);
	}

	/**
	 * Gets the starting point of the line.
	 * 
	 * @return The starting point of the line
	 */
	public Point getStart() {
		return start;
	}

	/**
	 * Sets the starting point of the line.
	 * 
	 * @param start
	 *            The starting point.
	 */
	public void setStart(Point start) {
		this.start = start;
		fireListeners();
	}

	/**
	 * Gets the ending point of the line.
	 * 
	 * @return The ending point of the line
	 */
	public Point getEnd() {
		return end;
	}

	/**
	 * Sets the ending point of the line.
	 * 
	 * @param end
	 *            The ending point.
	 */
	public void setEnd(Point end) {
		this.end = end;
		fireListeners();
	}

	@Override
	public void paint(Graphics2D g) {
		Graphics2D temp = (Graphics2D) g.create();
		temp.setColor(getForegroundColor());
		temp.drawLine(start.x, start.y, end.x, end.y);
		temp.dispose();
	}
	

	@Override
	public String toString() {
		return "Line";
	}
}
