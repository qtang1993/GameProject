//T102-08
//Qi Tang(qt4ur), Elaina Michelle Hill(emh2hb), Hye Won Chang(hc5we)

package qt4ur.cs2110.virginia.edu.gameproject;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by tomoei on 4/3/15.
 */
public class SplashScreen extends Activity {


    private static String TAG = SplashScreen.class.getName(); // Used to report an error in run()
    private static long SLEEP_TIME = 5;    // Set the duration of the splash screen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // To create a minimal appearance, remove the title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // ... and remove the notification bar if it exists
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.splashscreen); // Refers to the splash.xml file in the layout directory

        // Start timer and launch main activity
        IntentLauncher launcher = new IntentLauncher();
        launcher.start();
    }

    private class IntentLauncher extends Thread {
        @Override
        /**
         * Sleep for some time and then start new activity
         */
        public void run() {
            try {
                // Sleeping - displays the splash screen for this long before switching activities
                Thread.sleep(SLEEP_TIME*1000); // Display (sleep)
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }

            // Start main activity
            // Create an Intent in SplashActivity to start the MainActivity
            Intent intent = new Intent(SplashScreen.this, Mainmenu.class);
            SplashScreen.this.startActivity(intent);
            SplashScreen.this.finish();
        }
    }


}
