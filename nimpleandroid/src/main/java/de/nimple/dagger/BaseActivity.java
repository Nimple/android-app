package de.nimple.dagger;

import android.app.Activity;
import android.os.Bundle;

import butterknife.ButterKnife;
import de.nimple.NimpleApplication;

public class BaseActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		((NimpleApplication) getApplication()).inject(this);
	}

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		ButterKnife.inject(this);
	}
}
