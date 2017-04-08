package com.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

import com.dao.AdminDAO;
import com.dao.UserDAO;
import com.model.User;
import com.sun.xml.internal.bind.v2.runtime.Name;
/**
 * 管理员登录
 * @author 郭梦男
 *
 */
public class LoginServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String servletpath = request.getServletPath();
		System.out.println("line44:" + servletpath);

		// 分析确定处理此次请求的文件
		try {
			if ("/login".equals(servletpath)) {
				System.out.println("line48,login");
				login(request, response);
			} else if ("/getState".equals(servletpath)) {
				System.out.println("line34,getState");
				getState(request, response);
			} else if ("/modifyPW".equals(servletpath)) {
				System.out.println("line37,modifyPW");
				modifyPW(request, response);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	/**
	 * 登录验证
	 */
	protected void login(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		// 实现跨域请求需要在响应中添加如下响应头
		response.addHeader("Access-Control-Allow-Origin", "*");
		// 设置接收和响应字符编码格式，接收或响应中包含汉字时必须有相关声明
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");

		String userName = request.getParameter("userName");
//		userName=new  String(userName.getBytes("iso8859-1"), "utf-8");

		String userPW = request.getParameter("password");
		String name = new String(userName.getBytes("iso8859-1"), "utf-8");

		PrintWriter out = response.getWriter();
		//登录数据库数据验证s
		int ID = AdminDAO.logIn(userName, userPW);
		if (ID == -1) {
			//用户不存在
			out.write(String.valueOf(ID)+"用户名或密码错误，请重新登录");
		} else {
			// 获取当前sesson，将userID追加进去
			HttpSession session = request.getSession();// 没有Session就新建一个
			session.setAttribute("AdminID", ID);
			session.setAttribute("AdminName", userName);
			System.out.println("登陆成功,管理员ID："+ID);
			out.write(String.valueOf(ID));
			response.sendRedirect("main.jsp");
		}
			// System.out.println(new Date()+"\t"+ID);
			// out.write("test");
			// (类型转换)session.getAttribute("userID");//返回值为object类型，需进行类型转换

//			 request.getRequestDispatcher("MainTest2.html").forward(request,
//			 response);
			// request.getRequestDispatcher("/viewShoppingCar").forward(request,
			// response);

			// Cookie c1 = new Cookie("sessionID", session.getId());
			// System.out.println(session.getId());
			// c1.setPath("/");
			// c1.setDomain(getServletName());
			// // System.out.println(getServletName());
			// response.addCookie(c1);

			// response.addHeader("P3P","CP=\"NON DSP COR CURa ADMa DEVa TAIa PSAa PSDa IVAa IVDa CONa HISa TELa OTPa OUR UNRa IND UNI COM NAV INT DEM	CNT PRE LOC\"");

			out.flush();
			out.close();

		
	}

	
	/**
	 * 商城首页判断登录状态，若以登录则返回用户名，若未登录则返回“NULL”
	 */
	protected void getState(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		String nameString = null;
		// 实现跨域请求需要在响应中添加如下响应头
		response.addHeader("Access-Control-Allow-Origin", "*");
		// 设置接收和响应字符编码格式，接收或响应中包含汉字时必须有相关声明
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");

		try {
			HttpSession session = request.getSession();// 没有Session就新建一个

			nameString = (String) session.getAttribute("userName");
//			nameString = new String(nameString.getBytes("iso8859-1"), "utf-8");
			nameString = "[{\"name\":\"" + nameString + "\"}]";
		} catch (Exception e) {
			// TODO: handle exception
			nameString = "null";
		}
		// PrintWriter out=ServletActionContext.getResponse().getWriter();

		// JSONObject jsonObject = JSONObject.fromObject(nameString);
		// JSONArray jsonArray= JSONArray.fromObject(nameString);

		PrintWriter out = response.getWriter();
		// out.write(back.toCharArray());
		out.flush();
		// out.write (String.valueOf(jsonObject));
		out.write(nameString);
		// int a=1;
		// out.write(String.valueOf(a));
		// out.println(back);

		out.flush();
		// response.flushBuffer();
		out.close();
		// System.out.println("lne 149,getSrate:name:"+nameString);
	}

	/**
	 * 修改密码
	 */
	protected void modifyPW(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		// 实现跨域请求需要在响应中添加如下响应头
		response.addHeader("Access-Control-Allow-Origin", "*");
		// 设置接收和响应字符编码格式，接收或响应中包含汉字时必须有相关声明
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		boolean back = false;
		try {
			HttpSession session = request.getSession();// 没有Session就新建一个
			int ID = (Integer) session.getAttribute("userID");
			String oldPW = request.getParameter("oldPW");
			String newPW = request.getParameter("newPW");
			back = UserDAO.modifyPW(Integer.valueOf(ID), oldPW, newPW);
		} catch (Exception e) {
			// TODO: handle exception
			// 未登录
		}

		PrintWriter out = response.getWriter();
		out.write(String.valueOf(back));
		out.flush();
		out.close();
	}

	

}
