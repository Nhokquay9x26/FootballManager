package vn.asiantech.internship.footballmanager.ui.league.footballteam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
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
import vn.asiantech.internship.footballmanager.ui.league.player.PlayerActivity_;
import vn.asiantech.internship.footballmanager.widgets.AddDataDialog;
import vn.asiantech.internship.footballmanager.widgets.ConfirmDialog;
import vn.asiantech.internship.footballmanager.widgets.ToolBar;

/**
 * Created by nhokquay9x26 on 21/10/15.
 */
@EActivity(R.layout.activity_footballteam)
public class FootBallTeamActivity extends Activity implements FootBallTeamAdapter.OnItemListener,
        ToolBar.OnToolBarListener, ConfirmDialog.OnConfirmDialogListener,
        AddDataDialog.OnAddDataListener {
    EditText mEdtName;
    EditText mEdtNationality;
    EditText mEdtYear;

    EditText mEdtNameShow;
    List<FootBallTeamItem> mTeams;
    FootBallTeamAdapter mAdapter;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLinearLayoutManager;
    ScaleInAnimationAdapter scaleAdapter;
    int getId;
    ToolBar mToolBar;
    private int mSelect = -1;

    @Override
    protected void onStart() {
        super.onStart();
        if (mSelect >= 0) {
            FootBallTeamItem footBallTeamItem = FootBallTeamItem.findById(FootBallTeamItem.class, mTeams.get(mSelect).getId());
            mTeams.set(mSelect, footBallTeamItem);
            scaleAdapter.notifyDataSetChanged();
        }
    }

    @AfterViews
    void afterView() {
        mToolBar = (ToolBar) findViewById(R.id.tool_bar_team);
        mToolBar.setmOnToolBarListener(this);
        mToolBar.setTitle("TEAM");
        Bundle bundle = getIntent().getExtras();
        getId = Integer.parseInt(bundle.getString(Utils.EXTRA_KEY_LEAGUE_ID));

        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
        mTeams = FootBallTeamItem.findWithQuery(FootBallTeamItem.class, "Select * from Team where leagueId = " + getId);
        mAdapter = new FootBallTeamAdapter(mTeams);
        mAdapter.setmOnItemListener(this);
        mRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        init();
    }

    public void init() {
        LeagueItem leagueItem = LeagueItem.findById(LeagueItem.class, getId);
        mEdtNameShow = (EditText) findViewById(R.id.edtName);
        mEdtNameShow.setText(leagueItem.getName());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.imgAdd);
        fab.attachToRecyclerView(mRecyclerView);
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mAdapter);
        scaleAdapter = new ScaleInAnimationAdapter(alphaAdapter);
        scaleAdapter.setDuration(500);
        scaleAdapter.setFirstOnly(false);
        mRecyclerView.setAdapter(scaleAdapter);
    }

    @Click(R.id.imgAdd)
    void addData() {
        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.dialog_add_team, null);
        mEdtName = (EditText) dialoglayout.findViewById(R.id.edtName);
        mEdtNationality = (EditText) dialoglayout.findViewById(R.id.edtNationality);
        mEdtYear = (EditText) dialoglayout.findViewById(R.id.edtYear);
        AddDataDialog dialogAdd = new AddDataDialog();
        dialogAdd.isAdd(this, dialoglayout, "Add Team");
        dialogAdd.setmOnAddDataListener(this);
    }

    @Override
    public void onItemClick(int position) {
        mSelect = position;
        PlayerActivity_.intent(FootBallTeamActivity.this)
                .extra(Utils.EXTRA_KEY_TEAM_ID, mTeams.get(position).getId().toString())
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
    public void goBack() {
        finish();
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
        LeagueItem leagueItem = LeagueItem.findById(LeagueItem.class, getId);
        leagueItem.setName(newName);
        leagueItem.save();

        mEdtName.setEnabled(false);
        mToolBar.changeEditImage();
    }


    @Override
    public void onDialogConfirm(int position) {
        if (position == -1) {
            finish();
        } else {
            mTeams.get(position).delete();
            mTeams.remove(position);
            scaleAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onAddData() {
        String name = mEdtName.getText().toString();
        String nationality = mEdtNationality.getText().toString();
        String year = mEdtYear.getText().toString();
        FootBallTeamItem teamItem = new FootBallTeamItem(R.drawable.ic_add, name, nationality, year, getId);
        teamItem.save();
        mTeams.add(teamItem);
        scaleAdapter.notifyDataSetChanged();
    }
}
