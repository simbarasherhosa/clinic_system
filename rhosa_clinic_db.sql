-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 14, 2025 at 04:44 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `rhosa_clinic_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `bed_allocation`
--

CREATE TABLE `bed_allocation` (
  `patient_id` varchar(255) DEFAULT NULL,
  `is_icu_needed` varchar(255) DEFAULT NULL,
  `sector` varchar(255) DEFAULT NULL,
  `ward` varchar(255) DEFAULT NULL,
  `bed` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `cause_or_description` varchar(255) DEFAULT NULL,
  `date` timestamp NOT NULL DEFAULT current_timestamp(),
  `time` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `discharging`
--

CREATE TABLE `discharging` (
  `patient_id` varchar(255) DEFAULT NULL,
  `is_patient_treated` varchar(255) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `date` timestamp NOT NULL DEFAULT current_timestamp(),
  `time` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `discharging`
--

INSERT INTO `discharging` (`patient_id`, `is_patient_treated`, `remarks`, `date`, `time`) VALUES
('5', 'Yes', 'Patient is treated', '2025-06-24 20:35:31', '2025-06-24 20:35:31'),
('5', 'Yes', 'Patient is treated', '2025-06-24 20:46:06', '2025-06-24 20:46:06'),
('', 'No', '', '2025-06-24 20:50:12', '2025-06-24 20:50:12'),
('5', 'Yes', 'Patient is treated', '2025-06-24 20:50:20', '2025-06-24 20:50:20'),
('5', 'Yes', 'Patient is treated', '2025-06-24 20:50:25', '2025-06-24 20:50:25'),
('', 'No', '', '2025-06-24 20:50:58', '2025-06-24 20:50:58'),
('', 'No', '', '2025-06-24 20:51:14', '2025-06-24 20:51:14'),
('', 'No', '', '2025-06-24 20:52:10', '2025-06-24 20:52:10');

-- --------------------------------------------------------

--
-- Table structure for table `employees`
--

CREATE TABLE `employees` (
  `employee_ID` int(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `dob` varchar(255) DEFAULT NULL,
  `id_number` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `department` varchar(255) DEFAULT NULL,
  `email_address` varchar(255) DEFAULT NULL,
  `marital_status` varchar(255) DEFAULT NULL,
  `nationality` varchar(255) DEFAULT NULL,
  `salary` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `employees`
--

INSERT INTO `employees` (`employee_ID`, `name`, `last_name`, `dob`, `id_number`, `address`, `sex`, `department`, `email_address`, `marital_status`, `nationality`, `salary`) VALUES
(1, 'Black', 'Widow', '12-12-2001', '123456H32', 'Russia', 'Female', 'Gynecology', 'black@widow.com', 'Single', 'Russian', '12000');

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE `login` (
  `Username` varchar(255) DEFAULT NULL,
  `Rank` varchar(255) DEFAULT NULL,
  `Password` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`Username`, `Rank`, `Password`) VALUES
('simba', 'Admin', 'simba'),
('tony', 'Employee', 'tony'),
('vision', 'Employee', 'vision'),
('widow', 'Admin', 'widow');

-- --------------------------------------------------------

--
-- Table structure for table `medical_activities`
--

CREATE TABLE `medical_activities` (
  `patient_id` varchar(255) DEFAULT NULL,
  `medical_aid` varchar(255) DEFAULT NULL,
  `form_of_payment` varchar(255) DEFAULT NULL,
  `currency` varchar(255) DEFAULT NULL,
  `medical_activity` varchar(255) DEFAULT NULL,
  `activity_amount` varchar(255) DEFAULT NULL,
  `amount_paid` varchar(255) DEFAULT NULL,
  `amount_owed` varchar(255) DEFAULT NULL,
  `date` timestamp NOT NULL DEFAULT current_timestamp(),
  `time` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `other_illness`
--

CREATE TABLE `other_illness` (
  `patient_id` varchar(255) DEFAULT NULL,
  `medical_aid` varchar(255) DEFAULT NULL,
  `form_of_payment` varchar(255) DEFAULT NULL,
  `currency` varchar(255) DEFAULT NULL,
  `illness` varchar(255) DEFAULT NULL,
  `medication_fee` varchar(255) DEFAULT NULL,
  `medication_fee_paid` varchar(255) DEFAULT NULL,
  `medication_fee_owed` varchar(255) DEFAULT NULL,
  `date` timestamp NOT NULL DEFAULT current_timestamp(),
  `time` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `other_illness`
--

INSERT INTO `other_illness` (`patient_id`, `medical_aid`, `form_of_payment`, `currency`, `illness`, `medication_fee`, `medication_fee_paid`, `medication_fee_owed`, `date`, `time`) VALUES
('5', 'No', 'Cash', 'USD', 'No', '100', '43', '57.0', '2025-06-25 12:09:41', '2025-06-25 12:09:41');

-- --------------------------------------------------------

--
-- Table structure for table `patient_registration`
--

CREATE TABLE `patient_registration` (
  `patient_id` int(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `dob` varchar(255) DEFAULT NULL,
  `contact_details` varchar(255) DEFAULT NULL,
  `home_address` varchar(255) DEFAULT NULL,
  `next_of_kin` varchar(255) DEFAULT NULL,
  `date` timestamp NOT NULL DEFAULT current_timestamp(),
  `time` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `patient_registration`
--

INSERT INTO `patient_registration` (`patient_id`, `name`, `last_name`, `gender`, `dob`, `contact_details`, `home_address`, `next_of_kin`, `date`, `time`) VALUES
(1, 'Thanos', 'Villian', 'Male', '19/12/1980', '+123456789', 'Titan', 'Gammora', '2025-06-18 19:52:56', '2025-06-18 19:52:56'),
(5, 'Spongebob', 'Squarepant', 'Male', '12-02-2004', '+555 123 234', 'Pineapple Bikini Bottom', 'Patrick Star', '2025-06-24 19:58:13', '2025-06-24 19:58:13');

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

CREATE TABLE `payment` (
  `patient_id` varchar(255) DEFAULT NULL,
  `medical_aid` varchar(255) DEFAULT NULL,
  `form_of_payment` varchar(255) DEFAULT NULL,
  `currency` varchar(255) DEFAULT NULL,
  `admition_fee` varchar(255) DEFAULT NULL,
  `admition_time_in_days` varchar(255) DEFAULT NULL,
  `admition_fee_paid` varchar(255) DEFAULT NULL,
  `admition_fee_owed` varchar(255) DEFAULT NULL,
  `illness` varchar(255) DEFAULT NULL,
  `medication_fee` varchar(255) DEFAULT NULL,
  `medication_fee_paid` varchar(255) DEFAULT NULL,
  `medication_fee_owed` varchar(255) DEFAULT NULL,
  `medical_activities` varchar(255) DEFAULT NULL,
  `other_illness` varchar(255) DEFAULT NULL,
  `total_amount_paid` varchar(255) DEFAULT NULL,
  `total_amount_owed` varchar(255) DEFAULT NULL,
  `date` timestamp NOT NULL DEFAULT current_timestamp(),
  `time` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `payment`
--

INSERT INTO `payment` (`patient_id`, `medical_aid`, `form_of_payment`, `currency`, `admition_fee`, `admition_time_in_days`, `admition_fee_paid`, `admition_fee_owed`, `illness`, `medication_fee`, `medication_fee_paid`, `medication_fee_owed`, `medical_activities`, `other_illness`, `total_amount_paid`, `total_amount_owed`, `date`, `time`) VALUES
('5', 'No', 'Cash', 'USD', '10', '5', '50', '0.0', 'Cancer', '1000', '1100', '-100.0', 'No', 'No', '1150.0', '-100.0', '2025-06-24 20:34:27', '2025-06-24 20:34:27');

-- --------------------------------------------------------

--
-- Table structure for table `registration`
--

CREATE TABLE `registration` (
  `patient_id` int(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `dob` varchar(255) DEFAULT NULL,
  `contact_details` varchar(255) DEFAULT NULL,
  `home_address` varchar(255) DEFAULT NULL,
  `next_of_kin` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `screening`
--

CREATE TABLE `screening` (
  `patient_id` varchar(255) DEFAULT NULL,
  `temperature` varchar(255) DEFAULT NULL,
  `heartbeat_rate_per_min` varchar(255) DEFAULT NULL,
  `blood_pressure` varchar(255) DEFAULT NULL,
  `skin_status` varchar(255) DEFAULT NULL,
  `remarks` varchar(9000) DEFAULT NULL,
  `date` timestamp NOT NULL DEFAULT current_timestamp(),
  `time` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `screening`
--

INSERT INTO `screening` (`patient_id`, `temperature`, `heartbeat_rate_per_min`, `blood_pressure`, `skin_status`, `remarks`, `date`, `time`) VALUES
('5', '36', '80', '140/20', 'Dry', 'Normal except the BP, more care should be taken. Skin status shows\n high ', '2025-06-24 20:00:12', '2025-06-24 20:00:12'),
('5', '40', '100', '100/80', 'Normal', 'Not a good status as the patient is noticing unprecedent temperature\nrises', '2025-06-24 20:03:53', '2025-06-24 20:03:53'),
('5', '40', '100', '100/80', 'Normal', 'Not a good status as the patient is noticing unprecedent temperature\nrises', '2025-06-24 20:05:49', '2025-06-24 20:05:49'),
('5', '', '', '', 'Normal', '', '2025-06-25 13:24:08', '2025-06-25 13:24:08');

-- --------------------------------------------------------

--
-- Table structure for table `treatment`
--

CREATE TABLE `treatment` (
  `patient_id` varchar(255) DEFAULT NULL,
  `illness` varchar(255) DEFAULT NULL,
  `drug` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `treatment`
--

INSERT INTO `treatment` (`patient_id`, `illness`, `drug`) VALUES
('5', 'Malaria', 'Penicillin');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `employees`
--
ALTER TABLE `employees`
  ADD PRIMARY KEY (`employee_ID`);

--
-- Indexes for table `patient_registration`
--
ALTER TABLE `patient_registration`
  ADD PRIMARY KEY (`patient_id`);

--
-- Indexes for table `registration`
--
ALTER TABLE `registration`
  ADD PRIMARY KEY (`patient_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `employees`
--
ALTER TABLE `employees`
  MODIFY `employee_ID` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `patient_registration`
--
ALTER TABLE `patient_registration`
  MODIFY `patient_id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `registration`
--
ALTER TABLE `registration`
  MODIFY `patient_id` int(255) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
