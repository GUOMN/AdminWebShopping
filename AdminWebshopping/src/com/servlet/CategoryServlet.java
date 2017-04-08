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

import com.dao.CategoryDAO;
import com.dao.GoodsDAO;
import com.model.Category;
/**
 * 商品分类
 * @author 郭梦男
 *
 */
public class CategoryServlet extends HttpServlet {

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
		if ("/listCategorys".equals(servletpath)) {
			// System.out.println("categoryservelet,line35,dopost");
			listCategorys(request, response);
		} else if ("/goodsAmount".equals(servletpath))
			try {
				goodsAmount(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		// else if("/viewsorts".equals(servletpath))
		// viewsorts(request,response);
		// if("/listbrowsegoods".equals(servletpath))
		// // listbrowsegoods(request,response);
		// else if("/clearbrowse".equals(servletpath))
		// clearbrowse(request,response);
	}

	// 获取全部分类
	protected void listCategorys(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// JSON汉字解析乱码。加下行
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");

		CategoryDAO sortsDao = new CategoryDAO();
		Category sort = new Category();
		List<Category> list = sortsDao.queryALL();

		// 实现跨域请求需要在响应中添加如下响应头
		response.addHeader("Access-Control-Allow-Origin", "*");

		// JSONObject jsonObject = JSONObject.fromObject(sort);
		JSONArray jsonArray = JSONArray.fromObject(list);

		PrintWriter out = response.getWriter();
		out.write(jsonArray.toString());
		out.flush();
		out.close();
		// System.out.println("Categoryservelet,line49,listcategorys"+new
		// Date());
	}

	// protected void viewCategorys(HttpServletRequest request,
	// HttpServletResponse response)throws ServletException, IOException {
	// //查看该分类包含的商品
	// Integer sortId=StringHandler.strToint(request.getParameter("sortId"));
	// if(sortId!=null){
	// GoodsDAO goodDao=new GoodsDAO();
	// List<Goods> undersortGoods=goodDao.queryGoods(1,sortId.toString());
	// if(undersortGoods!=null){
	// // seebrowsegoodscookie(request,response,single);
	// request.setAttribute("undersortGoods",undersortGoods);
	// }
	// }
	//
	// RequestDispatcher rd=request.getRequestDispatcher("/viewSorts.jsp");
	// rd.forward(request,response);
	// }
	/**
	 * 返回商品页数
	 */
	protected void goodsAmount(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		// 实现跨域请求需要在响应中添加如下响应头
		response.addHeader("Access-Control-Allow-Origin", "*");
		// 设置接收和响应字符编码格式，接收或响应中包含汉字时必须有相关声明
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");

		GoodsDAO goodsDao = new GoodsDAO();
		String categoryID = request.getParameter("categoryID");
		int number = goodsDao.queryGoodsNumber(categoryID);
		int page = number / 6 + ((number % 6 == 0) ? 0 : 1);
		PrintWriter out = response.getWriter();
		// System.out.println("line113: page="+page);
		// out.write("[{\"page\":\""+String.valueOf(page)+"\"}]");
		out.write(String.valueOf(page));
		// out.write(String.valueOf(page));

	}

}
