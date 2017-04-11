package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.model.News;
/**
 * 实现功能： 1.通过id查询新闻,即通过新闻id，查找新闻详情 
 * 2、列出所有新闻 3、新增记录 4、更新记录 5、删除记录
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
	 * 列出所有新闻
	 */
	public static List<News> listNews() throws Exception {

		List<News> result = new ArrayList<News>();

		Connection con = DAO.getCon();
		StringBuilder sb = new StringBuilder();
		sb.append("select * from News ");
		PreparedStatement ptmt = con.prepareStatement(sb.toString());// 预编译

		ResultSet rs = ptmt.executeQuery();

		// 遍历
		while (rs.next()) {
			News ns = new News();
			ns.setNewsId(rs.getInt("NewsID"));
			ns.setSortId(rs.getInt("SortId"));
			ns.setTitle(rs.getString("title"));
			ns.setNews_content(rs.getString("News_content"));
			ns.setKeywords(rs.getString("keywords"));
			ns.setDate(rs.getDate("date"));
			ns.setAuthor(rs.getString("author"));
			ns.setNumhit(rs.getInt("numhit"));
			ns.setDeldete(rs.getInt("deldete"));
			ns.setImageURL(rs.getString("ImageURL"));
			ns.setId(ns.getNewsId());

			result.add(ns);
		}
		con.close();
		ptmt.close();
		return result;
	}
	
	/**
	 * 新增新闻
	 */
	public static boolean addNews(News ns) throws Exception {

		boolean success=false;

		Connection con = DAO.getCon();
		StringBuilder sb = new StringBuilder();
		sb.append("insert into news (SortId,title,News_content,keywords,date,author,numhit,ImageURL) values (?,?,?,?,now(),?,?,?) ");
		PreparedStatement ptmt = con.prepareStatement(sb.toString());// 预编译
		ptmt.setInt(1, ns.getSortId());
		ptmt.setString(2, ns.getTitle());
		ptmt.setString(3, ns.getNews_content());
		ptmt.setString(4, ns.getKeywords());
		ptmt.setString(5, ns.getAuthor());
		ptmt.setInt(6, ns.getNumhit());
		ptmt.setString(7, ns.getImageURL());

		int flag=ptmt.executeUpdate();
		if(flag==1) success=true;
		
		con.close();
		ptmt.close();
		return success;
	}

	
	/**
	 * 删除新闻
	 */
	public static boolean deleteNews(int id) throws Exception {

		boolean success=false;

		Connection con = DAO.getCon();
		StringBuilder sb = new StringBuilder();
		sb.append("delete from news where NewsID=?");
		PreparedStatement ptmt = con.prepareStatement(sb.toString());// 预编译
		ptmt.setInt(1, id);
		int flag=ptmt.executeUpdate();
		if(flag==1) success=true;
		
		con.close();
		ptmt.close();
		return success;
	}

	
	/**
	 * 更改新闻内容
	 */
	public static boolean   updateNews(News ns) throws Exception {

		boolean success=false;

		Connection con = DAO.getCon();
		StringBuilder sb = new StringBuilder();
		sb.append("update news set SortId=?,title=?,News_content=?,keywords=?,date=now(),author=?,numhit=?,ImageURL=? where NewsID=?  ");
		PreparedStatement ptmt = con.prepareStatement(sb.toString());// 预编译

		ptmt.setInt(1, ns.getSortId());
		ptmt.setString(2, ns.getTitle());
		ptmt.setString(3, ns.getNews_content());
		ptmt.setString(4, ns.getKeywords());
		ptmt.setString(5, ns.getAuthor());
		ptmt.setInt(6, ns.getNumhit());
		ptmt.setString(7, ns.getImageURL());
		ptmt.setInt(8, ns.getNewsId());
		
		int flag=ptmt.executeUpdate();
		if(flag==1) success=true;
		
		con.close();
		ptmt.close();
		return success;
	}


	/**
	 * 名称模糊查询
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
