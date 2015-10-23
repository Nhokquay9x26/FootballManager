package vn.asiantech.internship.footballmanager.ui.league.footballteam;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.footballmanager.R;
import vn.asiantech.internship.footballmanager.model.FootBallTeamItem;
import vn.asiantech.internship.footballmanager.widgets.CircleImageView;

/**
 * Created by nhokquay9x26 on 21/10/15.
 */
public class FootBallTeamAdapter extends RecyclerView.Adapter<FootBallTeamAdapter.ViewHolder> {

    private List<FootBallTeamItem> mTeams;
    private OnItemListener mOnItemListener;

    public OnItemListener getmOnItemListener() {
        return mOnItemListener;
    }

    public void setmOnItemListener(OnItemListener mOnItemListener) {
        this.mOnItemListener = mOnItemListener;
    }

    public FootBallTeamAdapter(List<FootBallTeamItem> mTeams) {
        this.mTeams = mTeams;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_league, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FootBallTeamAdapter.ViewHolder holder, int position) {
        holder.mTvName.setText(mTeams.get(position).getName());
        holder.mCircleImageView.setImageResource(mTeams.get(position).getLogo());
    }

    protected class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTvName;
        CircleImageView mCircleImageView;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            mTvName = (TextView) view.findViewById(R.id.tvName);
            mCircleImageView = (CircleImageView) view.findViewById(R.id.circleImageView);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemListener != null) {
                mOnItemListener.onItemClick(getAdapterPosition());
            }
        }
    }

    @Override
    public int getItemCount() {
        return mTeams.size();
    }

    public interface OnItemListener {
        void onItemClick(int position);
    }
}
