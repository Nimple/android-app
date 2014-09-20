package de.nimple;

import de.nimple.dagger.DaggerApplication;
import de.nimple.util.logging.Mixpanel;

public final class NimpleApplication extends DaggerApplication {
	private DataSyncController dsc;
	private Mixpanel mixpanel;

	@Override
	public void onCreate() {
		super.onCreate();
		dsc = new DataSyncController(getApplicationContext());
		mixpanel = Mixpanel.getInstance(getApplicationContext());
		com.facebook.AppEventsLogger.activateApp(getApplicationContext(), getString(R.string.app_id));
	}

	@Override
	public void onTerminate() {
		dsc.finish();
		mixpanel.flush();
		super.onTerminate();
	}
}
