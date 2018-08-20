package datacenter.crudreposity.entity.mysql2;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "hk_shareipo")
public class HK_ShareIPOModel implements java.io.Serializable{
    private static final long serialVersionUID = 1000004L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String code;
    private String exchange;

    private float PubApplyMultiple;
    private float issueprice;
    private  int TradeUnit;
    private  String name;
    private Date datetoaccount;

    private  float issuepriceceiling;
    private  float issuepricefloor;

    public float getIssuepriceceiling() {
        return issuepriceceiling;
    }

    public void setIssuepriceceiling(float issuepriceceiling) {
        this.issuepriceceiling = issuepriceceiling;
    }

    public float getIssuepricefloor() {
        return issuepricefloor;
    }

    public void setIssuepricefloor(float issuepricefloor) {
        this.issuepricefloor = issuepricefloor;
    }

    public Date getProposedlistdate() {
        return proposedlistdate;
    }

    public void setProposedlistdate(Date proposedlistdate) {
        this.proposedlistdate = proposedlistdate;
    }

    private Date proposedlistdate;

    public Date getDatetoaccount() {
        return datetoaccount;
    }

    public void setDatetoaccount(Date datetoaccount) {
        this.datetoaccount = datetoaccount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public float getPubApplyMultiple() {
        return PubApplyMultiple;
    }

    public void setPubApplyMultiple(float pubApplyMultiple) {
        PubApplyMultiple = pubApplyMultiple;
    }

    public float getIssueprice() {
        return issueprice;
    }

    public void setIssueprice(float issueprice) {
        this.issueprice = issueprice;
    }

    public int getTradeUnit() {
        return TradeUnit;
    }

    public void setTradeUnit(int tradeUnit) {
        TradeUnit = tradeUnit;
    }
}
