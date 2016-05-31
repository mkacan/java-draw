package hr.marin.jvdraw;

import hr.marin.jvdraw.actions.ExitAction;
import hr.marin.jvdraw.actions.ExportAction;
import hr.marin.jvdraw.actions.OpenAction;
import hr.marin.jvdraw.actions.SaveAction;
import hr.marin.jvdraw.actions.SaveAsAction;
import hr.marin.jvdraw.list.DrawingObjectList;
import hr.marin.jvdraw.list.DrawingObjectListModel;
import hr.marin.jvdraw.model.DrawingModel;
import hr.marin.jvdraw.model.DrawingModelImpl;
import hr.marin.jvdraw.model.DrawingModelListener;
import hr.marin.jvdraw.toolbar.JColorArea;
import hr.marin.jvdraw.toolbar.Shape;
import hr.marin.jvdraw.toolbar.ShapeButtons;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.nio.file.Path;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * The class represents a simple vector graphics program capable of drawing
 * lines and circles in different colors and sizes.
 * 
 * @author Marin
 *
 */
public class JVDraw extends JFrame {
	/**
	 * The default serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The drawing model used by the drawing canvas
	 */
	private DrawingModel drawingModel;
	/**
	 * The central component onto which all the shapes are painted
	 */
	private JDrawingCanvas drawingCanvas;
	/**
	 * The mouse listener used to enable the user to draw shapes using the mouse
	 */
	private MouseCreator mouseCreator;
	/**
	 * The data model for the drawing object list
	 */
	private DrawingObjectListModel listModel;
	/**
	 * The path of the currently opened file in the program
	 */
	private Path openedFile;
	/**
	 * Whether the currently opened file has been modified (true if it has)
	 */
	private boolean modified;

	/**
	 * Initializes the main window of the program.
	 */
	public JVDraw() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocation(250, 250);
		setSize(500, 500);
		setTitle("JVDraw");

		setLayout(new BorderLayout());

		JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JColorArea jca = new JColorArea(Color.BLACK);
		JColorArea jca2 = new JColorArea(Color.WHITE);

		toolbar.add(jca);
		toolbar.add(jca2);
		ShapeButtons sbg = new ShapeButtons(Shape.LINE);
		toolbar.add(sbg);
		add(toolbar, BorderLayout.NORTH);

		drawingModel = new DrawingModelImpl();
		drawingCanvas = new JDrawingCanvas(drawingModel);

		add(drawingCanvas, BorderLayout.CENTER);

		drawingModel.addDrawingModelListener(drawingCanvas);
		mouseCreator = new MouseCreator(drawingModel, jca, jca2, sbg);
		drawingCanvas.addMouseListener(mouseCreator);
		drawingCanvas.addMouseMotionListener(mouseCreator);

		listModel = new DrawingObjectListModel(drawingModel);
		drawingModel.addDrawingModelListener(listModel);

		add(new DrawingObjectList(listModel), BorderLayout.EAST);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		fileMenu.add(new OpenAction(this));
		fileMenu.add(new SaveAction(this));
		fileMenu.add(new SaveAsAction(this));
		fileMenu.add(new ExportAction(this));
		fileMenu.add(new ExitAction(this));

		drawingModel.addDrawingModelListener(new DrawingModelListener() {
			@Override
			public void objectsRemoved(DrawingModel source, int index0, int index1) {
				modified = true;
			}

			@Override
			public void objectsChanged(DrawingModel source, int index0, int index1) {
				modified = true;
			}

			@Override
			public void objectsAdded(DrawingModel source, int index0, int index1) {
				modified = true;
			}
		});

		add(new StatusBar(jca, jca2), BorderLayout.SOUTH);
	}

	/**
	 * This method is called once the program is run.
	 * 
	 * @param args
	 *            Command line arguments. Not used.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JVDraw().setVisible(true);
		});
	}

	/**
	 * Sets the path of the currently opened file.
	 * 
	 * @param path
	 *            The path of the file.
	 */
	public void setOpenedFile(Path path) {
		openedFile = path;
	}

	/**
	 * Gets the path of the currently opened file.
	 * 
	 * @return The path of the file.
	 */
	public Path getOpenedFile() {
		return openedFile;
	}

	/**
	 * Gets the current drawing model containing the geometric objects.
	 * 
	 * @return The current drawing model.
	 */
	public DrawingModel getDrawingModel() {
		return drawingModel;
	}

	/**
	 * Checks whether the currently opened file has been modified since it was
	 * last saved.
	 * 
	 * @return True if has been modified, false otherwise
	 */
	public boolean modified() {
		return modified;
	}

	/**
	 * Sets the modification status of the currently opened file according to
	 * the given argument.
	 * 
	 * @param modified
	 *            The modification status (true if the file has been modified,
	 *            false otherwise).
	 */
	public void setModified(boolean modified) {
		this.modified = modified;
	}

	/**
	 * Exits the program by closing the main window.
	 */
	public void exit() {
		dispose();
	}
}
