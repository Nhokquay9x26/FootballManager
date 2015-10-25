package vn.asiantech.internship.footballmanager.widgets;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by nhokquay9x26 on 23/10/15.
 */
public class ConfirmDialog {
    public ConfirmDialog() {
    }

    private OnConfirmDialogListener sOnConfirmDialogListener;

    public void setmOnConfirmDialogListener(OnConfirmDialogListener onConfirmDialogListener) {
        this.sOnConfirmDialogListener = onConfirmDialogListener;
    }

    public interface OnConfirmDialogListener {
        void onDialogConfirm(int position);
    }

    public void isConfirm(Context context, String title, String message,final int position) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (sOnConfirmDialogListener != null) {
                            sOnConfirmDialogListener.onDialogConfirm(position);
                        }
                    }
                })
                .show();
    }

}
