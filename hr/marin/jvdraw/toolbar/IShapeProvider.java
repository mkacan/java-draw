package hr.marin.jvdraw.toolbar;

/**
 * <p>
 * An interface that specifies the methods that a class has to implement in
 * order to be a shape provider. It contains only one method for providing the
 * current shape of the implementing object.
 * </p>
 * 
 * @author Marin
 *
 */
public interface IShapeProvider {
	/**
	 * Method obtains the current shape of this shape provider object.
	 * 
	 * @return The current shape of this shape provider.
	 */
	public Shape getCurrentShape();
}
