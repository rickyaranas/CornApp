import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {

    // Define your SharedPreferences file name here
    public static final String PREF_NAME = "MyPrefs";

    // Method to get SharedPreferences instance
    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // Method to store a string value in SharedPreferences
    public static void saveString(Context context, String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(key, value);
        editor.apply();
    }

    // Method to retrieve a string value from SharedPreferences
    public static String getString(Context context, String key, String defaultValue) {
        return getSharedPreferences(context).getString(key, defaultValue);
    }

    // Add more methods as needed for different data types (int, boolean, etc.)
}