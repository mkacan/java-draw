package hr.marin.jvdraw.actions;

import hr.marin.jvdraw.JVDraw;
import hr.marin.jvdraw.model.DrawingModel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * An implementation of the {@link AbstractAction} that represents the Export
 * action. It is used to export the image currently drawn on the canvas to an
 * image file (jpg, png or gif).
 * 
 * @author Marin
 *
 */
public class ExportAction extends AbstractAction {
	/**
	 * The default serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The main window of the {@link JVDraw} program.
	 */
	private JVDraw frame;

	/**
	 * Creates a new {@link ExportAction} object with the given {@link JVDraw}
	 * frame.
	 * 
	 * @param frame
	 *            The main window of the {@link JVDraw} program.
	 */
	public ExportAction(JVDraw frame) {
		this.frame = frame;
		putValue(NAME, "Export");

		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		DrawingModel drawingModel = frame.getDrawingModel();
		Rectangle minBoundRect = getMinBoundRect(drawingModel);
		BufferedImage image = new BufferedImage(minBoundRect.width, minBoundRect.height, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g = image.createGraphics();

		g.setColor(Color.WHITE);
		g.translate(-minBoundRect.x, -minBoundRect.y);
		g.fillRect(minBoundRect.x, minBoundRect.y, minBoundRect.width, minBoundRect.height);
		for (int i = 0, n = drawingModel.getSize(); i < n; i++) {
			drawingModel.getObject(i).paint(g);
		}
		g.dispose();

		JFileChooser fc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", "jpg", "png", "gif");
		fc.setFileFilter(filter);

		fc.setDialogTitle("Export document");

		if (fc.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(null, "Nothing was exported.", "System message",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		Path path = fc.getSelectedFile().toPath();
		if (Files.exists(path)) {
			int r = JOptionPane.showConfirmDialog(null, "The file " + path + " already exists. Overwrite?", "Warning",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (r != JOptionPane.YES_OPTION) {
				return;
			}
		}

		File file = path.toFile();

		try {
			ImageIO.write(image, getExtension(file), file);
		} catch (IOException ignorable) {
		}
	}

	/**
	 * Method calculates the minimum bounding rectangle for all the geometric
	 * objects in the given drawing model.
	 * 
	 * @param drawingModel
	 *            The drawing model containing all the geometric objects that
	 *            the minimum bounding rectangle is calculated for.
	 * @return The calculated minimum bounding rectangle for the given drawing
	 *         model.
	 */
	private Rectangle getMinBoundRect(DrawingModel drawingModel) {
		Rectangle result = drawingModel.getObject(0).getBoundingRect();

		for (int i = 0, n = drawingModel.getSize(); i < n; i++) {
			Rectangle r = drawingModel.getObject(i).getBoundingRect();

			if (r.x < result.x) {
				result.width += (result.x - r.x);
				result.x = r.x;
			}
			if (r.y < result.y) {
				result.height += (result.y - r.y);
				result.y = r.y;
			}
		}

		for (int i = 0, n = drawingModel.getSize(); i < n; i++) {
			Rectangle r = drawingModel.getObject(i).getBoundingRect();

			if (r.x + r.width > result.x + result.width) {
				result.width += (r.x + r.width - result.x - result.width);
			}

			if (r.y + r.height > result.y + result.height) {
				result.height += (r.y + r.height - result.y - result.height);
			}
		}

		return result;
	}

	/**
	 * Method obtains the extension of the given file.
	 * 
	 * @param file
	 *            The file whose extension is to be obtained.
	 * @return The obtained file extension.
	 */
	private String getExtension(File file) {
		String name = file.getName();
		int index = name.lastIndexOf('.');
		if (index == -1) {
			return "";
		}

		return name.substring(index + 1);
	}
}
