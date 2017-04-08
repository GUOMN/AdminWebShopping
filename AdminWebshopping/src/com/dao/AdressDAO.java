package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.model.Adress;
/**
 * 用于操作地址表 在提交订单前查询用户所有地址，并允许新增地址 在个人信息修改页面允许查询所有地址，允许修改，增加条目或删除条目
 * 
 * @param temp
 * @return
 * @throws SQLException
 */
public class AdressDAO {


	/**
	 * 传入userID，返回list：该用户所有已登记地址
	 * 
	 * @param userID
	 * @return
	 * @throws SQLException
	 */
	public static List<Adress> getAdress(int userID) throws SQLException {
		List<Adress> list = new ArrayList<Adress>();

		Connection con = DAO.getCon();
		String sqlstr = "select addressID,userID,name,tel,postcode,province,city,district,detailAddress from address where"
				+ " userID = ?";
		PreparedStatement pstm = con.prepareStatement(sqlstr);
		pstm.setInt(1, userID);

		ResultSet rs = pstm.executeQuery();
		while (rs.next() && rs.getRow() > 0) {
			Adress temp = new Adress();// 注意此对象声明位置

			temp.setAdressID(rs.getInt(1));
			temp.setUserID(rs.getInt(2));
			temp.setName(rs.getString(3));
			temp.setTel(rs.getString(4));
			temp.setPostcode(rs.getString(5));
			temp.setProvince(rs.getString(6));
			temp.setCity(rs.getString(7));
			temp.setDistrict(rs.getString(8));
			temp.setDetailAddress(rs.getString(9));

			list.add(temp);
			// System.out.println("in DAO"+temp.getBrand());
		}
		pstm.close();
		rs.close();
		con.close();
		return list;
	}

	/**
	 * 传入adress对象，添加到数据库
	 * 
	 * @param userID
	 * @return
	 * @throws SQLException
	 */
	public static boolean addAdress(Adress temp) throws SQLException {
		boolean back = false;
		if (!isExist(temp)) {
			Connection con = DAO.getCon();
			String sqlstr = "insert into address (userID,name,tel,postcode,province,city,district,detailAddress) VALUES (?,?,?,?,?,?,?,?)";
			PreparedStatement pstm = con.prepareStatement(sqlstr);
			// pstm.setInt(1,temp.getAdressID());
			pstm.setInt(1, temp.getUserID());
			pstm.setString(2, temp.getName());
			pstm.setString(3, temp.getTel());
			pstm.setString(4, temp.getPostcode());
			pstm.setString(5, temp.getProvince());
			pstm.setString(6, temp.getCity());
			pstm.setString(7, temp.getDistrict());
			pstm.setString(8, temp.getDetailAddress());

			// int flag=pstm.executeQuery();
			int flag = pstm.executeUpdate(); // 执行SQL语句，获得更新记录数
			if (flag > 0) {
				back = true;
			}
			pstm.close();
			con.close();
		}
		return back;
	}

	/**
	 * 查询相同Adress信息是否存在(添加时若存在则不允许重复添加)
	 */
	public static boolean isExist(Adress temp) throws SQLException {
		boolean back = false;

		Connection con = DAO.getCon();
		String sqlstr = "select * from address where"
				+ " addressID = ? and userID = ? and name= ? and tel= ? and postcode= ? and province= ? and city= ? and district= ? and detailAddress= ? ";
		PreparedStatement pstm = con.prepareStatement(sqlstr);
		pstm.setInt(1, temp.getAdressID());
		pstm.setInt(2, temp.getUserID());
		pstm.setString(3, temp.getName());
		pstm.setString(4, temp.getTel());
		pstm.setString(5, temp.getPostcode());
		pstm.setString(6, temp.getProvince());
		pstm.setString(7, temp.getCity());
		pstm.setString(8, temp.getDistrict());
		pstm.setString(9, temp.getDetailAddress());

		ResultSet rs = pstm.executeQuery();
		while (rs.next() && rs.getRow() > 0) {
			back = true;
		}
		pstm.close();
		con.close();
		return back;
	}

	/**
	 * 删除指定adress条目
	 * 
	 * @param temp
	 * @return
	 * @throws SQLException
	 */
	public static boolean deleteAdress(int AdressID) throws SQLException {
		boolean back = false;

		Connection con = DAO.getCon();
		String sqlstr = "delete from adress where adressID=?";
		PreparedStatement pstm = con.prepareStatement(sqlstr);
		pstm.setInt(1, AdressID);

		int flag = pstm.executeUpdate(); // 执行SQL语句，获得更新记录数
		if (flag > 0) {
			back = true;
		}
		pstm.close();
		con.close();
		return back;
	}

	/**
	 * 修改单条adress信息 先删除旧条目，再添加新条目 未重写，仅调用已有函数
	 * 
	 * @param temp
	 * @return
	 * @throws SQLException
	 */
	public static boolean modifyAdress(Adress temp) throws SQLException {
		boolean back = false;

		if (deleteAdress(temp.getUserID())) {
			if (addAdress(temp))
				back = true;
		}

		return back;
	}

}
