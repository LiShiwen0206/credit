package com.starsgroupchina.credit.key;

/**
 * Created by zhangfeng on 2018-5-31.
 */
public class KeyCache {

    private static final String KEY_ROOT_PREFIX = "credit:";

    static final String TOKEN = KEY_ROOT_PREFIX + "token:%s";

    private static final String KEY_PATTERN_IMAGE_CAPTCHA = KEY_ROOT_PREFIX + "image_captcha:%s";

    public static String getImageCaptchaKey(String captchaId) {
        return String.format(KEY_PATTERN_IMAGE_CAPTCHA, captchaId);
    }
    public static final String token(String token) {
        return String.format(TOKEN, token);
    }
}
