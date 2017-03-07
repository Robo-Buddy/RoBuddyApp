package seniordesign.robuddyuserapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ViewCheckedOutActivity extends AppCompatActivity {

    ListView yourCheckedOutItems;
    static ArrayList<Item> yourList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_checked_out);

        yourCheckedOutItems = (ListView) findViewById(R.id.your_items);
        registerForContextMenu(yourCheckedOutItems);

        Intent intent = getIntent();
        //yourList = intent.getParcelableArrayListExtra("YOUR_LIST");
        ArrayList<String> yourItems = new ArrayList<String>();
        yourItems.add("Screwdriver");
        ArrayAdapter<String> viewAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, yourItems);
        yourCheckedOutItems.setAdapter(viewAdapter);




    }

    @Override
    public void onCreateContextMenu(ContextMenu menu,
                                    View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.your_item_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch(item.getItemId()) {
            case R.id.view_contact_yours:
                int index = info.position;
                Item currentItem = yourList.get(index);
                Intent intent = new Intent(this, ItemDetailActivity.class);
                Bundle itemInfo = new Bundle();
                itemInfo.putParcelable("ITEMDETAIL", currentItem);
                intent.putExtras(itemInfo);
                startActivity(intent);
                return true;
            case R.id.delete_contact:
                index = info.position;
                intent = new Intent(this, UploadSearchActivity.class);
                yourList.remove(index);
                intent.putParcelableArrayListExtra("UPDATED_LOCAL_LIST", yourList);
                UploadSearchActivity.added = true;
                startActivity(intent);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


}
