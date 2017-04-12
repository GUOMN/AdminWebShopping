package com.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.model.BBStopic;
/**
 * 论坛帖子的DAO
 * 包括按ID查询帖子，按标题查询帖子
 * 其他条件筛选
 * 删除帖子，增加帖子
 * 当用户浏览、回复帖子时修改对应表
 * @author 郭梦男
 */
public  class BBStopicDAO {

	
	
	/**
	 * 功能： 根据条件返回行内容 （多个）
	 * 1、标题模糊2、发帖人姓名3、发表时间排序4、浏览量排序5、topicID
	 */
	public static ArrayList<BBStopic> query_topics(String parameter, int function_code) {
		try {
			Connection conn = DAO.getCon(); // 获得数据连接
			// 创建PreparedStatement对象，并传递SQL语句
			String SQLstr;
			switch (function_code) {
			case 1://标题模糊查询
				SQLstr = "SELECT topicID FROM bbs_topic  WHERE title LIKE \"?\"";
				break;
			case 2://根据发帖人姓名
				SQLstr = "SELECT topicID FROM bbs_topic JOIN `user` ON user.`userID`=bbs_topic.`userID`  WHERE user.`name` LIKE \"% ? %\"";
				break;
			case 3://根据发表时间排序
				SQLstr = "select topicID from bbs_topic order by publishTime";
				break;
			case 4://根据浏览量排序
				SQLstr = "select topicID from bbs_topic order by view_count";
				break;
			case 5://根据浏览量排序
				SQLstr = "select topicID from bbs_topic where topicID=?";
				break;
			default:
				SQLstr = "SELECT topicID FROM bbs_topic";
				break;
			}
//			System.out.println(SQLstr);
			PreparedStatement ps = conn.prepareStatement(SQLstr);
			if(function_code==1||function_code==2||function_code==5){
				ps.setString(1, parameter);
			}
			ResultSet rs = ps.executeQuery(); // 执行SQL语句，获得查询结果集
			ArrayList<BBStopic> list = new ArrayList<BBStopic>();
			while (rs.next()) { // 遍历结果集
				list.add(BBStopicDAO.queryTopicID(rs.getInt(1)));
			}
			rs.close();
			ps.close();
			conn.close(); // 关闭连接
			return list;
		} catch (Exception ex) {
			// JOptionPane.showMessageDialog(null, "数据库异常！\n" +
			// ex.getMessage());
			System.out.println("筛选帖子DAO异常" + ex.getMessage());
			return null;
		}
	}
	/**
	 * 无参查询，列出所有帖子
	 * @return
	 */
	public static List<BBStopic> list_topics() {
		List<BBStopic> list=new ArrayList<BBStopic>();
		try {
			Connection con = DAO.getCon();
			PreparedStatement pstm = con
					.prepareStatement("SELECT topicID FROM bbs_topic");
			ResultSet rs=pstm.executeQuery();
			
			while (rs.next()) {
				list.add(BBStopicDAO.queryTopicID(rs.getInt("topicID")));
				
			} 
			pstm.close();
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;

	}
	

	/**
	 * 根据topID查询行内容
	 */
	public static BBStopic queryTopicID(int ID) {
		BBStopic bs = new BBStopic();
		try {
			Connection con = DAO.getCon();
			PreparedStatement pStatement = con
					.prepareStatement("SELECT bbs_topic.*,user.`name` FROM bbs_topic JOIN USER ON bbs_topic.`userID`=user.`userID` WHERE bbs_topic.topicID=?");
			pStatement.setInt(1, ID);
			ResultSet rs = pStatement.executeQuery();
			while (rs.next() && rs.getRow() > 0) {
				bs.setTopicID(rs.getInt("topicID"));
				bs.setTitle(rs.getString("title"));
				bs.setUserID(rs.getInt("userID"));
				bs.setSectionID(rs.getInt("sectionID"));
				bs.setModifyTimeDate(rs.getDate("modifyTime"));
				bs.setPublishTimeDate(rs.getDate("publishTime"));
				bs.setContent(rs.getString("content"));
				bs.setOn_the_top(rs.getBoolean("on_the_top"));
				bs.setView_count(rs.getInt("view_count"));
				bs.setNum_of_reply(rs.getInt("num_of_reply"));
				bs.setUser_nameString(rs.getString("name"));
				bs.setId(bs.getTopicID());
			}
			pStatement.close();
			rs.close();
			con.close();
			return bs;

		} catch (Exception e) {
			// TODO: handle exception
			// JOptionPane.showMessageDialog(null, "数据库异常");
			System.out.println("读取帖子（单条）失败");
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 删除指定topic，及其附属reply
	 * 采用数据库触发器，Java只删除主贴，评论自动删除
	 * 自动判断当前用户是否为帖子发表者
	 */
	public static boolean delete_topic(int topicID){
		boolean success=false;
		
		try {
			Connection con = DAO.getCon();
//			PreparedStatement pstm = con
//					.prepareStatement("DELETE bbs_topic.*,bbs_reply.*  FROM bbs_topic JOIN bbs_reply " +
//							"ON bbs_topic.`topicID` = bbs_reply.`topicID` WHERE (bbs_topic.`topicID`=? and bbs_topic.`userID`=?)");
			PreparedStatement pstm = con
					.prepareStatement("DELETE FROM bbs_topic WHERE bbs_topic.`topicID`=?");
			pstm.setInt(1, topicID);

			int flag = pstm.executeUpdate();
			if (flag > 0) {
				success=true;
			}
			pstm.close();
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return success;
		
	}
	/**
	 * 获取用户头像和用户名，用于加载论坛
	 * 返回用户名，并执行头像检出，头像url为默认路径+userID.jpg
	 */
	public static String bbs_user_info(int ID) {
		String name="";
		try {
			Connection con = DAO.getCon();
			PreparedStatement pStatement = con
					.prepareStatement("select loginName from user where userID=?");
			pStatement.setInt(1, ID);
			ResultSet rs = pStatement.executeQuery();
			while (rs.next()) {
				name=rs.getString("loginName");
			}
			UserDAO.readIcon(String.valueOf(ID));
			pStatement.close();
			rs.close();
			con.close();
			return name;

		} catch (Exception e) {
			// TODO: handle exception
			// JOptionPane.showMessageDialog(null, "数据库异常");
			System.out.println("读取失败");
			return null;
		}

	}


}
