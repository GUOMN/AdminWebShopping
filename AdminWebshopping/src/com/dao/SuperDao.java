package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.toolsbean.PageBar;
/**
 * 资源类，用于信息分页，未用到
 */
public class SuperDao {

	private PageBar daoPage = new PageBar();
	private int daoPerR = daoPage.getPerR();
	private int daoPerP = daoPage.getPerP();

	public void setDaoPerR(int daoPerR) {
		this.daoPerR = daoPerR;
	}

	public void setDaoPerP(int daoPerP) {
		this.daoPerP = daoPerP;
	}

	public void setDaoPage(String sql, Object[] params, String strcurrentP,
			String strcurrentG, String goWhich) throws SQLException {

		daoPage.setAllR(getAllR(sql, params));// setallR所有记录数(int)(PageBar中定义),getAllR本类定义，表示数据库查得记录数
		daoPage.setPerR(daoPerR);
		daoPage.setPerP(daoPerP);
		daoPage.setPageBar(strcurrentP, strcurrentG, goWhich);
	}

	private int getAllR(String sql, Object[] params) throws SQLException {
		// 结果集行数
		int allR = 0;
		// DB mydb=new DB();
		// mydb.doPstm(sql, params);
		// ResultSet rs=mydb.getRs();
		// if(rs!=null&&rs.next()){
		// //rs.last();
		// allR=rs.getRow();
		// rs.close();
		// }
		// mydb.closed();

		Connection con = DAO.getCon();
		PreparedStatement pstm = con.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		while (rs.next() && rs != null) {
			allR = rs.getRow();
		}
		pstm.close();
		rs.close();
		con.close();
		return allR;
	}

	public PageBar getDaoPage() {
		return daoPage;
	}

	public int getDaoPerR() {
		return daoPerR;
	}

	public int getDaoPerP() {
		return daoPerP;
	}
}
