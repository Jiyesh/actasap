-- Run from user actuser/v1skl45D in schema actasap

-- Create accounts table
create table accounts (
  account_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  account_name VARCHAR(80),
  segment1 INTEGER,
  segment2 INTEGER,
  description VARCHAR(500)
);

-- Create journals table
create table journals (
  journal_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  journal_date DATE,
  amount DECIMAL (10,2),
  dr_account INTEGER,
  cr_account INTEGER,
  description VARCHAR(500)
);

-- Seeded Accounts
insert into accounts (account_name, segment1, segment2) values ('Cash',1,1);
insert into accounts (account_name, segment1, segment2) values ('Capital',3,1);
insert into accounts (account_name, segment1, segment2) values ('Notes Payable',2,1);
insert into accounts (account_name, segment1, segment2) values ('Prepaid Rent',1,2);
insert into accounts (account_name, segment1, segment2) values ('Equipment',1,3);
insert into accounts (account_name, segment1, segment2) values ('Accounts Receivable',1,4);
insert into accounts (account_name, segment1, segment2) values ('Service Revenue',3,2);
insert into accounts (account_name, segment1, segment2) values ('Unearned Revenue',2,2);
insert into accounts (account_name, segment1, segment2) values ('Salary Expense',3,3);
insert into accounts (account_name, segment1, segment2) values ('Utility Expense',3,4);
insert into accounts (account_name, segment1, segment2) values ('Inventory',1,5);
insert into accounts (account_name, segment1, segment2) values ('Sales Revenue',3,5);
insert into accounts (account_name, segment1, segment2) values ('Interest Payable',2,3);
insert into accounts (account_name, segment1, segment2) values ('Rent Expense',3,6);
insert into accounts (account_name, segment1, segment2) values ('Depreciation Expense',3,7);
insert into accounts (account_name, segment1, segment2) values ('Accounts Payable',2,3);
insert into accounts (account_name, segment1, segment2) values ('Utilites Payable',3,8);
insert into accounts (account_name, segment1, segment2) values ('Cost of Goods',3,9);
insert into accounts (account_name, segment1, segment2) values ('Interest Expense',3,10);
