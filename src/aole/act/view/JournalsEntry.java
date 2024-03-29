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
 * File: JournalEntry.java
 * Author: Bhupendra Aole
 * Date: Sep 27, 2009: 
 * 
 * Revisions:
 * ----------
 */

package aole.act.view;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import aole.act.db.DBConnection;
import aole.act.misc.SpringUtilities;
import aole.act.model.JournalsEO;

import com.toedter.calendar.JDateChooser;

class CAccounts {
	public int index;
	public String name;

	public CAccounts(int i, String n) {
		index = i;
		name = n;
	}

	public String toString () {
		return name;
	}
}

public class JournalsEntry implements ActionListener {
	JTextField txtAmt, txtDes;
	JComboBox cboDR, cboCR;
	JDateChooser cal;
	static SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd");
	JFrame frame;

	public JournalsEntry() {
		createAndShowGUI();
	}

	private CAccounts[] getAccountNames () {
		Connection con = DBConnection.getConnection();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT count(1) FROM Accounts");
			rs.next();
			int cnt = rs.getInt(1), i = 0;
			CAccounts actnames[] = new CAccounts[cnt];
			ResultSet rs2 = stmt
					.executeQuery("SELECT account_id, account_name FROM Accounts order by account_name");
			while (rs2.next()) {
				actnames[i] = new CAccounts(rs2.getInt(1), rs2.getString(2));
				i++;
			}
			rs.close();
			rs2.close();
			return actnames;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
			}
		}
		return null;
	}

	private void createAndShowGUI () {
		// Create and populate the panel.
		JPanel p = new JPanel(new SpringLayout());
		CAccounts actNames[] = getAccountNames();

		p.add(new JLabel("Date: ", JLabel.TRAILING));
		cal = new JDateChooser();
		cal.setDate(new Date());
		p.add(cal);

		p.add(new JLabel("Amount: ", JLabel.TRAILING));
		txtAmt = new JTextField(10);
		p.add(txtAmt);

		p.add(new JLabel("DR Account: ", JLabel.TRAILING));
		cboDR = new JComboBox(actNames);
		p.add(cboDR);

		p.add(new JLabel("CR Account: ", JLabel.TRAILING));
		cboCR = new JComboBox(actNames);
		p.add(cboCR);

		p.add(new JLabel("Description: ", JLabel.TRAILING));
		txtDes = new JTextField(10);
		p.add(txtDes);

		JButton btnApply = new JButton("Apply");
		btnApply.addActionListener(this);
		btnApply.setActionCommand("insert");

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(this);
		btnExit.setActionCommand("exit");

		p.add(btnExit);
		p.add(btnApply);

		// Lay out the panel.
		SpringUtilities.makeCompactGrid(p, 6, 2, // rows, cols
				6, 6, // initX, initY
				6, 6); // xPad, yPad

		// Create and set up the window.
		frame = new JFrame("Journal Entry Form");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		Container cp = frame.getContentPane();
		cp.add(p);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public void actionPerformed (ActionEvent ae) {
		if ("insert".equals(ae.getActionCommand())) {
			String date = dbFormat.format(cal.getDate());
			JournalsEO.insertRow(date, txtAmt.getText(), "0",
					((CAccounts) cboDR.getSelectedItem()).index, txtDes
							.getText());
			JournalsEO.insertRow(date, "0", txtAmt.getText(),
					((CAccounts) cboCR.getSelectedItem()).index, txtDes
							.getText());
		} else if ("exit".equals(ae.getActionCommand())) {
			DBConnection.closeConnection();
			frame.dispose();
		}
	}
}
