package hr.marin.jvdraw.toolbar;

/**
 * <p>
 * An enumeration that contains all geometric shapes that can be drawn in the
 * JVDraw program.<br>
 * Each member can also be obtained by its name via the method forName.
 * </p>
 * 
 * @author Marin
 *
 */
public enum Shape {
	/**
	 * Line shape
	 */
	LINE("Line"),
	/**
	 * Empty circle shape (no fill)
	 */
	CIRCLE_EMPTY("Circle"),
	/**
	 * Filled circle shape
	 */
	CIRCLE_FILLED("Filled circle");

	/**
	 * The name of the enumeration member,
	 */
	private final String name;

	/**
	 * Creates the enumeration member with the given string as its name.
	 * 
	 * @param name
	 *            The name of the enumeration member.
	 */
	private Shape(String name) {
		this.name = name;
	}

	/**
	 * Method obtains a shape with the given name. If there is no shape with the
	 * given name, the method returns null.
	 * 
	 * @param name
	 *            The name of the shape that is to be obtained.
	 * @return The shape with the given name, or null if there is no shape with
	 *         that name.
	 */
	public static Shape forName(String name) {
		for (Shape s : values()) {
			if (s.toString().equals(name)) {
				return s;
			}
		}

		return null;
	}

	@Override
	public String toString() {
		return name;
	}
}
