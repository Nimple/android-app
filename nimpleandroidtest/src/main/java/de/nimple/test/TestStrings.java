package de.nimple.test;

public class TestStrings {
	public final static String  WrongVCard1 = 
			"BEGIN:VCARD" + System.getProperty("line.separator") +
			"VERSION:3.0"  + System.getProperty("line.separator") +
			"N:Pan;Peter" + System.getProperty("line.separator") +
			"FN:Peter Pan" + System.getProperty("line.separator") +
			"TITLE:Wunderland" + System.getProperty("line.separator") +
			"EMAIL;TYPE=INTERNET:peter.pan@wunderland.de" + System.getProperty("line.separator") +
			"TEL;TYPE=voice,work,pref:01710203981" + System.getProperty("line.separator") +
			"END:VCARD";
	
	public final static String WrongVCard2 = 
			"BEGIN:VCARD" + System.getProperty("line.separator") +
			"VERSION:2.1" + System.getProperty("line.separator") +
			"FN:Jup Mueller" + System.getProperty("line.separator") +
			"N:Mueller;Jup" + System.getProperty("line.separator") +
			"TEL;HOME;VOICE:017209382032" + System.getProperty("line.separator") +
			"EMAIL;HOME;INTERNET:jub.mueller@gmail.com" + System.getProperty("line.separator") +
			"URL:https://www.facebook.com/jup.muller.12" + System.getProperty("line.separator") +
			"ORG:FCB" + System.getProperty("line.separator") +
			"END:VCARD";
	
	public final static String WrongVCard3 = 
			"BEGIN:VCARD" + System.getProperty("line.separator") +
			"VERSION:4.0" + System.getProperty("line.separator") +
			"FN:Techniker Krankenkasse" + System.getProperty("line.separator") +
			"N:;Techniker Krankenkasse;;;" + System.getProperty("line.separator") +
			"TEL;TYPE=HOME:080-028-58585" + System.getProperty("line.separator") +
			"END:VCARD";
	
//------------------------------------------ Correct ---------------------------------------------------
	
	public final static String CorrectVCard1 = 
			"BEGIN:VCARD" + System.getProperty("line.separator") +
			"VERSION:3.0" + System.getProperty("line.separator") +
			"FN:Techniker Krankenkasse" + System.getProperty("line.separator") +
			"N:;Techniker Krankenkasse;;;" + System.getProperty("line.separator") +
			"TEL;TYPE=HOME:080-028-58585" + System.getProperty("line.separator") +
			"END:VCARD";
	
	public final static String CorrectVCard2 = 
			"BEGIN:VCARD" + System.getProperty("line.separator") +
			"VERSION:3.0" + System.getProperty("line.separator") +
			"N:Lang;Sebastian" + System.getProperty("line.separator") +
			"TEL;CELL:+491733484138" + System.getProperty("line.separator") +
			"EMAIL:sebastian@appstronauten.com" + System.getProperty("line.separator") +
			"ORG:Appstronauten GbR" + System.getProperty("line.separator") +
			"TITLE:Founder" + System.getProperty("line.separator") +
			"URL:https://www.facebook.com/sebastian.lang.946" + System.getProperty("line.separator") +
			"X-FACEBOOK-ID:1054905012" + System.getProperty("line.separator") +
			"URL:https://twitter.com/Nimpleapp" + System.getProperty("line.separator") +
			"X-TWITTER-ID:2444364654" + System.getProperty("line.separator") +
			"URL:https://www.xing.com/profile/Sebastian_Lang34" + System.getProperty("line.separator") +
			"URL:http://www.linkedin.com/profile/view?id=289035843" + System.getProperty("line.separator") +
			"END:VCARD";
	
	public final static String CorrectVCard3 = 
			"BEGIN:VCARD" + System.getProperty("line.separator") +
			"VERSION:3.0" + System.getProperty("line.separator") +
			"N:Lang;Sebastian" + System.getProperty("line.separator") +
			"TEL;CELL:+491733484138" + System.getProperty("line.separator") +
			"EMAIL:sebastian@appstronauten.com" + System.getProperty("line.separator") +
			"URL:https://www.facebook.com/sebastian.lang.946" + System.getProperty("line.separator") +
			"X-FACEBOOK-ID:1054905012" + System.getProperty("line.separator") +
			"URL:http://www.linkedin.com/profile/view?id=289035843" + System.getProperty("line.separator") +
			"END:VCARD";
	
	public final static String CorrectVCard4 = 
			"BEGIN:VCARD" + System.getProperty("line.separator") +
			"VERSION:3.0" + System.getProperty("line.separator") +
			"N:Lang;Sebastian" + System.getProperty("line.separator") +
			"TEL;CELL:+491733484138" + System.getProperty("line.separator") +
			"EMAIL:sebastian@appstronauten.com" + System.getProperty("line.separator") +
			"ORG:Appstronauten GbR" + System.getProperty("line.separator") +
			"TITLE:Founder" + System.getProperty("line.separator") +
			"END:VCARD";
	
	public final static String CorrectVCard5 = 
			"BEGIN:VCARD" + System.getProperty("line.separator") +
			"VERSION:3.0" + System.getProperty("line.separator") +
			"N:Lang;Sebastian" + System.getProperty("line.separator") +
			"TEL;CELL:+491733484138" + System.getProperty("line.separator") +
			"EMAIL:sebastian@appstronauten.com" + System.getProperty("line.separator") +
			"ORG:Appstronauten GbR" + System.getProperty("line.separator") +
			"TITLE:Founder" + System.getProperty("line.separator") +
			"URL:https://www.xing.com/profile/Sebastian_Lang34" + System.getProperty("line.separator") +
			"URL:http://www.linkedin.com/profile/view?id=289035843" + System.getProperty("line.separator") +
			"END:VCARD";
	
	public final static String CorrectVCard6 = 
			"BEGIN:VCARD" + System.getProperty("line.separator") +
			"VERSION:3.0" + System.getProperty("line.separator") +
			"N:Lang;Sebastian" + System.getProperty("line.separator") +
			"URL:https://www.xing.com/profile/Sebastian_Lang34" + System.getProperty("line.separator") +
			"END:VCARD";

	public final static String CorrectVCard7 = 
			"BEGIN:VCARD" + "\r\n" +
			"VERSION:3.0" + "\r\n" +
			"N:Lang;Sebastian" + "\r\n" +
			"URL:https://www.xing.com/profile/Sebastian_Lang34" + "\r\n" +
			"END:VCARD";
	
	public final static String CorrectVCard8 = 
			"BEGIN:VCARD" + "\r" +
			"VERSION:3.0" + "\r" +
			"N:Lang;Sebastian" + "\r" +
			"URL:https://www.xing.com/profile/Sebastian_Lang34" + "\r" +
			"END:VCARD";
	
	public final static String CorrectVCard9 = 
			"BEGIN:VCARD" + "\r" +
			"VERSION:3.0" + "\r\n" +
			"N:Lang;Sebastian" + "\n" +
			"URL:https://www.xing.com/profile/Sebastian_Lang34" + "\r" +
			"END:VCARD";
	
	public final static String CorrectVCard10 = 
			"BEGIN:VCARD" + "\n" +
			"VERSION:3.0" + "\n" +
			"N:Lang;Sebastian" + "\n" +
			"URL:https://www.xing.com/profile/Sebastian_Lang34" + "\n" +
			"END:VCARD";
}
