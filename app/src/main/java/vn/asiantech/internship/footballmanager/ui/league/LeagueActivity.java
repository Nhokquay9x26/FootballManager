package vn.asiantech.internship.footballmanager.ui.league;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
import vn.asiantech.internship.footballmanager.model.LeagueItem;
import vn.asiantech.internship.footballmanager.ui.league.footballteam.FootBallTeamActivity_;
import vn.asiantech.internship.footballmanager.widgets.ToolBar;

/**
 * Created by nhokquay9x26 on 20/10/15.
 */
@EActivity(R.layout.activity_league)
public class LeagueActivity extends Activity implements LeagueAdapter.OnItemListener, ToolBar.OnToolBarListener {

    private Effectstype effect;
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
        toolBar();
        animationRecycleView();

    }

    public void toolBar() {
        mToolBar = (ToolBar) findViewById(R.id.tool_bar_league);
        mToolBar.setmOnToolBarListener(this);
        mToolBar.setTitle("LEAGUE");
    }

    public void animationRecycleView() {
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
                .withTitle("Add League")
                .withTitleColor("#000000")
                .withEffect(effect)
                .withButton1Text("Cancel")
                .withDialogColor("#0099FF")
                .withButton2Text("Ok")
                .setCustomView(R.layout.dialog_add_league, v.getContext())
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText edtName = (EditText) dialogBuilder.findViewById(R.id.edtName);
                        String name = edtName.getText().toString();
                        LeagueItem leagueItem = new LeagueItem(name);
                        leagueItem.save();
                        mLeagues.add(leagueItem);
                        scaleAdapter.notifyDataSetChanged();
                        dialogBuilder.dismiss();
                    }
                })
                .show();
    }

    @Override
    public void onItemClick(int position) {
        mSelect = position;
        FootBallTeamActivity_.intent(LeagueActivity.this)
                .extra(Utils.EXTRA_KEY_NAME, mLeagues.get(position).getName())
                .extra(Utils.EXTRA_KEY_LEAGUE_ID, mLeagues.get(position).getId().toString())
                .start();
    }

    @Override
    public void onDeleteItemClick(final int position) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Delete League");
        dialog.setMessage(Html.fromHtml("Do you delete " + ("<b>" + mLeagues.get(position).getName() + "</b>") + "?"));
        dialog.setCancelable(false);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mLeagues.get(position).delete();
                mLeagues.remove(position);
                scaleAdapter.notifyDataSetChanged();
            }
        });
        dialog.setNegativeButton("Cancel", null);
        dialog.show();
    }

    @Override
    public void goBack() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Delete League");
        dialog.setMessage(Html.fromHtml("You exit ?"));
        dialog.setCancelable(false);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        dialog.setNegativeButton("Cancel", null);
        dialog.show();
    }

    @Override
    public void doEdit() {

    }
}
