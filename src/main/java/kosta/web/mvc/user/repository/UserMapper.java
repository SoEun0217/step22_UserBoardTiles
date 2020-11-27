package kosta.web.mvc.user.repository;

import org.apache.ibatis.annotations.Select;

import kosta.web.mvc.user.dto.UserDTO;

public interface UserMapper {
	
	@Select("select user_id,pwd,name from member where user_id=#{userId} and pwd=#{pwd}")
	UserDTO loginCheck(UserDTO userDTO);
	
}
