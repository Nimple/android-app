package de.nimple.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import de.nimple.domain.DaoMaster;
import de.nimple.util.logging.Lg;

public class DatabaseHelper extends DaoMaster.DevOpenHelper {
	public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
		super(context, name, factory);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (oldVersion == 1 && newVersion == 2) {
			Lg.d("Upgrading schema from version " + oldVersion + " to " + newVersion + " by altering contacts table");

			db.execSQL("ALTER TABLE contacts ADD website TEXT");

			db.execSQL("ALTER TABLE contacts ADD street TEXT");
			db.execSQL("ALTER TABLE contacts ADD postal TEXT");
			db.execSQL("ALTER TABLE contacts ADD city TEXT");

			db.execSQL("UPDATE contacts SET website = '' WHERE website IS NULL");

			db.execSQL("UPDATE contacts SET street = '' WHERE street IS NULL");
			db.execSQL("UPDATE contacts SET postal = '' WHERE postal IS NULL");
			db.execSQL("UPDATE contacts SET city = '' WHERE city IS NULL");
		} else {
			Lg.d("Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
			DaoMaster.dropAllTables(db, true);
			onCreate(db);
		}
	}
}