package in.shantanupatil.notificationmanager.IntroSlider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import in.shantanupatil.notificationmanager.R;

public class IntroLauncer extends AppCompatActivity {

    private android.support.v4.view.ViewPager viewPager;
    private IntroductionSlider slider;
    private int[] layouts;

    private TextView[] dots;
    private LinearLayout dotsLayout;

    Button next;
    Button skip;

    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        slider = new IntroductionSlider(this);
        Log.d("CheckLaunch", "onCreate: " + slider.checkLauch());
        if (!slider.checkLauch()) {
            slider.setFirstLaunch(false);
            Log.d("CheckLaunch", "onCreate:if " + slider.checkLauch());
            Intent intent = new Intent(getApplicationContext(), LauncherScreenActivity.class);
            startActivity(intent);
            finish();
        }


        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_intro_launcer);

        layouts = new int[]{R.layout.slider_screen_one, R.layout.slider_screen_two, R.layout.screen_three};
        Log.d("ArrayLn", "" + layouts.length);

        viewPager = (android.support.v4.view.ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        next = (Button) findViewById(R.id.next);
        skip = (Button) findViewById(R.id.previous);
        addDots(0);
        ChangeStatusBarColor();
        viewPagerAdapter = new ViewPagerAdapter();
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(viewListener);


        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LauncherScreenActivity.class);
                startActivity(intent);
                finish();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int current = getItem(+1);
                if (current < layouts.length) {
                    viewPager.setCurrentItem(current);
                } else {
                    slider.setFirstLaunch(false);
                    Intent intent = new Intent(getApplicationContext(), LauncherScreenActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + 1;
    }

    private void addDots(int position) {
        dots = new TextView[layouts.length];
        dotsLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i]. setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(Color.parseColor("#FFFFFF"));
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[position].setTextColor(Color.parseColor("#F44336"));
        }
    }

    android.support.v4.view.ViewPager.OnPageChangeListener viewListener = new android.support.v4.view.ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);
            if (position == layouts.length - 1) {
                next.setText("Hello");
                skip.setVisibility(View.GONE);
            } else {
                next.setText("Next");
                skip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
;
    private void ChangeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public class ViewPagerAdapter extends PagerAdapter {

        private LayoutInflater inflater;

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(layouts[position], container, false);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View)object;
            container.removeView(view);
        }
    }
}
