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
	public static boolean newOrder(Order order, List<Orderitem> list) {
		boolean back = false;
		int OrderID = 0;
		try {
			Connection con = DAO.getCon();
			// //注意：order是sql关键字
			// // PreparedStatement
			// pstm=con.prepareStatement("select sum(list_number) from [order] where goodsID="+goodsid);
			// PreparedStatement
			// pstm=con.prepareStatement("insert into [order] " +
			// "(userID,time,deliver_address,pay_method,total_price,InvoiceID,status,remark)"
			// +
			// "VALUES(?,getdate(),?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			// // PreparedStatement
			// pstm=con.prepareStatement("@rt=exec ADDOrder  ?,?,?,?,?;select @rt");
			// select scope_identity()
			// // 插入到订单表，同时获取当前添加条目产生的自增标识
			// //orderID是自增标识，不能输入
			// // pstm.setInt(1,order.getOrderID());
			// pstm.setInt(1,order.getUserID());
			// // pstm.setString(3,order.getGoodsDtail());
			// // order.getTime()
			// //时间有sql的getdate（）函数获得，无须在Java获取
			// // pstm.setDate(3,(Date) new java.util.Date());
			// pstm.setInt(2,order.getDeliver_address());
			// pstm.setString(3,order.getPay_method());
			// pstm.setFloat(4,(float) order.getTotal_price());
			// pstm.setInt(5,order.getInvoiceID());
			// // pstm.setString(11,order.getStatus());
			// pstm.setString(6,"true");
			// pstm.setString(7,order.getRemark());
			// // PreparedStatement
			// pstm=con.prepareStatement("insert into [order] " +
			// //
			// "(userID,time,deliver_address,pay_method,total_price,InvoiceID,status,remark)"
			// +
			// //
			// "VALUES("+order.getUserID()+",getdate(),"+order.getDeliver_address()+",'"+order.getPay_method()+"',"+order.getTotal_price()+","+order.getInvoiceID()+",1)"
			// +
			// // " select scope_identity()");
			// //
			// // int f=pstm.executeUpdate(); // 执行SQL语句，获得更新记录数
			// // ResultSet rs=pstm.executeQuery("select scope_identity()");
			// ResultSet rs=pstm.executeQuery();

			// if(rs.next()){
			// OrderID=rs.getInt(1);
			// // }
			// System.out.println("line 58,newOrderID"+OrderID);
			// back= OrderitemDAO.newOrderItems(list,OrderID);
			//
			// pstm.close();
			// ****************************************************************************************************
//			CallableStatement cstmt = con.prepareCall("{? = call webshopping.ADDOrder(?,?,?,?,?,)}");//sql2005
			CallableStatement cstmt = con.prepareCall("{call webshopping.ADDOrder(?,?,?,?,?,?)}");
			cstmt.registerOutParameter(6, java.sql.Types.INTEGER);
			cstmt.setInt(1, order.getUserID());
			cstmt.setInt(2, order.getDeliver_address());
			cstmt.setString(3, order.getPay_method());
			cstmt.setFloat(4, (float) order.getTotal_price());
			cstmt.setInt(5, order.getInvoiceID());
			cstmt.execute();

			OrderID = cstmt.getInt(6);
			System.out.println("line 86,newOrderID:" + OrderID);
			back = OrderitemDAO.newOrderItems(list, OrderID);
			// ****************************************************************************************************

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
	 * 传入userID，返回list：该用户所有订单
	 * 
	 * @param userID
	 * @return
	 * @throws SQLException
	 */
	public static List<Order> getOrders(int userID) throws SQLException {
		List<Order> list = new ArrayList<Order>();

		Connection con = DAO.getCon();
		String sqlstr = "select orderID,userID,remark,time,deliver_address,pay_method,total_price,InvoiceID,status from `order` where"
				+ " userID = ?";
		PreparedStatement pstm = con.prepareStatement(sqlstr);
		pstm.setInt(1, userID);

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

			list.add(temp);
			// System.out.println("in DAO"+temp.getBrand());
		}
		pstm.close();
		rs.close();
		con.close();
		return list;
	}

	/**
	 * 修改订单（只允许修改生效状态status,理解为用户取消了该订单） 注意：不允许修改已失效的订单使其生效
	 * 
	 * @param userID
	 * @return
	 * @throws SQLException
	 */
	public static boolean modifyOrders(int orderID) throws SQLException {
		boolean back = false;

		Connection con = DAO.getCon();
		String sqlstr = "update `order` set status='false' where orderID=?";
		PreparedStatement pstm = con.prepareStatement(sqlstr);
		pstm.setInt(1, orderID);

		int flag = pstm.executeUpdate(); // 执行SQL语句，获得更新记录数
		if (flag > 0) {
			back = true;
		}
		pstm.close();
		con.close();
		return back;
	}
}
