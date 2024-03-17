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
            "jdbc:mysql://localhost:3306/kwanghodb",
            "root","rootroot"
    );
    this.pstmt = null;
    this.rs = null;
    }

    public static BankingRepository getInstance() {
        return instance;
    }

    public MESSENGER join(Banking banking) throws SQLException {
    String sql = "insert into banking(" +
            "username,password,name,balance,accountNumber)" +
            "values (?,?,?,?,?)";

    pstmt = conn.prepareStatement(sql);
    pstmt.setString(1,banking.getUsername());
    pstmt.setString(2,banking.getPassword());
    pstmt.setString(3,banking.getName());
    pstmt.setInt(4,banking.getBalance());
    pstmt.setString(5,banking.getAccountNumber());
    ;
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
        String sql = "update banking set balance = balance + ? where accountNumber = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,banking.getBalance());
        pstmt.setString(2, banking.getAccountNumber());

        return pstmt.executeUpdate() >=0 ? MESSENGER.SUCCESS : MESSENGER.FAIL;
    }

    public MESSENGER withdraw(Banking banking) throws SQLException {
        String sql = "update banking set balance = balance -? where accountNumber = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,banking.getBalance());
        pstmt.setString(2, banking.getAccountNumber());

        return pstmt.executeUpdate() >=0 ? MESSENGER.SUCCESS : MESSENGER.FAIL;
    }

    public void historySave(Banking banking) throws SQLException {
        String sql = "insert into history(balance,accountNumber,transation) values (?,?,?)";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,banking.getBalance());
        pstmt.setString(2,banking.getAccountNumber());
        pstmt.setString(3,banking.getTransation());
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
                System.out.printf("balance : %d, transation : %s\n"
                        ,rs.getInt("balance"),rs.getString("transation"));
            }while (rs.next());
        }else {
            System.out.println("데이터가 없습니다.");
        }

        return MESSENGER.SUCCESS;
    }
}
