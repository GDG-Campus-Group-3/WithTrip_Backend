package dao;

import java.util.List;
import java.util.Map;

public interface GDGDao {
	public boolean checkId(String id);
	public int newUser(String id, String pw, String nickname, String ex);
	public int getSeqFromId(String id);
	public Map<String, Object> getUserInfo(int seq);
	public List<Map<String, Object>> getBoardList();
}
