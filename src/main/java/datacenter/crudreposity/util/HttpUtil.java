package datacenter.crudreposity.util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.InflaterInputStream;

/**
 * @author 605162215@qq.com
 *
 * @date 2018年1月31日 下午4:32:40<br/>
 */
public class HttpUtil {
	public static void main(String[] args) throws Exception{
		
		for(int i=0;i<10;i++) {
			Thread t = new Thread(new Runnable() {
				public void run() {
					try {
						for(int i=0;i<10;i++) {
							URL url = new URL("http://192.168.220.130/index.html");
							HttpURLConnection conn = (HttpURLConnection)url.openConnection();
							InputStream in = conn.getInputStream();
							ByteArrayOutputStream bout  = new ByteArrayOutputStream();
							byte[] buff = new byte[1024];
							int len = 0;
							while((len = in.read(buff)) >= 0) {
								bout.write(buff, 0, len);
							}
							in.close();
							bout.close();
							byte[] response = bout.toByteArray();
							System.out.println(new String(response, "UTF-8"));
							Thread.sleep(3000);
						}
					}catch(Exception e) {
						
					}
				}
			});
			t.start();
		}
	}


	//处理post请求参数content_type为application/x-gzip的情况
//	public static  NewsListRequest getReqestData(HttpServletRequest request) throws IOException, ClassNotFoundException {
//		ObjectMapper objMapper = getJacksonMapper();
//		NewsListRequest newsListRequest = null;
//		String errMsg = null;
//		InputStream is = null;
//		OutputStream out = null;
//		boolean clientSupportCompress = false;
//		String clientIp = "";
//		try {
//			is = new InflaterInputStream(request.getInputStream());
//			clientSupportCompress = true;
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		try {
//			JavaType beanType = objMapper.getTypeFactory().constructParametricType(NewsListRequest.class,
//					RequestHeaderWithuuid.class,
//					NewsListRequestData.class,
//					RequestHeader.class);
//			newsListRequest = objMapper.readValue(is, beanType);
//		}catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		return  newsListRequest;
//	}
}
