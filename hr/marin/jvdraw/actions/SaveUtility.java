package hr.marin.jvdraw.actions;

import hr.marin.jvdraw.geometric.Circle;
import hr.marin.jvdraw.geometric.GeometricalObject;
import hr.marin.jvdraw.geometric.Line;
import hr.marin.jvdraw.model.DrawingModel;

import java.awt.Color;
import java.awt.Point;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * A utility class containing methods used by the SaveAction and SaveAsAction
 * classes.
 * 
 * @author Marin
 *
 */
public class SaveUtility {
	/**
	 * Method creates a textual representation of the given drawing model and
	 * saves it to the file with the given path.
	 * 
	 * @param drawingModel
	 *            The drawing model whose textual representation is to be saved
	 *            to the given file.
	 * @param openedFilePath
	 *            The file that will store the textual representation of the
	 *            drawing model.
	 */
	public static void saveToPath(DrawingModel drawingModel, Path openedFilePath) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0, n = drawingModel.getSize(); i < n; i++) {
			sb.append(toText(drawingModel.getObject(i)) + "\n");
		}

		try {
			Files.write(openedFilePath, sb.toString().getBytes(StandardCharsets.UTF_8));
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

	/**
	 * Method generates a textual representation of the given geometric object.
	 * 
	 * @param object
	 *            The geometric object whose textual representation is to be
	 *            generated.
	 * @return The textual representation of the given geometric object.
	 */
	public static String toText(GeometricalObject object) {
		String text = "";
		if (object.getClass() == Line.class) {
			Line line = (Line) object;
			Point st = line.getStart();
			Point end = line.getEnd();
			Color c = line.getForegroundColor();
			text = "LINE " + st.x + " " + st.y + " " + end.x + " " + end.y + " " + c.getRed() + " " + c.getGreen()
					+ " " + c.getBlue();
		} else {
			Circle circle = (Circle) object;
			Point c = circle.getCenter();
			int r = circle.getRadius();
			Color out = circle.getForegroundColor();
			Color fill = circle.getBackgroundColor();
			text += (fill == null ? "CIRCLE " : "FCIRCLE ");
			text += c.x + " " + c.y + " " + r + " " + out.getRed() + " " + out.getGreen() + " " + out.getBlue();
			text += (fill == null ? "" : " " + fill.getRed() + " " + fill.getGreen() + " " + fill.getBlue());
		}

		return text;
	}

	/**
	 * Method displays a dialog asking the user to choose a file that will be
	 * the save destination.
	 * 
	 * @return The file that the user chose to save to.
	 */
	public static Path chooseFile() {
		JFileChooser fc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Jvd files", "jvd");
		fc.setFileFilter(filter);

		fc.setDialogTitle("Save document");
		if (fc.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
			JOptionPane
					.showMessageDialog(null, "Nothing was saved.", "System message", JOptionPane.INFORMATION_MESSAGE);
			return null;
		}

		Path file = fc.getSelectedFile().toPath();
		if (Files.exists(file)) {
			int r = JOptionPane.showConfirmDialog(null, "The file " + file + " already exists. Overwrite?", "Warning",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (r != JOptionPane.YES_OPTION) {
				return null;
			}
		}

		return file;
	}
}
