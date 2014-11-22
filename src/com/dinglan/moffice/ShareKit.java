package com.dinglan.moffice;

import com.dinglan.moffice.model.Share;
import com.dinglan.weixin.msg.InImageMsg;
import com.dinglan.weixin.msg.InLinkMsg;
import com.dinglan.weixin.msg.InLocationEvent;
import com.dinglan.weixin.msg.InLocationMsg;
import com.dinglan.weixin.msg.InMenuEvent;
import com.dinglan.weixin.msg.InMsg;
import com.dinglan.weixin.msg.InTextMsg;
import com.dinglan.weixin.msg.InVideoMsg;
import com.dinglan.weixin.msg.InVoiceMsg;

/**
 * ShareAction
 */
public class ShareKit {
	public static void process(InMsg inmsg) {
		Share share = new Share();
		initMsg(share, inmsg);
		if (inmsg instanceof InTextMsg) {
			InTextMsg msg = (InTextMsg) inmsg;
			share.set("content", msg.getContent());
			share.save();
		} else if (inmsg instanceof InImageMsg) {
			InImageMsg msg = (InImageMsg) inmsg;
			share.set("mediaId", msg.getMediaId());
			share.set("msgId", msg.getMsgId());
			share.set("picUrl", msg.getPicUrl());
			share.save();
		}else if( inmsg instanceof InVoiceMsg){
			InVoiceMsg msg = (InVoiceMsg) inmsg;
			share.set("mediaId", msg.getMediaId());
			share.set("msgId", msg.getMsgId());
			share.save();
		}
		else if( inmsg instanceof InLinkMsg){
			InLinkMsg msg = (InLinkMsg) inmsg;
			share.set("url", msg.getUrl());
			share.set("description", msg.getDescription());
			share.save();
		}else if (inmsg instanceof InLocationEvent) {
			InLocationEvent msg = (InLocationEvent) inmsg;
			share.set("longitude", msg.getLongitude());
			share.set("latitude", msg.getLatitude());
			share.set("precision", msg.getPrecision());
			share.set("label", msg.getLabel());
			share.set("poiname", msg.getPoiname());
			share.save();
		}else if (inmsg instanceof InLocationMsg) {
			InLocationMsg msg = (InLocationMsg) inmsg;
			share.set("longitude", msg.getLocation_X());
			share.set("latitude", msg.getLocation_Y());
			share.set("precision", msg.getScale());
			share.set("label", msg.getLabel());
			share.set("msgId", msg.getMsgId());
			share.save();
		}else if( inmsg instanceof InVideoMsg){
			InVideoMsg msg = (InVideoMsg) inmsg;
			share.set("mediaId", msg.getMediaId());
			share.set("msgId", msg.getMsgId());
			share.set("thumbMediaId", msg.getThumbMediaId());
			share.save();
		}else if( inmsg instanceof InMenuEvent){
			InMenuEvent msg = (InMenuEvent) inmsg;
			share.set("event", msg.getEvent());
			share.set("eventKey", msg.getEventKey());
			share.save();
			
			//保持菜单点击用户 彭2014.11.21
			ActorKit.CURENT=inmsg.getFromUserName();
		}
	}

	private static void initMsg(Share share, InMsg msg) {
		share.set("toUserName", msg.getToUserName());
		share.set("fromUserName", msg.getFromUserName());
		share.set("createTime", msg.getCreateTime());
		share.set("msgType", msg.getMsgType());
	}
}
