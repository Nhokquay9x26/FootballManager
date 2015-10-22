package vn.asiantech.internship.footballmanager.model;

import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Table;

import lombok.Data;

/**
 * Created by nhokquay9x26 on 21/10/15.
 */
@Data
@Table(name = "Team")
public class FootBallTeamItem extends SugarRecord {
    @Column(name = "name")
    private String name;
    @Column(name = "nationality")
    private String nationality;
    @Column(name = "year")
    String year;
    @Column(name = "logo")
    private int logo;
    @Column(name = "leagueId")
    private long leagueId;

    public FootBallTeamItem() {

    }

    public FootBallTeamItem(int logo, String name, String nationality, String year, long leagueId) {
        this.name = name;
        this.nationality = nationality;
        this.logo = logo;
        this.leagueId = leagueId;
        this.year = year;
    }
}
