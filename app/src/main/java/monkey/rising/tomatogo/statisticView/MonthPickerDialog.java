package monkey.rising.tomatogo.statisticView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2016/7/22.
 */
public class MonthPickerDialog extends DatePickerDialog {

    public MonthPickerDialog(DailyFragment context, OnDateSetListener callBack,
                             int year, int monthOfYear, int dayOfMonth) {
        super(context.getContext(), callBack, year, monthOfYear, dayOfMonth);
        this.setTitle(year + "年" + (monthOfYear + 1) + "月");

        DatePicker datePicker = getDatePicker();
        try {
            Field daySpinner =datePicker.getClass().getDeclaredField("mDaySpinner");
            daySpinner.setAccessible(true);
            ((View)daySpinner.get(datePicker)).setVisibility(View.GONE);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int month, int day) {
        super.onDateChanged(view, year, month, day);
        this.setTitle(year + "年" + (month + 1) + "月");
    }
}
