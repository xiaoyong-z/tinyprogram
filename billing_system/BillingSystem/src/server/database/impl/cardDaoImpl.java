package server.database.impl;
import model.card;
import server.database.dao.cardDao;
import server.database.util.jdbcUtil;

import java.sql.*;
import java.util.Calendar;

public class cardDaoImpl implements cardDao {

    @Override
    public int insert(card card) {
        Connection conn = null;
        PreparedStatement ps = null;
        int result = 0;
        try {
            conn = jdbcUtil.getConn();
            String sql = "insert into Card values(? , ? , ?, ?, ?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, card.getCardNumber());
            ps.setString(2, card.getPassWard());
            ps.setDouble(3, card.getBalance());
            ps.setDouble(4, card.getTotalUse());
            ps.setInt(5, card.getUseTimes());
            ps.setInt(6, card.getStatus());
            ps.setInt(7, card.getDel());
            ps.setTimestamp(8, new Timestamp(card.getLast().getTimeInMillis()));
            ps.setTimestamp(9, new Timestamp(card.getLast().getTimeInMillis()));
            ps.setTimestamp(10, new Timestamp(card.getLast().getTimeInMillis()));
            result = ps.executeUpdate();
        } catch (SQLException var10) {
            var10.printStackTrace();
        } finally {
            jdbcUtil.release(conn, ps);
        }
        return result;
    }

    @Override
    public card search(String CardNumber) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = jdbcUtil.getConn();
            String sql = "select * from Card where cardNumber=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, CardNumber);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                String cardNumber = rs.getString("cardNumber");
                String passWord = rs.getString("passWard");
                double balance = rs.getDouble("balance");
                double totalUse = rs.getDouble("totalUse");
                int useTimes = rs.getInt("useTImes");
                int status = rs.getInt("status");
                int del = rs.getInt("del");
                Timestamp start = rs.getTimestamp("start");
                Timestamp end = rs.getTimestamp("end");
                Timestamp last = rs.getTimestamp("last");
                card new_card = new card(cardNumber, passWord, balance, totalUse,
                        useTimes, status, del,
                        start, end, last);
                return new_card;
            }else{
                System.out.println("没有该卡信息");
            }
        } catch (SQLException var10) {
            var10.printStackTrace();
        } finally {
            jdbcUtil.release(conn, ps);
        }
        return null;
    }

    private String dataShow(Calendar date){
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int hour = date.get(Calendar.HOUR_OF_DAY);
        int minute = date.get(Calendar.MINUTE);
        String result = new String(year + "-" + month + "-" + day + " " +
                hour + ":" + minute);
        return result;
    }

    @Override
    public void searchShow(String CardNumber) {
        card new_card = search(CardNumber);
        System.out.println("卡号\t状态\t余额\t累计使用\t使用次数\t上次使用时间");
        System.out.println(new_card.getCardNumber() + "\t" + new_card.getStatus() + "\t"
                + new_card.getBalance() + "\t" + new_card.getTotalUse() + "\t"
                + new_card.getUseTimes() + "\t" + dataShow(new_card.getLast()));

    }

    @Override
    public int useUpdate(String CardNumber) {
        Connection conn = null;
        PreparedStatement ps = null;
        int result = 0;
        try {
            conn = jdbcUtil.getConn();
            String sql = "select useTImes, balance from Card where cardNumber=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, CardNumber);
            ResultSet rs = ps.executeQuery();
            int useTimes = 0;
            double balance = 0;
            if(rs.next()){
                useTimes = rs.getInt("useTImes");
                balance = rs.getDouble("balance");
            }
            sql = "update Card set useTImes=useTImes + 1, status= 1 where cardNumber =?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, CardNumber);
            result = ps.executeUpdate();
        } catch (SQLException var10) {
            var10.printStackTrace();
        } finally {
            jdbcUtil.release(conn, ps);
        }
        if(result != 1){
            result = 4;
        }
        return result;
    }

    @Override
    public int stopUpdate(String CardNumber, double balance, Calendar end, double cost, Calendar start) {
        Connection conn = null;
        PreparedStatement ps = null;
        int result = 0;
        try {
            conn = jdbcUtil.getConn();
            String sql = "select totalUse from Card where cardNumber=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, CardNumber);
            ResultSet rs = ps.executeQuery();
            double totalUse = 0;
            if(rs.next()){
                totalUse = rs.getDouble("totalUse");
            }
            sql = "update Card set balance=?, last=?, status=?, totalUse=? where cardNumber =?";
            ps = conn.prepareStatement(sql);
            ps.setDouble(1, balance);
            ps.setTimestamp(2, new Timestamp(end.getTimeInMillis()));
            if(balance >= 0) {
                ps.setInt(3, 0);
            }else{
                ps.setInt(3, 1);
            }
            ps.setDouble(4, totalUse + cost);
            ps.setString(5, CardNumber);
            result = ps.executeUpdate();
        } catch (SQLException var10) {
            var10.printStackTrace();
        } finally {
            jdbcUtil.release(conn, ps);
        }
        if(result != 1){
            result = 4;
        }
        if(balance < 0){
            result = 3;
        }
        return result;
    }

    @Override
    public int topUp(String CardNumber, double amount) {
        Connection conn = null;
        PreparedStatement ps = null;
        int result = 0;
        try {
            conn = jdbcUtil.getConn();
            String sql = "select balance from Card where cardNumber=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, CardNumber);
            ResultSet rs = ps.executeQuery();
            double balance = 0;
            if(rs.next()){
                balance = rs.getDouble("balance");
            }
            sql = "update Card set balance=? where cardNumber =?";
            ps = conn.prepareStatement(sql);
            ps.setDouble(1, balance + amount);
            ps.setString(2, CardNumber);
            result = ps.executeUpdate();
        } catch (SQLException var10) {
            var10.printStackTrace();
        } finally {
            jdbcUtil.release(conn, ps);
        }
        if(result != 1){
            result = 3;
        }
        return result;
    }

    @Override
    public int drawBack(String cardNumber) {
        Connection conn = null;
        PreparedStatement ps = null;
        int result = 0;
        try {
            conn = jdbcUtil.getConn();
            String sql = "select balance from Card where cardNumber=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, cardNumber);
            ResultSet rs = ps.executeQuery();
            double balance = 0;
            if(rs.next()){
                balance = rs.getInt("balance");
            }
            sql = "update Card set balance=? where cardNumber =?";
            ps = conn.prepareStatement(sql);
            ps.setDouble(1, 0);
            ps.setString(2, cardNumber);
            result = ps.executeUpdate();
        } catch (SQLException var10) {
            var10.printStackTrace();
        } finally {
            jdbcUtil.release(conn, ps);
        }
        if(result != 1){
            result = 4;
        }
        return result;
    }

    @Override
    public int cancel(String cardNumber) {
        Connection conn = null;
        PreparedStatement ps = null;
        int result = 0;
        try {
            conn = jdbcUtil.getConn();
            String sql = "update Card set balance=0, status = 2, end=? where cardNumber =?";
            ps = conn.prepareStatement(sql);
            ps.setTimestamp(1, new Timestamp(Calendar.getInstance().getTimeInMillis()));
            ps.setString(2, cardNumber);
            result = ps.executeUpdate();
        } catch (SQLException var10) {
            var10.printStackTrace();
        } finally {
            jdbcUtil.release(conn, ps);
        }
        if(result != 1){
            result = 4;
        }
        return result;
    }

    private int tableRowCounts(){
        Connection conn = null;
        PreparedStatement ps = null;
        int count = 0;
        try {
            conn = jdbcUtil.getConn();
            String sql = "select * from Card";
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
            String sql = "select * from Card";
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String cardNumber = rs.getString("cardNumber");
                String passWord = rs.getString("passWard");
                double balance = rs.getDouble("balance");
                double totalUse = rs.getDouble("totalUse");
                int useTimes = rs.getInt("useTImes");
                int status = rs.getInt("status");
                String start = rs.getTimestamp("start").toString();
                String  end = rs.getTimestamp("end").toString();
                String  last = rs.getTimestamp("last").toString();
                objects[count] = new Object[]{cardNumber, passWord, balance,
                totalUse, useTimes, status, start, end, last};
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
