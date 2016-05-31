package hr.marin.jvdraw;

import hr.marin.jvdraw.geometric.Circle;
import hr.marin.jvdraw.geometric.GeometricalObject;
import hr.marin.jvdraw.geometric.Line;
import hr.marin.jvdraw.model.DrawingModel;
import hr.marin.jvdraw.toolbar.IColorProvider;
import hr.marin.jvdraw.toolbar.IShapeProvider;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * A mouse listener implementation that is used to enable the user to draw shapes by using a mouse.
 * @author Marin
 *
 */
public class MouseCreator extends MouseAdapter {
	/**
	 * The drawing model to which the geometric shapes that are to be drawn are added
	 */
	private DrawingModel model;
	/**
	 * The provider for the foreground color
	 */
	private IColorProvider foreground;
	/**
	 * The provider for the background color
	 */
	private IColorProvider background;
	/**
	 * The currently selected geometric shape
	 */
	private IShapeProvider shape;
	/**
	 * True if there has been an odd number of clicks, false if there has been an even number of clicks
	 */
	private boolean clicked;
	/**
	 * The geometric object that is being drawn currently
	 */
	private GeometricalObject current;
	
	/**
	 * Creates a new {@link MouseCreator} object with the given arguments.
	 * @param model The drawing model to which the geometric shapes that are to be drawn are added
	 * @param foreground The provider for the foreground color
	 * @param background The provider for the background color
	 * @param shape The currently selected geometric shape
	 */
	public MouseCreator(DrawingModel model, IColorProvider foreground, IColorProvider background, IShapeProvider shape) {
		this.model = model;
		this.foreground = foreground;
		this.background = background;
		this.shape = shape;
		clicked = false;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Point p = e.getPoint();
		if (!clicked) {
			switch (shape.getCurrentShape()) {
			case LINE:
				current = new Line(p, p, foreground.getCurrentColor());
				break;
			case CIRCLE_EMPTY:
				current = new Circle(p, 0, foreground.getCurrentColor(), null);
				break;
			case CIRCLE_FILLED:
				current = new Circle(p, 0, foreground.getCurrentColor(), background.getCurrentColor());
				break;
			}
			model.add(current);
		}
		
		clicked = !clicked;
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		if(!clicked) {
			return;
		}
		
		Point p = e.getPoint();
		if(current instanceof Line){
			((Line) current).setEnd(p);
		} else if(current instanceof Circle) {
			Circle c = (Circle) current;
			int newX = p.x - c.getCenter().x;
			int newY = p.y - c.getCenter().y;
			int r = (int) Math.sqrt(newX*newX + newY*newY);
			c.setRadius(r);
		}
	}
}
