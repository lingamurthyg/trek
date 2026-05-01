package com.acme.cargotrak.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.acme.cargotrak.util.SpringContextHolder;

public class HealthServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/plain;charset=UTF-8");
        PrintWriter pw = res.getWriter();
        pw.println("status: ok");
        DataSource ds = (DataSource) SpringContextHolder.getBean("dataSource");
        Connection con = null;
        Statement st = null;
        try {
            con = ds.getConnection();
            st = con.createStatement();
            st.execute("SELECT 1");
            pw.println("db: ok");
        } catch (Exception e) {
            pw.println("db: error: " + e.getMessage());
            res.setStatus(500);
        } finally {
            try { if (st != null) st.close(); } catch (Exception ex) {}
            try { if (con != null) con.close(); } catch (Exception ex) {}
        }
    }
}
