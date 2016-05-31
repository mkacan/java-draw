package hr.marin.jvdraw.actions;

import hr.marin.jvdraw.JVDraw;
import hr.marin.jvdraw.geometric.Circle;
import hr.marin.jvdraw.geometric.GeometricalObject;
import hr.marin.jvdraw.geometric.Line;
import hr.marin.jvdraw.model.DrawingModel;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * An implementation of the {@link AbstractAction} that represents the Open
 * action.
 * 
 * @author Marin
 *
 */
public class OpenAction extends AbstractAction {
	/**
	 * The default serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The main window of the {@link JVDraw} program.
	 */
	private JVDraw frame;

	/**
	 * Creates a new {@link OpenAction} object with the given {@link JVDraw}
	 * frame.
	 * 
	 * @param frame
	 *            The main window of the {@link JVDraw} program.
	 */
	public OpenAction(JVDraw frame) {
		this.frame = frame;
		putValue(NAME, "Open");

		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Jvd files", "jvd");
		fc.setFileFilter(filter);

		fc.setDialogTitle("Open file");
		if (fc.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
			return;
		}

		Path openedFilePath = fc.getSelectedFile().toPath();
		if (!Files.isReadable(openedFilePath)) {
			JOptionPane.showMessageDialog(null, "The file " + openedFilePath + " doesn't exist.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		List<String> lines = null;
		try {
			lines = Files.readAllLines(openedFilePath, StandardCharsets.UTF_8);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		frame.setOpenedFile(openedFilePath);

		addGeometricObjects(lines);
	}

	/**
	 * Method generates geometric objects using their given textual
	 * representation and adds all of those objects to the current drawing
	 * model.
	 * 
	 * @param lines
	 *            The list of the textual representations of all the geomtric
	 *            objects (1 line = 1 object).
	 */
	private void addGeometricObjects(List<String> lines) {
		GeometricalObject go = null;
		DrawingModel drawingModel = frame.getDrawingModel();

		for (String line : lines) {
			line = line.trim();
			try {
				if (line.startsWith("LINE")) {
					go = createLine(line.substring(4).trim());
				} else if (line.startsWith("CIRCLE")) {
					go = createCircle(line.substring(6).trim());
				} else if (line.startsWith("FCIRCLE")) {
					go = createFilledCircle(line.substring(7).trim());
				}
			} catch (RuntimeException e) {
				continue;
			}

			if (go != null) {
				drawingModel.add(go);
			}
		}
	}

	/**
	 * Method generates a filled {@link Circle} object from the given textual
	 * representation.
	 * 
	 * @param arguments
	 *            The textual representation of the circle parameters.
	 * @return The generated filled {@link Circle} object.
	 */
	private GeometricalObject createFilledCircle(String arguments) {
		String[] split = arguments.split(" +");

		return new Circle(new Point(Integer.parseInt(split[0]), Integer.parseInt(split[1])),
				Integer.parseInt(split[2]), new Color(Integer.parseInt(split[3]), Integer.parseInt(split[4]),
						Integer.parseInt(split[5])), new Color(Integer.parseInt(split[6]), Integer.parseInt(split[7]),
						Integer.parseInt(split[8])));
	}

	/**
	 * Method generates an empty {@link Circle} object from the given textual
	 * representation.
	 * 
	 * @param arguments
	 *            The textual representation of the circle parameters.
	 * @return The generated empty {@link Circle} object.
	 */
	private GeometricalObject createCircle(String arguments) {
		String[] split = arguments.split(" +");
		return new Circle(new Point(Integer.parseInt(split[0]), Integer.parseInt(split[1])),
				Integer.parseInt(split[2]), new Color(Integer.parseInt(split[3]), Integer.parseInt(split[4]),
						Integer.parseInt(split[5])), null);
	}

	/**
	 * Method generates a {@link Line} object from the given textual
	 * representation.
	 * 
	 * @param arguments
	 *            The textual representation of the line parameters.
	 * @return The generated {@link Line} object.
	 */
	private GeometricalObject createLine(String arguments) {
		String[] split = arguments.split(" +");

		return new Line(new Point(Integer.parseInt(split[0]), Integer.parseInt(split[1])), new Point(
				Integer.parseInt(split[2]), Integer.parseInt(split[3])), new Color(Integer.parseInt(split[4]),
				Integer.parseInt(split[5]), Integer.parseInt(split[6])));
	}

}
