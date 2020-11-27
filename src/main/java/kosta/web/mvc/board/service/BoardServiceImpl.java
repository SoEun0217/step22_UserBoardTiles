package kosta.web.mvc.board.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kosta.web.mvc.board.dto.ElectronicsDTO;
import kosta.web.mvc.board.repository.BoardDAO;


@Service
@Transactional
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDAO boardDAO;
	
	@Override
	public List<ElectronicsDTO> selectAll() {
		return boardDAO.selectAll();
	}

	@Override
	public ElectronicsDTO selectByModelNum(String modelNum, boolean state) {
		ElectronicsDTO dto = null;
		if(state) {
			boardDAO.readnumUpdate(modelNum);
			dto = boardDAO.selectByModelNum(modelNum);
		}else {
			dto = boardDAO.selectByModelNum(modelNum);
		}
		return dto;
	}

	@Override
	public int insert(ElectronicsDTO electronics) {
		int result = boardDAO.insert(electronics);
		if(result==0)throw new RuntimeException("정상적으로 등록되지 않았습니다.");
		return result;
	}

	@Override
	public int delete(String modelNum, String password,String savePath) {
		//비밀번호 체크
		ElectronicsDTO dbElec = boardDAO.selectByModelNum(modelNum);
		if(!dbElec.getPassword().equals(password.trim())) {
			throw new RuntimeException("비밀번호가 일치하지않습니다.");
		}
		int result = boardDAO.delete(modelNum, password);
		if(result == 0)throw new RuntimeException("정상적으로 삭제되지 않았습니다.");
		//삭제 완료후...파일이 있는 경우의 레코드라면 폴더에서 파일도 삭제한다.
		if(dbElec.getFname()!=null) {
			File file = new File(savePath+"/"+dbElec.getFname());
			file.delete();
		}
		return result;
	}

	@Override
	public int update(ElectronicsDTO electronics) {
		int result = boardDAO.update(electronics);
		if(result == 0 )throw new RuntimeException("정상적으로 수정되지 않았습니다");
		return result;
	}

}
