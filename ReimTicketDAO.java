package com.revature.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.model.ReimTicket;
import com.revature.utility.JDBCUtility;

public class ReimTicketDAO {

	// private String reimInfo;

	public List<ReimTicket> getAllReimTickets() throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {
			List<ReimTicket> reimtickets = new ArrayList<>();

			String sql = "SELECT reimticket_id, reimticket_type, reimstatus, resolver_id, reimauthor_id, reiminfo, reimamount FROM reimtickets";
			PreparedStatement pstmt = con.prepareStatement(sql);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int reimticketId = rs.getInt("reimticket_id");
				String reimticketType = rs.getString("reimticket_type");
				String reimstatus = rs.getString("reimstatus");
				int resolverId = rs.getInt("resolver_id");
				int reimauthorId = rs.getInt("reimauthor_id");
				String reimInfo = rs.getString("reiminfo");
				double reimAmount = rs.getDouble("reimamount");

				ReimTicket reimticket = new ReimTicket(reimticketId, reimticketType, reimstatus, resolverId, reimInfo,
						reimauthorId, reimAmount);

				reimtickets.add(reimticket);
			}

			return reimtickets;
		}
	}

	// ------------------made changes here check to see if it changed functionality
	// this is used to populate the the table on employee homepage...

	public List<ReimTicket> getAllReimTicketsByEmployee(int reimauthorId) throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {
			List<ReimTicket> reimtickets = new ArrayList<>();

			String sql = "SELECT reimticket_id, reimticket_type, reimstatus, resolver_id, reimauthor_id, reiminfo, reimamount FROM reimtickets WHERE reimauthor_id = ?";

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, reimauthorId);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int reimticketId = rs.getInt("reimticket_id");
				String reimticketType = rs.getString("reimticket_type");
				String reimStatus = rs.getString("reimstatus");
				int resolverId = rs.getInt("resolver_id");
				//int reimauthorId = rs.getInt("reimauthor_id");
				String reimInfo = rs.getString("reiminfo"); // reim descip added here
				double reimamount = rs.getDouble("reimamount");

				ReimTicket reimticket = new ReimTicket(reimticketId, reimticketType, reimStatus, resolverId, reimInfo, reimauthorId,
						 reimamount); // reim descrip added here

				reimtickets.add(reimticket);
			}

			return reimtickets;
		}
	}
	// ------------------------------------------------------------------------------------------------

	public ReimTicket getReimTicketById(int reimticketId) throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {

			String sql = "SELECT reimticket_id, reimticket_type, reiminfo, reimstatus, resolver_id, reimauthor_id, reimamount FROM reimtickets WHERE reimticket_id= ?";

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, reimticketId);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				int id = rs.getInt("reimticket_id"); // when changing id to reimticketId I get a duplicate local
														// variable error
				String reimticketType = rs.getString("reimticket_type");
				String reimStatus = rs.getString("reimstatus");
				int resolverId = rs.getInt("resolver_id");
				String reimInfo = rs.getString("reiminfo");
				int reimauthorId = rs.getInt("reimauthor_id");
				double reimAmount = rs.getDouble("reimamount");
				

				return new ReimTicket(id, reimticketType, reimStatus, resolverId, reimInfo, reimauthorId, reimAmount);
			} else {
				return null;
			}

		}
	}

	public void changeReimStatus(int reimticketId, String reimStatus, String reimInfo, int resolverId)
			throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "UPDATE reimtickets " + "SET " + "reimstatus = ?, " + "resolver_id = ?, " + "reiminfo = ? "
					+ "WHERE reimticket_id = ?";

			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, reimStatus);
			pstmt.setInt(2, resolverId);
			pstmt.setString(3, reimInfo); // may need to be changed or deleted.
			pstmt.setInt(4, reimticketId);
			
			

			int updatedCount = pstmt.executeUpdate();

			if (updatedCount != 1) {
				throw new SQLException("Something bad occurred when trying to update reimticket status");
			}
		}

	}

	public ReimTicket addReimTicket(String reimticketType, int reimauthorId, String reimInfo, InputStream content, double reimamount)
			throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {
			con.setAutoCommit(false); // Turn off autocommit

			String sql = "INSERT INTO reimtickets (reimticket_type, reimauthor_id, reiminfo, reimticket_image, reimamount)"
					+ " VALUES (?,?,?,?,?);";

			PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, reimticketType);
			pstmt.setInt(2, reimauthorId);
			pstmt.setString(3, reimInfo);
			pstmt.setBinaryStream(4, content);
			pstmt.setDouble(5, reimamount);

			int numberOfInsertedRecords = pstmt.executeUpdate();

			if (numberOfInsertedRecords != 1) {
				throw new SQLException("Issue occurred when adding reimticket");
			}

			ResultSet rs = pstmt.getGeneratedKeys();

			rs.next();
			int generatedId = rs.getInt(1);

			con.commit(); // COMMIT

			return new ReimTicket(generatedId, reimticketType, "Pending", 0, reimInfo, reimauthorId, reimamount);
		}
	}

	public InputStream getImageFromReimTicketById(int reimticketId) throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "SELECT reimticket_image FROM reimtickets WHERE reimticket_id = ?";

			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, reimticketId);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				InputStream image = rs.getBinaryStream("reimticket_image");

				return image;
			}

			return null;
		}
	}
}
