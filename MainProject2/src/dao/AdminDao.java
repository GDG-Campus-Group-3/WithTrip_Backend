package dao;

import java.util.List;
import java.util.Map;

public interface AdminDao {
	public int removeDrink(String name, int index);
	public List<Map<String, Object>> getDrinkList(int index);
	public int changeDrinkM(int index, String name, int dPrice, int stock);
	public List<Map<String, Object>> getSellRecord(int index);
}
