package com.zfznjj.smarthome.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.zfznjj.smarthome.service.SmarthomeService;
import com.zfznjj.smarthome.servlet.base.BaseServlet;

public class ElectricStateServlet extends BaseServlet {

	public void service(HttpServletRequest request ,
			HttpServletResponse response)
			throws IOException , ServletException
		{
			String state = request.getParameter("electricState");
			System.out.println(state);
			String masterCode = "", electricCode = "", electricState = "", stateInfo = "";
			if(state.length() == 22) { //�ɵ��������ֻ��8λ
				masterCode = state.substring(1,9);
				electricCode = state.substring(9,17);
				electricState = state.substring(17,19);
				stateInfo = state.substring(19,21);
			} else if (state.length() == 26) { //�µ����������12λ
				masterCode = state.substring(1,9);
				electricCode = state.substring(9,21);
				electricState = state.substring(21,23);
				stateInfo = state.substring(23,25);
			} else if (state.length() == 29) { // �µ�����ͬʱ�����������ͣ������ഫ��һ��������Ա�ı��
				masterCode = state.substring(1,9);
				electricCode = state.substring(9,21);
				electricState = state.substring(21,23);
				stateInfo = state.substring(23,28);
			}
			StringBuffer sb = new StringBuffer(stateInfo);
			while(sb.length() < 10){
				sb.append('*');
			}
			stateInfo = sb.toString();
			// ��ȡϵͳ��ҵ���߼����������״̬��Ϣ
			SmarthomeService smarthomeService = (SmarthomeService)getCtx().getBean("smarthomeService");
			smarthomeService.updateElectricState(masterCode,electricCode,electricState,stateInfo);
		}
}
