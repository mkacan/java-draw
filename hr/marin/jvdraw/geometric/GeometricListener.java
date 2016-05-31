package hr.marin.jvdraw.geometric;

/**
 * <p>
 * An interface that specifies the methods that a class needs to implement in
 * order to be a {@link GeometricListener}.<br>
 * A geometric listener can register itself onto a {@link GeometricalObject} and
 * then it will get notified every time that object changes via the method
 * objectChanged.
 * </p>
 * 
 * @author Marin
 *
 */
public interface GeometricListener {
	/**
	 * The method that specifies how this listener object is to react when the
	 * listened {@link GeometricalObject} changes.
	 * 
	 * @param source
	 *            The listened {@link GeometricalObject} that just changed.
	 */
	void objectChanged(GeometricalObject source);
}
