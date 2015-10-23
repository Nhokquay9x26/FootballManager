package vn.asiantech.internship.footballmanager.widgets;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by nhokquay9x26 on 23/10/15.
 */
public class ShowDialog {
    private Context mContext;
    private String mTitle;
    private String mMessage;

    private showDialog mShowDialog;

    public ShowDialog(Context context) {
        this.mContext = context;
        init(context);
    }

    public void init(Context context) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(mTitle);
        dialog.setMessage(mMessage);
        dialog.setCancelable(false);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialog.setNegativeButton("Cancel", null);
        dialog.show();
    }

    public interface showDialog {
//        void ();
    }
}
