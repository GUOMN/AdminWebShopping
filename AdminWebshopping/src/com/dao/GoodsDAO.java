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

	/**
	 * 分页获取所有商品（只返回一页）
	 */
	public List getPGoods(String strcurrentP, String strcurrentG, String goWhich)
			throws SQLException {
		// desc降序，asc升序
		String sqlall = "select * from goods order by goodID desc";
		SuperDao s1 = new SuperDao();
		s1.setDaoPage(sqlall, null, strcurrentP, strcurrentG, goWhich);// 分页
		int currentP = s1.getDaoPage().getCurrentP();// 页数
		int top1 = s1.getDaoPage().getPerR();// 每页显示的记录数
		int top2 = (currentP - 1) * top1;// 总记录数
		String sqlsub = "";
		if (currentP == 1)
			sqlsub = "select top " + top1
					+ " goodID from goods order by goodID desc";
		else
			sqlsub = "select top "
					+ top1
					+ " goodID from goods where (goodID < (select min(goodID) "
					+ "from (select top "
					+ top2
					+ " goodID from goods order by goodID desc) as minv)) order by goodID desc";
		//
		// List alllist=getList(sqlsub,null);//总返回表
		// List goodslist=divide(alllist,top1);//返回表的表，元素是页

		List alllist = queryGoods(0, 10000000);// 根据参数查询一页数目的goods
		List goodslist = divide(alllist, top1);// 返回表的表，元素是行（每行3个goods元素）
		return alllist;
	}

	private List divide(List list, int perR) {
		// 取到所有组分页,元素通过queryGoods（9，“任意字符串”）返回
		List goodslist = null;
		if (list != null) {
			goodslist = new ArrayList();
			/* ①首先补齐长度 */
			int blank = perR - list.size(); // 因为每页显示perR条记录，所以list的长度只能等于perR或小于perR
			if (blank > 0) { // 若list的长度小于perR，则向list中存放blank个null值，将list长度补齐到perR
				for (int i = 0; i < blank; i++)
					list.add(null);
			}
			/* ②然后进行划分 */
			for (int i = 0; i < 2; i++) { // 平均分成3份(行数)
				List temp = new ArrayList();
				for (int j = 0; j < 3; j++) { // 将每份中的元素存储到temp中
					temp.add(list.get(3 * i + j));
				}
				goodslist.add(temp); // 存储temp到medialist中
			}
		}
		return goodslist;
	}

	// 可以通过goodID返回行(用于显示商品详情页)
	public Goods queryGoods(int ID) {
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
	 * 用价格区间筛选 sql =
	 * "Select * from [sheet1$] Where 出库日期 between  #2005-1-4# and #2005-1-10#"
	 */
	public List<Goods> queryGoods(double bottom, double top) {
		List<Goods> list = new ArrayList<Goods>();
		try {
			Connection con = DAO.getCon();

			PreparedStatement pstm = con
					.prepareStatement("select goodID,categoryID,sex,number,name,introduct,material,attachment,"
							+ "price_common,price_special,color,handinventory,del,image "
							+ "from goods where price_common between ? and ?");
			pstm.setDouble(1, bottom);
			pstm.setDouble(2, top);
			ResultSet rs = pstm.executeQuery();
			while (rs.next() && rs.getRow() > 0) {
				Goods bs = new Goods();// 注意此对象声明位置
				bs.setGoodID(rs.getInt(1));
				bs.setCategoryID(rs.getInt(2));
				bs.setSex(rs.getString(3));
				bs.setNumber(rs.getString(4));
				bs.setName(rs.getString(5));
				bs.setIntroduct(rs.getString(6));
				bs.setMaterial(rs.getString(7));
				bs.setAttachment(rs.getString(8));
				bs.setPriceCommon(rs.getDouble(9));
				bs.setPriceSpecial(rs.getDouble(10));
				bs.setColor(rs.getString(11));
				bs.setHandinventory(rs.getInt(12));
				bs.setDel(rs.getString(13));
				bs.setImage(rs.getString(14));
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
	public List<Goods> queryGoods2(String categoryID, int page, int orderBY,
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
	public List<Goods> queryGoods(String goodName) {
		Goods bs = new Goods();
		try {
			Connection con = DAO.getCon();
			List<Goods> list = new ArrayList<Goods>();
			PreparedStatement pstm = con
					.prepareStatement("select goodID,categoryID,sex,number,name,introduct,material,attachment,"
							+ "price_common,price_special,color,handinventory,del,image "
							+ "from goods where name like '%" + goodName + "%'");
			ResultSet rs = pstm.executeQuery();
			while (rs.next() && rs.getRow() > 0) {
				bs.setGoodID(rs.getInt(1));
				bs.setCategoryID(rs.getInt(2));
				bs.setSex(rs.getString(3));
				bs.setNumber(rs.getString(4));
				bs.setName(rs.getString(5));
				bs.setIntroduct(rs.getString(6));
				bs.setMaterial(rs.getString(7));
				bs.setAttachment(rs.getString(8));
				bs.setPriceCommon(rs.getDouble(9));
				bs.setPriceSpecial(rs.getDouble(10));
				bs.setColor(rs.getString(11));
				bs.setHandinventory(rs.getInt(12));
				bs.setDel(rs.getString(13));
				bs.setImage(rs.getString(14));
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
	 * 复合条件筛选，用if代码块完成不同条件数目的SQL语句组合
	 */
	public List<Goods> queryGoods(ArrayList<Object> inList) {
		List<Goods> list = new ArrayList<Goods>();
		return list;
	}

	/**
	 * @throws SQLException
	 *             返回选定分类商品总数(用于概览)
	 */
	public int queryGoodsNumber(String categoryID) throws SQLException {
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

}
