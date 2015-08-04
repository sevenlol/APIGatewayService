package com.echarm.apigateway.popular.error;

import com.echarm.apigateway.accountsystem.error.EmptyParameterErrorBody;
import com.echarm.apigateway.accountsystem.error.InvalidParameterException;
import com.echarm.apigateway.accountsystem.error.MissingParameterErrorBody;
import com.echarm.apigateway.accountsystem.error.ServerSideProblemException;

public class PopularListExceptionFactory {

    public static Exception getMissingParamException(String obj, String in, String msg) {
        MissingParameterErrorBody body = new MissingParameterErrorBody(MissingParameterErrorBody.generateDescription(obj, in));
        InvalidParameterException exception = new InvalidParameterException(msg);
        exception.setErrorBody(body);
        return exception;
    }

    public static Exception getEmptyParamException(String obj, String in, String msg) {
        EmptyParameterErrorBody body = new EmptyParameterErrorBody(EmptyParameterErrorBody.generateDescription(obj, in));
        InvalidParameterException exception = new InvalidParameterException(msg);
        exception.setErrorBody(body);
        return exception;
    }

    public static Exception getServerProblemException(String msg) {
        return new ServerSideProblemException(msg);
    }
}
