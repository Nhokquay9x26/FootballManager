package vn.asiantech.internship.footballmanager.model;

import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Table;

import lombok.Data;

/**
 * Created by nhokquay9x26 on 23/10/15.
 */
@Data
@Table(name = "Player")
public class PlayerItem extends SugarRecord {
    @Column(name = "name")
    String name;
    @Column(name = "weight")
    String weight;
    @Column(name = "height")
    String height;
    @Column(name = "number")
    String number;
    @Column(name = "country")
    String country;
    @Column(name = "birthday")
    String birthday;
    @Column(name = "position")
    String position;
    @Column(name = "teamId")
    long teamId;

    public PlayerItem() {

    }

    public PlayerItem(String name, String number, String country, String weight, String height, String position, String birthday, long teamId) {
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.number = number;
        this.country = country;
        this.birthday = birthday;
        this.position = position;
        this.teamId = teamId;
    }

}
