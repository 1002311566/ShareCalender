package com.yihuan.sharecalendar.modle.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ronny on 2017/12/29.
 */

public class AdvertisingBean implements Serializable{
        /**
         * id : 13
         * content : 撒
         * publisher : 1
         * putTime : 2018-01-19 11:53:55
         * priority : 100
         * toggle : 1
         * exeType : 1
         * durationTime : 5
         * exeLocation : 1
         * startTime : 2018-01-19 00:00:00
         * endTime : 2018-01-20 00:00:00
         * period : 00
         * constellationId : 1
         * adType : 1
         * skinId : null
         * location : 1
         * sex : 1
         * playType : 2
         * actTitle : 3
         * actTimeBegin : 2018-01-19
         * actTimeEnd : 2018-01-21
         * actAddress : 广州
         * adImages : [{"id":5,"adverid":13,"image":"permanently/e46/7cf/145/izwz9dc6tjfwoo65crzoadz_1516334031835_34_137f19b9665e518bacf65a75fd33aee4.png"}]
         * adPeriods : [{"id":127,"adId":13,"hourStartTime":"10:00","hourEndTime":"15:00"}]
         * constellation : {"id":1,"name":"水瓶座","detailName":"01月20日─02月18日"}
         */

        private int id;
        private String content;
        private String publisher;
        private String putTime;
        private int priority;
        private String toggle;
        private String exeType;
        private int durationTime;
        private String exeLocation;
        private String startTime;
        private String endTime;
        private String period;
        private int constellationId;
        private String adType;
        private Object skinId;
        private int location;
        private String sex;
        private String playType;
        private String actTitle;
        private String actTimeBegin;
        private String actTimeEnd;
        private String actAddress;
        private ConstellationBean constellation;
        private List<AdImagesBean> adImages;
        private List<AdPeriodsBean> adPeriods;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPublisher() {
            return publisher;
        }

        public void setPublisher(String publisher) {
            this.publisher = publisher;
        }

        public String getPutTime() {
            return putTime;
        }

        public void setPutTime(String putTime) {
            this.putTime = putTime;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public String getToggle() {
            return toggle;
        }

        public void setToggle(String toggle) {
            this.toggle = toggle;
        }

        public String getExeType() {
            return exeType;
        }

        public void setExeType(String exeType) {
            this.exeType = exeType;
        }

        public int getDurationTime() {
            return durationTime;
        }

        public void setDurationTime(int durationTime) {
            this.durationTime = durationTime;
        }

        public String getExeLocation() {
            return exeLocation;
        }

        public void setExeLocation(String exeLocation) {
            this.exeLocation = exeLocation;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getPeriod() {
            return period;
        }

        public void setPeriod(String period) {
            this.period = period;
        }

        public int getConstellationId() {
            return constellationId;
        }

        public void setConstellationId(int constellationId) {
            this.constellationId = constellationId;
        }

        public String getAdType() {
            return adType;
        }

        public void setAdType(String adType) {
            this.adType = adType;
        }

        public Object getSkinId() {
            return skinId;
        }

        public void setSkinId(Object skinId) {
            this.skinId = skinId;
        }

        public int getLocation() {
            return location;
        }

        public void setLocation(int location) {
            this.location = location;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getPlayType() {
            return playType;
        }

        public void setPlayType(String playType) {
            this.playType = playType;
        }

        public String getActTitle() {
            return actTitle;
        }

        public void setActTitle(String actTitle) {
            this.actTitle = actTitle;
        }

        public String getActTimeBegin() {
            return actTimeBegin;
        }

        public void setActTimeBegin(String actTimeBegin) {
            this.actTimeBegin = actTimeBegin;
        }

        public String getActTimeEnd() {
            return actTimeEnd;
        }

        public void setActTimeEnd(String actTimeEnd) {
            this.actTimeEnd = actTimeEnd;
        }

        public String getActAddress() {
            return actAddress;
        }

        public void setActAddress(String actAddress) {
            this.actAddress = actAddress;
        }

        public ConstellationBean getConstellation() {
            return constellation;
        }

        public void setConstellation(ConstellationBean constellation) {
            this.constellation = constellation;
        }

        public List<AdImagesBean> getAdImages() {
            return adImages;
        }

        public void setAdImages(List<AdImagesBean> adImages) {
            this.adImages = adImages;
        }

        public List<AdPeriodsBean> getAdPeriods() {
            return adPeriods;
        }

        public void setAdPeriods(List<AdPeriodsBean> adPeriods) {
            this.adPeriods = adPeriods;
        }

        public static class ConstellationBean implements Serializable{
            /**
             * id : 1
             * name : 水瓶座
             * detailName : 01月20日─02月18日
             */

            private int id;
            private String name;
            private String detailName;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDetailName() {
                return detailName;
            }

            public void setDetailName(String detailName) {
                this.detailName = detailName;
            }
        }

        public static class AdImagesBean implements Serializable{
            /**
             * id : 5
             * adverid : 13
             * image : permanently/e46/7cf/145/izwz9dc6tjfwoo65crzoadz_1516334031835_34_137f19b9665e518bacf65a75fd33aee4.png
             */

            private int id;
            private int adverid;
            private String image;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getAdverid() {
                return adverid;
            }

            public void setAdverid(int adverid) {
                this.adverid = adverid;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }
        }

        public static class AdPeriodsBean implements Serializable{
            /**
             * id : 127
             * adId : 13
             * hourStartTime : 10:00
             * hourEndTime : 15:00
             */

            private int id;
            private int adId;
            private String hourStartTime;
            private String hourEndTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getAdId() {
                return adId;
            }

            public void setAdId(int adId) {
                this.adId = adId;
            }

            public String getHourStartTime() {
                return hourStartTime;
            }

            public void setHourStartTime(String hourStartTime) {
                this.hourStartTime = hourStartTime;
            }

            public String getHourEndTime() {
                return hourEndTime;
            }

            public void setHourEndTime(String hourEndTime) {
                this.hourEndTime = hourEndTime;
            }
    }
}
