package de.nimple.services.upgrade;

import android.content.Context;

import de.nimple.util.SharedPrefHelper;

/**
 * Created by dennis on 19.10.2014.
 */
public class ProVersionHelper {
    private Context m_ctx;
    private boolean m_isPro = false;

    public ProVersionHelper(Context ctx){
        if(ctx == null){
            throw new IllegalArgumentException();
        }
        m_ctx =ctx;
        load();
    }

    public boolean checkIsPro(){
        boolean ret = false;
        return ret;
    }

    public void save(){
        SharedPrefHelper.putBoolean(IS_PRO_VERSION,m_isPro,m_ctx);
    }

    public void load(){
        m_isPro = SharedPrefHelper.getBoolean(IS_PRO_VERSION,m_ctx);
    }

    public boolean getIsPro(){
        return m_isPro;
    }

    public void setIsPro(boolean isPro){
        m_isPro = isPro;
    }


    /* --------------        CONSTANT   ------------------------------------       */
    private final String IS_PRO_VERSION = "isProVersion";

}
