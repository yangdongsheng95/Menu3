package com.example.dc.menu3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<String> data;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        listView= (ListView) findViewById(R.id.listView);
        data=new ArrayList<>();
        for(int i=0;i<30;i++){
            data.add("data"+i);
        }
        adapter=new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_activated_1
                ,
                data
        );

        listView.setAdapter(adapter);

        //长按进入多选模式
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);

        //注册监听器
        listView.setMultiChoiceModeListener(new CabListener());
    }

    //选项菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);

        MenuItem item=menu.findItem(R.id.action_search);
        SearchView searchView= (SearchView) item.getActionView();
        searchView.setQueryHint("文件名");

        return super.onCreateOptionsMenu(menu);
    }

    //选中菜单
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    class CabListener implements AbsListView.MultiChoiceModeListener{

        /**
         * 状态改变
         * @param mode      模式
         * @param position  状态改变时，多选时点的哪一个，位置，索引
         * @param id        数据在数据库中,pk的值
         * @param checked   是否选中
         */
        @Override
        public void onItemCheckedStateChanged(
                ActionMode mode,
                int position,
                long id,
                boolean checked) {
            //获得listView选中的数量
            int n=listView.getCheckedItemCount();
            mode.setTitle(String.valueOf(n));
        }

        /**
         * 创建操作模式(加载菜单文件)
         * @param mode  模式
         * @param menu  菜单
         * @return
         */
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            getMenuInflater().inflate(R.menu.operation, menu);
            return true;
        }

        /**
         * 对模式的预处理(创建后执行 OnCreate之后执行)
         * @param mode
         * @param menu
         * @return
         */
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        /**
         * 选中了菜单项
         * @param mode
         * @param item
         * @return
         */
        @Override
        public boolean onActionItemClicked(
                ActionMode mode,
                MenuItem item) {

            switch (item.getItemId()){
                case R.id.action_delete:
                    doDelete();
                    break;
                case R.id.action_copy:
                    break;
            }

            //退出操作模式
            mode.finish();

            return true;
        }

        /**
         * 离开多选模式
         * @param mode
         */
        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }
    }

    /**
     * 删除
     */
    private void doDelete() {
        //选中的内容
        SparseBooleanArray array= listView.getCheckedItemPositions();
        for(int i=array.size()-1;i>=0;i--){
            int key=array.keyAt(i);
            if(array.get(key)){
                data.remove(key);
            }
        }

        Toast.makeText(MainActivity.this,array.toString(),Toast.LENGTH_SHORT).show();
    }
}
