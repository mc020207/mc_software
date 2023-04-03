drop database onlineshopping;
create database onlineshopping;
use onlineshopping;

CREATE TABLE `product`  (
  `productId` int NOT NULL AUTO_INCREMENT COMMENT 'auto increment',
  `shopId` int NOT NULL,
  `productName` varchar(255) NOT NULL,
  PRIMARY KEY (`productId`)
);

CREATE TABLE `shop`  (
  `shopId` int NOT NULL AUTO_INCREMENT COMMENT 'auto increment',
  `userId` int NOT NULL,
  `shopName` varchar(255) NOT NULL,
  `shopIntro` varchar(255) NOT NULL,
  `shopAddr` varchar(255) NOT NULL,
  `shopRegisterFund` float NOT NULL,
  `shopRegisterDate` date NOT NULL,
  `shopIsOpen` int NOT NULL COMMENT 'not inspect yet(not open):0; inspect but not approve:1(not open); inspect and approve(open):2',
  PRIMARY KEY (`shopId`)
);

CREATE TABLE `user`  (
  `userId` int NOT NULL AUTO_INCREMENT COMMENT 'auto increment',
  `userRole` int NOT NULL COMMENT 'normal user:0, shop owner:1, website manager:2',
  `userName` varchar(255) NOT NULL,
  `userPhone` varchar(255) NULL,
  `userIdCard` varchar(255) NULL,
  `userEmail` varchar(255) NULL,
  `userPwd` varchar(255) NOT NULL,
  PRIMARY KEY (`userId`)
);

ALTER TABLE `product` ADD CONSTRAINT `fk_product_shop_1` FOREIGN KEY (`shopId`) REFERENCES `shop` (`shopId`) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE `shop` ADD CONSTRAINT `fk_shop_user_1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE;

