package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.dao.AdminDAO;
import com.dao.UserDAO;
import com.model.User;
/**
 * 管理员后台接口，查询编辑用户信息，查看已成交订单，编辑推送新闻，管理论坛，编辑商城商品信息
 * 与easyUI结合实现网页端管理
 * @author 郭梦男
 * @data 2017/0/25
 */
public class UserServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String servletpath = request.getServletPath();
		// 分析确定处理此次请求的文件
		try {
			if ("/listUser".equals(servletpath)) {
				System.out.println("Admin:查询用户列表");
				listUser(request, response);
			}else if("/updateUser".equals(servletpath)){
				System.out.println("Admin:更改用户信息");
				//更改包括增加（无ID）、删除用户，改变行信息
				updateUser(request, response);	
			}else if("/userDELETE".equals(servletpath)){
				System.out.println("Admin:删除用户");
				//更改包括增加（无ID）、删除用户，改变行信息
				deleteUser(request, response);	
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void deleteUser(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		// 实现跨域请求需要在响应中添加如下响应头
		response.addHeader("Access-Control-Allow-Origin", "*");
		// 设置接收和响应字符编码格式，接收或响应中包含汉字时必须有相关声明
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
//			int OnDeleteUserID =  (Integer) session.getAttribute("id");
		int OnDeleteUserID= Integer.valueOf( request.getParameter("id"));
		
		boolean back=false;
		back=UserDAO.delete_user(Integer.valueOf(OnDeleteUserID));
		PrintWriter out = response.getWriter();
		out.write(String.valueOf(back));
		out.flush();
		out.close();
		
	}

	private void updateUser(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		// 实现跨域请求需要在响应中添加如下响应头
		response.addHeader("Access-Control-Allow-Origin", "*");
		// 设置接收和响应字符编码格式，接收或响应中包含汉字时必须有相关声明
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		//request.getAttribute()方法返回reques，sessiont范围内存在的对象，而request.getParameter()方法是获取http提交过来的数据。
		String isNewRecord=request.getParameter("isNewRecord");
		int userID=Integer.valueOf( request.getParameter("userID"));
		String name=request.getParameter("name");
		String tel=request.getParameter("tel");
		String email=request.getParameter("email");
		String age=request.getParameter("age");
		String qq=request.getParameter("qq");
		String sex=request.getParameter("sex");
		User u=new User(userID, name, sex, name, "1234", age, tel, email);
		

		boolean back=false;
			
		
			try {
				int ID= u.getUserID();//区分是否为新建用户
				if(ID==0){
					back=UserDAO.newUser(u);
				}else{
					back=UserDAO.modifyUserINFO(u);
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
			
		
		PrintWriter out = response.getWriter();
		out.write(String.valueOf(back));
		out.flush();
		out.close();
		
		
	}

	private void listUser(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		// 实现跨域请求需要在响应中添加如下响应头
				response.addHeader("Access-Control-Allow-Origin", "*");
				// 设置接收和响应字符编码格式，接收或响应中包含汉字时必须有相关声明
				request.setCharacterEncoding("UTF-8");
				response.setContentType("text/html; charset=utf-8");
				List<User> list = UserDAO.listUsers();
				JSONArray jsonArray=JSONArray.fromObject(list);
				PrintWriter out = response.getWriter();
				out.write(jsonArray.toString());
//				System.out.println("json"+jsonArray.toString());
				out.flush();
				out.close();
						
		
	}

}
