package server.database.impl;

import server.database.dao.consumeDao;
import server.database.util.jdbcUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Calendar;

public class consumeDaoImpl implements consumeDao {

    @Override
    public void use(String cardNumber) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = jdbcUtil.getConn();
            String sql = "select * from Card where cardNumber=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, cardNumber);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                String passWord = rs.getString("passWard");
                double totalUse = rs.getDouble("totalUse");
                int status = 0;
                int del = 0;
                Timestamp start = new Timestamp(Calendar.getInstance().getTimeInMillis());
                sql = "insert into consume values(?, ?, ?, ?, ?, ?)";
                ps = conn.prepareStatement(sql);
                ps.setString(1, cardNumber);
                ps.setTimestamp(2, start);
                ps.setTimestamp(3, null);
                ps.setDouble(4, totalUse);
                ps.setInt(5, status);
                ps.setInt(6, del);
                ps.executeUpdate();
            }
        } catch (SQLException var10) {
            var10.printStackTrace();
        } finally {
            jdbcUtil.release(conn, ps);
        }
    }

    @Override
    public void stop(String cardNumber, Calendar start) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = jdbcUtil.getConn();
            String sql = "select * from Card where cardNumber=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, cardNumber);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                double totalUse = rs.getDouble("totalUse");
                int status = 1;
                int del = 0;
                Timestamp start_record = new Timestamp(start.getTimeInMillis());
                Timestamp end_record = new Timestamp(Calendar.getInstance().getTimeInMillis());
                sql = "update consume set end=?, cost=?, status=? where start = ?";
                ps = conn.prepareStatement(sql);
                ps.setTimestamp(1, end_record);
                ps.setDouble(2, totalUse);
                ps.setInt(3, status);
                ps.setTimestamp(4, start_record);
                ps.executeUpdate();
            }
        } catch (SQLException var10) {
            var10.printStackTrace();
        } finally {
            jdbcUtil.release(conn, ps);
        }
    }

    @Override
    public Calendar search(String cardNumber) {
        Connection conn = null;
        PreparedStatement ps = null;
        Calendar result = null;
        try {
            conn = jdbcUtil.getConn();
            String sql = "select * from consume where cardNumber=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, cardNumber);
            ResultSet rs = ps.executeQuery();
            Timestamp start = null;
            while(rs.next()){
                start = rs.getTimestamp("start");
            }
            result = Calendar.getInstance();
            result.setTime(start);
        } catch (SQLException var10) {
            var10.printStackTrace();
        } finally {
            jdbcUtil.release(conn, ps);
        }
        return result;
    }

    private int tableRowCounts(String cardNumber){
        Connection conn = null;
        PreparedStatement ps = null;
        int count = 0;
        try {
            conn = jdbcUtil.getConn();
            String sql = "select * from consume where cardNumber=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, cardNumber);
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
    public Object[][] listTable(String cardNumber) {
        Connection conn = null;
        PreparedStatement ps = null;
        Object[][] objects = new Object[tableRowCounts(cardNumber)][];
        try {
            int count = 0;
            conn = jdbcUtil.getConn();
            String sql = "select * from consume where cardNumber=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, cardNumber);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                double cost = rs.getDouble("cost");
                BigDecimal b = new BigDecimal(cost);
                cost = b.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
                int status = rs.getInt("status");
                String start = rs.getTimestamp("start").toString();
                String  end = rs.getTimestamp("end").toString();
                objects[count] = new Object[]{start, end, cost, status};
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
