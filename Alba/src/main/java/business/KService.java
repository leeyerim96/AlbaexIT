package business;

import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import vo.KingVO;
import vo.PageVO;

public interface KService {

   KingVO loginCheck(KingVO vo);
   KingVO login(String kid) throws Exception;
   ArrayList<KingVO> selectList();
   ArrayList<KingVO> pageList(PageVO pvo);
   KingVO selectOne(KingVO vo);
   int check_kid(String kid) throws Exception;
   int totalRowCount();
   int insertKing(KingVO vo) throws Exception;
   int insertKing(KingVO vo, HttpServletResponse response) throws Exception;
   int update(KingVO vo);
   int delete(KingVO vo);
   
   ArrayList<KingVO> search(PageVO pvo);
   
   //�̸��� üũ
   int kcheck_email(String email) throws Exception;
   void kcheck_email(String email, HttpServletResponse response) throws Exception;
   
   //���̵� ã��
   String find_kid(String email) throws Exception;
   String find_kid(HttpServletResponse response, String email) throws Exception;
  
   //��й�ȣ ã��
   void find_kpw(HttpServletResponse response, KingVO vo) throws Exception;
   int update_kpw(KingVO vo) throws Exception;

   //ȸ�� ���� ����
   String create_key() throws Exception;
   void send_mail(KingVO vo, String div) throws Exception;
   void approval_king(KingVO vo, HttpServletResponse response) throws Exception;

}