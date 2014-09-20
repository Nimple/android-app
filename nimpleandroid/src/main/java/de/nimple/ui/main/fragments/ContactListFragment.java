package de.nimple.ui.main.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;
import de.nimple.R;
import de.nimple.domain.Contact;
import de.nimple.events.ContactAddedEvent;
import de.nimple.events.ContactDeletedEvent;
import de.nimple.persistence.ContactsPersistenceManager;
import de.nimple.util.export.Export;
import de.nimple.util.export.IExportExtender;
import de.nimple.util.fragment.MenuHelper;
import de.nimple.util.nimplecode.VCardHelper;

public class ContactListFragment extends Fragment implements IExportExtender {
	public static final ContactListFragment newInstance() {
		return new ContactListFragment();
	}

	private Context ctx;
	private List<Contact> listOfContacts;
	private ContactsListAdapter contactsAdapter;

	@InjectView(R.id.contactsList)
	ListView contactsList;
	@InjectView(R.id.noContactsTextView)
	TextView noContactsTextView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ctx = getActivity().getApplicationContext();
		final View view = inflater.inflate(R.layout.contacts_fragment, container, false);
		ButterKnife.inject(this, view);

		listOfContacts = ContactsPersistenceManager.getInstance(ctx).findAllContacts();

		EventBus.getDefault().register(this);

		contactsAdapter = new ContactsListAdapter(this.getActivity(), R.layout.single_contact, listOfContacts, inflater);
		contactsList.setAdapter(contactsAdapter);

		toggleInfoText();
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.contacts_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuHelper.selectMenuItem(item, this);
        return super.onOptionsItemSelected(item);
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

	public static void deleteContact(Context ctx, Contact c) {
		ContactsPersistenceManager.getInstance(ctx).remove(c);
		EventBus.getDefault().post(new ContactDeletedEvent(c));
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