package de.nimple.ui.main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ShareCompat;
import android.support.v4.app.ShareCompat.IntentBuilder;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.google.zxing.client.android.Intents;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import de.greenrobot.event.EventBus;
import de.nimple.R;
import de.nimple.events.ApplicationStartedEvent;
import de.nimple.events.ContactAddedEvent;
import de.nimple.events.DuplicatedContactEvent;
import de.nimple.events.NimpleCodeScanFailedEvent;
import de.nimple.events.NimpleCodeScannedEvent;
import de.nimple.ui.about.AboutNimpleActivity;
import de.nimple.ui.main.fragments.ContactListFragment;
import de.nimple.ui.main.fragments.ISaveExtender;
import de.nimple.ui.main.fragments.NimpleCardFragment;
import de.nimple.ui.main.fragments.NimpleCodeFragment;
import de.nimple.ui.parts.PagerSlidingTabStrip;
import de.nimple.util.export.Export;
import de.nimple.util.export.ExportHelper;
import de.nimple.util.logging.Lg;

public class MainActivity extends SherlockFragmentActivity {
	private static Context ctx;
	private NimplePagerAdapter adapter;

	@InjectView(R.id.tabs)
	PagerSlidingTabStrip tabs;
	@InjectView(R.id.pager)
	ViewPager pager;


	private static final int SCAN_REQUEST_CODE = 0x0000c0de;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.main);
		setSupportProgressBarIndeterminateVisibility(false);
		ButterKnife.inject(this);

		ctx = getApplicationContext();
		adapter = new NimplePagerAdapter(getSupportFragmentManager());

		pager.setAdapter(adapter);
		pager.setOffscreenPageLimit(2);

		tabs.setViewPager(pager);
		pager.setCurrentItem(1);

		EventBus.getDefault().register(this);
		EventBus.getDefault().post(new ApplicationStartedEvent());
	}

	@Override
	protected void onDestroy() {
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}

	public void onEvent(ContactAddedEvent ev) {
		// jump to contacts list
		pager.setCurrentItem(2);
		Toast.makeText(ctx, R.string.contact_added_toast, Toast.LENGTH_LONG).show();
	}

	public void onEvent(NimpleCodeScanFailedEvent ev) {
		Toast.makeText(ctx, R.string.contact_scan_failed, Toast.LENGTH_LONG).show();
	}

	public void onEvent(DuplicatedContactEvent ev) {
		Toast.makeText(ctx, String.format(getString(R.string.contact_scan_duplicated), ev.getContact().getName()), Toast.LENGTH_LONG).show();
}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.menu_add) {
			startScanner();
		} else if (item.getItemId() == R.id.menu_about) {
			startAboutNimpleActivity();
		} else if (item.getItemId() == R.id.menu_share) {
			shareApp();
		} else if (item.getItemId() == R.id.menu_feedback) {
			sendFeedback();
		} else if (item.getItemId() == R.id.menu_save){
            save();
        }

		return super.onOptionsItemSelected(item);
	}

    private void save() {
        int id = pager.getCurrentItem();
        Fragment frag = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + id);
        final Export export = ((ISaveExtender) frag).getExport();

       LayoutInflater layoutInflater
                = (LayoutInflater)getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.popup_export, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView exportMail = (TextView)popupView.findViewById(R.id.export_menu_mail);
        TextView exportSave = (TextView)popupView.findViewById(R.id.export_menu_save);
        exportMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MAybe it can be solved with temp file
                File file = new File(export.getPath(), export.getFilename() + export.getExtension());
                ExportHelper.save(export, file);
                sendEmail(Uri.fromFile(file));
                popupWindow.dismiss();
            }
        });
        exportSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(export.getPath(), export.getFilename() + export.getExtension());
                if(ExportHelper.save(export, file)){
                    Toast.makeText(ctx,"Erfolgreich auf SD-Karte gespeichert",Toast.LENGTH_SHORT);
                }else{
                    Toast.makeText(ctx,"Fehler beim Speichern",Toast.LENGTH_SHORT);
                }
                popupWindow.dismiss();
            }
        });
        popupWindow.showAsDropDown(tabs, 50, -30);
    }

	private void sendFeedback() {
		IntentBuilder intentBuilder = ShareCompat.IntentBuilder.from(this);
		intentBuilder.setType("message/rfc822");
		intentBuilder.addEmailTo(getString(R.string.feedback_email));
		intentBuilder.setSubject(getString(R.string.feedback_subject));
		intentBuilder.setText(getString(R.string.feedback_text));
		intentBuilder.setChooserTitle(getString(R.string.feedback_chooser));

		Intent intent = intentBuilder.getIntent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	private void shareApp() {
		sendEmail(null);
	}

    private void sendEmail(Uri attachment){
        IntentBuilder intentBuilder = ShareCompat.IntentBuilder.from(this);
        intentBuilder.setType("text/plain");
        intentBuilder.setSubject(getString(R.string.share_app_subject));
        intentBuilder.setText(getString(R.string.share_app_text));
        if(attachment != null) {
            intentBuilder.setStream(attachment);
        }
        intentBuilder.setChooserTitle(getString(R.string.share_app_chooser));

        Intent intent = intentBuilder.getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

	private void startAboutNimpleActivity() {
		Intent intent = new Intent(ctx, AboutNimpleActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		ctx.startActivity(intent);
	}

	private class NimplePagerAdapter extends FragmentPagerAdapter {
		public NimplePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public CharSequence getPageTitle(int pos) {
			switch (pos) {
			case 0:
				return getString(R.string.ncard_title);
			case 1:
				return getString(R.string.ncode_title);
			case 2:
				return getString(R.string.contacts_title);
			default:
				return null;
			}
		}

		@Override
		public Fragment getItem(int pos) {
			switch (pos) {
			case 0:
				return NimpleCardFragment.newInstance();
			case 1:
				return NimpleCodeFragment.newInstance();
			case 2:
				return ContactListFragment.newInstance();
			default:
				return null;
			}
		}

		@Override
		public int getCount() {
			return 3;
		}
	}

	private void startScanner() {
		Display display = getWindowManager().getDefaultDisplay();
		int w = display.getWidth();
		int h = display.getHeight();

		Intent intent = new Intent(Intents.Scan.ACTION);
		intent.setPackage("de.nimple");
		intent.putExtra(Intents.Scan.FORMATS, "QR_CODE");
		intent.putExtra(Intents.Scan.WIDTH, w);
		intent.putExtra(Intents.Scan.HEIGHT, h / 2);
		startActivityForResult(intent, SCAN_REQUEST_CODE);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		Lg.d("reqestCode:" + requestCode + ", resultCode:" + resultCode);

		if (requestCode == SCAN_REQUEST_CODE) {
			IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
			if (scanResult.getContents() != null) {
				String data = scanResult.getContents();
				Lg.d("scan result: " + data);
				EventBus.getDefault().post(new NimpleCodeScannedEvent(data));
			}
		}
	}
}