package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import com.dao.BBSreplyDAO;
import com.dao.BBStopicDAO;
import com.dao.NewsSortDAO;
import com.model.BBSreply;
import com.model.BBSsection;
import com.model.BBStopic;
import com.model.NewsSort;
/**
 * bbs论坛陌模块服务器
 * 主要业务：列出分类列表，获取某一分类帖子
 * 获取某一帖子详情ID，新建、删除帖子，根据title、username、userID查询
 * 获取某一帖子评论ID，根据title、username、userID查询
 */
public class BBSServlet extends HttpServlet {

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
			if ("/list_topics".equals(servletpath)) {
				list_topics(request, response);//列出版块内的所有帖子
			}else if ("/list_replies".equals(servletpath)) {
				list_replies(request, response);//列出帖子的所有回复
			}else if ("/delete_topic".equals(servletpath)) {
				delete_topic(request, response);//删除帖子
			}else if ("/delete_reply".equals(servletpath)) {
				delete_reply(request, response);//删除回复
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private void delete_reply(HttpServletRequest request,
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
				 String replyID=request.getParameter("id");
				//
				// 此处填写业务代码
				boolean b=BBSreplyDAO.delete_reply(Integer.valueOf(replyID));// 调用DAO查询数据

				// 将List转换成JSON数据，便于浏览器解析
				// JSONObject jsonObject = JSONObject.fromObject(new News());
//				JSONArray jsonArray = JSONArray.fromObject(list);

				// JSON汉字解析乱码。加下行
				PrintWriter out = response.getWriter();

				/***********************************************/

				out.flush();
				// 此处用于向浏览器返回数据
				out.write(String.valueOf(b));
				out.flush();
				out.close();
	}

	private void delete_topic(HttpServletRequest request,
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

				   String topicID=request.getParameter("id");
				//
				// 此处填写业务代码
				boolean b=BBStopicDAO.delete_topic(Integer.valueOf(topicID));// 调用DAO查询数据

				// 将List转换成JSON数据，便于浏览器解析
				// JSONObject jsonObject = JSONObject.fromObject(new News());
//				JSONArray jsonArray = JSONArray.fromObject(list);

				// JSON汉字解析乱码。加下行
				PrintWriter out = response.getWriter();

				/***********************************************/

				out.flush();
				// 此处用于向浏览器返回数据
				out.write(String.valueOf(b));
				out.flush();
				out.close();
	}

	private void list_replies(HttpServletRequest request,
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
				   String topicID=request.getParameter("topicID");
				//
				// 此处填写业务代码
				List<BBSreply> list = BBSreplyDAO.querry_reply(topicID, 1);// 调用DAO查询数据

				// 将List转换成JSON数据，便于浏览器解析
				// JSONObject jsonObject = JSONObject.fromObject(new News());
				JSONArray jsonArray = JSONArray.fromObject(list);

				// JSON汉字解析乱码。加下行
				PrintWriter out = response.getWriter();

				/***********************************************/

				out.flush();
				// 此处用于向浏览器返回数据
				out.write(String.valueOf(jsonArray));
				out.flush();
				out.close();
	}

	private void querry_topic(HttpServletRequest request,
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
				 String parameter=request.getParameter("parameter");
				 String function_code=request.getParameter("function_code");
				//
				// 此处填写业务代码
				List<BBStopic> list = BBStopicDAO.query_topics(parameter, Integer.valueOf(function_code));// 调用DAO查询数据
//				System.out.println(parameter+"    "+function_code);
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

	private void list_topics(HttpServletRequest request,
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
				//
				// 此处填写业务代码
				List<BBStopic> list = BBStopicDAO.list_topics();// 调用DAO查询数据

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

	
}
