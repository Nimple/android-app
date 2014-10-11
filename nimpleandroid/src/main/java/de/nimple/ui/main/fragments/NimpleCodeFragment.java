package de.nimple.ui.main.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.nimple.R;
import de.nimple.events.NimpleCardChangedEvent;
import de.nimple.events.NimpleCodeChangedEvent;
import de.nimple.services.export.Export;
import de.nimple.services.export.IExportExtender;
import de.nimple.services.nimplecode.NimpleCodeHelper;
import de.nimple.services.nimplecode.QRCodeCreator;
import de.nimple.services.nimplecode.VCardHelper;
import de.nimple.util.DensityHelper;
import de.nimple.util.NimpleCard;
import de.nimple.util.SharedPrefHelper;
import de.nimple.util.VersionResolver;
import de.nimple.util.fragment.MenuHelper;

public class NimpleCodeFragment extends Fragment implements IExportExtender {
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

    @InjectView(R.id.ncard_name)
    TextView nCardName;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ctx = getActivity().getApplicationContext();
		view = inflater.inflate(R.layout.main_ncode_fragment, container, false);
		ButterKnife.inject(this, view);
		EventBus.getDefault().register(this);
		//addSpinnerFunc();
        onBootstrap();
		refreshUi();
        setHasOptionsMenu(true);
        return view;
    }

    private void onBootstrap(){
        //is necassary for compatibility
        NimpleCodeHelper.initCardNameFunctionality(ctx);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.code_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuHelper.selectMenuItem(item, this);
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.ncard_listShow})
    public void showNCardList(){
        LayoutInflater layoutInflater
                = (LayoutInflater)getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.cards_popup_row, null);
        final ListPopupWindow popupDialog = new ListPopupWindow(getActivity(),null);
        ArrayAdapter<NimpleCard> adaper = new ArrayAdapter<NimpleCard>(
                getActivity(),
                R.layout.cards_popup_row, R.id.textView, NimpleCodeHelper.getCards(getActivity()));
        popupDialog.setAdapter(adaper);
        popupDialog.setAnchorView(getActivity().findViewById(R.id.tabs));
        popupDialog.setWidth(300);
        popupDialog.setHorizontalOffset(-10);
        popupDialog.setVerticalOffset(65);
        popupDialog.setModal(true);
        for(int i = 0; i < adaper.getCount(); i++){
            if( ((NimpleCard)adaper.getItem(i)).getId() == NimpleCodeHelper.getCurrentId()){
               //TODO
                i = adaper.getCount();
            }
        }
        popupDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NimpleCard cards = (NimpleCard) parent.getAdapter().getItem(position);
                NimpleCodeHelper.setCurrentId(cards.getId());
                refreshUi();
                EventBus.getDefault().post(new NimpleCodeChangedEvent());
                popupDialog.dismiss();
            }
        });
        popupDialog.show();
    }

    @OnClick({R.id.ncard_add})
    public void addCard(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getString(R.string.add_ncard_question));
        builder.setCancelable(true);
        builder.setPositiveButton(getString(R.string.button_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                NimpleCodeHelper.addCard(ctx);
            }
        });
        builder.setNegativeButton(getString(R.string.button_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @OnClick({R.id.ncard_del})
    public void delCard(){
        final NimpleCodeHelper ncode = new NimpleCodeHelper(ctx);
        if(ncode.holder.id != 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(getString(R.string.del_ncard_question));
            builder.setCancelable(true);
            builder.setPositiveButton(getString(R.string.button_ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ncode.delete(ncode.holder);
                    ncode.setCurrentId(0);
                    refreshUi();
                    EventBus.getDefault().post(new NimpleCodeChangedEvent());
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton(getString(R.string.button_cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }else{
            Toast.makeText(ctx, "Die letzte Nimple Karte kann nicht gelöscht werden", Toast.LENGTH_SHORT).show();
        }
    }

	@Override
	public void onDestroyView() {
		EventBus.getDefault().unregister(this);
		super.onDestroyView();
	}

	public void onEvent(NimpleCodeChangedEvent ev) {
		refreshUi();
	}

    public void onEvent(NimpleCardChangedEvent ev) {
        NimpleCodeHelper ncode = new NimpleCodeHelper(ctx);
        nCardName.setText(ncode.holder.cardName);
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
            NimpleCodeHelper ncode = new NimpleCodeHelper(ctx);
            nCardName.setText(ncode.holder.cardName);
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

	@Override
	public Export getExport() {
		return new Export<Bitmap>(QRCodeCreator.generateQrCode(VCardHelper.getCardFromSharedPrefs(ctx)));
	}
}