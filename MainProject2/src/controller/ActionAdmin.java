package controller;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import manager.AdminManager;


public class ActionAdmin extends MultiActionController {

	public void removeDrink(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter		out;
		
		try {
			response.setContentType("text/html; charset=utf-8");

			out = response.getWriter();
		}
		catch (IOException ioe) {
			return;
		}

		int index = Integer.parseInt(request.getParameter("INDEX"));
		String name = request.getParameter("D_NAME");
		AdminManager.removeDrink(index, name, out);
	}

	public void changeDrink(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter		out;
		
		try {
			response.setContentType("text/html; charset=utf-8");

			out = response.getWriter();
		}
		catch (IOException ioe) {
			return;
		}

		int index = Integer.parseInt(request.getParameter("INDEX"));
		String name = request.getParameter("D_NAME");
		int price = Integer.parseInt(request.getParameter("PRICE"));
		int stock = Integer.parseInt(request.getParameter("STOCK"));
		AdminManager.changeDrink(index, name, price, stock, out);
	}

	public void getSellRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter		out;
		
		try {
			response.setContentType("text/html; charset=utf-8");

			out = response.getWriter();
		}
		catch (IOException ioe) {
			return;
		}

		int index = Integer.parseInt(request.getParameter("INDEX"));
		AdminManager.getSellRecord(index, out);
	}

}