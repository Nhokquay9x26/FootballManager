package vn.asiantech.internship.footballmanager.ui.league.playerdetail;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import vn.asiantech.internship.footballmanager.R;
import vn.asiantech.internship.footballmanager.common.Utils;
import vn.asiantech.internship.footballmanager.model.PlayerItem;
import vn.asiantech.internship.footballmanager.widgets.ToolBar;

/**
 * Created by nhokquay9x26 on 23/10/15.
 */
@EActivity(R.layout.activity_player_detail)
public class PlayDetailActivity extends Activity implements ToolBar.OnToolBarListener {

    PlayerItem mPlayers;
    EditText mEdtName;
    EditText mEdtPosition;
    EditText mEdtNumber;
    EditText mEdtBirthday;
    TextView mTvAge;
    EditText mEdtCountry;
    EditText mEdtHeight;
    EditText mEdtWeight;
    ToolBar mToolBar;

    int getIdPlayer;

    @AfterViews
    void afterView() {
        Bundle bundle = getIntent().getExtras();
        getIdPlayer = Integer.parseInt(bundle.getString(Utils.EXTRA_KEY_PLAYER_ID));
        mPlayers = PlayerItem.findById(PlayerItem.class, getIdPlayer);
        mToolBar = (ToolBar) findViewById(R.id.tool_bar_player_detail);
        mToolBar.setTitle("INFORMATION PLAYER");
        mToolBar.setmOnToolBarListener(this);
        init();
    }

    public void init() {
        mEdtName = (EditText) findViewById(R.id.edtName);
        mEdtPosition = (EditText) findViewById(R.id.edtPosition);
        mEdtNumber = (EditText) findViewById(R.id.edtNumber);
        mEdtBirthday = (EditText) findViewById(R.id.edtBirthday);
        mTvAge = (TextView) findViewById(R.id.tvAge);
        mEdtCountry = (EditText) findViewById(R.id.edtCountry);
        mEdtHeight = (EditText) findViewById(R.id.edtHeight);
        mEdtWeight = (EditText) findViewById(R.id.edtWeight);

        mEdtName.setText(mPlayers.getName());
        mEdtPosition.setText(mPlayers.getPosition());
        mEdtNumber.setText(mPlayers.getNumber());
        mEdtBirthday.setText(mPlayers.getBirthday());
        mTvAge.setText(mPlayers.getAge());
        mEdtCountry.setText(mPlayers.getCountry());
        mEdtWeight.setText(mPlayers.getWeight());
        mEdtHeight.setText(mPlayers.getHeight());
    }

    @Override
    public void goBack() {
        this.finish();
    }

    @Override
    public void doEdit() {
        boolean isEditing = mToolBar.getEditing();
        if (isEditing) {
            updateLeague();
        } else {
            editLeague();
        }
    }

    private void editLeague() {
        mEdtName.setEnabled(true);
        mEdtNumber.setEnabled(true);
        mEdtBirthday.setEnabled(true);
        mEdtCountry.setEnabled(true);
        mEdtHeight.setEnabled(true);
        mEdtWeight.setEnabled(true);
        mToolBar.changeEditImage();
    }

    private void updateLeague() {
        String newName = mEdtName.getText().toString();
        String newNumber = mEdtNumber.getText().toString();
        String newBirthday = mEdtBirthday.getText().toString();
        String newCountry = mEdtCountry.getText().toString();
        String newHeight = mEdtHeight.getText().toString();
        String newWeight = mEdtWeight.getText().toString();

        PlayerItem playerItem = PlayerItem.findById(PlayerItem.class, getIdPlayer);
        playerItem.setName(newName);
        playerItem.setNumber(newNumber);
        playerItem.setBirthday(newBirthday);
        playerItem.setCountry(newCountry);
        playerItem.setHeight(newHeight);
        playerItem.setWeight(newWeight);
        playerItem.save();

        int year = 2015 - Integer.parseInt(newBirthday.substring(0,4));
        String y = String.valueOf(year);

        mTvAge.setText(y);
        mEdtName.setEnabled(false);
        mEdtNumber.setEnabled(false);
        mEdtBirthday.setEnabled(false);
        mEdtCountry.setEnabled(false);
        mEdtHeight.setEnabled(false);
        mEdtWeight.setEnabled(false);
        mToolBar.changeEditImage();
    }
}
