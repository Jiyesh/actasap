/*
Accounting101
Copyright (C) 2009  Bhupendra Aole

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
*/
/*
 * File: JournalEO.java
 * Author: Bhupendra Aole
 * Date: Sep 27, 2009: 
 * 
 * Revisions:
 * ----------
 */

package aole.act.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import aole.act.db.DBConnection;

/**
 * 
 */
public class JournalsEO {
	public static void insertRow (String date, String amtdr, String amtcr,
			int act, String descrip) {
		Connection con = DBConnection.getConnection();
		try {
			Statement stmt = con.createStatement();
			String query = "insert into journals (journal_date, amount_dr, amount_cr, account_id, description) values ";
			query = query + "('" + date + "'," + amtdr + "," + amtcr + ","
					+ act + ",'" + descrip + "')";
			System.out.println(query);
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
