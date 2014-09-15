package de.nimple.util.nimplecode;

import android.content.Context;
import de.nimple.util.SharedPrefHelper;

public class NimpleCodeHelper {
	public NimpleCode holder;
	private Context ctx;
    private  static String curId = NimpleCodeHelper.NC_CARD_1;

	public NimpleCodeHelper(Context ctx) {
		this.ctx = ctx;
        load();
	}

	private void load() {
        holder = new NimpleCode();
		holder.firstname = SharedPrefHelper.getString(NC_VALUE_FIRSTNAME + curId, ctx);
		holder.lastname = SharedPrefHelper.getString(NC_VALUE_LASTNAME + curId, ctx);
		holder.mail = SharedPrefHelper.getString(NC_VALUE_MAIL + curId, ctx);
		holder.phone = SharedPrefHelper.getString(NC_VALUE_PHONE + curId, ctx);

		holder.company = SharedPrefHelper.getString(NC_VALUE_COMPANY + curId, ctx);
		holder.position = SharedPrefHelper.getString(NC_VALUE_POSITION + curId, ctx);
		holder.address = new Address(SharedPrefHelper.getString(NC_VALUE_ADDRESS + curId, ctx));

		holder.websiteUrl = SharedPrefHelper.getString(NC_VALUE_URL_WEBSITE + curId, ctx);

		holder.facebookId = SharedPrefHelper.getString(NC_VALUE_ID_FACEBOOK + curId, ctx);
		holder.facebookUrl = SharedPrefHelper.getString(NC_VALUE_URL_FACEBOOK + curId, ctx);

		holder.twitterId = SharedPrefHelper.getString(NC_VALUE_ID_TWITTER + curId, ctx);
		holder.twitterUrl = SharedPrefHelper.getString(NC_VALUE_URL_TWITTER + curId, ctx);

		holder.xingUrl = SharedPrefHelper.getString(NC_VALUE_URL_XING + curId, ctx);

		holder.linkedinUrl = SharedPrefHelper.getString(NC_VALUE_URL_LINKEDIN + curId, ctx);

		holder.show.mail = SharedPrefHelper.getBoolean(NC_SHOW_MAIL + curId, true, ctx);
		holder.show.phone = SharedPrefHelper.getBoolean(NC_SHOW_PHONE + curId, true, ctx);
		holder.show.company = SharedPrefHelper.getBoolean(NC_SHOW_COMPANY + curId, true, ctx);
		holder.show.position = SharedPrefHelper.getBoolean(NC_SHOW_POSITION + curId, true, ctx);
		holder.show.address = SharedPrefHelper.getBoolean(NC_SHOW_ADDRESS + curId, true, ctx);

		holder.show.website = SharedPrefHelper.getBoolean(NC_SHOW_WEBSITE + curId, true, ctx);
		holder.show.facebook = SharedPrefHelper.getBoolean(NC_SHOW_FACEBOOK + curId, true, ctx);
		holder.show.twitter = SharedPrefHelper.getBoolean(NC_SHOW_TWITTER + curId, true, ctx);
		holder.show.xing = SharedPrefHelper.getBoolean(NC_SHOW_XING + curId, true, ctx);
		holder.show.linkedin = SharedPrefHelper.getBoolean(NC_SHOW_LINKEDIN + curId, true, ctx);
	}

	public void save() {
		SharedPrefHelper.putBoolean(NC_INIT + curId, true, ctx);
		SharedPrefHelper.putString(NC_VALUE_FIRSTNAME + curId, holder.firstname, ctx);
		SharedPrefHelper.putString(NC_VALUE_LASTNAME + curId, holder.lastname, ctx);
		SharedPrefHelper.putString(NC_VALUE_MAIL + curId, holder.mail, ctx);
		SharedPrefHelper.putString(NC_VALUE_PHONE + curId, holder.phone, ctx);

		SharedPrefHelper.putString(NC_VALUE_COMPANY + curId, holder.company, ctx);
		SharedPrefHelper.putString(NC_VALUE_POSITION + curId, holder.position, ctx);
		SharedPrefHelper.putString(NC_VALUE_ADDRESS + curId, holder.address.toString(), ctx);

		SharedPrefHelper.putString(NC_VALUE_URL_WEBSITE + curId, holder.websiteUrl, ctx);
		SharedPrefHelper.putString(NC_VALUE_ID_FACEBOOK + curId, holder.facebookId, ctx);
		SharedPrefHelper.putString(NC_VALUE_URL_FACEBOOK + curId, holder.facebookUrl, ctx);
		SharedPrefHelper.putString(NC_VALUE_ID_TWITTER + curId, holder.twitterId, ctx);
		SharedPrefHelper.putString(NC_VALUE_URL_TWITTER + curId, holder.twitterUrl, ctx);
		SharedPrefHelper.putString(NC_VALUE_URL_XING + curId, holder.xingUrl, ctx);
		SharedPrefHelper.putString(NC_VALUE_URL_LINKEDIN + curId, holder.linkedinUrl, ctx);

		// Show
		SharedPrefHelper.putBoolean(NC_SHOW_MAIL + curId, holder.show.mail, ctx);
		SharedPrefHelper.putBoolean(NC_SHOW_PHONE + curId, holder.show.phone, ctx);
		SharedPrefHelper.putBoolean(NC_SHOW_COMPANY + curId, holder.show.company, ctx);
		SharedPrefHelper.putBoolean(NC_SHOW_POSITION + curId, holder.show.position, ctx);
		SharedPrefHelper.putBoolean(NC_SHOW_ADDRESS + curId, holder.show.address, ctx);

		SharedPrefHelper.putBoolean(NC_SHOW_WEBSITE + curId, holder.show.website, ctx);
		SharedPrefHelper.putBoolean(NC_SHOW_FACEBOOK + curId, holder.show.facebook, ctx);
		SharedPrefHelper.putBoolean(NC_SHOW_TWITTER + curId, holder.show.twitter, ctx);
		SharedPrefHelper.putBoolean(NC_SHOW_XING + curId, holder.show.xing, ctx);
		SharedPrefHelper.putBoolean(NC_SHOW_LINKEDIN + curId, holder.show.linkedin, ctx);
	}

	public boolean isInitialState() {
		return !SharedPrefHelper.getBoolean(NC_INIT, ctx);
	}

    public void changeCardById(String id){
        this.save();
        this.curId = id;
        this.load();
    }

    public static String getCurrentId(){
        return curId;
    }

    public static void setCurrentId(String id){
         curId = id;
    }

	public class NimpleCode {
		public Show show = new Show();

		public String firstname;
		public String lastname;
		public String mail;
		public String phone;
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

	// ////////////////////////// constants /////////////////////////////
	private final String NC_INIT = "nimple_code_init";

	private final String NC_VALUE_FIRSTNAME = "nimple_code_firstname";
	private final String NC_VALUE_LASTNAME = "nimple_code_lastname";
	private final String NC_VALUE_MAIL = "nimple_code_mail";
	private final String NC_VALUE_PHONE = "nimple_code_phone";
	private final String NC_VALUE_ADDRESS = "nimple_code_address";
	private final String NC_VALUE_POSITION = "nimple_code_position";
	private final String NC_VALUE_COMPANY = "nimple_code_company";

	private final String NC_VALUE_URL_LINKEDIN = "nimple_code_linkedin_url";
	private final String NC_VALUE_URL_XING = "nimple_code_xing_url";
	private final String NC_VALUE_URL_TWITTER = "nimple_code_twitter_url";
	private final String NC_VALUE_URL_FACEBOOK = "nimple_code_facebook_url";
	private final String NC_VALUE_URL_WEBSITE = "nimple_code_website_url";
	private final String NC_VALUE_ID_FACEBOOK = "nimple_code_facebook_id";
	private final String NC_VALUE_ID_TWITTER = "nimple_code_twitter_id";

	// Show
	private final String NC_SHOW_MAIL = "nimple_code_mail_show";
	private final String NC_SHOW_PHONE = "nimple_code_phone_show";
	private final String NC_SHOW_COMPANY = "nimple_code_company_show";
	private final String NC_SHOW_POSITION = "nimple_code_position_show";
	private final String NC_SHOW_ADDRESS = "nimple_code_address_show";
	private final String NC_SHOW_LINKEDIN = "nimple_code_linkedin_show";
	private final String NC_SHOW_XING = "nimple_code_xing_show";
	private final String NC_SHOW_TWITTER = "nimple_code_twitter_show";
	private final String NC_SHOW_FACEBOOK = "nimple_code_facebook_show";
	private final String NC_SHOW_WEBSITE = "nimple_code_website_show";

    public static final String NC_CARD_1 ="";
    public static final String NC_CARD_2 ="2";
}