package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.model.Goods;
/**
 * 用goodID查询整行 用categoryID，sex，number，name，brand，attachment，价格区间，color，
 * handinventory是否有库存，查找符合条件的所有goodID 返回所有good
 * @author 郭梦男
 */
public class GoodsDAO {

	

	// 可以通过goodID返回行(用于显示商品详情页)
	public static Goods queryGoods(int ID) {
		Goods bs = new Goods();
		try {
			Connection con = DAO.getCon();
			PreparedStatement pstm = con
					.prepareStatement("select goodID,categoryID,sex,number,name,introduct,"
							+ "material,color,image,3DModelFile,price "
							+ "from goods where goodID = ?");
			pstm.setInt(1, ID);
			ResultSet rs = pstm.executeQuery();
			while (rs.next() && rs.getRow() > 0) {

				bs.setGoodID(rs.getInt(1));
				bs.setCategoryID(rs.getInt(2));
				bs.setSex(rs.getString(3));
				bs.setNumber(rs.getString(4));
				bs.setName(rs.getString(5));
				bs.setIntroduct(rs.getString(6));
				bs.setMaterial(rs.getString(7));
				bs.setColor(rs.getString(8));
				bs.setImage(rs.getString(9));
				bs.setModelFilelocation(rs.getString("3DModelFile"));
				bs.setPriceCommon(rs.getDouble("price"));
				bs.setId(bs.getGoodID());
			}
			pstm.close();
			rs.close();
			con.close();
			return bs;

		} catch (Exception e) {
			// TODO: handle exception
			// JOptionPane.showMessageDialog(null, "数据库异常");
			System.out.println("读取Goods失败");
			e.printStackTrace();
			return null;
		}

	}
	
	/**
	 * 列出所有商品
	 */
	public static List<Goods> queryGoods() {
		
		try {
			Connection con = DAO.getCon();
			List<Goods> list = new ArrayList<Goods>();
			PreparedStatement pstm = con
					.prepareStatement("select * from goods");
			ResultSet rs = pstm.executeQuery();
			while (rs.next() && rs.getRow() > 0) {
				Goods bs = new Goods();
				bs.setGoodID(rs.getInt("goodID"));
				bs.setCategoryID(rs.getInt("categoryID"));
				bs.setSex(rs.getString("sex"));
				bs.setNumber(rs.getString("number"));
				bs.setName(rs.getString("name"));
				bs.setIntroduct(rs.getString("introduct"));
				bs.setMaterial(rs.getString("material"));
				bs.setPriceCommon(rs.getDouble("price"));
				bs.setPriceSpecial(rs.getDouble("price"));
				bs.setColor(rs.getString("color"));
				bs.setImage(rs.getString("image"));
				bs.setModelFilelocation("3DModelFile");
				bs.setId(bs.getGoodID());
				list.add(bs);
			}
			pstm.close();
			rs.close();
			con.close();
			return list;

		} catch (Exception e) {
			// TODO: handle exception
			// JOptionPane.showMessageDialog(null, "数据库异常");
			System.out.println("读取失败");
			return null;
		}
	}

	/**
	 * 用价格区间筛选 sql =
	 * "Select * from [sheet1$] Where 出库日期 between  #2005-1-4# and #2005-1-10#"
	 */
	public static List<Goods> queryGoods(double bottom, double top) {
		List<Goods> list = new ArrayList<Goods>();
		try {
			Connection con = DAO.getCon();

			PreparedStatement pstm = con
					.prepareStatement("select * from goods where price_common between ? and ?");
			pstm.setDouble(1, bottom);
			pstm.setDouble(2, top);
			ResultSet rs = pstm.executeQuery();
			while (rs.next() && rs.getRow() > 0) {
				Goods bs = new Goods();// 注意此对象声明位置
				bs.setGoodID(rs.getInt("goodID"));
				bs.setCategoryID(rs.getInt("categoryID"));
				bs.setSex(rs.getString("sex"));
				bs.setNumber(rs.getString("number"));
				bs.setName(rs.getString("name"));
				bs.setIntroduct(rs.getString("introduct"));
				bs.setMaterial(rs.getString("material"));
				bs.setPriceCommon(rs.getDouble("price"));
				bs.setPriceSpecial(rs.getDouble("price"));
				bs.setColor(rs.getString("color"));
				bs.setImage(rs.getString("image"));
				bs.setModelFilelocation("3DModelFile");
				bs.setId(bs.getGoodID());
				list.add(bs);
				// System.out.println("in DAO"+bs.getBrand());
			}
			pstm.close();
			rs.close();
			con.close();

		} catch (Exception e) {
			// TODO: handle exception
			// JOptionPane.showMessageDialog(null, "数据库异常");
			System.out.println("读取商品详情失败");
			e.printStackTrace();

		}
		return list;
	}


	/**
	 * 页面商品获取重要函数 重载，根据分类查 用categoryID，查找符合条件的所有goodID orderBY(排序依据)0:忽略此条件 1:销量
	 * 2:价格 3：人气 order（升/降序）0:忽略此条件 1:升序 2：降序
	 */
	public static List<Goods> queryGoods(String categoryID, int page, int orderBY,
			int order, int num) {

		String SQLstr = null;

		try {
			String sqlstrString = null;
			switch (orderBY) {
			case 1:
				sqlstrString = "order by number";
				break;
			case 2:
				sqlstrString = "order by price_common";
				break;
			case 3:
				sqlstrString = "order by number";
				break;

			default:
				sqlstrString = "";
				break;
			}
			switch (order) {
			case 1:
				sqlstrString += " ASC";
				break;
			case 2:
				sqlstrString += " DESC";
				break;

			default:
				sqlstrString += "";
				break;
			}


			if (categoryID.equals("0")) {
				// categoryID=null;
//				SQLstr = "select TOP" + num		//用于sql2005
//						+ "goodID,name,image from goods where "
//						+ "  (goodID NOT IN (select TOP " + start
//						+ " goodID from goods " + sqlstrString + ")) "
//						+ sqlstrString;
				 SQLstr="select  goodID,name,image,price from goods"+
				 sqlstrString+" LIMIT "+((page-1)*num)+","+num;//limit仅在MySQL中有定义
				// System.out.println("line261, categoryID:"+categoryID);
			} else {
				// categoryID="where categoryID = "+Integer.valueOf(categoryID);
//				SQLstr = "select TOP 6 goodID,name,image from goods where  categoryID = "
//						+ categoryID
//						+ " and  (goodID NOT IN (select TOP "
//						+ start
//						+ " goodID from goods "
//						+ sqlstrString
//						+ ")) "
//						+ sqlstrString;
				 SQLstr="select  goodID,name,image,price from goods where  categoryID = "+ categoryID+
						 sqlstrString+" LIMIT "+((page-1)*num)+","+num;//limit仅在MySQL中有定义
			}

			Connection conn = DAO.getCon(); // 获得数据连接
			// 创建PreparedStatement对象，并传递SQL语句

			PreparedStatement ps = conn.prepareStatement(SQLstr);
			ResultSet rs = ps.executeQuery(); // 执行SQL语句，获得查询结果集

			List<Goods> list = new ArrayList<Goods>();

			while (rs.next() && rs.getRow() > 0) { // 遍历结果集
				Goods bs = new Goods();// 注意此对象声明位置
				bs.setGoodID(rs.getInt(1));
				bs.setName(rs.getString(2));
				bs.setImage(rs.getString(3));
				bs.setPriceCommon(rs.getDouble("price"));
				bs.setId(bs.getGoodID());
				list.add(bs);
			}
			rs.close();
			ps.close();
			conn.close(); // 关闭连接
			return list;
		} catch (Exception ex) {
			System.out.println("goodsDAO407行，数据库异常" + ex.getMessage());
			return null;
		}
	}

	/**
	 * 根据名称模糊查询
	 */
	public static List<Goods> queryGoods(String goodName) {
		Goods bs = new Goods();
		try {
			Connection con = DAO.getCon();
			List<Goods> list = new ArrayList<Goods>();
			PreparedStatement pstm = con
					.prepareStatement("select * from goods where name like '%" + goodName + "%'");
			ResultSet rs = pstm.executeQuery();
			while (rs.next() && rs.getRow() > 0) {
				bs.setGoodID(rs.getInt("goodID"));
				bs.setCategoryID(rs.getInt("categoryID"));
				bs.setSex(rs.getString("sex"));
				bs.setNumber(rs.getString("number"));
				bs.setName(rs.getString("name"));
				bs.setIntroduct(rs.getString("introduct"));
				bs.setMaterial(rs.getString("material"));
				bs.setPriceCommon(rs.getDouble("price"));
				bs.setPriceSpecial(rs.getDouble("price"));
				bs.setColor(rs.getString("color"));
				bs.setImage(rs.getString("image"));
				bs.setModelFilelocation("3DModelFile");
				bs.setId(bs.getGoodID());
				list.add(bs);
			}
			pstm.close();
			rs.close();
			con.close();
			return list;

		} catch (Exception e) {
			// TODO: handle exception
			// JOptionPane.showMessageDialog(null, "数据库异常");
			System.out.println("读取失败");
			return null;
		}
	}

	/**
	 * @throws SQLException
	 *             返回选定分类商品总数(用于概览)
	 */
	public static int queryGoodsNumber(String categoryID) throws SQLException {
		int all = 0;
		// String sqlall="select goodID from goods order by goodID";
		String sqlall = "select count(*) from goods";

		Connection con = DAO.getCon();
		PreparedStatement pstm = con.prepareStatement(sqlall);
		ResultSet rs = pstm.executeQuery();
		while (rs.next() && rs != null) {
			// all=rs.getRow();
			all = rs.getInt(1);
		}
		pstm.close();
		rs.close();
		con.close();
		return all;

	}
	
	/**
	 * 新建商品记录
	 */
	public static boolean addGoods(Goods gs) {
		boolean success=false;
		try {
			Connection con = DAO.getCon();
			PreparedStatement pstm = con
					.prepareStatement("INSERT INTO goods (NAME,categoryID,material,color,price,image,3DModelFile,introduct,number) VALUES (?,?,?,?,?,?,?,?,?)");
			pstm.setString(1, gs.getName());
			pstm.setInt(2, gs.getCategoryID());
			pstm.setString(3, gs.getMaterial());
			pstm.setString(4, gs.getColor());
			pstm.setDouble(5, gs.getPriceCommon());
			pstm.setString(6, gs.getImage());
			pstm.setString(7, gs.getModelFilelocation());
			pstm.setString(8, gs.getIntroduct());
			pstm.setInt(9,Integer.valueOf( gs.getNumber()));
			
			int flag=pstm.executeUpdate();
			if(flag==1){
				success=true;
			}
			pstm.close();
			con.close();
			return success;

		} catch (Exception e) {
			// TODO: handle exception
			// JOptionPane.showMessageDialog(null, "数据库异常");
			System.out.println("新建商品失败");
			return false;
		}
	}
	/**
	 * 更改商品记录
	 */
	public static boolean updateGoods(Goods gs) {
		boolean success=false;
		try {
			Connection con = DAO.getCon();
			PreparedStatement pstm = con
					.prepareStatement("UPDATE goods SET NAME=?,categoryID=?,material=?,color=?,price=?,image=?,3DModelFile=?,introduct=?,number=? where goodID=?");
			pstm.setString(1, gs.getName());
			pstm.setInt(2, gs.getCategoryID());
			pstm.setString(3, gs.getMaterial());
			pstm.setString(4, gs.getColor());
			pstm.setDouble(5, gs.getPriceCommon());
			pstm.setString(6, gs.getImage());
			pstm.setString(7, gs.getModelFilelocation());
			pstm.setString(8, gs.getIntroduct());
			pstm.setInt(9,Integer.valueOf( gs.getNumber()));
			pstm.setInt(10,gs.getGoodID());
			
			int flag=pstm.executeUpdate();
			if(flag==1){
				success=true;
			}
			pstm.close();
			con.close();
			return success;

		} catch (Exception e) {
			// TODO: handle exception
			// JOptionPane.showMessageDialog(null, "数据库异常");
			System.out.println("更改商品失败");
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 删除商品记录
	 */
	public static boolean deleteGoods(int goodsID) {
		boolean success=false;
		try {
			Connection con = DAO.getCon();
			List<Goods> list = new ArrayList<Goods>();
			PreparedStatement pstm = con
					.prepareStatement("DELETE FROM goods WHERE goodID=?");
			pstm.setInt(1,goodsID);
			
			
			int flag=pstm.executeUpdate();
			if(flag==1){
				success=true;
			}
			pstm.close();
			con.close();
			return success;

		} catch (Exception e) {
			// TODO: handle exception
			// JOptionPane.showMessageDialog(null, "数据库异常");
			System.out.println("删除商品失败");
			return false;
		}
	}

}
