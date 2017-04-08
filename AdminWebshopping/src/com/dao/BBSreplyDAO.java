package com.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.model.BBSreply;
import com.mysql.jdbc.Util;
/**
 * 对论坛回复数据表的操作 
 * 要能列出所有回复 (针对给定topicID)
 * 单条记录删除
 * 单条记录插入
 * @author 郭梦男
 */
public class BBSreplyDAO {


	// 新增回复
	public static boolean insert(BBSreply m) {
		boolean issuccess=false;
		try {
			Connection con = DAO.getCon();
			PreparedStatement pstm = con
					.prepareStatement("insert into bbs_reply(topicID,userID,title,content,modifyTime,publishTime) "
							+ "values(?,?,?,?,localtime(),localtime())");

			pstm.setInt(1, m.getTopicID());
			pstm.setInt(2, m.getUserID());
			pstm.setString(3, "undefined");
			pstm.setString(4, m.getContent());


			int flag = pstm.executeUpdate();
			if (flag > 0) {
				
				if( BBStopicDAO.count_reply( m.getTopicID())){//计数器+1
					issuccess=true;
				}
			} 
			pstm.close();
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return issuccess;
	}

	/**
	 * 通过topicID/title、userID/name、reply.title、时间段,获得当前ReplyID
	 *  参数function_code用于指示parameter所代表的内容
	 *  1、topicID 2、topicTitle 3、reply_userID 4、reply_userNAme 5、reply_title 6、根据时间排序
	 */
	public static ArrayList<BBSreply> querry_reply(String parameter, int function_code) {
		try {
			Connection conn = DAO.getCon(); // 获得数据连接
			// 创建PreparedStatement对象，并传递SQL语句
			String SQLstr="";
			switch (function_code) {
			case 1://根据topicID查询
				SQLstr = "select replyID from bbs_reply where topicID = ?";
				break;
			case 2://根据topic_title查询
				SQLstr = "SELECT replyID FROM bbs_reply JOIN bbs_topic ON bbs_reply.topicID=bbs_topic.topicID WHERE bbs_topic.`title` LIKE \"?\"";
				break;
			case 3://根据reply_userID查询
				SQLstr = "select replyID from bbs_reply where userID = ?";
				break;
			case 4://根据reply_username查询
				SQLstr = "SELECT replyID FROM bbs_reply JOIN USER ON user.`userID`=bbs_reply.`userID` WHERE user.`name` LIKE\"%?%\"";
				break;
			case 5://根据reply_title查询
				SQLstr = "select replyID from bbs_reply where title LIKE\"%?%\"";
				break;
			case 6://根据回复发表时间筛选
				SQLstr = "select replyID from bbs_reply where publishTime = ?";
				break;
			default:
				SQLstr = "select replyID from bbs_reply";
				break;
			}

			PreparedStatement ps = conn.prepareStatement(SQLstr);
			ps.setString(1, parameter);
			ResultSet rs = ps.executeQuery(); // 执行SQL语句，获得查询结果集
			ArrayList<BBSreply> list = new ArrayList<BBSreply>();
			
			while (rs.next()) { // 遍历结果集
				BBSreply bs = new BBSreply();
				bs = BBSreplyDAO.queryReplyID(rs.getInt(1));
				list.add(bs);
			}
			rs.close();
			ps.close();
			conn.close(); // 关闭连接
			return list;
		} catch (Exception ex) {
			// JOptionPane.showMessageDialog(null, "数据库异常！\n" +
			// ex.getMessage());
			System.out.println("评论列表读取失败" + ex.getMessage());
			return null;
		}
	}

	/**
	 * 返回某一行所有信息（以对象形式），根据replyID
	 */
	public static BBSreply queryReplyID(int ID) {
		BBSreply bs = new BBSreply();
		try {
			Connection con = DAO.getCon();
			PreparedStatement pStatement = con
					.prepareStatement("SELECT title,replyID,topicID,bbs_reply.userID,content,modifyTime,publishTime,user.`name`  FROM bbs_reply JOIN USER ON user.`userID`=bbs_reply.`userID` WHERE replyID = ?");
			pStatement.setInt(1, ID);
			ResultSet rs = pStatement.executeQuery();
			while (rs.next() && rs.getRow() > 0) {
				bs.setTitle(rs.getString("title"));
				bs.setReplyID(rs.getInt("replyID"));
				bs.setTopicID(rs.getInt("topicID"));
				bs.setUserID(rs.getInt("userID"));
				bs.setContent(rs.getString("content"));
				bs.setModifyTime((java.util.Date)rs.getDate("modifyTime"));
				bs.setPublishTimeDate((java.util.Date)rs.getDate("publishTime"));
				bs.setUser_nameString(rs.getString("name"));
				try {
					UserDAO.readIcon(String.valueOf(bs.getUserID()));
					bs.setIconURL("user_icon/real/"+bs.getUserID()+".jpg");
				} catch (Exception e) {
					// TODO: handle exception
				}
//				if(UserDAO.readIcon(String.valueOf(bs.getUserID()))){
//					
//				}
			}
			pStatement.close();
			rs.close();
			con.close();
			return bs;

		} catch (Exception e) {
			// TODO: handle exception
			// JOptionPane.showMessageDialog(null, "数据库异常");
			System.out.println("读取评论失败");
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 删除指定reply
	 * 自动判断当前用户是否为评论发表者
	 */
	public static boolean delete_reply(int replyID,int userID){
		boolean success=false;
		
		try {
			Connection con = DAO.getCon();
//			PreparedStatement pstm = con
//					.prepareStatement("DELETE bbs_topic.*,bbs_reply.*  FROM bbs_topic JOIN bbs_reply " +
//							"ON bbs_topic.`topicID` = bbs_reply.`topicID` WHERE (bbs_topic.`topicID`=? and bbs_topic.`userID`=?)");
			PreparedStatement pstm = con
					.prepareStatement("DELETE FROM bbs_reply WHERE (bbs_reply.`replyID`=? and bbs_reply.`userID`=?)");
			pstm.setInt(1, replyID);
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
}