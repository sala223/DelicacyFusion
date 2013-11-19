package com.df.masterdata.auxiliary;

import com.df.masterdata.auxiliary.Category;

public interface CategoryLoader {

	Category[] loadCategories();
	
	Category getCategory(String categoryCode);

}
