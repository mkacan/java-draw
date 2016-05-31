package hr.marin.jvdraw;

import java.awt.Color;

import hr.marin.jvdraw.toolbar.ColorChangeListener;
import hr.marin.jvdraw.toolbar.IColorProvider;
import hr.marin.jvdraw.toolbar.JColorArea;

import javax.swing.JLabel;

/**
 * The status bar for the {@link JVDraw} program that displays the currently selected foreground and background colors.
 * @author Marin
 *
 */
public class StatusBar extends JLabel implements ColorChangeListener {
	/**
	 * The default serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The provider for the currently selected foreground color.
	 */
	private JColorArea foreground;
	/**
	 * The provider for the currently selected background color.
	 */
	private JColorArea background;

	/**
	 * Creates the status bar with the given color providers.
	 * @param foreground The color provider for the foreground color.
	 * @param background The color provider for the background color.
	 */
	public StatusBar(JColorArea foreground, JColorArea background) {
		this.foreground = foreground;
		foreground.addColorChangeListener(this);
		this.background = background;
		background.addColorChangeListener(this);

		updateText();
	}

	/**
	 * Method updates the text of the status bar to match the currently selected colors.
	 */
	private void updateText() {
		Color f = foreground.getCurrentColor();
		Color b = background.getCurrentColor();
		setText("Foreground color: (" + f.getRed() + ", " + f.getGreen() + ", " + f.getBlue()
				+ "), background color: (" + b.getRed() + ", " + b.getGreen() + ", " + b.getBlue() + ").");
	}

	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
		updateText();
	}
}
