package hr.marin.jvdraw.model;

import hr.marin.jvdraw.JVDraw;
import hr.marin.jvdraw.geometric.GeometricalObject;

/**
 * <p>
 * An interface that specifies the methods that a class must implement in order
 * to be a drawing model, used by various components in the {@link JVDraw}
 * program (canvas, list, etc.).
 * </p>
 * 
 * @author Marin
 *
 */
public interface DrawingModel {
	/**
	 * Gets the number of {@link GeometricalObject}s stored in this model.
	 * 
	 * @return The number of {@link GeometricalObject}s stored in this model
	 */
	public int getSize();

	/**
	 * Gets the {@link GeometricalObject} at the given index.
	 * 
	 * @param index
	 *            The index of the returned geometric object
	 * @return The geometric object at the given index
	 */
	public GeometricalObject getObject(int index);

	/**
	 * Adds a {@link GeometricalObject} to the model.
	 * 
	 * @param object
	 *            The geometric object that is to be added to the model
	 */
	public void add(GeometricalObject object);

	/**
	 * Adds a {@link DrawingModelListener} to this model. The added listener
	 * will be notified whenever this model is changed.
	 * 
	 * @param l
	 *            The listener that will get registered to this model.
	 */
	public void addDrawingModelListener(DrawingModelListener l);

	/**
	 * Removes the given {@link DrawingModelListener} from this model. If there
	 * is no such listener in the model, the method does nothing.
	 * 
	 * @param l
	 *            The listener that will get deregistered from this model.
	 */
	public void removeDrawingModelListener(DrawingModelListener l);
}
