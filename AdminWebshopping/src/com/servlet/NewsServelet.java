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
import com.sun.org.apache.bcel.internal.generic.NEW;
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
		try {
			if ("/listNews".equals(servletpath)) {
				listNews(request, response);
			} else if ("/deleteNews".equals(servletpath)) {
				deleteNews(request, response);
			}else if ("/updateNews".equals(servletpath)) {
				updateNews(request, response);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
			

	}

	/**
	 * 列出所有新闻，供管理员修改
	 */
	private void listNews(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
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
		List<News> list = NewsDAO.listNews();// 调用DAO查询数据

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


/**
 * 新增、更新新闻
 * @param request
 * @param response
 * @throws Exception 
 */
	private void updateNews(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		// 实现跨域请求需要在响应中添加如下响应头
		response.addHeader("Access-Control-Allow-Origin", "*");
		// 设置接收和响应字符编码格式，接收或响应中包含汉字时必须有相关声明
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		// 获取浏览器发送的参数
		String NewsID="", SortId ="",title="",News_content="",keywords="",author="",numhit="",ImageURL="";
		try {
			NewsID = request.getParameter("newsId");
			SortId = request.getParameter("sortId");
			title = request.getParameter("title");
			News_content = request.getParameter("news_content");
			keywords = request.getParameter("keywords");
			author = request.getParameter("author");
			numhit = request.getParameter("numhit"); 
			ImageURL = request.getParameter("imageURL");

		} catch (Exception e) {
			// TODO: handle exception
			 e.printStackTrace();
		}
		News ns=new News(Integer.valueOf( NewsID), Integer.valueOf( SortId), Integer.valueOf(numhit), author, title, News_content, keywords, ImageURL);
		boolean flag=false;
		if(ns.getNewsId()==0){
			flag=NewsDAO.addNews(ns);
		}else {
			flag=NewsDAO.updateNews(ns);
		}

		PrintWriter out = response.getWriter();
		out.flush();
		out.write(String.valueOf(flag));
		out.flush();
		out.close();

	}
	private void deleteNews(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		// 实现跨域请求需要在响应中添加如下响应头
		response.addHeader("Access-Control-Allow-Origin", "*");
		// 设置接收和响应字符编码格式，接收或响应中包含汉字时必须有相关声明
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		// 获取浏览器发送的参数
		String newID = request.getParameter("id");
		boolean flag = false;
		try {
			flag = NewsDAO.deleteNews(Integer.valueOf(newID));
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


}
