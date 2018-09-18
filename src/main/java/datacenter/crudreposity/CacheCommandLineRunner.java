package datacenter.crudreposity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

/**
 * Created by qingtao.kong on 2017/9/7.
 */
@Component
public class CacheCommandLineRunner implements CommandLineRunner {

    private static Logger logger = LoggerFactory.getLogger(CacheCommandLineRunner.class);
    public static List<String> URIS;
    public static List<String> VERSIONS;

    @Override
    public void run(String... strings) throws Exception {
        URIS = getUris();
        VERSIONS = getVerions();
    }

    /**
     * 通过反射获取所有的request mapping uri
     * @return uri的集合
     */
    private List<String> getUris() {
        Field field;
        List<String> targetUris = new ArrayList<>();

        try {
            // 获取所有已经加载类
            field = ClassLoader.class.getDeclaredField("classes");
            field.setAccessible(true);
            Vector<Class> classes = (Vector<Class>) field.get(this.getClass().getClassLoader());
            List<Class> cl = new ArrayList<>(classes);

            // 获取RequestMapping值
            for (Class c : cl) {
                if (c.getAnnotation(RestController.class) != null || c.getAnnotation(Controller.class) != null) {
                    logger.info("scan: " + c.getName());
                    Method[] ms = c.getDeclaredMethods();
                    for (Method m : ms) {
                        String[] bath2;
                        if (m.getAnnotation(RequestMapping.class) != null) {
                            RequestMapping rm = m.getAnnotation(RequestMapping.class);
                            bath2 = rm.path().length > 0 ? rm.path() : rm.value();
                            bath2[0] = bath2[0].startsWith("/") ? bath2[0] : "/" + bath2[0];
                        } else if (m.getAnnotation(GetMapping.class) != null) {
                            GetMapping rm = m.getAnnotation(GetMapping.class);
                            bath2 = rm.path().length > 0 ? rm.path() : rm.value();
                            bath2[0] = bath2[0].startsWith("/") ? bath2[0] : "/" + bath2[0];
                        } else if (m.getAnnotation(PostMapping.class) != null) {
                            PostMapping rm = m.getAnnotation(PostMapping.class);
                            bath2 = rm.path().length > 0 ? rm.path() : rm.value();
                            bath2[0] = bath2[0].startsWith("/") ? bath2[0] : "/" + bath2[0];
                        } else if (m.getAnnotation(PutMapping.class) != null) {
                            PutMapping rm = m.getAnnotation(PutMapping.class);
                            bath2 = rm.path().length > 0 ? rm.path() : rm.value();
                            bath2[0] = bath2[0].startsWith("/") ? bath2[0] : "/" + bath2[0];
                        } else if (m.getAnnotation(DeleteMapping.class) != null) {
                            DeleteMapping rm = m.getAnnotation(DeleteMapping.class);
                            bath2 = rm.path().length > 0 ? rm.path() : rm.value();
                            bath2[0] = bath2[0].startsWith("/") ? bath2[0] : "/" + bath2[0];
                        } else if (m.getAnnotation(PatchMapping.class) != null) {
                            PatchMapping rm = m.getAnnotation(PatchMapping.class);
                            bath2 = rm.path().length > 0 ? rm.path() : rm.value();
                            bath2[0] = bath2[0].startsWith("/") ? bath2[0] : "/" + bath2[0];
                        } else
                            continue;
                        if (bath2.length == 0) {
                            continue;
                        }
                        // 组装RequestMapping
                        String uri =  bath2[0];
                        logger.info(uri);
                        targetUris.add(uri);
                    }
                }
            }
        }
        catch (Exception ex) {
            logger.info(ex.getMessage());
        }
        finally {
            logger.info("共检测出uri个数: " + targetUris.size());
        }
        return targetUris;
    }

    /**
     * 获取api版本
     * @return
     */
    private List<String> getVerions(){
        List<String> versions = Arrays.asList("1.6.60","1.7.60");
        logger.info("API 版本并存数: "  + versions.size());
        return versions;
    }
}
