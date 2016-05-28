-- phpMyAdmin SQL Dump
-- version 4.0.10.14
-- http://www.phpmyadmin.net
--
-- Servidor: localhost:3306
-- Tempo de Geração: 28/05/2016 às 19:01
-- Versão do servidor: 5.5.49-cll
-- Versão do PHP: 5.4.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Banco de dados: `whaves_scmu2`
--

-- --------------------------------------------------------

--
-- Estrutura para tabela `account`
--

CREATE TABLE IF NOT EXISTS `account` (
  `idaccount` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `salt` varchar(255) NOT NULL,
  `locked` varchar(255) NOT NULL,
  `idstate` bigint(20) NOT NULL,
  PRIMARY KEY (`idaccount`),
  KEY `fk_account_idstatex` (`idstate`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=51 ;

--
-- Fazendo dump de dados para tabela `account`
--

INSERT INTO `account` (`idaccount`, `username`, `password`, `salt`, `locked`, `idstate`) VALUES
(42, 'test', '9107e9f9810368ba35eb0b795f8af295ed11c9cfee8c91559aec0066a624349a9a5ecf18e3a04f3dfa83c49e1799a4eaa624e5ac30b32150c420c44e776570a3', 'pbtsF02C4NbK131o7f3O', '0', 42),
(43, 'root', '7ef0f3a74cf34741b12b497ab2e32fd20b741f7b3ddc0c94384344795e6e44f99915ae8e9c54f841a1c3f332348daf7eeb19c624f20b97da6cbc390f59b313bc', '3Jn2Cs6s2OU3En7OLM8E', '0', 43),
(44, 'nuno', '04b1c3d3ad42d3f3f7013fde7cdbcdba4e30024ba3188aa5a88d7c40eec065905b11aeda186d7df2607c72dd4dba73f241b9b3c7fbacba85a8a527e42faf3726', 'HnID00h2qe27cUpE3Kiq', '0', 44),
(45, 'miguel', '23ad01e8e0caa05172ca456694e0074db7fd5a7f232afab2ad6ac3afa2b75e99151e60f6aa4ea0ba994b3516e03837a292ef4003b79e075e7cf799f127990a31', '3NGeIbr0T80C81j0qrGM', '0', 45),
(46, 'douglas', '5e0e90c37f6ffc8bad73418573491eeddede43f353aa62afecd95db3357d04aa07d4f3ee79e79e8960dff1a5dcd830d2c5814e08f422b9e6f5c9e01747a12b9d', '7mf44g86T0p8AEnr6Ik4', '0', 41),
(47, 'jose', '55ced3c6ab55faa1075c8752174c5d2bbc68bac73047198fb2f1964f060cec2d16d93590b8199cde0cba34b1541615e29a994a0f2df755580cd0a61976999e90', 'GKNK88lUFmMq32f3Ea2L', '0', 47),
(48, 'rafael', '1b56c12814962e1891120ccd05e399085fb185378ede42e7571d70baa3b1f79a3d4b0d59684a2a4413fbeaa29b947e85330892130b8da225a77f768b6b5c185a', 'dpjjPUroIU3eUr9M0JcS', '0', 48),
(49, 'ines', 'edaede9cf467bef7d0884e8db199d0f0faf3c0c33e53f6b94660e300e079fa264a4c4b6552ad458310dd95913732e3f1bc70a7cf73b0433697ba49ae00bd117d', 'eK3od7FmRjMUm102kSP7', '0', 49),
(50, 'patricia', 'ebd928802295889ee432e37bc42ddb8e229a5302b6b120c32bc1f61a6a0932794d5d1801ddb1d7ebac8ad1ddea721587db6a103dbcf37f9dcff1925426c01146', 'QqGe372F4tnqBdD2jFG6', '0', 50),
(41, 'avelino', '3a3e8a20238a336b4fce160e5cf689c9c8d8c26f58bad29513c59d9f5e4af2f0334db46d342e92b73e58dd3e3cc0305222de42bbdebc54dc7fe9e805b97a7971', '5Dte8tEOCO31DbJBJBrR', '0', 41);

-- --------------------------------------------------------

--
-- Estrutura para tabela `schedule`
--

CREATE TABLE IF NOT EXISTS `schedule` (
  `idschedule` bigint(20) NOT NULL AUTO_INCREMENT,
  `json` varchar(500) NOT NULL,
  `namegroup` varchar(200) NOT NULL,
  PRIMARY KEY (`idschedule`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=63 ;

--
-- Fazendo dump de dados para tabela `schedule`
--

INSERT INTO `schedule` (`idschedule`, `json`, `namegroup`) VALUES
(62, '{"id":41,"lamp":true,"alarm":false,"alarmSensor":true,"smsNotifications":true,"harmfulGases":50.0,"luminosity":10.0,"temperature":12.0,"latitude":"58.6642216","longitude":"-9.2068716","d":"2016-05-28","t":"16:09:00"}', '77Il9N9c6G'),
(61, '{"id":41,"lamp":false,"alarm":false,"alarmSensor":false,"smsNotifications":true,"harmfulGases":50.0,"luminosity":10.0,"temperature":12.0,"latitude":"-31","longitude":"9","d":"2016-05-28","t":"11:38:00"}', '4TIj6g3E2N'),
(60, '{"id":41,"lamp":true,"alarm":false,"alarmSensor":false,"smsNotifications":true,"harmfulGases":50.0,"luminosity":10.0,"temperature":12.0,"latitude":"-31","longitude":"9","d":"2016-05-28","t":"11:35:00"}', 'P1Ftt82NIt'),
(59, '{"id":41,"lamp":false,"alarm":false,"alarmSensor":false,"smsNotifications":true,"harmfulGases":50.0,"luminosity":10.0,"temperature":12.0,"latitude":"-31","longitude":"9","d":"2016-05-28","t":"07:35:00"}', '69fr5qelA7'),
(58, '{"id":41,"lamp":false,"alarm":false,"alarmSensor":false,"smsNotifications":true,"harmfulGases":50.0,"luminosity":10.0,"temperature":12.0,"latitude":"-31","longitude":"9","d":"2016-05-28","t":"07:24:00"}', 'DQ5r3tdBG7'),
(57, '{"id":41,"lamp":true,"alarm":false,"alarmSensor":false,"smsNotifications":true,"harmfulGases":50.0,"luminosity":10.0,"temperature":12.0,"latitude":"-31","longitude":"9","d":"2016-05-28","t":"07:22:00"}', '7LGhj407R8'),
(56, '{"id":41,"lamp":true,"alarm":false,"alarmSensor":false,"smsNotifications":true,"harmfulGases":50.0,"luminosity":10.0,"temperature":12.0,"latitude":"-31","longitude":"9","d":"2016-05-28","t":"21:30:00"}', 'nnC5j6fES6'),
(55, '{"id":41,"lamp":true,"alarm":false,"alarmSensor":false,"smsNotifications":true,"harmfulGases":50.0,"luminosity":10.0,"temperature":12.0,"latitude":"-31","longitude":"9","d":"2016-05-28","t":"21:21:00"}', 'NcLD8Q6Agq');

-- --------------------------------------------------------

--
-- Estrutura para tabela `state`
--

CREATE TABLE IF NOT EXISTS `state` (
  `idstate` bigint(20) NOT NULL AUTO_INCREMENT,
  `lamp` tinyint(1) DEFAULT NULL,
  `alarm` tinyint(1) DEFAULT NULL,
  `alarmSensor` tinyint(1) DEFAULT NULL,
  `smsNotifications` tinyint(1) DEFAULT NULL,
  `harmfulGases` float DEFAULT NULL,
  `luminosity` float DEFAULT NULL,
  `temperature` float DEFAULT NULL,
  `latitude` varchar(45) DEFAULT NULL,
  `longitude` varchar(45) DEFAULT NULL,
  `date` varchar(45) NOT NULL,
  `time` varchar(45) NOT NULL,
  PRIMARY KEY (`idstate`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=51 ;

--
-- Fazendo dump de dados para tabela `state`
--

INSERT INTO `state` (`idstate`, `lamp`, `alarm`, `alarmSensor`, `smsNotifications`, `harmfulGases`, `luminosity`, `temperature`, `latitude`, `longitude`, `date`, `time`) VALUES
(50, 0, 1, 1, 0, 33.2339, 98.3966, 8.92564, '-31', '8', '2016-06-23', '00:00:00'),
(49, 1, 0, 0, 0, 19.3505, 98.2475, 80.7956, '-31', '8', '2016-06-23', '00:00:00'),
(48, 1, 1, 1, 1, 28.3564, 96.5577, 71.3943, '-31', '8', '2016-06-23', '00:00:00'),
(47, 1, 0, 0, 1, 80.3768, 16.8907, 83.2155, '-31', '8', '2016-06-23', '00:00:00'),
(46, 1, 1, 0, 1, 57.6634, 48.1237, 47.198, '38.6647566', '-9.2084666', '2016-06-23', '00:00:00'),
(45, 1, 0, 0, 1, 82.9148, 75.2518, 77.3374, '-31', '8', '2016-06-23', '00:00:00'),
(44, 1, 0, 0, 1, 76.6536, 52.4317, 68.2707, '-31', '8', '2016-05-27', '07:59:00'),
(43, 1, 0, 0, 0, 86.2582, 23.3698, 5.95735, '-31', '8', '2016-06-23', '00:00:00'),
(42, 1, 1, 0, 0, 11.6179, 26.1178, 70.4982, '-31', '8', '2016-06-23', '00:00:00'),
(41, 0, 0, 1, 1, 10, 9.27, 23, '38.6650767', '-9.2093252', '2016-05-28', '16:09:00');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
