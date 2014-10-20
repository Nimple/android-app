package de.nimple.ui.pro;

import android.os.Bundle;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import de.nimple.R;
import de.nimple.config.Config;
import de.nimple.dagger.BaseActivity;

/**
 * Created by dennis on 19.10.2014.
 */
public class ProActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pro);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.pro_button)
    public void purchaseProduct() {
        if(billing.purchase(Config.GOOGLE_PRODUCT_ID)) {
            Toast.makeText(ctx, ctx.getString(R.string.pro_purchase_succesfull),Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(ctx, ctx.getString(R.string.pro_purchase_err),Toast.LENGTH_SHORT).show();
        }
    }
}
