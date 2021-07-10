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
import oracle.net.aso.l;

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

	public static void getBoardList(PrintWriter out) {
		List<Map<String, Object>> list = gdgdao.getBoardList();
		JsonArray ja = new JsonArray();
		if ( list != null && list.size() > 0 ) {
			for ( Map<String, Object> o:list ) {
				JsonObject jo = new JsonObject();
				jo.addProperty("SEQ", Integer.parseInt(o.get("SEQ").toString()));
				jo.addProperty("STATE", Integer.parseInt(o.get("STATE").toString()));
				jo.addProperty("NICKNAME", o.get("NICKNAME").toString());
				jo.addProperty("TITLE", o.get("TITLE").toString());
				jo.addProperty("LOCATION_NAME", o.get("LOCATION_NAME").toString());
				jo.addProperty("START_DATE", o.get("START_DATE").toString());
				jo.addProperty("LAST_DATE", o.get("LAST_DATE").toString());
				jo.addProperty("MAX_COUNT", Integer.parseInt(o.get("COUNT").toString()));
				jo.addProperty("NOW_COUNT", Integer.parseInt(o.get("NOW_COUNT").toString()));
				ja.add(jo);
			}
		}
		JsonObject joR = new JsonObject();
		joR.add("LIST", ja);
		out.write(Func.getResultJson(joR));
	}

	public static void getBoardDetail(int seq, PrintWriter out) {
		Map<String, Object> o = gdgdao.getBoardDetail(seq);
		if ( o != null ) {
			JsonObject jo = new JsonObject();
			jo.addProperty("STATE", Integer.parseInt(o.get("STATE").toString()));
			jo.addProperty("TAG", o.get("TAG").toString());
			jo.addProperty("GPS", o.get("GPS").toString());
			jo.addProperty("CONTENT", o.get("CONTENT").toString());
			out.write(Func.getResultJson(jo));
		}
	}
	
	public static void newBoard(String tag, String title, String gps, String location_name, String start_date, String last_date, int count, String content, PrintWriter out) {
		gdgdao.newBoard(tag, title, gps, location_name, start_date, last_date, count, content);
		out.write(Func.getResultJson(new JsonObject()));
	}
}
