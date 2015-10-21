package vn.asiantech.internship.footballmanager.ui.league.footballteam;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import vn.asiantech.internship.footballmanager.R;
import vn.asiantech.internship.footballmanager.common.Utils;
import vn.asiantech.internship.footballmanager.ui.league.LeagueActivity_;

/**
 * Created by nhokquay9x26 on 21/10/15.
 */
@EActivity(R.layout.activity_footballteam)
public class FootBallTeamActivity extends Activity {

    TextView mTvName;

    @AfterViews
    void afterView() {
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString(Utils.EXTRA_KEY_);
        mTvName = (TextView) findViewById(R.id.tvName);
        mTvName.setText(name);
    }

    @Click(R.id.imgBack)
    void backLeague() {
        LeagueActivity_.intent(FootBallTeamActivity.this).start();
        finish();
    }
}
