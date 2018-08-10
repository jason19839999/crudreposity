package datacenter.crudreposity.dao.hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.coprocessor.AggregationClient;
import org.apache.hadoop.hbase.client.coprocessor.LongColumnInterpreter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import datacenter.crudreposity.dao.hbase.HBaseConnectionFactory;
import datacenter.crudreposity.util.*;


/**
 * HBase工具类
 *
 * @author weishi
 *
 */
public class HBaseClientUtil{
    public static final Logger logger = LoggerFactory.getLogger(HBaseClientUtil.class);


    /**
     * get the results from hbase which rowkey in the given range
     * @param tableName
     * @param startRow
     * @param stopRow
     * @param family
     * @param qualifiers
     * @param num
     * @return
     * @throws Exception
     */
    public static Vector<Result> range(String tableName, byte[] startRow, byte[] stopRow, String family, Vector<String> qualifiers, int num) throws Exception {
        Vector<Result> results = new Vector<Result>();
        ResultScanner rs = null;
        HConnection conn = null;
        HTableInterface table = null;

        try {
            conn = HBaseConnectionFactory.gethConnection();
            table = conn.getTable(tableName.getBytes("utf-8"));
            Scan scan = new Scan();

            if(startRow != null){
                scan.setStartRow(startRow);
            }
            if(stopRow != null){
                scan.setStopRow(stopRow);
            }

            if( num >= 0){
                Filter filter = new PageFilter(num);
                scan.setFilter(filter);
            }

            if(family != null && family.length() > 0 && qualifiers != null){
                for(String qualifier:qualifiers){
                    scan.addColumn(family.getBytes("utf-8"), qualifier.getBytes("utf-8"));
                }
            }

            rs = table.getScanner(scan);
            if(num >= 0){
                for (Result r:rs.next(num)) {
                    results.add(r);
                    //printResult(result);
                }
            }else{
                for (Result r:rs) {
                    results.add(r);
                    //printResult(result);
                }
            }

        } catch (Exception e) {
            throw e;
        } finally {
            if (table != null) table.close();
            if (rs != null) rs.close();
            //if (conn != null) conn.close();
        }
        return results;
    }

    /**
     * get the results from hbase which rowkey start with the given rowPrefix
     * @param tableName
     * @param startRow
     * @param stopRow
     * @param family
     * @param qualifiers
     * @param num
     * @return
     * @throws Exception
     */
    public static Vector<Result> prefix(String tableName, byte[] rowPrefix, String family, Vector<String> qualifiers, int num) throws Exception {
        Vector<Result> results = new Vector<Result>();
        ResultScanner rs = null;
        HConnection conn = null;
        HTableInterface table = null;

        try {
            conn = HBaseConnectionFactory.gethConnection();
            table = conn.getTable(tableName.getBytes("utf-8"));
            Scan scan = new Scan();

//			if(startRow != null){
//				scan.setStartRow(startRow);
//			}
//			if(stopRow != null){
//				scan.setStopRow(stopRow);
//			}


            FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);

            Filter filterPrefix = new PrefixFilter(rowPrefix);
            filterList.addFilter(filterPrefix);;

            if( num > 0){
                Filter filter = new PageFilter(num);
                filterList.addFilter(filter);

            }
            scan.setFilter(filterList);


            if(family != null && family.length() > 0 && qualifiers != null){
                for(String qualifier:qualifiers){
                    scan.addColumn(family.getBytes("utf-8"), qualifier.getBytes("utf-8"));
                }
            }

            rs = table.getScanner(scan);
            if(num >= 0){
                for (Result r:rs.next(num)) {
                    results.add(r);
                    //printResult(result);
                }
            }else{
                for (Result r:rs) {
                    results.add(r);
                    //printResult(result);
                }
            }

        } catch (Exception e) {
            throw e;
        } finally {
            if (table != null) table.close();
            if (rs != null) rs.close();
            //if (conn != null) conn.close();
        }
        return results;
    }

    /**
     * 读一行数据
     * @param row = "9285e4f07fffff04236ce31f76e80000";
     * @throws Exception
     */
    public static Result get(String tableName,byte[] row,String family, Vector<String> qualifiers) throws Exception {
        Result result = null;
        HConnection conn = null;
        HTableInterface table = null;
        try {
            conn = HBaseConnectionFactory.gethConnection();
            table = conn.getTable(tableName.getBytes("utf-8"));
            Get scan = new Get(row);
            if(family != null && family.length() > 0 && qualifiers != null){
                for(String qualifier:qualifiers){
                    scan.addColumn(family.getBytes("utf-8"), qualifier.getBytes("utf-8"));
                }
            }
            result = table.get(scan);
            //printResult(result);

        } catch (Exception e) {
            throw e;
        } finally {
            if (table != null) table.close();
            //if (conn != null) conn.close();
        }
        return result;
    }

    /**
     * 读一行数据, row is hex str
     * @param row = "9285e4f07fffff04236ce31f76e80000";
     * @throws Exception
     */
    public static Result get(String tableName,String row,String family, Vector<String> qualifiers) throws Exception {

        return get(tableName,ByteUtil.decodeHex(row), family, qualifiers);
    }

    /**
     *
     * @param tableName
     * @param rows
     * @param family
     * @param qualifiers
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> Result[] get(String tableName,final List<T> rows, final String family,final Vector<String> qualifiers) throws Exception{
        Result[] results = null;
        HConnection conn = null;
        HTableInterface table = null;
        try {
            conn = HBaseConnectionFactory.gethConnection();
            table = conn.getTable(tableName.getBytes("utf-8"));
            List<Get> gets = new ArrayList<Get>(){
                {
                    for(T row : rows){
                        Get get = null;
                        if(row.getClass().getSimpleName().equals("String")) {
                            get = new Get(ByteUtil.decodeHex((String)row));
                        }
                        else if(row.getClass().getSimpleName().equals("byte[]")){
                            get = new Get((byte[])row);
                        }
                        if(family != null && family.length() > 0 && qualifiers != null){
                            for(String qualifier:qualifiers){
                                get.addColumn(family.getBytes("utf-8"), qualifier.getBytes("utf-8"));
                            }
                        }
                        add(get);
                    }
                }
            };
            results = table.get(gets);
            //printResult(result);

        } catch (Exception e) {
            throw e;
        } finally {
            if (table != null) table.close();
            //if (conn != null) conn.close();
        }
        return results;
    }


    /**
     * print the scan result
     * @param r the hbase scan result
     */
    public static void printResult(Result r){

        String row = ByteUtil.encodeHex(r.getRow());
        byte[] family;
        byte[] qualifier;
        byte[] value;
        String familyStr;
        String qualifierStr;
        String valueStr;
        for(Cell cell:r.rawCells()) {

            qualifier = CellUtil.cloneQualifier(cell);
            value = CellUtil.cloneValue(cell);
            family = CellUtil.cloneFamily(cell);
            if (new String(qualifier).equals("replynum") || new String(qualifier).equals("clicknum")
                    || new String(qualifier).equals("reposts_count") || new String(qualifier).equals("comments_count")
                    || new String(qualifier).equals("attitudes_count") || new String(qualifier).equals("status")
                    || new String(qualifier).equals("category") || new String(qualifier).equals("source_type")
                    || new String(qualifier).equals("followers_count") || new String(qualifier).equals("friends_count")
                    || new String(qualifier).equals("pagefriends_count") || new String(qualifier).equals("statuses_count")
                    || new String(qualifier).equals("favourites_count") || new String(qualifier).equals("verified_type")
                    || new String(qualifier).equals("ptype") || new String(qualifier).equals("online_status")
                    || new String(qualifier).equals("bi_followers_count") || new String(qualifier).equals("star")
                    || new String(qualifier).equals("mbtype") || new String(qualifier).equals("mbrank")
                    || new String(qualifier).equals("block_word") || new String(qualifier).equals("block_app")
                    || new String(qualifier).equals("level") || new String(qualifier).equals("type")
                    || new String(qualifier).equals("ulevel") || new String(qualifier).equals("credit_score")
                    || new String(qualifier).equals("urank") || new String(qualifier).equals("class")
                    || new String(qualifier).equals("level") || new String(qualifier).equals("type")
                    || new String(qualifier).equals("hot") || new String(qualifier).equals("rank")
                    || new String(qualifier).equals("weight")) {
                valueStr = ByteUtil.toInt(value)+"";
            } else {
                valueStr = new String(value);
            }

            familyStr = new String(family);
            qualifierStr = new String(qualifier);

            System.out.println(row + ":" + familyStr + ":" + qualifierStr + ":" + valueStr);

        }
    }



    /**
     * Increment the Column Value for a given rowkey
     * @param tableName
     * @param row
     * @param family
     * @param qualifier
     * @param amount
     * @return
     * @throws Exception
     */
    public static long incrementColumnValue(String tableName, byte[] row, String family, String qualifier, long amount) throws Exception {
        long cnt1 = 0;
        HConnection conn = null;
        HTableInterface table = null;
        try {
            conn = HBaseConnectionFactory.gethConnection();
            table = conn.getTable(tableName.getBytes("utf-8"));
            cnt1 =table.incrementColumnValue(row, family.getBytes(), qualifier.getBytes(),amount);
        } catch (Exception e) {
            throw e;
        } finally {
            if (table != null) table.close();
            //if (conn != null) conn.close();
        }
        return cnt1;
    }

    /**
     *
     * @param tableName
     * @param rowKey
     * @return
     * @throws Exception
     */
    public static boolean exists(String tableName,String rowKey)throws Exception{
        HConnection conn = null;
        HTableInterface table = null;
        try {
            conn = HBaseConnectionFactory.gethConnection();
            table = conn.getTable(tableName.getBytes("utf-8"));
            Get scan = new Get(ByteUtil.decodeHex(rowKey));
            return table.exists(scan);
        } catch (Exception e) {
            throw e;
        } finally {
            if (table != null) table.close();
        }
    }

    /**
     * delete one row
     * @param tableName
     * @param row
     * @throws Exception
     */
    public static void deleteRowByKey(String tableName, byte[] row) throws Exception {
        HConnection conn = null;
        HTableInterface table = null;
        try {
            conn = HBaseConnectionFactory.gethConnection();
            table = conn.getTable(tableName.getBytes("utf-8"));
            Delete d = new Delete(row);
            table.delete(d);;
            //logger.info("delete complete");
        } catch (Exception e) {
            throw e;
        } finally {
            if (table != null) table.close();
            //if (conn != null) conn.close();
        }
    }

    /**
     * delete rows in batch
     * @param tableName
     * @param row
     * @throws Exception
     */
    public static void deleteRowByKeys(String tableName, List<byte[]> rows) throws Exception {
        HConnection conn = null;
        HTableInterface table = null;
        try {
            conn = HBaseConnectionFactory.gethConnection();
            table = conn.getTable(tableName.getBytes("utf-8"));
            ArrayList<Delete> deletes =  new ArrayList<Delete>();
            for(byte[] row: rows){
                Delete d = new Delete(row);
                deletes.add(d);
            }
            table.delete(deletes);
            //logger.info("delete batch complete");
        } catch (Exception e) {
            throw e;
        } finally {
            if (table != null) table.close();
            //if (conn != null) conn.close();
        }
    }
    /**
     * hbase 不直接提供update方法，一种update方法是直接写入新数据，这样用（row_key:family_key:qulity_name:）默认读取时读出的是新数据，
     * 老数据可以通过增加timestamp,version等字段读出；如：（row_key:family_key:qulity_name:timestamp:version）；
     * @throws Exception
     */
    public static void updateValue(String tableName, byte[] row, String family, String qualifier, byte[] value) throws Exception {
        HConnection conn = null;
        HTableInterface table = null;
        try {
            conn = HBaseConnectionFactory.gethConnection();
            table = conn.getTable(tableName.getBytes("utf-8"));

            Put put = new Put(row);
            put.add(family.getBytes("utf-8"), qualifier.getBytes("utf-8"), value);
            table.put(put);

        } catch (Exception e) {
            throw e;
        } finally {
            if (table != null) table.close();
            //if (conn != null) conn.close();
        }
    }


    /**
     * insert the Vector<Put> into habse
     * @param tableName
     * @param putList
     * @throws Exception
     */
    public static void insertValue(String tableName,Vector<Put> putList) throws Exception {
        HConnection conn = null;
        HTableInterface table = null;
        try {
            conn = HBaseConnectionFactory.gethConnection();
            table = conn.getTable(tableName.getBytes("utf-8"));
            table.put(putList);
        } catch (Exception e) {
            throw e;
        } finally {
            if (table != null) table.close();
            //if (conn != null) conn.close();
        }
    }

    /**
     * insert the Put into hbase
     * @param tableName
     * @param put
     * @throws Exception
     */
    public static void insertValue(String tableName,Put put) throws Exception {
        HConnection conn = null;
        HTableInterface table = null;
        try {
            conn = HBaseConnectionFactory.gethConnection();
            table = conn.getTable(tableName.getBytes("utf-8"));
            table.put(put);

        } catch (Exception e) {
            throw e;
        } finally {
            if (table != null) table.close();
            //if (conn != null) conn.close();
        }
    }

    /**
     * check if the rowkey exist in the table
     * @param tableName
     * @param rowkey
     * @throws Exception
     */
    public static boolean exist(String tableName, byte[] row) throws Exception {
        HConnection conn = null;
        HTableInterface table = null;
        try {
            conn = HBaseConnectionFactory.gethConnection();
            table = conn.getTable(tableName.getBytes("utf-8"));
            Get get = new Get(row);
            return table.exists(get);
        } catch (Exception e) {
            throw e;
        } finally {
            if (table != null) table.close();
            //if (conn != null) conn.close();
        }
    }
    /**
     * 删除表
     * @throws IOException
     */
    public static void dropTable(String tableName) throws Exception {
        HBaseAdmin admin = null;
        try {
            admin = HBaseConnectionFactory.gethBaseAdmin();
            if (admin.tableExists(tableName.getBytes("utf-8"))) {
                admin.disableTable(tableName.getBytes("utf-8"));
                admin.deleteTable(tableName.getBytes("utf-8"));
            }

        } catch (Exception e) {
            throw e;
        }
    }

    void rename(String oldTableName, String newTableName) throws Exception {
        String snapshotName = oldTableName+"_snap";
        HBaseAdmin admin = null;
        try {
            admin = HBaseConnectionFactory.gethBaseAdmin();
            admin.disableTable(oldTableName);
            admin.snapshot(snapshotName, oldTableName);
            admin.cloneSnapshot(snapshotName, newTableName);
            admin.deleteSnapshot(snapshotName);
            admin.deleteTable(oldTableName);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * get the count of a table
     * @throws Throwable
     * @throws IOException
     * @deprecated need coprocessor registered in hbase
     */
    public static long countOfRowForPrefix(String tableName,byte[] rowPrefix) throws Throwable {

        try {
            Configuration configuration = HBaseConnectionFactory.gethConfiguration();
            AggregationClient aggregationClient = new AggregationClient(configuration);
            Scan scan = new Scan(); //指定扫描列族，唯一值

            Filter filter = new PrefixFilter(rowPrefix);
            scan.setFilter(filter);
            //scan.addFamily("info");
            LongColumnInterpreter ci = new LongColumnInterpreter();
            long rowCount = aggregationClient.rowCount(TableName.valueOf(tableName), ci, scan);
            System.out.println("row count is " + rowCount);
            return rowCount;
        } catch (Exception e) {
            throw e;
        }
    }


}

