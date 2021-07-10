package manager;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import common.Func;
import dao.DrinkDao;
import dao.GDGDao;

public class GDGManager {

	private static GDGDao gdgdao;
	@SuppressWarnings( "static-access" )
	public GDGManager( GDGDao gdgdao ) {
		this.gdgdao = gdgdao;
	}

	public static void checkId(String id, PrintWriter out) {
		JsonObject jo = new JsonObject();
		jo.addProperty("CHECK", gdgdao.checkId(id));
		out.write(Func.getResultJson(jo));
	}
	
	public static void newUser(String id, String pw, String nickname, String ex, PrintWriter out) {
		gdgdao.newUser(id, pw, nickname, ex);
		JsonObject jo = new JsonObject();
		jo.addProperty("SEQ", gdgdao.getSeqFromId(id));
		out.write(Func.getResultJson(jo));
		
	}
	
	public static void getUserInfo(int seq, PrintWriter out) {
		Map<String, Object> ul = gdgdao.getUserInfo(seq);
		if ( ul != null ){
			JsonObject jo = new JsonObject();
			jo.addProperty("NICKNAME", ul.get("NICKNAME").toString());
			jo.addProperty("EX", ul.get("EX").toString());
			out.write(Func.getResultJson(jo));
		}
	}

}
