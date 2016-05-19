package com.expenses.util;

public interface SmartMoneyConstants {

	public static String STATUS_SUCCESS = "success";
	public static String STATUS_FAIL = "fail";

	public static String TYPE_MONTH = "month";
	public static String TYPE_WEEK = "week";

	public static String PROJECTION_GET_ACTION = "projectionsGet";
	public static String PROJECTION_SAVE_ACTION = "projectionsSave";

	public static String MONEY_TYPE_SALES = "sales";
	public static String MONEY_TYPE_CASH_GOING_OUT = "cashGoingOut";
	public static String MONEY_TYPE_BASIC_CASH_FLOW = "basicCashFlow";

	public static String BASIC_PROJECTIONS = "BasicProjections";
	public static String USER_MANAGEMENT = "UserManagement";
	public static String CREATE_USER = "createUser";
	public static String VALIDATE_USER = "validateUser";

	// http response codes
	public static String INTERNAL_SERVER_ERROR = "500";
	public static String NOT_FOUND = "404";
	public static String UNAUTHORIZED = "401";
	public static String CONFLICT = "401";
	public static String BAD_REQUEST = "400";
	
	//date format
	public static String DATE_FORMAT = "MMddyyyy";
	
	public static String SAVE_BASIC_FINANCIAL_GOAL = "save";
	public static String FIND_BASIC_FINANCIAL_GOAL = "find";
	public static String FIND_BASIC_FINANCIAL_GOAL_BY_DATE = "find-by-date";
	public static String UPDATE_BASIC_FINANCIAL_GOAL = "update";
}
