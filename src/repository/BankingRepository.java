package repository;

import DTO.Banking;
import bankingEnum.MESSENGER;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BankingRepository {
    private static BankingRepository instance;

    static {
        try {
            instance = new BankingRepository();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private BankingRepository() throws SQLException {
    this.conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/turingdb",
            "turing","password"
    );
    this.pstmt = null;
    this.rs = null;
    }

    public static BankingRepository getInstance() {
        return instance;
    }

    public MESSENGER join(Banking banking) throws SQLException {
    String sql = "insert into banking(" +
            "username,password,name,balance,accountNumber,accountdate)" +
            "values (?,?,?,?,?,?)";

    pstmt = conn.prepareStatement(sql);
    pstmt.setString(1,banking.getUsername());
    pstmt.setString(2,banking.getPassword());
    pstmt.setString(3,banking.getName());
    pstmt.setInt(4,banking.getBalance());
    pstmt.setString(5,banking.getAccountNumber());
    pstmt.setDate(6, new java.sql.Date(System.currentTimeMillis()));

        return  pstmt.executeUpdate()>= 0 ? MESSENGER.SUCCESS :MESSENGER.FAIL;
    }

    public MESSENGER login(Banking banking) throws SQLException {
        String sql ="select username from banking where username = ? and password=?";
        pstmt=conn.prepareStatement(sql);
        pstmt.setString(1,banking.getUsername());
        pstmt.setString(2,banking.getPassword());
        rs = pstmt.executeQuery();

        return rs.next() ? MESSENGER.SUCCESS : MESSENGER.FAIL;
    }

    public MESSENGER deposit(Banking banking) throws SQLException {
        String sql = "update banking set balance = balance + ?,accountdate = ? where accountNumber = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,banking.getBalance());
        pstmt.setDate(2,new java.sql.Date(System.currentTimeMillis()));
        pstmt.setString(3, banking.getAccountNumber());

        return pstmt.executeUpdate() >=0 ? MESSENGER.SUCCESS : MESSENGER.FAIL;
    }

    public MESSENGER withdraw(Banking banking) throws SQLException {
        String sql = "update banking set balance = balance -?, accountdate = ? where accountNumber = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,banking.getBalance());
        pstmt.setDate(2,new java.sql.Date(System.currentTimeMillis()));
        pstmt.setString(3, banking.getAccountNumber());

        return pstmt.executeUpdate() >=0 ? MESSENGER.SUCCESS : MESSENGER.FAIL;
    }

    public void historySave(Banking banking) throws SQLException {
        String sql = "insert into history(balance,accountNumber,transation,accountdate) values (?,?,?,?)";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,banking.getBalance());
        pstmt.setString(2,banking.getAccountNumber());
        pstmt.setString(3,banking.getTransation());
        pstmt.setDate(4,new java.sql.Date(System.currentTimeMillis()));
        pstmt.executeUpdate();
    }
    public void historySave(Banking banking,Banking recevier) throws SQLException {
        String sql = "insert into history(balance,accountNumber,transation,accountdate) values (?,?,concat(?,(select name from banking where accountNumber = ?)),?)";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,banking.getBalance());
        pstmt.setString(2,banking.getAccountNumber());
        pstmt.setString(3,banking.getTransation());
        pstmt.setString(4,recevier.getAccountNumber());
        pstmt.setDate(5,new java.sql.Date(System.currentTimeMillis()));
        pstmt.executeUpdate();
    }

    public MESSENGER accountList() throws SQLException {
        String sql = "select * from banking ";
        pstmt= conn.prepareStatement(sql);
        rs = pstmt.executeQuery();
        List<Banking> ls = new ArrayList<>();
        if (rs.next()){
            do{
            ls.add(Banking.builder()
                    .username(rs.getString("username"))
                    .name(rs.getString("name"))
                    .balance(rs.getInt("balance"))
                    .accountNumber(rs.getString("accountNumber"))
                    .build());
            }
            while (rs.next());
        }else {
            System.out.println("데이터가 없습니다.");
        }
        ls.forEach(System.out::println);
        return MESSENGER.SUCCESS ;
    }

    public MESSENGER bankingHistory(Banking banking) throws SQLException {
        String sql = "select * from history where accountNumber = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,banking.getAccountNumber());
        rs = pstmt.executeQuery();
        if(rs.next()){
            do{
                System.out.printf("balance : %d, transation : %s, Date : %s\n"
                        ,rs.getInt("balance"),rs.getString("transation"),rs.getDate("accountdate"));
            }while (rs.next());
        }else {
            System.out.println("데이터가 없습니다.");
        }

        return getTotalBalance(banking);
    }
    public MESSENGER getTotalBalance(Banking banking) throws SQLException {
        String sql = "select username, accountNumber,balance from banking where accountNumber = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,banking.getAccountNumber());
        rs = pstmt.executeQuery();
        if(rs.next()){
            do{
                System.out.printf("이름 : %s, 계좌번호 : %s, 잔액 : %d\n",
                        rs.getString("username"),rs.getString("accountNumber"),rs.getInt("balance"));
            }while (rs.next());
        }else {
            System.out.println("데이터가 없습니다.");
        }
    return MESSENGER.SUCCESS;
    }
    public MESSENGER accountTransfer(Banking receiver, Banking banking) throws SQLException {
        String sql = "UPDATE banking SET accountdate = ?, balance = " +
                "CASE WHEN accountNumber = ? THEN balance - ? " +
                "WHEN accountNumber = ? THEN balance + ?" +
                " ELSE balance END" +
                " WHERE accountNumber IN (?,?);";
        pstmt = conn.prepareStatement(sql);
        pstmt.setDate(1,new java.sql.Date(System.currentTimeMillis()));
        pstmt.setString(2,banking.getAccountNumber());
        pstmt.setInt(3,banking.getBalance());
        pstmt.setString(4,receiver.getAccountNumber());
        pstmt.setInt(5,banking.getBalance());
        pstmt.setString(6,banking.getAccountNumber());
        pstmt.setString(7,receiver.getAccountNumber());

        return pstmt.executeUpdate() >=0 ? MESSENGER.SUCCESS : MESSENGER.FAIL;
    }
}
