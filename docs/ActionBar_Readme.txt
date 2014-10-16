注意事项：

1. 关于 menu菜单xml中，如果加入了 showAsAction 属性，那么需要放在ActionBar上

2. showAsAction 的取值：always, never, withText, collapseActionView, ifRoom

	always : 总是显示在 ActionBar的上面，如果 always的按钮过多，
	
			 会把 ActionBar的标题压缩，直到最后放不下为止。
			 
			排列的方式是，没有指定 orderInCategory的按钮优先级最高
			
			也就是先添加在ActionBar上面；指定orderInCategory的按钮
			
			优先级要小于未指定的，按照 orderInCategory的数值升序排列
			
			（从小到大）数值越小 优先级越高，在前面显示。
			
	never : 始终显示在 Action Overflow 当中，不会出现在 ActionBar 的上面。
	
			排列的方法，和 always 排列描述的一致
	
	
	withText : 建议显示标题
	
			在 Action Overflow 默认就是显示标题的，不需要考虑这个属性
			
			在 手机等设备横屏的情况下，ActionBar尺寸足够长，那么也会自动显示文本的。
			
			在当前屏幕宽度较小的情况下，ActionBar上面的按钮，通常是只显示图标的
			
			
	ifRoom  :  代表 如果 当前的ActionBar 界面上可以容纳当前的按钮，那么就显示在 ActionBar上 main
			
			否则，直接显示在 Action Overflow中。
			
	collapseActionView   ：  
	
			代表当前的这个 ActionBar 按钮是一种点击之后可以在ActionBar上 main显示新的界面的按钮。		
			
3. ActionBar 可以设置自己的导航模式，这些导航模式代表了
	包含下拉列表的模式，Tab的模式，还是有标准的模式（只有一个ActionBar，剩下都是按钮）
	
	NavigationMode  可以设置
	
	！！！Tab 必须要设置Listener 才可以添加到 ActionBar 当中；
	
	！！！添加的Tab 在屏幕较窄的情况下，显示在ActionBar的下面；对于横屏的情况，Tab 现在ActionBar的标题后面
	
	TabListener 包含三个回调方法，选中、取消选中、重新选中
	
		onTabSelected() 代表切换选择时，选中事件，即 0 切换到 1 或者从 1 切换到 0
		
		onTabReselected() 代表重复点击当前的Tab，即当前为0， 那么再点击 0时，回调这个方法
		
			利用这个方法，可以实现类似微博这种，点击自身Tab，刷新数据的功能
			
		onTabUnselected() 代表从当前Tab切换其他Tab的时候，回调通知，哪个Tab取消选择
		
			利用这个方法，可以把当前取消选中的Tab下的网络请求暂停、停止。	

	
					 