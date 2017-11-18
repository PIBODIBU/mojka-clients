package com.mojka.poisk.data.account;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.mojka.poisk.data.model.User;

public abstract class AbstractAccountService {
    private final String SHARED_PREFERENCES_NAME = "com.mojka.poisk.account";

    private final String KEY_ID = "KEY_ID";
    private final String KEY_NAME = "KEY_NAME";
    private final String KEY_PHONE = "KEY_PHONE";
    private final String KEY_CAR = "KEY_CAR";
    private final String KEY_CITY = "KEY_CITY";
    private final String KEY_TOKEN = "KEY_TOKEN";

    private Context context;
    private SharedPreferences sharedPreferences;

    public AbstractAccountService(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public void addAccount(@NonNull User user) {
        sharedPreferences.edit()
                .putInt(KEY_ID, user.getId())
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
                sharedPreferences.getString(KEY_NAME, ""),
                sharedPreferences.getString(KEY_PHONE, ""),
                sharedPreferences.getString(KEY_CAR, ""),
                sharedPreferences.getString(KEY_CITY, ""),
                sharedPreferences.getString(KEY_TOKEN, "")
        );
    }
}
