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

package aole.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import aole.db.DBConnection;

/**
 * 
 */
public class JournalEO {
	public static void insertRow (String date, String amount, int dr, int cr,
			String descrip) {
		Connection con = DBConnection.getConnection();
		try {
			Statement stmt = con.createStatement();
			String query = "insert into journals (journal_date, amount, dr_account, cr_account, description) values ";
			query = query + "('" + date + "'," + amount + "," + dr + "," + cr
					+ ",'" + descrip + "')";
			System.out.println(query);
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
