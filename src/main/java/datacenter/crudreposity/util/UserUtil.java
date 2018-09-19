package datacenter.crudreposity.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.codec.binary.Base64;
import sun.security.provider.MD5;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserUtil {

	//裁剪图片处理
	private String getThumnails(String code){
		try {
			byte [] imageData = null; //获取二进制源文件流binFileService.getBinFile(rowkey).getData();
			if(imageData == null){
				return "";
			}
			ByteArrayInputStream intputStream = new ByteArrayInputStream(imageData);
			Thumbnails.Builder<? extends InputStream> builder = Thumbnails.of(intputStream).size(100, 100);
			BufferedImage bufferedImage = builder.asBufferedImage();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "png", baos);
			byte[] byteArray = baos.toByteArray();
			return Base64.encodeBase64String(byteArray);
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return "";
	}
	
	public static void main(String[] args)throws Exception {

	}
}
