package de.nimple.dagger;

import android.app.Activity;
import android.os.Bundle;

import javax.inject.Inject;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.nimple.events.NoOpEvent;

public abstract class BaseActivity extends Activity {
	@Inject
	EventBus eventBus;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		((DaggerApplication) getApplication()).inject(this);
		eventBus.register(this);
	}

	@Override
	protected void onDestroy() {
		eventBus.unregister(this);
		super.onDestroy();
	}

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		ButterKnife.inject(this);
	}

	public void onEvent(NoOpEvent ev) {}
}
