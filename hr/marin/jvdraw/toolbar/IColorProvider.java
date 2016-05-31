package hr.marin.jvdraw.toolbar;

import java.awt.Color;

/**
 * <p>
 * An interface that specifies the methods that a class has to implement in
 * order to be a color provider. It contains only one method for providing the
 * current color of the implementing object.
 * </p>
 * 
 * @author Marin
 *
 */
public interface IColorProvider {
	/**
	 * Method obtains the current color of this color provider object.
	 * 
	 * @return The current color of this color provider.
	 */
	public Color getCurrentColor();
}
