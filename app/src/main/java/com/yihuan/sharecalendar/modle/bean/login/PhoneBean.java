package com.yihuan.sharecalendar.modle.bean.login;

/**
 * Created by Ronny on 2018/1/16.
 * 手机注册情况
 */

public class PhoneBean {
        /**
         * local : true
         * weixin : false
         * weibo : false
         */

        private boolean local;
        private boolean weixin;
        private boolean weibo;

        public boolean isLocal() {
            return local;
        }

        public void setLocal(boolean local) {
            this.local = local;
        }

        public boolean isWeixin() {
            return weixin;
        }

        public void setWeixin(boolean weixin) {
            this.weixin = weixin;
        }

        public boolean isWeibo() {
            return weibo;
        }

        public void setWeibo(boolean weibo) {
            this.weibo = weibo;
        }
}
