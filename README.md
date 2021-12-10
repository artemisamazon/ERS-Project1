# ERS-Project1

ERS (Empolyee Reimbursement System) was created to manage the process of reimbursing employees for expenses that are approved by their employer/company. The system allows employees to submit reimbursement tickets (Reimtickets) to be reviewed by finance managers, submit receipts, and view past tickets that were submitted. Finance managers are authorized to approve Reimtickets submitted by employees. 

The ERS includes a users (either employees or finance managers) table and reimtickets table in the database. Attributes for the users table includes user ID,username, password, first-name, last-name, and user role. Reimtickets table attributes include remticket ID, status, author ID (employee ID) and resolver ID (Finance Manager ID), and reimbursement amount (reimamount). User ID was used as a foreign key to establish a relationship between Users table and reimticket table. 

Java in Spring Tool Suite along with SQL/Postgres in DBeaver was used to code the backend of the database. HTML, CSS, and JS, were used to provide the structuring, styling, and data manipulation on the frontend.  
