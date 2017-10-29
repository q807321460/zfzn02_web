package com.zfznjj.smarthome.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.zfznjj.smarthome.model.MasterNode;
import com.zfznjj.smarthome.service.SmarthomeService;
import com.zfznjj.smarthome.servlet.base.BaseServlet;

public class ElectricOrderServlet extends BaseServlet {

	public void service(HttpServletRequest request ,
			HttpServletResponse response)
			throws IOException , ServletException
		{
			String masterCode = request.getParameter("masterCode");
			// ��ȡϵͳ��ҵ���߼����
			SmarthomeService smarthomeService = (SmarthomeService)getCtx().getBean("smarthomeService");
			// ��ȡ״״̬��Ϣ
			String result = smarthomeService.ugetSceneOrderByMasterNode(masterCode);
			if(result.equals("none")){
				result = smarthomeService.ugetElectricOrderByMasterNode(masterCode);
			}
			
			response.setContentType("text/html; charset=utf-8");
			request.getSession(true).setAttribute("result" , result);
			try
			{
				// ����֤��userId��װ��JSONObject
				JSONObject jsonObj = new JSONObject()
					.put("result" , result);
				// �����Ӧ
				response.getWriter().println(jsonObj.toString());
			}
			catch (JSONException ex)
			{
				ex.printStackTrace();
			}
		}
	
	public String getRemortIP(HttpServletRequest request) {
		  if (request.getHeader("x-forwarded-for") == null) {
		   return request.getRemoteAddr();
		  }
		  return request.getHeader("x-forwarded-for");
		}
}
