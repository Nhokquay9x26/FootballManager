package vn.asiantech.internship.footballmanager.model;

import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Table;

import lombok.Data;

/**
 * Created by nhokquay9x26 on 29/10/15.
 */
@Data
@Table(name = "Coach")
public class CoachItem extends SugarRecord {
    @Column(name = "logo")
    private String logo;
    @Column(name = "name")
    private String name;
    @Column(name = "birthday")
    private String birthday;
    @Column(name = "country")
    private String country;
    @Column(name = "teamId")
    private long teamId;

    public CoachItem() {

    }

    public CoachItem(String logo, String name, String birthday, String country, long teamId) {
        this.logo = logo;
        this.name = name;
        this.birthday = birthday;
        this.country = country;
        this.teamId = teamId;
    }
}
