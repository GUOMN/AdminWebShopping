package com.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

import com.dao.FileUploadDAO;
import com.model.File_3DModel;
/**
 * 用于接收用户上传的文件（头像或者模型）
 * 
 * @author 郭梦男 2016-9-25
 */
public class FileUpload extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String servletpath = request.getServletPath();
		// 分析确定处理此次请求的文件
		if ("/UploadModel".equals(servletpath)) {
			UploadModel(request, response);
		} else if ("/querryAll".equals(servletpath)) {
			querryAll(request, response);
		}
	}

	private void querryAll(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String iDString = null;
		try {
			// 实现跨域请求需要在响应中添加如下响应头
			response.addHeader("Access-Control-Allow-Origin", "*");
			// 设置接收和响应字符编码格式，接收或响应中包含汉字时必须有相关声明
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=utf-8");
			iDString = null;
			// iDString="10000";
			HttpSession session = request.getSession();
			try {
				iDString = (String) session.getAttribute("userID");
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("上传stl文件未检测到用户ID，设置为默认值1（测试账号）");
				iDString = "1";
			}
			iDString = String.valueOf(session.getAttribute("userID"));
			// //**********************************
			// String nameString=session.getAttribute("userName").toString();
			// System.out.println("140"+nameString);

			FileUploadDAO dao = new FileUploadDAO();
			// List<File_3DModel> list =
			// dao.querryall(Integer.valueOf(iDString));
			List<File_3DModel> list = dao.querryall(Integer.valueOf(iDString));
			JSONArray jsonArray = JSONArray.fromObject(list);

			PrintWriter out = response.getWriter();
			out.write(jsonArray.toString());
			System.out.println(jsonArray.toString());

			out.flush();
			out.close();
			return;

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			// 未登录，转至登录页面
			// request.getRequestDispatcher("LogIn.html").forward(request,
			// response);
			// response.sendRedirect("LogIn.html");
			// response.setHeader("control","no-cache");
			// response.setHeader("location", "LogIn.html");
			// response.setStatus(302);
			return;
		}

	}

	public void UploadModel(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		// 实现跨域请求需要在响应中添加如下响应头
		response.addHeader("Access-Control-Allow-Origin", "*");
		// 设置接收和响应字符编码格式，接收或响应中包含汉字时必须有相关声明

		response.setContentType("text/html; charset=utf-8");
		// String userName=request.getParameter("userName");
		HttpSession session = request.getSession();// 没有Session就新建一个
		int userID = 0;
		try {
			userID = (Integer) session.getAttribute("userID");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("查询头像未检测到用户ID，设置为默认值1（测试账号）");
			userID = 1;
		}

		// 首先我们应该为上传的文件建一个存放的位置，一般位置分为临时和真是文件夹，那我们就需要获取这俩个文件夹的绝对路径，在servlet中我们可以这样做
		ServletContext application = this.getServletContext();
		// String tempDirectory =
		// application.getRealPath(Constant.TEMP_DIRECTORY) + "/";
		// String realDirectory =
		// application.getRealPath(Constant.REAL_DIRECTORY) + "/";
//		String tempDirectory = "C:/Users/郭梦男/git/WebShopping/WebShopping/WebRoot/uploadFile/temp/";
//		String realDirectory = "C:/Users/郭梦男/git/WebShopping/WebShopping/WebRoot/uploadFile/real/";
		String realDirectory= "C:/Users/guome/git/WebShopping/WebRoot/uploadFile/real/";//远程电脑路径
		String tempDirectory= "C:/Users/guome/git/WebShopping/WebRoot/uploadFile/temp/";//远程电脑路径

		// 然后建立文件工厂即仓库一个参数表示存放多大后flush，第一个参数为临界值，大于此数值存放到硬盘，小于存到内存
		// FileItemFactory factory = new
		// DiskFileItemFactory(Constant.SIZE_THRESHOLD,new File(tempDirectory));
		DiskFileItemFactory factory = new DiskFileItemFactory(10240, new File(
				tempDirectory));
		ServletFileUpload upload = new ServletFileUpload(factory);

		// 对上传的文件进行设定
		upload.setSizeMax(500 * 1024 * 1024);// 设置该次上传最大值为500M

		// .解析请求正文，获取上传文件，不抛出异常则写入真实路径
		List<FileItem> list = null;
		try {
			list = upload.parseRequest(new ServletRequestContext(request));
			// list=factory.
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Iterator<FileItem> iter = list.iterator();
		while (iter.hasNext()) {
			FileItem item = iter.next();
			// item.isFormField()用来判断当前对象是否是file表单域的数据 如果返回值是true说明不是 就是普通表单域
			if (item.isFormField()) {
				System.out.println("普通表单域" + item.getFieldName());
				// System.out.println(item.getString("utf-8"));

			} else {
				/*
				 * 只有file表单域才将该对象中的内容写到真实文件夹中 用标识字符表明当前文件是头像还是模型
				 */
				File_3DModel file = new File_3DModel();
				String lastpath = item.getName();// 获取上传文件的名称
				file.setModelName(lastpath);
				lastpath = lastpath.substring(lastpath.lastIndexOf("."));
				String filename = UUID.randomUUID().toString().replace("-", "")
						+ lastpath;
				System.out.println(filename);
				file.setLocation(filename);
				file.setUserID(userID);
				boolean success = false;
				try {
					item.write(new File(realDirectory + filename));
					// item.write(new File(realDirectory+lastpath));
					FileUploadDAO dao = new FileUploadDAO();
					success = dao.insert(file);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (success) {
					PrintWriter out = response.getWriter();
					out.write("模型上传成功");
					out.flush();
					out.close();
				}
			}
		}

	}
}
