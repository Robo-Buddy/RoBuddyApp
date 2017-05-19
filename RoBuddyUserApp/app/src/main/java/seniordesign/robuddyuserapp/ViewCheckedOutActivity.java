package seniordesign.robuddyuserapp;

import android.content.Context;
import android.content.Intent;
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

public class ViewCheckedOutActivity extends AppCompatActivity {

    private ListView yourCheckedOutItems;
    private ArrayList<String> yourItems = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_checked_out);

        Intent intent = getIntent();
        Bundle checkedOut = intent.getExtras();
        yourItems = checkedOut.getStringArrayList("LIST");
        if(yourItems.isEmpty()) {
            yourItems.add("No Items Checked Out");
        }
        yourCheckedOutItems = (ListView) findViewById(R.id.your_items);
        yourCheckedOutItems.setAdapter(new SearchAdapter(this, R.layout.search_list2, yourItems));


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu,
                                    View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.your_item_menu, menu);
    }

    private void checkInButtonMethod(int position) {
        int index = position;
        String currentItem = yourItems.get(index);
        yourItems.remove(currentItem);
        Intent removeIntent = new Intent(this, ViewCheckedOutActivity.class);
        Bundle userList = new Bundle();
        userList.putStringArrayList("LIST", yourItems);
        startActivity(removeIntent);
    }

    public void returnMain(View view) {
        Intent returnMainIntent = new Intent(this, MainPageActivity.class);
        Bundle userList = new Bundle();
        if(yourItems.contains("No Items Checked Out")) {
            yourItems.remove("No Items Checked Out");
        }
        userList.putStringArrayList("CHECKIN", yourItems);
        startActivity(returnMainIntent);
    }


    private class SearchAdapter extends ArrayAdapter<String> {
        private int layout;

        public SearchAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            layout = resource;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            SearchResultsActivity.ViewHolder primaryHolder = null;
            if(convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.itemName = (TextView) convertView.findViewById(R.id.itemName2);
                viewHolder.itemName.setText(getItem(position));
                viewHolder.checkInButton = (Button) convertView.findViewById(R.id.checkInButton);
                viewHolder.checkInButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view){
                        checkInButtonMethod(position);

                    }
                });

                convertView.setTag(viewHolder);

            }
            else {
                primaryHolder = (SearchResultsActivity.ViewHolder) convertView.getTag();
                primaryHolder.itemName.setText(getItem(position));
            }
            return convertView;
        }


    }

    public class ViewHolder {
        TextView itemName;
        Button checkInButton;

    }


}
