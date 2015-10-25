package vn.asiantech.internship.footballmanager.ui.league.player;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

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
import vn.asiantech.internship.footballmanager.ui.league.playerdetail.PlayDetailActivity_;
import vn.asiantech.internship.footballmanager.widgets.AddDataDialog;
import vn.asiantech.internship.footballmanager.widgets.ConfirmDialog;
import vn.asiantech.internship.footballmanager.widgets.ToolBar;

/**
 * Created by nhokquay9x26 on 22/10/15.
 */
@EActivity(R.layout.activity_player)
public class PlayerActivity extends Activity implements PlayerAdapter.OnItemListener,
        ToolBar.OnToolBarListener, ConfirmDialog.OnConfirmDialogListener,
        AddDataDialog.OnAddDataListener {

    EditText mEdtNameShow;
    EditText mEdtNationality;
    EditText mEdtYear;

    EditText mEdtName;
    EditText mEdtNumber;
    EditText mEdtCountry;
    EditText mEdtHeight;
    EditText mEdtWeight;
    EditText mEdtPosition;
    EditText mEdtBirthday;
    int getTeamId;

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

        Bundle bundle = getIntent().getExtras();
        getTeamId = Integer.parseInt(bundle.getString(Utils.EXTRA_KEY_TEAM_ID));
        mPlayers = PlayerItem.findWithQuery(PlayerItem.class, "Select * from Player where teamId = " + getTeamId);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
        mAdapter = new PlayerAdapter(mPlayers);
        mAdapter.setmOnItemListener(this);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        init();
    }

    public void init() {
        FootBallTeamItem teamItem = FootBallTeamItem.findById(FootBallTeamItem.class, getTeamId);
        mEdtNameShow = (EditText) findViewById(R.id.edtName);
        mEdtNationality = (EditText) findViewById(R.id.edtNationality);
        mEdtYear = (EditText) findViewById(R.id.edtYear);
        mEdtNameShow.setText(teamItem.getName());
        mEdtNationality.setText(teamItem.getNationality());
        mEdtYear.setText(teamItem.getYear());

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
        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.dialog_add_player, null);

        mEdtName = (EditText) dialoglayout.findViewById(R.id.edtName);
        mEdtNumber = (EditText) dialoglayout.findViewById(R.id.edtNumber);
        mEdtCountry = (EditText) dialoglayout.findViewById(R.id.edtCountry);
        mEdtWeight = (EditText) dialoglayout.findViewById(R.id.edtWeight);
        mEdtHeight = (EditText) dialoglayout.findViewById(R.id.edtHeight);
        mEdtPosition = (EditText) dialoglayout.findViewById(R.id.edtPosition);
        mEdtBirthday = (EditText) dialoglayout.findViewById(R.id.edtBirthday);

        AddDataDialog dialogAdd = new AddDataDialog();
        dialogAdd.isAdd(this, dialoglayout, "Add Player");
        dialogAdd.setmOnAddDataListener(this);
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

    @Override
    public void onDeleteItemClick(int position) {
        String mTitle = getString(R.string.dialog_delete_tittle);
        String mMessage = getString(R.string.dialog_delete_message);
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.isConfirm(this, mTitle, mMessage, position);
        dialog.setmOnConfirmDialogListener(this);
    }

    @Override
    public void onDialogConfirm(int position) {
        if (position == -1) {
            finish();
        } else {
            mPlayers.get(position).delete();
            mPlayers.remove(position);
            scaleAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onAddData() {
        String name = mEdtName.getText().toString();
        String number = mEdtNumber.getText().toString();
        String country = mEdtCountry.getText().toString();
        String weight = mEdtWeight.getText().toString();
        String height = mEdtHeight.getText().toString();
        String position = mEdtPosition.getText().toString();
        String birthday = mEdtBirthday.getText().toString();

        PlayerItem playerItem = new PlayerItem(name, number, country,
                weight, height, position, birthday, getTeamId);
        playerItem.save();
        mPlayers.add(playerItem);
        scaleAdapter.notifyDataSetChanged();
    }
}
