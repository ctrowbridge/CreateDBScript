This Java project creates a set of scripts to create and populate a Derby database
with city data.

Input data is located in the 'data' directory.
Data Source:  https://nordpil.com/resources/world-database-of-large-cities/

The three output scripts are located in the scripts directory.  Run the CreateDBScript
application to generate scripts. Once the scripts have been generated, you can create
and populate the database with the Derby ij command:
> ij
ij> run 'connectDB.sql';
ij> run 'createDB.sql';
ij> run 'loadDB.sql';
ij> exit;
>
