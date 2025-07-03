package ru.wg.web.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractAuthController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			doAuth(request, response);
			doGetOperation(request, response);
		} catch (Exception e) {
			throw new IOException("GET doAuth", e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			doAuth(request, response);
			doPostOperation(request, response);
		} catch (Exception e) {
			throw new IOException("POST doAuth", e);
		}
	}

	protected void doGetOperation(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		doOperation(request, response);
	}

	protected void doPostOperation(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		doOperation(request, response);
	}

	protected void doAuth(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	}

	protected abstract void doOperation(HttpServletRequest req,
			HttpServletResponse response) throws Exception;

}
