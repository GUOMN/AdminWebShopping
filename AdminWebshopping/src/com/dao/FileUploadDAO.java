package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.model.File_3DModel;
/**
 * 用于处理3D打印模型文件上传，（另：用户头像上传的数据库操作位于USERDAO.java中） 用于浏览用户所有已上传的模型（概览）
 * 用于展示某一个选中模型（具体信息，文件返回）
 * 
 * @author 郭梦男 2016-9-25
 */
public class FileUploadDAO {


	/**
	 * 查询所有该用户上传的模型（概览）
	 * 
	 * @param userID
	 * @return List<File_3DModel>
	 */
	public List<File_3DModel> querryall(int userID) {
		List<File_3DModel> list = new ArrayList<File_3DModel>();
		String sqlall = "select * from ModelFile where UserID = ?";
		Connection con = DAO.getCon();
		try {
			PreparedStatement pstm = con.prepareStatement(sqlall);
			pstm.setInt(1, userID);
			ResultSet rs = pstm.executeQuery();
			while (rs.next() && rs.getRow() > 0) {
				File_3DModel file = new File_3DModel();
				file.setLocation(rs.getString("Location"));
				file.setModelName(rs.getString("ModelName"));
				file.setUploadTime(rs.getDate("UploadTime").toString());
				file.setUserID(rs.getInt("UserID"));
				file.setDescription(rs.getString("description"));
				list.add(file);
			}
			pstm.close();
			rs.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * 根据模型ID查询文件路径
	 * 
	 * @param ModelID
	 * @return String（文件名）
	 */
	public String getLocation(String ModelID) {
		String location = null;
		String sqlall = "select Location from ModelFile where ModelID = ?";
		Connection con = DAO.getCon();
		try {
			PreparedStatement pstm = con.prepareStatement(sqlall);
			pstm.setString(1, ModelID);
			ResultSet rs = pstm.executeQuery();
			while (rs.next() && rs.getRow() > 0) {
				location = rs.getString("Location");
			}
			pstm.close();
			rs.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return location;
	}

	public boolean insert(File_3DModel file) {
		boolean success = false;
		String sqlall = "insert into ModelFile (UserID,ModelName,Location,UploadTime,description) VALUES (?,?,?,now(),?)";
		Connection con = DAO.getCon();
		try {
			PreparedStatement pstm = con.prepareStatement(sqlall);
			pstm.setInt(1, file.getUserID());
			pstm.setString(2, file.getModelName());
			pstm.setString(3, file.getLocation());
			pstm.setString(4, file.getDescription());
			int f = pstm.executeUpdate();
			if (f > 0) {
				success = true;
			}
			pstm.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return success;

	}

}
