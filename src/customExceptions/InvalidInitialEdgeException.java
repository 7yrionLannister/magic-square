package customExceptions;


@SuppressWarnings("serial")
public class InvalidInitialEdgeException extends IllegalArgumentException {
	
	private String initialEdge;
	
	/**The method allows to create an instance of this class with the specified invalid initialEdge
	 * @param initialEdge The invalid initial edge that caused this exception
	 * */
	public InvalidInitialEdgeException(String initialEdge) {
		super("Invalid initial edge: " + initialEdge);
		this.initialEdge = initialEdge;
		
	}

	/**The method allows to obtain the initial edge that caused the exception
	 * @return The invalid initial edge that caused the exception
	 * */
	public String getInitialEdge() {
		return initialEdge;
	}
}
