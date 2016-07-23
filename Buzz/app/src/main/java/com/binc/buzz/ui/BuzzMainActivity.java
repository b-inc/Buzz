package com.binc.buzz.ui;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.provider.SyncStateContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.binc.buzz.R;
import com.binc.buzz.util.BuzzConstants;
import com.jaouan.revealator.Revealator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BuzzMainActivity extends ListActivity {

    //use splash screen to getBuzz first few buzzes..

    private static final int NONE_ANIM = 1;
    private static final int FLIP_ANIM = 2;
    private static final int ZOOM_ANIM = 3;
    private final String FRAGMENT_TAG = "fragment_tag";
    //fragment_pool that holds fragment object for reuse
    private Queue<BuzzFragment> fragment_pool = new LinkedList<>();
    BuzzFragment fragment;
    List<StackView> sv_list = new ArrayList<>();
    FloatingActionButton fab;
    //reveal view
    View reveal_view;
    StackView list_item;

    AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            list_item = (StackView)view;
            revealAnimation(view);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    fetchAndShowBuzz(NONE_ANIM);
                }
            }, 1000);
        }
    };

    private GestureDetector gesture;
    private ScaleGestureDetector scaleGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buzz_main);

        //swipeable cards test start
        List<String> cat_list = new ArrayList<>();
        cat_list.add("Technology");
        cat_list.add("Science");
        cat_list.add("Politics");
        cat_list.add("India");
        cat_list.add("Sports");
        cat_list.add("Football");
        cat_list.add("Terror");
        cat_list.add("Super Models");
        MyAdapter myAdapter = new MyAdapter(cat_list, this);
        setListAdapter(myAdapter);
        getListView().setDividerHeight(30);
        getListView().setOnItemClickListener(mItemClickListener);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        //to identify gestures
        gesture = new GestureDetector(getBaseContext(),
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onDown(MotionEvent e) {
                        return true;
                    }

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                           float velocityY) {
                        Log.i("yash", "onFling has been called!");
                        final int SWIPE_MIN_DISTANCE = 120;
                        final int SWIPE_MAX_OFF_PATH = 250;
                        final int SWIPE_THRESHOLD_VELOCITY = 200;
                        try {
                            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                                return false;
                            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                Log.i("yash", "Right to Left");
                                flipCard();
                            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                Log.i("yash", "Left to Right");
                                flyCard();
                            }
                        } catch (Exception e) {
                            // nothing
                        }
                        return super.onFling(e1, e2, velocityX, velocityY);
                    }
                });
        //to identify pinch
        scaleGestureDetector = new ScaleGestureDetector(this,
                new ScaleGestureDetector.SimpleOnScaleGestureListener(){
                    @Override
                    public boolean onScale(ScaleGestureDetector detector) {
                        Log.i("yash","scaling");
                        if(detector.getScaleFactor() < 0.8) {
                            backToList();
                        }
                        return super.onScale(detector);
                    }
                });



        CardView frag_cv = (CardView)findViewById(R.id.cv);
        frag_cv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gesture.onTouchEvent(event);
                return scaleGestureDetector.onTouchEvent(event);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //get login status and update the fab button background
        SharedPreferences sp = getSharedPreferences(BuzzConstants.SP_NAME, Context.MODE_PRIVATE);
        boolean is_logged_in = sp.getBoolean(BuzzConstants.IS_LOGGED_IN, false);
        if(is_logged_in){
            fab.setOnClickListener(null);
            fab.setImageResource(R.drawable.create_buzz);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent login_intent = new Intent(BuzzMainActivity.this, CreateBuzzActivity.class);
                    BuzzMainActivity.this.startActivity(login_intent);
                }
            });
        } else {
            fab.setOnClickListener(null);
            fab.setImageResource(R.drawable.add_user);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent login_intent = new Intent(BuzzMainActivity.this, LoginActivity.class);
                    BuzzMainActivity.this.startActivity(login_intent);
                }
            });
        }
    }

    private void fetchAndShowBuzz(int anim_type) {
        //show the fragment
        fragment = getFragment();

        if (anim_type == NONE_ANIM) {
            (findViewById(R.id.cv)).setVisibility(View.VISIBLE);
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.cv, fragment, FRAGMENT_TAG)
                    .commit();
        } else if (anim_type == FLIP_ANIM) {
            getFragmentManager().beginTransaction()
                    .setCustomAnimations(
                            R.animator.card_flip_right_in,
                            R.animator.card_flip_right_out,
                            R.animator.card_flip_left_in,
                            R.animator.card_flip_left_out)
                    .replace(R.id.cv, fragment, FRAGMENT_TAG)
                    .commit();
        } else if (anim_type == ZOOM_ANIM) {
            getFragmentManager().beginTransaction()
                    .setCustomAnimations(
                            R.animator.next_buzz_anim,
                            R.animator.share_buzz_anim)
                    .replace(R.id.cv, fragment, FRAGMENT_TAG)
                    .commit();
        }
    }

    private void removeFragment(){
        getFragmentManager()
                .beginTransaction()
                .remove(fragment)
                .commit();
        (findViewById(R.id.cv)).setVisibility(View.GONE);
    }

    private BuzzFragment getFragment() {
        BuzzFragment frag = null;
        if (fragment_pool.size() < 3) {
            frag = new BuzzFragment();
            fragment_pool.add(frag);
        } else {
            frag = fragment_pool.poll();
            fragment_pool.add(frag);
        }

        return frag;
    }

    private void flipCard() {
        fetchAndShowBuzz(FLIP_ANIM);
    }

    private void flyCard() {
        fetchAndShowBuzz(ZOOM_ANIM);
    }

    private void revealAnimation(View v){
        fab.setVisibility(View.GONE);
        reveal_view = findViewById(R.id.view);
        Revealator.reveal(reveal_view)
                .from(v)
                .withChildsAnimation()
                .start();
    }

    private void unRevealAnimation(){
        Revealator.unreveal(reveal_view)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        list_item.setVisibility(View.VISIBLE);
                        Log.i("yash", "isshown:" + list_item.isShown());
                    }
                })
        .start();
    }

    @Override
    public void onBackPressed() {
        BuzzFragment myFrag = (BuzzFragment)getFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        if(myFrag != null && myFrag.isVisible()){
            backToList();
        } else {
            super.onBackPressed();
        }
    }

    private void backToList(){
        removeFragment();
        unRevealAnimation();
        fab.setVisibility(View.VISIBLE);
    }

    class MyAdapter extends BaseAdapter {
        List<String> items;
        Context mContext;

        MyAdapter(List<String> list, Context ctx){
            items = list;
            mContext = ctx;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //use holder pattern later
            Log.i("yash", "getView");
            return getStackView(position);
        }

        StackView getStackView(int position){
            StackView sv;
            if(sv_list.size() > position && sv_list.get(position) != null) {
                sv = sv_list.get(position);
            } else {
                sv = new StackView(mContext, items.get(position));
                sv_list.add(sv);
            }
            return sv;
        }
    }
}
