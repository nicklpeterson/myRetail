print("Initializing Database");

const adminUser = {
    username: "admin",
    password: "admin"
}

const initialPriceData = [
    {
        productId: "13860428",
        price: 13.49,
        currency: "USD"
    },
    {
        productId: "54456119",
        price: 3.59,
        currency: "USD"
    },
    {
        productId: "13264003",
        price: 4.99,
        currency: "USD"
    },
    {
       productId: "12954218",
       price: 17.55,
       currency: "CAD"
    },
]

db = db.getSiblingDB('my-retail-db');

db.createUser(
    {
        user: "admin",
        pwd: "admin",
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
db.getCollection("price").createIndex("productId")

print("Done Initializing Database");
