CREATE TABLE IF NOT EXISTS `member` (
  `account` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `confirm_password` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `phone` varchar(45) NOT NULL,
  `address` varchar(60) NOT NULL,
  PRIMARY KEY (`email`)
);

CREATE TABLE  IF NOT EXISTS `product` (
  `hs_code` varchar(255) NOT NULL,
  `seller_account` varchar(45) NOT NULL,
  `type` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `place` varchar(45) NOT NULL,
--  `number` int DEFAULT '0',
  `date` varchar(45) NOT NULL,
  `price` int DEFAULT '0',
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`hs_code`)
);

CREATE TABLE IF NOT EXISTS `menu` (
  `name` varchar(45) NOT NULL,
  `dish1` varchar(45) NOT NULL,
  `dish2` varchar(45) NOT NULL,
  `dish3` varchar(45) NOT NULL,
  PRIMARY KEY (`name`)
);

CREATE TABLE IF NOT EXISTS `orderform` (
  `order_id` varchar(45) NOT NULL,
  `seller_account` varchar(45) NOT NULL,
  `buyer_account` varchar(45) NOT NULL,
  `buyer_phone` varchar(45),
  `sent_address` varchar(45),
  `content` varchar(255) NOT NULL,
  `order_condition` varchar(45) NOT NULL,
  PRIMARY KEY (`order_id`)
);


CREATE TABLE IF NOT EXISTS `ordedrcontent` (
  `num_id` varchar(45) NOT NULL,
  `item_name` varchar(45) NOT NULL,
  `item_number` int DEFAULT '0',
  `item_price` int DEFAULT '0',
  `total_price` int DEFAULT '0',
  `seller_account` varchar(45),
  PRIMARY KEY (`num_id`)
);


CREATE TABLE IF NOT EXISTS  `shopping` (
  `buyer_account` varchar(45) NOT NULL,
  `buyer_content` varchar(1000) NOT NULL,
  PRIMARY KEY (`buyer_account`)
);

CREATE TABLE IF NOT EXISTS `shopppingcontent` (
  `buyer_shopping_number` varchar(255) NOT NULL,
  `item_id` varchar(45) NOT NULL,
  `item_name` varchar(45) DEFAULT NULL,
  `sell_account` varchar(45) DEFAULT NULL,
  `per_price` int DEFAULT '0',
  `item_num` int DEFAULT '0',
  `discription` varchar(255) DEFAULT NULL,
  `stock` int DEFAULT '0',
  PRIMARY KEY (`buyer_shopping_number`)

);

CREATE TABLE IF NOT EXISTS `dishlist` (
  `name` varchar(45) NOT NULL,
  `needed` varchar(300) NOT NULL,
  `cooking` varchar(1000) NOT NULL,
  PRIMARY KEY (`name`)
);





