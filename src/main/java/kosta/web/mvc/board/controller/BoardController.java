package kosta.web.mvc.board.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import kosta.web.mvc.board.dto.ElectronicsDTO;
import kosta.web.mvc.board.service.BoardService;

@Controller
@RequestMapping("/board")//board�ؿ� ��û�� �� �̸��� �Ͷ� ���
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	private final String savePath = "C:\\Edu\\Spring\\fileSave";
	
	/**
	 * ��ü����
	 * */
	@RequestMapping("/list")
	public ModelAndView selectAll(){
		List<ElectronicsDTO>list = boardService.selectAll();
		ModelAndView mv = new ModelAndView();
		mv.setViewName("board/list");
		mv.addObject("list", list);
		return mv;
	}
	
	/**
	 * �󼼺���
	 * */
	@RequestMapping("/detail/{modelNum}")
	public ModelAndView selectByModelNum(@PathVariable String modelNum) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("elec", boardService.selectByModelNum(modelNum, true));
		mv.setViewName("board/read");
		return mv;
	}
	
	/**
	 * �ٿ�ε��ϱ�
	 * */
	@RequestMapping("/down")
	public ModelAndView down(String fname) {
		return new ModelAndView("downLoadView","fname",new File(savePath+"/"+fname));
	}
	
	/**
	 * ������Ʈ��
	 * */
	@RequestMapping("/update/{modelNum}")
	public ModelAndView updateForm(@PathVariable String modelNum) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("elec",boardService.selectByModelNum(modelNum, false));
		mv.setViewName("board/update");
		return mv;
	}
	
	
	/**
	 * ������Ʈ�ϱ�
	 * */
	@RequestMapping("/realUpdate")
	public ModelAndView update(ElectronicsDTO dto) {
		boardService.update(dto);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("board/read");
		mv.addObject("elec",boardService.selectByModelNum(dto.getModelNum(), false));//��ȸ�� �����Ǹ� �ȵȴ�.
		return mv;
	}
	
	
	/**
	 * �Է�������
	 * */
	@RequestMapping("/write")
	public void insertForm() {}
	
	/**
	 * �۵��
	 * */
	@RequestMapping("/insert")
	public String insert(ElectronicsDTO elecDTO)throws IOException {
		//������ ÷�εǾ��ٸ� ����
		MultipartFile file = elecDTO.getFile();
		if(file.getSize()>0) {
			String fileName = file.getOriginalFilename();
			long fileSize = file.getSize();
			elecDTO.setFname(fileName);
			elecDTO.setFsize(fileSize);
			
			file.transferTo(new File(savePath+"/"+fileName));
		}
		
		boardService.insert(elecDTO);
		
		return "redirect:/board/list"; //"redirect:board/list"�̷��� /�� ���� ����ΰ� �Ǿ� board/baord/list�� ��
	}
	

	/**
	 * �����ϱ�
	 * */
	@RequestMapping("/delete")
	public String delete(String modelNum,String password) {
		boardService.delete(modelNum, password,savePath);
		return "redirect:/board/list";
	}
	
}
