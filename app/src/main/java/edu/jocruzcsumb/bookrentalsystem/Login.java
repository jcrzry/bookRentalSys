package edu.jocruzcsumb.bookrentalsystem;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private Button loginSubmit;
    private EditText userEdText;
    private EditText passEdText;
    private BookRental_DB db;
    private int errorCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new BookRental_DB(this);
        passEdText = (EditText)findViewById(R.id.passwrd);
        userEdText = (EditText)findViewById(R.id.usrname);
        loginSubmit = (Button)findViewById(R.id.login_submit);

        loginSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = userEdText.getText().toString();
                String password = passEdText.getText().toString();
                User u = new User(username,password,false);

                if(validateLogin(u) && db.userExists(u.getUsername())){
                    Intent intent = getIntent();
                    String nextActivity = intent.getStringExtra("nextActivity").toLowerCase();
                    Toast.makeText(getApplicationContext(),"Login information correct!",Toast.LENGTH_SHORT).show();
                    User verifiedUser = db.getUser(u.getUsername());
                    Bundle extra = new Bundle();
                    extra.putInt("userID", verifiedUser.getUser_id());
                    extra.putString("username",u.getUsername());
                    extra.putBoolean("Admin",verifiedUser.isAdmin());
                    switch (nextActivity){
                        case "place hold":
                            Intent i = new Intent(getApplicationContext(), Place_Hold.class);
                            i.putExtras(extra);
                            setResult(Activity.RESULT_OK,i);
                            finish();
                            break;
                        case "cancel hold":
                            Intent j = new Intent(getApplicationContext(), Cancel_Hold.class);
                            j.putExtras(extra);
                            setResult(Activity.RESULT_OK,j);
                            Login.this.startActivity(j);
                            finish();
                            break;
                        case "manage system":
                            if(verifiedUser.isAdmin()){
                                Intent k = new Intent(getApplicationContext(), Manage_System.class);
                                k.putExtras(extra);
                                setResult(Activity.RESULT_OK,k);
                                Login.this.startActivity(k);
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Login failed! Try again.", Toast.LENGTH_SHORT).show();

                            }
                            break;

                    }

                }
                else{
                    Toast.makeText(getApplicationContext(),"Login failed! Try again.", Toast.LENGTH_SHORT).show();
                    errorCount++;
                    if (errorCount == 2) {
                        AlertDialog errorAlert = new AlertDialog.Builder(Login.this).create();
                        errorAlert.setTitle("Alert:");
                        errorAlert.setMessage("Too many attempts! Will navigate to main menu.");
                        errorAlert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        finish();
                                    }
                                });
                        errorAlert.show();
                    }
                }
            }
        });


    }

    public boolean validateLogin(User u){
        return db.validLogin(u);
    }
}
