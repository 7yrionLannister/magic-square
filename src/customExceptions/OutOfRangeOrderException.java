package customExceptions;


@SuppressWarnings("serial")
public class OutOfRangeOrderException extends IllegalArgumentException {

	/**This constant indicates when the order is out of range due to a negative value
	 * */
	public static final String NEGATIVE = "negative";
	/**This constant indicates when the order is out of range due to an even value
	 * */
	public static final String EVEN = "even";
	/**This attribute represents the invalid order passed by parameter
	 * */
	private int order;
	/**This is the information message to concatenate with that of the superclass
	 * */
	private String customMessage;

	/**The method allows to create an instance of this class with the specified invalid order
	 * @param order The order found out of domain
	 * */
	public OutOfRangeOrderException(int order) {
		super("The order is not in the domain(positive odd numbers) ");
		this.order = order;
		customMessage = ": The order is " + determineTypeOfIllegality();
	}
	
	/**The method allows to obtain the order
	 * @return The value of the order
	 * */
	public int getOrder() {
		return order;
	}
	
	/**The method allows to determine the custom message of this exception depending on the order
	 * @return A String containing the custom message
	 * */
	private String determineTypeOfIllegality() {
		String illegality = "";
		if(order%2 == 0) {
			illegality = EVEN;
		}
		if(illegality.isEmpty()) {
			illegality = NEGATIVE;
		}
		else {
			illegality += " and " + NEGATIVE;
		}
		return illegality;
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
