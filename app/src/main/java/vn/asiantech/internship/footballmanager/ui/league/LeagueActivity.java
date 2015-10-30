package vn.asiantech.internship.footballmanager.ui.league;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import java.util.List;

import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;
import vn.asiantech.internship.footballmanager.R;
import vn.asiantech.internship.footballmanager.common.CheckDataNull;
import vn.asiantech.internship.footballmanager.common.Utils;
import vn.asiantech.internship.footballmanager.dialog.AddDataDialog;
import vn.asiantech.internship.footballmanager.dialog.ConfirmDialog;
import vn.asiantech.internship.footballmanager.model.LeagueItem;
import vn.asiantech.internship.footballmanager.model.PlayerItem;
import vn.asiantech.internship.footballmanager.ui.league.footballteam.FootBallTeamActivity_;
import vn.asiantech.internship.footballmanager.ui.league.player.PlayerActivity;
import vn.asiantech.internship.footballmanager.ui.league.player.PlayerAdapter;
import vn.asiantech.internship.footballmanager.ui.league.playerdetail.PlayDetailActivity_;
import vn.asiantech.internship.footballmanager.widgets.CircleImageView;

/**
 * Created by nhokquay9x26 on 20/10/15.
 */
@EActivity(R.layout.activity_league)
public class LeagueActivity extends Activity implements LeagueAdapter.OnItemListener,
        ConfirmDialog.OnConfirmDialogListener, AddDataDialog.OnAddDataListener, TextWatcher, PlayerAdapter.OnItemListener {

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
    @ViewById(R.id.imgMenu)
    ImageView mImgMenu;

    private boolean isClickSearch = false;
    private EditText mEdtName;
    private String mImgPath;
    private Uri mUri;
    private CircleImageView mCircleImageView;
    private List<LeagueItem> mLeagues;
    private List<PlayerItem> mPlayer;
    private LeagueAdapter mAdapter;
    private PlayerAdapter mAdapterPlayer;
    private ScaleInAnimationAdapter mScaleAdapter;
    private int mPositionSelect = -1;

    @Override
    protected void onStart() {
        super.onStart();
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
        mAdapter = new LeagueAdapter(mLeagues);
        mAdapter.setmOnItemListener(this);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
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
    void showAddData() {
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
                selectImage();
            }
        });
    }

    public void selectImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, Utils.PICK_PHOTO_FOR_AVATAR);
    }

    public void capturePicture() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
            startActivityForResult(intent, Utils.PICK_FROM_CAMERA);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == Utils.PICK_FROM_CAMERA || requestCode == Utils.PICK_PHOTO_FOR_AVATAR)
                && resultCode == RESULT_OK) {
            mUri = data.getData();
            mImgPath = Utils.getPicturePath(mUri, this);
            Bitmap mBitmapLeagueLogo = Utils.getThumbnail(mImgPath);
            mCircleImageView.setImageBitmap(mBitmapLeagueLogo);
        }
    }

    @Override
    public void onAddData(Dialog dialog) {
        String name = mEdtName.getText().toString();
        String logo = mImgPath;
        String isCheck = CheckDataNull.isCheckLeague(name, logo);
        if (!isCheck.equals(CheckDataNull.RETURN_TOAST_OK)) {
            Toast.makeText(this, isCheck, Toast.LENGTH_SHORT).show();
        } else {
            LeagueItem leagueItem = new LeagueItem(name, logo);
            leagueItem.save();
            mLeagues.add(leagueItem);
            mScaleAdapter.notifyDataSetChanged();
            Toast.makeText(this, getString(R.string.toast_add_data_suscess), Toast.LENGTH_LONG).show();
            dialog.dismiss();
        }
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
        showDialog(getString(R.string.dialog_delete_tittle),
                getString(R.string.dialog_delete_message), position);
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

    @Click(R.id.imgBack)
    void exitApp() {
        showDialog(getString(R.string.dialog_exit_tittle),
                getString(R.string.dialog_exit_message), -1);
    }


    @Click(R.id.imgMenu)
    void menuSelect() {
    }

    @Click(R.id.imgSearch)
    void searchPlayer() {
        if (!isClickSearch) {
            mEdtSearch.setVisibility(View.VISIBLE);
            mTvTitle.setVisibility(View.INVISIBLE);
            mImgMenu.setVisibility(View.INVISIBLE);
            mEdtSearch.addTextChangedListener(this);
            mImgBtnSearch.setImageResource(R.drawable.ic_cancel);
            isClickSearch = true;
        } else {
            mEdtSearch.setVisibility(View.INVISIBLE);
            mTvTitle.setVisibility(View.VISIBLE);
            mImgMenu.setVisibility(View.VISIBLE);
            mEdtSearch.setText("");
            afterView();
            mImgBtnSearch.setImageResource(R.drawable.ic_search);
            isClickSearch = false;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        mPlayer = PlayerItem.getAllPlayerByName("");
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String search = mEdtSearch.getText().toString();
        mPlayer = PlayerItem.getAllPlayerByName(search);
        mAdapterPlayer = new PlayerAdapter(mPlayer);
        mAdapterPlayer.setmOnItemListener(this, false);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mAdapterPlayer);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    @Override
    public void onPlayerItemClick(int position) {
        PlayDetailActivity_.intent(LeagueActivity.this)
                .extra(Utils.EXTRA_KEY_PLAYER_ID, mPlayer.get(position).getId().toString())
                .extra(Utils.EXTRA_KEY_TEAM_ID, String.valueOf(mPlayer.get(position).getTeamId()))
                .start();
    }

    @Override
    public void onPlayerDeleteItemClick(int position) {

    }
}
