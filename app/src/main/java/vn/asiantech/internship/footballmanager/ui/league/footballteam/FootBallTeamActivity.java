package vn.asiantech.internship.footballmanager.ui.league.footballteam;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
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
import vn.asiantech.internship.footballmanager.dialog.AddDataDialog;
import vn.asiantech.internship.footballmanager.dialog.ConfirmDialog;
import vn.asiantech.internship.footballmanager.model.FootBallTeamItem;
import vn.asiantech.internship.footballmanager.model.LeagueItem;
import vn.asiantech.internship.footballmanager.ui.league.player.PlayerActivity_;
import vn.asiantech.internship.footballmanager.widgets.CircleImageView;
import vn.asiantech.internship.footballmanager.widgets.ToolBar;

/**
 * Created by nhokquay9x26 on 21/10/15.
 */
@EActivity(R.layout.activity_footballteam)
public class FootBallTeamActivity extends Activity implements FootBallTeamAdapter.OnItemListener,
        ToolBar.OnToolBarListener, ConfirmDialog.OnConfirmDialogListener,
        AddDataDialog.OnAddDataListener {
    @ViewById(R.id.edtName)
    EditText mEdtNameLeague;
    @ViewById(R.id.circleImageView)
    CircleImageView mCircleImageView;
    @ViewById(R.id.recycleView)
    RecyclerView mRecyclerView;
    @ViewById(R.id.tool_bar_team)
    ToolBar mToolBar;
    @ViewById(R.id.imgBtnAdd)
    FloatingActionButton imgBtnAdd;

    private EditText mEdtName;
    private EditText mEdtDescription;
    private EditText mEdtNationality;
    private EditText mEdtYear;
    private Uri mUri;
    private String mImgPath;
    private List<FootBallTeamItem> mTeams;
    private FootBallTeamAdapter mAdapter;
    private ScaleInAnimationAdapter mScaleAdapter;
    int getIdLeague;
    private int mPositionSelect = -1;

    @Override
    protected void onStart() {
        super.onStart();
        if (mTeams != null && mPositionSelect >= 0 && mPositionSelect < mTeams.size()) {
            long id = mTeams.get(mPositionSelect).getId();
            FootBallTeamItem teamItem = FootBallTeamItem.getLeagueById(id);
            mTeams.set(mPositionSelect, teamItem);
            mScaleAdapter.notifyDataSetChanged();
        }
    }

    @AfterViews
    void afterView() {
        mToolBar.setmOnToolBarListener(this);
        mToolBar.setTitle(getString(R.string.toolbar_name_team));
        Bundle bundle = getIntent().getExtras();
        getIdLeague = Integer.parseInt(bundle.getString(Utils.EXTRA_KEY_LEAGUE_ID));

        mTeams = FootBallTeamItem.getAllTeamByLeagueId(getIdLeague);
        mAdapter = new FootBallTeamAdapter(mTeams, true);
        mAdapter.setmOnItemListener(this);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        init();
        addItemHeader();
    }

    public void addItemHeader(){
        LeagueItem leagueItem = LeagueItem.getLeagueById(getIdLeague);
        mEdtNameLeague.setText(leagueItem.getName());
        Utils.loadImage(leagueItem.getLogo(), mCircleImageView);
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
        View dialog = inflater.inflate(R.layout.dialog_add_team, null);
        mEdtName = (EditText) dialog.findViewById(R.id.edtName);
        mEdtNationality = (EditText) dialog.findViewById(R.id.edtNationality);
        mEdtDescription = (EditText) dialog.findViewById(R.id.edtDescription);
        mEdtYear = (EditText) dialog.findViewById(R.id.edtYear);
        AddDataDialog dialogAdd = new AddDataDialog();
        dialogAdd.isAdd(this, dialog, getString(R.string.dialog_tittle_add_team));
        dialogAdd.setmOnAddDataListener(this);
        mCircleImageView = (CircleImageView) dialog.findViewById(R.id.circleImageView);
        mCircleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }

    @Override
    public void onAddData(Dialog dialog) {
        String logo = mImgPath;
        String name = mEdtName.getText().toString();
        String description = mEdtDescription.getText().toString();
        String nationality = mEdtNationality.getText().toString();
        String year = mEdtYear.getText().toString();
        String isCheck = CheckDataNull.isCheckTeam(name, logo, description, nationality, year);

        if (!isCheck.equals(CheckDataNull.RETURN_TOAST_OK)) {
            Toast.makeText(this, isCheck, Toast.LENGTH_SHORT).show();
        } else {
            FootBallTeamItem teamItem = new
                    FootBallTeamItem(logo, name, description, nationality, year, getIdLeague);
            teamItem.save();
            mTeams.add(teamItem);
            mScaleAdapter.notifyDataSetChanged();
            Toast.makeText(this, getString(R.string.toast_add_data_suscess), Toast.LENGTH_LONG).show();
            dialog.dismiss();
        }
    }

    @Override
    public void onItemClick(int position) {
        mPositionSelect = position;
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
    public void onDialogConfirm(int position) {
        if (position >= 0) {
            long id = mTeams.get(position).getId();
            FootBallTeamItem.deleteFootBallTeamByLeagueId(id);
            mTeams.remove(position);
            mScaleAdapter.notifyDataSetChanged();
        } else {
            this.finish();
        }
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
        mEdtNameLeague.setEnabled(true);
        mCircleImageView.setEnabled(true);
        mToolBar.changeEditImage();
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
        } else {
            Toast.makeText(getApplication(), "Camera không được hỗ trợ", Toast.LENGTH_LONG).show();
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
        String newName = mEdtNameLeague.getText().toString();
        String newLogo = Utils.getPicturePath(mUri, this);
        if (newName == null) {
            Toast.makeText(this, R.string.toast_name_empty_error, Toast.LENGTH_SHORT).show();
        } else {
            LeagueItem leagueItem = LeagueItem.getLeagueById(getIdLeague);
            leagueItem.setName(newName);
            if (newLogo != null) {
                leagueItem.setLogo(newLogo);
            }
            leagueItem.save();
            mEdtNameLeague.setEnabled(false);
            mCircleImageView.setEnabled(false);
            mToolBar.changeEditImage();
        }
    }

}
