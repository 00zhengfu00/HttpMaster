package kale.http.framework.util;

import android.content.ClipboardManager;
import android.content.Context;

/**
 * @author http://blog.csdn.net/voiceofnet/article/details/7741259
 * @date 2015/8/6
 */
public class ClipBoardUtil {

    public static void copy(Context context,String text) {
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(text.trim());
    }

    /**
     * 实现粘贴功能
     * add by wangqianzhou
     */
    public static String paste(Context context) {
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        return cmb.getText().toString().trim();
    }
}
