package edu.virginia.cs.cs4720.shellder;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robbie on 9/9/2015.
 */

public class CustomAdapter extends BaseAdapter{

    private Context context;
    private List<Item> itemList;

    public CustomAdapter(Context context) {
        super();
        this.context = context;
        this.itemList = getDataForListView();
    }

    public List<Item> getDataForListView() {

        // Create the list to return
        List<Item> itemList = new ArrayList<Item>();
        // Create the string array from the strings.xml file
        Resources res = this.context.getResources();
        String[] descriptions = res.getStringArray(R.array.item_description_array);

        // Populate the list with activities from the bucket list
        for (int i = 0; i < 35; i++) {
            Item item = new Item(i+1, descriptions[i]);
            itemList.add(item);
        }
        return itemList;
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return itemList.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Item getItem(int position) {
        return itemList.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listitem, parent, false);
        }

        TextView title = (TextView) convertView.findViewById(R.id.textView1);
        TextView description = (TextView) convertView.findViewById(R.id.textView2);

        Item item = itemList.get(position);

        title.setText(item.getNumber() + "");
        description.setText(item.getDescription());

        return convertView;
    }
}
