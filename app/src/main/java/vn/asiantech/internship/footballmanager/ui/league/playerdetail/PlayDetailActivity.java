package vn.asiantech.internship.footballmanager.ui.league.playerdetail;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

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
        mEdtName = (EditText) findViewById(R.id.edtName);
        mEdtPosition = (EditText) findViewById(R.id.edtPosition);

        mEdtName.setText(mPlayers.getName().toString());
        mEdtPosition.setText(mPlayers.getPosition().toString());

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
        mToolBar.changeEditImage();
    }

    private void updateLeague() {
        String newName = mEdtName.getText().toString();
        PlayerItem playerItem = PlayerItem.findById(PlayerItem.class, getIdPlayer);
        playerItem.setName(newName);
        playerItem.save();

        mEdtName.setEnabled(false);
        mToolBar.changeEditImage();
    }
}
