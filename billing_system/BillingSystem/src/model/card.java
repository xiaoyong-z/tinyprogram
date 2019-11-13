package model;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Scanner;

public class card implements Serializable {
    private String cardNumber; //卡号，
    private String passWard; //密码

    private double balance; //余额
    private double totalUse; //累计金额

    private int useTimes; //使用次数
    private int status; //状态(0-未使用, 1-正在使用, 2-已注销, 3-失效）
    private int del; //删除标示 0-未删除，1-删除

    private Calendar start;//开卡时间
    private Calendar end;//卡的截止时间
    private Calendar last;//最后使用时间

    public double getTotalUse() {
        return totalUse;
    }

    public int getUseTimes() {
        return useTimes;
    }

    public int getDel() {
        return del;
    }

    public Calendar getStart() {
        return start;
    }

    public Calendar getEnd() {
        return end;
    }

    public Calendar getLast() {
        return last;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setPassWard(String passWard) {
        this.passWard = passWard;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setTotalUse(double totalUse) {
        this.totalUse = totalUse;
    }

    public void setUseTimes(int useTimes) {
        this.useTimes = useTimes;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setDel(int del) {
        this.del = del;
    }

    public void setStart(Calendar start) {
        this.start = start;
    }

    public void setEnd(Calendar end) {
        this.end = end;
    }

    public void setLast(Calendar last) {
        this.last = last;
    }

    public String getPassWard() {
        return passWard;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public int getStatus() {
        return status;
    }

    public double getBalance() {
        return balance;
    }


    public card(String cardNumber, String passWard, double balance,
                double totalUse, int useTimes, int status, int del,
                Timestamp start, Timestamp end, Timestamp last){
        this.cardNumber = cardNumber;
        this.passWard = passWard;
        this.balance = balance;
        this.totalUse = totalUse;
        this.useTimes = useTimes;
        this.status = status;
        this.del = del;
        this.start = Calendar.getInstance();
        this.start.setTime(start);
        this.end = Calendar.getInstance();
        this.end.setTime(end);
        this.last = Calendar.getInstance();
        this.last.setTime(last);
    }

    public card(String cardNumber, String passWord, double balance){
        Scanner sc = new Scanner(System.in);
        this.cardNumber = cardNumber;
        this.passWard = passWord;
        this.balance = balance;


        //初始化其他参数
        this.status = 0;
        this.totalUse = 0;
        this.del = 0;
        this.useTimes = 0;
        this.start = Calendar.getInstance();
        this.end = Calendar.getInstance();
        this.last = Calendar.getInstance();
    }

//    public card(){
//        Scanner sc = new Scanner(System.in);
//        //读入卡号，判断长度是否在1~18
//        this.cardNumber = CardManage.getCardNumber();
//        //读入密码，判断长度是否在1~8
//        this.passWard = CardManage.getCardPasswd();
//
//        //读入金额,判断金额是否大于30,这里假设网吧开卡至少要30元
//        do {
//            System.out.println("请输入开卡金额(开卡至少30元)");
//            double balance = sc.nextDouble();
//            if (balance >= 30) {
//                this.balance = balance;
//                break;
//            }
//            System.out.println("开卡至少30元!");
//        }while(true);
//
//        //初始化其他参数
//        this.status = 0;
//        this.totalUse = 0;
//        this.del = 0;
//        this.useTimes = 0;
//        this.start = Calendar.getInstance();
//        this.end = Calendar.getInstance();
//        this.last = Calendar.getInstance();
//    }


    //将日期按照指定格式输出
//    private String dataShow(Calendar date){
//        int year = date.get(Calendar.YEAR);
//        int month = date.get(Calendar.MONTH) + 1;
//        int day = date.get(Calendar.DAY_OF_MONTH);
//        int hour = date.get(Calendar.HOUR_OF_DAY);
//        int minute = date.get(Calendar.MINUTE);
//        String result = new String(year + "-" + month + "-" + day + " " +
//                hour + ":" + minute);
//        return result;
//    }

//    public void stopUpdate(double balance, Calendar end, double cost){
//        this.balance = balance;
//        this.last = end;
//        this.status = 0;
//        this.totalUse += cost;
//    }

//    public void useUpdate(){
//        this.useTimes += 1;
//        this.status = 1;
//    }

//    public void topUpUpdate(double amount){
//        this.balance += amount;
//    }

//    public void drawBackUpdate(){
//        this.balance = 0;
//    }

//    public void cancelUpdate(){
//        this.balance = 0;
//        this.status = 2;
//    }


//    public void stopShow(Calendar start, double cost){
//        System.out.println("卡号\t消费\t余额\t上机时间\t下机时间");
//        System.out.println(this.cardNumber + "\t" + cost + "\t" + this.balance + "\t"
//                + dataShow(this.start) + "\t" + dataShow(this.last) );
//    }

//    public void addShow(){
//        System.out.println("卡号\t密码\t状态\t开卡余额");
//        System.out.println(this.cardNumber + "\t" + this.passWard + "\t" + this.status + "\t"
//                          + this.balance    );
//    }

//    public void searchShow(){
//        System.out.println("卡号\t状态\t余额\t累计使用\t使用次数\t上次使用时间");
//        System.out.println(this.cardNumber + "\t" + this.status + "\t"
//                + this.balance + "\t" + this.totalUse + "\t" + this.useTimes + "\t" + dataShow(this.last));
//    }

//    public void useShow(){
//        System.out.println("卡号\t余额\t上机时间");
//        System.out.println(this.cardNumber + "\t" + this.balance + "\t" + dataShow(Calendar.getInstance()));
//    }

//    public void topUpShow(double amount){
//        System.out.println("卡号\t充值金额\t余额");
//        System.out.println(this.cardNumber + "\t" + amount + "\t" + this.balance);
//    }

//    public void drawBackShow(double amount){
//        System.out.println("卡号\t退费金额\t余额");
//        System.out.println(this.cardNumber + "\t" + amount + "\t" + this.balance);
//    }

//    public void cancelShow(double amount){
//        System.out.println("卡号\t退费金额");
//        System.out.println(this.cardNumber + "\t" + amount);
//    }
}
