package de.nimple.ui.edit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

import de.greenrobot.event.EventBus;
import de.nimple.R;
import de.nimple.enums.SocialNetwork;
import de.nimple.events.NimpleCodeChangedEvent;
import de.nimple.events.SocialConnectedEvent;
import de.nimple.events.SocialDisconnectedEvent;
import de.nimple.ui.edit.social.SocialLinkedinActivity;
import de.nimple.ui.edit.social.SocialTwitterActivity;
import de.nimple.ui.edit.social.SocialXingActivity;
import de.nimple.util.logging.Lg;
import de.nimple.util.nimplecode.Address;
import de.nimple.util.nimplecode.NimpleCodeHelper;

public class EditNimpleCodeActivity extends SherlockActivity {
	private Context ctx;

	// personal information
	@InjectView(R.id.firstnameEditText)
	public EditText firstname;
	@InjectView(R.id.lastnameEditText)
	public EditText lastname;
	@InjectView(R.id.mailEditText)
	public EditText mail;
	@InjectView(R.id.phoneEditText)
	public EditText phone;

	@InjectView(R.id.mailCheckbox)
	public CheckBox mailCheck;
	@InjectView(R.id.phoneCheckbox)
	public CheckBox phoneCheck;

	// business information
	@InjectView(R.id.companyEditText)
	public TextView company;
	@InjectView(R.id.positionEditText)
	public TextView position;

	@InjectView(R.id.companyCheckbox)
	public CheckBox companyCheck;
	@InjectView(R.id.positionCheckbox)
	public CheckBox positionCheck;
	
	@InjectView(R.id.websiteCheckbox)
	public CheckBox websiteCheck;
	@InjectView(R.id.websiteEditText)
	public EditText website;
	
	@InjectView(R.id.adressCheckbox)
	public CheckBox addressCheck;
	@InjectView(R.id.addressStreetEditText)
	public EditText addressStreet;
	@InjectView(R.id.addressPostalEditText)
	public EditText addressPostal;
	@InjectView(R.id.addressCityEditText)
	public EditText addressCity;
	
	// social information
	@InjectView(R.id.facebookRoundIcon)
	ImageView facebookImageView;
	@InjectView(R.id.twitterRoundIcon)
	ImageView twitterImageView;
	@InjectView(R.id.xingRoundIcon)
	ImageView xingImageView;
	@InjectView(R.id.linkedinRoundIcon)
	ImageView linkedinImageView;

	@InjectView(R.id.facebookTextView)
	TextView facebookTextView;
	@InjectView(R.id.twitterTextView)
	TextView twitterTextView;
	@InjectView(R.id.xingTextView)
	TextView xingTextView;
	@InjectView(R.id.linkedinTextView)
	TextView linkedinTextView;

	@InjectView(R.id.facebookCheckbox)
	public CheckBox facebookCheck;
	@InjectView(R.id.twitterCheckbox)
	public CheckBox twitterCheck;
	@InjectView(R.id.xingCheckbox)
	public CheckBox xingCheck;
	@InjectView(R.id.linkedinCheckbox)
	public CheckBox linkedinCheck;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.edit_ncard_screen);
		setSupportProgressBarIndeterminateVisibility(false);
		initActionBar();

		ButterKnife.inject(this);

		ctx = getApplicationContext();
		EventBus.getDefault().register(this);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}

	private void initActionBar() {
		/*View actionbarDoneButton = getLayoutInflater().inflate(R.layout.actionbar_done_button, null);
		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM,
				ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
		getSupportActionBar().setCustomView(actionbarDoneButton);
*/
        // BEGIN_INCLUDE (inflate_set_custom_view)
        // Inflate a "Done/Cancel" custom action bar view.
        final LayoutInflater inflater = (LayoutInflater) getSupportActionBar().getThemedContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View customActionBarView = inflater.inflate(
                R.layout.actionbar_done_cancel, null);
        customActionBarView.findViewById(R.id.actionbar_done).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // "Done"
                        onClickSave();
                    }
                });
        customActionBarView.findViewById(R.id.actionbar_cancel).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // "Cancel"
                        finish();
                    }
                });
        // Show the custom action bar view and hide the normal Home icon and title.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(
                ActionBar.DISPLAY_SHOW_CUSTOM,
                ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME
                        | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setCustomView(customActionBarView,
                new ActionBar.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
        // END_INCLUDE (inflate_set_custom_view)
        //setContentView(R.layout.actionbar_done_cancel);
    }

	public void onEvent(SocialConnectedEvent ev) {
		fillUi();
	}

	public void onEvent(SocialDisconnectedEvent ev) {
		fillUi();
	}

	@Override
	protected void onDestroy() {
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		fillUi();
	}

	public final static boolean isValidEmail(CharSequence target) {
		if (target == null) {
			return false;
		} else {
			return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
		}
	}

	private void fillUi() {
		// set all views
		NimpleCodeHelper ncode = new NimpleCodeHelper(ctx);

		firstname.setText(ncode.holder.firstname);
		lastname.setText(ncode.holder.lastname);
		mail.setText(ncode.holder.mail);
		phone.setText(ncode.holder.phone);
		website.setText(ncode.holder.websiteUrl);
		addressStreet.setText(ncode.holder.address.getStreet());
		addressPostal.setText(ncode.holder.address.getPostalCode());
		addressCity.setText(ncode.holder.address.getLocality());
		
		mailCheck.setChecked(ncode.holder.show.mail);
		phoneCheck.setChecked(ncode.holder.show.phone);

		company.setText(ncode.holder.company);
		position.setText(ncode.holder.position);

		companyCheck.setChecked(ncode.holder.show.company);
		positionCheck.setChecked(ncode.holder.show.position);
		
		websiteCheck.setChecked(ncode.holder.show.website);
		addressCheck.setChecked(ncode.holder.show.address);

		fillUiSocial(ncode);
	}

	private void fillUiSocial(NimpleCodeHelper ncode) {
		this.checkIfSocialMediaIsConnected(ncode.holder.facebookUrl,facebookTextView,facebookImageView,facebookCheck);
		this.checkIfSocialMediaIsConnected(ncode.holder.twitterUrl,twitterTextView,twitterImageView,twitterCheck);
		this.checkIfSocialMediaIsConnected(ncode.holder.xingUrl,xingTextView,xingImageView,xingCheck);
		this.checkIfSocialMediaIsConnected(ncode.holder.linkedinUrl,linkedinTextView,linkedinImageView,linkedinCheck);
		
		// to be set after the display logic as the current state will be saved
		facebookCheck.setChecked(ncode.holder.show.facebook);
		twitterCheck.setChecked(ncode.holder.show.twitter);
		xingCheck.setChecked(ncode.holder.show.xing);
		linkedinCheck.setChecked(ncode.holder.show.linkedin);
	}
	
	private void checkIfSocialMediaIsConnected(String url, TextView tv, ImageView iv, CheckBox cb) {
		if (url != null && url.length() != 0) {
			tv.setText(getText(R.string.social_connected));
			iv.setAlpha(255);
			cb.setVisibility(View.VISIBLE);
		} else {
			tv.setText(getText(R.string.social_disconnected));
			iv.setAlpha(30);
			cb.setVisibility(View.INVISIBLE);
		}
	}

	private void onClickSave() {
		if (performFormValidation())
			return;
		save();
		finish();
	}

	/**
	 * Performs form validation on mail, lastname and firstname
	 * 
	 * @return boolean, true if error occured, false if no error occurred
	 */
	private boolean performFormValidation() {
		boolean hasErrors = false;

		// form validation
		if (mail.getText().length() != 0 && !isValidEmail(mail.getText())) {
			mail.setError(getText(R.string.form_error_mail_invalid));
			mail.requestFocus();
			hasErrors = true;
		}

		if (lastname.getText().length() == 0) {
			lastname.setError(getText(R.string.form_error_lastname));
			lastname.requestFocus();
			hasErrors = true;
		}

		if (firstname.getText().length() == 0) {
			firstname.setError(getText(R.string.form_error_firstname));
			firstname.requestFocus();
			hasErrors = true;
		}

		return hasErrors;
	}

	private void save() {
		// read out all views and save them into sharedPreferences
		NimpleCodeHelper ncode = new NimpleCodeHelper(getApplicationContext());

		// EditPersonalFragment
		ncode.holder.firstname = firstname.getText().toString();
		ncode.holder.lastname = lastname.getText().toString();
		ncode.holder.mail = mail.getText().toString();

		ncode.holder.phone = phone.getText().toString();
		ncode.holder.show.mail = mailCheck.isChecked();
		ncode.holder.show.phone = phoneCheck.isChecked();
		ncode.holder.show.website = websiteCheck.isChecked();
		ncode.holder.show.address = addressCheck.isChecked();
		ncode.holder.websiteUrl = website.getText().toString();
		ncode.holder.address = new Address(addressStreet.getText().toString(),addressPostal.getText().toString(),addressCity.getText().toString());

		// EditSocialFragment
		ncode.holder.show.facebook = facebookCheck.isChecked();
		ncode.holder.show.twitter = twitterCheck.isChecked();
		ncode.holder.show.xing = xingCheck.isChecked();
		ncode.holder.show.linkedin = linkedinCheck.isChecked();

		// EditBusinessFragment
		ncode.holder.company = company.getText().toString();
		ncode.holder.position = position.getText().toString();
		ncode.holder.show.company = companyCheck.isChecked();
		ncode.holder.show.position = positionCheck.isChecked();

		ncode.save();
		EventBus.getDefault().post(new NimpleCodeChangedEvent());
	}

	@OnClick({ R.id.twitterTextView, R.id.twitterRoundIcon })
	void openConnectTwitterActivity() {
		if (twitterTextView.getText().length() == getString(R.string.social_disconnected).length()) {
			save();

			Intent intent = new Intent(ctx, SocialTwitterActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		} else {
			Toast.makeText(ctx, String.format(getString(R.string.social_disconnected_toast), "twitter"), Toast.LENGTH_LONG).show();
			EventBus.getDefault().post(new SocialDisconnectedEvent(SocialNetwork.TWITTER));
		}
	}

	@OnClick({ R.id.xingTextView, R.id.xingRoundIcon })
	void openConnectXingActivity() {
		if (xingTextView.getText().length() == getString(R.string.social_disconnected).length()) {
			save();

			Intent intent = new Intent(ctx, SocialXingActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		} else {
			Toast.makeText(ctx, String.format(getString(R.string.social_disconnected_toast), "xing"), Toast.LENGTH_LONG).show();
			EventBus.getDefault().post(new SocialDisconnectedEvent(SocialNetwork.XING));
		}
	}

	@OnClick({ R.id.linkedinTextView, R.id.linkedinRoundIcon })
	void openConnectLinkedinActivity() {
		if (linkedinTextView.getText().length() == getString(R.string.social_disconnected).length()) {
			save();

			Intent intent = new Intent(ctx, SocialLinkedinActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		} else {
			Toast.makeText(ctx, String.format(getString(R.string.social_disconnected_toast), "linkedin"), Toast.LENGTH_LONG).show();
			EventBus.getDefault().post(new SocialDisconnectedEvent(SocialNetwork.LINKEDIN));
		}
	}

	@OnClick({ R.id.facebookTextView, R.id.facebookRoundIcon })
	void openConnectFacebookActivity() {
		if (facebookTextView.getText().length() == getString(R.string.social_disconnected).length()) {
			save();

			// start Facebook Login
			Session.openActiveSession(this, true, new Session.StatusCallback() {
				// callback when session changes state
				@Override
				public void call(Session session, SessionState state, Exception exception) {
					if (session.isOpened()) {
						// make request to the /me API
						Request.newMeRequest(session, new Request.GraphUserCallback() {

							// callback after Graph API response with user object
							@Override
							public void onCompleted(GraphUser user, Response response) {
								if (user != null) {
									// String token = Session.getActiveSession().getAccessToken();
									Lg.d("Facebook login succeeded.");
									Toast.makeText(ctx, String.format(getString(R.string.social_connected_toast), "facebook"), Toast.LENGTH_LONG).show();
									EventBus.getDefault().post(new SocialConnectedEvent(SocialNetwork.FACEBOOK, user.getInnerJSONObject()));
								}
							}
						}).executeAsync();
					}
				}
			});
		} else {
			Toast.makeText(ctx, String.format(getString(R.string.social_disconnected_toast), "facebook"), Toast.LENGTH_LONG).show();
			EventBus.getDefault().post(new SocialDisconnectedEvent(SocialNetwork.FACEBOOK));
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.edit_card, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_discard:
			finish();
			return true;
		default:
			return false;
		}
	}
}