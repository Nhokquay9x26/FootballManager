package vn.asiantech.internship.footballmanager.ui.league.footballteam;

import android.app.Activity;
import android.content.Intent;
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
import vn.asiantech.internship.footballmanager.ui.league.player.PlayerActivity_;
import vn.asiantech.internship.footballmanager.widgets.ToolBar;

/**
 * Created by nhokquay9x26 on 21/10/15.
 */
@EActivity(R.layout.activity_footballteam)
public class FootBallTeamActivity extends Activity implements FootBallTeamAdapter.OnItemListener, ToolBar.OnToolBarListener {

    EditText mEdtName;
    private Effectstype effect;
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
        String name = bundle.getString(Utils.EXTRA_KEY_NAME);
        getId = Integer.parseInt(bundle.getString(Utils.EXTRA_KEY_LEAGUE_ID));
        mEdtName = (EditText) findViewById(R.id.edtName);
        mEdtName.setText(name);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
        mTeams = FootBallTeamItem.findWithQuery(FootBallTeamItem.class, "Select * from Team where leagueId = " + getId);
        mAdapter = new FootBallTeamAdapter(mTeams);
        mAdapter.setmOnItemListener(this);
        mRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        animationRecycle();

    }

    public void animationRecycle() {
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
                .withTitle("Add Teams")
                .withTitleColor("#000000")
                .withEffect(effect)
                .withButton1Text("Cancel")
                .withDialogColor("#0099FF")
                .withButton2Text("Ok")
                .setCustomView(R.layout.dialog_add_team, v.getContext())
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
                        EditText mEdtNationality = (EditText) dialogBuilder.findViewById(R.id.edtNationality);
                        EditText mEdtYear = (EditText) dialogBuilder.findViewById(R.id.edtYear);

                        String name = mEdtName.getText().toString();
                        String nationality = mEdtNationality.getText().toString();
                        String year = mEdtYear.getText().toString();

                        FootBallTeamItem footBallTeamItem = new FootBallTeamItem(R.drawable.ic_football, name, nationality, year, getId);
                        footBallTeamItem.save();
                        mTeams.add(footBallTeamItem);
                        scaleAdapter.notifyDataSetChanged();
                        dialogBuilder.dismiss();
                    }
                })
                .show();
    }

    @Override
    public void onItemClick(int position) {
        mSelect = position;
        PlayerActivity_.intent(FootBallTeamActivity.this)
                .extra(Utils.EXTRA_KEY_TEAM_ID, mTeams.get(position).getId().toString())
                .extra(Utils.EXTRA_KEY_NAME, mTeams.get(position).getName())
                .extra(Utils.EXTRA_KEY_NATIONALITY, mTeams.get(position).getNationality())
                .extra(Utils.EXTRA_KEY_YEAR, mTeams.get(position).getYear())
                .start();
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
        LeagueItem leagueItem = LeagueItem.findById(LeagueItem.class, getId);
        leagueItem.setName(newName);
        leagueItem.save();

        mEdtName.setEnabled(false);
        mToolBar.changeEditImage();
    }
}
