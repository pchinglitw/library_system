
CREATE TABLE "User" (
    user_id SERIAL PRIMARY KEY,
    phone_number VARCHAR(20) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    user_name VARCHAR(100) NOT NULL,
    registration_time TIMESTAMP NOT NULL,
    last_login_time TIMESTAMP
);

CREATE TABLE Book_Status (
    status_id VARCHAR(20) PRIMARY KEY,
    status_name VARCHAR(255) NOT NULL
);

CREATE TABLE Book (
    isbn VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    author VARCHAR(100) NOT NULL,
    introduction TEXT
);

CREATE TABLE Inventory (
    inventory_id SERIAL PRIMARY KEY,
    isbn VARCHAR(20) NOT NULL,
    store_time TIMESTAMP NOT NULL,
    status_id VARCHAR(20) NOT NULL,
	FOREIGN KEY (isbn) REFERENCES Book(isbn),
    FOREIGN KEY (status_id) REFERENCES Book_Status(status_id)
);

CREATE TABLE Borrowing_Record (
	record_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    inventory_id INT NOT NULL,
    borrowing_time TIMESTAMP NOT NULL,
    return_time TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES "User"(user_id),
    FOREIGN KEY (inventory_id) REFERENCES Inventory(inventory_id)
);
CREATE INDEX idx_borrowingrecord_userid ON Borrowing_Record(user_id);
CREATE INDEX idx_borrowingrecord_inventoryid ON Borrowing_Record(inventory_id);


CREATE OR REPLACE PROCEDURE get_all_books(out_cursor OUT REFCURSOR)
LANGUAGE plpgsql AS $$
BEGIN
  OPEN out_cursor FOR SELECT * FROM book;
END;
$$;

CREATE OR REPLACE PROCEDURE get_all_inventory(out_cursor OUT REFCURSOR)
LANGUAGE plpgsql AS $$
BEGIN
  OPEN out_cursor FOR SELECT * FROM inventory;
END;
$$;

CREATE OR REPLACE PROCEDURE get_inventory_by_id(IN inventoryId INTEGER, OUT out_cursor REFCURSOR)
LANGUAGE plpgsql AS $$
BEGIN
  OPEN out_cursor FOR SELECT * FROM inventory WHERE inventory.inventory_id = inventoryId;
END;
$$;

CREATE OR REPLACE PROCEDURE update_inventory_status(inventoryIds INT[], newStatus INT)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE Inventory
    SET status_id = newStatus
    WHERE inventory_id = ANY(inventoryIds);
END;
$$;

CREATE OR REPLACE PROCEDURE insert_book_record(userId INT, inventoryIds INT[])
LANGUAGE plpgsql
AS $$
DECLARE
    inventoryId INT;
BEGIN
    FOREACH inventoryId IN ARRAY inventoryIds LOOP
        INSERT INTO borrowing_record (user_id, inventory_id, borrowing_time)
        VALUES (userId, inventoryId, NOW());
    END LOOP;
END;
$$;

CREATE OR REPLACE PROCEDURE get_all_status_name(OUT ref_cursor REFCURSOR)
LANGUAGE plpgsql
AS $$
BEGIN
    OPEN ref_cursor FOR SELECT * FROM book_status;
END;
$$;

CREATE OR REPLACE PROCEDURE get_record(recordId INT, OUT ref_cursor REFCURSOR)
LANGUAGE plpgsql
AS $$
BEGIN
    OPEN ref_cursor FOR SELECT * FROM borrowing_record WHERE borrowing_record.record_id = recordId;
END;
$$;

CREATE OR REPLACE PROCEDURE get_record_by_user_id(userId INT, OUT ref_cursor REFCURSOR)
LANGUAGE plpgsql
AS $$
BEGIN
    OPEN ref_cursor FOR SELECT * FROM borrowing_record WHERE borrowing_record.user_id = userId;
END;
$$;

CREATE OR REPLACE PROCEDURE update_record(recordIdList INT[])
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE borrowing_record
    SET return_time = NOW()
    WHERE record_id = ANY(recordIdList);
END;
$$;

CREATE OR REPLACE PROCEDURE insert_new_user(phoneNum TEXT, pwd TEXT, userName TEXT)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO "User" (phone_number, password_hash, user_name, registration_time, last_login_time)
    VALUES (phoneNum, pwd, userName, NOW(), NOW());
END;
$$;


CREATE OR REPLACE PROCEDURE get_user_by_phone_number(phoneNum TEXT, OUT ref_cursor REFCURSOR)
LANGUAGE plpgsql
AS $$
BEGIN
    OPEN ref_cursor FOR SELECT * FROM "User" WHERE "User".phone_number = phoneNum;
END;
$$;