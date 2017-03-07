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

public class MainPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle userInfo = intent.getExtras();
        User currentUser = userInfo.getParcelable("NEW_USER");

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

        intent.putExtras(searchBundle);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }
}
