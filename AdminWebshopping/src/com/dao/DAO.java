package com.dao;

import java.sql.DriverManager;

import com.mysql.jdbc.Connection;


/**
 * 数据库连接
 * @author 郭梦男
 *
 */
public class DAO {
	private static DAO dao = new DAO(); // 声明DAO类的静态实例

	/**
	 * 构造方法，加载数据库驱动
	 */
	public DAO() {
		try {
			// Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Class.forName("com.mysql.jdbc.Driver");//反射机制
		} catch (ClassNotFoundException e) {
//			JOptionPane.showMessageDialog(null, "数据库驱动加载失败，请将JTDS驱动配置到构建路径中。\n"+ e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 获得数据库连接的方法
	 * 
	 * @return Connection
	 */
	public static java.sql.Connection getCon() {
		try {
			java.sql.Connection conn = null; // 定义数据库连接
			// String url =
			// "jdbc:sqlserver://localhost:1433;DatabaseName=WebShopping"; //
			// 数据库WebShopping的URL
			String url = "jdbc:mysql://localhost:3306/webshopping?user=root&password=1234&useUnicode=true&characterEncoding=utf8"; // 数据库WebShopping的URL
			conn=DriverManager.getConnection(url);
			// System.out.println("连接数据库成功");
			return conn; // 返回连接
		} catch (Exception e) {
//			JOptionPane.showMessageDialog(null, "数据库连接失败。\n请检查数据库用户名和密码是否正确。"+ e.getMessage());
			System.out.println("连接失败：");
			e.printStackTrace();
			return null;
		}
	}
}
