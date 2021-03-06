package edu.us.sports4u.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.text.*;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import edu.us.sports4u.R;
import android.widget.AdapterView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by Oksana on 17.08.2015.
 */
public class ChipsMultiAutoCompleteEditText extends MultiAutoCompleteTextView implements AdapterView.OnItemClickListener {
    private final String TAG = "ChipsMultiAutoCompleteEditText";

    /* Constructor */
    public ChipsMultiAutoCompleteEditText(Context context) {
        super(context);
        init(context);
    }

    /* Constructor */
    public ChipsMultiAutoCompleteEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /* Constructor */
    public ChipsMultiAutoCompleteEditText(Context context, AttributeSet attrs,
                                          int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    /* set listeners for item click and text change */
    public void init(Context context) {
        setOnItemClickListener(this);
        addTextChangedListener(textWather);
    }

    /*TextWatcher, If user type any country name and press comma then following code will regenerate chips */
    private TextWatcher textWather = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (count >= 1) {
                if (s.charAt(start) == ',')
                    updateChips(); // generate chips
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    public List<String> getChips() {
        String chips[] = getText().toString().trim().split(",");
        List<String> result = new ArrayList<>();

        Collections.addAll(result, chips);

        return result;
    }

    public void setChips(List<String> chips) {
        setText(TextUtils.join(",", chips));
        updateChips();
    }

    /*This function has whole logic for chips generate*/
    public void updateChips() {
        if (getText().toString().contains(",")) {
            SpannableStringBuilder ssb = new SpannableStringBuilder(getText());

            // split string wich comma
            List<String> chips = getChips();
            int x = 0;

            // loop will generate ImageSpan for every country name separated by comma
            for (String c : chips) {
                // inflate chips_edittext layout
                LayoutInflater lf = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                TextView textView = (TextView) lf.inflate(R.layout.chips_sdittext, null);
                textView.setText(c); // set text
                setFlags(textView, c); // set flag image
                // capture bitmapt of genreated textview
                int spec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
                textView.measure(spec, spec);
                textView.layout(0, 0, textView.getMeasuredWidth(), textView.getMeasuredHeight());
                Bitmap b = Bitmap.createBitmap(textView.getWidth(), textView.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(b);
                canvas.translate(-textView.getScrollX(), -textView.getScrollY());
                textView.draw(canvas);
                textView.setDrawingCacheEnabled(true);
                Bitmap cacheBmp = textView.getDrawingCache();
                Bitmap viewBmp = cacheBmp.copy(Bitmap.Config.ARGB_8888, true);
                textView.destroyDrawingCache();  // destory drawable
                // create bitmap drawable for imagespan
                BitmapDrawable bmpDrawable = new BitmapDrawable(viewBmp);
                bmpDrawable.setBounds(0, 0, bmpDrawable.getIntrinsicWidth(), bmpDrawable.getIntrinsicHeight());
                // create and set imagespan
                ssb.setSpan(new ImageSpan(bmpDrawable), x, x + c.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                x = x + c.length() + 1;
            }

            // set chips span
            setText(ssb);
            // move cursor to last
            setSelection(getText().length());
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        updateChips(); // call generate chips when user select any item from auto complete
    }

    /* this method set country flag image in textview's drawable component, this logic is not optimize, you need to change as per your requirement*/
    public void setFlags(TextView textView, String sport) {
        sport = sport.trim();

        if (sport.equals("Running")) {
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sport_running, 0);
        } else if (sport.equals("Tennis")) {
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sport_tennis, 0);
        } else if (sport.equals("Skiing")) {
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sport_skiing, 0);
        } else if (sport.equals("Table tennis")) {
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sport_tabletennis, 0);
        } else if (sport.equals("Volleyball")) {
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sport_volleyball, 0);
        } else if (sport.equals("Weightlifter")) {
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sport_weightlifting, 0);
        } else if (sport.equals("Snowboarding")) {
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sport_snowboard, 0);
        } else if (sport.equals("Swimming")) {
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sport_swimming, 0);
        } else if (sport.equals("Football")) {
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sport_football, 0);
        } else if (sport.equals("Climbing")) {
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sport_climbing, 0);
        } else if (sport.equals("Ice scating")) {
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sport_ski, 0);
        } else if (sport.equals("Basketball")) {
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sport_basketball, 0);
        } else if (sport.equals("Cycling")) {
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sport_bicycle, 0);
        }
    }
}

