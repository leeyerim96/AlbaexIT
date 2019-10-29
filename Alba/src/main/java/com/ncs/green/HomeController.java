package com.ncs.green;


import java.util.ArrayList;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import business.KService;
import business.NService;
import business.PService;
import vo.KingVO;
import vo.NoticeVO;
import vo.PartimerVO;
import vo.ResumeVO;


@Controller
public class HomeController {
	
	@Autowired
	@Qualifier("partimer")
	private PService pservice ;
	
	@Autowired
	@Qualifier("king")
	private KService kservice;
	
    @Autowired
    @Qualifier("notice")
    private NService nservice;
    
 // ��ȣȭ Encryption, ��ȣȭ Decryption
    @Autowired
    BCryptPasswordEncoder passwordEncoder ;
	
	@RequestMapping(value = {"/","/home"}, method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		
		return "home";
	}
	
    @RequestMapping(value="/logout")
	public ModelAndView logout(HttpServletRequest request, ModelAndView mv) {
    	request.getSession().invalidate();
	    mv.setViewName("home");
	    return mv;
	} // logout
	
    @RequestMapping(value="/about")
   	public ModelAndView about(ModelAndView mv) {
   	    mv.setViewName("aboutForm");
   	    return mv;
   	}
    
    //�α���
    @RequestMapping(value="/ploginf")
	public ModelAndView ploginf(ModelAndView mv) { 
	      mv.setViewName("partimer/ploginForm");
	      return mv ;
	}
	   
	@RequestMapping(value="/kloginf")
	public ModelAndView kloginf(ModelAndView mv) {	    
	      mv.setViewName("king/kloginForm");
	      return mv ;
	}
	   
    @RequestMapping(value="/mainf")
    public ModelAndView mainf(HttpServletRequest request, ModelAndView mv) {
        String kid = null;
        String pid = null;

       HttpSession session = request.getSession(false);
       if (session != null) {
          kid = (String) session.getAttribute("kloginID");
          pid = (String) session.getAttribute("ploginID");
    
          ArrayList<NoticeVO> nlist = nservice.selectList2();
          mv.addObject("Apple",nlist);
          mv.setViewName("mainForm");
            
          if(kid!=null) {
              mv.setViewName("layout3/mainForm");
          }else if(pid!=null) {
             mv.setViewName("layout2/mainForm");
       } 
          
       }
        
         return mv ;
    }
    
    @RequestMapping(value="/plogin")
    public ModelAndView plogin(HttpServletRequest request,
                ModelAndView mv, PartimerVO vo, NoticeVO vo2, ResumeVO vo3) {
       String id = vo.getPid();
       String password = vo.getPpw();
       
       HttpSession session = request.getSession();
       if (id.equals("admin") && password.equals("yerim96!")) {
          session.setAttribute("loginID", id);
          mv.setViewName("layout4/adminForm");
          return mv ;  
       }
       
       vo = pservice.selectOne(vo);
       
       if (vo != null) {
          String encPassword = vo.getPpw(); // DB�� ����� ��ȣȭ�� password.
          // passwordEncoder.matches(����ڰ� �Է��� password,��ȣȭ�� password ) 
          // �Է��� �н������ DB�� ����� �н����带 ���ϰ�, ���ٸ� true Ʋ���� false�� ����.
          //System.out.println("pw =="+password+" , enc="+encPassword);
          boolean passwordMatch = passwordEncoder.matches(password,encPassword);         
          
           if(vo.getApproval_status().equals("true")) {
              if (passwordMatch) {
                 session.setAttribute("ploginID", id);
                 session.setAttribute("ploginPW", password);
                 session.setAttribute("Pname", vo.getPname());
                 session.setAttribute("Phone", vo.getPhone());
                 session.setAttribute("Pno", vo.getPno());    
                 session.setAttribute("Kno", vo2.getKno());
                 session.setAttribute("Bno", vo2.getBno());
                 ArrayList<NoticeVO> nlist = nservice.selectList2();
                  mv.addObject("Apple",nlist);
              
                // BCryptPasswordEncoder �� ��ȣȭ �Ǹ� ��ȣȭ�� �Ұ�����.
                // �׷��� mDetail ȭ�鿡�� ��ȣȭ �Ǳ����� Low password�� ��� �ϱ� ���� ������.   
                mv.setViewName("layout2/mainForm");
             }else {
                mv.addObject("fail","pwError") ;
                mv.setViewName("layout/partimer/ploginForm");
             }             
              //  mv.setViewName("layout2/mainForm");
           }else if(vo.getApproval_status().equals("false")) {
               mv.addObject("fail1","true");
                mv.setViewName("layout/partimer/ploginForm");
           }
        } else {
           mv.addObject("fail","idError");
            mv.setViewName("layout/partimer/ploginForm");
        }
         return mv; 
     } // plogin
	
    @RequestMapping(value = "/klogin")
    public ModelAndView klogin(HttpServletRequest request, ModelAndView mv, KingVO vo, NoticeVO vo2) {
       String id = vo.getKid();
       String password = vo.getKpw();
       
       HttpSession session = request.getSession();
       if (id.equals("admin") && password.equals("yerim96!")) {
          session.setAttribute("loginID", id);
          mv.setViewName("layout4/adminForm");
          return mv;
       }

       vo = kservice.selectOne(vo);

       if (vo != null) {
            String encPassword = vo.getKpw(); // DB�� ����� ��ȣȭ�� password.
                // passwordEncoder.matches(����ڰ� �Է��� password,��ȣȭ�� password ) 
                // �Է��� �н������ DB�� ����� �н����带 ���ϰ�, ���ٸ� true Ʋ���� false�� ����.
                System.out.println("pw =="+password+" , enc="+encPassword);
                boolean passwordMatch = passwordEncoder.matches(password,encPassword);   
                
          if (vo.getApproval_status().equals("true")) {
              if (passwordMatch) {
             session.setAttribute("kloginID", id);
               session.setAttribute("kloginPW", password);
             session.setAttribute("Kno", vo.getKno());
             ArrayList<NoticeVO> nlist = nservice.selectList2();
             mv.addObject("Apple", nlist);
             mv.setViewName("layout3/mainForm");
              } else {
                   mv.addObject("fail", "pwError");
                   mv.setViewName("layout/king/kloginForm");
                }            
             } else if (vo.getApproval_status().equals("false")) {
                mv.addObject("fail1", "true");
                mv.setViewName("layout/king/kloginForm");
             }
          } else {
             mv.addObject("fail", "idError");
             mv.setViewName("layout/king/kloginForm");
          }
          return mv;
       } // klogin
	
    //ȸ������
	@RequestMapping(value="/pjoinf")
	public ModelAndView pjoinf(ModelAndView mv) {
		mv.setViewName("layout/partimer/pjoinForm");
		return mv ;
	}
	
	@RequestMapping(value="/pjoin", method = RequestMethod.POST)
	public ModelAndView aopinsertPartimer(ModelAndView mv, @ModelAttribute PartimerVO vo, HttpServletResponse response) 
				throws Exception {
	
		System.out.println("pJoin Test =>"+vo);
		
		String encPassword = passwordEncoder.encode(vo.getPpw());
		vo.setPpw(encPassword);
	
		int cnt = pservice.insertPartimer(vo, response);	
		
		if (cnt > 0) {
			mv.addObject("pjoinID", vo.getPid());
			mv.addObject("fCode", "PJS");
			mv.setViewName("layout/afterjoin");
		} else {
			mv.addObject("fCode", "JF");
			mv.setViewName("partimer/doFinish");
		} 
		return mv;	
	} // pjoin
	
	@RequestMapping(value="/agreef")
	public ModelAndView agreef(ModelAndView mv) {
		mv.setViewName("layout/partimer/provision");
		return mv ;
	}
	
	@RequestMapping(value="/kjoinf")
	public ModelAndView kjoinf(ModelAndView mv) {
	     mv.setViewName("layout/king/kjoinForm");
	     return mv ;
	}
	


	   @RequestMapping(value = "/kjoin", method = RequestMethod.POST)
	   public ModelAndView aopinsertKing(ModelAndView mv, @ModelAttribute KingVO vo, HttpServletResponse response)
	         throws Exception {
	      System.out.println("kJoin Test =>" + vo);

	      // Password ���ڵ�
	      String encPassword = passwordEncoder.encode(vo.getKpw());
	      vo.setKpw(encPassword);
	      int cnt = kservice.insertKing(vo, response);

	      if (cnt > 0) {
	         mv.addObject("kjoinID", vo.getKid());
	         mv.addObject("fCode", "KJS");
	         mv.setViewName("layout/afterjoin");
	      } else {
	         mv.addObject("fCode", "JF");
	         mv.setViewName("king/doFinish");
	      }
	      return mv;
	   } // kjoin

	@RequestMapping(value="/kagreef")
	public ModelAndView kagreef(ModelAndView mv) {
		mv.setViewName("layout/king/provision");
		return mv ;
	}
	
	// ���̵� �ߺ�üũ
	@RequestMapping(value="/pidcheck")
	public ModelAndView idcheck(ModelAndView mv, PartimerVO vo) {
		mv.addObject("pid",vo.getPid()) ;
		
		vo=pservice.selectOne(vo);
		
		if (vo!=null) { // ��� �Ұ���
			mv.addObject("idCheck","F");
		}else { // ��밡��
			mv.addObject("idCheck","T");
		}
		mv.setViewName("partimer/pidCheck");
		return mv;
	} // idcheck
	
	@RequestMapping(value="/kidcheck")
	public ModelAndView kidcheck(ModelAndView mv, KingVO vo) {
		mv.addObject("kid",vo.getKid()) ;
		
		vo=kservice.selectOne(vo);
		
		if (vo!=null) { // ��� �Ұ���
			mv.addObject("idCheck","F");
		}else { // ��밡��
			mv.addObject("idCheck","T");
		}
		mv.setViewName("king/kidCheck");
		return mv;
	} // kidcheck
	
	//���̵� ã��
	@RequestMapping(value = "/find_pid_form")
	public String find_id_form() throws Exception{
		return "layout/partimer/find_pid_form";
	}

	@RequestMapping(value = "/find_pid", method = RequestMethod.POST)
	public String find_pid(HttpServletResponse response, @RequestParam("email") String email, Model md) throws Exception{
		md.addAttribute("pid", pservice.find_pid(response, email));
		return "layout/partimer/find_pid";
	}
	
	@RequestMapping(value = "/find_kid_form")
	public String find_kid_form() throws Exception{
	    return "layout/king/find_kid_form";
	}

	@RequestMapping(value = "/find_kid", method = RequestMethod.POST)
	public String find_kid(HttpServletResponse response, @RequestParam("email") String email, Model md) throws Exception{
	    md.addAttribute("kid", pservice.find_pid(response, email));
	    return "layout/king/find_kid";
	}

	//��й�ȣ ã��
	@RequestMapping(value = "/find_ppw_form")
	public String find_ppw_form() throws Exception{
		return "layout/partimer/find_ppw_form";
	}

	@RequestMapping(value = "/find_ppw", method = RequestMethod.POST)
	public void find_ppw(@ModelAttribute PartimerVO partimer, HttpServletResponse response) throws Exception{		
		pservice.find_ppw(response, partimer);
	}

	@RequestMapping(value = "/find_kpw_form")
	public String find_kpw_form() throws Exception{
	    return "layout/king/find_kpw_form";
	}

	@RequestMapping(value = "/find_kpw", method = RequestMethod.POST)
	public void find_kpw(@ModelAttribute KingVO king, HttpServletResponse response) throws Exception{
		kservice.find_kpw(response, king);
	}

	// �̸��� �ߺ� Ȯ��
	@RequestMapping(value = "/check_email", method = RequestMethod.POST)
	public void check_email(@RequestParam("email") String email, HttpServletResponse response) throws Exception{
		pservice.check_email(email, response);
	}
		
	@RequestMapping(value = "/kcheck_email", method = RequestMethod.POST)
	public void kcheck_email(@RequestParam("email") String email, HttpServletResponse response) throws Exception{
		kservice.kcheck_email(email, response);
	}
		
	// ȸ�� ��������
	@RequestMapping(value = "/approval_partimer", method = RequestMethod.POST)
	public void approval_partiemr(@ModelAttribute PartimerVO vo, HttpServletResponse response) throws Exception{
		pservice.approval_partimer(vo, response);
	}

	@RequestMapping(value = "/approval_king", method = RequestMethod.POST)
	public void aopapproval_king(@ModelAttribute KingVO vo, HttpServletResponse response) throws Exception{
		kservice.approval_king(vo, response);
	}
	
}
