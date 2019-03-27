package atirek.pothiwala.utility.views;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.AppCompatImageButton;
import android.util.AttributeSet;


/**
 * Created by Admin on 11/27/2017.
 */

public class SquareImageButton extends AppCompatImageButton {

    public SquareImageButton(Context context) {
        super(context);
        init();
    }

    public SquareImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SquareImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStateListAnimator(null);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}

