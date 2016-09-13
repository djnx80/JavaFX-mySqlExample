package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MyDataBase {
	// dane do po³¹czenia
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost/peopledatabase";
	private static final String USER = "root";
	private static final String PASS = "test123test";
	private static Connection polaczenieZBaza = null;
	private static Statement bazaDanych = null;


	// open database and create statement
	public MyDataBase() {
		try {
			Class.forName(JDBC_DRIVER); // rejestruj sterownik i po³¹cz siê z baz¹
			polaczenieZBaza = DriverManager.getConnection(DB_URL, USER, PASS);
			bazaDanych = polaczenieZBaza.createStatement();
		} catch (SQLException se) {
			System.out.println("B³êdy od JDBC");
			se.printStackTrace();
		} catch (Exception e) {
			System.out.println("B³êdy od Class.forName");
			e.printStackTrace();
		}
	}
	
	// get how many rows in table
	public int howManyRows()	{
		String sqlQuery = "SELECT COUNT(*) FROM people";
		try {
			ResultSet rows = bazaDanych.executeQuery(sqlQuery);
			rows.next();
//			System.out.println(count);
			return rows.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	// close the database and connection
	public void CloseMyDataBase() {
		try {
			bazaDanych.close(); // zamknij bazê
		} catch (Exception e) {
			System.out.println("B³¹d przy zamykaniu bazy");
		}
		try {
			polaczenieZBaza.close(); // zamknij po³¹czenie
		} catch (Exception e) {
			System.out.println("B³¹d przy zamykaiu po³¹czenia");
		}
	}
	
	public void sortTable()	{
		String sqlQuery = "SELECT * FROM people ORDER BY name";
		try {
	//		bazaDanych.executeup
			System.out.println("posortowane");
			bazaDanych.executeQuery(sqlQuery);
		} catch (SQLException e) {
			System.out.println("Nie uda³o siê posortowaæ");
		}
	}
	
	public void updateRecord(int ID, String recordName, String newValue)	{
		String sqlQuery = "UPDATE people SET " + recordName + "=\"" + newValue +"\" WHERE idpeople="+Integer.toString(ID);
		System.out.println(sqlQuery);
		try {
			bazaDanych.executeUpdate(sqlQuery);
		} catch (SQLException e) {
			System.out.println("Nie uda³o siê wpisaæ rekordu");
		}
	}

	// read the column by row in table
	public ResultSet readByID(int id, String sortBy) {
		try {
			// SELECT * FROM people LIMIT 6,1
			String zapytaj = "SELECT * FROM people ORDER BY " + sortBy +" LIMIT " + Integer.toString(id) + ",1";
			ResultSet wynikZapytania = bazaDanych.executeQuery(zapytaj);
			return wynikZapytania;
		} catch (Exception e) { // nie uda³o siê pobraæ danych
			System.out.println("b³¹d przy odczycie po ID"); 
			return null;
		}
	}
	
	// remove record from table
	public void deleteRecord(int dataBaseID)	{
		String sqlQuery = "DELETE FROM people WHERE idpeople="+Integer.toString(dataBaseID);
		try {
			bazaDanych.executeUpdate(sqlQuery);
		} catch (SQLException e) {
			System.out.println("Nie uda³o siê usun¹æ rekordu");
		}		
	}
	// create new record in table
	public void newRecord()	{
		String sqlQuery = "INSERT INTO people (name, surname, age, email, phone, info) VALUES (null, null, null, null, null, null)";
		try {
			bazaDanych.executeUpdate(sqlQuery);
		} catch (SQLException e) {
			System.out.println("Nie uda³o siê dodaæ rekordu");
		}
	}
	
	// get the results from the column to string array
	public int readRecordsByID(ResultSet wyniki, String[] cells)	{		// wyniki to musza byc stringi
		int dbID = -1;
		try	{
		while (wyniki.next()) {
			// odczytaj wartoœci z kolumn
			dbID = wyniki.getInt("idpeople");
			cells[0] = wyniki.getString("name");
			cells[1] = wyniki.getString("surname");
			cells[2] = wyniki.getString("age");
			cells[3] = wyniki.getString("email");
			cells[4] = wyniki.getString("phone");
			cells[5] = wyniki.getString("info");
		}
			return dbID;
		} catch (Exception e)
		{
			System.out.println("b³¹d przy odczycie"); 
			return -1;
		}
	}
}
