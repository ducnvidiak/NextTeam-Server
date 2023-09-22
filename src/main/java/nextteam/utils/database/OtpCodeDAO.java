/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam.utils.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import nextteam.utils.RandomGenerator;
import nextteam.utils.SQLDatabase;

/**
 *
 * @author vnitd
 */
public class OtpCodeDAO extends SQLDatabase {

    public OtpCodeDAO(Connection connection) {
        super(connection);
    }

    public String generateOtp(int expiredInSecond, int userId) {
        executeUpdatePreparedStatement("UPDATE otpCode SET isDisable=1 WHERE expiredAt < GETDATE() AND isDisable=0");
        expiredInSecond *= 1000;
        String type = "NextTeam-" + RandomGenerator.simpleRandString(41);
        String code = RandomGenerator.randString(RandomGenerator.NUMERIC_CHARACTER, 6);
        int res = executeUpdatePreparedStatement(
                "INSERT INTO otpCode (userId, code, type, expiredAt)"
                + "VALUES (?, ?, ?, ?)",
                userId,
                code,
                type,
                new Date(new Date().getTime() + expiredInSecond)
        );
        if (res == 0) {
            throw new RuntimeException("Error while insert into the database!");
        }

        return type;
    }

    public boolean verifyOtp(String code, String type, int[] chance) {
        ResultSet rs = executeQueryPreparedStatement(
                "SELECT id, code FROM otpCode "
                + "WHERE isDisable=? AND type=?",
                0,
                type
        );
        try {
            if (rs.next()) {
                int otpId = rs.getInt(1);
                String dbCode = rs.getString(2);
                System.out.println(dbCode + " " + code);
                if (code.equals(dbCode)) {
                    executeUpdatePreparedStatement("UPDATE otpCode SET isDisable=? WHERE type=?", 1, type);
                    return true;
                } else {
                    String storedProcedureCall = "{call P_otpLostChance(?, ?)}";
                    CallableStatement callableStatement = getConnection().prepareCall(storedProcedureCall);
                    callableStatement.setInt(1, otpId);
                    callableStatement.registerOutParameter(2, Types.INTEGER);
                    callableStatement.execute();
                    int returnValue = callableStatement.getInt(2);
                    chance[0] = returnValue;
                    return false;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(OtpCodeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }
}
