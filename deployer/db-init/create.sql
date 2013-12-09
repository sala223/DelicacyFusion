CREATE TABLE DELIVERY (DELIVERYID BIGINT AUTO_INCREMENT NOT NULL, TENANT_ID VARCHAR(12), CLOSE_TIME DATETIME, CREATE_TIME DATETIME NOT NULL, ORDER_ID BIGINT NOT NULL, OWNER_ID BIGINT NOT NULL, STATUS VARCHAR(32), STORE_CODE VARCHAR(255) NOT NULL, PRIMARY KEY (DELIVERYID))
CREATE TABLE ORDERS (ORDER_ID BIGINT AUTO_INCREMENT NOT NULL, TENANT_ID VARCHAR(12), CLOSE_TIME DATETIME, COMMENT VARCHAR(255), CREATE_TIME DATETIME NOT NULL, CURRENCY VARCHAR(255), DEPOSIT FLOAT, DINNER_TIME DATETIME NOT NULL, DISCOUNT DECIMAL(38), IS_TAKE_OUT TINYINT(1) default 0, OTHER_FEE FLOAT, OWNER_ID BIGINT NOT NULL, PROMOTION_NAME VARCHAR(128), SERVICE_CARD_ID BIGINT, SERVICE_FEE FLOAT, STATUS VARCHAR(32), STORE_CODE VARCHAR(255) NOT NULL, TOTAL_PAYMENT DECIMAL(38), TOTAL_PAYMENT_AFTER_DISCOUNT DECIMAL(38), PRIMARY KEY (ORDER_ID))
CREATE TABLE PAYMENT (PAYMENT_ID BIGINT AUTO_INCREMENT NOT NULL, TENANT_ID VARCHAR(12), CLOSE_TIME DATETIME, CREATE_TIME DATETIME NOT NULL, CURRENCY VARCHAR(255), ORDER_ID BIGINT NOT NULL, OWNER_ID BIGINT NOT NULL, SPECIAL_DISCOUNT DECIMAL(38), STATUS VARCHAR(32), STORE_CODE VARCHAR(255) NOT NULL, TOTAL_AMOUNT DECIMAL(38), PRIMARY KEY (PAYMENT_ID))
CREATE TABLE SERVICE_CARD (ID BIGINT AUTO_INCREMENT NOT NULL, TENANT_ID VARCHAR(12), CREATED_TIME DATETIME NOT NULL, ORDER_ID BIGINT, STORE_CODE VARCHAR(64) NOT NULL, PRIMARY KEY (ID))
CREATE TABLE TENANT (CODE VARCHAR(64) NOT NULL, ADDRESS VARCHAR(1024) NOT NULL, CHANGED_TIME DATETIME, CREATED_BY BIGINT, CREATED_TIME DATETIME NOT NULL, DESCRIPTION LONGTEXT, IS_ENABLED TINYINT(1) default 0, NAME VARCHAR(128) NOT NULL UNIQUE, PRIMARY KEY (CODE))
CREATE TABLE GLOBAL_CONFIGURATION (ID BIGINT AUTO_INCREMENT NOT NULL, TENANT_ID VARCHAR(12), DOMAIN VARCHAR(64) NOT NULL, C_KEY VARCHAR(128) NOT NULL, C_VALUE LONGBLOB, PRIMARY KEY (ID))
CREATE UNIQUE INDEX IDX_GLOBAL_CONFIGURATION_T_DOMAIN_KEY ON GLOBAL_CONFIGURATION (DOMAIN, C_KEY, TENANT_ID)
CREATE TABLE DINING_TABLE (ID BIGINT AUTO_INCREMENT NOT NULL, TENANT_ID VARCHAR(12), BAR_CODE VARCHAR(128) NOT NULL, CAPACITY INTEGER, CHANGED_TIME DATETIME, CODE VARCHAR(255), CREATED_BY BIGINT, CREATED_TIME DATETIME NOT NULL, IS_ENABLED TINYINT(1) default 0, STORE_CODE VARCHAR(64) NOT NULL, PRIMARY KEY (ID))
CREATE UNIQUE INDEX IDX_DT_BAR_CODE ON DINING_TABLE (BAR_CODE)
CREATE INDEX IDX_DT_CODE ON DINING_TABLE (STORE_CODE, CODE)
CREATE INDEX INDEX_DINING_TABLE_CODE ON DINING_TABLE (CODE)
CREATE TABLE EMPLOYEE (ID BIGINT AUTO_INCREMENT NOT NULL, TENANT_ID VARCHAR(12), BIRTH DATE, CELL_PHONE VARCHAR(20), CHANGED_TIME DATETIME, CODE VARCHAR(255), CREATED_BY BIGINT, CREATED_TIME DATETIME NOT NULL, EMAIL VARCHAR(128) NOT NULL, FIRST_NAME VARCHAR(64), GENDER INTEGER, IS_ENABLED TINYINT(1) default 0, IS_LICENSED TINYINT(1) default 0, LAST_NAME VARCHAR(64), Login_USER_ID BIGINT, STORE_CODE VARCHAR(255), PRIMARY KEY (ID))
CREATE INDEX INDEX_EMPLOYEE_CODE ON EMPLOYEE (CODE)
CREATE TABLE ITEM (ID BIGINT AUTO_INCREMENT NOT NULL, TENANT_ID VARCHAR(12), CHANGED_TIME DATETIME, CODE VARCHAR(255), CREATED_BY BIGINT, CREATED_TIME DATETIME NOT NULL, IS_ENABLED TINYINT(1) default 0, PRICE FLOAT, STORE_CODE VARCHAR(64) NOT NULL, ITEMTEMPLATE_ID BIGINT, PRIMARY KEY (ID))
CREATE INDEX INDEX_ITEM_CODE ON ITEM (CODE)
CREATE TABLE ITEM_TEMPLATE (ID BIGINT AUTO_INCREMENT NOT NULL, TENANT_ID VARCHAR(12), CHANGED_TIME DATETIME, CODE VARCHAR(255), CREATED_BY BIGINT, CREATED_TIME DATETIME NOT NULL, PRICE_UNIT VARCHAR(255), DESCRIPTION LONGTEXT, IS_ENABLED TINYINT(1) default 0, ITEM_UNIT VARCHAR(255), NAME VARCHAR(128), PRICE FLOAT, TYPE VARCHAR(64), PRIMARY KEY (ID))
CREATE UNIQUE INDEX IDX_ITEM_TPL_T_CODE ON ITEM_TEMPLATE (CODE, TENANT_ID)
CREATE INDEX IDX_ITEM_TPL_T_NAME ON ITEM_TEMPLATE (NAME, TENANT_ID)
CREATE INDEX INDEX_ITEM_TEMPLATE_CODE ON ITEM_TEMPLATE (CODE)
CREATE TABLE PROMOTION (ID BIGINT AUTO_INCREMENT NOT NULL, TENANT_ID VARCHAR(12), CHANGED_TIME DATETIME, CODE VARCHAR(255), CREATED_BY BIGINT, CREATED_TIME DATETIME NOT NULL, DETAILS VARCHAR(255) NOT NULL, IS_ENABLED TINYINT(1) default 0, NAME VARCHAR(128) NOT NULL, QUALIFIER VARCHAR(128) NOT NULL, STORE_CODE VARCHAR(64) NOT NULL, PROMOTION_TYPE VARCHAR(255) NOT NULL, VALID_FROM DATETIME NOT NULL, VALID_TO DATETIME NOT NULL, PRIMARY KEY (ID))
CREATE INDEX INDEX_PROMOTION_CODE ON PROMOTION (CODE)
CREATE TABLE ROOM (ID BIGINT AUTO_INCREMENT NOT NULL, TENANT_ID VARCHAR(12), CHANGED_TIME DATETIME, CODE VARCHAR(255), CREATED_BY BIGINT, CREATED_TIME DATETIME NOT NULL, CURRENCY VARCHAR(255), DESCRIPTION VARCHAR(255), IS_ENABLED TINYINT(1) default 0, MINIMUM_COST DOUBLE, NAME VARCHAR(255) NOT NULL, STORE_CODE VARCHAR(64) NOT NULL, PRIMARY KEY (ID))
CREATE UNIQUE INDEX IDX_ROOM_STR_CODE ON ROOM (STORE_CODE, CODE, TENANT_ID)
CREATE UNIQUE INDEX IDX_ROOM_STR_NAME ON ROOM (STORE_CODE, NAME, TENANT_ID)
CREATE INDEX INDEX_ROOM_CODE ON ROOM (CODE)
CREATE TABLE STORE (ID BIGINT AUTO_INCREMENT NOT NULL, TENANT_ID VARCHAR(12), ADDRESS VARCHAR(1024) NOT NULL, BUSINESS_HOUR_FROM INTEGER NOT NULL, BUSINESS_HOUR_TO INTEGER NOT NULL, CHANGED_TIME DATETIME, CODE VARCHAR(255), CREATED_BY BIGINT, CREATED_TIME DATETIME NOT NULL, DESCRIPTION LONGTEXT, IMG_ID VARCHAR(512), IS_ENABLED TINYINT(1) default 0, NAME VARCHAR(255) NOT NULL, TELEPHONE1 VARCHAR(32) NOT NULL, TELEPHONE2 VARCHAR(32), TRAFFIC_INFO VARCHAR(512), PRIMARY KEY (ID))
CREATE UNIQUE INDEX IDX_STORE_T_CODE ON STORE (CODE, TENANT_ID)
CREATE UNIQUE INDEX IDX_STORE_T_NAME ON STORE (NAME, TENANT_ID)
CREATE INDEX INDEX_STORE_CODE ON STORE (CODE)
CREATE TABLE PERMISSIONS (ID BIGINT AUTO_INCREMENT NOT NULL, DESCRIPTION VARCHAR(1025), NAME VARCHAR(256), PRIMARY KEY (ID))
CREATE TABLE ROLES (ID BIGINT AUTO_INCREMENT NOT NULL, DESCRIPTION VARCHAR(1025), DOMAIN VARCHAR(64), NAME VARCHAR(256), PRIMARY KEY (ID))
CREATE TABLE USERS (ID BIGINT AUTO_INCREMENT NOT NULL, AGE INTEGER, CELLPHONE VARCHAR(20), CHANGED_TIME DATETIME, CREATED_TIME DATETIME NOT NULL, EMAIL VARCHAR(128) NOT NULL, FIRSTNAME VARCHAR(32), GENDER INTEGER, IMAGEID VARCHAR(512), IS_CELL_PHONE_VERIFIED TINYINT(1) default 0, IS_EMAIL_VERIFIED TINYINT(1) default 0, IS_TENANT_OWNER TINYINT(1) default 0, LASTNAME VARCHAR(56), LOCKED TINYINT(1) default 0, NICKNAME VARCHAR(128), PASSWORD VARCHAR(256) NOT NULL, TENANT_CODE VARCHAR(255), WEIBOACCOUNT VARCHAR(255) UNIQUE, PRIMARY KEY (ID))
CREATE UNIQUE INDEX INDEX_USERS_WEIBOACCOUNT ON USERS (WEIBOACCOUNT)
CREATE UNIQUE INDEX INDEX_USERS_CELLPHONE ON USERS (CELLPHONE)
CREATE UNIQUE INDEX INDEX_USERS_EMAIL ON USERS (EMAIL)
CREATE TABLE DELIVERY_LINE (ITEM_CODE VARCHAR(255) NOT NULL, LINE_NUMBER BIGINT NOT NULL, QUANTITY INTEGER, ROOM_CODE VARCHAR(255), order_id BIGINT)
CREATE TABLE ORDER_LINE (COMMENT VARCHAR(255), DISCOUNT DECIMAL(38), ITEM_CODE VARCHAR(255) NOT NULL, ITEM_NAME VARCHAR(128), ITEM_UNIT VARCHAR(32), LINE_NUMBER INTEGER NOT NULL, PRICE FLOAT, PROMOTION_NAME VARCHAR(255), PROMOTION_PRICE FLOAT, QUANTITY INTEGER, TABLE_CODE VARCHAR(255), TOTAL_PAYMENT DECIMAL(38), TOTAL_PAYMENT_AFTER_DISCOUNT DECIMAL(38), ORDER_ID BIGINT)
CREATE TABLE PAYMENT_LINE (AMOUNT DECIMAL(38), EXTERNAL_TRANSACTION_ID BIGINT, LINE_NUMBER BIGINT NOT NULL, METHOD VARCHAR(32), TIME_OF_PAYMENT VARCHAR(255), PAYMENT_ID BIGINT)
CREATE TABLE SERVICE_TABLE (SERVICE_ID BIGINT, TABLE_CODE VARCHAR(255))
CREATE TABLE ITEM_TEMPLATE_PICTURE (FORMAT VARCHAR(16) NOT NULL, HEIGTH INTEGER, IMG_ID VARCHAR(512) NOT NULL, WIDTH INTEGER, ITEM_TEMPLATE_ID BIGINT)
CREATE TABLE ITEM_TEMPLATE_CATEGORY (ITEM_TEMPLATE_ID BIGINT, CATEGORY_CODE VARCHAR(255))
CREATE TABLE RULE_PARAMETER (NAME VARCHAR(32) NOT NULL, VALUE VARCHAR(128) NOT NULL, PROMOTION_ID BIGINT)
CREATE TABLE PROMOTION_CATEGORY (PROMOTION_ID BIGINT, CATEGORY_CODE VARCHAR(255))
CREATE TABLE PROMOTION_ITEM (PROMOTION_ID BIGINT, ITEM_CODE VARCHAR(255))
CREATE TABLE ROOM_DINING_TABLE (Room_ID BIGINT NOT NULL, tables_ID BIGINT NOT NULL, PRIMARY KEY (Room_ID, tables_ID))
CREATE TABLE ROLE_PERMISSION (ROLE_ID BIGINT NOT NULL, PERMISSION_ID BIGINT NOT NULL, PRIMARY KEY (ROLE_ID, PERMISSION_ID))
ALTER TABLE ITEM ADD CONSTRAINT FK_ITEM_ITEMTEMPLATE_ID FOREIGN KEY (ITEMTEMPLATE_ID) REFERENCES ITEM_TEMPLATE (ID)
ALTER TABLE DELIVERY_LINE ADD CONSTRAINT FK_DELIVERY_LINE_order_id FOREIGN KEY (order_id) REFERENCES DELIVERY (DELIVERYID)
ALTER TABLE ORDER_LINE ADD CONSTRAINT FK_ORDER_LINE_ORDER_ID FOREIGN KEY (ORDER_ID) REFERENCES ORDERS (ORDER_ID)
ALTER TABLE PAYMENT_LINE ADD CONSTRAINT FK_PAYMENT_LINE_PAYMENT_ID FOREIGN KEY (PAYMENT_ID) REFERENCES PAYMENT (PAYMENT_ID)
ALTER TABLE SERVICE_TABLE ADD CONSTRAINT FK_SERVICE_TABLE_SERVICE_ID FOREIGN KEY (SERVICE_ID) REFERENCES SERVICE_CARD (ID)
ALTER TABLE ITEM_TEMPLATE_PICTURE ADD CONSTRAINT FK_ITEM_TEMPLATE_PICTURE_ITEM_TEMPLATE_ID FOREIGN KEY (ITEM_TEMPLATE_ID) REFERENCES ITEM_TEMPLATE (ID)
ALTER TABLE ITEM_TEMPLATE_CATEGORY ADD CONSTRAINT FK_ITEM_TEMPLATE_CATEGORY_ITEM_TEMPLATE_ID FOREIGN KEY (ITEM_TEMPLATE_ID) REFERENCES ITEM_TEMPLATE (ID)
ALTER TABLE RULE_PARAMETER ADD CONSTRAINT FK_RULE_PARAMETER_PROMOTION_ID FOREIGN KEY (PROMOTION_ID) REFERENCES PROMOTION (ID)
ALTER TABLE PROMOTION_CATEGORY ADD CONSTRAINT FK_PROMOTION_CATEGORY_PROMOTION_ID FOREIGN KEY (PROMOTION_ID) REFERENCES PROMOTION (ID)
ALTER TABLE PROMOTION_ITEM ADD CONSTRAINT FK_PROMOTION_ITEM_PROMOTION_ID FOREIGN KEY (PROMOTION_ID) REFERENCES PROMOTION (ID)
ALTER TABLE ROOM_DINING_TABLE ADD CONSTRAINT FK_ROOM_DINING_TABLE_tables_ID FOREIGN KEY (tables_ID) REFERENCES DINING_TABLE (ID)
ALTER TABLE ROOM_DINING_TABLE ADD CONSTRAINT FK_ROOM_DINING_TABLE_Room_ID FOREIGN KEY (Room_ID) REFERENCES ROOM (ID)
ALTER TABLE ROLE_PERMISSION ADD CONSTRAINT FK_ROLE_PERMISSION_ROLE_ID FOREIGN KEY (ROLE_ID) REFERENCES ROLES (ID)
ALTER TABLE ROLE_PERMISSION ADD CONSTRAINT FK_ROLE_PERMISSION_PERMISSION_ID FOREIGN KEY (PERMISSION_ID) REFERENCES PERMISSIONS (ID)
