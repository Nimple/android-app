package de.nimple.ui.main.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.actionbarsherlock.app.SherlockFragment;

import de.greenrobot.event.EventBus;
import de.nimple.R;
import de.nimple.events.NimpleCodeChangedEvent;
import de.nimple.ui.edit.EditNimpleCodeActivity;
import de.nimple.util.export.Export;
import de.nimple.util.nimplecode.Address;
import de.nimple.util.nimplecode.NimpleCodeHelper;
import de.nimple.util.nimplecode.VCardHelper;

public class NimpleCardFragment extends SherlockFragment implements ISaveExtender {
	public static final NimpleCardFragment newInstance() {
		return new NimpleCardFragment();
	}

	@InjectView(R.id.nameTextView)
	TextView nameTextView;
	@InjectView(R.id.mailTextView)
	TextView mailTextView;
	@InjectView(R.id.phoneTextView)
	TextView phoneTextView;
	@InjectView(R.id.companyTextView)
	TextView companyTextView;
	@InjectView(R.id.jobTextView)
	TextView positionTextView;
	@InjectView(R.id.websiteTextView)
	TextView websiteTextView;
	@InjectView(R.id.addressStreetTextView)
	TextView addressStreetTextView;
	@InjectView(R.id.addressCityTextView)
	TextView addressCityTextView;
	
	@InjectView(R.id.facebookRoundIcon)
	ImageView facebookImageView;
	@InjectView(R.id.twitterRoundIcon)
	ImageView twitterImageView;
	@InjectView(R.id.xingRoundIcon)
	ImageView xingImageView;
	@InjectView(R.id.linkedinRoundIcon)
	ImageView linkedinImageView;

	@InjectView(R.id.ncard_layout)
	RelativeLayout ncardLayout;

    @InjectView(R.id.spinner)
    Spinner spinner;

	private Context ctx;
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ctx = getSherlockActivity().getApplicationContext();
		view = inflater.inflate(R.layout.main_ncard_fragment, container, false);
		ButterKnife.inject(this, view);
		EventBus.getDefault().register(this);
        addSpinnerFunc();
		refreshUi();
		return view;
	}

    private void addSpinnerFunc(){
        spinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        NimpleCodeHelper.setCurrentId(NimpleCodeHelper.NC_CARD_1);
                        break;
                    case 1:
                        NimpleCodeHelper.setCurrentId(NimpleCodeHelper.NC_CARD_2);
                        break;
                }
                refreshUi();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		EventBus.getDefault().unregister(this);
	}

	public void onEvent(NimpleCodeChangedEvent ev) {
		refreshUi();
	}

	// @OnClick(R.id.ncard_layout) public void showEditHint() {
	// Toast.makeText(ctx, R.string.edit_ncard_hint, Toast.LENGTH_LONG).show();
	// }

	@OnClick(R.id.edit_nimple_card)
	public void startEditNimpleCodeActivity() {
		Intent intent = new Intent(ctx, EditNimpleCodeActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		ctx.startActivity(intent);
	}

	private void refreshUi() {
		NimpleCodeHelper ncode = new NimpleCodeHelper(ctx);

		if (!ncode.isInitialState()) {
			String name = ncode.holder.firstname + " " + ncode.holder.lastname;
			String phone = ncode.holder.phone;
			String mail = ncode.holder.mail;
			String company = ncode.holder.company;
			String position = ncode.holder.position;
			String website  = ncode.holder.websiteUrl;
			Address address = ncode.holder.address;

			nameTextView.setText(name);
			phoneTextView.setText(phone);
			mailTextView.setText(mail);
			companyTextView.setText(company);
			positionTextView.setText(position);
			websiteTextView.setText(website);
			
			addressStreetTextView.setText(address.getStreet());
			addressCityTextView.setText(address.getPostalCode() + " " + address.getLocality());
		}

		this.checkIfTextViewIsEmpty(ncode.holder.show.phone, phoneTextView);
		this.checkIfTextViewIsEmpty(ncode.holder.show.mail, mailTextView);
		this.checkIfTextViewIsEmpty(ncode.holder.show.company, companyTextView);
		this.checkIfTextViewIsEmpty(ncode.holder.show.position, positionTextView);
		this.checkIfTextViewIsEmpty(ncode.holder.show.website, websiteTextView);
		this.checkIfTextViewIsEmpty(ncode.holder.show.address, addressStreetTextView);
		this.checkIfTextViewIsEmpty(ncode.holder.show.address, addressCityTextView);
		
		this.checkIfSocialMediaIsEmpty(ncode.holder.show.facebook,ncode.holder.facebookUrl,facebookImageView);
		this.checkIfSocialMediaIsEmpty(ncode.holder.show.twitter,ncode.holder.twitterUrl,twitterImageView);
		this.checkIfSocialMediaIsEmpty(ncode.holder.show.xing,ncode.holder.xingUrl,xingImageView);
		this.checkIfSocialMediaIsEmpty(ncode.holder.show.linkedin,ncode.holder.linkedinUrl,linkedinImageView);
	}
	
	private void checkIfTextViewIsEmpty(boolean isShown, TextView vi){
		if(isShown){
			vi.setTextColor(getResources().getColor(R.color.nimple_default_text_color));
		} else {
			vi.setTextColor(getResources().getColor(R.color.nimple_text_color_hint));
		}
	}
	
	private void checkIfSocialMediaIsEmpty(boolean isShown,String content, ImageView iv){
		if(isShown && content.length() != 0){
			iv.setAlpha(255);
		} else {
			iv.setAlpha(30);
		}
	}

    @Override
    public Export getExport() {
        return new Export<String>(VCardHelper.getCardFromSharedPrefs(ctx));
    }
}