package vn.asiantech.internship.footballmanager.ui.league;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import java.util.List;

import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;
import vn.asiantech.internship.footballmanager.R;
import vn.asiantech.internship.footballmanager.model.LeagueItem;

/**
 * Created by nhokquay9x26 on 20/10/15.
 */
@EActivity(R.layout.activity_league)
public class LeagueActivity extends Activity implements LeagueAdapter.OnItemListener {

    private Effectstype effect;
    List<LeagueItem> mLeagues;
    LeagueAdapter mAdapter;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLinearLayoutManager;
    ScaleInAnimationAdapter scaleAdapter;

    @AfterViews
    void afterView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
        mLeagues = LeagueItem.listAll(LeagueItem.class);
        mAdapter = new LeagueAdapter(mLeagues);
        mAdapter.setmOnItemListener(this);
        mRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

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

    }
}
