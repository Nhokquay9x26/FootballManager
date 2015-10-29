package vn.asiantech.internship.footballmanager.common;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import vn.asiantech.internship.footballmanager.widgets.CircleImageView;

/**
 * Created by nhokquay9x26 on 21/10/15.
 */
public class Utils {
    public static final String EXTRA_KEY_LEAGUE_ID = "leagueId";
    public static final String EXTRA_KEY_TEAM_ID = "teamId";
    public static final String EXTRA_KEY_PLAYER_ID = "playerId";
    public static final int PICK_PHOTO_FOR_AVATAR = 1;
    public static final int PICK_FROM_CAMERA = 2;

    public static String getPicturePath(Uri uriImage, Context context) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        if(uriImage != null) {
            Cursor cursor = context.getContentResolver().query(uriImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String path = cursor.getString(columnIndex);
            cursor.close();
            return path;
        }else {
         return null;
        }
    }

    public static Bitmap getThumbnail(String mImgPath) {
        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mImgPath, bounds);
        if ((bounds.outWidth == -1) || (bounds.outHeight == -1))
            return null;
        int originalSize = (bounds.outHeight > bounds.outWidth) ?
                bounds.outHeight
                : bounds.outWidth;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = originalSize / 500;
        return BitmapFactory.decodeFile(mImgPath, opts);
    }

    public static void loadImage(String image, CircleImageView circleImageView) {
        Bitmap mBitmap = getThumbnail(image);
        if (mBitmap != null) {
            circleImageView.setImageBitmap(mBitmap);
        }
    }
}
