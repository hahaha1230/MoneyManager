package com.example.a25467.moneymanager.Activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.a25467.moneymanager.Datatable.InformationDataTable;
import com.example.a25467.moneymanager.Fragment.BookKeepingFragment;
import com.example.a25467.moneymanager.Fragment.NewNotesFragment;
import com.example.a25467.moneymanager.R;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainInterfaceActivity extends AppCompatActivity implements View.OnClickListener{
    int change=4;
    private TextView title,item_notes,item_bookkeeping;
    private ViewPager vp;
    private NewNotesFragment newNotes;
    private BookKeepingFragment bookKeeping;
    private List<Fragment> mFragementList=new ArrayList<Fragment>();
    private FragmentAdapter mFragmentAdapter;
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private Uri imageUri;
    private ImageView picture;
    private static final int TAKE_PHOTO=1;
    private  static  final int CHOOSE_PHOTO=2;
    public File outputImage;
    public  View headerview;
    private TextView username;




    // private CircleImageView circleImageView;



    String []titles=new String[]{"便签","记账"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_maininterface);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        headerview = navigationView.inflateHeaderView(R.layout.nav_header);
        username=(TextView)headerview.findViewById(R.id.username);
        //显示用户名
        InformationDataTable informationDataTable=new InformationDataTable();

      //  NavigationView navView=(NavigationView)findViewById(R.id.nav_view);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar !=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.category);
        }




        navigationView.setCheckedItem(R.id.setting);//设置默认为设置
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //分享应用
                if (item.getItemId()==R.id.share){
                    Intent intent=new Intent(Intent.ACTION_SEND);
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_SUBJECT,"share");
                    intent.putExtra(Intent.EXTRA_TEXT,"i have successfully share my message through my app");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(Intent.createChooser(intent,getTitle()));
                    //Toast.makeText(MainInterfaceActivity.this,"hhhhh",Toast.LENGTH_SHORT).show();
                }
                //设置
                else if (item.getItemId()==R.id.setting){
                    Intent intent=new Intent(MainInterfaceActivity.this,SettingActivity.class);
                    startActivity(intent);
                }
                else if (item.getItemId()==R.id.weather){
                    if(gMenuItem!=null){
                        gMenuItem.setTitle("Changed");
                    }

                   // Intent intent=new Intent(MainInterfaceActivity.this,WeatherActivity.class);
                    //startActivity(intent);
                }
                return false;
            }
        });

        //若点击头像，则弹出对话框让用户选择拍照或从手机图库中选择
        picture=(ImageView)headerview.findViewById(R.id.icon_image);


        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picture.showContextMenu();
            }
        });

        //若个人信息数据表中无记录，则创建一条
        List<InformationDataTable>informationDataTables= DataSupport.findAll(InformationDataTable.class);
        if (informationDataTables.isEmpty()){
            informationDataTable.setName("李华");
            informationDataTable.setSex("男");
            informationDataTable.setBudget_Pay(5000.00);
            informationDataTable.setBudget_Income(10000.00);
            informationDataTable.save();
        }



        picture.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(0, 0, 0, "拍照");
                menu.add(1, 1, 1, "从相册中选");

            }
        });





        //设置滑动
        initViews();
        mFragmentAdapter=new FragmentAdapter(this.getSupportFragmentManager(),mFragementList);
        vp.setOffscreenPageLimit(2);
        vp.setAdapter(mFragmentAdapter);
        vp.setCurrentItem(0);
        item_notes.setTextColor(Color.parseColor("#66CDAA"));

        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                title.setText(titles[position]);
                changeTextColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
   public MenuItem  gMenuItem=null;
    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu){
        getMenuInflater().inflate(R.menu.nav_menu, menu);
        gMenuItem= menu.findItem(R.id.weather);
        return true;
    }


    private void initViews(){
        title=(TextView)findViewById(R.id.title);
        item_notes=(TextView)findViewById(R.id.item_notes);
        item_bookkeeping=(TextView)findViewById(R.id.item_Book_Keepping) ;


        item_notes.setOnClickListener(this);
       item_bookkeeping.setOnClickListener(this);

        vp=(ViewPager)findViewById(R.id.mainViewPager);
        bookKeeping=new BookKeepingFragment();
        newNotes=new NewNotesFragment();



        mFragementList.add(newNotes);
        mFragementList.add(bookKeeping);
    }

    @Override
    public void onClick(View v){

        switch (v.getId()){
            case R.id.item_notes:
                vp.setCurrentItem(0,true);
                break;
            case R.id.item_Book_Keepping:
                vp.setCurrentItem(1,true);
                break;

            default:
                    break;
        }

    }
    @Override
    public boolean onContextItemSelected(MenuItem item){
        switch (item.getGroupId()){
            case 0:
                 outputImage=new File(getExternalCacheDir(),"output_image.jpg");
                try{
                    //如果已经存在了拍照的照片，则删除照片
                    if (outputImage.exists()){
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT>=24){
                    imageUri= FileProvider.getUriForFile(MainInterfaceActivity.this,
                            "com.example.a25467.moneymanager.fileprovider",outputImage);
                }
                else {
                    imageUri=Uri.fromFile(outputImage);
                }
                Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,TAKE_PHOTO);
                break;
                //调用手机相册中的照片
            case 1:
                if (ContextCompat.checkSelfPermission(MainInterfaceActivity.this, Manifest.permission
                .WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainInterfaceActivity.this,new
                    String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }
                else {
                    openAlbum();
                }
                break;
                default:
                    break;

        }

        return true;
    }
    //打开相册
    private void  openAlbum(){
        Intent intent=new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permission,int [] grantResults){
        switch (requestCode){
            case 1:
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }
                else {
                    Toast.makeText(this,"您拒绝了请求",Toast.LENGTH_SHORT).show();
                }
                break;
                default:
                    break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){

        picture = (ImageView) headerview.findViewById(R.id.icon_image);
        switch (requestCode){
            case TAKE_PHOTO:
                if (resultCode==RESULT_OK){
                    try{
                        Bitmap bmp=BitmapFactory.decodeFile(outputImage.getAbsolutePath());
                        Log.d("hhh",String.valueOf(bmp));
                        //picture.setImageDrawable(null);
                        //picture.setImageResource(null);

                        //Bitmap bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        picture.setImageBitmap(bmp);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
                break;
            case CHOOSE_PHOTO:
                //if (requestCode==RESULT_OK){
                    Log.d("hhh","223");
                    //判断手机系统版本号
                    if (Build.VERSION.SDK_INT>=19){
                        //4.4以及以上版本使用这个方法处理图片
                        handleImageOnKitKat(data);
                    }
                    else {
                        //4.4以下版本使用这个方法处理图片
                        handleImageBeforeKitKat(data);
                    }
                //}
                break;
                default:
                  break;
        }
    }
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data){
        String imagePath=null;
        Uri uri=data.getData();
        if (DocumentsContract.isDocumentUri(this,uri)){
            //如果是document类型的URI，则通过document id处理
            //Log.d("hhh","111");
            String docID=DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id=docID.split(":")[1];//解析出数字格式ID
                String selection=MediaStore.Images.Media._ID+"="+id;
                imagePath=getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
                //Log.d("hhh","112");
            }else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri= ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(docID));
                imagePath=getImagePath(contentUri,null);
               // Log.d("hhh","113");
            }
        }
        else if ("content".equalsIgnoreCase(uri.getScheme())){
            //如果是content类型的URI，则使用普通方式处理
            imagePath=getImagePath(uri,null);
           // Log.d("hhh","114");
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())){
            //如果是file类型的uri，直接获取图片路径即可
            imagePath=uri.getPath();
            Log.d("hhh","115");
        }
        displayImage(imagePath);//根据路径显示图片
        Log.d("hhh","116");
    }
    private void handleImageBeforeKitKat(Intent data){
        Uri uri=data.getData();
        String imagePath=getImagePath(uri,null);
        displayImage(imagePath);
    }
    private String getImagePath(Uri uri,String selection){
        String path=null;
        //通过uri和selection来获取真实的图片路径
        Cursor cursor=getContentResolver().query(uri,null,selection,null,null);
        if (cursor !=null){
            if (cursor.moveToFirst()){
                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
    //显示照片
    private void displayImage(String imagePath){
        Log.d("hhh","117");
        if (imagePath !=null){
            Bitmap bitmap=BitmapFactory.decodeFile(imagePath);
            Log.d("hhh",String.valueOf(bitmap));
            picture.setImageBitmap(bitmap);
        }
        else {
            Toast.makeText(this,"获得图片失败",Toast.LENGTH_SHORT).show();
        }
    }









    public class FragmentAdapter extends FragmentPagerAdapter{

        List<Fragment>fragmentList=new ArrayList<Fragment>();

        public  FragmentAdapter(FragmentManager fm,List<Fragment>fragmentList){
            super(fm);
            this.fragmentList=fragmentList;
        }
        @Override
        public Fragment getItem(int position){
            return fragmentList.get(position);
        }
        @Override
        public  int getCount(){
            return fragmentList.size();
        }
    }
    private  void changeTextColor(int position){
        if(position==0){
            item_notes.setTextColor(Color.parseColor("#66CDAA"));
            item_bookkeeping.setTextColor(Color.parseColor("#000000"));
        }
        else  if(position==1){
            item_notes.setTextColor(Color.parseColor("#000000"));
           item_bookkeeping.setTextColor(Color.parseColor("#66CDAA"));

        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
                default:
                    break;
        }
        return true;
    }


}
