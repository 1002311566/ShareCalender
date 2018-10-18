package com.yihuan.sharecalendar.modle.db;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.util.ArrayMap;

import com.yihuan.sharecalendar.modle.bean.active.TimeBean;
import com.yihuan.sharecalendar.modle.calendar.ActiveBean;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Ronny on 2018/1/4.
 */

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class ScheduleManager {
    private static ScheduleManager manager;
    private ArrayMap<String, List<ActiveBean>> map;

    private ScheduleManager() {
        map = new ArrayMap<>();
    }

    public static ScheduleManager getInstance() {
        if (manager == null) {
            synchronized (ScheduleManager.class) {
                if (manager == null) {
                    manager = new ScheduleManager();
                }
            }
        }
        return manager;
    }

    /**
     * 取得指定年月的一次性活动
     *
     * @param ym
     * @return
     */
    private List<ActiveBean> getOnceSchedule(String ym) {
        if (!map.containsKey(ym)) {
            List<ActiveBean> list = DBDao.getDao().getScheduleByYM(ym);//todo 该月的所有日程
            map.put(ym, list);
        }
        return map.get(ym);
    }

    /**
     * 添加日程
     *
     * @param activeBean
     * @return
     */
    public boolean addSchdule(ActiveBean activeBean) {
        long why = DBDao.getDao().addSchedule(activeBean);//添加到数据库
        if(why>0){
            List<ActiveBean> list = getOnceSchedule(activeBean.getStart_time().toYM());
            return list.add(activeBean);
        }
        return false;
    }

    /**
     * 编辑日程
     *
     * @param activeBean
     * @return
     */
    public boolean editSchdule(ActiveBean activeBean) {
        DBDao.getDao().updateSchdule(activeBean);
        String ym = activeBean.getStart_time().toYM();
        if(map.containsKey(ym)){
            List<ActiveBean> list = map.get(ym);
            Iterator<ActiveBean> iterator = list.iterator();
            while (iterator.hasNext()){
                ActiveBean next = iterator.next();
                if(next.getActive_id() == activeBean.getActive_id()){
                    iterator.remove();
                    return list.add(activeBean);
                }
            }
        }
        return false;
    }

    /**
     * 删除日程
     *
     * @return
     */
    public boolean deleteSchdule(ActiveBean activeBean) {
        Integer id = activeBean.getActive_id();
        DBDao.getDao().deleteSchdule(id);
        //一次性
        List<ActiveBean> list = getOnceSchedule(activeBean.getStart_time().toYM());
        Iterator<ActiveBean> i = list.iterator();
        while (i.hasNext()) {
            ActiveBean next = i.next();
            Integer active_id = next.getActive_id();
            if (active_id.intValue() == id.intValue()) {
                i.remove();
                return true;
            }
        }
        return false;
    }



    public void resetCurrentMonth(String ym) {
        List<ActiveBean> list = DBDao.getDao().getScheduleByYM(ym);
        map.put(ym, list);
    }

    public void resetAll() {
        map.clear();
    }

    /**
     * -1: 永不, 1: 每天, 2: 每周, 3: 每两周, 4: 每月, 5: 每年
     *
     * @return
     */
    public int queryYMD(TimeBean timeBean) {
        TimeBean currentTime = timeBean;
        int b = 0;

        List<ActiveBean> onceList = getOnceSchedule(currentTime.toYM());
        String currentYMD = currentTime.toYMD();
        for (ActiveBean bean : onceList) {
            //todo 当共享和个人标记都被赋值后，直接结束
            if (b == 11)
                return b;

            if (currentYMD.equals(bean.getStart_time().toYMD())) {
                if (bean.is_share_schdule()) {
                    if (b %10 == 1)
                        continue;
                    b = b + 1;
                } else {
                    if (b >= 10)
                        continue;
                    int i = b % 10;
                    b = 10+i;
                }
            }
//            //todo 当共享和个人标记都被赋值后，直接结束
//            if (ints[0] == 1 && ints[1] == 1)
//                return ints;
//
//            if (currentYMD.equals(bean.getStart_time().toYMD())) {
//                if (bean.is_share_schdule()) {
//                    if (ints[1] == 1)
//                        continue;
//                    ints[1] = 1;
//                } else {
//                    if (ints[0] == 1)
//                        continue;
//                    ints[0] = 1;
//                }
//            }
        }
        return b;
    }

}
