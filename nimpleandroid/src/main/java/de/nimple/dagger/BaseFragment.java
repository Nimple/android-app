package de.nimple.dagger;

import android.app.Activity;
import android.app.Fragment;

import de.nimple.NimpleApplication;

public class BaseFragment extends Fragment {
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((NimpleApplication) activity.getApplication()).inject(this);
	}
}
