package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.dao.OrderDAO;
import com.dao.OrderitemDAO;
import com.dao.ShoppingCarDAO;
import com.model.Order;
import com.model.Orderitem;
import com.model.ShoppingCar;
/**
 * 处理购物车，订单的相关请求（不包含adress，Invoice表相关操作）
 */
public class ShoppingServelet extends HttpServlet {


	/**
	 * 
	 */
	private static final long serialVersionUID = -5279797311079909557L;

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
			if ("/addTOshoppingCar".equals(servletpath)) {
				System.out.println("line43:addTOshoppingCar");
				addTOshoppingCar(request, response);
			} else if ("/viewShoppingCar".equals(servletpath)) {
				System.out.println("line46:viewShoppingCar");
				viewShoppingCar(request, response);
			} else if ("/modifyShoppingCar".equals(servletpath)) {
				System.out.println("line50:modifyShoppingCar");
				modifyShoppingCar(request, response);
			} else if ("/deleteShoppingCar".equals(servletpath)) {
				System.out.println("line53:deleteShoppingCar");
				deleteShoppingCar(request, response);
			} else if ("/addOrder".equals(servletpath)) {
				System.out.println("line56:addOrder");
				addOrder(request, response);
			} else if ("/cancelOrder".equals(servletpath)) {
				System.out.println("line59:cancelOrder");
				cancelOrder(request, response);
			} else if ("/viewOrder".equals(servletpath)) {
				System.out.println("line62:viewOrder");
				viewOrder(request, response);
			} else if ("/getOrderItem".equals(servletpath)) {
				System.out.println("line65:getOrderItem");
				getOrderItem(request, response);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 添加商品到购物车
	 */
	protected void addTOshoppingCar(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		// 实现跨域请求需要在响应中添加如下响应头
		response.addHeader("Access-Control-Allow-Origin", "*");
		// 设置接收和响应字符编码格式，接收或响应中包含汉字时必须有相关声明
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		// String userName=request.getParameter("userName");
		// String name = new String(userName.getBytes("iso8859-1"),"utf-8");
		// int userID;
		HttpSession session = request.getSession();// 没有Session就新建一个
		int userID = (Integer) session.getAttribute("userID");
		// System.out.println(session.getId());
		// try {

		// } catch (Exception e) {
		// // TODO: handle exception
		// System.out.println("ID = null");
		// }
		//
		// System.out.println("line94:in shoppingseverlet,get userID from session:"+userID);

		// String userPW=request.getParameter("password");
		String goodID = request.getParameter("goodID");
		// String userID=request.getParameter("userID");
		String amount = request.getParameter("amount");
		String color = request.getParameter("color");
		color = new String(color.getBytes("iso8859-1"), "utf-8");
		System.out.println(color);
		String material = request.getParameter("material");
		material = new String(material.getBytes("iso8859-1"), "utf-8");
		// System.out.println(material);
		// String state=request.getParameter("state");

		ShoppingCar temp = new ShoppingCar();
		temp.setGoodID(Integer.valueOf(goodID));
		temp.setUserID(Integer.valueOf(userID));
		temp.setAmount(Integer.valueOf(amount));
		temp.setColor(color);
		temp.setMaterial(material);
		// temp.setState(state);
		temp.setAddedTime(new Date());

		ShoppingCarDAO shoppingCar = new ShoppingCarDAO();
		boolean back = shoppingCar.addGood(temp);
		PrintWriter out = response.getWriter();
		out.write("{'" + String.valueOf(back) + "'}");
		out.flush();
		out.close();

	}

	/**
	 * 查看用户购物车
	 */
	protected void viewShoppingCar(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {

		String iDString = null;
		try {
			// 实现跨域请求需要在响应中添加如下响应头
			response.addHeader("Access-Control-Allow-Origin", "*");
			// 设置接收和响应字符编码格式，接收或响应中包含汉字时必须有相关声明
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=utf-8");
			iDString = null;
			HttpSession session = request.getSession();
			iDString = String.valueOf(session.getAttribute("userID"));
			// //**********************************
			// String nameString=session.getAttribute("userName").toString();
			// System.out.println("140"+nameString);

			ShoppingCarDAO shoppingCar = new ShoppingCarDAO();
			List<ShoppingCar> list = shoppingCar.querryShoppingCar(Integer
					.valueOf(iDString));
			JSONArray jsonArray = JSONArray.fromObject(list);

			PrintWriter out = response.getWriter();
			out.write(jsonArray.toString());
			System.out.println(jsonArray.toString());

			out.flush();
			out.close();
			return;

		} catch (Exception e) {
			// TODO: handle exception
			// 未登录，转至登录页面
			// request.getRequestDispatcher("LogIn.html").forward(request,
			// response);
			response.sendRedirect("LogIn.html");
			// response.setHeader("control","no-cache");
			// response.setHeader("location", "LogIn.html");
			// response.setStatus(302);
			return;
		}

	}

	/**
	 * 修改购物车
	 */
	protected void modifyShoppingCar(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		// 实现跨域请求需要在响应中添加如下响应头
		response.addHeader("Access-Control-Allow-Origin", "*");
		// 设置接收和响应字符编码格式，接收或响应中包含汉字时必须有相关声明
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		// String userName=request.getParameter("userName");
		// String name = new String(userName.getBytes("iso8859-1"),"utf-8");

		String carID = request.getParameter("shoppingCarID");
		String amount = request.getParameter("amount");

		ShoppingCarDAO shoppingCar = new ShoppingCarDAO();

		boolean accsee = shoppingCar.modifyShoppingCar(Integer.valueOf(carID),
				Integer.valueOf(amount));
		// JSONObject jsonObject = JSONObject.fromObject(shoppingCar);
		// JSONArray jsonArray= JSONArray.fromObject(list);

		PrintWriter out = response.getWriter();
		out.write(String.valueOf(accsee));
		out.flush();
		out.close();

	}

	/**
	 * 删除指定ID的购物车条目
	 * 
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */

	private void deleteShoppingCar(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		// TODO Auto-generated method stub
		// 实现跨域请求需要在响应中添加如下响应头
		response.addHeader("Access-Control-Allow-Origin", "*");
		// 设置接收和响应字符编码格式，接收或响应中包含汉字时必须有相关声明
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		// String userName=request.getParameter("userName");
		// String name = new String(userName.getBytes("iso8859-1"),"utf-8");

		String carID = request.getParameter("shoppingCarID");
		ShoppingCarDAO shoppingCar = new ShoppingCarDAO();
		System.out.println("删除购物车条目：" + carID);
		boolean flag = shoppingCar.deleteCar(Integer.valueOf(carID));

		PrintWriter out = response.getWriter();
		out.write(String.valueOf(flag));
		out.flush();
		out.close();

	}

	/**
	 * 新建订单 参数（区分大小写）：goodsID，amount，goodsDtail，deliver_address，pay_method，
	 * total_price，InvoiceID
	 */
	protected void addOrder(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		// 实现跨域请求需要在响应中添加如下响应头
		response.addHeader("Access-Control-Allow-Origin", "*");
		// 设置接收和响应字符编码格式，接收或响应中包含汉字时必须有相关声明
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		HttpSession session = request.getSession();// 没有Session就新建一个
		int userID = (Integer) session.getAttribute("userID");

		// 获取浏览器传来的信息
		// goodsDtail time deliver_address pay_method amount total_price
		// InvoiceID goodsID
		// String goodsDtail=request.getParameter("goodsDtail");
		// String goodID=request.getParameter("goodsID");
		String deliver_address = request.getParameter("deliver_address");
		// String amount=request.getParameter("amount");
		String pay_method = request.getParameter("pay_method");
		String total_price = request.getParameter("total_price");
		String InvoiceID = request.getParameter("InvoiceID");
		String remark = request.getParameter("text");// 订单备注
		// String time=request.getParameter("time");
		// 子表数据，接收JSON对象数组，需要转换为list<OrderItem>类型
		List<Orderitem> list = new ArrayList<Orderitem>();

		String orderItems = request.getParameter("orderItems");
		orderItems = new String(orderItems.getBytes("iso8859-1"), "utf-8");
		// System.out.println("line270,orderItems:"+orderItems);
		JSONArray JsonAray = JSONArray.fromObject(orderItems);
		if (JsonAray != null && !JsonAray.isEmpty()) {
			Iterator<Orderitem> it = JsonAray.iterator();
			while (it.hasNext()) {
				JSONObject JSONObj = JSONObject.fromObject(it.next());
				Orderitem orderitem = (Orderitem) JSONObject.toBean(JSONObj,
						Orderitem.class);
				// System.out.println(good.getImage());
				list.add(orderitem);
			}
		}

		// Integer.valueOf(arg0)
		// 将信息封装到modle层定义的Order类的对象中
		try {
			Order temp = new Order();
			temp.setUserID(Integer.valueOf(userID));
			temp.setDeliver_address(Integer.valueOf(deliver_address));
			temp.setInvoiceID(Integer.valueOf(InvoiceID));
			// temp.setInvoiceID(0);
			temp.setPay_method(pay_method);
			temp.setTotal_price(Double.valueOf(total_price));
			temp.setRemark(remark);
			// (Integer.valueOf(userID),Integer.valueOf(InvoiceID),Integer.valueOf(deliver_address),pay_method,Double.valueOf(total_price));

			boolean back = OrderDAO.newOrder(temp, list);
			// System.out.println("line295,order主表条目添加："+back);
			PrintWriter out = response.getWriter();
			out.write(String.valueOf(back));
			out.flush();
			out.close();

		} catch (Exception e) {
			// TODO: handle exception
			// e.getMessage();
			PrintWriter out = response.getWriter();
			out.write(e.getMessage());
			out.flush();
			out.close();

		}

		// temp.setUserID(Integer.valueOf(userID));
		// temp.setUserID(Integer.valueOf(userID1));
		// temp.setAmount(Integer.valueOf(amount));
		// temp.setColor(color);
		// temp.setMaterial(material);
		// // temp.setState(state);
		// temp.setAddedTime(new Date());

		// 调用DAO层组件操作数据库，完成数据读写
		// OrderDAO orderDAO=new OrderDAO();
		// boolean back=OrderDAO.newOrder(temp);
		// PrintWriter out = response.getWriter();
		// out.write(String.valueOf(back));
		// out.flush();
		// out.close();

	}

	/**
	 * 取消订单
	 */
	protected void cancelOrder(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		// 实现跨域请求需要在响应中添加如下响应头
		response.addHeader("Access-Control-Allow-Origin", "*");
		// 设置接收和响应字符编码格式，接收或响应中包含汉字时必须有相关声明
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		// String userName=request.getParameter("userName");
		// String name = new String(userName.getBytes("iso8859-1"),"utf-8");

		String OrderID = request.getParameter("OrderID");

		// OrderDAO orderDAO=new OrderDAO();

		boolean accsee = OrderDAO.modifyOrders(Integer.valueOf(OrderID));
		// JSONObject jsonObject = JSONObject.fromObject(shoppingCar);
		// JSONArray jsonArray= JSONArray.fromObject(list);

		PrintWriter out = response.getWriter();
		out.write(String.valueOf(accsee));
		out.flush();
		out.close();

	}

	/**
	 * 查看用户订单 仅含主表信息，子表信息由getOrderItem（）获取 无参数，从session获取USerID
	 */
	protected void viewOrder(HttpServletRequest request,
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

		// String userID=request.getParameter("userID");

		Order order = new Order();

		List<Order> list = OrderDAO.getOrders(userID);
		JSONObject jsonObject = JSONObject.fromObject(order);
		JSONArray jsonArray = JSONArray.fromObject(list);

		PrintWriter out = response.getWriter();
		out.write(jsonArray.toString());
		out.flush();
		out.close();

	}

	/**
	 * 查看用户订单 获取子表信息 参数：OrderID
	 */
	protected void getOrderItem(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		// 实现跨域请求需要在响应中添加如下响应头
		response.addHeader("Access-Control-Allow-Origin", "*");
		// 设置接收和响应字符编码格式，接收或响应中包含汉字时必须有相关声明
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		// String userName=request.getParameter("userName");
		// String name = new String(userName.getBytes("iso8859-1"),"utf-8");

		String OrderID = request.getParameter("OrderID");

		List<Orderitem> list = OrderitemDAO
				.getOrders(Integer.parseInt(OrderID));
		JSONObject jsonObject = JSONObject.fromObject(new Orderitem());
		JSONArray jsonArray = JSONArray.fromObject(list);

		PrintWriter out = response.getWriter();
		out.write(jsonArray.toString());
		out.flush();
		out.close();

	}

}
