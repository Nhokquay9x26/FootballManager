package vn.asiantech.internship.footballmanager.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import vn.asiantech.internship.footballmanager.R;


/**
 * Created by nhokquay9x26 on 25/10/15.
 */
public class ListDataDialog {
    Dialog mSelectDialog;

    public ListDataDialog() {

    }

    public void dismissSelectDialog() {
        mSelectDialog.dismiss();
    }

    public void isAddList(Context context, View layout, String mTitle) {
        mSelectDialog = new Dialog(context);
        mSelectDialog.setContentView(layout);
        mSelectDialog.setTitle(mTitle);
        mSelectDialog.setCancelable(false);
        mSelectDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        mSelectDialog.show();
    }
}
