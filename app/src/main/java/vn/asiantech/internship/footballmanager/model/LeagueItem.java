package vn.asiantech.internship.footballmanager.model;

import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Table;

import java.util.List;

import lombok.Data;

/**
 * Created by nhokquay9x26 on 20/10/15.
 */
@Data
@Table(name = "LEAGUES")
public class LeagueItem extends SugarRecord {
    @Column(name = "name")
    private String name;
    @Column(name = "logo")
    private String logo;

    public LeagueItem() {

    }

    public LeagueItem(String name, String logo) {
        this.name = name;
        this.logo = logo;
    }

    public static List<LeagueItem> getAllLeague(){
        return LeagueItem.listAll(LeagueItem.class);
    }

    public static LeagueItem getLeagueById(long id) {
        return LeagueItem.findById(LeagueItem.class, id);
    }

    public static void updateLeague(LeagueItem league) {
        if (league != null) {
            league.save();
        }
    }

    public static void deleteLeagueById(long id) {
        FootBallTeamItem.deleteFootBallTeamByLeagueId(id);
        LeagueItem league = LeagueItem.findById(LeagueItem.class, id);
        if (league != null) {
            league.delete();
        }
    }
}
