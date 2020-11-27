package kosta.web.mvc.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kosta.web.mvc.exception.AuthenticationException;
import kosta.web.mvc.user.dto.UserDTO;
import kosta.web.mvc.user.repository.UserDAO;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDAO userDAO;
	
	@Override
	public UserDTO loginCheck(UserDTO userDTO) {
		UserDTO dbDTO = userDAO.loginCheck(userDTO);
		if(dbDTO==null) throw new AuthenticationException("정보가 일치하지 않습니다.");
		return dbDTO;
	}

}
