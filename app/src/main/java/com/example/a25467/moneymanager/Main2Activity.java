package com.example.a25467.moneymanager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener{
    int change=4;
    private TextView title,item_notes,item_bookkeeping;
    private ViewPager vp;
    private New_Notes_Fragment newNotes;
    private BookKeeping_Fragment bookKeeping;
    private List<Fragment> mFragementList=new ArrayList<Fragment>();
    private FragmentAdapter mFragmentAdapter;
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private Uri imageUri;
    private ImageView picture;
    private static final int TAKE_PHOTO=1;
    public File outputImage;


   // private CircleImageView circleImageView;



    String []titles=new String[]{"便签","记账"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_main2);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
      //  NavigationView navView=(NavigationView)findViewById(R.id.nav_view);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar !=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.category);
        }

        View headerview = navigationView.inflateHeaderView(R.layout.nav_header);
        picture=(ImageView)headerview.findViewById(R.id.icon_image);
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picture.showContextMenu();
            }
        });

       // final CircleImageView head_iv = (CircleImageView) headerview.findViewById(R.id.icon_image);
      /*head_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                head_iv.showContextMenu();
                //Toast.makeText(Main2Activity.this, "jjjjjjj", Toast.LENGTH_SHORT).show();
            }
        });*/


        picture.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(0, 0, 0, "拍照");
                menu.add(1, 1, 1, "从相册中选");

            }
        });





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


    private void initViews(){
        title=(TextView)findViewById(R.id.title);
        item_notes=(TextView)findViewById(R.id.item_notes);
        item_bookkeeping=(TextView)findViewById(R.id.item_Book_Keepping) ;


        item_notes.setOnClickListener(this);
       item_bookkeeping.setOnClickListener(this);

        vp=(ViewPager)findViewById(R.id.mainViewPager);
        bookKeeping=new BookKeeping_Fragment();
        newNotes=new New_Notes_Fragment();



        mFragementList.add(newNotes);
        mFragementList.add(bookKeeping);
    }

    @Override
    public void onClick(View v){


        switch (v.getId()){
            case R.id.item_notes:
                vp.setCurrentItem(0,true);
                change=4;
                break;
            case R.id.item_Book_Keepping:
                vp.setCurrentItem(1,true);
                change=1;
                break;
            case R.id.icon_image:
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
                    if (outputImage.exists()){
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT>=24){
                    imageUri= FileProvider.getUriForFile(Main2Activity.this,
                            "com.example.a25467.moneymanager.fileprovider",outputImage);
                }
                else {
                    imageUri=Uri.fromFile(outputImage);
                }
                Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,TAKE_PHOTO);
                break;
            case 1:
                break;
                default:
                    break;

        }
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){

        View headerview = navigationView.inflateHeaderView(R.layout.nav_header);
        picture = (ImageView) headerview.findViewById(R.id.icon_image);
        switch (requestCode){
            case TAKE_PHOTO:
                if (resultCode==RESULT_OK){
                    try{
                        Bitmap bmp=BitmapFactory.decodeFile(outputImage.getAbsolutePath());
                        Log.d("hhh",String.valueOf(bmp));

                        //Bitmap bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        picture.setImageBitmap(bmp);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
                break;
                default:
                    break;
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
