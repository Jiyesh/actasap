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
 * File: DBConnection.java
 * Author: Bhupendra Aole
 * Date: Sep 27, 2009: 
 * 
 * Revisions:
 * ----------
 */

package aole.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 
 */
public class DBConnection {
	private static Connection con = null;

	public static Connection getConnection () {
		if (con == null) {
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				con = DriverManager
						.getConnection("jdbc:mysql://localhost/actasap?user=actuser&password=v1skl45D");
			} catch (ClassNotFoundException e) {
				System.err.println("ClassNotFoundException: " + e.getMessage());
			} catch (InstantiationException e) {
				System.err.println("InstantiationException: " + e.getMessage());
			} catch (IllegalAccessException e) {
				System.err.println("IllegalAccessException: " + e.getMessage());
			} catch (SQLException e) {
				System.err.println("SQLException: " + e.getMessage());
				System.err.println("SQLState: " + e.getSQLState());
				System.err.println("VendorError: " + e.getErrorCode());
			}
		}
		return con;
	}

	public static void closeConnection () {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
