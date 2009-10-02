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
 * File: BalanceSheet.java
 * Author: Bhupendra Aole
 * Date: Sep 28, 2009: 
 * 
 * Revisions:
 * ----------
 */

package aole.report;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import aole.db.DBConnection;

public class BalanceSheet {
	private JTable table;
	private ArrayList data[];
	final String[] headers = { "Assets", "Amount", "",
			"Liability/Owners Equity", "Amount" };

	private void prepareData () {
		int i, j;
		data = new ArrayList[headers.length];
		data[0] = new ArrayList();
		data[1] = new ArrayList();
		data[3] = new ArrayList();
		data[4] = new ArrayList();

		String sAsset = "select a.account_name Assets, ifnull(sum(amount_dr), 0) - ifnull(sum(amount_cr),0) Amount"
				+ " from journals j, accounts a"
				+ " where j.account_id = a.account_id"
				+ " and a.segment1 = 1"
				+ " group by segment1, segment2";
		String sLiab = "select a.account_name Liability, ifnull(sum(amount_cr), 0) - ifnull(sum(amount_dr),0) Amount"
				+ " from journals j, accounts a"
				+ " where j.account_id = a.account_id"
				+ " and a.segment1 in (2, 3)" + " group by segment1, segment2";
		Connection con = DBConnection.getConnection();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sAsset);
			while (rs.next()) {
				// account name
				data[0].add(rs.getString(1));
				// amount
				data[1].add(rs.getString(2));
			}
			rs.close();
			i = data[0].size();
			rs = stmt.executeQuery(sLiab);
			while (rs.next()) {
				// account name
				data[3].add(rs.getString(1));
				// amount
				data[4].add(rs.getString(2));
			}
			rs.close();
			j = data[3].size();
			// totals
			int diff = Math.abs(i - j);
			ArrayList al1, al2;
			if (i > j) {
				al1 = data[3];
				al2 = data[4];
			} else {
				al1 = data[0];
				al2 = data[1];
			}
			for (i = 0; i < diff; i++) {
				al1.add(" ");
				al2.add(" ");
			}
			String at = "select ifnull(sum(amount_dr), 0) - ifnull(sum(amount_cr),0) Amount"
					+ " from journals j, accounts a"
					+ " where j.account_id = a.account_id"
					+ " and a.segment1 = 1";
			rs = stmt.executeQuery(at);
			rs.next();
			data[0].add("Total");
			data[1].add(rs.getString(1));
			rs.close();
			String lt = "select ifnull(sum(amount_cr), 0) - ifnull(sum(amount_dr),0) Amount"
					+ " from journals j, accounts a"
					+ " where j.account_id = a.account_id"
					+ " and a.segment1 in (2, 3)";
			rs = stmt.executeQuery(lt);
			rs.next();
			data[3].add("Total");
			data[4].add(rs.getString(1));
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public BalanceSheet() {
		prepareData();
		TableModel dataModel = new AbstractTableModel() {
			public int getColumnCount () {
				return headers.length;
			}

			public int getRowCount () {
				return data[0].size();
			}

			public Object getValueAt (int row, int col) {
				if (col == 2)
					return " ";
				return data[col].get(row);
			}

			public String getColumnName (int column) {
				return headers[column];
			}

			public Class getColumnClass (int col) {
				return getValueAt(0, col).getClass();
			}

			public boolean isCellEditable (int row, int col) {
				return false;
			}
		};
		table = new JTable(dataModel);
		JScrollPane scrollpane = new JScrollPane(table);

		// Create and set up the window.
		JFrame frame = new JFrame("Balance Sheet");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.getContentPane().add(scrollpane);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void main (String[] args) {
		new BalanceSheet();
	}
}
