package vn.asiantech.internship.footballmanager.ui.league.player;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.melnykov.fab.FloatingActionButton;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import java.util.List;

import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;
import vn.asiantech.internship.footballmanager.R;
import vn.asiantech.internship.footballmanager.common.Utils;
import vn.asiantech.internship.footballmanager.model.FootBallTeamItem;
import vn.asiantech.internship.footballmanager.model.LeagueItem;
import vn.asiantech.internship.footballmanager.model.PlayerItem;
import vn.asiantech.internship.footballmanager.ui.league.footballteam.FootBallTeamActivity_;
import vn.asiantech.internship.footballmanager.ui.league.playerdetail.PlayDetailActivity_;
import vn.asiantech.internship.footballmanager.widgets.ToolBar;

/**
 * Created by nhokquay9x26 on 22/10/15.
 */
@EActivity(R.layout.activity_player)
public class PlayerActivity extends Activity implements PlayerAdapter.OnItemListener, ToolBar.OnToolBarListener {

    EditText mEdtName;
    EditText mEdtNationality;
    EditText mEdtYear;
    int getTeamId;

    private Effectstype effect;
    List<PlayerItem> mPlayers;
    PlayerAdapter mAdapter;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLinearLayoutManager;
    ScaleInAnimationAdapter scaleAdapter;
    ToolBar mToolBar;
    private int mSelect = -1;

    @Override
    protected void onStart() {
        super.onStart();
        if (mSelect >= 0) {
            PlayerItem playerItem = PlayerItem.findById(PlayerItem.class, mPlayers.get(mSelect).getId());
            mPlayers.set(mSelect, playerItem);
            scaleAdapter.notifyDataSetChanged();
        }
    }

    @AfterViews
    void afterViews() {
        mToolBar = (ToolBar) findViewById(R.id.tool_bar_player);
        mToolBar.setTitle("PLAYER");
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
        getTeamId = Integer.parseInt(bundle.getString(Utils.EXTRA_KEY_TEAM_ID));
        mPlayers = PlayerItem.findWithQuery(PlayerItem.class, "Select * from Player where teamId = " + getTeamId);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
        mAdapter = new PlayerAdapter(mPlayers);
        mAdapter.setmOnItemListener(this);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        animationRecycle();
    }

    public void animationRecycle(){
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.imgAdd);
        fab.attachToRecyclerView(mRecyclerView);
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mAdapter);
        scaleAdapter = new ScaleInAnimationAdapter(alphaAdapter);
        scaleAdapter.setDuration(500);
        scaleAdapter.setFirstOnly(false);
        mRecyclerView.setAdapter(scaleAdapter);
    }

    @Click(R.id.imgAdd)
    void addData(View v) {
        customDialog(v);
    }

    public void customDialog(View v) {
        effect = Effectstype.SlideBottom;

        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(this);
        dialogBuilder
                .isCancelableOnTouchOutside(false)
                .withTitle("Add Players")
                .withTitleColor("#000000")
                .withEffect(effect)
                .withButton1Text("Cancel")
                .withDialogColor("#0099FF")
                .withButton2Text("Ok")
                .setCustomView(R.layout.dialog_add_player, v.getContext())
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText mEdtName = (EditText) dialogBuilder.findViewById(R.id.edtName);
                        EditText mEdtNumber = (EditText) dialogBuilder.findViewById(R.id.edtNumber);
                        EditText mEdtCountry = (EditText) dialogBuilder.findViewById(R.id.edtCountry);
                        EditText mEdtWeight = (EditText) dialogBuilder.findViewById(R.id.edtWeight);
                        EditText mEdtHeight = (EditText) dialogBuilder.findViewById(R.id.edtHeight);
                        EditText mEdtPosition = (EditText) dialogBuilder.findViewById(R.id.edtPosition);
                        EditText mEdtBirthday = (EditText) dialogBuilder.findViewById(R.id.edtBirthday);

                        String name = mEdtName.getText().toString();
                        String number = mEdtNumber.getText().toString();
                        String country = mEdtCountry.getText().toString();
                        String weight = mEdtWeight.getText().toString();
                        String height = mEdtHeight.getText().toString();
                        String position = mEdtPosition.getText().toString();
                        String birthday = mEdtBirthday.getText().toString();

                        PlayerItem playerItem = new PlayerItem(name, number, country, weight, height, position, birthday, getTeamId);
                        playerItem.save();
                        mPlayers.add(playerItem);
                        scaleAdapter.notifyDataSetChanged();
                        dialogBuilder.dismiss();
                    }
                })
                .show();
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
        FootBallTeamItem footBallTeamItem = FootBallTeamItem.findById(FootBallTeamItem.class, getTeamId);
        footBallTeamItem.setName(newName);
        footBallTeamItem.save();

        mEdtName.setEnabled(false);
        mToolBar.changeEditImage();
    }

    @Override
    public void onItemClick(int position) {
        mSelect = position;
        PlayDetailActivity_.intent(PlayerActivity.this)
                .extra(Utils.EXTRA_KEY_PLAYER_ID, mPlayers.get(position).getId().toString())
                .start();
    }
}
