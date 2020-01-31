package com.example.neva_sandbox;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

// https://recipes4dev.tistory.com/45
// https://recipes4dev.tistory.com/43#recentComments
public class ListViewBtnAdapter extends ArrayAdapter implements View.OnClickListener {
    // 버튼 클릭 이벤트를 위한 Listener 인터페이스 정의.
    public interface ListBtnClickListener {
        void onListBtnClick(String ssid) ;
    }

    // 생성자로부터 전달된 resource id 값을 저장.
    int resourceId ;
    // 생성자로부터 전달된 ListBtnClickListener  저장.
    private ListBtnClickListener listBtnClickListener ;

    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ListViewBtnItem> listViewItemList = new ArrayList<ListViewBtnItem>() ;

    // ListViewBtnAdapter 생성자. 마지막에 ListBtnClickListener 추가.
    ListViewBtnAdapter(Context context, int resource, /*ArrayList<ListViewBtnItem> list, */ListBtnClickListener clickListener) {
        super(context, resource/*, list*/) ;

        // resource id 값 복사. (super로 전달된 resource를 참조할 방법이 없음.)
        this.resourceId = resource ;

        this.listBtnClickListener = clickListener ;
    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_btn_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        Button ssidButton = (Button) convertView.findViewById(R.id.buttonSSID) ;
        TextView secTextView = (TextView) convertView.findViewById(R.id.textViewSec) ;
        TextView strTextView = (TextView) convertView.findViewById(R.id.textViewStr) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListViewBtnItem listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        ssidButton.setText(listViewItem.getSsid());
        secTextView.setText(listViewItem.getSec());
        strTextView.setText(listViewItem.getStr());

        // button의 TAG에 position값 지정. Adapter를 click listener로 지정.
        ssidButton.setTag(position);
        ssidButton.setOnClickListener(this);

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String ssid, String sec, String str) {
        ListViewBtnItem item = new ListViewBtnItem();

        item.setSsid(ssid);
        item.setSec(sec);
        item.setStr(str);

        listViewItemList.add(item);
    }

    // button이 눌려졌을 때 실행되는 onClick함수.
    public void onClick(View v) {
        // ListBtnClickListener(MainActivity)의 onListBtnClick() 함수 호출.
        if (this.listBtnClickListener != null) {
            // https://recipes4dev.tistory.com/45
            Button ssidButton = (Button) v.findViewById(R.id.buttonSSID) ;
            this.listBtnClickListener.onListBtnClick((String) ssidButton.getText()) ; //(int)v.getTag()
        }
    }
}
