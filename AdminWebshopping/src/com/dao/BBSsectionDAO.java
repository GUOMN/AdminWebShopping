package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.model.BBSsection;
import com.model.BBStopic;
/**
 * 论坛版块操作
 * 包括查询版块列表，查询某一板块帖子
 * @author 郭梦男
 * 2017/2/25
 */
public class BBSsectionDAO {

	
	/**
	 * 查询版块列表
	 * @param null
	 */
	public static List<BBSsection> list_section() {
		List<BBSsection> list=new ArrayList<BBSsection>();
		
		try {
			Connection con = DAO.getCon();
			PreparedStatement pstm = con
					.prepareStatement("SELECT * FROM bbs_section");
			ResultSet rs=pstm.executeQuery();
			while (rs.next()) {
				BBSsection bs=new BBSsection();
				bs.setSection_name(rs.getString("section_name"));
				bs.setSectionID(rs.getInt("sectionID"));
				bs.setTop_topic(rs.getInt("top_topic"));
				list.add(bs);
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
	 * 查询版块内帖子
	 * @param sectionID
	 */
	public static List<BBStopic> list_topics(int sectionID) {
		List<BBStopic> list=new ArrayList<BBStopic>();
		try {
			Connection con = DAO.getCon();
			PreparedStatement pstm = con
					.prepareStatement("SELECT topicID,title FROM bbs_topic where sectionID=?");
			pstm.setInt(1, sectionID);
			ResultSet rs=pstm.executeQuery();
			
			while (rs.next()) {
				BBStopic bt=new BBStopic();
				bt.setTitle(rs.getString("title"));
				bt.setTopicID(rs.getInt("topicID"));
				list.add(bt);
				
			} 
			pstm.close();
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;

	}
}
