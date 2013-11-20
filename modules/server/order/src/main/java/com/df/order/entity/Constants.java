package com.df.order.entity;

public interface Constants {
	public static interface TTAN_ENTITY {
		public static final String OWNER_ID = "ownerId";
		public static final String STORE_CODE = "storeCode";
		public static final String STATUS = "status";
		public static final String CREATE_TIME = "createTime";
		public static final String CLOSE_TIME = "closeTime";

	}

	public static interface ORDER extends TTAN_ENTITY {
		public static final String TRAN_ID = "orderId";
		public static final String SERVICE_CARD_ID = "serviceCardId";
		public static final String OCCUPILED_TABLES = "occupiedTables";
	}

	public static interface SERVICE_CARD {
		public static final String ID = "id";
		public static final String STORE_CODE = "storeCode";
		public static final String TABLES = "tables";
		public static final String ORDER_ID = "orderId";
	}

	public static interface TABLE_RESOURCE {
		public static final String STORE_CODE = "storeCode";
	}
}
