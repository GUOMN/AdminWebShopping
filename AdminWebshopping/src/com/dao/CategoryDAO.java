package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.model.Category;
/**
 * 商品分类信息的dao
 * @author 郭梦男
 *
 */
public class CategoryDAO {
	/**
	 * 功能：获取所有分类，用于构建分类列表 根据分类name获取分类ID
	 * 无用类，系统暂不包含该功能
	 */
	// 返回所有信息（以对象形式）
	public List<Category> queryALL() {
		List<Category> list = new ArrayList<Category>();
		try {
			Connection con = DAO.getCon();
			PreparedStatement pStatement = con
					.prepareStatement("select categoryID,categoryName,[level],pid from category");
			ResultSet rs = pStatement.executeQuery();
			while (rs.next() && rs.getRow() > 0) {
				Category bs = new Category();
				bs.setCategoryID(rs.getInt(1));
				bs.setCategoryName(rs.getString(2));
				bs.setLevel(rs.getInt(3));
				bs.setPid(rs.getInt(4));
				list.add(bs);
			}
			pStatement.close();
			rs.close();
			con.close();
			return list;

		} catch (Exception e) {
			// TODO: handle exception
			// JOptionPane.showMessageDialog(null, "数据库异常");
			System.out.println("读取失败line39");
			return null;
		}
	}

	public int querryCategoryID(String name) {
		try {
			int categoryID = 0;
			Connection con = DAO.getCon();
			PreparedStatement pStatement = con
					.prepareStatement("select categoryID from category where categoryName="
							+ name);
			ResultSet rs = pStatement.executeQuery();
			while (rs.next() && rs.getRow() > 0) {
				categoryID = rs.getInt(1);
			}
			pStatement.close();
			rs.close();
			con.close();
			return categoryID;

		} catch (Exception e) {
			// TODO: handle exception
			// JOptionPane.showMessageDialog(null, "数据库异常");
			System.out.println("读取失败");
			return 0;
		}

	}

}
