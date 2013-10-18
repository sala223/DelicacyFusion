CREATE TABLE DELIVERY (DELIVERYID BIGINT AUTO_INCREMENT NOT NULL, TENANT_ID VARCHAR(12), CLOSE_TIME TIME, CREATE_TIME DATETIME NOT NULL, ORDER_ID BIGINT NOT NULL, OWNER_ID BIGINT NOT NULL, STATUS VARCHAR(32), STORE_CODE VARCHAR(255) NOT NULL, PRIMARY KEY (DELIVERYID))
CREATE TABLE ORDERS (ORDER_ID BIGINT AUTO_INCREMENT NOT NULL, TENANT_ID VARCHAR(12), CLOSE_TIME TIME, COMMENT VARCHAR(255), CREATE_TIME DATETIME NOT NULL, CURRENCY VARCHAR(255), DEPOSIT FLOAT, DINNER_TIME TIME NOT NULL, DISCOUNT DECIMAL(38), IS_TAKE_OUT TINYINT(1) default 0, OTHER_FEE FLOAT, OWNER_ID BIGINT NOT NULL, PROMOTION_ID BIGINT, PROMOTION_NAME VARCHAR(128), SERVICE_FEE FLOAT, STATUS VARCHAR(32), STORE_CODE VARCHAR(255) NOT NULL, TOTAL_PAYMENT DECIMAL(38), PRIMARY KEY (ORDER_ID))
CREATE TABLE PAYMENT (PAYMENT_ID BIGINT AUTO_INCREMENT NOT NULL, TENANT_ID VARCHAR(12), CLOSE_TIME TIME, CREATE_TIME DATETIME NOT NULL, CURRENCY VARCHAR(255), ORDER_ID BIGINT NOT NULL, OWNER_ID BIGINT NOT NULL, STATUS VARCHAR(32), STORE_CODE VARCHAR(255) NOT NULL, TOTAL DECIMAL(38), PRIMARY KEY (PAYMENT_ID))
CREATE TABLE CATEGORY (ID BIGINT AUTO_INCREMENT NOT NULL, TENANT_ID VARCHAR(12), CHANGED_TIME DATETIME, CODE VARCHAR(255), CREATED_BY BIGINT, CREATED_TIME DATETIME NOT NULL, IS_ENABLED TINYINT(1) default 0, NAME VARCHAR(128), PRIMARY KEY (ID))
CREATE UNIQUE INDEX IDX_CATEGORY_T_CODE ON CATEGORY (code, TENANT_ID)
CREATE INDEX INDEX_CATEGORY_CODE ON CATEGORY (CODE)
CREATE TABLE DINING_TABLE (ID BIGINT AUTO_INCREMENT NOT NULL, BAR_CODE VARCHAR(128) NOT NULL, CAPACITY INTEGER, NUMBER VARCHAR(32) NOT NULL, ROOM_ID BIGINT, PRIMARY KEY (ID))
CREATE UNIQUE INDEX IDX_DT_BAR_CODE ON DINING_TABLE (BAR_CODE)
CREATE INDEX IDX_DT_NUMBE ON DINING_TABLE (NUMBER)
CREATE TABLE ITEM (ID BIGINT AUTO_INCREMENT NOT NULL, TENANT_ID VARCHAR(12), CHANGED_TIME DATETIME, CODE VARCHAR(255), CREATED_BY BIGINT, CREATED_TIME DATETIME NOT NULL, IS_ENABLED TINYINT(1) default 0, PRICE FLOAT, STORE_CODE VARCHAR(64) NOT NULL, ITEMTEMPLATE_ID BIGINT, PRIMARY KEY (ID))
CREATE INDEX INDEX_ITEM_CODE ON ITEM (CODE)
CREATE TABLE ITEM_TEMPLATE (ID BIGINT AUTO_INCREMENT NOT NULL, TENANT_ID VARCHAR(12), CHANGED_TIME DATETIME, CODE VARCHAR(255), CREATED_BY BIGINT, CREATED_TIME DATETIME NOT NULL, PRICE_UNIT VARCHAR(255), DESCRIPTION LONGTEXT, IS_ENABLED TINYINT(1) default 0, ITEM_UNIT VARCHAR(255), NAME VARCHAR(128), PRICE FLOAT, TYPE VARCHAR(64), PRIMARY KEY (ID))
CREATE UNIQUE INDEX IDX_ITEM_TPL_T_CODE ON ITEM_TEMPLATE (CODE, TENANT_ID)
CREATE INDEX IDX_ITEM_TPL_T_NAME ON ITEM_TEMPLATE (NAME, TENANT_ID)
CREATE INDEX INDEX_ITEM_TEMPLATE_CODE ON ITEM_TEMPLATE (CODE)
CREATE TABLE PROMOTION (ID BIGINT AUTO_INCREMENT NOT NULL, TENANT_ID VARCHAR(12), CHANGED_TIME DATETIME, CODE VARCHAR(255), CREATED_BY BIGINT, CREATED_TIME DATETIME NOT NULL, Details VARCHAR(255) NOT NULL, IS_ENABLED TINYINT(1) default 0, NAME VARCHAR(128) NOT NULL, STORE_CODE VARCHAR(64) NOT NULL, PROMOTION_TYPE VARCHAR(255) NOT NULL, VALID_FROM DATETIME NOT NULL, VALID_TO DATETIME NOT NULL, QUALIFIER VARCHAR(128) NOT NULL, PRIMARY KEY (ID))
CREATE INDEX INDEX_PROMOTION_CODE ON PROMOTION (CODE)
CREATE TABLE ROOM (ID BIGINT AUTO_INCREMENT NOT NULL, TENANT_ID VARCHAR(12), CHANGED_TIME DATETIME, CODE VARCHAR(255), CREATED_BY BIGINT, CREATED_TIME DATETIME NOT NULL, CURRENCY VARCHAR(255), DESCRIPTION VARCHAR(255), IS_ENABLED TINYINT(1) default 0, MINIMUM_COST DOUBLE, NAME VARCHAR(255) NOT NULL, STORE_CODE VARCHAR(64) NOT NULL, TABLE_CAPACITY INTEGER, PRIMARY KEY (ID))
CREATE UNIQUE INDEX IDX_ROOM_STR_CODE ON ROOM (STORE_CODE, CODE, TENANT_ID)
CREATE UNIQUE INDEX IDX_ROOM_STR_NAME ON ROOM (STORE_CODE, NAME, TENANT_ID)
CREATE INDEX INDEX_ROOM_CODE ON ROOM (CODE)
CREATE TABLE STORE (ID BIGINT AUTO_INCREMENT NOT NULL, TENANT_ID VARCHAR(12), BUSINESS_HOUR_FROM INTEGER NOT NULL, BUSINESS_HOUR_TO INTEGER NOT NULL, CHANGED_TIME DATETIME, CODE VARCHAR(255), CREATED_BY BIGINT, CREATED_TIME DATETIME NOT NULL, DESCRIPTION LONGTEXT, IS_ENABLED TINYINT(1) default 0, NAME VARCHAR(255) NOT NULL, TELEPHONE1 VARCHAR(32) NOT NULL, TELEPHONE2 VARCHAR(32), TRAFFIC_INFO VARCHAR(512), PRIMARY KEY (ID))
CREATE UNIQUE INDEX IDX_STORE_T_CODE ON STORE (CODE, TENANT_ID)
CREATE UNIQUE INDEX IDX_STORE_T_NAME ON STORE (NAME, TENANT_ID)
CREATE INDEX INDEX_STORE_CODE ON STORE (CODE)
CREATE TABLE USERS (ID BIGINT AUTO_INCREMENT NOT NULL, AGE INTEGER, CHANGEDTIME DATE, CREATEDTIME DATE NOT NULL, EMAIL VARCHAR(128), FIRSTNAME VARCHAR(32), GENDER VARCHAR(20), IMAGEID VARCHAR(512), LASTNAME VARCHAR(56), LOCKED TINYINT(1) default 0, NICKNAME VARCHAR(128), PASSWORD VARCHAR(256), TELEPHONE VARCHAR(20), WEIBOACCOUNT VARCHAR(255) UNIQUE, PRIMARY KEY (ID))
CREATE UNIQUE INDEX INDEX_USERS_EMAIL ON USERS (EMAIL)
CREATE UNIQUE INDEX INDEX_USERS_TELEPHONE ON USERS (TELEPHONE)
CREATE TABLE DELIVERY_LINE (ITEM_CODE VARCHAR(255) NOT NULL, LINE_NUMBER BIGINT NOT NULL, QUANTITY INTEGER, ROOM_CODE VARCHAR(255), order_id BIGINT)
CREATE TABLE ORDER_LINE (COMMENT VARCHAR(255), DISCOUNT DECIMAL(38), ITEM_CODE VARCHAR(255) NOT NULL, LINE_NUMBER INTEGER NOT NULL, PRICE FLOAT, PROMOTION_ID BIGINT, PROMOTION_NAME VARCHAR(255), PROMOTION_PRICE FLOAT, PROMOTION_TOTAL_PAYMENT DECIMAL(38), QUANTITY INTEGER, ROOM_CODE VARCHAR(255), TABLE_NUMBER VARCHAR(255), TOTAL_PAYMENT DECIMAL(38), ORDER_ID BIGINT)
CREATE TABLE PAYMENT_LINE (ITEM_CODE VARCHAR(255) NOT NULL, LINE_NUMBER BIGINT NOT NULL, QUANTITY INTEGER, TOTAL DECIMAL(38), PAYMENT_ID BIGINT)
CREATE TABLE ITEM_TEMPLATE_PICTURE (COMMENTS VARCHAR(512), RAW_IMG_ID VARCHAR(512) NOT NULL, SCALE1_IMG_ID VARCHAR(512), SCALE2_IMG_ID VARCHAR(512), SCALE3_IMG_ID VARCHAR(512), ITEM_TEMPLATE_ID BIGINT)
CREATE TABLE ITEM_TEMPLATE_CATEGORY (ITEM_TEMPLATE_ID BIGINT, CATEGORY_CODE VARCHAR(255))
CREATE TABLE RULE_PARAMETER (NAME VARCHAR(32) NOT NULL, VALUE VARCHAR(128) NOT NULL, RULE_ID BIGINT)
CREATE TABLE PROMOTION_CATEGORY (PROMOTION_ID BIGINT, CATEGORY_CODE VARCHAR(255))
CREATE TABLE PROMOTION_ITEM (PROMOTION_ID BIGINT, ITEM_CODE VARCHAR(255))
CREATE TABLE STORE_ADDRESS (ADDRESS VARCHAR(512) NOT NULL, CITY VARCHAR(128) NOT NULL, COUNTRY VARCHAR(128) NOT NULL, COUNTY VARCHAR(128) NOT NULL, PROVINCE VARCHAR(128) NOT NULL, TOWN VARCHAR(128) NOT NULL, STORE_ID BIGINT)
ALTER TABLE DINING_TABLE ADD CONSTRAINT FK_DINING_TABLE_ROOM_ID FOREIGN KEY (ROOM_ID) REFERENCES ROOM (ID)
ALTER TABLE ITEM ADD CONSTRAINT FK_ITEM_ITEMTEMPLATE_ID FOREIGN KEY (ITEMTEMPLATE_ID) REFERENCES ITEM_TEMPLATE (ID)
ALTER TABLE DELIVERY_LINE ADD CONSTRAINT FK_DELIVERY_LINE_order_id FOREIGN KEY (order_id) REFERENCES DELIVERY (DELIVERYID)
ALTER TABLE ORDER_LINE ADD CONSTRAINT FK_ORDER_LINE_ORDER_ID FOREIGN KEY (ORDER_ID) REFERENCES ORDERS (ORDER_ID)
ALTER TABLE PAYMENT_LINE ADD CONSTRAINT FK_PAYMENT_LINE_PAYMENT_ID FOREIGN KEY (PAYMENT_ID) REFERENCES PAYMENT (PAYMENT_ID)
ALTER TABLE ITEM_TEMPLATE_PICTURE ADD CONSTRAINT FK_ITEM_TEMPLATE_PICTURE_ITEM_TEMPLATE_ID FOREIGN KEY (ITEM_TEMPLATE_ID) REFERENCES ITEM_TEMPLATE (ID)
ALTER TABLE ITEM_TEMPLATE_CATEGORY ADD CONSTRAINT FK_ITEM_TEMPLATE_CATEGORY_ITEM_TEMPLATE_ID FOREIGN KEY (ITEM_TEMPLATE_ID) REFERENCES ITEM_TEMPLATE (ID)
ALTER TABLE RULE_PARAMETER ADD CONSTRAINT FK_RULE_PARAMETER_RULE_ID FOREIGN KEY (RULE_ID) REFERENCES PROMOTION (ID)
ALTER TABLE PROMOTION_CATEGORY ADD CONSTRAINT FK_PROMOTION_CATEGORY_PROMOTION_ID FOREIGN KEY (PROMOTION_ID) REFERENCES PROMOTION (ID)
ALTER TABLE PROMOTION_ITEM ADD CONSTRAINT FK_PROMOTION_ITEM_PROMOTION_ID FOREIGN KEY (PROMOTION_ID) REFERENCES PROMOTION (ID)
ALTER TABLE STORE_ADDRESS ADD CONSTRAINT FK_STORE_ADDRESS_STORE_ID FOREIGN KEY (STORE_ID) REFERENCES STORE (ID)
