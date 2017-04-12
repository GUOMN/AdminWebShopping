package com.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.model.Order;
import com.model.Orderitem;
/**
 * 操作订单（order）表 可以添加订单，查看订单,取消订单（被取消的订单并不允许删除，只是修改其状态属性status，使其失效）
 */
public class OrderDAO {


	
	
	/**
	 * 新建订单
	 */
	public static boolean newOrder(Order order) {
		boolean back = false;
		try {
			Connection con = DAO.getCon();
			// //注意：order是sql关键字
//			 PreparedStatement pstm=con.prepareStatement("select sum(list_number) from [order] where goodsID="+goodsid);
			 PreparedStatement
			 pstm=con.prepareStatement("INSERT INTO `order` (userID,TIME,deliver_address,pay_method,total_price,InvoiceID,STATUS)" +
			 		" VALUES(?,NOW(),?,?,?,?,'true');");
			// //时间有sql的getdate（）函数获得，无须在Java获取
			 pstm.setInt(1,order.getUserID());
			 pstm.setInt(2,order.getDeliver_address());
			 pstm.setString(3,order.getPay_method());
			 pstm.setFloat(4,(float) order.getTotal_price());
			 pstm.setInt(5,order.getInvoiceID());
			
			 int flag=pstm.executeUpdate();
			 if(flag==1) back=true;
			 
			 
			 pstm.close();

			con.close();
			return back;

		} catch (Exception e) {
			// TODO: handle exception
			// JOptionPane.showMessageDialog(null, "数据库异常");
			System.out.println("新建订单失败:" + e.getMessage());
			return false;
		}
	}

	/**
	 * 返回list：所有订单
	 * 
	 * @param userID
	 * @return
	 * @throws SQLException
	 */
	public static List<Order> getOrders() throws SQLException {
		List<Order> list = new ArrayList<Order>();

		Connection con = DAO.getCon();
		String sqlstr = "select orderID,userID,remark,time,deliver_address,pay_method,total_price,InvoiceID,status from `order`";
		PreparedStatement pstm = con.prepareStatement(sqlstr);

		ResultSet rs = pstm.executeQuery();
		while (rs.next() && rs.getRow() > 0) {
			Order temp = new Order();// 注意此对象声明位置

			temp.setOrderID(rs.getInt(1));
			temp.setUserID(rs.getInt(2));
			temp.setRemark(rs.getString(3));
			// temp.setGoodsDtail(rs.getString(4));
			temp.setTime(rs.getDate(4));
			temp.setDeliver_address(rs.getInt(5));
			temp.setPay_method(rs.getString(6));
			// temp.setAmount(rs.getInt(8));
			temp.setTotal_price(rs.getDouble(7));
			temp.setInvoiceID(rs.getInt(8));
			temp.setStatus(rs.getString(9));
			temp.setId(temp.getOrderID());

			list.add(temp);
			// System.out.println("in DAO"+temp.getBrand());
		}
		pstm.close();
		rs.close();
		con.close();
		return list;
	}

	/**
	 * 修改订单
	 * 
	 * @param userID
	 * @return
	 * @throws SQLException
	 */
	public static boolean updateOrders(Order order) throws SQLException {
		boolean back = false;

		Connection con = DAO.getCon();
		String sqlstr = "update `order` set userID=?,remark=?,deliver_address=?,pay_method=?,total_price=?,InvoiceID=?,status=?  where orderID=?";
		PreparedStatement pstm = con.prepareStatement(sqlstr);
		pstm.setInt(1, order.getUserID());
		pstm.setString(2, order.getRemark());
		pstm.setInt(3, order.getDeliver_address());
		pstm.setString(4, order.getPay_method());
		pstm.setDouble(5, order.getTotal_price());
		pstm.setInt(6, order.getInvoiceID());
		pstm.setString(7, order.getStatus());
		pstm.setInt(8, order.getOrderID());

		int flag = pstm.executeUpdate(); // 执行SQL语句，获得更新记录数
		if (flag > 0) {
			back = true;
		}
		pstm.close();
		con.close();
		return back;
	}
	/**
	 * 删除订单
	 */
	public static boolean deleteOrder(int id) {
		boolean back = false;

		Connection con = DAO.getCon();
		try {
			PreparedStatement pstm = con
					.prepareStatement("delete from `order`  where orderID=?");

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
}
