package com.revature.dto;

import java.util.Objects;

public class ChangeReimStatusDTO {

	private String reimStatus;

	public ChangeReimStatusDTO() {
		super();
	}

	public ChangeReimStatusDTO(String reimStatus) {
		super();
		this.reimStatus = reimStatus;
	}

	public String getReimStatus() {
		return reimStatus;
	}

	public void setReimStatus(String reimStatus) {
		this.reimStatus = reimStatus;
	}

	@Override
	public int hashCode() {
		return Objects.hash(reimStatus);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChangeReimStatusDTO other = (ChangeReimStatusDTO) obj;
		return reimStatus == other.reimStatus;
	}

	@Override
	public String toString() {
		return "ChangeReimAmountDTO [reimStatus=" + reimStatus + "]";
	}
	
}