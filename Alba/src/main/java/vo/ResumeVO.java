package vo;

import org.springframework.web.multipart.MultipartFile;

public class ResumeVO {

   private int userno;
   private String title;
   private int pno;
   private String pname; //�������̸�
   private int age; //����
   private String gender; //����
   private String phone; //����ȣ
   private String academic; //�з�
   private String career;
   private String typeoccup; //����
   private String period; //�Ⱓ
   private String dow; //����
   private String timetotime; //�ð�
   private String region; //����
   private String uploadfile; // alba Table�� ���ǵ� field ��� ����
   private MultipartFile uploadfilef;// form�� input tag�� name�� ����
   private String addr;
   private String selfinfo; //�ڱ�Ұ���
   
   
  
   public String getSelfinfo() {
	return selfinfo;
}
public void setSelfinfo(String selfinfo) {
	this.selfinfo = selfinfo;
}
public int getUserno() {
      return userno;
   }
   public void setUserno(int userno) {
      this.userno = userno;
   }
   public String getTitle() {
      return title;
   }
   public void setTitle(String title) {
      this.title = title;
   }
   public String getPname() {
      return pname;
   }
   public void setPname(String pname) {
      this.pname = pname;
   }
   public int getAge() {
      return age;
   }
   public void setAge(int age) {
      this.age = age;
   }
   public String getGender() {
      return gender;
   }
   public void setGender(String gender) {
      this.gender = gender;
   }
   public String getPhone() {
      return phone;
   }
   public void setPhone(String phone) {
      this.phone = phone;
   }
   public String getAcademic() {
      return academic;
   }
   public void setAcademic(String academic) {
      this.academic = academic;
   }

   public String getCareer() {
      return career;
   }
   public void setCareer(String career) {
      this.career = career;
   }
   public String getTypeoccup() {
      return typeoccup;
   }
   public void setTypeoccup(String typeoccup) {
      this.typeoccup = typeoccup;
   }
   public String getPeriod() {
      return period;
   }
   public void setPeriod(String period) {
      this.period = period;
   }
   public String getDow() {
      return dow;
   }
   public void setDow(String dow) {
      this.dow = dow;
   }
   public String getTimetotime() {
      return timetotime;
   }
   public void setTimetotime(String timetotime) {
      this.timetotime = timetotime;
   }
   public String getRegion() {
      return region;
   }
   public void setRegion(String region) {
      this.region = region;
   }
   public int getPno() {
      return pno;
   }
   public void setPno(int pno) {
      this.pno = pno;
   }

   public String getUploadfile() {
      return uploadfile;
   }
   public void setUploadfile(String uploadfile) {
      this.uploadfile = uploadfile;
   }
   public MultipartFile getUploadfilef() {
      return uploadfilef;
   }
   public void setUploadfilef(MultipartFile uploadfilef) {
      this.uploadfilef = uploadfilef;
   }

   public String getAddr() {
      return addr;
   }
   public void setAddr(String addr) {
      this.addr = addr;
   }
@Override
public String toString() {
	return "ResumeVO [userno=" + userno + ", title=" + title + ", pno=" + pno + ", pname=" + pname + ", age=" + age
			+ ", gender=" + gender + ", phone=" + phone + ", academic=" + academic + ", career=" + career
			+ ", typeoccup=" + typeoccup + ", period=" + period + ", dow=" + dow + ", timetotime=" + timetotime
			+ ", region=" + region + ", uploadfile=" + uploadfile + ", uploadfilef=" + uploadfilef + ", addr=" + addr
			+ ", selfinfo=" + selfinfo + "]";
}


}