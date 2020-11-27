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
		if(result==0)throw new RuntimeException("���������� ��ϵ��� �ʾҽ��ϴ�.");
		return result;
	}

	@Override
	public int delete(String modelNum, String password,String savePath) {
		//��й�ȣ üũ
		ElectronicsDTO dbElec = boardDAO.selectByModelNum(modelNum);
		if(!dbElec.getPassword().equals(password.trim())) {
			throw new RuntimeException("��й�ȣ�� ��ġ�����ʽ��ϴ�.");
		}
		int result = boardDAO.delete(modelNum, password);
		if(result == 0)throw new RuntimeException("���������� �������� �ʾҽ��ϴ�.");
		//���� �Ϸ���...������ �ִ� ����� ���ڵ��� �������� ���ϵ� �����Ѵ�.
		if(dbElec.getFname()!=null) {
			File file = new File(savePath+"/"+dbElec.getFname());
			file.delete();
		}
		return result;
	}

	@Override
	public int update(ElectronicsDTO electronics) {
		int result = boardDAO.update(electronics);
		if(result == 0 )throw new RuntimeException("���������� �������� �ʾҽ��ϴ�");
		return result;
	}

}
