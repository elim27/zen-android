package com.MADAPPS.zen.activities;

import android.os.Bundle;
import android.widget.Button;

import com.MADAPPS.zen.R;
//import com.MADAPPS.zen.ui.home.TimerViews;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {
    private static Button homeBtn;
    private boolean isReady;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //sets up toolbar in StatsFragment
//        toolbar = findViewById(R.id.toolbarStats);
//        setSupportActionBar(toolbar);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.


        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_stats, R.id.navigation_more)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
      NavigationUI.setupWithNavController(navView, navController);

//        StreakCheck.checkDailyStreak(this);

//        homeBtn = findViewById(R.id.medBtn);
//        onClick();



    }

//
//    private void onClick(){
//        homeBtn.setOnClickListener(new View.OnClickListener() {
//                /**
//                 *Saves certain Preferences based on click/click order
//                 * @param v
//                 */
//                @Override
//                public void onClick(View v){
//                    //if button is clicked after countdown timer is finished
//                    //reset timer
//                    if(Prefs.getBoolVal(getApplicationContext(), Prefs.KEY_RUNNING)){
//                        isReady = false;
//                    }
//
//                    boolean isFinished = Prefs.getBoolVal(getApplicationContext(), Prefs.KEY_DONE);
//                    Log.i("isFinished", "Value: " + isFinished);
//                    if(isFinished){
//                        TimerViews.restartView();
//                        isReady = true;
//                    }else if (isReady){
//                        TimerViews.pausedView();
//                    }  else {
//                        TimerViews.unpausedView();;
//                    }
//
//                }
//
//        });
//
//    }

}
