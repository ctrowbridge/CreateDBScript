package com.cindy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Creates a set of scripts to generate SQL files to connect, create and
 * populate a database containing locations.
 * 
 * Data Source:  https://nordpil.com/resources/world-database-of-large-cities/
 * 
 * @author Cindy
 */
public class CreateDBScript {

	private static final String inputFileName = "data\\urbanareas1_1.tsv";
	private static final String dbName = "locations";
	private static final String createSqlFileName = "scripts\\createDB.sql";
	private static final String connectFileName = "scripts\\connectDB.sql";
	private static final String populateFileName = "scripts\\loadDB.sql";

	public CreateDBScript() {
	}

	/**
	 * Generate scripts to connect to database, create the table and populate
	 * the table.
	 */
	public void generateScripts() {
		generateConnectScript();
		generateCreateDbScript();
		generateLoadScript();
	}

	/**
	 * Generate a script to connect to the database. Assumes database is an
	 * imbedded Derby database.
	 */
	private void generateConnectScript() {

		System.out.println("\nGenerate database connection script ...");

		try {
			File outFile = new File(connectFileName);
			FileWriter fWriter = new FileWriter(outFile);
			PrintWriter pWriter = new PrintWriter(fWriter);

			pWriter.println("-- Script to connect to database");
			pWriter.println("CONNECT 'jdbc:derby:" + dbName + ";create=true';");
			pWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Script created \"" + connectFileName + "\"");
	}

	/**
	 * Generate a script to create the locations table.
	 */
	private void generateCreateDbScript() {

		System.out.println("\nGenerate database creation script ...");

		try {
			File outFile = new File(createSqlFileName);
			FileWriter fWriter = new FileWriter(outFile);
			PrintWriter pWriter = new PrintWriter(fWriter);

			pWriter.println("-- Script to create locations database ");

			pWriter.println("DROP TABLE locations;");
			pWriter.println("CREATE TABLE locations");
			pWriter.println("(");
			pWriter.println("    city varchar(200),");
			pWriter.println("    country varchar(100), ");
			pWriter.println("    country_code varchar(5), ");
			pWriter.println("    latitude float, ");
			pWriter.println("    longitude float, ");
			pWriter.println("    population float");
			pWriter.println(");");

			pWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Script created \"" + createSqlFileName + "\"");
	}

	/**
	 * Generate a script to load the database.
	 */
	private void generateLoadScript() {

		System.out.println("\nGenerate database load script ...");

		try {
			File outFile = new File(populateFileName);
			FileWriter fWriter = new FileWriter(outFile);
			PrintWriter pWriter = new PrintWriter(fWriter);

			pWriter.println("-- Script to populate database");

			parseDataFile(inputFileName, pWriter);

			pWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Script created \"" + populateFileName + "\"");
	}

	/**
	 * Parses the input file and generates SQL commands to load the databsae.
	 * 
	 * @param fileName
	 *            Input file name (tab-delimeted file)
	 * @param pWriter
	 *            Pointer to output file
	 */
	private void parseDataFile(String fileName, PrintWriter pWriter) {

		try {
			pWriter.println("INSERT INTO " + dbName + " VALUES");
			File inFile = new File(fileName);
			Scanner sc = new Scanner(inFile);

			String header = sc.nextLine(); // Skip header

			int count = 0;
			while (sc.hasNextLine()) {
				count++;
				String line = sc.nextLine();
				String parts[] = line.split("\t");

				String city = parts[0];
				city = cleanup(city);
				String country = parts[2];
				country = cleanup(country);
				String countryCode = parts[5];
				countryCode = cleanup(countryCode);

				String latStr = parts[3];
				String lonStr = parts[4];
				String populationStr = parts[19];

				pWriter.print(" ('" + city + "', '" + country + "', '" + countryCode + "', " + latStr + ", " + lonStr
						+ ", " + populationStr + ")");
				if (sc.hasNextLine()) {
					pWriter.println(", ");
				} else {
					pWriter.println("");
				}
			}
			System.out.println(" " + count + " cities added");

			pWriter.println(";");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Removes double quotes from string and replaces ' with ''
	 * 
	 * @param name
	 *            Input string (assumed to have " at beginning and end of
	 *            string)
	 * @return Cleaned up string
	 */
	private String cleanup(String name) {
		String cleanName = name.substring(1, name.length() - 1);
		cleanName = cleanName.replace("'", "''");

		return cleanName;
	}

	/**
	 * Main driver
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("<><><><><> CreateDBScript <><><><><>\n");

		CreateDBScript cdbs = new CreateDBScript();
		cdbs.generateScripts();

		System.out.println("Done!");
	}

}
