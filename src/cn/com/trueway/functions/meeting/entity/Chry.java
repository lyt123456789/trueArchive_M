package cn.com.trueway.functions.meeting.entity;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;

@Entity
@SqlResultSetMapping(name="ChryResults", 
        entities={ 
            @EntityResult(entityClass=cn.com.trueway.functions.meeting.entity.Chry.class,
            	fields={
	            	@FieldResult(name="DEPARTMENT_NAME", column="DEPARTMENT_NAME"),
					@FieldResult(name="EMPLOYEE_NAME", column="EMPLOYEE_NAME"),
					@FieldResult(name="EMPLOYEE_JOBTITLES", column="EMPLOYEE_JOBTITLES"),
					@FieldResult(name="EMPLOYEE_MOBILE", column="EMPLOYEE_MOBILE"),
					@FieldResult(name="EMPLOYEE_GUID", column="EMPLOYEE_GUID"),	
                })
        }
)

public class Chry {

	private String DEPARTMENT_NAME;
	private String EMPLOYEE_NAME;
	private String EMPLOYEE_JOBTITLES;
	private String EMPLOYEE_MOBILE;
	@Id
	private String EMPLOYEE_GUID;
	
	public Chry(){
		
	}

	public String getDEPARTMENT_NAME() {
		return DEPARTMENT_NAME;
	}

	public void setDEPARTMENT_NAME(String dEPARTMENT_NAME) {
		DEPARTMENT_NAME = dEPARTMENT_NAME;
	}

	public String getEMPLOYEE_NAME() {
		return EMPLOYEE_NAME;
	}

	public void setEMPLOYEE_NAME(String eMPLOYEE_NAME) {
		EMPLOYEE_NAME = eMPLOYEE_NAME;
	}

	public String getEMPLOYEE_JOBTITLES() {
		return EMPLOYEE_JOBTITLES;
	}

	public void setEMPLOYEE_JOBTITLES(String eMPLOYEE_JOBTITLES) {
		EMPLOYEE_JOBTITLES = eMPLOYEE_JOBTITLES;
	}

	public String getEMPLOYEE_MOBILE() {
		return EMPLOYEE_MOBILE;
	}

	public void setEMPLOYEE_MOBILE(String eMPLOYEE_MOBILE) {
		EMPLOYEE_MOBILE = eMPLOYEE_MOBILE;
	}

	public String getEMPLOYEE_GUID() {
		return EMPLOYEE_GUID;
	}

	public void setEMPLOYEE_GUID(String eMPLOYEE_GUID) {
		EMPLOYEE_GUID = eMPLOYEE_GUID;
	}
	
}
