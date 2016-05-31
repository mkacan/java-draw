package hr.marin.jvdraw.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import hr.marin.jvdraw.JVDraw;
import hr.marin.jvdraw.geometric.GeometricListener;
import hr.marin.jvdraw.geometric.GeometricalObject;

/**
 * An implementation of the {@link DrawingModel} interface used by the
 * {@link JVDraw} program.
 * 
 * @author Marin
 *
 */
public class DrawingModelImpl implements DrawingModel, GeometricListener {
	/**
	 * All the {@link GeometricalObject}s stored in this model.
	 */
	private List<GeometricalObject> objects;
	/**
	 * All the listeners of this model.
	 */
	private List<DrawingModelListener> listeners;

	/**
	 * An enumeration that contains all types of actions that can be made on the
	 * drawing model.
	 * 
	 * @author Marin
	 *
	 */
	private enum ObjectAction {
		/**
		 * If the model had its geometric objects changed
		 */
		CHANGED,
		/**
		 * If geometric objects were added to the model
		 */
		ADDED,
		/**
		 * If geometric objects were removed from the model
		 */
		REMOVED
	}

	/**
	 * Creates a new empty {@link DrawingModelImpl} object.
	 */
	public DrawingModelImpl() {
		objects = new ArrayList<>();
	}

	@Override
	public int getSize() {
		return objects.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		return objects.get(index);
	}

	@Override
	public void add(GeometricalObject object) {
		if (object == null) {
			return;
		}
		objects.add(object);
		object.addGeometricListener(this);
		fireListeners(ObjectAction.ADDED, 0, getSize());
	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		if (l == null) {
			return;
		}

		if (listeners == null) {
			listeners = new LinkedList<>();
		}

		listeners.add(l);
	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		if (l == null) {
			return;
		}

		Iterator<DrawingModelListener> it = listeners.iterator();
		while (it.hasNext()) {
			DrawingModelListener listener = it.next();
			if (listener.equals(l)) {
				it.remove();
				return;
			}
		}
	}

	@Override
	public void objectChanged(GeometricalObject source) {
		fireListeners(ObjectAction.CHANGED, 0, getSize());
	}

	/**
	 * Method notifies all the registered listeners that a geometric object has
	 * changed.
	 * 
	 * @param objectAction
	 *            Specifies what kind of action was performed on the drawing
	 *            model.
	 * @param index0
	 *            The first index of the interval that was changed.
	 * @param index1
	 *            The last index of the interval that was changed.
	 */
	private void fireListeners(ObjectAction objectAction, int index0, int index1) {
		if (listeners == null) {
			return;
		}

		for (DrawingModelListener listener : listeners) {
			switch (objectAction) {
			case ADDED:
				listener.objectsAdded(this, index0, index1);
				break;
			case REMOVED:
				listener.objectsRemoved(this, index0, index1);
				break;
			case CHANGED:
				listener.objectsChanged(this, index0, index1);
				break;
			}
		}
	}
}
