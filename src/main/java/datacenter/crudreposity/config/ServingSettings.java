package datacenter.crudreposity.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by qingtao.kong on 2015/2/6.
 */
@Component
@ConfigurationProperties(prefix = "common")
public class ServingSettings {
    private String hbase_conf_dir;
    private String proxy_host;
    private String stock_host;
    private Boolean client_proxy;
    private String k_url_patern;

    public String getK_url_patern() {
        return k_url_patern;
    }

    public void setK_url_patern(String k_url_patern) {
        this.k_url_patern = k_url_patern;
    }


    public String getHbase_conf_dir() {
        return hbase_conf_dir;
    }
    public void setHbase_conf_dir(String hbase_conf_dir) {
        this.hbase_conf_dir = hbase_conf_dir;
    }

    public String getProxy_host() {
        return proxy_host;
    }

    public void setProxy_host(String proxy_host) {
        this.proxy_host = proxy_host;
    }

    public String getStock_host() {
        return stock_host;
    }

    public void setStock_host(String stock_host) {
        this.stock_host = stock_host;
    }

    public Boolean getClient_proxy() {
        return client_proxy;
    }

    public void setClient_proxy(Boolean client_proxy) {
        this.client_proxy = client_proxy;
    }
}
