-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 12, 2019 at 09:03 PM
-- Server version: 10.4.6-MariaDB
-- PHP Version: 7.3.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `library_system`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteauthor` (IN `isbn` VARCHAR(15))  DELETE from b_author WHERE b_author.fk_isbn=isbn$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `insertauthor` (IN `fkisbn` VARCHAR(15), IN `aname` VARCHAR(20))  INSERT into b_author VALUES(fkisbn,aname)$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `insertbook` (IN `isbn` VARCHAR(15), IN `btitle` VARCHAR(100), IN `bdesc` VARCHAR(250), IN `sub` VARCHAR(20), IN `pdate` DATE, IN `pub` VARCHAR(15), IN `edition` INT(10), IN `fic` BOOLEAN, IN `dds` VARCHAR(15), IN `ebook` BOOLEAN, IN `nop` INT(10), IN `lcc` VARCHAR(15), IN `fklibid` VARCHAR(15))  INSERT INTO books VALUES(isbn,btitle,bdesc,sub,pdate,pub,edition,fic,dds,ebook,nop,lcc,fklibid)$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `insertinvent` (IN `libid` VARCHAR(15), IN `noc` INT(10), IN `sec` VARCHAR(5), IN `cat` VARCHAR(20), IN `staffinc` VARCHAR(30), IN `maxf` INT(10))  INSERT into inventory VALUES(libid,'BOOKS',noc,0,sec,cat,staffinc,maxf)$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `updatebooks` (IN `isbn` VARCHAR(15), IN `btitle` VARCHAR(100), IN `descrip` VARCHAR(250), IN `sub` VARCHAR(20), IN `pdate` DATE, IN `pub` VARCHAR(15), IN `edition` INT(10), IN `fic` BOOLEAN, IN `dds` VARCHAR(15), IN `ebook` BOOLEAN, IN `nop` INT(10), IN `lcc` VARCHAR(15), IN `fklibid` VARCHAR(15))  UPDATE books set books.isbn=isbn,books.b_title=btitle,books.b_description=descrip,books.subject=sub,books.b_pub_date=pdate,books.b_publisher_name=pub,books.edition=edition,books.isfiction=fic,books.dds=dds,books.isebook=ebook,books.no_of_pages=nop,books.lcc=lcc,books.fk_lib_id=fklibid WHERE books.isbn=isbn$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `updateinvent` (IN `libid` VARCHAR(15), IN `noc` INT(10), IN `sec` VARCHAR(5), IN `cat` VARCHAR(30), IN `staffinc` VARCHAR(30), IN `maxf` INT(10))  UPDATE inventory set inventory.lib_id=libid,inventory.type_of_item='BOOKS',inventory.no_of_copies=noc,inventory.borrowed_copies=0,inventory.section=sec,inventory.category=cat,inventory.staff_inc=staffinc,inventory.max_fine=maxf where inventory.lib_id=libid$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `address`
--

CREATE TABLE `address` (
  `fk_utd_id` varchar(10) NOT NULL,
  `addresses` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `books`
--

CREATE TABLE `books` (
  `isbn` varchar(15) NOT NULL,
  `b_title` varchar(100) NOT NULL,
  `b_description` varchar(250) NOT NULL,
  `subject` varchar(20) NOT NULL,
  `b_pub_date` date NOT NULL,
  `b_publisher_name` varchar(15) NOT NULL,
  `edition` int(10) NOT NULL,
  `isfiction` tinyint(1) NOT NULL,
  `dds` varchar(15) DEFAULT NULL,
  `isebook` tinyint(1) NOT NULL,
  `no_of_pages` int(10) NOT NULL,
  `lcc` varchar(15) NOT NULL,
  `fk_lib_id` varchar(15) NOT NULL
) ;

--
-- Dumping data for table `books`
--

INSERT INTO `books` (`isbn`, `b_title`, `b_description`, `subject`, `b_pub_date`, `b_publisher_name`, `edition`, `isfiction`, `dds`, `isebook`, `no_of_pages`, `lcc`, `fk_lib_id`) VALUES
('123111', 'CLOUD COMPUTING', 'jkabjbcalscnlc jascbsalc sjcbsaljcb', 'NETWORKING', '2019-10-01', 'GOODWILL', 3, 0, '789.111', 1, 95, 'NVB1299', 'LSBK12311'),
('123412', 'SUMEDH', 'sumedh34', 'SUMEDH34', '2019-10-27', 'SUNRISE', 3, 1, 'NULL', 0, 5, 'SUMEDH34', 'VSDVDS33'),
('123456', 'HARRY POTTER', 'magic and fun', 'ADVENTURE', '2019-10-17', 'JK', 1, 1, 'NULL', 0, 45, 'NVB1234', 'LSBK12345'),
('1234567890657', 'JNCAJ788', 'jncaj788', 'JNCAJ788', '2019-10-27', 'SUNRISE', 4, 1, 'NULL', 0, 7, 'JNCAJ788', 'JNCAJ788'),
('123457', 'HARRY POTTER AND ORDER OF PHOENIX', 'magic and fun', 'ADVENTURE', '2019-10-09', 'FAIRY', 1, 1, 'NULL', 1, 45, 'NVB1289', 'LSBK12346'),
('123489', 'DBMS', 'jkabjbcalscnlc jascbsalc sjcbsaljcb', 'DATABASE SYSTEMS', '2019-10-09', 'WISEMAN', 1, 0, '890.999', 0, 45, 'NVB1254', 'LSBK12340'),
('123990', 'SCI LAB', 'jkabjbcalscnlc jascbsalc sjcbsaljcb', 'DESIGN', '2019-10-07', 'SUNRISE', 2, 0, '789.100', 1, 95, 'NVB1879', 'LSBK17689'),
('199773', 'TARZAN', 'jdlvlavbnlvnlan', 'ADVENTURE', '2019-10-03', 'THAKUR', 5, 1, 'NULL', 0, 300, 'ADV8798', 'LSBK96989');

-- --------------------------------------------------------

--
-- Table structure for table `borrows`
--

CREATE TABLE `borrows` (
  `due_date` date NOT NULL,
  `borrow_date` date NOT NULL,
  `fk_utd_id` varchar(10) NOT NULL,
  `fk_lib_id` varchar(15) NOT NULL
) ;

-- --------------------------------------------------------

--
-- Table structure for table `b_author`
--

CREATE TABLE `b_author` (
  `fk_isbn` varchar(15) NOT NULL,
  `b_author_name` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `b_author`
--

INSERT INTO `b_author` (`fk_isbn`, `b_author_name`) VALUES
('123489', 'JOHN COLE'),
('123990', 'MATPAT'),
('123990', ' HARIS'),
('123457', 'JK ROWLING'),
('199773', 'MOWGLI'),
('123456', 'JK ROWLING'),
('123456', 'MANISH SISODIA'),
('123412', 'SUMEDH34'),
('123412', 'LAKSH'),
('123111', 'NURCAN'),
('1234567890657', 'JNCAJ788');

-- --------------------------------------------------------

--
-- Table structure for table `fines`
--

CREATE TABLE `fines` (
  `f_type` varchar(15) NOT NULL,
  `total_fine` int(10) NOT NULL,
  `fk_utd_id` varchar(10) NOT NULL,
  `fk_lib_id` varchar(15) NOT NULL
) ;

-- --------------------------------------------------------

--
-- Table structure for table `inventory`
--

CREATE TABLE `inventory` (
  `lib_id` varchar(15) NOT NULL,
  `type_of_item` varchar(15) NOT NULL,
  `no_of_copies` int(10) NOT NULL,
  `borrowed_copies` int(10) NOT NULL,
  `section` varchar(5) NOT NULL,
  `category` varchar(30) NOT NULL,
  `staff_inc` varchar(30) NOT NULL,
  `max_fine` int(10) NOT NULL
) ;

--
-- Dumping data for table `inventory`
--

INSERT INTO `inventory` (`lib_id`, `type_of_item`, `no_of_copies`, `borrowed_copies`, `section`, `category`, `staff_inc`, `max_fine`) VALUES
('JNCAJ788', 'BOOKS', 5, 0, 'A', 'JNCAJ788', 'JNCAJ788', 3),
('LSBK12311', 'BOOKS', 14, 0, 'A', 'COMPUTER SCIENCE', 'LOGAN PAUL', 20),
('LSBK12340', 'BOOKS', 10, 0, 'A', 'COMPUTER SCIENCE', 'vaidehi', 20),
('LSBK12345', 'BOOKS', 5, 0, 'F', 'NOVELS', 'MANISH PAUL', 30),
('LSBK12346', 'BOOKS', 10, 0, 'B', 'NOVELS', 'MANISH PAUL', 30),
('LSBK17689', 'BOOKS', 3, 0, 'B', 'ARCHITECTURE', 'LOGAN PAUL', 20),
('LSBK96989', 'BOOKS', 8, 0, 'B', 'NOVELS', 'MANISH PAUL', 20),
('VSDVDS33', 'BOOKS', 4, 0, 'F', 'SUMEDH34', 'SUMEDH34', 6);

-- --------------------------------------------------------

--
-- Table structure for table `journal`
--

CREATE TABLE `journal` (
  `j_issn` varchar(15) NOT NULL,
  `j_title` varchar(30) NOT NULL,
  `j_pub_date` date NOT NULL,
  `introduction` varchar(100) NOT NULL,
  `nature` varchar(20) NOT NULL,
  `fk_lib_id` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `j_author`
--

CREATE TABLE `j_author` (
  `fk_j_issn` varchar(15) NOT NULL,
  `j_author_name` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `magazines`
--

CREATE TABLE `magazines` (
  `m_issn` varchar(15) NOT NULL,
  `m_title` varchar(30) NOT NULL,
  `date_of_issue` date NOT NULL,
  `m_no_of_pages` int(10) NOT NULL,
  `m_category` varchar(20) NOT NULL,
  `m_publisher` varchar(20) NOT NULL,
  `fk_lib_id` varchar(15) NOT NULL
) ;

-- --------------------------------------------------------

--
-- Table structure for table `miscellaneous`
--

CREATE TABLE `miscellaneous` (
  `m_id` varchar(15) NOT NULL,
  `m_type_of_item` varchar(15) NOT NULL,
  `m_description` varchar(50) NOT NULL,
  `fk_lib_id` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `phone`
--

CREATE TABLE `phone` (
  `fk_utd_id` varchar(10) NOT NULL,
  `phone_no` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `restricted`
--

CREATE TABLE `restricted` (
  `r_id` varchar(15) NOT NULL,
  `r_title` varchar(20) NOT NULL,
  `r_type` varchar(20) NOT NULL,
  `r_category` varchar(30) NOT NULL,
  `fk_lib_id` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `utd_id` varchar(10) NOT NULL,
  `net_id` varchar(15) NOT NULL,
  `fname` varchar(15) NOT NULL,
  `minit` varchar(15) NOT NULL,
  `lname` varchar(15) NOT NULL,
  `email_id` varchar(25) NOT NULL,
  `gender` text NOT NULL,
  `department` text NOT NULL,
  `fk_u_type` varchar(20) NOT NULL
) ;

-- --------------------------------------------------------

--
-- Table structure for table `user_type`
--

CREATE TABLE `user_type` (
  `u_type` varchar(20) NOT NULL,
  `period_of_checkout` varchar(15) NOT NULL,
  `no_of_items` int(10) NOT NULL
) ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `address`
--
ALTER TABLE `address`
  ADD KEY `fk_utd_id` (`fk_utd_id`);

--
-- Indexes for table `books`
--
ALTER TABLE `books`
  ADD PRIMARY KEY (`isbn`),
  ADD UNIQUE KEY `isbn` (`isbn`),
  ADD UNIQUE KEY `lcc` (`lcc`),
  ADD KEY `fk_lib_id` (`fk_lib_id`),
  ADD KEY `subject_index` (`subject`);

--
-- Indexes for table `borrows`
--
ALTER TABLE `borrows`
  ADD KEY `fk_utd_id` (`fk_utd_id`),
  ADD KEY `fk_lib_id` (`fk_lib_id`);

--
-- Indexes for table `b_author`
--
ALTER TABLE `b_author`
  ADD KEY `fk_isbn` (`fk_isbn`),
  ADD KEY `bauthorname_index` (`b_author_name`);

--
-- Indexes for table `fines`
--
ALTER TABLE `fines`
  ADD KEY `fk_utd_id` (`fk_utd_id`),
  ADD KEY `fk_lib_id` (`fk_lib_id`);

--
-- Indexes for table `inventory`
--
ALTER TABLE `inventory`
  ADD PRIMARY KEY (`lib_id`),
  ADD UNIQUE KEY `lib_id` (`lib_id`),
  ADD KEY `category_index` (`category`),
  ADD KEY `section_index` (`section`);

--
-- Indexes for table `journal`
--
ALTER TABLE `journal`
  ADD PRIMARY KEY (`j_issn`),
  ADD UNIQUE KEY `j_issn` (`j_issn`),
  ADD KEY `fk_lib_id` (`fk_lib_id`),
  ADD KEY `nature_index` (`nature`);

--
-- Indexes for table `j_author`
--
ALTER TABLE `j_author`
  ADD KEY `fk_j_issn` (`fk_j_issn`),
  ADD KEY `jauthorname_index` (`j_author_name`);

--
-- Indexes for table `magazines`
--
ALTER TABLE `magazines`
  ADD PRIMARY KEY (`m_issn`),
  ADD UNIQUE KEY `m_issn` (`m_issn`),
  ADD KEY `fk_lib_id` (`fk_lib_id`),
  ADD KEY `mcategory_index` (`m_category`),
  ADD KEY `mpublisher_index` (`m_publisher`);

--
-- Indexes for table `miscellaneous`
--
ALTER TABLE `miscellaneous`
  ADD PRIMARY KEY (`m_id`),
  ADD UNIQUE KEY `m_id` (`m_id`),
  ADD KEY `fk_lib_id` (`fk_lib_id`);

--
-- Indexes for table `phone`
--
ALTER TABLE `phone`
  ADD KEY `fk_utd_id` (`fk_utd_id`);

--
-- Indexes for table `restricted`
--
ALTER TABLE `restricted`
  ADD PRIMARY KEY (`r_id`),
  ADD UNIQUE KEY `r_id` (`r_id`),
  ADD KEY `fk_lib_id` (`fk_lib_id`),
  ADD KEY `rcategory_index` (`r_category`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`utd_id`),
  ADD UNIQUE KEY `utd_id` (`utd_id`,`net_id`),
  ADD KEY `fk_u_type` (`fk_u_type`);

--
-- Indexes for table `user_type`
--
ALTER TABLE `user_type`
  ADD PRIMARY KEY (`u_type`),
  ADD UNIQUE KEY `u_type` (`u_type`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `address`
--
ALTER TABLE `address`
  ADD CONSTRAINT `address_ibfk_1` FOREIGN KEY (`fk_utd_id`) REFERENCES `user` (`utd_id`);

--
-- Constraints for table `books`
--
ALTER TABLE `books`
  ADD CONSTRAINT `books_ibfk_1` FOREIGN KEY (`fk_lib_id`) REFERENCES `inventory` (`lib_id`);

--
-- Constraints for table `borrows`
--
ALTER TABLE `borrows`
  ADD CONSTRAINT `borrows_ibfk_1` FOREIGN KEY (`fk_utd_id`) REFERENCES `user` (`utd_id`),
  ADD CONSTRAINT `borrows_ibfk_2` FOREIGN KEY (`fk_lib_id`) REFERENCES `inventory` (`lib_id`);

--
-- Constraints for table `b_author`
--
ALTER TABLE `b_author`
  ADD CONSTRAINT `b_author_ibfk_1` FOREIGN KEY (`fk_isbn`) REFERENCES `books` (`isbn`);

--
-- Constraints for table `fines`
--
ALTER TABLE `fines`
  ADD CONSTRAINT `fines_ibfk_1` FOREIGN KEY (`fk_utd_id`) REFERENCES `user` (`utd_id`),
  ADD CONSTRAINT `fines_ibfk_2` FOREIGN KEY (`fk_lib_id`) REFERENCES `inventory` (`lib_id`);

--
-- Constraints for table `journal`
--
ALTER TABLE `journal`
  ADD CONSTRAINT `journal_ibfk_1` FOREIGN KEY (`fk_lib_id`) REFERENCES `inventory` (`lib_id`);

--
-- Constraints for table `j_author`
--
ALTER TABLE `j_author`
  ADD CONSTRAINT `j_author_ibfk_1` FOREIGN KEY (`fk_j_issn`) REFERENCES `journal` (`j_issn`);

--
-- Constraints for table `magazines`
--
ALTER TABLE `magazines`
  ADD CONSTRAINT `magazines_ibfk_1` FOREIGN KEY (`fk_lib_id`) REFERENCES `inventory` (`lib_id`);

--
-- Constraints for table `miscellaneous`
--
ALTER TABLE `miscellaneous`
  ADD CONSTRAINT `miscellaneous_ibfk_1` FOREIGN KEY (`fk_lib_id`) REFERENCES `inventory` (`lib_id`);

--
-- Constraints for table `phone`
--
ALTER TABLE `phone`
  ADD CONSTRAINT `phone_ibfk_1` FOREIGN KEY (`fk_utd_id`) REFERENCES `user` (`utd_id`);

--
-- Constraints for table `restricted`
--
ALTER TABLE `restricted`
  ADD CONSTRAINT `restricted_ibfk_1` FOREIGN KEY (`fk_lib_id`) REFERENCES `inventory` (`lib_id`);

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`fk_u_type`) REFERENCES `user_type` (`u_type`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
