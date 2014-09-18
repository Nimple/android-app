package de.nimple.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ShareCompat;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.nimple.R;
import de.nimple.util.export.Export;
import de.nimple.util.export.ExportHelper;

/**
 * Created by dennis on 18.09.2014.
 */
public class ExportDialog extends PopupWindow {

    private Export export;
    private Context ctx;
    private Activity activity;
    private TextView exportMail;
    private TextView exportSave;
    private TextView exportCancel;

    public ExportDialog(View contentView, int width, int height, Export export,Activity activity){
        super(contentView, width, height);
        this.export = export;
        this.ctx = activity.getApplicationContext();
        this.activity = activity;
        ButterKnife.inject(contentView);
        exportMail = (TextView)contentView.findViewById(R.id.export_menu_mail);
        exportSave = (TextView)contentView.findViewById(R.id.export_menu_save);
        exportCancel = (TextView)contentView.findViewById(R.id.export_menu_cancel);
        init();
    }

    private void init(){
        exportMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFile();
            }
        });
        exportSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFile();
            }
        });
        exportCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void sendFile(){
        File file = new File(export.getPath(), export.getFilename() + export.getExtension());
        ExportHelper.save(export, file);
        sendEmail(Uri.fromFile(file));
        this.dismiss();
    }

    public void saveFile(){
        File file = new File(export.getPath(), export.getFilename() + export.getExtension());
        if(ExportHelper.save(export, file)){
            Toast.makeText(ctx,ctx.getString(R.string.export_save), Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(ctx,ctx.getString(R.string.export_save_err),Toast.LENGTH_SHORT).show();
        }
        this.dismiss();
    }

    private void sendEmail(Uri attachment){
        ShareCompat.IntentBuilder intentBuilder = ShareCompat.IntentBuilder.from(activity);
        intentBuilder.setType("text/plain");
        intentBuilder.setSubject(ctx.getString(R.string.share_app_subject));
        intentBuilder.setText(ctx.getString(R.string.share_app_text));
        if(attachment != null) {
            intentBuilder.setStream(attachment);
        }
        intentBuilder.setChooserTitle(ctx.getString(R.string.share_app_chooser));

        Intent intent = intentBuilder.getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(intent);
    }
}
