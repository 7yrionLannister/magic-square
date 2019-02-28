package customExceptions;


@SuppressWarnings("serial")
public class InvalidOrientationException extends IllegalArgumentException {

	/**The orientation that caused the exception
	 * */
	private int[] orientation;
	
	/**The method allows to create an instance of this class with the specified invalid orientation
	 * @param orientation The invalid orientation that caused the exception
	 * */
	public InvalidOrientationException(int[] orientation) {
		super("The chosen orientation is not in the domain: {" + orientation[0] + ", " + orientation[1]);
		this.orientation = orientation;
	}

	/**The method allows to obtain the orientation that caused the exception
	 * @return The invalid orientation that caused the exception
	 * */
	public int[] getOrientation() {
		return orientation;
	}
}
