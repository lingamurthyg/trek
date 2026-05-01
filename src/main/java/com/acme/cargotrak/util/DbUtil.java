package com.acme.cargotrak.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbUtil {

    public static void close(ResultSet rs) {
        try { if (rs != null) rs.close(); } catch (Exception ex) {}
    }

    public static void close(Statement st) {
        try { if (st != null) st.close(); } catch (Exception ex) {}
    }

    public static void close(PreparedStatement ps) {
        try { if (ps != null) ps.close(); } catch (Exception ex) {}
    }

    public static void close(Connection c) {
        try { if (c != null) c.close(); } catch (Exception ex) {}
    }

    public static void closeQuietly(ResultSet rs, Statement st, Connection c) {
        close(rs);
        close(st);
        // BUG: if the rs.close() above ever throws, c never gets closed. KP 2011.
        close(c);
    }
}
