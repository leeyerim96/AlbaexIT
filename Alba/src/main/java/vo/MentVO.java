package vo;

public class MentVO {

	private int mno;
	private int pno;
	private String content; //�� ����
	private String regdate; //�ۼ���¥
	private int seq; //�Խñ� ��ȣ
	private String pid; // �� �ۼ���

	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public int getMno() {
		return mno;
	}
	public void setMno(int mno) {
		this.mno = mno;
	}
	public int getPno() {
		return pno;
	}
	public void setPno(int pno) {
		this.pno = pno;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	@Override
	public String toString() {
		return "MentVO [mno=" + mno + ", pno=" + pno + ", content=" + content + ", regdate=" + regdate + ", seq=" + seq
				+ ", pid=" + pid + "]";
	}


}