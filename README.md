# ERS-Project1

ERS (Empolyee Reimbursement System) was created to manage the process of reimbursing employees for expenses that are approved by their employer/company. The system allows employees to submit reimbursement tickets (Reimtickets) to be reviewed by finance managers, submit receipts, and view past tickets that were submitted. Finance managers are authorized to approve Reimtickets submitted by employees. 

The ERS includes a users (either employees or finance managers) table and reimtickets table in the database. Attributes for the users table includes user ID,username, password, first-name, last-name, and user role. Reimtickets table attributes include remticket ID, status, author ID (employee ID) and resolver ID (Finance Manager ID), and reimbursement amount (reimamount). User ID was used as a foreign key to establish a relationship between Users table and reimticket table. 

Java in Spring Tool Suite along with SQL/Postgres in DBeaver was used to code the backend of the database. HTML, CSS, and JS, were used to provide the structuring, styling, and data manipulation on the frontend.  

Technologies Used:
JDBC, PostgreSQL, Java, JavaScript, HTML, and CSS

Features
* Log in and log out functionalities for Employee and Finance Manager
* Approve of submitted reimbursement ticket.
* Submission of Reimbursement ticket.

To-do list:
* password hashing
* Sorting of tickets by ticket status
* Update styling with CSS

Getting Started
1. Open Terminal.
2. Change the current working directory to the location where you want the cloned directory.
3. Type git clone, and then paste the URL.
      $ git clone https://github.com/artemisamazon/ERS-Project1.git
4. Press enter to create local clone. 
