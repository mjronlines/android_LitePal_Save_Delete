# androi 
# android_LitePal_Save_Delete
- 一个简单的安卓 LitePal 实用案例
### 步骤1：下载
```
compile 'org.litepal.android:core:1.4.0'
```
### 步骤2：
- 配置文件litepal.xml 在 assets 文件夹
```
<?xml version="1.0" encoding="utf-8"?>
<litepal>
    <dbname value="demo" ></dbname> <!--数据库名称-->

    <!--
       版本号
    -->
    <version value="1" ></version>

    <!--
        映射实体类
        <list>
            <mapping class="com.test.model.Reader"></mapping>
            <mapping class="com.test.model.Magazine"></mapping>
        </list>
    -->
    <list>
    </list>

    <!--
        Define where the .db file should be. "internal" means the .db file
        will be stored in the database folder of internal storage which no
        one can access. "external" means the .db file will be stored in the
        path to the directory on the primary external storage device where
        the application can place persistent files it owns which everyone
        can access. "internal" will act as default.
        For example:
        <storage value="external"></storage>
    -->

</litepal>
```
### 步骤3：配置LitePalApplication
```
<manifest>
    <application
        android:name="org.litepal.LitePalApplication"
        ...
    >
    ...
    </application>
</manifest>
```
#### 或者自己重写的的Application
```
<manifest>
    <application
        android:name="com.example.MyOwnApplication"
        ...
    >
    ...
    </application>
</manifest>
```
```
public class MyOwnApplication extends AnotherApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
    }
    ...
}
```

### 使用：
- 1、创建表
```
public class Album extends DataSupport {

    @Column(unique = true, defaultValue = "unknown")
    private String name;

    private float price;

    private byte[] cover;

    private List<Song> songs = new ArrayList<Song>();

    // generated getters and setters.
    ...
}
```
```
public class Song extends DataSupport {

    @Column(nullable = false)
    private String name;

    private int duration;

    @Column(ignore = true)
    private String uselessField;

    private Album album;

    // generated getters and setters.
    ...
}
```
### 在litepal.xml 添加映射
```
<list>
    <mapping class="org.litepal.litepalsample.model.Album"></mapping>
    <mapping class="org.litepal.litepalsample.model.Song"></mapping>
</list>
```
- 2、获取数据库  必须使用否则报空错误 放在application或第一个启动的activity中都可以
```
public class Album extends DataSupport {

    @Column(unique = true, defaultValue = "unknown")
    private String name;

    private float price;

    private byte[] cover;

    private List<Song> songs = new ArrayList<Song>();

    // generated getters and setters.
    ...
}
public class Song extends DataSupport {

    @Column(nullable = false)
    private String name;

    private int duration;

    @Column(ignore = true)
    private String uselessField;

    private Album album;

    // generated getters and setters.
    ...
}
```

### 在litepal.xml 添加映射
```
<list>
    <mapping class="org.litepal.litepalsample.model.Album"></mapping>
    <mapping class="org.litepal.litepalsample.model.Song"></mapping>
</list>
```

- 2、获取数据库  必须使用否则报空错误 放在application或第一个启动的activity中都可以
```
SQLiteDatabase db = LitePal.getDatabase();
```

- 3、更新表 只需要修改Model
```
public class Album extends DataSupport {

    @Column(unique = true, defaultValue = "unknown")
    private String name;

    @Column(ignore = true)
    private float price;

    private byte[] cover;

    private Date releaseDate;

    private List<Song> songs = new ArrayList<Song>();

    // generated getters and setters.
    ...
}
```
- 更新表后，然后增加litepal.xml中的版本号：
```
<version value="2" ></version>
```

- 3、保存数据
```
Album album = new Album();
album.setName("album");
album.setPrice(10.99f);
album.setCover(getCoverImageBytes());
album.save();
Song song1 = new Song();
song1.setName("song1");
song1.setDuration(320);
song1.setAlbum(album);
song1.save();
Song song2 = new Song();
song2.setName("song2");
song2.setDuration(356);
song2.setAlbum(album);
song2.save();
```
- 4、更新数据
```
Album albumToUpdate = DataSupport.find(Album.class, 1);
albumToUpdate.setPrice(20.99f); // raise the price
albumToUpdate.save();
```
- 具体的id更新
```
Album albumToUpdate = new Album();
albumToUpdate.setPrice(20.99f); // raise the price
albumToUpdate.update(id);
```
- 使用条件更新多条
```
Album albumToUpdate = new Album();
albumToUpdate.setPrice(20.99f); // raise the price
albumToUpdate.updateAll("name = ?", "album");
```
- 5、删除数据
```
DataSupport.delete(Song.class, id); //删除一条
DataSupport.deleteAll(Song.class, "duration > ?" , "350"); //删除多条
```
- 6、查询数据
```
Song song = DataSupport.find(Song.class, id); // 通过id查询1条数据
List<Song> allSongs = DataSupport.findAll(Song.class); //查询所有数据
List<Song> songs = DataSupport.where("name like ?", "song%").order("duration").find(Song.class); //复杂查询
```

