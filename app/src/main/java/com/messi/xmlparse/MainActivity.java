package com.messi.xmlparse;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.thoughtworks.xstream.XStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  @BindView(R.id.tv) TextView tv;
  private List<Student> students;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
  }

  @OnClick({ R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4 }) public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.btn4:
        //InputStream is = null;
        //try {
        //  is = getResources().getAssets().open("student.xml");
        //} catch (IOException e) {
        //  e.printStackTrace();
        //}
        //XStream xstream = new XStream();
        //xstream.alias("student", Student.class);
        //Student stu = (Student) xstream.fromXML(is);
        //tv.setText(stu.toString());
        Stu stu = new Stu();
        stu.setNickName("小花");
        stu.setName(stu.new Name("哈哈11", "女"));
        XStream xstream = new XStream();
        String xml = xstream.toXML(stu);
        tv.setText(xml);
        stu = (Stu) xstream.fromXML(xml);
        Toast.makeText(MainActivity.this, stu.toString(), Toast.LENGTH_LONG).show();
        break;
      case R.id.btn1:
        try {
          students = XmlUtils.sax(getResources().getAssets().open("stu.xml"));
          tv.setText(students.toString());
        } catch (Exception e) {
          e.printStackTrace();
        }
        break;
      case R.id.btn2:
        try {
          students = XmlUtils.pull(getResources().getAssets().open("stu.xml"));
          tv.setText(students.toString());
        } catch (Exception e) {
          e.printStackTrace();
        }
        break;
      case R.id.btn3:
        try {
          students = XmlUtils.dom(getResources().getAssets().open("stu.xml"));
          tv.setText(students.toString());
        } catch (Exception e) {
          e.printStackTrace();
        }
        break;
    }
  }
}
