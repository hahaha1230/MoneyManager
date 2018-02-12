package com.example.a25467.moneymanager;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by 25467 on 2018/2/12.
 */

public class CircleImageView extends ImageView {
    private static final  ScaleType SCALE_TYPE=ScaleType.CENTER_CROP;
    private static  final Bitmap.Config BITMAP_CONFIG=Bitmap.Config.ARGB_8888;
    private static final int COLORDRAWABLE_DIMENSION=1;
    private static final  int DEFAULT_BORDER_WIDTH=0;
    private static  final  int DEFAULT_BORDER_COLOR= Color.BLACK;
    private final RectF mDrawableRect=new RectF();
    private final RectF mBorderRect=new RectF();
    private final Matrix mShaderMatrix=new Matrix();
    private final Paint mBitmapPaint=new Paint();
    private final Paint mBorderPaint=new Paint();
    private int mBorderColor=DEFAULT_BORDER_COLOR;
    private int mBorderWidth=DEFAULT_BORDER_WIDTH;
    private Bitmap mBitmap;
    private BitmapShader mBitmapShader;
    private  int mBitmapWidth;
    private int mBitmapHeight;
    private float mDrawableRadius;
    private float mBorderRadius;
    private boolean mReady;
    private boolean mSetupPending;
    public CircleImageView(Context context, AttributeSet attrs){
        this(context,attrs,0);
    }
    public CircleImageView(Context context,AttributeSet attrs,int defStyle){
        super(context,attrs,defStyle);
        super.setScaleType(SCALE_TYPE);
        TypedArray a=context.obtainStyledAttributes(attrs,R.styleable.CircleImageView,defStyle,0);
        mBorderWidth=a.getDimensionPixelSize(R.styleable.CircleImageView_civ_border_width,DEFAULT_BORDER_WIDTH);
        mBorderColor=a.getColor(R.styleable.CircleImageView_civ_border_color,DEFAULT_BORDER_COLOR);
        a.recycle();
        mReady=true;
        if (mSetupPending){
            setup();
            mSetupPending=false;
        }
    }
    @Override
    public ScaleType getScaleType(){
        return SCALE_TYPE;
    }

    @Override
    protected void onDraw(Canvas canvas) {
       if (getDrawable()==null){
           return;
       }
       canvas.drawCircle(getWidth()/2,getHeight()/2,mDrawableRadius,mBitmapPaint);
       canvas.drawCircle(getWidth()/2,getHeight()/2,mBorderRadius,mBorderPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setup();
    }
    public int getBorderColor(){
        return mBorderColor;
    }
    public void setBorderColor(int borderColor){
        if (borderColor==mBorderColor){
            return;
        }
        mBorderColor=borderColor;
        mBorderPaint.setColor(mBorderColor);
        invalidate();
    }
    public int getBorderWidth(){
        return mBorderWidth;
    }
    public void setBorderWidth(int borderWidth){
        if (borderWidth==mBorderWidth){
            return;
        }
        mBorderWidth=borderWidth;
        setup();
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        mBitmap=bm;
        setup();
    }

    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        super.setImageDrawable(drawable);
        mBitmap=getBitmapFromDrawable(drawable);
        setup();
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        mBitmap=getBitmapFromDrawable(getDrawable());
        setup();
    }
    private Bitmap getBitmapFromDrawable(Drawable drawable){
        if (drawable==null){
            return null;
        }
        if (drawable instanceof BitmapDrawable){
            return ((BitmapDrawable)drawable).getBitmap();
        }
        try {
            Bitmap bitmap;
            if (drawable instanceof ColorDrawable){
                bitmap=Bitmap.createBitmap(COLORDRAWABLE_DIMENSION,COLORDRAWABLE_DIMENSION,BITMAP_CONFIG);
            }
            else {
                ImageSizeUtil.ImageSize imageSize = ImageSizeUtil.getImageViewSize(this);
                bitmap = Bitmap.createBitmap(imageSize.width,
                        imageSize.height, BITMAP_CONFIG);
            }

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError e) {
            return null;
        }
    }

    /**
     * 画圆形图的方法
     */
    private void setup() {
        if (!mReady) {
            mSetupPending = true;
            return;
        }

        if (mBitmap == null) {
            return;
        }
        /**
         *调用这个方法来产生一个画有一个位图的渲染器（Shader）。
         bitmap   在渲染器内使用的位图
         tileX      The tiling mode for x to draw the bitmap in.   在位图上X方向花砖模式
         tileY     The tiling mode for y to draw the bitmap in.    在位图上Y方向花砖模式
         TileMode：（一共有三种）
         CLAMP  ：如果渲染器超出原始边界范围，会复制范围内边缘染色。
         REPEAT ：横向和纵向的重复渲染器图片，平铺。
         MIRROR ：横向和纵向的重复渲染器图片，这个和REPEAT 重复方式不一样，他是以镜像方式平铺。
         */
        mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        /**
         * 设置画圆形的画笔
         */
        mBitmapPaint.setAntiAlias(true);//设置抗锯齿
        mBitmapPaint.setShader(mBitmapShader);//绘制图形时的图形变换

        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(mBorderColor);
        mBorderPaint.setStrokeWidth(mBorderWidth);

        mBitmapHeight = mBitmap.getHeight();
        mBitmapWidth = mBitmap.getWidth();
        /**
         * 设置边框矩形的坐标
         */
        mBorderRect.set(0, 0, getWidth(), getHeight());
        /**
         * 设置边框圆形的半径为图片的宽度和高度的一半的最大值
         */
        mBorderRadius = Math.max((mBorderRect.height() - mBorderWidth) / 2, (mBorderRect.width() - mBorderWidth) / 2);
        /**
         * 设置图片矩形的坐标
         */
        mDrawableRect.set(mBorderWidth, mBorderWidth, mBorderRect.width() - mBorderWidth, mBorderRect.height() - mBorderWidth);
        /**
         * 设置图片圆形的半径为图片的宽度和高度的一半的最大值
         */
        mDrawableRadius = Math.max(mDrawableRect.height() / 2, mDrawableRect.width() / 2);

        updateShaderMatrix();
        /**
         * 调用onDraw()方法进行绘画
         */
        invalidate();
    }

    /**
     * 更新位图渲染
     */
    private void updateShaderMatrix() {
        float scale;
        float dx = 0;
        float dy = 0;
        /**
         * 重置
         */
        mShaderMatrix.set(null);
        /**
         *计算缩放比，因为如果图片的尺寸超过屏幕，那么就会自动匹配到屏幕的尺寸去显示。
         * 确定移动的xy坐标
         *
         */
        if (mBitmapWidth * mDrawableRect.height() > mDrawableRect.width() * mBitmapHeight) {
            scale = mDrawableRect.width() / (float) mBitmapWidth;
            dy = (mDrawableRect.height() - mBitmapHeight * scale) * 0.5f;
        } else {
            scale = mDrawableRect.height() / (float) mBitmapHeight;
            dx = (mDrawableRect.width() - mBitmapWidth * scale) * 0.5f;
        }

        mShaderMatrix.setScale(scale, scale);
        mShaderMatrix.postTranslate((int) (dx + 0.5f) + mBorderWidth, (int) (dy + 0.5f) + mBorderWidth);
        /**
         * 设置shader的本地矩阵
         */
        mBitmapShader.setLocalMatrix(mShaderMatrix);
    }

}
