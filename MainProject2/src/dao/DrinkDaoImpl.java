package dao;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;


@SuppressWarnings("unchecked")
public class DrinkDaoImpl extends JdbcDaoSupport implements DrinkDao{
	public List<Map<String, Object>> getDrinkList(int index) {
	    final String query = "select u.SEQ, u.NAME, z.PRICE, z.STOCK from drink_info as u join drink_stock as z on u.SEQ=z.DRINK_SEQ where z.DVM_SEQ=? and z.STATE=0";
		return (List<Map<String, Object>>) getJdbcTemplate().query(query, new PSSetDao.PSSForInt(index), new ResultDao.RSEForResultListSet());
	}
	public int searchDrink(String dName) {
		final String query = "select SEQ from drink_info where REPLACE(NAME, ' ', '')=?";
		return (Integer) getJdbcTemplate().query(query, new PSSetDao.PSSForString(dName), new ResultDao.RSEForInt());
	}
	public int addNewDrink(String dName) {
		final String query = "INSERT INTO drink_info(NAME) VALUES(?)";
		return (Integer) getJdbcTemplate().update(query, new PSSetDao.PSSForString(dName));
	}
	public int getDrinkInfo(String dName) {
		final String query = "select SEQ from drink_info where NAME=?";
		return (Integer) getJdbcTemplate().query(query, new PSSetDao.PSSForString(dName), new ResultDao.RSEForInt());
	}
	public int addNewDrinkM(int index, int seq, int dPrice) {
		final String query = "INSERT INTO drink_stock(DVM_SEQ, DRINK_SEQ, PRICE, STOCK) VALUES(?, ?, ?, 0) ON DUPLICATE KEY UPDATE DVM_SEQ=DVM_SEQ;";
		return (Integer) getJdbcTemplate().update(query, new PSSetDao.PSSForTripleInt(index, seq, dPrice));
	}
	public int checkDrink(int seq, int index) {
		final String query = "select STOCK from drink_stock where DVM_SEQ=? and DRINK_SEQ=?";
		return (Integer) getJdbcTemplate().query(query, new PSSetDao.PSSForDoubleInt(index, seq), new ResultDao.RSEForInt());
	}
	public List<Map<String, Object>> checkDrinkOtherDVM(String name, int index) {
		final String query = "select u.DVM_SEQ, u.STOCK, z.LONGITUDE, z.LATITUDE from drink_stock as u join dvm_info as z on u.DVM_SEQ=z.SEQ where u.DRINK_SEQ=(select SEQ from drink_info where REPLACE(NAME, ' ', '')=REPLACE('"+name+"', ' ', '')) and u.DVM_SEQ!=? and u.STOCK>0";
		return (List<Map<String, Object>>) getJdbcTemplate().query(query, (PreparedStatementSetter) new PSSetDao.PSSForInt(index), new ResultDao.RSEForResultListSet());
	}
	public int buyDrink(int seq, int index) {
		final String query = "update drink_stock set STOCK=STOCK-1 where DVM_SEQ=? and DRINK_SEQ=?";
		return (Integer) getJdbcTemplate().update(query, new PSSetDao.PSSForDoubleInt(index, seq));
	}
	public int buyDrink(String name, int index) {
		final String query = "update drink_stock set STOCK=STOCK-1 where DVM_SEQ=? and DRINK_SEQ=(select SEQ from drink_info where name=?)";
		return (Integer) getJdbcTemplate().update(query, new PSSetDao.PSSForIntString(index, name));
	}
	public boolean checkPreCode(String code, int index){
		final String query = "select SEQ from pre_code as a where a.TARGET_DVM=? and a.CODE=? and a.STATE=0";
		return (Integer) getJdbcTemplate().query(query, new PSSetDao.PSSForIntString(index, code), new ResultDao.RSEForInt()) > 0;
	}

	public String getName(int seq){
		final String query = "select NAME from drink_info where SEQ=?";
		return (String) getJdbcTemplate().query(query, new PSSetDao.PSSForInt(seq), new ResultDao.RSEForString());
	}

	public Map<String, Object> getLocationInfo(int index){
		final String query = "select z.LONGITUDE, z.LATITUDE from dvm_info as z where z.SEQ=?";
		return (Map<String, Object>) getJdbcTemplate().query(query, new PSSetDao.PSSForInt(index), new ResultDao.RSEForResult());
	}
	public int inputPreCode(String code, int index, String name) {
		final String query = "insert into pre_code(CODE, TARGET_DVM, DRINK_SEQ) values(?, ?, (select SEQ from drink_info where NAME=?))";
		return (Integer) getJdbcTemplate().update(query, new PSSetDao.PSSForStringIntString(code, index, name));
	}

	public Map<String, Object> getPreCode(String code){
		final String query = "select CODE, STATE, TARGET_DVM, DRINK_SEQ, (select NAME from drink_info where SEQ=u.DRINK_SEQ) as D_NAME from pre_code as u where CODE=? and STATE=0";
		return (Map<String, Object>) getJdbcTemplate().query(query, new PSSetDao.PSSForString(code), new ResultDao.RSEForResult());
	}
	public int usePrecode(String code) {
		final String query = "update pre_code set STATE=1 where CODE=? and STATE!=1";
		return (Integer) getJdbcTemplate().update(query, new PSSetDao.PSSForString(code));
	}
	public int sellDrink(int index, int seq) {
		final String query = "insert into sell_drink(D_DVM_SEQ, D_SEQ, D_NAME, D_PRICE) values(?, ?, (select NAME from drink_info where SEQ=?), (select PRICE from drink_stock where DRINK_SEQ=? and DVM_SEQ=?));";
		return (Integer) getJdbcTemplate().update(query, new PSSetDao.PSSForFiveInt(index, seq, seq, seq, index));
	}
	public int getSeqDrink(String name, int index) {
		final String query = "select DRINK_SEQ from drink_stock where DVM_SEQ=? and DRINK_SEQ=(select SEQ from drink_info where NAME=?) and STATE=1";
		return (Integer) getJdbcTemplate().query(query, new PSSetDao.PSSForIntString(index, name), new ResultDao.RSEForInt());
	}
	public int isDrinkInfo(String dName, int index) {
		final String query = "select SEQ from drink_stock where DRINK_SEQ=(select SEQ from drink_info where NAME=?) and DVM_SEQ=?";
		return (Integer) getJdbcTemplate().query(query, new PSSetDao.PSSForStringInt(dName, index), new ResultDao.RSEForInt());
	}
	public int chgStateDrink(int seq, int i) {
		final String query = "update drink_stock set STATE=0 where DRINK_SEQ=? and DVM_SEQ=?";
		return (Integer) getJdbcTemplate().update(query, new PSSetDao.PSSForDoubleInt(seq, i));
	}
	public int changeDrinkM(int index, String name, int dPrice, int stock) {
		final String query = "INSERT INTO drink_stock(DRINK_SEQ, DVM_SEQ, PRICE, STOCK) VALUES((select SEQ from drink_info where NAME=?), ?, ?, ?) ON DUPLICATE KEY UPDATE DVM_SEQ=DVM_SEQ, PRICE=?, STOCK=?;";
		return (Integer) getJdbcTemplate().update(query, new PSSetDao.PSSForStringInts(name, index, dPrice, stock, dPrice, stock));
	}
}
