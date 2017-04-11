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
import com.mysql.jdbc.util.Base64Decoder.IntWrapper;
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
		if ("/listGoods".equals(servletpath)) {
			// System.out.println("line:34,"+servletpath);
			// listgoods(request,response);
			try {
				listgoods(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if ("/updateGoods".equals(servletpath)) {
			updateGoods(request, response);
			System.out.println("line44:updateGoods");
		} else if ("/deleteGoods".equals(servletpath)) {
			deleteGoods(request, response);
			System.out.println("line44:deleteGoods");
		}
	}

	

	private void deleteGoods(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String ID = request.getParameter("id");
		boolean flag=GoodsDAO.deleteGoods(Integer.valueOf(ID));
		PrintWriter out = response.getWriter();
		out.write(String.valueOf(flag));
		
	}

	private void updateGoods(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String goodID = request.getParameter("goodID");
		String categoryID = request.getParameter("categoryID");
		String number = request.getParameter("number");
		String name = request.getParameter("name");
		String introduct = request.getParameter("introduct");
		String material = request.getParameter("material");
		String color = request.getParameter("color");
		String priceCommon = request.getParameter("priceCommon");
		Goods good=new Goods(Integer.valueOf(goodID), Integer.valueOf(categoryID), number, name, introduct, material, color,Double.valueOf(priceCommon) );
		boolean success=false;
		switch (Integer.valueOf(goodID)) {
		case 0:
			System.out.println("新建商品记录");
			success=GoodsDAO.addGoods(good);
			break;

		default:
			System.out.println("更新商品记录");
			success=GoodsDAO.updateGoods(good);
			break;
		}
		PrintWriter out = response.getWriter();
		out.write(String.valueOf(success));
		
		
	}

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
			Goods good = GoodsDAO.queryGoods(Integer.valueOf(ID));
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
	 * 列出所有商品，用于管理
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


		List<Goods> list = GoodsDAO.queryGoods();

		JSONArray jsonArray = JSONArray.fromObject(list);

		// 实现跨域请求需要在响应中添加如下响应头
		response.addHeader("Access-Control-Allow-Origin", "*");

		// JSON汉字解析乱码。加下行
		response.setContentType("text/html; charset=utf-8");

		PrintWriter out = response.getWriter();
		out.write(jsonArray.toString());
		System.out.println("listGoods");
	}

}
