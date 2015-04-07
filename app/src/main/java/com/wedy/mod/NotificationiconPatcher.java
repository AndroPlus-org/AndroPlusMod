package org.androplus.wedymod;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.findClass;
import android.content.res.XModuleResources;
import android.content.res.XResources;
import android.os.Build;
import android.text.TextUtils;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.XposedHelpers.ClassNotFoundError;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;


public class NotificationiconPatcher implements IXposedHookZygoteInit, IXposedHookLoadPackage {
	private static XSharedPreferences preference = null;
	private static String modulePath = null;
	private static final String TEXTVIEW = "android.widget.TextView";

	public NotificationiconPatcher() {
	}

	public void initZygote(
			de.robv.android.xposed.IXposedHookZygoteInit.StartupParam startupparam)
			throws Throwable {

		modulePath = startupparam.modulePath;
		preference = new XSharedPreferences(NotificationiconPatcher.class
				.getPackage().getName());
		XModuleResources modRes = XModuleResources.createInstance(modulePath,
				null);

		boolean isTexticon = preference.getBoolean("key_icontext", false);

		if (isTexticon) {
			XResources.setSystemWideReplacement("android", "bool",
					"config_allowActionMenuItemTextWithIcon",
					modRes.fwd(R.bool.config_allowActionMenuItemTextWithIcon));
		}
		boolean isOffled = preference.getBoolean("key_ledoff", false);

		if (isOffled) {
			XResources
					.setSystemWideReplacement(
							"android",
							"bool",
							"config_led_off_when_battery_fully_charged",
							modRes.fwd(R.bool.config_led_off_when_battery_fully_charged));
		}
		boolean isUnplug = preference.getBoolean("key_unplugon", false);

		if (isUnplug) {
			XResources.setSystemWideReplacement("android", "bool",
					"config_unplugTurnsOnScreen",
					false);
			XResources.setSystemWideReplacement("android", "integer",
					"config_deskDockKeepsScreenOn", 0);
			XResources.setSystemWideReplacement("android", "integer",
					"config_carDockKeepsScreenOn", 0);
		}
		boolean isUnplugdock = preference.getBoolean("key_unplugdock", false);

		if (isUnplugdock) {
			XResources.setSystemWideReplacement("android", "bool",
					"config_deskDockEnablesAccelerometer",
					modRes.fwd(R.bool.config_deskDockEnablesAccelerometer));
			XResources.setSystemWideReplacement("android", "bool",
					"config_carDockEnablesAccelerometer",
					modRes.fwd(R.bool.config_carDockEnablesAccelerometer));
		}

		boolean isVolnoi = preference.getBoolean("key_noisy", false);

		if (isVolnoi) {
			XResources.setSystemWideReplacement("android", "bool",
					"config_sendAudioBecomingNoisy",
					modRes.fwd(R.bool.config_sendAudioBecomingNoisy));
		}
		boolean isVolsou = preference.getBoolean("key_volsound", false);

		if (isVolsou) {
			XResources.setSystemWideReplacement("android", "bool",
					"config_useVolumeKeySounds",
					modRes.fwd(R.bool.config_useVolumeKeySounds));
		}
		boolean isSkipnet = preference.getBoolean("key_skipnet", false);

		if (isSkipnet) {
			XResources.setSystemWideReplacement("android", "bool",
					"skip_restoring_network_selection",
					modRes.fwd(R.bool.skip_restoring_network_selection));
		}
		boolean isVoice = preference.getBoolean("key_voice", false);

		if (isVoice) {
			XResources.setSystemWideReplacement("android", "bool",
					"config_voice_capable",
					modRes.fwd(R.bool.config_voice_capable));
		}
		boolean isPreapn = preference.getBoolean("key_prefapn", false);

		if (isPreapn) {
			XResources.setSystemWideReplacement("android", "bool",
					"config_dontPreferApn",
					modRes.fwd(R.bool.config_dontPreferApn));
		}
		boolean isDimc = preference.getBoolean("key_dim", false);

		if (isDimc && Build.VERSION.SDK_INT <= 16) {
			XResources.setSystemWideReplacement("android", "integer",
					"config_screenBrightnessDim",
					modRes.fwd(R.integer.config_screenBrightnessDim));
			XposedBridge.log("Wedy: min dim 10 for 4.1");
		} else if (isDimc && Build.VERSION.SDK_INT >= 17) {
			XResources.setSystemWideReplacement(
							"android",
							"integer",
							"config_screenBrightnessDim",
							modRes.fwd(R.integer.config_screenBrightnessDim));
			XResources.setSystemWideReplacement(
							"android",
							"integer",
							"config_screenBrightnessSettingMinimum",
							modRes.fwd(R.integer.config_screenBrightnessSettingMinimum));
			XposedBridge.log("Wedy: I am JB 4.2 or later");
		}
		boolean isImehi = preference.getBoolean("key_ime", false);

		if (isImehi) {
			XResources.setSystemWideReplacement("android", "bool",
					"show_ongoing_ime_switcher",
					modRes.fwd(R.bool.show_ongoing_ime_switcher));
		}
		boolean isLockm = preference.getBoolean("key_lockmenu", false);

		if (isLockm) {
			XResources.setSystemWideReplacement("android", "bool",
					"config_disableMenuKeyInLockScreen",
					modRes.fwd(R.bool.config_disableMenuKeyInLockScreen));
		}
		boolean isLockrot = preference.getBoolean("key_lockrot", false);

		if (isLockrot) {
			XResources.setSystemWideReplacement("android", "bool",
					"lockscreen_isPortrait",
					modRes.fwd(R.bool.lockscreen_isPortrait));
			XResources.setSystemWideReplacement("android", "bool",
					"config_enableLockScreenRotation",
					modRes.fwd(R.bool.config_enableLockScreenRotation));
		}
		boolean isNotifbg = preference.getBoolean("key_notifbg", false);

		if (isNotifbg) {
			XResources.setSystemWideReplacement("android", "drawable",
					"notification_template_icon_bg", 0x00000000);
			XResources.setSystemWideReplacement("android", "drawable",
					"notification_template_icon_low_bg", 0x00000000);
		}
		boolean isHideime = preference.getBoolean("key_hideime", false);
		if (isHideime) {
			XResources.setSystemWideReplacement("android", "bool",
					"config_camera_sound_forced", false);
			XResources.setSystemWideReplacement("android", "bool",
					"config_enableCpuBoostForOverScrollerFling", true);
		}
		boolean isCpuboost = preference.getBoolean("key_boost", false);
		if (isCpuboost) {
			XResources.setSystemWideReplacement("android", "bool",
					"config_enableCpuBoostForOverScrollerFling", true);
		}
		boolean isGlove = preference.getBoolean("key_glove", false);
		if (isGlove) {
			XResources.setSystemWideReplacement("android", "bool",
					"config_enable_glove_mode", true);
		}
		boolean isLtedbmc = preference.getBoolean("key_navbar", false);
		if (isLtedbmc) {
			XResources.setSystemWideReplacement("android", "dimen",
					"navigation_bar_height",
					modRes.fwd(R.dimen.navigation_bar_height));
			XResources.setSystemWideReplacement("android", "dimen",
					"navigation_bar_height_landscape",
					modRes.fwd(R.dimen.navigation_bar_height_landscape));
		}
		boolean isAutob = preference.getBoolean("key_autob", false);
		if (isAutob) {
			XResources.setSystemWideReplacement("android", "array",
					"config_autoBrightnessLevels",
					modRes.fwd(R.array.config_autoBrightnessLevels));
			XResources.setSystemWideReplacement("android", "array",
					"config_autoBrightnessLcdBacklightValues",
					modRes.fwd(R.array.config_autoBrightnessLcdBacklightValues));
		}
		
		boolean isWifiband = preference.getBoolean("key_wifiband", false);

		if (isWifiband) {
			XResources.setSystemWideReplacement("android", "bool",
					"config_wifi_dual_band_support",
					true);
		}
		
		boolean isMonxs = preference.getBoolean("key_monx", false);

		if (isMonxs) {
			XResources.setSystemWideReplacement("android", "string",
					"global_action_reboot_normal",
					modRes.fwd(R.string.global_action_reboot_normal));
			XResources.setSystemWideReplacement("android", "string",
					"global_action_reboot_title",
					modRes.fwd(R.string.global_action_reboot_title));
			XResources.setSystemWideReplacement("android", "string",
					"global_action_fullscreen_toggle",
					modRes.fwd(R.string.global_action_fullscreen_toggle));
		}
		
		// Fix Japanese translations for Z3 style
		boolean isFixjpnz3 = preference.getBoolean("key_fixjpnz3", false);

		if (isFixjpnz3) {
			XResources.setSystemWideReplacement("android", "string",
					"global_action_screenshot_split_txt",
					modRes.fwd(R.string.global_action_screenshot_split_txt));
			XResources.setSystemWideReplacement("android", "string",
					"global_action_screenrecording_split_txt",
					modRes.fwd(R.string.global_action_screenrecording_split_txt));
		}
		// Fix Japanese translations for Z3 style
		boolean isFixjpnz32 = preference.getBoolean("key_fixjpnz32", false);

		if (isFixjpnz32) {
			XResources.setSystemWideReplacement("android", "string",
					"global_action_screenshot_split_txt",
					modRes.fwd(R.string.global_action_screenshot_split_txt_j));
			XResources.setSystemWideReplacement("android", "string",
					"global_action_screenrecording_split_txt",
					modRes.fwd(R.string.global_action_screenrecording_split_txt_j));
		}
		
		boolean isMonxsz1 = preference.getBoolean("key_monxz1", false);

		if (isMonxsz1) {
			XResources.setSystemWideReplacement("android", "string",
					"global_action_reboot_normal",
					modRes.fwd(R.string.global_action_reboot_normal));
			XResources.setSystemWideReplacement("android", "string",
					"global_action_reboot_title",
					modRes.fwd(R.string.global_action_reboot_title));
			XResources.setSystemWideReplacement("android", "string",
					"global_action_fullscreen_toggle",
					modRes.fwd(R.string.global_action_fullscreen_toggle));
			XResources.setSystemWideReplacement("android", "string",
					"global_action_reboot_hotreboot",
					modRes.fwd(R.string.global_action_reboot_hotreboot));
			XResources.setSystemWideReplacement("android", "string",
					"global_action_reboot_recovery",
					modRes.fwd(R.string.global_action_reboot_recovery));
		}
		

	}
	
	@Override
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable
	{
		switch(lpparam.packageName)
		{
			case "android":
				hookPowerManager(lpparam);
				break;
		}
		boolean isPurepaste = preference.getBoolean("key_purepaste", false);
		if (isPurepaste) {
		try {
		// TextViewを持つものは全て対象にする
		findClass(TEXTVIEW, lpparam.classLoader);
		} catch (ClassNotFoundError e) {
			XposedBridge.log("PurePaste : TextView not found " + lpparam.packageName);
			return;
		}
		try {
		// prepareSpacesAroundPaste を持っていない場合は無視
		findAndHookMethod(TEXTVIEW, lpparam.classLoader, "prepareSpacesAroundPaste", int.class, int.class, CharSequence.class, NEW_prepareSpacesAroundPaste);
		} catch (NoSuchMethodError e) {
			// XposedBridge.log("PurePaste : prepareSpacesAroundPaste not found " + lpparam.packageName);
			}
		}
		}

		// prepareSpacesAroundPaste で前後の空白をチェックして挿入してるので、何もしないようにする
		
		private static final XC_MethodReplacement NEW_prepareSpacesAroundPaste = new XC_MethodReplacement() {
		@Override
		
		protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
		int min = (Integer)param.args[0];
		int max = (Integer)param.args[1];
		long r;
		try {
			// #hideなのでこんな面倒くさいことをする
			r = (Long)XposedHelpers.callStaticMethod(TextUtils.class, "packRangeInLong", min, max);
			} catch (NoSuchMethodError e) {
			XposedBridge.log("PurePaste : TextUtils#packRangeInLong not found");
			r = (((long)min) << 32) | max;
			}
			XposedBridge.log("PurePaste : called prepareSpacesAroundPaste(" + min + ", " + max + ") return " + r);
			return r;
		}
		};

	public void hookPowerManager(LoadPackageParam lpparam)
		{
		boolean isUnplug = preference.getBoolean("key_unplugon", false);
		if(isUnplug){
			try
			{
				Object[] signature = new Object[2];
				signature[0] = long.class;
				signature[1] = new XC_MethodHook() {
					@Override
					protected void beforeHookedMethod(MethodHookParam param) throws Throwable
					{
						try
						{
							final String className = "com.android.server.DockObserver";
							final String methodName = "onUEvent";
							
							// The first elements are getThreadStackTrace, getStackTrace, beforeHookedMethod,
							// handleHookedMethod, PowerManagerService.wakeUp and PowerManager.wakeUp
							final StackTraceElement caller = Thread.currentThread().getStackTrace()[6];
							
							if(caller.getClassName().equals(className) && caller.getMethodName().equals(methodName))
							{
								// Don't call wakeUp if the caller is DockObserver.onUEvent
								param.setResult(null);
							}
						}
						catch(Throwable ignore)
						{
						}
					}
				};
				
				final String pm = "com.android.server.power.PowerManagerService";
				findAndHookMethod(pm, lpparam.classLoader, "wakeUp", signature);
			}
			catch(Throwable t)
			{
				XposedBridge.log(t);
			}
		}
		}
	
}