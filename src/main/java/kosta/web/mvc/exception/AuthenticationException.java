package kosta.web.mvc.exception;

/**
 * ����(�α���)�� �������� ��
 * */
public class AuthenticationException extends RuntimeException {
	
	public AuthenticationException() {}
	public AuthenticationException(String message) {
		super(message);
	}
}
