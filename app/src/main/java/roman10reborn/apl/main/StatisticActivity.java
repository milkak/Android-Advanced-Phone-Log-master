package roman10reborn.apl.main;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dacer.androidcharts.PieHelper;
import com.dacer.androidcharts.PieView;

import java.util.ArrayList;
import java.util.Hashtable;



/**
 * @author Aidan Follestad (afollestad) and Milos Kolias
 */
public class StatisticActivity extends ThemableActivity
{
    public static Toolbar mToolbar;
    public static String mTitle;
    SharedPreferences  Sp;
    public static String TAG = "Statistic";
    public static TextView text_total;
    public static TextView sum_total;

    public static TextView mstat_in_sum;
    public static TextView mstat_out_sum;
    public static TextView mstat_miss_sum;
    public static TextView mstat_out_time;
    public static TextView mstat_in_time;
    public static TextView mstat_miss_time;
    public static  String l_talkTime;
    private Typeface tf;
    public static int p;


   public   static class PieFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.statistic_activity, container, false);

            // draw Pie Chart

            final PieView pieView = (PieView)rootView.findViewById(R.id.pie_view);
            try {


            mstat_in_sum = (TextView)  rootView.findViewById(R.id.stat_in_sum);
            mstat_out_sum = (TextView)  rootView.findViewById(R.id.stat_out_sum);
            mstat_miss_sum = (TextView)  rootView.findViewById(R.id.stat_miss_sum);
            mstat_out_time = (TextView)  rootView.findViewById(R.id.stat_out_time);
            mstat_in_time = (TextView)  rootView.findViewById(R.id.stat_in_time);

            ImageView btn = (ImageView) rootView.findViewById(R.id.missing);
            btn.setImageDrawable(this.getResources().getDrawable(android.R.drawable.sym_call_missed));

            mstat_in_sum.setText(String.format("%2d", Main.total_inCallNum) + " ");
            mstat_out_sum.setText(String.format("%2d", Main.total_outCallNum) + " ");
            mstat_miss_sum.setText(String.format("%2d", Main.total_missCallNum) + " ");

            l_talkTime = String.format("%02d:%02d:%02d", Main.total_outCallTime/3600, (Main.total_outCallTime%3600)/60,(Main.total_outCallTime%3600)%60);
            mstat_out_time.setText(l_talkTime);
            l_talkTime = String.format("%02d:%02d:%02d", Main.total_inCallTime/3600,
                    (Main.total_inCallTime%3600)/60,(Main.total_inCallTime%3600)%60);
            mstat_in_time.setText(l_talkTime);


                Log.e("Statistic pred", Main.isFiltered + " " + Main.isFilteredUser);

            if (Main.isFiltered==1 && Main.isFilteredUser==1 ) {

                Log.e("Statistic in", Main.isFiltered+" "+Main.isFilteredUser);
                pieView.setVisibility(View.INVISIBLE);
            }
            else {
                pieView.setVisibility(View.VISIBLE);

                p =  (Main.total_inCallNum+Main.total_outCallNum+Main.total_missCallNum)/100;

                set(pieView, Main.total_inCallNum / p, Main.total_outCallNum / p, Main.total_missCallNum / p);
            }


            } catch (Exception e) {
                Log.e("Statistic", e.toString());
            }
            return rootView;
        }
   }



        @Override
    protected void onCreate(Bundle savedInstanceState) {
//            super.setTheme(R.style.appCompatLight);


        super.onCreate(savedInstanceState);
            setContentView(R.layout.statistic_activity_custom);




        try {
        mToolbar = (Toolbar) findViewById(R.id.toolbarnew);
                mToolbar.setPopupTheme(getThemeUtils().getPopupTheme());



                mToolbar.setBackgroundColor(getThemeUtils().primaryColor());

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.statistic_);
            getFragmentManager().beginTransaction().replace(R.id.statistic_content, new PieFragment()).commit();


            } catch (Exception e) {
                Log.e("Statistic", e.toString());
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

    public static void set(PieView pieView,Integer in,Integer out,Integer miss){
        Log.d(TAG, "Krok 1 "+in+" "+out+" ");
        ArrayList<PieHelper> pieHelperArrayList = new ArrayList<PieHelper>();
        pieHelperArrayList.add(new PieHelper(in, Color.BLUE));
        pieHelperArrayList.add(new PieHelper(out,Color.rgb(0,200,0)) );
        pieHelperArrayList.add(new PieHelper(miss,Color.RED));

        pieView.setDate(pieHelperArrayList);
      /*  pieView.setOnPieClickListener(new PieView.OnPieClickListener() {
            @Override
            public void onPieClick(int index) {
                if(index != PieView.NO_SELECTED_INDEX) {
                    textView.setText(index + " selected");
                }else{
                    textView.setText("No selected pie");
                }
            }
        });*/
        pieView.selectedPie(2);
    }




 }