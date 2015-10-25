package vn.asiantech.internship.footballmanager.ui.league;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.melnykov.fab.FloatingActionButton;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import java.util.List;

import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;
import vn.asiantech.internship.footballmanager.R;
import vn.asiantech.internship.footballmanager.common.Utils;
import vn.asiantech.internship.footballmanager.model.LeagueItem;
import vn.asiantech.internship.footballmanager.ui.league.footballteam.FootBallTeamActivity_;
import vn.asiantech.internship.footballmanager.widgets.AddDataDialog;
import vn.asiantech.internship.footballmanager.widgets.ConfirmDialog;
import vn.asiantech.internship.footballmanager.widgets.ToolBar;

/**
 * Created by nhokquay9x26 on 20/10/15.
 */
@EActivity(R.layout.activity_league)
public class LeagueActivity extends Activity implements LeagueAdapter.OnItemListener,
        ToolBar.OnToolBarListener, ConfirmDialog.OnConfirmDialogListener,
        AddDataDialog.OnAddDataListener {

    EditText mEdtName;
    List<LeagueItem> mLeagues;
    LeagueAdapter mAdapter;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLinearLayoutManager;
    ScaleInAnimationAdapter scaleAdapter;
    ToolBar mToolBar;
    private int mSelect = -1;

    @Override
    protected void onStart() {
        super.onStart();
        if (mSelect >= 0) {
            LeagueItem leagueItem = LeagueItem.findById(LeagueItem.class, mLeagues.get(mSelect).getId());
            mLeagues.set(mSelect, leagueItem);
            scaleAdapter.notifyDataSetChanged();
        }
    }

    @AfterViews
    void afterView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
        mLeagues = LeagueItem.listAll(LeagueItem.class);
        mAdapter = new LeagueAdapter(mLeagues);
        mAdapter.setmOnItemListener(this);
        mRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        init();

    }

    public void init() {
        mToolBar = (ToolBar) findViewById(R.id.tool_bar_league);
        mToolBar.setmOnToolBarListener(this);
        mToolBar.setTitle("LEAGUE");
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
        View dialoglayout = inflater.inflate(R.layout.dialog_add_league, null);
        mEdtName = (EditText) dialoglayout.findViewById(R.id.edtName);
        AddDataDialog dialogAdd = new AddDataDialog();
        dialogAdd.isAdd(this, dialoglayout, "Add League");
        dialogAdd.setmOnAddDataListener(this);
    }

    @Override
    public void onItemClick(int position) {
        mSelect = position;
        FootBallTeamActivity_.intent(LeagueActivity.this)
                .extra(Utils.EXTRA_KEY_LEAGUE_ID, mLeagues.get(position).getId().toString())
                .start();
    }

    @Override
    public void onDeleteItemClick(final int position) {
        showDialog(getString(R.string.dialog_delete_tittle), getString(R.string.dialog_delete_message), position);
    }

    public void showDialog(String mTitle, String mMessage, int pos) {
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.isConfirm(this, mTitle, mMessage, pos);
        dialog.setmOnConfirmDialogListener(this);
    }

    @Override
    public void goBack() {
        showDialog(getString(R.string.dialog_exit_tittle), getString(R.string.dialog_exit_message), -1);
    }

    @Override
    public void doEdit() {

    }

    @Override
    public void onDialogConfirm(int position) {
        if (position == -1) {
            finish();
        } else {
            mLeagues.get(position).delete();
            mLeagues.remove(position);
            scaleAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onAddData() {
        String name = mEdtName.getText().toString();
        LeagueItem leagueItem = new LeagueItem(name);
        leagueItem.save();
        mLeagues.add(leagueItem);
        scaleAdapter.notifyDataSetChanged();
    }
}
