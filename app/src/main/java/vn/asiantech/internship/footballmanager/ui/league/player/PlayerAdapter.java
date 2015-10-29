package vn.asiantech.internship.footballmanager.ui.league.player;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.footballmanager.R;
import vn.asiantech.internship.footballmanager.common.Utils;
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
        holder.mTvName.setText(mPlayers.get(position).getName());
        holder.mTvNumber.setText(mPlayers.get(position).getNumber());
        holder.mTvPosition.setText(mPlayers.get(position).getPosition());
        holder.mTvCountry.setText(mPlayers.get(position).getCountry());
        Utils.loadImage(mPlayers.get(position).getLogo(), holder.mCircleImageView);
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
        TextView mTvName;
        TextView mTvNumber;
        TextView mTvPosition;
        TextView mTvCountry;
        ImageView imgDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mTvNumber = (TextView) itemView.findViewById(R.id.tvNumber);
            mTvPosition = (TextView) itemView.findViewById(R.id.tvPosition);
            mTvCountry = (TextView) itemView.findViewById(R.id.tvCountry);
            mCircleImageView = (CircleImageView) itemView.findViewById(R.id.circleImageView);
            imgDelete = (ImageView) itemView.findViewById(R.id.imgDelete);
            imgDelete.setOnClickListener(this);
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

    public interface OnItemListener {
        void onItemClick(int position);

        void onDeleteItemClick(int position);
    }
}
