package de.nimple.dagger;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((DaggerApplication) activity.getApplication()).inject(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(getFragmentLayout(), container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		ButterKnife.inject(this, view);
	}

	/**
	 * Every fragment has to inflate a layout in the onCreateView method. We have added this method to
	 * avoid duplicate all the inflate code in every fragment. You only have to return the layout to
	 * inflate in this method when extends BaseFragment.
	 */
	protected abstract int getFragmentLayout();
}
