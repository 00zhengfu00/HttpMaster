package kale.http.framework;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import kale.framework.R;
import kale.http.framework.impl.HttpRequest;
import kale.http.framework.util.ClipBoardUtil;
import kale.http.framework.util.TextWatcherAdapter;
import kale.http.framework.view.CodeView;
import kale.http.framework.view.ShaderEditor;

public class MainActivity extends AppCompatActivity {

    private Presenter presenter;

    private String url;

    private boolean isPost;

    private boolean hasModel;

    // private SparseBooleanArray CheckedItemPositions;

    ////////////////// view ////////////////////

    private ProgressDialog loadingPd;

    private EditText urlEt;

    private ImageView clearUrlIv;

    private CheckedTextView isPostCtv;

    private CheckedTextView hasModelCtv;

    private ListView paramLv;

    private ShaderEditor methodEt;

    private LinearLayout codeLl;

    private Button copyMethodBtn;

    private HttpRequest mHttpRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new Presenter(this);
        findViews();
        setViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onActivityDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void findViews() {
        loadingPd = new ProgressDialog(this);
        urlEt = (EditText) findViewById(R.id.url_et);
        clearUrlIv = (ImageView) findViewById(R.id.clear_url_iv);
        isPostCtv = (CheckedTextView) findViewById(R.id.is_post_cTv);
        hasModelCtv = (CheckedTextView) findViewById(R.id.has_model_cTv);
        paramLv = (ListView) findViewById(R.id.option_lv);
        codeLl = (LinearLayout) findViewById(R.id.code_ll);
        methodEt = (ShaderEditor) findViewById(R.id.method_sd);
        copyMethodBtn = (Button) findViewById(R.id.copy_method_btn);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.json2model_menu) {
            createModelsByUrl();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setViews() {
        urlEt.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                url = urlEt.getText().toString().trim();
                if (TextUtils.isEmpty(url)) {
                    Toast.makeText(MainActivity.this, "url不能为空哦~", Toast.LENGTH_SHORT).show();
                }
                presenter.parseUrlToListItem(url);
                ((ArrayAdapter) paramLv.getAdapter()).notifyDataSetChanged();
                createMethodsBlock();
            }
        });
        urlEt.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // 这两个条件必须同时成立，如果仅仅用了enter判断，就会执行两次
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    createModelsByUrl();
                    return true;
                }
                return false;
            }
        });
        clearUrlIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlEt.setText("");
                paramLv.clearChoices();
            }
        });
        isPostCtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPostCtv.toggle();
                isPost = isPostCtv.isChecked();
                createMethodsBlock();
            }
        });
        hasModelCtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hasModelCtv.toggle();
                hasModel = hasModelCtv.isChecked();
                createMethodsBlock();
            }
        });
        paramLv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        paramLv.setAdapter(new ArrayAdapter<>(this, R.layout.param_item, presenter.getParamList()));
        paramLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 点击参数列表后刷新方法块
                createMethodsBlock();
            }
        });

        copyMethodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipBoardUtil.copy(getBaseContext(), methodEt.getText().toString().trim());
                final Snackbar snackbar = Snackbar.make(codeLl, "Has been copied to the clipboard", Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });

        // 初始化变量
        //  CheckedItemPositions = paramLv.getCheckedItemPositions();
        isPost = isPostCtv.isChecked();
        hasModel = hasModelCtv.isChecked();
        
        /*urlEt.setText("http://www.weather.com.cn/adat/sk/101010100.html?val_ue=123");// 要删掉
        url = urlEt.getText().toString().trim();
        presenter.parseUrlToListItem(url);// 
        createMethodsBlock();// 要删掉*/
        mHttpRequest = new OldHttpFrameWork();
        // 初始变量结束
    }

    private void createMethodsBlock() {
        if (TextUtils.isEmpty(url)) {
            Toast.makeText(MainActivity.this, "url不能为空哦~", Toast.LENGTH_SHORT).show();
            return;
        }
        String methodStr = presenter.getMethodBlock(url, paramLv.getCheckedItemPositions(), isPost, hasModel);
        methodEt.setTextHighlighted(methodStr);
    }

    public void createModelsByUrl() {
        if (TextUtils.isEmpty(url)) {
            Toast.makeText(MainActivity.this, "url不能为空哦~", Toast.LENGTH_SHORT).show();
            return;
        }
        codeLl.removeViews(2, codeLl.getChildCount() - 2);
        loadingPd.setMessage("Loading...");
        loadingPd.show();
        loadingPd.setCancelable(false);
        presenter.createModels(url);
    }

    public void onCreateModelsResponse(List<String> modelList) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        for (String modelStr : modelList) {
            CodeView codeView = new CodeView(this);
            codeView.setCodeText(modelStr);
            codeLl.addView(codeView, lp);
        }
        if (loadingPd.isShowing()) {
            loadingPd.dismiss();
        }
    }

}
