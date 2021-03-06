package com.echarm.apigateway.accountsystem.error;

import com.echarm.apigateway.popular.error.PopularArticleListNotExistErrorBody;
import com.echarm.apigateway.popular.error.PopularDoctorListNotExistErrorBody;
import com.echarm.apigateway.popular.error.PopularQAListNotExistErrorBody;

public class ResourceNotExistException extends Exception implements AttachedErrorBody{

	/**
	 * This exception is thrown when some resource specified in the request doesn't exist.
	 * Resources include Category and Article
	 *
	 * Attachable ErrorBody subclasses:
	 * 		CategoryNotExistErrorBody
	 * 		ArticleNotExistErrorBody
	 */
	private static final long serialVersionUID = 1L;

	private ErrorBody errorBody;

	public ResourceNotExistException(String msg) {
		super(msg);
	}

	@Override
	public void setErrorBody(ErrorBody body) {
		// only accept specific errors
		if (body instanceof CategoryNotExistErrorBody ||
			body instanceof AccountNotExistErrorBody ||
			body instanceof PopularArticleListNotExistErrorBody ||
			body instanceof PopularDoctorListNotExistErrorBody ||
			body instanceof PopularQAListNotExistErrorBody) {
			this.errorBody = body;
		}
	}

	@Override
	public ErrorBody getErrorBody() {
		return errorBody;
	}

}
