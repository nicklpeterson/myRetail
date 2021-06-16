### NoSQL Database
This project is using [docker](https://docs.docker.com/install/) to pull up and a local mongoDB database and a simple
 javascript for initializing the database. 

You will need to start the database before running the application:

- `docker-compose up` to start mongoDB + adminer via Docker
- `docker-compose stop && docker-compose rm` to delete the containers and their data

Once the database is running you can inspect it at: 
[http://localhost:5401/?mongo=dbMongo&username=dev&db=my-retail-db](http://localhost:5401/?mongo=dbMongo&username=dev&db=my-retail-db).
If you are prompted to login make sure to set the following fields:
- System: `MongoDB`
- Server: `dbMongo`
- Username: `admin`
- Password: `admin`
- Database: `my-retail-db`

##### Why?
I chose to run the database in docker for two reasons. First, it makes for a very nice development experience.
We can wipe the container and create a new database at any time and interact with the database using the adminer GUI.
Second, since this application will be reviewed locally, I expect that spinning up a local containerized database
will help avoid any machine specific issues. 

##### Future Changes
We may want to add a schema change management tool (like [Mongock](https://github.com/cloudyrock/mongock)) to
 facilitate easy database changes.
 
### Security
The put endpoint for updating product price information is only avilable to users with credentials saved in the
 user collection of the database. The endpoint is secured with authentication and authorization using JSON Web Tokens
  (JWT). 

The following header is required when accessing `/products/price/{id}`, where `xxx.yyy.zzz` is the JWT:
- `Authorization: Bearer xxx.yyy.zzz`

Before accessing the endpoint users must obtain a token using the login endpoint. For Example:

- `curl -i -H "Content-Type: application/json" -X POST -d '{"username": "admin", "password": "admin"}' http://localhost
:8080
/login`

If a user with the above credentials is found in the database, the application will respond with an authorization
 header that contains a JWT that will be valid for 15 minutes.
 


