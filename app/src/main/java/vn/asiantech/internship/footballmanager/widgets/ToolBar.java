package vn.asiantech.internship.footballmanager.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import vn.asiantech.internship.footballmanager.R;

/**
 * Created by nhokquay9x26 on 22/10/15.
 */
public class ToolBar extends RelativeLayout implements View.OnClickListener {
    Context mContext;
    boolean isImageButton;
    private TextView mTvTitle;
    private OnToolBarListener mOnToolBarListener;
    private ImageView mImgEdit;
    private boolean isEditing = false;

    public void setmOnToolBarListener(OnToolBarListener mOnToolBarListener) {
        this.mOnToolBarListener = mOnToolBarListener;
    }


    public ToolBar(Context context, boolean isImageButton) {
        super(context);
        mContext = context;
        this.isImageButton = isImageButton;
        init(context);
    }

    public ToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(context);
    }

    private void init(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.custom_tool_bar, this, false);
        mTvTitle = (TextView) rootView.findViewById(R.id.tvTitle);
        ImageView mImgBack = (ImageView) rootView.findViewById(R.id.imgBack);
        mImgEdit = (ImageView) rootView.findViewById(R.id.imgEdit);
        mImgBack.setOnClickListener(this);
        mImgEdit.setOnClickListener(this);
//        if(isImageButton){
//            mImgEdit.setImageResource(R.drawable.ic_menu);
//        }
        this.addView(rootView);
    }

    public void setTitle(String title) {
        mTvTitle.setText(title);
    }

    public boolean getEditing() {
        return this.isEditing;
    }

    public void changeEditImage() {
        if (isEditing) {
            mImgEdit.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit));
            isEditing = false;
        } else {
            mImgEdit.setImageDrawable(getResources().getDrawable(R.drawable.ic_save));
            isEditing = true;
        }
    }

    @Override
    public void onClick(View v) {
        if (mOnToolBarListener != null) {
            int id = v.getId();
            switch (id) {
                case R.id.imgBack:
                    mOnToolBarListener.goBack();
                    break;
                case R.id.imgEdit:
                    mOnToolBarListener.doEdit();
                    break;
            }
        }
    }

    public interface OnToolBarListener {
        void goBack();

        void doEdit();
    }

}
