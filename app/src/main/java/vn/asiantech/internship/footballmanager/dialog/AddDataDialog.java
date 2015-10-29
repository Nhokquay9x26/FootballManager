package vn.asiantech.internship.footballmanager.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

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
        void onAddData(Dialog dialog);
    }

    public void isAdd(Context context, View layout, String mTitle) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(layout);
        dialog.setTitle(mTitle);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        Button btnAdd = (Button) dialog.findViewById(R.id.btnAdd);
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnAddDataListener != null) {
                    mOnAddDataListener.onAddData(dialog);
                }
            }
        });
        dialog.show();
    }
}
