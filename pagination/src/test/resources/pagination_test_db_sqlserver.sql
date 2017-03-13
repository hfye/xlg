USE [master]
GO

/****** Object:  Database [pagination_test]    Script Date: 28/2/2017 12:32:12 PM ******/
DROP DATABASE [pagination_test]
GO

/****** Object:  Database [pagination_test]    Script Date: 28/2/2017 12:32:12 PM ******/
CREATE DATABASE [pagination_test]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'pagination_test', FILENAME = N'C:\Program Files (x86)\Microsoft SQL Server\MSSQL12.SQLEXPRESS\MSSQL\DATA\pagination_test.mdf' , SIZE = 5120KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'pagination_test_log', FILENAME = N'C:\Program Files (x86)\Microsoft SQL Server\MSSQL12.SQLEXPRESS\MSSQL\DATA\pagination_test_log.ldf' , SIZE = 1024KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO

ALTER DATABASE [pagination_test] SET COMPATIBILITY_LEVEL = 120
GO

IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [pagination_test].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO

ALTER DATABASE [pagination_test] SET ANSI_NULL_DEFAULT OFF 
GO

ALTER DATABASE [pagination_test] SET ANSI_NULLS OFF 
GO

ALTER DATABASE [pagination_test] SET ANSI_PADDING OFF 
GO

ALTER DATABASE [pagination_test] SET ANSI_WARNINGS OFF 
GO

ALTER DATABASE [pagination_test] SET ARITHABORT OFF 
GO

ALTER DATABASE [pagination_test] SET AUTO_CLOSE OFF 
GO

ALTER DATABASE [pagination_test] SET AUTO_SHRINK OFF 
GO

ALTER DATABASE [pagination_test] SET AUTO_UPDATE_STATISTICS ON 
GO

ALTER DATABASE [pagination_test] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO

ALTER DATABASE [pagination_test] SET CURSOR_DEFAULT  GLOBAL 
GO

ALTER DATABASE [pagination_test] SET CONCAT_NULL_YIELDS_NULL OFF 
GO

ALTER DATABASE [pagination_test] SET NUMERIC_ROUNDABORT OFF 
GO

ALTER DATABASE [pagination_test] SET QUOTED_IDENTIFIER OFF 
GO

ALTER DATABASE [pagination_test] SET RECURSIVE_TRIGGERS OFF 
GO

ALTER DATABASE [pagination_test] SET  DISABLE_BROKER 
GO

ALTER DATABASE [pagination_test] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO

ALTER DATABASE [pagination_test] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO

ALTER DATABASE [pagination_test] SET TRUSTWORTHY OFF 
GO

ALTER DATABASE [pagination_test] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO

ALTER DATABASE [pagination_test] SET PARAMETERIZATION SIMPLE 
GO

ALTER DATABASE [pagination_test] SET READ_COMMITTED_SNAPSHOT OFF 
GO

ALTER DATABASE [pagination_test] SET HONOR_BROKER_PRIORITY OFF 
GO

ALTER DATABASE [pagination_test] SET RECOVERY SIMPLE 
GO

ALTER DATABASE [pagination_test] SET  MULTI_USER 
GO

ALTER DATABASE [pagination_test] SET PAGE_VERIFY CHECKSUM  
GO

ALTER DATABASE [pagination_test] SET DB_CHAINING OFF 
GO

ALTER DATABASE [pagination_test] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO

ALTER DATABASE [pagination_test] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO

ALTER DATABASE [pagination_test] SET DELAYED_DURABILITY = DISABLED 
GO

ALTER DATABASE [pagination_test] SET  READ_WRITE 
GO


