package net.avenwu.yoyogithub.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Chaobin Wu on 6/1/16.
 */
public class Preference {
    static final String PROFILE_FILE = "profile";
    SharedPreferences mSharedPreference;

    static class Holder {
        static Preference INSTANCE = new Preference();
    }

    public static Preference get(Context context) {
        if (Holder.INSTANCE.mSharedPreference == null) {
            synchronized (Preference.class) {
                if (Holder.INSTANCE.mSharedPreference == null) {
                    Holder.INSTANCE.mSharedPreference = context.getSharedPreferences(PROFILE_FILE, Context.MODE_PRIVATE);
                }
            }
        }
        return Holder.INSTANCE;
    }

    final String ACCOUNT_NAME = "0x0001";

    public String getUserAccountName() {
        return mSharedPreference.getString(ACCOUNT_NAME, "");
    }

    public void setUserAccountName(String accountName) {
        mSharedPreference.edit().putString(ACCOUNT_NAME, accountName).apply();
    }
}
