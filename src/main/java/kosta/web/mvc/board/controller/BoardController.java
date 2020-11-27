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
@RequestMapping("/board")//board밑에 요청은 다 이리로 와라 명시
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	private final String savePath = "C:\\Edu\\Spring\\fileSave";
	
	/**
	 * 전체보기
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
	 * 상세보기
	 * */
	@RequestMapping("/detail/{modelNum}")
	public ModelAndView selectByModelNum(@PathVariable String modelNum) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("elec", boardService.selectByModelNum(modelNum, true));
		mv.setViewName("board/read");
		return mv;
	}
	
	/**
	 * 다운로드하기
	 * */
	@RequestMapping("/down")
	public ModelAndView down(String fname) {
		return new ModelAndView("downLoadView","fname",new File(savePath+"/"+fname));
	}
	
	/**
	 * 업데이트폼
	 * */
	@RequestMapping("/update/{modelNum}")
	public ModelAndView updateForm(@PathVariable String modelNum) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("elec",boardService.selectByModelNum(modelNum, false));
		mv.setViewName("board/update");
		return mv;
	}
	
	
	/**
	 * 업데이트하기
	 * */
	@RequestMapping("/realUpdate")
	public ModelAndView update(ElectronicsDTO dto) {
		boardService.update(dto);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("board/read");
		mv.addObject("elec",boardService.selectByModelNum(dto.getModelNum(), false));//조회수 증가되면 안된다.
		return mv;
	}
	
	
	/**
	 * 입력폼띄우기
	 * */
	@RequestMapping("/write")
	public void insertForm() {}
	
	/**
	 * 글등록
	 * */
	@RequestMapping("/insert")
	public String insert(ElectronicsDTO elecDTO)throws IOException {
		//파일이 첨부되었다면 저장
		MultipartFile file = elecDTO.getFile();
		if(file.getSize()>0) {
			String fileName = file.getOriginalFilename();
			long fileSize = file.getSize();
			elecDTO.setFname(fileName);
			elecDTO.setFsize(fileSize);
			
			file.transferTo(new File(savePath+"/"+fileName));
		}
		
		boardService.insert(elecDTO);
		
		return "redirect:/board/list"; //"redirect:board/list"이렇게 /를 빼면 상대경로가 되어 board/baord/list가 됨
	}
	

	/**
	 * 삭제하기
	 * */
	@RequestMapping("/delete")
	public String delete(String modelNum,String password) {
		boardService.delete(modelNum, password,savePath);
		return "redirect:/board/list";
	}
	
}
