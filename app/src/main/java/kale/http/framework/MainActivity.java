package kale.http.framework;

import com.jsontojava.JsonToJava;
import com.jsontojava.OutputOption;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import kale.framework.R;
import kale.http.framework.impl.HttpRequest;

public class MainActivity extends AppCompatActivity {

    HttpRequest mHttpRequest;
    EditText contentEt;

    android.os.Handler mHandler = new android.os.Handler(
    ) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            contentEt.setText((String)msg.obj);
        }
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contentEt = (EditText) findViewById(R.id.content_et);
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                JsonToJava jsonToJava = new JsonToJava();
//                jsonToJava.setUrl("http://www.weather.com.cn/adat/sk/101010100.html");
                jsonToJava.setUrl("http://www.duitang.com/napi/blog/list/by_club_id/?club_id=54aa79d9a3101a0f75731c62&limit=0&start=0");
                jsonToJava.setPackage("packageName");
                jsonToJava.setBaseType("RootClass");
                jsonToJava.addOutputOption(OutputOption.GSON);
                jsonToJava.fetchJson();
                Log.d("out", "onCreate json -->" + jsonToJava.getJsonPoJos());

                Message msg = new Message();
                msg.obj = jsonToJava.getJsonPoJos();
                mHandler.sendMessage(msg);
            }
        }).start();
       
        
       /* if(cmd.hasOption(OPTION_GSON)){
            jsonToJava.addOutputOption(OutputOption.GSON);
        }
        if(cmd.hasOption(OPTION_PARCELABLE)){
            jsonToJava.addOutputOption(OutputOption.PARCELABLE);
        }
        if(cmd.hasOption(OPTION_TO_STRING)){
            jsonToJava.addOutputOption(OutputOption.TO_STRING);
        }*/

        

        



        /*mHttpRequest = new OldHttpFrameWork();

        mHttpRequest.doPost("http://www.baidu.com", String.class, new Action<String>() {
            @Override
            public void call(String s) {
                Log.d("ddd", "call " + s);
            }
        });
        
        mHttpRequest.doGet("http://www.duitang.com", String.class, new HttpResponse<String>() {
            @Override
            public void onSuccess(String s) {
                Log.d("aaa", "onSuccess " + s);
            }

            @Override
            public void onError(Exception ex) {

            }
        });*/
    }
}
