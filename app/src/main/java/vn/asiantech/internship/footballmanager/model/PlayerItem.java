package vn.asiantech.internship.footballmanager.model;

import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Table;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.Arrays;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by nhokquay9x26 on 23/10/15.
 */
@EqualsAndHashCode(callSuper = false)
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
    @Column(name = "logo")
    String logo;
    @Column(name = "teamId")
    long teamId;

    public PlayerItem() {

    }

    public String getAge() {
        int day = 2015 - Integer.parseInt(getBirthday().toString().substring(0, 4));
        String age = String.valueOf(day);
        return age;
    }

    public PlayerItem(String logo, String name, String number, String country, String weight, String height,
                      String position, String birthday, long teamId) {
        this.logo = logo;
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.number = number;
        this.country = country;
        this.birthday = birthday;
        this.position = position;
        this.teamId = teamId;
    }

    public static void deletePlayerById(long id) {
        PlayerItem playerItem = PlayerItem.findById(PlayerItem.class, id);
        if (playerItem != null) {
            playerItem.delete();
        }
    }

    public static void deleteFootBallTeamByTeamId(long id) {
        List<PlayerItem> players = PlayerItem.find(PlayerItem.class, "teamId = ?", String.valueOf(id));
        if ((players != null)) {
            for (PlayerItem player : players) {
                PlayerItem.deletePlayerById(player.getId());
            }
        }
    }

    public static PlayerItem getTeamById(long id) {
        return PlayerItem.findById(PlayerItem.class, id);
    }

    public static List<PlayerItem> getAllPlayerByTeamId(int getTeamId) {
        return findWithQuery(PlayerItem.class, "Select * from Player where teamId = " + getTeamId);
    }

    public static List<PlayerItem> getAllPlayerByName(String name) {
        if (name != null && name.length() > 0) {
            return Select.from(PlayerItem.class).where(Condition.prop("name").like(name + "%")).list();
        }
        return null;
    }


    public enum EnumPosition {
        CF,
        LW, SS, RW,
        AM,
        LM, CM, RM,
        DM,
        LWB, RWB,
        LB, CB, RB,
        SW,
        GK
    }

    public static List<EnumPosition> getAllPosition() {
        return Arrays.asList(EnumPosition.values());
    }

    public static PlayerItem getPlayerById(long id) {
        return PlayerItem.findById(PlayerItem.class, id);
    }

    public static EnumPosition getEnumPosition(String position) {
        switch (position) {
            case "CF":
                return EnumPosition.CF;
            case "LW":
                return EnumPosition.LW;
            case "SS":
                return EnumPosition.SS;
            case "RW":
                return EnumPosition.RW;
            case "AM":
                return EnumPosition.AM;
            case "LM":
                return EnumPosition.LM;
            case "CM":
                return EnumPosition.CM;
            case "RM":
                return EnumPosition.RM;
            case "DM":
                return EnumPosition.DM;
            case "LWB":
                return EnumPosition.LWB;
            case "RWB":
                return EnumPosition.RWB;
            case "LB":
                return EnumPosition.LB;
            case "CB":
                return EnumPosition.CB;
            case "RB":
                return EnumPosition.RB;
            case "SW":
                return EnumPosition.SW;
            case "GK":
                return EnumPosition.GK;
            default:
                return null;
        }
    }

}
