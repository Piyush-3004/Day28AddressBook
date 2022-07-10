package com.blzAsg;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.opencsv.CSVWriter;

public class AddressBook {

	String bookName;
	ArrayList<Contact> contactDetail = new ArrayList<Contact>();
	// static ArrayList<Contact> contactDetails = new ArrayList<Contact>();
	static HashMap<String, ArrayList<Contact>> system = new HashMap();
	static HashMap<String, ArrayList<Contact>> cityDict = new HashMap();
	static HashMap<String, ArrayList<Contact>> stateDict = new HashMap();

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		addContact();

		int op = 1;
		while (op != 0) {
			System.out.println("enter 0 to break , 1 to proceed");
			op = scanner.nextInt();
			if (op == 0)
				break;
			System.out.println(
					"Enter 0 to Exit,1 to add new book & contact ,2 to view ,3 to edit ,4 to delete,5 to search by city,6 to get count, "
							+ "\n 7 to sort on name, 8 to sort on city/statr/zipCode,9 to add Book to file,11 to add to json");
			int ch = scanner.nextInt();
			switch (ch) {
			case 1:
				addNewBook();
				break;
			case 2:
				viewContact();
				break;
			case 3:
				editContact();
				break;
			case 4:
				AddressBook.deleteContact();
				break;
			case 5:
				searchByCity();
				break;
			case 6:
				showCountByState();
				break;
			case 7:
				sortByName();
				break;
			case 8:
				sortByCityStateZip();
				break;
			case 9:
				try {
					addBookToFile();
				} catch (IOException e) {
					System.out.println("not fount" + e);
				}
				break;
			case 10:
//				addToCsv();
			case 11:
				try {
					addToJson();
				} catch (IOException e) {
					System.out.println("not fount" + e);
				}
				break;
			}
		}
	}

	////////////////// Uc 15 write to json //////////////////

	public static void addToJson() throws IOException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter 1 to write to file , 2 read from file");
		int ch = scanner.nextInt();
		if (ch == 1) {

			JSONObject contactObject = new JSONObject();

			Set<String> bookNames = system.keySet();
			contactObject.put("Address Book Title : ", system.toString());
			try (FileWriter file = new FileWriter(
					"C:\\Users\\cheti\\eclipse-workspace\\Day28AdBkjson\\addrbook.json")) {

				file.write(contactObject.toJSONString());

			} catch (IOException e) {
				System.out.println("could not connect " + e);
			}
		}
		if (ch == 2) {

			try {
				JSONParser jsonParser = new JSONParser();
				Object obj = jsonParser
						.parse(new FileReader("C:\\Users\\cheti\\eclipse-workspace\\Day28AdBkjson\\addrbook.json"));

				JSONObject jsonObject = (JSONObject) obj;
				Object jArr = (String) jsonObject.get("Address Book Title : ".toString());
				System.out.println(jArr);

			} catch (IOException e) {
				System.out.println("could not connect " + e);
			} catch (ParseException e) {
				System.out.println(e);
			}
		}
	}

	/////////////////// Uc 14 write to CSV //////////////////
	// NOT WORKING CANNOT CONVERT OBJECT ARRAY TO STRING ARRAY
	public static void addToCsv() throws IOException {
		try (FileWriter writeCsv = new FileWriter(
				"C:\\Users\\cheti\\eclipse-workspace\\Day28AdBkjson\\addrbookcsv.csv");) {

			CSVWriter writerObj = new CSVWriter(writeCsv);
			List<String[]> data = new ArrayList<String[]>();
			// String[] strArr =Arrays.copyOf(system.get(0).size(),String[].class);

			
			Set<String> bookNames = system.keySet();
//			data.add(bookNames.toArray());
			for (String book : bookNames) {
				for (int i = 0; i < system.get(book).size(); i++) {

					// writerObj.write(system.get(book).stream().forEach(x->String.valueOf(x.first_Name)));
				}
			}
			writerObj.writeAll(data);
		} catch (IOException e) {
			System.out.println("could not connect");
		}

	}

	////////////////// Uc 13 add Contact Boot to File ///////////////////

	public static void addBookToFile() throws IOException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("enter  to ad to file, 2 to read from file");
		int ch = scanner.nextInt();

		if (ch == 1) {

			Path fn = Paths.get("C:\\Users\\cheti\\eclipse-workspace\\Day28AdBkjson\\addbook.txt");

			Files.writeString(fn, system.toString());
		}
		if (ch == 2) {
			FileReader fr = new FileReader("C:\\Users\\cheti\\eclipse-workspace\\Day28AdBkjson\\addbook.txt");
			int i;
			while ((i = fr.read()) != -1) {
				System.out.print((char) i);
			}
			System.out.println();
		}
	}

	///////////////////// Uc12 sort by name using city/state/zip
	///////////////////// //////////////////////

	public static void sortByCityStateZip() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("enter book name");
		String bn = scanner.next();
		System.out.println("enter 1 to sort on City ,2 to Sort on State, 3 to sort on Zip");
		int c = scanner.nextInt();
		switch (c) {
		case 1:
			List srtCity = system.get(bn).stream().sorted((o1, o2) -> o1.city.compareTo(o2.city.toString()))
					.collect(Collectors.toList());
			System.out.println(srtCity);
			break;
		case 2:
			List srtState = system.get(bn).stream().sorted((o1, o2) -> o1.state.compareTo(o2.state.toString()))
					.collect(Collectors.toList());
			System.out.println(srtState);
			break;
		case 3:
			List srtZip = system.get(bn).stream()
					.sorted((o1, o2) -> (Integer.valueOf(o1.zip_Code)).compareTo(Integer.valueOf(o2.zip_Code)))
					.collect(Collectors.toList());
			System.out.println(srtZip);
			break;
		}

		List srtd = system.get(bn).stream().sorted((o1, o2) -> o1.first_Name.compareTo(o2.first_Name.toString()))
				.collect(Collectors.toList());

		System.out.println(srtd);
	}

	///////////////////// Uc11 sort book on firstname ///////////////////////////

	public static void sortByName() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("enter book name");
		String bn = scanner.next();

		List srtd = system.get(bn).stream().sorted((o1, o2) -> o1.first_Name.compareTo(o2.first_Name.toString()))
				.collect(Collectors.toList());

		System.out.println(srtd);
	}

	////////////// Uc 10 get count of city column /////////
	public static void countByCityorstate() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter city name");
		String stateOrCity = scanner.next();

		Set<String> bookNames = system.keySet();
		for (String book : bookNames) {
			for (int i = 0; i < system.get(book).size(); i++) {

				List personsInCItyOrState = system.get(book).stream()
						.filter(x -> x.city.equals(stateOrCity) || x.state.equals(stateOrCity))
						.collect(Collectors.toList());

				System.out.println(personsInCItyOrState.size());
			}
		}
	}

	////////////// Uc 9 Maintain dictionary of city to person and state to person
	////////////// ////////////////////////////
	static void showCountByState() {
		int count = 0;

		Set<String> bookNames = system.keySet();

		for (String book : bookNames) {
			for (int i = 0; i < system.get(book).size(); i++) {

				List cities = system.get(book).stream().map(x -> x.city).collect(Collectors.toList());
				List citiesv = system.get(book).stream().collect(Collectors.toList());

			}
		}
		Set<String> citydict = cityDict.keySet();
		for (String city : citydict) {
			System.out.println("Name: " + city);
			System.out.println(cityDict.get(city));
		}

		Set<String> statedict = stateDict.keySet();
		for (String state : statedict) {
			System.out.println("Name: " + state);
			System.out.println(cityDict.get(state));
		}
		System.out.println("Number of persons are : " + count);
	}
///////////////// UC_8 ability to search across city and State  /////////////////

	public static void searchByCity() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter city name");
		String stateOrCity = scanner.next();

		Set<String> bookNames = system.keySet();

		for (String book : bookNames) {
			for (int i = 0; i < system.get(book).size(); i++) {

				List all = system.get(book).stream()
						.filter(x -> x.city.equals(stateOrCity) || x.state.equals(stateOrCity))
						.collect(Collectors.toList());

				System.out.println(all.size());
			}
		}
	}

	public static void addNewBook() {
		Scanner scanner = new Scanner(System.in);
		int op = 1;
		while (op != 0) {
			System.out.println("Enter 1 to add new book , 0 to exit");
			op = scanner.nextInt();
			if (op != 0) {
				addNewContact();
			}
		}
	}

	static void deleteContact() {

		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter name to delete the info");
		String name = scanner.next();

		Set<String> bookNames = system.keySet();

		for (String book : bookNames) {
			for (int i = 0; i < system.get(book).size(); i++) {

				if (system.get(book).get(i).first_Name.equals(name)) {
					system.remove(system.get(book).get(i));
				}
			}
		}
	}

	static void editContact() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter name to update the info");
		String name = scanner.next();

		Set<String> bookNames = system.keySet();

		for (String book : bookNames) {
			for (int i = 0; i < system.get(book).size(); i++) {
				System.out.println(system.get(book).get(i).first_Name);

				if (system.get(book).get(i).first_Name.equals(name)) {

					System.out.println(
							"Enter 1 to change firstname , 2 to chnage lastname , 3 to change address , 4 to change City \n 5 tochange state, 6 to change zipcode , 7 to change obilenumber , 8 to change Email.");
					int op = scanner.nextInt();
					switch (op) {
					case 1:
						System.out.println("Enter new name");
						String newName = scanner.next();
						system.get(book).get(i).first_Name = newName;
						break;
					case 2:
						String newLastName = scanner.next();
						system.get(book).get(i).last_Name = newLastName;
						break;
					case 3:
						String newAddress = scanner.next();
						system.get(book).get(i).address = newAddress;
						break;
					case 4:
						String newCity = scanner.next();
						system.get(book).get(i).city = newCity;
						break;
					case 5:
						String newState = scanner.next();
						system.get(book).get(i).state = newState;
						break;
					case 6:
						int newZipCode = scanner.nextInt();
						system.get(book).get(i).zip_Code = newZipCode;
						break;
					case 7:
						int newPhoneNumber = scanner.nextInt();
						system.get(book).get(i).phone_Number = newPhoneNumber;
						break;
					case 8:
						String newEmail = scanner.next();
						system.get(book).get(i).email = newEmail;
						break;
					}
				}
			}
		}
	}

	static void addNewContact() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("enter book name");
		String bn = scanner.next();
		System.out.println("Enter First Name : ");
/////// Uc 7 Check for duplicate contact  //////////////////
		String firstName = scanner.next();
		Set<String> bookNames = system.keySet();
		for (String book : bookNames) {
			for (int i = 0; i < system.get(book).size(); i++) {

				boolean duplicateContact = system.get(book).stream().anyMatch(x -> x.first_Name.equals(firstName));

				if (duplicateContact == true) {
					System.out.println("It is a duplicate contact.");
					return;
				}
			}
		}
		System.out.println("Enter Last Name : ");
		String lastName = scanner.next();
		System.out.println("Enter Address : ");
		String address = scanner.next();
		System.out.println("Enter City : ");
		String city = scanner.next();
		System.out.println("Enter State : ");
		String state = scanner.next();
		System.out.println("Enter ZipCode : ");
		int zipCode = scanner.nextInt();
		System.out.println("Enter Mobile Number : ");
		long phonenumber = scanner.nextLong();
		System.out.println("Enter EmailId : ");
		String emailId = scanner.next();
		AddressBook addBook1 = new AddressBook();
//		ArrayList<Contact> contactDetails = new ArrayList<Contact>();

		addBook1.contactDetail
				.add(new Contact(firstName, lastName, address, city, state, zipCode, phonenumber, emailId));
//		contactDetails.addAll(addBook1.contactDetail);

		system.put(bn, addBook1.contactDetail);
	}

	static void addContact() {
		AddressBook addBook = new AddressBook();
		addBook.bookName = "book0";
		addBook.contactDetail.add(new Contact("Piyush", "Patil", "Nashik", "Nashik", "Maharashtra", 422001, 901155747,
				"piyushp@gmail.com"));
//		contactDetails.addAll(addBook.contactDetail);
		addBook.contactDetail.add(new Contact("Dinesh", "Patil", "Nashik", "Nashik", "Maharashtra", 422001, 901155747,
				"piyushp@gmail.com"));
//		contactDetails.addAll(addBook.contactDetail);
		addBook.contactDetail.add(new Contact("Pyush", "Patil", "Nashik", "Nashik", "Maharashtra", 422001, 901155747,
				"piyushp@gmail.com"));

		system.put(addBook.bookName, addBook.contactDetail);
//		contactDetails.addAll(addBook.contactDetail);
		AddressBook addBook2 = new AddressBook();

		addBook2.bookName = "book1";
		addBook2.contactDetail.add(new Contact("Piyush", "Patil", "Nasik", "Nasik", "Maharashtra", 422001, 901155747,
				"piyushp@gmail.com"));
		system.put(addBook2.bookName, addBook2.contactDetail);
//		contactDetails.addAll(addBook2.contactDetail);

	}

	static void viewContact() {
		Set<String> bookNames = system.keySet();
		for (String book : bookNames) {
			System.out.println("Name: " + book);
			System.out.println(system.get(book));
		}

	}

}
