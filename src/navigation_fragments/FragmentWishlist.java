package navigation_fragments;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dailyexpenses.AddNewWishDialog;
import com.example.dailyexpenses.EditWishDialog;
import com.example.dailyexpenses.R;
import com.example.dailyexpenses.Wish;
import com.example.dailyexpenses.WishlistItemAdapter;

import database.DBHelper;

public class FragmentWishlist extends Fragment{
	
	private TextView tabTextWishlist;
	private WishlistItemAdapter wishlistadapter;
	private DBHelper db;
	private List<Wish> wishlist;
	private ListView wishListView;
	private Button addWishButton;
	private int currentWishlistItem;
	
	
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
	    	
	        View root = inflater.inflate(R.layout.wishlist_fragment, null);
	        
	        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),"fonts/LANENAR.ttf");
	        tabTextWishlist = (TextView) root.findViewById(R.id.tabTextWishlist);
	        tabTextWishlist.setTypeface(tf);
	        
	        db = new DBHelper(getActivity());
	        wishlist = db.getAllWishes();
	        
	        wishListView = (ListView) root.findViewById(R.id.wishesListView);
	        addWishButton = (Button) root.findViewById(R.id.addingWishButton);
	        
	        wishlistadapter = new WishlistItemAdapter(getActivity(), R.layout.wish_simple_list_item, wishlist, getActivity());
	        wishListView.setAdapter(wishlistadapter);
	        
	        addWishButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					DialogFragment addWishDialog = new AddNewWishDialog();
					addWishDialog.show(getActivity().getSupportFragmentManager(), "addwish");
					
					wishlistadapter.notifyDataSetChanged();
					wishListView.invalidateViews();
				}
			});
	        
	        wishListView.setOnItemLongClickListener(new OnItemLongClickListener() {

	            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
	                    int pos, long id) {
	            	
	            	currentWishlistItem = pos;
	                
	            	AlertDialog.Builder editORdeleteDialog = new AlertDialog.Builder(
	        				getActivity());
	            	
	            	editORdeleteDialog.setTitle(wishlist.get(currentWishlistItem).getName());
	            	editORdeleteDialog.setCancelable(true);
	            	
	            	editORdeleteDialog.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							
							DialogFragment editWishDialog = new EditWishDialog(wishlist.get(currentWishlistItem));
							editWishDialog.show(getActivity().getSupportFragmentManager(), "editwish");
							
							dialog.cancel();
						}
					});
	            	editORdeleteDialog.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							
							db.deleteWish(wishlist.get(currentWishlistItem).getWishId());
							
							dialog.cancel();
						}
					});             

	                AlertDialog alertDialog = editORdeleteDialog.create();
	                
	                alertDialog.show();
	                
	                return true;
	            }
	        }); 
	        
	        return root;
	    }
	 
	
}