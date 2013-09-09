package com.android.system.controled.util;

import android.content.Context;
import android.content.res.XmlResourceParser;

import com.android.system.controled.R;
import com.android.system.controled.bean.ConfigInfo;

/**
 * xml解析
 * 
 * @Description:
 * @author: zhuanggy
 * @see:
 * @since:
 * @copyright © 35.com
 * @Date:2013-9-9
 */
public class XmlUtil {

	private static String getXmlAttribute(Context context, XmlResourceParser xml, String name) {
		int resId = xml.getAttributeResourceValue(null, name, 0);
		if (resId == 0) {
			return xml.getAttributeValue(null, name);
		} else {
			return context.getString(resId);
		}
	}

	/**
	 * public static final String PHONE_NUMBER = "18210633121";// 控制者的电话号码
	 * 
	 * public final static String senderName = "control_app@163.com"; public final static String senderPwd =
	 * "control_app1$!";
	 * 
	 * 
	 * public final static String receiverEmail = "craining@163.com";
	 * 
	 * 
	 */

	public static ConfigInfo getConfigInfo(Context context) {
		ConfigInfo result = new ConfigInfo();
		try {
			XmlResourceParser xml = context.getResources().getXml(R.xml.providers);
			int xmlEventType;
			while ((xmlEventType = xml.next()) != XmlResourceParser.END_DOCUMENT) {
				if (xmlEventType == XmlResourceParser.START_TAG && "provider".equals(xml.getName())) {
					result.setControllerTel(getXmlAttribute(context, xml, "controllertel"));
					result.setSenderEmailAddr(getXmlAttribute(context, xml, "senderemail"));
					result.setSenderPwd(getXmlAttribute(context, xml, "senderpassward"));
					result.setReceiveEmailAddr(getXmlAttribute(context, xml, "receiveemail"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
