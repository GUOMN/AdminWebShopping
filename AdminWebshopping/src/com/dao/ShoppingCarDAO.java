package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.model.ShoppingCar;
/**
 * 用户的购物车dao
 * @author 郭梦男
 *
 */
public class ShoppingCarDAO {

	// 功能：根据userID查看用户的购物车，用户对购物车商品数量进行修改（含删除，结算）
	public ShoppingCarDAO() {
		try {
			// 自动维护，在所有查询前删除数量为0的条目
			deleteCar();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 添加商品到购物车
	 */
	@SuppressWarnings("deprecation")
	public boolean addGood(ShoppingCar temp) throws SQLException {

		boolean back = false;
		//先检查购物车是否有相同信息，若存在则修改数量
		int flag = isExist(temp);
		if (flag == -1) {
			//不存在，新建
			Connection con = DAO.getCon();
			String sqlstr = "insert into ShoppingCar (goodID,userID,color,material,state,date,amount)VALUES(?,?,?,?,?,?,?)";
			PreparedStatement pstm = con.prepareStatement(sqlstr);
			pstm.setInt(1, temp.getGoodID());
			pstm.setInt(2, temp.getUserID());
			pstm.setString(3, temp.getColor());
			pstm.setString(4, temp.getMaterial());
			pstm.setString(5, temp.getState());
			pstm.setInt(7, temp.getAmount());

			// 注意SQL的DATE和UTIL的DATE转换
			// pstm.setDate(6,new
			// java.sql.Date(temp.getAddedTimeDATE().getTime()));
			System.out.println(temp.getAddedTime());

			try {
				SimpleDateFormat format = new SimpleDateFormat(
						"yyyy-MM-dd hh:MM:ss");
				System.out.println("53:ShoppingCarDAO,dateSTR:"
						+ temp.getAddedTime());
				Date date = format.parse(temp.getAddedTime());
				System.out.println("60" + date.toString());
				// 将实体类的出生日期转换为Date类型
				// pstm.setTimestamp(6, new Timestamp(date.getTime()));
				pstm.setTimestamp(6, new Timestamp(System.currentTimeMillis()));

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			int flag1 = pstm.executeUpdate(); // 执行SQL语句，获得更新记录数
			if (flag1 > 0) {
				back = true;
			}
			pstm.close();
			con.close();
		} else {
			//存在，修改数量
			back = modifyShoppingCar2(flag, temp.getAmount());
		}

		return back;
	}

	/**
	 * 查询相同购物车信息是否存在(添加时若存在则只修改数目)
	 */
	public static int isExist(ShoppingCar temp) throws SQLException {
		int back = -1;

		Connection con = DAO.getCon();
		String sqlstr = "select ShoppingCarID from ShoppingCar where"
				+ " userID = ? and goodID = ? and color= ? and material= ? ";
		PreparedStatement pstm = con.prepareStatement(sqlstr);
		pstm.setInt(2, temp.getGoodID());
		pstm.setInt(1, temp.getUserID());
		pstm.setString(3, temp.getColor());
		pstm.setString(4, temp.getMaterial());

		ResultSet rs = pstm.executeQuery();
		if (rs.next() && rs.getRow() > 0) {
			back = rs.getInt(1);
		}
		pstm.close();
		rs.close();
		con.close();
		return back;
	}

	/**
	 * 查看某一用户的购物车 当查询到amount==0时删除该行
	 */
	public List<ShoppingCar> querryShoppingCar(int userID) throws SQLException {
		try {
			List<ShoppingCar> list = new ArrayList<ShoppingCar>();
			Connection con = DAO.getCon();
			PreparedStatement pstm = con
					.prepareStatement("SELECT goods.goodID,shoppingcar.color,shoppingcar.material,shoppingcar.state,shoppingcar.date,shoppingcar.amount,goods.name,goods.`price` " +
							",shoppingcar.`ShoppingCarID` FROM shoppingcar JOIN goods ON shoppingcar.`goodID`=goods.`goodID` WHERE shoppingcar.userID=?");
			pstm.setInt(1, userID);
			ResultSet rs = pstm.executeQuery();
			while (rs.next() && rs.getRow() > 0) {
				ShoppingCar temp = new ShoppingCar();// 注意此对象声明位置
				temp.setGoodID(rs.getInt("goodID"));
				temp.setColor(rs.getString("color"));
				temp.setMaterial(rs.getString("material"));
				temp.setState(rs.getString("state"));
				temp.setAddedTime(rs.getDate("date"));
				temp.setShoppingCarID(rs.getInt("ShoppingCarID"));
				if (rs.getInt("amount") > 0) {
					temp.setAmount(rs.getInt("amount"));
				} else {
					deleteCar(rs.getInt("ShoppingCarID"));
				}
				temp.setName(rs.getString("name"));
				temp.setPrice(rs.getDouble("price"));

				list.add(temp);
				// System.out.println("in DAO"+temp.getBrand());
			}
			pstm.close();
			rs.close();
			con.close();
			return list;

		} catch (Exception e) {
			// TODO: handle exception
			// JOptionPane.showMessageDialog(null, "数据库异常");
			System.out.println("读取用户购物车失败");
			return null;
		}
	}

	/**
	 * 修改购物车,含结算，删除(仅修改数量，不能修改其他属性) 传入ShoppingCarID，amount（直接指定最终数值）
	 */
	public boolean modifyShoppingCar(int carID, int amount) throws SQLException {
		boolean back = false;

		Connection con = DAO.getCon();
		String sqlstr = "update ShoppingCar set amount=?,date=? where ShoppingCarID=?";
		PreparedStatement pstm = con.prepareStatement(sqlstr);
		pstm.setInt(1, amount);
		pstm.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
		pstm.setInt(3, carID);

		int flag = pstm.executeUpdate(); // 执行SQL语句，获得更新记录数
		if (flag > 0) {
			back = true;
		}
		return back;
	}

	/**
	 * 重载修改购物车,可在原amount基础上增加减小 传入ShoppingCarID，attachment（原amount的增量，负号减）
	 */
	public boolean modifyShoppingCar2(int carID, int attachment)
			throws SQLException {
		boolean back = false;

		Connection con = DAO.getCon();
		String sqlstr = "update ShoppingCar set amount=amount+?,date=? where ShoppingCarID=?";
		PreparedStatement pstm = con.prepareStatement(sqlstr);
		pstm.setInt(1, attachment);
		pstm.setInt(3, carID);
		// pstm.setDate(2,new java.sql.Date(new Date().getTime()));
		// System.out.println(new java.sql.Date(new
		// Date().getTime()).toString());
		// System.out.println(new Date( new Date().getTime()));

		try {
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd hh:MM:ss");
			Date date = new Date();
			String dates = (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"))
					.format(date);
			System.out.println("197,dates:" + dates);
			date = format.parse(dates);// BUG
			System.out.println("199,date" + date);

			// 将实体类的出生日期转换为Date类型
			// pstm.setTimestamp(2, new Timestamp(date.getTime()));
			// pstm.setTime(2,new Time(new Date().getTime()));
			pstm.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int flag = pstm.executeUpdate(); // 执行SQL语句，获得更新记录数
		if (flag > 0) {
			back = true;
		}
		return back;
	}

	/**
	 * 删除一行（指定ID）
	 */
	public boolean deleteCar(int carID) throws SQLException {
		boolean back = false;

		Connection con = DAO.getCon();
		String sqlstr = "delete  from  ShoppingCar where ShoppingCarID=?";

		PreparedStatement pstm = con.prepareStatement(sqlstr);
		pstm.setInt(1, carID);

		int flag = pstm.executeUpdate(); // 执行SQL语句，获得更新记录数
		if (flag > 0) {
			back = true;
		}
		return back;
	}

	/**
	 * 重载删除方法 删除一行（当amount==0时） 用于表的自动维护，创建本DAO对象时调用
	 */
	public boolean deleteCar() throws SQLException {
		boolean back = false;

		Connection con = DAO.getCon();
		String sqlstr = "delete  from  ShoppingCar where amount=0";

		PreparedStatement pstm = con.prepareStatement(sqlstr);

		int flag = pstm.executeUpdate(); // 执行SQL语句，获得更新记录数
		if (flag > 0) {
			back = true;
		}
		return back;
	}

}
