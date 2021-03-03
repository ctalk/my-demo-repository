## Revature Coursework - Robert Kiesling

This is my repository of coursework for Revature company.

For further information, contact:  rkies@cpan.org

## BankProject App Setup

### Setup
The Bank Projects is designed to be used with a JDBC connection to PostgreSQL.  It
is typically run with the following authentication information defined in environment
variables:

- Java Environment
  - DB_USER - the database login.
  - DB_PASSWORD - the database password.
  - DB_URL - The JDBC URL of the database.  (Ex: jdbc:postgresql://localhost:5432/)

### Initialization
The program initializes the bank_app schema (basically, everything in
the name space "bank_app") and its tables if they are not present.
The initialization also creates the account, "admin," which you can
use to start creating users and accounts.

- Log in as admin (username: "admin" password: "admin").
- The menu options allow you to create employee accounts, clear the
  data from the DBMS, and generate test data.

### Tests
Some of the app's tests for successful operations use DBMS data that
you can generate from the admin menu.  So in order to run the tests,
log in as admin, and select the option, "Generate test data."  The
test data can co-exist with actual data, so you can select this option
at any time.

#### DBMS Initialization
When the bank app starts for the first time, it checks for the
presence of a schema which is known, simply, as, "bank_app."

If for some reason the DBMS has left one of the tables in an unstable
state, or you've accidentally deleted one of the tables, you can clear
them from the DBMS by entering the following SQL queries from your
favorite SQL front end.

drop table bank_app.users;
drop table bank_app.balances;
drop table bank_app.transactions;
drop schema bank_app;

This will cause the Bank App to re-create is schema and tables
the next time it is run.

Please remember to back up any existing data before re-creating the
tables, if they contain actual bank records.


