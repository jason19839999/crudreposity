package datacenter.crudreposity.filters;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by qingtao.kong on 2017/9/7.
 * Filter： 通过api调用接口的body JSON串中的version 版本，判断调用的api版本。
 * CacheCommandLine VERSIONS 存储着每个版本的最大值，
 * 比如：1.6.6 以下版本，会调用存在/1.6.6/....这样的API
 * 默认为最新的API
 */
public class VersionFilter extends OncePerRequestFilter {
    private static Logger logger = LoggerFactory.getLogger(VersionFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        //获取接口的requestBody消息体，说白了就是实体对象
        ServingRequestWrapper requestWrapper = new ServingRequestWrapper(httpServletRequest);
        String body = requestWrapper.getBody();
        String uri = requestWrapper.getRequestURI();
        JSONObject jsonObject = JSON.parseObject(body);

        try {
            String version = jsonObject.getJSONObject("header").getJSONObject("ua").getString("app_version");
            String targetUri = uri;


//            for (String v : CacheCommandLineRunner.VERSIONS) {
//                if (IsDel(v,version)) {
//                    targetUri = "/" + v + uri;
//                    break;
//                }
//            }
//
//            if (CacheCommandLineRunner.URIS.contains(targetUri)) {
//                logger.info(targetUri);
//                String finalTargetUri = targetUri;
//                requestWrapper.getRequestDispatcher(targetUri).forward(requestWrapper, httpServletResponse);
//            } else {
//                filterChain.doFilter(requestWrapper, httpServletResponse);
//            }
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            filterChain.doFilter(requestWrapper, httpServletResponse);
        }
    }

    private Boolean IsDel(String v, String version) {
        String[] vs = v.split("\\.");
        String[] versions = version.split("\\.");
        if (Integer.parseInt(vs[0]) > Integer.parseInt(versions[0])) {
            return true;
        } else {
            if (Integer.parseInt(vs[0]) == Integer.parseInt(versions[0])) {
                if (Integer.parseInt(vs[1]) > Integer.parseInt(versions[1])) {
                    return true;
                } else {
                    if (Integer.parseInt(vs[1]) == Integer.parseInt(versions[1])) {
                        if (Integer.parseInt(vs[2]) > Integer.parseInt(versions[2])){
                            return true;
                        }
                        else{
                            return false;
                        }
                    } else {
                       return false;
                    }
                }
            } else {
                return false;
            }
        }


    }


}


