package manager;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import common.Func;
import dao.DrinkDao;

public class DrinkManager {

	private static DrinkDao drinkdao;
	@SuppressWarnings( "static-access" )
	public DrinkManager( DrinkDao drinkdao ) {
		this.drinkdao = drinkdao;
	}
	
	public static void getDrinkList(int index, PrintWriter out) {
		returnDList(index, out);
	}
	
	public static void addNewDrink(String dName, int dPrice, int index, PrintWriter out) {
		if ( drinkdao.searchDrink(dName.replaceAll(" ", "")) > 0 ) {
			int seq = drinkdao.getSeqDrink(dName, index);
			if ( seq <= 0) {
				if ( drinkdao.isDrinkInfo(dName, index) > 0 )
					out.write(Func.getErrorResult(2));
				else {
					seq = drinkdao.getDrinkInfo(dName);
					if ( drinkdao.addNewDrinkM(index, seq, dPrice) < 1 ) {
						out.write(Func.getErrorResult(1));
						return;
					}
					returnDList(index, out);
				}
			}
			else {
				if ( drinkdao.chgStateDrink(seq, index) >= 1 && drinkdao.changeDrinkM(index, dName, dPrice, 0) >= 1 ) {
					returnDList(index, out);
				} else {
					out.write(Func.getErrorResult(1));
					return;
				}
			}
		} else {
			int seq = drinkdao.addNewDrink(dName);
			if ( seq > 0 ) seq = drinkdao.getDrinkInfo(dName);
			if ( seq < 1 || drinkdao.addNewDrinkM(index, seq, dPrice) < 1 ) {
				out.write(Func.getErrorResult(1));
				return;
			}
			
			returnDList(index, out);
		}
	}
	
	private static void returnDList(int index, PrintWriter out) {
		List<Map<String, Object>> dl = drinkdao.getDrinkList(index);
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

	public static void buyDrink(int seq, int index, PrintWriter out) {
		if ( drinkdao.checkDrink(seq, index) < 1 ) {
			out.write(Func.getErrorResult(1));
			return;
		}

		if ( drinkdao.buyDrink(seq, index) < 1 ) {
			out.write(Func.getErrorResult(2));
			return;
		}
		if ( drinkdao.sellDrink(index, seq) < 1 ) {
			out.write(Func.getErrorResult(3));
			return;
		}
		JsonObject joR = new JsonObject();
		joR.addProperty("NAME", drinkdao.getName(seq));
		out.write(Func.getResultJson(joR));
	}

	public static void getDrinkInfoFromOtherDVM(String name, int index, PrintWriter out) {
		List<Map<String, Object>> info = drinkdao.checkDrinkOtherDVM(name, index);
		JsonArray ja = new JsonArray();
		if ( info != null ) {
			for ( Map<String, Object> i : info ) {
				JsonObject joR = new JsonObject();
				joR.addProperty("DVM_SEQ", Integer.parseInt(i.get("DVM_SEQ").toString()));
				joR.addProperty("STOCK", Integer.parseInt(i.get("STOCK").toString()));
				joR.addProperty("LONGITUDE", Double.parseDouble(i.get("LONGITUDE").toString()));
				joR.addProperty("LATITUDE", Double.parseDouble(i.get("LATITUDE").toString()));
				ja.add(joR);
			}
			JsonObject jo = new JsonObject();
			jo.add("list", ja);
			out.write(Func.getResultJson(jo));
		} else {
			out.write(Func.getErrorResult(1));
			return;
		}
	}
	
	public static void getLocationInfo(int index, PrintWriter out) {
		Map<String, Object> info = drinkdao.getLocationInfo(index);
		if ( info != null ) {
			JsonObject joR = new JsonObject();
			joR.addProperty("LONGITUDE", Double.parseDouble(info.get("LONGITUDE").toString()));
			joR.addProperty("LATITUDE", Double.parseDouble(info.get("LATITUDE").toString()));
			out.write(Func.getResultJson(joR));
		} else {
			out.write(Func.getErrorResult(1));
			return;
		}
	}

	public static void inputPC(int index, String name, PrintWriter out) {
		JsonObject joR = new JsonObject();
        Random random = new Random();
		String code = Integer.toString(random.nextInt() * 100000 , 16).replaceAll("-", "");
		while (drinkdao.checkPreCode(code, index)) {
			code = Integer.toString(random.nextInt() * 100000 , 16).replaceAll("-", "");
		}
		if (drinkdao.inputPreCode(code, index, name) > 0) {
			joR.addProperty("code", code);
			out.write(Func.getResultJson(joR));
		}
	}

	public static void answerPrecodeInfo(int index, String code, PrintWriter out) {
		Map<String, Object> info = drinkdao.getPreCode(code);
		String name = "";
		JsonObject joR = new JsonObject();
		if ( info == null )
			joR.addProperty("RESULT", 0);
		else {
			if ( Integer.parseInt(info.get("TARGET_DVM").toString()) != index ) 
				joR.addProperty("RESULT", 1);
			else {
				try{
					if ( drinkdao.buyDrink(Integer.parseInt(info.get("DRINK_SEQ").toString()), index) > 0 && drinkdao.usePrecode(code) > 0 ){
						joR.addProperty("RESULT", 3);
						name = info.get("D_NAME").toString();
					} else
						joR.addProperty("RESULT", 2);
				} catch (Exception e) {
					joR.addProperty("RESULT", 2);
				}
			}
		}
		joR.addProperty("D_NAME", name);
		out.write(Func.getResultJson(joR));
	}
}
