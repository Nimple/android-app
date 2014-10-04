package de.nimple.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.PopupWindow;
import android.widget.Spinner;

import de.nimple.R;
import de.nimple.services.nimplecode.NimpleCodeHelper;

/**
 * Created by dennis on 01.10.2014.
 */
public class NCardPopUpDialog extends PopupWindow {
    
    private Context ctx;
    private Activity activity;
    private Spinner spinner;

    public NCardPopUpDialog(View contentView, int width, int height, Activity activity){
        super(contentView, width, height);
        this.activity = activity;
        init();
    }

    private void init() {
        this.ctx = activity.getApplicationContext();
       // ButterKnife.inject(this.getContentView());
        spinner = (Spinner)this.getContentView().findViewById(R.id.spinnerCards);
      //  spinner.setAdapter(new ArrayAdapter<NimpleCard>(activity, android.R.layout.simple_spinner_dropdown_item, NimpleCodeHelper.getCards(ctx)));
        String[] cards = new String[1];
        cards[0] = NimpleCodeHelper.getCards(ctx).get(0).toString();
        spinner.setAdapter(new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, cards));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               /* NimpleCodeHelper.setCurrentId(((NimpleCard)spinner.getSelectedItem()).getId());
                dismiss();*/
               // Toast.makeText(ctx, "Test", Toast.LENGTH_LONG);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //DO nothing
            }
        });
    }
}
