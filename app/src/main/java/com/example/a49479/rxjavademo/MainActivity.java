package com.example.a49479.rxjavademo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

public class MainActivity extends AppCompatActivity {

    private static final String tag = "MainActivity";

    ImageView iv;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        abc();
//        printArr();
        tv = (TextView) findViewById(R.id.tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printArr();
            }
        });

        iv = (ImageView)findViewById(R.id.iv);

//        mapTest();

        printStudentCourse();
    }


    public void abc() {
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onNext(String s) {
                Log.d(tag, "Item: " + s);
            }

            @Override
            public void onCompleted() {
                Log.d(tag, "Completed!");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(tag, "Error!");
            }
        };

        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("Hi");
                subscriber.onNext("Aloha");
                subscriber.onCompleted();
            }
        });

        observable.subscribe(subscriber);
    }


    public void printArr() {
        String[] names = {"a", "b", "c", "d", "e", "f", "g"};
        Observable.from(names)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String name) {
                        Log.d(tag, name);
                    }
                });

    }


    /**
     * map
     */
    public void mapTest(){
        Observable.just(R.mipmap.girl,R.mipmap.girl) // 输入类型 int
                .map(new Func1<Integer, Bitmap>() {
                    @Override
                    public Bitmap call(Integer res) { // 参数类型 String
                        return getBitmapFromResource(res); // 返回类型 Bitmap
                    }
                })
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) { // 参数类型 Bitmap
                        showBitmap(bitmap);
                    }
                });
    }

    public Bitmap getBitmapFromResource(int res){
        return BitmapFactory.decodeResource(getResources(),res);
    }

    public void showBitmap(Bitmap bitmap){
        iv.setImageBitmap(bitmap);
    }

    public void printStudentCourse(){
        Student[] students = new Student[4];
        students[0] = new Student(new Course[]{new Course("语文"),new Course("数学"),new Course("英语"),new Course("历史")},"大名");
        students[1] = new Student(new Course[]{new Course("语文"),new Course("数学"),new Course("英语"),new Course("化学")},"大名");
        students[2] = new Student(new Course[]{new Course("体育")},"大名");
        students[3] = new Student(new Course[]{new Course("艺术")},"大名");

        Subscriber<Course> subscriber = new Subscriber<Course>() {
            @Override
            public void onNext(Course course) {
                Log.d(tag, course.getName());
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }
        };
        Observable.from(students)
                .flatMap(new Func1<Student, Observable<Course>>() {
                    @Override
                    public Observable<Course> call(Student student) {
                        return Observable.from(student.getCourses());
                    }
                })
                .subscribe(subscriber);
    }

}
