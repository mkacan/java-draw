package hr.marin.jvdraw.actions;

import hr.marin.jvdraw.JVDraw;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.nio.file.Path;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

/**
 * An implementation of the {@link AbstractAction} that represents the Save
 * action.
 * 
 * @author Marin
 *
 */
public class SaveAction extends AbstractAction {
	/**
	 * The default serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The main window of the {@link JVDraw} program.
	 */
	private JVDraw frame;

	/**
	 * Creates a new {@link SaveAction} object with the given {@link JVDraw}
	 * frame.
	 * 
	 * @param frame
	 *            The main window of the {@link JVDraw} program.
	 */
	public SaveAction(JVDraw frame) {
		this.frame = frame;
		putValue(NAME, "Save");

		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Path openedFilePath = frame.getOpenedFile();
		
		if(openedFilePath == null) {
			openedFilePath = SaveUtility.chooseFile();
		}
		if (openedFilePath == null) {
			return;
		}

		SaveUtility.saveToPath(frame.getDrawingModel(), openedFilePath);
		frame.setModified(false);
	}

}
