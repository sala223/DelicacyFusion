INSERT INTO `delicacy_fusion`.`store` (`ID`, `TENANT_ID`, `BUSINESS_HOUR_FROM`, `BUSINESS_HOUR_TO`, `CODE`, `CREATED_BY`, `CREATED_TIME`, `IS_ENABLED`, `NAME`, `TELEPHONE1`) VALUES ('1', 'TEST', '480', '1320', 'S1', '0', '2013-01-02 09:22:22', '1', ' 望湘园龙阳路店', '13345453232');

INSERT INTO `delicacy_fusion`.`item_template` (`ID`, `TENANT_ID`, `CODE`, `CREATED_BY`, `CREATED_TIME`, `DESCRIPTION`, `IS_ENABLED`, `ITEM_UNIT`, `NAME`, `PRICE`, `PRICE_UNIT`, `TYPE`) VALUES ('1', 'TEST', 'A0001', '0', '2013-09-15 08:09:21', '', '1', 'CUP', 'IceCream', '23.0', 'RMB', 'FOOD');
INSERT INTO `delicacy_fusion`.`item_template` (`ID`, `TENANT_ID`, `CODE`, `CREATED_BY`, `CREATED_TIME`, `DESCRIPTION`, `IS_ENABLED`, `ITEM_UNIT`, `NAME`, `PRICE`, `PRICE_UNIT`, `TYPE`) VALUES ('2', 'TEST', 'A0002', '0', '2013-09-15 08:09:21', '', '1', 'CUP', 'Milk', '12.0', 'RMB', 'FOOD');
INSERT INTO `delicacy_fusion`.`item_template` (`ID`, `TENANT_ID`, `CODE`, `CREATED_BY`, `CREATED_TIME`, `DESCRIPTION`, `IS_ENABLED`, `ITEM_UNIT`, `NAME`, `PRICE`, `PRICE_UNIT`, `TYPE`) VALUES ('3', 'TEST', 'A0003', '0', '2013-09-15 08:09:21', '', '1', 'DISK', '剁椒鱼头', '68.0', 'RMB', 'FOOD');
INSERT INTO `delicacy_fusion`.`item_template` (`ID`, `TENANT_ID`, `CODE`, `CREATED_BY`, `CREATED_TIME`, `DESCRIPTION`, `IS_ENABLED`, `ITEM_UNIT`, `NAME`, `PRICE`, `PRICE_UNIT`, `TYPE`) VALUES ('4', 'TEST', 'A0004', '0', '2013-09-15 08:09:21', '', '1', 'DISK', '皮蛋豆腐', '33.0', 'RMB', 'FOOD');


INSERT INTO `delicacy_fusion`.`item_template_category`(`ITEM_TEMPLATE_ID`,`CATEGORY_CODE`) values(1, 'SYS00002');
INSERT INTO `delicacy_fusion`.`item_template_category`(`ITEM_TEMPLATE_ID`,`CATEGORY_CODE`) values(2, 'SYS00002');
INSERT INTO `delicacy_fusion`.`item_template_category`(`ITEM_TEMPLATE_ID`,`CATEGORY_CODE`) values(3, 'SYS00005');
INSERT INTO `delicacy_fusion`.`item_template_category`(`ITEM_TEMPLATE_ID`,`CATEGORY_CODE`) values(4, 'SYS00006');


INSERT INTO `delicacy_fusion`.`item` (`ID`, `TENANT_ID`, `CODE`, `CREATED_BY`, `CREATED_TIME`, `IS_ENABLED`, `PRICE`, `STORE_CODE`, `ITEMTEMPLATE_ID`) VALUES ('1', 'TEST', 'A0001', '0', '2013-09-02:12:23:21', '1', '23', 'S1', '1');
INSERT INTO `delicacy_fusion`.`item` (`ID`, `TENANT_ID`, `CODE`, `CREATED_BY`, `CREATED_TIME`, `IS_ENABLED`, `PRICE`, `STORE_CODE`, `ITEMTEMPLATE_ID`) VALUES ('2', 'TEST', 'A0002', '0', '2013-09-02:12:23:21', '1', '12', 'S1', '2');
INSERT INTO `delicacy_fusion`.`item` (`ID`, `TENANT_ID`, `CODE`, `CREATED_BY`, `CREATED_TIME`, `IS_ENABLED`, `PRICE`, `STORE_CODE`, `ITEMTEMPLATE_ID`) VALUES ('3', 'TEST', 'A0003', '0', '2013-09-02:12:23:21', '1', '68', 'S1', '3');
INSERT INTO `delicacy_fusion`.`item` (`ID`, `TENANT_ID`, `CODE`, `CREATED_BY`, `CREATED_TIME`, `IS_ENABLED`, `PRICE`, `STORE_CODE`, `ITEMTEMPLATE_ID`) VALUES ('4', 'TEST', 'A0004', '0', '2013-09-02:12:23:21', '1', '33', 'S1', '4');
