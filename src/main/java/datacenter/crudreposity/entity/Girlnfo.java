package datacenter.crudreposity.entity;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
/**
 * @描述
 * @创建人 shicong.zhang
 * @创建时间 $date$
 * @修改人和其它信息
 */

@Entity
@Table(name = "girl_info")
public class Girlnfo  implements java.io.Serializable{
    private static final long serialVersionUID = 1000004L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String cup_size;
    private int age;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCup_size() {
        return cup_size;
    }

    public void setCup_size(String cup_size) {
        this.cup_size = cup_size;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
