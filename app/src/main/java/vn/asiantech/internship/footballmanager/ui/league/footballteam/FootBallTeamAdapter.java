package vn.asiantech.internship.footballmanager.ui.league.footballteam;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.footballmanager.R;
import vn.asiantech.internship.footballmanager.common.Utils;
import vn.asiantech.internship.footballmanager.model.FootBallTeamItem;
import vn.asiantech.internship.footballmanager.widgets.CircleImageView;

/**
 * Created by nhokquay9x26 on 21/10/15.
 */
public class FootBallTeamAdapter extends RecyclerView.Adapter<FootBallTeamAdapter.ViewHolder> {

    private List<FootBallTeamItem> mTeams;
    private OnItemListener mOnItemListener;
    private boolean isShowDelete;

    public OnItemListener getmOnItemListener() {
        return mOnItemListener;
    }

    public void setmOnItemListener(OnItemListener mOnItemListener) {
        this.mOnItemListener = mOnItemListener;
    }

    public FootBallTeamAdapter(List<FootBallTeamItem> mTeams, boolean isShowDelete) {
        this.mTeams = mTeams;
        this.isShowDelete = isShowDelete;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_team, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FootBallTeamAdapter.ViewHolder holder, int position) {
        holder.mTvName.setText(mTeams.get(position).getName());
        holder.mTvDescription.setText(mTeams.get(position).getDescription());
        Utils.loadImage(mTeams.get(position).getLogo(), holder.mCircleImageView);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTvName;
        TextView mTvDescription;
        CircleImageView mCircleImageView;
        ImageView mImgDelete;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            mImgDelete = (ImageView) view.findViewById(R.id.imgDelete);
            mImgDelete.setOnClickListener(this);
            mTvName = (TextView) view.findViewById(R.id.tvName);
            mTvDescription = (TextView) view.findViewById(R.id.tvDescription);
            mCircleImageView = (CircleImageView) view.findViewById(R.id.circleImageView);
            if (!isShowDelete) {
                mImgDelete.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.imgDelete) {
                mOnItemListener.onDeleteItemClick(getAdapterPosition());
            } else if (mOnItemListener != null) {
                mOnItemListener.onItemClick(getAdapterPosition());
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mTeams != null) {
            return mTeams.size();
        } else {
            return 0;
        }
    }

    public interface OnItemListener {
        void onItemClick(int position);

        void onDeleteItemClick(int position);
    }
}
