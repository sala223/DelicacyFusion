package com.df.order.entity;

public interface Constants {
	public static interface TTAN_ENTITY {
		public static final String OWNER_ID = "ownerId";
		public static final String STORE_CODE = "storeCode";
		public static final String STATUS = "status";
	}

	public static interface ORDER extends TTAN_ENTITY {
		public static final String TRAN_ID = "orderId";
	}

	public static interface TABLE_RESOURCE {
		public static final String STORE_CODE = "storeCode";
	}
}
