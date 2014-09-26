package de.nimple.services.nimplecode.dto;

import de.nimple.services.nimplecode.Address;

/**
 * Created by bjohn on 23/09/14.
 */
public class NimpleCode {
	public Show show = new Show();
	public String cardName;

	public String firstname;
	public String lastname;
	public String mail;
	public String phone;
	public String phone_work;
	public String company;
	public String position;
	public Address address;

	public String websiteUrl;

	public String facebookUrl;
	public String facebookId;

	public String twitterUrl;
	public String twitterId;

	public String xingUrl;

	public String linkedinUrl;

	public class Show {
		public boolean mail;
		public boolean phone;
		public boolean phone_work;
		public boolean company;
		public boolean position;
		public boolean address;

		public boolean website;
		public boolean facebook;
		public boolean twitter;
		public boolean xing;
		public boolean linkedin;
	}
}