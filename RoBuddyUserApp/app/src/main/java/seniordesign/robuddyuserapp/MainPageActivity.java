package seniordesign.robuddyuserapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.*;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.*;
import com.amazonaws.services.dynamodbv2.model.*;

import java.util.ArrayList;


public class MainPageActivity extends AppCompatActivity {

    private static User currentUser = new User("EMPTYNAME", "EMPTYUTEID");
    public static boolean checkedOut = false;
    public static boolean checkedIn = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle userInfo = intent.getExtras();
        if(currentUser.getName().equals("EMPTYNAME") && currentUser.getEID().equals("EMPTYUTEID")) {
            currentUser = userInfo.getParcelable("NEW_USER");
            currentUser.newList();
        }

        if(checkedOut) {
            String newItem = userInfo.getString("CHECKOUT");
            currentUser.addCheckedoutList(newItem);
            checkedOut = false;
        }

        if(checkedIn) {
            String loseItem = userInfo.getString("CHECKIN");
            currentUser.removeCheckedoutList(loseItem);
            checkedIn = false;
        }

        setContentView(R.layout.activity_main_page);

        TextView greeting = (TextView) findViewById(R.id.greeting);
        greeting.setTextSize(20);
        greeting.setText("Hello " + currentUser.getName());

        handleIntent(getIntent());

    }

    public void viewCheckedOut(View view) {
        Intent viewCheckedOutIntent = new Intent(this, ViewCheckedOutActivity.class);
        Bundle userInfo = new Bundle();
        viewCheckedOutIntent.putExtras(userInfo);
        userInfo.putStringArrayList("LIST", currentUser.getCheckedout());
        startActivity(viewCheckedOutIntent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH != null && intent.getAction() != null) {
            if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
                String query = intent.getStringExtra(SearchManager.QUERY);
                searchData(query);
            }
        }

    }

    private void searchData(String query) {
        Intent intent = new Intent(this, SearchResultsActivity.class);
        Bundle searchBundle = new Bundle();
        searchBundle.putString("SEARCH_QUERY", query);
        searchBundle.putParcelable("USER", currentUser);
        intent.putExtras(searchBundle);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Search for Items");

        return true;
    }
}
