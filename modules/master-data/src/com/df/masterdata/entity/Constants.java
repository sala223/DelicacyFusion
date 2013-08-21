package com.df.masterdata.entity;

public interface Constants {

    public static interface MASTERDATA {
	public static final String IS_ENABLED_PROPERTY = "isEnabled";
    }

    public static interface STOREMASTERDATA extends MASTERDATA {
	public static final String STORE_PROPERTY = "store";
	public static final String STORE_ID_PROPERTY = "store.id";

    }

    public static interface CATEGORY extends MASTERDATA {
	public static final String ENTITY_TABLE = "Category";
	public static final String NAME_PROPERTY = "name";
	public static final String ID_PROPERTY = "id";
	public static final String PARENT_ID_PROPERTY = "parent.id";
    }

    public static interface STORE extends MASTERDATA {
	public static final String NAME_PROPERTY = "name";
	public static final String CODE_PROPERTY = "code";
    }

    public static interface ITEM_TEMPLATE extends MASTERDATA {
	public static final String TABLE_NAME = "item_template";
	public static final String CODE_PROPERTY = "code";
	public static final String NAME_PROPERTY = "name";
	public static final String CATEGORIES_PROPERTY = "categories";
    }

    public static interface ITEM {
	public static final String CODE_PROPERTY = "code";
	public static final String CATEGORY_ID_PROPERTY = "categories.id";
    }

    public static interface DINING_TABLE {
	public static final String NUMBER_PROPERTY = "number";
	public static final String BAR_CODE_PROPERTY = "barCode";
    }

    public static interface ROOM {
	public static final String NAME_PROPERTY = "name";
    }
}
