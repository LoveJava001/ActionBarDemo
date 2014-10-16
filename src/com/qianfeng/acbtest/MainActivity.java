package com.qianfeng.acbtest;

import android.R.integer;
import android.app.ActionBar;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.ActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.ShareActionProvider;
import android.widget.ShareActionProvider.OnShareTargetSelectedListener;


public class MainActivity extends FragmentActivity implements OnQueryTextListener, OnClickListener, OnShareTargetSelectedListener, TabListener, OnPageChangeListener {

	private ViewPager pager;
	private ActionBar actionBar;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        pager = (ViewPager) findViewById(R.id.viewPager);
        
        pager.setOnPageChangeListener(this);
        
        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return 2;
			}
			
			@Override
			public Fragment getItem(int arg0) {
				Fragment ret = new BlankFragment();
				
				Bundle arguments = new Bundle();
				arguments.putInt("Index", arg0);
				ret.setArguments(arguments);
				
				return ret;
			}
		});
        
        
        // 1. 获取ActionBar (Android 3.0以上，使用 getActionBar())
        //    v7包中的ActionBar 需要ActionBarActivity 通过 getSupportActionBar()
        //    来获取
        
        actionBar = getActionBar();
        // 设置导航模式为 Tab 显示
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        // 创建第一个Tab，并且添加到 ActionBar上面
        Tab tab = actionBar.newTab();
        tab.setText("好人");
        // 此处必须要给Tab设置回调接口，否则，程序抛异常
        tab.setTabListener(this);
        
        actionBar.addTab(tab);
        
        // 创建第二个Tab, 并且添加到 ActionBar上面
        tab = actionBar.newTab();
        tab.setText("坏人");
        
        tab.setTabListener(this);
        
        actionBar.addTab(tab);
        
        ////////////////////////////////////////
        // 1. 显示最左侧箭头
        
        actionBar.setDisplayHomeAsUpEnabled(true);
        
        // 2. 去掉应用程序的图标
        ColorDrawable icon = new ColorDrawable(Color.TRANSPARENT);
        // 以下两个方法设置ActionBar的左侧应用程序图标
        actionBar.setIcon(icon);  // API 14
        actionBar.setLogo(icon);  // API 14 才可以调用
        
        // 设置优先显示 Logo 而不是 Icon
//        actionBar.setDisplayUseLogoEnabled(useLogo);
        
        // 3. 去掉默认应用程序标题
        actionBar.setTitle("");
        
        // 4. 设置居中的标题
        // 可以设置外部的 layout 来实现 标题
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.action_bar_title);
        
        
        
    }


    /**
     * 加载Option菜单，菜单中如果包含针对ActionBar的设置，那么
     * 定义的菜单项会自动进行处理，可以自动的添加到 ActionBar 中
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       
    	getMenuInflater().inflate(R.menu.main, menu);
        
        // TODO 需要进行 MenuItem 的获取，从而找到那个 SearchView 的实例
        //      进行事件的处理
        
        MenuItem  item = menu.findItem(R.id.action_search);
        
        if(item != null){
        	
        		SearchView searchView = (SearchView)item.getActionView();
        		// 设置 查询输入时的事件监听
        		searchView.setOnQueryTextListener(this);
        		
        		// 设置ActionBar上面搜索按钮的事件点击监听
        		searchView.setOnSearchClickListener(this);
        		
        }
        
        item = menu.findItem(R.id.action_share);
        
        if(item != null){
        		ShareActionProvider provider = (ShareActionProvider)item.getActionProvider();
        		
        		
        		// 设置分享的默认类型
        		Intent shareIntent = new Intent(Intent.ACTION_SEND);
        		
        		shareIntent.setType("text/plain");
        		
        		shareIntent.putExtra(Intent.EXTRA_TEXT, "Hello World");
        		
        		provider.setShareIntent(shareIntent);
        		
        		// 设置分享的事件
        		// 设置分享目标选择监听器
        		provider.setOnShareTargetSelectedListener(this);
        		
        		
        }
        
        return true;
    }

    /**
     * Option菜单项点击的时候，进行回调，执行菜单的功能。同时支持 ActionBar 中
     * 每一个 Action的处理
     * 
     * @return boolean 如果返回 true 证明菜单项的当前类处理完成了，父类不需要在处理了
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
//        else if (id == R.id.action_search) {
//			
//		}
        return super.onOptionsItemSelected(item);
    }


	@Override
	public void onClick(View v) {
		// Log.d("ACB", "OnSearchClick ");
	}


	/**
	 * 提交搜索时回调，也就是 输入法中的搜索按钮点击
	 * @return boolean 相当于当前监听器是否有处理这个搜索，如果返回 false 将由SearchView默认处理
	 */
	@Override
	public boolean onQueryTextSubmit(String query) {
		Log.d("ACB", "Search submit " + query);
		return true;
	}


	@Override
	public boolean onQueryTextChange(String newText) {
		Log.d("ACB", "Search change:  " + newText);
		return true;
	}


	/**
	 * 当选择分享的目标之后（短信、QQ、微博等），会调用这个方法，进行设置
	 */
	@Override
	public boolean onShareTargetSelected(ShareActionProvider source,
			Intent intent) {
		
		
		
		return false;
	}
	
	
	/**
	 * 切换选择Tab，当使用 tab.select() 的时候，会自动触发 TabListener的事件回调
	 * @param pos
	 */
	private void switchTab(int pos){
		Tab tab = actionBar.getTabAt(pos);
		tab.select();
	}
	
	/////////////////////////////////////
	// 以下是 Tab Listener 的回调方法


	/**
	 * 这个方法，用于Tab选中时调用
	 */
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// todo 需要知道选择的是哪一个Tab
		 int position = tab.getPosition();
		 Toast.makeText(this, "选择了: " + position, Toast.LENGTH_SHORT).show();
		
		// TODO 可以进行 ViewPager的切换。或者时 Fragment 的replace.
		 
		pager.setCurrentItem(position);
		
	}


	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		int position = tab.getPosition();
		Toast.makeText(this, "取消选择了: " + position, Toast.LENGTH_SHORT).show();
	}


	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		int position = tab.getPosition();
		Toast.makeText(this, "重新选择了: " + position, Toast.LENGTH_SHORT).show();
	}


	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onPageSelected(int arg0) {
//		switchTab(arg0);
		actionBar.setSelectedNavigationItem(arg0);
		
	}
}
