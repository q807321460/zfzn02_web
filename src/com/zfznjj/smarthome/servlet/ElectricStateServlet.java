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
			// 这里将来很可能会添加中央空调的处理逻辑，这个反馈状态的长度是可变的，因此需要处理
			if(state.length() == 22) { // 旧电器，编号只有8位
				masterCode = state.substring(1,9);
				electricCode = state.substring(9,17);
				electricState = state.substring(17,19);
				stateInfo = state.substring(19,21);
			} else if (state.length() == 26) { // 新电器，编号有12位
				masterCode = state.substring(1,9);
				electricCode = state.substring(9,21);
				electricState = state.substring(21,23);
				stateInfo = state.substring(23,25);
			} else if (state.length() == 29) { // 新电器，同时又是门锁类型，这里会多传入一个开锁人员的编号
				masterCode = state.substring(1,9);
				electricCode = state.substring(9,21);
				electricState = state.substring(21,23);
				stateInfo = state.substring(23,28);
			} else { // 对于中央空调而言，长度一般是远超过29位的，至少应该是36位
				masterCode = state.substring(1,9);
				electricCode = state.substring(9,21);
				if (!electricCode.startsWith("1100")) 
					return;
				electricState = state.substring(21,23);
				int len = state.length();
				stateInfo = state.substring(23,len-1);
			}
			StringBuffer sb = new StringBuffer(stateInfo);
			while(sb.length() < 10){
				sb.append('*');
			}
			stateInfo = sb.toString();
			// 获取系统的业务逻辑组件，更新状态信息
			SmarthomeService smarthomeService = (SmarthomeService)getCtx().getBean("smarthomeService");
			smarthomeService.updateElectricState(masterCode,electricCode,electricState,stateInfo);
		}
}
