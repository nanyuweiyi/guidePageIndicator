# FPageIndicator
用来显示当前所在页的页面指示器，具体样式见Readme中的Gif文件。

![image](./help.gif)

---------------
# 使用说明
1527835928823818.gif
1、在build.grade中引用：
```
implementation 'com.github.nanyuweiyi:guidePageIndicator:1.0.1'
```
2、在Activity中调用：
```
ViewPager pager = (ViewPager) findViewById(R.id.viewpager);
pager.setAdapter(new PicFragmentAdapter(getSupportFragmentManager()));

FPageIndicator indicator = (FPageIndicator) findViewById(R.id.indicator);
indicator.attachToViewPager(pager, this);

注意实现implements FPageIndicator.Callback
```
## 说明

|方法名   	|使用说明   	|
|:-:	|---	|
|setSelectedPos(int pos)   	|设置当前选中的位置.   	|
|setCount(int count)   	|设置 Page Indicator 总共的数目   	|
|attachToViewPager(ViewPager viewPager, Callback callback)   	|如果想和 ViewPager 绑定起来，直接调用这个接口即可. 参数callback是为了判断是否到最后一张，必须实现FPageIndicator.Callback  	|

可自定义的地方

|属性名   	|解释   	|
|:-:	|---	|
|pi_count   	|总数目 	|
|pi_out_radius   	|外圈的大小 	|
|pi_radius   	|内圈的大小 	|
|pi_un_focus_color   	|未选中的颜色 	|
|pi_focus_color   	|选中时的颜色 	|
|pi_padding   	|两点之间的间距 	|


欢迎大家随时指出问题，谢谢。
