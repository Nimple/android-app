package de.nimple.ui.main.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.actionbarsherlock.app.SherlockFragment;

import de.greenrobot.event.EventBus;
import de.nimple.R;
import de.nimple.events.NimpleCodeChangedEvent;
import de.nimple.util.DensityHelper;
import de.nimple.util.SharedPrefHelper;
import de.nimple.util.VersionResolver;
import de.nimple.util.nimplecode.QRCodeCreator;
import de.nimple.util.nimplecode.VCardHelper;

public class NimpleCodeFragment extends SherlockFragment {
	public static final NimpleCodeFragment newInstance() {
		return new NimpleCodeFragment();
	}

	private Context ctx;
	private View view;

	@InjectView(R.id.no_ncode_layout)
	RelativeLayout noNimpleCode;
	@InjectView(R.id.ncode_layout)
	RelativeLayout nimpleCode;

	@InjectView(R.id.arrow_up)
	ImageView arrowUp;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ctx = getSherlockActivity().getApplicationContext();
		view = inflater.inflate(R.layout.main_ncode_fragment, container, false);
		ButterKnife.inject(this, view);
		EventBus.getDefault().register(this);
		refreshUi();
		return view;
	}

	@Override
	public void onDestroyView() {
		EventBus.getDefault().unregister(this);
		super.onDestroyView();
	}

	public void onEvent(NimpleCodeChangedEvent ev) {
		refreshUi();
	}

	private void refreshUi() {
		// if nimple code exists create and show qr code
		if (checkForNimpleCode()) {
			ImageView imgQRCode = (ImageView) view.findViewById(R.id.codeImageView);
			String ownNimpleCode = VCardHelper.getCardFromSharedPrefs(ctx);
			Bitmap bitmap = QRCodeCreator.generateQrCode(ownNimpleCode);

			// S3 and S4 get confused about displaying bitmaps without a specific density,
			// and fail to scale them properly. Setting the density to this value helps
			// (is possible ANY density helps, but haven't tested this)
			bitmap.setDensity(DisplayMetrics.DENSITY_HIGH);

			imgQRCode.setImageBitmap(bitmap);
		}

		// if nimple code does NOT exist show "fill out nimple code text and button"
		toggleNimpleCode();
	}

	private void toggleNimpleCode() {
		if (!checkForNimpleCode()) {
			noNimpleCode.setVisibility(View.VISIBLE);
			nimpleCode.setVisibility(View.GONE);
			setCorrectMarginOfArrowUp();
		} else {
			noNimpleCode.setVisibility(View.GONE);
			nimpleCode.setVisibility(View.VISIBLE);
		}
	}

	private static boolean hasSoftNavigation(Context context) {
		return !ViewConfiguration.get(context).hasPermanentMenuKey();
	}

	private void setCorrectMarginOfArrowUp() {
		// default actionBarHeight in dp
		int actionBarHeight = 30;
		double factor = 0.5;

		if (VersionResolver.isAtLeastICS()) {
			if (hasSoftNavigation(ctx)) {
				factor = 1.5;
				actionBarHeight = 48;
			}
		}

		int arrowUpPaddingRight = (int) Math.round(actionBarHeight * factor);
		arrowUpPaddingRight = (int) DensityHelper.convertDpToPixel(arrowUpPaddingRight, ctx);

		RelativeLayout.LayoutParams layoutParams = (LayoutParams) arrowUp.getLayoutParams();
		layoutParams.setMargins(0, 0, arrowUpPaddingRight, 0);
		arrowUp.setLayoutParams(layoutParams);
	}

	private boolean checkForNimpleCode() {
		return SharedPrefHelper.getBoolean("nimple_code_init", ctx);
	}
}