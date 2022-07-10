package com.blzAsg;

public class Contact {

	String first_Name;
	String last_Name;
	String address;
	String city;
	String state;
	int zip_Code;
	long phone_Number;
	String email;

	Contact(String first_Name, String last_Name, String address, String city, String state, int zip_Code,
			long phone_Number, String email) {
		this.first_Name = first_Name;
		this.last_Name = last_Name;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip_Code = zip_Code;
		this.phone_Number = phone_Number;
		this.email = email;

	}

	@Override
	public String toString() {
		return " [First_Name=" + first_Name + ", Last_Name=" + last_Name + ", Address=" + address + ", City=" + city
				+ ", State=" + state + ", Zip_code=" + zip_Code + ", Phone_number=" + phone_Number + ", Email=" + email
				+ "]";
	}

}
