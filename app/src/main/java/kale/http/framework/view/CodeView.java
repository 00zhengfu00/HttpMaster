package kale.http.framework.view;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import kale.framework.R;
import kale.http.framework.util.ClipBoardUtil;

/**
 * @author Jack Tony
 * @date 2015/8/6
 */
public class CodeView extends LinearLayout {

    public CodeView(Context context) {
        this(context, null);
    }

    public CodeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private ShaderEditor modelSe;
    
    private void initViews() {
        setOrientation(HORIZONTAL);
        inflate(getContext(), R.layout.model_view, this);
        
        modelSe = (ShaderEditor) findViewById(android.R.id.text1);
        Button copyModelBtn = (Button) findViewById(R.id.copy_model_btn);
        copyModelBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // model的内容体，不含类名的主体内容
                String modelContent = modelSe.getText().toString();
                if (modelContent.contains("{")) {
                    modelContent = modelContent.substring(modelContent.indexOf("{") + 1, modelContent.length() - 1);
                }  
                ClipBoardUtil.copy(getContext(), modelContent);
                final Snackbar snackbar = Snackbar.make((ViewGroup) getParent(), "Has been copied to the clipboard", Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });
    }

    public void setCodeText(String text) {
        modelSe.setTextHighlighted(text);
    }
    
}