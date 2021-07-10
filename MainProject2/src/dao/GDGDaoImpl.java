package dao;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;


@SuppressWarnings("unchecked")
public class GDGDaoImpl extends JdbcDaoSupport implements GDGDao{
	public boolean checkId(String id){
		final String query = "select z.ID from user as z where z.ID=?";
		return (String) getJdbcTemplate().query(query, new PSSetDao.PSSForString(id), new ResultDao.RSEForString())==null?false:true;
	}
	public int newUser(String id, String pw, String nickname, String ex){
		final String query = "insert into user(ID, PW, NICKNAME, EX) values(?, ?, ?, ?)";
		return (Integer) getJdbcTemplate().update(query, new PSSetDao.PSSForFourString(id, pw, nickname, ex));
	}
	public int getSeqFromId(String id){
		final String query = "select seq from user where id=?";
		return (Integer) getJdbcTemplate().query(query, new PSSetDao.PSSForString(id), new ResultDao.RSEForInt());
	}
	public Map<String, Object> getUserInfo(int seq){
		final String query = "select z.NICKNAME, z.EX from user as z where z.SEQ=?";
		return (Map<String, Object>) getJdbcTemplate().query(query, new PSSetDao.PSSForInt(seq), new ResultDao.RSEForResult());
	}
}
