package edu.jocruzcsumb.bookrentalsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.view.View;
/**
 * Created by jcrzry on 11/16/2016.
 */

public class Main_Menu extends AppCompatActivity{
    private Spinner mm_spinner;
    private ArrayAdapter<CharSequence> mm_adapt;
    private Button mm_button;
    private BookRental_DB db;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        db = new BookRental_DB(this);

        mm_spinner = (Spinner) findViewById(R.id.main_select_spinner);
        mm_adapt = ArrayAdapter.createFromResource(this,
                R.array.mm_spinOpts, android.R.layout.simple_spinner_item);
        mm_adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mm_spinner.setAdapter(mm_adapt);
        mm_button = (Button) findViewById(R.id.main_select_button);
        mm_button.setOnClickListener(new OnClickListener(){
            public void onClick(View v) {
                String selectedOpt = mm_spinner.getSelectedItem().toString();
                if (selectedOpt.equalsIgnoreCase("create account")) {
                    Intent createLink;
                    createLink = new Intent(getApplicationContext(), Create_Account.class);
                    Main_Menu.this.startActivity(createLink);
                }
                else if(selectedOpt.equalsIgnoreCase("place hold")){
                    Intent createLink;
                    createLink = new Intent(getApplicationContext(), Place_Hold.class);
                    Main_Menu.this.startActivity(createLink);
                }
                else{
                    Intent loginLink = new Intent(getApplicationContext(), Login.class);
                    Bundle nextExtra = new Bundle();
                    nextExtra.putString("nextActivity",selectedOpt);
                    loginLink.putExtras(nextExtra);
                    Main_Menu.this.startActivity(loginLink);
                }
            }
        });

    }
}

