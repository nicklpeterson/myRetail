print("Initializing Database");

db = db.getSiblingDB('my-retail-db');

db.createUser(
    {
        user: "dev",
        pwd: "123",
        roles: [
            {
                role: "readWrite",
                db :"my-retail-db"
            }
        ]
    }
)
db.createCollection('users');

print("Done Initializing Database");
