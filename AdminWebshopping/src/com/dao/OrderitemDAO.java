package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.model.Orderitem;
/**
 * 订单的商品描述部分，针对一张订单中商品品类一般不唯一的情况 多个Orderitem从属于一个Order
 */
public class OrderitemDAO {


	/**
	 * 新建子表（附属于订单表） 由OrderDAO中创建订单的函数调用
	 */
	public static boolean newOrderItems(Orderitem orderitem) {
		boolean back = false;

		Connection con = DAO.getCon();
		try {
			PreparedStatement pstm = con
					.prepareStatement("insert into `orderitem` "
							+ "(order_id,goods_id,goods_quantity,goodsDtail,price)"
							+ "VALUES(?,?,?,?,?)");

			

				pstm.setInt(1, orderitem.getOrderID());
				pstm.setInt(2, orderitem.getGoodID());
				pstm.setInt(3, orderitem.getGoodsQuantity());
				pstm.setString(4, orderitem.getDetail());
				pstm.setFloat(5, (float) orderitem.getPrice());

				int flag = pstm.executeUpdate(); // 执行SQL语句
				if (flag > 0) {
					back = true;
				}

			

			pstm.close();
			con.close();
			return back;

		} catch (Exception e) {
			// TODO: handle exception
			// JOptionPane.showMessageDialog(null, "数据库异常");
			System.out.println("新建订单子表失败");
			return false;
		}
	}
	
	/**
	 * 更新子表（附属于订单表）
	 */
	public static boolean updateOrderItems(Orderitem orderitem) {
		boolean back = false;

		Connection con = DAO.getCon();
		try {
			PreparedStatement pstm = con
					.prepareStatement("update  `orderitem` set order_id=?,goods_id=?,goods_quantity=?,goodsDtail=?,price=? where orderitemID=?");

			

				pstm.setInt(1, orderitem.getOrderID());
				pstm.setInt(2, orderitem.getGoodID());
				pstm.setInt(3, orderitem.getGoodsQuantity());
				pstm.setString(4, orderitem.getDetail());
				pstm.setFloat(5, (float) orderitem.getPrice());
				pstm.setInt(6, orderitem.getOrderitemID());

				int flag = pstm.executeUpdate(); // 执行SQL语句
				if (flag > 0) {
					back = true;
				}

			

			pstm.close();
			con.close();
			return back;

		} catch (Exception e) {
			// TODO: handle exception
			// JOptionPane.showMessageDialog(null, "数据库异常");
			System.out.println("新建订单子表失败");
			return false;
		}
	}
	
	/**
	 * 删除子表（附属于订单表）
	 */
	public static boolean deleteOrderItems(int id) {
		boolean back = false;

		Connection con = DAO.getCon();
		try {
			PreparedStatement pstm = con
					.prepareStatement("delete from `orderitem`  where orderitemID=?");

				pstm.setInt(1, id);

				int flag = pstm.executeUpdate(); // 执行SQL语句
				if (flag > 0) {
					back = true;
				}

			

			pstm.close();
			con.close();
			return back;

		} catch (Exception e) {
			// TODO: handle exception
			// JOptionPane.showMessageDialog(null, "数据库异常");
			System.out.println("删除订单子表失败");
			return false;
		}
	}

	/**
	 * 传入OrderID，返回list：该订单包含的所有商品信息 由浏览器直接请求
	 */
	public static List<Orderitem> getOrders(int OrderID) throws SQLException {
		List<Orderitem> list = new ArrayList<Orderitem>();

		Connection con = DAO.getCon();
		String sqlstr = "select orderitemID,goods_id,goods_quantity,goodsDtail,goods.name,goods.image,orderitem.price from orderitem join goods on orderitem.goods_id=goods.goodID where "
				+ " order_id = ?";
		PreparedStatement pstm = con.prepareStatement(sqlstr);
		pstm.setInt(1, OrderID);

		ResultSet rs = pstm.executeQuery();
		while (rs.next() && rs.getRow() > 0) {
			Orderitem temp = new Orderitem();// 注意此对象声明位置

			temp.setOrderitemID(rs.getInt(1));
			temp.setGoodID(rs.getInt(2));
			temp.setGoodsQuantity(rs.getInt(3));
			temp.setDetail(rs.getString(4));
			temp.setName(rs.getString(5));
			temp.setImage(rs.getString(6));
			temp.setPrice(rs.getFloat(7));
			temp.setId(temp.getOrderitemID());
			temp.setOrderID(OrderID);

			list.add(temp);
			// System.out.println("in DAO"+temp.getBrand());
		}
		pstm.close();
		rs.close();
		con.close();
		return list;
	}

}
