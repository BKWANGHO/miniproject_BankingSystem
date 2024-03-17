package repository;

import DTO.Banking;
import bankingEnum.MESSENGER;

import java.sql.*;

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
//        String sql = "update banking set balance = ? where accountNumber = ? ";
        String sql = "insert into banking(balance,accountNumber,transation) values (?,?,?)";
        pstmt=conn.prepareStatement(sql);
        pstmt.setInt(1,banking.getBalance());
        pstmt.setString(2,banking.getAccountNumber());
        pstmt.setString(3,banking.getTransation());

        return pstmt.executeUpdate()>= 0 ? MESSENGER.SUCCESS :MESSENGER.FAIL;
    }
}
