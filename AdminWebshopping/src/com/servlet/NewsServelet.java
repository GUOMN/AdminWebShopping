package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.dao.NewsDAO;
import com.dao.NewsSortDAO;
import com.model.News;
import com.model.NewsSort;
/**
 * 用于处理新闻相关页面的http请求 2016-10-23
 */
public class NewsServelet extends HttpServlet {


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
		if ("/viewNews".equals(servletpath)) {
			viewNews(request, response);
		} else if ("/viewNewsSort".equals(servletpath)) {
			viewNewsSort(request, response);
		} else if ("/listNewsSort".equals(servletpath)) {
			listNewsSort(request, response);
		}

	}

	/**
	 * 查询新闻分类
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void listNewsSort(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		// 实现跨域请求需要在响应中添加如下响应头
		response.addHeader("Access-Control-Allow-Origin", "*");
		// 设置接收和响应字符编码格式，接收或响应中包含汉字时必须有相关声明
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		/***********************************************/
		// 获取浏览器发送的参数，此处的"xxxxx"代表浏览器发送数据的“name”属性，作为接口参数，大小写敏感，需要明确定义给前端开发人员
		// String xxxx=request.getParameter("xxxxx");（参数可能不止一个）
		// String xxxx=request.getParameter("xxxxx");
		// String xxx=request.getParameter("xxxxx");
		// String xx=request.getParameter("xxxxx");
		//
		// 此处填写业务代码
		NewsSortDAO dao = new NewsSortDAO();
		List<NewsSort> list = dao.queryALL();// 调用DAO查询数据

		// 将List转换成JSON数据，便于浏览器解析
		// JSONObject jsonObject = JSONObject.fromObject(new News());
		JSONArray jsonArray = JSONArray.fromObject(list);

		// JSON汉字解析乱码。加下行
		PrintWriter out = response.getWriter();

		/***********************************************/

		out.flush();
		// 此处用于向浏览器返回数据
		out.write(jsonArray.toString());
		out.flush();
		out.close();

	}

	private void viewNewsSort(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		// 实现跨域请求需要在响应中添加如下响应头
		response.addHeader("Access-Control-Allow-Origin", "*");
		// 设置接收和响应字符编码格式，接收或响应中包含汉字时必须有相关声明
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		// 获取浏览器发送的参数
		String sorId ="";
		try {
			 sorId = request.getParameter("classify");

		} catch (Exception e) {
			// TODO: handle exception
			 sorId="0";
		}
		
		NewsDAO dao = new NewsDAO();
		List<News> list = dao.queryNews1(Integer.valueOf(sorId));
		JSONArray jsonArray = JSONArray.fromObject(list);

		PrintWriter out = response.getWriter();
		out.flush();
		out.write(jsonArray.toString());
		out.flush();
		out.close();

	}

	private void viewNews(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		// 实现跨域请求需要在响应中添加如下响应头
		response.addHeader("Access-Control-Allow-Origin", "*");
		// 设置接收和响应字符编码格式，接收或响应中包含汉字时必须有相关声明
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		// 获取浏览器发送的参数
		String newID = request.getParameter("newID");
		NewsDAO dao = new NewsDAO();
		News resultNews=dao.queryNews(Integer.valueOf(newID) );

		JSONObject JSON_of_news = JSONObject.fromObject(resultNews);
		PrintWriter out = response.getWriter();
		out.flush();
		out.write(JSON_of_news.toString());
		out.flush();
		out.close();

	}


}
