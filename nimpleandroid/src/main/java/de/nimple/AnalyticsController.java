package de.nimple;

import android.content.Context;

import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;
import javax.inject.Named;

import de.greenrobot.event.EventBus;
import de.nimple.config.Config;
import de.nimple.config.Constants;
import de.nimple.domain.Contact;
import de.nimple.events.ApplicationStartedEvent;
import de.nimple.events.ContactAddedEvent;
import de.nimple.events.ContactTransferredEvent;
import de.nimple.events.NimpleCodeChangedEvent;
import de.nimple.services.nimplecode.NimpleCodeHelper;
import de.nimple.dto.NimpleCode;

/**
 * Created by bjohn on 25/09/14.
 */
public class AnalyticsController {
	@Inject
	@Named("App")
	Context ctx;

	@Inject
	EventBus eventBus;

	private MixpanelAPI mixpanel;

	public AnalyticsController() {
		mixpanel = MixpanelAPI.getInstance(ctx, Config.MIXPANEL_TOKEN);
		eventBus.register(this);
	}

	public void finish() {
		eventBus.unregister(this);
		mixpanel.flush();
	}

	public void onEvent(ApplicationStartedEvent ev) {
		JSONObject props = new JSONObject();
		mixpanel.track("app started", props);
	}

	public void onEvent(ContactAddedEvent ev) throws JSONException {
		JSONObject props = new JSONObject();
		Contact c = ev.getContact();

		boolean isFlyerContact = false;
		if (c.getHash().equals(Constants.FLYER_CONTACT_HASH)) {
			isFlyerContact = true;
		}

		props.put("has phone number", c.getTelephone().length() != 0);
		props.put("has mail address", c.getEmail().length() != 0);
		props.put("has company", c.getCompany().length() != 0);
		props.put("has job title", c.getPosition().length() != 0);
		props.put("has facebook", c.getFacebookUrl().length() != 0);
		props.put("has twitter", c.getTwitterUrl().length() != 0);
		props.put("has xing", c.getXingUrl().length() != 0);
		props.put("has linkedin", c.getLinkedinUrl().length() != 0);
		props.put("has website", c.getWebsite().length() != 0);
		props.put("has address", c.hasAddress());
		props.put("is flyer contact", isFlyerContact);

		mixpanel.track("contact scanned", props);
	}

	public void onEvent(ContactTransferredEvent ev) {
		JSONObject props = new JSONObject();
		mixpanel.track("contact saved in adress book", props);
	}

	public void onEvent(NimpleCodeChangedEvent ev) throws JSONException {
		NimpleCode nimpleCode = new NimpleCodeHelper(ctx).holder;

		JSONObject props = new JSONObject();

		props.put("has phone number", nimpleCode.phone.length() != 0);
		props.put("has mail address", nimpleCode.mail.length() != 0);
		props.put("has company", nimpleCode.company.length() != 0);
		props.put("has job title", nimpleCode.position.length() != 0);
		props.put("has facebook", nimpleCode.facebookUrl.length() != 0);
		props.put("has twitter", nimpleCode.twitterUrl.length() != 0);
		props.put("has xing", nimpleCode.xingUrl.length() != 0);
		props.put("has website", nimpleCode.websiteUrl.length() != 0);
		props.put("has address", nimpleCode.address.hasAddress());
		props.put("has linkedin", nimpleCode.linkedinUrl.length() != 0);

		mixpanel.track("nimple code edited", props);
	}
}
