package roman10reborn.apl.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;


/**
 * @author Aidan Follestad (afollestad)
 */
public class PreferenceActivity extends ThemableActivity
{
    public static Toolbar mToolbar;
    public static String mTitle;
    SharedPreferences  Sp;
    public static String TAG = "Settings";
    public static Boolean aboutDialogShown;



    public static class SettingsFragment extends PreferenceFragment


    {
        SharedPreferences prefs;
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.pref_global);








            // Authors
            Preference preference4 = (Preference) findPreference("authors");
            preference4.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {

                    MaterialDialog.Builder a = new MaterialDialog.Builder(getActivity());
                    prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    String skin = prefs.getString("skin_color", "#5677fc");
                    final boolean dark = ThemeUtils.isDarkMode(getActivity());
                    if (dark)
                        a.theme(Theme.DARK);

                    a.positiveText(R.string.close);
                    a.positiveColor(Color.parseColor(skin));
                    LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View view = layoutInflater.inflate(R.layout.authors, null);
                    a.customView(view);
                    a.title(R.string.authors);
                    a.build().show();
                    return false;
                }
            });

            // O aplikaci
            findPreference("about").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    final boolean dark = ThemeUtils.isDarkMode(getActivity());
                    new MaterialDialog.Builder(getActivity())
                            .title(R.string.about)
                            .theme(dark ? Theme.DARK : Theme.LIGHT)
                            .positiveText(R.string.close)
                            .content(R.string.app_name)
                            .show();
                    return true;
                }
            });

         };

        public void onAttach(Activity activity) {
            super.onAttach(activity);
//            mCallback = (ThemeCallback) activity;
        }




        public static int resolveColor(Context context, int color) {
            TypedArray a = context.obtainStyledAttributes(new int[]{color});
            int resId = a.getColor(0, context.getResources().getColor(R.color.cabinet_color));
            a.recycle();
            return resId;
        }

        public static interface ThemeCallback {
            void onThemeSelection(Boolean kind);
        }


    };

        @Override
    protected void onCreate(Bundle savedInstanceState) {


            super.onCreate(savedInstanceState);
            setContentView(R.layout.preference_activity_custom);


            try {
        mToolbar = (Toolbar) findViewById(R.id.toolbarnew);
        mToolbar.setPopupTheme(getThemeUtils().getPopupTheme());



                mToolbar.setBackgroundColor(getThemeUtils().primaryColor());

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(Main.mmTitle);
        getFragmentManager().beginTransaction().replace(R.id.settings_content, new SettingsFragment()).commit();


            } catch (Exception e) {
                Log.e("Preferences", e.toString());
            }

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}