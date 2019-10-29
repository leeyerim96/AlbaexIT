package com.ncs.green;


import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import business.PService;
import vo.PageVO;
import vo.PartimerVO;

@Controller
public class PartimerController {

	@Autowired
	@Qualifier("partimer")
	private PService pservice ;
	
	// ��ȣȭ Encryption, ��ȣȭ Decryption
    @Autowired
    BCryptPasswordEncoder passwordEncoder ;
	
	// �����ڷ� ������ ��Ϻ���
	@RequestMapping(value="/plist")
	public ModelAndView plist(HttpServletRequest request, ModelAndView mv, PartimerVO vo, PageVO pvo) {  
		int currPage = 1;
		if (pvo.getCurrPage() > 1)
			currPage = pvo.getCurrPage();
		else pvo.setCurrPage(currPage);

		pvo.setTotalCount(pservice.totalRowCount());
		System.out.println("plist Test =>"+pvo);

		int totalPage = pvo.getTotalCount() / pvo.getPerPage();
		if (pvo.getTotalCount() % pvo.getPerPage() != 0)
			totalPage++;

		int sPage = 1, ePage = 1;
		sPage = ((currPage - 1) / pvo.getPerPageNo()) * pvo.getPerPageNo() + 1;
		ePage = sPage + pvo.getPerPageNo() - 1;

		int sno = ((currPage - 1)* pvo.getPerPage());

		pvo.setSno(sno);

		ArrayList<PartimerVO> plist = pservice.selectList();

		mv.addObject("Apple", plist);
		mv.addObject("totalPage", totalPage);
		mv.addObject("currPage", currPage);
		mv.addObject("perPageNo", pvo.getPerPageNo());
		mv.addObject("sPage", sPage);
		mv.addObject("ePage", ePage);

		HttpSession session = request.getSession(false);
		if (session != null) {

		} else
			System.out.println("**** session �� null ****");

		mv.addObject("Apple",plist);
		mv.setViewName("layout4/partimer/partimerList");
		return mv; 
	}

	// ������ �󼼺���
	   @RequestMapping(value="/pdetail")
	   public ModelAndView pdetail(HttpServletRequest request,
	         ModelAndView mv, PartimerVO vo) {

	      String pid = null;
	      String id= null;
	      String password = null; 
	      HttpSession session = request.getSession(false);
	      if (session != null) {
	         pid = (String) session.getAttribute("ploginID");
	         id  = (String) session.getAttribute("loginID");
	         password = (String) session.getAttribute("ploginPW"); 

	         if (pid != null || id !=null) {
	            if(pid != null) {
	               vo.setPid(pid);
	            }
	            vo = pservice.selectOne(vo);
	            vo.setPpw(password);
	            mv.addObject("Apple", vo);
	         } else
	            System.out.println("**** loginID �� null ****");
	      } else
	         System.out.println("**** session �� null ****");

	      if (vo != null && pid != null) {
	         mv.setViewName("layout2/partimer/partimerDetail");
	      }else if(id!=null){
	         mv.setViewName("layout4/partimer/partimerDetail");
	      }

	      else { 
	         mv.addObject("fCode", "D");
	         mv.setViewName("partimer/doFinish");
	      }
	      return mv; 
	   } // pdetail

	   // ���� ����
	   @RequestMapping(value="/pupdate")
	   public ModelAndView aoppupdate(HttpServletRequest request, ModelAndView mv, PartimerVO vo) 
	         throws ServletException, IOException {
	      String pid = null;
	      String id= null;
	      HttpSession session = request.getSession(false);
	      if (session != null) {
	         pid = (String) session.getAttribute("ploginID");
	         id  = (String) session.getAttribute("loginID");
	      }
	      // Password ���ڵ�
	      String encPassword = passwordEncoder.encode(vo.getPpw());
	      vo.setPpw(encPassword);
	      int cnt =  pservice.update(vo);

	      if (cnt > 0) {
	         if(pid!=null) {
	            mv.setViewName("redirect:/mainf");
	         }else if(id!=null) {
	            mv.setViewName("redirect:/plist");
	         }
	      } else {
	         mv.addObject("fCode", "U");
	         mv.setViewName("partimer/doFinish");
	      } // else
	      return mv;
	   } // pupdate



	// ȸ�� Ż��
	@RequestMapping(value="/pdelete")
	public ModelAndView aoppdelete(HttpServletRequest request,
			ModelAndView mv, PartimerVO vo) {
		String pid = null;
		String id = null;
		int cnt = 0;
		HttpSession session = request.getSession(false);
		if (session != null) {
			pid = (String) session.getAttribute("ploginID");
			id = (String) session.getAttribute("loginID");
			if (pid != null || id != null) {
				cnt = pservice.delete(vo);
				mv.addObject("Apple", vo);
			} else
				System.out.println("**** loginID �� null ****");
		} else
			System.out.println("**** session �� null ****");

		if (cnt > 0) {
			if(pid!=null)
				session.invalidate();
			mv.setViewName("home");
		} else if(id!=null){
			mv.addObject("fCode", "DS");
			mv.setViewName("redirect:/plist");
		}
		else 
		{ 
			mv.addObject("fCode", "DF");
		}
		return mv;
	} // pdelete


	// �˻�
	@RequestMapping(value="/searchpartimer")
	public ModelAndView searchpartimer(HttpServletRequest request, ModelAndView mv, PageVO pvo, PartimerVO vo) {

		int currPage = 1;
		if (pvo.getCurrPage() > 1)
			currPage = pvo.getCurrPage();
		else pvo.setCurrPage(currPage);

		pvo.setTotalCount(pservice.totalRowCount());
		int totalPage = pvo.getTotalCount() / pvo.getPerPage();
		if (pvo.getTotalCount() % pvo.getPerPage() != 0)
			totalPage++;

		int sPage = 1, ePage = 1;

		sPage = ((currPage - 1) / pvo.getPerPageNo()) * pvo.getPerPageNo() + 1;
		ePage = sPage + pvo.getPerPageNo() - 1;

		int sno = ((currPage - 1)* pvo.getPerPage());
		pvo.setSno(sno);

		ArrayList<PartimerVO> searchpartimer = pservice.search(pvo);


		mv.addObject("Apple", searchpartimer);
		mv.addObject("totalPage", totalPage);
		mv.addObject("currPage", currPage);
		mv.addObject("perPageNo", pvo.getPerPageNo());
		mv.addObject("sPage", sPage);
		mv.addObject("ePage", ePage);
		mv.setViewName("partimer/partimerSearch");

		return mv; 
	}// searchpartimer
}