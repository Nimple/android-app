package de.nimple.ui.main;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.myappfree.appvalidator.AppValidator;

import butterknife.InjectView;
import de.greenrobot.event.EventBus;
import de.nimple.R;
import de.nimple.dagger.BaseActivity;
import de.nimple.events.ApplicationStartedEvent;
import de.nimple.events.ContactAddedEvent;
import de.nimple.events.DuplicatedContactEvent;
import de.nimple.events.NimpleCodeScanFailedEvent;
import de.nimple.events.NimpleCodeScannedEvent;
import de.nimple.services.upgrade.ProVersionHelper;
import de.nimple.ui.main.fragments.ContactListFragment;
import de.nimple.ui.main.fragments.NimpleCardFragment;
import de.nimple.ui.main.fragments.NimpleCodeFragment;
import de.nimple.ui.parts.PagerSlidingTabStrip;
import de.nimple.util.Lg;

public class MainActivity extends BaseActivity {
    private static Context ctx;
    private NimplePagerAdapter adapter;

    @InjectView(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @InjectView(R.id.pager)
    ViewPager pager;

    public static final int SCAN_REQUEST_CODE = 0x0000c0de;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.main);
        setProgressBarIndeterminateVisibility(false);

        ctx = getApplicationContext();
        adapter = new NimplePagerAdapter(getFragmentManager());

        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(2);

        tabs.setViewPager(pager);
        pager.setCurrentItem(1);

        //EventBus.getDefault().register(this);
        EventBus.getDefault().post(new ApplicationStartedEvent());
        //Checked Google every time the app starts till it was bought, could be optimized
/*        if(!ProVersionHelper.getInstance(ctx).IsPro()){
          //  billing.loadOwnedPurchasesFromGoogle();
       }*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppValidator.isIapToUnlock(this, new AppValidator.OnAppValidatorListener() {
            @Override
            public void validated() {
                ProVersionHelper.getInstance(ctx).setPro(true);
                AppValidator.showDialog(MainActivity.this, "You have unlocked a special content for free by using myAppFree");
            }
        });
    }

    @Override
    protected void onDestroy() {
        //EventBus.getDefault().unregister(this);

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Lg.d("requestCode:" + requestCode + ", resultCode:" + resultCode);

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