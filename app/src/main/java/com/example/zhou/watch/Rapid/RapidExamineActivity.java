package com.example.zhou.watch.Rapid;


import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.zhou.watch.R;

public class RapidExamineActivity extends AppCompatActivity{

    private Fragment rapidExamineTipFragment = new RapidExamineTipFragment();
    private Fragment rapidExamineFragment = new RapidExamineFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rapid_examine);
        replaceFragment(0);

        /*Button startChecked = (Button)findViewById(R.id.start_checked);
        startChecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new RapidExamineFragment());
            }
        });*/
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.start_checked:
//                replaceFragment(new RapidExamineFragment());
//        }
//    }

    /**
     * 切换Fragment，如果是0，切换到提示页面，
     * 如果是1，切换到快速体检界面
     * @param which 变量0和1
     */
    public void replaceFragment(int which) {
        Fragment fragment = (which == 0) ? rapidExamineTipFragment : rapidExamineFragment;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);   //加入切换动画
        transaction.replace(R.id.check_fragment, fragment);
//        transaction.addToBackStack(null);
        transaction.commit();
    }
    public void saveResult(){
        SharedPreferences.Editor editor = getSharedPreferences("result", MODE_PRIVATE).edit();
        RapidExamineFragment ref = (RapidExamineFragment) getSupportFragmentManager().findFragmentById(R.id.check_fragment);
        editor.putString("血压",ref.xueya.getText().toString());
        editor.putString("心率",ref.xinlv.getText().toString());
        editor.putString("血氧", ref.xueyang.getText().toString());
        editor.putString("呼吸率", ref.nongdu.getText().toString());
        editor.apply();
        Toast.makeText(this ,"保存成功",Toast.LENGTH_SHORT).show();
    }
}
