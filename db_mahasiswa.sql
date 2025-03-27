-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 27, 2025 at 09:57 AM
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
-- Database: `db_mahasiswa`
--

-- --------------------------------------------------------

--
-- Table structure for table `mahasiswa`
--

CREATE TABLE `mahasiswa` (
  `id` int(11) NOT NULL,
  `nim` varchar(255) NOT NULL,
  `nama` varchar(255) NOT NULL,
  `nilai` varchar(255) NOT NULL,
  `alamat` varchar(255) NOT NULL,
  `jk` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `mahasiswa`
--

INSERT INTO `mahasiswa` (`id`, `nim`, `nama`, `nilai`, `alamat`, `jk`) VALUES
(1, '2203999', 'Amelia Zalfa Julianti', 'A', 'Bandung', 'Perempuan'),
(2, '2202292', 'Muhammad Iqbal Fadhilah', 'B', 'Bandung', 'Laki-Laki'),
(3, '2202346', 'Muhammad Rifky Afandi', 'A', 'Bandung', 'Laki-Laki'),
(4, '2210239', 'Muhammad Hanif Abdillah', 'B', 'Bandung', 'Laki-Laki'),
(6, '2205101', 'Kelvin Julian Putra', 'A', 'Bandung', 'Laki-Laki'),
(7, '2200163', 'Rifanny Lysara Annastasya', 'B', 'Bandung', 'Perempuan'),
(8, '2202869', 'Revana Faliha Salma', 'A', 'Bandung', 'Perempuan'),
(9, '2209489', 'Rakha Dhifiargo Hariadi', 'B', 'Bandung', 'Laki-Laki'),
(10, '2203142', 'Roshan Syalwan Nurilham', 'A', 'Bandung', 'Laki-Laki'),
(11, '2200311', 'Raden Rahman Ismail', 'B', 'Bandung', 'Laki-Laki'),
(12, '2200978', 'Ratu Syahirah Khairunnisa', 'A', 'Bandung', 'Perempuan'),
(13, '2204509', 'Muhammad Fahreza Fauzan', 'B', 'Bandung', 'Laki-Laki'),
(14, '2205027', 'Muhammad Rizki Revandi', 'A', 'Bandung', 'Laki-Laki'),
(15, '2203484', 'Arya Aydin Margono', 'B', 'Bandung', 'Laki-Laki'),
(16, '2200481', 'Marvel Ravindra Dioputra', 'A', 'Bandung', 'Laki-Laki'),
(17, '2209889', 'Muhammad Fadlul Hafiizh', 'B', 'Bandung', 'Laki-Laki'),
(18, '2206697', 'Rifa Sania', 'A', 'Bandung', 'Perempuan'),
(19, '2207260', 'Imam Chalish Rafidhul Haque', 'B', 'Bandung', 'Laki-Laki'),
(20, '2204343', 'Meiva Labibah Putri', 'B', 'Bandung', 'Perempuan'),
(22, '2345678', 'peter2', 'A', 'bekasi', 'Laki-Laki'),
(23, '2312345', 'Peter Parker', 'A', 'Margonda', 'Laki-Laki'),
(24, '2340853', 'Klara', 'A', 'Bandung', 'Perempuan'),
(30, '2308123', 'Shizuka', 'A', 'Jakarta', 'Perempuan'),
(31, '2307567', 'Hafsah', 'A', 'Bandung', 'Perempuan');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `mahasiswa`
--
ALTER TABLE `mahasiswa`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `mahasiswa`
--
ALTER TABLE `mahasiswa`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
