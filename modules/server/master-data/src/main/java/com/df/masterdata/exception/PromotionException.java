package com.df.masterdata.exception;

import com.df.core.common.exception.BusinessException;
import com.df.masterdata.entity.Promotion.PromotionType;
import com.df.masterdata.promotion.rule.descriptor.RuleDescriptor.RuleType;

public class PromotionException extends BusinessException {

	private static final long serialVersionUID = 1L;

	private static final String REALM = "Promotion";

	public static final int RULE_IS_NOT_FOUND = 100000;

	public static final int PROMOTION_WITH_CODE_EXIST = 100001;

	public static final int PROMOTION_WITH_CODE_NOT_EXIST = 100002;

	public static final int AT_LEAST_ONE_CATEGORY = 100003;

	public static final int AT_LEAST_ONE_ITEM = 100004;

	public static final int RULE_TYPE_MIS_MATCH = 100003;

	public PromotionException(Throwable cause, int errorCode) {
		super(cause, REALM, errorCode);
	}

	public PromotionException(Throwable cause, int errorCode, String messageFormat, Object... args) {
		super(cause, REALM, errorCode, messageFormat, args);
	}

	public PromotionException(int errorCode, String messageFormat, Object... args) {
		super(null, REALM, errorCode, messageFormat, args);
	}

	public static PromotionException ruleIsNotFound(String ruleQualifier) {
		return new PromotionException(RULE_IS_NOT_FOUND, "Rule %s is not found.", ruleQualifier);
	}

	public static PromotionException ruleTypeMisMatch(PromotionType pt) {
		String message = "";
		if (pt == PromotionType.CATEGORY || pt == PromotionType.ITEM) {
			message = "Rule Type must be " + RuleType.ITEM + " or " + RuleType.ALL;
		} else {
			message = "Rule Type must be " + RuleType.ORDER;

		}
		return new PromotionException(RULE_TYPE_MIS_MATCH, message);
	}

	public static PromotionException promitionWithCodeAlreadyExist(String promotionCode) {
		return new PromotionException(PROMOTION_WITH_CODE_EXIST, "Promition with code %s already exist.", promotionCode);
	}

	public static PromotionException atLeastOneCategoryToBeSpecified() {
		return new PromotionException(AT_LEAST_ONE_CATEGORY, "At least one category to be specified");
	}

	public static PromotionException atLeastOneItemToBeSpecified() {
		return new PromotionException(AT_LEAST_ONE_ITEM, "At least one item to be specified");
	}

	public static PromotionException promitionWithCodeNotExist(String promotionCode) {
		String msgFmt = "Promition with code %s does not exist.";
		return new PromotionException(PROMOTION_WITH_CODE_NOT_EXIST, msgFmt, promotionCode);
	}

}
