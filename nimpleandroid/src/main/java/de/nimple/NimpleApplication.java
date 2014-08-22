package de.nimple;

import android.app.Application;
import de.nimple.domain.Contact;
import de.nimple.exceptions.DuplicatedContactException;
import de.nimple.persistence.ContactsPersistenceManager;
import de.nimple.util.SharedPrefHelper;
import de.nimple.util.logging.Lg;
import de.nimple.util.logging.Mixpanel;

public class NimpleApplication extends Application {
	private DataSyncController dsc;
	private Mixpanel mixpanel;

	@Override
	public void onCreate() {
		super.onCreate();
		dsc = new DataSyncController(getApplicationContext());
		mixpanel = Mixpanel.getInstance(getApplicationContext());
		onBootstrap();
	}

	private boolean isInitial() {
		return !SharedPrefHelper.getBoolean("nimple_app_launched", getApplicationContext());
	}

	private void onBootstrap() {
		if (isInitial()) {
			Contact c = new Contact(null, getString(R.string.bootstrap_first_contact), "feedback.android@nimple.de", "", "http://www.nimple.de", "", "", "",
					"Appstronauten GbR", "Nimple - Networking Simple", "286113114869395", "https://www.facebook.com/nimpleapp", "2444364654",
					"https://twitter.com/Nimpleapp", "https://www.xing.com/companies/appstronautengbr", "https://www.linkedin.com/company/appstronauten-gbr",
					"", System.currentTimeMillis(), "");
			try {
				ContactsPersistenceManager.getInstance(getApplicationContext()).persist(c);
			} catch (DuplicatedContactException e) {
				Lg.d("DuplicatedContactException occured - should never happen onBootstrap");
			}

			SharedPrefHelper.putBoolean("nimple_app_launched", true, getApplicationContext());
		}
	}

	@Override
	public void onTerminate() {
		dsc.finish();
		mixpanel.flush();
		super.onTerminate();
	}
}