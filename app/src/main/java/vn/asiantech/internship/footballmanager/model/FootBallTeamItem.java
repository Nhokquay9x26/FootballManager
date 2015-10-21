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
    @Column(name = "description")
    private String description;
    @Column(name = "logo")
    private int logo;
    @Column(name = "team")
    private long teamId;

    public FootBallTeamItem() {

    }

    public FootBallTeamItem(String name, String description, int logo, long teamId) {
        this.name = name;
        this.description = description;
        this.logo = logo;
        this.teamId = teamId;
    }
}
