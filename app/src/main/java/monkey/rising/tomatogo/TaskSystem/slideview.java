package monkey.rising.tomatogo.TaskSystem;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Scroller;

/**
 * Created by lizhangfang on 2016/7/15.
 */
public class slideview extends ListView {



    private int slidePosition;
    private int downY;
    private int downX;
    private int screenWidth;
    private View itemView;
    private Scroller scroller;
    private static final int SNAP_VELOCITY=600;
    private VelocityTracker velocityTracker;
    private  boolean isSlide=false;
    private int mTouchSlop;
    private RemoveListener removeListener;
    private RemoveDirection removeDirection;





    public enum RemoveDirection{RIGHT,LEFT;}

    public slideview(Context context){
        this(context,null);
    }

    public slideview(Context context, AttributeSet attrs){
        this(context,attrs,0);
    }

    public slideview(Context context,AttributeSet attrs,int defStyle){
        super(context,attrs,defStyle);
        screenWidth=((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
        scroller=new Scroller(context);
        mTouchSlop= ViewConfiguration.get(getContext()).getScaledTouchSlop();

    }


    public void setRemoveListener(RemoveListener removeListener){
        this.removeListener=removeListener;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:{
                addVelocityTracker(ev);
                if(!scroller.isFinished()){
                    return super.dispatchTouchEvent(ev);
                }
                downX=(int)ev.getX();
                downY=(int)ev.getY();

                slidePosition=pointToPosition(downX,downY);
                if(slidePosition== AdapterView.INVALID_POSITION){
                    return super.dispatchTouchEvent(ev);
                }
                itemView=getChildAt(slidePosition-getFirstVisiblePosition());
                break;
            }

            case MotionEvent.ACTION_MOVE:{
                if(Math.abs(getScrolLVelocity())>SNAP_VELOCITY||(Math.abs(ev.getX()-downX)>mTouchSlop)&&Math.abs(ev.getY()-downY)<mTouchSlop){
                    isSlide=true;
                }
                break;
            }

            case MotionEvent.ACTION_UP:
                recycleVelocityTracker();
                break;

        }
      return super.dispatchTouchEvent(ev);
    }

    private void scrollRight(){
        removeDirection=RemoveDirection.RIGHT;
        final int delta=(screenWidth+itemView.getScrollX());
        scroller.startScroll(itemView.getScrollX(),0,-delta,0,Math.abs(delta));
        postInvalidate();
    }

    private void scrollLeft(){
        removeDirection=RemoveDirection.LEFT;
        final int delta=(screenWidth-itemView.getScrollX());
        scroller.startScroll(itemView.getScrollX(),0,delta,0,Math.abs(delta));
        postInvalidate();
    }

    private void scrollByDistance(){
        if(itemView.getScrollX()>=screenWidth/2){
            scrollLeft();
        }
        else if(itemView.getScrollX()<=-screenWidth/2){
            scrollRight();
        }
        else {
            itemView.scrollTo(0,0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(isSlide&&slidePosition!=AdapterView.INVALID_POSITION){
            requestDisallowInterceptTouchEvent(true);
            addVelocityTracker(ev);
            final int action=ev.getAction();
            int x=(int)ev.getX();
             switch(action){
                case MotionEvent.ACTION_DOWN:

                    break;
                case MotionEvent.ACTION_MOVE:
                    MotionEvent cancelEvent=MotionEvent.obtain(ev);
                    cancelEvent.setAction(MotionEvent.ACTION_CANCEL|(ev.getActionIndex()<<MotionEvent.ACTION_POINTER_INDEX_SHIFT));
                    onTouchEvent(cancelEvent);
                    int delta=downX-x;
                    downX=x;
                    itemView.scrollBy(delta,0);
                    return true;

                case MotionEvent.ACTION_UP:
                    if(!isVerticalScrollBarEnabled()){
                        setVerticalScrollBarEnabled(true);
                    }

                    int VelocityX=getScrolLVelocity();
                    if(VelocityX>SNAP_VELOCITY){
                        scrollRight();
                    }else if(VelocityX<-SNAP_VELOCITY){
                        scrollLeft();
                    }else {
                        scrollByDistance();
                    }
                  recycleVelocityTracker();
                    isSlide=false;
                    break;
            }
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
      if(scroller.computeScrollOffset()){
          itemView.scrollTo(scroller.getCurrX(),scroller.getCurrY());
          postInvalidate();

          if(scroller.isFinished()){
              if(removeListener==null){
                  throw new NullPointerException("removelistener is null");
              }
              itemView.scrollTo(0,0);
              removeListener.removeItem(removeDirection,slidePosition);
          }
      }
    }

    private void addVelocityTracker(MotionEvent event){
        if(velocityTracker==null){
            velocityTracker=VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);
    }

    private void recycleVelocityTracker(){
        if(velocityTracker!=null){
            velocityTracker.recycle();
            velocityTracker=null;
        }
    }

    private int getScrolLVelocity(){
        velocityTracker.computeCurrentVelocity(1000);
        int velocity=(int)velocityTracker.getXVelocity();
        return velocity;
    }

    public interface RemoveListener{
        public void removeItem(RemoveDirection direction,int position);
    }

}

