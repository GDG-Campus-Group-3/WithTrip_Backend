package controller;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import manager.DrinkManager;
import manager.GDGManager;


public class ActionGDG extends MultiActionController {

	public void checkId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter		out;
		
		try {
			response.setContentType("text/html; charset=utf-8");

			out = response.getWriter();
		}
		catch (IOException ioe) {
			return;
		}

		String id = request.getParameter("ID");
		GDGManager.checkId(id, out);
	}

	public void newUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter		out;
		
		try {
			response.setContentType("text/html; charset=utf-8");

			out = response.getWriter();
		}
		catch (IOException ioe) {
			return;
		}

		String id = request.getParameter("ID");
		String pw = request.getParameter("PW");
		String nickname = request.getParameter("NICKNAME");
		String ex = request.getParameter("EX");
		GDGManager.newUser(id, pw, nickname, ex, out);
	}

	public void getUserInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter		out;
		
		try {
			response.setContentType("text/html; charset=utf-8");

			out = response.getWriter();
		}
		catch (IOException ioe) {
			return;
		}

		int seq = Integer.parseInt(request.getParameter("SEQ"));
		GDGManager.getUserInfo(seq, out);
	}

	public void getBoardList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter		out;
		
		try {
			response.setContentType("text/html; charset=utf-8");

			out = response.getWriter();
		}
		catch (IOException ioe) {
			return;
		}
		GDGManager.getBoardList(out);
	}

	public void getBoardDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter		out;
		
		try {
			response.setContentType("text/html; charset=utf-8");

			out = response.getWriter();
		}
		catch (IOException ioe) {
			return;
		}

		int seq = Integer.parseInt(request.getParameter("SEQ"));
		GDGManager.getBoardDetail(seq, out);
	}
	
	public void newBoard(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter		out;
		
		try {
			response.setContentType("text/html; charset=utf-8");

			out = response.getWriter();
		}
		catch (IOException ioe) {
			return;
		}

		String tag = request.getParameter("TAG");
		String title = request.getParameter("TITLE");
		String gps = request.getParameter("GPS");
		String location_name = request.getParameter("LOCATION_NAME");
		String start_date = request.getParameter("START_DATE");
		String last_date = request.getParameter("LAST_DATE");
		int count = Integer.parseInt(request.getParameter("COUNT"));
		String content = request.getParameter("CONTENT");
		GDGManager.newBoard(tag, title, gps, location_name, start_date, last_date, count, content, out);
	}

	public void getBoardOwnerSeq(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter		out;
		
		try {
			response.setContentType("text/html; charset=utf-8");

			out = response.getWriter();
		}
		catch (IOException ioe) {
			return;
		}

		int seq = Integer.parseInt(request.getParameter("SEQ"));
		GDGManager.getBoardOwnerSeq(seq, out);
	}

	public void deleteBoard(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter		out;
		
		try {
			response.setContentType("text/html; charset=utf-8");

			out = response.getWriter();
		}
		catch (IOException ioe) {
			return;
		}

		int seq = Integer.parseInt(request.getParameter("SEQ"));
		GDGManager.deleteBoard(seq, out);
	}
}