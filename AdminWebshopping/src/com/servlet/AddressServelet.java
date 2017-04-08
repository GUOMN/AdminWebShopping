package com.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.dao.AdressDAO;
import com.model.Adress;
/**
 * 处理对adress相关的请求（添加，查询，修改，删除）
 * @author 郭梦男
 */
public class AddressServelet extends HttpServlet {

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
		// 分析确定处理此次请求的文件
		try {
			if ("/addAdress".equals(servletpath)) {
				System.out.println("line26:addAdress");
				addAdress(request, response);
			} else if ("/getAdress".equals(servletpath)) {
				System.out.println("line29:getAdress");
				getAdress(request, response);
			} else if ("/deleteAdress".equals(servletpath)) {
				System.out.println("line33:deleteAdress");
				deleteAdress(request, response);
			} else if ("/modifyAdress".equals(servletpath)) {
				System.out.println("line36:modifyAdress");
				modifyAdress(request, response);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 添加新地址
	 */
	protected void addAdress(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		// 实现跨域请求需要在响应中添加如下响应头
		response.addHeader("Access-Control-Allow-Origin", "*");
		// 设置接收和响应字符编码格式，接收或响应中包含汉字时必须有相关声明
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		// String userName=request.getParameter("userName");
		// String name = new String(userName.getBytes("iso8859-1"),"utf-8");
		HttpSession session = request.getSession();// 没有Session就新建一个
		int userID = (Integer) session.getAttribute("userID");
		// String adressID=request.getParameter("adressID");
		String name0 = request.getParameter("name");
		String name = new String(name0.getBytes("iso8859-1"), "utf-8");
		String tel = request.getParameter("tel");
		String postcode = request.getParameter("postcode");
		String province0 = request.getParameter("province");
		String province = new String(province0.getBytes("iso8859-1"), "utf-8");
		String city0 = request.getParameter("city");
		String city = new String(city0.getBytes("iso8859-1"), "utf-8");
		String district0 = request.getParameter("district");
		String district = new String(district0.getBytes("iso8859-1"), "utf-8");
		String detailAddress0 = request.getParameter("detailAddress");
		String detailAddress = new String(detailAddress0.getBytes("iso8859-1"),
				"utf-8");

		Adress as1 = new Adress(userID, name, tel, postcode, province, city,
				district, detailAddress);
		boolean accsee = AdressDAO.addAdress(as1);
		// JSONObject jsonObject = JSONObject.fromObject(shoppingCar);
		// JSONArray jsonArray= JSONArray.fromObject(list);

		PrintWriter out = response.getWriter();
		out.write(String.valueOf(accsee));
		out.flush();
		out.close();

	}

	/**
	 * 查询用户所有地址
	 */
	protected void getAdress(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		// 实现跨域请求需要在响应中添加如下响应头
		response.addHeader("Access-Control-Allow-Origin", "*");
		// 设置接收和响应字符编码格式，接收或响应中包含汉字时必须有相关声明
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		// String userName=request.getParameter("userName");
		// String name = new String(userName.getBytes("iso8859-1"),"utf-8");
		HttpSession session = request.getSession();// 没有Session就新建一个
		int userID = (Integer) session.getAttribute("userID");

		List<Adress> list = AdressDAO.getAdress(userID);
		JSONArray jsonArray = JSONArray.fromObject(list);

		PrintWriter out = response.getWriter();
		out.write(jsonArray.toString());
		out.flush();
		out.close();

	}

	/**
	 * 删除地址
	 */
	protected void deleteAdress(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		// 实现跨域请求需要在响应中添加如下响应头
		response.addHeader("Access-Control-Allow-Origin", "*");
		// 设置接收和响应字符编码格式，接收或响应中包含汉字时必须有相关声明
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		// String userName=request.getParameter("userName");
		// String name = new String(userName.getBytes("iso8859-1"),"utf-8");
		String adressID = request.getParameter("adressID");

		// OrderDAO orderDAO=new OrderDAO();

		boolean accsee = AdressDAO.deleteAdress(Integer.valueOf(adressID));
		// JSONObject jsonObject = JSONObject.fromObject(shoppingCar);
		// JSONArray jsonArray= JSONArray.fromObject(list);

		PrintWriter out = response.getWriter();
		out.write(String.valueOf(accsee));
		out.flush();
		out.close();

	}

	/**
	 * 修改地址
	 */
	protected void modifyAdress(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		// 实现跨域请求需要在响应中添加如下响应头
		response.addHeader("Access-Control-Allow-Origin", "*");
		// 设置接收和响应字符编码格式，接收或响应中包含汉字时必须有相关声明
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		// String userName=request.getParameter("userName");
		// String name = new String(userName.getBytes("iso8859-1"),"utf-8");
		HttpSession session = request.getSession();// 没有Session就新建一个
		int userID = (Integer) session.getAttribute("userID");
		// String adressID=request.getParameter("adressID");
		String name0 = request.getParameter("name");
		String name = new String(name0.getBytes("iso8859-1"), "utf-8");
		String tel = request.getParameter("tel");
		String postcode = request.getParameter("postcode");
		String province0 = request.getParameter("province");
		String province = new String(province0.getBytes("iso8859-1"), "utf-8");
		String city0 = request.getParameter("city");
		String city = new String(city0.getBytes("iso8859-1"), "utf-8");
		String district0 = request.getParameter("district");
		String district = new String(district0.getBytes("iso8859-1"), "utf-8");
		String detailAddress0 = request.getParameter("detailAddress");
		String detailAddress = new String(detailAddress0.getBytes("iso8859-1"),
				"utf-8");
		String adressID = request.getParameter("adressID");

		Adress as1 = new Adress(userID, name, tel, postcode, province, city,
				district, detailAddress);

		// OrderDAO orderDAO=new OrderDAO();

		AdressDAO adressDAO = new AdressDAO();
		boolean accsee = AdressDAO.deleteAdress(Integer.valueOf(adressID));
		accsee = AdressDAO.addAdress(as1);
		// JSONObject jsonObject = JSONObject.fromObject(shoppingCar);
		// JSONArray jsonArray= JSONArray.fromObject(list);

		PrintWriter out = response.getWriter();
		out.write(String.valueOf(accsee));
		out.flush();
		out.close();

	}

}
