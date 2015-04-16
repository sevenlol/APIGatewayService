package com.echarm.apigateway.accountsystem.util;

import com.echarm.apigateway.accountsystem.error.CategoryNotExistErrorBody;
import com.echarm.apigateway.accountsystem.error.EmptyParameterErrorBody;
import com.echarm.apigateway.accountsystem.error.InvalidParameterException;
import com.echarm.apigateway.accountsystem.error.MissingParameterErrorBody;
import com.echarm.apigateway.accountsystem.error.ResourceNotExistException;
import com.echarm.apigateway.accountsystem.model.Account;
import com.echarm.apigateway.accountsystem.model.DoctorAccount;

public class ControllerUtil {
	public static void checkRequestInput(boolean checkAccount, boolean checkAccountFieldNonNull, Account account, boolean checkCategory, boolean allowArbitraryCategory, String category)
                                         throws ResourceNotExistException, InvalidParameterException {

        if (checkCategory && Category.isCategoryExist(category) == null) {
        	ResourceNotExistException exception = new ResourceNotExistException("Category "+category+" Not Exist!");
            exception.setErrorBody(new CategoryNotExistErrorBody(category));
            throw exception;
        }

        if (!allowArbitraryCategory && category != null && Category.Arbitrary.equals(Category.isCategoryExist(category))) {
            ResourceNotExistException exception = new ResourceNotExistException("Arbitrary Category is not allowed");
            exception.setErrorBody(new CategoryNotExistErrorBody(category));
            throw exception;
        }

        if (!checkAccount)
        	return;

        // no json body in the HTTP request
        if (account == null) {
        	MissingParameterErrorBody body = new MissingParameterErrorBody(MissingParameterErrorBody.generateDescription("Json Object: Account", "Body"));
            InvalidParameterException exception = new InvalidParameterException("No Json Body in the Request!");
            exception.setErrorBody(body);
            throw exception;
        }

        // empty json body
        if (account.isAllJsonInputFieldNull()) {
        	EmptyParameterErrorBody body = new EmptyParameterErrorBody(EmptyParameterErrorBody.generateDescription("Json Object: Account", "Body"));
            InvalidParameterException exception = new InvalidParameterException("Empty Json Body in the Request!");
            exception.setErrorBody(body);
            throw exception;
        }

        // some input json fields null
        if (checkAccountFieldNonNull && !account.isAllJsonInputFieldNonNull()) {
        	String[] nullFieldNameList = account.getNullJsonInputFieldName();
        	if (nullFieldNameList != null) {
                StringBuilder builder = new StringBuilder();

                for (int i = 0; i < nullFieldNameList.length; i++) {
                    if (i != 0)
                        builder.append(",");
                    builder.append(nullFieldNameList[i]);
                }

                MissingParameterErrorBody body = new MissingParameterErrorBody(MissingParameterErrorBody.generateDescription(builder.toString(), "Body - Json Object: Account"));
                InvalidParameterException exception = new InvalidParameterException("Not All Fields of Account is in Json Body!");
                exception.setErrorBody(body);
                throw exception;
            }
        }

        // some input json fields empty
        if (checkAccountFieldNonNull && !account.isAllJsonInputFieldNonEmpty()) {
        	String[] emptyFieldNameList = account.getEmptyJsonInputFieldName();
            if (emptyFieldNameList != null) {
                StringBuilder builder = new StringBuilder();

                for (int i = 0; i < emptyFieldNameList.length; i++) {
                    if (i != 0)
                        builder.append(",");
                    builder.append(emptyFieldNameList[i]);
                }

                EmptyParameterErrorBody body = new EmptyParameterErrorBody(EmptyParameterErrorBody.generateDescription(builder.toString(), "Body - Json Object: Account"));
                InvalidParameterException exception = new InvalidParameterException("Some Fields of Account is empty in Json Body!");
                exception.setErrorBody(body);
                throw exception;
            }
        }

        // check doctor info
        // some field null
        if (checkAccountFieldNonNull && account instanceof DoctorAccount && !((DoctorAccount) account).getUserInfo().isAllRequiredFieldNonNull()) {
            String[] nullFieldNameList = ((DoctorAccount) account).getUserInfo().getNullRequiredInputFieldName();
            if (nullFieldNameList != null) {
                StringBuilder builder = new StringBuilder();

                for (int i = 0; i < nullFieldNameList.length; i++) {
                    if (i != 0)
                        builder.append(",");
                    builder.append(nullFieldNameList[i]);
                }

                MissingParameterErrorBody body = new MissingParameterErrorBody(MissingParameterErrorBody.generateDescription(builder.toString(), "Body - Json Object: Account - Json Object: UserInfo"));
                InvalidParameterException exception = new InvalidParameterException("Not All Fields of DoctorInfo is in Account - Json Body!");
                exception.setErrorBody(body);
                throw exception;
            }
        }

        // check doctor info
        // some non-null field empty
        if (checkAccountFieldNonNull && account instanceof DoctorAccount && !((DoctorAccount) account).getUserInfo().isAllRequiredNonNullFieldNonEmpty()) {
            String[] emptyFieldNameList = ((DoctorAccount) account).getUserInfo().getEmptyRequiredInputFieldName();
            if (emptyFieldNameList != null) {
                StringBuilder builder = new StringBuilder();

                for (int i = 0; i < emptyFieldNameList.length; i++) {
                    if (i != 0)
                        builder.append(",");
                    builder.append(emptyFieldNameList[i]);
                }

                EmptyParameterErrorBody body = new EmptyParameterErrorBody(EmptyParameterErrorBody.generateDescription(builder.toString(), "Body - Json Object: Account - Json Object: UserInfo"));
                InvalidParameterException exception = new InvalidParameterException("Some Fields of DoctorInfo is empty in Account - Json Body!");
                exception.setErrorBody(body);
                throw exception;
            }
        }
	}
}
