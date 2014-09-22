package de.nimple.dagger;

import android.content.Context;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.nimple.DataSyncController;
import de.nimple.services.billing.BillingInAppImpl;
import de.nimple.services.billing.BillingService;
import de.nimple.services.contacts.ContactsSQLiteImpl;
import de.nimple.services.contacts.ContactsService;
import de.nimple.ui.contact.DisplayContactActivity;
import de.nimple.ui.main.MainActivity;
import de.nimple.ui.main.fragments.ContactListFragment;

/**
 * Modules which uses injection are declared here.
 */
@Module(injects = {DataSyncController.class, MainActivity.class, ContactListFragment.class, DisplayContactActivity.class},
		library = true, complete = false)
public class NimpleModule {
	private Context context;

	public NimpleModule(Context context) {
		this.context = context;
	}

	@Provides
	@Named("App")
	public Context provideAppContext() {
		return context;
	}

	@Provides
	@Singleton
	public ContactsService provideContactsService(@Named("App") Context context) {
		return new ContactsSQLiteImpl(context);
	}

	@Provides
	@Singleton
	public BillingService provideBillingService(@Named("App") Context context) {
		return new BillingInAppImpl(context);
	}
}
