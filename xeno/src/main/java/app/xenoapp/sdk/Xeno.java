package app.xenoapp.sdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.UUID;
import app.xenoapp.sdk.ui.XenoActivity;

public class Xeno {

    private static String packageName = "app.xenoapp.sdk";
    private static Xeno instance;
    private XenoIdentity identity;
    private Boolean identified;
    private String apiKey;
    private String randomToken;
    private String locale;
    private String color = "#27c469";
    private Context context;

    private Xeno(Context context, String apiKey) {
        this.context = context;
        getOrCreateRandomToken();
        this.identity = new XenoIdentity();
        this.identity.setId(randomToken);
        this.identified = false;
        this.apiKey = apiKey;
    }

    //    DEPRECATED
    private Xeno(Context context, String apiKey, String secretKey) {
        Log.e(packageName, "The secretKey argument is deprecated. Please see https://xenoapp.help/hc/developers-documentation/android-sdk/automatically-identify-your-users-on-your-android-apps");
    }

    private Xeno(Context context, String apiKey, XenoIdentity identity) {
        this.context = context;
        this.apiKey = apiKey;
        this.identity = identity;
        this.identified = true;
    }

    //    DEPRECATED
    private Xeno(Context context, String apiKey, XenoIdentity identity, String secretKey) {
        Log.e(packageName, "The secretKey argument is deprecated. Please see https://xenoapp.help/hc/developers-documentation/android-sdk/automatically-identify-your-users-on-your-android-apps");
    }

    public static void initialize(Context context, String apiKey) {
        instance = new Xeno(context, apiKey);
    }

    //    DEPRECATED
    public static void initialize(Context context, String apiKey, String secretKey) {
        Log.e(packageName, "The secretKey argument is deprecated. Please see https://xenoapp.help/hc/developers-documentation/android-sdk/automatically-identify-your-users-on-your-android-apps");
    }

    public static void initialize(Context context, String apiKey, XenoIdentity identity) {
        instance = new Xeno(context, apiKey, identity);
    }

    //    DEPRECATED
    public static void initialize(Context context, String apiKey, String secretKey, XenoIdentity identity) {
        Log.e(packageName, "The secretKey argument is deprecated. Please see https://xenoapp.help/hc/developers-documentation/android-sdk/automatically-identify-your-users-on-your-android-apps");
    }


    public static Xeno getInstance() {
        if (instance == null) {
            Log.e(packageName, "You need to call add Xeno.initialize(\"getContext()\")");
        }
        return instance;
    }

    public static void show() {
        Intent intent = new Intent(getInstance().context, XenoActivity.class);
        intent.putExtra("LOADER_COLOR", getInstance().color);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getInstance().context.startActivity(intent);
        ((Activity) getInstance().context).overridePendingTransition(R.anim.slide_in_up, R.anim.nothing);
    }


    private void getOrCreateRandomToken() {
        randomToken = context.getSharedPreferences(packageName, Context.MODE_PRIVATE).getString("xenotoken", null);
        if (randomToken != null) {
            return;
        }
        SharedPreferences.Editor editor = context.getSharedPreferences(packageName, Context.MODE_PRIVATE).edit();

        setRandomToken(UUID.randomUUID().toString());

        editor.putString("xenotoken", randomToken);

        editor.apply();
    }

    public static void setLoaderColor(String color) {
        getInstance().color = color;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setRandomToken(String tokenId) {
        this.randomToken = randomToken;
    }

    public String getRandomToken() {
        return randomToken;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getLocale() {
        return locale;
    }

    public Context getContext() {
        return context;
    }

    public static XenoIdentity getIdentity() {
        return getInstance().identity;
    }

    public static void setIdentity(XenoIdentity identity) {
        getInstance().identity = identity;
    }

    public Boolean isIdentified() {
        return identified;
    }
}