package de.nimple.ui.main.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;
import de.nimple.R;
import de.nimple.dagger.BaseFragment;
import de.nimple.domain.Contact;
import de.nimple.events.ContactAddedEvent;
import de.nimple.events.ContactDeletedEvent;
import de.nimple.exceptions.DuplicatedContactException;
import de.nimple.services.contacts.ContactsService;
import de.nimple.util.SharedPrefHelper;
import de.nimple.util.export.Export;
import de.nimple.util.export.IExportExtender;
import de.nimple.util.logging.Lg;
import de.nimple.util.nimplecode.VCardHelper;

public class ContactListFragment extends BaseFragment implements IExportExtender {
	public static final ContactListFragment newInstance() {
		return new ContactListFragment();
	}

	private Context ctx;
	private List<Contact> listOfContacts;
	private ContactsListAdapter contactsAdapter;

	@Inject
	ContactsService contactsService;

	@InjectView(R.id.contactsList)
	ListView contactsList;
	@InjectView(R.id.noContactsTextView)
	TextView noContactsTextView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ctx = getActivity().getApplicationContext();
		onBootstrap();

		final View view = inflater.inflate(R.layout.contacts_fragment, container, false);
		ButterKnife.inject(this, view);

		listOfContacts = contactsService.findAllContacts();

		EventBus.getDefault().register(this);

		contactsAdapter = new ContactsListAdapter(this.getActivity(), R.layout.single_contact, listOfContacts, inflater);
		contactsList.setAdapter(contactsAdapter);

		toggleInfoText();
		return view;
	}

	private boolean isInitial() {
		return !SharedPrefHelper.getBoolean("nimple_app_launched", ctx);
	}

	private void onBootstrap() {
		if (isInitial()) {
			Contact c = new Contact(null, getString(R.string.bootstrap_first_contact), "feedback.android@nimple.de", "", "http://www.nimple.de", "", "", "", "",
					"Appstronauten GbR", "Nimple - Networking Simple", "286113114869395", "https://www.facebook.com/nimpleapp", "2444364654",
					"https://twitter.com/Nimpleapp", "https://www.xing.com/companies/appstronautengbr", "https://www.linkedin.com/company/appstronauten-gbr",
					"", System.currentTimeMillis(), "");

			try {
				contactsService.persist(c);
			} catch (DuplicatedContactException e) {
				Lg.d("DuplicatedContactException occured - should never happen onBootstrap");
			}

			SharedPrefHelper.putBoolean("nimple_app_launched", true, ctx);
		}
	}

	private void toggleInfoText() {
		if (listOfContacts.size() > 2) {
			noContactsTextView.setVisibility(View.GONE);
		} else {
			noContactsTextView.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		EventBus.getDefault().unregister(this);
	}

	public void onEvent(ContactAddedEvent ev) {
		listOfContacts.add(0, ev.getContact());
		contactsAdapter.notifyDataSetChanged();
		toggleInfoText();

		// display msg to user
		Toast.makeText(ctx, R.string.contact_added_toast, Toast.LENGTH_SHORT).show();
	}

	public void onEvent(ContactDeletedEvent ev) {
		listOfContacts.remove(ev.getContact());
		contactsAdapter.notifyDataSetChanged();
		toggleInfoText();
	}

	@Override
	public Export getExport() {
		StringBuilder sb = new StringBuilder();
		for (Contact con : listOfContacts) {
			sb.append(VCardHelper.getCardFromContact(con, ctx));
		}
		Export export = new Export<String>(sb.toString());
		export.setFilename("NimpleContacts");
		return export;
	}
}