package com.example.mad_6;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {
    EditText medname,meddate;
    Button insert,fetch;
    Spinner day;
    Switch switch1;
    TextView medtxt;
    DataBaseConn dbconnection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        medname=(EditText)findViewById(R.id.medicinename);
        meddate=(EditText)findViewById(R.id.date);
        insert=(Button)findViewById(R.id.insert);
        fetch=(Button)findViewById(R.id.fetch);
        day=(Spinner)findViewById(R.id.spinner);
        switch1=(Switch)findViewById(R.id.switch1);
        medtxt=(TextView)findViewById(R.id.medtext);
        dbconnection=new DataBaseConn(this);
        fetch.setVisibility(View.INVISIBLE);
        switch1.setOnCheckedChangeListener(new
                                                   CompoundButton.OnCheckedChangeListener() {
                                                       @Override
                                                       public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                                           if(!b)
                                                           {
                                                               fetch.setVisibility(View.INVISIBLE);
                                                               insert.setVisibility(View.VISIBLE);
                                                               medname.setVisibility(View.VISIBLE);
                                                               medtxt.setVisibility(View.VISIBLE);
                                                           }
                                                           else{
                                                               fetch.setVisibility(View.VISIBLE);
                                                               insert.setVisibility(View.INVISIBLE);
                                                               medname.setVisibility(View.INVISIBLE);
                                                               medtxt.setVisibility(View.INVISIBLE);
                                                           }
                                                       }
                                                   });
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = medname.getText().toString().trim();
                String date = meddate.getText().toString().trim();
                String time = day.getSelectedItem().toString().trim();
                boolean insert=dbconnection.insertvalues(name,date,time);
                if(insert==true)
                {
                    Toast.makeText(getApplicationContext(), "Data Inserted",
                            Toast.LENGTH_LONG).show();
                    medname.setText("");
                    meddate.setText("");
                }
                else {
                    Toast.makeText(getApplicationContext(), "Data Not Inserted",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        fetch.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                String date = meddate.getText().toString().trim();
                String time = day.getSelectedItem().toString().trim();
                try (Cursor c = dbconnection.FetchData(date, time)) {
                    if (c != null && c.moveToFirst()) {
                        StringBuilder med = new StringBuilder();
                        do {
                            med.append(c.getString(c.getColumnIndexOrThrow("MedicineName")))
                                    .append('\n');
                        } while (c.moveToNext());
                        Toast.makeText(getApplicationContext(), med.toString(),
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "No Entries in Database",
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "DB error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
