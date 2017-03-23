package seniordesign.robuddyuserapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/*So this is empty for now since we can't search yet, but basically going to be a list of items
  that match the user query.  Once the query finishes from search bar, this should look a lot like
  ViewCheckedOutActivity, effectively a ListView with click and hold options
 */

public class SearchResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
    }
}
