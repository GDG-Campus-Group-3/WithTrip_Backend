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
	public List<Map<String, Object>> getBoardList() {
		final String query = "SELECT u.SEQ, u.STATE, (SELECT NICKNAME FROM user WHERE SEQ=u.WRITER_SEQ) AS NICKNAME, u.TITLE, u.LOCATION_NAME, u.START_DATE, u.LAST_DATE, u.COUNT, (SELECT COUNT(SEQ) FROM follow_board_info WHERE STATE=0 AND BOARD_SEQ=u.SEQ) AS NOW_COUNT FROM board AS u WHERE u.STATE<100 ORDER BY u.STATE, u.START_DATE DESC";
		return (List<Map<String, Object>>) getJdbcTemplate().query(query, new ResultDao.RSEForResultListSet());
	}
	public Map<String, Object> getBoardDetail(int seq) {
		final String query = "SELECT u.STATE, u.TAG, u.GPS, u.CONTENT FROM board AS u WHERE u.SEQ=?";
		return (Map<String, Object>) getJdbcTemplate().query(query, new PSSetDao.PSSForInt(seq), new ResultDao.RSEForResult());
	}
	public int newBoard(int seq, String tag, String title, String gps, String location_name, String start_date,
			String last_date, int count, String content) {
		final String query = "INSERT INTO `board` (`TAG`, `TITLE`, `GPS`, `LOCATION_NAME`, `START_DATE`, `LAST_DATE`, `WRITER_SEQ`, `COUNT`, `CONTENT`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		return (Integer) getJdbcTemplate().update(query, new PSSetDao.PSSForStringsIntsStrings(6, 1, 1, tag, title, gps, location_name, start_date, last_date, seq, count, content));
	}
	public int getBoardOwnerSeq(int seq) {
		final String query = "select WRITER_SEQ from board where seq=?";
		return (Integer) getJdbcTemplate().query(query, new PSSetDao.PSSForInt(seq), new ResultDao.RSEForInt());
	}
	public int deleteBoard(int seq) {
		final String query = "update board set state=100 where seq=?";
		return (Integer) getJdbcTemplate().update(query, new PSSetDao.PSSForInt(seq));
	}
}
