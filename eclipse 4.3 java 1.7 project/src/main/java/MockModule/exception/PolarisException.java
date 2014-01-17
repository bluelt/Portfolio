package MockModule.exception;

import MockModule.common.ResultEnum;


public class PolarisException extends RuntimeException {

	ResultEnum result;
	private static final long serialVersionUID = -1985883349112106943L;

	public PolarisException() {
	}
	
	public PolarisException( ResultEnum result ) {
		this.result = result;
	}

	public PolarisException( Throwable cause, ResultEnum result ) {
		super( cause );
		this.result = result;
	}

}
