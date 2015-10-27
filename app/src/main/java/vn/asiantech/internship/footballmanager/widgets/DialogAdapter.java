package vn.asiantech.internship.footballmanager.widgets;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import vn.asiantech.internship.footballmanager.R;
import vn.asiantech.internship.footballmanager.ui.league.playerdetail.PlayDetailActivity;

/**
 * Created by nhokquay9x26 on 26/10/15.
 */
public class DialogAdapter extends RecyclerView.Adapter<DialogAdapter.ViewHolder> {
    List<PlayDetailActivity.Position> mPos;

    public DialogAdapter(List<PlayDetailActivity.Position> mPos){
        this.mPos = mPos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    protected class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemCount() {
        return mPos.size();
    }
}
