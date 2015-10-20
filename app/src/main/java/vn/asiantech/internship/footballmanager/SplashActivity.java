package vn.asiantech.internship.footballmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

import org.androidannotations.annotations.EActivity;

import vn.asiantech.internship.footballmanager.ui.leage.LeageActivity;
import vn.asiantech.internship.footballmanager.ui.leage.LeageActivity_;

@EActivity(R.layout.activity_splash)
public class SplashActivity extends Activity {

    protected boolean _active = true;
    protected int _splashTime = 2000;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // thread for displaying the SplashScreen
        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (_active && (waited < _splashTime)) {
                        sleep(100);
                        if (_active) {
                            waited += 100;
                        }
                    }
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    finish();
                    Intent mainIntent = new Intent(SplashActivity.this, LeageActivity_.class);
                    startActivity(mainIntent);
                    finish();
                }
            }
        };
        splashTread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            _active = false;
        }
        return true;
    }
}
