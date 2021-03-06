USE [master]
GO
/****** Object:  Database [freetut]    Script Date: 4/9/2021 6:09:31 PM ******/
CREATE DATABASE [freetut]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'freetut', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\freetut.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'freetut_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\freetut_log.ldf' , SIZE = 1536KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [freetut] SET COMPATIBILITY_LEVEL = 120
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [freetut].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [freetut] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [freetut] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [freetut] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [freetut] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [freetut] SET ARITHABORT OFF 
GO
ALTER DATABASE [freetut] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [freetut] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [freetut] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [freetut] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [freetut] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [freetut] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [freetut] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [freetut] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [freetut] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [freetut] SET  DISABLE_BROKER 
GO
ALTER DATABASE [freetut] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [freetut] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [freetut] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [freetut] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [freetut] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [freetut] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [freetut] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [freetut] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [freetut] SET  MULTI_USER 
GO
ALTER DATABASE [freetut] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [freetut] SET DB_CHAINING OFF 
GO
ALTER DATABASE [freetut] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [freetut] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
ALTER DATABASE [freetut] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [freetut] SET QUERY_STORE = OFF
GO
USE [freetut]
GO
/****** Object:  Table [dbo].[admin]    Script Date: 4/9/2021 6:09:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[admin](
	[username] [nvarchar](50) NOT NULL,
	[password] [text] NULL,
 CONSTRAINT [PK_admin] PRIMARY KEY CLUSTERED 
(
	[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[category]    Script Date: 4/9/2021 6:09:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[category](
	[category_id] [int] NOT NULL,
	[category_name] [nvarchar](50) NULL,
	[pre_category_id] [int] NULL,
	[path] [nvarchar](100) NULL,
	[post_id] [int] NULL,
	[status] [bit] NULL,
 CONSTRAINT [PK_category] PRIMARY KEY CLUSTERED 
(
	[category_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[comment]    Script Date: 4/9/2021 6:09:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[comment](
	[comment_id] [int] NOT NULL,
	[username] [nvarchar](50) NULL,
	[email] [varchar](50) NULL,
	[datetime] [datetime] NULL,
	[like_total] [int] NULL,
	[post_id] [int] NULL,
	[comment_content] [ntext] NULL,
 CONSTRAINT [PK_comment] PRIMARY KEY CLUSTERED 
(
	[comment_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[course]    Script Date: 4/9/2021 6:09:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[course](
	[course_id] [int] NOT NULL,
	[course_name] [nvarchar](100) NULL,
	[teacher] [nvarchar](50) NULL,
	[time] [int] NULL,
	[link] [text] NULL,
	[price] [money] NULL,
	[discount] [int] NULL,
	[discount_price] [money] NULL,
	[type] [nvarchar](100) NULL,
	[post_id] [int] NULL,
	[status] [bit] NULL,
 CONSTRAINT [PK_product] PRIMARY KEY CLUSTERED 
(
	[course_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[discount]    Script Date: 4/9/2021 6:09:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[discount](
	[discount_id] [int] NOT NULL,
	[discount_name] [varchar](30) NULL,
	[code] [varchar](50) NULL,
	[percentage] [int] NULL,
	[discount_link_page] [nvarchar](50) NULL,
	[status] [bit] NULL,
 CONSTRAINT [PK_discount] PRIMARY KEY CLUSTERED 
(
	[discount_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[order]    Script Date: 4/9/2021 6:09:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[order](
	[order_id] [int] NOT NULL,
	[customer_name] [nvarchar](50) NULL,
	[phone] [varchar](11) NULL,
	[email] [varchar](100) NULL,
	[facebook] [varchar](100) NULL,
	[datetime] [datetime] NULL,
	[address] [ntext] NULL,
	[total] [nvarchar](50) NULL,
	[payment_id] [int] NULL,
	[status] [varchar](15) NULL,
 CONSTRAINT [PK_order] PRIMARY KEY CLUSTERED 
(
	[order_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[order_detail]    Script Date: 4/9/2021 6:09:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[order_detail](
	[order_detail_id] [int] NOT NULL,
	[order_id] [int] NULL,
	[product_id] [int] NULL,
 CONSTRAINT [PK_order_detail] PRIMARY KEY CLUSTERED 
(
	[order_detail_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[payment]    Script Date: 4/9/2021 6:09:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[payment](
	[payment_id] [int] NOT NULL,
	[payment_name] [nvarchar](50) NULL,
 CONSTRAINT [PK_payment] PRIMARY KEY CLUSTERED 
(
	[payment_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[post]    Script Date: 4/9/2021 6:09:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[post](
	[post_id] [int] NOT NULL,
	[title] [nvarchar](100) NULL,
	[author] [nvarchar](50) NULL,
	[date] [date] NULL,
	[description] [ntext] NULL,
	[image] [varchar](50) NULL,
	[post_content] [ntext] NULL,
	[category_id] [int] NULL,
	[status] [bit] NULL,
	[template_id] [int] NULL,
 CONSTRAINT [PK_post] PRIMARY KEY CLUSTERED 
(
	[post_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[template]    Script Date: 4/9/2021 6:09:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[template](
	[template_id] [int] NOT NULL,
	[template_name] [nvarchar](50) NULL,
	[template_link] [nvarchar](50) NULL,
 CONSTRAINT [PK_template] PRIMARY KEY CLUSTERED 
(
	[template_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[category]  WITH CHECK ADD  CONSTRAINT [FK_category_category] FOREIGN KEY([pre_category_id])
REFERENCES [dbo].[category] ([category_id])
GO
ALTER TABLE [dbo].[category] CHECK CONSTRAINT [FK_category_category]
GO
ALTER TABLE [dbo].[category]  WITH CHECK ADD  CONSTRAINT [FK_category_post] FOREIGN KEY([post_id])
REFERENCES [dbo].[post] ([post_id])
GO
ALTER TABLE [dbo].[category] CHECK CONSTRAINT [FK_category_post]
GO
ALTER TABLE [dbo].[comment]  WITH CHECK ADD  CONSTRAINT [FK_comment_post] FOREIGN KEY([post_id])
REFERENCES [dbo].[post] ([post_id])
GO
ALTER TABLE [dbo].[comment] CHECK CONSTRAINT [FK_comment_post]
GO
ALTER TABLE [dbo].[course]  WITH CHECK ADD  CONSTRAINT [FK_product_post] FOREIGN KEY([post_id])
REFERENCES [dbo].[post] ([post_id])
GO
ALTER TABLE [dbo].[course] CHECK CONSTRAINT [FK_product_post]
GO
ALTER TABLE [dbo].[order]  WITH CHECK ADD  CONSTRAINT [FK_order_payment] FOREIGN KEY([payment_id])
REFERENCES [dbo].[payment] ([payment_id])
GO
ALTER TABLE [dbo].[order] CHECK CONSTRAINT [FK_order_payment]
GO
ALTER TABLE [dbo].[order_detail]  WITH CHECK ADD  CONSTRAINT [FK_order_detail_order] FOREIGN KEY([order_id])
REFERENCES [dbo].[order] ([order_id])
GO
ALTER TABLE [dbo].[order_detail] CHECK CONSTRAINT [FK_order_detail_order]
GO
ALTER TABLE [dbo].[order_detail]  WITH CHECK ADD  CONSTRAINT [FK_order_detail_product] FOREIGN KEY([product_id])
REFERENCES [dbo].[course] ([course_id])
GO
ALTER TABLE [dbo].[order_detail] CHECK CONSTRAINT [FK_order_detail_product]
GO
ALTER TABLE [dbo].[post]  WITH CHECK ADD  CONSTRAINT [FK_post_admin] FOREIGN KEY([author])
REFERENCES [dbo].[admin] ([username])
GO
ALTER TABLE [dbo].[post] CHECK CONSTRAINT [FK_post_admin]
GO
ALTER TABLE [dbo].[post]  WITH CHECK ADD  CONSTRAINT [FK_post_category] FOREIGN KEY([category_id])
REFERENCES [dbo].[category] ([category_id])
GO
ALTER TABLE [dbo].[post] CHECK CONSTRAINT [FK_post_category]
GO
ALTER TABLE [dbo].[post]  WITH CHECK ADD  CONSTRAINT [FK_post_template] FOREIGN KEY([template_id])
REFERENCES [dbo].[template] ([template_id])
GO
ALTER TABLE [dbo].[post] CHECK CONSTRAINT [FK_post_template]
GO
USE [master]
GO
ALTER DATABASE [freetut] SET  READ_WRITE 
GO
