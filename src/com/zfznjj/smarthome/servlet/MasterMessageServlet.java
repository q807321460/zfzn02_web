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

public class MasterMessageServlet extends BaseServlet {
		public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
			String masterCode = request.getParameter("masterCode");
			String masterVersion = request.getParameter("masterVersion");
			System.out.println("【主机】" + masterCode + " 版本更新至: " + masterVersion);
			// 获取系统的业务逻辑组件
			SmarthomeService smarthomeService = (SmarthomeService)getCtx().getBean("smarthomeService");
    		smarthomeService.updateMasterVersion(masterCode, masterVersion);
		}
}
