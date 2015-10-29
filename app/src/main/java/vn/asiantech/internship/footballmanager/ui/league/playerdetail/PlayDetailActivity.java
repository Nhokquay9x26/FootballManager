package vn.asiantech.internship.footballmanager.ui.league.playerdetail;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Calendar;
import java.util.List;

import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;
import vn.asiantech.internship.footballmanager.R;
import vn.asiantech.internship.footballmanager.common.Utils;
import vn.asiantech.internship.footballmanager.dialog.ListDataDialog;
import vn.asiantech.internship.footballmanager.model.FootBallTeamItem;
import vn.asiantech.internship.footballmanager.model.PlayerItem;
import vn.asiantech.internship.footballmanager.ui.league.footballteam.FootBallTeamAdapter;
import vn.asiantech.internship.footballmanager.widgets.CircleImageView;
import vn.asiantech.internship.footballmanager.widgets.ToolBar;

/**
 * Created by nhokquay9x26 on 23/10/15.
 */
@EActivity(R.layout.activity_player_detail)
public class PlayDetailActivity extends Activity implements ToolBar.OnToolBarListener,
        View.OnClickListener, FootBallTeamAdapter.OnItemListener {

    @ViewById(R.id.edtName)
    EditText mEdtName;
    @ViewById(R.id.tvPosition)
    TextView mTvPosition;
    @ViewById(R.id.tvTeam)
    TextView mTvTeam;
    @ViewById(R.id.edtNumber)
    EditText mEdtNumber;
    @ViewById(R.id.tvBirthday)
    TextView mTvBirthday;
    @ViewById(R.id.tvAge)
    TextView mTvAge;
    @ViewById(R.id.edtCountry)
    EditText mEdtCountry;
    @ViewById(R.id.edtHeight)
    EditText mEdtHeight;
    @ViewById(R.id.edtWeight)
    EditText mEdtWeight;
    @ViewById(R.id.tool_bar_player_detail)
    ToolBar mToolBar;
    @ViewById(R.id.circleImageView)
    CircleImageView mCircleImageView;

    private PlayerItem mPlayers;
    private Uri mUri;
    private String mImgPath;
    private ListDataDialog mSelectDialog;
    private String idNew;
    RecyclerView mRecyclerView;
    List<FootBallTeamItem> mTeams;
    FootBallTeamAdapter mAdapter;

    int getIdPlayer;
    int getIdTeam;

    @AfterViews
    void afterView() {
        Bundle bundle = getIntent().getExtras();
        getIdPlayer = Integer.parseInt(bundle.getString(Utils.EXTRA_KEY_PLAYER_ID));
        getIdTeam = Integer.parseInt(bundle.getString(Utils.EXTRA_KEY_TEAM_ID));
        mPlayers = PlayerItem.findById(PlayerItem.class, getIdPlayer);
        mToolBar = (ToolBar) findViewById(R.id.tool_bar_player_detail);
        mToolBar.setTitle("INFORMATION PLAYER");
        mToolBar.setmOnToolBarListener(this);
        init();
    }

    public void init() {
        mEdtName.setText(mPlayers.getName());
        mTvPosition.setText(mPlayers.getPosition());
        mEdtNumber.setText(mPlayers.getNumber());
        mTvBirthday.setText(mPlayers.getBirthday());
        mTvAge.setText(mPlayers.getAge());
        mEdtCountry.setText(mPlayers.getCountry());
        mEdtWeight.setText(mPlayers.getWeight());
        mEdtHeight.setText(mPlayers.getHeight());

        PlayerItem playerItem = PlayerItem.getTeamById(getIdPlayer);
        Utils.loadImage(playerItem.getLogo(), mCircleImageView);

        FootBallTeamItem teamItem = FootBallTeamItem.getLeagueById(getIdTeam);
        mTvTeam.setText(teamItem.getName());
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
        mEdtNumber.setEnabled(true);
        mTvBirthday.setEnabled(true);
        mEdtCountry.setEnabled(true);
        mEdtHeight.setEnabled(true);
        mEdtWeight.setEnabled(true);
        mToolBar.changeEditImage();
        mTvTeam.setEnabled(true);
        mCircleImageView.setEnabled(true);
        mTvPosition.setEnabled(true);
        mTvBirthday.setOnClickListener(this);
        mTvTeam.setOnClickListener(this);
        mTvPosition.setOnClickListener(this);
        mCircleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }



    private void updateLeague() {
        String newLogo = Utils.getPicturePath(mUri, this);
        String newName = mEdtName.getText().toString();
        String newNumber = mEdtNumber.getText().toString();
        String newBirthday = mTvBirthday.getText().toString();
        String newCountry = mEdtCountry.getText().toString();
        String newHeight = mEdtHeight.getText().toString();
        String newWeight = mEdtWeight.getText().toString();
        String newPosition = mTvPosition.getText().toString();

        PlayerItem playerItem = PlayerItem.findById(PlayerItem.class, getIdPlayer);
        playerItem.setName(newName);
        playerItem.setNumber(newNumber);
        playerItem.setBirthday(newBirthday);
        playerItem.setCountry(newCountry);
        if (newLogo != null) {
            Log.d("a", "New Logo = "+newLogo);
            playerItem.setLogo(newLogo);
        }
        playerItem.setHeight(newHeight);
        playerItem.setWeight(newWeight);
        if (idNew != null) {
            playerItem.setTeamId(Integer.parseInt(idNew));
        }
        playerItem.save();

        int year = 2015 - Integer.parseInt(newBirthday.substring(0, 4));
        String y = String.valueOf(year);

        mTvAge.setText(y);
        mEdtName.setEnabled(false);
        mEdtNumber.setEnabled(false);
        mTvBirthday.setEnabled(false);
        mEdtCountry.setEnabled(false);
        mEdtHeight.setEnabled(false);
        mEdtWeight.setEnabled(false);
        mTvTeam.setEnabled(false);
        mTvPosition.setEnabled(false);
        mCircleImageView.setEnabled(false);
        mToolBar.changeEditImage();
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvTeam: {
                LayoutInflater inflater = getLayoutInflater();
                View dialoglayout = inflater.inflate(R.layout.dialog_list_team, null);
                mSelectDialog = new ListDataDialog();
                mSelectDialog.isAddList(this, dialoglayout, getString(R.string.dialog_tittle_select_team));

                mRecyclerView = (RecyclerView) dialoglayout.findViewById(R.id.recycleView);
                mTeams = FootBallTeamItem.listAll(FootBallTeamItem.class);
                mAdapter = new FootBallTeamAdapter(mTeams, false);
                mAdapter.setmOnItemListener(this);
                mRecyclerView.setHasFixedSize(true);
                LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
                mRecyclerView.setLayoutManager(mLinearLayoutManager);
                AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mAdapter);
                ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(alphaAdapter);
                scaleAdapter.setDuration(500);
                scaleAdapter.setFirstOnly(false);
                mRecyclerView.setAdapter(scaleAdapter);
                break;
            }
            case R.id.tvPosition: {
                LayoutInflater inflater = getLayoutInflater();
                View dialoglayout = inflater.inflate(R.layout.dialog_list_team, null);
                ListDataDialog dialogAdd = new ListDataDialog();
                dialogAdd.isAddList(this, dialoglayout, "Select Position");

                break;
            }
            case R.id.tvBirthday: {
                showDatePicker(v);
                break;
            }
        }
    }

    public void showDatePicker(View v) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpd = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mTvBirthday.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }

    @Override
    public void onItemClick(int position) {
        mTvTeam.setText(mTeams.get(position).getName());
        idNew = mTeams.get(position).getId().toString();
        mSelectDialog.dismissSelectDialog();
    }

    @Override
    public void onDeleteItemClick(int position) {

    }
}
