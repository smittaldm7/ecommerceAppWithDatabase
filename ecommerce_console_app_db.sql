-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.1.38-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             10.2.0.5599
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for ecommerce_console_app_db
CREATE DATABASE IF NOT EXISTS `ecommerce_console_app_db` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `ecommerce_console_app_db`;

-- Dumping structure for procedure ecommerce_console_app_db.add_cart_item
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `add_cart_item`(
	IN `CustomerId` INT,
	IN `ProductName` VARCHAR(50),
	IN `SupplierName` VARCHAR(50),
	IN `Quantity` INT


,
	OUT `InsufficientStock` INT








)
BEGIN

declare Id INT;
declare Stock INT;
declare Discount FLOAT;
declare Price FLOAT;

declare test float;


declare ItemPriceAfterDiscount FLOAT;
declare TotalPriceAfterDiscount FLOAT;

set test = 40.1;

set Id = (select sp.id from supplier_product sp 
			inner join product p on p.id = sp.product_id
			inner join supplier s on s.id = sp.supplier_id
		where p.name = ProductName
		and s.name = SupplierName);
		
SET Stock = (SELECT sp.stock from supplier_product sp WHERE sp.id = Id);

SET Discount = (SELECT sp.discount_percent from supplier_product sp WHERE sp.id = Id);

SET Price = (SELECT sp.price from supplier_product sp WHERE sp.id = Id);

select Price;

SET ItemPriceAfterDiscount = Price*((100.0-Discount)/100.0);


SET TotalPriceAfterDiscount = ItemPriceAfterDiscount*Quantity;

if Quantity>Stock then
	Set InsufficientStock = 1;
	
ELSE 
	set InsufficientStock = 0;
	insert into cart_item (supplier_product_id, quantity, customer_id, item_price_after_discount, total_price_after_discount) 
			values (Id, Quantity, CustomerId, ItemPriceAfterDiscount, TotalPriceAfterDiscount);
end if;
		
		
END//
DELIMITER ;

-- Dumping structure for procedure ecommerce_console_app_db.add_order
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `add_order`(
	IN `CustomerId` INT,
	OUT `CartEmpty` INT,
	OUT `InsufficientStock` INT























)
BEGIN

declare NumberOfCartItems INT;



declare NoOfCartItemsWithSufficientStock INT;
declare SumTotal FLOAT;

declare OrderId INT;

set NumberOfCartItems = (select count(*) from cart_item where customer_id = CustomerId);

if NumberOfCartItems <= 0 then
	set CartEmpty = 1;
else
	set CartEmpty = 0;
	set NoOfCartItemsWithSufficientStock = (select count(*) from cart_item ci 
						inner join supplier_product sp on sp.id = ci.supplier_product_id
						where sp.stock >= ci.quantity);
	if NoOfCartItemsWithSufficientStock < NumberOfCartItems then
		set InsufficientStock = 1;
	else
		set InsufficientStock = 0;
		
		/*Get total bill amount for all items in cart*/
		set SumTotal = (select sum(total_price_after_discount) from cart_item where customer_id = CustomerId);
		
		/*create order entry*/
		insert into order_table (customer_id, date, total_bill_amount) values (CustomerId, NOW(), SumTotal);
		
		/*get order id of row just created*/
		set OrderId = (select max(id) from order_table where customer_id=CustomerId);
		
		
	
		/*creating order itme entries*/
		INSERT into order_item (order_id, supplier_product_id, quantity, item_price_after_discount, total_price_after_discount)
			SELECT OrderId, supplier_product_id, quantity, item_price_after_discount, total_price_after_discount 
			FROM cart_item;

		/*removing stock from inventory*/
		update order_item oi 
			INNER JOIN supplier_product sp ON oi.supplier_product_id = sp.id
			SET sp.stock = sp.stock-oi.quantity
			WHERE oi.order_id =OrderId;
		

		DELETE from cart_item where customer_id = CustomerId;
	end if;
end if;






END//
DELIMITER ;

-- Dumping structure for procedure ecommerce_console_app_db.add_supplier_product
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `add_supplier_product`(
	IN `SupplierId` INT,
	IN `ProductId` INT,
	IN `Price` FLOAT,
	IN `DiscountPercent` FLOAT,
	IN `Stock` INT




)
BEGIN
insert into supplier_product (supplier_id, product_id, price, discount_percent, stock)
	values (SupplierId, ProductId, Price, DiscountPercent, Stock);
END//
DELIMITER ;

-- Dumping structure for table ecommerce_console_app_db.cart_item
CREATE TABLE IF NOT EXISTS `cart_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `supplier_product_id` int(11) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `customer_id` int(11) DEFAULT NULL,
  `item_price_after_discount` float DEFAULT NULL,
  `total_price_after_discount` float DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_cart_item_supplier_product` (`supplier_product_id`),
  KEY `FK_cart_item_customer` (`customer_id`),
  CONSTRAINT `FK_cart_item_customer` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
  CONSTRAINT `FK_cart_item_supplier_product` FOREIGN KEY (`supplier_product_id`) REFERENCES `supplier_product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=latin1;

-- Dumping data for table ecommerce_console_app_db.cart_item: ~0 rows (approximately)
/*!40000 ALTER TABLE `cart_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `cart_item` ENABLE KEYS */;

-- Dumping structure for table ecommerce_console_app_db.customer
CREATE TABLE IF NOT EXISTS `customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL DEFAULT '0',
  `address` varchar(150) NOT NULL DEFAULT '0',
  `phone_number` bigint(20) NOT NULL DEFAULT '0',
  `email` varchar(50) NOT NULL DEFAULT '0',
  `password` varchar(50) NOT NULL DEFAULT '0',
  `amount` float NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Dumping data for table ecommerce_console_app_db.customer: ~2 rows (approximately)
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` (`id`, `name`, `address`, `phone_number`, `email`, `password`, `amount`) VALUES
	(1, 'sidharth', '#1 NGV Koramangala', 22067130, 'smittaldm7@gmail.com', 'sid123', 0),
	(2, 'Ramona', '#5 MG Road', 22023430, 'email.vineetmittal@gmail.com', 'vin123', 0);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;

-- Dumping structure for procedure ecommerce_console_app_db.get_order_items
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_order_items`(
	IN `customerId` INT,
	IN `orderId` INT

)
BEGIN
select * from order_items where order_id = orderId;
END//
DELIMITER ;

-- Dumping structure for procedure ecommerce_console_app_db.get_products_from_search_string
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_products_from_search_string`(
	IN `SearchString` VARCHAR(50)
)
BEGIN
select * from product p inner join 
					product_keyword_relationship pk on p.id = pk.product_id inner join
					keyword k on pk.keyword_id = k.id
where k.text = SearchString;
END//
DELIMITER ;

-- Dumping structure for procedure ecommerce_console_app_db.get_product_suppliers
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_product_suppliers`(
	IN `ProductName` VARCHAR(50)



)
BEGIN
select 	sp.supplier_id as supplier_id, 
			sp.product_id as product_id, 
			sp.price as price, 
			sp.discount_percent as discount_percent, 
			sp.stock as stock 
		from supplier_product sp inner join
						product p on p.id = sp.product_id
					where p.name = ProductName;
						
END//
DELIMITER ;

-- Dumping structure for table ecommerce_console_app_db.keyword
CREATE TABLE IF NOT EXISTS `keyword` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;

-- Dumping data for table ecommerce_console_app_db.keyword: ~15 rows (approximately)
/*!40000 ALTER TABLE `keyword` DISABLE KEYS */;
INSERT INTO `keyword` (`id`, `text`) VALUES
	(1, 'shoes'),
	(2, 'puma'),
	(3, 'running'),
	(4, 'laptop'),
	(5, 'dell'),
	(6, 'computer'),
	(7, 'inspiron'),
	(8, 'pigeon'),
	(9, 'stove'),
	(10, 'cooking'),
	(11, 'gas'),
	(12, 'LG'),
	(13, '190'),
	(14, 'fridge'),
	(15, 'refrigerator');
/*!40000 ALTER TABLE `keyword` ENABLE KEYS */;

-- Dumping structure for table ecommerce_console_app_db.order_item
CREATE TABLE IF NOT EXISTS `order_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) DEFAULT NULL,
  `supplier_product_id` int(11) DEFAULT NULL,
  `quantity` float DEFAULT NULL,
  `item_price_after_discount` float DEFAULT NULL,
  `total_price_after_discount` float DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_order_item_supplier_product` (`supplier_product_id`),
  CONSTRAINT `FK_order_item_supplier_product` FOREIGN KEY (`supplier_product_id`) REFERENCES `supplier_product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;

-- Dumping data for table ecommerce_console_app_db.order_item: ~8 rows (approximately)
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
INSERT INTO `order_item` (`id`, `order_id`, `supplier_product_id`, `quantity`, `item_price_after_discount`, `total_price_after_discount`) VALUES
	(1, 5, 18, 2, 900, 1800),
	(2, 5, 20, 1, 20000, 20000),
	(4, 8, 18, 2, 900, 1800),
	(5, 8, 20, 1, 20000, 20000),
	(7, 9, 18, 2, 900, 1800),
	(8, 9, 20, 1, 20000, 20000),
	(10, 10, 18, 1, 900, 900),
	(11, 10, 19, 1, 2800, 2800),
	(13, 11, 19, 3, 2800, 8400),
	(14, 11, 18, 5, 900, 4500);
/*!40000 ALTER TABLE `order_item` ENABLE KEYS */;

-- Dumping structure for table ecommerce_console_app_db.order_table
CREATE TABLE IF NOT EXISTS `order_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `total_bill_amount` float NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_order_customer` (`customer_id`),
  CONSTRAINT `FK_order_customer` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

-- Dumping data for table ecommerce_console_app_db.order_table: ~9 rows (approximately)
/*!40000 ALTER TABLE `order_table` DISABLE KEYS */;
INSERT INTO `order_table` (`id`, `customer_id`, `date`, `total_bill_amount`) VALUES
	(2, 1, '2019-08-31 21:50:41', 100),
	(3, 1, '2019-08-31 21:56:01', 2100),
	(4, 1, '2019-08-31 22:18:46', 2100),
	(5, 1, '2019-08-31 22:20:41', 2100),
	(6, 1, '2019-08-31 22:29:15', 21800),
	(7, 1, '2019-09-01 11:55:10', 21800),
	(8, 1, '2019-09-01 11:56:06', 21800),
	(9, 1, '2019-09-01 11:56:59', 21800),
	(10, 1, '2019-09-01 12:53:06', 3700),
	(11, 1, '2019-09-01 13:42:11', 12900);
/*!40000 ALTER TABLE `order_table` ENABLE KEYS */;

-- Dumping structure for table ecommerce_console_app_db.product
CREATE TABLE IF NOT EXISTS `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- Dumping data for table ecommerce_console_app_db.product: ~4 rows (approximately)
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` (`id`, `name`) VALUES
	(1, 'Puma Running Shoes'),
	(2, 'Dell Inspiron Laptop'),
	(3, 'Pigeon Gas Stove'),
	(4, 'LG 190L Refrigerator');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;

-- Dumping structure for table ecommerce_console_app_db.product_keyword_relationship
CREATE TABLE IF NOT EXISTS `product_keyword_relationship` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) DEFAULT NULL,
  `keyword_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;

-- Dumping data for table ecommerce_console_app_db.product_keyword_relationship: ~15 rows (approximately)
/*!40000 ALTER TABLE `product_keyword_relationship` DISABLE KEYS */;
INSERT INTO `product_keyword_relationship` (`id`, `product_id`, `keyword_id`) VALUES
	(1, 1, 1),
	(2, 1, 2),
	(3, 1, 3),
	(4, 2, 4),
	(5, 2, 5),
	(6, 2, 6),
	(7, 2, 7),
	(8, 3, 8),
	(9, 3, 9),
	(10, 3, 10),
	(11, 3, 11),
	(12, 4, 12),
	(13, 5, 13),
	(14, 5, 14),
	(15, 5, 15);
/*!40000 ALTER TABLE `product_keyword_relationship` ENABLE KEYS */;

-- Dumping structure for table ecommerce_console_app_db.supplier
CREATE TABLE IF NOT EXISTS `supplier` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL DEFAULT '0',
  `warehouse_address` varchar(150) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- Dumping data for table ecommerce_console_app_db.supplier: ~3 rows (approximately)
/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
INSERT INTO `supplier` (`id`, `name`, `warehouse_address`) VALUES
	(1, 'Guptas', '#101, MG Road, Bangalore'),
	(2, 'Mehras', '#12, 5th Block Kormangala, Bangalore'),
	(3, 'Reddys', '#322, JC Road, Bangalore'),
	(4, 'Chakma', '#1 Nagaland');
/*!40000 ALTER TABLE `supplier` ENABLE KEYS */;

-- Dumping structure for table ecommerce_console_app_db.supplier_product
CREATE TABLE IF NOT EXISTS `supplier_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `supplier_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `discount_percent` float DEFAULT NULL,
  `stock` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK__supplier` (`supplier_id`),
  KEY `FK__product` (`product_id`),
  CONSTRAINT `FK__product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FK__supplier` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1;

-- Dumping data for table ecommerce_console_app_db.supplier_product: ~5 rows (approximately)
/*!40000 ALTER TABLE `supplier_product` DISABLE KEYS */;
INSERT INTO `supplier_product` (`id`, `supplier_id`, `product_id`, `price`, `discount_percent`, `stock`) VALUES
	(18, 1, 1, 1000, 10, 7),
	(19, 1, 2, 2800, 0, 9),
	(20, 2, 2, 20000, 0, 4),
	(21, 2, 3, 18000, 0, 10),
	(22, 3, 2, 2000, 0, 5);
/*!40000 ALTER TABLE `supplier_product` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
