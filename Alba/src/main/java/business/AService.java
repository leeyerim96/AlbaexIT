package business;

import java.util.ArrayList;

import vo.ApplylistVO;
import vo.ReservationVO;

public interface AService {

   ArrayList<ApplylistVO> resultList();
   ArrayList<ApplylistVO> selectList();
   ArrayList<ApplylistVO> nselectList();
   
   ApplylistVO selectOne(ApplylistVO vo);
   
   int insert(ApplylistVO vo);
   int delete(ApplylistVO vo);
   
   // ���� ����
   int check_apply(ApplylistVO vo);
   
   //�հ�
   int selectPass(ApplylistVO vo);
   int selectFail(ApplylistVO vo);
   
}