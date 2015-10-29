package vn.asiantech.internship.footballmanager.common;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import vn.asiantech.internship.footballmanager.model.FootBallTeamItem;
import vn.asiantech.internship.footballmanager.model.LeagueItem;
import vn.asiantech.internship.footballmanager.model.PlayerItem;

/**
 * Created by nhokquay9x26 on 28/10/15.
 */
public class CheckDataNull {
    public static final String RETURN_TOAST_NAME = "Name Empty";
    public static final String RETURN_TOAST_LOGO = "Logo Empty";
    public static final String RETURN_TOAST_DESCRIPTION = "Description Empty";
    public static final String RETURN_TOAST_NATIONALITY = "Nationality Empty";
    public static final String RETURN_TOAST_YEAR = "Year Empty";
    public static final String RETURN_TOAST_WEIGHT = "Weight Empty";
    public static final String RETURN_TOAST_HEIGHT = "Height Empty";
    public static final String RETURN_TOAST_NUMBER = "Number Empty";
    public static final String RETURN_TOAST_COUNTRY = "Country Empty";
    public static final String RETURN_TOAST_POSITION = "Position Empty";
    public static final String RETURN_TOAST_BIRTHDAY = "Birthday Empty";
    public static final String RETURN_TOAST_NAME_REPEAT = "Name Repeat";
    public static final String RETURN_TOAST_OK = "OK";

    public static String isCheckLeague(String name, String logo) {
        List<LeagueItem> mLeague = LeagueItem.find(LeagueItem.class, "name = ?", name);
        if (name.equals("")) {
            return RETURN_TOAST_NAME;
        } else if (logo == null) {
            return RETURN_TOAST_LOGO;
        } else if (mLeague.size() > 0) {
            return RETURN_TOAST_NAME_REPEAT;
        } else {
            return RETURN_TOAST_OK;
        }
    }

    public static String isCheckTeam(String name, String logo,
                                     String description, String nationality, String year) {
        List<FootBallTeamItem> mTeams = FootBallTeamItem.find(FootBallTeamItem.class, "name = ?", name);
        if (name.equals("")) {
            return RETURN_TOAST_NAME;
        } else if (logo == null) {
            return RETURN_TOAST_LOGO;
        } else if (description.equals("")) {
            return RETURN_TOAST_DESCRIPTION;
        } else if (nationality.equals("")) {
            return RETURN_TOAST_NATIONALITY;
        } else if (year.equals("")) {
            return RETURN_TOAST_YEAR;
        } else if (mTeams.size() > 0) {
            return RETURN_TOAST_NAME_REPEAT;
        } else {
            return RETURN_TOAST_OK;
        }
    }

    public static String isCheckPlayer(String name, String logo, String number, String country,
                                       String weight, String height, String position, String birthday) {
        List<PlayerItem> mPlayer = PlayerItem.find(PlayerItem.class, "name = ?", name);

        if (name.equals("")) {
            return RETURN_TOAST_NAME;
        } else if (logo == null) {
            return RETURN_TOAST_LOGO;
        } else if (number.equals("")) {
            return RETURN_TOAST_NUMBER;
        } else if (country.equals("")) {
            return RETURN_TOAST_COUNTRY;
        } else if (weight.equals("")) {
            return RETURN_TOAST_WEIGHT;
        }else if (height.equals("")) {
            return RETURN_TOAST_HEIGHT;
        }else if (position.equals("")) {
            return RETURN_TOAST_POSITION;
        }else if (birthday.equals("")) {
            return RETURN_TOAST_BIRTHDAY;
        } else if (mPlayer.size() > 0) {
            return RETURN_TOAST_NAME_REPEAT;
        } else {
            return RETURN_TOAST_OK;
        }
    }
}
