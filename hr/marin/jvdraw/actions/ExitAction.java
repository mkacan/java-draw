package hr.marin.jvdraw.actions;

import hr.marin.jvdraw.JVDraw;
import java.awt.event.ActionEvent;
import java.nio.file.Path;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 * An implementation of the {@link AbstractAction} that represents the Exit
 * action.
 * 
 * @author Marin
 *
 */
public class ExitAction extends AbstractAction {
	/**
	 * The default serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The main window of the {@link JVDraw} program.
	 */
	private JVDraw frame;

	/**
	 * Creates a new {@link ExitAction} object with the given {@link JVDraw}
	 * frame.
	 * 
	 * @param frame
	 *            The main window of the {@link JVDraw} program.
	 */
	public ExitAction(JVDraw frame) {
		this.frame = frame;
		putValue(NAME, "Exit");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!frame.modified()) {
			frame.exit();
			return;
		}

		int r = JOptionPane.showConfirmDialog(null, "The file has been modified. Save?", "Warning",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (r == JOptionPane.YES_OPTION) {
			Path openedFilePath = frame.getOpenedFile();

			if (openedFilePath == null) {
				openedFilePath = SaveUtility.chooseFile();
			}
			if (openedFilePath == null) {
				return;
			}

			SaveUtility.saveToPath(frame.getDrawingModel(), openedFilePath);
		}

		frame.exit();
	}

}
