package manager;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import common.Func;
import dao.AdminDao;

public class AdminManager {

	private static AdminDao admindao;
	@SuppressWarnings( "static-access" )
	public AdminManager( AdminDao admindao ) {
		this.admindao = admindao;
	}
	public static void removeDrink(int index, String name, PrintWriter out) {
		if ( admindao.removeDrink(name, index) > 0 ) {
			returnDList(index, out);
		}
	}
	private static void returnDList(int index, PrintWriter out) {
		List<Map<String, Object>> dl = admindao.getDrinkList(index);
		JsonArray ja = new JsonArray();
		if ( dl != null ){
			for ( Map<String, Object> d : dl ) {
				JsonObject jo = new JsonObject();
				jo.addProperty("SEQ", Integer.parseInt(d.get("SEQ").toString()));
				jo.addProperty("NAME", d.get("NAME").toString());
				jo.addProperty("PRICE", Integer.parseInt(d.get("PRICE").toString()));
				jo.addProperty("STOCK", Integer.parseInt(d.get("STOCK").toString()));
				ja.add(jo);
			}
		}
		JsonObject joR = new JsonObject();
		joR.add("list", ja);
		out.write(Func.getResultJson(joR));
	}
	public static void changeDrink(int index, String name, int price, int stock, PrintWriter out) {
		if ( admindao.changeDrinkM(index, name, price, stock) > 0 ) {
			returnDList(index, out);
		}	
	}
	public static void getSellRecord(int index, PrintWriter out) {
		List<Map<String, Object>> dl = admindao.getSellRecord(index);
		JsonArray ja = new JsonArray();
		if ( dl != null ){
			for ( Map<String, Object> d : dl ) {
				JsonObject jo = new JsonObject();
				jo.addProperty("NAME", d.get("NAME").toString());
				jo.addProperty("COUNT", Integer.parseInt(d.get("COUNT").toString()));
				jo.addProperty("SUM", Integer.parseInt(d.get("SUM").toString()));
				ja.add(jo);
			}
		}
		JsonObject joR = new JsonObject();
		joR.add("list", ja);
		out.write(Func.getResultJson(joR));
	}
}
