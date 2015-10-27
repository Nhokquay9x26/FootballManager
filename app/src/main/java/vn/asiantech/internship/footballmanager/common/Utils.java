package vn.asiantech.internship.footballmanager.common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import vn.asiantech.internship.footballmanager.widgets.CircleImageView;

/**
 * Created by nhokquay9x26 on 21/10/15.
 */
public class Utils {
    public static final String EXTRA_KEY_LEAGUE_ID = "leagueId";
    public static final String EXTRA_KEY_TEAM_ID = "teamId";
    public static final String EXTRA_KEY_PLAYER_ID = "playerId";
    public static final int PICK_PHOTO_FOR_AVATAR = 1;

    public static String convertBitmapToString(Bitmap bitmap) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(byteArray, Base64.DEFAULT);
        } catch (Exception e) {
            return null;
        }
    }

    public static Bitmap convertStringToBitmap(String encode) {
        try {
            InputStream inputStream = new ByteArrayInputStream(Base64.decode(encode.getBytes(), Base64.DEFAULT));
            return BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            return null;
        }
    }

    public static void loadImage(String image, CircleImageView circleImageView) {
        Bitmap bitmap = convertStringToBitmap(image);
        if (bitmap != null) {
            circleImageView.setImageBitmap(bitmap);
        }
    }
}
