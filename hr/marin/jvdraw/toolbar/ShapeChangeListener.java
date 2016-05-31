package hr.marin.jvdraw.toolbar;

/**
 * <p>
 * An interface that specifies the methods that a class has to implement in
 * order to be a shape change listener. It offers a single method for a listened
 * object to notify the listener that a change of shape has happened.
 * </p>
 * 
 * @author Marin
 *
 */
public interface ShapeChangeListener {
	/**
	 * Method used by listened objects to notify their listeners that a change
	 * of shape has happened and provides them with the information relevant for
	 * that event.
	 * 
	 * @param source
	 *            The object that provides the current shape.
	 * @param oldShape
	 *            The shape of the listened object before the change.
	 * @param newShape
	 *            The shape of the listened object after the change.
	 */
	public void shapeChanged(IShapeProvider source, Shape oldShape, Shape newShape);
}
