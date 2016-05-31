package hr.marin.jvdraw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import hr.marin.jvdraw.model.DrawingModel;
import hr.marin.jvdraw.model.DrawingModelListener;
import javax.swing.JComponent;

/**
 * The central component of a {@link JVDraw} program. It represents the canvas
 * onto which all the geometric shapes are drawn.
 * 
 * @author Marin
 *
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener {
	/**
	 * The default serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The drawing model used by this canvas to obtain geometric objects that are to be drawn
	 */
	private DrawingModel drawingModel;

	/**
	 * Creates a new {@link JDrawingCanvas} object with the given {@link DrawingModel} argument.
	 * @param drawingModel The drawing model used by this canvas to obtain geometric objects that are to be drawn
	 */
	public JDrawingCanvas(DrawingModel drawingModel) {
		this.drawingModel = drawingModel;
		setOpaque(true);
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		drawingModel = source;
		repaint();
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		drawingModel = source;
		repaint();
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		drawingModel = source;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		Dimension size = getSize();
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, size.width, size.height);

		for (int i = 0, n = drawingModel.getSize(); i < n; i++) {
			drawingModel.getObject(i).paint(g2d);
		}
	}
}
