package de.nimple.test;

import de.nimple.domain.Contact;
import de.nimple.util.nimplecode.VCardHelper;
import junit.framework.TestCase;

//vCardParser should run through all tests without an error
public class ParserTest extends TestCase {
	public void testParseWrong() {
		long timeStart = System.currentTimeMillis(); 
		Contact contact = VCardHelper.getContactFromCard(TestStrings.WrongVCard1);
		assertEquals("Peter Pan", contact.getName());
		assertEquals("Wunderland", contact.getPosition());
		assertEquals("peter.pan@wunderland.de",contact.getEmail());
		assertEquals("01710203981",contact.getTelephone());
	    long timeEnd = System.currentTimeMillis(); 
        System.out.println("Dauer Parsen: " + (timeEnd - timeStart) + " Millisek.");
        
        timeStart = System.currentTimeMillis(); 
		contact = VCardHelper.getContactFromCard(TestStrings.WrongVCard2);
		assertEquals("Jup Mueller", contact.getName());
		assertEquals("FCB", contact.getCompany());
		assertEquals("jub.mueller@gmail.com",contact.getEmail());
		assertEquals("017209382032",contact.getTelephone());
		assertEquals("https://www.facebook.com/jup.muller.12",contact.getFacebookUrl());
		timeEnd = System.currentTimeMillis(); 
        System.out.println("Dauer Parsen: " + (timeEnd - timeStart) + " Millisek.");
        
        timeStart = System.currentTimeMillis(); 
		contact = VCardHelper.getContactFromCard(TestStrings.WrongVCard3);
		assertEquals("Techniker Krankenkasse", contact.getName());
		assertEquals("080-028-58585",contact.getTelephone());
		timeEnd = System.currentTimeMillis(); 
        System.out.println("Dauer Parsen: " + (timeEnd - timeStart) + " Millisek.");
        
        timeStart = System.currentTimeMillis(); 
		contact = VCardHelper.getContactFromCard(TestStrings.CorrectVCard1);
		assertEquals("Techniker Krankenkasse", contact.getName());
		assertEquals("080-028-58585",contact.getTelephone());
		timeEnd = System.currentTimeMillis(); 
        System.out.println("Dauer Parsen: " + (timeEnd - timeStart) + " Millisek.");
        
        timeStart = System.currentTimeMillis(); 
		contact = VCardHelper.getContactFromCard(TestStrings.CorrectVCard2);
		assertEquals("Sebastian Lang", contact.getName());
		assertEquals("+491733484138",contact.getTelephone());
		assertEquals("Appstronauten GbR",contact.getCompany());
		assertEquals("Founder",contact.getPosition());
		assertEquals("https://www.facebook.com/sebastian.lang.946",contact.getFacebookUrl());
		assertEquals("1054905012",contact.getFacebookId());
		assertEquals("https://twitter.com/Nimpleapp",contact.getTwitterUrl());
		assertEquals("2444364654",contact.getTwitterId());
		assertEquals("https://www.xing.com/profile/Sebastian_Lang34",contact.getXingUrl());
		assertEquals("http://www.linkedin.com/profile/view?id=289035843",contact.getLinkedinUrl());
		timeEnd = System.currentTimeMillis(); 
        System.out.println("Dauer Parsen: " + (timeEnd - timeStart) + " Millisek.");
        
        timeStart = System.currentTimeMillis(); 
		contact = VCardHelper.getContactFromCard(TestStrings.CorrectVCard3);
		assertEquals("Sebastian Lang", contact.getName());
		assertEquals("+491733484138",contact.getTelephone());
		assertEquals("sebastian@appstronauten.com",contact.getEmail());
		assertEquals("https://www.facebook.com/sebastian.lang.946",contact.getFacebookUrl());
		assertEquals("1054905012",contact.getFacebookId());
		assertEquals("http://www.linkedin.com/profile/view?id=289035843",contact.getLinkedinUrl());
		timeEnd = System.currentTimeMillis(); 
        System.out.println("Dauer Parsen: " + (timeEnd - timeStart) + " Millisek.");
 
        timeStart = System.currentTimeMillis(); 
		contact = VCardHelper.getContactFromCard(TestStrings.CorrectVCard4);
		assertEquals("Sebastian Lang", contact.getName());
		assertEquals("+491733484138",contact.getTelephone());
		assertEquals("Appstronauten GbR",contact.getCompany());
		assertEquals("Founder",contact.getPosition());
		assertEquals("sebastian@appstronauten.com",contact.getEmail());
		timeEnd = System.currentTimeMillis(); 
        System.out.println("Dauer Parsen: " + (timeEnd - timeStart) + " Millisek.");

        timeStart = System.currentTimeMillis(); 
		contact = VCardHelper.getContactFromCard(TestStrings.CorrectVCard5);
		assertEquals("Sebastian Lang", contact.getName());
		assertEquals("+491733484138",contact.getTelephone());
		assertEquals("Appstronauten GbR",contact.getCompany());
		assertEquals("Founder",contact.getPosition());
		assertEquals("sebastian@appstronauten.com",contact.getEmail());
		assertEquals("https://www.xing.com/profile/Sebastian_Lang34",contact.getXingUrl());
		assertEquals("http://www.linkedin.com/profile/view?id=289035843",contact.getLinkedinUrl());
		timeEnd = System.currentTimeMillis(); 
        System.out.println("Dauer Parsen: " + (timeEnd - timeStart) + " Millisek.");
        
        timeStart = System.currentTimeMillis(); 
      	contact = VCardHelper.getContactFromCard(TestStrings.CorrectVCard6);
      	assertEquals("Sebastian Lang", contact.getName());
      	assertEquals("https://www.xing.com/profile/Sebastian_Lang34",contact.getXingUrl());
      	timeEnd = System.currentTimeMillis(); 
        System.out.println("Dauer Parsen: " + (timeEnd - timeStart) + " Millisek.");
        
        timeStart = System.currentTimeMillis(); 
      	contact = VCardHelper.getContactFromCard(TestStrings.CorrectVCard7);
      	assertEquals("Sebastian Lang", contact.getName());
      	assertEquals("https://www.xing.com/profile/Sebastian_Lang34",contact.getXingUrl());
      	timeEnd = System.currentTimeMillis(); 
        System.out.println("Dauer Parsen: " + (timeEnd - timeStart) + " Millisek.");
        
        timeStart = System.currentTimeMillis(); 
      	contact = VCardHelper.getContactFromCard(TestStrings.CorrectVCard8);
      	assertEquals("Sebastian Lang", contact.getName());
      	assertEquals("https://www.xing.com/profile/Sebastian_Lang34",contact.getXingUrl());
      	timeEnd = System.currentTimeMillis(); 
        System.out.println("Dauer Parsen: " + (timeEnd - timeStart) + " Millisek.");
        
        timeStart = System.currentTimeMillis(); 
      	contact = VCardHelper.getContactFromCard(TestStrings.CorrectVCard9);
      	assertEquals("Sebastian Lang", contact.getName());
      	assertEquals("https://www.xing.com/profile/Sebastian_Lang34",contact.getXingUrl());
      	timeEnd = System.currentTimeMillis(); 
        System.out.println("Dauer Parsen: " + (timeEnd - timeStart) + " Millisek.");
        
        timeStart = System.currentTimeMillis(); 
      	contact = VCardHelper.getContactFromCard(TestStrings.CorrectVCard10);
      	assertEquals("Sebastian Lang", contact.getName());
      	assertEquals("https://www.xing.com/profile/Sebastian_Lang34",contact.getXingUrl());
      	timeEnd = System.currentTimeMillis(); 
        System.out.println("Dauer Parsen: " + (timeEnd - timeStart) + " Millisek.");
	}
}
