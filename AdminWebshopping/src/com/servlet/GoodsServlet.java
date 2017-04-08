package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.dao.GoodsDAO;
import com.model.Goods;
/**
 * 用于商品列表，商品详情
 * @author 郭梦男
 *
 */
public class GoodsServlet extends HttpServlet {
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
		// System.out.println(servletpath);
		// 分析确定处理此次请求的文件
		if ("/listgoods".equals(servletpath)) {
			// System.out.println("line:34,"+servletpath);
			// listgoods(request,response);
			try {
				listgoods(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if ("/viewgoods".equals(servletpath)) {
			viewgoods(request, response);
			System.out.println("line44:viewGoods");
		}

		// if("/listALLgoods".equals(servletpath))

		// listbrowsegoods(request,response);
		// else if("/clearbrowse".equals(servletpath))
		// clearbrowse(request,response);
	}

	// protected void listgoods(HttpServletRequest request, HttpServletResponse
	// response)throws ServletException, IOException {
	// try {
	// Goods goods=new Goods();
	// GoodsDAO goodsDao=new GoodsDAO();
	// SuperDao s1=new SuperDao();
	// String strcurrentP=request.getParameter("currentP");
	// String strcurrentG=request.getParameter("currentG");
	// String goWhich="listgoods";
	//
	// System.out.println("flag11:listALLgoods");
	//
	// List<?> goodslist=goodsDao.getAllGoods(strcurrentP,strcurrentG,goWhich);
	//
	//
	// //不采用返回值的方式返回查询结果
	// //下面两句设置request.setAttribute，在jsp文件中可以读取，实现了信息传递
	// request.setAttribute("goodslist",goodslist);
	// //分页的list，每一页是商品的list
	// request.setAttribute("pageBar",s1.getDaoPage());
	// //分页信息，包含页数，每页组数
	// } catch (SQLException e) {
	// System.out.println("in goodsservelt");
	// e.printStackTrace();
	// }
	// RequestDispatcher rd=request.getRequestDispatcher("/listGoods.jsp");
	// rd.forward(request,response);
	// }

	protected void viewgoods(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// 查看商品详情(single)
		// System.out.println("goodsservelet,line87,viewgoods");
		// Integer
		// goodId=StringHandler.strToint(request.getParameter("goodID"));
		String ID = request.getParameter("GoodsID");
		System.out.println(ID);
		// request.getRequestDispatcher("goods.html").forward(request,
		// response);
		if (ID != null) {
			GoodsDAO goodsDao = new GoodsDAO();
			Goods good = new GoodsDAO().queryGoods(Integer.valueOf(ID));
			// 实现跨域请求需要在响应中添加如下响应头
			response.addHeader("Access-Control-Allow-Origin", "*");
			// JSON汉字解析乱码。加下行
			response.setContentType("text/html; charset=utf-8");
			JSONObject jsonObject = JSONObject.fromObject(good);
			PrintWriter out = response.getWriter();
			// JSONArray jsonArray= JSONArray.fromObject(good);
			out.write("[" + jsonObject.toString() + "]");
			System.out.println("line103,viewGoods:" + jsonObject.toString());
			// }
		}

	}

	/**
	 * 打开商城首页自动加载所有分类，第一页（默认） 返回所有商品
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException
	 */
	// protected void listgoods(HttpServletRequest request, HttpServletResponse
	// response)throws ServletException, IOException, SQLException {
	// Goods goods=new Goods();
	// GoodsDAO goodsDao=new GoodsDAO();
	// // //获取所有商品
	// JSONObject jsonObject = JSONObject.fromObject(goods);
	// // System.out.println(jsonObject);
	// List<Goods> list=goodsDao.queryGoods(0,1000000);
	// JSONArray jsonArray= JSONArray.fromObject(list);
	// //JSON汉字解析乱码。加下行
	// response.setContentType("text/html; charset=utf-8");
	// PrintWriter out = response.getWriter();
	// out.write(jsonArray.toString());
	// System.out.println("out.write");
	//
	//
	//
	//
	// // String strcurrentP=request.getParameter("currentP");
	// // String strcurrentG=request.getParameter("currentG");
	//
	//
	//
	//
	//
	// }
	/**
	 * 查询返回指定页
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException
	 */
	protected void listgoods(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {

		// //设置接收和响应字符编码格式，接收或响应中包含汉字时必须有相关声明
		// request.setCharacterEncoding("text/html; charset=utf-8");
		// response.setContentType("text/html; charset=utf-8");

		Goods goods = new Goods();
		GoodsDAO goodsDao = new GoodsDAO();

		String categoryID = request.getParameter("categoryID");
		String page = request.getParameter("page");
		String orderBY = request.getParameter("orderBY");
		String order = request.getParameter("order");
		String flag = request.getParameter("num");//-1 代表limit返回所有记录
		int num = Integer.valueOf(flag);
		num=12;

		// categoryID="1";
		// page="1";
		// orderBY="1";
		// order="1";

		// System.out.println("line157"+categoryID);
		// System.out.println(page);
		// System.out.println(orderBY);
		// System.out.println(order);

		List<Goods> list = goodsDao.queryGoods2(categoryID, Integer.valueOf(page),
				Integer.valueOf(orderBY), Integer.valueOf(order), num);

		// JSONObject jsonObject = JSONObject.fromObject(goods);
		// System.out.println(jsonObject);

		// JSONArray jsonArray= JSONArray.fromObject(goodslist);
		JSONArray jsonArray = JSONArray.fromObject(list);

		// 实现跨域请求需要在响应中添加如下响应头
		response.addHeader("Access-Control-Allow-Origin", "*");

		// JSON汉字解析乱码。加下行
		response.setContentType("text/html; charset=utf-8");

		PrintWriter out = response.getWriter();
		out.write(jsonArray.toString());
		System.out.println(jsonArray.toString());
	}

}
