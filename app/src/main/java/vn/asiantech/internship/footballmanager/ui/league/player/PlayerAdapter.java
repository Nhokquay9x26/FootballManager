package vn.asiantech.internship.footballmanager.ui.league.player;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.footballmanager.R;
import vn.asiantech.internship.footballmanager.model.PlayerItem;
import vn.asiantech.internship.footballmanager.widgets.CircleImageView;

/**
 * Created by nhokquay9x26 on 23/10/15.
 */
public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {

    List<PlayerItem> mPlayers;
    private OnItemListener mOnItemListener;

    public OnItemListener getmOnItemListener() {
        return mOnItemListener;
    }

    public void setmOnItemListener(OnItemListener mOnItemListener) {
        this.mOnItemListener = mOnItemListener;
    }

    public PlayerAdapter(List<PlayerItem> mPlayers) {
        this.mPlayers = mPlayers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_player, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvName.setText(mPlayers.get(position).getName());
        holder.tvNumber.setText(mPlayers.get(position).getNumber());
        holder.tvPosition.setText(mPlayers.get(position).getPosition());
        holder.tvCountry.setText(mPlayers.get(position).getCountry());
    }

    @Override
    public int getItemCount() {
        if (mPlayers != null) {
            return mPlayers.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView mCircleImageView;
        TextView tvName;
        TextView tvNumber;
        TextView tvAge;
        TextView tvPosition;
        TextView tvCountry;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.tvName = (TextView) itemView.findViewById(R.id.tvName);
            this.tvNumber = (TextView) itemView.findViewById(R.id.tvNumber);
            this.tvAge = (TextView) itemView.findViewById(R.id.tvAge);
            this.tvPosition = (TextView) itemView.findViewById(R.id.tvPosition);
            this.tvCountry = (TextView) itemView.findViewById(R.id.tvCountry);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemListener != null) {
                mOnItemListener.onItemClick(getAdapterPosition());
            }
        }
    }

    public interface OnItemListener {
        void onItemClick(int position);
    }
}
