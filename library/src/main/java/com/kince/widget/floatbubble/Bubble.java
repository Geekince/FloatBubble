package com.kince.widget.floatbubble;

import android.graphics.Paint;

/**
 * 代表每个气泡
 * 半径、x坐标、y坐标
 */
public class Bubble {

    private Paint paint;

    /**
     * 气泡半径
     */
    private float radius;
    /**
     * 上升速度
     */
    private float speedY;
    /**
     * 平移速度
     */
    private float speedX;
    /**
     * 气泡x坐标
     */
    private float x;
    /**
     * 气泡y坐标
     */
    private float y;

    /**
     * 纯色背景色
     */
    private int originalColor;

    /**
     * 渐变背景色
     */
    private int[] shaderColor;

    private FloatType type;

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    public float getSpeedX() {
        return speedX;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public FloatType getType() {
        return type;
    }

    public void setType(FloatType type) {
        this.type = type;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public int getOriginalColor() {
        return originalColor;
    }

    public void setOriginalColor(int originalColor) {
        this.originalColor = originalColor;
    }

    public int[] getShaderColor() {
        return shaderColor;
    }

    public void setShaderColor(int[] shaderColor) {
        this.shaderColor = shaderColor;
    }

// 移动
//    void move() {
//        //向角度的方向移动，偏移圆心
//        cx += vx;
//        cy += vy;
//    }

}