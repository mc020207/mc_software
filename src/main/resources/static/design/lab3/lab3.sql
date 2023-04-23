drop database onlineshopping;
create database onlineshopping;
use onlineshopping
CREATE TABLE `account`  (
  `accountId` int NOT NULL AUTO_INCREMENT,
  `userId` int NOT NULL,
  `accountType` int NOT NULL,
  `accountMoney` float NOT NULL,
  `accountState` int NOT NULL,
  PRIMARY KEY (`accountId`)
);

CREATE TABLE `flow`  (
  `flowId` int NOT NULL AUTO_INCREMENT,
  `accountIdFrom` int NULL,
  `accountIdTo` int NULL,
  `flowMoney` float NULL,
  `flowDate` datetime NULL,
  PRIMARY KEY (`flowId`)
);

CREATE TABLE `order`  (
  `orderId` int NOT NULL AUTO_INCREMENT,
  `userId` int NOT NULL,
  `productId` int NOT NULL,
  `orderState` int NOT NULL,
  `orderDate` datetime NULL,
  `orderMoney` float NULL,
  PRIMARY KEY (`orderId`)
);

CREATE TABLE `product`  (
  `productId` int NOT NULL AUTO_INCREMENT COMMENT 'auto increment',
  `shopId` int NOT NULL,
  `productName` varchar(255) NOT NULL,
  `productState` int NOT NULL,
  `productIntro` varchar(255) NULL,
  `productPrice` float NULL,
  PRIMARY KEY (`productId`)
);

CREATE TABLE `productImg`  (
  `productImgId` int NOT NULL AUTO_INCREMENT,
  `productId` int NULL,
  `productImgAddr` varchar(255) NULL,
  PRIMARY KEY (`productImgId`)
);

CREATE TABLE `productRecord`  (
  `productRecordId` int NOT NULL AUTO_INCREMENT,
  `productId` int NULL,
  `productRecordDate` datetime NULL,
  `productRecordComment` varchar(255) NULL,
  `productRecordState` int NULL,
  PRIMARY KEY (`productRecordId`)
);

CREATE TABLE `shop`  (
  `shopId` int NOT NULL AUTO_INCREMENT COMMENT 'auto increment',
  `userId` int NOT NULL,
  `shopName` varchar(255) NOT NULL,
  `shopIntro` varchar(255) NOT NULL,
  `shopAddr` varchar(255) NOT NULL,
  `shopRegisterFund` float NOT NULL,
  `shopRegisterDate` date NOT NULL,
  `shopState` int NOT NULL,
  PRIMARY KEY (`shopId`)
);

CREATE TABLE `shopRecord`  (
  `shopRecordId` int NOT NULL AUTO_INCREMENT,
  `shopId` int NULL,
  `shopRecordDate` datetime NULL,
  `shopRecordComment` varchar(255) NULL,
  `shopRecordState` int NULL,
  PRIMARY KEY (`shopRecordId`)
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

ALTER TABLE `account` ADD CONSTRAINT `fk_account_user_1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE `flow` ADD CONSTRAINT `fk_flow_account_1` FOREIGN KEY (`accountIdFrom`) REFERENCES `account` (`accountId`) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE `flow` ADD CONSTRAINT `fk_flow_account_2` FOREIGN KEY (`accountIdTo`) REFERENCES `account` (`accountId`) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE `order` ADD CONSTRAINT `fk_order_user_1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE `order` ADD CONSTRAINT `fk_order_product_1` FOREIGN KEY (`productId`) REFERENCES `product` (`productId`) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE `product` ADD CONSTRAINT `fk_product_shop_1` FOREIGN KEY (`shopId`) REFERENCES `shop` (`shopId`) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE `productImg` ADD CONSTRAINT `fk_productImg_product_1` FOREIGN KEY (`productId`) REFERENCES `product` (`productId`) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE `productRecord` ADD CONSTRAINT `fk_productRecord_product_1` FOREIGN KEY (`productId`) REFERENCES `product` (`productId`) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE `shop` ADD CONSTRAINT `fk_shop_user_1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE `shopRecord` ADD CONSTRAINT `fk_shopRecord_shop_1` FOREIGN KEY (`shopId`) REFERENCES `shop` (`shopId`) ON DELETE CASCADE ON UPDATE CASCADE;

