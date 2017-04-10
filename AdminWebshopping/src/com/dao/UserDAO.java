package com.dao;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.model.User;
/**
 * 商城用户DAO 功能：用户登录密码验证，用户注册, 修改用户表中信息，修改address表信息，修改发票表信息 修改密码
 */
public class UserDAO {


	/**
	 * 列出所有用户信息
	 * @return List<User>
	 */
	public static List<User> listUsers(){
		List<User> list =new ArrayList<User>();
		Connection con = DAO.getCon();
		PreparedStatement pstm;
		ResultSet rs;
		try {
			pstm = con.prepareStatement("select * from user");
			rs = pstm.executeQuery();
			
			while(rs.next()){
				User user=new User();
				user.setUserID(rs.getInt("userID"));
				user.setName(rs.getString("name"));
				user.setSex(rs.getString("sex"));
				user.setLoginName(rs.getString("loginName"));
				user.setLoginPW(rs.getString("loginPW"));
				user.setAge(rs.getString("age"));
				user.setTel(rs.getString("tel"));
				user.setEmail(rs.getString("email"));
				user.setId(rs.getInt("userID"));
				list.add(user);	
			}
			pstm.close();
			rs.close();
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
			return list;


		
		
	}

	
	/**
	 * 用户注册 传入用户名（string），密码（string），邮箱（string） 返回字符串
	 */
	public static String signUp(String userName, String userPW, String Email)
			throws SQLException {
		String backString = "false,注册失败";
		if (isExist(userName)) {
			backString = "false,用户名已存在";
		} else {
			Connection con = DAO.getCon();
			String sqlstr = "insert into `user` (loginName,loginPW,email)VALUES(?,?,?)";
			PreparedStatement pstm = con.prepareStatement(sqlstr);
			pstm.setString(1, userName);
			pstm.setString(2, userPW);
			pstm.setString(3, Email);
			int flag = pstm.executeUpdate(); // 执行SQL语句，获得更新记录数
			if (flag > 0) {
				backString = "true";
				// JOptionPane.showMessageDialog(null, "添加成功！");
			}
			pstm.close();
			con.close(); // 关闭连接
			// ResultSet rs=pstm.executeQuery();
			// while(rs.next()&&rs.getRow()>0){
			// pass=rs.getString(1).equals(userPW);
			// }

		}
		return backString;
	}

	/**
	 * 查询用户名是否已存在
	 */
	public static boolean isExist(String userName) throws SQLException {
		boolean pass = false;

		Connection con = DAO.getCon();
		String sqlstr = "select loginPW from `user` where loginName = '"
				+ userName + "'";
		PreparedStatement pstm = con.prepareStatement(sqlstr);

		// int flag = pstm.executeUpdate(); // 执行SQL语句，获得更新记录数
		// if (flag > 0) {
		// pass=true;
		// // JOptionPane.showMessageDialog(null, "添加成功！");
		// }
		ResultSet rs = pstm.executeQuery();
		while (rs.next() && rs.getRow() > 0) {
			pass = true;
		}

		// System.out.println(userPW+'\t'+rs.getString(1));

		pstm.close();
		con.close(); // 关闭连接
		// ResultSet rs=pstm.executeQuery();
		// while(rs.next()&&rs.getRow()>0){
		// pass=rs.getString(1).equals(userPW);
		// }
		return pass;
	}

	/**
	 * 用于管理员操作（更新已有用户信息）
	 * 修改用户信息 传入用户对象（解析ID），
	 * 修改：邮箱（string），手机（String），DefaultAddressID（int）
	 * subscribeNews(int),subscribeOrderStatus(int) 返回布尔值
	 */
	public static boolean modifyUserINFO(User temp) throws SQLException {
		boolean back = false;
		Connection con = DAO.getCon();
		PreparedStatement pstm = null;
		if (temp.getImage() != null) {
			String sqlstr = "update `user` set email=?,tel=?,DefaultAddressID=?,name=?,loginName=?  where userID=?";
			pstm = con.prepareStatement(sqlstr);
			pstm.setInt(7, temp.getUserID());
			pstm.setString(1, temp.getEmail());
			pstm.setString(2, temp.getTel());
			pstm.setInt(3, temp.getDefaultAddressID());
			// pstm.setString(4,temp.getImage());
			// 存储用户头像
			try {
				FileInputStream in = null;
				if (temp.getImage() != null) {
					File file = new File(temp.getImage());

					in = new FileInputStream(file);

					pstm.setBinaryStream(4, in, (int) file.length());
				} else {
					//
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			pstm.setString(5, temp.getName());
			pstm.setString(6, temp.getLoginName());
		} else {
			String sqlstr = "update `user` set email=?,tel=?,DefaultAddressID=?,name=?,loginName=?,age=?,sex=? where userID=?";
			pstm = con.prepareStatement(sqlstr);
			
			pstm.setString(1, temp.getEmail());
			pstm.setString(2, temp.getTel());
			pstm.setInt(3, temp.getDefaultAddressID());
			pstm.setString(4, temp.getName());
			pstm.setString(5, temp.getLoginName());
			pstm.setString(6, temp.getAge());
			pstm.setString(7, temp.getSex());
			pstm.setInt(8, temp.getUserID());
		}

		int flag = pstm.executeUpdate(); // 执行SQL语句，获得更新记录数
		if (flag==1) {
			back = true;
			// JOptionPane.showMessageDialog(null, "添加成功！");
		}
		pstm.close();
		con.close(); // 关闭连接
		// ResultSet rs=pstm.executeQuery();
		// while(rs.next()&&rs.getRow()>0){
		// pass=rs.getString(1).equals(userPW);
		// }

		return back;
	}
	/**
	 * 管理员新建用户
	 * @param (User)user
	 * @return (boolean) flag
	 */
	public static boolean newUser(User user) {
		boolean back = false;
		try {
			if (isExist(user.getName())) {
				back = false;//用户名已存在
			} else {
				Connection con = DAO.getCon();
				String sqlstr = "insert into `user` (loginName,loginPW,email,sex,name,age,tel,IsAdmin)VALUES(?,1234,?,?,?,?,?,?)";
				PreparedStatement pstm;
				try {
					pstm = con.prepareStatement(sqlstr);
					pstm.setString(1, user.getLoginName());
					pstm.setString(2, user.getEmail());
					pstm.setString(3, user.getSex());
					pstm.setString(4, user.getName());
					pstm.setString(5, user.getAge());
					pstm.setString(6, user.getTel());
					pstm.setBoolean(7, false);
					
					
					int flag = pstm.executeUpdate(); // 执行SQL语句，获得更新记录数
					back=flag==1?true:false;
					
					pstm.close();
					con.close(); // 关闭连接
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return back;
	}
	/**
	 * 管理员删除账户
	 * @return boolean
	 * @param userID(int)
	 */
	public static boolean delete_user(int userID) {
		boolean option = false;

		Connection con = DAO.getCon();
		String sqlstr = "delete from `user` where userID =?";
		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sqlstr);
			pstm.setInt(1,userID);
			int i=pstm.executeUpdate();
			if (i==1) {
				option = true;
			}
			pstm.close();
			con.close(); // 关闭连接

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return option;		
	}

	/**
	 * 用于用户自己修改部分信息
	 * 修改用户信息 传入用户ID，邮箱（string），手机（String),性别，签名档(remark)，昵称 返回布尔值
	 */
	public static boolean modifyUserINFO2(User temp) throws SQLException {
		boolean back = false;
		Connection con = DAO.getCon();
		PreparedStatement pstm = null;
		String sqlstr = "update `user` set email=?,tel=?,remark=?,sex=?,name=?  where userID=?";
		pstm = con.prepareStatement(sqlstr);
		pstm.setInt(6, temp.getUserID());
		pstm.setString(1, temp.getEmail());
		pstm.setString(2, temp.getTel());
		pstm.setString(3, temp.getRemark());
		pstm.setString(4, temp.getSex());
		pstm.setString(5, temp.getName());

		int flag = pstm.executeUpdate(); // 执行SQL语句，获得更新记录数
		if (flag > 0) {
			back = true;
			// JOptionPane.showMessageDialog(null, "添加成功！");
		}
		pstm.close();
		con.close(); // 关闭连接
		// ResultSet rs=pstm.executeQuery();
		// while(rs.next()&&rs.getRow()>0){
		// pass=rs.getString(1).equals(userPW);
		// }

		return back;
	}

	/**
	 * 显示用户信息
	 * 
	 * @param用户ID
	 * @return（昵称（非登录名），登录名，年龄电话，email，签名档（remark）,性别）
	 */
	public static User getUserINFO(int ID) throws SQLException {
		Connection con = DAO.getCon();
		PreparedStatement pstm = null;
		String sqlstr = "select * from  `user` where userID=?";
		pstm = con.prepareStatement(sqlstr);
		pstm.setInt(1, ID);

		ResultSet rs = pstm.executeQuery(); // 执行SQL语句，获得结果集
		User temp = new User();
		if (rs.next()) {
			temp.setAge(rs.getString("age"));
			temp.setEmail(rs.getString("email"));
			temp.setLoginName(rs.getString("loginName"));
			temp.setName(rs.getString("name"));// 昵称
			temp.setSex(rs.getString("sex"));
			temp.setRemark(rs.getString("remark"));// 签名档
			temp.setTel(rs.getString("tel"));
		}

		pstm.close();
		con.close(); // 关闭连接
		// ResultSet rs=pstm.executeQuery();
		// while(rs.next()&&rs.getRow()>0){
		// pass=rs.getString(1).equals(userPW);
		// }

		return temp;
	}


	/*
	 * 实现的功能： 用数据库存储图片数据，并且读取出来放在当前项目里
	 */

	/**
	 * 头像写入数据库
	 * 
	 * @param user
	 * @return boolean 标志位
	 * @throws SQLException
	 * @throws IOException
	 * @author 郭梦男
	 */
	public static boolean modifyICON(String ID) throws SQLException,
			IOException {
		boolean f = false;
		Connection conn = null;
		PreparedStatement pstm = null;

		try {
			// 2.建立连接
			conn = DAO.getCon();
			// //单例设计模式
			// conn=JdbcUtilsSingle.getInstance().getConnection();
			// 3.创建语句
			String sql = "update `user` set image=?  where userID=?";
			pstm = conn.prepareStatement(sql);

			File file = new File(
//					"C:/Users/郭梦男/git/WebShopping/WebShopping/WebRoot/user_icon/uploaded//"
					"C:/Users/guome/git/WebShopping/WebRoot/user_icon/real//"//远程电脑路径
							+ ID + ".jpg");
			InputStream in = new BufferedInputStream(new FileInputStream(file));
			pstm.setBinaryStream(1, in, (int) file.length());
			pstm.setInt(2, Integer.valueOf(ID));
			// 4.执行语句

			int i = pstm.executeUpdate();
			in.close();
			if (i > 0) {
				f = true;
			}
		} finally {
			// JdbcUtils.free(resultset, prest, conn);
			if (pstm != null)
				pstm.close();
			if (conn != null)
				conn.close();

		}
		return f;
	}

	/**
	 * 头像从数据库读出
	 * 恢复到本地文件，将url发送回浏览器
	 * @throws SQLException
	 * @throws IOException
	 */
	public static boolean readIcon(String ID) throws SQLException, IOException {
		boolean success=false;
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet resultset = null;

		try {
			// 2.建立连接
			conn = DAO.getCon();
			// //单例设计模式
			// conn=JdbcUtilsSingle.getInstance().getConnection();
			// 3.创建语句
			pstm = conn
					.prepareStatement("select image from `user` where userID=?");
			pstm.setInt(1, Integer.valueOf(ID));
			// 4.执行语句
			resultset = pstm.executeQuery();
			// 5.处理结果
			while (resultset.next()) {
				Blob blob = resultset.getBlob(1);
				InputStream in = blob.getBinaryStream();
				// reader=resultset.getCharacterStream(1);
				// File file=new
				// File("C:/Users/郭梦男/git/WebShopping/WebShopping/WebRoot/user_icon/real/"+ID+".jpg");
				File file = new File(
//						"C:/Users/郭梦男/git/WebShopping/WebShopping/WebRoot/user_icon/real//"
						"C:/Users/guome/git/WebShopping/WebRoot/user_icon/real//"//远程电脑路径
								+ ID + ".jpg");
				OutputStream out = new BufferedOutputStream(
						new FileOutputStream(file));
				byte[] buff = new byte[1024];
				for (int i = 0; (i = in.read(buff)) > 0;) {
					out.write(buff, 0, i);
				}
				out.close();
				in.close();
				success=true;
			}

		} finally {
			// JdbcUtils.free(resultset, st, conn);
			if (pstm != null)
				pstm.close();
			if (conn != null)
				conn.close();
			if (resultset != null)
				resultset.close();
		}
		return success;
	}

	/**
	 * 修改密码 传入用户ID，旧密码，新密码 返回布尔值
	 */
	public static boolean modifyPW(int ID, String oldPW, String newPW)
			throws SQLException {
		boolean back = false;

		Connection con = DAO.getCon();
		String sqlstr = "update `user` set loginPW='?'  where userID=? and loginPW='?'";
		PreparedStatement pstm = con.prepareStatement(sqlstr);
		pstm.setString(1, newPW);
		pstm.setInt(2, ID);
		pstm.setString(3, oldPW);
		int flag = pstm.executeUpdate(); // 执行SQL语句，获得更新记录数
		if (flag > 0) {
			back = true;
			// JOptionPane.showMessageDialog(null, "添加成功！");
		}
		pstm.close();
		con.close(); // 关闭连接
		// ResultSet rs=pstm.executeQuery();
		// while(rs.next()&&rs.getRow()>0){
		// pass=rs.getString(1).equals(userPW);
		// }

		return back;
	}

}
