package com.vanderclin.registry;

import android.content.*;
import android.net.*;
import android.os.*;
import android.preference.*;
import android.support.design.widget.*;
import android.support.v7.app.*;
import android.text.*;
import android.view.*;
import android.widget.*;
import com.google.firebase.database.*;
import com.vanderclin.registry.*;
import java.util.*;

import com.vanderclin.library.AppUtils;
import com.vanderclin.library.UpdateChecker;

import com.vanderclin.registry.R;

public class MainActivity extends AppCompatActivity
{
	private int SETTINGS_ACTION = 1;
    private EditText editTextName;
	private EditText editTextCode;
    private ListView listViewProducts;
	private FloatingActionButton floatingActionButton;
	private CoordinatorLayout coordinatorLayout;
    private List<ProductsList> productsList;
	private CharSequence[] values = {"Carnes & Aves","FLV","Frios","Padaria","Peixaria","Rotisseria"};
	private AlertDialog alertDialog1;
	
	private static final String SHARED_PREFS = "sharedPrefs";
    private DatabaseReference databaseProducts;

	private String spk;
    @Override
    protected void onCreate(Bundle savedInstanceState)
	{
		SharedPreferences pref = PreferenceManager
			.getDefaultSharedPreferences(this);
		String themeName = pref.getString("theme", getString(R.string.dark));
		if (themeName.equals(getString(R.string.dark)))
		{
			setTheme(R.style.dark);
		}
		else 
		if (themeName.equals(getString(R.string.red)))
		{
			setTheme(R.style.red);
		}
		if (themeName.equals(getString(R.string.pink)))
		{
			setTheme(R.style.pink);
		}
		if (themeName.equals(getString(R.string.purple)))
		{
			setTheme(R.style.purple);
		}
		if (themeName.equals(getString(R.string.deepPurple)))
		{
			setTheme(R.style.deepPurple);
		}
		if (themeName.equals(getString(R.string.indigo)))
		{
			setTheme(R.style.indigo);
		}
		if (themeName.equals(getString(R.string.blue)))
		{
			setTheme(R.style.blue);
		}
		if (themeName.equals(getString(R.string.lightBlue)))
		{
			setTheme(R.style.lightBlue);
		}
		if (themeName.equals(getString(R.string.cyan)))
		{
			setTheme(R.style.cyan);
		}
		if (themeName.equals(getString(R.string.teal)))
		{
			setTheme(R.style.teal);
		}
		if (themeName.equals(getString(R.string.green)))
		{
			setTheme(R.style.green);
		}
		if (themeName.equals(getString(R.string.lightGreen)))
		{
			setTheme(R.style.lightGreen);
		}
		if (themeName.equals(getString(R.string.lime)))
		{
			setTheme(R.style.lime);
		}
		if (themeName.equals(getString(R.string.yellow)))
		{
			setTheme(R.style.yellow);
		}
		if (themeName.equals(getString(R.string.amber)))
		{
			setTheme(R.style.amber);
		}
		if (themeName.equals(getString(R.string.orange)))
		{
			setTheme(R.style.orange);
		}
		if (themeName.equals(getString(R.string.deepOrange)))
		{
			setTheme(R.style.deepOrange);
		}
		if (themeName.equals(getString(R.string.brown)))
		{
			setTheme(R.style.brown);
		}
		if (themeName.equals(getString(R.string.grey)))
		{
			setTheme(R.style.grey);
		}
		if (themeName.equals(getString(R.string.blueGrey)))
		{
			setTheme(R.style.blueGrey);
		}
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
		SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
		spk = sharedPreferences.getString("FIREBASE_KEY", "");
		if (TextUtils.isEmpty(spk)) {
			createAlertDialog();
        }
		

        databaseProducts = FirebaseDatabase.getInstance().getReference(spk);

		coordinatorLayout = (CoordinatorLayout) findViewById(R.id.root_coordinator);
		registerForContextMenu(coordinatorLayout);
		checkConnection();

        editTextName = (EditText) findViewById(R.id.editTextName);
		editTextCode = (EditText) findViewById(R.id.editTextCode);
        listViewProducts = (ListView) findViewById(R.id.listViewArtists);

		floatingActionButton = (FloatingActionButton) findViewById(R.id.fab_add);
        productsList = new ArrayList<>();

		floatingActionButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view)
				{
					DialogAdd();
				}
			});

        listViewProducts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
				@Override
				public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
				{
					ProductsList proList = productsList.get(i);
					showUpdateDeleteDialog(proList.getKey(), proList.getName(), proList.getCode());
					return true;
				}
			});

    }

	private void checkConnection()
	{
        ConnectivityManager cm = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting())
		{
			checkUpdate();
        }
		else
		{
            Snackbar snackbarTwo = Snackbar.make(coordinatorLayout, getString(R.string.please_check_your_internet_connection), Snackbar.LENGTH_LONG);
			snackbarTwo.setDuration(60000);
			snackbarTwo.show();
		}
    }


	private void DialogAdd()
	{
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_add, null);
		dialogBuilder.setTitle(R.string.add_product);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = dialogView.findViewById(R.id.editTextName);
		final EditText editTextCode = dialogView.findViewById(R.id.editTextCode);
        final Button addButton = dialogView.findViewById(R.id.buttonAdd);

        final AlertDialog b = dialogBuilder.create();
        b.show();


        addButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view)
				{

					String name = editTextName.getText().toString().trim();
					String code = editTextCode.getText().toString().trim();
					if (!TextUtils.isEmpty(name))
					{
						String key = databaseProducts.push().getKey();
						ProductsList list = new ProductsList(key, name, code);
						databaseProducts.child(key).setValue(list);
						editTextName.setText("");
						editTextCode.setText("");
						Toast.makeText(MainActivity.this, getString(R.string.added_item), Toast.LENGTH_LONG).show();
						b.dismiss();
					}
					if (!TextUtils.isEmpty(code)) {
						Toast.makeText(MainActivity.this, getString(R.string.please_enter_a_name), Toast.LENGTH_LONG).show();
					}
					else
					{
						Toast.makeText(MainActivity.this, getString(R.string.please_enter_a_code), Toast.LENGTH_LONG).show();
					}


				}
			});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == SETTINGS_ACTION)
		{
			if (resultCode == SettingsActivity.RESULT_CODE_THEME_UPDATED)
			{
				finish();
				startActivity(getIntent());
				return;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
	{
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
	{

        int id = item.getItemId();
        if (id == R.id.settings)
		{
			startActivityForResult(new Intent(this, SettingsActivity.class), SETTINGS_ACTION);

        }
		if (id == R.id.about)
		{
			startActivityForResult(new Intent(this, AboutActivity.class), SETTINGS_ACTION);

        }
		if (id == R.id.select_section)
		{
			createAlertDialog();
		}
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart()
	{
        super.onStart();
        databaseProducts.addValueEventListener(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot dataSnapshot)
				{
					productsList.clear();
					for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
					{
						ProductsList proList = postSnapshot.getValue(ProductsList.class);
						productsList.add(proList);
					}
					
					ProductsView mAdapter = new ProductsView(MainActivity.this, productsList);
					listViewProducts.setAdapter(mAdapter);
				}

				@Override
				public void onCancelled(DatabaseError databaseError)
				{

				}
			});
    }




    private void showUpdateDeleteDialog(final String key, String name, String code)
	{

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_edit, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = dialogView.findViewById(R.id.editTextName);
		final EditText editTextCode = dialogView.findViewById(R.id.editTextCode);
        final Button buttonUpdate = dialogView.findViewById(R.id.buttonUpdateArtist);
        final Button buttonDelete = dialogView.findViewById(R.id.buttonDeleteArtist);

        dialogBuilder.setTitle(R.string.add_or_delete);
        final AlertDialog b = dialogBuilder.create();
        b.show();
		
		editTextName.setText(name);
		editTextCode.setText(code);


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view)
				{
					String name = editTextName.getText().toString().trim();
					String code = editTextCode.getText().toString().trim();
					if (!TextUtils.isEmpty(name))
					{
						updateProduct(key, name, code);
						b.dismiss();
					}
				}
			});

        buttonDelete.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view)
				{

					deleteProduct(key);
					b.dismiss();
				}
			});
    }


    private boolean updateProduct(String key, String name, String code)
	{
        DatabaseReference DR = FirebaseDatabase.getInstance().getReference(spk).child(key);
        ProductsList proList = new ProductsList(key, name, code);
        DR.setValue(proList);
        Toast.makeText(getApplicationContext(), getString(R.string.updated_item), Toast.LENGTH_LONG).show();
        return true;
    }


    private boolean deleteProduct(String id)
	{
        DatabaseReference DR = FirebaseDatabase.getInstance().getReference(spk).child(id);
        DR.removeValue();
        Toast.makeText(getApplicationContext(), getString(R.string.deleted_item), Toast.LENGTH_LONG).show();

        return true;
    }
	
	public void createAlertDialog()
	{
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(getString(R.string.choose_your_section));
		builder.setCancelable(false);
        builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int item)
				{
					
					SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
					SharedPreferences.Editor editor = sharedPreferences.edit();

					switch (item)
					{
						case 0:
							String carnes_aves = "carnes_aves";
							editor.putString("FIREBASE_KEY", carnes_aves);
							editor.apply();
							break;
							
						case 1:
							String flv = "flv";
							editor.putString("FIREBASE_KEY", flv);
							editor.apply();
							break;
							
						case 2:
							String frios = "frios";
							editor.putString("FIREBASE_KEY", frios);
							editor.apply();
							break;
							
						case 3:
							String padaria = "padaria";
							editor.putString("FIREBASE_KEY", padaria);
							editor.apply();
							break;
							
						case 4:
							String peixaria = "peixaria";
							editor.putString("FIREBASE_KEY", peixaria);
							editor.apply();
							break;
							
						case 5:
							String rotisseria = "rotisseria";
							editor.putString("FIREBASE_KEY", rotisseria);
							editor.apply();
							break;
							
					}
					alertDialog1.dismiss();
					finish();
					startActivity(getIntent());
				}
			});
        alertDialog1 = builder.create();
        alertDialog1.show();

    }
	
	private void checkUpdate() {
		UpdateChecker.checkForDialog(MainActivity.this);
		UpdateChecker.checkForNotification(MainActivity.this);
	}

}
