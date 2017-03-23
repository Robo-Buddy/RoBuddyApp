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
    static ArrayList<String> yourItems = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_checked_out);

        yourCheckedOutItems = (ListView) findViewById(R.id.your_items);
        registerForContextMenu(yourCheckedOutItems);

        Intent intent = getIntent();
        //yourList = intent.getParcelableArrayListExtra("YOUR_LIST");
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
            case R.id.view_item_yours:
                int index = info.position;
                String viewItem = yourItems.get(index);
                Intent intent = new Intent(this, ItemDetailActivity.class);
                Bundle itemInfo = new Bundle();
                itemInfo.putString("ITEMDETAIL", viewItem);
                intent.putExtras(itemInfo);
                startActivity(intent);
                return true;
            case R.id.item_return:
                index = info.position;
                intent = new Intent(this, MainPageActivity.class);
                yourItems.remove(index);
                //intent.putParcelableArrayListExtra("UPDATED_LOCAL_LIST", yourList);
                startActivity(intent);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


}
