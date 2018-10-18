package com.yihuan.sharecalendar.modle;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.ArrayMap;

import com.yihuan.sharecalendar.modle.bean.friend.FriendListBean;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ronny on 2017/10/3.
 * 处理friend列表
 */

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class FriendModle {

    private ArrayMap<String, List<FriendListBean>> map = new ArrayMap<>();
    public ArrayMap<String, List<FriendListBean>> getFriends(List<FriendListBean> listBeen){
        init(listBeen);
        return map;
    }

    private void init(List<FriendListBean> listBeen) {
        for (FriendListBean bean : listBeen){
            if(map.containsKey(bean.getNicknameInitial())){
                map.get(bean.getNicknameInitial()).add(bean);
            }else{
                map.put(bean.getNicknameInitial(), new ArrayList<FriendListBean>());
                map.get(bean.getNicknameInitial()).add(bean);
            }
        }

    }
}
