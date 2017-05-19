package seniordesign.robuddyuserapp;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

public class ItemCheckOutActivity extends AppCompatActivity {

    User currentUser;
    String checkoutItem;

    CognitoCachingCredentialsProvider credentialsProvider;
    AmazonDynamoDBClient ddbClient;// = new AmazonDynamoDBClient(credentialsProvider);
    DynamoDBMapper mapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_check_out);
        TextView greeting = (TextView) findViewById(R.id.table_select_prompt);
        greeting.setTextSize(20);
        greeting.setText("Select your table number below");
        Intent intent = getIntent();
        Bundle checkoutInfo = intent.getExtras();
        currentUser = checkoutInfo.getParcelable("USER");
        checkoutItem = checkoutInfo.getString("ITEM");
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                "us-west-2:a35d988d-8460-4e52-9990-f7f3d5816fd8", // Identity Pool ID
                Regions.US_WEST_2 // Region
        );
        ddbClient = Region.getRegion(Regions.US_WEST_2).createClient(
                AmazonDynamoDBClient.class,
                credentialsProvider,
                new ClientConfiguration()
                );
        mapper = new DynamoDBMapper(ddbClient);

    }

    public void CheckOut(View view) {
        Spinner tableNumSpinner = (Spinner) findViewById(R.id.table_selection);
        String tableNum = String.valueOf(tableNumSpinner.getSelectedItem());
        currentUser.setTableNum(tableNum);
        currentUser.setSignature();
        currentUser.setItem(checkoutItem);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            mapper.save(currentUser);
            Toast.makeText(ItemCheckOutActivity.this, currentUser.getItem() + " successfully checked out!"
                    , Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            System.out.println(e);
        }


        Intent checkOutCompleteIntent = new Intent(this, MainPageActivity.class);

        startActivity(checkOutCompleteIntent);

    }

    public void Cancel(View view) {
        Intent returnMainIntent = new Intent(this, MainPageActivity.class);
        startActivity(returnMainIntent);
    }
}
