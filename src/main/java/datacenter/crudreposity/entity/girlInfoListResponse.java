package datacenter.crudreposity.entity;

import java.util.List;

/**
 * @描述
 * @创建人 shicong.zhang
 * @创建时间 $date$
 * @修改人和其它信息
 */
public class girlInfoListResponse {
    private List<Girlnfo> response_data;
    private String key;

    public girlInfoListResponse(List<Girlnfo> response_data) {
         this.response_data = response_data;
    }

    public girlInfoListResponse() {

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<Girlnfo> getResponse_data() {
        return response_data;
    }

    public void setResponse_data(List<Girlnfo> response_data) {
        this.response_data = response_data;
    }


}


