package roman10reborn.dialogs;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import roman10reborn.apl.data2.BooleanArrayList;
import roman10reborn.apl.main.Main;
import roman10reborn.apl.main.R;
import roman10reborn.apl.main.ThemableActivity;
import roman10reborn.apl.main.ThemeUtils;
import roman10reborn.utils.ConstantStatic;
import roman10reborn.utils.SortingUtilsStatic;


/**
 * @author Milos Kolias
 */
public class MainFilterNumberListDialog extends ThemableActivity
{
    public static Toolbar mToolbar;
    public static String mTitle;
    SharedPreferences  Sp;
    public static String TAG = "Dialog";
    public static  ArrayList<String> numbersList = new ArrayList<String>();

    public static BooleanArrayList ifSelected = new BooleanArrayList(50);
    public static ArrayList<String> selectedNumbersList = new ArrayList<String>();



    public   static class FilterNumberListDialogFragment extends ListFragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.calllog_filter_number_list_dialog, container, false);

            return rootView;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            setHasOptionsMenu(true);
            //set up the list view

            try {
            numbersList = getNumberListFromCurrentLogs();
            setListAdapter(new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_multiple_choice, numbersList));
            final ListView listView = getListView();
            listView.setItemsCanFocus(false);
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            initChecked();
            } catch (Exception e) {
                Log.e("Filter Name", e.toString());
            }
        }


        private void initChecked() {
            int l_size = ifSelected.size();
            for (int i = 0; i< l_size; ++i) {
                if (ifSelected.get(i)) {
                    this.getListView().setItemChecked(i, true);
                }
            }
        }

        private ArrayList<String> getNumberListFromCurrentLogs() {
            Set<String> l_set = new HashSet<String>();
            int l_size = Main.recordList.size();
            for (int i = 0; i < l_size; ++i) {
                String number = Main.recordList.get(i).aplr_number;
                l_set.add(number.compareTo("-1")==0 ? ConstantStatic.NOTAVAIL : number);
            }
            ArrayList<String> l_numbers = new ArrayList<String>(l_set);
            SortingUtilsStatic.sortStringAsc(l_numbers);
            for (int i = 0; i < l_numbers.size(); ++i) {
                ifSelected.add(false);
            }
            return l_numbers;
        }


        public void onListItemClick(ListView l, View v, int position, long id) {
            super.onListItemClick(l, v, position, id);
            ifSelected.set(position, !ifSelected.get(position));

         }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean onOptionsItemSelected(final MenuItem item) {



            switch (item.getItemId()) {
                case R.id.calllog_filter_number_list_multi_btn_first:
                    int l_size = this.getListAdapter().getCount();
                    for (int i = 0; i < l_size; ++i) {
                        ifSelected.set(i, true);
                        this.getListView().setItemChecked(i, true);
                    }
                    return true;
                case R.id.calllog_filter_number_list_multi_btn_second:
                    selectedNumbersList.clear();
                     l_size = ifSelected.size();
                    for (int i = 0; i < l_size; ++i) {
                        if (ifSelected.get(i)) {
                            selectedNumbersList.add(numbersList.get(i));
                        }
                    }
                    getActivity().setResult(RESULT_OK);
                    getActivity().finish();
                return true;
                case R.id.calllog_filter_number_list_multi_btn_third:
                    //setResult(RESULT_CANCELED);
                    selectedNumbersList.clear();
                    getActivity().setResult(RESULT_CANCELED);
                    getActivity().finish();
                    return true;
                case R.id.calllog_filter_number_list_multi_btn_forth:
                     l_size = this.getListAdapter().getCount();
                    for (int i = 0; i < l_size; ++i) {
                        ifSelected.set(i, false);
                        this.getListView().setItemChecked(i, false);
                    }
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }

    }




        @Override
    protected void onCreate(Bundle savedInstanceState) {
            final boolean dark = ThemeUtils.isDarkMode(this);
            super.setTheme(dark ? R.style.appCompatDark : R.style.appCompatLight);


            super.onCreate(savedInstanceState);
            setContentView(R.layout.calllog_filter_name);



            try {
        mToolbar = (Toolbar) findViewById(R.id.toolbarnew);
                mToolbar.setPopupTheme(getThemeUtils().getPopupTheme());
                mToolbar.setBackgroundColor(getThemeUtils().primaryColor());

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.dialog_numbers_log);

            getFragmentManager().beginTransaction().replace(R.id.dialog_content, new FilterNumberListDialogFragment()).commit();



            } catch (Exception e) {
                Log.e("Dialog", e.toString());
            }

    }



    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.dialog_filter_number, menu);
        return true;
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