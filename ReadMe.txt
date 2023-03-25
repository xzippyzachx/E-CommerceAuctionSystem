Github: https://github.com/xzippyzachx/E-CommerceAuctionSystem

Follow these instructions to setup and run the E-Commerce Auction System

|PostgresSQL Database Setup|

Step 1 - Ensure Docker Desktop is installed on your machine

Step 2 - Run these commands to pull the postgresql database docker images:

docker pull xzippyzachx/postgres_users
docker pull xzippyzachx/postgres_items
docker pull xzippyzachx/postgres_auctions
docker pull xzippyzachx/postgres_payments

Step 3 - Run these commands to create and run docker containers of the images:

docker run --name postgres_users -d -p 5432:5432 xzippyzachx/postgres_users
docker run --name postgres_items -d -p 5433:5432 xzippyzachx/postgres_items
docker run --name postgres_auctions -d -p 5434:5432 xzippyzachx/postgres_auctions
docker run --name postgres_payments -d -p 5435:5432 xzippyzachx/postgres_payments


|Running The Servers|
Step 1 - Import the E-CommerceAuctionSystem.zip into your IDE (Intellij, Eclipse)
Step 2 - Run each server as a maven spring project
