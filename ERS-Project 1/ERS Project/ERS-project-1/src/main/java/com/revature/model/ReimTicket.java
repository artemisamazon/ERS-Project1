package com.revature.model;

import java.util.Objects;

public class ReimTicket {

	private int reimticketId;
	private String reimticketType;
	private String reimstatus;
	private int resolverId;
	private String reimInfo; //reimticket description
	private int reimauthorId;
	private double reimamount;
	
	public ReimTicket() {
		super();
	}

	public ReimTicket(int reimticketId, String reimticketType, String reimstatus, int resolverId, String reimInfo, int reimauthorId, double reimamount) {
		super();
		this.reimticketId = reimticketId;
		this.reimticketType = reimticketType;
		this.reimstatus = reimstatus; //changed reimamount to reimstatus
		this.resolverId = resolverId;
		this.reimInfo = reimInfo; //reimticket description
		this.reimauthorId = reimauthorId;
		this.reimamount = reimamount;
		
	}

	public int getReimticketId() {
		return reimticketId;
	}

	public void setReimticketId(int reimticketId) {
		this.reimticketId = reimticketId;
	}

	public String getReimticketType() {
		return reimticketType;
	}

	public void setReimticketType(String reimticketType) {
		this.reimticketType = reimticketType;
	}

	public String getReimstatus() {
		return reimstatus;
	}

	public void setReimstatus(String reimstatus) {
		this.reimstatus = reimstatus;
	}

	public int getResolverId() {
		return resolverId;
	}

	public void setResolverId(int resolverId) {
		this.resolverId = resolverId;
	}

	public String getReimInfo() {
		return reimInfo;
	}

	public void setReimInfo(String reimInfo) {
		this.reimInfo = reimInfo;
	}

	public int getReimauthorId() {
		return reimauthorId;
	}

	public void setReimauthorId(int reimauthorId) {
		this.reimauthorId = reimauthorId;
	}

	public double getReimamount() {
		return reimamount;
	}

	public void setReimamount(double reimamount) {
		this.reimamount = reimamount;
	}

	@Override
	public int hashCode() {
		return Objects.hash(reimInfo, reimamount, reimauthorId, reimstatus, reimticketId, reimticketType, resolverId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReimTicket other = (ReimTicket) obj;
		return Objects.equals(reimInfo, other.reimInfo) && reimamount == other.reimamount
				&& reimauthorId == other.reimauthorId && Objects.equals(reimstatus, other.reimstatus)
				&& reimticketId == other.reimticketId && Objects.equals(reimticketType, other.reimticketType)
				&& resolverId == other.resolverId;
	}

	@Override
	public String toString() {
		return "ReimTicket [reimticketId=" + reimticketId + ", reimticketType=" + reimticketType + ", reimstatus="
				+ reimstatus + ", resolverId=" + resolverId + ", reimInfo=" + reimInfo + ", reimauthorId="
				+ reimauthorId + ", reimamount=" + reimamount + "]";
	}





	
}