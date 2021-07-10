package dao;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.support.JdbcDaoSupport;


@SuppressWarnings("unchecked")
public class AdminDaoImpl extends JdbcDaoSupport implements AdminDao{
	public int removeDrink(String name, int index) {
		final String query = "update drink_stock set STATE=1, STOCK=0 where DVM_SEQ=? and DRINK_SEQ=(select SEQ from drink_info where NAME=?)";
		return (Integer) getJdbcTemplate().update(query, new PSSetDao.PSSForIntString(index, name));
	}
	public List<Map<String, Object>> getDrinkList(int index) {
	    final String query = "select u.SEQ, u.NAME, z.PRICE, z.STOCK from drink_info as u join drink_stock as z on u.SEQ=z.DRINK_SEQ where z.DVM_SEQ=? and z.STATE=0";
		return (List<Map<String, Object>>) getJdbcTemplate().query(query, new PSSetDao.PSSForInt(index), new ResultDao.RSEForResultListSet());
	}
	public int changeDrinkM(int index, String name, int dPrice, int stock) {
		final String query = "INSERT INTO drink_stock(DRINK_SEQ, DVM_SEQ, PRICE, STOCK) VALUES((select SEQ from drink_info where NAME=?), ?, ?, ?) ON DUPLICATE KEY UPDATE DVM_SEQ=DVM_SEQ, PRICE=?, STOCK=?;";
		return (Integer) getJdbcTemplate().update(query, new PSSetDao.PSSForStringInts(name, index, dPrice, stock, dPrice, stock));
	}
	public List<Map<String, Object>> getSellRecord(int index) {
	    final String query = "select (select NAME from drink_info where SEQ=u.D_SEQ)as NAME, count(u.SEQ) as COUNT, sum(u.D_PRICE) as SUM from sell_drink as u where u.D_DVM_SEQ=? group by u.D_SEQ";
		return (List<Map<String, Object>>) getJdbcTemplate().query(query, new PSSetDao.PSSForInt(index), new ResultDao.RSEForResultListSet());
		
	}
}
