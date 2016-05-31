package hr.marin.jvdraw.geometric;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * The class represents a geometric object that can be drawn. The user can
 * obtain information about the bounding rectangle of the object, as well as the
 * foreground and background colors that the object must be drawn with.<br>
 * 
 * </p>
 * 
 * @author Marin
 *
 */
public abstract class GeometricalObject {
	/**
	 * The foreground color that this geometric object must be drawn with
	 */
	private Color foregroundColor;
	/**
	 * The background color that this geometric object must be drawn with
	 */
	private Color backgroundColor;
	
	/**
	 * All the listeners that get notified whenever this object is changed.
	 */
	private List<GeometricListener> listeners;

	/**
	 * Method obtains the rectangle that bounds this object. The bounding
	 * rectangle is the smallest rectangle that can cover the whole object.
	 * 
	 * @return The bounding rectangle of this geometric object
	 * @see Rectangle
	 */
	public abstract Rectangle getBoundingRect();
	
	/**
	 * Method paints this {@link GeometricalObject} using the given {@link Graphics2D} object.
	 * @param g The object that is used to paint this {@link GeometricalObject}.
	 */
	public abstract void paint(Graphics2D g);

	/**
	 * Creates a new {@link GeometricalObject} with the given foreground and background colors.
	 * @param foreground The foreground color that this geometric object must be drawn with
	 * @param background The background color that this geometric object must be drawn with
	 */
	public GeometricalObject(Color foreground, Color background) {
		foregroundColor = foreground;
		backgroundColor = background;
	}

	/**
	 * Method notifies all the registered listeners that this object has been changed.
	 */
	protected void fireListeners() {
		if (listeners == null) {
			return;
		}

		for (GeometricListener listener : listeners) {
			listener.objectChanged(this);
		}
	}
	
	/**
	 * Adds a new listener to this geometric object. The added listener
	 * will be notified every time this object is changed.
	 * 
	 * @param l
	 *            The listener that is to be registered to this component. If
	 *            null was given, the method does nothing.
	 */
	public void addGeometricListener(GeometricListener l) {
		if (l == null) {
			return;
		}

		if (listeners == null) {
			listeners = new LinkedList<>();
		}

		listeners.add(l);
	}

	/**
	 * Removes an already registered listener from this geometric object.
	 * If the given argument was null or a listener that is not registered to
	 * this component, the method does nothing.
	 * 
	 * @param l
	 *            The listener that is to be deregistered from this component.
	 */
	public void removeGeometricListener(GeometricListener l) {
		if (l == null) {
			return;
		}

		Iterator<GeometricListener> it = listeners.iterator();
		while (it.hasNext()) {
			GeometricListener listener = it.next();
			if (listener.equals(l)) {
				it.remove();
				return;
			}
		}
	}
	
	/**
	 * Method obtains the foreground color that this geometric object must be drawn with.
	 * @return The foreground color that this geometric object must be drawn with
	 */
	public Color getForegroundColor() {
		return foregroundColor;
	}

	/**
	 * Method obtains the background color that this geometric object must be drawn with.
	 * @return The background color that this geometric object must be drawn with
	 */
	public Color getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * Sets the foreground color for this {@link GeometricalObject}.
	 * @param foregroundColor The new foreground color.
	 */
	public void setForegroundColor(Color foregroundColor) {
		this.foregroundColor = foregroundColor;
		fireListeners();
	}

	/**
	 * Sets the background color for this {@link GeometricalObject}.
	 * @param backgroundColor The new background color.
	 */	
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
		fireListeners();
	}
}
