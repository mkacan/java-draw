package hr.marin.jvdraw.toolbar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

/**
 * <p>
 * A concrete implementation of the {@link IColorProvider} interface that
 * displays and enables the user to change the current color.<br>
 * It is essentially a square that displays the current color. The user can
 * change the color by clicking on the square and choosing a color in the color
 * chooser dialog.<br>
 * The component notifies all of its listeners every time the color is changed.
 * </p>
 * 
 * @author Marin
 *
 */
public class JColorArea extends JComponent implements IColorProvider {
	/**
	 * The default serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The title of the color chooser dialog.
	 */
	private static final String COLOR_CHOOSER_TITLE = "Choose a color";
	/**
	 * The default color of the square, if null is given in the constructor.
	 */
	private static final Color DEFAULT_COLOR = Color.BLACK;
	/**
	 * The preferred width of the square displaying the current color.
	 */
	private static final int PREFERRED_WIDTH = 15;
	/**
	 * The preferred height of the square displaying the current color.
	 */
	private static final int PREFERRED_HEIGHT = 15;
	/**
	 * The currently selected color.
	 */
	private Color selectedColor;
	/**
	 * All the listeners that get notified whenever a new color is selected.
	 */
	private List<ColorChangeListener> listeners;

	/**
	 * Creates the {@link JColorArea} component with the given initial color.
	 * 
	 * @param color
	 *            The initial color of the component.
	 */
	public JColorArea(Color color) {
		selectedColor = (color == null) ? DEFAULT_COLOR : color;
		this.setOpaque(true);

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Color chosenColor = JColorChooser.showDialog(JColorArea.this, COLOR_CHOOSER_TITLE, selectedColor);
				if (chosenColor != null) {
					Color previousColor = selectedColor;
					selectedColor = chosenColor;

					fireListeners(previousColor, selectedColor);
					JColorArea.this.repaint();
				}
			}
		});
	}

	/**
	 * Method notifies all the registered listeners that a change of color has
	 * happened.
	 * 
	 * @param previousColor
	 *            The color before it was changed.
	 * @param newColor
	 *            The new color after the change.
	 */
	private void fireListeners(Color previousColor, Color newColor) {
		if (listeners == null) {
			return;
		}

		for (ColorChangeListener listener : listeners) {
			listener.newColorSelected(this, previousColor, newColor);
		}
	}

	/**
	 * Adds a new listener to this color provider object. The added listener
	 * will be notified every time the color of this component is changed.
	 * 
	 * @param l
	 *            The listener that is to be registered to this component. If
	 *            null was given, the method does nothing.
	 */
	public void addColorChangeListener(ColorChangeListener l) {
		if (l == null) {
			return;
		}

		if (listeners == null) {
			listeners = new LinkedList<>();
		}

		listeners.add(l);
	}

	/**
	 * Removes an already registered listener from this color provider object.
	 * If the given argument was null or a listener that is not registered to
	 * this component, the method does nothing.
	 * 
	 * @param l
	 *            The listener that is to be deregistered from this component.
	 */
	public void removeColorChangeListener(ColorChangeListener l) {
		if (l == null) {
			return;
		}

		Iterator<ColorChangeListener> it = listeners.iterator();
		while (it.hasNext()) {
			ColorChangeListener listener = it.next();
			if (listener.equals(l)) {
				it.remove();
				return;
			}
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();

		Dimension size = this.getSize();
		Insets insets = this.getInsets();

		Rectangle rect = new Rectangle(insets.left, insets.top, size.width - insets.left - insets.right, size.height
				- insets.top - insets.bottom);

		g2d.setColor(selectedColor);
		g2d.fillRect(rect.x, rect.y, rect.width, rect.height);

		g2d.dispose();
	}

	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}
}
