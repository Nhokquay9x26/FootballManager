package vn.asiantech.internship.footballmanager.widgets;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;

import vn.asiantech.internship.footballmanager.R;

/**
 * Created by nhokquay9x26 on 25/10/15.
 */
public class AddDataDialog {
    public AddDataDialog() {

    }

    private OnAddDataListener mOnAddDataListener;

    public void setmOnAddDataListener(OnAddDataListener mOnAddDataListener) {
        this.mOnAddDataListener = mOnAddDataListener;
    }

    public interface OnAddDataListener {
        void onAddData();
    }

    public void isAdd(Context context, View layout, String mTitle) {
        new AlertDialog.Builder(context)
                .setView(layout)
                .setTitle(mTitle)
                .setCancelable(false)
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (mOnAddDataListener != null) {
                            mOnAddDataListener.onAddData();
                        }
                    }
                })
                .show();

    }


}
