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
		// ���Ի�ȡPOST�����е�����bodyΪһ��MAP��ʽ��JSON�ַ�����keyֵ�����С���
		// id, time, username, customDeveloperCallbackUrl, did, mid, type, cid,
		// password, cname
		BufferedReader br = request.getReader();
		String str, body = "";
		while ((str = br.readLine()) != null) {
			body += str;
		}
		Map map = JsonPluginsUtil.jsonToMap(body);
		String electricCode = String.valueOf(map.get("did")); // ����ͷID
		String type = String.valueOf(map.get("type")); // �������ͣ�0��������� 1����̬���   11���豸���� 12���豸���ߣ�
		String time = String.valueOf(map.get("time")); // ����ʱ�䣬long��ʽ
		// ��ȡϵͳ��ҵ���߼����
		SmarthomeService smarthomeService = (SmarthomeService) getCtx().getBean("smarthomeService");
		smarthomeService.lechageAlarm(electricCode, type, time);
	}
}
