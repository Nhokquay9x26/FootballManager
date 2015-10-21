package vn.asiantech.internship.footballmanager.ui.league;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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

    List<LeagueItem> mLeagues;
    LeagueAdapter mAdapter;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLinearLayoutManager;

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
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(alphaAdapter);
        scaleAdapter.setDuration(500);
        scaleAdapter.setFirstOnly(false);
        mRecyclerView.setAdapter(scaleAdapter);
    }

    @Click(R.id.imgAdd)
    void addData() {
        for (int i = 0; i < 50; i++) {
            LeagueItem leagueItem = new LeagueItem("League " + i);
            leagueItem.save();
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {
    }
}
