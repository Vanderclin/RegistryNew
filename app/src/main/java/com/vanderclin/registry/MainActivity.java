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

import com.vanderclin.registry.R;

public class MainActivity extends AppCompatActivity
{
	private int SETTINGS_ACTION = 1;
    private EditText editTextName;
	private EditText editTextCode;
    private ListView listViewArtists;
	private FloatingActionButton floatingActionButton;
	private CoordinatorLayout coordinatorLayout;
    private List<NameList> namelists;
	private CharSequence[] values = {"Carnes & Aves "," Frios "," Padaria "," Peixaria "," Rotisseria "};
	private AlertDialog alertDialog1;
	
	private static final String SHARED_PREFS = "sharedPrefs";
    DatabaseReference databaseProducts;

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
        listViewArtists = (ListView) findViewById(R.id.listViewArtists);

		floatingActionButton = (FloatingActionButton) findViewById(R.id.fab_add);
        namelists = new ArrayList<>();

		floatingActionButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view)
				{
					DialogAdd();
				}
			});

        listViewArtists.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
				@Override
				public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
				{
					NameList nmlist = namelists.get(i);
					showUpdateDeleteDialog(nmlist.getKey(), nmlist.getName(), nmlist.getCode());
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
						NameList artist = new NameList(key, name, code);

						//Saving the Artist
						databaseProducts.child(key).setValue(artist);

						//setting edittext to blank again
						editTextName.setText("");
						editTextCode.setText("");

						//displaying a success toast
						Toast.makeText(MainActivity.this, "Name added", Toast.LENGTH_LONG).show();
						b.dismiss();
					}
					else
					{
						//if the value is not given displaying a toast
						Toast.makeText(MainActivity.this, "Please enter a name", Toast.LENGTH_LONG).show();
					}


				}
			});
	}
	/*
	 private void addnamelist() {

	 String name = editTextName.getText().toString().trim();
	 String code = editTextCode.getText().toString().trim();

	 //checking if the value is provided
	 if (!TextUtils.isEmpty(name)) {

	 //getting a unique id using push().getKey() method
	 //it will create a unique id and we will use it as the Primary Key for our Artist
	 String key = databaseArtists.push().getKey();

	 //creating an Artist Object
	 NameList artist = new NameList(key, name, code);

	 //Saving the Artist
	 databaseArtists.child(key).setValue(artist);

	 //setting edittext to blank again
	 editTextName.setText("");
	 editTextCode.setText("");

	 //displaying a success toast
	 Toast.makeText(this, "Name added", Toast.LENGTH_LONG).show();
	 } else {
	 //if the value is not given displaying a toast
	 Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
	 }
	 }
	 */

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
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart()
	{
        super.onStart();
        //attaching value event listener
        databaseProducts.addValueEventListener(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot dataSnapshot)
				{
					//clearing the previous artist list
					namelists.clear();

					//iterating through all the nodes
					for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
					{
						//getting artist
						NameList nmlist = postSnapshot.getValue(NameList.class);
						//adding artist to the list
						namelists.add(nmlist);
					}

					//creating adapter
					ShowNameList myAdapter = new ShowNameList(MainActivity.this, namelists);
					listViewArtists.setAdapter(myAdapter);
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

        dialogBuilder.setTitle(name);
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view)
				{
					String name = editTextName.getText().toString().trim();
					String code = editTextCode.getText().toString().trim();
					if (!TextUtils.isEmpty(name))
					{
						updateArtist(key, name, code);
						b.dismiss();
					}
				}
			});

        buttonDelete.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view)
				{

					deleteArtist(key);
					b.dismiss();
				}
			});
    }


    private boolean updateArtist(String key, String name, String code)
	{
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference(spk).child(key);

        //updating artist
        NameList nmlist = new NameList(key, name, code);
        dR.setValue(nmlist);
        Toast.makeText(getApplicationContext(), "Name Updated", Toast.LENGTH_LONG).show();
        return true;
    }


    private boolean deleteArtist(String id)
	{
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference(spk).child(id);

        //removing artist
        dR.removeValue();

        //removing all tracks
        Toast.makeText(getApplicationContext(), "Name Deleted", Toast.LENGTH_LONG).show();

        return true;
    }
	
	public void createAlertDialog()
	{
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(getString(R.string.choose_your_session));
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
							String frios = "frios";
							editor.putString("FIREBASE_KEY", frios);
							editor.apply();
							
							break;
						case 2:
							String padaria = "padaria";
							editor.putString("FIREBASE_KEY", padaria);
							editor.apply();
							break;
						case 3:
							String peixaria = "peixaria";
							editor.putString("FIREBASE_KEY", peixaria);
							editor.apply();
							break;
						case 4:
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

}
