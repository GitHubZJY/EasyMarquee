# EasyMarquee [![](https://jitpack.io/v/GitHubZJY/EasyMarquee.svg)](https://jitpack.io/#GitHubZJY/EasyMarquee)
一个基于ViewFlipper实现的滚动轮播组件，支持设置滚动时长，默认可选垂直或水平滚动，支持设置自定义滚动动画和自定义样式<br/>
A running lantern component based on view flipper with scrolling vertically or horizontally. Supports setting the rolling time, also supports setting custom rolling animation and custom style.

## 特性
1. 基于ViewFlipper实现，继承ViewFlipper的所有方法 <br/>
2. 支持设置滚动方向和滚动时长 <br/>
3. 支持自定义每个滚动项的样式 <br/>
4. 支持动态更新滚动数据 <br/>
5. 支持添加滚动监听 <br/>
5. 支持AndroidX <br/>

## 效果预览
![](https://github.com/GitHubZJY/EasyMarquee/blob/master/preview/easy_marquee.gif)

## 如何使用
在项目根目录的build.gradle添加：
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

在项目的build.gradle添加如下依赖：
```
implementation 'com.github.GitHubZJY:EasyMarquee:v1.0.0'
```

### 1.在xml中引用EasyMarquee

```xml
<com.zjy.marqueelib.EasyMarqueeView
        android:id="@+id/marquee_view"
        android:layout_width="match_parent"
        android:layout_height="100dp"
        app:marquee_auto_flip="false"
        app:marquee_duration="500"
        app:marquee_flip_interval="3000"
        app:marquee_orientation="top_to_bottom"
        />
```

### 2.在代码中初始化EasyMarqueeView
```java
EasyMarqueeView easyMarqueeView = findViewById(R.id.marquee_view);
//设置滚动监听
easyMarqueeView.setMarqueeListener(new MarqueeListener() {
    @Override
    public void onMarquee(int position) {
        Log.d("onMarquee", "position: " + position);
    }
});
```

### 3.绑定数据
绑定数据模仿RecyclerView的适配器模式设计，与数据相关的操作均通过 `MarqueeAdapter` 作为中间者来进行.
#### 1.自定义一个adapter继承于 `MarqueeAdapter` ，通过实现adapter的方法，自定义滚动项的样式。`MarqueeAdapter` 的各个方法作用如下：
>**onCreateView** 创建每一个滚动项的视图 <br/>
**onBindView** 绑定每一个滚动项的视图数据

```java
private MarqueeAdapter<String> adapter = new MarqueeAdapter<String>() {
    @Override
    public View onCreateView(int position, String data) {
        return LayoutInflater.from(MainActivity.this).inflate(R.layout.item_layout, null);
    }

    @Override
    public void onBindView(final int position, View view, final String data) {
        TextView titleView = view.findViewById(R.id.item_title);
        if (position % 2 == 0) {
            view.setBackgroundColor(Color.LTGRAY);
        } else {
            view.setBackgroundColor(Color.RED);
        }
        titleView.setText(data);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
            }
       });
    }
};
```

#### 2.通过adapter的 `setData` 绑定自定义数据源.

```java
List<String> dataList = new ArrayList<>();
dataList.add("title1");
dataList.add("title2");
dataList.add("title3");
dataList.add("title4");
adapter.setData(dataList);
```

#### 3.将adapter设置给EasyMarqueeView.

```
easyMarqueeView.setMarqueeAdapter(adapter);
```
&nbsp;
### 4.数据变更时刷新视图.
数据变更也是通过adapter对象来进行：<br/>
如果是所有数据替换，可调用 `setData` 方法设置新的数据. <br/>
如果是单条数据刷新，可调用 `notifyItemChanged(int position, T data)` 进行更新，position是对应的下标，data为新的数据. <br/>
如果是单条数据插入，可调用 `notifyItemInsert(int position, T data)` 进行更新，position是对应的下标，data为新的数据.
&nbsp;
### 5.开始滚动
```
easyMarqueeView.startFlip();
```
&nbsp;
### 5.结束滚动
一般建议在Activity销毁的时候停止滚动
```
@Override
protected void onDestroy() {
    super.onDestroy();
    easyMarqueeView.stopFlip();
}
```
&nbsp;
### 6.其他属性.
本库也提供了其它的一些配置，例如： `getCurrentItem` 、 `setFlipInterval` 等，同时也继承了 `ViewFlipper` 的方法，后续会再根据需要进行扩充.
本库的自定义样式和数据类型参考适配器的模式设计，滚动轮播效果已经基本满足需求，后续会再针对视图的复用和其它拓展属性继续更新，提升组件的性能，欢迎issue和star~

&nbsp;
## About Me
一个在奋斗路上的Android小生 <br/>
GitHub: [GitHubZJY](https://github.com/GitHubZJY) <br/>
欢迎关注简书: [Android小Y](https://www.jianshu.com/u/4cb2688ddf31)
