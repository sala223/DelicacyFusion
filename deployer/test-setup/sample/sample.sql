SET SQL_SAFE_UPDATES=0; 
DELETE FROM `delicacy_fusion`.`store_address`;
DELETE FROM `delicacy_fusion`.`store`;
INSERT INTO `delicacy_fusion`.`store` (`ID`, `TENANT_ID`, `BUSINESS_HOUR_FROM`, `BUSINESS_HOUR_TO`, `CODE`, `CREATED_BY`, `CREATED_TIME`, `IS_ENABLED`, `NAME`, `TELEPHONE1`) VALUES ('1', 'TEST', '480', '1320', 'S1', '0', '2013-01-02 09:22:22', '1', ' 望湘园龙阳路店', '13345453232');
INSERT INTO `delicacy_fusion`.`store` (`ID`, `TENANT_ID`, `BUSINESS_HOUR_FROM`, `BUSINESS_HOUR_TO`, `CODE`, `CREATED_BY`, `CREATED_TIME`, `IS_ENABLED`, `NAME`, `TELEPHONE1`) VALUES ('2', 'TEST', '480', '1320', 'S2', '0', '2013-02-01 09:22:22', '1', ' 望湘园正大广场店', '13345453232');
INSERT INTO `delicacy_fusion`.`store` (`ID`, `TENANT_ID`, `BUSINESS_HOUR_FROM`, `BUSINESS_HOUR_TO`, `CODE`, `CREATED_BY`, `CREATED_TIME`, `IS_ENABLED`, `NAME`, `TELEPHONE1`) VALUES ('3', 'TEST', '480', '1320', 'S3', '0', '2013-03-05 09:22:22', '1', ' 望湘园浦三路店', '13345453232');

INSERT INTO `delicacy_fusion`.`room` (ID, CHANGED_TIME, CODE, CREATED_BY, CREATED_TIME, CURRENCY, DESCRIPTION, IS_ENABLED, MINIMUM_COST, NAME, STORE_CODE, TABLE_CAPACITY, TENANT_ID) VALUES (1, null, '大堂', 0, '2013-10-20 14:39:43.828', null, null, 1, 0.0, 'A004', 'S1', 2147483647, 'test');
INSERT INTO `delicacy_fusion`.DINING_TABLE (BAR_CODE, CAPACITY, NUMBER, ROOM_ID) VALUES ('TV10001', 10, 'S1000001', 1);
INSERT INTO `delicacy_fusion`.DINING_TABLE (BAR_CODE, CAPACITY, NUMBER, ROOM_ID) VALUES ('TV10002', 10, 'S1000002', 1);
INSERT INTO `delicacy_fusion`.DINING_TABLE (BAR_CODE, CAPACITY, NUMBER, ROOM_ID) VALUES ('TV10003', 10, 'S1000003', 1);

INSERT INTO `delicacy_fusion`.`item_template` (`ID`, `TENANT_ID`, `CODE`, `CREATED_BY`, `CREATED_TIME`, `DESCRIPTION`, `IS_ENABLED`, `ITEM_UNIT`, `NAME`, `PRICE`, `PRICE_UNIT`, `TYPE`) VALUES ('1', 'TEST', 'A0001', '0', '2013-09-15 08:09:21', '', '1', 'CUP', 'IceCream', '23.0', 'RMB', 'FOOD');
INSERT INTO `delicacy_fusion`.`item_template` (`ID`, `TENANT_ID`, `CODE`, `CREATED_BY`, `CREATED_TIME`, `DESCRIPTION`, `IS_ENABLED`, `ITEM_UNIT`, `NAME`, `PRICE`, `PRICE_UNIT`, `TYPE`) VALUES ('2', 'TEST', 'A0002', '0', '2013-09-15 08:09:21', '', '1', 'CUP', 'Milk', '12.0', 'RMB', 'FOOD');
INSERT INTO `delicacy_fusion`.`item_template` (`ID`, `TENANT_ID`, `CODE`, `CREATED_BY`, `CREATED_TIME`, `DESCRIPTION`, `IS_ENABLED`, `ITEM_UNIT`, `NAME`, `PRICE`, `PRICE_UNIT`, `TYPE`) VALUES ('3', 'TEST', 'A0003', '0', '2013-09-15 08:09:21', '', '1', 'DISK', '剁椒鱼头', '68.0', 'RMB', 'FOOD');
INSERT INTO `delicacy_fusion`.`item_template` (`ID`, `TENANT_ID`, `CODE`, `CREATED_BY`, `CREATED_TIME`, `DESCRIPTION`, `IS_ENABLED`, `ITEM_UNIT`, `NAME`, `PRICE`, `PRICE_UNIT`, `TYPE`) VALUES ('4', 'TEST', 'A0004', '0', '2013-09-15 08:09:21', '', '1', 'DISK', '皮蛋豆腐', '33.0', 'RMB', 'FOOD');


INSERT INTO `delicacy_fusion`.`item_template_category`(`ITEM_TEMPLATE_ID`,`CATEGORY_CODE`) values(1, 'SYS00002');
INSERT INTO `delicacy_fusion`.`item_template_category`(`ITEM_TEMPLATE_ID`,`CATEGORY_CODE`) values(2, 'SYS00002');
INSERT INTO `delicacy_fusion`.`item_template_category`(`ITEM_TEMPLATE_ID`,`CATEGORY_CODE`) values(3, 'SYS00005');
INSERT INTO `delicacy_fusion`.`item_template_category`(`ITEM_TEMPLATE_ID`,`CATEGORY_CODE`) values(4, 'SYS00006');

INSERT INTO `delicacy_fusion`.`item_template_picture`(`ITEM_TEMPLATE_ID`,`IMG_ID`,`WIDTH`,`HEIGTH`,`FORMAT`) values(1, 'AAAADAAAABgABAAAACRkNGNhM2U4Yi1lMTlhLTQ4NmItYjg4YS0xMTVmNzBlZThmYTgAAAAEdGVzdA',251,201,'jpg');
INSERT INTO `delicacy_fusion`.`item_template_picture`(`ITEM_TEMPLATE_ID`,`IMG_ID`,`WIDTH`,`HEIGTH`,`FORMAT`) values(2, 'AAAADAAAABgABAAAACRkNGNhM2U4Yi1lMTlhLTQ4NmItYjg4YS0xMTVmNzBlZThmYTgAAAAEdGVzdA',251,201,'jpg');
INSERT INTO `delicacy_fusion`.`item_template_picture`(`ITEM_TEMPLATE_ID`,`IMG_ID`,`WIDTH`,`HEIGTH`,`FORMAT`) values(3, 'AAAADAAAABgABAAAACRkNGNhM2U4Yi1lMTlhLTQ4NmItYjg4YS0xMTVmNzBlZThmYTgAAAAEdGVzdA',251,201,'jpg');
INSERT INTO `delicacy_fusion`.`item_template_picture`(`ITEM_TEMPLATE_ID`,`IMG_ID`,`WIDTH`,`HEIGTH`,`FORMAT`) values(4, 'AAAADAAAABgABAAAACRkNGNhM2U4Yi1lMTlhLTQ4NmItYjg4YS0xMTVmNzBlZThmYTgAAAAEdGVzdA',251,201,'jpg');


INSERT INTO `delicacy_fusion`.`item` (`ID`, `TENANT_ID`, `CODE`, `CREATED_BY`, `CREATED_TIME`, `IS_ENABLED`, `PRICE`, `STORE_CODE`, `ITEMTEMPLATE_ID`) VALUES ('1', 'TEST', 'A0001', '0', '2013-09-02:12:23:21', '1', '23', 'S1', '1');
INSERT INTO `delicacy_fusion`.`item` (`ID`, `TENANT_ID`, `CODE`, `CREATED_BY`, `CREATED_TIME`, `IS_ENABLED`, `PRICE`, `STORE_CODE`, `ITEMTEMPLATE_ID`) VALUES ('2', 'TEST', 'A0002', '0', '2013-09-02:12:23:21', '1', '12', 'S1', '2');
INSERT INTO `delicacy_fusion`.`item` (`ID`, `TENANT_ID`, `CODE`, `CREATED_BY`, `CREATED_TIME`, `IS_ENABLED`, `PRICE`, `STORE_CODE`, `ITEMTEMPLATE_ID`) VALUES ('3', 'TEST', 'A0003', '0', '2013-09-02:12:23:21', '1', '68', 'S1', '3');
INSERT INTO `delicacy_fusion`.`item` (`ID`, `TENANT_ID`, `CODE`, `CREATED_BY`, `CREATED_TIME`, `IS_ENABLED`, `PRICE`, `STORE_CODE`, `ITEMTEMPLATE_ID`) VALUES ('4', 'TEST', 'A0004', '0', '2013-09-02:12:23:21', '1', '33', 'S1', '4');
