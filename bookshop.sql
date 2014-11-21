-- phpMyAdmin SQL Dump
-- version 4.2.7.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Nov 21, 2014 at 02:16 AM
-- Server version: 5.6.20
-- PHP Version: 5.5.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `bookshop`
--

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

CREATE TABLE IF NOT EXISTS `account` (
  `id` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `pass` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `perid` int(11) NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `account`
--

INSERT INTO `account` (`id`, `pass`, `perid`, `name`, `phone`, `email`) VALUES
('admin', 'admin', 1, 'Huynh Ngoc Nam', '0976528954', 'nam.huynh@gmail.com'),
('hien', '1234', 3, 'Huynh Thi Thu Hien', '123456', 'thuhien123@gmail.com'),
('nghia.nt', '123456', 1, 'Nguyen Trong Nghia', '0972357951', 'nghia.nt@gmail.com'),
('quy', '1234', 3, 'Quy', '0905928340', 'huuquy114@gmail.com'),
('tuan', '12345', 2, 'Tuan', '0978326202', 'kimtuan257@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `bill`
--

CREATE TABLE IF NOT EXISTS `bill` (
  `billid` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `id` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `date` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `bill`
--

INSERT INTO `bill` (`billid`, `id`, `date`) VALUES
('BID01', 'quy', '2014-11-20 02:17:02'),
('BID02', 'nghia.nt', '2014-11-20 02:23:02'),
('BID03', 'hien', '2014-11-20 03:00:21'),
('BID04', 'tuan', '2014-11-20 03:09:21'),
('BID05', 'hien', '2014-05-19 04:33:47'),
('BID06', 'quy', '2013-12-11 08:36:03'),
('BID07', 'nghia.nt', '2012-10-09 00:37:27'),
('BID08', 'tuan', '2013-11-13 03:40:55'),
('BID09', 'tuan', '2014-10-15 01:14:45'),
('BID10', 'hien', '2014-10-23 03:14:06'),
('BID11', 'quy', '2013-12-18 08:21:24');

-- --------------------------------------------------------

--
-- Table structure for table `billdetail`
--

CREATE TABLE IF NOT EXISTS `billdetail` (
  `billid` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `bookid` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `amountsell` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `billdetail`
--

INSERT INTO `billdetail` (`billid`, `bookid`, `amountsell`) VALUES
('BID01', 'B01', 2),
('BID01', 'B04', 1),
('BID01', 'B07', 1),
('BID02', 'B02', 3),
('BID03', 'B03', 2),
('BID03', 'B05', 2),
('BID04', 'B01', 1),
('BID04', 'B02', 1),
('BID04', 'B03', 2),
('BID04', 'B06', 5),
('BID05', 'B06', 5),
('BID06', 'B02', 2),
('BID06', 'B05', 3),
('BID07', 'B04', 1),
('BID07', 'B07', 10),
('BID08', 'B05', 2),
('BID09', 'B06', 7),
('BID10', 'B04', 1),
('BID10', 'B06', 4),
('BID10', 'B07', 3),
('BID11', 'B01', 3),
('BID11', 'B02', 2),
('BID11', 'B03', 4);

--
-- Triggers `billdetail`
--
DELIMITER //
CREATE TRIGGER `amountsell_delete` BEFORE DELETE ON `billdetail`
 FOR EACH ROW UPDATE book as b
SET b.amount = b.amount + OLD.amountsell
WHERE b.bookid = OLD.bookid
//
DELIMITER ;
DELIMITER //
CREATE TRIGGER `amountsell_insert` BEFORE INSERT ON `billdetail`
 FOR EACH ROW UPDATE book as b
SET b.amount = b.amount - NEW.amountsell
WHERE b.bookid = NEW.bookid
//
DELIMITER ;
DELIMITER //
CREATE TRIGGER `amountsell_update` BEFORE UPDATE ON `billdetail`
 FOR EACH ROW UPDATE book as b
SET b.amount = b.amount + (OLD.amountsell - NEW.amountsell)
WHERE b.bookid = NEW.bookid AND NEW.bookid = OLD.bookid
//
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `book`
--

CREATE TABLE IF NOT EXISTS `book` (
  `bookid` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `bookname` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `categoryid` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `amount` int(11) unsigned NOT NULL DEFAULT '0',
  `unitprice` int(11) unsigned NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `book`
--

INSERT INTO `book` (`bookid`, `bookname`, `categoryid`, `amount`, `unitprice`) VALUES
('B01', 'Math 12', 'C02', 47, 30000),
('B02', 'Gone With The Wind', 'C01', 20, 150000),
('B03', 'A Little Princess', 'C03', 24, 55000),
('B04', 'History 12', 'C02', 24, 20000),
('B05', 'One Thousand and One Nights', 'C01', 38, 1000000),
('B06', 'The Princess and the Pea', 'C03', 56, 21700),
('B07', 'Book of Science', 'C02', 56, 320000);

-- --------------------------------------------------------

--
-- Table structure for table `bookcategories`
--

CREATE TABLE IF NOT EXISTS `bookcategories` (
  `categoryid` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `categoryname` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `bookcategories`
--

INSERT INTO `bookcategories` (`categoryid`, `categoryname`) VALUES
('C01', 'Novel'),
('C02', 'Textbook'),
('C03', 'Children book');

-- --------------------------------------------------------

--
-- Table structure for table `permission`
--

CREATE TABLE IF NOT EXISTS `permission` (
  `perid` int(11) NOT NULL,
  `pername` varchar(50) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `permission`
--

INSERT INTO `permission` (`perid`, `pername`) VALUES
(1, 'Administrator'),
(2, 'Shop manager'),
(3, 'Employee');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account`
--
ALTER TABLE `account`
 ADD PRIMARY KEY (`id`), ADD KEY `perid` (`perid`);

--
-- Indexes for table `bill`
--
ALTER TABLE `bill`
 ADD PRIMARY KEY (`billid`), ADD KEY `id` (`id`);

--
-- Indexes for table `billdetail`
--
ALTER TABLE `billdetail`
 ADD PRIMARY KEY (`billid`,`bookid`), ADD KEY `bookid` (`bookid`);

--
-- Indexes for table `book`
--
ALTER TABLE `book`
 ADD PRIMARY KEY (`bookid`), ADD KEY `categoryid` (`categoryid`);

--
-- Indexes for table `bookcategories`
--
ALTER TABLE `bookcategories`
 ADD PRIMARY KEY (`categoryid`);

--
-- Indexes for table `permission`
--
ALTER TABLE `permission`
 ADD PRIMARY KEY (`perid`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `account`
--
ALTER TABLE `account`
ADD CONSTRAINT `account_ibfk_1` FOREIGN KEY (`perid`) REFERENCES `permission` (`perid`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `bill`
--
ALTER TABLE `bill`
ADD CONSTRAINT `bill_ibfk_1` FOREIGN KEY (`id`) REFERENCES `account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `billdetail`
--
ALTER TABLE `billdetail`
ADD CONSTRAINT `billdetail_ibfk_1` FOREIGN KEY (`billid`) REFERENCES `bill` (`billid`) ON DELETE CASCADE ON UPDATE CASCADE,
ADD CONSTRAINT `billdetail_ibfk_2` FOREIGN KEY (`bookid`) REFERENCES `book` (`bookid`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `book`
--
ALTER TABLE `book`
ADD CONSTRAINT `book_ibfk_1` FOREIGN KEY (`categoryid`) REFERENCES `bookcategories` (`categoryid`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
