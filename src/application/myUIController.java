package application;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class myUIController implements Initializable {

	@FXML
	private BorderPane mainBorderPane;
	
	// text fields
	public TextField tfName;
	public TextField tfSurname;
	public TextField tfAge;
	public TextField tfEmail;
	public TextField tfPhone;
	public TextArea taInfo; 
	
	// buttons
	public Button bFirst;
	public Button bLast;
	public Button bPrevious;
	public Button bNext;
	public Label lID;
	public ComboBox<String> comboSort;
	
	// data base
	private MyDataBase myDB;
	private ResultSet results;		// a row
	private  String records[] = new String[6];	// cells
	private int actualID;
	private int dataBaseID;
	private int itemsInDB;
	private String sortBy;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		comboSort.getItems().addAll("ID", "Name", "Surname", "Age", "Email","Phone", "Info");
		myDB = new MyDataBase();
		actualID = 0;
		dataBaseID = 1;
		itemsInDB = myDB.howManyRows();
		sortBy = "idpeople";
		updateAll();
	}
	
	// first id
	public void firstID()	{
		actualID = 0;
		updateAll();
	}
	// last id
	public void lastID()	{
		actualID = itemsInDB - 1;
		updateAll();
	}
	// previous id
	public void prevID()	{
		if (actualID > 0)	{
			actualID--;
			updateAll();
		}
	}
	// next id
	public void nextID()	{
		if (actualID < itemsInDB - 1)	{
			actualID++;
			updateAll();
		}
	}
	// update record and text fields
	private void updateAll() {
		results = myDB.readByID(actualID, sortBy);
		dataBaseID = myDB.readRecordsByID(results, records);
		System.out.println(dataBaseID);
		setFields();
		lID.setText(Integer.toString(actualID));
		
	}

	// update the records when enter pressed.
	public void changeName()	{
		myDB.updateRecord(dataBaseID, "name", tfName.getText());
	}
	public void changeSurname()	{
		myDB.updateRecord(dataBaseID, "surname", tfSurname.getText());
	}
	public void changeAge()	{
		myDB.updateRecord(dataBaseID, "age", tfAge.getText());
	}
	public void changeEmail()	{
		myDB.updateRecord(dataBaseID, "email", tfEmail.getText());
	}
	public void changePhone()	{
		myDB.updateRecord(dataBaseID, "phone", tfPhone.getText());
	}
	public void changeInfo()	{
		myDB.updateRecord(dataBaseID, "info", taInfo.getText());
	}
	
	// set text fields with values taken from data base
	public void setFields()	{
		tfName.setText(records[0]);
		tfSurname.setText(records[1]);
		tfAge.setText(records[2]);
		tfEmail.setText(records[3]);
		tfPhone.setText(records[4]);
		taInfo.setText(records[5]);
	}
	
	// add new record to the table
	public void addNewRecord()	{
		// clear text fields
		tfName.setText("");
		tfSurname.setText("");
		tfAge.setText("");
		tfEmail.setText("");
		tfPhone.setText("");
		taInfo.setText("");
		
		myDB.newRecord();
		itemsInDB = myDB.howManyRows();
		actualID = itemsInDB-1;
		sortBy = "idpeople";
		updateAll();
	}
	
	// remove record
	public void removeRecord()	{
		myDB.deleteRecord(dataBaseID);
		itemsInDB = myDB.howManyRows();
		// if it was the last one then decrease by 1
		if (actualID > itemsInDB-1)
			actualID--;
		updateAll();
	}
	
	// what was choosen from ComboBox
	public void comboBoxChoice()	{
		System.out.println(comboSort.getValue());
		String value = comboSort.getValue();
		if (value.equals("ID"))	{
			sortBy = "idpeople";
			updateAll();
		} else if (value.equals("Name"))	{
			sortBy = "name";
			updateAll();
		} else if (value.equals("Surname"))	{
			sortBy = "surname";
			updateAll();
		} else if (value.equals("Age"))	{
			sortBy = "age";
			updateAll();
		} else if (value.equals("Email"))	{
			sortBy = "email";
			updateAll();
		} else if (value.equals("Phone"))	{
			sortBy = "phone";
			updateAll();
		} else if (value.equals("Info"))	{
			sortBy = "info";
			updateAll();
		}
	}
	
	// exit the program
	public void quitProgram()	{

		
	}
}
