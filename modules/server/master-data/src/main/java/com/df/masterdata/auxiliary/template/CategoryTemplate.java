package com.df.masterdata.auxiliary.template;

public interface CategoryTemplate {

    CategoryProfile[] getCategories();

    CategoryProfile getCategory(String categoryCode);
}
