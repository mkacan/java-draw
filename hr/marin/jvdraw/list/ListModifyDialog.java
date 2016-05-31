package hr.marin.jvdraw.list;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.text.NumberFormat;

import hr.marin.jvdraw.geometric.Circle;
import hr.marin.jvdraw.geometric.GeometricalObject;
import hr.marin.jvdraw.geometric.Line;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.NumberFormatter;

/**
 * <p>
 * An implementation of {@link JPanel} that represents a dialog that is
 * displayed after the user double-clicks on a {@link GeometricalObject} in a
 * list. The dialog enables the user to change the selected
 * {@link GeometricalObject}s properties.
 * </p>
 * 
 * @author Marin
 *
 */
public class ListModifyDialog extends JPanel {
	/**
	 * The default serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The number formatter used by all the number fields of the dialog.
	 */
	private NumberFormatter numFormatter;

	/**
	 * Creates a new {@link ListModifyDialog} for the given
	 * {@link GeometricalObject} whose properties will be modifiable by the user
	 * via the dialog.
	 * 
	 * @param object
	 *            The geometric object whose properties will be modifiable via
	 *            the created dialog.
	 */
	public ListModifyDialog(GeometricalObject object) {
		numFormatter = new NumberFormatter(NumberFormat.getIntegerInstance());
		numFormatter.setAllowsInvalid(false);
		numFormatter.setMinimum(new Integer(0));

		if (object.getClass() == Line.class) {
			initLineDialog((Line) object);
		} else if (object.getClass() == Circle.class) {
			initCircleDialog((Circle) object);
		}
	}

	/**
	 * Method initializes the panel if the given geometric objects was a circle.
	 * @param circle The {@link Circle} object whose modification dialog is to be created by this method.
	 */
	private void initCircleDialog(Circle circle) {
		if (circle.isFilled()) {
			setLayout(new GridLayout(4, 4));
		} else {
			setLayout(new GridLayout(3, 4));
		}

		add(new JLabel("Outline: ", JLabel.RIGHT));
		JButton outlineColor = new JButton("Choose");
		outlineColor.addActionListener((e) -> {
			Color chosenColor = JColorChooser.showDialog(ListModifyDialog.this, "Choose a color",
					circle.getForegroundColor());
			if (chosenColor != null) {
				circle.setForegroundColor(chosenColor);
			}
		});
		add(outlineColor);
		add(new JPanel());
		add(new JPanel());

		if (circle.isFilled()) {
			add(new JLabel("Fill: ", JLabel.RIGHT));
			JButton fillColor = new JButton("Choose");
			fillColor.addActionListener((e) -> {
				Color chosenColor = JColorChooser.showDialog(ListModifyDialog.this, "Choose a color",
						circle.getBackgroundColor());
				if (chosenColor != null) {
					circle.setBackgroundColor(chosenColor);
				}
			});
			add(fillColor);
			add(new JPanel());
			add(new JPanel());

		}

		add(new JLabel("Center: ", JLabel.RIGHT));

		JFormattedTextField centerXtf = new JFormattedTextField(numFormatter);
		centerXtf.setText(Integer.toString(circle.getCenter().x));
		JFormattedTextField centerYtf = new JFormattedTextField(numFormatter);
		centerYtf.setText(Integer.toString(circle.getCenter().y));

		add(centerXtf);
		add(centerYtf);
		JButton setCenter = new JButton("Set");
		setCenter.addActionListener((e) -> {
			circle.setCenter(new Point(Integer.parseInt(centerXtf.getText()), Integer.parseInt(centerYtf.getText())));
		});
		add(setCenter);

		add(new JLabel("Radius: ", JLabel.RIGHT));
		JFormattedTextField radiusTf = new JFormattedTextField(numFormatter);
		radiusTf.setText(Integer.toString(circle.getRadius()));
		add(radiusTf);
		JButton setRadius = new JButton("Set");
		setRadius.addActionListener((e) -> {
			circle.setRadius(Integer.parseInt(radiusTf.getText()));
		});
		add(setRadius);
		add(new JPanel());
	}

	/**
	 * Method initializes the panel if the given geometric objects was a line.
	 * @param line The {@link Line} object whose modification dialog is to be created by this method.
	 */
	private void initLineDialog(Line line) {
		setLayout(new GridLayout(3, 4));

		add(new JLabel("Color: ", JLabel.RIGHT));
		JButton chooseColor = new JButton("Choose");
		chooseColor.addActionListener((e) -> {
			Color chosenColor = JColorChooser.showDialog(ListModifyDialog.this, "Choose a color",
					line.getForegroundColor());
			if (chosenColor != null) {
				line.setForegroundColor(chosenColor);
			}
		});
		add(chooseColor);
		add(new JPanel());
		add(new JPanel());

		add(new JLabel("Start: ", JLabel.RIGHT));

		JFormattedTextField startXtf = new JFormattedTextField(numFormatter);
		startXtf.setText(Integer.toString(line.getStart().x));
		JFormattedTextField startYtf = new JFormattedTextField(numFormatter);
		startYtf.setText(Integer.toString(line.getStart().y));

		add(startXtf);
		add(startYtf);
		JButton setStart = new JButton("Set");
		setStart.addActionListener((e) -> {
			line.setStart(new Point(Integer.parseInt(startXtf.getText()), Integer.parseInt(startYtf.getText())));
		});
		add(setStart);

		add(new JLabel("End: ", JLabel.RIGHT));
		JFormattedTextField endXtf = new JFormattedTextField(numFormatter);
		endXtf.setText(Integer.toString(line.getEnd().x));
		JFormattedTextField endYtf = new JFormattedTextField(numFormatter);
		endYtf.setText(Integer.toString(line.getEnd().y));

		add(endXtf);
		add(endYtf);
		JButton setEnd = new JButton("Set");
		setEnd.addActionListener((e) -> {
			line.setEnd(new Point(Integer.parseInt(endXtf.getText()), Integer.parseInt(endYtf.getText())));
		});

		add(setEnd);
	}
}
