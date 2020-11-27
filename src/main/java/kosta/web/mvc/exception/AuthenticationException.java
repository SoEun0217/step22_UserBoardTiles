package kosta.web.mvc.exception;

/**
 * 인증(로그인)에 실패했을 때
 * */
public class AuthenticationException extends RuntimeException {
	
	public AuthenticationException() {}
	public AuthenticationException(String message) {
		super(message);
	}
}
