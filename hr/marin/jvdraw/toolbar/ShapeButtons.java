package hr.marin.jvdraw.toolbar;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

/**
 * <p>
 * A concrete implementation of the {@link IShapeProvider} interface that
 * displays and enables the user to change the current shape.<br>
 * It is a group of buttons, each of which corresponds to a {@link Shape}. The
 * currently selected button represents the current shape. The user can change
 * the shape by clicking on an unselected button.<br>
 * The component notifies all of its listeners every time the shape is changed.
 * </p>
 * 
 * @author Marin
 *
 */
public class ShapeButtons extends JPanel implements IShapeProvider {
	/**
	 * The default serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The default selected shape, if null is given in the constructor.
	 */
	private static final Shape DEFAULT_SHAPE = Shape.LINE;
	/**
	 * A button group containing all the shape buttons.
	 */
	private ButtonGroup buttonGroup;
	/**
	 * A mapping of shapes to their corresponding buttons.
	 */
	private Map<Shape, JToggleButton> toggleButtons;
	/**
	 * The currently selected shape.
	 */
	private Shape selected;
	/**
	 * All the listeners that get notified whenever a new shape is selected.
	 */
	private List<ShapeChangeListener> listeners;

	/**
	 * Creates the {@link ShapeButtons} component with the given shape initially selected.
	 * 
	 * @param shape
	 *            The shape whose button is initially selected.
	 */
	public ShapeButtons(Shape shape) {
		setLayout(new FlowLayout(FlowLayout.LEFT));

		buttonGroup = new ButtonGroup();

		toggleButtons = new HashMap<>();
		for (Shape s : Shape.values()) {
			JToggleButton button = new JToggleButton(s.toString());
			add(button);

			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JToggleButton pressed = (JToggleButton) e.getSource();
					if (!pressed.equals(toggleButtons.get(selected))) {
						buttonGroup.setSelected(pressed.getModel(), true);

						Shape previous = selected;
						selected = Shape.forName(pressed.getText());
						fireListeners(previous, selected);
					}
				}
			});
			toggleButtons.put(s, button);
			buttonGroup.add(button);
		}

		selected = (shape == null) ? DEFAULT_SHAPE : shape;

		buttonGroup.setSelected(toggleButtons.get(selected).getModel(), true);
	}

	/**
	 * Method notifies all the registered listeners that a change of shape has
	 * happened.
	 * 
	 * @param previous
	 *            The shape before it was changed.
	 * @param selected
	 *            The new shape after the change.
	 */
	private void fireListeners(Shape previous, Shape selected) {
		if (listeners == null) {
			return;
		}

		for (ShapeChangeListener listener : listeners) {
			listener.shapeChanged(this, previous, selected);
		}
	}

	/**
	 * Adds a new listener to this shape provider object. The added listener
	 * will be notified every time the selected shape is changed.
	 * 
	 * @param l
	 *            The listener that is to be registered to this component. If
	 *            null was given, the method does nothing.
	 */
	public void addShapeChangeListener(ShapeChangeListener l) {
		if (l == null) {
			return;
		}

		if (listeners == null) {
			listeners = new LinkedList<>();
		}

		listeners.add(l);
	}
	
	/**
	 * Removes an already registered listener from this shape provider object.
	 * If the given argument was null or a listener that is not registered to
	 * this component, the method does nothing.
	 * 
	 * @param l
	 *            The listener that is to be deregistered from this component.
	 */
	public void removeShapeChangeListener(ShapeChangeListener l) {
		if (l == null) {
			return;
		}

		Iterator<ShapeChangeListener> it = listeners.iterator();
		while (it.hasNext()) {
			ShapeChangeListener listener = it.next();
			if (listener.equals(l)) {
				it.remove();
				return;
			}
		}
	}

	@Override
	public Shape getCurrentShape() {
		return selected;
	}
}
