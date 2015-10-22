package vn.asiantech.internship.footballmanager.ui.league.player;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import vn.asiantech.internship.footballmanager.R;
import vn.asiantech.internship.footballmanager.common.Utils;
import vn.asiantech.internship.footballmanager.ui.league.footballteam.FootBallTeamActivity_;

/**
 * Created by nhokquay9x26 on 22/10/15.
 */
@EActivity(R.layout.activity_footballteam)
public class PlayerActivity extends Activity {

    TextView tvName;

    @AfterViews
    void afterViews() {
        tvName = (TextView) findViewById(R.id.tvName);
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString(Utils.EXTRA_KEY_NAME);
        tvName.setText(name);
    }
}
