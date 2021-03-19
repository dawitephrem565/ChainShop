package com.filenber.chainshop;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private static SharedPrefManager mInstance;
    private Context mCtx;

    private static final String SHARED_PREF_NAME = "mysharedpref12";
    private static final String KEY_lan = "key";
    private static final String save_phone = "Saved_phone";
  private static final String Save_UID = "Saved_UID";
  private static  final String Bussiness_name ="Bussines_name";
  private static  final String Bussiness_ID ="Bussines_Id";
  private static  final String Bussiness_Descr ="Bussines_Descr";
  private static  final String Bussiness_Commision ="Bussines_commission";
    private SharedPrefManager(Context context) {
        mCtx = context;

    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }
    public void save_num(String save_num ){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(save_phone, save_num);

        editor.apply();


    }
  public void save_uid(String save_uid ){

    SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();

    editor.putString(Save_UID, save_uid);

    editor.apply();


  }
    public void save_lan(String lan ){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_lan, lan);

        editor.apply();


    }
  public void Save_Business_Data (String Business_Name_ed,String Business_Id_ed,String Business_Descr_ed,String Bussiness_Commision_ed)
  {
    SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();

    editor.putString(Bussiness_name, Business_Name_ed);
    editor.putString(Bussiness_ID, Business_Id_ed);
    editor.putString(Bussiness_Descr, Business_Descr_ed);
    editor.putString(Bussiness_Commision, Bussiness_Commision_ed);
    editor.apply();
  }
  public String  getBussiness_Name()
  {
    SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    return sharedPreferences.getString(Bussiness_name, null);

  }
  public String  getBussiness_ID()
  {
    SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    return sharedPreferences.getString(Bussiness_ID, null);

  }
  public String  getBussiness_Descr()
  {
    SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    return sharedPreferences.getString(Bussiness_Descr, null);

  }
  public String  getBussiness_Commision()
  {
    SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    return sharedPreferences.getString(Bussiness_Commision, null);

  }
  public String saved_uid(){
    SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    return sharedPreferences.getString(Save_UID, null);
  }

  public String saved(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(save_phone, null);
    }

    public String lan(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_lan, null);
    }
}
