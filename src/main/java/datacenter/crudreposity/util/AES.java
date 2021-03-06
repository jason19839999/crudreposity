package datacenter.crudreposity.util;


import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;


/**
 * 
 * @author weishi
 *
 */
public class AES {


	// 加密
	public static String AESEncrypt(String sSrc, String sKey) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");// 创建AES的Key生产者
		kgen.init(128, new SecureRandom(sKey.getBytes()));// 利用用户密码作为随机数初始化出
		// 128位的key生产者
		//加密没关系，SecureRandom是生成安全随机数序列，password.getBytes()是种子，只要种子相同，序列就一样，所以解密只要有password就行
		SecretKey secretKey = kgen.generateKey();// 根据用户密码，生成一个密钥
		byte[] enCodeFormat = secretKey.getEncoded();// 返回基本编码格式的密钥，如果此密钥不支持编码，则返回
		SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");// 转换为AES专用密钥
		Cipher cipher = Cipher.getInstance("AES");// 创建密码器
		byte[] byteContent = sSrc.getBytes("utf-8");
		cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化为加密模式的密码器
		byte[] result = cipher.doFinal(byteContent);// 加密
		return Base64.getEncoder().encodeToString(result);
	}

	// 解密
	public static String AESDecrypt(String sSrc, String sKey) throws Exception {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");// 创建AES的Key生产者
			kgen.init(128, new SecureRandom(sKey.getBytes()));
			SecretKey secretKey = kgen.generateKey();// 根据用户密码，生成一个密钥
			byte[] enCodeFormat = secretKey.getEncoded();// 返回基本编码格式的密钥
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");// 转换为AES专用密钥
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化为解密模式的密码器
			byte[] result = cipher.doFinal(Base64.getDecoder().decode(sSrc));
			return new String(result); // 明文

		} catch (Exception e) {
		 e.printStackTrace();
		}
		return null;
	}

}
