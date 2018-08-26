package datacenter.crudreposity.entity.mongodb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Id;

/**
 * @描述
 * @创建人 shicong.zhang
 * @创建时间 $date$
 * @修改人和其它信息
 */

@Data
@AllArgsConstructor
@ToString
public class User {
    @Id
    private Integer id;
    private String name;
    private Integer age;

}
