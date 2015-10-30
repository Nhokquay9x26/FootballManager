package vn.asiantech.internship.footballmanager.ui.league.player;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
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
import vn.asiantech.internship.footballmanager.model.FootBallTeamItem;
import vn.asiantech.internship.footballmanager.model.LeagueItem;
import vn.asiantech.internship.footballmanager.model.PlayerItem;
import vn.asiantech.internship.footballmanager.ui.league.LeagueAdapter;
import vn.asiantech.internship.footballmanager.ui.league.playerdetail.PlayDetailActivity_;
import vn.asiantech.internship.footballmanager.dialog.AddDataDialog;
import vn.asiantech.internship.footballmanager.dialog.ConfirmDialog;
import vn.asiantech.internship.footballmanager.widgets.CircleImageView;
import vn.asiantech.internship.footballmanager.widgets.ToolBar;

/**
 * Created by nhokquay9x26 on 22/10/15.
 */
@EActivity(R.layout.activity_player)
public class PlayerActivity extends Activity implements PlayerAdapter.OnItemListener,
        ToolBar.OnToolBarListener, ConfirmDialog.OnConfirmDialogListener,
        AddDataDialog.OnAddDataListener {

    @ViewById(R.id.circleImageView)
    CircleImageView mCircleImageView;
    @ViewById(R.id.edtName)
    EditText mEdtNameShow;
    @ViewById(R.id.edtNationality)
    EditText mEdtNationality;
    @ViewById(R.id.edtDescription)
    EditText mEdtDescription;
    @ViewById(R.id.edtYear)
    EditText mEdtYear;
    @ViewById(R.id.tool_bar_player)
    ToolBar mToolBar;
    @ViewById(R.id.imgBtnAdd)
    FloatingActionButton imgBtnAdd;
    @ViewById(R.id.recycleView)
    RecyclerView mRecyclerView;

    EditText mEdtName;
    EditText mEdtNumber;
    EditText mEdtCountry;
    EditText mEdtHeight;
    EditText mEdtWeight;
    EditText mEdtPosition;
    EditText mEdtBirthday;

    private int getTeamId;
    private Uri mUri;
    private String mImgPath;
    private List<PlayerItem> mPlayers;
    private PlayerAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private ScaleInAnimationAdapter mScaleAdapter;
    private int mPositionSelect = -1;

    @Override
    protected void onStart() {
        super.onStart();
        if (mPlayers != null && mPositionSelect >= 0 && mPositionSelect < mPlayers.size()) {
            long id = mPlayers.get(mPositionSelect).getId();
            PlayerItem playerItem = PlayerItem.getTeamById(id);
            mPlayers.set(mPositionSelect, playerItem);
            mScaleAdapter.notifyDataSetChanged();
        }
    }

    @AfterViews
    void afterViews() {
        mToolBar.setTitle(getString(R.string.toolbar_name_player));
        mToolBar.setmOnToolBarListener(this);
        Bundle bundle = getIntent().getExtras();
        getTeamId = Integer.parseInt(bundle.getString(Utils.EXTRA_KEY_TEAM_ID));

        mPlayers = PlayerItem.getAllPlayerByTeamId(getTeamId);
        mAdapter = new PlayerAdapter(mPlayers);
        mAdapter.setmOnItemListener(this, true);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        addItemHeader();
        init();
    }

    public void addItemHeader() {
        FootBallTeamItem teamItem = FootBallTeamItem.getTeamById(getTeamId);
        Utils.loadImage(teamItem.getLogo(), mCircleImageView);
        mEdtNameShow.setText(teamItem.getName());
        mEdtNationality.setText(teamItem.getNationality());
        mEdtDescription.setText(teamItem.getDescription());
        mEdtYear.setText(teamItem.getYear());
    }

    public void init() {
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
        View dialoglayout = inflater.inflate(R.layout.dialog_add_player, null);

        mCircleImageView = (CircleImageView) dialoglayout.findViewById(R.id.circleImageView);
        mEdtName = (EditText) dialoglayout.findViewById(R.id.edtName);
        mEdtNumber = (EditText) dialoglayout.findViewById(R.id.edtNumber);
        mEdtCountry = (EditText) dialoglayout.findViewById(R.id.edtCountry);
        mEdtWeight = (EditText) dialoglayout.findViewById(R.id.edtWeight);
        mEdtHeight = (EditText) dialoglayout.findViewById(R.id.edtHeight);
        mEdtPosition = (EditText) dialoglayout.findViewById(R.id.edtPosition);
        mEdtBirthday = (EditText) dialoglayout.findViewById(R.id.edtBirthday);

        mCircleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        AddDataDialog dialogAdd = new AddDataDialog();
        dialogAdd.isAdd(this, dialoglayout, getString(R.string.dialog_tittle_add_player));
        dialogAdd.setmOnAddDataListener(this);
    }

    @Override
    public void onAddData(Dialog dialog) {
        String logo = mImgPath;
        String name = mEdtName.getText().toString();
        String number = mEdtNumber.getText().toString();
        String country = mEdtCountry.getText().toString();
        String weight = mEdtWeight.getText().toString();
        String height = mEdtHeight.getText().toString();
        String position = mEdtPosition.getText().toString();
        String birthday = mEdtBirthday.getText().toString();

        String isCheck = CheckDataNull.isCheckPlayer(name, logo, number, country,
                weight, height, position, birthday);
        if (!isCheck.equals(CheckDataNull.RETURN_TOAST_OK)) {
            Toast.makeText(this, isCheck, Toast.LENGTH_SHORT).show();
        } else {
            PlayerItem playerItem = new PlayerItem(logo, name, number, country,
                    weight, height, position, birthday, getTeamId);
            playerItem.save();
            mPlayers.add(playerItem);
            mScaleAdapter.notifyDataSetChanged();
            dialog.dismiss();
        }
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
        mEdtNameShow.setEnabled(true);
        mEdtNationality.setEnabled(true);
        mEdtDescription.setEnabled(true);
        mEdtYear.setEnabled(true);
        mToolBar.changeEditImage();
        mCircleImageView.setEnabled(true);
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
        if ((requestCode == Utils.PICK_FROM_CAMERA || requestCode == Utils.PICK_PHOTO_FOR_AVATAR) && resultCode == RESULT_OK) {
            mUri = data.getData();
            mImgPath = Utils.getPicturePath(mUri, this);
            Bitmap mBitmapLogo = Utils.getThumbnail(mImgPath);
            mCircleImageView.setImageBitmap(mBitmapLogo);
        }
    }

    private void updateLeague() {
        String newLogo = Utils.getPicturePath(mUri, this);
        String newName = mEdtNameShow.getText().toString();
        String newDescription = mEdtDescription.getText().toString();
        String newNationality = mEdtNationality.getText().toString();
        String newYear = mEdtYear.getText().toString();
        FootBallTeamItem footBallTeamItem = FootBallTeamItem.getTeamById(getTeamId);
        footBallTeamItem.setName(newName);
        footBallTeamItem.setDescription(newDescription);
        footBallTeamItem.setNationality(newNationality);
        if (newLogo != null) {
            footBallTeamItem.setLogo(newLogo);
        }
        footBallTeamItem.setYear(newYear);
        footBallTeamItem.save();

        mEdtNameShow.setEnabled(false);
        mEdtNationality.setEnabled(false);
        mEdtDescription.setEnabled(false);
        mCircleImageView.setEnabled(false);
        mEdtYear.setEnabled(false);
        mToolBar.changeEditImage();
    }

    @Override
    public void onDialogConfirm(int position) {
        if (position == -1) {
            finish();
        } else {
            mPlayers.get(position).delete();
            mPlayers.remove(position);
            mScaleAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onPlayerItemClick(int position) {
        mPositionSelect = position;
        PlayDetailActivity_.intent(PlayerActivity.this)
                .extra(Utils.EXTRA_KEY_PLAYER_ID, mPlayers.get(position).getId().toString())
                .extra(Utils.EXTRA_KEY_TEAM_ID, String.valueOf(getTeamId))
                .start();
    }

    @Override
    public void onPlayerDeleteItemClick(int position) {
        String mTitle = getString(R.string.dialog_delete_tittle);
        String mMessage = getString(R.string.dialog_delete_message);
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.isConfirm(this, mTitle, mMessage, position);
        dialog.setmOnConfirmDialogListener(this);
    }
}
