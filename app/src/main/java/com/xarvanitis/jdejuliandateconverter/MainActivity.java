package com.xarvanitis.jdejuliandateconverter;

import static com.xarvanitis.jdejuliandateconverter.JdeJulianDateConversionMethods.CalendarToJulian;
import static com.xarvanitis.jdejuliandateconverter.JdeJulianDateConversionMethods.FirstDayCurrentMonthJulian;
import static com.xarvanitis.jdejuliandateconverter.JdeJulianDateConversionMethods.FirstDayOfCurrentYear;
import static com.xarvanitis.jdejuliandateconverter.JdeJulianDateConversionMethods.FirstDayPreviousMonthJulian;
import static com.xarvanitis.jdejuliandateconverter.JdeJulianDateConversionMethods.JulianToCalendar;
import static com.xarvanitis.jdejuliandateconverter.JdeJulianDateConversionMethods.LastDayCurrentMonthJulian;
import static com.xarvanitis.jdejuliandateconverter.JdeJulianDateConversionMethods.LastDayPreviousMonthJulian;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    final Calendar normalDateCalendar= Calendar.getInstance();
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    InputMethodManager imm;
    EditText from_normal_tv,from_jde_tv;
    TextView today_tv,this_month_tv,last_month_tv,year_start_tv,to_jde_tv,to_normal_tv;

    String yearStart, currentMonthStart,currentMonthEnd,lastMonthStart,lastMonthEnd,today;
    LocalDate toNormal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        from_normal_tv=findViewById(R.id.from_normal_tv);
        from_jde_tv=findViewById(R.id.from_jde_tv);
        to_jde_tv=findViewById(R.id.to_jde_tv);
        to_normal_tv=findViewById(R.id.to_normal_tv);
        today_tv=findViewById(R.id.today_tv);
        this_month_tv=findViewById(R.id.this_month_tv);
        last_month_tv=findViewById(R.id.last_month_tv);
        year_start_tv=findViewById(R.id.year_start_tv);

        onLoad();

        DatePickerDialog.OnDateSetListener date = (view, year, month, dayOfMonth) -> {
            LocalDate ld = LocalDate.of(year,month+1, dayOfMonth);
            from_normal_tv.setText(ld.format(formatter));
            to_jde_tv.setText(CalendarToJulian(ld));
        };
        from_normal_tv.setOnClickListener(view ->
                new DatePickerDialog(MainActivity.this,date,
                        normalDateCalendar.get(Calendar.YEAR),
                        normalDateCalendar.get(Calendar.MONTH),
                        normalDateCalendar.get(Calendar.DAY_OF_MONTH))
                        .show());


        from_jde_tv.setOnEditorActionListener((v, actionId, event) -> {
            if ((actionId & EditorInfo.IME_MASK_ACTION) != 0) {
                toNormal = JulianToCalendar(from_jde_tv.getText().toString());
                if(toNormal!=null){
                    to_normal_tv.setText(toNormal.format(formatter));
                }else{to_normal_tv.setText("Invalid Julian Date");}

                from_jde_tv.setCursorVisible(false);
                imm = (InputMethodManager) getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(to_normal_tv.getApplicationWindowToken(), 0);
                return true;
            }else {return false;}
        });

        from_jde_tv.setOnClickListener(v -> {
            if (v.getId() == from_jde_tv.getId())
            {
                from_jde_tv.getText().clear();
                from_jde_tv.setCursorVisible(true);
            }
        });
    }

    private void onLoad() {
        today=CalendarToJulian(LocalDate.now());
        today_tv.setText(today);

        currentMonthStart = FirstDayCurrentMonthJulian();
        currentMonthEnd= LastDayCurrentMonthJulian();
        StringBuilder currentMonthDates = new StringBuilder(currentMonthStart).append(" - ").append(currentMonthEnd);
        this_month_tv.setText(currentMonthDates);

        lastMonthStart= FirstDayPreviousMonthJulian();
        lastMonthEnd= LastDayPreviousMonthJulian();
        StringBuilder lastMonthDates = new StringBuilder(lastMonthStart).append(" - ").append(lastMonthEnd);
        last_month_tv.setText(lastMonthDates);

        yearStart= FirstDayOfCurrentYear();
        StringBuilder currentYearUpToday = new StringBuilder(yearStart).append(" - ").append(today);
        year_start_tv.setText(currentYearUpToday);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }
}