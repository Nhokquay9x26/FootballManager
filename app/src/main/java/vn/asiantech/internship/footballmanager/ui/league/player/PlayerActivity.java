package vn.asiantech.internship.footballmanager.ui.league.player;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import vn.asiantech.internship.footballmanager.R;
import vn.asiantech.internship.footballmanager.common.Utils;
import vn.asiantech.internship.footballmanager.ui.league.footballteam.FootBallTeamActivity_;
import vn.asiantech.internship.footballmanager.widgets.ToolBar;

/**
 * Created by nhokquay9x26 on 22/10/15.
 */
@EActivity(R.layout.activity_player)
public class PlayerActivity extends Activity implements ToolBar.OnToolBarListener {

    EditText mEdtName;
    EditText mEdtNationality;
    EditText mEdtYear;
    ToolBar mToolBar;

    @AfterViews
    void afterViews() {
        mToolBar = (ToolBar) findViewById(R.id.tool_bar_play);
        mToolBar.setTitle("Player");
        mToolBar.setmOnToolBarListener(this);
        mEdtName = (EditText) findViewById(R.id.edtName);
        mEdtNationality = (EditText) findViewById(R.id.edtNationality);
        mEdtYear = (EditText) findViewById(R.id.edtYear);
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString(Utils.EXTRA_KEY_NAME);
        String nationality = bundle.getString(Utils.EXTRA_KEY_NATIONALITY);
        String year = bundle.getString(Utils.EXTRA_KEY_YEAR);
        mEdtName.setText(name);
        mEdtNationality.setText(nationality);
        mEdtYear.setText(year);
    }


    @Override
    public void goBack() {
        this.finish();
    }

    @Override
    public void doEdit() {

    }
}
