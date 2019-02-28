package customExceptions;

import model.MagicSquare;


@SuppressWarnings("serial")
public class LoubereMethodNotSatisfiedException extends IllegalStateException {
	/**The attribute indicates what is the invalid initial edge that caused the exception
	 * */
	private String initialEdge;
	/**The attribute indicates what is the invalid orientation that caused the exception
	 * */
	private int[] orientation;
	/**This is the information message to concatenate with that of the superclass
	 * */
	private String customMessage;

	/**The method allows to create an instance of this class with the specified invalid initial edge 
	 * and orientation
	 * @param initialEdge The invalid initial edge that caused the exception
	 * @param orientation The invalid orientation that caused the exception
	 * */
	public LoubereMethodNotSatisfiedException(String initialEdge, int[] orientation) {
		super("The attributes of the magic square, in spite of being in the domain, don't comply with Loubere method rules\n" + 
				"Valid options of the Loubere method:\n" + 
				"	* MagicSquare.FIRST_ROW and MagicSquare.NO\n" +
				"	* MagicSquare.FIRST_ROW and MgicSquare.NE\n" +
				"	* MagicSquare.LAST_ROW and MagicSquare.SO\n" +
				"	* MagicSquare.LAST_ROW and MagicSquare.SE\n" + 
				"	* MagicSquare.FIRST_COLUMN and MagicSquare.NO\n" +
				"	* MagicSquare.FIRST_COLUMN and MagicSquare.SO\n" +
				"	* MagicSquare.LAST_COLUMN and MagicSquare.NE\n" +
				"	* MagicSquare.LAST_COLUMN and MagicSquare.SE\n");
		this.initialEdge = initialEdge;
		this.orientation = orientation;
		customMessage = determineCustomMessage();
	}

	/**The method allows to get the initial edge that caused the exception
	 * @return The invalid initial edge that caused the exception
	 * */
	public String getInitialEdge() {
		return initialEdge;
	}

	/**The method allows to obtain the orientation that caused this exception
	 * @return The invalid orientation that caused this exception
	 * */
	public int[] getOrientation() {
		return orientation;
	}

	/**The method allows to determine the custom message depending on the invalid initial edge and orientation
	 * @return The message with the information about the problem
	 * */
	private String determineCustomMessage() {
		String cmsg = "\n* Incompatible attributes: ";
		switch(initialEdge) {
		case MagicSquare.FIRST_ROW:
			cmsg += "MagicSquare.FIRST_ROW and ";
			break;
		case MagicSquare.LAST_ROW:
			cmsg += "MagicSquare.LAST_ROW and ";
			break;
		case MagicSquare.FIRST_COLUMN:
			cmsg += "MagicSquare.FIRST_COLUMN and ";
			break;
		case MagicSquare.LAST_COLUMN:
			cmsg += "MagicSquare.LAST_COLUMN and ";
			break;
		}
		
		if(orientation[0] == MagicSquare.NO[0] && orientation[1] == MagicSquare.NO[1]) {
			cmsg += "MagicSquare.NO";
		} else if(orientation[0] == MagicSquare.SO[0] && orientation[1] == MagicSquare.SO[1]) {
			cmsg += "MagicSquare.SO";
		} else if(orientation[0] == MagicSquare.NE[0] && orientation[1] == MagicSquare.NE[1]) {
			cmsg += "MagicSquare.NE";
		} else if(orientation[0] == MagicSquare.SE[0] && orientation[1] == MagicSquare.SE[1]) {
			cmsg += "MagicSquare.SE";
		}
		return cmsg;
	}
	
	/**The method allows to get the information message of this exception
	 * */
	@Override
	public String getMessage() {
		String msg = super.getMessage();
		msg += customMessage;
		return msg;
	}
}
