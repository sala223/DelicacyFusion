SET SQL_SAFE_UPDATES=0; 
USE `delicacy_fusion`;
INSERT INTO `tenant` (`CODE`, IS_ENABLED, `DESCRIPTION`, `NAME`, `CREATED_BY`, CREATED_TIME, ADDRESS) VALUES ('TEST', 1, '', '望湘园餐饮有限公司', '1','2013-01-02 09:22:22','上海市龙阳路');
DELETE FROM `store`;
INSERT INTO `store` (`ID`, `TENANT_ID`, `BUSINESS_HOUR_FROM`, `BUSINESS_HOUR_TO`, `CODE`, `CREATED_BY`, `CREATED_TIME`, `IS_ENABLED`, `NAME`, `TELEPHONE1`, ADDRESS) VALUES ('1', 'TEST', '480', '1320', 'S1', '0', '2013-01-02 09:22:22', '1', ' 望湘园龙阳路店', '13345453232','上海市龙阳路');
INSERT INTO `store` (`ID`, `TENANT_ID`, `BUSINESS_HOUR_FROM`, `BUSINESS_HOUR_TO`, `CODE`, `CREATED_BY`, `CREATED_TIME`, `IS_ENABLED`, `NAME`, `TELEPHONE1`, ADDRESS) VALUES ('2', 'TEST', '480', '1320', 'S2', '0', '2013-02-01 09:22:22', '1', ' 望湘园正大广场店', '13345453232', '上海市陆家嘴正大广场');
INSERT INTO `store` (`ID`, `TENANT_ID`, `BUSINESS_HOUR_FROM`, `BUSINESS_HOUR_TO`, `CODE`, `CREATED_BY`, `CREATED_TIME`, `IS_ENABLED`, `NAME`, `TELEPHONE1`, ADDRESS,IMG_ID) VALUES ('3', 'TEST', '480', '1320', 'S3', '0', '2013-03-05 09:22:22', '1', ' 望湘园浦三路店', '13345453232','上海市浦三路','AAAADAAAABgABAAAACRkNGNhM2U4Yi1lMTlhLTQ4NmItYjg4YS0xMTVmNzBlZThmYTgAAAAEdGVzdA');
 
INSERT INTO `room` (ID, CHANGED_TIME, CODE, CREATED_BY, CREATED_TIME, CURRENCY, DESCRIPTION, IS_ENABLED, MINIMUM_COST, NAME, STORE_CODE, TENANT_ID) VALUES (1, null, '大堂', 0, '2013-10-20 14:39:43.828', null, null, 1, 0.0, 'A004', 'S1', 'test');
INSERT INTO DINING_TABLE (BAR_CODE, CAPACITY, CODE, IS_ENABLED, STORE_CODE,TENANT_ID,CREATED_TIME) VALUES ('TV10001', 10, 'S1000001',1, 'S1', 'TEST','2013-01-02 09:22:22');
INSERT INTO DINING_TABLE (BAR_CODE, CAPACITY, CODE, IS_ENABLED, STORE_CODE,TENANT_ID,CREATED_TIME) VALUES ('TV10002', 10, 'S1000002',1, 'S1', 'TEST','2013-01-02 09:22:22');
INSERT INTO DINING_TABLE (BAR_CODE, CAPACITY, CODE, IS_ENABLED, STORE_CODE,TENANT_ID,CREATED_TIME) VALUES ('TV10003', 10, 'S1000003',1, 'S1', 'TEST','2013-01-02 09:22:22');
INSERT INTO DINING_TABLE (BAR_CODE, CAPACITY, CODE, IS_ENABLED, STORE_CODE,TENANT_ID,CREATED_TIME) VALUES ('TV10004', 10, 'S1000004',1, 'S1', 'TEST','2013-01-02 09:22:22');
INSERT INTO DINING_TABLE (BAR_CODE, CAPACITY, CODE, IS_ENABLED, STORE_CODE,TENANT_ID,CREATED_TIME) VALUES ('TV10005', 10, 'S1000005',1, 'S1', 'TEST','2013-01-02 09:22:22');
INSERT INTO DINING_TABLE (BAR_CODE, CAPACITY, CODE, IS_ENABLED, STORE_CODE,TENANT_ID,CREATED_TIME) VALUES ('TV10006', 10, 'S1000006',1, 'S1', 'TEST','2013-01-02 09:22:22');
INSERT INTO DINING_TABLE (BAR_CODE, CAPACITY, CODE, IS_ENABLED, STORE_CODE,TENANT_ID,CREATED_TIME) VALUES ('TV10007', 10, 'S1000007',1, 'S1', 'TEST','2013-01-02 09:22:22');
INSERT INTO DINING_TABLE (BAR_CODE, CAPACITY, CODE, IS_ENABLED, STORE_CODE,TENANT_ID,CREATED_TIME) VALUES ('TV10008', 10, 'S1000008',1, 'S1', 'TEST','2013-01-02 09:22:22');
INSERT INTO DINING_TABLE (BAR_CODE, CAPACITY, CODE, IS_ENABLED, STORE_CODE,TENANT_ID,CREATED_TIME) VALUES ('TV10009', 10, 'S1000009',1, 'S1', 'TEST','2013-01-02 09:22:22');


INSERT INTO `item_template` (`ID`, `TENANT_ID`, `CODE`, `CREATED_BY`, `CREATED_TIME`, `DESCRIPTION`, `IS_ENABLED`, `ITEM_UNIT`, `NAME`, `PRICE`, `PRICE_UNIT`, `TYPE`) VALUES ('1', 'TEST', 'A0001', '0', '2013-09-15 08:09:21', '', '1', 'CUP', '香草味冰淇淋', '23.0', 'RMB', 'FOOD');
INSERT INTO `item_template` (`ID`, `TENANT_ID`, `CODE`, `CREATED_BY`, `CREATED_TIME`, `DESCRIPTION`, `IS_ENABLED`, `ITEM_UNIT`, `NAME`, `PRICE`, `PRICE_UNIT`, `TYPE`) VALUES ('2', 'TEST', 'A0002', '0', '2013-09-15 08:09:21', '', '1', 'CUP', 'Milk', '12.0', 'RMB', 'FOOD');
INSERT INTO `item_template` (`ID`, `TENANT_ID`, `CODE`, `CREATED_BY`, `CREATED_TIME`, `DESCRIPTION`, `IS_ENABLED`, `ITEM_UNIT`, `NAME`, `PRICE`, `PRICE_UNIT`, `TYPE`) VALUES ('3', 'TEST', 'A0003', '0', '2013-09-15 08:09:21', '', '1', 'DISK', '剁椒鱼头', '68.0', 'RMB', 'FOOD');
INSERT INTO `item_template` (`ID`, `TENANT_ID`, `CODE`, `CREATED_BY`, `CREATED_TIME`, `DESCRIPTION`, `IS_ENABLED`, `ITEM_UNIT`, `NAME`, `PRICE`, `PRICE_UNIT`, `TYPE`) VALUES ('4', 'TEST', 'A0004', '0', '2013-09-15 08:09:21', '', '1', 'DISK', '皮蛋豆腐', '33.0', 'RMB', 'FOOD');


INSERT INTO `item_template_category`(`ITEM_TEMPLATE_ID`,`CATEGORY_CODE`) values(1, 'SYS00002');
INSERT INTO `item_template_category`(`ITEM_TEMPLATE_ID`,`CATEGORY_CODE`) values(2, 'SYS00002');
INSERT INTO `item_template_category`(`ITEM_TEMPLATE_ID`,`CATEGORY_CODE`) values(3, 'SYS00005');
INSERT INTO `item_template_category`(`ITEM_TEMPLATE_ID`,`CATEGORY_CODE`) values(4, 'SYS00006');

INSERT INTO `item_template_picture`(`ITEM_TEMPLATE_ID`,`IMG_ID`,`WIDTH`,`HEIGTH`,`FORMAT`) values(1, 'AAAADAAAABgABAAAACRkNGNhM2U4Yi1lMTlhLTQ4NmItYjg4YS0xMTVmNzBlZThmYTgAAAAEdGVzdA',251,201,'gif');
INSERT INTO `item_template_picture`(`ITEM_TEMPLATE_ID`,`IMG_ID`,`WIDTH`,`HEIGTH`,`FORMAT`) values(2, 'AAAADAAAABgABAAAACRkNGNhM2U4Yi1lMTlhLTQ4NmItYjg4YS0xMTVmNzBlZThmYTgAAAAEdGVzdA',251,201,'gif');
INSERT INTO `item_template_picture`(`ITEM_TEMPLATE_ID`,`IMG_ID`,`WIDTH`,`HEIGTH`,`FORMAT`) values(3, 'AAAADAAAABgABAAAACRkNGNhM2U4Yi1lMTlhLTQ4NmItYjg4YS0xMTVmNzBlZThmYTgAAAAEdGVzdA',251,201,'gif');
INSERT INTO `item_template_picture`(`ITEM_TEMPLATE_ID`,`IMG_ID`,`WIDTH`,`HEIGTH`,`FORMAT`) values(4, 'AAAADAAAABgABAAAACRkNGNhM2U4Yi1lMTlhLTQ4NmItYjg4YS0xMTVmNzBlZThmYTgAAAAEdGVzdA',251,201,'gif');


INSERT INTO `item` (`ID`, `TENANT_ID`, `CODE`, `CREATED_BY`, `CREATED_TIME`, `IS_ENABLED`, `PRICE`, `STORE_CODE`, `ITEMTEMPLATE_ID`) VALUES ('1', 'TEST', 'A0001', '0', '2013-09-02:12:23:21', '1', '23', 'S1', '1');
INSERT INTO `item` (`ID`, `TENANT_ID`, `CODE`, `CREATED_BY`, `CREATED_TIME`, `IS_ENABLED`, `PRICE`, `STORE_CODE`, `ITEMTEMPLATE_ID`) VALUES ('2', 'TEST', 'A0002', '0', '2013-09-02:12:23:21', '1', '12', 'S1', '2');
INSERT INTO `item` (`ID`, `TENANT_ID`, `CODE`, `CREATED_BY`, `CREATED_TIME`, `IS_ENABLED`, `PRICE`, `STORE_CODE`, `ITEMTEMPLATE_ID`) VALUES ('3', 'TEST', 'A0003', '0', '2013-09-02:12:23:21', '1', '68', 'S1', '3');
INSERT INTO `item` (`ID`, `TENANT_ID`, `CODE`, `CREATED_BY`, `CREATED_TIME`, `IS_ENABLED`, `PRICE`, `STORE_CODE`, `ITEMTEMPLATE_ID`) VALUES ('4', 'TEST', 'A0004', '0', '2013-09-02:12:23:21', '1', '33', 'S1', '4');

INSERT INTO PROMOTION (TENANT_ID,ID, CHANGED_TIME, CODE, CREATED_BY, CREATED_TIME, DETAILS, IS_ENABLED, NAME, QUALIFIER, STORE_CODE, PROMOTION_TYPE, VALID_FROM, VALID_TO) VALUES ("test",1,null, "NAT_5_DIS", 0, "2013-11-21 13:09:48.561", "Food TESTA0001 with 50% discount in national day.", 1, "National day 50% discount", "itemDiscount", "S1", "ITEM", "2013-11-21 13:09:48.561", "2013-12-01 13:09:48.561");
INSERT INTO RULE_PARAMETER (PROMOTION_ID, NAME, VALUE) VALUES (1, "DISCOUNT", 5);
INSERT INTO PROMOTION_ITEM (PROMOTION_ID, ITEM_CODE) VALUES (1, "A0001");
INSERT INTO PROMOTION_ITEM (PROMOTION_ID, ITEM_CODE) VALUES (1, "A0002"); 
INSERT INTO SERVICE_CARD (TENANT_ID,ID,CREATED_TIME, ORDER_ID, STORE_CODE) VALUES ("test",1,"2013-11-21 13:09:48.568", 1, "S1");
INSERT INTO SERVICE_TABLE (SERVICE_ID, TABLE_CODE) VALUES (1, "S1000001");
INSERT INTO SERVICE_TABLE (SERVICE_ID, TABLE_CODE) VALUES (1, "S1000002");
INSERT INTO ORDERS (TENANT_ID,ORDER_ID, CLOSE_TIME, COMMENT, CREATE_TIME, CURRENCY, DEPOSIT, DINNER_TIME, DISCOUNT, IS_TAKE_OUT, OTHER_FEE, OWNER_ID, PROMOTION_NAME, SERVICE_CARD_ID, SERVICE_FEE, STATUS, STORE_CODE, TOTAL_PAYMENT, TOTAL_PAYMENT_AFTER_DISCOUNT) VALUES ("test",1,null, null, "2013-11-21 13:09:48.866", null, 0.0, "2013-11-21 13:09:48.844", null, false, 0.0, 0, null, 1, 0.0, "OPEN", "S1", 74, 74);
INSERT INTO ORDER_LINE (ORDER_ID, COMMENT, DISCOUNT, ITEM_CODE, ITEM_NAME, ITEM_UNIT, LINE_NUMBER, PRICE, PROMOTION_NAME, PROMOTION_PRICE, QUANTITY, TABLE_CODE, TOTAL_PAYMENT, TOTAL_PAYMENT_AFTER_DISCOUNT) VALUES (1, null, 23, "A0001", "香草味冰淇淋", "CUP", 1, 23, "National day 50% discount", 12.5, 2, null, 46, 23);
INSERT INTO ORDER_LINE (ORDER_ID, COMMENT, DISCOUNT, ITEM_CODE, ITEM_NAME, ITEM_UNIT, LINE_NUMBER, PRICE, PROMOTION_NAME, PROMOTION_PRICE, QUANTITY, TABLE_CODE, TOTAL_PAYMENT, TOTAL_PAYMENT_AFTER_DISCOUNT) VALUES (1, null, 0, "A0003", "剁椒鱼头", "DISK", 2, 68, null, null, 1, null, 68, 68);
INSERT INTO SERVICE_CARD (TENANT_ID,ID,CREATED_TIME, ORDER_ID, STORE_CODE) VALUES ("test",2,"2013-11-21 13:09:48.568", 2, "S1");
INSERT INTO SERVICE_TABLE (SERVICE_ID, TABLE_CODE) VALUES (2, "S1000003");
INSERT INTO SERVICE_TABLE (SERVICE_ID, TABLE_CODE) VALUES (2, "S1000004");
INSERT INTO ORDERS (TENANT_ID,ORDER_ID, CLOSE_TIME, COMMENT, CREATE_TIME, CURRENCY, DEPOSIT, DINNER_TIME, DISCOUNT, IS_TAKE_OUT, OTHER_FEE, OWNER_ID, PROMOTION_NAME, SERVICE_CARD_ID, SERVICE_FEE, STATUS, STORE_CODE, TOTAL_PAYMENT, TOTAL_PAYMENT_AFTER_DISCOUNT) VALUES ("test",2,null, null, "2013-11-21 13:09:48.866", null, 0.0, "2013-11-21 13:09:48.844", null, false, 0.0, 0, null, 2, 0.0, "OPEN", "S1", 74, 74);
INSERT INTO ORDER_LINE (ORDER_ID, COMMENT, DISCOUNT, ITEM_CODE, ITEM_NAME, ITEM_UNIT, LINE_NUMBER, PRICE, PROMOTION_NAME, PROMOTION_PRICE, QUANTITY, TABLE_CODE, TOTAL_PAYMENT, TOTAL_PAYMENT_AFTER_DISCOUNT) VALUES (2, null, 23, "A0001", "香草味冰淇淋", "CUP", 1, 23, "National day 50% discount", 12.5, 2, null, 46, 23);
INSERT INTO ORDER_LINE (ORDER_ID, COMMENT, DISCOUNT, ITEM_CODE, ITEM_NAME, ITEM_UNIT, LINE_NUMBER, PRICE, PROMOTION_NAME, PROMOTION_PRICE, QUANTITY, TABLE_CODE, TOTAL_PAYMENT, TOTAL_PAYMENT_AFTER_DISCOUNT) VALUES (2, null, 0, "A0003",  "剁椒鱼头", "DISK", 2, 68, null, null, 1, null, 68, 68);

INSERT INTO `delicacy_fusion`.`global_configuration` (`ID`, `TENANT_ID`, `DOMAIN`, `C_KEY`,`C_VALUE`) VALUES ('1', 'test', 'ANDROID_UI', 'ANDROID_NAVIGATION_TABS','{"tabs":[{"categoryCode":"SYS00011","index":0},{"categoryCode":"SYS00012","index":1},{"categoryCode":"SYS00013","index":2},{"categoryCode":"SYS00014","index":3},{"categoryCode":"SYS00015","index":4},{"categoryCode":"SYS00016","index":5},{"categoryCode":"SYS00017","index":6}]}'); 