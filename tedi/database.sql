CREATE DATABASE  IF NOT EXISTS tedi_db /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE tedi_db;
-- MySQL dump 10.13  Distrib 8.0.36, for Linux (x86_64)
--
-- Host: localhost    Database: tedi_db
-- ------------------------------------------------------
-- Server version	8.0.39-0ubuntu0.22.04.1

--
-- Table structure for table role
--
SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS role;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE role (
  id bigint NOT NULL AUTO_INCREMENT,
  role varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table role
--

LOCK TABLES role WRITE;
/*!40000 ALTER TABLE role DISABLE KEYS */;
INSERT INTO role VALUES (1,'admin'),(2,'user');
/*!40000 ALTER TABLE role ENABLE KEYS */;
UNLOCK TABLES;



--
-- Table structure for table user
--

DROP TABLE IF EXISTS user;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE user (
  id bigint NOT NULL AUTO_INCREMENT,
  admin bit(1) DEFAULT NULL,
  email varchar(255) DEFAULT NULL,
  first_name varchar(255) DEFAULT NULL,
  image mediumblob,
  is_public_education bit(1) DEFAULT NULL,
  is_public_skills bit(1) DEFAULT NULL,
  is_public_work_experience bit(1) DEFAULT NULL,
  last_name varchar(255) DEFAULT NULL,
  password varchar(255) DEFAULT NULL,
  phone_number bigint DEFAULT NULL,
  work_title varchar(255) DEFAULT NULL,
  workplace varchar(255) DEFAULT NULL,
  role_id bigint NOT NULL,
  PRIMARY KEY (id),
  KEY FKn82ha3ccdebhokx3a8fgdqeyy (role_id),
  CONSTRAINT FKn82ha3ccdebhokx3a8fgdqeyy FOREIGN KEY (role_id) REFERENCES role (id)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table user
--

LOCK TABLES user WRITE;
/*!40000 ALTER TABLE user DISABLE KEYS */;
-- Insert 50 users with realistic data
INSERT INTO user (admin, email, first_name, last_name, phone_number, work_title, workplace, role_id, password, is_public_education, is_public_skills, is_public_work_experience)
VALUES 
    (0, 'john.doe@gmail.com', 'John', 'Doe', 1234567890, 'Software Engineer', 'Google', 2, '1234', 1, 1, 1),
    (0, 'jane.smith@gmail.com', 'Jane', 'Smith', 9876543210, 'Product Manager', 'Microsoft', 2, '1234', 0, 1, 1),
    (0, 'alice.johnson@gmail.com', 'Alice', 'Johnson', 1928374650, 'Data Scientist', 'Facebook', 2, '1234', 1, 0, 1),
    (0, 'bob.brown@gmail.com', 'Bob', 'Brown', 1029384756, 'UX Designer', 'Amazon', 2, '1234', 1, 1, 0),
        (0, 'charlie.davis@gmail.com', 'Charlie', 'Davis', 1122334455, 'DevOps Engineer', 'Netflix', 2, '1234', 1, 1, 1),
    (0, 'emily.miller@gmail.com', 'Emily', 'Miller', 2233445566, 'Marketing Specialist', 'Spotify', 2, '1234', 0, 1, 0),
    (0, 'david.wilson@gmail.com', 'David', 'Wilson', 3344556677, 'Sales Manager', 'Oracle', 2, '1234', 1, 1, 0),
    (0, 'michael.moore@gmail.com', 'Michael', 'Moore', 4455667788, 'Cybersecurity Expert', 'Cisco', 2, '1234', 1, 0, 1),
    (0, 'olivia.white@gmail.com', 'Olivia', 'White', 5566778899, 'Software Architect', 'IBM', 2, '1234', 1, 1, 1),
    (0, 'lucas.king@gmail.com', 'Lucas', 'King', 6677889900, 'Business Analyst', 'Salesforce', 2, '1234', 1, 0, 1),
    (0, 'mia.harris@gmail.com', 'Mia', 'Harris', 7788990011, 'Frontend Developer', 'Adobe', 2, '1234', 1, 1, 0),
    (0, 'noah.martinez@gmail.com', 'Noah', 'Martinez', 8899001122, 'Backend Developer', 'Red Hat', 2, '1234', 0, 1, 1),
    (0, 'ella.rodriguez@gmail.com', 'Ella', 'Rodriguez', 9900112233, 'Project Manager', 'Uber', 2, '1234', 1, 1, 0),
    (0, 'sophia.walker@gmail.com', 'Sophia', 'Walker', 1011121314, 'IT Consultant', 'Deloitte', 2, '1234', 1, 1, 1),
    (0, 'ethan.lee@gmail.com', 'Ethan', 'Lee', 1112131415, 'Product Designer', 'Slack', 2, '1234', 1, 0, 1),
    (0, 'liam.baker@gmail.com', 'Liam', 'Baker', 1213141516, 'Marketing Director', 'Pinterest', 2, '1234', 1, 1, 0),
    (0, 'ava.clark@gmail.com', 'Ava', 'Clark', 1314151617, 'Content Strategist', 'Medium', 2, '1234', 0, 1, 1),
    (0, 'mason.jones@gmail.com', 'Mason', 'Jones', 1415161718, 'Cloud Engineer', 'AWS', 2, '1234', 1, 1, 1),
    (0, 'isabella.davis@gmail.com', 'Isabella', 'Davis', 1516171819, 'Graphic Designer', 'Canva', 2, '1234', 1, 0, 1),
    (0, 'james.moore@gmail.com', 'James', 'Moore', 1617181920, 'Network Administrator', 'AT&T', 2, '1234', 1, 1, 0),
    (0, 'logan.garcia@gmail.com', 'Logan', 'Garcia', 1718192021, 'System Administrator', 'IBM', 2, '1234', 0, 1, 1),
    (0, 'sebastian.martin@gmail.com', 'Sebastian', 'Martin', 1820212223, 'HR Manager', 'LinkedIn', 2, '1234', 1, 1, 0),
    (0, 'zoe.thomas@gmail.com', 'Zoe', 'Thomas', 1920222324, 'SEO Specialist', 'Shopify', 2, '1234', 0, 1, 1),
    (0, 'joseph.jackson@gmail.com', 'Joseph', 'Jackson', 2023242526, 'Tech Lead', 'GitHub', 2, '1234', 1, 0, 1),
    (0, 'chloe.wilson@gmail.com', 'Chloe', 'Wilson', 2134252627, 'Web Developer', 'GoDaddy', 2, '1234', 1, 1, 0),
    (0, 'daniel.evans@gmail.com', 'Daniel', 'Evans', 2245262728, 'Game Developer', 'Riot Games', 2, '1234', 0, 1, 1),
    (0, 'madison.hall@gmail.com', 'Madison', 'Hall', 2356272829, 'Product Marketer', 'Zendesk', 2, '1234', 1, 1, 0),
    (0, 'joshua.taylor@gmail.com', 'Joshua', 'Taylor', 2467282930, 'Data Engineer', 'Palantir', 2, '1234', 1, 0, 1),
    (0, 'matthew.allen@gmail.com', 'Matthew', 'Allen', 2578293031, 'AI Specialist', 'OpenAI', 2, '1234', 1, 1, 1),
    (0, 'alex.miller@gmail.com', 'Alex', 'Miller', 2689303132, 'Site Reliability Engineer', 'Google', 2, '1234', 0, 1, 0),
    (0, 'luke.young@gmail.com', 'Luke', 'Young', 2790313233, 'Mobile Developer', 'Snapchat', 2, '1234', 1, 1, 1),
    (0, 'natalie.carter@gmail.com', 'Natalie', 'Carter', 2801323334, 'Full Stack Developer', 'Stripe', 2, '1234', 1, 0, 1),
    (0, 'grace.hill@gmail.com', 'Grace', 'Hill', 2912333435, 'Growth Hacker', 'Airbnb', 2, '1234', 0, 1, 1),
    (0, 'andrew.scott@gmail.com', 'Andrew', 'Scott', 3023343536, 'IT Support', 'HP', 2, '1234', 1, 1, 0),
    (0, 'sarah.green@gmail.com', 'Sarah', 'Green', 3134353637, 'DevOps Consultant', 'Accenture', 2, '1234', 1, 0, 1),
    (0, 'oliver.adams@gmail.com', 'Oliver', 'Adams', 3245363738, 'Cloud Architect', 'Google Cloud', 2, '1234', 1, 1, 0),
    (0, 'benjamin.campbell@gmail.com', 'Benjamin', 'Campbell', 3356373839, 'Machine Learning Engineer', 'DeepMind', 2, '1234', 0, 1, 1),
    (0, 'lucas.perry@gmail.com', 'Lucas', 'Perry', 3467383940, 'Blockchain Developer', 'Coinbase', 2, '1234', 1, 1, 0),
    (0, 'victoria.morgan@gmail.com', 'Victoria', 'Morgan', 3578394041, 'IT Consultant', 'SAP', 2, '1234', 1, 0, 1),
    (0, 'jackson.hughes@gmail.com', 'Jackson', 'Hughes', 3689404142, 'Business Intelligence Analyst', 'Zoom', 2, '1234', 0, 1, 1),
    (0, 'nathan.henderson@gmail.com', 'Nathan', 'Henderson', 3790414243, 'Security Engineer', 'CrowdStrike', 2, '1234', 1, 1, 0),
    (0, 'lily.ward@gmail.com', 'Lily', 'Ward', 3901424344, 'Web Developer', 'Wix', 2, '1234', 1, 0, 1),
    (0, 'alexander.cooper@gmail.com', 'Alexander', 'Cooper', 4012434445, 'AI Researcher', 'Meta AI', 2, '1234', 1, 1, 0),
    (0, 'hailey.foster@gmail.com', 'Hailey', 'Foster', 4123444546, 'UI Designer', 'Adobe', 2, '1234', 0, 1, 1);
/*!40000 ALTER TABLE user ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table article
--

DROP TABLE IF EXISTS article;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE article (
  id bigint NOT NULL AUTO_INCREMENT,
  content longtext,
  date_posted datetime(6) DEFAULT NULL,
  picture mediumblob,
  title varchar(255) DEFAULT NULL,
  video mediumblob,
  author bigint DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FKdw5d9vdw43e3nvtpqk8l4iitp (author),
  CONSTRAINT FKdw5d9vdw43e3nvtpqk8l4iitp FOREIGN KEY (author) REFERENCES user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table article
--

LOCK TABLES article WRITE;
/*!40000 ALTER TABLE article DISABLE KEYS */;
INSERT INTO article (title, content, date_posted, picture, video, author) VALUES 
-- User 2
('The Future of Web Development', 'Exploring the latest trends in web development, including the rise of frameworks like React and Angular.', '2023-01-15 10:00:00', NULL, NULL, 2),
('Python vs. Java: A Comparison', 'A deep dive into the differences between Python and Java, and when to use each language.', '2023-02-20 14:00:00', NULL, NULL, 2),

-- User 3
('Data Science in 2024', 'An analysis of the future of data science and how it will impact various industries.', '2023-03-05 09:30:00', NULL, NULL, 3),
('Machine Learning Basics', 'An introduction to the basics of machine learning, including key concepts and algorithms.', '2023-04-10 11:15:00', NULL, NULL, 3),
('Big Data Challenges', 'Discussing the challenges faced in handling and processing big data in modern enterprises.', '2023-05-22 13:45:00', NULL, NULL, 3),

-- User 4
('DevOps Best Practices', 'A guide to implementing DevOps in your organization, including tools and methodologies.', '2023-06-12 08:00:00', NULL, NULL, 4),

-- User 5
('Frontend Development Trends', 'Exploring the latest trends in frontend development, including the use of modern JavaScript frameworks.', '2023-07-03 16:30:00', NULL, NULL, 5),
('Building Responsive UIs', 'Tips and tricks for building responsive user interfaces that work across all devices.', '2023-08-15 12:00:00', NULL, NULL, 5),
('CSS Grid vs. Flexbox', 'A comparison of CSS Grid and Flexbox, and when to use each layout technique.', '2023-09-25 10:00:00', NULL, NULL, 5),
('JavaScript Performance Optimization', 'How to optimize the performance of your JavaScript code for faster loading times.', '2023-10-05 14:20:00', NULL, NULL, 5),

-- User 6
('Designing for User Experience', 'An in-depth look at designing for user experience and how it impacts product success.', '2023-11-01 11:00:00', NULL, NULL, 6),
('The Importance of Accessibility in Web Design', 'Why accessibility is crucial in web design and how to implement it effectively.', '2023-12-15 13:00:00', NULL, NULL, 6),

-- User 7
('Data Engineering Best Practices', 'Key best practices for data engineering, including pipeline design and data quality management.', '2023-12-22 09:00:00', NULL, NULL, 7),

-- User 8
('Building Scalable Applications', 'Strategies for building scalable applications that can handle high traffic and large datasets.', '2023-06-21 15:00:00', NULL, NULL, 8),

-- User 9
('Responsive Web Design Techniques', 'Techniques for creating responsive web designs that adapt to different screen sizes.', '2023-07-08 17:00:00', NULL, NULL, 9),

-- User 10
('Analyzing Data with Python', 'A guide to analyzing data using Python libraries such as pandas and NumPy.', '2023-08-03 10:00:00', NULL, NULL, 10),
('SQL for Data Analysis', 'How to use SQL for data analysis, including advanced query techniques.', '2023-09-12 12:00:00', NULL, NULL, 10),

-- User 11
('Securing Your Network', 'Best practices for securing your network infrastructure against cyber threats.', '2023-09-30 14:00:00', NULL, NULL, 11),
('Cybersecurity Trends in 2024', 'An overview of the latest trends in cybersecurity and how to prepare for them.', '2023-10-20 09:00:00', NULL, NULL, 11),

-- User 12
('Developing iOS Applications with Swift', 'A beginner''s guide to developing iOS applications using Swift.', '2023-11-15 11:00:00', NULL, NULL, 12),
('UI Design Principles for Mobile Apps', 'Principles of UI design specifically for mobile applications.', '2023-12-10 16:00:00', NULL, NULL, 12),

-- User 13
('Machine Learning in Healthcare', 'How machine learning is being used to revolutionize healthcare.', '2023-06-28 14:00:00', NULL, NULL, 13),

-- User 14
('System Administration Tips', 'Tips and tricks for effective system administration, including automation and monitoring.', '2023-07-15 13:00:00', NULL, NULL, 14),

-- User 15
('Cloud Computing Advantages', 'Exploring the advantages of cloud computing for businesses of all sizes.', '2023-08-20 15:00:00', NULL, NULL, 15),
('AWS vs. Azure: A Comparison', 'Comparing the features and services of AWS and Azure.', '2023-09-18 10:00:00', NULL, NULL, 15),

-- User 16
('Managing Databases with MySQL', 'Best practices for managing and optimizing MySQL databases.', '2023-10-25 14:00:00', NULL, NULL, 16),

-- User 17
('Leading a Development Team', 'Strategies for effectively leading a development team and ensuring project success.', '2023-11-05 16:00:00', NULL, NULL, 17),

-- User 18
('Full Stack Development with JavaScript', 'A guide to full stack development using JavaScript, including both frontend and backend technologies.', '2023-12-01 09:00:00', NULL, NULL, 18),
('Building RESTful APIs', 'How to design and build RESTful APIs that are secure and scalable.', '2023-12-25 11:00:00', NULL, NULL, 18),

-- User 19
('Backend Development Strategies', 'Key strategies for effective backend development, including architecture and database management.', '2023-06-17 12:00:00', NULL, NULL, 19),

-- User 20
('Designing Intuitive User Interfaces', 'How to design intuitive user interfaces that are easy to use and navigate.', '2023-07-02 14:00:00', NULL, NULL, 20),
('CSS Grid: A Comprehensive Guide', 'A comprehensive guide to CSS Grid and how to use it for modern web layouts.', '2023-08-05 10:00:00', NULL, NULL, 20),

-- User 21
('Software Architecture Principles', 'Key principles of software architecture and how to apply them to large-scale projects.', '2023-09-10 13:00:00', NULL, NULL, 21),

-- User 22
('Implementing Cybersecurity Strategies', 'How to implement effective cybersecurity strategies to protect your organization.', '2023-10-15 16:00:00', NULL, NULL, 22),

-- User 23
('Machine Learning Deployment', 'Strategies for deploying machine learning models in production environments.', '2023-11-18 12:00:00', NULL, NULL, 23),

-- User 24
('DevOps in Practice', 'Practical tips for implementing DevOps in your organization.', '2023-12-20 14:00:00', NULL, NULL, 24),

-- User 25
('Data Pipeline Optimization', 'How to optimize data pipelines for better performance and reliability.', '2023-06-30 09:00:00', NULL, NULL, 25),

-- User 26
('Securing Network Infrastructure', 'Best practices for securing your network infrastructure.', '2023-07-18 11:00:00', NULL, NULL, 26),

-- User 27
('Full Stack Development Tips', 'Tips for full stack developers on managing both frontend and backend tasks effectively.', '2023-08-12 13:00:00', NULL, NULL, 27),

-- User 28
('Architecting for the Cloud', 'Key considerations for architecting applications for the cloud.', '2023-09-22 16:00:00', NULL, NULL, 28),

-- User 29
('Building Scalable Applications with Python', 'How to build scalable applications using Python.', '2023-10-08 18:00:00', NULL, NULL, 29),
('Data Science for Beginners', 'An introduction to data science for those new to the field.', '2023-11-11 14:00:00', NULL, NULL, 29),

-- User 30
('Designing for Mobile First', 'Why and how to design your applications with a mobile-first approach.', '2023-12-05 10:00:00', NULL, NULL, 30),

-- User 31
('Developing Mobile Apps with Flutter', 'A guide to developing mobile applications using Flutter.', '2023-06-25 11:00:00', NULL, NULL, 31),

-- User 32
('DevOps Automation Techniques', 'How to automate DevOps processes to increase efficiency.', '2023-07-14 12:00:00', NULL, NULL, 32),

-- User 33
('Building Modern Web Applications', 'Tips for building modern web applications using the latest technologies.', '2023-08-10 14:00:00', NULL, NULL, 33),

-- User 34
('Data Engineering Challenges', 'Common challenges faced in data engineering and how to overcome them.', '2023-09-19 16:00:00', NULL, NULL, 34),

-- User 35
('Securing Cloud Infrastructure', 'How to secure your cloud infrastructure against threats.', '2023-10-28 18:00:00', NULL, NULL, 35),

-- User 36
('Building Enterprise Software', 'Key considerations when building enterprise software solutions.', '2023-11-22 15:00:00', NULL, NULL, 36),
('Implementing DevOps with AWS', 'How to implement DevOps practices in AWS environments.', '2023-12-29 13:00:00', NULL, NULL, 36),

-- User 37
('Backend Development with Node.js', 'Using Node.js for backend development, including best practices and performance tips.', '2023-06-13 10:00:00', NULL, NULL, 37),

-- User 38
('Architecting Scalable Systems', 'How to architect systems that scale efficiently.', '2023-07-16 17:00:00', NULL, NULL, 38),

-- User 39
('Machine Learning in Production', 'Challenges and solutions for deploying machine learning models in production.', '2023-08-25 19:00:00', NULL, NULL, 39),

-- User 40
('Leading Development Teams', 'How to effectively lead a development team and ensure project success.', '2023-09-30 11:00:00', NULL, NULL, 40),

-- User 41
('Networking Basics', 'An introduction to the basics of networking for IT professionals.', '2023-10-15 13:00:00', NULL, NULL, 41),

-- User 42
('Cybersecurity in the Cloud', 'How to secure cloud environments and protect against threats.', '2023-11-09 15:00:00', NULL, NULL, 42),

-- User 43
('Data Science Techniques', 'Advanced techniques in data science for analyzing large datasets.', '2023-12-04 17:00:00', NULL, NULL, 43),

-- User 44
('Developing Android Apps with Kotlin', 'A guide to developing Android applications using Kotlin.', '2023-06-10 09:00:00', NULL, NULL, 44),

-- User 45
('Full Stack Development with React and Node.js', 'Building full stack applications using React for the frontend and Node.js for the backend.', '2023-07-07 11:00:00', NULL, NULL, 45);
/*!40000 ALTER TABLE article ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table comments
--

DROP TABLE IF EXISTS comments;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE comments (
  id bigint NOT NULL AUTO_INCREMENT,
  comment longtext,
  article bigint DEFAULT NULL,
  poster bigint DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FKba7gsfj26c34vqd3yelkhcwjd (article),
  KEY FKtf5g6swvn2uc52ojrep8c2pb1 (poster),
  CONSTRAINT FKba7gsfj26c34vqd3yelkhcwjd FOREIGN KEY (article) REFERENCES article (id),
  CONSTRAINT FKtf5g6swvn2uc52ojrep8c2pb1 FOREIGN KEY (poster) REFERENCES user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table comments
--

LOCK TABLES comments WRITE;
/*!40000 ALTER TABLE comments DISABLE KEYS */;
-- Comments for all 60 articles
INSERT INTO comments (article, poster, comment) VALUES
-- Article 1
(1, 8, 'Great article on web development!'),
(1, 9, 'Thanks for sharing these insights.'),
-- Article 2
(2, 10, 'Very informative comparison!'),
(2, 11, 'Python is definitely my favorite.'),
-- Article 3
(3, 12, 'Data science is a game-changer!'),
(3, 13, 'Machine learning is the future.'),
-- Article 4
(4, 14, 'DevOps practices are essential.'),
(4, 15, 'Great overview of the tools available.'),
-- Article 5
(5, 16, 'Frontend development is evolving fast!'),
(5, 17, 'CSS Grid is a game-changer.'),
-- Article 6
(6, 18, 'User experience should be a priority.'),
(6, 19, 'Accessibility is crucial.'),
-- Article 7
(7, 20, 'Data engineering is becoming more important.'),
(7, 21, 'Great tips for managing data pipelines.'),
-- Article 8
(8, 22, 'Scalability is critical for apps.'),
(8, 23, 'Thanks for sharing these tips.'),
-- Article 9
(9, 24, 'Responsive design is key.'),
(9, 25, 'Great insights on web design.'),
-- Article 10
(10, 26, 'Data analysis with Python is powerful.'),
(10, 27, 'SQL is a must for data management.'),
-- Article 11
(11, 28, 'Networking is essential for IT professionals.'),
(11, 29, 'Great guide on setting up secure networks.'),
-- Article 12
(12, 30, 'Mobile development has never been easier with Swift.'),
(12, 31, 'Thanks for the tips on Swift UI.'),
-- Article 13
(13, 32, 'Machine learning in healthcare is revolutionary.'),
(13, 33, 'Exciting times for AI in medicine.'),
-- Article 14
(14, 34, 'System administration requires constant vigilance.'),
(14, 35, 'Automation can save a lot of time in system management.'),
-- Article 15
(15, 36, 'Cloud computing is transforming business.'),
(15, 37, 'AWS offers great services for startups.'),
-- Article 16
(16, 38, 'MySQL is a reliable choice for database management.'),
(16, 39, 'Optimizing queries is key to performance.'),
-- Article 17
(17, 40, 'Leading a dev team requires more than technical skills.'),
(17, 41, 'Communication is key in leadership.'),
-- Article 18
(18, 2, 'Full stack development is demanding but rewarding.'),
(18, 3, 'JavaScript is versatile for both frontend and backend.'),
-- Article 19
(19, 4, 'Backend architecture is critical for scalability.'),
(19, 5, 'API design can make or break a project.'),
-- Article 20
(20, 6, 'UI/UX design can significantly improve user retention.'),
(20, 7, 'Consider accessibility from the start.'),
-- Article 21
(21, 8, 'Software architecture principles are foundational.'),
(21, 9, 'Modular design is the way forward.'),
-- Article 22
(22, 20, 'Cybersecurity is a moving target.'),
(22, 11, 'Stay updated on the latest security threats.'),
-- Article 23
(23, 22, 'Machine learning deployment is challenging but rewarding.'),
(23, 33, 'Careful planning is needed for production ML systems.'),
-- Article 24
(24, 14, 'DevOps practices are necessary for continuous delivery.'),
(24, 25, 'Automation tools can streamline workflows.'),
-- Article 25
(25, 6, 'Data pipeline optimization is key for performance.'),
(25, 17, 'Monitor your data processes regularly.'),
-- Article 26
(26, 38, 'Network security is critical for modern infrastructure.'),
(26, 19, 'Implement multiple layers of defense.'),
-- Article 27
(27, 15, 'Full stack developers need to balance frontend and backend skills.'),
(27, 2, 'Stay updated with the latest frameworks.'),
-- Article 28
(28, 3, 'Cloud architecture is evolving rapidly.'),
(28, 4, 'Stay ahead by learning new tools.'),
-- Article 29
(29, 5, 'Python remains a top choice for scalable applications.'),
(29, 6, 'Combine Python with cloud services for best results.'),
-- Article 30
(30, 7, 'Data science for beginners is accessible now more than ever.'),
(30, 8, 'Start with the basics and build from there.'),
-- Article 31
(31, 9, 'Designing for mobile first ensures a broad reach.'),
(31, 10, 'Consider user experience on all devices.'),
-- Article 32
(32, 11, 'Flutter is a powerful tool for mobile development.'),
(32, 12, 'Cross-platform development saves time and resources.'),
-- Article 33
(33, 13, 'Automating DevOps increases efficiency.'),
(33, 14, 'Start small with automation and scale up.'),
-- Article 34
(34, 15, 'Modern web apps require modern development techniques.'),
(34, 16, 'Use the latest tools to stay competitive.'),
-- Article 35
(35, 17, 'Data engineering challenges are evolving.'),
(35, 18, 'Focus on data quality and integrity.'),
-- Article 36
(36, 19, 'Cloud infrastructure needs to be secure.'),
(36, 20, 'Use encryption and access controls.'),
-- Article 37
(37, 21, 'Enterprise software development requires planning.'),
(37, 22, 'Involve stakeholders early in the process.'),
-- Article 38
(38, 23, 'DevOps with AWS integrates well into CI/CD pipelines.'),
(38, 24, 'Leverage AWS services for automation.'),
-- Article 39
(39, 25, 'Node.js is perfect for backend development.'),
(39, 26, 'Consider performance and scalability.'),
-- Article 40
(40, 27, 'Scalable systems require careful planning.'),
(40, 28, 'Use load balancers to manage traffic.'),
-- Article 41
(41, 29, 'Machine learning models need regular updates.'),
(41, 30, 'Monitor model performance over time.'),
-- Article 42
(42, 31, 'Leading teams requires both vision and strategy.'),
(42, 32, 'Communicate your vision clearly.'),
-- Article 43
(43, 33, 'Networking basics are essential for IT professionals.'),
(43, 34, 'Understand the fundamentals before diving deep.'),
-- Article 44
(44, 35, 'Cybersecurity in the cloud is complex.'),
(44, 36, 'Implement best practices for cloud security.'),
-- Article 45
(45, 37, 'Data science techniques evolve quickly.'),
(45, 38, 'Stay updated on the latest methods.'),
-- Article 46
(46, 39, 'Kotlin is great for Android development.'),
(46, 40, 'Leverage Kotlin’s features for better apps.'),
-- Article 47
(47, 41, 'React and Node.js are powerful together.'),
(47, 42, 'Build full stack apps efficiently.'),
-- Article 48
(48, 43, 'Understanding UX design principles is crucial.'),
(48, 44, 'Focus on user needs first.'),
-- Article 49
(49, 45, 'DevOps automation with Kubernetes.'),
(49, 2, 'Kubernetes simplifies deployment.'),
-- Article 50
(50, 3, 'Design systems improve consistency.'),
(50, 4, 'Implement design systems early.');
/*!40000 ALTER TABLE comments ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table connection
--

DROP TABLE IF EXISTS connection;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE connection (
  id bigint NOT NULL AUTO_INCREMENT,
  user_a bigint DEFAULT NULL,
  user_b bigint DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FKpcguk4clupoms263i23gvfsn7 (user_a),
  KEY FKsx0kbmtmc4jncb3aujvxa6ikr (user_b),
  CONSTRAINT FKpcguk4clupoms263i23gvfsn7 FOREIGN KEY (user_a) REFERENCES user (id),
  CONSTRAINT FKsx0kbmtmc4jncb3aujvxa6ikr FOREIGN KEY (user_b) REFERENCES user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table connection
--

LOCK TABLES connection WRITE;
/*!40000 ALTER TABLE connection DISABLE KEYS */;
INSERT INTO connection (user_a, user_b) VALUES 
(2, 3), (2, 4), (2, 5), (2, 6), (2, 7), (2, 8), (2, 9), (2, 10),
(3, 4), (3, 5), (3, 6), (3, 7), (3, 8), (3, 9), (3, 10), (3, 11),
(4, 5), (4, 6), (4, 7), (4, 8), (4, 9), (4, 10), (4, 11), (4, 12),
(5, 6), (5, 7), (5, 8), (5, 9), (5, 10), (5, 11), (5, 12), (5, 13),
(6, 7), (6, 8), (6, 9), (6, 10), (6, 11), (6, 12), (6, 13), (6, 14),
(7, 8), (7, 9), (7, 10), (7, 11), (7, 12), (7, 13), (7, 14), (7, 15),
(8, 9), (8, 10), (8, 11), (8, 12), (8, 13), (8, 14), (8, 15), (8, 16),
(9, 10), (9, 11), (9, 12), (9, 13), (9, 14), (9, 15), (9, 16), (9, 17),
(10, 11), (10, 12), (10, 13), (10, 14), (10, 15), (10, 16), (10, 17), (10, 18),
(11, 12), (11, 13), (11, 14), (11, 15), (11, 16), (11, 17), (11, 18), (11, 19),
(12, 13), (12, 14), (12, 15), (12, 16), (12, 17), (12, 18), (12, 19), (12, 20),
(13, 14), (13, 15), (13, 16), (13, 17), (13, 18), (13, 19), (13, 20), (13, 21),
(14, 15), (14, 16), (14, 17), (14, 18), (14, 19), (14, 20), (14, 21), (14, 22),
(15, 16), (15, 17), (15, 18), (15, 19), (15, 20), (15, 21), (15, 22), (15, 23),
(16, 17), (16, 18), (16, 19), (16, 20), (16, 21), (16, 22), (16, 23), (16, 24),
(17, 18), (17, 19), (17, 20), (17, 21), (17, 22), (17, 23), (17, 24), (17, 25),
(18, 19), (18, 20), (18, 21), (18, 22), (18, 23), (18, 24), (18, 25), (18, 26),
(19, 20), (19, 21), (19, 22), (19, 23), (19, 24), (19, 25), (19, 26), (19, 27),
(20, 21), (20, 22), (20, 23), (20, 24), (20, 25), (20, 26), (20, 27), (20, 28),
(21, 22), (21, 23), (21, 24), (21, 25), (21, 26), (21, 27), (21, 28), (21, 29),
(22, 23), (22, 24), (22, 25), (22, 26), (22, 27), (22, 28), (22, 29), (22, 30),
(23, 24), (23, 25), (23, 26), (23, 27), (23, 28), (23, 29), (23, 30), (23, 31),
(24, 25), (24, 26), (24, 27), (24, 28), (24, 29), (24, 30), (24, 31), (24, 32),
(25, 26), (25, 27), (25, 28), (25, 29), (25, 30), (25, 31), (25, 32), (25, 33),
(26, 27), (26, 28), (26, 29), (26, 30), (26, 31), (26, 32), (26, 33), (26, 34),
(27, 28), (27, 29), (27, 30), (27, 31), (27, 32), (27, 33), (27, 34), (27, 35),
(28, 29), (28, 30), (28, 31), (28, 32), (28, 33), (28, 34), (28, 35), (28, 36),
(29, 30), (29, 31), (29, 32), (29, 33), (29, 34), (29, 35), (29, 36), (29, 37),
(30, 31), (30, 32), (30, 33), (30, 34), (30, 35), (30, 36), (30, 37), (30, 38),
(31, 32), (31, 33), (31, 34), (31, 35), (31, 36), (31, 37), (31, 38), (31, 39),
(32, 33), (32, 34), (32, 35), (32, 36), (32, 37), (32, 38), (32, 39), (32, 40),
(33, 34), (33, 35), (33, 36), (33, 37), (33, 38), (33, 39), (33, 40), (33, 41),
(34, 35), (34, 36), (34, 37), (34, 38), (34, 39), (34, 40), (34, 41), (34, 42),
(35, 36), (35, 37), (35, 38), (35, 39), (35, 40), (35, 41), (35, 42), (35, 43),
(36, 37), (36, 38), (36, 39), (36, 40), (36, 41), (36, 42), (36, 43), (36, 44),
(37, 38), (37, 39), (37, 40), (37, 41), (37, 42), (37, 43), (37, 44), (37, 45),
(38, 39), (38, 40), (38, 41), (38, 42), (38, 43), (38, 44), (38, 45),
(39, 40), (39, 41), (39, 42), (39, 43), (39, 44), (39, 45),
(40, 41), (40, 42), (40, 43), (40, 44), (40, 45),
(41, 42), (41, 43), (41, 44), (41, 45),
(42, 43), (42, 44), (42, 45),
(43, 44), (43, 45),
(44, 45);
/*!40000 ALTER TABLE connection ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table education
--

DROP TABLE IF EXISTS education;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE education (
  id bigint NOT NULL AUTO_INCREMENT,
  education varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table education
--

LOCK TABLES education WRITE;
/*!40000 ALTER TABLE education DISABLE KEYS */;
INSERT INTO education (education) VALUES 
('Bachelor of Science in Computer Science'),
('Master of Science in Computer Science'),
('Bachelor of Science in Information Technology'),
('Master of Science in Information Technology'),
('Bachelor of Business Administration'),
('Master of Business Administration'),
('Bachelor of Arts in Economics'),
('Master of Arts in Economics'),
('Bachelor of Science in Electrical Engineering'),
('Master of Science in Electrical Engineering'),
('Bachelor of Science in Mechanical Engineering'),
('Master of Science in Mechanical Engineering'),
('Bachelor of Science in Civil Engineering'),
('Master of Science in Civil Engineering'),
('Bachelor of Science in Mathematics'),
('Master of Science in Mathematics'),
('Bachelor of Arts in Psychology'),
('Master of Arts in Psychology'),
('Bachelor of Science in Physics'),
('Master of Science in Physics'),
('Bachelor of Science in Chemistry'),
('Master of Science in Chemistry'),
('Bachelor of Arts in English Literature'),
('Master of Arts in English Literature'),
('Bachelor of Science in Biology'),
('Master of Science in Biology'),
('Bachelor of Science in Environmental Science'),
('Master of Science in Environmental Science'),
('Bachelor of Science in Finance'),
('Master of Science in Finance'),
('Bachelor of Science in Accounting'),
('Master of Science in Accounting'),
('Bachelor of Science in Marketing'),
('Master of Science in Marketing'),
('Bachelor of Arts in Political Science'),
('Master of Arts in Political Science'),
('Bachelor of Science in Sociology'),
('Master of Science in Sociology'),
('Bachelor of Science in Software Engineering'),
('Master of Science in Software Engineering');
/*!40000 ALTER TABLE education ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table experience
--

DROP TABLE IF EXISTS experience;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE experience (
  id bigint NOT NULL AUTO_INCREMENT,
  experience varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table experience
--

LOCK TABLES experience WRITE;
/*!40000 ALTER TABLE experience DISABLE KEYS */;
INSERT INTO experience (experience) VALUES 
('Software Engineer at Google'),
('Data Analyst at Microsoft'),
('Product Manager at Amazon'),
('System Administrator at IBM'),
('Network Engineer at Cisco'),
('DevOps Engineer at Netflix'),
('Cybersecurity Specialist at Palo Alto Networks'),
('Cloud Architect at AWS'),
('Database Administrator at Oracle'),
('Web Developer at Shopify'),
('Mobile Developer at Apple'),
('UX/UI Designer at Adobe'),
('Backend Developer at Red Hat'),
('Frontend Developer at Facebook'),
('IT Support Specialist at Dell'),
('AI Researcher at OpenAI'),
('Blockchain Developer at Coinbase'),
('Machine Learning Engineer at DeepMind'),
('Full Stack Developer at Airbnb'),
('Site Reliability Engineer at Google'),
('Technical Support at HP'),
('IT Consultant at Accenture'),
('Project Manager at Uber'),
('Business Analyst at Salesforce'),
('QA Engineer at Intel'),
('Security Engineer at CrowdStrike'),
('Solutions Architect at IBM'),
('Software Tester at Slack'),
('Big Data Engineer at Cloudera'),
('Robotics Engineer at Boston Dynamics'),
('IoT Specialist at GE'),
('Tech Lead at GitHub'),
('Digital Marketer at HubSpot'),
('SEO Specialist at Shopify'),
('Business Intelligence Analyst at Zoom'),
('Operations Manager at Dropbox'),
('Finance Manager at Stripe'),
('Growth Hacker at Pinterest'),
('Content Strategist at Medium'),
('Sales Manager at Oracle');
/*!40000 ALTER TABLE experience ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table job
--

DROP TABLE IF EXISTS job;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE job (
  id bigint NOT NULL AUTO_INCREMENT,
  date_posted datetime(6) DEFAULT NULL,
  job_description longtext,
  job_title longtext,
  author bigint DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FKtg5s4mk68rj7q34232olppj1h (author),
  CONSTRAINT FKtg5s4mk68rj7q34232olppj1h FOREIGN KEY (author) REFERENCES user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table job
--

LOCK TABLES job WRITE;
/*!40000 ALTER TABLE job DISABLE KEYS */;
INSERT INTO job (job_title, job_description, date_posted, author) VALUES 
-- User 2
('Software Engineer at Google', 'Developing scalable web applications using Python and Java.', '2023-06-01 10:00:00', 2),
('Backend Developer at Red Hat', 'Developing RESTful APIs using Java and managing databases with SQL.', '2023-06-05 14:00:00', 2),

-- User 3
('Data Scientist at Microsoft', 'Performing data analysis and building predictive models using Python and Machine Learning techniques.', '2023-06-02 11:00:00', 3),

-- User 4
('DevOps Engineer at Netflix', 'Managing and automating cloud infrastructure using AWS and DevOps practices.', '2023-06-03 12:00:00', 4),
('Cloud Architect at AWS', 'Designing cloud-based solutions and managing cloud infrastructure on AWS.', '2023-06-06 15:00:00', 4),
('Security Engineer at CrowdStrike', 'Implementing and managing security protocols and firewalls.', '2023-06-10 19:00:00', 4),

-- User 5
('Frontend Developer at Facebook', 'Building responsive user interfaces using React and JavaScript.', '2023-06-04 13:00:00', 5),

-- User 6
('UI/UX Designer at Adobe', 'Designing user interfaces and improving user experience for web applications.', '2023-06-08 17:00:00', 6),
('Machine Learning Engineer at DeepMind', 'Building and deploying machine learning models using Python and TensorFlow.', '2023-06-07 16:00:00', 6),

-- User 7
('Data Engineer at Palantir', 'Building data pipelines and managing data warehouses using SQL and Big Data tools.', '2023-06-09 18:00:00', 7),

-- User 8
('Software Engineer at Microsoft', 'Developing and maintaining enterprise software solutions.', '2023-06-11 10:00:00', 8),
('DevOps Specialist at AWS', 'Implementing DevOps practices and managing cloud infrastructure on AWS.', '2023-06-12 12:00:00', 8),

-- User 9
('Frontend Developer at Shopify', 'Creating responsive web interfaces with JavaScript and React.', '2023-06-13 14:00:00', 9),

-- User 10
('Data Analyst at Oracle', 'Analyzing data and generating reports using SQL and Python.', '2023-06-14 15:00:00', 10),

-- User 11
('Cybersecurity Analyst at Cisco', 'Monitoring and responding to security threats in a large enterprise environment.', '2023-06-15 16:00:00', 11),
('Network Engineer at IBM', 'Designing and maintaining network infrastructure.', '2023-06-16 17:00:00', 11),

-- User 12
('Mobile Developer at Apple', 'Developing and maintaining iOS applications using Swift.', '2023-06-17 18:00:00', 12),

-- User 13
('Machine Learning Specialist at Google', 'Designing machine learning models and deploying them in production.', '2023-06-18 19:00:00', 13),

-- User 14
('System Administrator at LinkedIn', 'Managing servers and ensuring uptime and security.', '2023-06-19 10:00:00', 14),

-- User 15
('Cloud Engineer at Google Cloud', 'Building and managing cloud-based solutions.', '2023-06-20 11:00:00', 15),

-- User 16
('Database Administrator at MySQL', 'Managing and optimizing databases using MySQL.', '2023-06-21 12:00:00', 16),

-- User 17
('Tech Lead at GitHub', 'Leading a team of developers to build and maintain GitHub features.', '2023-06-22 13:00:00', 17),

-- User 18
('Full Stack Developer at Airbnb', 'Developing end-to-end web applications using JavaScript and Python.', '2023-06-23 14:00:00', 18),

-- User 19
('Backend Developer at Netflix', 'Creating and maintaining backend services and APIs.', '2023-06-24 15:00:00', 19),

-- User 20
('UI/UX Designer at Adobe', 'Improving user experience and designing intuitive interfaces.', '2023-06-25 16:00:00', 20),
('Frontend Developer at Facebook', 'Building responsive web applications using JavaScript and React.', '2023-06-26 17:00:00', 20),

-- User 21
('Software Architect at IBM', 'Designing and overseeing software architecture and implementation.', '2023-06-27 18:00:00', 21),

-- User 22
('Cybersecurity Specialist at Palo Alto Networks', 'Developing and implementing cybersecurity strategies.', '2023-06-28 19:00:00', 22),

-- User 23
('Machine Learning Engineer at Amazon', 'Building and deploying machine learning models.', '2023-06-29 10:00:00', 23),

-- User 24
('DevOps Engineer at Microsoft', 'Implementing DevOps practices across multiple teams.', '2023-06-30 11:00:00', 24),

-- User 25
('Data Engineer at Palantir', 'Building data pipelines and managing large datasets.', '2023-07-01 12:00:00', 25),

-- User 26
('Network Security Engineer at AT&T', 'Securing network infrastructure and responding to threats.', '2023-07-02 13:00:00', 26),

-- User 27
('Full Stack Developer at Medium', 'Developing and maintaining both frontend and backend components.', '2023-07-03 14:00:00', 27),

-- User 28
('Cloud Architect at AWS', 'Designing and managing scalable cloud solutions.', '2023-07-04 15:00:00', 28),

-- User 29
('Software Engineer at Google', 'Building scalable web applications.', '2023-07-05 16:00:00', 29),
('Data Scientist at Microsoft', 'Performing data analysis and building predictive models.', '2023-07-06 17:00:00', 29),

-- User 30
('UI/UX Designer at Adobe', 'Improving user experience for mobile and web apps.', '2023-07-07 18:00:00', 30),

-- User 31
('Mobile Developer at Snapchat', 'Developing and maintaining Android and iOS applications.', '2023-07-08 19:00:00', 31),

-- User 32
('DevOps Engineer at Netflix', 'Automating and managing cloud infrastructure.', '2023-07-09 10:00:00', 32),

-- User 33
('Frontend Developer at LinkedIn', 'Building responsive and scalable web applications.', '2023-07-10 11:00:00', 33),

-- User 34
('Data Engineer at Airbnb', 'Building and managing data pipelines.', '2023-07-11 12:00:00', 34),

-- User 35
('Security Engineer at CrowdStrike', 'Implementing security protocols and managing firewalls.', '2023-07-12 13:00:00', 35),

-- User 36
('Software Engineer at Microsoft', 'Developing enterprise software solutions.', '2023-07-13 14:00:00', 36),
('DevOps Specialist at AWS', 'Implementing DevOps practices across cloud infrastructure.', '2023-07-14 15:00:00', 36),

-- User 37
('Backend Developer at Red Hat', 'Developing APIs and managing databases.', '2023-07-15 16:00:00', 37),

-- User 38
('Cloud Architect at Google Cloud', 'Designing cloud-based solutions.', '2023-07-16 17:00:00', 38),

-- User 39
('Machine Learning Engineer at DeepMind', 'Building machine learning models.', '2023-07-17 18:00:00', 39),

-- User 40
('Tech Lead at GitHub', 'Leading a team of developers.', '2023-07-18 19:00:00', 40),

-- User 41
('Network Engineer at Cisco', 'Designing and maintaining network infrastructure.', '2023-07-19 10:00:00', 41),

-- User 42
('Cybersecurity Analyst at CrowdStrike', 'Monitoring and responding to security threats.', '2023-07-20 11:00:00', 42),

-- User 43
('Data Scientist at Facebook', 'Analyzing data and building predictive models.', '2023-07-21 12:00:00', 43),

-- User 44
('Mobile Developer at Google', 'Developing and maintaining Android applications.', '2023-07-22 13:00:00', 44),

-- User 45
('Full Stack Developer at Airbnb', 'Developing end-to-end web applications.', '2023-07-23 14:00:00', 45);
/*!40000 ALTER TABLE job ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table job_applicants
--

DROP TABLE IF EXISTS job_applicants;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE job_applicants (
  applicant bigint NOT NULL,
  job_id bigint NOT NULL,
  KEY FKm1uaa56b66pr6absdad0axlf4 (job_id),
  KEY FKjthoxrgyf4ld2gs4ap3pp1pyc (applicant),
  CONSTRAINT FKjthoxrgyf4ld2gs4ap3pp1pyc FOREIGN KEY (applicant) REFERENCES user (id),
  CONSTRAINT FKm1uaa56b66pr6absdad0axlf4 FOREIGN KEY (job_id) REFERENCES job (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table job_applicants
--

LOCK TABLES job_applicants WRITE;
/*!40000 ALTER TABLE job_applicants DISABLE KEYS */;
INSERT INTO job_applicants (applicant, job_id) VALUES
-- User 2
(2, 3), (2, 4), (2, 5), -- Jobs posted by other users

-- User 3
(3, 1), (3, 4), (3, 6),

-- User 4
(4, 1), (4, 2), (4, 7),

-- User 5
(5, 1), (5, 3), (5, 9),

-- User 6
(6, 2), (6, 3), (6, 10),

-- User 7
(7, 1), (7, 5), (7, 11),

-- User 8
(8, 3), (8, 6), (8, 12),

-- User 9
(9, 2), (9, 4), (9, 13),

-- User 10
(10, 1), (10, 4), (10, 14),

-- User 11
(11, 2), (11, 3), (11, 15),

-- User 12
(12, 1), (12, 5), (12, 16),

-- User 13
(13, 2), (13, 7), (13, 17),

-- User 14
(14, 3), (14, 6), (14, 18),

-- User 15
(15, 2), (15, 4), (15, 19),

-- User 16
(16, 1), (16, 5), (16, 20),

-- User 17
(17, 3), (17, 6), (17, 21),

-- User 18
(18, 2), (18, 7), (18, 22),

-- User 19
(19, 3), (19, 4), (19, 23),

-- User 20
(20, 2), (20, 6), (20, 24),

-- User 21
(21, 3), (21, 5), (21, 25),

-- User 22
(22, 1), (22, 4), (22, 26),

-- User 23
(23, 2), (23, 5), (23, 27),

-- User 24
(24, 1), (24, 3), (24, 28),

-- User 25
(25, 2), (25, 6), (25, 29),

-- User 26
(26, 1), (26, 3), (26, 30),

-- User 27
(27, 2), (27, 5), (27, 31),

-- User 28
(28, 1), (28, 4), (28, 32),

-- User 29
(29, 2), (29, 6), (29, 33),

-- User 30
(30, 1), (30, 3), (30, 34),

-- User 31
(31, 2), (31, 4), (31, 35),

-- User 32
(32, 1), (32, 3), (32, 36),

-- User 33
(33, 2), (33, 5), (33, 37),

-- User 34
(34, 3), (34, 4), (34, 38),

-- User 35
(35, 1), (35, 2), (35, 39),

-- User 36
(36, 3), (36, 5), (36, 40),

-- User 37
(37, 2), (37, 4), (37, 41),

-- User 38
(38, 1), (38, 3), (38, 42),

-- User 39
(39, 2), (39, 5), (39, 43),

-- User 40
(40, 3), (40, 4), (40, 44),

-- User 41
(41, 1), (41, 2), (41, 45),

-- User 42
(42, 3), (42, 5), (42, 46),

-- User 43
(43, 2), (43, 4), (43, 47),

-- User 44
(44, 1), (44, 3), (44, 48),

-- User 45
(45, 2), (45, 4), (45, 49);
/*!40000 ALTER TABLE job_applicants ENABLE KEYS */;
UNLOCK TABLES;


-- Table structure for table skills
--

DROP TABLE IF EXISTS skills;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE skills (
  id bigint NOT NULL AUTO_INCREMENT,
  skill longtext,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table skills
--

LOCK TABLES skills WRITE;
/*!40000 ALTER TABLE skills DISABLE KEYS */;
INSERT INTO skills (skill) VALUES 
('Python'),
('JavaScript'),
('Java'),
('C#'),
('SQL'),
('HTML'),
('CSS'),
('React'),
('Node.js'),
('Angular'),
('Django'),
('Flask'),
('Ruby on Rails'),
('Spring Boot'),
('Kotlin'),
('Swift'),
('Objective-C'),
('Go'),
('Rust'),
('TypeScript'),
('PHP'),
('Laravel'),
('ASP.NET'),
('DevOps'),
('Docker'),
('Kubernetes'),
('Machine Learning'),
('Deep Learning'),
('Natural Language Processing'),
('Data Science'),
('Big Data'),
('Cybersecurity'),
('Cloud Computing'),
('AWS'),
('Azure'),
('GCP'),
('Blockchain'),
('IoT'),
('AR/VR'),
('Mobile Development');


/*!40000 ALTER TABLE skills ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table job_skills
--

DROP TABLE IF EXISTS job_skills;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE job_skills (
  skill_id bigint NOT NULL,
  job_id bigint NOT NULL,
  KEY FKqktpavcbch6i6k65kcc0xq029 (job_id),
  KEY FKe10ho7um0atjm67b9dgokmfyx (skill_id),
  CONSTRAINT FKe10ho7um0atjm67b9dgokmfyx FOREIGN KEY (skill_id) REFERENCES skills (id),
  CONSTRAINT FKqktpavcbch6i6k65kcc0xq029 FOREIGN KEY (job_id) REFERENCES job (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table job_skills
--

LOCK TABLES job_skills WRITE;
/*!40000 ALTER TABLE job_skills DISABLE KEYS */;
INSERT INTO job_skills (skill_id, job_id) VALUES
-- Job ID 1 (Software Engineer at Google)
(1, 1), -- Python
(3, 1), -- Java
(33, 1), -- Cloud Computing

-- Job ID 2 (Backend Developer at Red Hat)
(3, 2), -- Java
(5, 2), -- SQL

-- Job ID 3 (Data Scientist at Microsoft)
(1, 3), -- Python
(27, 3), -- Machine Learning
(30, 3), -- Data Science

-- Job ID 4 (DevOps Engineer at Netflix)
(24, 4), -- DevOps
(25, 4), -- Docker
(26, 4), -- Kubernetes

-- Job ID 5 (Cloud Architect at AWS)
(33, 5), -- Cloud Computing
(34, 5), -- AWS

-- Job ID 6 (Security Engineer at CrowdStrike)
(32, 6), -- Cybersecurity
(34, 6), -- AWS

-- Job ID 7 (Frontend Developer at Facebook)
(2, 7), -- JavaScript
(7, 7), -- CSS
(8, 7), -- React

-- Job ID 8 (UI/UX Designer at Adobe)
(6, 8), -- HTML
(7, 8), -- CSS

-- Job ID 9 (Machine Learning Engineer at DeepMind)
(1, 9), -- Python
(27, 9), -- Machine Learning
(28, 9), -- Deep Learning

-- Job ID 10 (Data Engineer at Palantir)
(5, 10), -- SQL
(31, 10), -- Big Data

-- Job ID 11 (Software Engineer at Microsoft)
(1, 11), -- Python
(33, 11), -- Cloud Computing
(34, 11), -- AWS

-- Job ID 12 (DevOps Specialist at AWS)
(24, 12), -- DevOps
(25, 12), -- Docker
(26, 12), -- Kubernetes

-- Job ID 13 (Frontend Developer at Shopify)
(2, 13), -- JavaScript
(8, 13), -- React

-- Job ID 14 (Data Analyst at Oracle)
(5, 14), -- SQL
(1, 14), -- Python

-- Job ID 15 (Cybersecurity Analyst at Cisco)
(32, 15), -- Cybersecurity

-- Job ID 16 (Network Engineer at IBM)
(39, 16), -- Network Engineering

-- Job ID 17 (Mobile Developer at Apple)
(16, 17), -- Swift
(17, 17), -- Objective-C

-- Job ID 18 (Machine Learning Specialist at Google)
(27, 18), -- Machine Learning
(1, 18), -- Python

-- Job ID 19 (System Administrator at LinkedIn)
(39, 19), -- Network Engineering
(32, 19), -- Cybersecurity

-- Job ID 20 (Cloud Engineer at Google Cloud)
(33, 20), -- Cloud Computing
(36, 20), -- GCP

-- Job ID 21 (Database Administrator at MySQL)
(5, 21), -- SQL
(39, 21), -- Network Engineering

-- Job ID 22 (Tech Lead at GitHub)
(1, 22), -- Python
(24, 22), -- DevOps

-- Job ID 23 (Full Stack Developer at Airbnb)
(2, 23), -- JavaScript
(1, 23), -- Python

-- Job ID 24 (Backend Developer at Netflix)
(3, 24), -- Java
(5, 24), -- SQL

-- Job ID 25 (UI/UX Designer at Adobe)
(6, 25), -- HTML
(7, 25), -- CSS

-- Job ID 26 (Frontend Developer at Facebook)
(2, 26), -- JavaScript
(8, 26), -- React

-- Job ID 27 (Software Architect at IBM)
(33, 27), -- Cloud Computing
(24, 27), -- DevOps

-- Job ID 28 (Cybersecurity Specialist at Palo Alto Networks)
(32, 28), -- Cybersecurity

-- Job ID 29 (Machine Learning Engineer at Amazon)
(27, 29), -- Machine Learning
(1, 29), -- Python

-- Job ID 30 (DevOps Engineer at Microsoft)
(24, 30), -- DevOps
(25, 30), -- Docker
(26, 30), -- Kubernetes

-- Job ID 31 (Data Engineer at Palantir)
(5, 31), -- SQL
(31, 31), -- Big Data

-- Job ID 32 (Network Security Engineer at AT&T)
(39, 32), -- Network Engineering
(32, 32), -- Cybersecurity

-- Job ID 33 (Full Stack Developer at Medium)
(2, 33), -- JavaScript
(1, 33), -- Python

-- Job ID 34 (Cloud Architect at AWS)
(33, 34), -- Cloud Computing
(34, 34), -- AWS

-- Job ID 35 (Software Engineer at Google)
(1, 35), -- Python
(3, 35), -- Java
(33, 35), -- Cloud Computing

-- Job ID 36 (Data Scientist at Microsoft)
(1, 36), -- Python
(27, 36), -- Machine Learning
(30, 36), -- Data Science

-- Job ID 37 (UI/UX Designer at Adobe)
(6, 37), -- HTML
(7, 37), -- CSS

-- Job ID 38 (Mobile Developer at Snapchat)
(17, 38), -- Objective-C
(16, 38), -- Swift

-- Job ID 39 (DevOps Engineer at Netflix)
(24, 39), -- DevOps
(25, 39), -- Docker
(26, 39), -- Kubernetes

-- Job ID 40 (Frontend Developer at LinkedIn)
(2, 40), -- JavaScript
(8, 40), -- React

-- Job ID 41 (Data Engineer at Airbnb)
(5, 41), -- SQL
(31, 41), -- Big Data

-- Job ID 42 (Security Engineer at CrowdStrike)
(32, 42), -- Cybersecurity

-- Job ID 43 (Software Engineer at Microsoft)
(1, 43), -- Python
(33, 43), -- Cloud Computing

-- Job ID 44 (DevOps Specialist at AWS)
(24, 44), -- DevOps
(25, 44), -- Docker
(26, 44), -- Kubernetes

-- Job ID 45 (Backend Developer at Red Hat)
(3, 45), -- Java
(5, 45), -- SQL

-- Job ID 46 (Cloud Architect at Google Cloud)
(33, 46), -- Cloud Computing
(36, 46), -- GCP

-- Job ID 47 (Machine Learning Engineer at DeepMind)
(1, 47), -- Python
(27, 47), -- Machine Learning
(28, 47), -- Deep Learning

-- Job ID 48 (Tech Lead at GitHub)
(1, 48), -- Python
(24, 48), -- DevOps

-- Job ID 49 (Network Engineer at Cisco)
(24, 49), -- Network Engineering

-- Job ID 50 (Cybersecurity Analyst at CrowdStrike)
(32, 50), -- Cybersecurity

-- Job ID 51 (Data Scientist at Facebook)
(1, 51), -- Python
(30, 51), -- Data Science

-- Job ID 52 (Mobile Developer at Google)
(17, 52), -- Objective-C
(16, 52), -- Swift

-- Job ID 53 (Full Stack Developer at Airbnb)
(2, 53), -- JavaScript
(1, 53), -- Python
(8, 53); -- React
/*!40000 ALTER TABLE job_skills ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table likes
--


DROP TABLE IF EXISTS likes;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE likes (
  id bigint NOT NULL AUTO_INCREMENT,
  article bigint DEFAULT NULL,
  user bigint DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FK8e9n4u6g2438ih8bpk0ou02q9 (article),
  KEY FK72scn55bsusgdvalx8jiigh2a (user),
  CONSTRAINT FK72scn55bsusgdvalx8jiigh2a FOREIGN KEY (user) REFERENCES user (id),
  CONSTRAINT FK8e9n4u6g2438ih8bpk0ou02q9 FOREIGN KEY (article) REFERENCES article (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table likes
--

LOCK TABLES likes WRITE;
/*!40000 ALTER TABLE likes DISABLE KEYS */;
-- Likes for all 60 articles
INSERT INTO likes (article, user) VALUES
-- Article 1
(1, 3), (1, 4), (1, 5), (1, 6), (1, 7),
-- Article 2
(2, 8), (2, 9), (2, 10),
-- Article 3
(3, 11), (3, 12), (3, 13), (3, 14),
-- Article 4
(4, 15), (4, 16), (4, 17),
-- Article 5
(5, 18), (5, 19), (5, 20), (5, 21),
-- Article 6
(6, 22), (6, 23), (6, 24), (6, 25),
-- Article 7
(7, 26), (7, 27), (7, 28),
-- Article 8
(8, 29), (8, 30), (8, 31),
-- Article 9
(9, 32), (9, 33),
-- Article 10
(10, 34), (10, 35), (10, 36),
-- Article 11
(11, 37), (11, 38), (11, 39),
-- Article 12
(12, 40), (12, 41),
-- Article 13
(13, 42), (13, 43), (13, 44),
-- Article 14
(14, 45), (14, 2), (14, 3),
-- Article 15
(15, 4), (15, 5), (15, 6),
-- Article 16
(16, 7), (16, 8), (16, 9),
-- Article 17
(17, 10), (17, 11), (17, 12),
-- Article 18
(18, 13), (18, 14), (18, 15),
-- Article 19
(19, 16), (19, 17),
-- Article 20
(20, 18), (20, 19), (20, 20),
-- Article 21
(21, 21), (21, 22), (21, 23),
-- Article 22
(22, 24), (22, 25), (22, 26),
-- Article 23
(23, 27), (23, 28), (23, 29),
-- Article 24
(24, 30), (24, 31), (24, 32),
-- Article 25
(25, 33), (25, 34),
-- Article 26
(26, 35), (26, 36), (26, 37),
-- Article 27
(27, 38), (27, 39), (27, 40),
-- Article 28
(28, 41), (28, 42),
-- Article 29
(29, 43), (29, 44),
-- Article 30
(30, 45), (30, 2), (30, 3),
-- Article 31
(31, 4), (31, 5),
-- Article 32
(32, 6), (32, 7), (32, 8),
-- Article 33
(33, 9), (33, 10), (33, 11),
-- Article 34
(34, 12), (34, 13),
-- Article 35
(35, 14), (35, 15), (35, 16),
-- Article 36
(36, 17), (36, 18), (36, 19),
-- Article 37
(37, 20), (37, 21),
-- Article 38
(38, 22), (38, 23), (38, 24),
-- Article 39
(39, 25), (39, 26), (39, 27),
-- Article 40
(40, 28), (40, 29),
-- Article 41
(41, 30), (41, 31), (41, 32),
-- Article 42
(42, 33), (42, 34), (42, 35),
-- Article 43
(43, 36), (43, 37), (43, 38),
-- Article 44
(44, 39), (44, 40),
-- Article 45
(45, 41), (45, 42), (45, 43),
-- Article 46
(46, 44), (46, 45), (46, 2),
-- Article 47
(47, 3), (47, 4), (47, 5),
-- Article 48
(48, 6), (48, 7),
-- Article 49
(49, 8), (49, 9), (49, 10),
-- Article 50
(50, 11), (50, 12),
-- Article 51
(51, 13), (51, 14), (51, 15),
-- Article 52
(52, 16), (52, 17), (52, 18),
-- Article 53
(53, 19), (53, 20),
-- Article 54
(54, 21), (54, 22), (54, 23),
-- Article 55
(55, 24), (55, 25), (55, 26),
-- Article 56
(56, 27), (56, 28), (56, 29),
-- Article 57
(57, 30), (57, 31),
-- Article 58
(58, 32), (58, 33), (58, 34),
-- Article 59
(59, 35), (59, 36), (59, 37);
/*!40000 ALTER TABLE likes ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table message
--

DROP TABLE IF EXISTS message;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE message (
  id bigint NOT NULL AUTO_INCREMENT,
  date datetime(6) DEFAULT NULL,
  message varchar(255) DEFAULT NULL,
  receiver_id bigint DEFAULT NULL,
  sender_id bigint DEFAULT NULL,
  status tinyint DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table message
--

LOCK TABLES message WRITE;
/*!40000 ALTER TABLE message DISABLE KEYS */;
INSERT INTO message (sender_id, receiver_id, message, date, status) VALUES 
(2, 3, 'Hey, how are you?', '2023-01-15 10:00:00', 1),
(3, 2, 'I am good, thanks!', '2023-01-15 10:05:00', 1),
(2, 3, 'Great to hear!', '2023-01-15 10:10:00', 0),

(4, 5, 'Are you attending the meeting?', '2023-02-01 09:30:00', 1),
(5, 4, 'Yes, I will be there.', '2023-02-01 09:35:00', 1),
(4, 5, 'Perfect, see you then!', '2023-02-01 09:40:00', 0),

(6, 7, 'Did you finish the report?', '2023-03-12 14:20:00', 1),
(7, 6, 'Almost done, will send it by EOD.', '2023-03-12 14:25:00', 1),
(6, 7, 'Thanks, appreciate it.', '2023-03-12 14:30:00', 0),

(8, 9, 'Let’s catch up later?', '2023-04-05 11:00:00', 1),
(9, 8, 'Sure, I am free after 3 PM.', '2023-04-05 11:05:00', 1),
(8, 9, 'Cool, talk to you then.', '2023-04-05 11:10:00', 0),

(10, 11, 'What’s the update on the project?', '2023-05-07 15:15:00', 1),
(11, 10, 'We are on track, no worries.', '2023-05-07 15:20:00', 1),
(10, 11, 'Good to know, thanks!', '2023-05-07 15:25:00', 0),

(12, 13, 'Can you send the documents?', '2023-06-18 08:45:00', 1),
(13, 12, 'I’ll send them in a minute.', '2023-06-18 08:50:00', 1),
(12, 13, 'Thanks a lot!', '2023-06-18 08:55:00', 0),

(14, 15, 'Any plans for the weekend?', '2023-07-20 17:30:00', 1),
(15, 14, 'Not yet, maybe a short trip.', '2023-07-20 17:35:00', 1),
(14, 15, 'Sounds fun, enjoy!', '2023-07-20 17:40:00', 0),

(16, 17, 'Can we reschedule our meeting?', '2023-08-10 13:00:00', 1),
(17, 16, 'Sure, let me check my calendar.', '2023-08-10 13:05:00', 1),
(16, 17, 'Thanks!', '2023-08-10 13:10:00', 0);
/*!40000 ALTER TABLE message ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table notification
--

DROP TABLE IF EXISTS notification;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE notification (
  id bigint NOT NULL AUTO_INCREMENT,
  date_sent datetime(6) DEFAULT NULL,
  is_comment bit(1) DEFAULT NULL,
  message longtext,
  article bigint DEFAULT NULL,
  receiver bigint DEFAULT NULL,
  sender bigint DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FKf8oas02vtqmqc1muml8qr43ng (article),
  KEY FKhivcjl7a0kx0owwuy2api09i7 (receiver),
  KEY FKgt20jhv21p1cifxbhwof1fijo (sender),
  CONSTRAINT FKf8oas02vtqmqc1muml8qr43ng FOREIGN KEY (article) REFERENCES article (id),
  CONSTRAINT FKgt20jhv21p1cifxbhwof1fijo FOREIGN KEY (sender) REFERENCES user (id),
  CONSTRAINT FKhivcjl7a0kx0owwuy2api09i7 FOREIGN KEY (receiver) REFERENCES user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table notification
--

LOCK TABLES notification WRITE;
/*!40000 ALTER TABLE notification DISABLE KEYS */;
-- Article 1 Likes
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) 
VALUES 
('2023-01-15 10:05:00', 0, NULL, 1, 2, 3), 
('2023-01-15 10:10:00', 0, NULL, 1, 2, 4),
('2023-01-15 10:15:00', 0, NULL, 1, 2, 5),
('2023-01-15 10:20:00', 0, NULL, 1, 2, 6),
('2023-01-15 10:25:00', 0, NULL, 1, 2, 7);

-- Article 2 Likes
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) 
VALUES 
('2023-02-20 14:05:00', 0, NULL, 2, 2, 8),
('2023-02-20 14:10:00', 0, NULL, 2, 2, 9),
('2023-02-20 14:15:00', 0, NULL, 2, 2, 10);

-- Article 3 Likes
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) 
VALUES 
('2023-03-05 09:35:00', 0, NULL, 3, 3, 11),
('2023-03-05 09:40:00', 0, NULL, 3, 3, 12),
('2023-03-05 09:45:00', 0, NULL, 3, 3, 13),
('2023-03-05 09:50:00', 0, NULL, 3, 3, 14);

-- Article 4 Likes
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) 
VALUES 
('2023-04-10 11:20:00', 0, NULL, 4, 3, 15),
('2023-04-10 11:25:00', 0, NULL, 4, 3, 16),
('2023-04-10 11:30:00', 0, NULL, 4, 3, 17);

-- Article 5 Likes
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) 
VALUES 
('2023-05-22 13:50:00', 0, NULL, 5, 3, 18),
('2023-05-22 13:55:00', 0, NULL, 5, 3, 19),
('2023-05-22 14:00:00', 0, NULL, 5, 3, 20),
('2023-05-22 14:05:00', 0, NULL, 5, 3, 21);

-- Article 6 Likes
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) 
VALUES 
('2023-06-12 08:05:00', 0, NULL, 6, 4, 22),
('2023-06-12 08:10:00', 0, NULL, 6, 4, 23),
('2023-06-12 08:15:00', 0, NULL, 6, 4, 24),
('2023-06-12 08:20:00', 0, NULL, 6, 4, 25);

-- Article 7 Likes
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) 
VALUES 
('2023-07-03 16:35:00', 0, NULL, 7, 5, 26),
('2023-07-03 16:40:00', 0, NULL, 7, 5, 27),
('2023-07-03 16:45:00', 0, NULL, 7, 5, 28);

-- Article 8 Likes
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) 
VALUES 
('2023-08-15 12:05:00', 0, NULL, 8, 5, 29),
('2023-08-15 12:10:00', 0, NULL, 8, 5, 30),
('2023-08-15 12:15:00', 0, NULL, 8, 5, 31);

-- Article 9 Likes
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) 
VALUES 
('2023-09-25 10:05:00', 0, NULL, 9, 5, 32),
('2023-09-25 10:10:00', 0, NULL, 9, 5, 33);

-- Article 10 Likes
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) 
VALUES 
('2023-10-05 14:25:00', 0, NULL, 10, 5, 34),
('2023-10-05 14:30:00', 0, NULL, 10, 5, 35),
('2023-10-05 14:35:00', 0, NULL, 10, 5, 36);

-- Article 1 Comments
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) 
VALUES 
('2023-01-15 12:05:00', 1, 'Great article on web development!', 1, 2, 8), 
('2023-01-15 12:10:00', 1, 'Thanks for sharing these insights.', 1, 2, 9);

-- Article 2 Comments
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) 
VALUES 
('2023-02-20 14:20:00', 1, 'Very informative comparison!', 2, 2, 10), 
('2023-02-20 14:25:00', 1, 'Python is definitely my favorite.', 2, 2, 11);

-- Article 3 Comments
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) 
VALUES 
('2023-03-05 09:55:00', 1, 'Data science is a game-changer!', 3, 3, 12), 
('2023-03-05 10:00:00', 1, 'Machine learning is the future.', 3, 3, 13);

-- Article 4 Comments
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) 
VALUES 
('2023-04-10 11:35:00', 1, 'DevOps practices are essential.', 4, 3, 14), 
('2023-04-10 11:40:00', 1, 'Great overview of the tools available.', 4, 3, 15);

-- Article 5 Comments
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) 
VALUES 
('2023-05-22 14:10:00', 1, 'Frontend development is evolving fast!', 5, 3, 16), 
('2023-05-22 14:15:00', 1, 'CSS Grid is a game-changer.', 5, 3, 17);

-- Article 6 Comments
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) 
VALUES 
('2023-06-12 08:25:00', 1, 'User experience should be a priority.', 6, 4, 18), 
('2023-06-12 08:30:00', 1, 'Accessibility is crucial.', 6, 4, 19);

-- Article 7 Comments
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) 
VALUES 
('2023-07-03 16:50:00', 1, 'Data engineering is becoming more important.', 7, 5, 20), 
('2023-07-03 16:55:00', 1, 'Great tips for managing data pipelines.', 7, 5, 21);

-- Article 8 Comments
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) 
VALUES 
('2023-08-15 12:20:00', 1, 'Scalability is critical for apps.', 8, 5, 22), 
('2023-08-15 12:25:00', 1, 'Thanks for sharing these tips.', 8, 5, 23);

-- Article 9 Comments
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) 
VALUES 
('2023-09-25 10:15:00', 1, 'Responsive design is key.', 9, 5, 24), 
('2023-09-25 10:20:00', 1, 'Great insights on web design.', 9, 5, 25);

-- Article 10 Comments
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) 
VALUES 
('2023-10-05 14:40:00', 1, 'Data analysis with Python is powerful.', 10, 5, 26), 
('2023-10-05 14:45:00', 1, 'SQL is a must for data management.', 10, 5, 27);

-- Article 11 Likes
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) 
VALUES 
('2023-11-10 10:05:00', 0, NULL, 11, 6, 37), 
('2023-11-10 10:10:00', 0, NULL, 11, 6, 38),
('2023-11-10 10:15:00', 0, NULL, 11, 6, 39);

-- Article 12 Likes
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) 
VALUES 
('2023-12-20 12:05:00', 0, NULL, 12, 6, 40), 
('2023-12-20 12:10:00', 0, NULL, 12, 6, 41);

-- Article 13 Likes
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) 
VALUES 
('2024-01-05 15:05:00', 0, NULL, 13, 7, 42), 
('2024-01-05 15:10:00', 0, NULL, 13, 7, 43),
('2024-01-05 15:15:00', 0, NULL, 13, 7, 44);

-- Article 14 Likes
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) 
VALUES 
('2024-02-10 09:05:00', 0, NULL, 14, 8, 45), 
('2024-02-10 09:10:00', 0, NULL, 14, 8, 2),
('2024-02-10 09:15:00', 0, NULL, 14, 8, 3);

-- Article 15 Likes
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) 
VALUES 
('2024-03-15 11:05:00', 0, NULL, 15, 8, 4), 
('2024-03-15 11:10:00', 0, NULL, 15, 8, 5),
('2024-03-15 11:15:00', 0, NULL, 15, 8, 6);

-- Article 16 Likes
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) 
VALUES 
('2024-04-20 16:05:00', 0, NULL, 16, 9, 7), 
('2024-04-20 16:10:00', 0, NULL, 16, 9, 8),
('2024-04-20 16:15:00', 0, NULL, 16, 9, 9);

-- Article 17 Likes
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) 
VALUES 
('2024-05-25 13:05:00', 0, NULL, 17, 9, 10), 
('2024-05-25 13:10:00', 0, NULL, 17, 9, 11),
('2024-05-25 13:15:00', 0, NULL, 17, 9, 12);

-- Article 18 Likes
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) 
VALUES 
('2024-06-10 10:05:00', 0, NULL, 18, 10, 13), 
('2024-06-10 10:10:00', 0, NULL, 18, 10, 14),
('2024-06-10 10:15:00', 0, NULL, 18, 10, 15);

-- Article 19 Likes
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) 
VALUES 
('2024-07-15 12:05:00', 0, NULL, 19, 10, 16), 
('2024-07-15 12:10:00', 0, NULL, 19, 10, 17);

-- Article 20 Likes
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) 
VALUES 
('2024-08-20 14:05:00', 0, NULL, 20, 10, 18), 
('2024-08-20 14:10:00', 0, NULL, 20, 10, 19),
('2024-08-20 14:15:00', 0, NULL, 20, 10, 20);

-- Article 11 Comments
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) 
VALUES 
('2023-11-10 11:05:00', 1, 'Networking is essential for IT professionals.', 11, 6, 28), 
('2023-11-10 11:10:00', 1, 'Great guide on setting up secure networks.', 11, 6, 29);

-- Article 12 Comments
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) 
VALUES 
('2023-12-20 13:05:00', 1, 'Mobile development has never been easier with Swift.', 12, 6, 30), 
('2023-12-20 13:10:00', 1, 'Thanks for the tips on Swift UI.', 12, 6, 31);

-- Article 13 Comments
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) 
VALUES 
('2024-01-05 16:05:00', 1, 'Machine learning in healthcare is revolutionary.', 13, 7, 32), 
('2024-01-05 16:10:00', 1, 'Exciting times for AI in medicine.', 13, 7, 33);

-- Article 14 Comments
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) 
VALUES 
('2024-02-10 10:05:00', 1, 'System administration requires constant vigilance.', 14, 8, 34), 
('2024-02-10 10:10:00', 1, 'Automation can save a lot of time in system management.', 14, 8, 35);

-- Article 15 Comments
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) 
VALUES 
('2024-03-15 12:05:00', 1, 'Cloud computing is transforming business.', 15, 8, 36), 
('2024-03-15 12:10:00', 1, 'AWS offers great services for startups.', 15, 8, 37);

-- Article 16 Comments
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) 
VALUES 
('2024-04-20 17:05:00', 1, 'MySQL is a reliable choice for database management.', 16, 9, 38), 
('2024-04-20 17:10:00', 1, 'Optimizing queries is key to performance.', 16, 9, 39);

-- Article 17 Comments
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) 
VALUES 
('2024-05-25 14:05:00', 1, 'Leading a dev team requires more than technical skills.', 17, 9, 40), 
('2024-05-25 14:10:00', 1, 'Communication is key in leadership.', 17, 9, 41);

-- Article 18 Comments
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) 
VALUES 
('2024-06-10 11:05:00', 1, 'Full stack development is demanding but rewarding.', 18, 10, 2), 
('2024-06-10 11:10:00', 1, 'JavaScript is versatile for both frontend and backend.', 18, 10, 3);

-- Article 19 Comments
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) 
VALUES 
('2024-07-15 13:05:00', 1, 'Backend architecture is critical for scalability.', 19, 10, 4), 
('2024-07-15 13:10:00', 1, 'API design can make or break a project.', 19, 10, 5);

-- Article 20 Comments
INSERT INTO notification (date_sent, is_comment,message, article, receiver, sender) 
VALUES 
('2024-08-31 12:15:00',1,'UI/UX design can significantly improve user retention.',20,12,6),
('2024-09-11 11:23:00',1,'Consider accessibility from the start.',20,12,7);

-- Notifications for Article 21
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) VALUES
('2023-11-01 11:00:00.000000', 0, NULL, 21, 6, 21),
('2023-11-01 11:00:00.000000', 0, NULL, 21, 6, 22),
('2023-11-01 11:00:00.000000', 0, NULL, 21, 6, 23),
('2023-11-01 11:00:00.000000', 1, 'Software architecture principles are foundational.', 21, 6, 21),
('2023-11-01 11:00:00.000000', 1, 'Modular design is the way forward.', 21, 6, 22);

-- Notifications for Article 22
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) VALUES
('2023-12-15 13:00:00.000000', 0, NULL, 22, 6, 24),
('2023-12-15 13:00:00.000000', 0, NULL, 22, 6, 25),
('2023-12-15 13:00:00.000000', 0, NULL, 22, 6, 26),
('2023-12-15 13:00:00.000000', 1, 'Cybersecurity is a moving target.', 22, 6, 24),
('2023-12-15 13:00:00.000000', 1, 'Stay updated on the latest security threats.', 22, 6, 25);

-- Notifications for Article 23
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) VALUES
('2023-12-22 09:00:00.000000', 0, NULL, 23, 7, 27),
('2023-12-22 09:00:00.000000', 0, NULL, 23, 7, 28),
('2023-12-22 09:00:00.000000', 0, NULL, 23, 7, 29),
('2023-12-22 09:00:00.000000', 1, 'Machine learning deployment is challenging but rewarding.', 23, 7, 27),
('2023-12-22 09:00:00.000000', 1, 'Careful planning is needed for production ML systems.', 23, 7, 28);

-- Notifications for Article 24
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) VALUES
('2023-06-21 15:00:00.000000', 0, NULL, 24, 8, 30),
('2023-06-21 15:00:00.000000', 0, NULL, 24, 8, 31),
('2023-06-21 15:00:00.000000', 0, NULL, 24, 8, 32),
('2023-06-21 15:00:00.000000', 1, 'DevOps practices are necessary for continuous delivery.', 24, 8, 30),
('2023-06-21 15:00:00.000000', 1, 'Automation tools can streamline workflows.', 24, 8, 31);

-- Notifications for Article 25
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) VALUES
('2023-07-08 17:00:00.000000', 0, NULL, 25, 9, 33),
('2023-07-08 17:00:00.000000', 0, NULL, 25, 9, 34),
('2023-07-08 17:00:00.000000', 1, 'Data pipeline optimization is key for performance.', 25, 9, 33),
('2023-07-08 17:00:00.000000', 1, 'Monitor your data processes regularly.', 25, 9, 34);

-- Notifications for Article 26
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) VALUES
('2023-08-03 10:00:00.000000', 0, NULL, 26, 10, 35),
('2023-08-03 10:00:00.000000', 0, NULL, 26, 10, 36),
('2023-08-03 10:00:00.000000', 0, NULL, 26, 10, 37),
('2023-08-03 10:00:00.000000', 1, 'Network security is critical for modern infrastructure.', 26, 10, 35),
('2023-08-03 10:00:00.000000', 1, 'Implement multiple layers of defense.', 26, 10, 36);

-- Notifications for Article 27
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) VALUES
('2023-09-25 10:00:00.000000', 0, NULL, 27, 11, 38),
('2023-09-25 10:00:00.000000', 0, NULL, 27, 11, 39),
('2023-09-25 10:00:00.000000', 0, NULL, 27, 11, 40),
('2023-09-25 10:00:00.000000', 1, 'Full stack developers need to balance frontend and backend skills.', 27, 11, 38),
('2023-09-25 10:00:00.000000', 1, 'Stay updated with the latest frameworks.', 27, 11, 39);

-- Notifications for Article 28
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) VALUES
('2023-10-05 14:20:00.000000', 0, NULL, 28, 12, 41),
('2023-10-05 14:20:00.000000', 0, NULL, 28, 12, 42),
('2023-10-05 14:20:00.000000', 1, 'Cloud architecture is evolving rapidly.', 28, 12, 41),
('2023-10-05 14:20:00.000000', 1, 'Stay ahead by learning new tools.', 28, 12, 42);

-- Notifications for Article 29
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) VALUES
('2023-11-01 11:00:00.000000', 0, NULL, 29, 12, 43),
('2023-11-01 11:00:00.000000', 0, NULL, 29, 12, 44),
('2023-11-01 11:00:00.000000', 1, 'Python remains a top choice for scalable applications.', 29, 12, 43),
('2023-11-01 11:00:00.000000', 1, 'Combine Python with cloud services for best results.', 29, 12, 44);

-- Notifications for Article 30
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) VALUES
('2023-11-15 11:00:00.000000', 0, NULL, 30, 13, 45),
('2023-11-15 11:00:00.000000', 0, NULL, 30, 13, 2),
('2023-11-15 11:00:00.000000', 0, NULL, 30, 13, 3),
('2023-11-15 11:00:00.000000', 1, 'Data science for beginners is accessible now more than ever.', 30, 13, 45),
('2023-11-15 11:00:00.000000', 1, 'Start with the basics and build from there.', 30, 13, 2),
('2023-11-15 11:00:00.000000', 1, 'Designing for mobile first ensures a broad reach.', 30, 13, 3);

-- Notifications for Article 31
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) VALUES
('2023-12-15 13:00:00.000000', 0, NULL, 31, 14, 4),
('2023-12-15 13:00:00.000000', 0, NULL, 31, 14, 5),
('2023-12-15 13:00:00.000000', 1, 'Designing for mobile first ensures a broad reach.', 31, 14, 4),
('2023-12-15 13:00:00.000000', 1, 'Consider user experience on all devices.', 31, 14, 5);

-- Notifications for Article 32
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) VALUES
('2024-01-10 10:00:00.000000', 0, NULL, 32, 20, 6),
('2024-01-10 10:00:00.000000', 0, NULL, 32, 20, 7),
('2024-01-10 10:00:00.000000', 0, NULL, 32, 20, 8),
('2024-01-10 10:00:00.000000', 1, 'Flutter is a powerful tool for mobile development.', 32, 20, 11),
('2024-01-10 10:00:00.000000', 1, 'Cross-platform development saves time and resources.', 32, 20, 12);

-- Notifications for Article 33
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) VALUES
('2024-02-05 14:00:00.000000', 0, NULL, 33, 21, 9),
('2024-02-05 14:00:00.000000', 0, NULL, 33, 21, 10),
('2024-02-05 14:00:00.000000', 0, NULL, 33, 21, 11),
('2024-02-05 14:00:00.000000', 1, 'Automating DevOps increases efficiency.', 33, 21, 13),
('2024-02-05 14:00:00.000000', 1, 'Start small with automation and scale up.', 33, 21, 14);

-- Notifications for Article 34
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) VALUES
('2024-03-01 11:00:00.000000', 0, NULL, 34, 22, 12),
('2024-03-01 11:00:00.000000', 0, NULL, 34, 22, 13),
('2024-03-01 11:00:00.000000', 1, 'Modern web apps require modern development techniques.', 34, 22, 15),
('2024-03-01 11:00:00.000000', 1, 'Use the latest tools to stay competitive.', 34, 22, 16);

-- Notifications for Article 35
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) VALUES
('2024-03-15 12:00:00.000000', 0, NULL, 35, 23, 14),
('2024-03-15 12:00:00.000000', 0, NULL, 35, 23, 15),
('2024-03-15 12:00:00.000000', 0, NULL, 35, 23, 16),
('2024-03-15 12:00:00.000000', 1, 'Data engineering challenges are evolving.', 35, 23, 17),
('2024-03-15 12:00:00.000000', 1, 'Focus on data quality and integrity.', 35, 23, 18);

-- Notifications for Article 36
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) VALUES
('2024-04-01 09:00:00.000000', 0, NULL, 36, 24, 17),
('2024-04-01 09:00:00.000000', 0, NULL, 36, 24, 18),
('2024-04-01 09:00:00.000000', 0, NULL, 36, 24, 19),
('2024-04-01 09:00:00.000000', 1, 'Cloud infrastructure needs to be secure.', 36, 24, 19),
('2024-04-01 09:00:00.000000', 1, 'Use encryption and access controls.', 36, 24, 20);

-- Notifications for Article 37
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) VALUES
('2024-04-15 15:00:00.000000', 0, NULL, 37, 25, 20),
('2024-04-15 15:00:00.000000', 0, NULL, 37, 25, 21),
('2024-04-15 15:00:00.000000', 1, 'Enterprise software development requires planning.', 37, 25, 21),
('2024-04-15 15:00:00.000000', 1, 'Involve stakeholders early in the process.', 37, 25, 22);

-- Notifications for Article 38
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) VALUES
('2024-05-10 10:00:00.000000', 0, NULL, 38, 26, 22),
('2024-05-10 10:00:00.000000', 0, NULL, 38, 26, 23),
('2024-05-10 10:00:00.000000', 0, NULL, 38, 26, 24),
('2024-05-10 10:00:00.000000', 1, 'DevOps with AWS integrates well into CI/CD pipelines.', 38, 26, 23),
('2024-05-10 10:00:00.000000', 1, 'Leverage AWS services for automation.', 38, 26, 24);

-- Notifications for Article 39
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) VALUES
('2024-06-01 11:00:00.000000', 0, NULL, 39, 27, 25),
('2024-06-01 11:00:00.000000', 0, NULL, 39, 27, 26),
('2024-06-01 11:00:00.000000', 0, NULL, 39, 27, 27),
('2024-06-01 11:00:00.000000', 1, 'Node.js is perfect for backend development.', 39, 27, 25),
('2024-06-01 11:00:00.000000', 1, 'Consider performance and scalability.', 39, 27, 26);

-- Notifications for Article 40
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) VALUES
('2024-07-01 12:00:00.000000', 0, NULL, 40, 28, 28),
('2024-07-01 12:00:00.000000', 0, NULL, 40, 28, 29),
('2024-07-01 12:00:00.000000', 1, 'Scalable systems require careful planning.', 40, 28, 27),
('2024-07-01 12:00:00.000000', 1, 'Use load balancers to manage traffic.', 40, 28, 28);

-- Notifications for Article 41
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) VALUES
('2024-08-01 10:00:00.000000', 0, NULL, 41, 29, 30),
('2024-08-01 10:00:00.000000', 0, NULL, 41, 29, 31),
('2024-08-01 10:00:00.000000', 0, NULL, 41, 29, 32),
('2024-08-01 10:00:00.000000', 1, 'Machine learning models need regular updates.', 41, 29, 29),
('2024-08-01 10:00:00.000000', 1, 'Monitor model performance over time.', 41, 29, 30);

-- Notifications for Article 42
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) VALUES
('2024-09-01 12:00:00.000000', 0, NULL, 42, 29, 33),
('2024-09-01 12:00:00.000000', 0, NULL, 42, 29, 34),
('2024-09-01 12:00:00.000000', 0, NULL, 42, 29, 35),
('2024-09-01 12:00:00.000000', 1, 'Leading teams requires both vision and strategy.', 42, 29, 31),
('2024-09-01 12:00:00.000000', 1, 'Communicate your vision clearly.', 42, 29, 32);

-- Notifications for Article 43
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) VALUES
('2024-10-01 10:00:00.000000', 0, NULL, 43, 30, 36),
('2024-10-01 10:00:00.000000', 0, NULL, 43, 30, 37),
('2024-10-01 10:00:00.000000', 0, NULL, 43, 30, 38),
('2024-10-01 10:00:00.000000', 1, 'Networking basics are essential for IT professionals.', 43, 30, 33),
('2024-10-01 10:00:00.000000', 1, 'Understand the fundamentals before diving deep.', 43, 30, 34);

-- Notifications for Article 44
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) VALUES
('2024-10-15 14:00:00.000000', 0, NULL, 44, 31, 39),
('2024-10-15 14:00:00.000000', 0, NULL, 44, 31, 40),
('2024-10-15 14:00:00.000000', 1, 'Cybersecurity in the cloud is complex.', 44, 31, 35),
('2024-10-15 14:00:00.000000', 1, 'Implement best practices for cloud security.', 44, 31, 36);

-- Notifications for Article 45
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) VALUES
('2024-11-01 09:00:00.000000', 0, NULL, 45, 32, 41),
('2024-11-01 09:00:00.000000', 0, NULL, 45, 32, 42),
('2024-11-01 09:00:00.000000', 0, NULL, 45, 32, 43),
('2024-11-01 09:00:00.000000', 1, 'Data science techniques evolve quickly.', 45, 32, 37),
('2024-11-01 09:00:00.000000', 1, 'Stay updated on the latest methods.', 45, 32, 38);

-- Notifications for Article 46
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) VALUES
('2024-11-15 15:00:00.000000', 0, NULL, 46, 33, 44),
('2024-11-15 15:00:00.000000', 0, NULL, 46, 33, 45),
('2024-11-15 15:00:00.000000', 0, NULL, 46, 33, 2),
('2024-11-15 15:00:00.000000', 1, 'Kotlin is great for Android development.', 46, 33, 39),
('2024-11-15 15:00:00.000000', 1, 'Leverage Kotlin’s features for better apps.', 46, 33, 40);

-- Notifications for Article 47
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) VALUES
('2024-12-01 11:00:00.000000', 0, NULL, 47, 34, 3),
('2024-12-01 11:00:00.000000', 0, NULL, 47, 34, 4),
('2024-12-01 11:00:00.000000', 0, NULL, 47, 34, 5),
('2024-12-01 11:00:00.000000', 1, 'React and Node.js are powerful together.', 47, 34, 41),
('2024-12-01 11:00:00.000000', 1, 'Build full stack apps efficiently.', 47, 34, 42);

-- Notifications for Article 48
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) VALUES
('2024-12-15 10:00:00.000000', 0, NULL, 48, 35, 6),
('2024-12-15 10:00:00.000000', 0, NULL, 48, 35, 7),
('2024-12-15 10:00:00.000000', 1, 'Understanding UX design principles is crucial.', 48, 35, 43),
('2024-12-15 10:00:00.000000', 1, 'Focus on user needs first.', 48, 35, 44);

-- Notifications for Article 49
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) VALUES
('2024-12-30 14:00:00.000000', 0, NULL, 49, 36, 8),
('2024-12-30 14:00:00.000000', 0, NULL, 49, 36, 9),
('2024-12-30 14:00:00.000000', 0, NULL, 49, 36, 10),
('2024-12-30 14:00:00.000000', 1, 'DevOps automation with Kubernetes.', 49, 36, 45),
('2024-12-30 14:00:00.000000', 1, 'Kubernetes simplifies deployment.', 49, 36, 2);

-- Notifications for Article 50
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) VALUES
('2024-12-31 11:00:00.000000', 0, NULL, 50, 36, 11),
('2024-12-31 11:00:00.000000', 0, NULL, 50, 36, 12),
('2024-12-31 11:00:00.000000', 1, 'Design systems improve consistency.', 50, 36, 3),
('2024-12-31 11:00:00.000000', 1, 'Implement design systems early.', 50, 36, 4);

-- Notifications for Article 51
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) VALUES
('2025-01-01 10:00:00.000000', 0, NULL, 51, 37, 13),
('2025-01-01 10:00:00.000000', 0, NULL, 51, 37, 14),
('2025-01-01 10:00:00.000000', 0, NULL, 51, 37, 15);

-- Notifications for Article 52
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) VALUES
('2025-01-15 14:00:00.000000', 0, NULL, 52, 38, 16),
('2025-01-15 14:00:00.000000', 0, NULL, 52, 38, 17),
('2025-01-15 14:00:00.000000', 0, NULL, 52, 38, 18);

-- Notifications for Article 53
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) VALUES
('2025-02-01 09:00:00.000000', 0, NULL, 53, 39, 19),
('2025-02-01 09:00:00.000000', 0, NULL, 53, 39, 20);

-- Notifications for Article 54
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) VALUES
('2025-02-15 15:00:00.000000', 0, NULL, 54, 40, 21),
('2025-02-15 15:00:00.000000', 0, NULL, 54, 40, 22),
('2025-02-15 15:00:00.000000', 0, NULL, 54, 40, 23);

-- Notifications for Article 55
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) VALUES
('2025-03-01 11:00:00.000000', 0, NULL, 55, 41, 24),
('2025-03-01 11:00:00.000000', 0, NULL, 55, 41, 25),
('2025-03-01 11:00:00.000000', 0, NULL, 55, 41, 26);

-- Notifications for Article 56
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) VALUES
('2025-03-15 10:00:00.000000', 0, NULL, 56, 42, 27),
('2025-03-15 10:00:00.000000', 0, NULL, 56, 42, 28),
('2025-03-15 10:00:00.000000', 0, NULL, 56, 42, 29);

-- Notifications for Article 57
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) VALUES
('2025-04-01 14:00:00.000000', 0, NULL, 57, 43, 30),
('2025-04-01 14:00:00.000000', 0, NULL, 57, 43, 31);

-- Notifications for Article 58
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) VALUES
('2025-04-15 12:00:00.000000', 0, NULL, 58, 44, 32),
('2025-04-15 12:00:00.000000', 0, NULL, 58, 44, 33),
('2025-04-15 12:00:00.000000', 0, NULL, 58, 44, 34);

-- Notifications for Article 59
INSERT INTO notification (date_sent, is_comment, message, article, receiver, sender) VALUES
('2025-05-01 09:00:00.000000', 0, NULL, 59, 45, 35),
('2025-05-01 09:00:00.000000', 0, NULL, 59, 45, 36),
('2025-05-01 09:00:00.000000', 0, NULL, 59, 45, 37);



/*!40000 ALTER TABLE notification ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table request
--

DROP TABLE IF EXISTS request;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE request (
  id bigint NOT NULL AUTO_INCREMENT,
  date_sent datetime(6) DEFAULT NULL,
  receiver bigint DEFAULT NULL,
  sender bigint DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FKq0fge3ypdy5vf5npcw7ugdumt (receiver),
  KEY FK1fpck1phlhkpky6wboygs18qq (sender),
  CONSTRAINT FK1fpck1phlhkpky6wboygs18qq FOREIGN KEY (sender) REFERENCES user (id),
  CONSTRAINT FKq0fge3ypdy5vf5npcw7ugdumt FOREIGN KEY (receiver) REFERENCES user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table request
--

LOCK TABLES request WRITE;
/*!40000 ALTER TABLE request DISABLE KEYS */;
INSERT INTO request (sender, receiver, date_sent) VALUES 
(11, 2, '2023-01-15 14:23:00'), 
(12, 2, '2023-02-10 09:34:12'), 
(13, 2, '2023-03-05 18:22:45'), 
(14, 2, '2023-04-12 07:50:30'),

(3, 12, '2023-01-18 11:15:34'), 
(3, 13, '2023-02-22 16:45:23'), 
(3, 14, '2023-03-16 20:10:08'), 
(3, 15, '2023-04-21 06:30:57'),

(4, 13, '2023-02-01 10:12:40'), 
(4, 14, '2023-03-09 14:56:30'), 
(4, 15, '2023-04-04 21:17:13'), 
(4, 16, '2023-05-14 08:45:10'),

(5, 14, '2023-03-03 12:23:20'), 
(5, 15, '2023-04-10 17:55:40'), 
(5, 16, '2023-05-05 19:32:50'), 
(5, 17, '2023-06-01 13:12:09'),

(6, 15, '2023-02-27 10:05:24'), 
(6, 16, '2023-03-15 14:48:35'), 
(6, 17, '2023-04-20 08:39:42'), 
(6, 18, '2023-05-25 11:57:20'),

(7, 16, '2023-03-09 09:30:14'), 
(7, 17, '2023-04-14 15:24:19'), 
(7, 18, '2023-05-19 07:11:29'), 
(7, 19, '2023-06-20 18:44:05'),

(8, 17, '2023-03-15 13:56:33'), 
(8, 18, '2023-04-23 09:14:41'), 
(8, 19, '2023-05-28 19:52:56'), 
(8, 20, '2023-07-03 08:21:50'),

(9, 18, '2023-02-18 16:07:45'), 
(9, 19, '2023-03-27 11:31:12'), 
(9, 20, '2023-05-02 21:10:40'), 
(9, 21, '2023-06-07 10:40:33'),

(10, 19, '2023-01-25 14:12:14'), 
(10, 20, '2023-02-28 09:03:25'), 
(10, 21, '2023-04-04 20:22:46'), 
(10, 22, '2023-05-10 07:17:59'),

(11, 20, '2023-03-06 12:08:31'), 
(11, 21, '2023-04-11 18:14:15'), 
(11, 22, '2023-05-16 08:26:40'), 
(11, 23, '2023-06-21 14:44:52'),

(12, 21, '2023-03-11 16:34:23'), 
(12, 22, '2023-04-18 11:22:10'), 
(12, 23, '2023-05-23 09:48:57'), 
(12, 24, '2023-06-28 19:31:40'),

(13, 22, '2023-03-14 10:25:30'), 
(13, 23, '2023-04-22 15:11:45'), 
(13, 24, '2023-05-27 20:36:00'), 
(13, 25, '2023-07-02 07:58:15'),

(14, 23, '2023-01-30 14:45:23'), 
(14, 24, '2023-03-06 08:21:50'), 
(14, 25, '2023-04-12 19:37:28'), 
(14, 26, '2023-05-17 11:52:03'),

(15, 24, '2023-02-19 12:40:45'), 
(15, 25, '2023-03-25 16:34:21'), 
(15, 26, '2023-05-01 09:13:50'), 
(15, 27, '2023-06-05 18:10:34'),

(16, 25, '2023-02-13 13:57:50'), 
(16, 26, '2023-03-20 17:43:05'), 
(16, 27, '2023-04-25 11:22:30'), 
(16, 28, '2023-05-30 21:55:45'),

(17, 26, '2023-01-24 11:22:34'), 
(17, 27, '2023-02-26 14:33:19'), 
(17, 28, '2023-04-02 18:44:29'), 
(17, 29, '2023-05-08 12:09:40'),

(18, 27, '2023-01-17 15:12:29'), 
(18, 28, '2023-03-23 09:07:56'), 
(18, 29, '2023-05-04 19:21:13'), 
(18, 30, '2023-06-09 07:42:21'),

(19, 28, '2023-02-15 13:34:18'), 
(19, 29, '2023-03-28 11:45:08'), 
(19, 30, '2023-05-09 08:12:34'), 
(19, 31, '2023-06-14 10:24:12'),

(20, 29, '2023-01-27 16:30:45'), 
(20, 30, '2023-03-02 15:23:12'), 
(20, 31, '2023-04-07 09:44:21'), 
(20, 32, '2023-05-12 20:35:33'),

(21, 30, '2023-02-08 10:05:55'), 
(21, 31, '2023-03-19 17:16:40'), 
(21, 32, '2023-04-23 12:24:10'), 
(21, 33, '2023-06-01 08:30:45'),

(22, 31, '2023-02-11 11:50:34'), 
(22, 32, '2023-03-26 18:45:13'), 
(22, 33, '2023-05-03 09:57:23'), 
(22, 34, '2023-06-07 14:41:58'),

(23, 32, '2023-01-23 09:25:23'), 
(23, 33, '2023-03-03 13:11:12'), 
(23, 34, '2023-04-08 20:52:30'), 
(23, 35, '2023-05-13 07:19:41'),

(24, 33, '2023-02-04 14:34:42'), 
(24, 34, '2023-03-15 08:40:25'), 
(24, 35, '2023-04-19 11:55:37'), 
(24, 36, '2023-05-24 16:28:56'),

(25, 34, '2023-01-12 10:30:28'), 
(25, 35, '2023-02-23 19:42:11'), 
(25, 36, '2023-04-01 09:14:20'), 
(25, 37, '2023-05-06 17:45:39'),

(26, 35, '2023-02-06 12:57:14'), 
(26, 36, '2023-03-18 11:23:34'), 
(26, 37, '2023-04-27 18:08:56'), 
(26, 38, '2023-06-02 13:15:42'),

(27, 36, '2023-01-31 15:05:48'), 
(27, 37, '2023-03-10 13:44:11'), 
(27, 38, '2023-04-13 21:25:23'), 
(27, 39, '2023-05-18 08:50:38'),

(28, 37, '2023-02-20 14:25:32'), 
(28, 38, '2023-03-29 17:09:05'), 
(28, 39, '2023-05-11 08:35:30'), 
(28, 40, '2023-06-16 19:24:49'),

(29, 38, '2023-02-02 12:45:15'), 
(29, 39, '2023-03-12 16:14:10'), 
(29, 40, '2023-04-16 19:52:23'), 
(29, 41, '2023-05-22 11:43:18'),

(30, 39, '2023-01-14 09:30:23'), 
(30, 40, '2023-03-01 13:28:34'), 
(30, 41, '2023-04-06 15:10:08'), 
(30, 42, '2023-05-11 20:18:47'),

(31, 40, '2023-02-12 11:07:19'), 
(31, 41, '2023-03-20 17:30:34'), 
(31, 42, '2023-04-28 08:23:58'), 
(31, 43, '2023-06-03 14:21:42'),

(32, 41, '2023-01-16 08:32:12'), 
(32, 42, '2023-02-21 16:44:29'), 
(32, 43, '2023-03-27 20:54:13'), 
(32, 44, '2023-05-09 09:12:50'),

(33, 42, '2023-01-19 13:27:54'), 
(33, 43, '2023-02-28 11:33:21'), 
(33, 44, '2023-04-03 18:49:56'), 
(33, 45, '2023-05-14 07:57:15'),

(34, 43, '2023-01-21 16:45:22'), 
(34, 44, '2023-03-05 09:56:39'), 
(34, 45, '2023-04-09 20:11:30'),

(35, 44, '2023-02-03 11:12:10'), 
(35, 45, '2023-03-17 17:19:23'),

(36, 44, '2023-01-20 09:14:28'),

(37, 45, '2023-02-24 10:11:34');
/*!40000 ALTER TABLE request ENABLE KEYS */;
UNLOCK TABLES;



--
-- Table structure for table user_education
--

DROP TABLE IF EXISTS user_education;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE user_education (
  education_id bigint NOT NULL,
  user_id bigint NOT NULL,
  KEY FKsaf9ufomub3a45jg7vhwaq168 (user_id),
  KEY FKon94bpljlhff0f12pc6jqtnkg (education_id),
  CONSTRAINT FKon94bpljlhff0f12pc6jqtnkg FOREIGN KEY (education_id) REFERENCES education (id),
  CONSTRAINT FKsaf9ufomub3a45jg7vhwaq168 FOREIGN KEY (user_id) REFERENCES user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table user_education
--

LOCK TABLES user_education WRITE;
/*!40000 ALTER TABLE user_education DISABLE KEYS */;
INSERT INTO user_education (user_id, education_id) VALUES 
(2, 1), (2, 2), (2, 3), (2, 4),
(3, 5), (3, 6), (3, 7), (3, 8),
(4, 9), (4, 10), (4, 11), (4, 12),
(5, 13), (5, 14), (5, 15), (5, 16),
(6, 17), (6, 18), (6, 19), (6, 20),
(7, 21), (7, 22), (7, 23), (7, 24),
(8, 25), (8, 26), (8, 27), (8, 28),
(9, 29), (9, 30), (9, 31), (9, 32),
(10, 33), (10, 34), (10, 35), (10, 36),
(11, 37), (11, 38), (11, 39), (11, 40),
(12, 1), (12, 5), (12, 9), (12, 13),
(13, 2), (13, 6), (13, 10), (13, 14),
(14, 3), (14, 7), (14, 11), (14, 15),
(15, 4), (15, 8), (15, 12), (15, 16),
(16, 17), (16, 21), (16, 25), (16, 29),
(17, 18), (17, 22), (17, 26), (17, 30),
(18, 19), (18, 23), (18, 27), (18, 31),
(19, 20), (19, 24), (19, 28), (19, 32),
(20, 33), (20, 37), (20, 1), (20, 5),
(21, 34), (21, 38), (21, 2), (21, 6),
(22, 35), (22, 39), (22, 3), (22, 7),
(23, 36), (23, 40), (23, 4), (23, 8),
(24, 9), (24, 13), (24, 17), (24, 21),
(25, 10), (25, 14), (25, 18), (25, 22),
(26, 11), (26, 15), (26, 19), (26, 23),
(27, 12), (27, 16), (27, 20), (27, 24),
(28, 25), (28, 29), (28, 33), (28, 37),
(29, 26), (29, 30), (29, 34), (29, 38),
(30, 27), (30, 31), (30, 35), (30, 39),
(31, 28), (31, 32), (31, 36), (31, 40),
(32, 1), (32, 5), (32, 9), (32, 13),
(33, 2), (33, 6), (33, 10), (33, 14),
(34, 3), (34, 7), (34, 11), (34, 15),
(35, 4), (35, 8), (35, 12), (35, 16),
(36, 17), (36, 21), (36, 25), (36, 29),
(37, 18), (37, 22), (37, 26), (37, 30),
(38, 19), (38, 23), (38, 27), (38, 31),
(39, 20), (39, 24), (39, 28), (39, 32),
(40, 33), (40, 37), (40, 1), (40, 5),
(41, 34), (41, 38), (41, 2), (41, 6),
(42, 35), (42, 39), (42, 3), (42, 7),
(43, 36), (43, 40), (43, 4), (43, 8),
(44, 9), (44, 13), (44, 17), (44, 21),
(45, 10), (45, 14), (45, 18), (45, 22);
/*!40000 ALTER TABLE user_education ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table user_experience
--

DROP TABLE IF EXISTS user_experience;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE user_experience (
  experience_id bigint NOT NULL,
  user_id bigint NOT NULL,
  KEY FK62ggxhyhd0por82q5b4c1e6qp (user_id),
  KEY FKj39f62q9k1wk3ieg0keoss6k7 (experience_id),
  CONSTRAINT FK62ggxhyhd0por82q5b4c1e6qp FOREIGN KEY (user_id) REFERENCES user (id),
  CONSTRAINT FKj39f62q9k1wk3ieg0keoss6k7 FOREIGN KEY (experience_id) REFERENCES experience (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table user_experience
--

LOCK TABLES user_experience WRITE;
/*!40000 ALTER TABLE user_experience DISABLE KEYS */;
INSERT INTO user_experience (user_id, experience_id) VALUES 
(2, 1), (2, 2), (2, 3), (2, 4),
(3, 5), (3, 6), (3, 7), (3, 8),
(4, 9), (4, 10), (4, 11), (4, 12),
(5, 13), (5, 14), (5, 15), (5, 16),
(6, 17), (6, 18), (6, 19), (6, 20),
(7, 21), (7, 22), (7, 23), (7, 24),
(8, 25), (8, 26), (8, 27), (8, 28),
(9, 29), (9, 30), (9, 31), (9, 32),
(10, 33), (10, 34), (10, 35), (10, 36),
(11, 37), (11, 38), (11, 39), (11, 40),
(12, 1), (12, 5), (12, 9), (12, 13),
(13, 2), (13, 6), (13, 10), (13, 14),
(14, 3), (14, 7), (14, 11), (14, 15),
(15, 4), (15, 8), (15, 12), (15, 16),
(16, 17), (16, 21), (16, 25), (16, 29),
(17, 18), (17, 22), (17, 26), (17, 30),
(18, 19), (18, 23), (18, 27), (18, 31),
(19, 20), (19, 24), (19, 28), (19, 32),
(20, 33), (20, 37), (20, 1), (20, 5),
(21, 34), (21, 38), (21, 2), (21, 6),
(22, 35), (22, 39), (22, 3), (22, 7),
(23, 36), (23, 40), (23, 4), (23, 8),
(24, 9), (24, 13), (24, 17), (24, 21),
(25, 10), (25, 14), (25, 18), (25, 22),
(26, 11), (26, 15), (26, 19), (26, 23),
(27, 12), (27, 16), (27, 20), (27, 24),
(28, 25), (28, 29), (28, 33), (28, 37),
(29, 26), (29, 30), (29, 34), (29, 38),
(30, 27), (30, 31), (30, 35), (30, 39),
(31, 28), (31, 32), (31, 36), (31, 40),
(32, 1), (32, 5), (32, 9), (32, 13),
(33, 2), (33, 6), (33, 10), (33, 14),
(34, 3), (34, 7), (34, 11), (34, 15),
(35, 4), (35, 8), (35, 12), (35, 16),
(36, 17), (36, 21), (36, 25), (36, 29),
(37, 18), (37, 22), (37, 26), (37, 30),
(38, 19), (38, 23), (38, 27), (38, 31),
(39, 20), (39, 24), (39, 28), (39, 32),
(40, 33), (40, 37), (40, 1), (40, 5),
(41, 34), (41, 38), (41, 2), (41, 6),
(42, 35), (42, 39), (42, 3), (42, 7),
(43, 36), (43, 40), (43, 4), (43, 8),
(44, 9), (44, 13), (44, 17), (44, 21),
(45, 10), (45, 14), (45, 18), (45, 22);
/*!40000 ALTER TABLE user_experience ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table user_skills
--

DROP TABLE IF EXISTS user_skills;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE user_skills (
  skill_id bigint NOT NULL,
  user_id bigint NOT NULL,
  KEY FKrpl0b19tyo13ryrhtxvqfn231 (user_id),
  KEY FKh223y61gwijpgqt6nlsuti07g (skill_id),
  CONSTRAINT FKh223y61gwijpgqt6nlsuti07g FOREIGN KEY (skill_id) REFERENCES skills (id),
  CONSTRAINT FKrpl0b19tyo13ryrhtxvqfn231 FOREIGN KEY (user_id) REFERENCES user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table user_skills
--

LOCK TABLES user_skills WRITE;
/*!40000 ALTER TABLE user_skills DISABLE KEYS */;
INSERT INTO user_skills (user_id, skill_id) VALUES 
(2, 1), (2, 2), (2, 3), (2, 4), (2, 5),
(3, 6), (3, 7), (3, 8), (3, 9), (3, 10),
(4, 11), (4, 12), (4, 13), (4, 14), (4, 15),
(5, 16), (5, 17), (5, 18), (5, 19), (5, 20),
(6, 21), (6, 22), (6, 23), (6, 24), (6, 25),
(7, 26), (7, 27), (7, 28), (7, 29), (7, 30),
(8, 31), (8, 32), (8, 33), (8, 34), (8, 35),
(9, 36), (9, 37), (9, 38), (9, 39), (9, 40),
(10, 1), (10, 6), (10, 11), (10, 16), (10, 21),
(11, 26), (11, 31), (11, 36), (11, 2), (11, 7),
(12, 12), (12, 17), (12, 22), (12, 27), (12, 32),
(13, 37), (13, 3), (13, 8), (13, 13), (13, 18),
(14, 23), (14, 28), (14, 33), (14, 38), (14, 4),
(15, 9), (15, 14), (15, 19), (15, 24), (15, 29),
(16, 34), (16, 39), (16, 5), (16, 10), (16, 15),
(17, 20), (17, 25), (17, 30), (17, 35), (17, 40),
(18, 1), (18, 11), (18, 21), (18, 31), (18, 36),
(19, 2), (19, 12), (19, 22), (19, 32), (19, 37),
(20, 3), (20, 13), (20, 23), (20, 33), (20, 38),
(21, 4), (21, 14), (21, 24), (21, 34), (21, 39),
(22, 5), (22, 15), (22, 25), (22, 35), (22, 40),
(23, 6), (23, 16), (23, 26), (23, 36), (23, 1),
(24, 7), (24, 17), (24, 27), (24, 37), (24, 2),
(25, 8), (25, 18), (25, 28), (25, 38), (25, 3),
(26, 9), (26, 19), (26, 29), (26, 39), (26, 4),
(27, 10), (27, 20), (27, 30), (27, 40), (27, 5),
(28, 1), (28, 12), (28, 23), (28, 34), (28, 6),
(29, 2), (29, 13), (29, 24), (29, 35), (29, 7),
(30, 3), (30, 14), (30, 25), (30, 36), (30, 8),
(31, 4), (31, 15), (31, 26), (31, 37), (31, 9),
(32, 5), (32, 16), (32, 27), (32, 38), (32, 10),
(33, 6), (33, 17), (33, 28), (33, 39), (33, 11),
(34, 7), (34, 18), (34, 29), (34, 40), (34, 12),
(35, 8), (35, 19), (35, 30), (35, 1), (35, 13),
(36, 9), (36, 20), (36, 31), (36, 2), (36, 14),
(37, 10), (37, 21), (37, 32), (37, 3), (37, 15),
(38, 11), (38, 22), (38, 33), (38, 4), (38, 16),
(39, 12), (39, 23), (39, 34), (39, 5), (39, 17),
(40, 13), (40, 24), (40, 35), (40, 6), (40, 18),
(41, 14), (41, 25), (41, 36), (41, 7), (41, 19),
(42, 15), (42, 26), (42, 37), (42, 8), (42, 20),
(43, 16), (43, 27), (43, 38), (43, 9), (43, 21),
(44, 17), (44, 28), (44, 39), (44, 10), (44, 22),
(45, 18), (45, 29), (45, 40), (45, 11), (45, 23);
/*!40000 ALTER TABLE user_skills ENABLE KEYS */;
UNLOCK TABLES;








-- Enable foreign key checks
SET FOREIGN_KEY_CHECKS = 1;


