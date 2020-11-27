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
	 * 로그인 폼 띄우기
	 * */
	@RequestMapping("/login")
	public String login() {
		return "user/login";
	}
	
	/**
	 * 로그인하기
	 * */
	@RequestMapping("/loginCheck")
	public String loginCheck(UserDTO userDTO,HttpSession session) {
		//서비스 호출
		UserDTO dbDTO = userService.loginCheck(userDTO);
		//성공하면 세션에 정보를 저장한다.(${loginUser} / ${loginName})
		session.setAttribute("loginUser", dbDTO.getUserId());//아이디
		session.setAttribute("loginName", dbDTO.getName());//이름
		return "redirect:/";//다시 index로 보낸다.
	}
	
	/**
	 * 로그아웃하기
	 * */
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";//만약에 redirect를 안하고 그냥 / 로하면 WEB-INF/views//라는게 있다는 의미가 됨!
	}
	
	@ExceptionHandler(AuthenticationException.class)
	public ModelAndView error(AuthenticationException e) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("error/errorMessage");
		mv.addObject("errorMsg",e.getMessage());
		return mv;
	}
}
