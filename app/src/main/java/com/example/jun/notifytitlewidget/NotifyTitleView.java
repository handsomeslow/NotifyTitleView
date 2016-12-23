package com.example.jun.notifytitlewidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Jun on 2016/12/22.
 */

public class NotifyTitleView extends RelativeLayout{
    public static int STYLE_GREEN = 10;
    public static int STYLE_BLUE = 11;
    public static int STYLE_GRY = 12;

    private static int LEBAL_ID = 11111;
    private static int DELETE_ID = 11112;
    private static int HELP_ID = 11113;

    private int style;

    private String lebalName;

    private int lebalBackground;

    TextView deleteTv;
    TextView lebalTv;
    TextView helpTv;
    int lebalWidth;
    int deleteWidth;


    public NotifyTitleView(Context context) {
        super(context);
        initView();
    }

    public NotifyTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttrs(context, attrs);
        initView();
    }

    public NotifyTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttrs(context, attrs);
        initView();
    }

    void getAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NotifyTitleView);
        lebalName = typedArray.getString(R.styleable.NotifyTitleView_lebal);
        lebalBackground = typedArray.getResourceId(R.styleable.NotifyTitleView_lebalBackgroud, 0);
        lebalWidth = typedArray.getDimensionPixelSize(R.styleable.NotifyTitleView_lebalWidth,280);
        Log.d("jun", "getAttrs: "+lebalWidth);
        typedArray.recycle();
    }

    void initView() {
        lebalTv = new TextView(getContext());
        lebalTv.setId(LEBAL_ID);
        lebalTv.setGravity(Gravity.CENTER);
        lebalTv.setPadding(0, 0, 20, 0);
        lebalTv.setText(lebalName);
        lebalTv.setTextSize(20);
        lebalTv.setTextColor(Color.WHITE);
        lebalTv.setBackgroundResource(lebalBackground);
        LayoutParams layoutParams = new LayoutParams(lebalWidth, LayoutParams.MATCH_PARENT);

        deleteTv = new TextView(getContext());
        deleteTv.setId(DELETE_ID);
        deleteTv.setGravity(Gravity.CENTER);
        deleteTv.setPadding(40, 0, 40, 0);
        deleteTv.setText("清除Cookie");
        deleteTv.setTextSize(15);
        deleteTv.setTextColor(Color.WHITE);
        deleteTv.setBackgroundColor(Color.RED);
        LayoutParams layoutParams1 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        layoutParams1.setMargins(lebalWidth-30, 0, 0, 0);
        deleteTv.setVisibility(GONE);
        deleteTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onNotifyClickListener!=null){
                    onNotifyClickListener.onDeleteClick(view);
                }
            }
        });

        int width =View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        int height =View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        deleteTv.measure(width,height);
        deleteWidth=deleteTv.getMeasuredWidth();

        helpTv = new TextView(getContext());
        helpTv.setId(HELP_ID);
        helpTv.setGravity(Gravity.CENTER);
        helpTv.setText("帮助");
        helpTv.setPadding(40, 0, 40, 0);
        helpTv.setTextColor(Color.WHITE);
        helpTv.setTextSize(15);
        helpTv.setBackgroundColor(Color.parseColor("#E54B00"));
        LayoutParams layoutParams3 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        //layoutParams3.addRule(RIGHT_OF, deleteTv.getId());
        layoutParams3.setMargins(lebalWidth + deleteWidth-30, 0, 0, 0);
        helpTv.setVisibility(GONE);
        helpTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onNotifyClickListener!=null){
                    onNotifyClickListener.onHelpClick(view);
                }
            }
        });

        this.addView(helpTv, layoutParams3);
        this.addView(deleteTv, layoutParams1);
        this.addView(lebalTv, layoutParams);

        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        lp.addRule(CENTER_IN_PARENT);
        this.setLayoutParams(lp);
    }

    public int getTitleViewState(){

        return deleteTv.getVisibility();
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

    private void showViewFromLeft(final View view,float ditectX,long duration) {
        TranslateAnimation translateAnimation = new TranslateAnimation(-1*ditectX, 0, 0, 0);
        translateAnimation.setDuration(duration);
        view.startAnimation(translateAnimation);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void hideView(final View view,float ditectX,long duration){
        TranslateAnimation translateAnimation = new TranslateAnimation(0, -1*ditectX, 0, 0);
        translateAnimation.setDuration(duration);
        view.startAnimation(translateAnimation);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    // 延迟隐藏工具栏handler
    protected Handler hideHandler;
    protected Runnable runnable;

    public void notifyView(){
        showDeteleAndInfoView();
        if (hideHandler == null)
            hideHandler = new Handler();

        if (runnable == null) {
            runnable = new Runnable() {
                public void run() {
                    hideDeteleAndInfoView();
                }
            };
        }
        hideHandler.removeCallbacks(runnable);
        hideHandler.postDelayed(runnable, 2000);
    }

    public void showDeleteView(){
        showViewFromLeft(deleteTv,300f,500);
    }

    public void hideDeleteView(){
        hideView(deleteTv,300f,500);
    }

    public void showHelpView(){
        LayoutParams layoutParams3 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        layoutParams3.setMargins(lebalWidth -30, 0, 0, 0);
        helpTv.setLayoutParams(layoutParams3);
        showViewFromLeft(helpTv,200f,500);
    }

    public void hideHelpView(){
        hideView(helpTv,200f,500);
    }

    public void showDeteleAndInfoView(){
        LayoutParams layoutParams3 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        layoutParams3.setMargins(lebalWidth+ deleteWidth-30, 0, 0, 0);
        helpTv.setLayoutParams(layoutParams3);
        showViewFromLeft(deleteTv,300f,500);
        showViewFromLeft(helpTv,600f,700);
    }

    public void hideDeteleAndInfoView(){
        hideView(helpTv,600f,700);
        hideView(deleteTv,300f,500);
    }

    public void setOnNotifyClickListener(OnNotifyClickListener onNotifyClickListener) {
        this.onNotifyClickListener = onNotifyClickListener;
    }

    private OnNotifyClickListener onNotifyClickListener;
    public interface OnNotifyClickListener{
        void onDeleteClick(View view);

        void onHelpClick(View view);
    }

    public static int MODEL_HELP = 9001;
    public static int MODEL_HELP_WITH_DETELE = 9002;
    public static int MODEL_NOFITY = 9003;

    private int model;

    public void skipView(int model){
        this.model = model;
        switch (model){
            case 9001:
                showHelpView();
                break;

            case 9002:
                showDeteleAndInfoView();
                break;

            case 9003:
                notifyView();
                break;
        }
    }

    public void hideView(){
        switch (model){
            case 9001:
                hideHelpView();
                break;

            case 9002:
                hideDeteleAndInfoView();
                break;
        }
    }


}
