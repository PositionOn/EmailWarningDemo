package com.example.emailwarningdemo.Utils;

import lombok.SneakyThrows;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * 请求数据类
 */
public class RequestUtil {

	@SneakyThrows
	public static String getParam(HttpServletRequest request) {
		// 读取参数
		InputStream inputStream;
		StringBuilder sb = new StringBuilder();
		inputStream = request.getInputStream();
		String s;
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
		while ((s = in.readLine()) != null) {
			sb.append(s);
		}
		in.close();
		inputStream.close();
		return sb.toString();
	}

}
