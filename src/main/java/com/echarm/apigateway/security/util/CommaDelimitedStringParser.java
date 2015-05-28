package com.echarm.apigateway.security.util;

import java.util.ArrayList;

import com.echarm.apigateway.accountsystem.error.InvalidParameterException;
import com.echarm.apigateway.accountsystem.error.MissingParameterErrorBody;
import com.echarm.apigateway.accountsystem.error.ServerSideProblemException;

public class CommaDelimitedStringParser {

	public static String[] parse(String str) throws InvalidParameterException {
		if (str == null) {
			throw new ServerSideProblemException("Comma delimited string should not be null");
		}

		if (str.equals("")) {
			// empty string, throw error
			MissingParameterErrorBody body = new MissingParameterErrorBody(MissingParameterErrorBody.generateDescription("String: id_list", "Query Parameter"));
            InvalidParameterException exception = new InvalidParameterException("Empty String Parameter: Comma Delimited String");
            exception.setErrorBody(body);
            throw exception;
		}

		if (str.matches("^,+")) {
			// TODO create a new error body
			InvalidParameterException exception = new InvalidParameterException("Invalid String Parameter: Comma Delimited String");
            exception.setErrorBody(null);
            throw exception;
		}

		String[] accountIdList = str.split(",");

		if (accountIdList == null || accountIdList.length <= 0) {
			throw new ServerSideProblemException("Account ID List should not be null or empty");
		}

		ArrayList<String> idList = new ArrayList<String>();
		for (int i = 0; i < accountIdList.length; i++) {
			if (accountIdList[i] != null && !accountIdList[i].equals(""))
				idList.add(accountIdList[i]);
		}

		if (idList.size() <= 0) {
			throw new ServerSideProblemException("Account ID List should not be null or empty");
		}
		accountIdList = idList.toArray(new String[idList.size()]);

		return accountIdList;
	}

}
