INSERT INTO "User" (phone_number, password_hash, user_name, registration_time, last_login_time)
VALUES 
('1234567890', 'hashed_password', 'John Doe', NOW(), NOW()),
('0987654321', 'another_hashed_password', 'Jane Smith', NOW(), NOW());

INSERT INTO book_status (status_id, status_name)
VALUES
('1', '在庫'),
('2', '出借中'),
('3', '整理中(歸還後未入庫)'),
('4', '遺失'),
('5', '損毀'),
('6', '廢棄');

INSERT INTO Book (isbn, name, author, introduction)
VALUES 
('123-4-56-789012-3', 'Database Fundamentals', 'Jane Austin', 'An introduction to databases.'),
('987-6-54-321098-7', 'Advanced SQL', 'John Doe', 'Deep dive into advanced SQL techniques.'),
('978-3-16-148410-1', 'To Kill a Mockingbird', 'Harper Lee', 'A novel set in the Deep South that deals with themes of racial injustice and moral growth through the eyes of a young girl'),
('978-3-16-148410-2', '1984', 'George Orwell', 'A dystopian novel exploring the dangers of totalitarianism, surveillance, and public manipulation.'),
('978-3-16-148410-3', 'The Great Gatsby', 'F. Scott Fitzgerald', 'A story of love, wealth, and ambition, set in the roaring twenties, exploring the American dreams decay.'),
('978-3-16-148410-4', 'Brave New World', ' Aldous Huxley','A futuristic novel presenting a society controlled by technology and conditioning, questioning freedom and happiness.'),
('978-3-16-148410-5', 'The Catcher in the Rye', 'J.D. Salinger', 'A coming-of-age novel that explores themes of identity, belonging, and loss through the narrative of a troubled teenager.'),
('978-3-16-148410-6', 'Sapiens: A Brief History of Humankind', 'Yuval Noah Harari', ' A book that explores the history of the human species from the Stone Age to the 21st century, examining how Homo sapiens became dominant.'),
('978-3-16-148410-7', 'Thinking, Fast and Slow', 'Daniel Kahneman', 'A psychological book that delves into the two systems that drive the way we think and make decisions.'),
('978-3-16-148410-8', 'The Code Book: The Science of Secrecy from Ancient Egypt to Quantum Cryptography', 'Simon Singh', 'An exploration of the history and science of codes and code-breaking, from ancient times to the digital age.'),
('978-3-16-148410-9', 'The Road Less Traveled: A New Psychology of Love, Traditional Values, and Spiritual Growth', 'M. Scott Peck', 'A book combining psychology and spirituality, examining the nature of loving relationships and the path to personal growth.'),
('978-3-16-148411-1', 'A Brief History of Time', 'Stephen Hawking', 'An accessible exploration of cosmology, from the Big Bang to black holes, and the nature of the universe.');

INSERT INTO Inventory (isbn, store_time, status_id)
VALUES 
('123-4-56-789012-3', NOW(), '1'),
('987-6-54-321098-7', NOW(), '6'),
('978-3-16-148410-1', NOW(), '1'),
('978-3-16-148410-2', NOW(), '1'),
('978-3-16-148410-3', NOW(), '1'),
('978-3-16-148410-4', NOW(), '2'),
('978-3-16-148410-5', NOW(), '2'),
('978-3-16-148410-6', NOW(), '2'),
('978-3-16-148410-7', NOW(), '4'),
('978-3-16-148410-8', NOW(), '3'),
('978-3-16-148410-9', NOW(), '5'),
('978-3-16-148411-1', NOW(), '1');

INSERT INTO Borrowing_Record (user_id, inventory_id, borrowing_time, return_time)
VALUES 
(1, 9, NOW(), NOW() + interval '7 days'),
(2, 10, NOW(), NOW() + interval '10 days'),
(1, 3, NOW(), NOW() + interval '7 days'),
(1, 4, NOW(), NOW() + interval '7 days'),
(1, 5, NOW(), NOW() + interval '7 days'),
(2, 7, NOW(), NULL),
(2, 8, NOW(), NULL);