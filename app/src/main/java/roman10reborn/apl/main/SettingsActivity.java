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
public class SettingsActivity extends ThemableActivity implements ColorChooserDialog.ColorCallback
{
    public static Toolbar mToolbar;
    public static String mTitle;
    SharedPreferences  Sp;
    public static String TAG = "Settings";


    @Override
    public void onColorSelection(int title, int color) {
        if (title == R.string.primary_color)
            getThemeUtils().primaryColor(color);
        else if (title == R.string.accent_color)
            getThemeUtils().accentColor(color);
        else
            getThemeUtils().thumbnailColor(color);
        recreate();
    }


    public static class SettingsFragment extends PreferenceFragment


    {
        SharedPreferences prefs;
        ThemeCallback mCallback;
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.pref_userinterface);



            findPreference("base_theme").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    //    ImageLoader.getInstance().clearMemoryCache();

                    final boolean dark = ThemeUtils.isDarkMode(getActivity());
                    prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    int preselect = 0;
                    if (prefs.getBoolean("true_black", false)) {
                        preselect = 2;
                    } else if (prefs.getBoolean("dark_mode", false)) {
                        preselect = 1;
                    }

                    new MaterialDialog.Builder(getActivity())
                            .title(R.string.base_theme)
                            .items(R.array.base_themes)
                            .theme(dark ? Theme.DARK : Theme.LIGHT)
                            .itemsCallbackSingleChoice(preselect, new MaterialDialog.ListCallback() {
                                @SuppressLint("CommitPrefEdits")
                                @Override
                                public void onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                                    if (getActivity() == null) return;
                                    SharedPreferences.Editor prefs = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
                                    switch (i) {
                                        default:
                                            prefs.remove("dark_mode").remove("true_black");
                                            break;
                                        case 1:
                                            prefs.remove("true_black")
                                                    .putBoolean("dark_mode", true);
                                            break;
                                        case 2:
                                            prefs.putBoolean("dark_mode", true)
                                                    .putBoolean("true_black", true);
                                            break;
                                    }
                                    prefs.commit();
                                    getActivity().recreate();
                                }
                            }).show();
                    return false;
                }
            });




            ThemeUtils themeUtils = ((ThemableActivity) getActivity()).getThemeUtils();
            CabinetPreference primaryColor= (CabinetPreference) findPreference("primary_color");
            primaryColor.setColor(themeUtils.primaryColor(), resolveColor(getActivity(), R.attr.colorAccent));

            primaryColor.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    ThemeUtils themeUtils = ((ThemableActivity) getActivity()).getThemeUtils();
                    new ColorChooserDialog().show(getActivity(), preference.getTitleRes(),
                            themeUtils.primaryColor());
                    return true;
                }
            });

            // Authors
          /*  Preference preference4 = (Preference) findPreference("authors");
            preference4.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {

                    MaterialDialog.Builder a = new MaterialDialog.Builder(getActivity());
                    prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    String skin = prefs.getString("skin_color", "#5677fc");
                    final boolean dark = ThemeUtils.isDarkMode(getActivity());
                    if(dark)
                        a.theme(Theme.DARK);

                    a.positiveText(R.string.close);
                    a.positiveColor(Color.parseColor(skin));
                    LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View view = layoutInflater.inflate(R.layout.authors, null);
                    a.customView(view);
                    a.title(R.string.authors);
                    /*a.callback(new MaterialDialog.Callback() {
                        @Override
                        public void onPositive(MaterialDialog materialDialog) {

                            materialDialog.cancel();
                        }

                        @Override
                        public void onNegative(MaterialDialog materialDialog) {

                        }
                    });*/
                /*a.setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });*/
                   /* a.build().show();



                    return false;
                }
            });*/


         };

        public void onAttach(Activity activity) {
            super.onAttach(activity);
//            mCallback = (ThemeCallback) activity;
        }



        public void onColorSelection(String color) {

            // getThemeUtils().thumbnailColor(color);
//        mCallback.ColorSelectionUpdate( color);
            //recreate();
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

            Intent l_intent = new Intent();
            setResult(RESULT_OK, l_intent);

            Main.mToolbar.setBackgroundColor(getThemeUtils().primaryColor());
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}