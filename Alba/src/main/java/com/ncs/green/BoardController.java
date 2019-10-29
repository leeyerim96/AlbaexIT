package com.ncs.green;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import business.BService;
import business.MService;
import vo.CpboardVO;
import vo.MentVO;
import vo.PageVO;
import vo.PartimerVO;

@Controller
public class BoardController {
	@Autowired
	private BService service;
	@Autowired
	private MService mservice;

	//�Խ��� ���
	@RequestMapping(value = "/blist")
	public ModelAndView blist(HttpServletRequest request, ModelAndView mv, PageVO pvo, PartimerVO vo, CpboardVO vo2) {
		String id = null;
		String pid=null;
		int currPage = 1;
		HttpSession session = request.getSession(false);
		if (session != null) {
			id = (String)session.getAttribute("loginID");
			pid = (String)session.getAttribute("ploginID");
		} else
			System.out.println("**** session �� null ****");

		if (pvo.getCurrPage() > 1)
			currPage = pvo.getCurrPage();
		else
			pvo.setCurrPage(currPage);

		pvo.setTotalCount(service.totalRowCount());
		System.out.println("totalcount" + pvo.getTotalCount());

		int totalPage = pvo.getTotalCount() / pvo.getPerPage();
		if (pvo.getTotalCount() % pvo.getPerPage() != 0)
			totalPage++;

		int sPage = 1, ePage = 1;
		sPage = ((currPage - 1) / pvo.getPerPageNo()) * pvo.getPerPageNo() + 1;
		ePage = sPage + pvo.getPerPageNo() - 1;

		int sno = ((currPage - 1) * pvo.getPerPage());
		pvo.setSno(sno);

		ArrayList<CpboardVO> blist = service.pageList(pvo);

		mv.addObject("hyejin", blist);
		mv.addObject("totalPage", totalPage);
		mv.addObject("currPage", currPage);
		mv.addObject("perPageNo", pvo.getPerPageNo());
		mv.addObject("sPage", sPage);
		mv.addObject("ePage", ePage);
		if(vo!=null && id!=null) {
			mv.setViewName("layout4/cpboard/pageList");	
		}else if(pid!=null){
			mv.setViewName("layout2/cpboard/pageList");
		}
		return mv; 
	} //blist
	
	//�Խñ� �󼼺���
	@RequestMapping(value = "/bdetail")
	public ModelAndView bdetail(HttpServletRequest request, ModelAndView mv, CpboardVO vo, PartimerVO vo2, MentVO vo3) {

		String pid = null;
		String id = null;

		HttpSession session = request.getSession(false);
		if (session != null) {
			session.setAttribute("Seq", vo.getSeq());
			pid = (String) session.getAttribute("ploginID");
			id = (String) session.getAttribute("loginID");

			if (vo.getPid() != null && (!vo.getPid().equals(pid) || id != null)) {
				if (service.countUp(vo) > 0) {
					System.out.println("**CountUp ����!!");
				} else {
					System.out.println("**CountUp ����!!");
				}
			}
		} else {
			System.out.println("** Controller bdeatil => session is null");
		}

		vo2.setPid(pid);
		
		vo = service.selectOne(vo);
		mv.addObject("hyejin", vo);
		
		ArrayList<MentVO> mlist = mservice.mentList();
		mv.addObject("yerim", mlist);
		
		if(vo!=null && id!=null) {
			mv.setViewName("layout4/cpboard/boardDetail");
		}else if(vo!=null && pid!=null){
			mv.setViewName("layout2/cpboard/boardDetail");
		}
		return mv; 
	} //bdetail
	
	@RequestMapping(value = "/binsertf")
	public ModelAndView binserf(ModelAndView mv, CpboardVO vo) {
		mv.setViewName("layout2/cpboard/binsertForm");
		return mv;
	} //binsertf

	//�� ����
	@RequestMapping(value = "/binsert")
	public ModelAndView aopbinsert(HttpServletRequest request, ModelAndView mv, CpboardVO vo) {
		int pno = 0;
		int cnt = 0;
		HttpSession session = request.getSession(false);
		if (session != null) {
			pno = (Integer) session.getAttribute("Pno");
		}
		vo.setPno(pno);
		
		cnt = service.insert(vo);
		
		if (cnt > 0) {
			mv.setViewName("redirect:blist");
		} else {
			mv.addObject("fCode", "I");
			mv.setViewName("layout2/cpboard/boardFinish");
		}

		return mv;
	} // binsert

	//�� ����
	@RequestMapping(value = "/bupdate")
	public ModelAndView aopbupdate(HttpServletRequest request, ModelAndView mv, CpboardVO vo)
			throws ServletException, IOException {
		String id = null;
		int cnt = 0;
		HttpSession session = request.getSession(false);
		if (session != null) {
			id = (String) session.getAttribute("loginID");
		}
			
		cnt = service.update(vo);
		if (cnt > 0) {
			if(id!=null) {
				mv.addObject("fCode", "BS");
				mv.setViewName("redirect:/blist"); }
			else {
				mv.setViewName("redirect:/blist");
			}
		} else {
			mv.addObject("fCode", "U");
			mv.setViewName("laylout2/cpboard/boardFinish");
			}
		return mv;
	   } // bupdate

	//�� ����
	@RequestMapping(value = "/bdelete")
	public ModelAndView aopbdelete(ModelAndView mv, CpboardVO vo) {
        int cnt = 0;
        
		cnt = service.delete(vo);
		
		if (cnt > 0) {
			mv.setViewName("redirect:blist");
		} else {
			mv.addObject("fCode", "D");
			mv.setViewName("layout2/cpboard/boardFinish");
		}
		return mv;
	} // bdelete

	// �Խ��� �˻�
	@RequestMapping(value = "/boardsearch")
	public ModelAndView boardsearch(HttpServletRequest request, ModelAndView mv, PageVO pvo, CpboardVO vo) {
        int currPage = 1;
		if (pvo.getCurrPage() > 1)
			currPage = pvo.getCurrPage();
		else
			pvo.setCurrPage(currPage);

		pvo.setTotalCount(service.totalRowCount());
		int totalPage = pvo.getTotalCount() / pvo.getPerPage();
		if (pvo.getTotalCount() % pvo.getPerPage() != 0)
			totalPage++;

		int sPage = 1, ePage = 1;
		sPage = ((currPage - 1) / pvo.getPerPageNo()) * pvo.getPerPageNo() + 1;
		ePage = sPage + pvo.getPerPageNo() - 1;

		int sno = ((currPage - 1) * pvo.getPerPage());
		pvo.setSno(sno);

		ArrayList<CpboardVO> searchlist = service.search(pvo);

		mv.addObject("hyejin", searchlist);
		mv.addObject("totalPage", totalPage);
		mv.addObject("currPage", currPage);
		mv.addObject("perPageNo", pvo.getPerPageNo());
		mv.addObject("sPage", sPage);
		mv.addObject("ePage", ePage);
		mv.setViewName("cpboard/boardSearch");
		return mv;
	} //boardsearch

}// class