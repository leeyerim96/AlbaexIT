package business;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.mail.HtmlEmail;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vo.PageVO;
import vo.PartimerVO;

@Service("partimer")
public class PServiceImpl implements PService {
   @Autowired
   private SqlSession dao ;
   private static final String NS ="green.mapper.PartimerMapper." ;
   
   @Autowired
   BCryptPasswordEncoder passwordEncoder ;
   
   @Override 
   public PartimerVO loginCheck(PartimerVO vo) {
      return dao.selectOne(NS+"loginPartimer", vo); 
   }
   
   public PartimerVO login(String pid) throws Exception{
	  return dao.selectOne(NS+"login", pid);
   }
   
   @Override
   public ArrayList<PartimerVO> selectList() {
	  return (ArrayList)dao.selectList(NS+"partimerlist");
   }
	
   @Override
  	public ArrayList<PartimerVO> pageList(PageVO pvo) {
  		return (ArrayList)dao.selectList(NS+"pageList",pvo);
   }
   
   @Override 
   public PartimerVO selectOne(PartimerVO vo) { 
	  return dao.selectOne(NS+"selectDetail", vo); 
   }	
	
   public int check_pid(String pid) throws Exception{
      return dao.selectOne(NS+"check_pid", pid);
   }
   
   @Override
   public int totalRowCount() {
	  return dao.selectOne(NS + "totalrowCount");
   }

   @Override
   public int insertPartimer(PartimerVO vo) throws Exception { 
	  return dao.insert(NS+"insertPartimer",vo) ; 
   } 
	
   @Override
   public int insertPartimer(PartimerVO vo, HttpServletResponse response) throws Exception {
	   response.setContentType("text/html;charset=utf-8");
	   PrintWriter out = response.getWriter();
	   
	   if(check_pid(vo.getPid()) == 1) {
		   out.println("<script>");
		   out.println("alert('������ ���̵� �ֽ��ϴ�.');");
		   out.println("history.go(-1);");
		   out.println("</script>");
		   out.close();
		   return 0;
	   }else if(check_email(vo.getEmail())==1) {
		   out.println("<script>");
		   out.println("alert('������ �̸����� �ֽ��ϴ�.');");
		   out.println("history.go(-1);");
		   out.println("</script>");
		   out.close();
		   return 0;
	   }else {
		   // ����Ű set
		   vo.setApproval_key(create_key());
		   insertPartimer(vo);
		   send_mail(vo, "join");
		   return 1;
	   }
   }
   
   @Override
   public int update(PartimerVO vo) {
      return dao.update(NS+"updatePartimer",vo) ;
   }  
   
   @Override
   public int delete(PartimerVO vo) {
      return dao.delete(NS+"deletePartimer",vo) ;
   } 
  
   @Override
   public ArrayList<PartimerVO> search(PageVO pvo) {
	  return (ArrayList)dao.selectList(NS + "searchList", pvo);
   }
   
   //�̸��� üũ
   public int check_email(String email) throws Exception {
		return dao.selectOne(NS+"check_email", email);
   }
   
   @Override
  	public void check_email(String email, HttpServletResponse response) throws Exception {
  		PrintWriter out = response.getWriter();
  		out.println(check_email(email));
  		out.close();
  	}
   
   //���̵� ã��
   @Override
   public String find_pid(String email)throws Exception{
      return dao.selectOne(NS+"find_pid", email);
   }
   
   @Override
   public String find_pid(HttpServletResponse response, String email) throws Exception {
      response.setContentType("text/html;charset=utf-8");
      PrintWriter out = response.getWriter();
      String pid = find_pid(email);
      
      if (pid == null) {
         out.println("<script>");
         out.println("alert('���Ե� ���̵� �����ϴ�.');");
         out.println("history.go(-1);");
         out.println("</script>");
         out.close();
         return null;
      } else {
         return pid;
      }
   }
  
   //��й�ȣ ã��   
   @Override
   public void find_ppw(HttpServletResponse response, PartimerVO vo) throws Exception {
      response.setContentType("text/html;charset=utf-8");
      PrintWriter out = response.getWriter();
         
      // ���̵� ������
      if(check_pid(vo.getPid()) == 0) {
         out.print("���̵� �����ϴ�.");
         out.close();
      }
      // ���Կ� ����� �̸����� �ƴϸ�
      else if(!vo.getEmail().equals(login(vo.getPid()).getEmail())) {
         out.print("�߸��� �̸��� �Դϴ�.");
         out.close();
      }else {
      // �ӽ� ��й�ȣ ����
         String pw = "";
         for (int i = 0; i < 12; i++) {
            pw += (char) ((Math.random() * 26) + 97);
         }
         vo.setPpw(pw);
        // ��й�ȣ ���� ���� �߼�   
         send_mail(vo, "find_ppw");
         String encPassword = passwordEncoder.encode(vo.getPpw());
 	     vo.setPpw(encPassword);
         // ��й�ȣ ����
         update_ppw(vo);
         
            
         out.print("�̸��Ϸ� �ӽ� ��й�ȣ�� �߼��Ͽ����ϴ�.");
         out.close();
      }
   }

   @Transactional
   public int update_ppw(PartimerVO vo) throws Exception{
      return dao.update(NS+"update_ppw", vo);
   }
   
   //ȸ�� ���� ����
   @Override
	public String create_key() throws Exception {
		String key = "";
		Random rd = new Random();

		for (int i = 0; i < 8; i++) {
			key += rd.nextInt(10);
		}
		return key;
	}
   
   // �̸��� �߼�
   @Override
   public void send_mail(PartimerVO vo, String div) throws Exception {
      // Mail Server ����
      String charSet = "utf-8";
      String hostSMTP = "smtp.naver.com";
      String hostSMTPid = "leeyerim96@naver.com";
      String hostSMTPpwd = "ehdanf12";

      // ������ ��� EMail, ����, ����
      String fromEmail = "leeyerim96@naver.com";
      String fromName = "Alba Exit";
      String subject = "";
      String msg = "";
      
		if(div.equals("join")) {
			// ȸ������ ���� ����
			subject ="Alba Exit ȸ������ ���� �����Դϴ�.";
			msg += "<div align='center' style='border:1px solid black; font-family:verdana'>";
			msg += "<h3 style='color: blue;'>";
			msg += vo.getPid() + "�� ȸ�������� ȯ���մϴ�.</h3>";
			msg += "<div style='font-size: 130%'>";
			msg += "�ϴ��� ���� ��ư Ŭ�� �� ���������� ȸ�������� �Ϸ�˴ϴ�.</div><br/>";
			msg += "<form method='post' action='http://192.168.0.22:9090/green/approval_partimer'>";
			msg += "<input type='hidden' name='email' value='" + vo.getEmail() + "'>";
			msg += "<input type='hidden' name='approval_key' value='" + vo.getApproval_key() + "'>";
			msg += "<input type='submit' value='����'></form><br/></div>";
		}
		else if(div.equals("find_ppw")) {
         subject = "AlbaExit �ӽ� ��й�ȣ �Դϴ�.";
         msg += "<div align='center' style='border:1px solid black; font-family:verdana'>";
         msg += "<h3 style='color: blue;'>";
         msg += vo.getPid() + "���� �ӽ� ��й�ȣ �Դϴ�. ��й�ȣ�� �����Ͽ� ����ϼ���.</h3>";
         msg += "<p>�ӽ� ��й�ȣ : ";
         msg += vo.getPpw() + "</p></div>";
      }
		
      // �޴� ��� E-Mail �ּ�
      String mail =vo.getEmail();
      try {
         HtmlEmail email = new HtmlEmail();
         email.setDebug(true);
         email.setCharset(charSet);
         email.setSSL(true);
         email.setHostName(hostSMTP);
         email.setSmtpPort(465);

         email.setAuthentication(hostSMTPid, hostSMTPpwd);
         email.setTLS(true);
         email.addTo(mail, charSet);
         email.setFrom(fromEmail, fromName, charSet);
         email.setSubject(subject);
         email.setHtmlMsg(msg);
         email.send();
      } catch (Exception e) {
         System.out.println("���Ϲ߼� ���� : " + e);
      }
   }
      
   @Transactional
   public int approval_partimer(PartimerVO vo) throws Exception{
  	  return dao.update(NS+"approval_partimer", vo);
   }
      
   @Override
   public void approval_partimer(PartimerVO vo, HttpServletResponse response) throws Exception {
  	  response.setContentType("text/html;charset=utf-8");
  	  PrintWriter out = response.getWriter();
  	  if (approval_partimer(vo) == 0) { // �̸��� ������ �����Ͽ��� ���
  		 out.println("<script>");
  		 out.println("alert('�߸��� �����Դϴ�.');");
  		 out.println("history.go(-1);");
  		 out.println("</script>");
  		 out.close();
  	  } else { // �̸��� ������ �����Ͽ��� ���
  		 out.println("<script>");
  		 out.println("alert('������ �Ϸ�Ǿ����ϴ�. �α��� �� �̿��ϼ���.');");
  		 out.println("location.href='home';");
  	   	 out.println("</script>");
  		 out.close();
  	  }
   }
}