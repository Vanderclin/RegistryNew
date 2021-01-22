package com.vanderclin.registry;

import android.widget.ListAdapter;
import com.google.firebase.database.IgnoreExtraProperties;
import java.util.List;

@IgnoreExtraProperties
public class ProductsList
{
	
    private String key;
    private String name;
	private String code;

    public ProductsList()
	{

    }

    public ProductsList(String key, String name, String code)
	{
        this.key = key;
        this.name = name;
		this.code = code;
    }

    public String getKey()
	{
        return key;
    }

    public String getName()
	{
        return name;
    }

	public String getCode()
	{
		return code;
	}


}
