package com.goldmantis.shaystudy.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.goldmantis.shaystudy.R;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: 金螳螂企业（集团）有限公司
 * @Date: 2018/1/18 09:54
 * @Version:
 * @Description: 带删除按钮的editText
 */

public class CustomEditTextClearView extends LinearLayout 
{
    private EditText etContent;
    private ImageView imgClear;
    private TextWatcher textWatcher;
    public CustomEditTextClearView(Context context) {
        this(context,null);
    }

    public CustomEditTextClearView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomEditTextClearView(Context context, @Nullable AttributeSet attrs, int 
            defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) 
    {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable
                .CustomEditTextClearView, 0, 0);
        int etColor=a.getColor(R.styleable.CustomEditTextClearView_et_text_color, ContextCompat.getColor(getContext(), R.color.colorAccent));
        float etTextSize = a.getDimension(R.styleable.CustomEditTextClearView_et_text_size, getResources().getDimension(R.dimen.sp10));
        String etHint = a.getString(R.styleable.CustomEditTextClearView_et_hint);
        int imgResourceId = a.getResourceId(R.styleable.CustomEditTextClearView_img_resource, -1);
        setOrientation(HORIZONTAL);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.custom_edit_clear_layout,
                this, true);
        etContent = (EditText) view.findViewById(R.id.et_text_show);
        imgClear = (ImageView) view.findViewById(R.id.img_clear_all);
        etContent.setTextSize(etTextSize);
        
        etContent.setTextSize(TypedValue.COMPLEX_UNIT_PX,etTextSize);
        etContent.setTextColor(etColor);
        etContent.setHint(etHint);
        imgClear.setImageResource(imgResourceId);
        if (textWatcher == null) {
            initTextWatch();
        }
       etContent.addTextChangedListener(textWatcher);
        imgClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) 
            {
                etContent.setText("");
                imgClear.setVisibility(GONE);
            }
        });
    }

    private void initTextWatch() 
    {
        textWatcher = new TextWatcher() 
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) 
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) 
            {

            }

            @Override
            public void afterTextChanged(Editable s) 
            {
                String inputText = s.toString().trim();
                if (TextUtils.isEmpty(inputText))
                {
                    imgClear.setVisibility(GONE);
                }else 
                {
                    imgClear.setVisibility(VISIBLE);
                }
            }
        };
    }

   

    /**
     * 获取最后的字符串
     * @return
     */
    public String getEditTextString()
    {
        return etContent.getText().toString().trim();
    }

    /**
     * 设置hint内容
     */
    public void setHint(CharSequence sequence)
    {
        etContent.setHint(sequence);
    }
    
     
    
}
