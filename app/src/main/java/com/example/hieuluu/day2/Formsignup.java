package com.example.hieuluu.day2;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.SimpleTimeZone;

public class Formsignup extends Activity implements TextWatcher {
    private Button dongy;
    private Button trove;
    boolean usernameflag;
    boolean passwordflag;
    boolean emailflag;
    boolean phoneflag;
    TextView txtDate;
    Button btDate;
    EditText usename,phone,password,email;
    ArrayList<DateTimePicker> arrDate = new ArrayList<DateTimePicker>();
    ArrayAdapter< DateTimePicker> adapter = null;
    Calendar cal;
    int[] fullid = {R.id.ten, R.id.password, R.id.email, R.id.phone};
    String arr[]={
            "Khác",
            "Female",
            "Male" };
    TextView selection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        checkEnable(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formsignup);
        dongy = (Button) findViewById(R.id.dongy);
        trove = (Button) findViewById(R.id.trove);
        usename = (EditText) findViewById(R.id.ten);
        password = (EditText) findViewById(R.id.password);
        email= (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);

        dongy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                Intent dongy = new Intent(Formsignup.this, MainActivity.class);
                startActivity(dongy);
            }

        });
        usename.addTextChangedListener(this);
        password.addTextChangedListener(this);
        email.addTextChangedListener(this);
        phone.addTextChangedListener(this);

        trove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "", Toast.LENGTH_SHORT).show();
                Intent trove = new Intent(Formsignup.this, MainActivity.class);
                startActivity(trove);

            }
        });

        getFormWidgets();
        getDefautInfor();
        addEventFormWidgets();

//        selection = (TextView) findViewById(R.id.selection);
        Spinner spin = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arr);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spin.setAdapter(adapter);
//        spin.setOnItemSelectedListener(new MyProcessEvent());

    }

    private void addEventFormWidgets() {
        btDate.setOnClickListener(new myButtonEvent());
    }

    public void checkEnable(boolean check) {


        for (int i = 0; i < fullid.length; i++)

        {
                findViewById(fullid[i]).setEnabled(check);


        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_formsignup, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(s != null){
          dongy.setEnabled(true);

        }
        else {

            dongy.setEnabled(false);

        }



    }

    public void getFormWidgets() {
        txtDate=(TextView) findViewById(R.id.txtDate);
        btDate =(Button) findViewById(R.id.buttonDate);
    }

    public void getDefautInfor() {
        cal = Calendar.getInstance();
        SimpleDateFormat dtf = null;
        dtf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String strDate = dtf.format(cal.getTime());
        txtDate.setText(strDate);


    }


    private class myButtonEvent implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.buttonDate:
                    showDatePickerDialog();
                    break;
            }
        }
    }
    public void showDatePickerDialog()
    {
            final OnDateSetListener callback = new OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    txtDate.setText((dayOfMonth) + "/" + (monthOfYear + 1) + "/" + year);
                    cal.set(year, monthOfYear, dayOfMonth);

                }
            };
            String s=txtDate.getText()+"";
            String strArrtmp[]=s.split("/");
            int ngay=Integer.parseInt(strArrtmp[0]);
            int thang=Integer.parseInt(strArrtmp[1])-1;
            int nam=Integer.parseInt(strArrtmp[2]);
            DatePickerDialog pic=new DatePickerDialog(
                    Formsignup.this,
                    callback, nam, thang, ngay);
            pic.setTitle("");
            pic.show();
    }

    private class MyProcessEvent implements android.widget.AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0,View arg1, int arg2,long arg3)
        {
            selection.setText(arr[arg2]);
        }
        public void onNothingSelected(AdapterView<?>arg0)
        {
            selection.setText("");
        }
    }
}
