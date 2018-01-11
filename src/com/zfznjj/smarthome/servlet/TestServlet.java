package com.zfznjj.smarthome.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.zfznjj.smarthome.service.SmarthomeService;
import com.zfznjj.smarthome.servlet.base.BaseServlet;
import com.zfznjj.smarthome.util.JsonPluginsUtil;
import com.zfznjj.smarthome.util.SmartHomeUtil;

public class TestServlet extends BaseServlet {
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// 可以获取POST推送中的数据body为一个MAP格式的JSON字符串，key值包含有——
		// id, time, username, customDeveloperCallbackUrl, did, mid, type, cid,
		// password, cname
		BufferedReader br = request.getReader();
		String str, body = "";
		while ((str = br.readLine()) != null) {
			body += str;
		}
		Map map = JsonPluginsUtil.jsonToMap(body);
		String electricCode = String.valueOf(map.get("did")); // 摄像头ID
		String type = String.valueOf(map.get("type")); // 报警类型（0：人体红外 1：动态监测   11：设备上线 12：设备下线）
		String time = String.valueOf(map.get("time")); // 报警时间，long格式
		// 获取系统的业务逻辑组件
		SmarthomeService smarthomeService = (SmarthomeService) getCtx().getBean("smarthomeService");
		smarthomeService.lechageAlarm(electricCode, type, time);
	}
}
