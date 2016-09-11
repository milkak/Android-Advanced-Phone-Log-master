package roman10reborn.dialogs;

import java.util.ArrayList;
import java.util.List;

import roman10reborn.apl.data2.BooleanArrayList;
import roman10reborn.apl.main.Main;
import roman10reborn.apl.main.R;
import roman10reborn.apl.main.R.color;
import roman10reborn.apl.main.ThemableActivity;
import roman10reborn.apl.main.ThemeUtils;
import roman10reborn.iconifiedlist.IconifiedText;

import android.app.ListActivity;
import android.app.ListFragment;
import android.content.Context;
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
import android.widget.ListView;


public class MainChoiceDialog extends ThemableActivity
{
	public static Toolbar mToolbar;
	public static String mTitle;
	SharedPreferences Sp;
	public static  String[] options;
	public static final int OPTION_DELETE_ONE = 0;
	public static final int OPTION_DELETE_FROM_CONTACT = 1;
	public static final int OPTION_ADD_TO_CAL = 2;
	public static final int OPTION_SCH_CAL = 3;
	public static final int OPTION_COPY = 4;
	private List<IconifiedText> optionsList = new ArrayList<IconifiedText>();





	public   static class FilterChoiceDialogFragment extends ListFragment {

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.main_choice_dialog, container, false);

			return rootView;
		}
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			setHasOptionsMenu(true);
			//set up the list view

			options = new String[] {
					getString(R.string.delete_log),  getString(R.string.delete_all), getString(R.string.add_calendar),
					getString(R.string.schedule_),getString(R.string.copy_cliboard)
			};


			//this.getListView().setBackgroundColor(color.solid_white);
			//IconifiedTextListAdapter adapter = new IconifiedTextListAdapter(mContext);
			//convertOptionsToDisplayData();
			//adapter.setListItems(optionsList);
			//this.setListAdapter(adapter);
			setListAdapter(new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_single_choice, options));

			final ListView listView = getListView();

			listView.setItemsCanFocus(false);
			listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		}

		@Override
		public void onListItemClick(ListView l, View v, int position, long id)
		{
			Intent l_intent = new Intent();
			l_intent.putExtra(Main.MAIN_OPTOINS_CHOICE, position);
			getActivity().setResult(RESULT_OK, l_intent);
			getActivity().finish();
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
				getSupportActionBar().setTitle(R.string.dialog_choice_log);

				getFragmentManager().beginTransaction().replace(R.id.dialog_content, new FilterChoiceDialogFragment()).commit();


			} catch (Exception e) {
				Log.e("Dialog", e.toString());
			}

		}



		public boolean onCreateOptionsMenu(final Menu menu) {
		//	getMenuInflater().inflate(R.menu.dialog_filter_number, menu);
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


