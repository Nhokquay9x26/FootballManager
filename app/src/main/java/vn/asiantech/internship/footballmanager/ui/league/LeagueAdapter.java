package vn.asiantech.internship.footballmanager.ui.league;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.footballmanager.R;
import vn.asiantech.internship.footballmanager.model.LeagueItem;
import vn.asiantech.internship.footballmanager.widget.CircleImageView;

/**
 * Created by nhokquay9x26 on 20/10/15.
 */
public class LeagueAdapter extends RecyclerView.Adapter<LeagueAdapter.ViewHolder> {

    private List<LeagueItem> mLeagues;
    private OnItemListener mOnItemListener;

    public OnItemListener getmOnItemListener() {
        return mOnItemListener;
    }

    public void setmOnItemListener(OnItemListener mOnItemListener) {
        this.mOnItemListener = mOnItemListener;
    }

    public LeagueAdapter(List<LeagueItem> mLeagues) {
        this.mLeagues = mLeagues;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_league, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(LeagueAdapter.ViewHolder holder, int position) {
        holder.tvName.setText(mLeagues.get(position).getName());
        holder.mCircleImageViewLogo.setImageResource(mLeagues.get(position).getLogo());
    }

    protected class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvName;
        private CircleImageView mCircleImageViewLogo;

        public ViewHolder(View convertView) {
            super(convertView);
            convertView.setOnClickListener(this);
            tvName = (TextView) convertView.findViewById(R.id.tvName);
            mCircleImageViewLogo = (CircleImageView) convertView.findViewById(R.id.circleImageViewLeague);
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
        if (mLeagues != null) {
            return mLeagues.size();
        } else
            return 0;
    }

    public interface OnItemListener {
        void onItemClick(int position);
    }

}
