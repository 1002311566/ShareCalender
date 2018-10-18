package com.yihuan.sharecalendar.wxapi;

import android.graphics.Bitmap;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.yihuan.sharecalendar.global.App;

/**
 * Created by Ronny on 2017/12/7.
 */

public class WXShareHelper {
    public static final String TO_FRIEND = "to_friend";
    public static final String TO_CIRCLE_FRIEND = "to_circle_friend";

    /**
     * todo 分享文字
     * @param text
     * @param toWhere
     */
    public static void shareText(String text, String toWhere){
        WXTextObject wxTextObject = new WXTextObject();
        wxTextObject.text = text;

        WXMediaMessage wxMediaMessage = new WXMediaMessage();
        wxMediaMessage.mediaObject = wxTextObject;
        wxMediaMessage.description = text;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = System.currentTimeMillis()+"--text";
        req.message = wxMediaMessage;

        if(toWhere != null && toWhere.equals(TO_FRIEND)){
            //todo 分享给好友
            req.scene = SendMessageToWX.Req.WXSceneSession;
        }else if(toWhere != null && toWhere.equals(TO_CIRCLE_FRIEND)){
            //todo 分享到朋友圈
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }
        App.api.sendReq(req);
    }

    /**
     * todo 分享图片
     * @param bitmap
     * @param toWhere
     */
    public static void shareImg(Bitmap bitmap, String toWhere){
        WXImageObject imageObject = new WXImageObject(bitmap);
        WXMediaMessage mediaMessage = new WXMediaMessage();
        mediaMessage.mediaObject = imageObject;

        //todo 设置缩略图
        Bitmap.createScaledBitmap(bitmap, 10, 10, true);
        bitmap.recycle();
//        mediaMessage.thumbData = Util.//todo 将图片转成字节数组

        //todo 构造Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = System.currentTimeMillis() + "--img";
        req.message = mediaMessage;
        if(toWhere != null && toWhere.equals(TO_FRIEND)){
            //todo 分享给好友
            req.scene = SendMessageToWX.Req.WXSceneSession;
        }else if(toWhere != null && toWhere.equals(TO_CIRCLE_FRIEND)){
            //todo 分享到朋友圈
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }
        App.api.sendReq(req);

    }

    public static void shareUrl(String url, String title, String des, Bitmap bitmap, String toWhere){
        WXWebpageObject object = new WXWebpageObject();
        object.webpageUrl = url;

        WXMediaMessage message = new WXMediaMessage(object);
        message.title = title;//网页标题
        message.description = des;//网页描述
        if(bitmap != null){
//            message.thumbData  = //todo bitmap 转 字节数组
        }
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message = message;
        if(toWhere != null && toWhere.equals(TO_FRIEND)){
            //todo 分享给好友
            req.scene = SendMessageToWX.Req.WXSceneSession;
        }else if(toWhere != null && toWhere.equals(TO_CIRCLE_FRIEND)){
            //todo 分享到朋友圈
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }
        App.api.sendReq(req);
    }
}
