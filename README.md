# LearnAIDL
a demo for learnning AIDL

## NOTICE

- IService.aidl，自定义类.aidl 还有自定义类.java 需要都放在 aidl 包里。
- aidl中支持的参数类型为：基本类型。
  String,CharSequence,List,Map等其他类型必须使用import导入，即使它们可能在同一个包里。

- 接口中的参数除了aidl支持的类型，其他类型必须标识其方向：
  到底是输入还是输出抑或两者兼之，用in，out或者inout来表示。

- Stub类就是一个普通的Binder，只不过它实现了我们定义的aidl接口。
  它还有一个静态方法asInterface(android.os.IBinder obj),这个方法很有用，
  通过它我们就可以在客户端中得到IMyService的实例，进而通过实例来调用其方法。

- parcelable是个类型，首字母是小写的，和Parcelable接口不是一个东西，要注意。

- 有可能你的service只想让某个特定的apk使用，而不是所有apk都能使用。
  这个时候，你需要重写Stub中的onTransact方法，根据调用者的uid来获得其信息，
  然后做权限认证，如果返回true，则调用成功，否则调用会失败。对于其他apk，
  你只要在onTransact中返回false就可以让其无法调用IMyService中的方法。

- 客户端存放aidl文件的包必须和服务端一样。

- transact和onTransact的区别
IBinder的主要API是transact()，与它对应另一方法是Binder.onTransact()。
第一个方法使你可以向远端的IBinder对象发送发出调用，第二个方法使你自己的远程对象能够响应接收到的调用。
IBinder的API都是同步执行的，比如transact()直到对方的Binder.onTransact()方法调用完成后才返回。
调用发生在进程内时无疑是这样的，而在进程间时，在IPC的帮助下，也是同样的效果。
