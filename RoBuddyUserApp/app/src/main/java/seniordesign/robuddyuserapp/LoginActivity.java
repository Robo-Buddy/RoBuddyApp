package seniordesign.robuddyuserapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
//import com.amazonaws.auth.CognitoCachingCredentialsProvider;
//import com.amazonaws.regions.Regions;
//import com.amazonaws.services.dynamodbv2.*;
//import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.*;
//import com.amazonaws.services.dynamodbv2.model.*;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void submit(View view) {
        EditText name = (EditText) findViewById(R.id.name_edit);
        EditText uteid = (EditText) findViewById(R.id.uteid_edit);

        TextView requiredText = (TextView) findViewById(R.id.required_text);

        String nameString = name.getText().toString();
        String uteidString = uteid.getText().toString();
        if(!nameString.equals("") || !uteidString.equals("")) {
            User newUser = new User(nameString, uteidString);
            Intent loginCompleteIntent = new Intent(this, MainPageActivity.class);
            Bundle userInfo = new Bundle();
            userInfo.putParcelable("NEW_USER", newUser);
            loginCompleteIntent.putExtras(userInfo);
            startActivity(loginCompleteIntent);
        }
        else {
            requiredText.setTextColor(Color.RED);
            String errorString = "Not all required fields were filled\n(";
            if(nameString.equals("")) {
                errorString += "Name";
            }
            if(uteidString.equals("")) {
                if(nameString.equals("")) {
                    errorString += ", ";
                }
                errorString += "UT EID";
            }
            errorString += ")";
            requiredText.setText(errorString);
        }


    }
}
