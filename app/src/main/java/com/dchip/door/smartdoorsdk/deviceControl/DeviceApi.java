package com.dchip.door.smartdoorsdk.deviceControl;



import com.dchip.door.smartdoorsdk.Bean.ApiGetCardListModel;
import com.dchip.door.smartdoorsdk.Bean.AppUpdateModel;
import com.dchip.door.smartdoorsdk.Bean.JsonResult;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author zhangdeming
 * @date 创建时间 2017/5/11
 * @description 描述类的功能
 */
public interface DeviceApi {

    /**
     * 查询服务器上最新版本号
     *
     * @param type 默认为1
     * @return
     */
    @FormUrlEncoded
    @POST("mine/version")
    Call<JsonResult<AppUpdateModel>> checkVersion(@Field("type") int type);

    /**
     * 上传主控板的MAC码
     *
     * @param mac
     * @return
     */
    @FormUrlEncoded
    @POST("maincontrol/uploadVersionInfo")
    Call<JsonResult<Object>> uploadAppVersion(@Field("mac") String mac, @Field("versionName") String versionName);

    /**
     * 上传主控板的MAC码
     *
     * @param mac
     * @return
     */
    @FormUrlEncoded
    @POST("maincontrol/upload")
    Call<JsonResult<Object>> uploadMac(@Field("mac") String mac);

    /**
     * 上传锁的数据
     *
     * @param mac
     * @param uid
     * @return
     */
    @FormUrlEncoded
    @POST("lockcontrol/upload")
    Call<JsonResult<Object>> uploadLock(@Field("mac") String mac, @Field("uid") String uid);

    /**
     * 锁板故障上报
     *
     * @param uid
     * @param type
     * @return
     */
    @FormUrlEncoded
    @POST("lockcontrol/reportFault")
    Call<JsonResult<Object>> reportFault(@Field("uid") String uid, @Field("type") int type);

    /**
     * appCrash 上传
     *
     * @param mac
     * @param errorContent
     * @return
     */
    @FormUrlEncoded
    @POST("errorlog/uploadError")
    Call<JsonResult<Object>> reportCrash(@Field("mac") String mac, @Field("errorContent") String errorContent);

    /**
     * 上传是否写卡成功
     *
     * @param mac
     * @param status
     * @return
     */
    @FormUrlEncoded
    @POST("maincontrol/updateTerminalRecord")
    Call<JsonResult<Object>> reportWriteCardStatus(@Field("mac") String mac, @Field("status") int status);

    /**
     * appCrash 上传
     *
     */
    @FormUrlEncoded
    @POST("access/setOpenByCardRecord")
    Call<JsonResult<Object>> uploadCardId(@Field("uid") String uid, @Field("cardId") String cardId, @Field("id") String phone);

    /**
     * appCrash 上传
     *
     */
    @FormUrlEncoded
    @POST("flow/save")
    Call<JsonResult<Object>> uploadFlow(@Field("mac") String mac, @Field("flows") String flows);

    /**
     * app更新失败上传
     *
     */
    @FormUrlEncoded
    @POST("maincontrol/uploadInstallFailReasion")
    Call<JsonResult<Object>> installFail(@Field("mac") String mac, @Field("failReasion") String failReasion);

    /**
     * app更新失败上传
     *
     */
    @FormUrlEncoded
    @POST("access/getCardListByMac")
    Call<JsonResult<ApiGetCardListModel>> getCardListByMac(@Field("mac") String mac);


//    /**
//     * 开锁成功返回
//     *
//     * @param uid
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("lockcontrol/openResult")
//    Call<JsonResult<Object>> openResult(@Field("uid") String uid);
}
