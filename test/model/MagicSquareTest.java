package model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


import org.junit.jupiter.api.Test;

import customExceptions.*;


public class MagicSquareTest {

	/**The magic square to test
	 * */
	private MagicSquare magicSquare;
	/**The matrix to save the generated magic square
	 * */
	private int[][] matrix;

	/**The method assigns null to the magic square and to the matrix to have an empty stage with which to 
	 * test the MagicSquare constructor
	 * */
	private void setupScenary1() {
		magicSquare = null;
		matrix = null;
	}

	/**The method creates a magic square that completely complies with the conditions of the Loubere method
	 * */
	private void setupScenary2() {
		magicSquare = new MagicSquare(3, MagicSquare.LAST_ROW, MagicSquare.SO);
		matrix = null;
	}

	/**The method creates a magic square that does not comply with the conditions of the Loubere method 
	 * */
	private void setupScenary3() {
		magicSquare = new MagicSquare(11, MagicSquare.FIRST_COLUMN, MagicSquare.SE);
		matrix = null;
	}
	
	/**The method creates a magic square that completely complies with the conditions of the Loubere method 
	 * and its corresponding matrix 
	 * */
	private void setupScenary4() {
		magicSquare = new MagicSquare(5, MagicSquare.FIRST_ROW, MagicSquare.NE);
		matrix = magicSquare.generateMagicSquare();
	}
	
	/**The method creates a magic square that completely complies with the conditions of the Loubere method 
	 * and its corresponding matrix 
	 * */
	private void setupScenary5() {
		magicSquare = new MagicSquare(7, MagicSquare.LAST_ROW, MagicSquare.SO);
		matrix = magicSquare.generateMagicSquare();
	}

	/**The method allows to perform different tests to the MagicSquare constructor, considering 
	 * the different situations that may arise and the different types of input that can be 
	 * passed as a parameter
	 * */
	@Test
	public void createMagicSquareTest() {
		//all the parameters given to the constructor are in the domain
		setupScenary1();
		int order = 13; //Valid order
		String initialEdge = MagicSquare.FIRST_COLUMN; //Valid initialEdge
		int[] orientation = MagicSquare.SE.clone();//Valid orientation
		magicSquare = new MagicSquare(order, initialEdge, orientation);
		assertTrue("The order does not correspond to the one entered by parameter",
				order == magicSquare.getOrder());
		assertTrue("The initialEdge does not correspond to the one entered by parameter", 
				initialEdge.equals(magicSquare.getInitialEdge()));
		assertNotNull("The orientation must be initialized", magicSquare.getOrientation());
		assertTrue("The orientation does not correspond to the one entered by parameter", 
				orientation[0] == magicSquare.getOrientation()[0] && orientation[1] == magicSquare.getOrientation()[1]);
		
		//in this case the order is invalid
		setupScenary1();
		try {
			new MagicSquare(-2, MagicSquare.FIRST_COLUMN, MagicSquare.NE);
		}
		catch(OutOfRangeOrderException|InvalidInitialEdgeException|InvalidOrientationException iae) {
			assertTrue(true);
		}

		//in this case the initialEdge is invalid
		setupScenary1();
		try {
			new MagicSquare(3, "valar morghulis", MagicSquare.NO);
		}
		catch(OutOfRangeOrderException|InvalidInitialEdgeException|InvalidOrientationException iae) {
			assertTrue(true);
		}

		//in this case the orientation is invalid
		setupScenary1();
		try {
			new MagicSquare(5, MagicSquare.LAST_COLUMN, new int[]{-365, 1999});
		}
		catch(OutOfRangeOrderException|InvalidInitialEdgeException|InvalidOrientationException iae) {
			assertTrue(true);
		}

		//in this case everything is invalid
		setupScenary1();
		try {
			new MagicSquare(-86, "valar dohaeris", new int[]{5, 5});
		}
		catch(OutOfRangeOrderException|InvalidInitialEdgeException|InvalidOrientationException iae) {
			assertTrue(true);
		}
	}

	
	/**The method allows testing the functionality of generating magic square in the MagicSquare class<br>
	 * The generateMagiSquare method returns an array that represents the magic square as long as its 
	 * attributes meet the requirements of Loubere's method
	 * */
	@Test
	public void generateMagicSquareTest() {
		setupScenary2();
		try {
			matrix = magicSquare.generateMagicSquare();
			assertTrue("The generated matrix is not a square", matrix.length == matrix[0].length);
			assertTrue("The order of the generated matrix is different from the order of the magic square",
					matrix.length == magicSquare.getOrder());
		}
		catch(LoubereMethodNotSatisfiedException lmnse) {
			fail("The magic square matrix should have been generated without problems");
		}

		setupScenary3();
		try {
			matrix = magicSquare.generateMagicSquare();
			fail("The magic square matrix should not have been generated as this magic quare is not a valid one");
		}
		catch(LoubereMethodNotSatisfiedException lmnse) {
			assertTrue(true);
		}
	}

	/**The method allows to verify that the sum of each row, column and diagonal of 
	 * the generated matrix is equal to the number obtained by the getMagicalSum method
	 * */
	@Test
	public void getMagicalSumTest() {
		setupScenary4();
		int diagonal1 = 0;
		int diagonal2 = 0;
		int row = 0;
		int column = 0;
		int sum = magicSquare.getMagicalSum();
		for(int i = 0; i < matrix.length; i++) {
			row = 0;
			column = 0;
			for(int j = 0; j < matrix[i].length; j++) { 
				if(i==j) {
					diagonal1 += matrix[i][j];
				}
				if(i+j == matrix.length-1) {
					diagonal2 += matrix[i][j];
				}
				row += matrix[i][j];
				column += matrix[j][i];
			}
			assertTrue("The sum of row " + i + " is not " + sum, row == sum);
			assertTrue("The sum of column " + i + " is not " + sum, column == sum);
		}
		assertTrue("The sum of diagonal1 is not " + sum, diagonal1 == sum);
		assertTrue("The sum of diagonal2 is not " + sum, diagonal2 == sum);
		
		setupScenary5();
		diagonal1 = 0;
		diagonal2 = 0;
		sum = magicSquare.getMagicalSum();
		for(int i = 0; i < matrix.length; i++) {
			row = 0;
			column = 0;
			for(int j = 0; j < matrix[i].length; j++) { 
				if(i==j) {
					diagonal1 += matrix[i][j];
				}
				if(i+j == matrix.length-1) {
					diagonal2 += matrix[i][j];
				}
				row += matrix[i][j];
				column += matrix[j][i];
			}
			assertTrue("The sum of row " + i + " is not " + sum, row == sum);
			assertTrue("The sum of column " + i + " is not " + sum, column == sum);
		}
		assertTrue("The sum of diagonal1 is not " + sum, diagonal1 == sum);
		assertTrue("The sum of diagonal2 is not " + sum, diagonal2 == sum);
	}
}
