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
            "username,password,name,balance)" +
            "values (?,?,?,?)";

    pstmt = conn.prepareStatement(sql);
    pstmt.setString(1,banking.getUsername());
    pstmt.setString(2,banking.getPassword());
    pstmt.setString(3,banking.getName());
    pstmt.setInt(4,banking.getBalance());
    ;
        return  pstmt.executeUpdate()>= 0 ? MESSENGER.SUCCESS :MESSENGER.FAIL;
    }
}
