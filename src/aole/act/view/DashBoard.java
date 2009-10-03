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
 * File: DashBoard.java
 * Author: Bhupendra Aole
 * Date: Oct 3, 2009: 
 * 
 * Revisions:
 * ----------
 */

package aole.act.view;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import aole.act.report.BalanceSheet;
import aole.act.report.IncomeStatement;

public class DashBoard implements ActionListener {

	public DashBoard() {
		JFrame jf = new JFrame("DashBoard");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container c = jf.getContentPane();
		c.setLayout(new GridLayout(4, 1));

		JButton tf = new JButton("New Account");
		tf.addActionListener(this);
		tf.setActionCommand("account");
		c.add(tf);

		tf = new JButton("New Journal");
		tf.addActionListener(this);
		tf.setActionCommand("journal");
		c.add(tf);

		tf = new JButton("Show Balance Sheet");
		tf.addActionListener(this);
		tf.setActionCommand("bs");
		c.add(tf);

		tf = new JButton("Show Income Statement");
		tf.addActionListener(this);
		tf.setActionCommand("is");
		c.add(tf);

		jf.pack();
		jf.setVisible(true);
	}

	public static void main (String[] args) {
		new DashBoard();
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed (ActionEvent event) {
		if (event.getActionCommand().equals("account")) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run () {
					new AccountsEntry();
				}
			});
		} else if (event.getActionCommand().equals("journal")) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run () {
					new JournalsEntry();
				}
			});
		} else if (event.getActionCommand().equals("bs")) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run () {
					new BalanceSheet();
				}
			});
		} else if (event.getActionCommand().equals("is")) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run () {
					new IncomeStatement();
				}
			});
		}
	}
}
