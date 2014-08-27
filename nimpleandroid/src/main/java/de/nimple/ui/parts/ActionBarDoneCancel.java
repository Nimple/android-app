package de.nimple.ui.parts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.ActionBar;

import de.nimple.R;

final public class ActionBarDoneCancel {
	public static void apply(final ActionBarDoneCancelCallback callback, final ActionBar actionBar) {
		LayoutInflater inflater = (LayoutInflater) actionBar.getThemedContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View v = inflater.inflate(R.layout.actionbar_done_cancel, null);
        v.findViewById(R.id.actionbar_done).setBackgroundColor(v.getResources().getColor(R.color.nimple_color));
		v.findViewById(R.id.actionbar_done).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				callback.onDoneCallback();
			}
		});
        v.findViewById(R.id.actionbar_cancel).setBackgroundColor(v.getResources().getColor(R.color.nimple_color));
		v.findViewById(R.id.actionbar_cancel).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				callback.onCancelCallback();
			}
		});
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM, ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
		actionBar.setCustomView(v, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
	}

	public interface ActionBarDoneCancelCallback {
		public abstract void onDoneCallback();

		public abstract void onCancelCallback();
	}
}