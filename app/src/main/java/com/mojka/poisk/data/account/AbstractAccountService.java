package com.mojka.poisk.data.account;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.mojka.poisk.data.model.User;

public abstract class AbstractAccountService {
    private final String SHARED_PREFERENCES_NAME = "com.mojka.poisk.account";

    public static final String KEY_ID = "KEY_ID";
    public static final String KEY_NAME = "KEY_NAME";
    public static final String KEY_PHONE = "KEY_PHONE";
    public static final String KEY_CAR = "KEY_CAR_ID";
    public static final String KEY_CITY = "KEY_CITY";
    public static final String KEY_TOKEN = "KEY_TOKEN";

    private Context context;
    private SharedPreferences sharedPreferences;

    public AbstractAccountService(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public void setAccount(@NonNull User user) {
        sharedPreferences.edit()
                .putString(KEY_NAME, user.getName())
                .putString(KEY_PHONE, user.getPhone())
                .putString(KEY_CAR, user.getCar())
                .putString(KEY_CITY, user.getCity())
                .putString(KEY_TOKEN, user.getToken())
                .apply();
    }

    @NonNull
    public User getAccount() {
        return new User(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_PHONE, ""),
                sharedPreferences.getString(KEY_NAME, ""),
                sharedPreferences.getString(KEY_CITY, ""),
                sharedPreferences.getString(KEY_CAR, ""),
                sharedPreferences.getString(KEY_TOKEN, "")
        );
    }

    @NonNull
    public String getToken() {
        if (!isLogged())
            return "";

        return getAccount().getToken();
    }

    public void setParam(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public void setParam(String key, Integer value) {
        sharedPreferences.edit().putInt(key, value).apply();
    }

    public Boolean isLogged() {
        return !sharedPreferences.getString(KEY_PHONE, "").equals("");
    }

    public void logout() {
        sharedPreferences.edit().clear().apply();
    }
}
