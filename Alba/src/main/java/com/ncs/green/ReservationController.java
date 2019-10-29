package com.ncs.green;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import business.REService;
import vo.KingVO;
import vo.PartimerVO;
import vo.ReservationVO;

@Controller
public class ReservationController {

	  @Autowired
	   @Qualifier("reservation")
	   private REService reservice;	 
	  
	  //���� �߰�
	  @RequestMapping(value="/reinsert")
	  @ResponseBody
	    public ModelAndView aopreinsert(HttpServletRequest request, ModelAndView mv, ReservationVO vo) {
	    	
		  int cnt = reservice.check_reservation(vo);
		  int cnt2 = reservice.check_reservation2(vo);
		  if(cnt>0) {
			  mv.addObject("fCode","REF");
		  }else if(cnt2>0) {
			  mv.addObject("fCode","REF2");
		  }
		  else{
			  int cnt3 = reservice.insertReservation(vo);
			  if(cnt3>0) {
				  mv.addObject("fCode", "RES");
			  } else {
				  mv.addObject("fCode", "REF");
			  }
		  }
		 	
	    	mv.setViewName("jsonView");
	   	
	   		return mv; 
	   	} //reserveInsert
	  
	//���� �󼼺���
	   @RequestMapping(value="/redetail")
	   public ModelAndView redetail(HttpServletRequest request, HttpServletResponse response, ModelAndView mv, ReservationVO vo) {

	      vo = reservice.selectOne(vo);
	      response.setContentType("text/html; charset=UTF-8");
	      mv.addObject("reDetail", vo);
	      System.out.println("����󼼺���"+vo);
	      mv.setViewName("jsonView");
	         

	      return mv; 
	   } //reserveInsert
	  
	  
	  //���� 
	    @RequestMapping(value="/relist")
	   	public ModelAndView relist(HttpServletRequest request, ModelAndView mv, ReservationVO vo) {
	 
	        String bname=request.getParameter("bname");
	        
	        //trim: String ���� ����ִ� ���� ���� ����
	        String ano1 = request.getParameter("ano").trim(); 
	        int ano = Integer.parseInt(ano1);
	
	          
	        System.out.println("ano="+ano);

	
            ArrayList<ReservationVO> relist = reservice.reservationList(vo);
            int size = reservice.listCount();
            mv.addObject("size",size);
            mv.addObject("Bname",bname);
            mv.addObject("Ano",ano);
            mv.addObject("reservecalendar",relist);
            System.out.println("���ฮ��Ʈ"+relist);
            mv.setViewName("reservation/reserveForm");
            
	   		return mv; 
	   	} //relist
	    
	    @RequestMapping(value="/krelist")
	   	public ModelAndView krelist(HttpServletRequest request, ModelAndView mv, ReservationVO vo) {
	    	int kno = 0;
	    	HttpSession session = request.getSession(false);
			if (session != null) {
				kno = (Integer) session.getAttribute("Kno");
			} else
				System.out.println("**** session �� null ****");

			vo.setKno(kno);
	        String bname=request.getParameter("bname");

            ArrayList<ReservationVO> relist = reservice.reservationList(vo);
            int size = reservice.listCount();
            mv.addObject("size",size);
            mv.addObject("Bname",bname);
            mv.addObject("reservecalendar",relist);
            mv.setViewName("reservation/kreserveForm");
            
	   		return mv; 
	   	} //relist
	    
	  //���� ����
	    @RequestMapping(value="/redelete")
	    @ResponseBody
	    public ModelAndView aopredelete(ModelAndView mv, ReservationVO vo) {
	       
	       int cnt=0;
	       cnt=reservice.deleteReservation(vo);

	       if(cnt>0) {
	    	   mv.addObject("fCode", "DES");
	       } else {
	    	   mv.addObject("fCode", "DEF");
	       }
		   mv.setViewName("jsonView"); 
	       return mv;
	    } // reserveDelete
	    
	   //���� ����(������)
	   @RequestMapping(value="/selectApproval")
	   @ResponseBody
	   public ModelAndView aopselectApproval(ModelAndView mv, ReservationVO vo) {
	       
	      int cnt=0;
	      cnt=reservice.selectApproval(vo);

	      if(cnt>0) {
	   	   mv.addObject("fCode", "UAS");
	      } else {
	   	   
	      }
		  mv.setViewName("jsonView"); 
	      return mv;
	   } //selectapproval
	   
	   //���� �ź�(������)
	   @RequestMapping(value="/selectRefuse")
	   @ResponseBody
	   public ModelAndView aopselectRefuse(ModelAndView mv, ReservationVO vo) {
	       
	      int cnt=0;
	      cnt=reservice.selectRefuse(vo);

	      if(cnt>0) {
	   	   mv.addObject("fCode", "URS");
	      } else {
	   	   
	      }
		  mv.setViewName("jsonView"); 
	      return mv;
	   } //selectapproval
}
