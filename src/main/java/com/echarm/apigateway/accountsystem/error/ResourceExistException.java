package com.echarm.apigateway.accountsystem.error;

public class ResourceExistException extends Exception implements AttachedErrorBody {

    /**
     * This exception is thrown when some resource already exists.
     * Resources include Account
     *
     * Attachable ErrorBody subclasses:
     *      AccountExistErrorBody
     */
    private static final long serialVersionUID = 1L;

    private ErrorBody errorBody;

    public ResourceExistException(String msg) {
        super(msg);
    }

    @Override
    public void setErrorBody(ErrorBody body) {
        if (body instanceof AccountExistErrorBody)
            this.errorBody = body;
    }

    @Override
    public ErrorBody getErrorBody() {
        return errorBody;
    }

}
