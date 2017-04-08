package com.toolsbean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB {
	private Connection con;
	private PreparedStatement pstm;
	private String user = "sa";
	private String password = "1234";
	private String className = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
	private String url = "jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=db_shoppingweb";

	public DB() {
		try {
			Class.forName(className);
		} catch (ClassNotFoundException e) {
			// System.out.println("閸旗姾娴囬弫鐗埚祦鎼存捇铍嶉崝銊ャ亼镨愩傥绱?);
			e.printStackTrace();
		}
	}

	/**
	 * 閸掓稑缂挞弫鐗埚祦鎼存捁绻涢幒锟?/ public Connection getCon(){ if(con==null){ try {
	 * con=DriverManager.getConnection(url,user,password); } catch (SQLException
	 * e) { System.out.println("閸掓稑缂挞弫鐗埚祦鎼存捁绻涢幒銉ャ亼镨愩傥绱?); con=null;
	 * e.printStackTrace(); } } return con; }
	 * 
	 * /**
	 * 
	 * @閸旗喕鍏橀敍姘嚠閺佺増宓佹惔鎾圭箻鐞涘苯顤冮妴浣稿灩閵嗕焦鏁奸妴浣圭叀阉垮秳缍?
	 * @閸椤倹鏆熼敍姝磓l娑撶癄QL镨囶厼褰为敍娌篴rams娑撶瘘bject閺佹壆绮嶉敍宀勫櫡闂堛垹鐡ㄩ崒銊ф畱閺励垯璐焥ql鐞涖劎銇氶惃鍑称L镨囶厼褰炴稉锟??"閸楃姳缍呯粭锕佺ゴ閸婅偐娈戦弫鐗埚祦
	 */
	public void doPstm(String sql, Object[] params) {
		if (sql != null && !sql.equals("")) {
			if (params == null)
				params = new Object[0];
			// getCon();
			if (con != null) {
				try {
					System.out.println(sql);
					pstm = con.prepareStatement(sql);
					// pstm=con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
					for (int i = 0; i < params.length; i++) {
						pstm.setObject(i + 1, params[i]);
					}
					pstm.execute();
				} catch (SQLException e) {
					System.out.println("doPstm()閺傝纭堕仑娲晩阌涳拷");
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @閸旗喕鍏橀敍姘冲箯閸欐牞镄熼凄钰爋Pstm()閺傝纭堕幍褑颟戦弻銉嚄阉垮秳缍旈崥搴ょ箲閸ョ偟娈慠esultSet缂佹挻鐏夐梿锟?
	 * @鏉╂柨娲栭崐纭风窗ResultSet
	 * @throws SQLException
	 */
	public ResultSet getRs() throws SQLException {
		return pstm.getResultSet();
	}

	/**
	 * @閸旗喕鍏橀敍姘冲箯閸欐牞镄熼凄钰爋Pstm()閺傝纭堕幍褑颟戦弴瀛樻煀阉垮秳缍旈崥搴ょ箲閸ョ偛濂栭崫宥囨畱镨佹澘缍嶉弫锟?
	 * @鏉╂柨娲栭崐纭风窗int
	 * @throws SQLException
	 */
	public int getCount() throws SQLException {
		return pstm.getUpdateCount();
	}

	/**
	 * @閸旗喕鍏橀敍姘跺橼閺€缍猺epareStatement鐎电钖勬稉宥nnection鐎电钖?
	 */
	public void closed() {
		try {
			if (pstm != null)
				pstm.close();
		} catch (SQLException e) {
			System.out.println("閸忔娊妫磒stm鐎电钖勬径杈Е阌涳拷");
			e.printStackTrace();
		}
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			System.out.println("閸忔娊妫碿on鐎电钖勬径杈Е阌涳拷");
			e.printStackTrace();
		}
	}
}
