import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;

import android.widget.EditText;

import kale.framework.R;
import kale.http.framework.MainActivity;

/**
 * @author Jack Tony
 * @date 2015/8/8
 */
@RunWith(RobolectricGradleTestRunner.class)
// 设置被测项目的AndroidManifest.xml
public class MainActivityTest {

    @Test
    public void testMainActivity() {
        MainActivity mainActivity = Robolectric.setupActivity(MainActivity.class);
        EditText et = (EditText) mainActivity.findViewById(R.id.url_et);
        String text = et.getText().toString();
        
        Assert.assertEquals("123", text);
    }
}  