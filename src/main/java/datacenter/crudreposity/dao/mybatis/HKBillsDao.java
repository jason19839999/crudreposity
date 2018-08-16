package datacenter.crudreposity.dao.mybatis;

import datacenter.crudreposity.entity.HKBill;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;

public interface HKBillsDao {
    @Select("select * from hk_bills")
    ArrayList<HKBill> getAllBills();

    @Insert("insert into hk_bills(name,rowkey,code,bill_type,bill_date,png_location,pdf_location,email) values(#{name},#{rowkey},#{code},#{bill_type},#{bill_date},#{png_location},#{pdf_location},#{email})")
    int insertBill(HKBill hkBill);

    @Select("select * from hk_bills where name=#{name} and code=#{code} and bill_date = #{bill_date} limit 1")
    HKBill getBillByCND(HKBill hkBill);

    @Update("update hk_bills set name=#{name}, rowkey=#{rowkey} ,code=#{code}, bill_type=#{bill_type},bill_date = #{bill_date}, png_location = #{png_location},pdf_location = #{pdf_location},email=#{email}")
    int updateBill(HKBill hkBill);

    @Select("select email from regist_account_log where funds_account=#{funds_account} and is_effective=1 and is_set_pwd = 1 and is_rewrite = 1 limit 1")
    String getEmail(@Param("funds_account") String funds_account);

    @Select("select email from regist_account_log where funds_account=#{funds_account} and email is not null order by insert_time desc limit 1")
    String getLastEmail(@Param("funds_account") String funds_account);

    @Update("update hk_bills set name=#{name} where code=#{code}")
    int updateBillName(HKBill hkBill);

}
