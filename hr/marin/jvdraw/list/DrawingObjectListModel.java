package hr.marin.jvdraw.list;

import hr.marin.jvdraw.geometric.GeometricalObject;
import hr.marin.jvdraw.model.DrawingModel;
import hr.marin.jvdraw.model.DrawingModelListener;

import javax.swing.AbstractListModel;

/**
 * An implementation of the {@link AbstractListModel} that represents a list
 * model for the {@link DrawingObjectList}. It is model for a list of
 * {@link GeometricalObject}s. It offers the standard list model methods.
 * 
 * @author Marin
 *
 */
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject> implements DrawingModelListener {
	/**
	 * The default serial version UID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The drawing model containing all the {@link GeometricalObject}s that this list model provides to its lists
	 */
	private DrawingModel model;

	/**
	 * Creates a new {@link DrawingObjectListModel} with the given drawing model.
	 * @param model The drawing model containing all the {@link GeometricalObject}s that this list model provides to its lists
	 */
	public DrawingObjectListModel(DrawingModel model) {
		this.model = model;
	}

	@Override
	public int getSize() {
		return model.getSize();
	}

	@Override
	public GeometricalObject getElementAt(int index) {
		return model.getObject(index);
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		fireIntervalAdded(this, index0, index1);
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		fireIntervalRemoved(this, index0, index1);
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		fireContentsChanged(this, index0, index1);
	}

}
