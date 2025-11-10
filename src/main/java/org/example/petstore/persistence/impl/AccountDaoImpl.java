package org.example.petstore.persistence.impl;

import org.example.petstore.domain.Account;
import org.example.petstore.persistence.AccountDao;
import org.example.petstore.persistence.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDaoImpl implements AccountDao {
    public Account getAccountByUsername(String username) {
        String sql = """
            SELECT
                SIGNON.USERNAME,
                ACCOUNT.EMAIL,
                ACCOUNT.FIRSTNAME,
                ACCOUNT.LASTNAME,
                ACCOUNT.STATUS,
                ACCOUNT.ADDR1 AS address1,
                ACCOUNT.ADDR2 AS address2,
                ACCOUNT.CITY,
                ACCOUNT.STATE,
                ACCOUNT.ZIP,
                ACCOUNT.COUNTRY,
                ACCOUNT.PHONE,
                PROFILE.LANGPREF AS languagePreference,
                PROFILE.FAVCATEGORY AS favouriteCategoryId,
                PROFILE.MYLISTOPT AS listOption,
                PROFILE.BANNEROPT AS bannerOption,
                BANNERDATA.BANNERNAME
            FROM ACCOUNT, PROFILE, SIGNON, BANNERDATA
            WHERE ACCOUNT.USERID = ?
              AND SIGNON.USERNAME = ACCOUNT.USERID
              AND PROFILE.USERID = ACCOUNT.USERID
              AND PROFILE.FAVCATEGORY = BANNERDATA.FAVCATEGORY
            """;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapAccount(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据用户名和密码查询账户信息（登录验证）
     */
    public Account getAccountByUsernameAndPassword(String username, String password) {
        String sql = """
            SELECT
                SIGNON.USERNAME,
                ACCOUNT.EMAIL,
                ACCOUNT.FIRSTNAME,
                ACCOUNT.LASTNAME,
                ACCOUNT.STATUS,
                ACCOUNT.ADDR1 AS address1,
                ACCOUNT.ADDR2 AS address2,
                ACCOUNT.CITY,
                ACCOUNT.STATE,
                ACCOUNT.ZIP,
                ACCOUNT.COUNTRY,
                ACCOUNT.PHONE,
                PROFILE.LANGPREF AS languagePreference,
                PROFILE.FAVCATEGORY AS favouriteCategoryId,
                PROFILE.MYLISTOPT AS listOption,
                PROFILE.BANNEROPT AS bannerOption,
                BANNERDATA.BANNERNAME
            FROM ACCOUNT, PROFILE, SIGNON, BANNERDATA
            WHERE ACCOUNT.USERID = ?
              AND SIGNON.PASSWORD = ?
              AND SIGNON.USERNAME = ACCOUNT.USERID
              AND PROFILE.USERID = ACCOUNT.USERID
              AND PROFILE.FAVCATEGORY = BANNERDATA.FAVCATEGORY
            """;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapAccount(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 更新账户信息（ACCOUNT 表）
     */
    public void updateAccount(Account account) {
        String sql = """
            UPDATE ACCOUNT SET
                EMAIL = ?, FIRSTNAME = ?, LASTNAME = ?, STATUS = ?,
                ADDR1 = ?, ADDR2 = ?, CITY = ?, STATE = ?, ZIP = ?, COUNTRY = ?, PHONE = ?
            WHERE USERID = ?
            """;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, account.getEmail());
            ps.setString(2, account.getFirstName());
            ps.setString(3, account.getLastName());
            ps.setString(4, account.getStatus());
            ps.setString(5, account.getAddress1());
            ps.setString(6, account.getAddress2());
            ps.setString(7, account.getCity());
            ps.setString(8, account.getState());
            ps.setString(9, account.getZip());
            ps.setString(10, account.getCountry());
            ps.setString(11, account.getPhone());
            ps.setString(12, account.getUsername());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 插入账户（ACCOUNT 表）
     */
    public void insertAccount(Account account) {
        String sql = """
            INSERT INTO ACCOUNT
            (EMAIL, FIRSTNAME, LASTNAME, STATUS, ADDR1, ADDR2, CITY, STATE, ZIP, COUNTRY, PHONE, USERID)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, account.getEmail());
            ps.setString(2, account.getFirstName());
            ps.setString(3, account.getLastName());
            ps.setString(4, account.getStatus());
            ps.setString(5, account.getAddress1());
            ps.setString(6, account.getAddress2());
            ps.setString(7, account.getCity());
            ps.setString(8, account.getState());
            ps.setString(9, account.getZip());
            ps.setString(10, account.getCountry());
            ps.setString(11, account.getPhone());
            ps.setString(12, account.getUsername());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新 PROFILE 表
     */
    public void updateProfile(Account account) {
        String sql = "UPDATE PROFILE SET LANGPREF = ?, FAVCATEGORY = ? WHERE USERID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, account.getLanguagePreference());
            ps.setString(2, account.getFavouriteCategoryId());
            ps.setString(3, account.getUsername());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 插入 PROFILE
     */
    public void insertProfile(Account account) {
        String sql = "INSERT INTO PROFILE (LANGPREF, FAVCATEGORY, USERID) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, account.getLanguagePreference());
            ps.setString(2, account.getFavouriteCategoryId());
            ps.setString(3, account.getUsername());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新密码
     */
    public void updateSignon(Account account) {
        String sql = "UPDATE SIGNON SET PASSWORD = ? WHERE USERNAME = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, account.getPassword());
            ps.setString(2, account.getUsername());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 插入 SIGNON
     */
    public void insertSignon(Account account) {
        String sql = "INSERT INTO SIGNON (PASSWORD, USERNAME) VALUES (?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, account.getPassword());
            ps.setString(2, account.getUsername());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 把结果集映射为 Account 实例
     */
    public Account mapAccount(ResultSet rs) {
        Account account = new Account();
        try {
            account.setUsername(rs.getString("USERNAME"));
            account.setEmail(rs.getString("EMAIL"));
            account.setFirstName(rs.getString("FIRSTNAME"));
            account.setLastName(rs.getString("LASTNAME"));
            account.setStatus(rs.getString("STATUS"));
            account.setAddress1(rs.getString("address1"));
            account.setAddress2(rs.getString("address2"));
            account.setCity(rs.getString("CITY"));
            account.setState(rs.getString("STATE"));
            account.setZip(rs.getString("ZIP"));
            account.setCountry(rs.getString("COUNTRY"));
            account.setPhone(rs.getString("PHONE"));
            account.setLanguagePreference(rs.getString("languagePreference"));
            account.setFavouriteCategoryId(rs.getString("favouriteCategoryId"));
            account.setBannerName(rs.getString("BANNERNAME"));
        }catch (SQLException e) {
            e.printStackTrace();
        }
        // 如果 PROFILE.MYLISTOPT / BANNEROPT 是 tinyint(1)，可转 boolean
        try {
            account.setListOption(rs.getInt("listOption") == 1);
            account.setBannerOption(rs.getInt("bannerOption") == 1);
        } catch (SQLException ignored) {}
        return account;
    }
}
