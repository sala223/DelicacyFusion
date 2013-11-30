package com.df.masterdata.entity;

public interface Constants {

	public static interface MASTERDATA {
		public static final String ID_PROPERTY = "id";
		public static final String CODE_PROPERTY = "code";
		public static final String IS_ENABLED_PROPERTY = "isEnabled";
	}

	public static interface STORE_AWARE_MASTERDATA extends MASTERDATA {
		public static final String STORE_CODE_PROPERTY = "storeCode";
	}

	public static interface STORE extends MASTERDATA {
		public static final String NAME_PROPERTY = "name";
		public static final String CODE_PROPERTY = "code";
	}

	public static interface ITEM_TEMPLATE extends MASTERDATA {
		public static final String CODE_PROPERTY = "code";
		public static final String NAME_PROPERTY = "name";
		public static final String TYPE_PROPERTY = "type";
		public static final String CATEGORIES_PROPERTY = "categories";
	}

	public static interface ITEM extends STORE_AWARE_MASTERDATA {
		public static final String TEMPLATE_PROPERTY = "itemTemplate";
	}

	public static interface DINING_TABLE extends STORE_AWARE_MASTERDATA {
		public static final String BAR_CODE_PROPERTY = "barCode";
	}

	public static interface ROOM extends STORE_AWARE_MASTERDATA {
		public static final String NAME_PROPERTY = "name";
	}

	public static interface EMPLOYEE extends MASTERDATA {
		public static final String EMAIL_PROPERTY = "email";
		public static final String CELL_PHONE_PROPERTY = "cellPhone";
	}

	public static interface PROMOTION extends STORE_AWARE_MASTERDATA {
		public static final String NAME_PROPERTY = "name";

		public static final String VALID_FROM_PROPERTY = "validFrom";

		public static final String VALID_TO_PROPERTY = "validTo";

		public static final String TYPE_PROPERTY = "type";

		public static final String ITEMS_PROPERTY = "items";

		public static final String CATEGORIES_PROPERTY = "categories";

	}
}
