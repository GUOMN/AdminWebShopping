package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.model.NewsSort;
/**
 * 实现功能： 1.获取所有分类信息,即获取所有的分类名（SortName）、分类id（SortId） 2.根据分类名（SortName
 * ）,获取相关的分类id（SortId） 3.根据分类id（SortId），获取该分类的新闻标题（title），概览（keywords）
 */
public class NewsSortDAO {


	/**
	 * 查询全部分类 获取所有分类信息,即获取所有的分类名（SortName）、分类id（SortId）
	 */
	public List<NewsSort> queryALL() {
		List<NewsSort> list = new ArrayList<NewsSort>();
		try {
			Connection con = DAO.getCon();
			PreparedStatement pStatement = con
					.prepareStatement("select SortId,SortName from NewsSort");
			ResultSet rs = pStatement.executeQuery();
			while (rs.next() && rs.getRow() > 0) {
				NewsSort ns = new NewsSort();
				ns.setSortId(rs.getInt(1));
				ns.setSortName(rs.getString(2));

				list.add(ns);
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

	/**
	 * 根据分类名查询id 根据分类名（SortName ）,获取相关的分类id（SortId）
	 * 
	 * @param name
	 * @return
	 */
	public int querrySortId(String name) {
		try {
			int SortId = 0;
			Connection con = DAO.getCon();
			PreparedStatement pStatement = con
					.prepareStatement("select SortId from NewsSort where SortName="
							+ name);
			ResultSet rs = pStatement.executeQuery();

			while (rs.next() && rs.getRow() > 0) {
				SortId = rs.getInt(1);
			}

			pStatement.close();
			rs.close();
			con.close();
			return SortId;

		} catch (Exception e) {
			// TODO: handle exception
			// JOptionPane.showMessageDialog(null, "数据库异常");
			System.out.println("读取失败");
			return 0;
		}

	}

}
