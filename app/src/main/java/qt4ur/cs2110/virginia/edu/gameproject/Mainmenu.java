//T102-08
//Qi Tang(qt4ur), Elaina Michelle Hill(emh2hb), Hye Won Chang(hc5we)

package qt4ur.cs2110.virginia.edu.gameproject;

import android.app.ActionBar;
import android.content.SharedPreferences;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by tomoei on 4/6/15.
 */
public class Mainmenu extends ActionBarActivity {

    RelativeLayout menu;
    Button easy,medium,hard;
    int level,coins,hp,bombs;
//
//    public Mainmenu(){
//        level = 1;
//        score = 0;
//    }



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);




        menu = (RelativeLayout)findViewById(R.id.main_menu);
        easy = (Button)findViewById(R.id.easy);
        medium = (Button)findViewById(R.id.medium);
        hard = (Button)findViewById(R.id.hard);
//        load = (Button)findViewById(R.id.load);


//        load.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent myIntent = new Intent(Mainmenu.this, MainActivity.class);
//
////                myIntent.putExtra("coins",10);
////                myIntent.putExtra("hp",20);
////                myIntent.putExtra("bomb",5);
//
//                startActivity(myIntent);
//
//
//            }
//        });

        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(Mainmenu.this, MainActivity.class);

                myIntent.putExtra("level",1);
                startActivity(myIntent);


            }
        });

        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(Mainmenu.this, MainActivity.class);
                myIntent.putExtra("level",2);

                startActivity(myIntent);




            }
        });

        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(Mainmenu.this, MainActivity.class);
                myIntent.putExtra("level",3);

                startActivity(myIntent);


            }
        });
    }



    protected void onPause(){

        super.onPause();
    }

    protected void onResume(){

        super.onResume();
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
