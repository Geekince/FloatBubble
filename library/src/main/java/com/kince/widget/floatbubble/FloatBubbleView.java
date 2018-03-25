package com.kince.widget.floatbubble;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Kince
 * 浮动气泡容器
 * 实现思路如下：
 * 1.初始化默认气泡
 * 2.绘制浮动气泡
 * 3.开始暂停
 */
public class FloatBubbleView extends View {

    private static final String TAG = "FloatBubbleView";
    private static final int MSG_CREATE_BUBBLE = 0;// Handler Msg

    private static final int DEFAULT_BUBBLE_COUNT = 3;// 默认气泡数量

    protected int mWidth;
    protected int mHeight;

    private List<Bubble> mBubbles = new CopyOnWriteArrayList<>();
    private Random mRandom = new Random();
    private boolean mStarting = false;

    private TextPaint mTextPaint;//

    /**
     * 控制浮动方向
     */
    private boolean isUpDown = true;
    private boolean isLeftRight = true;
    private boolean isLeftUp = true;

    private int mCount = 10;   // 小球个数
    private int maxRadius;  // 小球最大半径
    private int minRadius; // 小球最小半径
    private int minSpeed = 1; // 小球最小移动速度
    private int maxSpeed = 5; // 小球最大移动速度

    public FloatBubbleView(Context context) {
        this(context, null);
    }

    public FloatBubbleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatBubbleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = resolveSize(mWidth, widthMeasureSpec);
        mHeight = resolveSize(mHeight, heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initDefaultConfig();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画气泡
        drawBubble(canvas);
    }

    @Override
    public void invalidate() {
        if (mStarting) {
            super.invalidate();
        }
    }

    private void initView() {
        mCount = DEFAULT_BUBBLE_COUNT;

        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(getResources().getDimensionPixelSize(R.dimen.x32));
    }

    private void initDefaultConfig() {
        initDefaultBubble(DEFAULT_BUBBLE_COUNT);

    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_CREATE_BUBBLE) {
                if (mStarting && mWidth > 0) {
                    invalidate();
                    //下次产生气泡的时间
                    mHandler.sendEmptyMessageDelayed(MSG_CREATE_BUBBLE, 50);
                }
            }
        }

    };

    private void initDefaultBubble(int count) {
        for (int i = 0; i < count; i++) {
            Bubble bubble = new Bubble();

            // 换金分割
            bubble.setRadius((float) (mWidth * 0.312));

            // 位居中心
            bubble.setX(mWidth / 2);
            bubble.setY(mHeight / 2);

            // 随机摆动幅度
            bubble.setSpeedX(mRandom.nextFloat() * 2.3f);
            bubble.setSpeedY(mRandom.nextFloat() * 2.3f);

            // 设置浮动类型
            if (i == 0) {
                Paint paint = new Paint();
                bubble.setPaint(paint);
                bubble.setType(FloatType.Float_UP_DOWN);
                bubble.setShaderColor(new int[]{Color.CYAN, Color.MAGENTA});
            } else if (i == 1) {
                Paint paint = new Paint();
                bubble.setPaint(paint);
                bubble.setType(FloatType.Float_LEFT_RIGHT);
                bubble.setShaderColor(new int[]{Color.RED, Color.YELLOW});
            } else if (i == 2) {
                Paint paint = new Paint();
                bubble.setPaint(paint);
                bubble.setType(FloatType.Float_LEFT_UP);
                bubble.setShaderColor(new int[]{Color.BLUE, Color.GREEN});
            } else if (i == 3) {
                bubble.setType(FloatType.Float_LEFT_DOWN);
            } else if (i == 4) {
                bubble.setType(FloatType.Float_RIGHT_UP);
            } else if (i == 5) {
                bubble.setType(FloatType.Float_RIGHT_DOWN);
            }

            //将气泡添加到集合
            mBubbles.add(bubble);
        }
    }

    private void drawBubble(Canvas canvas) {
        for (Bubble bubble : mBubbles) {

            float y = bubble.getY();
            float x = bubble.getX();
            float speedY = bubble.getSpeedY();
            float speedX = bubble.getSpeedX();
            float radius = bubble.getRadius();
            Paint paint = bubble.getPaint();

            //变换x y坐标
            if (bubble.getType() == FloatType.Float_UP_DOWN) {
                // X值不变，Y值上下浮动
                if (isUpDown) {
                    y -= speedY;
                } else {
                    y += speedY;
                }
                if (y <= mHeight / 2 - 20) {
                    isUpDown = false;
                } else if (y >= mHeight / 2 + 20) {
                    isUpDown = true;
                }
            } else if (bubble.getType() == FloatType.Float_LEFT_RIGHT) {
                // Y值不变，X值上下浮动
                if (isLeftRight) {
                    x -= speedX;
                } else {
                    x += speedX;
                }
                if (x <= mWidth / 2 - 20) {
                    isLeftRight = false;
                } else if (x >= mWidth / 2 + 20) {
                    isLeftRight = true;
                }
            } else if (bubble.getType() == FloatType.Float_LEFT_UP) {
                //
                if (isLeftUp) {
                    x -= speedX;
                    y -= speedY;
                } else {
                    x += speedX;
                    y += speedY;
                }
                if (x <= mWidth / 2 - 20 && y <= mHeight / 2 - 20) {
                    isLeftUp = false;
                } else if (x >= mWidth / 2 + 20 && y >= mHeight / 2 + 20) {
                    isLeftUp = true;
                }
            } else if (bubble.getType() == FloatType.Float_LEFT_DOWN) {

            } else if (bubble.getType() == FloatType.Float_RIGHT_UP) {

            } else if (bubble.getType() == FloatType.Float_RIGHT_DOWN) {

            }

            //减速 (加速减速可自行设置)
            speedY *= 0.99f;
            if (speedY < 1) {
                //最小上升速度
                speedY = 1;
            }
            speedX *= 0.99f;
            if (speedX < 1) {
                //最小上升速度
                speedX = 1;
            }

            bubble.setX(x);
            bubble.setY(y);
            bubble.setSpeedY(speedY);
            bubble.setSpeedX(speedX);
            //设置气泡渐变
            if (bubble.getShaderColor() != null && bubble.getShaderColor().length != 0) {
                RadialGradient shader = new RadialGradient(x, y, radius, bubble.getShaderColor(), null, Shader.TileMode.REPEAT);
                paint.setShader(shader);
            } else {
                paint.setColor(bubble.getOriginalColor());
                paint.setAlpha(50);
            }
            canvas.drawCircle(x, y, radius, paint);
        }
    }

    public void startFloatAnim() {
        if (!mStarting) {
            mStarting = true;
            invalidate();
            mHandler.sendEmptyMessage(MSG_CREATE_BUBBLE);
        }
    }

    /**
     * 停止气泡上升动画
     */
    public void stopFloatAnim() {
        if (mStarting) {
            mStarting = false;
            mHandler.removeMessages(MSG_CREATE_BUBBLE);
            mBubbles.clear();
            invalidate();
        }
    }

}
