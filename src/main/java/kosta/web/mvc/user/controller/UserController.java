package kosta.web.mvc.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kosta.web.mvc.exception.AuthenticationException;
import kosta.web.mvc.user.dto.UserDTO;
import kosta.web.mvc.user.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	/**
	 * �α��� �� ����
	 * */
	@RequestMapping("/login")
	public String login() {
		return "user/login";
	}
	
	/**
	 * �α����ϱ�
	 * */
	@RequestMapping("/loginCheck")
	public String loginCheck(UserDTO userDTO,HttpSession session) {
		//���� ȣ��
		UserDTO dbDTO = userService.loginCheck(userDTO);
		//�����ϸ� ���ǿ� ������ �����Ѵ�.(${loginUser} / ${loginName})
		session.setAttribute("loginUser", dbDTO.getUserId());//���̵�
		session.setAttribute("loginName", dbDTO.getName());//�̸�
		return "redirect:/";//�ٽ� index�� ������.
	}
	
	/**
	 * �α׾ƿ��ϱ�
	 * */
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";//���࿡ redirect�� ���ϰ� �׳� / ���ϸ� WEB-INF/views//��°� �ִٴ� �ǹ̰� ��!
	}
	
	@ExceptionHandler(AuthenticationException.class)
	public ModelAndView error(AuthenticationException e) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("error/errorMessage");
		mv.addObject("errorMsg",e.getMessage());
		return mv;
	}
}
