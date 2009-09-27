-- Create database schema 'actasap'
CREATE DATABASE actasap;

-- Create user actprog
GRANT SELECT,INSERT,UPDATE,DELETE,CREATE,DROP
  ON actasap.* TO 'actuser'@'localhost' IDENTIFIED BY 'v1skl45D';