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

import com.dao.NewsDAO;
import com.dao.OrderDAO;
import com.dao.OrderitemDAO;
import com.dao.ShoppingCarDAO;
import com.model.Order;
import com.model.Orderitem;
import com.model.ShoppingCar;
/**
 * 处理购物车，订单的相关请求（不包含adress，Invoice表相关操作）
 */
public class OrderServelet extends HttpServlet {


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
			if ("/updateOrder".equals(servletpath)) {
				updateOrder(request, response);
			} else if ("/ListOrder".equals(servletpath)) {
				ListOrder(request, response);
			} else if ("/deleteOrder".equals(servletpath)) {
				deleteOrder(request, response);
			}else if ("/getOrderItem".equals(servletpath)) {
				getOrderItem(request, response);
			}else if ("/deleteOrderItem".equals(servletpath)) {
				deleteOrderItem(request, response);
			}else if ("/updateOrderItem".equals(servletpath)) {
				updateOrderItem(request, response);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	private void updateOrderItem(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		// 实现跨域请求需要在响应中添加如下响应头
				response.addHeader("Access-Control-Allow-Origin", "*");
				// 设置接收和响应字符编码格式，接收或响应中包含汉字时必须有相关声明
				request.setCharacterEncoding("UTF-8");
				response.setContentType("text/html; charset=utf-8");
				int OrderID = Integer.valueOf( request.getParameter("orderID"));
				int orderitemID = Integer.valueOf( request.getParameter("orderitemID"));
				int goodID = Integer.valueOf( request.getParameter("goodID"));
				int goodsQuantity = Integer.valueOf( request.getParameter("goodsQuantity"));//number
				String detail = request.getParameter("detail");//商品颜色、材料的描述
				String price = request.getParameter("price");
			
				// 将信息封装到modle层定义的Order类的对象中
				try {
					Orderitem odi=new Orderitem(orderitemID, OrderID, goodID, goodsQuantity, detail, Double.valueOf(price));
					boolean back=false;
					
					switch (orderitemID) {
					case 0:
						back = OrderitemDAO.newOrderItems(odi);
						break;

					default:
						back = OrderitemDAO.updateOrderItems(odi);
						break;
					}
					
					
					
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
		
	}

	private void deleteOrderItem(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		// 实现跨域请求需要在响应中添加如下响应头
				response.addHeader("Access-Control-Allow-Origin", "*");
				// 设置接收和响应字符编码格式，接收或响应中包含汉字时必须有相关声明
				request.setCharacterEncoding("UTF-8");
				response.setContentType("text/html; charset=utf-8");
				// 获取浏览器发送的参数
				String ID = request.getParameter("id");
				boolean flag = false;
				try {
					flag =OrderitemDAO.deleteOrderItems(Integer.valueOf(ID));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				PrintWriter out = response.getWriter();
				out.flush();
				out.write(String.valueOf(flag));
				out.flush();
				out.close();

		
	}

	private void deleteOrder(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		// 实现跨域请求需要在响应中添加如下响应头
				response.addHeader("Access-Control-Allow-Origin", "*");
				// 设置接收和响应字符编码格式，接收或响应中包含汉字时必须有相关声明
				request.setCharacterEncoding("UTF-8");
				response.setContentType("text/html; charset=utf-8");
				// 获取浏览器发送的参数
				String ID = request.getParameter("id");
				boolean flag = false;
				try {
					flag = OrderDAO.deleteOrder(Integer.valueOf(ID));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				PrintWriter out = response.getWriter();
				out.flush();
				out.write(String.valueOf(flag));
				out.flush();
				out.close();

		
	}

	/**
	 * 更新，新建订单 参数（区分大小写）：goodsID，amount，goodsDtail，deliver_address，pay_method，
	 * total_price，InvoiceID
	 */
	protected void updateOrder(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		// 实现跨域请求需要在响应中添加如下响应头
		response.addHeader("Access-Control-Allow-Origin", "*");
		// 设置接收和响应字符编码格式，接收或响应中包含汉字时必须有相关声明
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
//		HttpSession session = request.getSession();// 没有Session就新建一个
//		int userID = (Integer) session.getAttribute("userID");

		// 获取浏览器传来的信息
		// goodsDtail time deliver_address pay_method amount total_price
		// InvoiceID goodsID
		// String goodsDtail=request.getParameter("goodsDtail");
		// String goodID=request.getParameter("goodsID");
		int OrderID = Integer.valueOf( request.getParameter("orderID"));
		int userID = Integer.valueOf( request.getParameter("userID"));
		String deliver_address = request.getParameter("deliver_address");
		// String amount=request.getParameter("amount");
		String pay_method = request.getParameter("pay_method");
		String total_price = request.getParameter("total_price");
		String InvoiceID = request.getParameter("invoiceID");
		String remark = request.getParameter("remark");// 订单备注
		String status = request.getParameter("status");
		// String time=request.getParameter("time");
		// 子表数据，接收JSON对象数组，需要转换为list<OrderItem>类型
//		List<Orderitem> list = new ArrayList<Orderitem>();

//		String orderItems = request.getParameter("orderItems");//json数组
//		orderItems = new String(orderItems.getBytes("iso8859-1"), "utf-8");
//		// System.out.println("line270,orderItems:"+orderItems);
//		JSONArray JsonAray = JSONArray.fromObject(orderItems);
//		if (JsonAray != null && !JsonAray.isEmpty()) {
//			Iterator<Orderitem> it = JsonAray.iterator();
//			while (it.hasNext()) {
//				JSONObject JSONObj = JSONObject.fromObject(it.next());
//				Orderitem orderitem = (Orderitem) JSONObject.toBean(JSONObj,
//						Orderitem.class);
//				// System.out.println(good.getImage());
//				list.add(orderitem);
//			}
//		}

		// Integer.valueOf(arg0)
		// 将信息封装到modle层定义的Order类的对象中
		try {
			boolean back=false;
			Order temp = new Order();
			temp.setOrderID(OrderID);
			temp.setUserID(Integer.valueOf(userID));
			temp.setDeliver_address(Integer.valueOf(deliver_address));
			temp.setInvoiceID(Integer.valueOf(InvoiceID));
			// temp.setInvoiceID(0);
			temp.setPay_method(pay_method);
			temp.setTotal_price(Double.valueOf(total_price));
			temp.setRemark(remark);
			temp.setStatus(status);
			switch (OrderID) {
			case 0:
				back = OrderDAO.newOrder(temp);
				break;

			default:
				back = OrderDAO.updateOrders(temp);
				break;
			}
			
			
			
			// System.out.println("line295,order主表条目添加："+back);
			PrintWriter out = response.getWriter();
			out.write(String.valueOf(back));
			out.flush();
			out.close();

		} catch (Exception e) {
			// TODO: handle exception
			// e.getMessage();
			e.printStackTrace();
			PrintWriter out = response.getWriter();
			out.write(e.getMessage());
			out.flush();
			out.close();

		}

	}


	/**
	 * 查看用户订单 仅含主表信息，子表信息由getOrderItem（）获取 无参数
	 */
	protected void ListOrder(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		// 实现跨域请求需要在响应中添加如下响应头
		response.addHeader("Access-Control-Allow-Origin", "*");
		// 设置接收和响应字符编码格式，接收或响应中包含汉字时必须有相关声明
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");

		List<Order> list = OrderDAO.getOrders();
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
//		// 设置接收和响应字符编码格式，接收或响应中包含汉字时必须有相关声明
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		// String userName=request.getParameter("userName");
		// String name = new String(userName.getBytes("iso8859-1"),"utf-8");
		String OrderID =new String();
		
		try {
			 OrderID = request.getParameter("OrderID");
			 if(OrderID.equals("")) OrderID="18";
			 
		} catch (Exception e) {
			// TODO: handle exception
			OrderID="18";
		}
		
//		System.out.println(OrderID);

		try {
			List<Orderitem> list = OrderitemDAO.getOrders(Integer.valueOf(OrderID));
			JSONArray jsonArray = JSONArray.fromObject(list);
			PrintWriter out = response.getWriter();
//			System.out.println(jsonArray.toString());
			
			out.write(jsonArray.toString());
			out.flush();
			out.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		

	}

}
