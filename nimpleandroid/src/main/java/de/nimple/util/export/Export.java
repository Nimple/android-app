package de.nimple.util.export;

import android.graphics.Bitmap;

/**
 * Created by dennis on 16.09.2014.
 */
public class Export<T>{

    private T content;
    private Type type;

    public Export(T content){
        this.content = content;
        if(content instanceof  String){
            type = Type.VCard;
        }else if(content instanceof Bitmap){
            type = Type.Barcode;
        }
    }

    public T getContent(){
        return content;
    }

    public Type getType(){
        return type;
    }

    public static enum Type{
        VCard,
        Barcode
    }
}


