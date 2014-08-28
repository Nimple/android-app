package de.nimple.persistence;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import de.greenrobot.dao.query.QueryBuilder;
import de.nimple.domain.Contact;
import de.nimple.domain.ContactDao;
import de.nimple.domain.ContactDao.Properties;
import de.nimple.domain.DaoMaster;
import de.nimple.domain.DaoMaster.DevOpenHelper;
import de.nimple.domain.DaoSession;
import de.nimple.exceptions.DuplicatedContactException;
import de.nimple.util.logging.Lg;

public class ContactsPersistenceManager {
	private static ContactsPersistenceManager instance;
	private ContactDao contactDao;

	public static ContactsPersistenceManager getInstance(Context ctx) {
		if (instance == null) {
			instance = new ContactsPersistenceManager(ctx);
		}
		return instance;
	}

	private ContactsPersistenceManager(Context ctx) {
		DevOpenHelper helper = new DatabaseHelper(ctx, "nimple-db", null);
		SQLiteDatabase db = helper.getWritableDatabase();
		DaoMaster daoMaster = new DaoMaster(db);
		DaoSession daoSession = daoMaster.newSession();
		contactDao = daoSession.getContactDao();
	}

	public Contact persist(Contact contact) throws DuplicatedContactException {
		Lg.d("persisting contact " + contact.getName());

		// check for duplicates
		if (isContactWithHashExisting(contact)) {
			Lg.e("contact already exists");
			throw new DuplicatedContactException();
		}

		contactDao.insert(contact);
		return contact;
	}

	public Contact findContactById(long id) {
		return contactDao.loadByRowId(id);
	}

	public boolean isContactWithHashExisting(Contact c) {
		String hash = c.getHash();
		Lg.d("checking existance of contact with hash: " + hash);
		long amt = contactDao.queryBuilder().where(Properties.Hash.eq(hash)).count();
		return (amt > 0);
	}

	public Contact update(Contact contact) {
		Lg.d("updating contact " + contact.getName());
		contactDao.update(contact);
		return contact;
	}

	public void remove(Contact contact) {
		Lg.d("removing contact " + contact.getName());
		contactDao.delete(contact);
	}

	public List<Contact> findAllContacts() {
		Lg.d("Looking up all contacts ordered");
		QueryBuilder<Contact> query = contactDao.queryBuilder();
		query.orderRaw("created DESC");
		return query.list();
	}
}