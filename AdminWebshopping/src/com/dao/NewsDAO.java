package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.model.News;
/**
 * 实现功能： 1.通过id查询新闻,即通过新闻id，查找新闻详情 2.优化查询方法，添加过滤条件（如按照标题title查询） 3.模糊查询
 * ，例如，根据标题title模糊查询 4.进一步优化后的查询方法
 * 5.根据分类id（SortId），获取该分类的新闻标题（title），概览（keywords）
 * 
 * @author MYJ
 * @param id
 * @return news
 * 
 */
public class NewsDAO {


	/**
	 * 通过id查询新闻
	 * 
	 * @param id
	 * @return
	 */
	public News queryNews(int id) {

		try {
			Connection con = DAO.getCon();
			PreparedStatement pstm = con.prepareStatement("select * from news where NewsId =?");
			pstm.setInt(1, id);

			ResultSet rs = pstm.executeQuery();
			News ns = new News();
			if(rs.next()){
				
				ns.setNewsId(id);
				ns.setSortId(rs.getInt("SortId"));
				ns.setTitle(rs.getString("title"));
				ns.setNews_content(rs.getString("News_content"));
				ns.setKeywords(rs.getString("keywords"));
				ns.setDate(rs.getDate("date"));
				ns.setAuthor(rs.getString("author"));
				ns.setNumhit(rs.getInt("numhit"));
				ns.setDeldete(rs.getInt("deldete"));
				ns.setImageURL(rs.getString("ImageURL"));
			}
			pstm.close();
			rs.close();
			con.close();
			return ns;

		} catch (Exception e) {

			// JOptionPane.showMessageDialog(null, "数据库异常");
			System.out.println("读取失败\n");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 优化查询方法，添加过滤条件（如按照标题查询）
	 * 
	 * @param MYJ
	 * @return
	 * @throws Exception
	 */
	public List<News> query(String name) throws Exception {

		List<News> result = new ArrayList<News>();

		Connection con = DAO.getCon();
		StringBuilder sb = new StringBuilder();
		sb.append("select * from News ");

		sb.append(" where title=? ");

		PreparedStatement ptmt = con.prepareStatement(sb.toString());// 预编译

		ptmt.setString(1, name);// 传参

		ResultSet rs = ptmt.executeQuery();

		// 遍历
		while (rs.next()) {
			News ns = new News();
			ns.setNewsId(rs.getInt("NewsId"));
			ns.setSortId(rs.getInt("SortId"));
			ns.setTitle(rs.getString("title"));
			ns.setNews_content(rs.getString("News_content"));
			ns.setKeywords(rs.getString("keywords"));
			ns.setDate(rs.getDate("date"));
			ns.setAuthor(rs.getString("author"));
			ns.setNumhit(rs.getInt("numhit"));
			ns.setDeldete(rs.getInt("deldete"));
			ns.setImageURL(rs.getString("ImageURL"));

			result.add(ns);
		}
		return result;
	}

	/**
	 * 模糊查询
	 * 
	 * @param name
	 * @return
	 * @throws Exception
	 */

	public List<News> query1(String name) throws Exception {

		List<News> result = new ArrayList<News>();

		Connection con = DAO.getCon();
		StringBuilder sb = new StringBuilder();
		sb.append("select * from News ");

		sb.append(" where title like ? ");

		PreparedStatement ptmt = con.prepareStatement(sb.toString());// 预编译

		ptmt.setString(1, "%" + name + "%"); // %是通配符

		ResultSet rs = ptmt.executeQuery();
		// 遍历
		while (rs.next()) {
			News ns = new News();
			ns.setNewsId(rs.getInt("NewsId"));
			ns.setSortId(rs.getInt("SortId"));
			ns.setTitle(rs.getString("title"));
			ns.setNews_content(rs.getString("News_content"));
			ns.setKeywords(rs.getString("keywords"));
			ns.setDate(rs.getDate("date"));
			ns.setAuthor(rs.getString("author"));
			ns.setNumhit(rs.getInt("numhit"));
			ns.setDeldete(rs.getInt("deldete"));
			ns.setImageURL(rs.getString("ImageURL"));

			result.add(ns);
		}
		return result;
	}

	/**
	 * 进一步优化后的查询方法
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<News> query(List<Map<String, Object>> params) throws Exception {
		List<News> result = new ArrayList<News>();

		Connection con = DAO.getCon();
		StringBuilder sb = new StringBuilder();

		sb.append("select * from News where 1=1 ");// 小技巧where 1=1

		// 先判断集合是否为空，然后遍历集合
		if (params != null && params.size() > 0) {
			for (int i = 0; i < params.size(); i++) {
				Map<String, Object> map = params.get(i);
				sb.append(" and " + map.get("name") + " " + map.get("rela")
						+ " " + map.get("value") + " ");
			}
		}

		PreparedStatement ptmt = con.prepareStatement(sb.toString());// 预编译

		System.out.println(sb.toString()); // 打印SQL语句

		ResultSet rs = ptmt.executeQuery();

		while (rs.next()) {
			News ns = new News();
			ns.setNewsId(rs.getInt("NewsId"));
			ns.setSortId(rs.getInt("SortId"));
			ns.setTitle(rs.getString("title"));
			ns.setNews_content(rs.getString("News_content"));
			ns.setKeywords(rs.getString("keywords"));
			ns.setDate(rs.getDate("date"));
			ns.setAuthor(rs.getString("author"));
			ns.setNumhit(rs.getInt("numhit"));
			ns.setDeldete(rs.getInt("deldete"));
			ns.setImageURL(rs.getString("ImageURL"));

			result.add(ns);
		}
		return result;
	}

	/**
	 * 根据分类id（SortId），获取该分类的新闻标题（title），概览（keywords）
	 * 
	 * @author MYJ
	 */
	public List<News> queryNews1(int SortId) {
		List<News> list = new ArrayList<News>();

		try {
			Connection con = DAO.getCon();
			PreparedStatement pstm=null;
			if (SortId==0) {
				 pstm = con
						.prepareStatement("select * from News");
			}else {
				 pstm = con
						.prepareStatement("select * from News where SortId=?");
			}
			
			pstm.setInt(1, SortId);

			ResultSet rs = pstm.executeQuery();

			while (rs.next() && rs.getRow() > 0) {
				News ns = new News();

				ns.setSortId(SortId);
				ns.setNewsId(rs.getInt("NewsId"));
				ns.setTitle(rs.getString("title"));
				ns.setNews_content(rs.getString("News_content"));
				ns.setKeywords(rs.getString("keywords"));
				ns.setDate(rs.getDate("date"));
				ns.setAuthor(rs.getString("author"));
				ns.setNumhit(rs.getInt("numhit"));
				ns.setDeldete(rs.getInt("deldete"));
				ns.setImageURL(rs.getString("ImageURL"));
				list.add(ns);
			}
			pstm.close();
			rs.close();
			con.close();
			return list;

		} catch (Exception e) {

			// JOptionPane.showMessageDialog(null, "数据库异常");
			System.out.println("读取失败\n");
			e.printStackTrace();
			return null;
		}
	}
}
