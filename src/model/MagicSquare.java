package model;

import customExceptions.*;


public class MagicSquare {

	/**This constant is a vector that represents the northwest direction of filling the magic square<br>
	 * Defines the domain of the "orientation" attribute
	 * */
	public final static int[] NO = {-1,-1};
	/**This constant is a vector that represents the southwest direction of filling the magic square<br>
	 * Defines the domain of the "orientation" attribute
	 * */
	public final static int[] SO = {1,-1};
	/**This constant is a vector that represents the northeast direction of filling the magic square<br>
	 * Defines the domain of the "orientation" attribute
	 * */
	public final static int[] NE = {-1,1};
	/**This constant is a vector that represents the southeast direction of filling the magic square<br>
	 * Defines the domain of the "orientation" attribute
	 * */
	public final static int[] SE = {1,1};
	/**This constant is a vector that represents the relative movement towards the center for the magic squares that begin to fill in the last column
	 * */
	public final static int[] LEFT = {0,-1};
	/**This constant is a vector that represents the relative movement towards the center for the magic squares that begin to fill in the first column
	 * */
	public final static int[] RIGHT = {0,1};
	/**This constant is a vector that represents the relative movement towards the center for the magic squares that begin to fill in the last row
	 * */
	public final static int[] UP = {-1,0};
	/**This constant is a vector that represents the relative movement towards the center for the magic squares that begin to fill in the first row
	 * */
	public final static int[] DOWN = {1,0};

	/**This constant indicates the initial filling position in the first row<br>
	 * Defines the domain of the "initialEdge" attribute
	 * */
	public final static String FIRST_ROW = "first row";
	/**This constant indicates the initial position of filling in the last row<br>
	 * Defines the domain of the "initialEdge" attribute
	 * */
	public final static String LAST_ROW = "last row";
	/**This constant indicating the initial filling position in the first column<br>
	 * Defines the domain of the "initialEdge" attribute
	 * */
	public final static String FIRST_COLUMN = "first column";
	/**This constant indicates the initial filling position in the last column<br>
	 * Defines the domain of the "initialEdge" attribute
	 * */
	public final static String LAST_COLUMN = "last column";

	/**This attribute indicates the order of the magic square to generate
	 * */
	private int order;
	/**This attribute indicates the position at where the magic square begins to fill
	 * */
	private String initialEdge;
	/**This attribute indicates the direction in which the matrix will be filled
	 * */
	private int[] orientation; 

	/**The constructor allows to obtain an instance of an odd order MagicSquare
	 * @param order The order of the magic square to generate<br>
	 * 	order%2 != 0 && order > 0
	 * @param initialEdge The position at where the matrix begins to fill<br>
	 * 	This parameter should be <b>FIRS_ROW, LAST_ROW, FIRST_COLUMN</b> or <b>LAST_COLUMN</b>
	 * @param orientation The direction in which the matrix will be filled<br>
	 * 	This parameter should be <b>NO, SO, NE</b> or <b>SE</b>
	 * @throws OutOfRangeOrderException if the order does not meet the above conditions
	 * @throws InvalidInitialEdgeException if the initial edge is not one of the valid values described above
	 * @throws InvalidOrientationException if orientation is not one of the valid values described above 
	 * */
	public MagicSquare(int order, String initialEdge, int[] orientation) throws OutOfRangeOrderException, InvalidInitialEdgeException, InvalidOrientationException {
		setOrder(order);
		setInitialEdge(initialEdge);
		setOrientation(orientation);
	}

	/**The method allows to obtain the magic square order
	 * @return The order of the magic square
	 * */
	public int getOrder() {
		return order;
	}

	/**The method allows to modify the magic square order
	 * @param order The new order
	 * 	order%2 != 0 && order > 0
	 * @throws OutOfRangeOrderException if the order does not meet the above conditions
	 * */
	public void setOrder(int order) throws OutOfRangeOrderException {
		if(order%2 != 0 && order > 0) {
			this.order = order;
		}
		else {
			throw new OutOfRangeOrderException(order);
		}
	}

	/**The method allows to obtain the magic square initial filling position
	 * @return The magic square initial filling position
	 * */
	public String getInitialEdge() {
		return initialEdge;
	}

	/**The method allows to modify the magic square initial filling position
	 * @param initialEdge The new magic square initial filling position<br>
	 * 	This parameter should be <b>FIRS_ROW, LAST_ROW, FIRST_COLUMN</b> or <b>LAST_COLUMN</b>
	 * @throws InvalidInitialEdgeException if the initial edge is not one of the valid values described above
	 * */
	public void setInitialEdge(String initialEdge) throws InvalidInitialEdgeException {
		if(initialEdge.equals(FIRST_ROW) || initialEdge.equals(LAST_ROW) || initialEdge.equals(FIRST_COLUMN) || initialEdge.equals(LAST_COLUMN)) {
			this.initialEdge = initialEdge;
		}
		else {
			throw new InvalidInitialEdgeException(initialEdge);
		}
	}

	/**The method allows to obtain the direction in which the magic square is filled
	 * @return A vector that indicates the direction in which the magic square is filled
	 * */
	public int[] getOrientation() {
		return orientation;
	}
	
	/**The method allows to modify the direction in which the magic square will be filled
	 * @param orientation The new orientation of filling
	 * 	This parameter should be <b>NO, SO, NE</b> or <b>SE</b>
	 * @throws InvalidOrientationException if orientation is not one of the valid values described above 
	 * */
	public void setOrientation(int[] orientation) throws InvalidOrientationException {
		if(orientation[0] != NO[0] && orientation[1] != NO[1] && 
				orientation[0] != SE[0] && orientation[1] != SE[1] && 
				orientation[0] != NE[0] && orientation[1] != NE[1] && 
				orientation[0] != SO[0] && orientation[1] != SO[1]) {
			throw new InvalidOrientationException(orientation);
		}
		else {
			this.orientation = orientation;
		}
	}

	/**The method allows to obtain the magical sum of this magic square<br>
	 * It is the number that adds each row, column and diagonal of the magic square
	 * @return The magical sum of this magic square based on its order
	 * */
	public int getMagicalSum() {
		int sum = (order*order*order + order)/2;
		return sum;
	}

	/**The method allows to generate a magic square according to this MagicSquare attributes
	 * @return A matrix of integers that represents this magic square
	 * @throws LoubereMethodNotSatisfiedException if both initialEdge and orientation don't 
	 * make the indexes to get out of the matrix in the first filling move
	 * */
	public int[][] generateMagicSquare() throws LoubereMethodNotSatisfiedException {
		int[] goCenter = new int[2];
		int matrix[][] = new int[order][order];
		int row = -1;
		int column = -1;
		if(initialEdge == FIRST_ROW) {
			goCenter = DOWN;
			row = 0;
			column = order/2;
		} else if(initialEdge.equals(LAST_ROW)) {
			goCenter = UP;
			row = order-1;
			column = order/2;
		} else if(initialEdge.equals(FIRST_COLUMN)) {
			goCenter = RIGHT;
			row = order/2;
			column = 0;
		} else if(initialEdge.equals(LAST_COLUMN)) {
			goCenter = LEFT;
			row = order/2;
			column = order-1;
		}
		matrix[row][column] = 1;
		try {
			for(int i = 1; i < order*order; i++) {
				row += orientation[0];
				column += orientation[1];
				row = correctIndex(row);
				column = correctIndex(column);
				if(matrix[row][column] != 0) {
					row -= orientation[0];
					column -= orientation[1];
					row = correctIndex(row);
					column = correctIndex(column);
					row += goCenter[0];
					column += goCenter[1];
				}
				matrix[row][column] = i+1;
			}
		}
		catch(ArrayIndexOutOfBoundsException aioobe) {
			throw new LoubereMethodNotSatisfiedException(initialEdge, orientation);
		}
		return matrix;
	}

	/**The method receives an integer as a parameter that represents the current position of the row 
	 * or column and, if it is outside the limits of the matrix, returns the position in 
	 * which it should be
	 * @param index The current row or column index
	 * @return The position where the index should be
	 * */
	private int correctIndex(int index) {
		if(index == -1) {
			index = order-1;
		}
		else if(index == order) {
			index = 0;
		}
		return index;
	}
}
