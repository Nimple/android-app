package de.nimple;

import android.content.Context;

import org.json.JSONObject;

import javax.inject.Inject;
import javax.inject.Named;

import de.greenrobot.event.EventBus;
import de.nimple.domain.Contact;
import de.nimple.enums.SocialNetwork;
import de.nimple.events.ContactAddedEvent;
import de.nimple.events.DuplicatedContactEvent;
import de.nimple.events.NimpleCodeChangedEvent;
import de.nimple.events.NimpleCodeScanFailedEvent;
import de.nimple.events.NimpleCodeScannedEvent;
import de.nimple.events.SocialConnectedEvent;
import de.nimple.events.SocialDisconnectedEvent;
import de.nimple.exceptions.DuplicatedContactException;
import de.nimple.services.contacts.ContactsService;
import de.nimple.util.logging.Lg;
import de.nimple.util.nimplecode.NimpleCodeHelper;
import de.nimple.util.nimplecode.VCardHelper;

public class DataSyncController {
	@Inject
	ContactsService contactsService;

	@Inject
	@Named("App")
	Context ctx;

	public DataSyncController(Context ctx) {
		EventBus.getDefault().register(this);
	}

	public void finish() {
		EventBus.getDefault().unregister(this);
	}

	public void onEventMainThread(SocialConnectedEvent ev) {
		NimpleCodeHelper ncode = new NimpleCodeHelper(ctx);

		try {
			JSONObject json = ev.getResponse();
			// Lg.d(json.toString());

			// parse facebook json
			if (ev.getType() == SocialNetwork.FACEBOOK) {
				String id = json.getString("id");
				String link = json.getString("link");
				ncode.holder.facebookId = id;
				ncode.holder.facebookUrl = link;
			}

			// parse twitter json
			if (ev.getType() == SocialNetwork.TWITTER) {
				int id = json.getInt("id");
				String screen_name = json.getString("screen_name");
				ncode.holder.twitterId = Integer.toString(id);
				ncode.holder.twitterUrl = "https://twitter.com/" + screen_name;
			}

			// parse xing json
			if (ev.getType() == SocialNetwork.XING) {
				JSONObject idCard = json.getJSONObject("id_card");
				String permalink = idCard.getString("permalink");
				ncode.holder.xingUrl = permalink;
			}

			// parse linkedin json
			if (ev.getType() == SocialNetwork.LINKEDIN) {
				JSONObject siteStandardProfileRequest = json.getJSONObject("siteStandardProfileRequest");
				String url = siteStandardProfileRequest.getString("url");
				url = url.substring(0, url.indexOf("&"));
				Lg.d("linkedin url=" + url);
				ncode.holder.linkedinUrl = url;
			}

			ncode.save();
			EventBus.getDefault().post(new NimpleCodeChangedEvent());
		} catch (Exception e) {
			// should never happen
			Lg.e(e.toString());
		}
	}

	public void onEventMainThread(SocialDisconnectedEvent ev) {
		NimpleCodeHelper ncode = new NimpleCodeHelper(ctx);

		// remove facebook
		if (ev.getType() == SocialNetwork.FACEBOOK) {
			ncode.holder.facebookId = "";
			ncode.holder.facebookUrl = "";
		}

		// remove twitter
		if (ev.getType() == SocialNetwork.TWITTER) {
			ncode.holder.twitterId = "";
			ncode.holder.twitterUrl = "";
		}

		// remove xing
		if (ev.getType() == SocialNetwork.XING) {
			ncode.holder.xingUrl = "";
		}

		// remove linkedin
		if (ev.getType() == SocialNetwork.LINKEDIN) {
			ncode.holder.linkedinUrl = "";
		}

		ncode.save();
		EventBus.getDefault().post(new NimpleCodeChangedEvent());
	}

	public void onEventMainThread(NimpleCodeScannedEvent ev) {
		Contact contact = VCardHelper.getContactFromCard(ev.getData());
		if (contact != null) {
			Lg.d("hash of contact = " + contact.getHash());

			try {
				contactsService.persist(contact);
				EventBus.getDefault().post(new ContactAddedEvent(contact));
			} catch (DuplicatedContactException e) {
				EventBus.getDefault().post(new DuplicatedContactEvent(contact));
			}
		} else {
			EventBus.getDefault().post(new NimpleCodeScanFailedEvent());
		}
	}
}