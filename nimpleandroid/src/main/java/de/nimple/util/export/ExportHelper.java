package de.nimple.util.export;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by dennis on 17.09.2014.
 */
public class ExportHelper {

    public static boolean save(Export export, File file) {
        if (export == null) {
            return false;
        }

        if (export.getType() == Export.Type.Barcode) {
            return ExportHelper.saveBitmap(file, (Bitmap) export.getContent());
        } else if (export.getType() == Export.Type.VCard) {
            return ExportHelper.saveText(file,(String) export.getContent());
        }

        return false;
    }

    public static boolean saveBitmap(File file, Bitmap content){
        try {
            FileOutputStream fOut = null;
            fOut = new FileOutputStream(file);
            content.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean saveText(File file, String content){
        try {
            FileOutputStream fOut = null;
            fOut = new FileOutputStream(file);
            fOut.write(content.getBytes());
            fOut.flush();
            fOut.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
