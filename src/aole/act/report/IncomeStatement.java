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
 * File: IncomeStatement.java
 * Author: Bhupendra Aole
 * Date: Oct 2, 2009: 
 * 
 * Revisions:
 * ----------
 */

package aole.act.report;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import aole.act.db.DBConnection;

import com.toedter.calendar.JDateChooser;

public class IncomeStatement implements ActionListener {
	private JTable table;
	private ArrayList data[];

	JDateChooser calt, calf;
	static SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd");

	public IncomeStatement() {
		showParams();
	}

	private void showParams () {
		JFrame jf = new JFrame("Parameters");
		Container cp = jf.getContentPane();
		cp.setLayout(new GridLayout(3, 1));

		JPanel dfp = new JPanel();
		dfp.add(new JLabel("From Date:"));
		calf = new JDateChooser(new Date());
		dfp.add(calf);

		JPanel dtp = new JPanel();
		dtp.add(new JLabel("To Date:"));
		calt = new JDateChooser(new Date());
		dtp.add(calt);

		cp.add(dfp);
		cp.add(dtp);
		JButton btn = new JButton("Prepare Income Statement");
		btn.setActionCommand("run");
		btn.addActionListener(this);
		cp.add(btn);

		jf.pack();
		jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jf.setVisible(true);
	}

	private void prepareData () {
		String datef = dbFormat.format(calf.getDate());
		String datet = dbFormat.format(calt.getDate());

		data = new ArrayList[3];
		data[0] = new ArrayList();
		data[1] = new ArrayList();
		data[2] = new ArrayList();

		data[0].add("Revenue");
		data[1].add("");
		data[2].add("");

		String ss = "select a.account_name, sum(j.amount_cr) from journals j, accounts a where "
				+ "j.account_id = a.account_id and "
				+ "a.segment1 = 3 and "
				+ "a.segment2 != 1 and "
				+ "j.journal_date >= '"
				+ datef
				+ "' and "
				+ "j.journal_date <= '"
				+ datet
				+ "' and "
				+ "j.amount_cr > 0 "
				+ "group by segment1, segment2 "
				+ "order by j.amount_cr desc";
		Connection con = DBConnection.getConnection();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(ss);
			while (rs.next()) {
				// account name
				data[0].add("  " + rs.getString(1));
				// amount
				data[1].add(rs.getString(2));
				data[2].add("");
			}
			rs.close();

			ss = "select sum(j.amount_cr) from journals j, accounts a where "
					+ "j.account_id = a.account_id and "
					+ "a.segment1 = 3 and " + "a.segment2 != 1 and "
					+ "j.journal_date >= '" + datef + "' and "
					+ "j.journal_date <= '" + datet + "' and "
					+ "j.amount_cr > 0";
			rs = stmt.executeQuery(ss);
			rs.next();
			data[0].add("Total revenue");
			data[1].add("");
			data[2].add(rs.getString(1));
			rs.close();

			data[0].add("Expenses");
			data[1].add("");
			data[2].add("");

			ss = "select a.account_name, sum(j.amount_dr) from journals j, accounts a where "
					+ "j.account_id = a.account_id and "
					+ "a.segment1 = 3 and "
					+ "a.segment2 != 1 and "
					+ "j.journal_date >= '"
					+ datef
					+ "' and "
					+ "j.journal_date <= '"
					+ datet
					+ "' and "
					+ "j.amount_dr > 0 "
					+ "group by segment1, segment2 "
					+ "order by j.amount_dr desc";
			rs = stmt.executeQuery(ss);
			while (rs.next()) {
				// account name
				data[0].add("  " + rs.getString(1));
				// amount
				data[1].add(rs.getString(2));
				data[2].add("");
			}
			rs.close();

			ss = "select sum(j.amount_dr) from journals j, accounts a where "
					+ "j.account_id = a.account_id and "
					+ "a.segment1 = 3 and " + "a.segment2 != 1 and "
					+ "j.journal_date >= '" + datef + "' and "
					+ "j.journal_date <= '" + datet + "' and "
					+ "j.amount_dr > 0";
			rs = stmt.executeQuery(ss);
			rs.next();
			data[0].add("Total expenses");
			data[1].add("");
			data[2].add(rs.getString(1));
			rs.close();

			ss = "select sum(j.amount_cr) - sum(j.amount_dr) from journals j, accounts a where "
					+ "j.account_id = a.account_id and "
					+ "j.journal_date >= '"
					+ datef
					+ "' and "
					+ "j.journal_date <= '"
					+ datet
					+ "' and "
					+ "a.segment1 = 3 and " + "a.segment2 != 1";
			rs = stmt.executeQuery(ss);
			rs.next();
			data[0].add("Net income");
			data[1].add("");
			data[2].add(rs.getString(1));
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void createAndShowGUI () {
		prepareData();
		TableModel dataModel = new AbstractTableModel() {
			public int getColumnCount () {
				return 3;
			}

			public int getRowCount () {
				return data[0].size();
			}

			public Object getValueAt (int row, int col) {
				return data[col].get(row);
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
		JFrame frame = new JFrame("Income Statement");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frame.getContentPane().add(scrollpane);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed (ActionEvent ae) {
		if (ae.getActionCommand().equals("run")) {
			createAndShowGUI();
		}
	}
}
