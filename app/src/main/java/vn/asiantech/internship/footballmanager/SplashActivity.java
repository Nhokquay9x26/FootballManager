package vn.asiantech.internship.footballmanager;

import android.app.Activity;
import android.os.Handler;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import vn.asiantech.internship.footballmanager.model.CoachItem;
import vn.asiantech.internship.footballmanager.model.FootBallTeamItem;
import vn.asiantech.internship.footballmanager.model.LeagueItem;
import vn.asiantech.internship.footballmanager.model.PlayerItem;
import vn.asiantech.internship.footballmanager.ui.league.LeagueActivity_;

@EActivity(R.layout.activity_splash)
public class SplashActivity extends Activity {

    @AfterViews
    void afterViews() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LeagueActivity_.intent(SplashActivity.this).start();
                finish();
            }
        }, 2000);
        List<LeagueItem> mLeague = LeagueItem.getAllLeague();
        if (mLeague.size() <= 0) {
            addDemoLeagueData();
        }
    }

    private void addDemoLeagueData() {
        int i = 0;
        while (i < 5) {
            LeagueItem league = new LeagueItem("League Name " + i, "");
            league.save();
            addDemoFootBallData(league.getId());
            i++;
        }
    }

    private void addDemoFootBallData(Long id) {
        int i = 0;
        while (i < 5) {
            FootBallTeamItem footBallTeam = new FootBallTeamItem("", "Foot Ball Name " + id + i,
                    "If you have other conditions like group by, " +
                            "order by or limit, you could use the " +
                            "following method on the domain entity: " + i,
                    "Anh " + i, "1990", i);
            footBallTeam.save();
            addDemoCoachData(footBallTeam.getId());
            addDemoPlayerData(footBallTeam.getId());
            i++;
        }
    }

    private void addDemoCoachData(long team_id) {
        String name = randomName();
        String birthday = randomBirthday();
        CoachItem coach = new CoachItem("", name, birthday, "Lào", team_id);
        coach.save();
    }

    private void addDemoPlayerData(Long id) {
        int i = 0;
        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30));
        while (i < 5) {
            Random r = new Random();
            int height = 160 + r.nextInt(40);
            int weight = 50 + r.nextInt(40);
            int index = r.nextInt(numbers.size());
            int number = numbers.get(index);
            numbers.remove(index);
            PlayerItem player = new PlayerItem("", randomName(), String.valueOf(number),
                    "England", String.valueOf(weight), String.valueOf(height), randomPosition(),
                    randomBirthday(), id);
            player.save();
            i++;
        }
    }

    private String randomPosition() {
        Random r = new Random();
        int position = 1 + r.nextInt(16);
        switch (position) {
            case 1:
                return PlayerItem.EnumPosition.CF.toString();
            case 2:
                return PlayerItem.EnumPosition.LW.toString();
            case 3:
                return PlayerItem.EnumPosition.SS.toString();
            case 4:
                return PlayerItem.EnumPosition.RW.toString();
            case 5:
                return PlayerItem.EnumPosition.AM.toString();
            case 6:
                return PlayerItem.EnumPosition.LM.toString();
            case 7:
                return PlayerItem.EnumPosition.CM.toString();
            case 8:
                return PlayerItem.EnumPosition.RM.toString();
            case 9:
                return PlayerItem.EnumPosition.DM.toString();
            case 10:
                return PlayerItem.EnumPosition.LWB.toString();
            case 11:
                return PlayerItem.EnumPosition.RWB.toString();
            case 12:
                return PlayerItem.EnumPosition.LB.toString();
            case 13:
                return PlayerItem.EnumPosition.CB.toString();
            case 14:
                return PlayerItem.EnumPosition.RB.toString();
            case 15:
                return PlayerItem.EnumPosition.SW.toString();
            case 16:
                return PlayerItem.EnumPosition.GK.toString();
            default:
                return PlayerItem.EnumPosition.GK.toString();
        }
    }

    private String randomName() {
        List<String> names = new ArrayList<>(Arrays.asList("David", "Peter", "Leon", "Leonard", "Rosie", "Richard", "William", "Albert", "Robert", "Eric"));
        Random r = new Random();
        int i = r.nextInt(names.size());
        String name = names.get(i);
        names.remove(i);
        i = r.nextInt(names.size());
        name = name + " " + names.get(i);
        return name;
    }

    private String randomBirthday() {
        String birthday;
        Random r = new Random();
        int y = 1985 + r.nextInt(10);
        int m = 1 + r.nextInt(12);
        int d = 1 + r.nextInt(28);
        birthday = y + "/" + m + "/" + d;
        return birthday;
    }

}
