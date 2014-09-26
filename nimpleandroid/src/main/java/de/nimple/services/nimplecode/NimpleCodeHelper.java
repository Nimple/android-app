package de.nimple.services.nimplecode;

import android.content.Context;

import de.nimple.R;
import de.nimple.services.nimplecode.dto.NimpleCode;
import de.nimple.util.SharedPrefHelper;

public class NimpleCodeHelper implements NimpleCodeService {
	public NimpleCode holder;
	private Context ctx;
	private static int m_curId = 0; //NimpleCodeHelper.NC_CARD_1;

	public NimpleCodeHelper(Context ctx) {
		this.ctx = ctx;
	}

	@Override
	public NimpleCode load() {
		String curId = "";

		//Necessary to support versions with one card
		if (m_curId != 0) {
			curId = String.valueOf(m_curId);
		}

		holder = new NimpleCode();
		holder.cardName = SharedPrefHelper.getString(NC_CARD_NAME + curId, ctx);
		holder.firstname = SharedPrefHelper.getString(NC_VALUE_FIRSTNAME + curId, ctx);
		holder.lastname = SharedPrefHelper.getString(NC_VALUE_LASTNAME + curId, ctx);
		holder.mail = SharedPrefHelper.getString(NC_VALUE_MAIL + curId, ctx);
		holder.phone = SharedPrefHelper.getString(NC_VALUE_PHONE + curId, ctx);
		holder.phone_work = SharedPrefHelper.getString(NC_VALUE_PHONE_WORK + curId, ctx);

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
		holder.show.phone_work = SharedPrefHelper.getBoolean(NC_SHOW_PHONE_WORK + curId, true, ctx);
		holder.show.company = SharedPrefHelper.getBoolean(NC_SHOW_COMPANY + curId, true, ctx);
		holder.show.position = SharedPrefHelper.getBoolean(NC_SHOW_POSITION + curId, true, ctx);
		holder.show.address = SharedPrefHelper.getBoolean(NC_SHOW_ADDRESS + curId, true, ctx);

		holder.show.website = SharedPrefHelper.getBoolean(NC_SHOW_WEBSITE + curId, true, ctx);
		holder.show.facebook = SharedPrefHelper.getBoolean(NC_SHOW_FACEBOOK + curId, true, ctx);
		holder.show.twitter = SharedPrefHelper.getBoolean(NC_SHOW_TWITTER + curId, true, ctx);
		holder.show.xing = SharedPrefHelper.getBoolean(NC_SHOW_XING + curId, true, ctx);
		holder.show.linkedin = SharedPrefHelper.getBoolean(NC_SHOW_LINKEDIN + curId, true, ctx);

		return holder;
	}

	@Override
	public void save(NimpleCode nimpleCode) {
		holder = nimpleCode;

		String curId = "";

		//Necessary to support versions with one card
		if (m_curId != 0) {
			curId = String.valueOf(m_curId);
		}

		SharedPrefHelper.putBoolean(NC_INIT, true, ctx);

		if (holder.cardName != null && !holder.cardName.equals("")) {
			SharedPrefHelper.putString(NC_CARD_NAME + curId, holder.cardName, ctx);
		} else {
			SharedPrefHelper.putString(NC_CARD_NAME + curId, ctx.getString(R.string.nimpleCards_defaultName) + "_" + curId, ctx);
		}

		SharedPrefHelper.putString(NC_VALUE_FIRSTNAME + curId, holder.firstname, ctx);
		SharedPrefHelper.putString(NC_VALUE_LASTNAME + curId, holder.lastname, ctx);
		SharedPrefHelper.putString(NC_VALUE_MAIL + curId, holder.mail, ctx);
		SharedPrefHelper.putString(NC_VALUE_PHONE + curId, holder.phone, ctx);
		SharedPrefHelper.putString(NC_VALUE_PHONE_WORK + curId, holder.phone_work, ctx);

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
		SharedPrefHelper.putBoolean(NC_SHOW_PHONE_WORK + curId, holder.show.phone, ctx);
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

	public static int getCurrentId() {
		return m_curId;
	}

	public static void setCurrentId(int id) {
		m_curId = id;
	}

	// ////////////////////////// constants /////////////////////////////
	private final String NC_INIT = "nimple_code_init";

	private final String NC_CARD_NAME = "nimple_card_name";

	private final String NC_VALUE_FIRSTNAME = "nimple_code_firstname";
	private final String NC_VALUE_LASTNAME = "nimple_code_lastname";
	private final String NC_VALUE_MAIL = "nimple_code_mail";
	private final String NC_VALUE_PHONE = "nimple_code_phone";
	private final String NC_VALUE_PHONE_WORK = "nimple_code_phone_work";
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
	private final String NC_SHOW_PHONE_WORK = "nimple_code_phone_show_work";
	private final String NC_SHOW_COMPANY = "nimple_code_company_show";
	private final String NC_SHOW_POSITION = "nimple_code_position_show";
	private final String NC_SHOW_ADDRESS = "nimple_code_address_show";
	private final String NC_SHOW_LINKEDIN = "nimple_code_linkedin_show";
	private final String NC_SHOW_XING = "nimple_code_xing_show";
	private final String NC_SHOW_TWITTER = "nimple_code_twitter_show";
	private final String NC_SHOW_FACEBOOK = "nimple_code_facebook_show";
	private final String NC_SHOW_WEBSITE = "nimple_code_website_show";

//    public static final String NC_CARD_1 ="";
//    public static final String NC_CARD_2 ="2";
}