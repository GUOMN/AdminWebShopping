package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.model.News;
import com.model.User;
/**
 * 管理员操作：查询编辑用户信息，查看已成交订单，编辑推送新闻，管理论坛，编辑商城商品信息
 * @author 郭梦男
 * @data 2017/1/25
 */
public class AdminDAO {
	/**
	 * 登录密码验证 传入用户名，密码（string） 返回布尔值
	 */
	public static int logIn(String userName, String userPW) throws SQLException {
		int back = -1;

		Connection con = DAO.getCon();
		String sqlstr = "select Password,AdminID from `administrator` where LoginName = ?";
		PreparedStatement pstm = con.prepareStatement(sqlstr);
		pstm.setString(1, userName);
		// System.out.println("userDAO,line28,login");
		ResultSet rs = pstm.executeQuery();
		while (rs.next() && rs.getRow() > 0) {
			String pw = rs.getString(1);
			if (rs.getString(1).equals(userPW)) {
				back = rs.getInt(2);
			}

			// System.out.println(userPW+'\t'+rs.getString(1));
		}

		return back;
	}

	
	
}
