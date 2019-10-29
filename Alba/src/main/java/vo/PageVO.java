package vo;


public class PageVO {
	private int currPage;   // ����� ���� Page
	private int perPage=15;  // 1page �� ��� ����
	private int totalCount; // total Record ����
	private int perPageNo=3; // �� ȭ�鿡 ��µǴ� pageNo ����
	private int sno; // start rownum
	private String search;  

	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}

	public int getSno() {
		return sno;
	}

	public void setSno(int sno) {
		this.sno = sno;
	}



	public int getPerPageNo() {
		return perPageNo;
	}

	public void setPerPageNo(int perPageNo) {
		this.perPageNo = perPageNo;
	}


	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public int getPerPage() {
		return perPage;
	}

	public void setPerPage(int perPage) {
		this.perPage = perPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	@Override
	public String toString() {
		return "PageVO [currPage=" + currPage + ", perPage=" + perPage + ", totalCount=" + totalCount + ", perPageNo="
				+ perPageNo + ", sno=" + sno + ", search=" + search + "]";
	}




}
