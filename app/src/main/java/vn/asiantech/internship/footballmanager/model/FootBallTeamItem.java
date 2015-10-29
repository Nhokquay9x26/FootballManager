package vn.asiantech.internship.footballmanager.model;

import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Table;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by nhokquay9x26 on 21/10/15.
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Table(name = "Team")
public class FootBallTeamItem extends SugarRecord {
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "nationality")
    private String nationality;
    @Column(name = "year")
    String year;
    @Column(name = "logo")
    private String logo;
    @Column(name = "leagueId")
    private long leagueId;

    public FootBallTeamItem() {

    }

    public FootBallTeamItem(String logo, String name, String description, String nationality, String year, long leagueId) {
        this.name = name;
        this.description = description;
        this.nationality = nationality;
        this.logo = logo;
        this.leagueId = leagueId;
        this.year = year;
    }

    public static void deleteFootBallTeamByLeagueId(long id) {
        PlayerItem.deleteFootBallTeamByTeamId(id);
        FootBallTeamItem footBallTeam = FootBallTeamItem.findById(FootBallTeamItem.class, id);
        if (footBallTeam != null) {
            footBallTeam.delete();
        }
    }

    public static List<FootBallTeamItem> getAllTeamByLeagueId(long id) {
        return FootBallTeamItem.findWithQuery(FootBallTeamItem.class, "Select * from Team where leagueId = " + id);
    }

    public static FootBallTeamItem getLeagueById(long id) {
        return FootBallTeamItem.findById(FootBallTeamItem.class, id);
    }


    public static FootBallTeamItem getTeamById(int getTeamId) {
        return findById(FootBallTeamItem.class, getTeamId);
    }
}
