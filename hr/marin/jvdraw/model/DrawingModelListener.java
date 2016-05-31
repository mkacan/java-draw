package hr.marin.jvdraw.model;

import hr.marin.jvdraw.geometric.GeometricalObject;

/**
 * <p>
 * An interface that specifies the methods that a class must implement in order
 * to be a drawing model listener.<br>
 * Such a listener can register itself to a drawing model and get notified each
 * time that model is changed.
 * </p>
 * 
 * @author Marin
 *
 */
public interface DrawingModelListener {
	/**
	 * Called if {@link GeometricalObject}s were added to the listened model.
	 * 
	 * @param source
	 *            The listened model.
	 * @param index0
	 *            The starting index of the addition interval.
	 * @param index1
	 *            The ending index of the addition interval.
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);

	/**
	 * Called if {@link GeometricalObject}s were removed from the listened
	 * model.
	 * 
	 * @param source
	 *            The listened model.
	 * @param index0
	 *            The starting index of the removal interval.
	 * @param index1
	 *            The ending index of the removal interval.
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);

	/**
	 * Called if existing {@link GeometricalObject}s in the model were changed.
	 * 
	 * @param source
	 *            The listened model.
	 * @param index0
	 *            The starting index of the change interval.
	 * @param index1
	 *            The ending index of the change interval.
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);
}
