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
 * File: AccountsEO.java
 * Author: Bhupendra Aole
 * Date: Sep 28, 2009: 
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
public class AccountsEO {
	public static void insertRow (String name, int seg1, int seg2,
			String descrip) {
		Connection con = DBConnection.getConnection();
		try {
			Statement stmt = con.createStatement();
			String query = "insert into accounts (account_name, segment1, segment2, description) values ";
			query = query + "('" + name + "'," + seg1 + "," + seg2 + ",'"
					+ descrip + "')";
			System.out.println(query);
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
