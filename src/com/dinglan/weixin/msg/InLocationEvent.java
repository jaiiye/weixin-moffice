package com.dinglan.weixin.msg;


/**
	上报地理位置事件
	<xml>
		<ToUserName><![CDATA[toUser]]></ToUserName>
		<FromUserName><![CDATA[fromUser]]></FromUserName>
		<CreateTime>123456789</CreateTime>
		<MsgType><![CDATA[event]]></MsgType>
			<Event><![CDATA[LOCATION]]></Event>
			<Latitude>23.137466</Latitude>
			<Longitude>113.352425</Longitude>
			<Precision>119.385040</Precision>
	</xml>
 */
public class InLocationEvent extends InMsg {
	
	private String event;
	private String latitude;
	private String longitude;
	private String precision;
	private String label;
	private String poiname;
	
	public InLocationEvent(String toUserName, String fromUserName, Integer createTime, String msgType) {
		super(toUserName, fromUserName, createTime, msgType);
	}
	
	public String getEvent() {
		return event;
	}
	
	public void setEvent(String event) {
		this.event = event;
	}
	
	public String getLatitude() {
		return latitude;
	}
	
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	public String getLongitude() {
		return longitude;
	}
	
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public String getPrecision() {
		return precision;
	}
	
	public void setPrecision(String precision) {
		this.precision = precision;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getPoiname() {
		return poiname;
	}

	public void setPoiname(String poiname) {
		this.poiname = poiname;
	}
}




