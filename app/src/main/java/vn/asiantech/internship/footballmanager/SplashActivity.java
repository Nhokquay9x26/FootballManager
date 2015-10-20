package vn.asiantech.internship.footballmanager;

import android.app.Activity;
import android.os.Handler;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import vn.asiantech.internship.footballmanager.ui.leage.LeageActivity_;

@EActivity(R.layout.activity_splash)
public class SplashActivity extends Activity {
    @AfterViews
    void afterViews(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                    LeageActivity_.intent(SplashActivity.this).start();
            }
        }, 2000);
    }
}
