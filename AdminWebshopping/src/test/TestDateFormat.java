package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestDateFormat {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
		Date date = new Date();
		String string = format.format(date);
		System.out.println(string);

		try {
			Date date1 = format.parse(string);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("60" + date.toString());
		// 将实体类的出生日期转换为Date类型
		// pstm.setTimestamp(6, new Timestamp(date.getTime()));
	}

}
