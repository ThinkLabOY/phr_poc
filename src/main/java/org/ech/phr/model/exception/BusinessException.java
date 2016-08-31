package org.ech.phr.model.exception;

import org.apache.log4j.Logger;

public class BusinessException extends Exception {
	
	private static final long serialVersionUID = -5706142497414680765L;
	final static Logger log = Logger.getLogger(BusinessException.class);

	public static BusinessException EX_HBS_001 = new BusinessException("HBS-001", "Hbase IOException");
	public static BusinessException EX_UTL_001 = new BusinessException("UTL-001", "UnsupportedEncodingException");
	public static BusinessException EX_UTL_002 = new BusinessException("UTL-002", "NoSuchAlgorithmException");
	public static BusinessException EX_UTL_003 = new BusinessException("UTL-003", "IllegalAccessException");
	public static BusinessException EX_UTL_004 = new BusinessException("UTL-004", "InvocationTargetException");
	public static BusinessException EX_JSN_001 = new BusinessException("JSN-001", "JSON JsonProcessingException");
	public static BusinessException EX_JSN_002 = new BusinessException("JSN-002", "JSON JsonParseException");
	public static BusinessException EX_JSN_003 = new BusinessException("JSN-003", "JSON JsonMappingException");
	public static BusinessException EX_JSN_004 = new BusinessException("JSN-004", "JSON IOException");
	
	public static BusinessException EX_USR_001 = new BusinessException("USR-001", "Organisation not registered!");

	public static BusinessException EX_TCH_001 = new BusinessException("TCH-001", "Unknown technical exception!");

	private String errorCode;
	private String errorMessage;
	private Throwable errorCause;
	
	public BusinessException(String errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public BusinessException(String errorCode, String errorMessage, Throwable cause) {
		super(errorMessage, cause);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
	    return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public static void throwBusinessException(BusinessException businessException, Throwable cause) throws BusinessException {
		logException(businessException, cause);
		businessException.setErrorCause(cause);
		throw businessException;
	}

	public static void throwBusinessException(BusinessException businessException) throws BusinessException {
		logException(businessException);
		throw businessException;
	}
	
	public static void logException(BusinessException businessException, Throwable cause) {
		log.error("Exception Code: " + businessException.getErrorCode() + "; Message: " + businessException.getErrorMessage() + "; cause: " + cause.getMessage() + ".");
	}
	
	public static void logException(BusinessException businessException) {
		log.error("Exception Code: " + businessException.getErrorCode() + "; Message: " + businessException.getErrorMessage() + ".");
	}

	public Throwable getErrorCause() {
		return errorCause;
	}

	public void setErrorCause(Throwable errorCause) {
		this.errorCause = errorCause;
	}
	
}
