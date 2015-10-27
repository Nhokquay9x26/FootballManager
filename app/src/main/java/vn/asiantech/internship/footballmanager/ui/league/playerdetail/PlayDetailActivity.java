package vn.asiantech.internship.footballmanager.ui.league.playerdetail;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;
import vn.asiantech.internship.footballmanager.R;
import vn.asiantech.internship.footballmanager.common.Utils;
import vn.asiantech.internship.footballmanager.model.FootBallTeamItem;
import vn.asiantech.internship.footballmanager.model.PlayerItem;
import vn.asiantech.internship.footballmanager.ui.league.footballteam.FootBallTeamAdapter;
import vn.asiantech.internship.footballmanager.dialog.AddDataDialog;
import vn.asiantech.internship.footballmanager.widgets.DialogAdapter;
import vn.asiantech.internship.footballmanager.widgets.ToolBar;

/**
 * Created by nhokquay9x26 on 23/10/15.
 */
@EActivity(R.layout.activity_player_detail)
public class PlayDetailActivity extends Activity implements ToolBar.OnToolBarListener,
        View.OnClickListener, FootBallTeamAdapter.OnItemListener {

    PlayerItem mPlayers;
    EditText mEdtName;
    TextView mTvPosition;
    TextView mTvTeam;
    EditText mEdtNumber;
    TextView mTvBirthday;
    TextView mTvAge;
    EditText mEdtCountry;
    EditText mEdtHeight;
    EditText mEdtWeight;
    ToolBar mToolBar;
    RecyclerView mRecyclerView;
    List<FootBallTeamItem> mTeams;
    FootBallTeamAdapter mAdapter;

    int getIdPlayer;
    int getIdTeam;

    @AfterViews
    void afterView() {
        Bundle bundle = getIntent().getExtras();
        getIdPlayer = Integer.parseInt(bundle.getString(Utils.EXTRA_KEY_PLAYER_ID));
        getIdTeam = Integer.parseInt(bundle.getString(Utils.EXTRA_KEY_TEAM_ID));
        mPlayers = PlayerItem.findById(PlayerItem.class, getIdPlayer);
        mToolBar = (ToolBar) findViewById(R.id.tool_bar_player_detail);
        mToolBar.setTitle("INFORMATION PLAYER");
        mToolBar.setmOnToolBarListener(this);
        init();
    }

    public void init() {
        mEdtName = (EditText) findViewById(R.id.edtName);
        mTvPosition = (TextView) findViewById(R.id.tvPosition);
        mEdtNumber = (EditText) findViewById(R.id.edtNumber);
        mTvBirthday = (TextView) findViewById(R.id.tvBirthday);
        mTvAge = (TextView) findViewById(R.id.tvAge);
        mTvTeam = (TextView) findViewById(R.id.tvTeam);
        mEdtCountry = (EditText) findViewById(R.id.edtCountry);
        mEdtHeight = (EditText) findViewById(R.id.edtHeight);
        mEdtWeight = (EditText) findViewById(R.id.edtWeight);

        mEdtName.setText(mPlayers.getName());
        mTvPosition.setText(mPlayers.getPosition());
        mEdtNumber.setText(mPlayers.getNumber());
        mTvBirthday.setText(mPlayers.getBirthday());
        mTvAge.setText(mPlayers.getAge());
        mEdtCountry.setText(mPlayers.getCountry());
        mEdtWeight.setText(mPlayers.getWeight());
        mEdtHeight.setText(mPlayers.getHeight());

        FootBallTeamItem teamItem = FootBallTeamItem.findById(FootBallTeamItem.class, getIdTeam);
        mTvTeam.setText(teamItem.getName());
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
        mTvBirthday.setEnabled(true);
        mEdtCountry.setEnabled(true);
        mEdtHeight.setEnabled(true);
        mEdtWeight.setEnabled(true);
        mToolBar.changeEditImage();
        mTvTeam.setEnabled(true);
        mTvPosition.setEnabled(true);
        mTvBirthday.setOnClickListener(this);
        mTvTeam.setOnClickListener(this);
        mTvPosition.setOnClickListener(this);
    }

    String idNew;

    private void updateLeague() {
        String newName = mEdtName.getText().toString();
        String newNumber = mEdtNumber.getText().toString();
        String newBirthday = mTvBirthday.getText().toString();
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
        if (idNew != null) {
            playerItem.setTeamId(Integer.parseInt(idNew));
        }
        playerItem.save();

        int year = 2015 - Integer.parseInt(newBirthday.substring(0, 4));
        String y = String.valueOf(year);

        mTvAge.setText(y);
        mEdtName.setEnabled(false);
        mEdtNumber.setEnabled(false);
        mTvBirthday.setEnabled(false);
        mEdtCountry.setEnabled(false);
        mEdtHeight.setEnabled(false);
        mEdtWeight.setEnabled(false);
        mTvTeam.setEnabled(false);
        mTvPosition.setEnabled(false);
        mToolBar.changeEditImage();
    }

    public enum Position {
        GK,
        SW,
        LB, CB, RB,
        LWB, RWB,
        DM,
        LM, CM, RM,
        AM,
        LW, SS, RW,
        CF
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvTeam: {
                LayoutInflater inflater = getLayoutInflater();
                View dialoglayout = inflater.inflate(R.layout.dialog_list_team, null);
                AddDataDialog dialogAdd = new AddDataDialog();
                dialogAdd.isAdd(this, dialoglayout, "Select Team");

                mRecyclerView = (RecyclerView) dialoglayout.findViewById(R.id.recycleView);
                mTeams = FootBallTeamItem.listAll(FootBallTeamItem.class);
                mAdapter = new FootBallTeamAdapter(mTeams);
                mAdapter.setmOnItemListener(this);
                mRecyclerView.setHasFixedSize(true);
                LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
                mRecyclerView.setLayoutManager(mLinearLayoutManager);
                AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mAdapter);
                ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(alphaAdapter);
                scaleAdapter.setDuration(500);
                scaleAdapter.setFirstOnly(false);
                mRecyclerView.setAdapter(scaleAdapter);
                break;
            }
            case R.id.tvPosition: {
                LayoutInflater inflater = getLayoutInflater();
                View dialoglayout = inflater.inflate(R.layout.dialog_list_team, null);
                AddDataDialog dialogAdd = new AddDataDialog();
                dialogAdd.isAdd(this, dialoglayout, "Select Position");

                mRecyclerView = (RecyclerView) dialoglayout.findViewById(R.id.recycleView);
                List<Position> mPos = new ArrayList<Position>();
                DialogAdapter adapter = new DialogAdapter(mPos);
                mRecyclerView.setHasFixedSize(true);
                LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
                mRecyclerView.setLayoutManager(mLinearLayoutManager);
                AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
                ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(alphaAdapter);
                scaleAdapter.setDuration(500);
                scaleAdapter.setFirstOnly(false);
                mRecyclerView.setAdapter(scaleAdapter);
                break;
            }
            case R.id.tvBirthday: {
                showDatePicker(v);
                break;
            }
        }
    }

    public void showDatePicker(View v) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpd = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mTvBirthday.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }

    @Override
    public void onItemClick(int position) {
        mTvTeam.setText(mTeams.get(position).getName());
        idNew = mTeams.get(position).getId().toString();
    }

    @Override
    public void onDeleteItemClick(int position) {

    }
}
