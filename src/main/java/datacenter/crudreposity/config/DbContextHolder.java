package datacenter.crudreposity.config;

/**
 * @描述
 * @创建人 shicong.zhang
 * @创建时间 $date$
 * @修改人和其它信息
 */
public class DbContextHolder {
    public enum DbType {
        MASTER,
        REPLICA1
    }


    private static final ThreadLocal<DbType> contextHolder = new ThreadLocal<DbType>();

    public static void setDbType(DbType dbType) {
        if(dbType == null){
            throw new NullPointerException();
        }
        contextHolder.set(dbType);
    }

    public static DbType getDbType() {
        return (DbType) contextHolder.get();
    }

    public static void clearDbType() {
        contextHolder.remove();
    }
}
