package com.hehao.hehaolibrary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hehao.hehaolibrary.R;
import com.hehao.hehaolibrary.util.HHUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 带上拉下拉刷新的listview
 * @author HeHao
 * @time 2015/11/6 10:00
 * @email 139940512@qq.com
 */
public class HHRefreshListView extends ListView implements AbsListView.OnScrollListener{
    /** 定义状态常量
     * FLAG_NORMAL:     0、正常状态  </br>
     * FLAG_PULLING:    1、拉动状态  </br>
     * FLAG_REFRESHING: 2、正在刷新状态    </br>
     * FLAG_RELEASE:    3、请释放状态 </br>
     */
    private int state;
    private static final int FLAG_NORMAL = 0;
    private static final int FLAG_PULLING = 1;
    private static final int FLAG_REFRESHING = 2;
    private static final int FLAG_RELEASE = 3   ;

    private final static int RATIO = 3;// 移动的比例

    private boolean isScrollTop;
    private boolean isScrollBottom;

    //是否开启的了上拉下拉刷新，如果设置了对应的listener则认为开启了
    private boolean isPullDownRefresh;
    private boolean isPullUpRefresh;

    private OnRefreshListener pullUpListener;
    private OnRefreshListener pullDownListener;

    private boolean isRecording;
    private boolean isBack;

    private int startY;

    /**滚动方向*/
    private int direction;
    private final static int TO_UP = 1; //向上
    private final static int TO_DOWN = 2;   //向下

    private LayoutInflater inflater ;
    /**头部和尾部*/
    private LinearLayout headerView, footerView;
    /**头部和尾部箭头图标*/
    private ImageView imgHeadArrow, imgFootArrow;
    /**头部和尾部文字*/
    private TextView tvHeadHint, tvHeadTime, tvFootHint, tvFootTime;
    /**控件头部和尾部高度*/
    private int headerHeight, footerHeight;
    /**箭头动画效果*/
    private RotateAnimation animation;
    private RotateAnimation reverseAnimation;

    public HHRefreshListView(Context context) {
        super(context);
        init(context);
    }

    public HHRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public String getNowTime() {
        Date d = new Date(System.currentTimeMillis());
        // String type = "yyyy-MM-dd HH:mm:ss";
        String type = "HH:mm:ss";
        SimpleDateFormat formatter = new SimpleDateFormat(type, Locale.CHINA);
        return formatter.format(d);
    }

    public interface OnRefreshListener {
        public void onRefresh(HHRefreshListView lv, boolean first);
    }

    /**
     * 设置上拉刷新监听器
     * @param listener  监听器
     */
    public void setPullUpRefreshListener(OnRefreshListener listener){
        pullUpListener = listener;
        isPullUpRefresh = true;
    }

    /**
     * 设置下拉刷新监听器
     * @param listener
     */
    public void setPullDownRefreshListener(OnRefreshListener listener){
        pullDownListener = listener;
        isPullDownRefresh = true;
    }
    /**
     * 初始化函数
     * @param context   上下文参数
     */
    private void init(Context context){
        inflater = LayoutInflater.from(context);
        initHeader();
        initFooter();
        initAnim();

        //初始化状态量
        state = FLAG_NORMAL;
        isScrollTop = false;
        isScrollBottom = false;
        isPullDownRefresh = false;
        isPullUpRefresh = false;

        setOnScrollListener(this);
    }

    /**
     * 初始化头部
     */
    private void initHeader(){
        headerView = (LinearLayout)inflater.inflate(R.layout.hhlistview_header, null);
        imgHeadArrow = HHUtils.getView(headerView, R.id.hhlistview_arrow);
        tvHeadHint = HHUtils.getView(headerView, R.id.hhlistview_header_hint);
        tvHeadTime = HHUtils.getView(headerView, R.id.hhlistview_header_time);

        headerHeight = getResources().getDimensionPixelSize(R.dimen.hhlistview_height);
        headerView.setPadding(0, -1 * headerHeight, 0, 0);
        headerView.invalidate();
        addHeaderView(headerView);
    }

    /**
     * 初始化尾部
     */
    private void initFooter(){
        footerView = (LinearLayout)inflater.inflate(R.layout.hhlistview_footer, null);
        imgFootArrow = HHUtils.getView(footerView, R.id.hhlistview_foot_arrow);
        tvFootHint = HHUtils.getView(footerView, R.id.hhlistview_footer_hint);
        tvFootTime = HHUtils.getView(footerView, R.id.hhlistview_footer_time);

        footerHeight = getResources().getDimensionPixelSize(R.dimen.hhlistview_height);
        footerView.setPadding(0, 0, 0, -1 * footerHeight);
        footerView.invalidate();
        addFooterView(footerView);
    }

    /**
     * 初始化动画参数
     */
    private void initAnim() {
        animation = new RotateAnimation(0, -180,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(250);
        animation.setFillAfter(true);
        reverseAnimation = new RotateAnimation(-180, 0,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        reverseAnimation.setInterpolator(new LinearInterpolator());
        reverseAnimation.setDuration(200);
        reverseAnimation.setFillAfter(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //如果开启了上拉或者下拉刷新且在正在刷新状态
        if((isPullDownRefresh || isPullUpRefresh) && state != FLAG_REFRESHING){
            switch (ev.getAction()){
                case MotionEvent.ACTION_DOWN:
                    if(!(isScrollTop||isScrollBottom))
                        break;
                    if(!isRecording){
                        isRecording = true;
                        startY = (int) ev.getY();
                        direction = 0;
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if(!(isScrollTop||isScrollBottom))
                        break;
                    int tempY = (int)ev.getY();
                    if(!isRecording){
                        startY = tempY;
                        isRecording = true;
                        direction = 0;
                    }

                    //开始检测
                    if(state!=FLAG_REFRESHING && isRecording){
                        int distance = tempY - startY;
                        int abs = Math.abs(distance);
                        if(state == FLAG_NORMAL){
                            if(distance != 0){
                                if(distance > 0)
                                    direction = TO_DOWN;
                                else
                                    direction = TO_UP;
                                state = FLAG_PULLING;
                                changeViewByState();
                            }
                        }
                        if(direction==TO_DOWN && !isPullDownRefresh){
                            state = FLAG_NORMAL;
                            break;
                        }
                        if(direction==TO_UP && !isPullUpRefresh){
                            state = FLAG_NORMAL;
                            break;
                        }
                        if(state == FLAG_PULLING){
                            if(direction == TO_DOWN){
                                if(distance/RATIO >= headerHeight){
                                    state = FLAG_RELEASE;
                                    isBack = true;
                                    changeViewByState();
                                }else if(distance < 0){
                                    state = FLAG_NORMAL;
                                    changeViewByState();
                                }
                                headerView.setPadding(0,distance/RATIO-headerHeight, 0, 0);
                            }
                            else if(direction == TO_UP){
                                if(abs/RATIO >= footerHeight){
                                    state = FLAG_RELEASE;
                                    isBack = true;
                                    changeViewByState();
                                }else if(distance >= 0){
                                    state = FLAG_NORMAL;
                                    changeViewByState();
                                }
                                footerView.setPadding(0, 0, 0, abs/RATIO-footerHeight);
                            }
                        }
                        //到了可以松手刷新的状态了
                        if(state == FLAG_RELEASE){
                            if(direction == TO_DOWN){
                                if((distance/RATIO<headerHeight) && distance>0){
                                    state = FLAG_PULLING;
                                    changeViewByState();
                                }else if(distance<=0){
                                    state = FLAG_NORMAL;
                                    changeViewByState();
                                }
                                headerView.setPadding(0,distance/RATIO-headerHeight, 0, 0);
                            }
                            else if(direction == TO_UP){
                                if((abs/RATIO<footerHeight) && distance<0){
                                    state = FLAG_PULLING;
                                    changeViewByState();
                                }else if(distance>=0){
                                    state = FLAG_NORMAL;
                                    changeViewByState();
                                }
                                footerView.setPadding(0, 0, 0, abs/RATIO - footerHeight);
                            }
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if(!(isScrollTop||isScrollBottom))
                        break;
                    if(state != FLAG_REFRESHING){
                        if(state == FLAG_PULLING){
                            state = FLAG_NORMAL;
                            changeViewByState();
                        }
                        if(state == FLAG_RELEASE){
                            state = FLAG_REFRESHING;
                            changeViewByState();
                            if(direction == TO_DOWN){
                                if(pullDownListener != null){
                                    pullDownListener.onRefresh(this, false);
                                }
                            }else if(direction == TO_UP){
                                if(pullUpListener != null){
                                    pullUpListener.onRefresh(this, false);
                                }
                            }
                        }
                    }
                    isRecording = false;
                    isBack = false;
                    break;
            }
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 根据控件状态改变控件
     */
    private void changeViewByState() {
        if(direction == TO_DOWN){
            switch (state){
                case FLAG_RELEASE:
                    imgHeadArrow.setVisibility(View.VISIBLE);
                    tvHeadHint.setVisibility(View.VISIBLE);
                    tvHeadTime.setVisibility(View.VISIBLE);
                    imgHeadArrow.clearAnimation();
                    imgHeadArrow.startAnimation(animation);
                    tvHeadHint.setText(getResources().getString(R.string.hhlistview_release_refresh));
                    break;
                case FLAG_PULLING:
                    tvHeadHint.setVisibility(View.VISIBLE);
                    tvHeadTime.setVisibility(View.VISIBLE);
                    imgHeadArrow.clearAnimation();
                    imgHeadArrow.setVisibility(View.VISIBLE);
                    if(isBack){
                        isBack = false;
                        imgHeadArrow.clearAnimation();
                        imgHeadArrow.startAnimation(reverseAnimation);
                        tvHeadHint.setText(getResources().getString(R.string.hhlistview_pull_down));
                    }else{
                        tvHeadHint.setText(getResources().getString(R.string.hhlistview_pull_down));
                    }
                    break;
                case FLAG_REFRESHING:
                    headerView.setPadding(0,0,0,0);
                    imgHeadArrow.clearAnimation();
                    imgHeadArrow.setVisibility(View.GONE);
                    tvHeadHint.setText(getResources().getString(R.string.hhlistview_hint_loading));
                    tvHeadTime.setVisibility(View.VISIBLE);
                    break;
                case FLAG_NORMAL:
                    headerView.setPadding(0,-1*headerHeight, 0, 0);
                    imgHeadArrow.clearAnimation();
                    imgHeadArrow.setImageResource(R.drawable.refresh_arrow_top);
                    tvHeadHint.setText(getResources().getString(R.string.hhlistview_hint_loaded));
                    tvHeadTime.setVisibility(View.VISIBLE);
                    break;
            }
        }else if(direction == TO_UP){
            switch (state){
                case FLAG_RELEASE:
                    imgFootArrow.setVisibility(View.VISIBLE);
                    tvFootHint.setVisibility(View.VISIBLE);
                    tvFootTime.setVisibility(View.VISIBLE);
                    imgFootArrow.clearAnimation();
                    imgFootArrow.startAnimation(animation);
                    tvFootHint.setText(getResources().getString(R.string.hhlistview_release_loadmore));
                    break;
                case FLAG_PULLING:
                    tvFootHint.setVisibility(View.VISIBLE);
                    tvFootTime.setVisibility(View.VISIBLE);
                    imgFootArrow.clearAnimation();
                    imgFootArrow.setVisibility(View.VISIBLE);
                    if(isBack){
                        isBack = false;
                        imgFootArrow.clearAnimation();
                        imgFootArrow.startAnimation(reverseAnimation);
                        tvFootHint.setText(getResources().getString(R.string.hhlistview_pull_up));
                    }else{
                        tvFootHint.setText(getResources().getString(R.string.hhlistview_pull_up));
                    }
                    break;
                case FLAG_REFRESHING:
                    imgFootArrow.clearAnimation();
                    imgFootArrow.setVisibility(View.GONE);
                    tvFootHint.setText(getResources().getString(R.string.hhlistview_hint_loading));
                    tvFootTime.setVisibility(View.VISIBLE);
                    break;
                case FLAG_NORMAL:
                    footerView.setPadding(0,-1*footerHeight,0,0);
                    imgFootArrow.clearAnimation();
                    imgFootArrow.setImageResource(R.drawable.refresh_arrow_top);
                    tvFootHint.setText(getResources().getString(R.string.hhlistview_hint_loaded));
                    tvFootTime.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if(state == FLAG_NORMAL){
            if(firstVisibleItem == 0)
                isScrollTop = true;
            else
                isScrollTop = false;

            if(getLastVisiblePosition() == visibleItemCount-1)
                isScrollBottom = true;
            else
                isScrollBottom = false;
        }
    }

    public void OnRefreshComplete(){
        state = FLAG_NORMAL;
        if(direction == TO_DOWN){
            tvHeadTime.setText("上次更新:" + getNowTime());
        }else if(direction == TO_UP){
            tvFootTime.setText("上次更新:" + getNowTime());
        }
        changeViewByState();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {}
}