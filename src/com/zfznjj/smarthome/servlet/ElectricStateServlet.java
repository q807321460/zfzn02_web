package com.zfznjj.smarthome.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.zfznjj.smarthome.service.SmarthomeService;
import com.zfznjj.smarthome.servlet.base.BaseServlet;

public class ElectricStateServlet extends BaseServlet{

	public void service(HttpServletRequest request ,
			HttpServletResponse response)
			throws IOException , ServletException
		{
			String state = request.getParameter("electricState");
			if(state.length() == 22) {//�ɵ��������ֻ��8λ
				String masterCode = state.substring(1,9);
				String electricCode = state.substring(9,17);
				String electricState = state.substring(17,19);
				String stateInfo = state.substring(19,21);
				System.out.println(state);
				StringBuffer sb = new StringBuffer(stateInfo);
				while(sb.length() < 10){
					sb.append('*');
				}
				stateInfo = sb.toString();
				// ��ȡϵͳ��ҵ���߼����
				SmarthomeService smarthomeService = (SmarthomeService)getCtx().getBean("smarthomeService");
				// ����״̬��Ϣ
				smarthomeService.updateElectricState(masterCode,electricCode,electricState,stateInfo);
			}else if (state.length() == 26) {//�µ����������12λ
				String masterCode = state.substring(1,9);
				String electricCode = state.substring(9,21);
				String electricState = state.substring(21,23);
				String stateInfo = state.substring(23,25);
				System.out.println(state);
				StringBuffer sb = new StringBuffer(stateInfo);
				while(sb.length() < 10){
					sb.append('*');
				}
				stateInfo = sb.toString();
				// ��ȡϵͳ��ҵ���߼����
				SmarthomeService smarthomeService = (SmarthomeService)getCtx().getBean("smarthomeService");
				// ����״̬��Ϣ
				smarthomeService.updateElectricState(masterCode,electricCode,electricState,stateInfo);
			}
		}
}
