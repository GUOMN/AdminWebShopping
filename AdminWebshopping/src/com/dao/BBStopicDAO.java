package com.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

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
			while (rs.next() && rs.getRow() > 0) { // 遍历结果集
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
	 * 用户查看表计数器修改
	 * 用户查看+1
	 */
	public static boolean count_view(int topicID){
		boolean success=false;
		try {
			Connection con = DAO.getCon();
			String SQLstr = "UPDATE bbs_topic SET view_count = view_count+1 WHERE topicID = ?";
			PreparedStatement pstm = con.prepareStatement(SQLstr);
			pstm.setInt(1, topicID);


			int flag = pstm.executeUpdate();
			if (flag ==1) {
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
	 * 用户跟帖表计数器修改
	 * 在dao层中调用
	 * 用户跟帖+1
	 */
	public static boolean count_reply(int topicID){
		boolean success=false;
		try {
			Connection con = DAO.getCon();
			String SQLstr = "UPDATE bbs_topic SET num_of_reply = num_of_reply+1 WHERE topicID = ?";
			PreparedStatement pstm = con.prepareStatement(SQLstr);
			pstm.setInt(1, topicID);


			int flag = pstm.executeUpdate();
			if (flag ==1) {
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
	 * 插入操作
	 */
	public static boolean insert(BBStopic m) {
		boolean success=false;
		try {
			Connection con = DAO.getCon();
			PreparedStatement pstm = con
					.prepareStatement("insert into bbs_topic(title,userID,sectionID,modifyTime,publishTime,content,on_the_top) "
							+ "values(?,?,?,localtime(),localtime(),?,?)");

			pstm.setString(1, m.getTitle());
			pstm.setInt(2, m.getUserID());
			pstm.setInt(3, m.getSectionID());
			pstm.setString(4, m.getContent());
			pstm.setBoolean(5, m.isOn_the_top());


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
				try {
					UserDAO.readIcon(String.valueOf(bs.getUserID()));
					bs.setIconURL("user_icon/real/"+bs.getUserID()+".jpg");
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println(bs.getUserID()+",此用户ID无法获取头像");
				}
			
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
	public static boolean delete_topic(int topicID,int userID){
		boolean success=false;
		
		try {
			Connection con = DAO.getCon();
//			PreparedStatement pstm = con
//					.prepareStatement("DELETE bbs_topic.*,bbs_reply.*  FROM bbs_topic JOIN bbs_reply " +
//							"ON bbs_topic.`topicID` = bbs_reply.`topicID` WHERE (bbs_topic.`topicID`=? and bbs_topic.`userID`=?)");
			PreparedStatement pstm = con
					.prepareStatement("DELETE FROM bbs_topic WHERE (bbs_topic.`topicID`=? and bbs_topic.`userID`=?)");
			pstm.setInt(1, topicID);
			pstm.setInt(2, userID);

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
