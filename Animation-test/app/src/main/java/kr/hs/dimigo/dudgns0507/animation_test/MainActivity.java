package kr.hs.dimigo.dudgns0507.animation_test;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class MainActivity extends FragmentActivity {

    private int NUM_PAGES = 5;

    public final static int FRAGMENT_PAGE1 = 0;
    public final static int FRAGMENT_PAGE2 = 1;
    public final static int FRAGMENT_PAGE3 = 2;
    public final static int FRAGMENT_PAGE4 = 3;
    public final static int FRAGMENT_PAGE5 = 4;

    static int page = 0, c = 0;
    float preview_pos = 0.0f;
    int pos = 0;

    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LinearLayout page_num = (LinearLayout)findViewById(R.id.page_num);
        final FrameLayout frame = (FrameLayout)findViewById(R.id.bt_frame);

        final float[] frame_width = new float[1];
        final float[] frame_height = new float[1];
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        final int width = metrics.widthPixels;
        final int height = metrics.heightPixels;

        mViewPager = (ViewPager)findViewById(R.id.container);
        mViewPager.setAdapter(new pageAdapter(getSupportFragmentManager()));
        mViewPager.setCurrentItem(FRAGMENT_PAGE1);

        page_num.setAlpha(0.0f);
        frame.setAlpha(0.0f);

        frame.post(new Runnable() {
            @Override
            public void run() {
                frame_width[0] = frame.getWidth();
                frame_height[0] = frame.getHeight();
            }
        });

        // 로그인 버튼이 눌러졌을 때 mViewPager.setCurrentItem(0); 를 실행

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                // 페이지가 스크롤 될 때
                ScaleAnimation scale1 = new ScaleAnimation(preview_pos/2 + 1.0f, positionOffset/2 + 1.0f, preview_pos/2 + 1.0f, positionOffset/2 + 1.0f, 10.0f, 10.0f);
                ScaleAnimation scale2 = new ScaleAnimation(1.5f - preview_pos/2, 1.5f - positionOffset/2, 1.5f - preview_pos/2, 1.5f - positionOffset/2, 10.0f, 10.0f);
                scale1.setFillAfter(true); // 애니메이션 종료 후 원래 상태로 돌아가지 않음
                scale2.setFillAfter(true);

                // 하단 페이지 표시 점 4개
                FrameLayout c1 = (FrameLayout)findViewById(R.id.c1);
                FrameLayout c2 = (FrameLayout)findViewById(R.id.c2);
                FrameLayout c3 = (FrameLayout)findViewById(R.id.c3);
                FrameLayout c4 = (FrameLayout)findViewById(R.id.c4);

                Button register_bt = (Button)findViewById(R.id.register_bt);

                int fromy, toy;

                fromy = (int)(frame_height[0] - frame_height[0] /width*pos);
                toy = (int)(frame_height[0] - frame_height[0] /width*positionOffsetPixels);

                // login_top_layout 애니메이션 부분
                if(pos != positionOffsetPixels) {
                    if(position == 0) {
                        final Animation trans = new TranslateAnimation(0, 0, fromy, toy);

                        trans.setFillAfter(true);
                        frame.startAnimation(trans);
                        pos = positionOffsetPixels;
                        frame.setAlpha(positionOffset);
                        page_num.setAlpha(positionOffset);
                    }
                    if(preview_pos != 0 && positionOffset != 0) {
                        if(page == position) {
                            switch (page) {
                                case 0:
                                    c1.startAnimation(scale1);
                                    break;
                                case 1:
                                    c2.startAnimation(scale1);
                                    c1.startAnimation(scale2);
                                    break;
                                case 2:
                                    c3.startAnimation(scale1);
                                    c2.startAnimation(scale2);
                                    break;
                                case 3:
                                    c4.startAnimation(scale1);
                                    c3.startAnimation(scale2);
                                    break;
                                case 4:
                                    c4.startAnimation(scale1);
                                    c3.startAnimation(scale2);
                                    break;
                            }
                        }
                    }
                    page = position;
                    preview_pos = positionOffset;
                }
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private class pageAdapter extends FragmentPagerAdapter {

        public pageAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case FRAGMENT_PAGE1 :
                    return new Page1Activity();
                case FRAGMENT_PAGE2 :
                    return new Page2Activity();
                case FRAGMENT_PAGE3 :
                    return new Page3Activity();
                case FRAGMENT_PAGE4 :
                    return new Page4Activity();
                case FRAGMENT_PAGE5 :
                    return new Page5Activity();
                default : return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
