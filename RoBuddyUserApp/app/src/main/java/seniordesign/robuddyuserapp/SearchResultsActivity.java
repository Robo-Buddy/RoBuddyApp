package seniordesign.robuddyuserapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/*So this is empty for now since we can't searchicon yet, but basically going to be a list of items
  that match the user query.  Once the query finishes from searchicon bar, this should look a lot like
  ViewCheckedOutActivity, effectively a ListView with click and hold options
 */

public class SearchResultsActivity extends AppCompatActivity {

    private ListView availableItemsListView;
    private ArrayList<String> queryMatch;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);


        /* MAKE SURE THIS CODE IS REPLACED EVENTUALLY SERIOUSLY IT SUCKS */
        ArrayList<String> dummyList = new ArrayList<String>();
        dummyList.add("Screwdriver");
        dummyList.add("Scissors");
        dummyList.add("Hammer");
        dummyList.add("Soldering Iron");
        dummyList.add("Glue");
        dummyList.add("Tape");



        Intent intent = getIntent();
        Bundle searchBundle = intent.getExtras();
        String query = searchBundle.getString("SEARCH_QUERY");
        currentUser = searchBundle.getParcelable("USER");



        queryMatch = new ArrayList<String>();
        for(String item : dummyList) {
            if(item.toLowerCase().startsWith(query.toLowerCase())) {
                queryMatch.add(item);
            }
        }
        if(queryMatch.isEmpty()) {
            queryMatch.add("Sorry your search does not match any available items");
        }
        /*
        ArrayAdapter<String> searchAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, queryMatch);
        availableItemsListView.setAdapter(searchAdapter);
        */
        availableItemsListView = (ListView)findViewById(R.id.availableItemsList);
        availableItemsListView.setAdapter(new SearchAdapter(this, R.layout.search_list, queryMatch));

    }


    public void returnHome(View view) {
        Intent returnMainIntent = new Intent(this, MainPageActivity.class);
        startActivity(returnMainIntent);
    }

    //credits to https://www.youtube.com/watch?v=ZEEYYvVwJGY for the searchadapter code

    private void checkOutButtonMethod(int position) {
        int index = position;
        String currentItem = queryMatch.get(index);
        Intent intent = new Intent(this, ItemCheckOutActivity.class);
        Bundle checkOutBundle = new Bundle();
        checkOutBundle.putString("ITEM", currentItem);
        checkOutBundle.putParcelable("USER", currentUser);
        intent.putExtras(checkOutBundle);

        startActivity(intent);
    }


    private class SearchAdapter extends ArrayAdapter<String> {
        private int layout;

        public SearchAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            layout = resource;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder primaryHolder = null;
            if(convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.itemName = (TextView) convertView.findViewById(R.id.itemName);
                viewHolder.itemName.setText(getItem(position));
                viewHolder.checkOutButton = (Button) convertView.findViewById(R.id.checkOutButton);
                viewHolder.checkOutButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view){
                        checkOutButtonMethod(position);

                    }
                });

                convertView.setTag(viewHolder);

            }
            else {
                primaryHolder = (ViewHolder) convertView.getTag();
                primaryHolder.itemName.setText(getItem(position));
            }
            return convertView;
        }


    }

    public class ViewHolder {
        TextView itemName;
        Button checkOutButton;

    }

}
