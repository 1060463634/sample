package com.example.sample.network;

/**
 * Created by hello on 2017/6/14.
 */
public class Student {
    String name;
    String age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }


    /**
     * 添加
        Student student = new Student();
        student.setName("wangminglei");
        student.setAge("2");
        student.save();
     */


    /**
     * 更新
     *  ContentValues values = new ContentValues();
        values.put("title", "今日iPhone6发布");
        DataSupport.update(News.class, values, 2);

        ContentValues values = new ContentValues();
        values.put("title", "今日iPhone6 Plus发布");
        DataSupport.updateAll(News.class, values, "title = ? and commentcount > ?", "今日iPhone6发布", "0");

     */

    /**
     * 删除
     * DataSupport.delete(News.class, 2);
     * DataSupport.deleteAll(News.class, "title = ? and commentcount = ?", "今日iPhone6发布", "0");
     * DataSupport.deleteAll(News.class);
     */


    /**
     *  查询
     *  News news = DataSupport.find(News.class, 1);
     *  News firstNews = DataSupport.findFirst(News.class);
     *  News lastNews = DataSupport.findLast(News.class);
     *  List<News> allNews = DataSupport.findAll(News.class);
     *
         List<News> newsList = DataSupport.where("commentcount > ?", "0").find(News.class);
         List<News> newsList = DataSupport.select("title", "content")
        .where("commentcount > ?", "0")
        .order("publishdate desc")
        .limit(10).find(News.class);
     */

}
