package hr.marin.jvdraw.toolbar;

import java.awt.Color;

/**
 * <p>
 * An interface that specifies the methods that a class has to implement in
 * order to be a color change listener. It offers a single method for a listened
 * object to notify the listener that a change of color has happened.
 * </p>
 * 
 * @author Marin
 *
 */
public interface ColorChangeListener {
	/**
	 * Method used by listened objects to notify their listeners that a change
	 * of color has happened and provides them with the information relevant for
	 * that event.
	 * 
	 * @param source
	 *            The object that provides the current color.
	 * @param oldColor
	 *            The color of the listened object before the change.
	 * @param newColor
	 *            The color of the listened object after the change.
	 */
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}
