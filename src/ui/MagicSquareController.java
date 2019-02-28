package ui;

import customExceptions.OutOfRangeOrderException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import model.MagicSquare;


public class MagicSquareController {

	/**This is the ToggleGroup that contains the four RadioButtons for selecting the initial edge of the magic square
	 * */
	@FXML private ToggleGroup edgeToggleGroup;
	/**This is the RadioButton that allows to choose the first row as initial edge of the magic square
	 * */
	@FXML private RadioButton firstRow;
	/**This is the RadioButton that allows to choose the first column as initial edge of the magic square
	 * */
	@FXML private RadioButton firstColumn;
	/**This is the RadioButton that allows to choose the last row as initial edge of the magic square
	 * */
	@FXML private RadioButton lastRow;
	/**This is the RadioButton that allows to choose the last column as initial edge of the magic square
	 * */
	@FXML private RadioButton lastColumn;

	/**This is the ToggleGroup that contains the four RadioButtons for selecting the orientation of the magic square
	 * */
	@FXML private ToggleGroup dirToggleGroup;
	/**This is the RadioButton that allows to choose the magic square northwest orientation
	 * */
	@FXML private RadioButton no;
	/**This is the RadioButton that allows to choose the magic square northeast orientation
	 * */
	@FXML private RadioButton ne;
	/**This is the RadioButton that allows to choose the magic square southwest orientation
	 * */
	@FXML private RadioButton so;
	/**This is the RadioButton that allows to choose the magic square southeast orientation
	 * */
	@FXML private RadioButton se;

	/**The TextField where the user inputs the magic square order
	 * */
	@FXML private TextField orderTextField;
	/**The Label that we use to inform the user of an invalid entry or the order of the magic square
	 * */
	@FXML private Label messageLabel;
	/**The ScrollPane where the matrix will be contained
	 * */
	@FXML private ScrollPane viewPane;
	/**The matrix that will represent the magic square
	 * */
	@FXML private GridPane matrix;
	/**The Button used to generate a magic square and show it in the interface
	 * */
	@FXML private Button generateButton;
	/**Label to show the magic constant
	 * */
	@FXML private Label magicSumColumn;
	/**Label to show the magic constant
	 * */
	@FXML private Label magicSumRow;

	/**The magic square to fill
	 * */
	private MagicSquare magicSquare;

	/**This method is called automatically when the FMXLLoader finishes loading everything so we can access
	 * the user interface components, add information about what every RadioButton has to do and set the
	 * style of the labels for the magic constants
	 * */
	@FXML
	public void initialize() {
		firstRow.setUserData(MagicSquare.FIRST_ROW);
		firstColumn.setUserData(MagicSquare.FIRST_COLUMN);
		lastRow.setUserData(MagicSquare.LAST_ROW);
		lastColumn.setUserData(MagicSquare.LAST_COLUMN);

		no.setUserData(MagicSquare.NO);
		ne.setUserData(MagicSquare.NE);
		so.setUserData(MagicSquare.SO);
		se.setUserData(MagicSquare.SE);

		matrix = new GridPane();
		viewPane.setContent(matrix);
		magicSumColumn = new Label();
		magicSumColumn.setMinWidth(30);
		magicSumColumn.setStyle("-fx-background-color: Red");
		magicSumColumn.setTextFill(Color.web("#FFFFFF"));
		magicSumColumn.setFont(messageLabel.getFont());
		magicSumRow = new Label();
		magicSumRow.setMinWidth(30);
		magicSumRow.setStyle("-fx-background-color: Red");
		magicSumRow.setTextFill(Color.web("#FFFFFF"));
		magicSumRow.setFont(messageLabel.getFont());
		generateButton.setDisable(true);
	}

	/**The method is the event handler for the RadioButtons that belong to edgeToggleGroup<br>
	 * It disables the RadioButtons in dirToggleGroup that can not be combined with this initial edge selection
	 * @param event The event that is caught when an initial edge is selected
	 * */
	@FXML
	public void edgeSelected(ActionEvent event) {
		if(dirToggleGroup.getSelectedToggle() != null) {
			dirToggleGroup.getSelectedToggle().setSelected(false);
		}

		switch((String)edgeToggleGroup.getSelectedToggle().getUserData()) {
		case MagicSquare.FIRST_ROW:
			no.setDisable(false);
			ne.setDisable(false);
			so.setDisable(true);
			se.setDisable(true);
			break;
		case MagicSquare.FIRST_COLUMN:
			no.setDisable(false);
			ne.setDisable(true);
			so.setDisable(false);
			se.setDisable(true);
			break;	
		case MagicSquare.LAST_ROW:
			no.setDisable(true);
			ne.setDisable(true);
			so.setDisable(false);
			se.setDisable(false);
			break;	
		case MagicSquare.LAST_COLUMN:
			no.setDisable(true);
			ne.setDisable(false);
			so.setDisable(true);
			se.setDisable(false);
			break;	
		}

		generateButton.setDisable(true);
	}

	/**The method is the event handler for the RadioButtons that belong to dirToggleGroup<br>
	 * It enables the geneateButton to be pressed if and only if one initial edge and one orientation are selected
	 * @param event The event that is caught when an orientation is selected
	 * */
	@FXML
	public void dirSelected(ActionEvent event) {
		if(edgeToggleGroup.getSelectedToggle() != null && dirToggleGroup.getSelectedToggle() != null) {
			generateButton.setDisable(false);
		}
		else {
			generateButton.setDisable(true);
		}
	}

	/**The method is the event handler for generateButton<br>
	 * It allows to remove all the elements of the matrix, generates the magic square, fills 
	 * the matrix with labels that contain the corresponding information of the magic square on each field 
	 * and assigns them an event handler
	 * @param event The event that is caught when the generateButton is pressed
	 * */
	@FXML
	public void generateButtonPressed(ActionEvent event) {
		matrix.getChildren().clear();
		if(!orderTextField.getText().isEmpty()) {
			try {
				magicSquare = new MagicSquare(Integer.parseInt(orderTextField.getText()),
						(String)edgeToggleGroup.getSelectedToggle().getUserData(), 
						(int[])dirToggleGroup.getSelectedToggle().getUserData());
				messageLabel.setText("The magical constant is " + magicSquare.getMagicalSum());
				int mtrx[][] = magicSquare.generateMagicSquare();
				Label box;
				int evenOrOdd = -1;
				for(int i = 0; i < mtrx.length; i++) {
					for(int j = 0; j < magicSquare.getOrder(); j++) {
						evenOrOdd += 1;
						box = new Label(""+mtrx[i][j]);
						box.addEventHandler(MouseEvent.MOUSE_CLICKED, new OnBoxSelected());
						box.setMinWidth(30);
						if(evenOrOdd%2 == 0) {
							box.setStyle("-fx-background-color: White");
						}
						else {
							box.setStyle("-fx-background-color: BurlyWood");
						}
						matrix.add(box, j, i);
					}
				}
			}
			catch(OutOfRangeOrderException oore) {
				messageLabel.setText("The order is not in the domain(positive odd numbers)");
			}
			catch(NumberFormatException nfe) {
				messageLabel.setText("The order should be an integer");
			}
		}
		else {
			messageLabel.setText("You must specify an order");
		}
	}

	public class OnBoxSelected implements EventHandler<MouseEvent>{
		/**The method is the event handler for the matrix labels<br>
		 * It allows to highlight their row and column when they are selected
		 * @param e The event caught when a label is clicked
		 * */
		@Override
		public void handle(MouseEvent e) {
			int node = -1;
			matrix.getChildren().remove(magicSumColumn);
			matrix.getChildren().remove(magicSumRow);
			Label a;
			Label b = (Label) e.getSource();
			int bRow = GridPane.getRowIndex(b);
			int bColumn = GridPane.getColumnIndex(b);
			for(int k = 0; k < Math.sqrt(matrix.getChildren().size()); k++) {
				for(int l = 0; l < Math.sqrt(matrix.getChildren().size()); l++) {
					node += 1;
					a = (Label) matrix.getChildren().get(node);
					if(node%2 == 0) {
						a.setStyle("-fx-background-color: White");
					}
					else {
						a.setStyle("-fx-background-color: BurlyWood");
					}
					if(GridPane.getRowIndex(a) == bRow || GridPane.getColumnIndex(a) == bColumn) {
						a.setStyle("-fx-background-color: CadetBlue");
					}
				}
			}
			magicSumColumn.setText(magicSquare.getMagicalSum()+"");
			magicSumRow.setText(magicSquare.getMagicalSum()+"");
			matrix.addRow(bRow, magicSumColumn);
			matrix.addColumn(bColumn, magicSumRow);
		}
	}
}
