# ImoocDaily
Android 记账本

这个项目是学习 eclipse_xu 的慕课网课程《Android 记账本》的学习项目。

使用到的开源库: hellocharts-android。

下面是一些笔记：

1. 视频的bug。

第2章 数据库的创建和数据测试，里面的2-1和2-2是完全一样的内容。并且这两个视频都是中间有一部分没有声音，黑屏了，但对学习的内容没有什么影响。

第4章，最后一部分的代码没有写完，视频就结束了。不过其实影响也不大，可以去 hellocharts-android 的 github 官方页面里找一下用法。其实就加几行代码就完事了。

2. 一个过时的用法

singleLine = “true" 这个用法，Android Studio 已经提示说废弃了，可以替换为 maxLines = "1"。

3. 一些补充的知识

android:ellipsize="marquee" 这个属性，作用是当 TextView 里的内容超过控件的宽度时，应该怎样显示。
当属性值设为 start、middle、end 时，内容（显示顺序为从左到右时）会分别从左边、中间、右边省略，也就是显示三个点的省略号 ...  
当属性值为 none 时，内容会直接截断
上面四种属性值，TextView 都会无法显示出被省略的内容。
而当属性值为marquee时，内容会从 end 位置省略，并且可以通过水平滑动，显示被省略的内容。

4. 一点点的优化

这里用 EditText 来接收 title 和 money，后面的 money 有被转成数字的操作，所以最好给 money 的 EditText 设置 inputType 为 numberSigned，这样可以确保转换数字的时候不会出现异常。
