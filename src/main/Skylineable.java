package main;

import java.util.Map;

public abstract class Skylineable {
	protected Map<String, Float> QoS;
	protected int id;
	protected String name;
	
	public Map<String, Float> getQoS() {
		return QoS;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setQoS(Map<String, Float> qoS) {
		QoS = qoS;
	}
}
