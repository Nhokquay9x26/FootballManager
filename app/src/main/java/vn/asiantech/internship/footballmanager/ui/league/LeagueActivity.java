package vn.asiantech.internship.footballmanager.ui.league;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;
import vn.asiantech.internship.footballmanager.R;
import vn.asiantech.internship.footballmanager.common.Utils;
import vn.asiantech.internship.footballmanager.dialog.AddDataDialog;
import vn.asiantech.internship.footballmanager.dialog.ConfirmDialog;
import vn.asiantech.internship.footballmanager.model.LeagueItem;
import vn.asiantech.internship.footballmanager.ui.league.footballteam.FootBallTeamActivity_;
import vn.asiantech.internship.footballmanager.widgets.CircleImageView;

/**
 * Created by nhokquay9x26 on 20/10/15.
 */
@EActivity(R.layout.activity_league)
public class LeagueActivity extends Activity implements LeagueAdapter.OnItemListener,
        ConfirmDialog.OnConfirmDialogListener, AddDataDialog.OnAddDataListener, TextWatcher {

    @ViewById(R.id.recycleView)
    RecyclerView mRecyclerView;
    @ViewById(R.id.imgBtnAdd)
    FloatingActionButton imgBtnAdd;
    @ViewById(R.id.edtSearch)
    EditText mEdtSearch;
    @ViewById(R.id.tvTitle)
    TextView mTvTitle;
    @ViewById(R.id.imgSearch)
    ImageView mImgBtnSearch;

    private EditText mEdtName;
    private Bitmap mBitmapLeagueLogo;
    private CircleImageView mCircleImageView;
    private List<LeagueItem> mLeagues;
    private LeagueAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private ScaleInAnimationAdapter mScaleAdapter;
    private int mPositionSelect = -1;

    @Override
    protected void onStart() {
        super.onStart();
        updateListLeague();
    }

    public void updateListLeague() {
        if (mLeagues != null && mPositionSelect >= 0 && mPositionSelect < mLeagues.size()) {
            long id = mLeagues.get(mPositionSelect).getId();
            LeagueItem league = LeagueItem.getLeagueById(id);
            mLeagues.set(mPositionSelect, league);
            mScaleAdapter.notifyDataSetChanged();
        }
    }

    @AfterViews
    void afterView() {
        mLeagues = LeagueItem.getAllLeague();
        mAdapter = new LeagueAdapter(mLeagues, true);
        mAdapter.setmOnItemListener(this);
        mRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        setAnimationRecycleView();
    }

    public void setAnimationRecycleView() {
        imgBtnAdd.attachToRecyclerView(mRecyclerView);
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mAdapter);
        mScaleAdapter = new ScaleInAnimationAdapter(alphaAdapter);
        mScaleAdapter.setDuration(500);
        mScaleAdapter.setFirstOnly(false);
        mRecyclerView.setAdapter(mScaleAdapter);
    }

    @Click(R.id.imgBtnAdd)
    void addData() {
        LayoutInflater inflater = getLayoutInflater();
        View dialog = inflater.inflate(R.layout.dialog_add_league, null);
        AddDataDialog dialogAdd = new AddDataDialog();
        dialogAdd.isAdd(this, dialog, getString(R.string.dialog_tittle_add_league));
        dialogAdd.setmOnAddDataListener(this);
        mEdtName = (EditText) dialog.findViewById(R.id.edtName);
        mCircleImageView = (CircleImageView) dialog.findViewById(R.id.circleImageView);
        mCircleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, Utils.PICK_PHOTO_FOR_AVATAR);
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        mPositionSelect = position;
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
    public void onDialogConfirm(int position) {
        if (position >= 0) {
            long id = mLeagues.get(position).getId();
            LeagueItem.deleteLeagueById(id);
            mLeagues.remove(position);
            mScaleAdapter.notifyDataSetChanged();
        } else {
            this.finish();
        }
    }

    @Override
    public void onAddData() {
        String name = mEdtName.getText().toString();
        String logo = Utils.convertBitmapToString(mBitmapLeagueLogo);
        if (name.equals("")){
            Toast.makeText(this, getString(R.string.toast_name_empty_error), Toast.LENGTH_LONG).show();
        } else if(mBitmapLeagueLogo == null) {
            Toast.makeText(this, getString(R.string.toast_image_empty_error), Toast.LENGTH_LONG).show();
        }else{
            LeagueItem leagueItem = new LeagueItem(name, logo);
            leagueItem.save();
            mLeagues.add(leagueItem);
            mScaleAdapter.notifyDataSetChanged();
            Toast.makeText(this, getString(R.string.toast_add_data_suscess), Toast.LENGTH_LONG).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Utils.PICK_PHOTO_FOR_AVATAR && data != null) {
            try {
                InputStream inputStream = this.getContentResolver().openInputStream(data.getData());
                mBitmapLeagueLogo = BitmapFactory.decodeStream(inputStream);
                mCircleImageView.setImageBitmap(mBitmapLeagueLogo);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    boolean click = false;

    @Click(R.id.imgSearch)
    void searchPlayer() {
        if (!click) {
            mEdtSearch.setVisibility(View.VISIBLE);
            mTvTitle.setVisibility(View.INVISIBLE);
            mEdtSearch.addTextChangedListener(this);
            mImgBtnSearch.setImageResource(R.drawable.ic_cancel);
            click = true;
        } else {
            mEdtSearch.setVisibility(View.INVISIBLE);
            mTvTitle.setVisibility(View.VISIBLE);
            mEdtSearch.setText("");
            mImgBtnSearch.setImageResource(R.drawable.ic_search);
            click = false;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Toast.makeText(this, mEdtSearch.getText().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
