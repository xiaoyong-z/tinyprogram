package server.database.util;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class jdbcUtil {
    static String driverClass = null;
    static String url = null;
    static String name = null;
    static String passwd = null;
    static {
        try {
            Properties properties = new Properties();
            InputStream is = new FileInputStream("jdbc.properties");
            properties.load(is);

            driverClass = properties.getProperty("driverClass");
            url = properties.getProperty("url");
            passwd = properties.getProperty("passwd");
            name = properties.getProperty("name");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void release(Connection conn, PreparedStatement st, ResultSet rs){
        closeRs(rs);
        closeSt(st);
        closeConn(conn);
    }

    public static Connection getConn(){
        Connection conn = null;
        try {
            Class.forName(driverClass);
            conn = DriverManager.getConnection(url, name, passwd);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    private static void closeRs(ResultSet rs){
        try {
            if(rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            rs = null;
        }
    }

    private static void closeSt(Statement st){
        try {
            if(st != null) {
                st.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            st = null;
        }
    }

    private static void closeConn(Connection conn){
        try {
            if(conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            conn = null;
        }
    }

    public static void release(Connection conn, PreparedStatement st) {
        closeSt(st);
        closeConn(conn);
    }
}

