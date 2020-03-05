package cn.com.trueway.archives.reports.pojo;

/**
 * 南通市档案局利用工作统计报表
 * @author user
 *
 */
public class WorkStatistics {
	private String id;
	//接待人
	private String receivePeople;
	//利用人数
	private String usingPeopleNum;
	//利用天数
	private String usingDays;
	//利用人次
	private String usingPeopleTimes;
	//调卷（件）总数
	private String archivesNum;
	//打印件数
	private String printTimes;
	//打印页数
	private String printPageNums;
	//复印件数
	private String copyTimes;
	//复印页数
	private String copyPageNums;
	//摘抄件数
	private String excerptTimes;
	//摘抄页数
	private String excerptPageNums;
	//拍摄件数
	private String photoTimes;
	//拍摄画图数
	private String photoPageNums;
	//查档目的
	private String borrowCdmd;
	//提供档案（卷次）
	private String borrowJuan;
	//提供档案（件）
	private String borrowJian;
	//提供资料（册）
	private String borrowData;
	//复制资料总页数
	private String pageCopyTotal;
	//其他目的
	private String borrowQtmd;
	//档案门类
	private String borrowArchive;
	//资料载体类别
	private String formats;
	
	public String getBorrowCdmd() {
		return borrowCdmd;
	}
	public void setBorrowCdmd(String borrowCdmd) {
		this.borrowCdmd = borrowCdmd;
	}
	public String getBorrowJuan() {
		return borrowJuan;
	}
	public void setBorrowJuan(String borrowJuan) {
		this.borrowJuan = borrowJuan;
	}
	public String getBorrowJian() {
		return borrowJian;
	}
	public void setBorrowJian(String borrowJian) {
		this.borrowJian = borrowJian;
	}
	public String getBorrowData() {
		return borrowData;
	}
	public void setBorrowData(String borrowData) {
		this.borrowData = borrowData;
	}
	public String getPageCopyTotal() {
		return pageCopyTotal;
	}
	public void setPageCopyTotal(String pageCopyTotal) {
		this.pageCopyTotal = pageCopyTotal;
	}
	public String getReceivePeople() {
		return receivePeople;
	}
	public void setReceivePeople(String receivePeople) {
		this.receivePeople = receivePeople;
	}
	public String getUsingPeopleNum() {
		return usingPeopleNum;
	}
	public void setUsingPeopleNum(String usingPeopleNum) {
		this.usingPeopleNum = usingPeopleNum;
	}
	public String getUsingDays() {
		return usingDays;
	}
	public void setUsingDays(String usingDays) {
		this.usingDays = usingDays;
	}
	public String getUsingPeopleTimes() {
		return usingPeopleTimes;
	}
	public void setUsingPeopleTimes(String usingPeopleTimes) {
		this.usingPeopleTimes = usingPeopleTimes;
	}
	public String getArchivesNum() {
		return archivesNum;
	}
	public void setArchivesNum(String archivesNum) {
		this.archivesNum = archivesNum;
	}
	public String getPrintTimes() {
		return printTimes;
	}
	public void setPrintTimes(String printTimes) {
		this.printTimes = printTimes;
	}
	public String getPrintPageNums() {
		return printPageNums;
	}
	public void setPrintPageNums(String printPageNums) {
		this.printPageNums = printPageNums;
	}
	public String getCopyTimes() {
		return copyTimes;
	}
	public void setCopyTimes(String copyTimes) {
		this.copyTimes = copyTimes;
	}
	public String getCopyPageNums() {
		return copyPageNums;
	}
	public void setCopyPageNums(String copyPageNums) {
		this.copyPageNums = copyPageNums;
	}
	public String getExcerptTimes() {
		return excerptTimes;
	}
	public void setExcerptTimes(String excerptTimes) {
		this.excerptTimes = excerptTimes;
	}
	public String getExcerptPageNums() {
		return excerptPageNums;
	}
	public void setExcerptPageNums(String excerptPageNums) {
		this.excerptPageNums = excerptPageNums;
	}
	public String getPhotoTimes() {
		return photoTimes;
	}
	public void setPhotoTimes(String photoTimes) {
		this.photoTimes = photoTimes;
	}
	public String getPhotoPageNums() {
		return photoPageNums;
	}
	public void setPhotoPageNums(String photoPageNums) {
		this.photoPageNums = photoPageNums;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBorrowQtmd() {
		return borrowQtmd;
	}
	public void setBorrowQtmd(String borrowQtmd) {
		this.borrowQtmd = borrowQtmd;
	}
	public String getBorrowArchive() {
		return borrowArchive;
	}
	public void setBorrowArchive(String borrowArchive) {
		this.borrowArchive = borrowArchive;
	}
	public String getFormats() {
		return formats;
	}
	public void setFormats(String formats) {
		this.formats = formats;
	}
	
	public WorkStatistics() {
		
	}
	public WorkStatistics(String usingPeopleTimes, String printPageNums, String copyPageNums, String excerptPageNums,
			String photoPageNums, String borrowJuan, String borrowJian, String borrowData, String pageCopyTotal,
			String formats) {
		super();
		this.usingPeopleTimes = usingPeopleTimes;
		this.printPageNums = printPageNums;
		this.copyPageNums = copyPageNums;
		this.excerptPageNums = excerptPageNums;
		this.photoPageNums = photoPageNums;
		this.borrowJuan = borrowJuan;
		this.borrowJian = borrowJian;
		this.borrowData = borrowData;
		this.pageCopyTotal = pageCopyTotal;
		this.formats = formats;
	}
	
}
