package vn.asiantech.internship.footballmanager.model;

import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Table;

import lombok.Data;

/**
 * Created by nhokquay9x26 on 20/10/15.
 */
@Data
@Table(name = "LEAGE")
public class LeagueItem extends SugarRecord {
    @Column(name = "name")
    String name;
    @Column(name = "logo")
    int logo;

    public LeagueItem(){

    }

    public LeagueItem(String name){
        this.name = name;
        this.logo = logo;
    }
}
