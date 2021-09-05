package NewsUI;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.example.newsdaily.MainActivity;
import com.example.newsdaily.R;
import com.example.newsdaily.MainActivity;
import java.lang.reflect.Type;

public class SearchBar extends androidx.appcompat.widget.AppCompatEditText {

    private Drawable magnifierIcon;
    private Drawable clearIcon;

    public SearchBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setSingleLine(true);
        TypedArray a = context.obtainStyledAttributes(
                attrs,
                R.styleable.SearchBar,
                0, 0);
        try {
            magnifierIcon = a.getDrawable(R.styleable.SearchBar_magnifierIcon);
            clearIcon = a.getDrawable(R.styleable.SearchBar_clearIcon);
        } finally {
            a.recycle();
            setCompoundDrawablesRelativeWithIntrinsicBounds(magnifierIcon, null, null, null);
        }
        this.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        toggleClearIcon(isFocused());
    }

    private void toggleClearIcon(boolean isFocused) {
        if (getText().toString().isEmpty() || !isFocused) {
            setCompoundDrawablesRelativeWithIntrinsicBounds(magnifierIcon, null, null, null);
        } else {
            setCompoundDrawablesRelativeWithIntrinsicBounds(magnifierIcon, null, clearIcon, null);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

        }

        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event != null) {
            if (clearIcon != null) {
                 if (event.getAction() == MotionEvent.ACTION_UP
                     && event.getX() > getMeasuredWidth() - clearIcon.getIntrinsicWidth() - 20
                     && event.getX() < getMeasuredWidth() + 20
                     && event.getY() > getMeasuredHeight() / 2 - clearIcon.getIntrinsicHeight() / 2 + 20
                     && event.getY() < getMeasuredHeight() / 2 + clearIcon.getIntrinsicHeight() / 2 + 20
                 ) {
                     setText("");
                 }
            }
        }
        performClick();
        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        toggleClearIcon(isFocused());
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }
}
