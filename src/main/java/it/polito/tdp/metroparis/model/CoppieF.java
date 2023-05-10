package it.polito.tdp.metroparis.model;

import java.util.Objects;

public class CoppieF {
	
	private Fermata p;
	private Fermata a;
	
	
	public CoppieF(Fermata p, Fermata a) {
		super();
		this.p = p;
		this.a = a;
	}


	public Fermata getP() {
		return p;
	}


	public void setP(Fermata p) {
		this.p = p;
	}


	public Fermata getA() {
		return a;
	}


	public void setA(Fermata a) {
		this.a = a;
	}


	@Override
	public int hashCode() {
		return Objects.hash(a, p);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CoppieF other = (CoppieF) obj;
		return Objects.equals(a, other.a) && Objects.equals(p, other.p);
	}
	
	
	
	
	
	

}
