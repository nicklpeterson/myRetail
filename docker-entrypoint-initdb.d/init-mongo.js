print("Initializing Database");

/*
The security layer assumes we are only storing encrypted passwords. So, it will try to decrypt the password
during authentication. Here we are just storing admin hashed with Bcrypt.
 */
const adminUser = {
    username: "admin",
    password: "$2a$04$P8Wwwbtm246F0XxRK1E7wOuei0mvjIDVyyWeQrYGXBHyRp3p92fFa" // 'admin' encrypted
}

const initialPriceData = [
    {
        _id: "13860428",
        price: 13.49,
        currency: "USD"
    },
    {
        _id: "54456119",
        price: 3.59,
        currency: "USD"
    },
    {
        _id: "13264003",
        price: 4.99,
        currency: "CAD"
    },
    {
        _id: "12954218",
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
