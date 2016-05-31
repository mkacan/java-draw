package hr.marin.jvdraw.actions;

import hr.marin.jvdraw.JVDraw;
import java.awt.event.ActionEvent;
import java.nio.file.Path;

import javax.swing.AbstractAction;

/**
 * An implementation of the {@link AbstractAction} that represents the Save As
 * action.
 * 
 * @author Marin
 *
 */
public class SaveAsAction extends AbstractAction {
	/**
	 * The default serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The main window of the {@link JVDraw} program.
	 */
	private JVDraw frame;

	/**
	 * Creates a new {@link SaveAsAction} object with the given {@link JVDraw}
	 * frame.
	 * 
	 * @param frame
	 *            The main window of the {@link JVDraw} program.
	 */
	public SaveAsAction(JVDraw frame) {
		this.frame = frame;
		putValue(NAME, "Save as");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Path openedFilePath = SaveUtility.chooseFile();
		if (openedFilePath == null) {
			return;
		}

		SaveUtility.saveToPath(frame.getDrawingModel(), openedFilePath);
		frame.setModified(false);
	}
}
