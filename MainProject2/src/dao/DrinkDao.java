package dao;

import java.util.List;
import java.util.Map;

public interface DrinkDao {
	public List<Map<String, Object>> getDrinkList(int index);
	public int searchDrink(String dName);
	public int addNewDrink(String dName);
	public int getDrinkInfo(String dName);
	public int addNewDrinkM(int index, int seq, int dPrice);
	public int checkDrink(int seq, int index);
	public List<Map<String, Object>> checkDrinkOtherDVM(String name, int index);
	public int buyDrink(int seq, int index);
	public int buyDrink(String name, int index);
	public boolean checkPreCode(String code, int index);
	public String getName(int seq);
	public Map<String, Object> getLocationInfo(int index);
	public int inputPreCode(String code, int index, String name);
	public Map<String, Object> getPreCode(String code);
	public int usePrecode(String code);
	public int sellDrink(int index, int seq);
	public int getSeqDrink(String name, int index);
	public int isDrinkInfo(String dName, int index);
	public int chgStateDrink(int seq, int i);
	public int changeDrinkM(int index, String name, int dPrice, int stock);
}
