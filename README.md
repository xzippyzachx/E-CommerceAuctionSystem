## System Design
![image](https://github.com/xzippyzachx/E-CommerceAuctionSystem/assets/33043402/780a36dd-845f-4aa4-8bd6-0063f6c784d8)

## Database Design
![image](https://github.com/xzippyzachx/E-CommerceAuctionSystem/assets/33043402/1b2df9aa-4f2e-4af3-afa6-2f4c100fbf1f)

## Follow these instructions to setup and run the E-Commerce Auction System

### Using Docker Compose

* Step 1 - Ensure Docker Desktop is installed on your machine and running
* Step 2 - Unzip the `E-CommerceAuctionSystem.zip`
* Step 3 - Run the following command in the project root (Location of `docker-compose.yml`)

```bash
docker-compose up
```

### Manual PostgresSQL Database Setup

* Step 1 - Ensure Docker Desktop is installed on your machine and running
* Step 2 - Run these commands to pull the postgresql database docker images:

```bash
# users
docker pull xzippyzachx/postgres_users
# items
docker pull xzippyzachx/postgres_items
# auctions
docker pull xzippyzachx/postgres_auctions
# payments
docker pull xzippyzachx/postgres_payments
```

* Step 3 - Run these commands to create and run docker containers of the images:
```bash
# users
docker run --name postgres_users -d -p 5432:5432 xzippyzachx/postgres_users
# items
docker run --name postgres_items -d -p 5433:5432 xzippyzachx/postgres_items
# auctions
docker run --name postgres_auctions -d -p 5434:5432 xzippyzachx/postgres_auctions
# payments
docker run --name postgres_payments -d -p 5435:5432 xzippyzachx/postgres_payments
```

### Manually Running The Servers via an IDE

* Step 1 - Import the `E-CommerceAuctionSystem.zip` into your IDE (Intellij, Eclipse)
* Step 2 - Run each server as a maven spring project (There are 5 servers)


### Entry URL:
http://localhost:8080/login


### Github Link:
[Github](https://github.com/xzippyzachx/E-CommerceAuctionSystem)
