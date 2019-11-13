package server.database.impl;

import model.user;
import server.database.dao.adminDao;
import server.database.util.jdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class adminDaoImpl implements adminDao {
    @Override
    public int insert(String username, String password) {
        Connection conn = null;
        PreparedStatement ps = null;
        int result = 0;
        try {
            conn = jdbcUtil.getConn();
            String sql = "insert into user values(adminID, ? , ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            result = ps.executeUpdate();
        } catch (SQLException var10) {
            var10.printStackTrace();
        } finally {
            jdbcUtil.release(conn, ps);
        }
        return result;
    }

    @Override
    public user login(String username) {
        Connection conn = null;
        PreparedStatement ps = null;
        int result = 0;
        user myUesr = null;
        try {
            conn = jdbcUtil.getConn();
            String sql = "select * from user where adminName=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String password = rs.getString("adminPassword");
                String name = rs.getString("adminName");
                int id = Integer.parseInt(rs.getString("adminID"));
                myUesr = new user(id, name, password);
            }

        } catch (SQLException var10) {
            var10.printStackTrace();
        } finally {
            jdbcUtil.release(conn, ps);
        }
        return myUesr;
    }

    @Override
    public int delete(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        int result = 0;
        try {
            conn = jdbcUtil.getConn();
            String sql = "delete from user where adminID = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            result = ps.executeUpdate();
        } catch (SQLException var9) {
            var9.printStackTrace();
        } finally {
            jdbcUtil.release(conn, ps);
        }
        return result;
    }

    @Override
    public int update(int id, String password) {
        Connection conn = null;
        PreparedStatement ps = null;
        int result = 0;
        try {
            conn = jdbcUtil.getConn();
            String sql = "update user set adminPassword =? where adminID=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, password);
            ps.setInt(2, id);
            result = ps.executeUpdate();
        } catch (SQLException var10) {
            var10.printStackTrace();
        } finally {
            jdbcUtil.release(conn, ps);
        }
        return result;
    }

    private int tableRowCounts(){
        Connection conn = null;
        PreparedStatement ps = null;
        int count = 0;
        try {
            conn = jdbcUtil.getConn();
            String sql = "select * from user";
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            rs.last();
            count = rs.getRow();
        } catch (SQLException var9) {
            var9.printStackTrace();
        } finally {
            jdbcUtil.release(conn, ps);
        }
        return count;
    }


    @Override
    public Object[][] listTable() {
        Connection conn = null;
        PreparedStatement ps = null;
        Object[][] objects = new Object[tableRowCounts()][];
        try {
            int count = 0;
            conn = jdbcUtil.getConn();
            String sql = "select * from user";
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String userID = rs.getString("adminID");
                String userName = rs.getString("adminName");
                String userPassword = rs.getString("adminPassword");
                objects[count] = new Object[]{userID, userName, userPassword};
                count++;
            }
        } catch (SQLException var9) {
            var9.printStackTrace();
        } finally {
            jdbcUtil.release(conn, ps);
        }
        return objects;
    }
}