package com.android.system.controled;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

public class MainAtivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

//		// 设备安全管理服务 2.2之前的版本是没有对外暴露的 只能通过反射技术获取
//		DevicePolicyManager devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
//		// 申请权限
//		ComponentName componentName = new ComponentName(MainAtivity.this, MainDeviceAdminReceiver.class);
//		// 判断该组件是否有系统管理员的权限
//		boolean isAdminActive = devicePolicyManager.isAdminActive(componentName);
//		if (isAdminActive) {

			startActivity(new Intent(Settings.ACTION_SETTINGS));

//			// devicePolicyManager.lockNow(); // 锁屏
//			// devicePolicyManager.resetPassword("123", 0); // 设置锁屏密码
//			// devicePolicyManager.wipeData(0); 恢复出厂设置 (建议大家不要在真机上测试) 模拟器不支持该操作
//		} else {
//			Intent intent = new Intent();
//			// 指定动作名称
//			intent.setAction(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
//			// 指定给哪个组件授权
//			intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
//			startActivity(intent);
//		}

		finish();
	}

}
