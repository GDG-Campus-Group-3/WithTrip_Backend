package dao;

import java.util.List;
import java.util.Map;

public interface GDGDao {
	public boolean checkId(String id);
	public int newUser(String id, String pw, String nickname, String ex);
	public int getSeqFromId(String id);
	public Map<String, Object> getUserInfo(int seq);
	public List<Map<String, Object>> getBoardList();
	public Map<String, Object> getBoardDetail(int seq);
	public int newBoard(int seq, String tag, String title, String gps, String location_name, String start_date,
			String last_date, int count, String content);
	public int getBoardOwnerSeq(int seq);
	public int deleteBoard(int seq);
}
