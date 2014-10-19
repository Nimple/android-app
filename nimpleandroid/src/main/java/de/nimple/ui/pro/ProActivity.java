package de.nimple.ui.pro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import de.nimple.R;
import de.nimple.ui.edit.EditNimpleCodeActivity;

/**
 * Created by dennis on 19.10.2014.
 */
public class ProActivity extends Activity {

    private Context ctx;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pro);
        ctx = getApplicationContext();
        ButterKnife.inject(this);
    }

    @OnClick(R.id.pro_button)
    public void startEditNimpleCodeActivity() {
        Intent intent = new Intent(ctx, EditNimpleCodeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(intent);
    }
}
