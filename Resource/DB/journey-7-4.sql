-- phpMyAdmin SQL Dump
-- version 3.5.5
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jul 04, 2013 at 09:53 PM
-- Server version: 5.5.14
-- PHP Version: 5.3.15

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `ihjourney`
--
CREATE DATABASE `ihjourney` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;
USE `ihjourney`;

-- --------------------------------------------------------

--
-- Table structure for table `ihj_call_record`
--

CREATE TABLE IF NOT EXISTS `ihj_call_record` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `platform` varchar(20) COLLATE utf8_bin NOT NULL,
  `os` varchar(20) COLLATE utf8_bin NOT NULL,
  `device` varchar(20) COLLATE utf8_bin NOT NULL,
  `address` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `number` varchar(20) COLLATE utf8_bin NOT NULL,
  `service_number` varchar(20) COLLATE utf8_bin NOT NULL,
  `times` bigint(20) NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='ihakula journey call record' AUTO_INCREMENT=1 ;

--
-- Dumping data for table `ihj_call_record`
--

-- --------------------------------------------------------

--
-- Table structure for table `ihj_feedback`
--

CREATE TABLE IF NOT EXISTS `ihj_feedback` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `platform` varchar(20) COLLATE utf8_bin NOT NULL,
  `os` varchar(20) COLLATE utf8_bin NOT NULL,
  `device` varchar(20) COLLATE utf8_bin NOT NULL,
  `description` longtext CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='ihakula journey feedback' AUTO_INCREMENT=1 ;

--
-- Dumping data for table `ihj_feedback`
--

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
