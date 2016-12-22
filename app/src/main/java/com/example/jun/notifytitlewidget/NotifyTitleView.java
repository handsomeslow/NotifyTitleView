package com.example.jun.notifytitlewidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Jun on 2016/12/22.
 */

public class NotifyTitleView extends RelativeLayout {
    public static int STYLE_GREEN = 10;
    public static int STYLE_BLUE = 11;
    public static int STYLE_GRY = 12;

    private int style;

    private String lebalName;

    private int lebalBackground;

    public NotifyTitleView(Context context) {
        super(context);
        initView();
    }

    public NotifyTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttrs(context,attrs);
        initView();
    }

    public NotifyTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttrs(context,attrs);
        initView();
    }

    void getAttrs(Context context, AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.NotifyTitleView);
        lebalName = typedArray.getString(R.styleable.NotifyTitleView_lebal);
        lebalBackground = typedArray.getResourceId(R.styleable.NotifyTitleView_lebalBackgroud,0);
        typedArray.recycle();
    }

    void initView(){

        TextView lebalTv = new TextView(getContext());
        lebalTv.setText(lebalName);
        lebalTv.setTextColor(Color.WHITE);
        lebalTv.setBackgroundResource(lebalBackground);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,120);
        lebalTv.setLayoutParams(layoutParams);
        this.addView(lebalTv);

        TextView deleteTv = new TextView(getContext());
        deleteTv.setText("删除");
        deleteTv.setTextColor(Color.WHITE);
        deleteTv.setBackgroundColor(Color.RED);
        this.addView(deleteTv);


        TextView infoTv = new TextView(getContext());
        infoTv.setText("帮助");
        infoTv.setTextColor(Color.WHITE);
        infoTv.setBackgroundColor(Color.GREEN);
        this.addView(infoTv);
    }

    public String getLebalName() {
        return lebalName;
    }

    public void setLebalName(String lebalName) {
        this.lebalName = lebalName;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }
}
