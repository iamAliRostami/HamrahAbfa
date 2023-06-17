package com.aseem.versatileprogressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ProgBar extends RelativeLayout {
    private Context mContext;
    private AttributeSet attrs;
    private int styleAttr;
    private ImageView imageView;
    private TextView textMsg;

    public ProgBar(Context context) {
        super(context);
        initView();
    }

    public ProgBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.attrs = attrs;
        initView();
    }

    public ProgBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        this.attrs = attrs;
        this.styleAttr = defStyleAttr;
        initView();
    }

    private void initView() {
        inflate(mContext, R.layout.progress_bar_layout, this);

        TypedArray arr = mContext.obtainStyledAttributes(attrs, R.styleable.ProgBar, styleAttr, 0);

        Drawable imageFile = arr.getDrawable(R.styleable.ProgBar_barType);
        String customMsg = arr.getString(R.styleable.ProgBar_text);
        int textColor = arr.getColor(R.styleable.ProgBar_androidtextColor, Color.BLACK);
        float textSize = arr.getDimension(R.styleable.ProgBar_textSize, 16);
        int enlarge = arr.getInt(R.styleable.ProgBar_enlarge, 2);
        imageView = findViewById(R.id.progressImg);
        View progBg = findViewById(R.id.progBg);
        textMsg = findViewById(R.id.textMsg);


        if (imageFile != null) {
            setProgressVector(imageFile);
        }

        if (customMsg != null) {
            setTextMsg(customMsg);
        }

        setTextColor(textColor);
        setTextSize(textSize);
        enlarge(enlarge);

        arr.recycle();
    }

    public void setScaleType(ImageView.ScaleType scaleType) {
        imageView.setScaleType(scaleType);
    }

    public void setProgressVector(Drawable imageFile) {
        Glide.with(mContext)
                .load(imageFile)
                .into(imageView);
    }

    public void enlarge(int enlarge) {
        if (enlarge >= 1 && enlarge <= 10)
            imageView.getLayoutParams().height = enlarge * 100;
    }

    public void setTextMsg(String message) {
        textMsg.setText(message);
    }

    public void setTextColor(int color) {
        textMsg.setTextColor(color);
    }

    public void setTextSize(float size) {
        textMsg.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }


}
