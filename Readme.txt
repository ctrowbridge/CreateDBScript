This Java project creates a set of scripts to create and populate a Derby database
with city data.

Currently requires Derby - https://db.apache.org/derby/. It shouldn't be too difficult
to modify this for another database.

Input data is located in the 'data' directory.
Data Source:  https://nordpil.com/resources/world-database-of-large-cities/

Usage:
Run the CreateDBScript application to generate scripts. The three output scripts will be
located in the scripts directory. Once the scripts have been generated, you can create
and populate the database with the Derby ij command:
> ij
ij> run 'connectDB.sql';
ij> run 'createDB.sql';
ij> run 'loadDB.sql';
ij> exit;
>
