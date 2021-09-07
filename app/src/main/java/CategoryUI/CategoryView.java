package CategoryUI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.newsdaily.R;

public class CategoryView extends FrameLayout {

    String category;
    TextView textView;
    private PointF initPos;
    private PointF movePos;
    boolean isClicked = false;

    public CategoryView(@NonNull Context context, String _category, int x, int y) {
        super(context);
        category = _category;
        init();
    }

    public void init() {
        textView = new TextView(getContext());
        textView.setText(category);
        textView.setBackgroundResource(R.drawable.ic_b_bg);
        textView.setTextColor(Color.BLACK);
        textView.setPadding(10, 10, 10, 10);

        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(layoutParams);

        this.addView(textView);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();

        textView.setX(100);
        textView.setY(100);

        canvas.restore();
        super.dispatchDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Rect rect = new Rect();
                int[] textViewLocation = new int[2];
                textView.getLocationOnScreen(textViewLocation);
                rect.left = textViewLocation[0];
                rect.top = textViewLocation[1];
                rect.right = rect.left + textView.getWidth();
                rect.bottom = rect.top + textView.getHeight();

                if (rect.contains((int)event.getRawX(), (int)event.getRawY())) {

                }

                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
        }
        postInvalidate();
        return true;
    }
}

