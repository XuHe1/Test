package top.lovelily.oracle;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;

public class SmsClient {
	public static void main(String[] args) throws Exception {
		// 发送短信地址，以下为示例地址，具体地址询问网关获取
		String url_send_sms = "http://112.122.11.247:8860/sendSms";
		// 用户账号，必填
		String cust_code = "000117";
		// 用户密码，必填
		String cust_pwd = "123456";
		// 短信内容，必填
		String content = "happy new year!你好";
		// 接收号码，必填，同时发送给多个号码时,号码之间用英文半角逗号分隔
		String destMobiles = "18955291897,18655209026";
		// 业务标识，选填，由客户自行填写不超过20位的数字
		String uid = "";
		// 长号码，选填
		String sp_code = "";
		// 是否需要状态报告
		String need_report = "yes";
		// 数字签名，签名内容根据 “短信内容+客户密码”进行MD5编码后获得
		String sign = content + cust_pwd;
		// jdk1.8 md5
        MessageDigest MD5 = MessageDigest.getInstance("MD5");
        byte[] bytes = MD5.digest(sign.getBytes("UTF-8"));
		StringBuilder buf = new StringBuilder(bytes.length * 2);
		int i;

		for (i = 0; i < bytes.length; i++) {
			if (((int) bytes[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Long.toString((int) bytes[i] & 0xff, 16));
		}

		sign = buf.toString();
        String json_send_sms = "{\"cust_code\":\"" + cust_code + "\",\"sp_code\":\"" + sp_code + "\",\"content\":\"" + content +
                "\",\"destMobiles\":\"" + destMobiles + "\",\"uid\":\"" + uid + "\",\"need_report\":\"" + need_report + "\",\"sign\":\"" + sign + "\"}";
		URL url = new URL(url_send_sms);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
		httpURLConnection.setRequestMethod("POST");
		httpURLConnection.setDoOutput(true);
		httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		OutputStream out = httpURLConnection.getOutputStream();
		out.write(json_send_sms.getBytes("UTF-8"));
		System.out.println("send msg:\t"+json_send_sms);
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;
		try {
			inputStream = httpURLConnection.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
			reader = new BufferedReader(inputStreamReader);
			while ((tempLine = reader.readLine()) != null) {
				resultBuffer.append(tempLine);
			}
			System.out.println("recv msg:\t"+resultBuffer.toString());
		} catch (Exception e) {
			throw e;
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (inputStreamReader != null) {
				inputStreamReader.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
			httpURLConnection.disconnect();
		}
	}
}