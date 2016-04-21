package quikr.com.dietpro.Activity.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import quikr.com.dietpro.Activity.Model.MyOrderModel;

/**
 * Created by Rakshith
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    private static final int        DATABASE_VERSION    = 1;
    private static final String     DATABASE_NAME       = "DietPro";

    private static final String     TABLE_MY_ORDERS     = "MyOrders";
    private static final String     TABLE_MY_ADDRESS    = "MyAddress";

    private static final String     MY_ORDER_ID         = "myOrderId";
    private static final String     MY_ORDER_DATE       = "myOrderDate";
    private static final String     MY_ORDER_PRICE      = "myOrderPrice";
    private static final String     MY_ORDER_DETAILS    = "myOrderDetails";

    private static final String     MY_ADDRESS_ID         = "myAddressId";
    private static final String     MY_ADDRESS_Name       = "myAddressName";
    private static final String     MY_ADDRESS_PH_NO      = "myAddressPhoneNum";
    private static final String     MY_ADDRESS_LOCATION   = "myAddressLoaction";

    private static final String CREATE_TABLE_MY_ORDER = "CREATE TABLE " + TABLE_MY_ORDERS + "(" + MY_ORDER_ID + " INTEGER AUTO INCREMENT,"
            + MY_ORDER_DATE + " DATETIME," + MY_ORDER_PRICE + " TEXT," + MY_ORDER_DETAILS + " TEXT" + ")";

    private static final String CREATE_TABLE_MY_ADDRESS = "CREATE TABLE " + TABLE_MY_ADDRESS + "(" + MY_ADDRESS_ID + " INTEGER AUTO INCREMENT,"
            + MY_ADDRESS_Name + " TEXT," + MY_ADDRESS_PH_NO + " INTEGER," + MY_ADDRESS_LOCATION + " TEXT" + ")";

    public DataBaseHelper(Context context) {
        super(context , DATABASE_NAME , null , DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MY_ORDER);
        db.execSQL(CREATE_TABLE_MY_ADDRESS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + TABLE_MY_ORDERS);
        db.execSQL("DROP TABLE IF EXIST " + TABLE_MY_ADDRESS);
        onCreate(db);
    }

    public long insertMyOrder(MyOrderModel orderModel)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MY_ORDER_DATE, String.valueOf(orderModel.getOrderDate()));
        contentValues.put(MY_ORDER_PRICE, orderModel.getOrderPrice());
        contentValues.put(MY_ORDER_DETAILS, orderModel.getOrderDetail());

        long orderId = sqLiteDatabase.insert(TABLE_MY_ORDERS , null , contentValues);
        return orderId;
    }

    public List<MyOrderModel> getAllMyOrders()
    {
        List<MyOrderModel> myOrderModelList = new ArrayList<MyOrderModel>();
        String selectQuery = "SELECT * FROM " + TABLE_MY_ORDERS + " ORDER BY " + MY_ORDER_ID + " DESC";

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        if(cursor.moveToFirst())
        {
            do {
                MyOrderModel myOrderModel = new MyOrderModel();
                myOrderModel.setOrderDate(cursor.getString(cursor.getColumnIndex(MY_ORDER_DATE)));
                myOrderModel.setOrderPrice(cursor.getString(cursor.getColumnIndex(MY_ORDER_PRICE)));
                myOrderModel.setOrderDetail(cursor.getString(cursor.getColumnIndex(MY_ORDER_DETAILS)));

                myOrderModelList.add(myOrderModel);
            }while (cursor.moveToNext());
        }
        return myOrderModelList;
    }

    public int myOrderCount()
    {
        String countQuery = "SELECT * FROM " + TABLE_MY_ORDERS;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(countQuery, null);
        int count = cursor.getCount();

        return count;
    }

    public int updateMyOrder(MyOrderModel myOrderModel)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MY_ORDER_DATE , myOrderModel.getOrderDate());
        contentValues.put(MY_ORDER_PRICE , myOrderModel.getOrderPrice());
        contentValues.put(MY_ORDER_DETAILS , myOrderModel.getOrderDetail());

        return sqLiteDatabase.update(TABLE_MY_ORDERS , contentValues , MY_ORDER_ID + " = ? " , new String[]{String.valueOf(myOrderModel.getOrderId())});
    }

    public void deleteMyOrder(long id)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_MY_ORDERS, MY_ORDER_ID + " = ? ", new String[]{String.valueOf(id)});
    }

    public void closeDB()
    {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        if(sqLiteDatabase != null && sqLiteDatabase.isOpen())
        {
            sqLiteDatabase.close();
        }
    }

    public void deleteDataBase()
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_MY_ORDERS , null , null);
    }

    public boolean isDataPresent(String tableName , String fieldName , String fieldValue)
    {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String rawQuery = "SELECT * FROM " + tableName + " WHERE " + fieldName + " = " + fieldValue;

        Cursor cursor = sqLiteDatabase.rawQuery(rawQuery , null);
        if(cursor.getCount() <= 0)
        {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
}
