package controller;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import manager.DrinkManager;


public class ActionDrink extends MultiActionController {

	public void getDrinkList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter		out;
		
		try {
			response.setContentType("text/html; charset=utf-8");

			out = response.getWriter();
		}
		catch (IOException ioe) {
			return;
		}

		int index = Integer.parseInt(request.getParameter("INDEX"));
		DrinkManager.getDrinkList(index, out);
	}

	public void addNewDrink(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter		out;
		
		try {
			response.setContentType("text/html; charset=utf-8");

			out = response.getWriter();
		}
		catch (IOException ioe) {
			return;
		}

		String dName = request.getParameter("D_NAME");
		int dPrice = Integer.parseInt(request.getParameter("D_PRICE"));
		int index = Integer.parseInt(request.getParameter("INDEX"));
		DrinkManager.addNewDrink(dName, dPrice, index, out);
	}

	public void buyDrink(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter		out;
        
		try {
			response.setContentType("text/html; charset=utf-8");

			out = response.getWriter();
		}
		catch (IOException ioe) {
			return;
		}

		int seq = Integer.parseInt(request.getParameter("D_SEQ"));
		int index = Integer.parseInt(request.getParameter("INDEX"));
		DrinkManager.buyDrink(seq, index, out);
	}

	public void getDrinkInfoFromOtherDVM(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter		out;
		
		try {
			response.setContentType("text/html; charset=utf-8");

			out = response.getWriter();
		}
		catch (IOException ioe) {
			return;
		}

		String name = request.getParameter("D_NAME");
		int index = Integer.parseInt(request.getParameter("INDEX"));
		DrinkManager.getDrinkInfoFromOtherDVM(name, index, out);
	}

	public void getLocationInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter		out;
		
		try {
			response.setContentType("text/html; charset=utf-8");

			out = response.getWriter();
		}
		catch (IOException ioe) {
			return;
		}

		int index = Integer.parseInt(request.getParameter("INDEX"));
		DrinkManager.getLocationInfo(index, out);
	}

	public void inputPC(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		DrinkManager.inputPC(index, name, out);
	}
	public void answerPrecodeInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter		out;
		
		try {
			response.setContentType("text/html; charset=utf-8");

			out = response.getWriter();
		}
		catch (IOException ioe) {
			return;
		}

		int index = Integer.parseInt(request.getParameter("INDEX"));
		String code = request.getParameter("CODE");
		DrinkManager.answerPrecodeInfo(index, code, out);
	}
	
}