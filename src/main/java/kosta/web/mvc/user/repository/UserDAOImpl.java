package kosta.web.mvc.user.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kosta.web.mvc.user.dto.UserDTO;

@Repository
public class UserDAOImpl implements UserDAO {

	@Autowired
	private SqlSession session;
	
	@Override
	public UserDTO loginCheck(UserDTO userDTO) {
		UserMapper mapper = session.getMapper(UserMapper.class);
		return mapper.loginCheck(userDTO);
	}

}
