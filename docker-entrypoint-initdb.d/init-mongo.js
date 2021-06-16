print("Initializing Database");

const adminUser = {
    username: "admin",
    password: "admin"
}

const initialPriceData = [
    {
        id: 13860428,
        price: 13.49,
        currency: "USD"
    },
    {
        id: 54456119,
        price: 3.59,
        currency: "USD"
    },
    {
        id: 13264003,
        price: 4.99,
        currency: "USD"
    },
    {
        id: 12954218,
        price: 17.55,
        currency: "CAD"
    },
]

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
db.createCollection('user');
db.createCollection('price');

db.getCollection("user").insert(adminUser);
db.getCollection("price").insert(initialPriceData);

print("Done Initializing Database");
