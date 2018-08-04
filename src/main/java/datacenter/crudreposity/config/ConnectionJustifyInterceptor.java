package datacenter.crudreposity.config;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @描述
 * @创建人 shicong.zhang
 * @创建时间 $date$
 * @修改人和其它信息
 */
@Aspect
@Component
public class ConnectionJustifyInterceptor implements Ordered {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionJustifyInterceptor.class);

    @Around(value = "@annotation(connectionJustify)")
    public Object proceed(ProceedingJoinPoint proceedingJoinPoint,ConnectionJustify connectionJustify) throws Throwable {
        try {
            if (connectionJustify.dbType() == DbContextHolder.DbType.MASTER){
                logger.info("set database connection to master");
                DbContextHolder.setDbType(DbContextHolder.DbType.MASTER);
            }
            Object result = proceedingJoinPoint.proceed();
            return result;
        } finally {
            DbContextHolder.clearDbType();
            logger.info("restore database connection");
        }
    }


    @Override
    public int getOrder() {
        return 0;
    }
}
