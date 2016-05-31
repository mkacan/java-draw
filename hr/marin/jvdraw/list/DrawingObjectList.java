package hr.marin.jvdraw.list;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import hr.marin.jvdraw.geometric.GeometricalObject;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.border.BevelBorder;

/**
 * A {@link JList} implementation that represents a list of
 * {@link GeometricalObject}s.
 * 
 * @author Marin
 *
 */
public class DrawingObjectList extends JList<GeometricalObject> {
	/**
	 * The default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new {@link DrawingObjectList} using the given list model.
	 * 
	 * @param listModel
	 *            The data model used by this list to obtain
	 *            {@link GeometricalObject}s to display.
	 */
	public DrawingObjectList(DrawingObjectListModel listModel) {
		super(listModel);
		setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		setOpaque(true);
		setPreferredSize(new Dimension(100, 0));
		addMouseListener(new DoubleClickListener());
	}

	/**
	 * A {@link MouseListener} implementation that listens for a double-click
	 * performed by the user on a list cell and then displays a dialog enabling
	 * the user to change the parameters of the selected
	 * {@link GeometricalObject}.
	 * 
	 * @author Marin
	 *
	 */
	class DoubleClickListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() != 2) {
				return;
			}

			int index = DrawingObjectList.this.locationToIndex(e.getPoint());
			if (index == -1) {
				return;
			}

			Rectangle cellBounds = getCellBounds(index, index);
			if (!cellBounds.contains(e.getPoint())) {
				return;
			}

			JOptionPane.showMessageDialog(DrawingObjectList.this, new ListModifyDialog(getModel().getElementAt(index)));
		}
	}
}
