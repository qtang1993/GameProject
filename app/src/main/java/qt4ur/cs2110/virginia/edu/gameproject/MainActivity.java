//T102-08
//Qi Tang(qt4ur), Elaina Michelle Hill(emh2hb), Hye Won Chang(hc5we)

package qt4ur.cs2110.virginia.edu.gameproject;

import android.app.ActionBar;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences;
import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;
import java.util.Random;


public class MainActivity extends Activity {

    RelativeLayout layout;
    ImageView boy, coinBoard, hpboard, sbomb, playMusic,muteMusic,bigbomb,pause;
    TextView moneyBoard,hp,bombnum, gameover;
    int coins, size,level;
    Boy b;
    ImageView[] ghostimages ;
    ImageView[] bombimages ;
    Ghosts ghosts;

    static final String COIN = "0";
    static final String LEVEL = "1";
    final long L = 10;


    MediaPlayer backgroundMusic,smallBomb;


    Random random = new Random();

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putInt(COIN, coins);
        savedInstanceState.putInt(LEVEL, getIntent().getIntExtra("level", 1));

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
        Log.d("save","save called");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore state members from saved instance
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            coins = savedInstanceState.getInt(COIN);
            level = savedInstanceState.getInt(LEVEL);
        }
        Log.d("restore","restore called");


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialization here
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        setUp();
        Log.d("create","create called");

    }


    public void setUp() {

        layout = (RelativeLayout) findViewById(R.id.layout);

        //background music play button
        playMusic = (ImageView) findViewById(R.id.sound);
        playMusic.setImageDrawable(getResources().getDrawable(R.drawable.sound));

        //background music mute button
        muteMusic = (ImageView) findViewById(R.id.mute);
        muteMusic.setImageDrawable(getResources().getDrawable(R.drawable.mute));

        /*  based on the level you choose
            EASY: at most 10 ghosts on the screen
            MEDIUM: at most 15 ghosts
            HARD: at most 20 ghosts
         */
        level = getIntent().getIntExtra("level",1);

//        b.setHp(getIntent().getIntExtra("hp",100));
//        ghosts.setBomb(getIntent().getIntExtra("bomb",10));
//        coins = getIntent().getIntExtra("coins",0);
        if (level == 1)
            size = 10;
        else if (level == 2)
            size = 15;
        else if (level == 3)
            size = 20;

        ghostimages = new ImageView[size];
        bombimages = new ImageView[size];

        //create a boy on the screen
        boy = (ImageView) findViewById(R.id.boy);
        boy.setImageDrawable(getResources().getDrawable(R.drawable.boy));
        boy.setX(500);
        boy.setY(500);
        b = new Boy(boy);

        coinBoard = (ImageView)findViewById(R.id.coinBoard);
        coinBoard.setImageDrawable(getResources().getDrawable(R.drawable.coin));

        hpboard = (ImageView)findViewById(R.id.hppic);
        hpboard.setImageDrawable(getResources().getDrawable(R.drawable.heart));

        sbomb = (ImageView)findViewById(R.id.bombicon);
        sbomb.setImageDrawable(getResources().getDrawable(R.drawable.bomb));


        bigbomb = (ImageView)findViewById(R.id.bigbomb);
        bigbomb.setImageDrawable(getResources().getDrawable(R.drawable.bigbomb));

        pause = (ImageView)findViewById(R.id.pause);
        pause.setImageDrawable(getResources().getDrawable(R.drawable.pause));

        moneyBoard = (TextView)findViewById(R.id.money);
        moneyBoard.setText(""+coins);
        coins = 0;

        hp = (TextView)findViewById(R.id.hp);
        bombnum = (TextView)findViewById(R.id.bombnum);
        gameover = (TextView)findViewById(R.id.gameover);
        gameover.setVisibility(View.INVISIBLE);

        //randomly generate 3 ghosts on the screen when the game starts
        for (int i=0;i<size;i++){
            createGhost(i);
            ImageView imageView = (ImageView) layout.findViewWithTag(""+i);
            ghostimages[i]=imageView;
            bombimages[i]=createBomb(ghostimages[i]);

        }

        ghosts = new Ghosts(b,ghostimages,hp,bombnum,bombimages,level,gameover);

        backgroundMusic = MediaPlayer.create(getApplicationContext(),R.raw.pump);
        try{
            backgroundMusic.prepare();
        }catch (Exception e){

        }
        smallBomb = MediaPlayer.create(getApplicationContext(),R.raw.smallbomb);

        try{
            smallBomb.prepare();
        }catch (Exception e){

        }


    }

    protected void onStart(){
        super.onStart();
        setUp();
        Log.d("start","start called");
    }

    protected void onResume(){

        //now game starts
        super.onResume();
        //play the background music if click on the play button.
        playMusic.setOnClickListener(new View.OnClickListener(){
            @Override
                public void onClick(View view){
                    backgroundMusic.setLooping(true);
                    backgroundMusic.start();
                }

        });

        //the music is muted when mute button is clicked
        muteMusic.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                if (backgroundMusic != null && backgroundMusic.isPlaying()) {//If music is playing already
                    backgroundMusic.pause();//Stop playing the music
                }

            }
        });

        //ghosts move towards the character
        ghosts.execute(ghostimages);

        //touch the screen to let the character move
        layout.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (b.getHp()>0 && !ghosts.isPause()) {
                    b.move(event);
                    boy.setX(b.getX());
                    boy.setY(b.getY());

                }
                return true;
            }

        });

        //click on small bomb picture to buy bombs. 3 coins to buy 1 bomb.
        sbomb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (coins >= 3){
                    ghosts.setBomb(ghosts.getBomb()+1);
                    coins-= 3;
                    moneyBoard.setText("" + coins);
                }else{
                    Toast.makeText(getApplicationContext(), "NEED 3 COINS!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


        //click on the red bomb to release a big bomb which kills all the ghosts. spend 20 coins.
        //kill ghosts using a big bomb will not add coins
        bigbomb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (coins >= 20){
                    coins-= 20;
                    ghosts.killAll();
                    moneyBoard.setText("" + coins);
                }else{
                    Toast.makeText(getApplicationContext(), "NEED 20 COINS!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        //click on pause button to pause the game
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ghosts.setPause(true);
            }
        });

        //click on PAUSE to resume the game
        gameover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ghosts.setPause(false);
            }
        });

        /*  kill one ghost and collect one coin
            after kill one ghost, a new ghost will randomly appear on the screen
            click on a ghost and release a bomb if bomb number is greater than 0
        */
        ghostOnClick();

        Log.d("resume","coins"+coins);

    }

    protected void onRestart(){
        super.onRestart();
        Log.d("restart","restart called");
    }

    protected void onPause(){
        //go back to the main menu
        finish();
        ghosts.cancel(true);
        ghosts = null;
        super.onPause();

        Log.d("pause","onpause called");

    }

    protected void onStop(){
//        ghosts.setPause(true);
       super.onStop();
    }










    //inner methods below--------------------------------------------------------

    public void ghostOnClick(){

        for (int i =0;i<ghosts.getGhosts().length;i++) {
            final int ii = i;
            ghostimages[ii].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ghosts.getBomb() > 0) {
//                        Thread play = new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                smallBomb.start();
//                            }
//                        });
//                        play.start();
                        ghosts.getGhosts()[ii].setKilled(true);
                        ghosts.getGhosts()[ii].setTick(0);
                        coins++;
                        moneyBoard.setText("" + coins);
                        ghosts.setBomb(ghosts.getBomb() - 1);
                    }else{
                        Toast.makeText(getApplicationContext(), "NO MORE BOMBS!",
                                Toast.LENGTH_SHORT).show();
                    }
                    Log.d("NO."+ii, "click");

                }
            });
        }
    }


    //create a ghost image on the screen
    public void createGhost(int i){
        //ImageView Setup
        ImageView imageView = new ImageView(this);
        //setting image resource
        imageView.setImageResource(R.drawable.ghost1);
        imageView.setTag(""+i);
        //setting image position
        imageView.setLayoutParams(new ActionBar.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        //adding view to layout
        layout.addView(imageView);
        imageView.setX(random.nextFloat()*900+30);
        imageView.setY(random.nextFloat()*1500+50);
        imageView.setVisibility(View.INVISIBLE);
    }

    //create a bomb picture
    public ImageView createBomb(ImageView ghostImage) {
        //ImageView Setup
        ImageView imageView = new ImageView(MainActivity.this);
        //setting image resource
        imageView.setImageResource(R.drawable.explosion1);
        imageView.setTag("bomb");
        //setting image position
        imageView.setLayoutParams(new ActionBar.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        //adding view to layout
        layout.addView(imageView);
        imageView.setX(ghostImage.getX());
        imageView.setY(ghostImage.getY());
        imageView.setVisibility(View.INVISIBLE);
        return imageView;
    }









}
